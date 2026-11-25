package org.jetbrains.kotlin.diagnostics.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualMemberDiff;

/* JADX INFO: compiled from: PlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00030\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ*\u0010\t\u001a\u00020\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00030\u00022\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/ExpectActualScopeDiffsRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff;", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "mode", "Lorg/jetbrains/kotlin/diagnostics/rendering/MultiplatformDiagnosticRenderingMode;", "(Lorg/jetbrains/kotlin/diagnostics/rendering/MultiplatformDiagnosticRenderingMode;)V", "render", "", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualScopeDiffsRenderer implements DiagnosticParameterRenderer<Set<? extends ExpectActualMemberDiff<? extends CallableMemberDescriptor, ? extends ClassDescriptor>>> {
    public static final ExpectActualScopeDiffsRenderer TEXT = new ExpectActualScopeDiffsRenderer(new MultiplatformDiagnosticRenderingMode());
    private final MultiplatformDiagnosticRenderingMode mode;

    public ExpectActualScopeDiffsRenderer(MultiplatformDiagnosticRenderingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.mode = mode;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public String render(Set<? extends ExpectActualMemberDiff<? extends CallableMemberDescriptor, ? extends ClassDescriptor>> obj, final RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        if (obj.isEmpty()) {
            throw new IllegalStateException("Check failed.".toString());
        }
        final StringBuilder sb = new StringBuilder();
        MultiplatformDiagnosticRenderingMode multiplatformDiagnosticRenderingMode = this.mode;
        List<ExpectActualMemberDiff> list = CollectionsKt.toList(obj);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (final ExpectActualMemberDiff expectActualMemberDiff : list) {
            arrayList.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.ExpectActualScopeDiffsRenderer$render$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Intrinsics.checkNotNullExpressionValue(sb.append('\n'), "append(...)");
                    StringBuilder sbAppend = sb.append(ExpectActualScopeDiffRenderer.INSTANCE.render2((ExpectActualMemberDiff<? extends CallableMemberDescriptor, ? extends ClassDescriptor>) expectActualMemberDiff, renderingContext));
                    Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
                    Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
                }
            });
        }
        multiplatformDiagnosticRenderingMode.renderList(sb, arrayList);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
