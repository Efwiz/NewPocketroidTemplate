package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirJvmStaticChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J6\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J*\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J6\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J*\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016H\u0002J*\u0010\u0019\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\u0014\u0010\u001c\u001a\u00020\u001b*\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0014\u0010\u001f\u001a\u00020\u001b*\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0016\u0010 \u001a\u0004\u0018\u00010!*\u00020\u00022\u0006\u0010\"\u001a\u00020#H\u0002J\u001a\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010%*\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\f\u0010&\u001a\u00020\u0016*\u00020\u0010H\u0002J\u0014\u0010'\u001a\u00020\u001b*\u00020\u00022\u0006\u0010\"\u001a\u00020#H\u0002J\f\u0010(\u001a\u00020\u001b*\u00020\u0010H\u0002J\u0010\u0010)\u001a\u00020\u001b*\u0006\u0012\u0002\b\u00030%H\u0002J\u0014\u0010*\u001a\u00020\u001b*\u00020\t2\u0006\u0010+\u001a\u00020,H\u0002¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmStaticChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAnnotated", "targetSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "outerProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "checkForInterface", "checkOverrideCannotBeStatic", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "checkStaticOnConstOrJvmField", "chooseMostSpecific", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "reportStaticNotInProperObject", "supportsJvmStaticInInterface", "", "containerIsInterface", "outerLevel", "", "containerIsNonCompanionObject", "findAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getContainerAt", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getMinimumVisibility", "hasAnnotationNamedAs", "hasExternalParts", "isCompanion", "supports", ConfigConstants.CONFIG_FEATURE_SECTION, "Lorg/jetbrains/kotlin/config/LanguageFeature;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmStaticChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJvmStaticChecker INSTANCE = new FirJvmStaticChecker();

    private FirJvmStaticChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if ((declaration instanceof FirConstructor) || (declaration instanceof FirPropertyAccessor)) {
            return;
        }
        if (findAnnotation(declaration, StandardClassIds.Annotations.INSTANCE.getJvmStatic()) != null) {
            checkAnnotated$default(this, declaration, context, reporter, declaration.getSource(), null, 16, null);
        }
        if (declaration instanceof FirProperty) {
            FirProperty firProperty = (FirProperty) declaration;
            FirPropertyAccessor getter = firProperty.getGetter();
            if (getter != null) {
                check$checkIfAnnotated(context, declaration, reporter, getter);
            }
            FirPropertyAccessor setter = firProperty.getSetter();
            if (setter != null) {
                check$checkIfAnnotated(context, declaration, reporter, setter);
            }
        }
    }

    private static final void check$checkIfAnnotated(CheckerContext checkerContext, FirDeclaration firDeclaration, DiagnosticReporter diagnosticReporter, FirDeclaration firDeclaration2) {
        if (FirAnnotationUtilsKt.hasAnnotation(firDeclaration2, StandardClassIds.Annotations.INSTANCE.getJvmStatic(), checkerContext.getSession())) {
            KtSourceElement source = firDeclaration2.getSource();
            if (source == null) {
                source = firDeclaration.getSource();
            }
            INSTANCE.checkAnnotated(firDeclaration2, checkerContext, diagnosticReporter, source, firDeclaration instanceof FirProperty ? (FirProperty) firDeclaration : null);
        }
    }

    static /* synthetic */ void checkAnnotated$default(FirJvmStaticChecker firJvmStaticChecker, FirDeclaration firDeclaration, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, FirProperty firProperty, int i, Object obj) {
        if ((i & 16) != 0) {
            firProperty = null;
        }
        firJvmStaticChecker.checkAnnotated(firDeclaration, checkerContext, diagnosticReporter, ktSourceElement, firProperty);
    }

    private final void checkAnnotated(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter, KtSourceElement targetSource, FirProperty outerProperty) {
        FirClassLikeSymbol<?> containerAt;
        if ((declaration instanceof FirMemberDeclaration) && (containerAt = getContainerAt(context, 0)) != null) {
            boolean zSupports = supports(context, LanguageFeature.JvmStaticInInterface);
            boolean zAreEqual = Intrinsics.areEqual(containerAt.getClassId().getShortClassName(), SpecialNames.ANONYMOUS);
            if (FirHelpersKt.getClassKind(containerAt) != ClassKind.OBJECT || (!isCompanion(containerAt) && zAreEqual)) {
                reportStaticNotInProperObject(context, reporter, zSupports, targetSource);
            } else if (isCompanion(containerAt) && containerIsInterface(context, 1)) {
                if (zSupports) {
                    checkForInterface(declaration, context, reporter, targetSource);
                } else {
                    reportStaticNotInProperObject(context, reporter, zSupports, targetSource);
                }
            }
            checkOverrideCannotBeStatic((FirMemberDeclaration) declaration, context, reporter, targetSource, outerProperty);
            checkStaticOnConstOrJvmField(declaration, context, reporter, targetSource);
        }
    }

    private final void reportStaticNotInProperObject(CheckerContext context, DiagnosticReporter reporter, boolean supportsJvmStaticInInterface, KtSourceElement targetSource) {
        KtDiagnosticFactory0 jvm_static_not_in_object_or_class_companion;
        if (supportsJvmStaticInInterface) {
            jvm_static_not_in_object_or_class_companion = FirJvmErrors.INSTANCE.getJVM_STATIC_NOT_IN_OBJECT_OR_COMPANION();
        } else {
            jvm_static_not_in_object_or_class_companion = FirJvmErrors.INSTANCE.getJVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION();
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, targetSource, jvm_static_not_in_object_or_class_companion, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private final void checkForInterface(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter, KtSourceElement targetSource) {
        Visibility visibility;
        boolean zIsExternal;
        if (declaration instanceof FirCallableDeclaration) {
            boolean z = declaration instanceof FirProperty;
            if (z) {
                visibility = getMinimumVisibility((FirProperty) declaration);
            } else {
                visibility = ((FirMemberDeclaration) declaration).getStatus().getVisibility();
            }
            if (z) {
                zIsExternal = hasExternalParts((FirProperty) declaration);
            } else {
                zIsExternal = ((FirMemberDeclaration) declaration).getStatus().isExternal();
            }
            if (!Intrinsics.areEqual(visibility, Visibilities.Public.INSTANCE)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, targetSource, FirJvmErrors.INSTANCE.getJVM_STATIC_ON_NON_PUBLIC_MEMBER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else if (zIsExternal) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, targetSource, FirJvmErrors.INSTANCE.getJVM_STATIC_ON_EXTERNAL_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final boolean hasExternalParts(FirProperty firProperty) {
        boolean zIsExternal = firProperty.getStatus().isExternal();
        FirPropertyAccessor getter = firProperty.getGetter();
        if (getter != null) {
            zIsExternal = zIsExternal || getter.getStatus().isExternal();
        }
        FirPropertyAccessor setter = firProperty.getSetter();
        if (setter != null) {
            return zIsExternal || setter.getStatus().isExternal();
        }
        return zIsExternal;
    }

    private final Visibility getMinimumVisibility(FirProperty firProperty) {
        Visibility visibility = firProperty.getStatus().getVisibility();
        FirPropertyAccessor getter = firProperty.getGetter();
        if (getter != null) {
            visibility = INSTANCE.chooseMostSpecific(visibility, getter.getStatus().getVisibility());
        }
        FirPropertyAccessor setter = firProperty.getSetter();
        return setter != null ? INSTANCE.chooseMostSpecific(visibility, setter.getStatus().getVisibility()) : visibility;
    }

    private final Visibility chooseMostSpecific(Visibility a, Visibility b) {
        Integer numCompareTo = a.compareTo(b);
        return (numCompareTo == null || numCompareTo.intValue() <= 0) ? a : b;
    }

    private final void checkOverrideCannotBeStatic(FirMemberDeclaration declaration, CheckerContext context, DiagnosticReporter reporter, KtSourceElement targetSource, FirProperty outerProperty) {
        if ((outerProperty != null ? outerProperty.getStatus() : declaration.getStatus()).isOverride() && containerIsNonCompanionObject(context, 0)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, targetSource, FirJvmErrors.INSTANCE.getOVERRIDE_CANNOT_BE_STATIC(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkStaticOnConstOrJvmField(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter, KtSourceElement targetSource) {
        FirBackingField backingField;
        if (declaration instanceof FirProperty) {
            if (((FirMemberDeclaration) declaration).getStatus().isConst() || ((backingField = ((FirProperty) declaration).getBackingField()) != null && hasAnnotationNamedAs(backingField, StandardClassIds.Annotations.INSTANCE.getJvmField()))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, targetSource, FirJvmErrors.INSTANCE.getJVM_STATIC_ON_CONST_OR_JVM_FIELD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final boolean containerIsInterface(CheckerContext checkerContext, int i) {
        ClassKind classKind;
        FirClassLikeSymbol<?> containerAt = getContainerAt(checkerContext, i);
        return (containerAt == null || (classKind = FirHelpersKt.getClassKind(containerAt)) == null || classKind != ClassKind.INTERFACE) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean containerIsNonCompanionObject(CheckerContext checkerContext, int i) {
        FirClassLikeSymbol<?> containerAt = getContainerAt(checkerContext, i);
        if (containerAt == null) {
            return false;
        }
        E fir = containerAt.getFir();
        FirRegularClass firRegularClass = fir instanceof FirRegularClass ? (FirRegularClass) fir : null;
        return (firRegularClass == null || firRegularClass.getClassKind() != ClassKind.OBJECT || firRegularClass.getStatus().isCompanion()) ? false : true;
    }

    private final FirClassLikeSymbol<?> getContainerAt(CheckerContext checkerContext, int i) {
        FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.getOrNull(CollectionsKt.asReversed(checkerContext.getContainingDeclarations()), i + (CollectionsKt.lastOrNull((List) checkerContext.getContainingDeclarations()) instanceof FirProperty ? 1 : 0));
        if (firDeclaration instanceof FirClassLikeDeclaration) {
            return ((FirClassLikeDeclaration) firDeclaration).getSymbol();
        }
        return null;
    }

    private final boolean supports(CheckerContext checkerContext, LanguageFeature languageFeature) {
        return FirLanguageSettingsComponentKt.getLanguageVersionSettings(checkerContext.getSession()).supportsFeature(languageFeature);
    }

    private final boolean isCompanion(FirClassLikeSymbol<?> firClassLikeSymbol) {
        FirRegularClassSymbol firRegularClassSymbol = firClassLikeSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) firClassLikeSymbol : null;
        return firRegularClassSymbol != null && firRegularClassSymbol.getRawStatus().isCompanion();
    }

    private final boolean hasAnnotationNamedAs(FirDeclaration firDeclaration, ClassId classId) {
        return findAnnotation(firDeclaration, classId) != null;
    }

    private final FirAnnotation findAnnotation(FirDeclaration firDeclaration, ClassId classId) {
        Object next;
        Iterator<T> it = firDeclaration.getAnnotations().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(((FirAnnotation) next).getAnnotationTypeRef())), classId)) {
                return (FirAnnotation) next;
            }
        }
        next = null;
        return (FirAnnotation) next;
    }
}
