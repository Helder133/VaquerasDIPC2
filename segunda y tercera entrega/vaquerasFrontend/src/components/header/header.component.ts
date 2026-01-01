import { Component, OnInit, inject, signal } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MenuItem } from '../../models/ui/menu-item';
import { EnumUsuario } from '../../models/usuario/EnumUsuario';

@Component({
    selector: 'app-header',
    standalone: true,
    imports: [CommonModule, RouterLink, RouterLinkActive],
    templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {

    private router = inject(Router);
    menuItems = signal<MenuItem[]>([]);
    userName = signal<string>('Usuario');
    userRole = signal<string>('');
    avatarUrl = signal<string | null>(null);

    ngOnInit(): void {
        this.cargarDatosUsuario();
        this.generarMenu();
    }

    private cargarDatosUsuario() {
        this.userName.set(localStorage.getItem('nombre') || 'Usuario');
        this.userRole.set(localStorage.getItem('rol') || '');
    }

    private generarMenu() {
        const rol = this.userRole();
        const items: MenuItem[] = [];

        items.push({ label: 'Inicio', path: '/home', icon: 'bi-house-door-fill' });
        EnumUsuario.admin_empresa
        if (rol === EnumUsuario.admin_sistema) {
            items.push(
                { label: 'Gestión Usuarios', path: '/usuarios/gestion', icon: 'bi-people-fill' },
                { label: 'Reportes', path: '/reportes', icon: 'bi-bar-chart-fill' }
            );
        } else if (rol === EnumUsuario.manager) {
            items.push(
                { label: 'Mi Equipo', path: '/usuarios/gestion', icon: 'bi-people' }
            );
        } else if (rol === EnumUsuario.admin_empresa) {
            items.push(
                { label: 'Mis Juegos', path: '/juegos/gestion', icon: 'bi-controller' },
                { label: 'Publicar', path: '/juegos/nuevo', icon: 'bi-plus-circle-fill' }
            );
        } else if (rol === EnumUsuario.comun) {
            items.push(
                { label: 'Tienda', path: '/store', icon: 'bi-shop' },
                { label: 'Biblioteca', path: '/biblioteca', icon: 'bi-collection-play-fill' },
                { label: 'Cartera', path: '/cartera', icon: 'bi-wallet2' }
            );
        }
        this.menuItems.set(items);
    }

    logout() {
        localStorage.clear();
        this.router.navigate(['/login']);
    }
}