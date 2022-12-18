import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

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
    private snackBar: MatSnackBar,
    private location: Location) {
    this.form = formBuilder.group({
      name: [null],
      category: [null]
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.service.save(this.form.value)
    .subscribe(result => this.onSuccess(), error => this.onError()); // Está fazendo com que ao se usar este metodo seja salvo os dados do formulário dando subscribe com seu resultado e também se tiver algum erro exibindo o erro com uma snack bar

  }

  onCancel() {
    this.location.back(); // Está fazendo com que ao ser acionado o método onCancel() ele volte a página para a anterior.
  }

  onSuccess() { //Ao ter um result no onSubmit() ele é acionado gerando uma snack bar de sucesso, e retornando a página para a anterior.
    this.snackBar.open('Curso criado com sucesso!', '', {duration: 4000});
    this.onCancel();
  }

  onError() { //Ao se ter um erro ele é acionado se o método usar ele e mostra uma snackbar na tela do usuário.
    this.snackBar.open('Erro ao salvar curso.', '', { duration: 5000 });
  }
}
