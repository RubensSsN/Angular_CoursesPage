import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Observable, of } from 'rxjs';

import { Course } from '../model/course';
import { ErrorDialogComponent } from './../../shared/components/error-dialog/error-dialog.component';
import { CoursesService } from './../services/courses.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit {
  courses$: Observable<Course[]>;
  displayedColumns = ['name', 'category', 'actions'];

  constructor(
    private coursesService: CoursesService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute //Pega a rota ativa atualmente quando chamado.
    ) {
    this.courses$ = this.coursesService.list()
    .pipe(
      catchError(error => {
        this.onError('Erro ao carregar cursos'); //Chama o método onError() passando o parâmetro 'Erro ao carregar cursos'.
        return of([])
      })
    );
  }

  onError(errorMsg : string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg
    }); //Ao termos um erro ele faz com que se pegue a mensagem do erro pelo seu parametro e abra uma caixa (dialog) e carregue o component ErrorDialogComponent e passa a data o tipo de errorMsg para informar o erro ocorrido.
  }

  ngOnInit(): void {

  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route}) //Faz com que ao ser acionado o método onAdd() leve para a rota atual + /new por causa do relativeTo que faz com que seja relativo a rota atual (ativa) por ter um valor de this.route, gerando assim uma melhor organização do nosso código, pois se mudarmos a rota não afetará o código inteiro e ficará mais fácil de se arrumar.
  }
}
