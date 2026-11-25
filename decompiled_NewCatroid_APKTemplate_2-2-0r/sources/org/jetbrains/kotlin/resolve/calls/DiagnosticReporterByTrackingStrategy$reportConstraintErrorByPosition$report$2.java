package org.jetbrains.kotlin.resolve.calls;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.resolve.calls.tower.TrackingBindingTrace;

/* JADX INFO: compiled from: DiagnosticReporterByTrackingStrategy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DiagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$2 extends FunctionReference implements Function1<Diagnostic, Unit> {
    DiagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$2(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "report(Lorg/jetbrains/kotlin/diagnostics/Diagnostic;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "report";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(TrackingBindingTrace.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Diagnostic diagnostic) {
        invoke2(diagnostic);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(Diagnostic p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((TrackingBindingTrace) this.receiver).report(p0);
    }
}
