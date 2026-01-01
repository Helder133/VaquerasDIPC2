import { Routes } from '@angular/router';
import { LoginPageComponent } from '../pages/login-page/login-page.component';
import { UserFormComponent } from '../pages/usuario-page/user-form-component/user-form.component';
import { HomeComponent } from '../pages/home/home.component';
import { roleGuard } from '../services/security/role-guard.service';
import { EnumUsuario } from '../models/usuario/EnumUsuario';

export const routes: Routes = [

    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: LoginPageComponent
    },
    {
        path: 'register',
        component: UserFormComponent
    },
    {
        path: 'home',
        component: HomeComponent,
        canActivate: [roleGuard],
        data: { allowedRoles: [EnumUsuario.comun, EnumUsuario.manager, EnumUsuario.admin_empresa, EnumUsuario.admin_sistema] },
    },
    {
        path: '**',
        redirectTo: 'login'
    },
];
