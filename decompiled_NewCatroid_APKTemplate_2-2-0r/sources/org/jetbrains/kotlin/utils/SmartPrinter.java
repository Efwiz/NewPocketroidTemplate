package org.jetbrains.kotlin.utils;

import java.util.Arrays;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: SmartPrinter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0006\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u001b\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\u0010J\u001f\u0010\u0011\u001a\u00020\u00102\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0013\"\u00020\u0001¢\u0006\u0002\u0010\u0014J\u001f\u0010\u0015\u001a\u00020\u00102\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0013\"\u00020\u0001¢\u0006\u0002\u0010\u0014J\u0006\u0010\u0016\u001a\u00020\u0010J\b\u0010\u0017\u001a\u00020\u0006H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/utils/SmartPrinter;", "", "appendable", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", OutputKeys.INDENT, "", "(Ljava/lang/Appendable;Ljava/lang/String;)V", "notFirstPrint", "", "printer", "Lorg/jetbrains/kotlin/utils/Printer;", "getCurrentIndentInUnits", "", "getIndentUnit", "popIndent", "", "print", Constants.OBJECTS, "", "([Ljava/lang/Object;)V", "println", "pushIndent", "toString", "Companion", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SmartPrinter {
    private boolean notFirstPrint;
    private final Printer printer;

    public SmartPrinter(Appendable appendable, String indent) {
        Intrinsics.checkNotNullParameter(appendable, "appendable");
        Intrinsics.checkNotNullParameter(indent, "indent");
        this.printer = new Printer(appendable, indent);
    }

    public /* synthetic */ SmartPrinter(Appendable appendable, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(appendable, (i & 2) != 0 ? "    " : str);
    }

    public final void print(Object... objects) {
        Intrinsics.checkNotNullParameter(objects, "objects");
        if (this.notFirstPrint) {
            this.printer.printWithNoIndent(Arrays.copyOf(objects, objects.length));
        } else {
            this.printer.print(Arrays.copyOf(objects, objects.length));
        }
        this.notFirstPrint = true;
    }

    public final void println(Object... objects) {
        Intrinsics.checkNotNullParameter(objects, "objects");
        if (this.notFirstPrint) {
            this.printer.printlnWithNoIndent(Arrays.copyOf(objects, objects.length));
        } else {
            this.printer.println(Arrays.copyOf(objects, objects.length));
        }
        this.notFirstPrint = false;
    }

    public final void pushIndent() {
        this.printer.pushIndent();
    }

    public final void popIndent() {
        this.printer.popIndent();
    }

    public final int getCurrentIndentInUnits() {
        return this.printer.getCurrentIndentLengthInUnits();
    }

    public final int getIndentUnit() {
        return this.printer.getIndentUnitLength();
    }

    public String toString() {
        String string = this.printer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
