import { Injectable } from '@angular/core';
import { FormGroup, UntypedFormArray, UntypedFormControl, UntypedFormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  constructor() { }

  /**
   * @param formGroup - Nome do Formulário.
   * @param fieldName - Nome do campo do Formulário.
   * @returns Chamada do método que irá tratar o erro do formulário enviando o campo para ele.
   * Este método pega o erro do campo (field) e chama o método getErrorMessageFromField passando esse campo com o erro para ser tratado.
   */
  getErrorMessage(formGroup: UntypedFormGroup, fieldName: string) {
    const field = formGroup.get(fieldName) as UntypedFormControl;
    return this.getErrorMessageFromField(field);
  }

  /**
   * Este método pega o do campo recebido e trata este erro de uma maneira personalizada.
   * @param field Nome do campo do formulário.
   * @returns Retorna uma mensagem de erro.
   */
  getErrorMessageFromField(field: UntypedFormControl) {

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

  /**
   * Esse método pega o campo através do FormArray e chama o método getErrorMessageFromField enviando esse campo.
   * @param formGroup Nome do formulario.
   * @param formArrayName Array requerido do formulario.
   * @param fieldName Campo do formulário que é contido no formArrayName.
   * @param index Index da posição do que queremos no formArray.
   * @returns Chama o método getErrorMessageFromField enviando esse field.
   */
  getFormArrayFieldErrorMessage(formGroup: UntypedFormGroup, formArrayName: string, fieldName: string, index: number) {
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    const field = formArray.controls[index].get(fieldName) as UntypedFormControl;
    return this.getErrorMessageFromField(field);
  }

  /**
   * Esse método verifica se o FormArray é requerido.
   * @param formGroup Nome do formulário.
   * @param formArrayName Array requerido do formulario.
   * @returns verifica se o Array requerido do formulario é valido, se contém erro e se foi tocado (selecionado).
   */
  isFormArrayRequired(formGroup: UntypedFormGroup, formArrayName: string) {
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    return !formArray.valid && formArray.hasError('required') && formArray.touched;
  }

}
