import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const roleGuard: CanActivateFn = (route, state) => {
    const router = inject(Router);

    // 1. Obtener datos actuales (siempre frescos, nada de variables viejas)
    const userRole = localStorage.getItem('rol');

    // 2. Obtener roles permitidos desde la configuración de la ruta
    // Usamos 'as string[]' para decirle a TypeScript que esperamos un array de textos
    const allowedRoles = route.data['allowedRoles'] as string[];

    // 3. Validar
    if (userRole && allowedRoles.includes(userRole)) {
        return true; // ¡Pase usted!
    }

    // 4. Redirección inteligente
    // Si tiene rol (pero no el correcto) -> Home
    // Si no tiene rol (no está logueado) -> Login
    const redirectUrl = userRole ? '/home' : '/login';

    console.warn(`Acceso denegado. Rol: ${userRole}. Requeridos: ${allowedRoles}`);
    router.navigate([redirectUrl]);
    return false;
};