import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

import { Course } from '../../model/course';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.scss'],
})
export class CoursesListComponent implements OnInit {
  @Input() courses: Course[] = []; // Permite pegarmos os dados do component de courses por causa do @Input().
  @Output() add = new EventEmitter(false); // Permite passarmos algo para fora do nosso component.
  @Output() edit = new EventEmitter(false); // Permite passarmos algo para fora do nosso component.
  @Output() remove = new EventEmitter(false); // Permite passarmos algo para fora do nosso component.

  readonly displayedColumns = ['name', 'category', 'actions'];

  constructor() {}

  ngOnInit(): void {}

  onAdd() {
    this.add.emit(true); // Ao acionado emite um evento true.
  }

  onEdit(course: Course) {
    this.edit.emit(course); // Ao acionado emite um evento com o course que o acionou.
  }

  onDelete(course: Course) {
    this.remove.emit(course);
  }
}
