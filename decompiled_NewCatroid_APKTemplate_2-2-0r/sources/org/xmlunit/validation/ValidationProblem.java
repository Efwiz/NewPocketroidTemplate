package org.xmlunit.validation;

import org.xml.sax.SAXParseException;

/* JADX INFO: loaded from: classes4.dex */
public class ValidationProblem {
    public static final int UNKNOWN = -1;
    private final int column;
    private final int line;
    private final String message;
    private final ProblemType type;

    public enum ProblemType {
        ERROR,
        WARNING
    }

    public ValidationProblem(String str, int i, int i2, ProblemType problemType) {
        this.message = str;
        this.line = i;
        this.column = i2;
        this.type = problemType;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public ProblemType getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ValidationProblem { line=");
        sb.append(this.line);
        sb.append(", column=").append(this.column);
        sb.append(", type=").append(this.type);
        sb.append(", message='").append(this.message).append("' }");
        return sb.toString();
    }

    static ValidationProblem fromException(SAXParseException sAXParseException, ProblemType problemType) {
        return new ValidationProblem(sAXParseException.getMessage(), sAXParseException.getLineNumber() > 0 ? sAXParseException.getLineNumber() : -1, sAXParseException.getColumnNumber() > 0 ? sAXParseException.getColumnNumber() : -1, problemType);
    }
}
