import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../services/usuario/Usuario.service';
import { EnumUsuario } from '../../../models/usuario/EnumUsuario';

@Component({
    selector: 'app-user-form',
    standalone: true,
    imports: [ReactiveFormsModule, CommonModule, RouterLink],
    templateUrl: './user-form.component.html'
})
export class UserFormComponent implements OnInit {

    userForm!: FormGroup;
    selectedFile: File | null = null;

    isLoading = signal(false);
    isEditMode = signal(false);
    userIdToEdit: number | null = null;
    errorMessage = signal('');
    successMessage = signal('');
    avatarPreview = signal<string | null>(null);

    currentUserRole: string | null = null;
    rolACrear: string = EnumUsuario.comun; // Por defecto
    mostrarCamposComunes: boolean = true; // Telefono, Pais, Avatar (Solo para COMUN)
    mostrarInputEmpresa: boolean = false; // Solo para ADMIN_SISTEMA creando MANAGER

    private fb = inject(FormBuilder);
    private userService = inject(UsuarioService);
    private router = inject(Router);
    private route = inject(ActivatedRoute);

    ngOnInit(): void {
        this.currentUserRole = localStorage.getItem('rol');
        this.verificarPermisosYConfigurar();
        this.inicializarFormulario();

        // Verificar si hay un ID en la URL (Modo Edición)
        const idParam = this.route.snapshot.paramMap.get('id');
        if (idParam) {
            this.isEditMode.set(true);
            this.userIdToEdit = Number(idParam);
            this.cargarDatosUsuario(this.userIdToEdit);
        }
    }

    private verificarPermisosYConfigurar() {
        // Lógica de Jerarquía definida en tu prompt
        if (!this.currentUserRole) {
            // 1. Usuario Público -> Se registra él mismo
            this.rolACrear = EnumUsuario.comun;
            this.mostrarCamposComunes = true;
            this.mostrarInputEmpresa = false;
        }
        else if (this.currentUserRole === EnumUsuario.admin_sistema) {
            // 2. Admin Sistema -> Crea Manager
            this.rolACrear = EnumUsuario.manager;
            this.mostrarCamposComunes = false; // Solo email, nombre, pass, fecha
            this.mostrarInputEmpresa = true;   // Debe escribir el nombre de la empresa nueva
        }
        else if (this.currentUserRole === EnumUsuario.manager) {
            // 3. Manager -> Crea Admin Empresa
            this.rolACrear = EnumUsuario.admin_empresa;
            this.mostrarCamposComunes = false;
            this.mostrarInputEmpresa = false; // La empresa se toma del localStorage del Manager
        }
    }

    private inicializarFormulario() {
        this.userForm = this.fb.group({
            nombre: ['', [Validators.required]],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(4)]], // En edición podría ser opcional
            fecha_nacimiento: ['', [Validators.required]],
            // Campos opcionales o condicionales
            telefono: [''],
            pais: [''],
            nombreEmpresa: [''], // Solo visible si es ADMIN_SISTEMA
            avatar: [null]
        });

        // Validaciones dinámicas según rol (opcional, pero recomendado)
        if (this.rolACrear === EnumUsuario.comun) {
            this.userForm.get('pais')?.setValidators([Validators.required]);
            this.userForm.get('telefono')?.setValidators([Validators.required]);
        }
    }

    // Evento cuando seleccionan una imagen
    onFileSelected(event: any) {
        const file = event.target.files[0];
        if (file) {
            this.selectedFile = file;
            this.userForm.patchValue({ avatar: file });
        }

        // --- LÓGICA DE PREVISUALIZACIÓN ---
        const reader = new FileReader();

        // Cuando termine de leer el archivo, actualizamos la señal
        reader.onload = () => {
            this.avatarPreview.set(reader.result as string);
        };

        // Leemos el archivo como una URL de datos (Base64)
        reader.readAsDataURL(file);

    }

    submit() {
        if (this.userForm.invalid) {
            this.userForm.markAllAsTouched();
            return;
        }

        this.isLoading.set(true);
        this.errorMessage.set('');

        // Construimos el FormData (Obligatorio para enviar archivos + texto)
        const formData = new FormData();
        const formValues = this.userForm.value;

        // 1. Datos Básicos
        formData.append('nombre', formValues.nombre);
        formData.append('email', formValues.email);
        formData.append('password', formValues.password);
        formData.append('fecha_nacimiento', formValues.fecha_nacimiento);
        formData.append('rol', this.rolACrear); // El rol lo decidimos nosotros por lógica

        // 2. Datos Condicionales
        if (this.rolACrear === EnumUsuario.comun) {
            formData.append('telefono', formValues.telefono);
            formData.append('pais', formValues.pais);
            if (this.selectedFile) {
                formData.append('avatar', this.selectedFile);
            }
        }

        // 3. Datos de Empresa
        if (this.rolACrear === EnumUsuario.manager) {
            // El Admin Sistema escribe el nombre de la empresa
            formData.append('nombreEmpresa', formValues.nombreEmpresa);
        } else if (this.rolACrear === EnumUsuario.admin_empresa) {
            // El Manager hereda su empresa al nuevo usuario
            const miEmpresa = localStorage.getItem('nombreEmpresa') || '';
            formData.append('nombreEmpresa', miEmpresa);
        }

        // Enviar al Backend
        if (this.isEditMode()) {
            // Lógica de edición (cuando tengas el endpoint)
            console.log('Editando usuario...', formData);
            this.isLoading.set(false);
        } else {
            this.userService.crearUsuario(formData).subscribe({
                next: () => {
                    this.isLoading.set(false);
                    this.successMessage.set('Usuario creado exitosamente');
                    this.userForm.reset();
                    this.selectedFile = null;
                    // Opcional: Redirigir
                    setTimeout(() => this.router.navigate(['/home']), 2000);
                },
                error: (err) => {
                    this.isLoading.set(false);
                    this.errorMessage.set(err.error?.error || 'Error al crear usuario');
                }
            });
        }
    }

    // Método stub para cargar datos al editar
    private cargarDatosUsuario(id: number) {
        // Aquí llamarías a this.userService.obtenerUsuario(id)...
        // y usarías this.userForm.patchValue(data);
    }
}