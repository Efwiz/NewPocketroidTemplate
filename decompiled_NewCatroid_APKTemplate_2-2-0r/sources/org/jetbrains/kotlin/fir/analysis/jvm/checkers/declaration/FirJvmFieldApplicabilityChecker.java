package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.JvmFieldApplicabilityProblem;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirJvmFieldApplicabilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\f\u0010\u0013\u001a\u00020\r*\u00020\u0002H\u0002J\u0014\u0010\u0014\u001a\u00020\r*\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\f\u0010\u0016\u001a\u00020\r*\u00020\u0010H\u0002J\u0014\u0010\u0017\u001a\u00020\r*\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0014\u0010\u0019\u001a\u00020\r*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010\u001a\u001a\u00020\r*\u00020\u00022\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmFieldApplicabilityChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isInsideJvmMultifileClassFile", "", "isInterfaceCompanionWithPublicJvmFieldProperties", "containingClass", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "hasCustomAccessor", "hasJvmFieldAnnotation", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "isDontMangleClass", "isInlineClassThatRequiresMangling", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "isInsideCompanionObjectOfInterface", "isOverridable", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmFieldApplicabilityChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirJvmFieldApplicabilityChecker INSTANCE = new FirJvmFieldApplicabilityChecker();

    private FirJvmFieldApplicabilityChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirAnnotation annotationByClassId;
        JvmFieldApplicabilityProblem jvmFieldApplicabilityProblem;
        KtDiagnosticFactory1<String> inapplicable_jvm_field;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirSession session = context.getSession();
        FirBackingField backingField = declaration.getBackingField();
        if (backingField == null || (annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(backingField, JvmAbi.JVM_FIELD_ANNOTATION_CLASS_ID, session)) == null) {
            return;
        }
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(declaration);
        FirRegularClassSymbol firRegularClassSymbol = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, session) : null;
        if (declaration.getDelegate() != null) {
            jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.DELEGATE;
        } else {
            if (!DeclarationAttributesKt.getHasBackingField(declaration)) {
                return;
            }
            if (isOverridable(declaration, firRegularClassSymbol)) {
                jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.NOT_FINAL;
            } else {
                FirProperty firProperty = declaration;
                if (Visibilities.INSTANCE.isPrivate(firProperty.getStatus().getVisibility())) {
                    jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.PRIVATE;
                } else if (hasCustomAccessor(declaration)) {
                    jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.CUSTOM_ACCESSOR;
                } else if (firProperty.getStatus().isOverride()) {
                    jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.OVERRIDES;
                } else if (firProperty.getStatus().isLateInit()) {
                    jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.LATEINIT;
                } else if (firProperty.getStatus().isConst()) {
                    jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.CONST;
                } else if (firRegularClassSymbol != null && isInsideCompanionObjectOfInterface(firRegularClassSymbol, session)) {
                    if (!FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.JvmFieldInInterface)) {
                        jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.INSIDE_COMPANION_OF_INTERFACE;
                    } else if (isInterfaceCompanionWithPublicJvmFieldProperties(firRegularClassSymbol, session)) {
                        return;
                    } else {
                        jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.NOT_PUBLIC_VAL_WITH_JVMFIELD;
                    }
                } else if (firRegularClassSymbol == null && isInsideJvmMultifileClassFile(context)) {
                    jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.TOP_LEVEL_PROPERTY_OF_MULTIFILE_FACADE;
                } else if (!isInlineClassThatRequiresMangling(declaration.getReturnTypeRef(), session) && !DeclarationUtilsKt.needsMultiFieldValueClassFlattening(declaration.getReturnTypeRef(), session)) {
                    return;
                } else {
                    jvmFieldApplicabilityProblem = JvmFieldApplicabilityProblem.RETURN_TYPE_IS_VALUE_CLASS;
                }
            }
        }
        if (Intrinsics.areEqual((Object) DeclarationAttributesKt.getFromPrimaryConstructor(declaration), (Object) true) && !FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.ProhibitJvmFieldOnOverrideFromInterfaceInPrimaryConstructor)) {
            inapplicable_jvm_field = FirJvmErrors.INSTANCE.getINAPPLICABLE_JVM_FIELD_WARNING();
        } else {
            inapplicable_jvm_field = FirJvmErrors.INSTANCE.getINAPPLICABLE_JVM_FIELD();
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), inapplicable_jvm_field, jvmFieldApplicabilityProblem.getErrorMessage(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final boolean isInlineClassThatRequiresMangling(FirTypeRef firTypeRef, FirSession firSession) {
        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(FirTypeUtilsKt.getConeType(firTypeRef), firSession);
        return (regularClassSymbol == null || !regularClassSymbol.getRawStatus().isInline() || isDontMangleClass(regularClassSymbol)) ? false : true;
    }

    private final boolean isDontMangleClass(FirRegularClassSymbol firRegularClassSymbol) {
        return Intrinsics.areEqual(firRegularClassSymbol.getClassId(), StandardClassIds.INSTANCE.getResult());
    }

    private final boolean isOverridable(FirProperty firProperty, FirRegularClassSymbol firRegularClassSymbol) {
        Modality modality;
        FirProperty firProperty2 = firProperty;
        return (Intrinsics.areEqual(firProperty2.getStatus().getVisibility(), Visibilities.Private.INSTANCE) || firProperty2.getStatus().getModality() == Modality.FINAL || (firRegularClassSymbol != null && ((modality = firRegularClassSymbol.getResolvedStatus().getModality()) == null || modality == Modality.FINAL))) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x001d  */
    /* JADX WARN: Code duplicated, block: B:14:0x0023  */
    private final boolean hasCustomAccessor(FirProperty firProperty) {
        FirPropertyAccessor setter;
        KtSourceElement source;
        KtSourceElement source2;
        KtSourceElementKind kind = null;
        if (firProperty.getGetter() == null) {
            if (firProperty.getSetter() != null) {
                setter = firProperty.getSetter();
                if (setter != null && (source = setter.getSource()) != null) {
                    kind = source.getKind();
                }
                if (!(kind instanceof KtFakeSourceElementKind)) {
                }
            }
            return false;
        }
        FirPropertyAccessor getter = firProperty.getGetter();
        if (((getter == null || (source2 = getter.getSource()) == null) ? null : source2.getKind()) instanceof KtFakeSourceElementKind) {
            if (firProperty.getSetter() != null) {
                setter = firProperty.getSetter();
                if (setter != null) {
                    kind = source.getKind();
                }
                if (!(kind instanceof KtFakeSourceElementKind)) {
                }
            }
            return false;
        }
        return true;
    }

    private final boolean isInsideCompanionObjectOfInterface(FirRegularClassSymbol firRegularClassSymbol, FirSession firSession) {
        if (!firRegularClassSymbol.getRawStatus().isCompanion()) {
            return false;
        }
        FirClassLikeSymbol<?> containingDeclarationSymbol = FirHelpersKt.getContainingDeclarationSymbol(firRegularClassSymbol, firSession);
        ClassKind classKind = containingDeclarationSymbol != null ? FirHelpersKt.getClassKind(containingDeclarationSymbol) : null;
        return classKind == ClassKind.INTERFACE || classKind == ClassKind.ANNOTATION_CLASS;
    }

    private final boolean isInterfaceCompanionWithPublicJvmFieldProperties(FirRegularClassSymbol containingClass, FirSession session) {
        for (FirBasedSymbol<?> firBasedSymbol : containingClass.getDeclarationSymbols()) {
            if (firBasedSymbol instanceof FirPropertySymbol) {
                FirCallableSymbol firCallableSymbol = (FirCallableSymbol) firBasedSymbol;
                if (Intrinsics.areEqual(firCallableSymbol.getResolvedStatus().getVisibility(), Visibilities.Public.INSTANCE)) {
                    FirPropertySymbol firPropertySymbol = (FirPropertySymbol) firBasedSymbol;
                    if (firPropertySymbol.isVar() || firCallableSymbol.getResolvedStatus().getModality() != Modality.FINAL || !hasJvmFieldAnnotation(firPropertySymbol, session)) {
                    }
                }
                return false;
            }
        }
        return true;
    }

    private final boolean hasJvmFieldAnnotation(FirPropertySymbol firPropertySymbol, FirSession firSession) {
        FirBackingFieldSymbol backingFieldSymbol = firPropertySymbol.getBackingFieldSymbol();
        return (backingFieldSymbol != null ? FirAnnotationUtilsKt.getAnnotationByClassId(backingFieldSymbol, JvmAbi.JVM_FIELD_ANNOTATION_CLASS_ID, firSession) : null) != null;
    }

    private final boolean isInsideJvmMultifileClassFile(CheckerContext context) {
        List<FirAnnotation> annotations;
        FirFile containingFile = context.getContainingFile();
        if (containingFile == null || (annotations = containingFile.getAnnotations()) == null) {
            return false;
        }
        List<FirAnnotation> list = annotations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(((FirAnnotation) it.next()).getAnnotationTypeRef())), JvmNames.INSTANCE.getJVM_MULTIFILE_CLASS_ID())) {
                return true;
            }
        }
        return false;
    }
}
