package org.jetbrains.kotlin.build.report.statistics.file;

import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.util.Arrays;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Printer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B%\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0002J\u0006\u0010\f\u001a\u00020\nJ\u001f\u0010\r\u001a\u00020\n2\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000f\"\u00020\u0006¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\nH\u0002J\b\u0010\u0012\u001a\u00020\nH\u0002J!\u0010\u0013\u001a\u00020\n2\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000f\"\u00020\u0006H\u0002¢\u0006\u0002\u0010\u0010J\u001f\u0010\u0014\u001a\u00020\n2\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000f\"\u00020\u0006¢\u0006\u0002\u0010\u0010J\u0006\u0010\u0015\u001a\u00020\nJ1\u0010\u0016\u001a\u0002H\u0017\"\u0004\b\u0000\u0010\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00062\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00170\u001aH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001bR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/build/report/statistics/file/Printer;", "", "out", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "indentUnit", "", OutputKeys.INDENT, "(Ljava/lang/Appendable;Ljava/lang/String;Ljava/lang/String;)V", "append", "", "s", "popIndent", "print", "strings", "", "([Ljava/lang/String;)V", "printIndent", "printLineSeparator", "printWithNoIndent", "println", "pushIndent", "withIndent", ExifInterface.GPS_DIRECTION_TRUE, "headLine", "fn", "Lkotlin/Function0;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Printer {
    private String indent;
    private final String indentUnit;
    private final Appendable out;

    public Printer(Appendable out, String indentUnit, String indent) {
        Intrinsics.checkNotNullParameter(out, "out");
        Intrinsics.checkNotNullParameter(indentUnit, "indentUnit");
        Intrinsics.checkNotNullParameter(indent, "indent");
        this.out = out;
        this.indentUnit = indentUnit;
        this.indent = indent;
    }

    public /* synthetic */ Printer(Appendable appendable, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(appendable, (i & 2) != 0 ? "  " : str, (i & 4) != 0 ? "" : str2);
    }

    private final void append(String s) {
        try {
            this.out.append(s);
        } catch (IOException unused) {
        }
    }

    public final void println(String... strings) {
        Intrinsics.checkNotNullParameter(strings, "strings");
        print((String[]) Arrays.copyOf(strings, strings.length));
        printLineSeparator();
    }

    private final void printLineSeparator() {
        String str = PrinterKt.LINE_SEPARATOR;
        Intrinsics.checkNotNullExpressionValue(str, "access$getLINE_SEPARATOR$p(...)");
        append(str);
    }

    public final void print(String... strings) {
        Intrinsics.checkNotNullParameter(strings, "strings");
        if (!(strings.length == 0)) {
            printIndent();
        }
        printWithNoIndent((String[]) Arrays.copyOf(strings, strings.length));
    }

    private final void printIndent() {
        append(this.indent);
    }

    private final void printWithNoIndent(String... strings) {
        for (String str : strings) {
            append(str);
        }
    }

    public final void pushIndent() {
        this.indent += this.indentUnit;
    }

    public final void popIndent() {
        if (this.indent.length() < this.indentUnit.length()) {
            throw new IllegalStateException("No indentation to pop".toString());
        }
        String strSubstring = this.indent.substring(this.indentUnit.length());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        this.indent = strSubstring;
    }

    public static /* synthetic */ Object withIndent$default(Printer printer, String str, Function0 fn, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        Intrinsics.checkNotNullParameter(fn, "fn");
        if (str != null) {
            printer.println(str);
        }
        printer.pushIndent();
        try {
            return fn.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            printer.popIndent();
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T withIndent(String headLine, Function0<? extends T> fn) {
        Intrinsics.checkNotNullParameter(fn, "fn");
        if (headLine != null) {
            println(headLine);
        }
        pushIndent();
        try {
            return fn.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            popIndent();
            InlineMarker.finallyEnd(1);
        }
    }
}
