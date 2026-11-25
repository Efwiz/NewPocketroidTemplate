package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirTypeRefSource;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeConflictingProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: FirProjectionRelationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirProjectionRelationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "ProjectionRelation", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirProjectionRelationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirProjectionRelationChecker INSTANCE = new FirProjectionRelationChecker();

    /* JADX INFO: compiled from: FirProjectionRelationChecker.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirProjectionRelationChecker$ProjectionRelation;", "", "(Ljava/lang/String;I)V", "Conflicting", "Redundant", "None", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum ProjectionRelation {
        Conflicting,
        Redundant,
        None;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ProjectionRelation> getEntries() {
            return $ENTRIES;
        }
    }

    private FirProjectionRelationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirPropertyAccessor) {
            return;
        }
        if (declaration instanceof FirCallableDeclaration) {
            checkTypeRef(((FirCallableDeclaration) declaration).getReturnTypeRef(), context, reporter);
        }
        if (declaration instanceof FirClass) {
            Iterator<FirTypeRef> it = ((FirClass) declaration).getSuperTypeRefs().iterator();
            while (it.hasNext()) {
                checkTypeRef(it.next(), context, reporter);
            }
        } else if (declaration instanceof FirTypeAlias) {
            checkTypeRef(((FirTypeAlias) declaration).getExpandedTypeRef(), context, reporter);
        }
    }

    /* JADX WARN: Code duplicated, block: B:79:0x011c  */
    private final void checkTypeRef(FirTypeRef typeRef, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElementKind kind;
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        ProjectionRelation projectionRelation;
        KtDiagnosticFactory1<ConeKotlinType> redundant_projection;
        boolean z;
        boolean z2;
        KtSourceElement source = typeRef.getSource();
        if (source == null || (kind = source.getKind()) == null || kind.getShouldSkipErrorTypeReporting()) {
            return;
        }
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
        if (coneClassLikeType == null || (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, context.getSession(), null, 2, null)) == null) {
            return;
        }
        FirClassLikeSymbol<?> symbol = TypeUtilsKt.toSymbol(coneClassLikeTypeFullyExpandedType$default, context.getSession());
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null) {
            return;
        }
        List<FirTypeParameterSymbol> typeParameterSymbols = firRegularClassSymbol.getTypeParameterSymbols();
        ConeTypeProjection[] typeArguments = coneClassLikeType.getTypeArguments();
        int iMin = Math.min(typeParameterSymbols.size(), typeArguments.length);
        List<FirTypeRefSource> listExtractArgumentsTypeRefAndSource = FirHelpersKt.extractArgumentsTypeRefAndSource(typeRef);
        if (listExtractArgumentsTypeRefAndSource == null) {
            return;
        }
        int i = 0;
        while (i < iMin) {
            FirTypeParameterSymbol firTypeParameterSymbol = typeParameterSymbols.get(i);
            ConeTypeProjection coneTypeProjection = typeArguments[i];
            ConeTypeProjection coneTypeProjection2 = coneClassLikeTypeFullyExpandedType$default.getTypeArguments()[i];
            Variance variance = firTypeParameterSymbol.getVariance();
            if ((coneTypeProjection2 instanceof ConeKotlinTypeConflictingProjection) || ((((z = coneTypeProjection instanceof ConeKotlinTypeProjectionIn)) && variance == Variance.OUT_VARIANCE) || (((z2 = coneTypeProjection instanceof ConeKotlinTypeProjectionOut)) && variance == Variance.IN_VARIANCE))) {
                projectionRelation = ProjectionRelation.Conflicting;
            } else if ((z && variance == Variance.IN_VARIANCE) || (z2 && variance == Variance.OUT_VARIANCE)) {
                projectionRelation = ProjectionRelation.Redundant;
            } else {
                projectionRelation = ProjectionRelation.None;
            }
            FirTypeRefSource firTypeRefSource = (FirTypeRefSource) CollectionsKt.getOrNull(listExtractArgumentsTypeRefAndSource, i);
            if (firTypeRefSource == null) {
                i = i;
                listExtractArgumentsTypeRefAndSource = listExtractArgumentsTypeRefAndSource;
            } else {
                if (projectionRelation != ProjectionRelation.None) {
                    KtSourceElement source2 = typeRef.getSource();
                    if (!((source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind)) {
                        KtSourceElement source3 = firTypeRefSource.getSource();
                        if (source3 == null) {
                            FirTypeRef typeRef2 = firTypeRefSource.getTypeRef();
                            source3 = typeRef2 != null ? typeRef2.getSource() : null;
                        }
                        KtSourceElement ktSourceElement = source3;
                        if (projectionRelation == ProjectionRelation.Conflicting) {
                            redundant_projection = !Intrinsics.areEqual(coneClassLikeType, coneClassLikeTypeFullyExpandedType$default) ? FirErrors.INSTANCE.getCONFLICTING_PROJECTION_IN_TYPEALIAS_EXPANSION() : FirErrors.INSTANCE.getCONFLICTING_PROJECTION();
                        } else {
                            redundant_projection = FirErrors.INSTANCE.getREDUNDANT_PROJECTION();
                        }
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktSourceElement, redundant_projection, coneClassLikeTypeFullyExpandedType$default, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                }
                FirTypeRef typeRef3 = firTypeRefSource.getTypeRef();
                if (typeRef3 != null) {
                    INSTANCE.checkTypeRef(typeRef3, context, reporter);
                }
            }
            i++;
            listExtractArgumentsTypeRefAndSource = listExtractArgumentsTypeRefAndSource;
        }
    }
}
