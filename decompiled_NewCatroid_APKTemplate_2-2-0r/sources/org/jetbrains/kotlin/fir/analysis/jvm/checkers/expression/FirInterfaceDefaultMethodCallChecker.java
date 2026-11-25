package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.FirJvmAnnotationHelperKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.java.JvmDefaultModeContainerKt;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbolKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirInterfaceDefaultMethodCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u000e\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\tH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirInterfaceDefaultMethodCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "findContainingMember", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirInterfaceDefaultMethodCallChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirInterfaceDefaultMethodCallChecker INSTANCE = new FirInterfaceDefaultMethodCallChecker();

    private FirInterfaceDefaultMethodCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        CallableId callableId;
        ClassId classId;
        FirRegularClass firRegularClass;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(expression.getCalleeReference(), false, 1, null);
        if (resolvedCallableSymbol$default == null || (callableId = resolvedCallableSymbol$default.getCallableId()) == null || (classId = callableId.getClassId()) == null || classId.isLocal() || FirHelpersKt.explicitReceiverIsNotSuperReference(expression)) {
            return;
        }
        Iterator it = CollectionsKt.asReversed(context.getContainingDeclarations()).iterator();
        while (true) {
            if (!it.hasNext()) {
                firRegularClass = null;
                break;
            }
            FirDeclaration firDeclaration = (FirDeclaration) it.next();
            if (!(firDeclaration instanceof FirRegularClass)) {
                firDeclaration = null;
            }
            firRegularClass = (FirRegularClass) firDeclaration;
            if (firRegularClass != null && firRegularClass != null) {
                break;
            }
        }
        FirRegularClass firRegularClass2 = firRegularClass;
        if (firRegularClass2 == null) {
            return;
        }
        FirSession session = context.getSession();
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(session).getClassLikeSymbolByClassId(classId);
        FirRegularClassSymbol firRegularClassSymbol = classLikeSymbolByClassId instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbolByClassId : null;
        if (firRegularClassSymbol == null) {
            return;
        }
        JvmDefaultMode jvmDefaultModeState = JvmDefaultModeContainerKt.getJvmDefaultModeState(session);
        if (firRegularClassSymbol.getClassKind() == ClassKind.INTERFACE) {
            if (((firRegularClassSymbol.getOrigin() instanceof FirDeclarationOrigin.Java) || FirJvmAnnotationHelperKt.isCompiledToJvmDefault(resolvedCallableSymbol$default, session, jvmDefaultModeState)) && firRegularClass2.getClassKind() == ClassKind.INTERFACE) {
                FirCallableDeclaration firCallableDeclarationFindContainingMember = findContainingMember(context);
                FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclarationFindContainingMember != null ? firCallableDeclarationFindContainingMember.getSymbol() : null;
                if (symbol == null || FirJvmAnnotationHelperKt.isCompiledToJvmDefault(symbol, session, jvmDefaultModeState)) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJvmErrors.INSTANCE.getINTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final FirCallableDeclaration findContainingMember(CheckerContext checkerContext) {
        FirCallableDeclaration firCallableDeclaration;
        Iterator it = CollectionsKt.asReversed(checkerContext.getContainingDeclarations()).iterator();
        while (true) {
            firCallableDeclaration = null;
            if (!it.hasNext()) {
                break;
            }
            FirDeclaration firDeclaration = (FirDeclaration) it.next();
            if (!(firDeclaration instanceof FirCallableDeclaration)) {
                firDeclaration = null;
            }
            FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) firDeclaration;
            if (firCallableDeclaration2 != null) {
                FirCallableDeclaration firCallableDeclaration3 = firCallableDeclaration2;
                firCallableDeclaration = (((firCallableDeclaration3 instanceof FirSimpleFunction) && !Intrinsics.areEqual(((FirSimpleFunction) firCallableDeclaration3).getSymbol().getCallableId().getClassId(), FirClassLikeSymbolKt.getANONYMOUS_CLASS_ID())) || (firCallableDeclaration3 instanceof FirProperty)) ? firCallableDeclaration2 : null;
                if (firCallableDeclaration != null) {
                    break;
                }
            }
        }
        return firCallableDeclaration;
    }
}
