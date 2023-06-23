import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, UntypedFormArray, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

import { CoursesService } from '../../services/courses.service';
import { Course } from '../../model/course';
import { Lesson } from '../../model/lesson';

/**
 * @author Rubens Samuel > @GitHub RubensSsN
 */
@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss'],
})
export class CourseFormComponent implements OnInit {

  form!: FormGroup; // O sinal de ! está fazendo com que o Angular permita nós inicializarmos a variável form em outro lugar.

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course']; // Obtem a rota e pega um snapshot (imagem da rota em um determinado período de tempo) e acessamos o objeto data que dentro dele temos qualquer coisa que colocarmos dentro do resolver no caso o course que queremos então o especificamos.
    // Iniciado o formullário de edição ele já carrega o course em questão por causa do id que está no end-point sendo passado como parâmetro para o resolver.
    this.form = this.formBuilder.group({
      _id: [course._id],
      name: [course.name, [Validators.required, //Com o validadtors o Angular Material faz o css por causa da classe Validators para o erro se não tiver valor.
      Validators.minLength(3), //Com o validadtors o Angular Material faz o css por causa da classe Validators para o erro se o tamanho for menor que 3 caracteres.
      Validators.maxLength(100)], //Com o validadtors o Angular Material faz o css por causa da classe Validators para o erro se o tamanho for maior que 100 caracteres.
      ],
      category: [course.category, [Validators.required]],  //Com o validadtors o Angular Material faz o css por causa da classe Validators para o erro se não tiver valor.
      lessons: this.formBuilder.array(
        this.obterLesson(course), Validators.required
      )
    });
  }

  /**
   * Este método obtem todas as Lessons de cada curso recebido, ele é chamado na inicialização.
   * @param course recebe um course e obtem as Lessons dele.
   * @returns FormGroup com os campos de Lesson.
   */
  private obterLesson(course: Course) {
    const lessons = [];
    if (course?.lessons) {
      course.lessons.forEach(lesson => {
        lessons.push(this.criarLesson(lesson));
      });
    } else {
      lessons.push(this.criarLesson());
    }
    return lessons;
  }


  // Se não for passado nenhum valor do tipo Lesson vai ser setado valores vazios, se for passado valores do tipo Lesson vai ser setado os valores passados.
  private criarLesson(lesson: Lesson = { id: '', name: '', url: '' }) {
    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      url: [lesson.url, [Validators.required, Validators.minLength(10), Validators.maxLength(11)]]
    });
  }

  /**
   * Esse método faz com que seja possível pegarmos os FormArray de Lessons através do Form Control.
  */
  getLessonsFormArray() {
    return (<UntypedFormArray>this.form.get('lessons'))?.controls
  }

  adicionarAulas(): void {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.push(this.criarLesson());
  }

  /**
   * Método que remove a aula a partir do index da lesson (Aula) em questão.
   * @param index É o index da lesson que temos naquele formulário, pegamos ele na variável lesson do html.
   */
  removerAula(index: number): void {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.removeAt(index);
  }

  // Está fazendo com que ao se usar este método seja salvo os dados do formulário dando subscribe com seu resultado e também se tiver algum erro exibindo o erro com uma snack bar
  onSubmit() {
    if (this.form.valid) {
      this.service.save(this.form.value).subscribe(
        (result) => this.onSuccess(),
        (error) => this.onError()
      );
    } else {
      alert('form invalido');
    }
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

  isFormArrayRequired() {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    return !lessons.valid && lessons.hasError('required') && lessons.touched;
  }

}
