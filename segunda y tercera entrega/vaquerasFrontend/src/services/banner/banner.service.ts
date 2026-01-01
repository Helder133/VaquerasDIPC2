import { inject, Injectable } from "@angular/core";
import { RestConstants } from "../../shared/restapi/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { BannerResponse } from "../../models/banner/BannerResponse";
import { BannerRequest } from "../../models/banner/BannerRequest";
import { BannerUpdate } from "../../models/banner/BannerUpdate";

@Injectable({
    providedIn: 'root'
})

export class BannerService {

    private restConstants = new RestConstants();
    private httpClient = inject(HttpClient);
    private url = `${this.restConstants.getApiURL()}banner`;

    public obtenerBannersAdmin(): Observable<BannerResponse[]> {
        return this.httpClient.get<BannerResponse[]>(`${this.url}/admin_sistema`);
    }
    
    public obtenerBannersUsuario(): Observable<BannerResponse[]> {
        return this.httpClient.get<BannerResponse[]>(`${this.url}/usuario`);
    }
    
    public agregarBanner(request: BannerRequest): Observable<any> {
        return this.httpClient.post(this.url, request);
    }
    
    public actualizarEstado(id: number, estado: boolean): Observable<any> {
        const update: BannerUpdate = { estado };
        return this.httpClient.put(`${this.url}/${id}`, update);
    }
    
    public eliminarBanner(id: number): Observable<any> {
        return this.httpClient.delete(`${this.url}/${id}`);
    }

}