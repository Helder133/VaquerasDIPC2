import { CategoriaVideojuego } from "../categoria/CategoriaVideojuego";
import { Multimedia } from "../multimedia/Multimedia";

export interface VideojuegoResponse {
    videojuego_id: number;
    empresa_id: number;
    nombre: string;
    precio: number;
    recurso_minimo: string;
    clasificacion: string;
    estado: boolean;
    fecha: string;
    imagen: string;
    descripcion: string;
    nombre_empresa: string;
    puntaje: number;
    categorias: CategoriaVideojuego[]; 
    multimedias: Multimedia[];
}