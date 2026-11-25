package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: UpperBoundChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u001a\b\u0002\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\t\u0012 \b\u0002\u0010\u000b\u001a\u001a\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0005J\u0016\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u000b\u001a\u001a\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/UpperBoundViolatedReporter;", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "argumentType", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "baseDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "diagnosticForTypeAliases", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;", "Lorg/jetbrains/kotlin/psi/KtElement;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;)V", "report", "", "typeArgumentReference", "substitutedBound", "reportForTypeAliasExpansion", "callElement", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UpperBoundViolatedReporter {
    private final KotlinType argumentType;
    private final DiagnosticFactory2<KtTypeReference, KotlinType, KotlinType> baseDiagnostic;
    private final DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> diagnosticForTypeAliases;
    private final BindingTrace trace;
    private final TypeParameterDescriptor typeParameterDescriptor;

    public UpperBoundViolatedReporter(BindingTrace trace, KotlinType argumentType, TypeParameterDescriptor typeParameterDescriptor, DiagnosticFactory2<KtTypeReference, KotlinType, KotlinType> baseDiagnostic, DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> diagnosticForTypeAliases) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
        Intrinsics.checkNotNullParameter(baseDiagnostic, "baseDiagnostic");
        Intrinsics.checkNotNullParameter(diagnosticForTypeAliases, "diagnosticForTypeAliases");
        this.trace = trace;
        this.argumentType = argumentType;
        this.typeParameterDescriptor = typeParameterDescriptor;
        this.baseDiagnostic = baseDiagnostic;
        this.diagnosticForTypeAliases = diagnosticForTypeAliases;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ UpperBoundViolatedReporter(BindingTrace bindingTrace, KotlinType kotlinType, TypeParameterDescriptor typeParameterDescriptor, DiagnosticFactory2 UPPER_BOUND_VIOLATED, DiagnosticFactory3 UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 8) != 0) {
            UPPER_BOUND_VIOLATED = Errors.UPPER_BOUND_VIOLATED;
            Intrinsics.checkNotNullExpressionValue(UPPER_BOUND_VIOLATED, "UPPER_BOUND_VIOLATED");
        }
        DiagnosticFactory2 diagnosticFactory2 = UPPER_BOUND_VIOLATED;
        if ((i & 16) != 0) {
            UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION = Errors.UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION;
            Intrinsics.checkNotNullExpressionValue(UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION, "UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION");
        }
        this(bindingTrace, kotlinType, typeParameterDescriptor, diagnosticFactory2, UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION);
    }

    public final void report(KtTypeReference typeArgumentReference, KotlinType substitutedBound) {
        Intrinsics.checkNotNullParameter(typeArgumentReference, "typeArgumentReference");
        Intrinsics.checkNotNullParameter(substitutedBound, "substitutedBound");
        BindingTrace bindingTrace = this.trace;
        Diagnostic diagnosticOn = this.baseDiagnostic.on(typeArgumentReference, substitutedBound, this.argumentType);
        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
        DiagnosticUtilsKt.reportDiagnosticOnce(bindingTrace, diagnosticOn);
    }

    public final void reportForTypeAliasExpansion(KtElement callElement, KotlinType substitutedBound) {
        Intrinsics.checkNotNullParameter(callElement, "callElement");
        Intrinsics.checkNotNullParameter(substitutedBound, "substitutedBound");
        BindingTrace bindingTrace = this.trace;
        Diagnostic diagnosticOn = this.diagnosticForTypeAliases.on(callElement, substitutedBound, this.argumentType, this.typeParameterDescriptor);
        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
        DiagnosticUtilsKt.reportDiagnosticOnce(bindingTrace, diagnosticOn);
    }
}
