import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BannerService } from '../../services/banner/banner.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { HeaderComponent } from '../../components/header/header.component';
import { EnumUsuario } from '../../models/usuario/EnumUsuario';
import { BannerResponse } from '../../models/banner/BannerResponse';
import { VideojuegoService } from '../../services/videojuego/videojuego.service';
import { VideojuegoResponse } from '../../models/videojuego/VideojuegoResponse';
import { GameCardComponent } from '../../components/game-card/game-card.component';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [CommonModule, FormsModule, HeaderComponent, GameCardComponent],
    templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

    private bannerService = inject(BannerService);
    private videojuegoService = inject(VideojuegoService)
    private sanitizer = inject(DomSanitizer);

    // Estados
    banners = signal<BannerResponse[]>([]);
    isLoading = signal(false);
    isLoadingTop = signal(false);
    topRanking = signal<VideojuegoResponse[]>([]);
    // Permisos
    isAdmin = signal(false);      // ¿Es Admin Sistema? (Para editar)
    canViewBanner = signal(false); // ¿Puede ver el banner? (Admin Sistema o Comun)

    nuevoJuegoId: number | null = null;

    ngOnInit(): void {
        const rol = localStorage.getItem('rol');
        EnumUsuario.admin_sistema
        // 1. Definir permisos
        this.isAdmin.set(rol === EnumUsuario.admin_sistema);

        // REGLA DE NEGOCIO: Solo COMUN y ADMIN_SISTEMA ven el banner
        const puedeVer = rol === EnumUsuario.comun || rol === EnumUsuario.admin_sistema;
        this.canViewBanner.set(puedeVer);

        // 2. Cargar datos SOLO si tiene permiso
        if (puedeVer) {
            this.cargarBanners();
            this.cargarTopJuegos();
        }
    }

    cargarTopJuegos() {
        this.isLoadingTop.set(true);
        this.videojuegoService.obtenerElTopRanking().subscribe({
            next: (data) => {
                this.topRanking.set(data);
                this.isLoadingTop.set(false);
            },
            error: (err) => {
                console.error('Error cargando top ranking', err);
                this.isLoadingTop.set(false);
            }
        });
    }

    cargarBanners() {
        this.isLoading.set(true);
        let peticion$;

        // Si es Admin, trae todo (incluso ocultos). Si es Comun, solo visibles.
        if (this.isAdmin()) {
            peticion$ = this.bannerService.obtenerBannersAdmin();
        } else {
            peticion$ = this.bannerService.obtenerBannersUsuario();
        }

        peticion$.subscribe({
            next: (data) => {
                this.banners.set(data);
                this.isLoading.set(false);
            },
            error: (err) => {
                console.error(err);
                this.isLoading.set(false);
            }
        });
    }

    // ... (El resto de funciones: agregarAlBanner, cambiarEstado, eliminar, getImagenSrc siguen igual) ...
    agregarAlBanner() {
        if (!this.nuevoJuegoId) return;
        this.bannerService.agregarBanner({ videojuego_id: this.nuevoJuegoId }).subscribe({
            next: () => { this.nuevoJuegoId = null; this.cargarBanners(); },
            error: (err) => alert('Error agregando')
        });
    }

    cambiarEstado(banner: BannerResponse) {
        const nuevoEstado = !banner.estado;
        this.bannerService.actualizarEstado(banner.banner_id, nuevoEstado).subscribe({
            next: () => banner.estado = nuevoEstado
        });
    }

    eliminar(id: number) {
        if (!confirm('¿Eliminar?')) return;
        this.bannerService.eliminarBanner(id).subscribe({
            next: () => this.banners.update(l => l.filter(b => b.banner_id !== id))
        });
    }

    getImagenSrc(base64: string): SafeUrl {
        return this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${base64}`);
    }
}