import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Action } from 'rxjs/internal/scheduler/Action';

import { CoursesService } from './../services/courses.service';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent implements OnInit {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar) {
    this.form = formBuilder.group({
      name: [null],
      category: [null]
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.service.save(this.form.value)
    .subscribe(result => console.log(result), error => this.onError()); // Está fazendo com que ao se usar este metodo seja salvo os dados do formulário dando subscribe com seu resultado e também se tiver algum erro exibindo o erro com uma snack bar
  }

  onCancel() {

  }

  onError() {
    this.snackBar.open('Erro ao salvar curso.', '', { duration: 5000 }); //Ao se ter um erro ele é acionado se o método usar ele e mostra uma snackbar na tela do usuário.
  }
}
