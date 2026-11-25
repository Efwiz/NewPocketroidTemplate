package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.InvocationKindTransformerKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirJvmSuspensionPointInsideMutexLockChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJvmSuspensionPointInsideMutexLockChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "synchronizedBlockParamName", "Lorg/jetbrains/kotlin/name/Name;", "synchronizedCallableId", "Lorg/jetbrains/kotlin/name/CallableId;", "withLockCallableId", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmSuspensionPointInsideMutexLockChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirJvmSuspensionPointInsideMutexLockChecker INSTANCE = new FirJvmSuspensionPointInsideMutexLockChecker();
    private static final Name synchronizedBlockParamName;
    private static final CallableId synchronizedCallableId;
    private static final CallableId withLockCallableId;

    private FirJvmSuspensionPointInsideMutexLockChecker() {
    }

    static {
        FqName fqName = new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        Name nameIdentifier = Name.identifier("synchronized");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        synchronizedCallableId = new CallableId(fqName, nameIdentifier);
        FqName fqName2 = new FqName("kotlin.concurrent");
        Name nameIdentifier2 = Name.identifier("withLock");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        withLockCallableId = new CallableId(fqName2, nameIdentifier2);
        Name nameIdentifier3 = Name.identifier("block");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        synchronizedBlockParamName = nameIdentifier3;
    }

    /* JADX WARN: Code duplicated, block: B:53:0x00df  */
    /* JADX WARN: Code duplicated, block: B:73:0x00eb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x0038 A[SYNTHETIC] */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirValueParameter value;
        ConeKotlinType type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        boolean z = false;
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(expression.getCalleeReference(), false, 1, null);
        if (resolvedCallableSymbol$default != null && resolvedCallableSymbol$default.getRawStatus().isSuspend()) {
            boolean z2 = false;
            FirElement firElement = null;
            for (FirElement firElement2 : CollectionsKt.asReversed(context.getContainingElements())) {
                if (firElement2 instanceof FirFunctionCall) {
                    FirCallableSymbol resolvedCallableSymbol$default2 = FirReferenceUtilsKt.toResolvedCallableSymbol$default(((FirFunctionCall) firElement2).getCalleeReference(), false, 1, null);
                    if (resolvedCallableSymbol$default2 != null) {
                        FirArgumentList argumentList = ((FirCall) firElement2).getArgumentList();
                        LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
                        if (mapping == null) {
                            value = null;
                            break;
                        }
                        Iterator<Map.Entry<FirExpression, FirValueParameter>> it = mapping.entrySet().iterator();
                        do {
                            if (!it.hasNext()) {
                                value = null;
                                break;
                            } else {
                                Map.Entry<FirExpression, FirValueParameter> next = it.next();
                                FirAnonymousFunction firAnonymousFunctionUnwrapAnonymousFunctionExpression = InvocationKindTransformerKt.unwrapAnonymousFunctionExpression(next.getKey());
                                value = (firAnonymousFunctionUnwrapAnonymousFunctionExpression == null || !Intrinsics.areEqual(firAnonymousFunctionUnwrapAnonymousFunctionExpression, firElement)) ? null : next.getValue();
                            }
                        } while (value == null);
                        FirTypeRef returnTypeRef = value != null ? value.getReturnTypeRef() : null;
                        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
                        if (firResolvedTypeRef != null && (type = firResolvedTypeRef.getType()) != null && FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType(type, context.getSession())) {
                            z = true;
                            break;
                        } else if (Intrinsics.areEqual(resolvedCallableSymbol$default2.getCallableId(), synchronizedCallableId)) {
                            if (!Intrinsics.areEqual(value != null ? value.getName() : null, synchronizedBlockParamName)) {
                                if (Intrinsics.areEqual(resolvedCallableSymbol$default2.getCallableId(), withLockCallableId)) {
                                }
                            }
                            z2 = true;
                        } else if (Intrinsics.areEqual(resolvedCallableSymbol$default2.getCallableId(), withLockCallableId)) {
                            z2 = true;
                        }
                    } else {
                        continue;
                    }
                } else if (!(firElement2 instanceof FirFunction)) {
                    continue;
                } else if (((FirMemberDeclaration) firElement2).getStatus().isSuspend()) {
                    z = true;
                    break;
                } else if (firElement2 instanceof FirAnonymousFunction) {
                    firElement = firElement2;
                }
            }
            if (z2 && z) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJvmErrors.INSTANCE.getSUSPENSION_POINT_INSIDE_CRITICAL_SECTION(), resolvedCallableSymbol$default, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }
}
