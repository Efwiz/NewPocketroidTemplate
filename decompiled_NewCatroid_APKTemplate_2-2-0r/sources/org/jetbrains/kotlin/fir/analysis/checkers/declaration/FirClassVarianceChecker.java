package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory4;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirTypeRefSource;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.types.EnrichedProjectionKind;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: FirClassVarianceChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J.\u0010\u000f\u001a\u00020\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002JP\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u00172\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0002J4\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassVarianceChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkCallableDeclaration", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "checkTypeParameters", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "checkVarianceConflict", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "containingType", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "isInAbbreviation", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirClassVarianceChecker extends FirDeclarationChecker<FirClass> {
    public static final FirClassVarianceChecker INSTANCE = new FirClassVarianceChecker();

    /* JADX INFO: compiled from: FirClassVarianceChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ProjectionKind.values().length];
            try {
                iArr[ProjectionKind.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProjectionKind.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProjectionKind.INVARIANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[EnrichedProjectionKind.values().length];
            try {
                iArr2[EnrichedProjectionKind.OUT.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[EnrichedProjectionKind.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[EnrichedProjectionKind.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[EnrichedProjectionKind.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private FirClassVarianceChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkTypeParameters(declaration.getTypeParameters(), Variance.OUT_VARIANCE, context, reporter);
        Iterator<FirTypeRef> it = declaration.getSuperTypeRefs().iterator();
        while (it.hasNext()) {
            checkVarianceConflict$default(this, it.next(), Variance.OUT_VARIANCE, context, reporter, null, 16, null);
        }
        for (FirAnnotationContainer firAnnotationContainer : declaration.getDeclarations()) {
            if (!(firAnnotationContainer instanceof FirMemberDeclaration) || !Visibilities.INSTANCE.isPrivate(((FirMemberDeclaration) firAnnotationContainer).getStatus().getVisibility())) {
                if ((firAnnotationContainer instanceof FirTypeParameterRefsOwner) && !(firAnnotationContainer instanceof FirClass)) {
                    checkTypeParameters(((FirTypeParameterRefsOwner) firAnnotationContainer).getTypeParameters(), Variance.IN_VARIANCE, context, reporter);
                }
                if (firAnnotationContainer instanceof FirCallableDeclaration) {
                    checkCallableDeclaration((FirCallableDeclaration) firAnnotationContainer, context, reporter);
                }
            }
        }
    }

    private final void checkCallableDeclaration(FirCallableDeclaration member, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source = member.getSource();
        if ((member instanceof FirSimpleFunction) && source != null && !(source.getKind() instanceof KtFakeSourceElementKind)) {
            Iterator<FirValueParameter> it = ((FirSimpleFunction) member).getValueParameters().iterator();
            while (it.hasNext()) {
                checkVarianceConflict$default(this, it.next().getReturnTypeRef(), Variance.IN_VARIANCE, context, reporter, null, 16, null);
            }
        }
        Variance variance = ((member instanceof FirProperty) && ((FirProperty) member).getIsVar()) ? Variance.INVARIANT : Variance.OUT_VARIANCE;
        KtSourceElement source2 = member.getReturnTypeRef().getSource();
        checkVarianceConflict(member.getReturnTypeRef(), variance, context, reporter, (source2 == null || !(source == null || !(source2.getKind() instanceof KtFakeSourceElementKind) || (source.getKind() instanceof KtFakeSourceElementKind))) ? source : source2);
        FirReceiverParameter receiverParameter = member.getReceiverParameter();
        FirTypeRef typeRef = receiverParameter != null ? receiverParameter.getTypeRef() : null;
        if (typeRef != null) {
            checkVarianceConflict$default(this, typeRef, Variance.IN_VARIANCE, context, reporter, null, 16, null);
        }
    }

    private final void checkTypeParameters(List<? extends FirTypeParameterRef> typeParameters, Variance variance, CheckerContext context, DiagnosticReporter reporter) {
        for (FirTypeParameterRef firTypeParameterRef : typeParameters) {
            if (firTypeParameterRef instanceof FirTypeParameter) {
                Iterator<FirResolvedTypeRef> it = firTypeParameterRef.getSymbol().getResolvedBounds().iterator();
                while (it.hasNext()) {
                    checkVarianceConflict$default(this, it.next(), variance, context, reporter, null, 16, null);
                }
            }
        }
    }

    static /* synthetic */ void checkVarianceConflict$default(FirClassVarianceChecker firClassVarianceChecker, FirTypeRef firTypeRef, Variance variance, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, int i, Object obj) {
        if ((i & 16) != 0) {
            ktSourceElement = null;
        }
        firClassVarianceChecker.checkVarianceConflict(firTypeRef, variance, checkerContext, diagnosticReporter, ktSourceElement);
    }

    private final void checkVarianceConflict(FirTypeRef type, Variance variance, CheckerContext context, DiagnosticReporter reporter, KtSourceElement source) {
        checkVarianceConflict$default(this, FirTypeUtilsKt.getConeType(type), variance, type, FirTypeUtilsKt.getConeType(type), context, reporter, source, false, 128, null);
    }

    static /* synthetic */ void checkVarianceConflict$default(FirClassVarianceChecker firClassVarianceChecker, ConeKotlinType coneKotlinType, Variance variance, FirTypeRef firTypeRef, ConeKotlinType coneKotlinType2, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, boolean z, int i, Object obj) {
        firClassVarianceChecker.checkVarianceConflict(coneKotlinType, variance, firTypeRef, coneKotlinType2, checkerContext, diagnosticReporter, (i & 64) != 0 ? null : ktSourceElement, (i & 128) != 0 ? false : z);
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00c4  */
    private final void checkVarianceConflict(ConeKotlinType type, Variance variance, FirTypeRef typeRef, ConeKotlinType containingType, CheckerContext context, DiagnosticReporter reporter, KtSourceElement source, boolean isInAbbreviation) {
        Variance variance2;
        Variance variance3;
        Variance variance4;
        FirTypeRef typeRef2;
        KtSourceElement source2;
        Variance varianceOpposite;
        KtSourceElement source3;
        if (type instanceof ConeTypeParameterType) {
            ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType((ConeSimpleKotlinType) type, context.getSession());
            FirTypeParameterSymbol typeParameterSymbol = ((ConeTypeParameterType) type).getLookupTag().getTypeParameterSymbol();
            if (source == null) {
                source3 = typeRef != null ? typeRef.getSource() : null;
            } else {
                source3 = source;
            }
            if (source3 == null || typeParameterSymbol.getVariance().allowsPosition(variance) || coneSimpleKotlinTypeFullyExpandedType.getAttributes().contains(CompilerConeAttributes.UnsafeVariance.INSTANCE)) {
                return;
            }
            FirErrors firErrors = FirErrors.INSTANCE;
            KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) source3, (KtDiagnosticFactory4<FirTypeParameterSymbol, Variance, Variance, ConeKotlinType>) ((KtDiagnosticFactory4<Object, Object, Object, Object>) (isInAbbreviation ? firErrors.getTYPE_VARIANCE_CONFLICT_IN_EXPANDED_TYPE() : firErrors.getTYPE_VARIANCE_CONFLICT_ERROR())), typeParameterSymbol, typeParameterSymbol.getVariance(), variance, containingType, (DiagnosticContext) context, (128 & 128) != 0 ? null : null);
            return;
        }
        if (type instanceof ConeClassLikeType) {
            ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) type, context.getSession(), null, 2, null);
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeTypeFullyExpandedType$default.getLookupTag(), context.getSession());
            if (symbol instanceof FirClassSymbol) {
                List<FirTypeRefSource> listExtractArgumentsTypeRefAndSource = FirHelpersKt.extractArgumentsTypeRefAndSource(typeRef);
                ConeTypeProjection[] typeArguments = coneClassLikeTypeFullyExpandedType$default.getTypeArguments();
                int length = typeArguments.length;
                for (int i = 0; i < length; i++) {
                    ConeTypeProjection coneTypeProjection = typeArguments[i];
                    FirTypeParameterSymbol firTypeParameterSymbol = (FirTypeParameterSymbol) CollectionsKt.getOrNull(symbol.getTypeParameterSymbols(), i);
                    if (firTypeParameterSymbol != null && (variance2 = firTypeParameterSymbol.getVariance()) != null) {
                        int i2 = WhenMappings.$EnumSwitchMapping$0[coneTypeProjection.getKind().ordinal()];
                        if (i2 == 1) {
                            variance3 = Variance.IN_VARIANCE;
                        } else if (i2 == 2) {
                            variance3 = Variance.OUT_VARIANCE;
                        } else if (i2 == 3) {
                            variance3 = Variance.INVARIANT;
                        }
                        ConeKotlinType type2 = ConeTypeProjectionKt.getType(coneTypeProjection);
                        if (type2 != null) {
                            int i3 = WhenMappings.$EnumSwitchMapping$1[EnrichedProjectionKind.INSTANCE.getEffectiveProjectionKind(variance2, variance3).ordinal()];
                            if (i3 != 1) {
                                if (i3 == 2) {
                                    varianceOpposite = variance.opposite();
                                } else if (i3 == 3) {
                                    varianceOpposite = Variance.INVARIANT;
                                } else {
                                    if (i3 != 4) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    variance4 = null;
                                }
                                variance4 = varianceOpposite;
                            } else {
                                variance4 = variance;
                            }
                            if (variance4 != null) {
                                FirTypeRefSource firTypeRefSource = listExtractArgumentsTypeRefAndSource != null ? (FirTypeRefSource) CollectionsKt.getOrNull(listExtractArgumentsTypeRefAndSource, i) : null;
                                checkVarianceConflict(type2, variance4, firTypeRefSource != null ? firTypeRefSource.getTypeRef() : null, containingType, context, reporter, (firTypeRefSource == null || (typeRef2 = firTypeRefSource.getTypeRef()) == null || (source2 = typeRef2.getSource()) == null) ? source : source2, !Intrinsics.areEqual(coneClassLikeTypeFullyExpandedType$default, type));
                            }
                        }
                    }
                }
            }
        }
    }
}
