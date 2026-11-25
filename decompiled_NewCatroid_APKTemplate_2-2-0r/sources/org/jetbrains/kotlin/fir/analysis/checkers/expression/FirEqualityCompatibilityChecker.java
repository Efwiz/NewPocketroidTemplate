package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KAnnotatedElement;
import kotlin.reflect.KFunction;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirEqualityCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u00010B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH\u0002J0\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0002J4\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u00172\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\b\u001a\u00020\tH\u0002J\"\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u001b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J4\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u00172\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u001e\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002J\u001c\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J \u0010 \u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"H\u0002J \u0010#\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"H\u0002J \u0010$\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH\u0002J!\u0010%\u001a\u00020\u0006*\u00020\r2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00060'H\u0082\bJ\u001c\u0010(\u001a\u00020\u0019*\u00020\u000f2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH\u0002JT\u0010*\u001a\u00020\u0006*\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010+\u001a\u00020\r2\u0006\u0010,\u001a\u00020-2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020\u00142\u0006\u0010/\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\tH\u0002¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirEqualityCompatibilityChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirEqualityOperatorCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkEqualityApplicability", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirEqualityCompatibilityChecker$Applicability;", "l", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/TypeInfo;", "r", "checkIdentityApplicability", "checkSenselessness", "lType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "rType", "getEnumInapplicabilityDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "forceWarning", "", "getGeneralInapplicabilityDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "", "getIdentityLessInapplicabilityDiagnostic", "getInapplicabilityFor", "getSourceLessInapplicabilityDiagnostic", "isIdentityComparedWithImplicitBoxing", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isPrimitiveWithNonPrimitiveSupertype", "shouldReportAsPerRules1", "ifInapplicable", "block", "Lkotlin/Function1;", "isSubtypeOf", "other", "reportInapplicabilityDiagnostic", "applicability", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "lUserType", "rUserType", "Applicability", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEqualityCompatibilityChecker extends FirExpressionChecker<FirEqualityOperatorCall> {
    public static final FirEqualityCompatibilityChecker INSTANCE = new FirEqualityCompatibilityChecker();

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FirEqualityCompatibilityChecker.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirEqualityCompatibilityChecker$Applicability;", "", "(Ljava/lang/String;I)V", "APPLICABLE", "GENERALLY_INAPPLICABLE", "INAPPLICABLE_AS_ENUMS", "INAPPLICABLE_AS_IDENTITY_LESS", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    enum Applicability {
        APPLICABLE,
        GENERALLY_INAPPLICABLE,
        INAPPLICABLE_AS_ENUMS,
        INAPPLICABLE_AS_IDENTITY_LESS;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Applicability> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: FirEqualityCompatibilityChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[FirOperation.values().length];
            try {
                iArr[FirOperation.EQ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirOperation.NOT_EQ.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirOperation.IDENTITY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FirOperation.NOT_IDENTITY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Applicability.values().length];
            try {
                iArr2[Applicability.APPLICABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private FirEqualityCompatibilityChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirEqualityOperatorCall expression, CheckerContext context, DiagnosticReporter reporter) {
        KAnnotatedElement kAnnotatedElement;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<FirExpression> arguments = expression.getArgumentList().getArguments();
        if (arguments.size() == 2) {
            ArgumentInfo argumentInfo = FirEqualityCompatibilityCheckerKt.toArgumentInfo(arguments.get(0), context);
            ArgumentInfo argumentInfo2 = FirEqualityCompatibilityCheckerKt.toArgumentInfo(arguments.get(1), context);
            checkSenselessness(argumentInfo.getSmartCastType(), argumentInfo2.getSmartCastType(), context, expression, reporter);
            int i = WhenMappings.$EnumSwitchMapping$0[expression.getOperation().ordinal()];
            if (i == 1 || i == 2) {
                kAnnotatedElement = (KFunction) new FirEqualityCompatibilityChecker$check$checkApplicability$1(this);
            } else if (i == 3 || i == 4) {
                kAnnotatedElement = (KFunction) new FirEqualityCompatibilityChecker$check$checkApplicability$2(this);
            } else {
                throw new IllegalStateException("Invalid operator of FirEqualityOperatorCall".toString());
            }
            Function3 function3 = (Function3) kAnnotatedElement;
            Applicability applicability = (Applicability) function3.invoke(argumentInfo.getOriginalTypeInfo(), argumentInfo2.getOriginalTypeInfo(), context);
            if (WhenMappings.$EnumSwitchMapping$1[applicability.ordinal()] != 1) {
                INSTANCE.reportInapplicabilityDiagnostic(reporter, expression, applicability, expression.getOperation(), (applicability != Applicability.INAPPLICABLE_AS_ENUMS && argumentInfo.getOriginalTypeInfo().getIsLiterallyTypeParameter() && argumentInfo2.getOriginalTypeInfo().getIsLiterallyTypeParameter()) && !context.get$languageVersionSettings().supportsFeature(LanguageFeature.ReportErrorsForComparisonOperators), argumentInfo.getOriginalTypeInfo(), argumentInfo2.getOriginalTypeInfo(), argumentInfo.getUserType(), argumentInfo2.getUserType(), context);
                return;
            } else {
                if ((argumentInfo.getArgument() instanceof FirSmartCastExpression) || (argumentInfo2.getArgument() instanceof FirSmartCastExpression)) {
                    Applicability applicability2 = (Applicability) function3.invoke(argumentInfo.getSmartCastTypeInfo(), argumentInfo2.getSmartCastTypeInfo(), context);
                    if (WhenMappings.$EnumSwitchMapping$1[applicability2.ordinal()] != 1) {
                        INSTANCE.reportInapplicabilityDiagnostic(reporter, expression, applicability2, expression.getOperation(), true, argumentInfo.getSmartCastTypeInfo(), argumentInfo2.getSmartCastTypeInfo(), argumentInfo.getUserType(), argumentInfo2.getUserType(), context);
                        return;
                    }
                    return;
                }
                return;
            }
        }
        throw new IllegalArgumentException("Equality operator call with non-2 arguments".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Applicability checkEqualityApplicability(TypeInfo l, TypeInfo r, CheckerContext context) {
        return (((l.getIsBuiltin() || r.getIsBuiltin()) || (FirEqualityCompatibilityCheckerKt.isIdentityLess(l) || FirEqualityCompatibilityCheckerKt.isIdentityLess(r))) && shouldReportAsPerRules1(l, r, context)) ? getInapplicabilityFor(l, r) : Applicability.APPLICABLE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Applicability checkIdentityApplicability(TypeInfo l, TypeInfo r, CheckerContext context) {
        boolean z = l.getIsBuiltin() || r.getIsBuiltin();
        boolean z2 = (ConeTypeUtilsKt.isNullable(l.getType()) && ConeTypeUtilsKt.isNullable(r.getType())) ? false : true;
        if (FirEqualityCompatibilityCheckerKt.isIdentityLess(l) || FirEqualityCompatibilityCheckerKt.isIdentityLess(r)) {
            return Applicability.INAPPLICABLE_AS_IDENTITY_LESS;
        }
        return (z && z2 && shouldReportAsPerRules1(l, r, context)) ? getInapplicabilityFor(l, r) : Applicability.APPLICABLE;
    }

    private final Applicability getInapplicabilityFor(TypeInfo l, TypeInfo r) {
        boolean z = FirEqualityCompatibilityCheckerKt.getEnforcesEmptyIntersection(l) || FirEqualityCompatibilityCheckerKt.getEnforcesEmptyIntersection(r);
        boolean z2 = l.getIsEnumClass() || r.getIsEnumClass();
        if (!z && z2) {
            return Applicability.INAPPLICABLE_AS_ENUMS;
        }
        return Applicability.GENERALLY_INAPPLICABLE;
    }

    private final boolean shouldReportAsPerRules1(TypeInfo l, TypeInfo r, CheckerContext context) {
        return (ConeBuiltinTypeUtilsKt.isNothingOrNullableNothing(l.getType()) || ConeBuiltinTypeUtilsKt.isNothingOrNullableNothing(r.getType()) || isSubtypeOf(l, r, context) || isSubtypeOf(r, l, context)) ? false : true;
    }

    private final boolean isSubtypeOf(TypeInfo typeInfo, TypeInfo typeInfo2, CheckerContext checkerContext) {
        return TypeUtilsKt.isSubtypeOf$default(typeInfo.getNotNullType(), typeInfo2.getNotNullType(), checkerContext.getSession(), false, 4, null);
    }

    private final KtDiagnosticFactory3<String, ConeKotlinType, ConeKotlinType> getGeneralInapplicabilityDiagnostic(boolean forceWarning) {
        if (forceWarning) {
            return FirErrors.INSTANCE.getEQUALITY_NOT_APPLICABLE_WARNING();
        }
        return FirErrors.INSTANCE.getEQUALITY_NOT_APPLICABLE();
    }

    private final KtDiagnosticFactory2<ConeKotlinType, ConeKotlinType> getIdentityLessInapplicabilityDiagnostic(TypeInfo l, TypeInfo r, boolean forceWarning, CheckerContext context) {
        boolean z = FirEqualityCompatibilityCheckerKt.isNotNullPrimitive(l) && FirEqualityCompatibilityCheckerKt.isNotNullPrimitive(r);
        boolean zAreEqual = Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(l.getType()), ConeTypeUtilsKt.getClassId(r.getType()));
        boolean z2 = (l.getIsPrimitive() || r.getIsPrimitive()) && (isSubtypeOf(l, r, context) || isSubtypeOf(r, l, context)) && !context.get$languageVersionSettings().supportsFeature(LanguageFeature.ReportErrorsForComparisonOperators);
        if (zAreEqual && z) {
            return FirErrors.INSTANCE.getDEPRECATED_IDENTITY_EQUALS();
        }
        if (isIdentityComparedWithImplicitBoxing(l, r, context.getSession())) {
            return FirErrors.INSTANCE.getIMPLICIT_BOXING_IN_IDENTITY_EQUALS();
        }
        if (forceWarning || z2) {
            return FirErrors.INSTANCE.getFORBIDDEN_IDENTITY_EQUALS_WARNING();
        }
        return FirErrors.INSTANCE.getFORBIDDEN_IDENTITY_EQUALS();
    }

    private final boolean isIdentityComparedWithImplicitBoxing(TypeInfo l, TypeInfo r, FirSession session) {
        return isPrimitiveWithNonPrimitiveSupertype(l, r, session) || isPrimitiveWithNonPrimitiveSupertype(r, l, session);
    }

    private final boolean isPrimitiveWithNonPrimitiveSupertype(TypeInfo l, TypeInfo r, FirSession session) {
        return FirEqualityCompatibilityCheckerKt.isNotNullPrimitive(l) && !FirEqualityCompatibilityCheckerKt.isNotNullPrimitive(r) && TypeUtilsKt.isSubtypeOf$default(l.getType(), r.getType(), session, false, 4, null);
    }

    private final KtDiagnosticFactory2<ConeKotlinType, ConeKotlinType> getSourceLessInapplicabilityDiagnostic(boolean forceWarning) {
        if (forceWarning) {
            return FirErrors.INSTANCE.getINCOMPATIBLE_TYPES_WARNING();
        }
        return FirErrors.INSTANCE.getINCOMPATIBLE_TYPES();
    }

    private final KtDiagnosticFactory2<ConeKotlinType, ConeKotlinType> getEnumInapplicabilityDiagnostic(TypeInfo l, TypeInfo r, boolean forceWarning, CheckerContext context) {
        boolean zSupportsFeature = context.get$languageVersionSettings().supportsFeature(LanguageFeature.ProhibitComparisonOfIncompatibleEnums);
        boolean z = ((FirEqualityCompatibilityCheckerKt.isNullableEnum(l) && FirEqualityCompatibilityCheckerKt.isNullableEnum(r)) || ((l.getType() instanceof ConeIntersectionType) || (r.getType() instanceof ConeIntersectionType))) && !context.get$languageVersionSettings().supportsFeature(LanguageFeature.ReportErrorsForComparisonOperators);
        if (forceWarning || !zSupportsFeature || z) {
            return FirErrors.INSTANCE.getINCOMPATIBLE_ENUM_COMPARISON();
        }
        return FirErrors.INSTANCE.getINCOMPATIBLE_ENUM_COMPARISON_ERROR();
    }

    private final void reportInapplicabilityDiagnostic(DiagnosticReporter diagnosticReporter, FirEqualityOperatorCall firEqualityOperatorCall, Applicability applicability, FirOperation firOperation, boolean z, TypeInfo typeInfo, TypeInfo typeInfo2, ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2, CheckerContext checkerContext) {
        if (applicability == Applicability.INAPPLICABLE_AS_IDENTITY_LESS) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firEqualityOperatorCall.getSource(), getIdentityLessInapplicabilityDiagnostic(typeInfo, typeInfo2, z, checkerContext), coneKotlinType, coneKotlinType2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return;
        }
        if (applicability == Applicability.INAPPLICABLE_AS_ENUMS) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firEqualityOperatorCall.getSource(), getEnumInapplicabilityDiagnostic(typeInfo, typeInfo2, z, checkerContext), coneKotlinType, coneKotlinType2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return;
        }
        KtSourceElement source = firEqualityOperatorCall.getSource();
        if (!((source != null ? source.getKind() : null) instanceof KtRealSourceElementKind)) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firEqualityOperatorCall.getSource(), getSourceLessInapplicabilityDiagnostic(z), coneKotlinType, coneKotlinType2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        } else {
            if (applicability == Applicability.GENERALLY_INAPPLICABLE) {
                KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, (AbstractKtSourceElement) firEqualityOperatorCall.getSource(), (KtDiagnosticFactory3<String, ConeKotlinType, ConeKotlinType>) ((KtDiagnosticFactory3<Object, Object, Object>) getGeneralInapplicabilityDiagnostic(z)), firOperation.getOperator(), coneKotlinType, coneKotlinType2, (DiagnosticContext) checkerContext, (64 & 64) != 0 ? null : null);
                return;
            }
            throw new IllegalStateException("Shouldn't be here".toString());
        }
    }

    private final void checkSenselessness(ConeKotlinType lType, ConeKotlinType rType, CheckerContext context, FirEqualityOperatorCall expression, DiagnosticReporter reporter) {
        ConeKotlinType coneKotlinType;
        if (ConeBuiltinTypeUtilsKt.isNullableNothing(rType)) {
            coneKotlinType = lType;
        } else if (!ConeBuiltinTypeUtilsKt.isNullableNothing(lType)) {
            return;
        } else {
            coneKotlinType = rType;
        }
        if (coneKotlinType instanceof ConeErrorType) {
            return;
        }
        boolean z = false;
        boolean z2 = expression.getOperation() == FirOperation.EQ || expression.getOperation() == FirOperation.IDENTITY;
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(context.getSession());
        if (ConeBuiltinTypeUtilsKt.isNullableNothing(coneKotlinType)) {
            z = z2;
        } else {
            if (typeContext.isNullableType(coneKotlinType)) {
                return;
            }
            if (!z2) {
                z = true;
            }
        }
        KtSourceElement source = expression.getSource();
        if (!Intrinsics.areEqual(source != null ? source.getElementType() : null, KtNodeTypes.BINARY_EXPRESSION) && coneKotlinType == lType && !z) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getSENSELESS_NULL_IN_WHEN(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        } else {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getSENSELESS_COMPARISON(), Boolean.valueOf(z), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
