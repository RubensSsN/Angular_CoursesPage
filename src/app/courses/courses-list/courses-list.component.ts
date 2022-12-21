import { Course } from './../model/course';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.scss']
})
export class CoursesListComponent implements OnInit {

  @Input() courses: Course[] = []; // Permite pegarmos os dados do component de courses por causa do @Input().
  readonly displayedColumns = ['name', 'category', 'actions'];

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route}) //Faz com que ao ser acionado o método onAdd() leve para a rota atual + /new por causa do relativeTo que faz com que seja relativo a rota atual (ativa) por ter um valor de this.route, gerando assim uma melhor organização do nosso código, pois se mudarmos a rota não afetará o código inteiro e ficará mais fácil de se arrumar.
  }

}
