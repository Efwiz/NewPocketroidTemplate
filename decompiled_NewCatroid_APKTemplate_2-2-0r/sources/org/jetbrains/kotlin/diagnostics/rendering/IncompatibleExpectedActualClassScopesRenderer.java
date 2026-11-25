package org.jetbrains.kotlin.diagnostics.rendering;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: PlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001022\u0012.\u0012,\u0012(\u0012&\u0012\u0004\u0012\u00020\u0004\u0012\u001c\u0012\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00050\u00030\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJB\u0010\u000b\u001a\u00020\f20\u0010\r\u001a,\u0012(\u0012&\u0012\u0004\u0012\u00020\u0004\u0012\u001c\u0012\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00050\u00030\u00022\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/IncompatibleExpectedActualClassScopesRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "", "mode", "Lorg/jetbrains/kotlin/diagnostics/rendering/MultiplatformDiagnosticRenderingMode;", "(Lorg/jetbrains/kotlin/diagnostics/rendering/MultiplatformDiagnosticRenderingMode;)V", "render", "", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncompatibleExpectedActualClassScopesRenderer implements DiagnosticParameterRenderer<List<? extends Pair<? extends MemberDescriptor, ? extends Map<ExpectActualCompatibility.Incompatible<? extends MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>>>>> {
    public static final IncompatibleExpectedActualClassScopesRenderer TEXT = new IncompatibleExpectedActualClassScopesRenderer(new MultiplatformDiagnosticRenderingMode());
    private final MultiplatformDiagnosticRenderingMode mode;

    public IncompatibleExpectedActualClassScopesRenderer(MultiplatformDiagnosticRenderingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.mode = mode;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public /* bridge */ /* synthetic */ String render(List<? extends Pair<? extends MemberDescriptor, ? extends Map<ExpectActualCompatibility.Incompatible<? extends MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>>>> list, RenderingContext renderingContext) {
        return render2((List<? extends Pair<? extends MemberDescriptor, ? extends Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>>>>) list, renderingContext);
    }

    /* JADX INFO: renamed from: render, reason: avoid collision after fix types in other method */
    public String render2(List<? extends Pair<? extends MemberDescriptor, ? extends Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>>>> obj, RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        if (obj.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        this.mode.newLine(sb);
        PlatformIncompatibilityDiagnosticRendererKt.renderIncompatibleClassScopes(sb, obj, "", renderingContext, this.mode);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
