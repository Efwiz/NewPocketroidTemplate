package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedCallableReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSuspendCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J,\u0010\u0016\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J4\u0010\u0019\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001b2\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J \u0010\u001e\u001a\u00020\u00132\b\u0010\u001f\u001a\u0004\u0018\u00010 2\f\u0010!\u001a\b\u0012\u0002\b\u0003\u0018\u00010\"H\u0002J8\u0010#\u001a\u001a\u0012\u0006\u0012\u0004\u0018\u00010 \u0012\u0006\u0012\u0004\u0018\u00010 \u0012\u0006\u0012\u0004\u0018\u00010%0$*\u00020\u00022\u0006\u0010&\u001a\u00020'2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0002J\u000e\u0010(\u001a\u0004\u0018\u00010)*\u00020\u0002H\u0002J\u0014\u0010*\u001a\u00020\u0013*\u00020%2\u0006\u0010&\u001a\u00020'H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirSuspendCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "BUILTIN_SUSPEND_NAME", "Lorg/jetbrains/kotlin/name/Name;", "KOTLIN_SUSPEND_BUILT_IN_FUNCTION_CALLABLE_ID", "Lorg/jetbrains/kotlin/name/CallableId;", "getKOTLIN_SUSPEND_BUILT_IN_FUNCTION_CALLABLE_ID$checkers", "()Lorg/jetbrains/kotlin/name/CallableId;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkNonLocalReturnUsage", "", "enclosingSuspendFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "checkRestrictsSuspension", "calledDeclarationSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "checkSuspendModifierForm", "reference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", NativeSymbol.TYPE_NAME, "findEnclosingSuspendFunction", "sameInstanceOfReceiver", "useSiteReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "declarationSiteReceiverOwnerSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "computeReceiversInfo", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "formOfSuspendModifierForLambdaOrFun", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/SuspendCallArgumentKind;", "isRestrictSuspensionReceiver", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSuspendCallChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    private static final Name BUILTIN_SUSPEND_NAME;
    public static final FirSuspendCallChecker INSTANCE = new FirSuspendCallChecker();
    private static final CallableId KOTLIN_SUSPEND_BUILT_IN_FUNCTION_CALLABLE_ID;

    /* JADX INFO: compiled from: FirSuspendCallChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SuspendCallArgumentKind.values().length];
            try {
                iArr[SuspendCallArgumentKind.FUN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SuspendCallArgumentKind.LAMBDA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirSuspendCallChecker() {
    }

    static {
        Name callableName = StandardClassIds.Callables.INSTANCE.getSuspend().getCallableName();
        BUILTIN_SUSPEND_NAME = callableName;
        KOTLIN_SUSPEND_BUILT_IN_FUNCTION_CALLABLE_ID = new CallableId(StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE(), callableName);
    }

    public final CallableId getKOTLIN_SUSPEND_BUILT_IN_FUNCTION_CALLABLE_ID$checkers() {
        return KOTLIN_SUSPEND_BUILT_IN_FUNCTION_CALLABLE_ID;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(expression.getCalleeReference());
        if (resolved == null) {
            return;
        }
        FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
        FirCallableSymbol<?> firCallableSymbol = resolvedSymbol instanceof FirCallableSymbol ? (FirCallableSymbol) resolvedSymbol : null;
        if (firCallableSymbol == null) {
            return;
        }
        Name name2 = resolved.getName();
        Name name3 = BUILTIN_SUSPEND_NAME;
        if (Intrinsics.areEqual(name2, name3) || ((firCallableSymbol instanceof FirNamedFunctionSymbol) && Intrinsics.areEqual(firCallableSymbol.getName(), name3))) {
            checkSuspendModifierForm(expression, resolved, firCallableSymbol, context, reporter);
        }
        if (resolved instanceof FirResolvedCallableReference) {
            return;
        }
        boolean z = firCallableSymbol instanceof FirNamedFunctionSymbol;
        if (!z) {
            if (!(firCallableSymbol instanceof FirPropertySymbol) || !Intrinsics.areEqual(firCallableSymbol.getCallableId(), StandardClassIds.Callables.INSTANCE.getCoroutineContext())) {
                return;
            }
        } else if (!firCallableSymbol.getRawStatus().isSuspend()) {
            return;
        }
        FirFunction firFunctionFindEnclosingSuspendFunction = findEnclosingSuspendFunction(context);
        if (firFunctionFindEnclosingSuspendFunction != null) {
            if (!checkNonLocalReturnUsage(firFunctionFindEnclosingSuspendFunction, context)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getNON_LOCAL_SUSPENSION_POINT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (checkRestrictsSuspension(expression, firFunctionFindEnclosingSuspendFunction, firCallableSymbol, context)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getILLEGAL_RESTRICTED_SUSPENDING_FUNCTION_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (z) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getILLEGAL_SUSPEND_FUNCTION_CALL(), firCallableSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        } else if (firCallableSymbol instanceof FirPropertySymbol) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getILLEGAL_SUSPEND_PROPERTY_ACCESS(), firCallableSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private final void checkSuspendModifierForm(FirQualifiedAccessExpression expression, FirResolvedNamedReference reference, FirCallableSymbol<?> symbol, CheckerContext context, DiagnosticReporter reporter) {
        if (Intrinsics.areEqual(symbol.getCallableId(), KOTLIN_SUSPEND_BUILT_IN_FUNCTION_CALLABLE_ID)) {
            if (Intrinsics.areEqual(reference.getName(), BUILTIN_SUSPEND_NAME) && expression.getExplicitReceiver() == null && formOfSuspendModifierForLambdaOrFun(expression) != null) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getNON_MODIFIER_FORM_FOR_BUILT_IN_SUSPEND(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (Intrinsics.areEqual(reference.getName(), BUILTIN_SUSPEND_NAME)) {
            SuspendCallArgumentKind suspendCallArgumentKindFormOfSuspendModifierForLambdaOrFun = formOfSuspendModifierForLambdaOrFun(expression);
            int i = suspendCallArgumentKindFormOfSuspendModifierForLambdaOrFun == null ? -1 : WhenMappings.$EnumSwitchMapping$0[suspendCallArgumentKindFormOfSuspendModifierForLambdaOrFun.ordinal()];
            if (i == 1) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getMODIFIER_FORM_FOR_NON_BUILT_IN_SUSPEND_FUN(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else {
                if (i != 2) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getMODIFIER_FORM_FOR_NON_BUILT_IN_SUSPEND(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0077  */
    /* JADX WARN: Code duplicated, block: B:31:0x0089  */
    /* JADX WARN: Code duplicated, block: B:33:0x008c  */
    /* JADX WARN: Multi-variable type inference failed */
    private final SuspendCallArgumentKind formOfSuspendModifierForLambdaOrFun(FirQualifiedAccessExpression firQualifiedAccessExpression) {
        KtSourceElement child$default;
        LighterASTNode lighterASTNode;
        KtSourceElement ktSourceElement;
        KtSourceElement child$default2;
        KtSourceElement child$default3;
        IElementType tokenType = null;
        if (!(firQualifiedAccessExpression instanceof FirFunctionCall)) {
            return null;
        }
        FirFunctionCall firFunctionCall = (FirFunctionCall) firQualifiedAccessExpression;
        if (firFunctionCall.getCalleeReference() instanceof FirResolvedCallableReference) {
            return null;
        }
        List<FirTypeProjection> typeArguments = firQualifiedAccessExpression.getTypeArguments();
        if (!(typeArguments instanceof Collection) || !typeArguments.isEmpty()) {
            Iterator<T> it = typeArguments.iterator();
            while (it.hasNext()) {
                if (((FirTypeProjection) it.next()).getSource() != null) {
                    return null;
                }
            }
        }
        FirCall firCall = (FirCall) firQualifiedAccessExpression;
        if (CollectionsKt.singleOrNull((List) firCall.getArgumentList().getArguments()) instanceof FirLambdaArgumentExpression) {
            if (firQualifiedAccessExpression.getExplicitReceiver() == null) {
                child$default2 = firQualifiedAccessExpression.getSource();
            } else {
                KtSourceElement source = firQualifiedAccessExpression.getSource();
                if (source != null) {
                    IElementType CALL_EXPRESSION = KtNodeTypes.CALL_EXPRESSION;
                    Intrinsics.checkNotNullExpressionValue(CALL_EXPRESSION, "CALL_EXPRESSION");
                    child$default2 = FirSourceUtilsKt.getChild$default(source, CALL_EXPRESSION, 1, 1, false, 8, (Object) null);
                } else {
                    ktSourceElement = null;
                }
                if (ktSourceElement != null) {
                    IElementType VALUE_ARGUMENT_LIST = KtNodeTypes.VALUE_ARGUMENT_LIST;
                    Intrinsics.checkNotNullExpressionValue(VALUE_ARGUMENT_LIST, "VALUE_ARGUMENT_LIST");
                    child$default3 = FirSourceUtilsKt.getChild$default(ktSourceElement, VALUE_ARGUMENT_LIST, 0, 1, false, 10, (Object) null);
                } else {
                    child$default3 = null;
                }
                if (child$default3 == null) {
                    return SuspendCallArgumentKind.LAMBDA;
                }
            }
            ktSourceElement = child$default2;
            if (ktSourceElement != null) {
                IElementType VALUE_ARGUMENT_LIST2 = KtNodeTypes.VALUE_ARGUMENT_LIST;
                Intrinsics.checkNotNullExpressionValue(VALUE_ARGUMENT_LIST2, "VALUE_ARGUMENT_LIST");
                child$default3 = FirSourceUtilsKt.getChild$default(ktSourceElement, VALUE_ARGUMENT_LIST2, 0, 1, false, 10, (Object) null);
            } else {
                child$default3 = null;
            }
            if (child$default3 == null) {
                return SuspendCallArgumentKind.LAMBDA;
            }
        }
        if (firFunctionCall.getOrigin() == FirFunctionCallOrigin.Infix) {
            FirExpression firExpression = (FirExpression) CollectionsKt.lastOrNull((List) firCall.getArgumentList().getArguments());
            if (firExpression instanceof FirAnonymousFunctionExpression) {
                KtSourceElement source2 = firQualifiedAccessExpression.getSource();
                if (source2 != null) {
                    IElementType PARENTHESIZED = KtNodeTypes.PARENTHESIZED;
                    Intrinsics.checkNotNullExpressionValue(PARENTHESIZED, "PARENTHESIZED");
                    child$default = FirSourceUtilsKt.getChild$default(source2, PARENTHESIZED, 0, 1, false, 10, (Object) null);
                } else {
                    child$default = null;
                }
                if (child$default == null) {
                    KtSourceElement source3 = ((FirAnonymousFunctionExpression) firExpression).getSource();
                    if (source3 != null && (lighterASTNode = source3.getLighterASTNode()) != null) {
                        tokenType = lighterASTNode.getTokenType();
                    }
                    if (Intrinsics.areEqual(tokenType, KtStubElementTypes.FUNCTION)) {
                        return SuspendCallArgumentKind.FUN;
                    }
                    return SuspendCallArgumentKind.LAMBDA;
                }
            }
        }
        return null;
    }

    private final FirFunction findEnclosingSuspendFunction(CheckerContext context) {
        FirDeclaration firDeclarationPrevious;
        boolean zIsSuspend;
        List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
        ListIterator<FirDeclaration> listIterator = containingDeclarations.listIterator(containingDeclarations.size());
        do {
            if (!listIterator.hasPrevious()) {
                firDeclarationPrevious = null;
                break;
            }
            firDeclarationPrevious = listIterator.previous();
            FirDeclaration firDeclaration = firDeclarationPrevious;
            if (firDeclaration instanceof FirAnonymousFunction) {
                FirAnonymousFunction firAnonymousFunction = (FirAnonymousFunction) firDeclaration;
                zIsSuspend = firAnonymousFunction.getIsLambda() ? FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType(FirTypeUtilsKt.getConeType(firAnonymousFunction.getTypeRef()), context.getSession()) : ((FirMemberDeclaration) firDeclaration).getStatus().isSuspend();
            } else {
                zIsSuspend = firDeclaration instanceof FirSimpleFunction ? ((FirMemberDeclaration) firDeclaration).getStatus().isSuspend() : false;
            }
        } while (!zIsSuspend);
        if (firDeclarationPrevious instanceof FirFunction) {
            return (FirFunction) firDeclarationPrevious;
        }
        return null;
    }

    private final boolean checkNonLocalReturnUsage(FirFunction enclosingSuspendFunction, CheckerContext context) {
        List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
        Object obj = null;
        if (containingDeclarations instanceof List) {
            int size = containingDeclarations.size() - 1;
            if (size >= 0) {
                while (true) {
                    int i = size - 1;
                    obj = containingDeclarations.get(size);
                    if (obj instanceof FirFunction) {
                        obj = obj;
                        break;
                    }
                    if (i < 0) {
                        break;
                    }
                    size = i;
                }
            }
        } else {
            for (Object obj2 : CollectionsKt.reversed(containingDeclarations)) {
                if (obj2 instanceof FirFunction) {
                    obj = obj2;
                    break;
                }
            }
        }
        FirFunction firFunction = (FirFunction) obj;
        if (firFunction == null) {
            return false;
        }
        if (!(firFunction instanceof FirAnonymousFunction) || enclosingSuspendFunction == firFunction) {
            return enclosingSuspendFunction == firFunction;
        }
        return ((FirAnonymousFunction) firFunction).getInlineStatus().getReturnAllowed();
    }

    private final boolean checkRestrictsSuspension(FirQualifiedAccessExpression expression, FirFunction enclosingSuspendFunction, FirCallableSymbol<?> calledDeclarationSymbol, CheckerContext context) {
        FirResolvedTypeRef resolvedReceiverTypeRef;
        ConeKotlinType coneType;
        ConeClassLikeLookupTag lookupTag;
        FirSession session = context.getSession();
        ConeSimpleKotlinType dispatchReceiverType = enclosingSuspendFunction.getDispatchReceiverType();
        ConeClassLikeType coneClassLikeType = dispatchReceiverType instanceof ConeClassLikeType ? (ConeClassLikeType) dispatchReceiverType : null;
        FirRegularClassSymbol firRegularClassSymbol = (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : LookupTagUtilsKt.toFirRegularClassSymbol(lookupTag, session);
        if (enclosingSuspendFunction.getReceiverParameter() == null) {
            enclosingSuspendFunction = null;
        }
        FirFunctionSymbol<? extends FirFunction> symbol = enclosingSuspendFunction != null ? enclosingSuspendFunction.getSymbol() : null;
        Triple<FirExpression, FirExpression, ConeKotlinType> tripleComputeReceiversInfo = computeReceiversInfo(expression, session, calledDeclarationSymbol);
        FirExpression firExpressionComponent1 = tripleComputeReceiversInfo.component1();
        FirExpression firExpressionComponent2 = tripleComputeReceiversInfo.component2();
        ConeKotlinType coneKotlinTypeComponent3 = tripleComputeReceiversInfo.component3();
        for (FirExpression firExpression : CollectionsKt.listOfNotNull((Object[]) new FirExpression[]{firExpressionComponent1, firExpressionComponent2})) {
            if (isRestrictSuspensionReceiver(FirTypeUtilsKt.getConeType(firExpression.getTypeRef()), session) && !sameInstanceOfReceiver(firExpression, firRegularClassSymbol) && !sameInstanceOfReceiver(firExpression, symbol)) {
                return false;
            }
        }
        if (symbol == null || (resolvedReceiverTypeRef = symbol.getResolvedReceiverTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(resolvedReceiverTypeRef)) == null || !isRestrictSuspensionReceiver(coneType, session)) {
            return true;
        }
        FirFunctionSymbol<? extends FirFunction> firFunctionSymbol = symbol;
        if (sameInstanceOfReceiver(firExpressionComponent1, firFunctionSymbol)) {
            return true;
        }
        return sameInstanceOfReceiver(firExpressionComponent2, firFunctionSymbol) && coneKotlinTypeComponent3 != null && isRestrictSuspensionReceiver(coneKotlinTypeComponent3, session);
    }

    private final boolean isRestrictSuspensionReceiver(ConeKotlinType coneKotlinType, FirSession firSession) {
        if (coneKotlinType instanceof ConeClassLikeType) {
            FirRegularClassSymbol firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) coneKotlinType, firSession, null, 2, null).getLookupTag(), firSession);
            if (firRegularClassSymbol == null) {
                return false;
            }
            if (FirAnnotationUtilsKt.getAnnotationByClassId(firRegularClassSymbol, StandardClassIds.Annotations.INSTANCE.getRestrictsSuspension(), firSession) != null) {
                return true;
            }
            List<ConeKotlinType> resolvedSuperTypes = firRegularClassSymbol.getResolvedSuperTypes();
            if (!(resolvedSuperTypes instanceof Collection) || !resolvedSuperTypes.isEmpty()) {
                Iterator<T> it = resolvedSuperTypes.iterator();
                while (it.hasNext()) {
                    if (INSTANCE.isRestrictSuspensionReceiver((ConeKotlinType) it.next(), firSession)) {
                        return true;
                    }
                }
            }
            return false;
        }
        if (!(coneKotlinType instanceof ConeTypeParameterType)) {
            return false;
        }
        List<FirResolvedTypeRef> resolvedBounds = ((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().getResolvedBounds();
        if (!(resolvedBounds instanceof Collection) || !resolvedBounds.isEmpty()) {
            Iterator<T> it2 = resolvedBounds.iterator();
            while (it2.hasNext()) {
                if (INSTANCE.isRestrictSuspensionReceiver(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it2.next()), firSession)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean sameInstanceOfReceiver(FirExpression useSiteReceiverExpression, FirBasedSymbol<?> declarationSiteReceiverOwnerSymbol) {
        if (declarationSiteReceiverOwnerSymbol == null || useSiteReceiverExpression == null || !(useSiteReceiverExpression instanceof FirThisReceiverExpression)) {
            return false;
        }
        return Intrinsics.areEqual(((FirThisReceiverExpression) useSiteReceiverExpression).getCalleeReference().getBoundSymbol(), declarationSiteReceiverOwnerSymbol);
    }

    private final Triple<FirExpression, FirExpression, ConeKotlinType> computeReceiversInfo(FirQualifiedAccessExpression firQualifiedAccessExpression, FirSession firSession, FirCallableSymbol<?> firCallableSymbol) {
        if ((firQualifiedAccessExpression instanceof FirImplicitInvokeCall) && !Intrinsics.areEqual(firQualifiedAccessExpression.getDispatchReceiver(), FirNoReceiverExpression.INSTANCE) && FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType(FirTypeUtilsKt.getConeType(firQualifiedAccessExpression.getDispatchReceiver().getTypeRef()), firSession)) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firQualifiedAccessExpression.getDispatchReceiver().getTypeRef());
            if (!CompilerConeAttributesKt.isExtensionFunctionType(coneType)) {
                return new Triple<>(null, null, null);
            }
            Object orNull = CollectionsKt.getOrNull(((FirImplicitInvokeCall) firQualifiedAccessExpression).getArgumentList().getArguments(), 0);
            Object orNull2 = ArraysKt.getOrNull(coneType.getTypeArguments(), 0);
            return new Triple<>(null, orNull, orNull2 instanceof ConeKotlinType ? (ConeKotlinType) orNull2 : null);
        }
        FirExpression dispatchReceiver = firQualifiedAccessExpression.getDispatchReceiver();
        if (dispatchReceiver instanceof FirNoReceiverExpression) {
            dispatchReceiver = null;
        }
        FirExpression extensionReceiver = firQualifiedAccessExpression.getExtensionReceiver();
        if (extensionReceiver instanceof FirNoReceiverExpression) {
            extensionReceiver = null;
        }
        FirResolvedTypeRef resolvedReceiverTypeRef = firCallableSymbol.getResolvedReceiverTypeRef();
        return new Triple<>(dispatchReceiver, extensionReceiver, resolvedReceiverTypeRef != null ? FirTypeUtilsKt.getConeType(resolvedReceiverTypeRef) : null);
    }
}
