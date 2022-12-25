import { CourseFormComponent } from './containers/course-form/course-form.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CoursesComponent } from './containers/courses/courses.component';
import { CourseResolver } from './guards/course.resolver';

const routes: Routes = [
  { path: '', component: CoursesComponent },
  { path: 'new', component: CourseFormComponent, resolve: {course: CourseResolver} },
  { path: 'edit/:id', component: CourseFormComponent, resolve: {course: CourseResolver} } // Para darmos nome para parametros no Angular da URL a gente coloca (:) e o nome que quisermos chamar depois, que no caso é o id, e ai depois conseguimos pegar esse paramêtro na rota e fazer alguma coisa com essa informação como por exemplo ir na nossa API e buscar o curso que está sendo editado.
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoursesRoutingModule { }
