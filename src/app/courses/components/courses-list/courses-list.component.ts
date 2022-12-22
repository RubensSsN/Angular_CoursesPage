import { Course } from '../../model/course';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.scss']
})
export class CoursesListComponent implements OnInit {

  @Input() courses: Course[] = []; // Permite pegarmos os dados do component de courses por causa do @Input().
  @Output() add = new EventEmitter(false); // Permite passarmos algo para fora do nosso component.

  readonly displayedColumns = ['name', 'category', 'actions'];

  constructor() { }

  ngOnInit(): void {
  }

  onAdd() {
    this.add.emit(true); // Ao acionado emite um evento true.
  }

}
