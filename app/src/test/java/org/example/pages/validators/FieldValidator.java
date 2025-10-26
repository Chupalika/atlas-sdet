package org.example.pages.validators;

import java.util.List;

import org.example.pages.forms.GenericFormWrapper;

public interface FieldValidator {
  List<String> validateErrorMessages(String fieldName, GenericFormWrapper<?> form);
  void fillWithValidData(String fieldName, GenericFormWrapper<?> form);
}
