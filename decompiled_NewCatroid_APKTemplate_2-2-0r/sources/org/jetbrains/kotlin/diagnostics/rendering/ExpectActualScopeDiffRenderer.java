package org.jetbrains.kotlin.diagnostics.rendering;

import java.text.MessageFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualMemberDiff;

/* JADX INFO: compiled from: PlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0005J$\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/ExpectActualScopeDiffRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff;", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "()V", "render", "", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualScopeDiffRenderer implements DiagnosticParameterRenderer<ExpectActualMemberDiff<? extends CallableMemberDescriptor, ? extends ClassDescriptor>> {
    public static final ExpectActualScopeDiffRenderer INSTANCE = new ExpectActualScopeDiffRenderer();

    private ExpectActualScopeDiffRenderer() {
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public String render(ExpectActualMemberDiff<? extends CallableMemberDescriptor, ? extends ClassDescriptor> obj, RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        String str = MessageFormat.format(obj.getKind().getRawMessage(), Renderers.DECLARATION_NAME_WITH_KIND.render(obj.getActualMember(), renderingContext), Renderers.NAME.render(obj.getExpectClass()));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }
}
