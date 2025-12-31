import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class RoleGuardService implements CanActivate {
    private url = localStorage.getItem('rol') ? '/home' : '/login';
    constructor(private router: Router) {

    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
        if (!this.userRoleInAllowedRoles(route.data['allowedRoles'])) {
            console.log('RoleGuardService initialized with URL:', this.url);
            this.router.navigate([this.url]);
            return false;
        }

        return true; 
    }

    userRoleInAllowedRoles(allowedRoles: string[]): boolean {
        let rol = localStorage.getItem('rol');
        return rol != null && allowedRoles.includes(rol);
    }
}