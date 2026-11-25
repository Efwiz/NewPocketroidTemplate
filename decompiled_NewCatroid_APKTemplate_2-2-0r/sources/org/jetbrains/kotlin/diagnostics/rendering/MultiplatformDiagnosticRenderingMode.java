package org.jetbrains.kotlin.diagnostics.rendering;

import com.reandroid.archive.PathTree;
import java.util.Iterator;
import java.util.List;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: PlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007H\u0016J,\u0010\b\u001a\u00020\u00042\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J(\u0010\u000f\u001a\u00020\u00042\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u00072\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00120\u0011H\u0016¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/MultiplatformDiagnosticRenderingMode;", "", "()V", "newLine", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "renderDescriptor", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", OutputKeys.INDENT, "", "renderList", PathTree.NAME_elements, "", "Lkotlin/Function0;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
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

    public void renderDescriptor(StringBuilder sb, DeclarationDescriptor descriptor, RenderingContext context, String indent) {
        Intrinsics.checkNotNullParameter(sb, "sb");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(indent, "indent");
        sb.append(indent);
        sb.append("    ");
        StringBuilder sbAppend = sb.append(Renderers.COMPACT_WITH_MODIFIERS.render(descriptor, context));
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
    }
}
