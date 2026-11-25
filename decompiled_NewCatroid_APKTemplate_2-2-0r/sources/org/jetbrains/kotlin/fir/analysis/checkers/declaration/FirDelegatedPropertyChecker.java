package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeConstraintSystemHasContradiction;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableWrongReceiver;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: FirDelegatedPropertyChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDelegatedPropertyChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegatedPropertyChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirDelegatedPropertyChecker INSTANCE = new FirDelegatedPropertyChecker();

    private FirDelegatedPropertyChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirBlock body;
        FirBlock body2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirExpression delegate = declaration.getDelegate();
        if (delegate == null) {
            return;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(delegate.getTypeRef());
        KtSourceElement source = delegate.getSource();
        if (coneType instanceof ConeErrorType) {
            if (source != null) {
                ConeDiagnostic diagnostic = ((ConeErrorType) coneType).getDiagnostic();
                ConeSimpleDiagnostic coneSimpleDiagnostic = diagnostic instanceof ConeSimpleDiagnostic ? (ConeSimpleDiagnostic) diagnostic : null;
                if ((coneSimpleDiagnostic != null ? coneSimpleDiagnostic.getKind() : null) == DiagnosticKind.RecursionInImplicitTypes) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getRECURSION_IN_IMPLICIT_TYPES(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                return;
            }
            return;
        }
        FirPropertyAccessor getter = declaration.getGetter();
        if (getter != null && (body2 = getter.getBody()) != null) {
            body2.acceptChildren(new DelegatedPropertyAccessorVisitor(reporter, source, coneType, context, declaration, true));
        }
        FirPropertyAccessor setter = declaration.getSetter();
        if (setter == null || (body = setter.getBody()) == null) {
            return;
        }
        body.acceptChildren(new DelegatedPropertyAccessorVisitor(reporter, source, coneType, context, declaration, false));
    }

    /* JADX INFO: compiled from: FirDelegatedPropertyChecker.kt */
    @Metadata(d1 = {"\u0000/\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"org/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDelegatedPropertyChecker$check$DelegatedPropertyAccessorVisitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitorVoid;", "isGet", "", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Z)V", "checkFunctionCall", "", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "checkFunctionReferenceErrors", "checkReturnType", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitFunctionCall", "visitImplicitInvokeCall", "implicitInvokeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DelegatedPropertyAccessorVisitor extends FirVisitorVoid {
        final /* synthetic */ CheckerContext $context;
        final /* synthetic */ FirProperty $declaration;
        final /* synthetic */ ConeKotlinType $delegateType;
        final /* synthetic */ DiagnosticReporter $reporter;
        final /* synthetic */ KtSourceElement $source;
        private final boolean isGet;

        public DelegatedPropertyAccessorVisitor(DiagnosticReporter reporter, KtSourceElement ktSourceElement, ConeKotlinType delegateType, CheckerContext context, FirProperty declaration, boolean z) {
            Intrinsics.checkNotNullParameter(reporter, "$reporter");
            Intrinsics.checkNotNullParameter(delegateType, "$delegateType");
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(declaration, "$declaration");
            this.$reporter = reporter;
            this.$source = ktSourceElement;
            this.$delegateType = delegateType;
            this.$context = context;
            this.$declaration = declaration;
            this.isGet = z;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        /* JADX INFO: renamed from: visitElement */
        public void mo6787visitElement(FirElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            element.acceptChildren(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitFunctionCall(FirFunctionCall functionCall) {
            Intrinsics.checkNotNullParameter(functionCall, "functionCall");
            checkFunctionCall(functionCall);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitImplicitInvokeCall(FirImplicitInvokeCall implicitInvokeCall) {
            Intrinsics.checkNotNullParameter(implicitInvokeCall, "implicitInvokeCall");
            checkFunctionCall(implicitInvokeCall);
        }

        private final void checkFunctionCall(FirFunctionCall functionCall) {
            boolean zCheckFunctionReferenceErrors = checkFunctionReferenceErrors(functionCall);
            if (!this.isGet || zCheckFunctionReferenceErrors) {
                return;
            }
            checkReturnType(functionCall);
        }

        private final boolean checkFunctionReferenceErrors(FirFunctionCall functionCall) {
            FirElement calleeReference = functionCall.getCalleeReference();
            if (!FirReferenceUtilsKt.isError((FirReference) calleeReference)) {
                return false;
            }
            ConeDiagnostic diagnostic = ((FirDiagnosticHolder) calleeReference).getDiagnostic();
            KtSourceElement source = calleeReference.getSource();
            if (!Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE)) {
                return false;
            }
            String str = (this.isGet ? "getValue" : "setValue") + '(' + CollectionsKt.joinToString$default(functionCall.getArgumentList().getArguments(), ", ", null, null, 0, null, new Function1<FirExpression, CharSequence>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDelegatedPropertyChecker$check$DelegatedPropertyAccessorVisitor$checkFunctionReferenceErrors$expectedFunctionSignature$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(FirExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return ConeTypeUtilsKt.renderReadable(FirTypeUtilsKt.getConeType(it.getTypeRef()));
                }
            }, 30, null) + ')';
            String str2 = this.isGet ? MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX : "delegate for var (read-write property)";
            if (diagnostic instanceof ConeUnresolvedNameError) {
                KtDiagnosticReportHelpersKt.reportOn(this.$reporter, (AbstractKtSourceElement) this.$source, (KtDiagnosticFactory3<String, ConeKotlinType, String>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getDELEGATE_SPECIAL_FUNCTION_MISSING()), str, this.$delegateType, str2, (DiagnosticContext) this.$context, (64 & 64) != 0 ? null : null);
            } else if (diagnostic instanceof ConeAmbiguityError) {
                ConeAmbiguityError coneAmbiguityError = (ConeAmbiguityError) diagnostic;
                if (CandidateApplicabilityKt.isSuccess(coneAmbiguityError.getApplicability())) {
                    DiagnosticReporter diagnosticReporter = this.$reporter;
                    KtSourceElement ktSourceElement = this.$source;
                    KtDiagnosticFactory2<String, Collection<FirBasedSymbol<?>>> delegate_special_function_ambiguity = FirErrors.INSTANCE.getDELEGATE_SPECIAL_FUNCTION_AMBIGUITY();
                    Collection<AbstractCandidate> candidates = coneAmbiguityError.getCandidates();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates, 10));
                    Iterator<T> it = candidates.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((AbstractCandidate) it.next()).getSymbol());
                    }
                    KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, delegate_special_function_ambiguity, str, arrayList, this.$context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                } else {
                    DiagnosticReporter diagnosticReporter2 = this.$reporter;
                    KtSourceElement ktSourceElement2 = this.$source;
                    CheckerContext checkerContext = this.$context;
                    Collection<AbstractCandidate> candidates2 = coneAmbiguityError.getCandidates();
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates2, 10));
                    Iterator<T> it2 = candidates2.iterator();
                    while (it2.hasNext()) {
                        arrayList2.add(((AbstractCandidate) it2.next()).getSymbol());
                    }
                    checkFunctionReferenceErrors$reportInapplicableDiagnostics(diagnosticReporter2, ktSourceElement2, str, checkerContext, arrayList2);
                }
            } else if (diagnostic instanceof ConeInapplicableWrongReceiver) {
                KtDiagnosticReportHelpersKt.reportOn(this.$reporter, (AbstractKtSourceElement) this.$source, (KtDiagnosticFactory3<String, ConeKotlinType, String>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getDELEGATE_SPECIAL_FUNCTION_MISSING()), str, this.$delegateType, str2, (DiagnosticContext) this.$context, (64 & 64) != 0 ? null : null);
            } else if (diagnostic instanceof ConeInapplicableCandidateError) {
                checkFunctionReferenceErrors$reportInapplicableDiagnostics(this.$reporter, this.$source, str, this.$context, CollectionsKt.listOf(((ConeInapplicableCandidateError) diagnostic).getCandidate().getSymbol()));
            } else {
                if (!(diagnostic instanceof ConeConstraintSystemHasContradiction)) {
                    return false;
                }
                checkFunctionReferenceErrors$reportInapplicableDiagnostics(this.$reporter, this.$source, str, this.$context, CollectionsKt.listOf(((ConeConstraintSystemHasContradiction) diagnostic).getCandidate().getSymbol()));
            }
            return true;
        }

        private static final void checkFunctionReferenceErrors$reportInapplicableDiagnostics(DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, String str, CheckerContext checkerContext, Collection<? extends FirBasedSymbol<?>> collection) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, FirErrors.INSTANCE.getDELEGATE_SPECIAL_FUNCTION_NONE_APPLICABLE(), str, collection, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }

        private final void checkReturnType(FirFunctionCall functionCall) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(functionCall.getTypeRef());
            ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(this.$declaration.getReturnTypeRef());
            if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(this.$context.getSession()), (KotlinTypeMarker) coneType, (KotlinTypeMarker) coneType2, false, 8, (Object) null)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn(this.$reporter, (AbstractKtSourceElement) this.$source, (KtDiagnosticFactory3<String, ConeKotlinType, ConeKotlinType>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getDELEGATE_SPECIAL_FUNCTION_RETURN_TYPE_MISMATCH()), "getValue", coneType2, coneType, (DiagnosticContext) this.$context, (64 & 64) != 0 ? null : null);
        }
    }
}
