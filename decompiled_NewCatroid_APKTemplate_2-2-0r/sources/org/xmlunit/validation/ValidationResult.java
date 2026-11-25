package org.xmlunit.validation;

/* JADX INFO: loaded from: classes4.dex */
public class ValidationResult {
    private final Iterable<ValidationProblem> problems;
    private final boolean valid;

    public ValidationResult(boolean z, Iterable<ValidationProblem> iterable) {
        this.valid = z;
        this.problems = iterable;
    }

    public boolean isValid() {
        return this.valid;
    }

    public Iterable<ValidationProblem> getProblems() {
        return this.problems;
    }
}
