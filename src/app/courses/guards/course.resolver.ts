import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { CoursesService } from '../services/courses.service';
import { Course } from '../model/course';

@Injectable({
  providedIn: 'root'
})
export class CourseResolver implements Resolve<Course> {

  constructor(private service: CoursesService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Course> {
    if (route.params && route.params['id']) { // Confere se temos parâmetros na url em questão e se esse parâmetro é um id como está especificado no courses-routing.module e se tiver ele vai ir no service utilizar o método loadById() passando o id da route como parâmetro e retornando a resposta que no caso é um course.
      return this.service.loadById(route.params['id'])
    }

    return of({ _id: '', name: '', category: '', lessons: [] }); // Se não o if acima não for executado retorna um course vazio.
  }
}
