package org.jetbrains.kotlin.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.transform.OutputKeys;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes6.dex */
public class Printer {
    public static final String LINE_SEPARATOR = System.getProperty(SystemProperties.LINE_SEPARATOR);
    private int blankLineCountIncludingCurrent;
    private String indent;
    private final String indentUnit;
    private int length;
    private final int maxBlankLines;
    private final Appendable out;
    private boolean withholdIndentOnce;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        if (i == 2 || i == 5 || i == 7) {
            objArr[0] = "indentUnit";
        } else if (i != 8) {
            switch (i) {
                case 10:
                    objArr[0] = "parent";
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    objArr[0] = "org/jetbrains/kotlin/utils/Printer";
                    break;
                default:
                    objArr[0] = "out";
                    break;
            }
        } else {
            objArr[0] = OutputKeys.INDENT;
        }
        switch (i) {
            case 11:
                objArr[1] = "println";
                break;
            case 12:
                objArr[1] = "print";
                break;
            case 13:
                objArr[1] = "printWithNoIndent";
                break;
            case 14:
                objArr[1] = "withholdIndentOnce";
                break;
            case 15:
                objArr[1] = "printlnWithNoIndent";
                break;
            case 16:
                objArr[1] = "pushIndent";
                break;
            case 17:
                objArr[1] = "popIndent";
                break;
            case 18:
            case 19:
                objArr[1] = "separated";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/utils/Printer";
                break;
        }
        switch (i) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Printer(Appendable appendable) {
        this(appendable, Integer.MAX_VALUE);
        if (appendable == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Printer(Appendable appendable, String str) {
        this(appendable, Integer.MAX_VALUE, str);
        if (appendable == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Printer(Appendable appendable, int i) {
        this(appendable, i, "    ");
        if (appendable == null) {
            $$$reportNull$$$0(3);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Printer(Appendable appendable, int i, String str) {
        this(appendable, i, str, "");
        if (appendable == null) {
            $$$reportNull$$$0(4);
        }
        if (str == null) {
            $$$reportNull$$$0(5);
        }
    }

    private Printer(Appendable appendable, int i, String str, String str2) {
        if (appendable == null) {
            $$$reportNull$$$0(6);
        }
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (str2 == null) {
            $$$reportNull$$$0(8);
        }
        this.blankLineCountIncludingCurrent = 0;
        this.withholdIndentOnce = false;
        this.length = 0;
        this.out = appendable;
        this.maxBlankLines = i;
        this.indentUnit = str;
        this.indent = str2;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Printer(Appendable appendable, Printer printer) {
        this(appendable, printer.maxBlankLines, printer.indentUnit, printer.indent);
        if (appendable == null) {
            $$$reportNull$$$0(9);
        }
        if (printer == null) {
            $$$reportNull$$$0(10);
        }
    }

    private void append(Object obj) {
        try {
            String string = obj.toString();
            this.out.append(string);
            this.length += string.length();
        } catch (IOException unused) {
        }
    }

    public Printer println(Object... objArr) {
        print(objArr);
        printLineSeparator();
        return this;
    }

    private void printLineSeparator() {
        int i = this.blankLineCountIncludingCurrent;
        if (i <= this.maxBlankLines) {
            this.blankLineCountIncludingCurrent = i + 1;
            append(LINE_SEPARATOR);
        }
    }

    public Printer print(Object... objArr) {
        if (this.withholdIndentOnce) {
            this.withholdIndentOnce = false;
        } else if (objArr.length > 0) {
            printIndent();
        }
        printWithNoIndent(objArr);
        return this;
    }

    public void printIndent() {
        append(this.indent);
    }

    public Printer printWithNoIndent(Object... objArr) {
        for (Object obj : objArr) {
            this.blankLineCountIncludingCurrent = 0;
            append(obj);
        }
        return this;
    }

    public Printer withholdIndentOnce() {
        this.withholdIndentOnce = true;
        return this;
    }

    public Printer printlnWithNoIndent(Object... objArr) {
        printWithNoIndent(objArr);
        printLineSeparator();
        return this;
    }

    public Printer pushIndent() {
        this.indent += this.indentUnit;
        return this;
    }

    public Printer popIndent() {
        if (this.indent.length() < this.indentUnit.length()) {
            throw new IllegalStateException("No indentation to pop");
        }
        this.indent = this.indent.substring(this.indentUnit.length());
        return this;
    }

    public Printer separated(Object obj, Object... objArr) {
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                printlnWithNoIndent(obj);
            }
            printlnWithNoIndent(objArr[i]);
        }
        return this;
    }

    public Printer separated(Object obj, Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            printlnWithNoIndent(it.next());
            if (it.hasNext()) {
                printlnWithNoIndent(obj);
            }
        }
        return this;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public String toString() {
        return this.out.toString();
    }

    public int getCurrentIndentLengthInUnits() {
        return this.indent.length() / this.indentUnit.length();
    }

    public int getIndentUnitLength() {
        return this.indentUnit.length();
    }
}
