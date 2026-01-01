import { CategoriaVideojuego } from "../categoria/CategoriaVideojuego";

export interface BannerResponse {
    banner_id: number;
    videojuego_id: number;
    estado: boolean; // true = visible, false = oculto
    empresa_id: number;
    nombreVideojuego: string;
    precio: number;
    recursoMinimo: string;
    fecha: string;
    imagen: string; // Jackson lo serializa como Base64 string automáticamente
    descripcion: string;
    clasificacion: string;
    nombreEmpresa: string;
    puntaje: number;
    categorias: CategoriaVideojuego[];

}