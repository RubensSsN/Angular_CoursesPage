import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Observable, of } from 'rxjs';

import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';
import { ConfirmationDialogComponent } from '../../../shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit {
  courses$: Observable<Course[]> | null = null;

  constructor(
    private coursesService: CoursesService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute //Pega a rota ativa atualmente quando chamado.
  ) {
    this.refresh();
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg,
    }); //Ao termos um erro ele faz com que se pegue a mensagem do erro pelo seu parametro e abra uma caixa (dialog) e carregue o component ErrorDialogComponent e passa a data o tipo de errorMsg para informar o erro ocorrido.
  }

  refresh() {
    this.courses$ = this.coursesService.list().pipe(
      catchError((error) => {
        this.onError('Erro ao carregar cursos'); //Chama o método onError() passando o parâmetro 'Erro ao carregar cursos'.
        return of([]);
      })
    );
  }

  ngOnInit(): void {}

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route }); //Faz com que ao ser acionado o método onAdd() leve para a rota atual + /new por causa do relativeTo que faz com que seja relativo a rota atual (ativa) por ter um valor de this.route, gerando assim uma melhor organização do nosso código, pois se mudarmos a rota não afetará o código inteiro e ficará mais fácil de se arrumar.
  }
  onEdit(course: Course) {
    this.router.navigate(['edit', course._id], { relativeTo: this.route }); //Quando o método onEdit() é chamado ele pega o course que foi passado redireciona para o end-point de edit e pega o id do course que acionou o método para adicionar também no end-point e ser possivel a edição daquele course.
  }

  onRemove(course: Course) {
    // Abre um dialog de confirmação.
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Tem certeza que deseja excluir este curso?',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      // Após ser fechado (afterClosed()) pega o result do onConfirm() que está no confirm-dialog.component o dialog ele verifica se o result é true (no caso é se o usuário clicar sim para excluir o curso após selecionar um curso para excluir), se for ele executa o código, se não apenas não faz nada.
      if (result) {
        // Está solicitando uma chamada do método delete() do coursesService, porém precisamos de dar subscribe() para que a chamada seja realmente executada.
        this.coursesService.delete(course._id).subscribe(
          () => {
            this.refresh();
            // Caixinha informando que o curso foi criado com sucesso.
            this.snackBar.open('Curso excluído com sucesso', 'X', {
              duration: 2000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
            });
          },
          // Tratando se houver erro (não encontrar registro).
          (error) => this.onError('Erro ao deletar curso')
        );
      }
    });
  }
}
