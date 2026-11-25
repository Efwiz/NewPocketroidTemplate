package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import com.reandroid.apk.ApkUtil;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryForDeprecation3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirExposedVisibilityDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J,\u0010\u0010\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00172\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J.\u0010\u001b\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001d\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u001c*\u00020\u001f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u001eH\u0002¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirExposedVisibilityDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "checkFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "checkMemberReceiver", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "memberDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "checkParameterBounds", "checkProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "checkSupertypes", "checkTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "findVisibilityExposure", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ApkUtil.DEF_MODULE_NAME, "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExposedVisibilityDeclarationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirExposedVisibilityDeclarationChecker INSTANCE = new FirExposedVisibilityDeclarationChecker();

    /* JADX INFO: compiled from: FirExposedVisibilityDeclarationChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EffectiveVisibility.Permissiveness.values().length];
            try {
                iArr[EffectiveVisibility.Permissiveness.LESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EffectiveVisibility.Permissiveness.UNKNOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirExposedVisibilityDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirAnonymousFunction) {
            return;
        }
        if (declaration instanceof FirTypeAlias) {
            checkTypeAlias((FirTypeAlias) declaration, reporter, context);
            return;
        }
        if (declaration instanceof FirProperty) {
            checkProperty((FirProperty) declaration, reporter, context);
        } else if (declaration instanceof FirFunction) {
            checkFunction((FirFunction) declaration, reporter, context);
        } else if (declaration instanceof FirRegularClass) {
            checkClass((FirRegularClass) declaration, reporter, context);
        }
    }

    private final void checkClass(FirRegularClass declaration, DiagnosticReporter reporter, CheckerContext context) {
        checkSupertypes(declaration, reporter, context);
        checkParameterBounds(declaration, reporter, context);
    }

    private final void checkSupertypes(FirRegularClass declaration, DiagnosticReporter reporter, CheckerContext context) {
        EffectiveVisibility.Local effectiveVisibility;
        FirRegularClassSymbol regularClassSymbol;
        Pair<FirBasedSymbol<?>, EffectiveVisibility> pairFindVisibilityExposure;
        FirDeclarationStatus status = declaration.getStatus();
        FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
        if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
            effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
        }
        if (Intrinsics.areEqual(effectiveVisibility, EffectiveVisibility.Local.INSTANCE)) {
            return;
        }
        List<FirTypeRef> superTypeRefs = declaration.getSuperTypeRefs();
        boolean z = declaration.getClassKind() == ClassKind.INTERFACE;
        for (FirTypeRef firTypeRef : superTypeRefs) {
            FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            if (coneClassLikeType != null && (regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneClassLikeType, context.getSession())) != null) {
                if ((regularClassSymbol.getClassKind() == ClassKind.INTERFACE) == z && (pairFindVisibilityExposure = findVisibilityExposure(coneClassLikeType, context, effectiveVisibility)) != null) {
                    FirBasedSymbol<?> firBasedSymbolComponent1 = pairFindVisibilityExposure.component1();
                    EffectiveVisibility effectiveVisibilityComponent2 = pairFindVisibilityExposure.component2();
                    KtSourceElement source = firTypeRef.getSource();
                    if (source == null) {
                        source = declaration.getSource();
                    }
                    KtSourceElement ktSourceElement = source;
                    FirErrors firErrors = FirErrors.INSTANCE;
                    KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) ktSourceElement, (KtDiagnosticFactory3<EffectiveVisibility, FirBasedSymbol<?>, EffectiveVisibility>) ((KtDiagnosticFactory3<Object, Object, Object>) (z ? firErrors.getEXPOSED_SUPER_INTERFACE() : firErrors.getEXPOSED_SUPER_CLASS())), effectiveVisibility, firBasedSymbolComponent1, effectiveVisibilityComponent2, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
                    z = z;
                }
            }
        }
    }

    private final void checkParameterBounds(FirRegularClass declaration, DiagnosticReporter reporter, CheckerContext context) {
        EffectiveVisibility.Local effectiveVisibility;
        FirDeclarationStatus status = declaration.getStatus();
        FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
        if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
            effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
        }
        if (Intrinsics.areEqual(effectiveVisibility, EffectiveVisibility.Local.INSTANCE)) {
            return;
        }
        Iterator<FirTypeParameterRef> it = declaration.getTypeParameters().iterator();
        while (it.hasNext()) {
            for (FirResolvedTypeRef firResolvedTypeRef : it.next().getSymbol().getResolvedBounds()) {
                Pair<FirBasedSymbol<?>, EffectiveVisibility> pairFindVisibilityExposure = findVisibilityExposure(FirTypeUtilsKt.getConeType(firResolvedTypeRef), context, effectiveVisibility);
                if (pairFindVisibilityExposure != null) {
                    KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) firResolvedTypeRef.getSource(), (KtDiagnosticFactory3<EffectiveVisibility, FirBasedSymbol<?>, EffectiveVisibility>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getEXPOSED_TYPE_PARAMETER_BOUND()), effectiveVisibility, pairFindVisibilityExposure.component1(), pairFindVisibilityExposure.component2(), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
                }
            }
        }
    }

    private final void checkTypeAlias(FirTypeAlias declaration, DiagnosticReporter reporter, CheckerContext context) {
        EffectiveVisibility.Local effectiveVisibility;
        Pair<FirBasedSymbol<?>, EffectiveVisibility> pairFindVisibilityExposure;
        ConeClassLikeType expandedConeType = FirDeclarationUtilKt.getExpandedConeType(declaration);
        FirDeclarationStatus status = declaration.getStatus();
        FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
        if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
            effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
        }
        EffectiveVisibility effectiveVisibility2 = effectiveVisibility;
        if (Intrinsics.areEqual(effectiveVisibility2, EffectiveVisibility.Local.INSTANCE) || expandedConeType == null || (pairFindVisibilityExposure = findVisibilityExposure(expandedConeType, context, effectiveVisibility2)) == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) declaration.getSource(), (KtDiagnosticFactory3<EffectiveVisibility, FirBasedSymbol<?>, EffectiveVisibility>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getEXPOSED_TYPEALIAS_EXPANDED_TYPE()), effectiveVisibility2, pairFindVisibilityExposure.component1(), pairFindVisibilityExposure.component2(), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
    }

    private final void checkFunction(FirFunction declaration, DiagnosticReporter reporter, CheckerContext context) {
        EffectiveVisibility.Local effectiveVisibility;
        Pair<FirBasedSymbol<?>, EffectiveVisibility> pairFindVisibilityExposure;
        Pair<FirBasedSymbol<?>, EffectiveVisibility> pairFindVisibilityExposure2;
        KtSourceElement source = declaration.getSource();
        if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) {
            return;
        }
        Intrinsics.checkNotNull(declaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration");
        FirFunction firFunction = declaration;
        FirDeclarationStatus status = firFunction.getStatus();
        FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
        if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
            effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
        }
        boolean z = declaration instanceof FirConstructor;
        if (z && firFunction.getStatus().isFromSealedClass()) {
            effectiveVisibility = EffectiveVisibility.PrivateInClass.INSTANCE;
        }
        if (Intrinsics.areEqual(effectiveVisibility, EffectiveVisibility.Local.INSTANCE)) {
            return;
        }
        if (!z && !(declaration instanceof FirPropertyAccessor) && (pairFindVisibilityExposure2 = findVisibilityExposure(FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef()), context, effectiveVisibility)) != null) {
            KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) declaration.getSource(), (KtDiagnosticFactory3<EffectiveVisibility, FirBasedSymbol<?>, EffectiveVisibility>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getEXPOSED_FUNCTION_RETURN_TYPE()), effectiveVisibility, pairFindVisibilityExposure2.component1(), pairFindVisibilityExposure2.component2(), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
        }
        if (!(declaration instanceof FirPropertyAccessor)) {
            int i = 0;
            for (Object obj : declaration.getValueParameters()) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                FirValueParameter firValueParameter = (FirValueParameter) obj;
                if (i < declaration.getValueParameters().size() && (pairFindVisibilityExposure = INSTANCE.findVisibilityExposure(FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()), context, effectiveVisibility)) != null) {
                    KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) firValueParameter.getSource(), (KtDiagnosticFactory3<EffectiveVisibility, FirBasedSymbol<?>, EffectiveVisibility>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getEXPOSED_PARAMETER_TYPE()), effectiveVisibility, pairFindVisibilityExposure.component1(), pairFindVisibilityExposure.component2(), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
                }
                i = i2;
            }
        }
        FirReceiverParameter receiverParameter = declaration.getReceiverParameter();
        checkMemberReceiver(receiverParameter != null ? receiverParameter.getTypeRef() : null, declaration instanceof FirCallableDeclaration ? declaration : null, reporter, context);
    }

    private final void checkProperty(FirProperty declaration, DiagnosticReporter reporter, CheckerContext context) {
        EffectiveVisibility.Local effectiveVisibility;
        if (declaration.getIsLocal()) {
            return;
        }
        FirDeclarationStatus status = declaration.getStatus();
        FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
        if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
            effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
        }
        EffectiveVisibility effectiveVisibility2 = effectiveVisibility;
        if (Intrinsics.areEqual(effectiveVisibility2, EffectiveVisibility.Local.INSTANCE)) {
            return;
        }
        Pair<FirBasedSymbol<?>, EffectiveVisibility> pairFindVisibilityExposure = findVisibilityExposure(FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef()), context, effectiveVisibility2);
        if (pairFindVisibilityExposure != null) {
            FirBasedSymbol<?> firBasedSymbolComponent1 = pairFindVisibilityExposure.component1();
            EffectiveVisibility effectiveVisibilityComponent2 = pairFindVisibilityExposure.component2();
            if (!Intrinsics.areEqual((Object) DeclarationAttributesKt.getFromPrimaryConstructor(declaration), (Object) true)) {
                KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) declaration.getSource(), (KtDiagnosticFactory3<EffectiveVisibility, FirBasedSymbol<?>, EffectiveVisibility>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getEXPOSED_PROPERTY_TYPE()), effectiveVisibility2, firBasedSymbolComponent1, effectiveVisibilityComponent2, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
            } else {
                KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) declaration.getSource(), (KtDiagnosticFactoryForDeprecation3<EffectiveVisibility, FirBasedSymbol<?>, EffectiveVisibility>) ((KtDiagnosticFactoryForDeprecation3<Object, Object, Object>) FirErrors.INSTANCE.getEXPOSED_PROPERTY_TYPE_IN_CONSTRUCTOR()), effectiveVisibility2, firBasedSymbolComponent1, effectiveVisibilityComponent2, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
            }
        }
        FirReceiverParameter receiverParameter = declaration.getReceiverParameter();
        checkMemberReceiver(receiverParameter != null ? receiverParameter.getTypeRef() : null, declaration, reporter, context);
    }

    private final void checkMemberReceiver(FirTypeRef typeRef, FirCallableDeclaration memberDeclaration, DiagnosticReporter reporter, CheckerContext context) {
        EffectiveVisibility.Local effectiveVisibility;
        Pair<FirBasedSymbol<?>, EffectiveVisibility> pairFindVisibilityExposure;
        if (typeRef == null || memberDeclaration == null) {
            return;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(typeRef);
        FirDeclarationStatus status = memberDeclaration.getStatus();
        FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
        if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
            effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
        }
        EffectiveVisibility effectiveVisibility2 = effectiveVisibility;
        if (Intrinsics.areEqual(effectiveVisibility2, EffectiveVisibility.Local.INSTANCE) || (pairFindVisibilityExposure = findVisibilityExposure(coneType, context, effectiveVisibility2)) == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) typeRef.getSource(), (KtDiagnosticFactory3<EffectiveVisibility, FirBasedSymbol<?>, EffectiveVisibility>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getEXPOSED_RECEIVER_TYPE()), effectiveVisibility2, pairFindVisibilityExposure.component1(), pairFindVisibilityExposure.component2(), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
    }

    private final Pair<FirBasedSymbol<?>, EffectiveVisibility> findVisibilityExposure(ConeKotlinType coneKotlinType, CheckerContext checkerContext, EffectiveVisibility effectiveVisibility) {
        FirClassLikeSymbol<?> symbol;
        Pair<FirBasedSymbol<?>, EffectiveVisibility> pairFindVisibilityExposure;
        ConeClassLikeType coneClassLikeType = coneKotlinType instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinType : null;
        if (coneClassLikeType == null || (symbol = LookupTagUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, checkerContext.getSession(), null, 2, null).getLookupTag(), checkerContext.getSession())) == null) {
            return null;
        }
        EffectiveVisibility effectiveVisibility2 = ((symbol instanceof FirRegularClassSymbol) || (symbol instanceof FirTypeAliasSymbol)) ? symbol.getResolvedStatus().getEffectiveVisibility() : null;
        if (effectiveVisibility2 != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[effectiveVisibility2.relation(effectiveVisibility, TypeComponentsKt.getTypeContext(checkerContext.getSession())).ordinal()];
            if (i == 1 || i == 2) {
                return TuplesKt.to(symbol, effectiveVisibility2);
            }
        }
        for (ConeTypeProjection coneTypeProjection : coneClassLikeType.getTypeArguments()) {
            ConeClassLikeType coneClassLikeType2 = coneTypeProjection instanceof ConeClassLikeType ? (ConeClassLikeType) coneTypeProjection : null;
            if (coneClassLikeType2 != null && (pairFindVisibilityExposure = findVisibilityExposure(coneClassLikeType2, checkerContext, effectiveVisibility)) != null) {
                return pairFindVisibilityExposure;
            }
        }
        return null;
    }
}
