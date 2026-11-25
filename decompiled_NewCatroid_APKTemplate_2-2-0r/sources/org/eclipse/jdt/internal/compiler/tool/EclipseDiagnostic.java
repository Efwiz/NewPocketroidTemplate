package org.eclipse.jdt.internal.compiler.tool;

import java.io.File;
import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblem;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;

/* JADX INFO: loaded from: classes14.dex */
public class EclipseDiagnostic implements Diagnostic<EclipseFileObject> {
    private final int columnNumber;
    private final int endPosition;
    private Diagnostic.Kind kind;
    private final int lineNumber;
    private final char[] originatingFileName;
    private final String[] problemArguments;
    private final DefaultProblemFactory problemFactory;
    private final int problemId;
    private final int startPosition;

    private EclipseDiagnostic(Diagnostic.Kind kind, int i, String[] strArr, char[] cArr, DefaultProblemFactory defaultProblemFactory, int i2, int i3, int i4, int i5) {
        this.kind = kind;
        this.problemId = i;
        this.problemArguments = strArr;
        this.originatingFileName = cArr;
        this.problemFactory = defaultProblemFactory;
        this.lineNumber = i2;
        this.columnNumber = i3;
        this.startPosition = i4;
        this.endPosition = i5;
    }

    private EclipseDiagnostic(Diagnostic.Kind kind, int i, String[] strArr, char[] cArr, DefaultProblemFactory defaultProblemFactory) {
        this(kind, i, strArr, cArr, defaultProblemFactory, -1, -1, -1, -1);
    }

    public static EclipseDiagnostic newInstance(CategorizedProblem categorizedProblem, DefaultProblemFactory defaultProblemFactory) {
        return categorizedProblem instanceof DefaultProblem ? newInstanceFromDefaultProblem((DefaultProblem) categorizedProblem, defaultProblemFactory) : new EclipseDiagnostic(getKind(categorizedProblem), categorizedProblem.getID(), categorizedProblem.getArguments(), categorizedProblem.getOriginatingFileName(), defaultProblemFactory);
    }

    private static EclipseDiagnostic newInstanceFromDefaultProblem(DefaultProblem defaultProblem, DefaultProblemFactory defaultProblemFactory) {
        return new EclipseDiagnostic(getKind(defaultProblem), defaultProblem.getID(), defaultProblem.getArguments(), defaultProblem.getOriginatingFileName(), defaultProblemFactory, defaultProblem.getSourceLineNumber(), defaultProblem.getSourceColumnNumber(), defaultProblem.getSourceStart(), defaultProblem.getSourceEnd());
    }

    private static Diagnostic.Kind getKind(CategorizedProblem categorizedProblem) {
        Diagnostic.Kind kind = Diagnostic.Kind.OTHER;
        if (categorizedProblem.isError()) {
            return Diagnostic.Kind.ERROR;
        }
        if (categorizedProblem.isWarning()) {
            return Diagnostic.Kind.WARNING;
        }
        return ((categorizedProblem instanceof DefaultProblem) && ((DefaultProblem) categorizedProblem).isInfo()) ? Diagnostic.Kind.NOTE : kind;
    }

    public Diagnostic.Kind getKind() {
        return this.kind;
    }

    public EclipseFileObject getSource() {
        File file = new File(new String(this.originatingFileName));
        if (file.exists()) {
            return new EclipseFileObject(null, file.toURI(), JavaFileObject.Kind.SOURCE, null);
        }
        return null;
    }

    public long getPosition() {
        return this.startPosition;
    }

    public long getStartPosition() {
        return this.startPosition;
    }

    public long getEndPosition() {
        return this.endPosition;
    }

    public long getLineNumber() {
        return this.lineNumber;
    }

    public long getColumnNumber() {
        return this.columnNumber;
    }

    public String getCode() {
        return Integer.toString(this.problemId);
    }

    public String getMessage(Locale locale) {
        if (locale != null) {
            this.problemFactory.setLocale(locale);
        }
        return this.problemFactory.getLocalizedMessage(this.problemId, this.problemArguments);
    }
}
