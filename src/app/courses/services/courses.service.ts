import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Course } from '../model/course';
import { delay, first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private readonly API = 'api/courses';

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Course[]>(this.API)
    .pipe(
      first(), //Faz com que pegamos o primeiro pacote de informações e desligamos a conexão.
      //delay(5000), // Faz o delay da lista carregar ser de 5 segundos.
      tap(courses => console.log(courses))
    );
    }
}
