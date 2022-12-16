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

  save(record: Course) {
    return this.httpClient.post<Course>(this.API, record).pipe(first()); //Está salvando o dado via post para a API e esse dado é um course que retorna um observable do tipo Course e faz um pipe firts() para enviar o dado e encerrar a conexão, fazemos isso no Service pois é ele quem cuida da lógica de négocios e transações exteriores.
  }
}
