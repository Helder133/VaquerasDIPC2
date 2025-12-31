import { EnumUsuario } from "./EnumUsuario";

export interface UsuarioResponse {
    usuario_id: number;
    nombre: string;
    email: string;
    fecha_nacimiento: string;
    rol: EnumUsuario;
    telefono: string;
    avatar: string;
    pais: string;
    empresa_id: number;
    nombreEmpresa: string;
}
