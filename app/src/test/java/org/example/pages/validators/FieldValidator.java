package org.example.pages.validators;

import org.example.pages.forms.GenericFormWrapper;

public interface FieldValidator {
  void validate(String fieldName, String fieldLabel, GenericFormWrapper<?> form);
}
