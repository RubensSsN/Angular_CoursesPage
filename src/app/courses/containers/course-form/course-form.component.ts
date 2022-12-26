import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NonNullableFormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

import { CoursesService } from '../../services/courses.service';
import { Course } from '../../model/course';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent implements OnInit {

  form = this.formBuilder.group({
      _id: [''],
      name: [''],
      category: ['']
    });;

  constructor(private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course']; // Obtem a rota e pega um snapshot (imagem da rota em um determinado período de tempo) e acessamos o objeto data que dentro dele temos qualquer coisa que colocarmos dentro do resolver no caso o course que queremos então o especificamos.
    this.form.setValue({ // Iniciado o formullário de edição ele já carrega o course em questão por causa do id que está no end-point sendo passado como parâmetro para o resolver.
      _id: course._id,
      name: course.name,
      category: course.category
    })
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
