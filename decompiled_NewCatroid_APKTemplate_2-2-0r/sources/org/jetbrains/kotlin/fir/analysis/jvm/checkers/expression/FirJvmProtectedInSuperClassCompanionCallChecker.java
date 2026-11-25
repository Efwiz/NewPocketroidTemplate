package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: FirJvmProtectedInSuperClassCompanionCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJvmProtectedInSuperClassCompanionCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmProtectedInSuperClassCompanionCallChecker extends FirExpressionChecker<FirStatement> {
    public static final FirJvmProtectedInSuperClassCompanionCallChecker INSTANCE = new FirJvmProtectedInSuperClassCompanionCallChecker();

    private FirJvmProtectedInSuperClassCompanionCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirStatement expression, CheckerContext context, DiagnosticReporter reporter) {
        FirExpression dispatchReceiver;
        FirRegularClassSymbol regularClassSymbol;
        FirReference calleeReference;
        FirCallableSymbol resolvedCallableSymbol$default;
        Visibility visibility;
        FirClass firClass;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirClass firClass2 = null;
        if (expression instanceof FirQualifiedAccessExpression) {
            dispatchReceiver = ((FirQualifiedAccessExpression) expression).getDispatchReceiver();
        } else {
            dispatchReceiver = expression instanceof FirVariableAssignment ? FirExpressionUtilKt.getDispatchReceiver((FirVariableAssignment) expression) : null;
        }
        if (dispatchReceiver == null || (dispatchReceiver instanceof FirNoReceiverExpression) || (regularClassSymbol = FirHelpersKt.toRegularClassSymbol(dispatchReceiver.getTypeRef(), context.getSession())) == null || (calleeReference = FirExpressionUtilKt.getCalleeReference(expression)) == null || (resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null)) == null) {
            return;
        }
        if (resolvedCallableSymbol$default instanceof FirPropertySymbol) {
            if (expression instanceof FirVariableAssignment) {
                FirPropertyAccessorSymbol setterSymbol = ((FirPropertySymbol) resolvedCallableSymbol$default).getSetterSymbol();
                if (setterSymbol == null || (visibility = setterSymbol.getResolvedStatus().getVisibility()) == null) {
                    visibility = resolvedCallableSymbol$default.getResolvedStatus().getVisibility();
                }
            } else {
                FirPropertyAccessorSymbol getterSymbol = ((FirPropertySymbol) resolvedCallableSymbol$default).getGetterSymbol();
                if (getterSymbol == null || (visibility = getterSymbol.getResolvedStatus().getVisibility()) == null) {
                    visibility = resolvedCallableSymbol$default.getResolvedStatus().getVisibility();
                }
            }
        } else {
            visibility = resolvedCallableSymbol$default.getResolvedStatus().getVisibility();
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Protected.INSTANCE) && FirAnnotationUtilsKt.getAnnotationByClassId(resolvedCallableSymbol$default, AnnotationUtilKt.getJVM_STATIC_ANNOTATION_CLASS_ID(), context.getSession()) == null && regularClassSymbol.getRawStatus().isCompanion()) {
            FirClassLikeSymbol<?> containingDeclarationSymbol = FirHelpersKt.getContainingDeclarationSymbol(regularClassSymbol, context.getSession());
            FirRegularClassSymbol firRegularClassSymbol = containingDeclarationSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) containingDeclarationSymbol : null;
            if (firRegularClassSymbol == null) {
                return;
            }
            ConeClassLikeType coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(firRegularClassSymbol);
            Iterator it = CollectionsKt.asReversed(context.getContainingDeclarations()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    firClass = null;
                    break;
                }
                FirDeclaration firDeclaration = (FirDeclaration) it.next();
                if (!(firDeclaration instanceof FirClass)) {
                    firDeclaration = null;
                }
                firClass = (FirClass) firDeclaration;
                if (firClass != null) {
                    if (!AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(context.getSession()), (KotlinTypeMarker) ScopeUtilsKt.defaultType(firClass.getSymbol()), (KotlinTypeMarker) coneClassLikeTypeDefaultType, false, 8, (Object) null)) {
                        firClass = null;
                    }
                    if (firClass != null) {
                        break;
                    }
                }
            }
            if (firClass == null) {
                return;
            }
            for (FirDeclaration firDeclaration2 : CollectionsKt.asReversed(context.getContainingDeclarations())) {
                if (!(firDeclaration2 instanceof FirClass)) {
                    firDeclaration2 = null;
                }
                FirClass firClass3 = (FirClass) firDeclaration2;
                if (firClass3 != null) {
                    FirClass firClass4 = firClass3;
                    if (!Intrinsics.areEqual(firClass4.getSymbol(), regularClassSymbol) && !Intrinsics.areEqual(firClass4.getSymbol(), firRegularClassSymbol)) {
                        firClass3 = null;
                    }
                    if (firClass3 != null) {
                        firClass2 = firClass3;
                        break;
                    }
                }
            }
            if (firClass2 == null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, calleeReference.getSource(), FirJvmErrors.INSTANCE.getSUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
