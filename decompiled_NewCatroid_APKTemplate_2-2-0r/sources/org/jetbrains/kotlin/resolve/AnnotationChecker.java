package org.jetbrains.kotlin.resolve;

import com.reandroid.arsc.chunk.TypeBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;

/* JADX INFO: compiled from: AnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 02\u00020\u0001:\u00010B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\"\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011J$\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00142\n\u0010\u0015\u001a\u00060\u0016j\u0002`\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J6\u0010\u0018\u001a\u00020\u000b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00140\u001a2\n\u0010\u0015\u001a\u00060\u0016j\u0002`\u00172\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J\u0016\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0018\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u0010!\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\"2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J6\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010&\u001a\u00020'2\b\b\u0002\u0010(\u001a\u00020'2\b\b\u0002\u0010)\u001a\u00020'H\u0002J\u0010\u0010*\u001a\u00020'2\u0006\u0010\u0010\u001a\u00020+H\u0002J\u0018\u0010,\u001a\u0004\u0018\u00010-*\u0004\u0018\u00010\r2\u0006\u0010\u0010\u001a\u00020.H\u0002J\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020-0\u001a*\u0004\u0018\u00010\rH\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AnnotationChecker;", "", "additionalCheckers", "", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "platformAnnotationFeaturesSupport", "Lorg/jetbrains/kotlin/resolve/PlatformAnnotationFeaturesSupport;", "(Ljava/lang/Iterable;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/PlatformAnnotationFeaturesSupport;)V", "check", "", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "checkAnnotationEntry", "entry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "actualTargets", "Lorg/jetbrains/kotlin/resolve/AnnotationTargetList;", "Lorg/jetbrains/kotlin/resolve/TargetList;", "checkEntries", TypeBlock.NAME_entries, "", "checkExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "checkPropertyUseSiteTargetAnnotations", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "checkSuperTypeAnnotations", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "checkTypeReference", "reference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "isSuperType", "", "shouldCheckReferenceItself", "checkWithoutLanguageFeature", "isRepeatableAnnotation", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getDefaultUseSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getImplicitUseSiteTargetList", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Name TARGET_ALLOWED_TARGETS;
    private final Iterable<AdditionalAnnotationChecker> additionalCheckers;
    private final LanguageVersionSettings languageVersionSettings;
    private final PlatformAnnotationFeaturesSupport platformAnnotationFeaturesSupport;

    @JvmStatic
    public static final Set<KotlinTarget> applicableTargetSet(AnnotationDescriptor annotationDescriptor) {
        return INSTANCE.applicableTargetSet(annotationDescriptor);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AnnotationChecker(Iterable<? extends AdditionalAnnotationChecker> additionalCheckers, LanguageVersionSettings languageVersionSettings, PlatformAnnotationFeaturesSupport platformAnnotationFeaturesSupport) {
        Intrinsics.checkNotNullParameter(additionalCheckers, "additionalCheckers");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(platformAnnotationFeaturesSupport, "platformAnnotationFeaturesSupport");
        this.additionalCheckers = additionalCheckers;
        this.languageVersionSettings = languageVersionSettings;
        this.platformAnnotationFeaturesSupport = platformAnnotationFeaturesSupport;
    }

    public static /* synthetic */ void check$default(AnnotationChecker annotationChecker, KtAnnotated ktAnnotated, BindingTrace bindingTrace, DeclarationDescriptor declarationDescriptor, int i, Object obj) {
        if ((i & 4) != 0) {
            declarationDescriptor = null;
        }
        annotationChecker.check(ktAnnotated, bindingTrace, declarationDescriptor);
    }

    public final void check(KtAnnotated annotated, BindingTrace trace, DeclarationDescriptor descriptor) {
        KtTypeReference ktTypeReferenceMo7396getTypeReference;
        List<KtTypeReference> typeArgumentsAsTypes;
        List listFilterNotNull;
        List<TypeParameterDescriptor> declaredTypeParameters;
        KtTypeReference typeReference;
        Intrinsics.checkNotNullParameter(annotated, "annotated");
        Intrinsics.checkNotNullParameter(trace, "trace");
        BindingContext bindingContext = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        AnnotationTargetList actualTargetList = INSTANCE.getActualTargetList(annotated, descriptor, bindingContext);
        List<KtAnnotationEntry> annotationEntries = annotated.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        checkEntries(annotationEntries, actualTargetList, trace, annotated);
        if (annotated instanceof KtProperty) {
            checkPropertyUseSiteTargetAnnotations((KtProperty) annotated, trace);
        }
        if (annotated instanceof KtClassOrObject) {
            checkSuperTypeAnnotations((KtClassOrObject) annotated, trace);
        }
        boolean z = annotated instanceof KtCallableDeclaration;
        if (z) {
            KtCallableDeclaration ktCallableDeclaration = (KtCallableDeclaration) annotated;
            KtTypeReference ktTypeReferenceMo7396getTypeReference2 = ktCallableDeclaration.mo7396getTypeReference();
            if (ktTypeReferenceMo7396getTypeReference2 != null) {
                check$default(this, ktTypeReferenceMo7396getTypeReference2, trace, null, 4, null);
            }
            KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = ktCallableDeclaration.mo7393getReceiverTypeReference();
            if (ktTypeReferenceMo7393getReceiverTypeReference != null) {
                check$default(this, ktTypeReferenceMo7393getReceiverTypeReference, trace, null, 4, null);
            }
        }
        if ((annotated instanceof KtTypeAlias) && (typeReference = ((KtTypeAlias) annotated).getTypeReference()) != null) {
            check$default(this, typeReference, trace, null, 4, null);
        }
        if ((annotated instanceof KtTypeParameterListOwner) && (z || this.languageVersionSettings.supportsFeature(LanguageFeature.ProperCheckAnnotationsTargetInTypeUsePositions) || ((annotated instanceof KtClass) && this.languageVersionSettings.supportsFeature(LanguageFeature.ClassTypeParameterAnnotations)))) {
            if ((annotated instanceof KtClass) && this.languageVersionSettings.supportsFeature(LanguageFeature.ClassTypeParameterAnnotations)) {
                ClassDescriptor classDescriptor = descriptor instanceof ClassDescriptor ? (ClassDescriptor) descriptor : null;
                if (classDescriptor != null && (declaredTypeParameters = classDescriptor.getDeclaredTypeParameters()) != null) {
                    Iterator<T> it = declaredTypeParameters.iterator();
                    while (it.hasNext()) {
                        ForceResolveUtil.forceResolveAllContents(((TypeParameterDescriptor) it.next()).getAnnotations());
                    }
                }
            }
            KtTypeParameterListOwner ktTypeParameterListOwner = (KtTypeParameterListOwner) annotated;
            List<KtTypeParameter> typeParameters = ktTypeParameterListOwner.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            for (KtTypeParameter ktTypeParameter : typeParameters) {
                Intrinsics.checkNotNull(ktTypeParameter);
                check$default(this, ktTypeParameter, trace, null, 4, null);
            }
            Iterator<KtTypeParameter> it2 = ktTypeParameterListOwner.getTypeParameters().iterator();
            while (it2.hasNext()) {
                KtTypeReference extendsBound = it2.next().getExtendsBound();
                if (extendsBound != null) {
                    checkTypeReference$default(this, extendsBound, trace, false, true, z, 4, null);
                }
            }
            Iterator<KtTypeConstraint> it3 = ktTypeParameterListOwner.getTypeConstraints().iterator();
            while (it3.hasNext()) {
                KtTypeReference boundTypeReference = it3.next().getBoundTypeReference();
                if (boundTypeReference != null) {
                    checkTypeReference$default(this, boundTypeReference, trace, false, true, false, 20, null);
                }
            }
        }
        if (annotated instanceof KtTypeReference) {
            if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProperCheckAnnotationsTargetInTypeUsePositions)) {
                checkTypeReference$default(this, (KtTypeReference) annotated, trace, false, false, false, 28, null);
            } else {
                KtTypeElement typeElement = ((KtTypeReference) annotated).getTypeElement();
                if (typeElement != null && (typeArgumentsAsTypes = typeElement.getTypeArgumentsAsTypes()) != null && (listFilterNotNull = CollectionsKt.filterNotNull(typeArgumentsAsTypes)) != null) {
                    Iterator it4 = listFilterNotNull.iterator();
                    while (it4.hasNext()) {
                        check$default(this, (KtTypeReference) it4.next(), trace, null, 4, null);
                    }
                }
            }
        }
        if (annotated instanceof KtDeclarationWithBody) {
            for (KtParameter ktParameter : ((KtDeclarationWithBody) annotated).getValueParameters()) {
                if (!ktParameter.hasValOrVar()) {
                    Intrinsics.checkNotNull(ktParameter);
                    check$default(this, ktParameter, trace, null, 4, null);
                    if ((annotated instanceof KtFunctionLiteral) && (ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference()) != null) {
                        check$default(this, ktTypeReferenceMo7396getTypeReference, trace, null, 4, null);
                    }
                }
            }
        }
    }

    public final void checkExpression(KtExpression expression, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(trace, "trace");
        List<KtAnnotationEntry> annotationEntries = KtPsiUtilKt.getAnnotationEntries(expression);
        BindingContext bindingContext = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        AnnotationTargetList actualTargetList = INSTANCE.getActualTargetList(expression, null, bindingContext);
        PsiElement parent = expression.getMyParent();
        checkEntries(annotationEntries, actualTargetList, trace, parent instanceof KtAnnotated ? (KtAnnotated) parent : null);
        if ((expression instanceof KtCallElement) && this.languageVersionSettings.supportsFeature(LanguageFeature.ProperCheckAnnotationsTargetInTypeUsePositions)) {
            List<KtTypeProjection> typeArguments = ((KtCallElement) expression).getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
            ArrayList<KtTypeReference> arrayList = new ArrayList();
            Iterator<T> it = typeArguments.iterator();
            while (it.hasNext()) {
                KtTypeReference typeReference = ((KtTypeProjection) it.next()).getTypeReference();
                if (typeReference != null) {
                    arrayList.add(typeReference);
                }
            }
            for (KtTypeReference ktTypeReference : arrayList) {
                List<KtAnnotationEntry> annotationEntries2 = ktTypeReference.getAnnotationEntries();
                Companion companion = INSTANCE;
                Intrinsics.checkNotNull(ktTypeReference);
                BindingContext bindingContext2 = trace.getContext();
                Intrinsics.checkNotNullExpressionValue(bindingContext2, "getBindingContext(...)");
                checkEntries$default(this, annotationEntries2, companion.getActualTargetList(ktTypeReference, null, bindingContext2), trace, null, 8, null);
            }
        }
        if (expression instanceof KtLambdaExpression) {
            Iterator<KtParameter> it2 = ((KtLambdaExpression) expression).getValueParameters().iterator();
            while (it2.hasNext()) {
                KtTypeReference ktTypeReferenceMo7396getTypeReference = it2.next().mo7396getTypeReference();
                if (ktTypeReferenceMo7396getTypeReference != null) {
                    check$default(this, ktTypeReferenceMo7396getTypeReference, trace, null, 4, null);
                }
            }
        }
    }

    private static final List<ClassDescriptor> checkPropertyUseSiteTargetAnnotations$getDescriptors(List<? extends KtAnnotationEntry> list, BindingTrace bindingTrace) {
        ClassDescriptor annotationClass;
        if (list == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) bindingTrace.get(BindingContext.ANNOTATION, (KtAnnotationEntry) it.next());
            if (annotationDescriptor != null) {
                Intrinsics.checkNotNull(annotationDescriptor);
                annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
            } else {
                annotationClass = null;
            }
            if (annotationClass != null) {
                arrayList.add(annotationClass);
            }
        }
        return arrayList;
    }

    private final void checkPropertyUseSiteTargetAnnotations(KtProperty property, BindingTrace trace) {
        ClassDescriptor annotationClass;
        AnnotationUseSiteTarget defaultUseSiteTarget;
        KtParameter parameter;
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitRepeatedUseSiteTargetAnnotations);
        Pair[] pairArr = new Pair[3];
        AnnotationUseSiteTarget annotationUseSiteTarget = AnnotationUseSiteTarget.PROPERTY_GETTER;
        KtPropertyAccessor getter = property.getGetter();
        List<KtAnnotationEntry> annotationEntries = null;
        pairArr[0] = TuplesKt.to(annotationUseSiteTarget, checkPropertyUseSiteTargetAnnotations$getDescriptors(getter != null ? getter.getAnnotationEntries() : null, trace));
        AnnotationUseSiteTarget annotationUseSiteTarget2 = AnnotationUseSiteTarget.PROPERTY_SETTER;
        KtPropertyAccessor setter = property.getSetter();
        pairArr[1] = TuplesKt.to(annotationUseSiteTarget2, checkPropertyUseSiteTargetAnnotations$getDescriptors(setter != null ? setter.getAnnotationEntries() : null, trace));
        AnnotationUseSiteTarget annotationUseSiteTarget3 = AnnotationUseSiteTarget.SETTER_PARAMETER;
        KtPropertyAccessor setter2 = property.getSetter();
        if (setter2 != null && (parameter = setter2.getParameter()) != null) {
            annotationEntries = parameter.getAnnotationEntries();
        }
        pairArr[2] = TuplesKt.to(annotationUseSiteTarget3, checkPropertyUseSiteTargetAnnotations$getDescriptors(annotationEntries, trace));
        Map mapMapOf = MapsKt.mapOf(pairArr);
        for (KtAnnotationEntry ktAnnotationEntry : property.getAnnotationEntries()) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, ktAnnotationEntry);
            if (annotationDescriptor != null && (annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor)) != null) {
                KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
                if (useSiteTarget == null || (defaultUseSiteTarget = useSiteTarget.getAnnotationUseSiteTarget()) == null) {
                    defaultUseSiteTarget = getDefaultUseSiteTarget(property, annotationDescriptor);
                }
                List list = (List) mapMapOf.get(defaultUseSiteTarget);
                if (list != null && list.contains(annotationClass) && !isRepeatableAnnotation(annotationClass)) {
                    if (zSupportsFeature) {
                        Diagnostic diagnosticOn = Errors.REPEATED_ANNOTATION.on(ktAnnotationEntry);
                        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                        DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
                    } else {
                        trace.report(Errors.REPEATED_ANNOTATION_WARNING.on(ktAnnotationEntry));
                    }
                }
            }
        }
    }

    private final void checkSuperTypeAnnotations(KtClassOrObject annotated, BindingTrace trace) {
        List<KtSuperTypeListEntry> superTypeListEntries = annotated.getSuperTypeListEntries();
        ArrayList<KtTypeReference> arrayList = new ArrayList();
        Iterator<T> it = superTypeListEntries.iterator();
        while (it.hasNext()) {
            KtTypeReference typeReference = ((KtSuperTypeListEntry) it.next()).getTypeReference();
            if (typeReference != null) {
                arrayList.add(typeReference);
            }
        }
        for (KtTypeReference ktTypeReference : arrayList) {
            Intrinsics.checkNotNull(ktTypeReference);
            checkTypeReference$default(this, ktTypeReference, trace, true, false, false, 24, null);
        }
    }

    static /* synthetic */ void checkTypeReference$default(AnnotationChecker annotationChecker, KtTypeReference ktTypeReference, BindingTrace bindingTrace, boolean z, boolean z2, boolean z3, int i, Object obj) {
        annotationChecker.checkTypeReference(ktTypeReference, bindingTrace, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3);
    }

    private final void checkTypeReference(KtTypeReference reference, BindingTrace trace, boolean isSuperType, boolean shouldCheckReferenceItself, boolean checkWithoutLanguageFeature) {
        List<KtTypeReference> typeArgumentsAsTypes;
        ClassDescriptor annotationClass;
        boolean z = isSuperType || shouldCheckReferenceItself;
        for (KtAnnotationEntry ktAnnotationEntry : reference.getAnnotationEntries()) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, ktAnnotationEntry);
            ClassId classId = null;
            if (z) {
                if (annotationDescriptor instanceof LazyAnnotationDescriptor) {
                    ((LazyAnnotationDescriptor) annotationDescriptor).forceResolveAllContents();
                }
                BindingContext bindingContext = trace.getContext();
                Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
                AnnotationTargetList actualTargetList = INSTANCE.getActualTargetList(reference, null, bindingContext);
                if (ktAnnotationEntry.getUseSiteTarget() != null && isSuperType) {
                    trace.report(Errors.ANNOTATION_ON_SUPERCLASS.on(this.languageVersionSettings, ktAnnotationEntry));
                } else if (z && (this.languageVersionSettings.supportsFeature(LanguageFeature.ProperCheckAnnotationsTargetInTypeUsePositions) || checkWithoutLanguageFeature)) {
                    checkAnnotationEntry(ktAnnotationEntry, actualTargetList, trace);
                }
            }
            if (annotationDescriptor != null && (annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor)) != null) {
                classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) annotationClass);
            }
            if (Intrinsics.areEqual(classId, StandardClassIds.Annotations.INSTANCE.getExtensionFunctionType())) {
                KotlinType kotlinType = (KotlinType) trace.get(BindingContext.TYPE, reference);
                if (kotlinType == null) {
                    kotlinType = (KotlinType) trace.get(BindingContext.ABBREVIATED_TYPE, reference);
                }
                if (kotlinType != null) {
                    if (!FunctionTypesKt.isFunctionOrKFunctionTypeWithAnySuspendability(kotlinType)) {
                        if (this.languageVersionSettings.supportsFeature(LanguageFeature.ForbidExtensionFunctionTypeOnNonFunctionTypes)) {
                            trace.report(Errors.WRONG_EXTENSION_FUNCTION_TYPE.on(ktAnnotationEntry));
                        } else {
                            trace.report(Errors.WRONG_EXTENSION_FUNCTION_TYPE_WARNING.on(ktAnnotationEntry));
                        }
                    } else if (kotlinType.getArguments().size() <= 1) {
                        trace.report(Errors.WRONG_EXTENSION_FUNCTION_TYPE.on(ktAnnotationEntry));
                    }
                }
            }
        }
        KtTypeElement typeElement = reference.getTypeElement();
        if (typeElement == null || (typeArgumentsAsTypes = typeElement.getTypeArgumentsAsTypes()) == null) {
            return;
        }
        for (KtTypeReference ktTypeReference : typeArgumentsAsTypes) {
            if (ktTypeReference != null) {
                checkTypeReference$default(this, ktTypeReference, trace, false, true, false, 20, null);
            }
        }
    }

    private final List<AnnotationUseSiteTarget> getImplicitUseSiteTargetList(KtAnnotated ktAnnotated) {
        if (ktAnnotated instanceof KtParameter) {
            return ((KtParameter) ktAnnotated).getOwnerFunction() instanceof KtPrimaryConstructor ? UseSiteTargetsList.INSTANCE.getT_CONSTRUCTOR_PARAMETER() : CollectionsKt.emptyList();
        }
        if (ktAnnotated instanceof KtProperty) {
            return !((KtProperty) ktAnnotated).isLocal() ? UseSiteTargetsList.INSTANCE.getT_PROPERTY() : CollectionsKt.emptyList();
        }
        if (ktAnnotated instanceof KtPropertyAccessor) {
            return CollectionsKt.listOf(((KtPropertyAccessor) ktAnnotated).isGetter() ? AnnotationUseSiteTarget.PROPERTY_GETTER : AnnotationUseSiteTarget.PROPERTY_SETTER);
        }
        return CollectionsKt.emptyList();
    }

    private final AnnotationUseSiteTarget getDefaultUseSiteTarget(KtAnnotated ktAnnotated, AnnotationDescriptor annotationDescriptor) {
        Object next;
        Iterator<T> it = getImplicitUseSiteTargetList(ktAnnotated).iterator();
        while (it.hasNext()) {
            next = it.next();
            if (CollectionsKt.contains(INSTANCE.applicableTargetSet(annotationDescriptor), KotlinTarget.INSTANCE.getUSE_SITE_MAPPING().get((AnnotationUseSiteTarget) next))) {
                return (AnnotationUseSiteTarget) next;
            }
        }
        next = null;
        return (AnnotationUseSiteTarget) next;
    }

    static /* synthetic */ void checkEntries$default(AnnotationChecker annotationChecker, List list, AnnotationTargetList annotationTargetList, BindingTrace bindingTrace, KtAnnotated ktAnnotated, int i, Object obj) {
        if ((i & 8) != 0) {
            ktAnnotated = null;
        }
        annotationChecker.checkEntries(list, annotationTargetList, bindingTrace, ktAnnotated);
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00d1  */
    private final void checkEntries(List<? extends KtAnnotationEntry> entries, AnnotationTargetList actualTargets, BindingTrace trace, KtAnnotated annotated) {
        AnnotationUseSiteTarget defaultUseSiteTarget;
        if (entries.isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        for (KtAnnotationEntry ktAnnotationEntry : entries) {
            checkAnnotationEntry(ktAnnotationEntry, actualTargets, trace);
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, ktAnnotationEntry);
            if (annotationDescriptor != null) {
                if (annotationDescriptor instanceof LazyAnnotationDescriptor) {
                    ((LazyAnnotationDescriptor) annotationDescriptor).forceResolveAllContents();
                }
                boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.ContextReceivers);
                if (Intrinsics.areEqual(annotationDescriptor.getFqName(), new FqName("kotlin.ContextFunctionTypeParams")) && !zSupportsFeature) {
                    trace.report(Errors.UNSUPPORTED_FEATURE.on(ktAnnotationEntry, TuplesKt.to(LanguageFeature.ContextReceivers, this.languageVersionSettings)));
                }
                ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
                if (annotationClass != null) {
                    KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
                    if (useSiteTarget == null || (defaultUseSiteTarget = useSiteTarget.getAnnotationUseSiteTarget()) == null) {
                        defaultUseSiteTarget = getDefaultUseSiteTarget(annotated, annotationDescriptor);
                    }
                    HashMap map2 = map;
                    KotlinType type = annotationDescriptor.getType();
                    Object obj = map2.get(type);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        map2.put(type, obj);
                    }
                    List list = (List) obj;
                    if (!list.contains(defaultUseSiteTarget)) {
                        List list2 = list;
                        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                            Iterator it = list2.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    boolean z = true;
                                    boolean z2 = ((AnnotationUseSiteTarget) it.next()) == null;
                                    if (defaultUseSiteTarget != null) {
                                        z = false;
                                    }
                                    if (z2 != z) {
                                        if (!isRepeatableAnnotation(annotationClass)) {
                                            trace.report(Errors.REPEATED_ANNOTATION.on(ktAnnotationEntry));
                                        }
                                    }
                                }
                            }
                        }
                    } else if (!isRepeatableAnnotation(annotationClass)) {
                        trace.report(Errors.REPEATED_ANNOTATION.on(ktAnnotationEntry));
                    }
                    list.add(defaultUseSiteTarget);
                }
            }
        }
        Iterator<AdditionalAnnotationChecker> it2 = this.additionalCheckers.iterator();
        while (it2.hasNext()) {
            it2.next().checkEntries(entries, actualTargets.getDefaultTargets(), trace, annotated, this.languageVersionSettings);
        }
    }

    private final void checkAnnotationEntry(KtAnnotationEntry entry, AnnotationTargetList actualTargets, BindingTrace trace) {
        String description;
        String description2;
        Set setApplicableTargetSet = INSTANCE.applicableTargetSet(entry, trace);
        KtAnnotationUseSiteTarget useSiteTarget = entry.getUseSiteTarget();
        AnnotationUseSiteTarget annotationUseSiteTarget = useSiteTarget != null ? useSiteTarget.getAnnotationUseSiteTarget() : null;
        if (checkAnnotationEntry$check(setApplicableTargetSet, annotationUseSiteTarget, actualTargets.getDefaultTargets()) || checkAnnotationEntry$check(setApplicableTargetSet, annotationUseSiteTarget, actualTargets.getCanBeSubstituted()) || checkAnnotationEntry$checkWithUseSiteTargets(annotationUseSiteTarget, actualTargets, setApplicableTargetSet)) {
            checkAnnotationEntry$checkUselessFunctionLiteralAnnotation(setApplicableTargetSet, entry, trace);
            return;
        }
        String str = "unidentified target";
        if (annotationUseSiteTarget != null) {
            DiagnosticFactory2<KtAnnotationEntry, String, String> diagnosticFactory2 = Errors.WRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET;
            KtAnnotationEntry ktAnnotationEntry = entry;
            KotlinTarget kotlinTarget = (KotlinTarget) CollectionsKt.firstOrNull((List) actualTargets.getDefaultTargets());
            if (kotlinTarget != null && (description2 = kotlinTarget.getDescription()) != null) {
                str = description2;
            }
            trace.report(diagnosticFactory2.on(ktAnnotationEntry, str, annotationUseSiteTarget.getRenderName()));
            return;
        }
        DiagnosticFactory1<KtAnnotationEntry, String> diagnosticFactory1 = Errors.WRONG_ANNOTATION_TARGET;
        KtAnnotationEntry ktAnnotationEntry2 = entry;
        KotlinTarget kotlinTarget2 = (KotlinTarget) CollectionsKt.firstOrNull((List) actualTargets.getDefaultTargets());
        if (kotlinTarget2 != null && (description = kotlinTarget2.getDescription()) != null) {
            str = description;
        }
        trace.report(diagnosticFactory1.on(ktAnnotationEntry2, str));
    }

    private static final boolean checkAnnotationEntry$check(Set<? extends KotlinTarget> set, AnnotationUseSiteTarget annotationUseSiteTarget, List<? extends KotlinTarget> list) {
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

    private static final void checkAnnotationEntry$checkUselessFunctionLiteralAnnotation(Set<? extends KotlinTarget> set, KtAnnotationEntry ktAnnotationEntry, BindingTrace bindingTrace) {
        AnnotationDescriptor annotationDescriptor;
        if (set.contains(KotlinTarget.FUNCTION)) {
            PsiElement parent = ktAnnotationEntry.getMyParent();
            KtAnnotatedExpression ktAnnotatedExpression = parent instanceof KtAnnotatedExpression ? (KtAnnotatedExpression) parent : null;
            if (ktAnnotatedExpression == null || (annotationDescriptor = (AnnotationDescriptor) bindingTrace.get(BindingContext.ANNOTATION, ktAnnotationEntry)) == null) {
                return;
            }
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
            if ((annotationClass != null ? DescriptorUtilsKt.getAnnotationRetention(annotationClass) : null) == KotlinRetention.SOURCE) {
                return;
            }
            KtExpression baseExpression = ktAnnotatedExpression.getBaseExpression();
            KtLambdaExpression ktLambdaExpression = baseExpression instanceof KtLambdaExpression ? (KtLambdaExpression) baseExpression : null;
            if (ktLambdaExpression != null && InlineUtil.isInlinedArgument(ktLambdaExpression.getFunctionLiteral(), bindingTrace.getContext(), false)) {
                bindingTrace.report(Errors.NON_SOURCE_ANNOTATION_ON_INLINED_LAMBDA_EXPRESSION.on(ktAnnotationEntry));
            }
        }
    }

    private static final boolean checkAnnotationEntry$checkWithUseSiteTargets(AnnotationUseSiteTarget annotationUseSiteTarget, AnnotationTargetList annotationTargetList, Set<? extends KotlinTarget> set) {
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

    private final boolean isRepeatableAnnotation(ClassDescriptor descriptor) {
        return DescriptorUtilsKt.isAnnotatedWithKotlinRepeatable(descriptor) || this.platformAnnotationFeaturesSupport.isRepeatableAnnotationClass(descriptor);
    }

    /* JADX INFO: compiled from: AnnotationChecker.kt */
    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0016\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tJ$\u0010\u0011\u001a\u00060\u0012j\u0002`\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\n\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018J&\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u001a2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0017\u001a\u00020\u0018J\u0016\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\u001c\u001a\u00020\u000bJ\u0016\u0010\u001d\u001a\u00020\u001e*\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AnnotationChecker$Companion;", "", "()V", "TARGET_ALLOWED_TARGETS", "Lorg/jetbrains/kotlin/name/Name;", "applicableTargetSet", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "entry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "applicableTargetSetFromTargetAnnotationOrNull", "getActualTargetList", "Lorg/jetbrains/kotlin/resolve/AnnotationTargetList;", "Lorg/jetbrains/kotlin/resolve/TargetList;", "annotated", "Lorg/jetbrains/kotlin/psi/KtElement;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getDeclarationSiteActualTargetList", "", "loadAnnotationTargets", "targetEntryDescriptor", "hasBackingField", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Set<KotlinTarget> applicableTargetSet(KtAnnotationEntry entry, BindingTrace trace) {
            Set<KotlinTarget> setApplicableTargetSetFromTargetAnnotationOrNull;
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, entry);
            if (annotationDescriptor == null) {
                return KotlinTarget.INSTANCE.getDEFAULT_TARGET_SET();
            }
            if (KotlinTypeKt.isError(annotationDescriptor.getType())) {
                return KotlinTarget.INSTANCE.getALL_TARGET_SET();
            }
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
            return (annotationClass == null || (setApplicableTargetSetFromTargetAnnotationOrNull = applicableTargetSetFromTargetAnnotationOrNull(annotationClass)) == null) ? KotlinTarget.INSTANCE.getDEFAULT_TARGET_SET() : setApplicableTargetSetFromTargetAnnotationOrNull;
        }

        @JvmStatic
        public final Set<KotlinTarget> applicableTargetSet(AnnotationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(descriptor);
            return annotationClass == null ? SetsKt.emptySet() : applicableTargetSet(annotationClass);
        }

        public final Set<KotlinTarget> applicableTargetSet(ClassDescriptor classDescriptor) {
            Set<KotlinTarget> setLoadAnnotationTargets;
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            AnnotationDescriptor annotationDescriptorMo6752findAnnotation = classDescriptor.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.target);
            return (annotationDescriptorMo6752findAnnotation == null || (setLoadAnnotationTargets = AnnotationChecker.INSTANCE.loadAnnotationTargets(annotationDescriptorMo6752findAnnotation)) == null) ? KotlinTarget.INSTANCE.getDEFAULT_TARGET_SET() : setLoadAnnotationTargets;
        }

        public final Set<KotlinTarget> applicableTargetSetFromTargetAnnotationOrNull(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            AnnotationDescriptor annotationDescriptorMo6752findAnnotation = classDescriptor.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.target);
            if (annotationDescriptorMo6752findAnnotation == null) {
                return null;
            }
            return loadAnnotationTargets(annotationDescriptorMo6752findAnnotation);
        }

        public final Set<KotlinTarget> loadAnnotationTargets(AnnotationDescriptor targetEntryDescriptor) {
            Intrinsics.checkNotNullParameter(targetEntryDescriptor, "targetEntryDescriptor");
            ConstantValue<?> constantValue = targetEntryDescriptor.getAllValueArguments().get(AnnotationChecker.TARGET_ALLOWED_TARGETS);
            ArrayValue arrayValue = constantValue instanceof ArrayValue ? (ArrayValue) constantValue : null;
            if (arrayValue == null) {
                return null;
            }
            List<? extends ConstantValue<?>> value = arrayValue.getValue();
            ArrayList<EnumValue> arrayList = new ArrayList();
            for (Object obj : value) {
                if (obj instanceof EnumValue) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (EnumValue enumValue : arrayList) {
                KotlinTarget.Companion companion = KotlinTarget.INSTANCE;
                String strAsString = enumValue.getEnumEntryName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                KotlinTarget kotlinTargetValueOrNull = companion.valueOrNull(strAsString);
                if (kotlinTargetValueOrNull != null) {
                    arrayList2.add(kotlinTargetValueOrNull);
                }
            }
            return CollectionsKt.toSet(arrayList2);
        }

        public final List<KotlinTarget> getDeclarationSiteActualTargetList(KtElement annotated, ClassDescriptor descriptor, BindingContext context) {
            Intrinsics.checkNotNullParameter(annotated, "annotated");
            Intrinsics.checkNotNullParameter(context, "context");
            return getActualTargetList(annotated, descriptor, context).getDefaultTargets();
        }

        private final boolean hasBackingField(DeclarationDescriptor declarationDescriptor, BindingContext bindingContext) {
            Boolean bool;
            PropertyDescriptor propertyDescriptor = declarationDescriptor instanceof PropertyDescriptor ? (PropertyDescriptor) declarationDescriptor : null;
            if (propertyDescriptor == null || (bool = (Boolean) bindingContext.get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor)) == null) {
                bool = false;
            }
            return bool.booleanValue();
        }

        public final AnnotationTargetList getActualTargetList(KtElement annotated, DeclarationDescriptor descriptor, BindingContext context) {
            Intrinsics.checkNotNullParameter(annotated, "annotated");
            Intrinsics.checkNotNullParameter(context, "context");
            if (annotated instanceof KtClassOrObject) {
                ClassDescriptor classDescriptor = descriptor instanceof ClassDescriptor ? (ClassDescriptor) descriptor : null;
                if (classDescriptor != null) {
                    KotlinTarget.Companion companion = KotlinTarget.INSTANCE;
                    ClassKind kind = classDescriptor.getKind();
                    Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
                    return new AnnotationTargetList(companion.classActualTargets(kind, classDescriptor.getIsInner(), classDescriptor.getIsCompanionObject(), DescriptorUtils.isLocal(classDescriptor)), null, null, 6, null);
                }
                return AnnotationTargetLists.INSTANCE.getT_CLASSIFIER();
            }
            if (annotated instanceof KtDestructuringDeclarationEntry) {
                return AnnotationTargetLists.INSTANCE.getT_LOCAL_VARIABLE();
            }
            if (annotated instanceof KtProperty) {
                KtProperty ktProperty = (KtProperty) annotated;
                if (ktProperty.isLocal()) {
                    return AnnotationTargetLists.INSTANCE.getT_LOCAL_VARIABLE();
                }
                return ktProperty.isMember() ? AnnotationTargetLists.INSTANCE.T_MEMBER_PROPERTY(hasBackingField(descriptor, context), ktProperty.hasDelegate()) : AnnotationTargetLists.INSTANCE.T_TOP_LEVEL_PROPERTY(hasBackingField(descriptor, context), ktProperty.hasDelegate());
            }
            if (annotated instanceof KtParameter) {
                KtParameter ktParameter = (KtParameter) annotated;
                if (ktParameter.getDestructuringDeclaration() != null) {
                    return AnnotationTargetLists.INSTANCE.getT_DESTRUCTURING_DECLARATION();
                }
                return ktParameter.hasValOrVar() ? AnnotationTargetLists.INSTANCE.getT_VALUE_PARAMETER_WITH_VAL() : AnnotationTargetLists.INSTANCE.getT_VALUE_PARAMETER_WITHOUT_VAL();
            }
            if (annotated instanceof KtConstructor) {
                return AnnotationTargetLists.INSTANCE.getT_CONSTRUCTOR();
            }
            if (annotated instanceof KtFunction) {
                if (ExpressionTypingUtils.isFunctionExpression(descriptor)) {
                    return AnnotationTargetLists.INSTANCE.getT_FUNCTION_EXPRESSION();
                }
                KtFunction ktFunction = (KtFunction) annotated;
                if (ktFunction.getMyName() == null) {
                    return AnnotationTargetLists.INSTANCE.getT_FUNCTION_EXPRESSION();
                }
                if (ktFunction.isLocal()) {
                    return AnnotationTargetLists.INSTANCE.getT_LOCAL_FUNCTION();
                }
                if ((ktFunction.getMyParent() instanceof KtClassOrObject) || (ktFunction.getMyParent() instanceof KtClassBody)) {
                    return AnnotationTargetLists.INSTANCE.getT_MEMBER_FUNCTION();
                }
                return AnnotationTargetLists.INSTANCE.getT_TOP_LEVEL_FUNCTION();
            }
            if (annotated instanceof KtTypeAlias) {
                return AnnotationTargetLists.INSTANCE.getT_TYPEALIAS();
            }
            if (annotated instanceof KtPropertyAccessor) {
                return ((KtPropertyAccessor) annotated).isGetter() ? AnnotationTargetLists.INSTANCE.getT_PROPERTY_GETTER() : AnnotationTargetLists.INSTANCE.getT_PROPERTY_SETTER();
            }
            if (annotated instanceof KtTypeReference) {
                return AnnotationTargetLists.INSTANCE.getT_TYPE_REFERENCE();
            }
            if (annotated instanceof KtFile) {
                return AnnotationTargetLists.INSTANCE.getT_FILE();
            }
            if (annotated instanceof KtTypeParameter) {
                return AnnotationTargetLists.INSTANCE.getT_TYPE_PARAMETER();
            }
            if (annotated instanceof KtTypeProjection) {
                return ((KtTypeProjection) annotated).getProjectionKind() == KtProjectionKind.STAR ? AnnotationTargetLists.INSTANCE.getT_STAR_PROJECTION() : AnnotationTargetLists.INSTANCE.getT_TYPE_PROJECTION();
            }
            if (annotated instanceof KtAnonymousInitializer) {
                return AnnotationTargetLists.INSTANCE.getT_INITIALIZER();
            }
            if (annotated instanceof KtDestructuringDeclaration) {
                return AnnotationTargetLists.INSTANCE.getT_DESTRUCTURING_DECLARATION();
            }
            if (annotated instanceof KtLambdaExpression) {
                return AnnotationTargetLists.INSTANCE.getT_FUNCTION_LITERAL();
            }
            if (annotated instanceof KtObjectLiteralExpression) {
                return AnnotationTargetLists.INSTANCE.getT_OBJECT_LITERAL();
            }
            return annotated instanceof KtExpression ? AnnotationTargetLists.INSTANCE.getT_EXPRESSION() : AnnotationTargetLists.INSTANCE.getEMPTY();
        }
    }

    static {
        Name nameIdentifier = Name.identifier("allowedTargets");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        TARGET_ALLOWED_TARGETS = nameIdentifier;
    }
}
