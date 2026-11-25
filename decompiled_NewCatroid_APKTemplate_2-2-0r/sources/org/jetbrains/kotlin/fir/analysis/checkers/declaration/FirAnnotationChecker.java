package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDanglingModifierList;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.CallableIdUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttributeKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.AnnotationTargetList;

/* JADX INFO: compiled from: FirAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J \u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J(\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J0\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J*\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u00132\b\u0010\u001a\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J(\u0010\u001b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002JO\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\u0010\u0001(\u0000J \u0010\"\u001a\u00020\t2\u0006\u0010#\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\"\u0010\"\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010&\u001a\u00020\t2\u0006\u0010'\u001a\u00020!2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J0\u0010(\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010)\u001a\u00020 2\u0006\u0010$\u001a\u00020*H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000ò\u0001\u0004\n\u00020!¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnnotationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "deprecatedClassId", "Lorg/jetbrains/kotlin/name/FqName;", "deprecatedSinceKotlinClassId", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAnnotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "checkAnnotationTarget", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "checkAnnotationUseSiteTarget", "annotated", "target", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "checkDeprecatedCalls", "deprecatedSinceKotlin", "deprecated", "checkMultiFieldValueClassAnnotationRestrictions", "checkPropertyGetter", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "checkRepeatedAnnotations", "annotationContainer", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "checkRepeatedAnnotationsInProperty", "property", "reportIfMfvc", "hint", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirAnnotationChecker INSTANCE = new FirAnnotationChecker();
    private static final FqName deprecatedClassId = new FqName("kotlin.Deprecated");
    private static final FqName deprecatedSinceKotlinClassId = new FqName("kotlin.DeprecatedSinceKotlin");

    /* JADX INFO: compiled from: FirAnnotationChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnnotationUseSiteTarget.values().length];
            try {
                iArr[AnnotationUseSiteTarget.FIELD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[AnnotationUseSiteTarget.RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[AnnotationUseSiteTarget.FILE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[AnnotationUseSiteTarget.PROPERTY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[AnnotationUseSiteTarget.PROPERTY_GETTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[AnnotationUseSiteTarget.PROPERTY_SETTER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[AnnotationUseSiteTarget.SETTER_PARAMETER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirReceiverParameter receiverParameter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirDanglingModifierList) {
            return;
        }
        checkAnnotationContainer(declaration, context, reporter);
        if (!(declaration instanceof FirCallableDeclaration) || (receiverParameter = ((FirCallableDeclaration) declaration).getReceiverParameter()) == null) {
            return;
        }
        INSTANCE.checkAnnotationContainer(receiverParameter, context, reporter);
    }

    private final void checkAnnotationContainer(FirAnnotationContainer declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirReceiverParameter receiverParameter;
        FirAnnotation firAnnotation = null;
        FirAnnotation firAnnotation2 = null;
        for (FirAnnotation firAnnotation3 : declaration.getAnnotations()) {
            FqName fqName = CallableIdUtilsKt.fqName(firAnnotation3, context.getSession());
            if (fqName != null) {
                if (Intrinsics.areEqual(fqName, deprecatedClassId)) {
                    firAnnotation = firAnnotation3;
                } else if (Intrinsics.areEqual(fqName, deprecatedSinceKotlinClassId)) {
                    firAnnotation2 = firAnnotation3;
                }
                checkAnnotationTarget(declaration, firAnnotation3, context, reporter);
            }
        }
        boolean z = declaration instanceof FirCallableDeclaration;
        if (z && (receiverParameter = ((FirCallableDeclaration) declaration).getReceiverParameter()) != null) {
            Iterator<FirAnnotation> it = receiverParameter.getAnnotations().iterator();
            while (it.hasNext()) {
                reportIfMfvc(context, reporter, it.next(), "receivers", receiverParameter.getTypeRef());
            }
        }
        if (firAnnotation2 != null) {
            checkDeprecatedCalls(firAnnotation2, firAnnotation, context, reporter);
        }
        checkRepeatedAnnotations(declaration, context, reporter);
        if (z) {
            if (declaration instanceof FirProperty) {
                checkRepeatedAnnotationsInProperty((FirProperty) declaration, context, reporter);
            }
            KtSourceElement source = declaration.getSource();
            if ((source != null ? source.getKind() : null) instanceof KtRealSourceElementKind) {
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) declaration;
                KtSourceElement source2 = firCallableDeclaration.getReturnTypeRef().getSource();
                if ((source2 != null ? source2.getKind() : null) instanceof KtRealSourceElementKind) {
                    FirTypeRef returnTypeRef = firCallableDeclaration.getReturnTypeRef();
                    FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
                    ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                    checkRepeatedAnnotations(type instanceof ConeKotlinType ? type : null, context, reporter);
                    return;
                }
                return;
            }
            return;
        }
        if (declaration instanceof FirTypeAlias) {
            checkRepeatedAnnotations(FirTypeUtilsKt.getConeType(((FirTypeAlias) declaration).getExpandedTypeRef()), context, reporter);
        }
    }

    private final void reportIfMfvc(CheckerContext context, DiagnosticReporter reporter, FirAnnotation annotation, String hint, FirTypeRef type) {
        if (DeclarationUtilsKt.needsMultiFieldValueClassFlattening(type, context.getSession())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getANNOTATION_ON_ILLEGAL_MULTI_FIELD_VALUE_CLASS_TYPED_TARGET(), hint, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private static final boolean checkMultiFieldValueClassAnnotationRestrictions$hasNoReceivers(FirPropertyAccessor firPropertyAccessor) {
        if (firPropertyAccessor.getContextReceivers().isEmpty()) {
            FirReceiverParameter receiverParameter = firPropertyAccessor.getReceiverParameter();
            if ((receiverParameter != null ? receiverParameter.getTypeRef() : null) == null && firPropertyAccessor.getPropertySymbol().getResolvedReceiverTypeRef() == null && firPropertyAccessor.getPropertySymbol().getResolvedContextReceivers().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:59:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:61:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:62:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:64:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:65:0x00df  */
    /* JADX WARN: Code duplicated, block: B:67:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:68:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:70:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:83:? A[RETURN, SYNTHETIC] */
    private final void checkMultiFieldValueClassAnnotationRestrictions(FirAnnotationContainer declaration, FirAnnotation annotation, CheckerContext context, DiagnosticReporter reporter) {
        FirPropertyAccessor firPropertyAccessor;
        Pair pair;
        FirTypeRef returnTypeRef;
        FirPropertySymbol propertySymbol;
        FirExpression delegate;
        FirTypeRef typeRef;
        FirReceiverParameter receiverParameter;
        FirTypeRef typeRef2;
        AnnotationUseSiteTarget useSiteTarget = annotation.getUseSiteTarget();
        switch (useSiteTarget == null ? -1 : WhenMappings.$EnumSwitchMapping$0[useSiteTarget.ordinal()]) {
            case -1:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                if (declaration instanceof FirProperty) {
                    FirProperty firProperty = (FirProperty) declaration;
                    if (!firProperty.getIsLocal()) {
                        Set<KotlinTarget> allowedAnnotationTargets = FirAnnotationHelpersKt.getAllowedAnnotationTargets(annotation, context.getSession());
                        if ((Intrinsics.areEqual((Object) DeclarationAttributesKt.getFromPrimaryConstructor(firProperty), (Object) true) && allowedAnnotationTargets.contains(KotlinTarget.VALUE_PARAMETER)) || allowedAnnotationTargets.contains(KotlinTarget.PROPERTY) || !allowedAnnotationTargets.contains(KotlinTarget.FIELD)) {
                            return;
                        } else {
                            pair = TuplesKt.to("fields", firProperty.getReturnTypeRef());
                        }
                    } else if (declaration instanceof FirField) {
                        pair = TuplesKt.to("fields", ((FirField) declaration).getReturnTypeRef());
                    } else if (declaration instanceof FirValueParameter) {
                        pair = TuplesKt.to("parameters", ((FirValueParameter) declaration).getReturnTypeRef());
                    } else if (!(declaration instanceof FirVariable)) {
                        pair = TuplesKt.to("variables", ((FirVariable) declaration).getReturnTypeRef());
                    } else {
                        if (!(declaration instanceof FirPropertyAccessor)) {
                            return;
                        }
                        firPropertyAccessor = (FirPropertyAccessor) declaration;
                        if (firPropertyAccessor.getIsGetter() || !checkMultiFieldValueClassAnnotationRestrictions$hasNoReceivers(firPropertyAccessor)) {
                            return;
                        } else {
                            pair = TuplesKt.to("getters", firPropertyAccessor.getReturnTypeRef());
                        }
                    }
                } else if (declaration instanceof FirField) {
                    pair = TuplesKt.to("fields", ((FirField) declaration).getReturnTypeRef());
                } else if (declaration instanceof FirValueParameter) {
                    pair = TuplesKt.to("parameters", ((FirValueParameter) declaration).getReturnTypeRef());
                } else {
                    if (!(declaration instanceof FirVariable)) {
                        if (!(declaration instanceof FirPropertyAccessor)) {
                            firPropertyAccessor = (FirPropertyAccessor) declaration;
                            if (firPropertyAccessor.getIsGetter()) {
                                return;
                            } else {
                                return;
                            }
                        }
                        return;
                    }
                    pair = TuplesKt.to("variables", ((FirVariable) declaration).getReturnTypeRef());
                }
                break;
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
                FirBackingField firBackingField = declaration instanceof FirBackingField ? (FirBackingField) declaration : null;
                if (firBackingField == null || (returnTypeRef = firBackingField.getReturnTypeRef()) == null) {
                    return;
                } else {
                    pair = TuplesKt.to("fields", returnTypeRef);
                }
                break;
            case 2:
                FirBackingField firBackingField2 = declaration instanceof FirBackingField ? (FirBackingField) declaration : null;
                if (firBackingField2 == null || (propertySymbol = firBackingField2.getPropertySymbol()) == null || (delegate = propertySymbol.getDelegate()) == null || (typeRef = delegate.getTypeRef()) == null) {
                    return;
                } else {
                    pair = TuplesKt.to("delegate fields", typeRef);
                }
                break;
            case 3:
                FirCallableDeclaration firCallableDeclaration = declaration instanceof FirCallableDeclaration ? (FirCallableDeclaration) declaration : null;
                if (firCallableDeclaration == null || (receiverParameter = firCallableDeclaration.getReceiverParameter()) == null || (typeRef2 = receiverParameter.getTypeRef()) == null) {
                    return;
                } else {
                    pair = TuplesKt.to("receivers", typeRef2);
                }
                break;
        }
        reportIfMfvc(context, reporter, annotation, (String) pair.component1(), (FirTypeRef) pair.component2());
    }

    private final void checkAnnotationTarget(FirAnnotationContainer declaration, FirAnnotation annotation, CheckerContext context, DiagnosticReporter reporter) {
        String description;
        AnnotationTargetList actualTargetList = FirHelpersKt.getActualTargetList(declaration);
        Set<KotlinTarget> allowedAnnotationTargets = FirAnnotationHelpersKt.getAllowedAnnotationTargets(annotation, context.getSession());
        AnnotationUseSiteTarget useSiteTarget = annotation.getUseSiteTarget();
        if (useSiteTarget != null) {
            checkAnnotationUseSiteTarget(declaration, annotation, useSiteTarget, context, reporter);
        }
        if (checkAnnotationTarget$check(allowedAnnotationTargets, useSiteTarget, actualTargetList.getDefaultTargets()) || checkAnnotationTarget$check(allowedAnnotationTargets, useSiteTarget, actualTargetList.getCanBeSubstituted()) || checkAnnotationTarget$checkWithUseSiteTargets(useSiteTarget, actualTargetList, allowedAnnotationTargets)) {
            if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.ValueClasses)) {
                checkMultiFieldValueClassAnnotationRestrictions(declaration, annotation, context, reporter);
                return;
            }
            return;
        }
        KotlinTarget kotlinTarget = (KotlinTarget) CollectionsKt.firstOrNull((List) actualTargetList.getDefaultTargets());
        if (kotlinTarget == null || (description = kotlinTarget.getDescription()) == null) {
            description = "unidentified target";
        }
        String str = description;
        if (useSiteTarget != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getWRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET(), str, useSiteTarget.getRenderName(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        } else {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getWRONG_ANNOTATION_TARGET(), str, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private static final boolean checkAnnotationTarget$check(Set<? extends KotlinTarget> set, AnnotationUseSiteTarget annotationUseSiteTarget, List<? extends KotlinTarget> list) {
        List<? extends KotlinTarget> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        for (KotlinTarget kotlinTarget : list2) {
            if (set.contains(kotlinTarget) && (annotationUseSiteTarget == null || KotlinTarget.INSTANCE.getUSE_SITE_MAPPING().get(annotationUseSiteTarget) == kotlinTarget)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean checkAnnotationTarget$checkWithUseSiteTargets(AnnotationUseSiteTarget annotationUseSiteTarget, AnnotationTargetList annotationTargetList, Set<? extends KotlinTarget> set) {
        if (annotationUseSiteTarget == null) {
            return false;
        }
        KotlinTarget kotlinTarget = KotlinTarget.INSTANCE.getUSE_SITE_MAPPING().get(annotationUseSiteTarget);
        List<KotlinTarget> onlyWithUseSiteTarget = annotationTargetList.getOnlyWithUseSiteTarget();
        if ((onlyWithUseSiteTarget instanceof Collection) && onlyWithUseSiteTarget.isEmpty()) {
            return false;
        }
        for (KotlinTarget kotlinTarget2 : onlyWithUseSiteTarget) {
            if (set.contains(kotlinTarget2) && kotlinTarget2 == kotlinTarget) {
                return true;
            }
        }
        return false;
    }

    private final void checkAnnotationUseSiteTarget(FirAnnotationContainer annotated, FirAnnotation annotation, AnnotationUseSiteTarget target, CheckerContext context, DiagnosticReporter reporter) {
        KtDiagnosticFactory1<String> inapplicable_target_on_property_warning;
        KtSourceElement source = annotation.getSource();
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.FromUseSiteTarget.INSTANCE)) {
            return;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[target.ordinal()]) {
            case 1:
                if (annotated instanceof FirBackingField) {
                    FirPropertySymbol propertySymbol = ((FirBackingField) annotated).getPropertySymbol();
                    if (propertySymbol.getDelegateFieldSymbol() == null || DeclarationAttributesKt.getHasBackingField(propertySymbol)) {
                        return;
                    }
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getINAPPLICABLE_TARGET_PROPERTY_HAS_NO_BACKING_FIELD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                return;
            case 2:
                if ((annotated instanceof FirBackingField) && ((FirBackingField) annotated).getPropertySymbol().getDelegateFieldSymbol() == null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getINAPPLICABLE_TARGET_PROPERTY_HAS_NO_DELEGATE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                return;
            case 3:
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getWRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET(), "declaration", target.getRenderName(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                return;
            case 4:
                if (annotated instanceof FirFile) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getINAPPLICABLE_FILE_TARGET(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            case 5:
            case 6:
                boolean zSupportsFeature = FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.ProhibitUseSiteGetTargetAnnotations);
                if (zSupportsFeature) {
                    inapplicable_target_on_property_warning = FirErrors.INSTANCE.getINAPPLICABLE_TARGET_ON_PROPERTY();
                } else if (!zSupportsFeature) {
                    inapplicable_target_on_property_warning = FirErrors.INSTANCE.getINAPPLICABLE_TARGET_ON_PROPERTY_WARNING();
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                checkPropertyGetter(annotated, annotation, target, context, reporter, inapplicable_target_on_property_warning);
                return;
            case 7:
            case 9:
                if (checkPropertyGetter(annotated, annotation, target, context, reporter, FirErrors.INSTANCE.getINAPPLICABLE_TARGET_ON_PROPERTY()) || ((FirProperty) annotated).getIsVar()) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getINAPPLICABLE_TARGET_PROPERTY_IMMUTABLE(), target.getRenderName(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                return;
            case 8:
                if (annotated instanceof FirValueParameter) {
                    FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations());
                    if ((firDeclaration instanceof FirConstructor) && ((FirConstructor) firDeclaration).getIsPrimary()) {
                        KtSourceElement source2 = annotated.getSource();
                        if (source2 == null || !LightTreePositioningStrategiesKt.hasValOrVar(source2)) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getREDUNDANT_ANNOTATION_TARGET(), target.getRenderName(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                            return;
                        }
                        return;
                    }
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getINAPPLICABLE_PARAM_TARGET(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getINAPPLICABLE_PARAM_TARGET(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            default:
                return;
        }
    }

    private final boolean checkPropertyGetter(FirAnnotationContainer annotated, FirAnnotation annotation, AnnotationUseSiteTarget target, CheckerContext context, DiagnosticReporter reporter, KtDiagnosticFactory1<String> diagnostic) {
        boolean z = !(annotated instanceof FirProperty) || ((FirProperty) annotated).getIsLocal();
        if (z) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), diagnostic, target.getRenderName(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
        return z;
    }

    private final void checkDeprecatedCalls(FirAnnotation deprecatedSinceKotlin, FirAnnotation deprecated, CheckerContext context, DiagnosticReporter reporter) {
        FirFile containingFile = context.getContainingFile();
        if (containingFile != null && !UtilsKt.getPackageFqName(containingFile).startsWith(StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE().shortName())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, deprecatedSinceKotlin.getSource(), FirErrors.INSTANCE.getDEPRECATED_SINCE_KOTLIN_OUTSIDE_KOTLIN_SUBPACKAGE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (deprecated == null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, deprecatedSinceKotlin.getSource(), FirErrors.INSTANCE.getDEPRECATED_SINCE_KOTLIN_WITHOUT_DEPRECATED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        Iterator<Name> it = deprecated.getArgumentMapping().getMapping().keySet().iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(it.next().getIdentifier(), FirebaseAnalytics.Param.LEVEL)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, deprecatedSinceKotlin.getSource(), FirErrors.INSTANCE.getDEPRECATED_SINCE_KOTLIN_WITH_DEPRECATED_LEVEL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
        }
    }

    private final void checkRepeatedAnnotations(FirAnnotationContainer annotationContainer, CheckerContext context, DiagnosticReporter reporter) {
        FirAnnotationHelpersKt.checkRepeatedAnnotation(annotationContainer, annotationContainer.getAnnotations(), context, reporter);
    }

    private final void checkRepeatedAnnotations(ConeKotlinType type, CheckerContext context, DiagnosticReporter reporter) {
        if (type == null) {
            return;
        }
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(type, context.getSession());
        FirAnnotationHelpersKt.checkRepeatedAnnotation(null, CustomAnnotationTypeAttributeKt.getCustomAnnotations(coneKotlinTypeFullyExpandedType.getAttributes()), context, reporter);
        for (ConeTypeProjection coneTypeProjection : coneKotlinTypeFullyExpandedType.getTypeArguments()) {
            if (coneTypeProjection instanceof ConeKotlinType) {
                checkRepeatedAnnotations((ConeKotlinType) coneTypeProjection, context, reporter);
            }
        }
    }

    private static final List<ConeKotlinType> checkRepeatedAnnotationsInProperty$getAnnotationTypes(FirAnnotationContainer firAnnotationContainer) {
        List<FirAnnotation> annotations;
        if (firAnnotationContainer == null || (annotations = firAnnotationContainer.getAnnotations()) == null) {
            return CollectionsKt.emptyList();
        }
        List<FirAnnotation> list = annotations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType(((FirAnnotation) it.next()).getAnnotationTypeRef()));
        }
        return arrayList;
    }

    private final void checkRepeatedAnnotationsInProperty(FirProperty property, CheckerContext context, DiagnosticReporter reporter) {
        List<FirValueParameter> valueParameters;
        Pair[] pairArr = new Pair[3];
        AnnotationUseSiteTarget annotationUseSiteTarget = AnnotationUseSiteTarget.PROPERTY_GETTER;
        FirPropertyAccessor getter = property.getGetter();
        pairArr[0] = TuplesKt.to(annotationUseSiteTarget, getter != null ? checkRepeatedAnnotationsInProperty$getAnnotationTypes(getter) : null);
        AnnotationUseSiteTarget annotationUseSiteTarget2 = AnnotationUseSiteTarget.PROPERTY_SETTER;
        FirPropertyAccessor setter = property.getSetter();
        pairArr[1] = TuplesKt.to(annotationUseSiteTarget2, setter != null ? checkRepeatedAnnotationsInProperty$getAnnotationTypes(setter) : null);
        AnnotationUseSiteTarget annotationUseSiteTarget3 = AnnotationUseSiteTarget.SETTER_PARAMETER;
        FirPropertyAccessor setter2 = property.getSetter();
        pairArr[2] = TuplesKt.to(annotationUseSiteTarget3, checkRepeatedAnnotationsInProperty$getAnnotationTypes((setter2 == null || (valueParameters = setter2.getValueParameters()) == null) ? null : (FirValueParameter) CollectionsKt.single((List) valueParameters)));
        Map mapMapOf = MapsKt.mapOf(pairArr);
        boolean zSupportsFeature = FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.ProhibitRepeatedUseSiteTargetAnnotations);
        for (FirAnnotation firAnnotation : property.getAnnotations()) {
            AnnotationUseSiteTarget useSiteTarget = firAnnotation.getUseSiteTarget();
            if (useSiteTarget == null) {
                useSiteTarget = FirAnnotationHelpersKt.getDefaultUseSiteTarget(property, firAnnotation, context);
            }
            List list = (List) mapMapOf.get(useSiteTarget);
            if (list != null && list.contains(FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef())) && !FirAnnotationHelpersKt.isRepeatable(firAnnotation, context.getSession())) {
                FirErrors firErrors = FirErrors.INSTANCE;
                KtDiagnosticFactory0 repeated_annotation = zSupportsFeature ? firErrors.getREPEATED_ANNOTATION() : firErrors.getREPEATED_ANNOTATION_WARNING();
                KtSourceElement source = firAnnotation.getSource();
                if (!((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), repeated_annotation, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
