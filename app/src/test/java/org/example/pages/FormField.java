package org.example.pages;

import java.util.List;

import org.example.pages.validators.FieldValidator;

public record FormField(String name, String label, String xpath, String errorXpath, List<FieldValidator> validators) {}
