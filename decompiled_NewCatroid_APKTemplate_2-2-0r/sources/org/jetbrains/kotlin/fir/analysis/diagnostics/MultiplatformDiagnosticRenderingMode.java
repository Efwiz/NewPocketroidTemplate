package org.jetbrains.kotlin.fir.analysis.diagnostics;

import com.reandroid.archive.PathTree;
import java.util.Iterator;
import java.util.List;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirPlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007H\u0016J(\u0010\b\u001a\u00020\u00042\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u00072\u0012\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u000b0\nH\u0016J(\u0010\f\u001a\u00020\u00042\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u00072\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/MultiplatformDiagnosticRenderingMode;", "", "()V", "newLine", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "renderList", PathTree.NAME_elements, "", "Lkotlin/Function0;", "renderSymbol", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", OutputKeys.INDENT, "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class MultiplatformDiagnosticRenderingMode {
    public void newLine(StringBuilder sb) {
        Intrinsics.checkNotNullParameter(sb, "sb");
        Intrinsics.checkNotNullExpressionValue(sb.append('\n'), "append(...)");
    }

    public void renderList(StringBuilder sb, List<? extends Function0<Unit>> elements) {
        Intrinsics.checkNotNullParameter(sb, "sb");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Intrinsics.checkNotNullExpressionValue(sb.append('\n'), "append(...)");
        Iterator<? extends Function0<Unit>> it = elements.iterator();
        while (it.hasNext()) {
            it.next().invoke();
        }
    }

    public void renderSymbol(StringBuilder sb, FirBasedSymbol<?> symbol, String indent) {
        Intrinsics.checkNotNullParameter(sb, "sb");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(indent, "indent");
        sb.append(indent);
        sb.append("    ");
        StringBuilder sbAppend = sb.append(FirDiagnosticRenderers.INSTANCE.getSYMBOL().render(symbol));
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
    }
}
