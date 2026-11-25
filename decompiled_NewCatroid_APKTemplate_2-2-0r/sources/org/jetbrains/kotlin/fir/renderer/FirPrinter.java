package org.jetbrains.kotlin.fir.renderer;

import com.reandroid.archive.PathTree;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: FirPrinter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004¢\u0006\u0002\u0010\u0005J\u0006\u0010\n\u001a\u00020\u000bJ\r\u0010\f\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\rJ\u001f\u0010\u000e\u001a\u00020\u000b2\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0010\"\u00020\u0001¢\u0006\u0002\u0010\u0011J\u001f\u0010\u0012\u001a\u00020\u000b2\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0010\"\u00020\u0001¢\u0006\u0002\u0010\u0011J\r\u0010\u0013\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\u0014J(\u0010\u0015\u001a\u00020\u000b2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001aJ'\u0010\u001b\u001a\u00020\u000b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\n\u0010\u001f\u001a\u00060 R\u00020!H\u0000¢\u0006\u0002\b\"J'\u0010#\u001a\u00020\u000b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\n\u0010\u001f\u001a\u00060 R\u00020!H\u0000¢\u0006\u0002\b$J\b\u0010%\u001a\u00020\u0017H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "(Ljava/lang/StringBuilder;)V", "lineBeginning", "", "printer", "Lorg/jetbrains/kotlin/utils/Printer;", "newLine", "", "popIndent", "popIndent$tree", "print", Constants.OBJECTS, "", "([Ljava/lang/Object;)V", "println", "pushIndent", "pushIndent$tree", "renderInBraces", "leftBrace", "", "rightBrace", "f", "Lkotlin/Function0;", "renderSeparated", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/fir/FirElement;", "visitor", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "renderSeparated$tree", "renderSeparatedWithNewlines", "renderSeparatedWithNewlines$tree", "toString", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirPrinter {
    private boolean lineBeginning;
    private final Printer printer;

    public FirPrinter(StringBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        this.printer = new Printer(builder);
        this.lineBeginning = true;
    }

    public final void print(Object... objects) {
        Intrinsics.checkNotNullParameter(objects, "objects");
        if (this.lineBeginning) {
            this.lineBeginning = false;
            this.printer.print(Arrays.copyOf(objects, objects.length));
        } else {
            this.printer.printWithNoIndent(Arrays.copyOf(objects, objects.length));
        }
    }

    public final void println(Object... objects) {
        Intrinsics.checkNotNullParameter(objects, "objects");
        print(Arrays.copyOf(objects, objects.length));
        this.printer.printlnWithNoIndent(new Object[0]);
        this.lineBeginning = true;
    }

    public final void pushIndent$tree() {
        this.printer.pushIndent();
    }

    public final void popIndent$tree() {
        this.printer.popIndent();
    }

    public final void newLine() {
        println(new Object[0]);
    }

    public static /* synthetic */ void renderInBraces$default(FirPrinter firPrinter, String str, String str2, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: renderInBraces");
        }
        if ((i & 1) != 0) {
            str = "{";
        }
        if ((i & 2) != 0) {
            str2 = "}";
        }
        firPrinter.renderInBraces(str, str2, function0);
    }

    public final void renderInBraces(String leftBrace, String rightBrace, Function0<Unit> f) {
        Intrinsics.checkNotNullParameter(leftBrace, "leftBrace");
        Intrinsics.checkNotNullParameter(rightBrace, "rightBrace");
        Intrinsics.checkNotNullParameter(f, "f");
        println(" " + leftBrace);
        pushIndent$tree();
        f.invoke();
        popIndent$tree();
        println(rightBrace);
    }

    public final void renderSeparated$tree(List<? extends FirElement> elements, FirRenderer.Visitor visitor) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        int i = 0;
        for (FirElement firElement : elements) {
            int i2 = i + 1;
            if (i > 0) {
                print(", ");
            }
            firElement.accept(visitor);
            i = i2;
        }
    }

    public final void renderSeparatedWithNewlines$tree(List<? extends FirElement> elements, FirRenderer.Visitor visitor) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        int i = 0;
        for (FirElement firElement : elements) {
            int i2 = i + 1;
            if (i > 0) {
                print(",");
                newLine();
            }
            firElement.accept(visitor);
            i = i2;
        }
    }

    public String toString() {
        String string = this.printer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
