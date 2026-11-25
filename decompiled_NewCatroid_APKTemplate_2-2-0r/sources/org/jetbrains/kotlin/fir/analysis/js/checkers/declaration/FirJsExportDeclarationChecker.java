package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: FirJsExportDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J \u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J$\u0010\u0017\u001a\u00020\u0006*\u00020\r2\u0006\u0010\u0018\u001a\u00020\u00192\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\u001bH\u0002J$\u0010\u001c\u001a\u00020\u0006*\u00020\r2\u0006\u0010\u0018\u001a\u00020\u00192\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\u001bH\u0002R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\bR\u0018\u0010\t\u001a\u00020\u0006*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0018\u0010\f\u001a\u00020\u0006*\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000e¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsExportDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "isInlineWithReified", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Z", "isInsideInterface", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)Z", "isPrimitiveExportableConeKotlinType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "check", "", "declaration", "context", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "validateDeclarationOnConsumableName", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "isExportable", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "currentlyProcessed", "", "isExportableReturn", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsExportDeclarationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJsExportDeclarationChecker INSTANCE = new FirJsExportDeclarationChecker();

    /* JADX INFO: compiled from: FirJsExportDeclarationChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirJsExportDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (FirJsHelpersKt.isExportedObject(declaration.getSymbol(), context) && (declaration instanceof FirMemberDeclaration)) {
            boolean zHasAnnotation = FirAnnotationUtilsKt.hasAnnotation(declaration, JsStandardClassIds.Annotations.JsName, context.getSession());
            FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) declaration;
            if (firMemberDeclaration.getStatus().isExpect()) {
                check$reportWrongExportedDeclaration(reporter, declaration, context, "expect");
            }
            validateDeclarationOnConsumableName(firMemberDeclaration, context, reporter);
            if (firMemberDeclaration instanceof FirFunction) {
                Iterator<FirTypeParameterRef> it = firMemberDeclaration.getTypeParameters().iterator();
                while (it.hasNext()) {
                    check$checkTypeParameter(context, reporter, it.next());
                }
                if (isInlineWithReified((FirCallableDeclaration) declaration)) {
                    check$reportWrongExportedDeclaration(reporter, declaration, context, "inline function with reified type parameters");
                    return;
                }
                if (firMemberDeclaration.getStatus().isSuspend()) {
                    check$reportWrongExportedDeclaration(reporter, declaration, context, "suspend function");
                    return;
                }
                if ((declaration instanceof FirConstructor) && !((FirConstructor) declaration).getIsPrimary() && !zHasAnnotation) {
                    check$reportWrongExportedDeclaration(reporter, declaration, context, "secondary constructor without @JsName");
                }
                if (declaration instanceof FirPropertyAccessor) {
                    return;
                }
                FirFunction firFunction = (FirFunction) declaration;
                Iterator<FirValueParameter> it2 = firFunction.getValueParameters().iterator();
                while (it2.hasNext()) {
                    check$checkValueParameter(context, reporter, it2.next());
                }
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firFunction.getReturnTypeRef());
                if (isExportable$default(this, coneType, context.getSession(), null, 2, null)) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNON_EXPORTABLE_TYPE(), "return type", coneType, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                return;
            }
            String strConcat = null;
            strConcat = null;
            if (firMemberDeclaration instanceof FirProperty) {
                KtSourceElement source = declaration.getSource();
                if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE)) {
                    return;
                }
                if (FirDeclarationUtilKt.isExtension((FirCallableDeclaration) declaration)) {
                    check$reportWrongExportedDeclaration(reporter, declaration, context, "extension property");
                    return;
                }
                ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(((FirProperty) declaration).getReturnTypeRef());
                if (isExportable$default(this, coneType2, context.getSession(), null, 2, null)) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNON_EXPORTABLE_TYPE(), "return type", coneType2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                return;
            }
            if (firMemberDeclaration instanceof FirClass) {
                Iterator<FirTypeParameterRef> it3 = firMemberDeclaration.getTypeParameters().iterator();
                while (it3.hasNext()) {
                    check$checkTypeParameter(context, reporter, it3.next());
                }
                int i = WhenMappings.$EnumSwitchMapping$0[((FirClass) declaration).getClassKind().ordinal()];
                if (i == 1) {
                    strConcat = "annotation class";
                } else if (i == 2) {
                    if (isInsideInterface(context)) {
                        strConcat = "nested class inside exported interface";
                    } else if (firMemberDeclaration.getStatus().isInline()) {
                        strConcat = "value class";
                    }
                } else if (isInsideInterface(context)) {
                    strConcat = (firMemberDeclaration.getStatus().isCompanion() ? "companion object" : "nested/inner declaration").concat(" inside exported interface");
                }
                if (strConcat != null) {
                    check$reportWrongExportedDeclaration(reporter, declaration, context, strConcat);
                }
            }
        }
    }

    private static final void check$checkTypeParameter(CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, FirTypeParameterRef firTypeParameterRef) {
        for (FirResolvedTypeRef firResolvedTypeRef : firTypeParameterRef.getSymbol().getResolvedBounds()) {
            if (!isExportable$default(INSTANCE, firResolvedTypeRef.getType(), checkerContext.getSession(), null, 2, null)) {
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firTypeParameterRef.getSource(), FirJsErrors.INSTANCE.getNON_EXPORTABLE_TYPE(), "upper bound", firResolvedTypeRef.getType(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
        }
    }

    private static final void check$checkValueParameter(CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, FirValueParameter firValueParameter) {
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
        if (isExportable$default(INSTANCE, coneType, checkerContext.getSession(), null, 2, null)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firValueParameter.getSource(), FirJsErrors.INSTANCE.getNON_EXPORTABLE_TYPE(), "parameter", coneType, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private static final void check$reportWrongExportedDeclaration(DiagnosticReporter diagnosticReporter, FirDeclaration firDeclaration, CheckerContext checkerContext, String str) {
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firDeclaration.getSource(), FirJsErrors.INSTANCE.getWRONG_EXPORTED_DECLARATION(), str, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final boolean isInsideInterface(CheckerContext checkerContext) {
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) checkerContext.getContainingDeclarations());
        FirClass firClass = objLastOrNull instanceof FirClass ? (FirClass) objLastOrNull : null;
        return firClass != null && firClass.getClassKind() == ClassKind.INTERFACE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isInlineWithReified(FirCallableDeclaration firCallableDeclaration) {
        if (firCallableDeclaration instanceof FirPropertyAccessor) {
            return isInlineWithReified((FirCallableDeclaration) ((FirPropertyAccessor) firCallableDeclaration).getPropertySymbol().getFir());
        }
        List<FirTypeParameterRef> typeParameters = firCallableDeclaration.getTypeParameters();
        if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                if (((FirTypeParameterRef) it.next()).getSymbol().isReified()) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isExportableReturn(ConeKotlinType coneKotlinType, FirSession firSession, Set<ConeKotlinType> set) {
        return ConeBuiltinTypeUtilsKt.isUnit(coneKotlinType) || isExportable(coneKotlinType, firSession, set);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ boolean isExportable$default(FirJsExportDeclarationChecker firJsExportDeclarationChecker, ConeKotlinType coneKotlinType, FirSession firSession, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            set = new LinkedHashSet();
        }
        return firJsExportDeclarationChecker.isExportable(coneKotlinType, firSession, set);
    }

    private final boolean isExportable(ConeKotlinType coneKotlinType, FirSession firSession, Set<ConeKotlinType> set) {
        ConeTypeProjection coneTypeProjection;
        ConeKotlinType type;
        if (!set.add(coneKotlinType)) {
            return true;
        }
        set.add(coneKotlinType);
        for (ConeTypeProjection coneTypeProjection2 : coneKotlinType.getTypeArguments()) {
            ConeKotlinType type2 = ConeTypeProjectionKt.getType(coneTypeProjection2);
            if (!(type2 != null && INSTANCE.isExportable(type2, firSession, set))) {
                set.remove(coneKotlinType);
                return false;
            }
        }
        set.remove(coneKotlinType);
        if (FunctionalTypeUtilsKt.isBasicFunctionType(coneKotlinType, firSession) && (coneTypeProjection = (ConeTypeProjection) ArraysKt.lastOrNull(coneKotlinType.getTypeArguments())) != null && (type = ConeTypeProjectionKt.getType(coneTypeProjection)) != null) {
            isExportableReturn(type, firSession, set);
        }
        ConeKotlinType coneKotlinTypeWithNullability$default = TypeUtilsKt.withNullability$default(coneKotlinType, ConeNullability.NOT_NULL, TypeComponentsKt.getTypeContext(firSession), null, false, 12, null);
        boolean z = ConeBuiltinTypeUtilsKt.isAny(coneKotlinTypeWithNullability$default) || ConeBuiltinTypeUtilsKt.isNullableAny(coneKotlinTypeWithNullability$default) || (coneKotlinTypeWithNullability$default instanceof ConeDynamicType) || isPrimitiveExportableConeKotlinType(coneKotlinType);
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(coneKotlinType, firSession);
        if (z) {
            return true;
        }
        if (!((symbol != null ? symbol.getFir() : null) instanceof FirMemberDeclaration)) {
            if (ConeBuiltinTypeUtilsKt.isEnum(coneKotlinType)) {
                return true;
            }
            if (symbol != null && FirJsHelpersKt.isEffectivelyExternal(symbol, firSession)) {
                return true;
            }
            if (symbol != null && FirJsHelpersKt.isExportedObject(symbol, firSession)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isPrimitiveExportableConeKotlinType(ConeKotlinType coneKotlinType) {
        return (coneKotlinType instanceof ConeTypeParameterType) || ConeBuiltinTypeUtilsKt.isBoolean(coneKotlinType) || ConeBuiltinTypeUtilsKt.isThrowableOrNullableThrowable(coneKotlinType) || ConeBuiltinTypeUtilsKt.isString(coneKotlinType) || (ConeBuiltinTypeUtilsKt.isPrimitiveNumberOrNullableType(coneKotlinType) && !ConeBuiltinTypeUtilsKt.isLong(coneKotlinType)) || ConeBuiltinTypeUtilsKt.isNothingOrNullableNothing(coneKotlinType) || ConeBuiltinTypeUtilsKt.isArrayType(coneKotlinType);
    }

    private final void validateDeclarationOnConsumableName(FirMemberDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        if (!FirHelpersKt.isTopLevel(context) || FirDeclarationUtilKt.getNameOrSpecialName(declaration).isSpecial()) {
            return;
        }
        FirExpression annotationFirstArgument = FirHelpersKt.getAnnotationFirstArgument(declaration.getSymbol(), JsStandardClassIds.Annotations.JsName, context.getSession());
        if (annotationFirstArgument == null || (source = annotationFirstArgument.getSource()) == null) {
            source = declaration.getSource();
        }
        FirConstExpression firConstExpression = annotationFirstArgument instanceof FirConstExpression ? (FirConstExpression) annotationFirstArgument : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        String strAsString = value instanceof String ? (String) value : null;
        if (strAsString == null) {
            strAsString = FirDeclarationUtilKt.getNameOrSpecialName(declaration).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        String str = strAsString;
        if (IdentifierPolicyKt.getSPECIAL_KEYWORDS().contains(str)) {
            return;
        }
        if (IdentifierPolicyKt.getRESERVED_KEYWORDS().contains(str) || !Intrinsics.areEqual(FirJsHelpersKt.sanitizeName(str), str)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJsErrors.INSTANCE.getNON_CONSUMABLE_EXPORTED_IDENTIFIER(), str, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
