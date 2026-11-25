package org.jetbrains.kotlin.com.intellij.codeInsight;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
public abstract class NullableNotNullManager {
    protected static final Logger LOG = Logger.getInstance((Class<?>) NullableNotNullManager.class);
    protected final Project myProject;

    interface NullabilityAnnotationDataHolder {
        Nullability getNullability(String str);

        Set<String> qualifiedNames();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:29:0x0059  */
    /* JADX WARN: Code duplicated, block: B:32:0x0066  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 19 || i == 35 || i == 22 || i == 23) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 19 || i == 35 || i == 22 || i == 23) ? 2 : 3];
        if (i != 15) {
            switch (i) {
                case 2:
                case 4:
                case 6:
                    objArr[0] = "original";
                    break;
                case 3:
                case 5:
                case 7:
                    objArr[0] = "generated";
                    break;
                case 8:
                    objArr[0] = "annotation";
                    break;
                case 9:
                    objArr[0] = "target";
                    break;
                default:
                    switch (i) {
                        case 17:
                            objArr[0] = "nullability";
                            break;
                        case 18:
                            objArr[0] = "annotation";
                            break;
                        case 19:
                        case 22:
                        case 23:
                            objArr[0] = "org/jetbrains/kotlin/com/intellij/codeInsight/NullableNotNullManager";
                            break;
                        case 20:
                            objArr[0] = "memberAnno";
                            break;
                        case 21:
                            objArr[0] = "typeAnno";
                            break;
                        case 24:
                            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                            break;
                        default:
                            switch (i) {
                                case 29:
                                    objArr[0] = "place";
                                    break;
                                case 30:
                                case 31:
                                    objArr[0] = "placeTargetTypes";
                                    break;
                                default:
                                    switch (i) {
                                        case 35:
                                            objArr[0] = "org/jetbrains/kotlin/com/intellij/codeInsight/NullableNotNullManager";
                                            break;
                                        case 36:
                                            objArr[0] = "annotation";
                                            break;
                                        case 37:
                                            objArr[0] = "qualifiedNames";
                                            break;
                                        default:
                                            objArr[0] = "owner";
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[0] = "nullabilities";
        }
        if (i == 19) {
            objArr[1] = "correctNullability";
        } else if (i == 35) {
            objArr[1] = "getNullability";
        } else if (i == 22) {
            objArr[1] = "getNullablesWithNickNames";
        } else if (i != 23) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/NullableNotNullManager";
        } else {
            objArr[1] = "getNotNullsWithNickNames";
        }
        switch (i) {
            case 1:
                objArr[2] = "getNotNullAnnotation";
                break;
            case 2:
            case 3:
                objArr[2] = "copyNotNullAnnotation";
                break;
            case 4:
            case 5:
                objArr[2] = "copyNullableAnnotation";
                break;
            case 6:
            case 7:
                objArr[2] = "copyNullableOrNotNullAnnotation";
                break;
            case 8:
            case 9:
                objArr[2] = "copyAnnotation";
                break;
            case 10:
                objArr[2] = "findOwnNullabilityInfo";
                break;
            case 11:
                objArr[2] = "findEffectiveNullabilityInfo";
                break;
            case 12:
                objArr[2] = "doFindEffectiveNullabilityAnnotation";
                break;
            case 13:
                objArr[2] = "findNullityDefaultFiltered";
                break;
            case 14:
            case 15:
                objArr[2] = "findExplicitNullabilityAnnotation";
                break;
            case 16:
                objArr[2] = "findPlainAnnotation";
                break;
            case 17:
            case 18:
                objArr[2] = "correctNullability";
                break;
            case 19:
            case 22:
            case 23:
            case 35:
                break;
            case 20:
            case 21:
                objArr[2] = "areDifferentNullityAnnotations";
                break;
            case 24:
                objArr[2] = "hasHardcodedContracts";
                break;
            case 25:
                objArr[2] = "getOwnerType";
                break;
            case 26:
            case 32:
                objArr[2] = "isNullable";
                break;
            case 27:
            case 33:
                objArr[2] = "isNotNull";
                break;
            case 28:
                objArr[2] = "findNullityDefaultInHierarchy";
                break;
            case 29:
            case 30:
                objArr[2] = "findNullabilityDefault";
                break;
            case 31:
                objArr[2] = "findNullityDefaultOnPackage";
                break;
            case 34:
                objArr[2] = "getNullability";
                break;
            case 36:
                objArr[2] = "isNullabilityAnnotation";
                break;
            case 37:
                objArr[2] = "findAnnotationInTypeHierarchy";
                break;
            default:
                objArr[2] = "getNullableAnnotation";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 19 && i != 35 && i != 22 && i != 23) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract boolean canAnnotateLocals(String str);

    protected abstract NullabilityAnnotationDataHolder getAllNullabilityAnnotationsWithNickNames();

    public abstract Optional<Nullability> getAnnotationNullability(String str);

    public abstract String getDefaultNotNull();

    public abstract String getDefaultNullable();

    public abstract List<String> getInstrumentedNotNulls();

    public abstract List<String> getNotNulls();

    public abstract List<String> getNullables();

    abstract NullabilityAnnotationInfo getNullityDefault(PsiModifierListOwner psiModifierListOwner, PsiAnnotation.TargetType[] targetTypeArr, PsiElement psiElement, boolean z);

    protected boolean hasHardcodedContracts(PsiElement psiElement) {
        if (psiElement != null) {
            return false;
        }
        $$$reportNull$$$0(24);
        return false;
    }

    public abstract boolean isTypeUseAnnotationLocationRestricted(String str);

    public abstract void setDefaultNotNull(String str);

    public abstract void setDefaultNullable(String str);

    public abstract void setInstrumentedNotNulls(List<String> list);

    public abstract void setNotNulls(String... strArr);

    public abstract void setNullables(String... strArr);

    protected NullableNotNullManager(Project project) {
        this.myProject = project;
    }

    public static NullableNotNullManager getInstance(Project project) {
        return (NullableNotNullManager) project.getService(NullableNotNullManager.class);
    }

    @Deprecated
    public PsiAnnotation getNullableAnnotation(PsiModifierListOwner psiModifierListOwner, boolean z) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(0);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindEffectiveNullabilityInfo = findEffectiveNullabilityInfo(psiModifierListOwner);
        if (nullabilityAnnotationInfoFindEffectiveNullabilityInfo == null || nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getNullability() != Nullability.NULLABLE) {
            return null;
        }
        if (z || nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getInheritedFrom() == null) {
            return nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getAnnotation();
        }
        return null;
    }

    @Deprecated
    public PsiAnnotation getNotNullAnnotation(PsiModifierListOwner psiModifierListOwner, boolean z) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(1);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindEffectiveNullabilityInfo = findEffectiveNullabilityInfo(psiModifierListOwner);
        if (nullabilityAnnotationInfoFindEffectiveNullabilityInfo == null || nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getNullability() != Nullability.NOT_NULL) {
            return null;
        }
        if (z || nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getInheritedFrom() == null) {
            return nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getAnnotation();
        }
        return null;
    }

    public PsiAnnotation copyNotNullAnnotation(PsiModifierListOwner psiModifierListOwner, PsiModifierListOwner psiModifierListOwner2) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(2);
        }
        if (psiModifierListOwner2 == null) {
            $$$reportNull$$$0(3);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindOwnNullabilityInfo = findOwnNullabilityInfo(psiModifierListOwner);
        if (nullabilityAnnotationInfoFindOwnNullabilityInfo == null || nullabilityAnnotationInfoFindOwnNullabilityInfo.getNullability() != Nullability.NOT_NULL) {
            return null;
        }
        return copyAnnotation(nullabilityAnnotationInfoFindOwnNullabilityInfo.getAnnotation(), psiModifierListOwner2);
    }

    public PsiAnnotation copyNullableAnnotation(PsiModifierListOwner psiModifierListOwner, PsiModifierListOwner psiModifierListOwner2) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(4);
        }
        if (psiModifierListOwner2 == null) {
            $$$reportNull$$$0(5);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindOwnNullabilityInfo = findOwnNullabilityInfo(psiModifierListOwner);
        if (nullabilityAnnotationInfoFindOwnNullabilityInfo == null || nullabilityAnnotationInfoFindOwnNullabilityInfo.getNullability() != Nullability.NULLABLE) {
            return null;
        }
        return copyAnnotation(nullabilityAnnotationInfoFindOwnNullabilityInfo.getAnnotation(), psiModifierListOwner2);
    }

    public PsiAnnotation copyNullableOrNotNullAnnotation(PsiModifierListOwner psiModifierListOwner, PsiModifierListOwner psiModifierListOwner2) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(6);
        }
        if (psiModifierListOwner2 == null) {
            $$$reportNull$$$0(7);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindOwnNullabilityInfo = findOwnNullabilityInfo(psiModifierListOwner);
        if (nullabilityAnnotationInfoFindOwnNullabilityInfo == null) {
            return null;
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindEffectiveNullabilityInfo = findEffectiveNullabilityInfo(psiModifierListOwner2);
        if (nullabilityAnnotationInfoFindEffectiveNullabilityInfo == null || nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getNullability() != nullabilityAnnotationInfoFindOwnNullabilityInfo.getNullability()) {
            return copyAnnotation(nullabilityAnnotationInfoFindOwnNullabilityInfo.getAnnotation(), psiModifierListOwner2);
        }
        return null;
    }

    private static PsiAnnotation copyAnnotation(PsiAnnotation psiAnnotation, PsiModifierListOwner psiModifierListOwner) {
        PsiModifierList psiModifierListMo6095getModifierList;
        if (psiAnnotation == null) {
            $$$reportNull$$$0(8);
        }
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(9);
        }
        String annotationFqName = psiAnnotation.getAnnotationFqName();
        if (annotationFqName == null || JavaPsiFacade.getInstance(psiAnnotation.getProject()).findClass(annotationFqName, psiModifierListOwner.getResolveScope()) == null || (psiModifierListMo6095getModifierList = psiModifierListOwner.getModifierList()) == null || AnnotationTargetUtil.isStrictlyTypeUseAnnotation(psiModifierListMo6095getModifierList, psiAnnotation) || psiModifierListMo6095getModifierList.hasAnnotation(annotationFqName)) {
            return null;
        }
        return psiModifierListMo6095getModifierList.addAnnotation(annotationFqName);
    }

    public NullabilityAnnotationInfo findOwnNullabilityInfo(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(10);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindEffectiveNullabilityInfo = findEffectiveNullabilityInfo(psiModifierListOwner);
        if (nullabilityAnnotationInfoFindEffectiveNullabilityInfo == null || nullabilityAnnotationInfoFindEffectiveNullabilityInfo.isContainer() || nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getInheritedFrom() != null) {
            return null;
        }
        return nullabilityAnnotationInfoFindEffectiveNullabilityInfo;
    }

    public NullabilityAnnotationInfo findExplicitNullability(PsiModifierListOwner psiModifierListOwner) {
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindPlainAnnotation = findPlainAnnotation(psiModifierListOwner, false, true, getAllNullabilityAnnotationsWithNickNames());
        return nullabilityAnnotationInfoFindPlainAnnotation != null ? nullabilityAnnotationInfoFindPlainAnnotation : findNullityDefaultInHierarchy(psiModifierListOwner);
    }

    public NullabilityAnnotationInfo findEffectiveNullabilityInfo(final PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(11);
        }
        PsiType ownerType = getOwnerType(psiModifierListOwner);
        if (ownerType == null || TypeConversionUtil.isPrimitiveAndNotNull(ownerType)) {
            return null;
        }
        return (NullabilityAnnotationInfo) CachedValuesManager.getCachedValue((PsiElement) psiModifierListOwner, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.NullableNotNullManager$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6459x2e112c4c(psiModifierListOwner);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$findEffectiveNullabilityInfo$0$org-jetbrains-kotlin-com-intellij-codeInsight-NullableNotNullManager, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6459x2e112c4c(PsiModifierListOwner psiModifierListOwner) {
        return CachedValueProvider.Result.create(doFindEffectiveNullabilityAnnotation(psiModifierListOwner), PsiModificationTracker.MODIFICATION_COUNT);
    }

    private NullabilityAnnotationInfo doFindEffectiveNullabilityAnnotation(PsiModifierListOwner psiModifierListOwner) {
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindNullityDefaultFiltered;
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(12);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindPlainAnnotation = findPlainAnnotation(psiModifierListOwner, true, false, getAllNullabilityAnnotationsWithNickNames());
        if (nullabilityAnnotationInfoFindPlainAnnotation != null) {
            return nullabilityAnnotationInfoFindPlainAnnotation;
        }
        boolean z = psiModifierListOwner instanceof PsiParameter;
        boolean z2 = z && (psiModifierListOwner.getLightParent() instanceof PsiParameterList) && (psiModifierListOwner.getLightParent().getLightParent() instanceof PsiLambdaExpression);
        if (!z2 && (nullabilityAnnotationInfoFindNullityDefaultFiltered = findNullityDefaultFiltered(psiModifierListOwner)) != null) {
            return nullabilityAnnotationInfoFindNullityDefaultFiltered;
        }
        if (z) {
            List<PsiParameter> superAnnotationOwners = AnnotationUtil.getSuperAnnotationOwners((PsiParameter) psiModifierListOwner);
            if (!superAnnotationOwners.isEmpty()) {
                for (PsiParameter psiParameter : superAnnotationOwners) {
                    if (findPlainAnnotation(psiParameter, false, false, getAllNullabilityAnnotationsWithNickNames()) != null) {
                        return null;
                    }
                    NullabilityAnnotationInfo nullabilityAnnotationInfoFindNullityDefaultInHierarchy = findNullityDefaultInHierarchy(psiParameter);
                    if (nullabilityAnnotationInfoFindNullityDefaultInHierarchy != null) {
                        if (nullabilityAnnotationInfoFindNullityDefaultInHierarchy.getNullability() == Nullability.NOT_NULL) {
                            return nullabilityAnnotationInfoFindNullityDefaultInHierarchy.withInheritedFrom(psiParameter);
                        }
                        return null;
                    }
                }
                return null;
            }
        }
        if (z2) {
            return findNullityDefaultFiltered(psiModifierListOwner);
        }
        return null;
    }

    private NullabilityAnnotationInfo findNullityDefaultFiltered(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(13);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindNullityDefaultInHierarchy = findNullityDefaultInHierarchy(psiModifierListOwner);
        if (nullabilityAnnotationInfoFindNullityDefaultInHierarchy == null) {
            return null;
        }
        if (nullabilityAnnotationInfoFindNullityDefaultInHierarchy.getNullability() == Nullability.NULLABLE || !hasHardcodedContracts(psiModifierListOwner)) {
            return nullabilityAnnotationInfoFindNullityDefaultInHierarchy;
        }
        return null;
    }

    public PsiAnnotation findExplicitNullabilityAnnotation(PsiModifierListOwner psiModifierListOwner, final Collection<Nullability> collection) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(14);
        }
        if (collection == null) {
            $$$reportNull$$$0(15);
        }
        final NullabilityAnnotationDataHolder allNullabilityAnnotationsWithNickNames = getAllNullabilityAnnotationsWithNickNames();
        final Set set = (Set) allNullabilityAnnotationsWithNickNames.qualifiedNames().stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.NullableNotNullManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return collection.contains(allNullabilityAnnotationsWithNickNames.getNullability((String) obj));
            }
        }).collect(Collectors.toSet());
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindPlainAnnotation = findPlainAnnotation(psiModifierListOwner, false, false, new NullabilityAnnotationDataHolder() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.NullableNotNullManager.1
            @Override // org.jetbrains.kotlin.com.intellij.codeInsight.NullableNotNullManager.NullabilityAnnotationDataHolder
            public Set<String> qualifiedNames() {
                return set;
            }

            @Override // org.jetbrains.kotlin.com.intellij.codeInsight.NullableNotNullManager.NullabilityAnnotationDataHolder
            public Nullability getNullability(String str) {
                Nullability nullability = allNullabilityAnnotationsWithNickNames.getNullability(str);
                if (collection.contains(nullability)) {
                    return nullability;
                }
                return null;
            }
        });
        if (nullabilityAnnotationInfoFindPlainAnnotation == null || !collection.contains(nullabilityAnnotationInfoFindPlainAnnotation.getNullability())) {
            return null;
        }
        return nullabilityAnnotationInfoFindPlainAnnotation.getAnnotation();
    }

    private NullabilityAnnotationInfo findPlainAnnotation(PsiModifierListOwner psiModifierListOwner, boolean z, boolean z2, NullabilityAnnotationDataHolder nullabilityAnnotationDataHolder) {
        AnnotationUtil.AnnotationAndOwner annotationAndOwner;
        Nullability nullability;
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(16);
        }
        if (z && (psiModifierListOwner instanceof PsiMethod)) {
            annotationAndOwner = AnnotationUtil.findAnnotationAndOwnerInHierarchy(psiModifierListOwner, nullabilityAnnotationDataHolder.qualifiedNames(), z2);
        } else {
            PsiAnnotation psiAnnotationFindAnnotation = AnnotationUtil.findAnnotation(psiModifierListOwner, nullabilityAnnotationDataHolder.qualifiedNames(), z2);
            annotationAndOwner = psiAnnotationFindAnnotation == null ? null : new AnnotationUtil.AnnotationAndOwner(psiModifierListOwner, psiAnnotationFindAnnotation);
        }
        PsiType ownerType = getOwnerType(psiModifierListOwner);
        if (annotationAndOwner != null && (ownerType instanceof PsiArrayType) && !AnnotationUtil.isInferredAnnotation(annotationAndOwner.annotation) && !AnnotationUtil.isExternalAnnotation(annotationAndOwner.annotation) && AnnotationTargetUtil.isTypeAnnotation(annotationAndOwner.annotation)) {
            annotationAndOwner = null;
        }
        if (annotationAndOwner != null) {
            Nullability nullability2 = nullabilityAnnotationDataHolder.getNullability(annotationAndOwner.annotation.getAnnotationFqName());
            if (nullability2 == null) {
                return null;
            }
            Nullability nullabilityCorrectNullability = correctNullability(nullability2, annotationAndOwner.annotation);
            if (ownerType != null) {
                for (PsiAnnotation psiAnnotation : ownerType.getApplicableAnnotations()) {
                    if (psiAnnotation != annotationAndOwner.annotation && (nullability = nullabilityAnnotationDataHolder.getNullability(psiAnnotation.getAnnotationFqName())) != null) {
                        if (nullability == nullabilityCorrectNullability) {
                            annotationAndOwner = new AnnotationUtil.AnnotationAndOwner(psiModifierListOwner, psiAnnotation);
                            break;
                        }
                        return null;
                    }
                }
            }
            return new NullabilityAnnotationInfo(annotationAndOwner.annotation, nullabilityCorrectNullability, annotationAndOwner.owner != psiModifierListOwner ? annotationAndOwner.owner : null, false);
        }
        if (ownerType instanceof PsiPrimitiveType) {
            return null;
        }
        return findAnnotationInTypeHierarchy(ownerType, nullabilityAnnotationDataHolder);
    }

    protected Nullability correctNullability(Nullability nullability, PsiAnnotation psiAnnotation) {
        if (nullability == null) {
            $$$reportNull$$$0(17);
        }
        if (psiAnnotation == null) {
            $$$reportNull$$$0(18);
        }
        if (nullability == null) {
            $$$reportNull$$$0(19);
        }
        return nullability;
    }

    protected List<String> getNullablesWithNickNames() {
        List<String> nullables = getNullables();
        if (nullables == null) {
            $$$reportNull$$$0(22);
        }
        return nullables;
    }

    protected List<String> getNotNullsWithNickNames() {
        List<String> notNulls = getNotNulls();
        if (notNulls == null) {
            $$$reportNull$$$0(23);
        }
        return notNulls;
    }

    private static PsiType getOwnerType(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(25);
        }
        if (psiModifierListOwner instanceof PsiVariable) {
            return ((PsiVariable) psiModifierListOwner).getType();
        }
        if (psiModifierListOwner instanceof PsiMethod) {
            return ((PsiMethod) psiModifierListOwner).getReturnType();
        }
        return null;
    }

    public boolean isNullable(PsiModifierListOwner psiModifierListOwner, boolean z) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(26);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindEffectiveNullabilityInfo = findEffectiveNullabilityInfo(psiModifierListOwner);
        return nullabilityAnnotationInfoFindEffectiveNullabilityInfo != null && nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getNullability() == Nullability.NULLABLE && (z || nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getInheritedFrom() == null);
    }

    public boolean isNotNull(PsiModifierListOwner psiModifierListOwner, boolean z) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(27);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindEffectiveNullabilityInfo = findEffectiveNullabilityInfo(psiModifierListOwner);
        return nullabilityAnnotationInfoFindEffectiveNullabilityInfo != null && nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getNullability() == Nullability.NOT_NULL && (z || nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getInheritedFrom() == null);
    }

    public NullabilityAnnotationInfo findDefaultTypeUseNullability(PsiElement psiElement) {
        if (psiElement == null) {
            return null;
        }
        if ((psiElement.getLightParent() instanceof PsiTypeElement) && (psiElement.getLightParent().getLightParent() instanceof PsiLocalVariable)) {
            return null;
        }
        return findNullabilityDefault(psiElement, PsiAnnotation.TargetType.TYPE_USE);
    }

    NullabilityAnnotationInfo findNullityDefaultInHierarchy(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(28);
        }
        return findNullabilityDefault(psiModifierListOwner, AnnotationTargetUtil.getTargetsForLocation(psiModifierListOwner.getModifierList()));
    }

    private NullabilityAnnotationInfo findNullabilityDefault(PsiElement psiElement, PsiAnnotation.TargetType... targetTypeArr) {
        NullabilityAnnotationInfo nullityDefault;
        if (psiElement == null) {
            $$$reportNull$$$0(29);
        }
        if (targetTypeArr == null) {
            $$$reportNull$$$0(30);
        }
        for (PsiElement context = psiElement.getContext(); context != null; context = context.getContext()) {
            if ((context instanceof PsiModifierListOwner) && (nullityDefault = getNullityDefault((PsiModifierListOwner) context, targetTypeArr, psiElement, false)) != null) {
                return nullityDefault;
            }
            if (context instanceof PsiClassOwner) {
                return findNullityDefaultOnPackage(targetTypeArr, JavaPsiFacade.getInstance(context.getProject()).findPackage(((PsiClassOwner) context).getPackageName()), psiElement);
            }
        }
        return null;
    }

    private NullabilityAnnotationInfo findNullityDefaultOnPackage(PsiAnnotation.TargetType[] targetTypeArr, PsiPackage psiPackage, PsiElement psiElement) {
        if (targetTypeArr == null) {
            $$$reportNull$$$0(31);
        }
        boolean z = false;
        while (psiPackage != null) {
            NullabilityAnnotationInfo nullityDefault = getNullityDefault(psiPackage, targetTypeArr, psiElement, z);
            if (nullityDefault != null) {
                return nullityDefault;
            }
            psiPackage = psiPackage.getParentPackage();
            z = true;
        }
        return null;
    }

    public static boolean isNullable(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(32);
        }
        return getNullability(psiModifierListOwner) == Nullability.NULLABLE;
    }

    public static boolean isNotNull(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(33);
        }
        return getNullability(psiModifierListOwner) == Nullability.NOT_NULL;
    }

    public static Nullability getNullability(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(34);
        }
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindEffectiveNullabilityInfo = getInstance(psiModifierListOwner.getProject()).findEffectiveNullabilityInfo(psiModifierListOwner);
        Nullability nullability = nullabilityAnnotationInfoFindEffectiveNullabilityInfo == null ? Nullability.UNKNOWN : nullabilityAnnotationInfoFindEffectiveNullabilityInfo.getNullability();
        if (nullability == null) {
            $$$reportNull$$$0(35);
        }
        return nullability;
    }

    public static boolean isNullabilityAnnotation(PsiAnnotation psiAnnotation) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(36);
        }
        return getInstance(psiAnnotation.getProject()).getAllNullabilityAnnotationsWithNickNames().getNullability(psiAnnotation.getAnnotationFqName()) != null;
    }

    private NullabilityAnnotationInfo findAnnotationInTypeHierarchy(PsiType psiType, final NullabilityAnnotationDataHolder nullabilityAnnotationDataHolder) {
        if (nullabilityAnnotationDataHolder == null) {
            $$$reportNull$$$0(37);
        }
        if (psiType == null) {
            return null;
        }
        final Ref refCreate = Ref.create(null);
        InheritanceUtil.processSuperTypes(psiType, true, (Processor<? super PsiType>) new Processor() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.NullableNotNullManager$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return this.f$0.m6458x7510bf79(nullabilityAnnotationDataHolder, refCreate, (PsiType) obj);
            }
        });
        return (NullabilityAnnotationInfo) refCreate.get();
    }

    /* JADX INFO: renamed from: lambda$findAnnotationInTypeHierarchy$2$org-jetbrains-kotlin-com-intellij-codeInsight-NullableNotNullManager, reason: not valid java name */
    /* synthetic */ boolean m6458x7510bf79(NullabilityAnnotationDataHolder nullabilityAnnotationDataHolder, Ref ref, PsiType psiType) {
        NullabilityAnnotationInfo nullabilityAnnotationInfoFindNullabilityDefault;
        for (PsiAnnotation psiAnnotation : psiType.getAnnotations()) {
            String annotationFqName = psiAnnotation.getAnnotationFqName();
            if (nullabilityAnnotationDataHolder.qualifiedNames().contains(annotationFqName)) {
                Nullability nullability = nullabilityAnnotationDataHolder.getNullability(annotationFqName);
                if (nullability != null) {
                    ref.set(new NullabilityAnnotationInfo(psiAnnotation, correctNullability(nullability, psiAnnotation), false));
                }
                return false;
            }
        }
        if (!(psiType instanceof PsiClassType)) {
            return true;
        }
        PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiType);
        if (!(psiClassResolveClassInClassTypeOnly instanceof PsiTypeParameter)) {
            return false;
        }
        if (psiClassResolveClassInClassTypeOnly.getExtendsListTypes().length != 0 || (nullabilityAnnotationInfoFindNullabilityDefault = findNullabilityDefault(psiClassResolveClassInClassTypeOnly, PsiAnnotation.TargetType.TYPE_PARAMETER)) == null) {
            return true;
        }
        ref.set(nullabilityAnnotationInfoFindNullabilityDefault);
        return false;
    }
}
