package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeLocalVariableNoTypeOrInitializer;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: FirTopLevelPropertiesChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u0005H\u0000\u001aB\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002\u001a\u0018\u0010 \u001a\u0004\u0018\u00010!*\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002\u001a \u0010 \u001a\u0004\u0018\u00010!*\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\"\u001a\u00020#H\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0005*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u001a\u0010\t\u001a\u00020\u0005*\u0004\u0018\u00010\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u0018\u0010\r\u001a\u00020\u0005*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\b¨\u0006$"}, d2 = {"deprecationWarning", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "getDeprecationWarning", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;)Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "hasAnyAccessorImplementation", "", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getHasAnyAccessorImplementation", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Z", "hasImplementation", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getHasImplementation", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;)Z", "hasSetterAccessorImplementation", "getHasSetterAccessorImplementation", "checkPropertyInitializer", "", "containingClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "property", "modifierList", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList;", "isDefinitelyAssignedInConstructor", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reachable", "reportMustBeInitialized", "propertySource", "Lorg/jetbrains/kotlin/KtSourceElement;", "isOpenValDeferredInitDeprecationWarning", "getEffectiveModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirTopLevelPropertiesCheckerKt {
    public static /* synthetic */ void checkPropertyInitializer$default(FirClass firClass, FirProperty firProperty, FirModifierList firModifierList, boolean z, DiagnosticReporter diagnosticReporter, CheckerContext checkerContext, boolean z2, int i, Object obj) {
        if ((i & 64) != 0) {
            z2 = true;
        }
        checkPropertyInitializer(firClass, firProperty, firModifierList, z, diagnosticReporter, checkerContext, z2);
    }

    private static final void reportMustBeInitialized(FirProperty firProperty, boolean z, FirClass firClass, KtSourceElement ktSourceElement, boolean z2, DiagnosticReporter diagnosticReporter, CheckerContext checkerContext) {
        KtDiagnosticFactory0 must_be_initialized;
        if (firProperty.getStatus().getModality() == Modality.ABSTRACT) {
            throw new IllegalStateException("reportMustBeInitialized isn't called for abstract properties".toString());
        }
        boolean z3 = (firClass == null || getHasSetterAccessorImplementation(firProperty) || getEffectiveModality(firProperty, firClass, checkerContext.get$languageVersionSettings()) == Modality.FINAL || !z) ? false : true;
        boolean z4 = (firClass == null || getHasAnyAccessorImplementation(firProperty)) ? false : true;
        if (z2 && !z3 && z4) {
            throw new IllegalStateException("Not reachable case. Every \"open val + deferred init\" case that could be made `abstract`, also could be made `final`".toString());
        }
        boolean z5 = !checkerContext.get$languageVersionSettings().supportsFeature(LanguageFeature.ProhibitMissedMustBeInitializedWhenThereIsNoPrimaryConstructor) && firClass != null && org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.primaryConstructorIfAny(firClass, checkerContext.getSession()) == null && z;
        if (z3 && z4) {
            must_be_initialized = FirErrors.INSTANCE.getMUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT();
        } else if (z3) {
            must_be_initialized = FirErrors.INSTANCE.getMUST_BE_INITIALIZED_OR_BE_FINAL();
        } else if (z4) {
            must_be_initialized = FirErrors.INSTANCE.getMUST_BE_INITIALIZED_OR_BE_ABSTRACT();
        } else {
            must_be_initialized = FirErrors.INSTANCE.getMUST_BE_INITIALIZED();
        }
        KtSourceElement ktSourceElement2 = ktSourceElement;
        boolean z6 = z5 || z2;
        if (z6) {
            must_be_initialized = getDeprecationWarning(must_be_initialized);
        } else if (z6) {
            throw new NoWhenBranchMatchedException();
        }
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement2, must_be_initialized, checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private static final KtDiagnosticFactory0 getDeprecationWarning(KtDiagnosticFactory0 ktDiagnosticFactory0) {
        if (Intrinsics.areEqual(ktDiagnosticFactory0, FirErrors.INSTANCE.getMUST_BE_INITIALIZED())) {
            return FirErrors.INSTANCE.getMUST_BE_INITIALIZED_WARNING();
        }
        if (Intrinsics.areEqual(ktDiagnosticFactory0, FirErrors.INSTANCE.getMUST_BE_INITIALIZED_OR_BE_ABSTRACT())) {
            return FirErrors.INSTANCE.getMUST_BE_INITIALIZED_OR_BE_ABSTRACT_WARNING();
        }
        if (Intrinsics.areEqual(ktDiagnosticFactory0, FirErrors.INSTANCE.getMUST_BE_INITIALIZED_OR_BE_FINAL())) {
            return FirErrors.INSTANCE.getMUST_BE_INITIALIZED_OR_BE_FINAL_WARNING();
        }
        if (Intrinsics.areEqual(ktDiagnosticFactory0, FirErrors.INSTANCE.getMUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT())) {
            return FirErrors.INSTANCE.getMUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT_WARNING();
        }
        throw new IllegalStateException("Only MUST_BE_INITIALIZED is supported".toString());
    }

    private static final boolean getHasImplementation(FirPropertyAccessor firPropertyAccessor) {
        return ((firPropertyAccessor instanceof FirDefaultPropertyAccessor) || firPropertyAccessor == null || firPropertyAccessor.getBody() == null) ? false : true;
    }

    private static final boolean getHasSetterAccessorImplementation(FirProperty firProperty) {
        return getHasImplementation(firProperty.getSetter());
    }

    private static final boolean getHasAnyAccessorImplementation(FirProperty firProperty) {
        return getHasImplementation(firProperty.getGetter()) || getHasImplementation(firProperty.getSetter());
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0021  */
    private static final Modality getEffectiveModality(FirProperty firProperty, FirClass firClass) {
        boolean z;
        FirDeclarationStatus status;
        if (firProperty.getStatus().getModality() != Modality.OPEN) {
            z = false;
        } else {
            if (((firClass == null || (status = firClass.getStatus()) == null) ? null : status.getModality()) == Modality.FINAL) {
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            return Modality.FINAL;
        }
        if (z) {
            throw new NoWhenBranchMatchedException();
        }
        return firProperty.getStatus().getModality();
    }

    private static final Modality getEffectiveModality(FirProperty firProperty, FirClass firClass, LanguageVersionSettings languageVersionSettings) {
        boolean zSupportsFeature = languageVersionSettings.supportsFeature(LanguageFeature.TakeIntoAccountEffectivelyFinalInMustBeInitializedCheck);
        if (zSupportsFeature) {
            return getEffectiveModality(firProperty, firClass);
        }
        if (zSupportsFeature) {
            throw new NoWhenBranchMatchedException();
        }
        return firProperty.getStatus().getModality();
    }

    public static final void checkPropertyInitializer(FirClass firClass, FirProperty property, FirModifierList firModifierList, boolean z, DiagnosticReporter reporter, CheckerContext context, boolean z2) {
        KtSourceElement source;
        KtSourceElement source2;
        KtSourceElement source3;
        KtSourceElement source4;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(context, "context");
        boolean z3 = firClass != null && firClass.getClassKind() == ClassKind.INTERFACE;
        KtModifierKeywordToken ABSTRACT_KEYWORD = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD, "ABSTRACT_KEYWORD");
        boolean zContains = FirKeywordUtilsKt.contains(firModifierList, ABSTRACT_KEYWORD);
        FirProperty firProperty = property;
        if (firProperty.getStatus().getModality() == Modality.ABSTRACT || zContains) {
            FirTypeRef returnTypeRef = property.getReturnTypeRef();
            if (property.getInitializer() == null && property.getDelegate() == null && (returnTypeRef instanceof FirErrorTypeRef) && (((FirErrorTypeRef) returnTypeRef).getDiagnostic() instanceof ConeLocalVariableNoTypeOrInitializer) && (source = property.getSource()) != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getPROPERTY_WITH_NO_TYPE_NO_INITIALIZER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            return;
        }
        boolean hasBackingField = DeclarationAttributesKt.getHasBackingField(property);
        if (z3 && hasBackingField && getHasAnyAccessorImplementation(property) && (source4 = property.getSource()) != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source4, FirErrors.INSTANCE.getBACKING_FIELD_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        boolean zIsEffectivelyExpect = DeclarationUtilsKt.isEffectivelyExpect(firProperty, firClass, context);
        if (property.getInitializer() != null) {
            FirExpression initializer = property.getInitializer();
            if (initializer == null || (source3 = initializer.getSource()) == null) {
                return;
            }
            if (z3) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source3, FirErrors.INSTANCE.getPROPERTY_INITIALIZER_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if (zIsEffectivelyExpect) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source3, FirErrors.INSTANCE.getEXPECTED_PROPERTY_INITIALIZER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            } else if (!hasBackingField) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source3, FirErrors.INSTANCE.getPROPERTY_INITIALIZER_NO_BACKING_FIELD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            } else {
                if (property.getReceiverParameter() != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source3, FirErrors.INSTANCE.getEXTENSION_PROPERTY_WITH_BACKING_FIELD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                return;
            }
        }
        if (property.getDelegate() != null) {
            FirExpression delegate = property.getDelegate();
            if (delegate == null || (source2 = delegate.getSource()) == null) {
                return;
            }
            if (z3) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getDELEGATED_PROPERTY_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            } else {
                if (zIsEffectivelyExpect) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getEXPECTED_DELEGATED_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                return;
            }
        }
        KtSourceElement source5 = property.getSource();
        if (source5 == null) {
            return;
        }
        boolean zIsEffectivelyExternal = DeclarationUtilsKt.isEffectivelyExternal(firProperty, firClass, context);
        boolean z4 = (property.getInitializer() == null && (!z || getHasSetterAccessorImplementation(property) || getEffectiveModality(property, firClass, context.get$languageVersionSettings()) == Modality.OPEN)) ? false : true;
        if (hasBackingField && !z3 && !firProperty.getStatus().isLateInit() && !zIsEffectivelyExpect && !z4 && !zIsEffectivelyExternal && !DeclarationAttributesKt.getHasExplicitBackingField(property)) {
            if (property.getReceiverParameter() != null && !getHasAnyAccessorImplementation(property)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source5, FirErrors.INSTANCE.getEXTENSION_PROPERTY_MUST_HAVE_ACCESSORS_OR_BE_ABSTRACT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else if (z2) {
                boolean z5 = !context.get$languageVersionSettings().supportsFeature(LanguageFeature.ProhibitOpenValDeferredInitialization) && getEffectiveModality(property, firClass, context.get$languageVersionSettings()) == Modality.OPEN && property.getIsVal() && z;
                if (!z5 || getEffectiveModality(property, firClass) != Modality.FINAL) {
                    reportMustBeInitialized(property, z, firClass, source5, z5, reporter, context);
                }
            }
        }
        if (firProperty.getStatus().isLateInit()) {
            if (zIsEffectivelyExpect) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source5, FirErrors.INSTANCE.getEXPECTED_LATEINIT_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (hasBackingField && !z3 && z4 && context.get$languageVersionSettings().supportsFeature(LanguageFeature.EnableDfaWarningsInK2)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source5, FirErrors.INSTANCE.getUNNECESSARY_LATEINIT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
