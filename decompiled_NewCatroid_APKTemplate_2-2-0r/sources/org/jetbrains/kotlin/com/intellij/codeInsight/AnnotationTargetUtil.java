package org.jetbrains.kotlin.com.intellij.codeInsight;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiReceiverParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class AnnotationTargetUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) AnnotationTargetUtil.class);
    public static final Set<PsiAnnotation.TargetType> DEFAULT_TARGETS = ContainerUtil.immutableSet(PsiAnnotation.TargetType.PACKAGE, PsiAnnotation.TargetType.TYPE, PsiAnnotation.TargetType.ANNOTATION_TYPE, PsiAnnotation.TargetType.FIELD, PsiAnnotation.TargetType.METHOD, PsiAnnotation.TargetType.CONSTRUCTOR, PsiAnnotation.TargetType.PARAMETER, PsiAnnotation.TargetType.LOCAL_VARIABLE, PsiAnnotation.TargetType.MODULE, PsiAnnotation.TargetType.RECORD_COMPONENT);
    private static final PsiAnnotation.TargetType[] PACKAGE_TARGETS = {PsiAnnotation.TargetType.PACKAGE};
    private static final PsiAnnotation.TargetType[] TYPE_USE_TARGETS = {PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] ANNOTATION_TARGETS = {PsiAnnotation.TargetType.ANNOTATION_TYPE, PsiAnnotation.TargetType.TYPE, PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] TYPE_TARGETS = {PsiAnnotation.TargetType.TYPE, PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] TYPE_PARAMETER_TARGETS = {PsiAnnotation.TargetType.TYPE_PARAMETER, PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] CONSTRUCTOR_TARGETS = {PsiAnnotation.TargetType.CONSTRUCTOR, PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] METHOD_TARGETS = {PsiAnnotation.TargetType.METHOD, PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] FIELD_TARGETS = {PsiAnnotation.TargetType.FIELD, PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] RECORD_COMPONENT_TARGETS = {PsiAnnotation.TargetType.RECORD_COMPONENT, PsiAnnotation.TargetType.FIELD, PsiAnnotation.TargetType.METHOD, PsiAnnotation.TargetType.PARAMETER, PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] PARAMETER_TARGETS = {PsiAnnotation.TargetType.PARAMETER, PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] LOCAL_VARIABLE_TARGETS = {PsiAnnotation.TargetType.LOCAL_VARIABLE, PsiAnnotation.TargetType.TYPE_USE};
    private static final PsiAnnotation.TargetType[] MODULE_TARGETS = {PsiAnnotation.TargetType.MODULE};

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 18:
                objArr[0] = "reference";
                break;
            case 19:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 20:
            case 26:
                objArr[0] = "annotation";
                break;
            case 21:
            case 23:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 22:
            case 24:
                objArr[0] = "annotationType";
                break;
            case 25:
            case 27:
                objArr[0] = "modifierListOwner";
                break;
            case 28:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/codeInsight/AnnotationTargetUtil";
                break;
        }
        switch (i) {
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/AnnotationTargetUtil";
                break;
            default:
                objArr[1] = "getTargetsForLocation";
                break;
        }
        switch (i) {
            case 18:
                objArr[2] = "translateTargetRef";
                break;
            case 19:
                objArr[2] = "isTypeAnnotation";
                break;
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[2] = "findAnnotationTarget";
                break;
            case 24:
                objArr[2] = "getAnnotationTargets";
                break;
            case 25:
            case 26:
            case 27:
                objArr[2] = "getTarget";
                break;
            case 28:
                objArr[2] = "keepStrictlyTypeUseAnnotations";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    public static PsiAnnotation.TargetType[] getTargetsForLocation(PsiAnnotationOwner psiAnnotationOwner) {
        if (psiAnnotationOwner == null) {
            PsiAnnotation.TargetType[] targetTypeArr = PsiAnnotation.TargetType.EMPTY_ARRAY;
            if (targetTypeArr == null) {
                $$$reportNull$$$0(0);
            }
            return targetTypeArr;
        }
        if ((psiAnnotationOwner instanceof PsiType) || (psiAnnotationOwner instanceof PsiTypeElement)) {
            PsiAnnotation.TargetType[] targetTypeArr2 = TYPE_USE_TARGETS;
            if (targetTypeArr2 == null) {
                $$$reportNull$$$0(1);
            }
            return targetTypeArr2;
        }
        if (psiAnnotationOwner instanceof PsiTypeParameter) {
            PsiAnnotation.TargetType[] targetTypeArr3 = TYPE_PARAMETER_TARGETS;
            if (targetTypeArr3 == null) {
                $$$reportNull$$$0(2);
            }
            return targetTypeArr3;
        }
        if (psiAnnotationOwner instanceof PsiModifierList) {
            PsiElement parent = ((PsiModifierList) psiAnnotationOwner).getMyParent();
            if (parent instanceof PsiPackageStatement) {
                PsiAnnotation.TargetType[] targetTypeArr4 = PACKAGE_TARGETS;
                if (targetTypeArr4 == null) {
                    $$$reportNull$$$0(3);
                }
                return targetTypeArr4;
            }
            if (parent instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) parent;
                if (psiClass.mo6095getModifierList() != psiAnnotationOwner) {
                    PsiAnnotation.TargetType[] targetTypeArr5 = PsiAnnotation.TargetType.EMPTY_ARRAY;
                    if (targetTypeArr5 == null) {
                        $$$reportNull$$$0(4);
                    }
                    return targetTypeArr5;
                }
                if (psiClass.isAnnotationType()) {
                    PsiAnnotation.TargetType[] targetTypeArr6 = ANNOTATION_TARGETS;
                    if (targetTypeArr6 == null) {
                        $$$reportNull$$$0(5);
                    }
                    return targetTypeArr6;
                }
                PsiAnnotation.TargetType[] targetTypeArr7 = TYPE_TARGETS;
                if (targetTypeArr7 == null) {
                    $$$reportNull$$$0(6);
                }
                return targetTypeArr7;
            }
            if (parent instanceof PsiRecordComponent) {
                PsiAnnotation.TargetType[] targetTypeArr8 = RECORD_COMPONENT_TARGETS;
                if (targetTypeArr8 == null) {
                    $$$reportNull$$$0(7);
                }
                return targetTypeArr8;
            }
            if (parent instanceof PsiMethod) {
                if (((PsiMethod) parent).isConstructor()) {
                    PsiAnnotation.TargetType[] targetTypeArr9 = CONSTRUCTOR_TARGETS;
                    if (targetTypeArr9 == null) {
                        $$$reportNull$$$0(8);
                    }
                    return targetTypeArr9;
                }
                PsiAnnotation.TargetType[] targetTypeArr10 = METHOD_TARGETS;
                if (targetTypeArr10 == null) {
                    $$$reportNull$$$0(9);
                }
                return targetTypeArr10;
            }
            if (parent instanceof PsiField) {
                PsiAnnotation.TargetType[] targetTypeArr11 = FIELD_TARGETS;
                if (targetTypeArr11 == null) {
                    $$$reportNull$$$0(10);
                }
                return targetTypeArr11;
            }
            if (parent instanceof PsiParameter) {
                PsiElement lightOwner = parent.getMyParent();
                if ((lightOwner instanceof PsiForeachStatement) || (parent instanceof PsiPatternVariable)) {
                    PsiAnnotation.TargetType[] targetTypeArr12 = LOCAL_VARIABLE_TARGETS;
                    if (targetTypeArr12 == null) {
                        $$$reportNull$$$0(11);
                    }
                    return targetTypeArr12;
                }
                if ((lightOwner instanceof PsiParameterList) && (lightOwner.getMyParent() instanceof PsiLambdaExpression) && ((PsiParameter) parent).getTypeElement() == null) {
                    PsiAnnotation.TargetType[] targetTypeArr13 = PsiAnnotation.TargetType.EMPTY_ARRAY;
                    if (targetTypeArr13 == null) {
                        $$$reportNull$$$0(12);
                    }
                    return targetTypeArr13;
                }
                PsiAnnotation.TargetType[] targetTypeArr14 = PARAMETER_TARGETS;
                if (targetTypeArr14 == null) {
                    $$$reportNull$$$0(13);
                }
                return targetTypeArr14;
            }
            if (parent instanceof PsiLocalVariable) {
                PsiAnnotation.TargetType[] targetTypeArr15 = LOCAL_VARIABLE_TARGETS;
                if (targetTypeArr15 == null) {
                    $$$reportNull$$$0(14);
                }
                return targetTypeArr15;
            }
            if (parent instanceof PsiReceiverParameter) {
                PsiAnnotation.TargetType[] targetTypeArr16 = TYPE_USE_TARGETS;
                if (targetTypeArr16 == null) {
                    $$$reportNull$$$0(15);
                }
                return targetTypeArr16;
            }
            if (parent instanceof PsiJavaModule) {
                PsiAnnotation.TargetType[] targetTypeArr17 = MODULE_TARGETS;
                if (targetTypeArr17 == null) {
                    $$$reportNull$$$0(16);
                }
                return targetTypeArr17;
            }
        }
        PsiAnnotation.TargetType[] targetTypeArr18 = PsiAnnotation.TargetType.EMPTY_ARRAY;
        if (targetTypeArr18 == null) {
            $$$reportNull$$$0(17);
        }
        return targetTypeArr18;
    }

    public static Set<PsiAnnotation.TargetType> extractRequiredAnnotationTargets(PsiAnnotationMemberValue psiAnnotationMemberValue) {
        PsiAnnotation.TargetType targetTypeTranslateTargetRef;
        if (psiAnnotationMemberValue instanceof PsiReference) {
            PsiAnnotation.TargetType targetTypeTranslateTargetRef2 = translateTargetRef((PsiReference) psiAnnotationMemberValue);
            if (targetTypeTranslateTargetRef2 != null) {
                return Collections.singleton(targetTypeTranslateTargetRef2);
            }
            return null;
        }
        if (!(psiAnnotationMemberValue instanceof PsiArrayInitializerMemberValue)) {
            return null;
        }
        EnumSet enumSetNoneOf = EnumSet.noneOf(PsiAnnotation.TargetType.class);
        for (PsiAnnotationMemberValue psiAnnotationMemberValue2 : ((PsiArrayInitializerMemberValue) psiAnnotationMemberValue).getInitializers()) {
            if ((psiAnnotationMemberValue2 instanceof PsiReference) && (targetTypeTranslateTargetRef = translateTargetRef((PsiReference) psiAnnotationMemberValue2)) != null) {
                enumSetNoneOf.add(targetTypeTranslateTargetRef);
            }
        }
        return enumSetNoneOf;
    }

    private static PsiAnnotation.TargetType translateTargetRef(PsiReference psiReference) {
        String referenceName;
        if (psiReference == null) {
            $$$reportNull$$$0(18);
        }
        if ((psiReference instanceof PsiJavaCodeReferenceElement) && (referenceName = ((PsiJavaCodeReferenceElement) psiReference).getCustomReferenceName()) != null) {
            try {
                return PsiAnnotation.TargetType.valueOf(referenceName);
            } catch (IllegalArgumentException unused) {
            }
        }
        PsiElement psiElementResolve = psiReference.resolve();
        if (!(psiElementResolve instanceof PsiEnumConstant)) {
            return null;
        }
        String name2 = ((PsiEnumConstant) psiElementResolve).getMyName();
        try {
            return PsiAnnotation.TargetType.valueOf(name2);
        } catch (IllegalArgumentException unused2) {
            LOG.warn("Unknown target: " + name2);
            return null;
        }
    }

    public static boolean isTypeAnnotation(PsiAnnotation psiAnnotation) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(19);
        }
        return findAnnotationTarget(psiAnnotation, PsiAnnotation.TargetType.TYPE_USE) == PsiAnnotation.TargetType.TYPE_USE;
    }

    public static PsiAnnotation.TargetType findAnnotationTarget(PsiAnnotation psiAnnotation, PsiAnnotation.TargetType... targetTypeArr) {
        PsiClass psiClassResolveAnnotationType;
        if (psiAnnotation == null) {
            $$$reportNull$$$0(20);
        }
        if (targetTypeArr == null) {
            $$$reportNull$$$0(21);
        }
        if (targetTypeArr.length != 0 && (psiClassResolveAnnotationType = psiAnnotation.resolveAnnotationType()) != null) {
            return findAnnotationTarget(psiClassResolveAnnotationType, targetTypeArr);
        }
        return PsiAnnotation.TargetType.UNKNOWN;
    }

    public static PsiAnnotation.TargetType findAnnotationTarget(PsiClass psiClass, PsiAnnotation.TargetType... targetTypeArr) {
        Set<PsiAnnotation.TargetType> annotationTargets;
        if (psiClass == null) {
            $$$reportNull$$$0(22);
        }
        if (targetTypeArr == null) {
            $$$reportNull$$$0(23);
        }
        if (targetTypeArr.length != 0 && (annotationTargets = getAnnotationTargets(psiClass)) != null) {
            for (PsiAnnotation.TargetType targetType : targetTypeArr) {
                if (targetType != PsiAnnotation.TargetType.UNKNOWN && annotationTargets.contains(targetType)) {
                    return targetType;
                }
            }
            return null;
        }
        return PsiAnnotation.TargetType.UNKNOWN;
    }

    public static Set<PsiAnnotation.TargetType> getAnnotationTargets(PsiClass psiClass) {
        final PsiModifierList modifierList;
        if (psiClass == null) {
            $$$reportNull$$$0(24);
        }
        if (psiClass.isAnnotationType() && (modifierList = psiClass.mo6095getModifierList()) != null) {
            return (Set) CachedValuesManager.getCachedValue((PsiElement) modifierList, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationTargetUtil$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return CachedValueProvider.Result.create(AnnotationTargetUtil.calcAnnotationTargets(modifierList), PsiModificationTracker.MODIFICATION_COUNT);
                }
            });
        }
        return null;
    }

    private static Set<PsiAnnotation.TargetType> calcAnnotationTargets(PsiModifierList psiModifierList) {
        PsiAnnotation psiAnnotationFindAnnotation = psiModifierList.findAnnotation(CommonClassNames.JAVA_LANG_ANNOTATION_TARGET);
        if (psiAnnotationFindAnnotation == null) {
            return DEFAULT_TARGETS;
        }
        PsiNameValuePair psiNameValuePairFindDeclaredAttribute = AnnotationUtil.findDeclaredAttribute(psiAnnotationFindAnnotation, null);
        if (psiNameValuePairFindDeclaredAttribute == null) {
            return null;
        }
        return extractRequiredAnnotationTargets(psiNameValuePairFindDeclaredAttribute.getDetachedValue());
    }

    public static void collectStrictlyTypeUseAnnotations(PsiModifierList psiModifierList, List<? super PsiAnnotation> list) {
        if (psiModifierList == null) {
            return;
        }
        for (PsiAnnotation psiAnnotation : psiModifierList.getAnnotations()) {
            if (isStrictlyTypeUseAnnotation(psiModifierList, psiAnnotation)) {
                list.add(psiAnnotation);
            }
        }
    }

    public static PsiType keepStrictlyTypeUseAnnotations(PsiModifierList psiModifierList, PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(28);
        }
        if (psiModifierList == null) {
            return psiType;
        }
        ArrayList arrayList = new ArrayList();
        PsiAnnotation[] annotations = psiType.getAnnotations();
        for (PsiAnnotation psiAnnotation : annotations) {
            if (isStrictlyTypeUseAnnotation(psiModifierList, psiAnnotation)) {
                arrayList.add(psiAnnotation);
            }
        }
        if (annotations.length == arrayList.size()) {
            return psiType;
        }
        if (arrayList.isEmpty()) {
            return psiType.annotate(TypeAnnotationProvider.EMPTY);
        }
        return psiType.annotate(TypeAnnotationProvider.Static.create((PsiAnnotation[]) arrayList.toArray(PsiAnnotation.EMPTY_ARRAY)));
    }

    public static boolean isStrictlyTypeUseAnnotation(PsiModifierList psiModifierList, PsiAnnotation psiAnnotation) {
        final Set<PsiAnnotation.TargetType> annotationTargets;
        PsiElement parent = psiAnnotation.getMyParent();
        if ((parent instanceof PsiJavaCodeReferenceElement) || (parent instanceof PsiTypeElement)) {
            return true;
        }
        PsiClass psiClassResolveAnnotationType = psiAnnotation.resolveAnnotationType();
        if (psiClassResolveAnnotationType == null || (annotationTargets = getAnnotationTargets(psiClassResolveAnnotationType)) == null || !annotationTargets.contains(PsiAnnotation.TargetType.TYPE_USE)) {
            return false;
        }
        return annotationTargets.size() == 1 || NullableNotNullManager.isNullabilityAnnotation(psiAnnotation) || !ContainerUtil.exists(getTargetsForLocation(psiModifierList), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationTargetUtil$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return AnnotationTargetUtil.lambda$isStrictlyTypeUseAnnotation$1(annotationTargets, (PsiAnnotation.TargetType) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$isStrictlyTypeUseAnnotation$1(Set set, PsiAnnotation.TargetType targetType) {
        return targetType != PsiAnnotation.TargetType.TYPE_USE && set.contains(targetType);
    }
}
