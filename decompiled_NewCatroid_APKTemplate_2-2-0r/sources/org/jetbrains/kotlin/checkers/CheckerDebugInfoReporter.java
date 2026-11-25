package org.jetbrains.kotlin.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.checkers.diagnostics.ActualDiagnostic;
import org.jetbrains.kotlin.checkers.diagnostics.DebugInfoDiagnostic;
import org.jetbrains.kotlin.checkers.diagnostics.factories.DebugInfoDiagnosticFactory0;
import org.jetbrains.kotlin.checkers.utils.DebugInfoUtil;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtReferenceExpression;

/* JADX INFO: compiled from: CheckerDebugInfoReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B;\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0004H\u0016J\u0010\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000bH\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/checkers/CheckerDebugInfoReporter;", "Lorg/jetbrains/kotlin/checkers/utils/DebugInfoUtil$DebugInfoReporter;", "dynamicCallDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "markDynamicCalls", "", "debugAnnotations", "Lorg/jetbrains/kotlin/checkers/diagnostics/ActualDiagnostic;", "withNewInference", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "", "(Ljava/util/List;ZLjava/util/List;ZLjava/lang/String;)V", "newDiagnostic", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "factory", "Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory0;", "reportDynamicCall", "declarationDescriptor", "reportElementWithErrorType", "expression", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "reportMissingUnresolved", "reportUnresolvedWithTarget", "target", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckerDebugInfoReporter extends DebugInfoUtil.DebugInfoReporter {
    private final List<ActualDiagnostic> debugAnnotations;
    private final List<DeclarationDescriptor> dynamicCallDescriptors;
    private final boolean markDynamicCalls;
    private final String platform;
    private final boolean withNewInference;

    public CheckerDebugInfoReporter(List<DeclarationDescriptor> dynamicCallDescriptors, boolean z, List<ActualDiagnostic> debugAnnotations, boolean z2, String str) {
        Intrinsics.checkNotNullParameter(dynamicCallDescriptors, "dynamicCallDescriptors");
        Intrinsics.checkNotNullParameter(debugAnnotations, "debugAnnotations");
        this.dynamicCallDescriptors = dynamicCallDescriptors;
        this.markDynamicCalls = z;
        this.debugAnnotations = debugAnnotations;
        this.withNewInference = z2;
        this.platform = str;
    }

    @Override // org.jetbrains.kotlin.checkers.utils.DebugInfoUtil.DebugInfoReporter
    public void reportElementWithErrorType(KtReferenceExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        newDiagnostic(expression, DebugInfoDiagnosticFactory0.INSTANCE.getELEMENT_WITH_ERROR_TYPE());
    }

    @Override // org.jetbrains.kotlin.checkers.utils.DebugInfoUtil.DebugInfoReporter
    public void reportMissingUnresolved(KtReferenceExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        newDiagnostic(expression, DebugInfoDiagnosticFactory0.INSTANCE.getMISSING_UNRESOLVED());
    }

    @Override // org.jetbrains.kotlin.checkers.utils.DebugInfoUtil.DebugInfoReporter
    public void reportUnresolvedWithTarget(KtReferenceExpression expression, String target) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(target, "target");
        newDiagnostic(expression, DebugInfoDiagnosticFactory0.INSTANCE.getUNRESOLVED_WITH_TARGET());
    }

    @Override // org.jetbrains.kotlin.checkers.utils.DebugInfoUtil.DebugInfoReporter
    public void reportDynamicCall(KtElement element, DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        this.dynamicCallDescriptors.add(declarationDescriptor);
        if (this.markDynamicCalls) {
            newDiagnostic(element, DebugInfoDiagnosticFactory0.INSTANCE.getDYNAMIC());
        }
    }

    private final void newDiagnostic(KtElement element, DebugInfoDiagnosticFactory0 factory) {
        this.debugAnnotations.add(new ActualDiagnostic(new DebugInfoDiagnostic(element, factory), this.platform, this.withNewInference));
    }
}
