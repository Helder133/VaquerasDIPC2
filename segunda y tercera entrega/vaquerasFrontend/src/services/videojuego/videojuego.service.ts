import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { RestConstants } from "../../shared/restapi/rest-constants";
import { VideojuegoResponse } from "../../models/videojuego/VideojuegoResponse";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})

export class VideojuegoService {

    private httpClient = inject(HttpClient);
    private restConstants = new RestConstants();
    private apiUrl = `${this.restConstants.getApiURL()}videojuego`; 

    obtenerElTopRanking(): Observable<VideojuegoResponse[]> {
        return this.httpClient.get<VideojuegoResponse[]>(`${this.apiUrl}/topRanking`);
    }

}