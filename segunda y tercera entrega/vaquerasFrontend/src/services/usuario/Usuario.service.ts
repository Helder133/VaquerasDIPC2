import { inject, Injectable } from "@angular/core";
import { RestConstants } from "../../shared/restapi/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { UsuarioResponse } from "../../models/usuario/UsuarioResponse";

@Injectable({
    providedIn: 'root'
})

export class UsuarioService {

    private restConstants = new RestConstants();
    private httpClient = inject(HttpClient);
    private url = `${this.restConstants.getApiURL()}usuario/`;
    
    public crearUsuario(formData: FormData): Observable<any> {
        return this.httpClient.post<any>(this.url, formData);
    }

    public editarUsuario(usuarioId: number, formData: FormData): Observable<any> {
        const editUrl = `${this.url}${usuarioId}`;
        return this.httpClient.put<any>(editUrl, formData);
    }

    public obtenerUsuario(usuarioId: number): Observable<UsuarioResponse> {
        const getUrl = `${this.url}${usuarioId}`;
        return this.httpClient.get<UsuarioResponse>(getUrl);
    }

}