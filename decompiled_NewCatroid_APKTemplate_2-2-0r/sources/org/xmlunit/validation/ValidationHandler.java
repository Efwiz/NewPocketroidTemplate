package org.xmlunit.validation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/* JADX INFO: loaded from: classes4.dex */
final class ValidationHandler implements ErrorHandler {
    private List<ValidationProblem> problems = new LinkedList();
    private boolean valid = true;
    private SAXParseException lastFatalError = null;

    ValidationHandler() {
    }

    @Override // org.xml.sax.ErrorHandler
    public void error(SAXParseException sAXParseException) {
        if (sAXParseException != this.lastFatalError) {
            this.valid = false;
            this.problems.add(ValidationProblem.fromException(sAXParseException, ValidationProblem.ProblemType.ERROR));
        }
    }

    @Override // org.xml.sax.ErrorHandler
    public void fatalError(SAXParseException sAXParseException) {
        this.valid = false;
        this.lastFatalError = sAXParseException;
        this.problems.add(ValidationProblem.fromException(sAXParseException, ValidationProblem.ProblemType.ERROR));
    }

    @Override // org.xml.sax.ErrorHandler
    public void warning(SAXParseException sAXParseException) {
        this.problems.add(ValidationProblem.fromException(sAXParseException, ValidationProblem.ProblemType.WARNING));
    }

    ValidationResult getResult() {
        return new ValidationResult(this.valid, Collections.unmodifiableList(this.problems));
    }
}
