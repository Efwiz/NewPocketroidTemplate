package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceBound;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiSearchScopeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.JavaClassSupers;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaClassSupersImpl extends JavaClassSupers {
    private static final Logger LOG = Logger.getInstance((Class<?>) JavaClassSupersImpl.class);
    private static final Set<String> ourReportedInconsistencies = ContainerUtil.newConcurrentSet();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 13 || i == 14 || i == 18) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 13 || i == 14 || i == 18) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 9:
            case 16:
                objArr[0] = "derivedClass";
                break;
            case 2:
            case 6:
                objArr[0] = "scope";
                break;
            case 3:
            case 7:
            case 11:
                objArr[0] = "derivedSubstitutor";
                break;
            case 4:
                objArr[0] = "lowerBound";
                break;
            case 8:
            case 12:
            case 15:
            default:
                objArr[0] = "superClass";
                break;
            case 10:
                objArr[0] = "resolveScope";
                break;
            case 13:
            case 14:
            case 18:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/JavaClassSupersImpl";
                break;
            case 17:
                objArr[0] = "aClass";
                break;
        }
        if (i == 13) {
            objArr[1] = "createRawSubstitutor";
        } else if (i == 14) {
            objArr[1] = "composeSubstitutors";
        } else if (i != 18) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/JavaClassSupersImpl";
        } else {
            objArr[1] = "classInfo";
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "processLowerBound";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "getSuperSubstitutorWithCaching";
                break;
            case 12:
                objArr[2] = "createRawSubstitutor";
                break;
            case 13:
            case 14:
            case 18:
                break;
            case 15:
            case 16:
                objArr[2] = "reportHierarchyInconsistency";
                break;
            case 17:
                objArr[2] = "classInfo";
                break;
            default:
                objArr[2] = "getSuperClassSubstitutor";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 14 && i != 18) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ boolean lambda$reportHierarchyInconsistency$0(PsiClass psiClass, PsiClass psiClass2) {
        return psiClass2 != psiClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.JavaClassSupers
    public PsiSubstitutor getSuperClassSubstitutor(PsiClass psiClass, PsiClass psiClass2, GlobalSearchScope globalSearchScope, PsiSubstitutor psiSubstitutor) {
        List<PsiType> listSingletonList;
        PsiType inferredLowerBoundForSynthetic;
        PsiSubstitutor psiSubstitutorProcessLowerBound;
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(1);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(2);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
        if (InheritanceImplUtil.hasObjectQualifiedName(psiClass)) {
            return PsiSubstitutor.EMPTY;
        }
        if (psiClass instanceof InferenceVariable) {
            listSingletonList = ((InferenceVariable) psiClass).getBounds(InferenceBound.LOWER);
        } else {
            listSingletonList = (!(psiClass instanceof PsiTypeParameter) || (inferredLowerBoundForSynthetic = TypeConversionUtil.getInferredLowerBoundForSynthetic((PsiTypeParameter) psiClass)) == null) ? null : Collections.singletonList(inferredLowerBoundForSynthetic);
        }
        if (listSingletonList != null) {
            for (PsiType psiType : listSingletonList) {
                if (psiType != null && (psiSubstitutorProcessLowerBound = processLowerBound(psiType, psiClass2, globalSearchScope, psiSubstitutor)) != null) {
                    return psiSubstitutorProcessLowerBound;
                }
            }
        }
        if (psiClass2 instanceof PsiTypeParameter) {
            return processTypeParameter((PsiTypeParameter) psiClass2, globalSearchScope, psiClass, new HashSet(), psiSubstitutor);
        }
        return getSuperSubstitutorWithCaching(psiClass, psiClass2, globalSearchScope, psiSubstitutor);
    }

    private static PsiSubstitutor processLowerBound(PsiType psiType, PsiClass psiClass, GlobalSearchScope globalSearchScope, PsiSubstitutor psiSubstitutor) {
        if (psiType == null) {
            $$$reportNull$$$0(4);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(5);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(6);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(7);
        }
        if (psiType instanceof PsiClassType) {
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = ((PsiClassType) psiType).resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element == null) {
                return null;
            }
            if (element.equals(psiClass)) {
                return psiSubstitutor;
            }
            PsiSubstitutor superSubstitutorWithCaching = getSuperSubstitutorWithCaching(element, psiClass, globalSearchScope, classResolveResultResolveGenerics.getSubstitutor());
            if (superSubstitutorWithCaching != null) {
                return composeSubstitutors(psiSubstitutor, superSubstitutorWithCaching, element);
            }
            return null;
        }
        if (psiType instanceof PsiIntersectionType) {
            for (PsiType psiType2 : ((PsiIntersectionType) psiType).getConjuncts()) {
                PsiSubstitutor psiSubstitutorProcessLowerBound = processLowerBound(psiType2, psiClass, globalSearchScope, psiSubstitutor);
                if (psiSubstitutorProcessLowerBound != null) {
                    return psiSubstitutorProcessLowerBound;
                }
            }
            return null;
        }
        if (psiType instanceof PsiCapturedWildcardType) {
            return processLowerBound(((PsiCapturedWildcardType) psiType).getUpperBound(), psiClass, globalSearchScope, psiSubstitutor);
        }
        return null;
    }

    private static PsiSubstitutor getSuperSubstitutorWithCaching(PsiClass psiClass, PsiClass psiClass2, GlobalSearchScope globalSearchScope, PsiSubstitutor psiSubstitutor) {
        if (psiClass == null) {
            $$$reportNull$$$0(8);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(9);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(10);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(11);
        }
        PsiSubstitutor superClassSubstitutor = ScopedClassHierarchy.getSuperClassSubstitutor(psiClass2, globalSearchScope, psiClass);
        if (superClassSubstitutor == null) {
            return null;
        }
        return PsiUtil.isRawSubstitutor(psiClass2, psiSubstitutor) ? createRawSubstitutor(psiClass) : composeSubstitutors(psiSubstitutor, superClassSubstitutor, psiClass);
    }

    static PsiSubstitutor createRawSubstitutor(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(12);
        }
        PsiSubstitutor psiSubstitutorCreateRawSubstitutor = JavaPsiFacade.getElementFactory(psiClass.getProject()).createRawSubstitutor(psiClass);
        if (psiSubstitutorCreateRawSubstitutor == null) {
            $$$reportNull$$$0(13);
        }
        return psiSubstitutorCreateRawSubstitutor;
    }

    private static PsiSubstitutor composeSubstitutors(PsiSubstitutor psiSubstitutor, PsiSubstitutor psiSubstitutor2, PsiClass psiClass) {
        PsiType psiTypeSubstitute;
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        Map<PsiTypeParameter, PsiType> substitutionMap = psiSubstitutor.getSubstitutionMap();
        Map<PsiTypeParameter, PsiType> substitutionMap2 = psiSubstitutor2.getSubstitutionMap();
        for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(psiClass)) {
            if (substitutionMap.containsKey(psiTypeParameter) || substitutionMap2.containsKey(psiTypeParameter)) {
                final PsiType psiTypeSubstitute2 = psiSubstitutor2.substitute(psiTypeParameter);
                PsiClass psiClassResolveClassInClassTypeOnly = PsiCapturedWildcardType.isCapture() ? PsiUtil.resolveClassInClassTypeOnly(psiTypeSubstitute2) : null;
                if ((psiClassResolveClassInClassTypeOnly instanceof PsiTypeParameter) && psiClassResolveClassInClassTypeOnly != psiTypeParameter) {
                    psiTypeSubstitute = psiSubstitutor.substituteWithBoundsPromotion((PsiTypeParameter) psiClassResolveClassInClassTypeOnly);
                    if (psiTypeSubstitute != null && psiTypeSubstitute2.getAnnotations().length > 0) {
                        final PsiAnnotation[] annotations = psiTypeSubstitute.getAnnotations();
                        psiTypeSubstitute = psiTypeSubstitute.annotate(new TypeAnnotationProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaClassSupersImpl.1
                            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/JavaClassSupersImpl$1", "getAnnotations"));
                            }

                            @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
                            public PsiAnnotation[] getAnnotations() {
                                PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) ArrayUtil.mergeArrays(psiTypeSubstitute2.getAnnotations(), annotations);
                                if (psiAnnotationArr == null) {
                                    $$$reportNull$$$0(0);
                                }
                                return psiAnnotationArr;
                            }
                        });
                    }
                } else {
                    psiTypeSubstitute = psiSubstitutor.substitute(psiTypeSubstitute2);
                }
                psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, psiTypeSubstitute);
            }
        }
        if (psiSubstitutorPut == null) {
            $$$reportNull$$$0(14);
        }
        return psiSubstitutorPut;
    }

    private static PsiSubstitutor processTypeParameter(PsiTypeParameter psiTypeParameter, GlobalSearchScope globalSearchScope, PsiClass psiClass, Set<? super PsiTypeParameter> set, PsiSubstitutor psiSubstitutor) {
        if (psiTypeParameter.getManager().areElementsEquivalent(psiTypeParameter, psiClass)) {
            return PsiSubstitutor.EMPTY;
        }
        if (!set.add(psiTypeParameter)) {
            return null;
        }
        for (PsiClassType psiClassType : psiTypeParameter.getExtendsListTypes()) {
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element != null) {
                if (element instanceof PsiTypeParameter) {
                    PsiSubstitutor psiSubstitutorProcessTypeParameter = processTypeParameter((PsiTypeParameter) element, globalSearchScope, psiClass, set, psiSubstitutor);
                    if (psiSubstitutorProcessTypeParameter != null) {
                        return psiSubstitutorProcessTypeParameter;
                    }
                } else {
                    PsiSubstitutor superSubstitutorWithCaching = getSuperSubstitutorWithCaching(psiClass, element, globalSearchScope, classResolveResultResolveGenerics.getSubstitutor());
                    if (superSubstitutorWithCaching != null) {
                        return composeSubstitutors(psiSubstitutor, superSubstitutorWithCaching, psiClass);
                    }
                }
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.JavaClassSupers
    public void reportHierarchyInconsistency(final PsiClass psiClass, PsiClass psiClass2) {
        if (psiClass == null) {
            $$$reportNull$$$0(15);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(16);
        }
        if (ourReportedInconsistencies.add(psiClass2.getQualifiedName() + "/" + psiClass.getQualifiedName()) || ApplicationManager.getApplication().isUnitTestMode()) {
            final StringBuilder sb = new StringBuilder("superClassSubstitutor requested when derived doesn't extend super:\n");
            sb.append("Super: " + classInfo(psiClass));
            sb.append("Derived: " + classInfo(psiClass2));
            sb.append("isInheritor: via util=" + InheritanceUtil.isInheritorOrSelf(psiClass2, psiClass, true) + ", directly=" + psiClass2.isInheritor(psiClass, true) + "\n");
            sb.append("Super in derived's scope: " + PsiSearchScopeUtil.isInScope(psiClass2.getResolveScope(), (PsiElement) psiClass) + "\n");
            if (!InheritanceUtil.processSupers(psiClass2, false, (Processor<? super PsiClass>) new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaClassSupersImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Processor
                public final boolean process(Object obj) {
                    return JavaClassSupersImpl.lambda$reportHierarchyInconsistency$0(psiClass, (PsiClass) obj);
                }
            })) {
                sb.append("Plain derived's supers contain Super\n");
            }
            sb.append("Hierarchy:\n");
            new ScopedClassHierarchy(psiClass2, psiClass2.getResolveScope()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaClassSupersImpl.2
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ModuleXmlParser.TYPE, "org/jetbrains/kotlin/com/intellij/psi/impl/JavaClassSupersImpl$2", "visitType"));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ScopedClassHierarchy
                void visitType(PsiClassType psiClassType, Map<PsiClass, PsiClassType.ClassResolveResult> map) {
                    if (psiClassType == null) {
                        $$$reportNull$$$0(0);
                    }
                    PsiClass psiClassResolve = psiClassType.resolve();
                    sb.append("  each: ");
                    sb.append(psiClassResolve == null ? "unresolved " + psiClassType : JavaClassSupersImpl.classInfo(psiClassResolve));
                    super.visitType(psiClassType, map);
                }
            }.visitType(JavaPsiFacade.getElementFactory(psiClass2.getProject()).createType(psiClass2, PsiSubstitutor.EMPTY), new HashMap());
            LOG.error(sb);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String classInfo(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(17);
        }
        String str = (psiClass.getQualifiedName() + "(" + psiClass.getClass().getName() + "; " + PsiUtilCore.getVirtualFile(psiClass) + ");\n") + "    extends: ";
        for (PsiClassType psiClassType : psiClass.getExtendsListTypes()) {
            str = str + "    " + psiClassType + " (" + psiClassType.getClass().getName() + "; " + psiClassType.resolve() + ") ";
        }
        String str2 = str + "\n    implements: ";
        for (PsiClassType psiClassType2 : psiClass.getImplementsListTypes()) {
            str2 = str2 + "    " + psiClassType2 + " (" + psiClassType2.getClass().getName() + "; " + psiClassType2.resolve() + ") ";
        }
        String str3 = str2 + "\n";
        if (str3 == null) {
            $$$reportNull$$$0(18);
        }
        return str3;
    }
}
