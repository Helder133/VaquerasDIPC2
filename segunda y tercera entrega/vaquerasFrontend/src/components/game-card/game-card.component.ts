import { Component, Input, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { RouterLink } from '@angular/router';
import { VideojuegoResponse } from '../../models/videojuego/VideojuegoResponse';

@Component({
    selector: 'app-game-card',
    standalone: true,
    imports: [CommonModule, RouterLink],
    templateUrl: './game-card.component.html',
    styleUrls: ['./game-card.component.css']
})
export class GameCardComponent {

    @Input({ required: true }) juego!: VideojuegoResponse;

    private sanitizer = inject(DomSanitizer);

    getImagenSrc(): SafeUrl {
        if (!this.juego.imagen) {
            return 'assets/placeholder.png'; // Imagen por defecto si falla
        }
        return this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${this.juego.imagen}`);
    }

    formatPuntaje(val: number): string {
        return val ? val.toFixed(1) : '0.0';
    }
}