import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { MatLegacySnackBar as MatSnackBar } from '@angular/material/legacy-snack-bar';
import { ActivatedRoute } from '@angular/router';

import { CoursesService } from '../../services/courses.service';
import { Course } from '../../model/course';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss'],
})
export class CourseFormComponent implements OnInit {
  form = this.formBuilder.group({
    _id: [''],
    name: ['', [Validators.required, //Com o validadtors o Angular Material faz o css por causa da classe Validators para o erro se não tiver valor.
        Validators.minLength(3), //Com o validadtors o Angular Material faz o css por causa da classe Validators para o erro se o tamanho for menor que 3 caracteres.
        Validators.maxLength(100)], //Com o validadtors o Angular Material faz o css por causa da classe Validators para o erro se o tamanho for maior que 100 caracteres.
    ],
    category: ['', [Validators.required]]  //Com o validadtors o Angular Material faz o css por causa da classe Validators para o erro se não tiver valor.
  });

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course']; // Obtem a rota e pega um snapshot (imagem da rota em um determinado período de tempo) e acessamos o objeto data que dentro dele temos qualquer coisa que colocarmos dentro do resolver no caso o course que queremos então o especificamos.
    this.form.setValue({
      // Iniciado o formullário de edição ele já carrega o course em questão por causa do id que está no end-point sendo passado como parâmetro para o resolver.
      _id: course._id,
      name: course.name,
      category: course.category,
    });
  }

  onSubmit() {
    this.service.save(this.form.value).subscribe(
      (result) => this.onSuccess(),
      (error) => this.onError()
    ); // Está fazendo com que ao se usar este metodo seja salvo os dados do formulário dando subscribe com seu resultado e também se tiver algum erro exibindo o erro com uma snack bar
  }

  onCancel() {
    this.location.back(); // Está fazendo com que ao ser acionado o método onCancel() ele volte a página para a anterior.
  }

  onSuccess() {
    //Ao ter um result no onSubmit() ele é acionado gerando uma snack bar de sucesso, e retornando a página para a anterior.
    this.snackBar.open('Curso criado com sucesso!', '', { duration: 4000 });
    this.onCancel();
  }

  onError() {
    //Ao se ter um erro ele é acionado se o método usar ele e mostra uma snackbar na tela do usuário.
    this.snackBar.open('Erro ao salvar curso.', '', { duration: 5000 });
  }

  getErrorMessage(fieldName: string) {
    const field = this.form.get(fieldName);

    if (field?.hasError('required')) {  // Verifica se tem algum erro no formulário do tipo required.
      return 'Campo obrigatório';
    }
    if (field?.hasError('minlength')) { // Verifica se tem algum erro no formulário do tipo minlength.
      const requiredLength: number = field.errors ? field.errors['minlength']['requiredLength'] : 5; // Se tiver um erro do tipo minlength ele pega a quantidade de caracteres mínimas necessárias e retorna o número 5 para o requiredLength.
      return `Tamanho mínimo precisa ser de ${requiredLength} caracteres.`;
    }
    if (field?.hasError('maxlength')) {
      const requiredLength: number = field.errors ? field.errors['maxlength']['requiredLength'] : 100; // Se tiver um erro do tipo maxlength ele pega a quantidade de caracteres máximas necessárias e retorna o número 100 para o requiredLength.
      return `Tamanho máximo excedido de ${requiredLength} caracteres.`;
    }

    return 'Erro ao salvar curso';
  }
}
