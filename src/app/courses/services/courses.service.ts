import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Course } from '../model/course';
import { delay, first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CoursesService {
  private readonly API = 'api/courses';

  constructor(private httpClient: HttpClient) {}

  list() {
    return this.httpClient.get<Course[]>(this.API).pipe(
      first() //Faz com que pegamos o primeiro pacote de informações e desligamos a conexão.
      //delay(5000), // Faz o delay da lista carregar ser de 5 segundos.
    );
  }

  loadById(id: string) {
    return this.httpClient.get<Course>(`${this.API}/${id}`); //Carrega o id que está chegando para ele e retorna o course com aquele id.
  }

  save(record: Partial<Course>) {
    // Verifica se o course passado já tem um id (já esta cadastrado) ou se é um curso novo
    if (record._id) {
      // Se for um curso que tenha id ele vai acionar o método de update(record) para dar o PUT
      return this.update(record);
    }
    return this.create(record); // Se for um curso novo que não tenha id ele vai acionar o método de create(record) para dar o POST.
  }

  private create(record: Partial<Course>) {
    return this.httpClient.post<Course>(this.API, record).pipe(first()); //Está salvando o dado via POST para a API e esse dado é um course que retorna um observable do tipo Course e faz um pipe firts() para enviar o dado e encerrar a conexão, fazemos isso no Service pois é ele quem cuida da lógica de négocios e transações exteriores.
  }

  //Metodo que ao ser acionado da um PUT (atualiza) o curso que foi passado para ele no parâmetro no caso chamamos de record, e fecha a conexão depois de enviar o primeiro dado e encerra a conexão com o back-end.
  private update(record: Partial<Course>) {
    return this.httpClient
      .put<Course>(`${this.API}/${record._id}`, record)
      .pipe(first());
  }

  //Metodo que ao ser acionado da um DELETE (deleta) o curso que foi passado para ele no parâmetro no caso chamamos de record, e fecha a conexão depois de enviar o primeiro dado e encerra a conexão com o back-end.
  delete(id: string) {
    return this.httpClient.delete(`${this.API}/${id}`).pipe(first());
  }
}
