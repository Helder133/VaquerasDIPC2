import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from "@angular/router";
import { CommonModule } from '@angular/common';
import { LoginService } from '../../services/login/Login.service';
import { LoginRequest } from '../../models/login/LoginRequest';

@Component({
    selector: 'app-login-page',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        CommonModule,
        RouterLink
    ],
    templateUrl: './login-page.component.html'
})
export class LoginPageComponent implements OnInit {

    loginFormGroup!: FormGroup;
    isLoading: boolean = false;
    isError: boolean = false;
    mensajeError: string = '';

    private formBuilder = inject(FormBuilder);
    private loginService = inject(LoginService);
    private router = inject(Router);

    ngOnInit(): void {
        this.loginFormGroup = this.formBuilder.group({
            email: ['', [Validators.required, Validators.email, Validators.maxLength(200)]],
            contraseña: ['', [Validators.required, Validators.maxLength(100)]]
        });
    }

    submit(): void {

        if (this.loginFormGroup.invalid) {
            this.loginFormGroup.markAllAsTouched();
            return;
        }

        this.isError = false;
        this.isLoading = true;
        const loginRequest: LoginRequest = this.loginFormGroup.value;

        this.loginService.loginUser(loginRequest).subscribe({
            next: (resp) => {
                this.isLoading = false;
                localStorage.setItem('rol', resp.rol);
                localStorage.setItem('usuario_id', JSON.stringify(resp.usuario_id));
                localStorage.setItem('nombreEmpresa', resp.nombreEmpresa);
                localStorage.setItem('empresa_id', JSON.stringify(resp.empresa_id));
                localStorage.setItem('nombre', resp.nombre);
                localStorage.setItem('avatar', resp.avatar);

                console.log("datos guardados en localStorage: ","Rol: "+ localStorage.getItem('rol'),
                    "usuario_id: "+localStorage.getItem('usuario_id'),
                    "nombreEmpresa: "+localStorage.getItem('nombreEmpresa'),
                    "empresa_id: "+localStorage.getItem('empresa_id'),
                    "nombre: "+localStorage.getItem('nombre'),
                    "avatar: "+localStorage.getItem('avatar'));
                this.router.navigate(['/home']);
            },
            error: (err) => {
                this.isLoading = false;
                this.isError = true;
                this.mensajeError = err.error?.error || "Error de conexión";
            }
        });
    }
}