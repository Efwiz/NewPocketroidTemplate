package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.HashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirInconsistentTypeParameterHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirTypeRefSource;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSupertypesChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J.\u0010\u000f\u001a\u00020\u00062\f\u0010\u0010\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00112\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J>\u0010\u0013\u001a\u00020\u00062\f\u0010\u0010\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\tH\u0002J(\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSupertypesChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAnnotationOnSuperclass", "superTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "checkClassCannotBeExtendedDirectly", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "checkDelegationNotToInterface", "checkExpandedTypeCannotBeInherited", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "fullyExpandedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "coneType", "checkProjectionInImmediateArgumentToSupertype", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSupertypesChecker extends FirDeclarationChecker<FirClass> {
    public static final FirSupertypesChecker INSTANCE = new FirSupertypesChecker();

    private FirSupertypesChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        boolean z;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = declaration.getSource();
        if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) {
            return;
        }
        boolean z2 = declaration.getClassKind() == ClassKind.INTERFACE;
        HashSet hashSet = new HashSet();
        boolean z3 = !z2;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        boolean z8 = false;
        for (FirTypeRef firTypeRef : declaration.getSuperTypeRefs()) {
            if (firTypeRef.getSource() != null) {
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firTypeRef);
                if (z4 || coneType.getNullability() != ConeNullability.NULLABLE) {
                    z = z4;
                } else {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getNULLABLE_SUPERTYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    z = true;
                }
                if (!z5 && CompilerConeAttributesKt.isExtensionFunctionType(coneType) && !FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.FunctionalTypeWithExtensionAsSupertype)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getSUPERTYPE_IS_EXTENSION_FUNCTION_TYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    z5 = true;
                }
                checkAnnotationOnSuperclass(firTypeRef, context, reporter);
                ConeKotlinType coneKotlinType = coneType;
                ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, context.getSession());
                FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(coneKotlinTypeFullyExpandedType, context.getSession());
                if (symbol instanceof FirRegularClassSymbol) {
                    if (!hashSet.add(symbol)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getSUPERTYPE_APPEARS_TWICE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                    FirRegularClassSymbol firRegularClassSymbol = (FirRegularClassSymbol) symbol;
                    if (firRegularClassSymbol.getClassKind() != ClassKind.INTERFACE) {
                        if (z6) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, r6.getSource(), FirErrors.INSTANCE.getMANY_CLASSES_IN_SUPERTYPE_LIST(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        } else {
                            z6 = true;
                        }
                        if (!z3) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, r6.getSource(), FirErrors.INSTANCE.getINTERFACE_WITH_SUPERCLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            z3 = true;
                        }
                    }
                    boolean z9 = firRegularClassSymbol.getClassKind() == ClassKind.OBJECT;
                    if (!z7 && !z9 && ((FirClassLikeSymbol) symbol).getResolvedStatus().getModality() == Modality.FINAL) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, r6.getSource(), FirErrors.INSTANCE.getFINAL_SUPERTYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        z7 = true;
                    }
                    if (!z8 && z9) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, r6.getSource(), FirErrors.INSTANCE.getSINGLETON_IN_SUPERTYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        z8 = true;
                    }
                } else {
                    symbol = symbol;
                    coneKotlinTypeFullyExpandedType = coneKotlinTypeFullyExpandedType;
                    coneKotlinType = coneKotlinType;
                }
                checkClassCannotBeExtendedDirectly(symbol, reporter, r6, context);
                if (!(coneKotlinType.getTypeArguments().length == 0)) {
                    checkProjectionInImmediateArgumentToSupertype(coneKotlinType, firTypeRef, reporter, context);
                } else {
                    checkExpandedTypeCannotBeInherited(symbol, coneKotlinTypeFullyExpandedType, reporter, r6, coneKotlinType, context);
                }
                z4 = z;
            }
        }
        checkDelegationNotToInterface(declaration, context, reporter);
        if (!(declaration instanceof FirRegularClass) || declaration.getSuperTypeRefs().size() <= 1) {
            return;
        }
        FirInconsistentTypeParameterHelpersKt.checkInconsistentTypeParameters(CollectionsKt.listOf(new Pair(null, ((FirRegularClass) declaration).getSymbol())), context, reporter, declaration.getSource(), true);
    }

    private final void checkAnnotationOnSuperclass(FirTypeRef superTypeRef, CheckerContext context, DiagnosticReporter reporter) {
        for (FirAnnotation firAnnotation : superTypeRef.getAnnotations()) {
            if (firAnnotation.getUseSiteTarget() != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getANNOTATION_ON_SUPERCLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final void checkClassCannotBeExtendedDirectly(FirClassifierSymbol<?> symbol, DiagnosticReporter reporter, FirTypeRef superTypeRef, CheckerContext context) {
        if ((symbol instanceof FirRegularClassSymbol) && Intrinsics.areEqual(((FirRegularClassSymbol) symbol).getClassId(), StandardClassIds.INSTANCE.getEnum())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, superTypeRef.getSource(), FirErrors.INSTANCE.getCLASS_CANNOT_BE_EXTENDED_DIRECTLY(), symbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private final void checkProjectionInImmediateArgumentToSupertype(ConeKotlinType coneType, FirTypeRef superTypeRef, DiagnosticReporter reporter, CheckerContext context) {
        FirTypeRefSource firTypeRefSource;
        List<FirTypeRefSource> listExtractArgumentsTypeRefAndSource = FirHelpersKt.extractArgumentsTypeRefAndSource(superTypeRef);
        if (listExtractArgumentsTypeRefAndSource == null) {
            return;
        }
        ConeTypeProjection[] typeArguments = coneType.getTypeArguments();
        int length = typeArguments.length;
        for (int i = 0; i < length; i++) {
            if (FirHelpersKt.isConflictingOrNotInvariant(typeArguments[i]) && (firTypeRefSource = (FirTypeRefSource) CollectionsKt.getOrNull(listExtractArgumentsTypeRefAndSource, i)) != null) {
                KtSourceElement source = firTypeRefSource.getSource();
                if (source == null) {
                    source = superTypeRef.getSource();
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getPROJECTION_IN_IMMEDIATE_ARGUMENT_TO_SUPERTYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final void checkExpandedTypeCannotBeInherited(FirBasedSymbol<?> symbol, ConeKotlinType fullyExpandedType, DiagnosticReporter reporter, FirTypeRef superTypeRef, ConeKotlinType coneType, CheckerContext context) {
        if ((symbol instanceof FirRegularClassSymbol) && ((FirRegularClassSymbol) symbol).getClassKind() == ClassKind.INTERFACE) {
            for (ConeTypeProjection coneTypeProjection : fullyExpandedType.getTypeArguments()) {
                if (FirHelpersKt.isConflictingOrNotInvariant(coneTypeProjection)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, superTypeRef.getSource(), FirErrors.INSTANCE.getEXPANDED_TYPE_CANNOT_BE_INHERITED(), coneType, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    return;
                }
            }
        }
    }

    private final void checkDelegationNotToInterface(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirRegularClassSymbol regularClassSymbol;
        for (FirDeclaration firDeclaration : declaration.getDeclarations()) {
            if ((firDeclaration instanceof FirField) && Intrinsics.areEqual(((FirMemberDeclaration) firDeclaration).getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
                FirField firField = (FirField) firDeclaration;
                if (FirHelpersKt.isDelegated(firField.getName()) && (regularClassSymbol = FirHelpersKt.toRegularClassSymbol(firField.getReturnTypeRef(), context.getSession())) != null && regularClassSymbol.getClassKind() != ClassKind.INTERFACE) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firField.getReturnTypeRef().getSource(), FirErrors.INSTANCE.getDELEGATION_NOT_TO_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
