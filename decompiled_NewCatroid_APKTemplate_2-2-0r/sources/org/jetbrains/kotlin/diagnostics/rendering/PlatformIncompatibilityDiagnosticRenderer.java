package org.jetbrains.kotlin.diagnostics.rendering;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: PlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2 \u0012\u001c\u0012\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00050\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ0\u0010\t\u001a\u00020\n2\u001e\u0010\u000b\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00050\u00022\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/PlatformIncompatibilityDiagnosticRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "", "mode", "Lorg/jetbrains/kotlin/diagnostics/rendering/MultiplatformDiagnosticRenderingMode;", "(Lorg/jetbrains/kotlin/diagnostics/rendering/MultiplatformDiagnosticRenderingMode;)V", "render", "", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlatformIncompatibilityDiagnosticRenderer implements DiagnosticParameterRenderer<Map<ExpectActualCompatibility.Incompatible<? extends MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>>> {
    public static final PlatformIncompatibilityDiagnosticRenderer TEXT = new PlatformIncompatibilityDiagnosticRenderer(new MultiplatformDiagnosticRenderingMode());
    private final MultiplatformDiagnosticRenderingMode mode;

    public PlatformIncompatibilityDiagnosticRenderer(MultiplatformDiagnosticRenderingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.mode = mode;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public /* bridge */ /* synthetic */ String render(Map<ExpectActualCompatibility.Incompatible<? extends MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>> map, RenderingContext renderingContext) {
        return render2((Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>>) map, renderingContext);
    }

    /* JADX INFO: renamed from: render, reason: avoid collision after fix types in other method */
    public String render2(Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>> obj, RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        if (obj.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        this.mode.newLine(sb);
        PlatformIncompatibilityDiagnosticRendererKt.renderIncompatibilityInformation(sb, obj, "", renderingContext, this.mode);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
