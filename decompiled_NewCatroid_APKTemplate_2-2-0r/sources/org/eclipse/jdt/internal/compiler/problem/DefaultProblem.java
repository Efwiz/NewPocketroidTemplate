package org.eclipse.jdt.internal.compiler.problem;

import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.internal.compiler.util.Messages;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class DefaultProblem extends CategorizedProblem {
    public static final Object[] EMPTY_VALUES = new Object[0];
    private static final String MARKER_TYPE_PROBLEM = "org.eclipse.jdt.core.problem";
    private static final String MARKER_TYPE_TASK = "org.eclipse.jdt.core.task";
    private String[] arguments;
    public int column;
    private int endPosition;
    private char[] fileName;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private int f631id;
    private int line;
    private String message;
    private int severity;
    private int startPosition;

    public DefaultProblem(char[] cArr, String str, int i, String[] strArr, int i2, int i3, int i4, int i5, int i6) {
        this.fileName = cArr;
        this.message = str;
        this.f631id = i;
        this.arguments = strArr;
        this.severity = i2;
        this.startPosition = i3;
        this.endPosition = i4;
        this.line = i5;
        this.column = i6;
    }

    public String errorReportSource(char[] cArr) {
        int i;
        char c;
        char c2;
        int i2 = this.startPosition;
        int i3 = this.endPosition;
        if (i2 > i3 || ((i2 < 0 && i3 < 0) || cArr.length == 0)) {
            return Messages.problem_noSourceInformation;
        }
        StringBuffer stringBuffer = new StringBuffer(" ");
        stringBuffer.append(Messages.bind(Messages.problem_atLine, String.valueOf(this.line)));
        stringBuffer.append(Util.LINE_SEPARATOR);
        stringBuffer.append('\t');
        int length = cArr.length;
        int i4 = this.startPosition;
        if (i4 >= length) {
            i4 = length - 1;
        }
        while (i4 > 0 && (c2 = cArr[i4 - 1]) != '\n' && c2 != '\r') {
            i4--;
        }
        int i5 = this.endPosition;
        if (i5 >= length) {
            i5 = length - 1;
        }
        while (true) {
            int i6 = i5 + 1;
            if (i6 >= length || (c = cArr[i6]) == '\r' || c == '\n') {
                break;
            }
            i5 = i6;
        }
        while (true) {
            char c3 = cArr[i4];
            if (c3 != ' ' && c3 != '\t') {
                break;
            }
            i4++;
        }
        stringBuffer.append(cArr, i4, (i5 - i4) + 1);
        stringBuffer.append(Util.LINE_SEPARATOR).append("\t");
        while (true) {
            i = this.startPosition;
            if (i4 >= i) {
                break;
            }
            stringBuffer.append(cArr[i4] == '\t' ? '\t' : ' ');
            i4++;
        }
        while (true) {
            int i7 = this.endPosition;
            if (i7 >= length) {
                i7 = length - 1;
            }
            if (i <= i7) {
                stringBuffer.append(Util.C_EXCEPTION_START);
                i++;
            } else {
                return stringBuffer.toString();
            }
        }
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public String[] getArguments() {
        return this.arguments;
    }

    @Override // org.eclipse.jdt.core.compiler.CategorizedProblem
    public int getCategoryID() {
        return ProblemReporter.getProblemCategory(this.severity, this.f631id);
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public int getID() {
        return this.f631id;
    }

    public String getInternalCategoryMessage() {
        switch (getCategoryID()) {
            case 0:
                return "unspecified";
            case 10:
                return "buildpath";
            case 20:
                return "syntax";
            case 30:
                return PsiKeyword.IMPORT;
            case 40:
                return ModuleXmlParser.TYPE;
            case 50:
                return "member";
            case 60:
                return "internal";
            case 70:
                return "javadoc";
            case 80:
                return "code style";
            case 90:
                return "potential programming problem";
            case 100:
                return "name shadowing conflict";
            case 110:
                return "deprecation";
            case 120:
                return "unnecessary code";
            case 130:
                return "unchecked/raw";
            case 140:
                return "nls";
            case 150:
                return SchemaSymbols.ATTVAL_RESTRICTION;
            default:
                return null;
        }
    }

    @Override // org.eclipse.jdt.core.compiler.CategorizedProblem
    public String getMarkerType() {
        if (this.f631id == 536871362) {
            return MARKER_TYPE_TASK;
        }
        return MARKER_TYPE_PROBLEM;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public String getMessage() {
        return this.message;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public char[] getOriginatingFileName() {
        return this.fileName;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public int getSourceEnd() {
        return this.endPosition;
    }

    public int getSourceColumnNumber() {
        return this.column;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public int getSourceLineNumber() {
        return this.line;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public int getSourceStart() {
        return this.startPosition;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public boolean isError() {
        return (this.severity & 1) != 0;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public boolean isWarning() {
        int i = this.severity;
        return (i & 1) == 0 && (i & 1024) == 0;
    }

    @Override // org.eclipse.jdt.core.compiler.CategorizedProblem, org.eclipse.jdt.core.compiler.IProblem
    public boolean isInfo() {
        return (this.severity & 1024) != 0;
    }

    public void setOriginatingFileName(char[] cArr) {
        this.fileName = cArr;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public void setSourceEnd(int i) {
        this.endPosition = i;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public void setSourceLineNumber(int i) {
        this.line = i;
    }

    @Override // org.eclipse.jdt.core.compiler.IProblem
    public void setSourceStart(int i) {
        this.startPosition = i;
    }

    public String toString() {
        String str = "Pb(" + (this.f631id & 16777215) + ") ";
        if (this.message != null) {
            return String.valueOf(str) + this.message;
        }
        if (this.arguments == null) {
            return str;
        }
        for (int i = 0; i < this.arguments.length; i++) {
            str = String.valueOf(str) + " " + this.arguments[i];
        }
        return str;
    }
}
