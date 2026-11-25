package org.jetbrains.kotlin.fir.resolve.transformers;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;

/* JADX INFO: compiled from: ReturnTypeCalculatorForFullBodyResolve.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculatorForFullBodyResolve;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "diagnosticKind", "Lorg/jetbrains/kotlin/fir/diagnostics/DiagnosticKind;", "reason", "", "(Lorg/jetbrains/kotlin/fir/diagnostics/DiagnosticKind;Ljava/lang/String;)V", "fakeOverrideTypeCalculator", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "getFakeOverrideTypeCalculator", "()Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "tryCalculateReturnTypeOrNull", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Companion", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReturnTypeCalculatorForFullBodyResolve extends ReturnTypeCalculator {
    private final DiagnosticKind diagnosticKind;
    private final String reason;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ReturnTypeCalculatorForFullBodyResolve Default = new ReturnTypeCalculatorForFullBodyResolve(DiagnosticKind.RecursionInImplicitTypes, "Recursion with local function");
    private static final ReturnTypeCalculatorForFullBodyResolve Contract = new ReturnTypeCalculatorForFullBodyResolve(DiagnosticKind.InferenceError, "Cannot calculate return type during full-body resolution (local class/object?)");

    /* JADX INFO: compiled from: ReturnTypeCalculatorForFullBodyResolve.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculatorForFullBodyResolve$Companion;", "", "()V", "Contract", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculatorForFullBodyResolve;", "getContract", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculatorForFullBodyResolve;", "Default", "getDefault", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ReturnTypeCalculatorForFullBodyResolve getDefault() {
            return ReturnTypeCalculatorForFullBodyResolve.Default;
        }

        public final ReturnTypeCalculatorForFullBodyResolve getContract() {
            return ReturnTypeCalculatorForFullBodyResolve.Contract;
        }
    }

    private ReturnTypeCalculatorForFullBodyResolve(DiagnosticKind diagnosticKind, String str) {
        this.diagnosticKind = diagnosticKind;
        this.reason = str;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator
    public FakeOverrideTypeCalculator getFakeOverrideTypeCalculator() {
        return FakeOverrideTypeCalculator.Forced.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator
    public FirResolvedTypeRef tryCalculateReturnTypeOrNull(FirCallableDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirTypeRef returnTypeRef = declaration.getReturnTypeRef();
        if (returnTypeRef instanceof FirResolvedTypeRef) {
            return (FirResolvedTypeRef) returnTypeRef;
        }
        if (declaration.getOrigin().getFromSupertypes()) {
            return FakeOverrideTypeCalculator.Forced.INSTANCE.computeReturnType(declaration);
        }
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic(this.reason + ": " + UtilsKt.render(declaration), this.diagnosticKind));
        return firErrorTypeRefBuilder.mo6809build();
    }
}
