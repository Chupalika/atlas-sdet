package org.example.pages;

import java.util.List;

import org.example.pages.validators.FieldValidator;
import org.openqa.selenium.By;

public record FormField(String name, String label, By locator, List<FieldValidator> validators) {}
