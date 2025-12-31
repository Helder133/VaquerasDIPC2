import { Routes } from '@angular/router';
import { LoginPageComponent } from '../pages/login-page/login-page.component';
import { UserFormComponent } from '../pages/usuario-page/user-form-component/user-form.component';

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
        path: '**',
        redirectTo: 'login'
    },
];
