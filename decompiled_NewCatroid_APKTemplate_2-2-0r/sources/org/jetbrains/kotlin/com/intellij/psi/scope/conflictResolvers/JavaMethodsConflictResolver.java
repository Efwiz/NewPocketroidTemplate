package org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaVersionService;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.GenericsUtil;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImmediateClassType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;
import org.jetbrains.kotlin.com.intellij.util.containers.FactoryMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class JavaMethodsConflictResolver implements PsiConflictResolver {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) JavaMethodsConflictResolver.class);
    private final PsiType[] myActualParameterTypes;
    private final PsiElement myArgumentsList;
    private final PsiFile myContainingFile;
    protected LanguageLevel myLanguageLevel;

    private enum Specifics {
        FIRST,
        SECOND,
        NEITHER
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 13 || i == 31 || i == 38) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 13 || i == 31 || i == 38) ? 2 : 3];
        switch (i) {
            case 1:
            case 25:
            case 34:
                objArr[0] = "languageLevel";
                break;
            case 2:
                objArr[0] = "containingFile";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 39:
                objArr[0] = "conflicts";
                break;
            case 8:
            case 19:
            case 20:
            case 32:
                objArr[0] = "method";
                break;
            case 9:
            case 18:
                objArr[0] = "conflict";
                break;
            case 13:
            case 31:
            case 38:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/scope/conflictResolvers/JavaMethodsConflictResolver";
                break;
            case 21:
                objArr[0] = "info1";
                break;
            case 22:
                objArr[0] = "info2";
                break;
            case 23:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 24:
                objArr[0] = "method1";
                break;
            case 26:
                objArr[0] = "methodSubstitutor1";
                break;
            case 27:
                objArr[0] = "method2";
                break;
            case 28:
                objArr[0] = "types2AtSite";
                break;
            case 29:
                objArr[0] = "siteSubstitutor1";
                break;
            case 30:
            case 36:
                objArr[0] = "types1";
                break;
            case 33:
                objArr[0] = "siteSubstitutor";
                break;
            case 35:
                objArr[0] = "typeParameters";
                break;
            case 37:
                objArr[0] = "types2";
                break;
            default:
                objArr[0] = "argumentsList";
                break;
        }
        if (i == 13) {
            objArr[1] = "getSubstitutor";
        } else if (i == 31) {
            objArr[1] = "typesAtSite";
        } else if (i != 38) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/conflictResolvers/JavaMethodsConflictResolver";
        } else {
            objArr[1] = "calculateMethodSubstitutor";
        }
        switch (i) {
            case 3:
                objArr[2] = "resolveConflict";
                break;
            case 4:
                objArr[2] = "guardedOverloadResolution";
                break;
            case 5:
                objArr[2] = "checkPotentiallyCompatibleMethods";
                break;
            case 6:
            case 7:
                objArr[2] = "checkSpecifics";
                break;
            case 8:
            case 9:
                objArr[2] = "nonComparable";
                break;
            case 10:
                objArr[2] = "checkAccessStaticLevels";
                break;
            case 11:
            case 12:
                objArr[2] = "checkSameSignatures";
                break;
            case 13:
            case 31:
            case 38:
                break;
            case 14:
                objArr[2] = "checkStaticMethodsOfInterfaces";
                break;
            case 15:
                objArr[2] = "checkParametersNumber";
                break;
            case 16:
            case 17:
                objArr[2] = "checkApplicability";
                break;
            case 18:
                objArr[2] = "getPertinentApplicabilityLevel";
                break;
            case 19:
                objArr[2] = "getCheckAccessLevel";
                break;
            case 20:
                objArr[2] = "getCheckStaticLevel";
                break;
            case 21:
            case 22:
                objArr[2] = "isMoreSpecific";
                break;
            case 23:
                objArr[2] = "getSiteSubstitutor";
                break;
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[2] = "isApplicableTo";
                break;
            case 29:
            case 30:
                objArr[2] = "typesAtSite";
                break;
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
                objArr[2] = "calculateMethodSubstitutor";
                break;
            case 39:
                objArr[2] = "checkPrimitiveVarargs";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 31 && i != 38) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected boolean nonComparable(CandidateInfo candidateInfo, CandidateInfo candidateInfo2, boolean z) {
        if (candidateInfo == null) {
            $$$reportNull$$$0(8);
        }
        if (candidateInfo2 != null) {
            return false;
        }
        $$$reportNull$$$0(9);
        return false;
    }

    public JavaMethodsConflictResolver(PsiElement psiElement, PsiType[] psiTypeArr, LanguageLevel languageLevel, PsiFile psiFile) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        this.myArgumentsList = psiElement;
        this.myActualParameterTypes = psiTypeArr;
        this.myLanguageLevel = languageLevel;
        this.myContainingFile = psiFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver
    public final CandidateInfo resolveConflict(List<CandidateInfo> list) {
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        PsiElement psiElement = this.myArgumentsList;
        if ((psiElement instanceof PsiExpressionList) && MethodCandidateInfo.isOverloadCheck(psiElement)) {
            LOG.error("Recursive conflict resolution for:" + this.myArgumentsList.getMyParent() + "; file=" + this.myArgumentsList.getContainingFile());
        }
        return guardedOverloadResolution(list);
    }

    protected CandidateInfo guardedOverloadResolution(List<CandidateInfo> list) {
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        checkStaticMethodsOfInterfaces(list);
        if (list.size() == 1) {
            return list.get(0);
        }
        Map<MethodCandidateInfo, PsiSubstitutor> mapCreate = FactoryMap.create(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((MethodCandidateInfo) obj).getSubstitutor(false);
            }
        });
        boolean zCheckParametersNumber = checkParametersNumber(list, getActualParametersLength(), mapCreate, true);
        if (list.size() == 1) {
            return list.get(0);
        }
        checkSameSignatures(list, mapCreate);
        if (list.size() == 1) {
            return list.get(0);
        }
        checkAccessStaticLevels(list, true);
        if (list.size() == 1) {
            return list.get(0);
        }
        checkParametersNumber(list, getActualParametersLength(), mapCreate, false);
        if (list.size() == 1) {
            return list.get(0);
        }
        if (zCheckParametersNumber) {
            checkPotentiallyCompatibleMethods(list);
            if (list.size() == 1) {
                return list.get(0);
            }
        }
        int iCheckApplicability = checkApplicability(list, mapCreate);
        if (list.size() == 1) {
            return list.get(0);
        }
        if (!zCheckParametersNumber) {
            return null;
        }
        checkSpecifics(list, iCheckApplicability, mapCreate, 0);
        if (list.size() == 1) {
            return list.get(0);
        }
        checkPrimitiveVarargs(list, getActualParametersLength());
        if (list.size() == 1) {
            return list.get(0);
        }
        HashSet hashSet = new HashSet(list);
        if (hashSet.size() == 1) {
            return (CandidateInfo) hashSet.iterator().next();
        }
        return null;
    }

    private static void checkPotentiallyCompatibleMethods(List<CandidateInfo> list) {
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<CandidateInfo> it = list.iterator();
        while (it.hasNext()) {
            CandidateInfo next = it.next();
            if (next instanceof MethodCandidateInfo) {
                ThreeState threeStateIsPotentiallyCompatible = ((MethodCandidateInfo) next).isPotentiallyCompatible();
                if (threeStateIsPotentiallyCompatible == ThreeState.NO) {
                    it.remove();
                } else if (threeStateIsPotentiallyCompatible == ThreeState.UNSURE) {
                    arrayList.add(next);
                }
            }
        }
        if (list.size() > arrayList.size()) {
            list.removeAll(arrayList);
        }
    }

    protected void checkSpecifics(List<CandidateInfo> list, int i) {
        if (list == null) {
            $$$reportNull$$$0(6);
        }
        checkSpecifics(list, i, null, 0);
    }

    protected void checkSpecifics(List<CandidateInfo> list, int i, Map<MethodCandidateInfo, PsiSubstitutor> map, int i2) {
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        boolean z = i > 1;
        int size = list.size();
        if (z) {
            CandidateInfo[] candidateInfoArr = (CandidateInfo[]) list.toArray(CandidateInfo.EMPTY_ARRAY);
            for (int i3 = 1; i3 < size; i3++) {
                CandidateInfo candidateInfo = candidateInfoArr[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    ProgressManager.checkCanceled();
                    CandidateInfo candidateInfo2 = candidateInfoArr[i4];
                    if (!nonComparable(candidateInfo, candidateInfo2, i == 3)) {
                        int i5 = AnonymousClass1.$SwitchMap$com$intellij$psi$scope$conflictResolvers$JavaMethodsConflictResolver$Specifics[isMoreSpecific((MethodCandidateInfo) candidateInfo, (MethodCandidateInfo) candidateInfo2, i, map, i2).ordinal()];
                        if (i5 == 1) {
                            list.remove(candidateInfo2);
                        } else if (i5 == 2) {
                            list.remove(candidateInfo);
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$scope$conflictResolvers$JavaMethodsConflictResolver$Specifics;

        static {
            int[] iArr = new int[Specifics.values().length];
            $SwitchMap$com$intellij$psi$scope$conflictResolvers$JavaMethodsConflictResolver$Specifics = iArr;
            try {
                iArr[Specifics.FIRST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$scope$conflictResolvers$JavaMethodsConflictResolver$Specifics[Specifics.SECOND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$scope$conflictResolvers$JavaMethodsConflictResolver$Specifics[Specifics.NEITHER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected static void checkAccessStaticLevels(List<? extends CandidateInfo> list, boolean z) {
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        int size = list.size();
        int[] iArr = new int[size];
        int iMax = -1;
        int i = 0;
        for (CandidateInfo candidateInfo : list) {
            ProgressManager.checkCanceled();
            MethodCandidateInfo methodCandidateInfo = (MethodCandidateInfo) candidateInfo;
            int checkAccessLevel = z ? getCheckAccessLevel(methodCandidateInfo) : getCheckStaticLevel(methodCandidateInfo);
            iArr[i] = checkAccessLevel;
            iMax = Math.max(iMax, checkAccessLevel);
            i++;
        }
        for (int i2 = size - 1; i2 >= 0; i2--) {
            if (iArr[i2] < iMax) {
                list.remove(i2);
            }
        }
    }

    protected void checkSameSignatures(List<? extends CandidateInfo> list) {
        if (list == null) {
            $$$reportNull$$$0(11);
        }
        checkSameSignatures(list, null);
    }

    /* JADX WARN: Code duplicated, block: B:47:0x00fd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:48:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:75:0x011e A[SYNTHETIC] */
    protected void checkSameSignatures(List<? extends CandidateInfo> list, Map<MethodCandidateInfo, PsiSubstitutor> map) {
        int i;
        PsiElement currentFileResolveScope;
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        HashMap map2 = new HashMap(list.size());
        HashSet hashSet = new HashSet();
        GlobalSearchScope resolveScope = ResolveScopeManager.getInstance(this.myContainingFile.getProject()).getResolveScope(this.myContainingFile);
        Iterator<? extends CandidateInfo> it = list.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            PsiMethod element = ((MethodCandidateInfo) it.next()).getElement();
            PsiClass containingClass = element.mo6092getContainingClass();
            if (containingClass != null && containingClass.isInterface()) {
                i = 1;
            }
            Iterator<HierarchicalMethodSignature> it2 = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(element, resolveScope).getSuperSignatures().iterator();
            while (it2.hasNext()) {
                PsiMethod method = it2.next().getMethod();
                if (i == 0) {
                    hashSet.add(method);
                } else {
                    PsiClass containingClass2 = method.mo6092getContainingClass();
                    if (containingClass2 != null && !CommonClassNames.JAVA_LANG_OBJECT.equals(containingClass2.getQualifiedName())) {
                        hashSet.add(method);
                    }
                }
            }
        }
        while (i < list.size()) {
            ProgressManager.checkCanceled();
            CandidateInfo candidateInfo = list.get(i);
            PsiMethod psiMethod = (PsiMethod) candidateInfo.getElement();
            if (!psiMethod.hasModifierProperty("static") && hashSet.contains(psiMethod)) {
                list.remove(i);
            } else {
                PsiClass containingClass3 = psiMethod.mo6092getContainingClass();
                MethodSignature signature = psiMethod.getSignature(getSubstitutor((MethodCandidateInfo) candidateInfo, map));
                CandidateInfo candidateInfo2 = (CandidateInfo) map2.get(signature);
                if (candidateInfo2 == null) {
                    map2.put(signature, candidateInfo);
                } else {
                    PsiMethod psiMethod2 = (PsiMethod) candidateInfo2.getElement();
                    PsiClass containingClass4 = psiMethod2.mo6092getContainingClass();
                    if (containingClass3 == null || containingClass4 == null) {
                        if (psiMethod == psiMethod2) {
                            currentFileResolveScope = candidateInfo.getCurrentFileResolveScope();
                            PsiElement currentFileResolveScope2 = candidateInfo2.getCurrentFileResolveScope();
                            if (!(currentFileResolveScope instanceof PsiClass) && (currentFileResolveScope2 instanceof PsiClass) && PsiTreeUtil.isAncestor(currentFileResolveScope, currentFileResolveScope2, true) && !candidateInfo2.isAccessible()) {
                                map2.put(signature, candidateInfo);
                            }
                        }
                    } else if (containingClass3.isInterface() && CommonClassNames.JAVA_LANG_OBJECT.equals(containingClass4.getQualifiedName())) {
                        map2.put(signature, candidateInfo);
                    } else if (containingClass4.isInterface() && CommonClassNames.JAVA_LANG_OBJECT.equals(containingClass3.getQualifiedName())) {
                        list.remove(candidateInfo);
                    } else if (psiMethod == psiMethod2) {
                        currentFileResolveScope = candidateInfo.getCurrentFileResolveScope();
                        PsiElement currentFileResolveScope3 = candidateInfo2.getCurrentFileResolveScope();
                        if (!(currentFileResolveScope instanceof PsiClass)) {
                        }
                    }
                }
                i++;
            }
            i--;
            i++;
        }
    }

    private static PsiSubstitutor getSubstitutor(MethodCandidateInfo methodCandidateInfo, Map<MethodCandidateInfo, PsiSubstitutor> map) {
        PsiSubstitutor substitutor = map != null ? map.get(methodCandidateInfo) : methodCandidateInfo.getSubstitutor(false);
        if (substitutor == null) {
            $$$reportNull$$$0(13);
        }
        return substitutor;
    }

    private void checkStaticMethodsOfInterfaces(List<CandidateInfo> list) {
        PsiClass containingClass;
        PsiClass qualifiedClass;
        if (list == null) {
            $$$reportNull$$$0(14);
        }
        if (this.myArgumentsList instanceof PsiExpressionList) {
            Iterator<CandidateInfo> it = list.iterator();
            while (it.hasNext()) {
                CandidateInfo next = it.next();
                if (next instanceof MethodCandidateInfo) {
                    PsiMethod element = ((MethodCandidateInfo) next).getElement();
                    if (element.hasModifierProperty("static")) {
                        PsiElement currentFileResolveScope = next.getCurrentFileResolveScope();
                        if (!(currentFileResolveScope instanceof PsiImportStaticStatement) && (containingClass = element.mo6092getContainingClass()) != null && containingClass.isInterface() && (qualifiedClass = getQualifiedClass(currentFileResolveScope)) != null && !containingClass.getManager().areElementsEquivalent(containingClass, qualifiedClass)) {
                            it.remove();
                        }
                    }
                }
            }
        }
    }

    private PsiClass getQualifiedClass(PsiElement psiElement) {
        PsiElement myParent = this.myArgumentsList.getMyParent();
        if (!(myParent instanceof PsiMethodCallExpression)) {
            return null;
        }
        PsiExpression qualifierExpression = ((PsiMethodCallExpression) myParent).getMethodExpression().getQualifierExpression();
        if (qualifierExpression instanceof PsiReferenceExpression) {
            PsiElement psiElementResolve = ((PsiReferenceExpression) qualifierExpression).resolve();
            if (psiElementResolve instanceof PsiClass) {
                return (PsiClass) psiElementResolve;
            }
        } else if (qualifierExpression == null && (psiElement instanceof PsiClass)) {
            return (PsiClass) psiElement;
        }
        if (qualifierExpression != null) {
            return PsiUtil.resolveClassInType(qualifierExpression.getType());
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0060  */
    /* JADX WARN: Code duplicated, block: B:46:0x00b8  */
    private boolean checkParametersNumber(List<? extends CandidateInfo> list, int i, Map<MethodCandidateInfo, PsiSubstitutor> map, boolean z) {
        boolean z2;
        MethodCandidateInfo methodCandidateInfo;
        PsiMethod element;
        if (list == null) {
            $$$reportNull$$$0(15);
        }
        int i2 = 0;
        boolean z3 = false;
        IntArrayList intArrayList = null;
        while (i2 < list.size()) {
            ProgressManager.checkCanceled();
            CandidateInfo candidateInfo = list.get(i2);
            if (z && !candidateInfo.isStaticsScopeCorrect()) {
                return true;
            }
            if (candidateInfo instanceof MethodCandidateInfo) {
                MethodCandidateInfo methodCandidateInfo2 = (MethodCandidateInfo) candidateInfo;
                PsiMethod element2 = methodCandidateInfo2.getElement();
                int parametersCount = element2.getParameterList().getParametersCount();
                if (!this.myLanguageLevel.isAtLeast(LanguageLevel.JDK_1_8) ? !element2.isVarArgs() : !methodCandidateInfo2.isVarargs()) {
                    z2 = false;
                } else if (parametersCount - 1 <= i) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2 || parametersCount == i) {
                    if (intArrayList != null) {
                        for (int size = intArrayList.size() - 1; size >= 0; size--) {
                            int i3 = intArrayList.getInt(size);
                            if (z && z2 && (element = (methodCandidateInfo = (MethodCandidateInfo) list.get(i3)).getElement()) != element2) {
                                if (!MethodSignatureUtil.isSubsignature(element.getSignature(map.get(methodCandidateInfo)), element2.getSignature(map.get(candidateInfo)))) {
                                    list.remove(i3);
                                    i2--;
                                }
                            } else {
                                list.remove(i3);
                                i2--;
                            }
                        }
                        intArrayList = null;
                    }
                    z3 = true;
                } else if (z3) {
                    list.remove(i2);
                    i2--;
                } else {
                    if (intArrayList == null) {
                        intArrayList = new IntArrayList(list.size() - i2);
                    }
                    intArrayList.add(i2);
                }
            }
            i2++;
        }
        return z3;
    }

    public int checkApplicability(List<CandidateInfo> list) {
        if (list == null) {
            $$$reportNull$$$0(16);
        }
        return checkApplicability(list, null);
    }

    public int checkApplicability(List<CandidateInfo> list, Map<MethodCandidateInfo, PsiSubstitutor> map) {
        if (list == null) {
            $$$reportNull$$$0(17);
        }
        boolean z = false;
        int i = 0;
        for (CandidateInfo candidateInfo : list) {
            ProgressManager.checkCanceled();
            int pertinentApplicabilityLevel = getPertinentApplicabilityLevel((MethodCandidateInfo) candidateInfo, map);
            if (i > 0 && i != pertinentApplicabilityLevel) {
                z = true;
            }
            if (pertinentApplicabilityLevel > i) {
                i = pertinentApplicabilityLevel;
            }
        }
        if (z) {
            Iterator<CandidateInfo> it = list.iterator();
            while (it.hasNext()) {
                ProgressManager.checkCanceled();
                if (getPertinentApplicabilityLevel((MethodCandidateInfo) it.next(), map) < i) {
                    it.remove();
                }
            }
        }
        return i;
    }

    protected int getPertinentApplicabilityLevel(MethodCandidateInfo methodCandidateInfo, Map<MethodCandidateInfo, PsiSubstitutor> map) {
        if (methodCandidateInfo == null) {
            $$$reportNull$$$0(18);
        }
        return methodCandidateInfo.getPertinentApplicabilityLevel(map);
    }

    private static int getCheckAccessLevel(MethodCandidateInfo methodCandidateInfo) {
        if (methodCandidateInfo == null) {
            $$$reportNull$$$0(19);
        }
        return methodCandidateInfo.isAccessible() ? 1 : 0;
    }

    private static int getCheckStaticLevel(MethodCandidateInfo methodCandidateInfo) {
        if (methodCandidateInfo == null) {
            $$$reportNull$$$0(20);
        }
        return (!(methodCandidateInfo.getCurrentFileResolveScope() instanceof PsiImportStaticStatement) ? 1 : 0) | ((methodCandidateInfo.isStaticsScopeCorrect() ? 1 : 0) << 1);
    }

    private int getActualParametersLength() {
        PsiType[] psiTypeArr = this.myActualParameterTypes;
        if (psiTypeArr == null) {
            Logger logger = LOG;
            PsiElement psiElement = this.myArgumentsList;
            logger.assertTrue(psiElement instanceof PsiExpressionList, psiElement);
            return ((PsiExpressionList) this.myArgumentsList).getExpressionCount();
        }
        return psiTypeArr.length;
    }

    private Specifics isMoreSpecific(MethodCandidateInfo methodCandidateInfo, MethodCandidateInfo methodCandidateInfo2, int i, Map<MethodCandidateInfo, PsiSubstitutor> map, int i2) {
        PsiSubstitutor psiSubstitutor;
        PsiSubstitutor psiSubstitutor2;
        PsiClass psiClass;
        PsiMethod psiMethod;
        boolean z;
        boolean z2;
        int i3;
        PsiParameter[] psiParameterArr;
        PsiMethod psiMethod2;
        boolean z3;
        if (methodCandidateInfo == null) {
            $$$reportNull$$$0(21);
        }
        if (methodCandidateInfo2 == null) {
            $$$reportNull$$$0(22);
        }
        PsiMethod element = methodCandidateInfo.getElement();
        PsiMethod element2 = methodCandidateInfo2.getElement();
        PsiClass containingClass = element.mo6092getContainingClass();
        PsiClass containingClass2 = element2.mo6092getContainingClass();
        PsiParameter[] parameters = element.getParameterList().getParameters();
        PsiParameter[] parameters2 = element2.getParameterList().getParameters();
        PsiTypeParameter[] typeParameters = element.getTypeParameters();
        PsiTypeParameter[] typeParameters2 = element2.getTypeParameters();
        PsiSubstitutor substitutor = getSubstitutor(methodCandidateInfo, map);
        PsiSubstitutor substitutor2 = getSubstitutor(methodCandidateInfo2, map);
        int iMax = Math.max(Math.max(parameters.length, parameters2.length), (this.myLanguageLevel.isAtLeast(LanguageLevel.JDK_1_8) && (element.isVarArgs() || element2.isVarArgs())) ? getActualParametersLength() : 0);
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(iMax);
        PsiType[] psiTypeArrCreateArray2 = PsiType.createArray(iMax);
        boolean[] zArr = new boolean[iMax];
        boolean z4 = i == 2;
        int i4 = 0;
        while (true) {
            if (i4 >= iMax) {
                break;
            }
            ProgressManager.checkCanceled();
            int i5 = iMax;
            PsiType type = parameters.length > 0 ? parameters[Math.min(i4, parameters.length - 1)].getType() : null;
            PsiType type2 = parameters2.length > 0 ? parameters2[Math.min(i4, parameters2.length - 1)].getType() : null;
            if (z4) {
                z3 = z4;
                boolean z5 = type instanceof PsiEllipsisType;
                if (z5) {
                    psiMethod2 = element;
                    if (type2 instanceof PsiEllipsisType) {
                        psiParameterArr = parameters;
                        if (parameters.length == parameters2.length && ((containingClass != null && !JavaVersionService.getInstance().isAtLeast(containingClass, JavaSdkVersion.JDK_1_7)) || ((PsiArrayType) type).getComponentType().equalsToText(CommonClassNames.JAVA_LANG_OBJECT) || ((PsiArrayType) type2).getComponentType().equalsToText(CommonClassNames.JAVA_LANG_OBJECT))) {
                            type = ((PsiEllipsisType) type).toArrayType();
                            type2 = ((PsiEllipsisType) type2).toArrayType();
                        }
                    } else {
                        psiParameterArr = parameters;
                    }
                } else {
                    psiParameterArr = parameters;
                    psiMethod2 = element;
                }
                if (z5) {
                    type = ((PsiArrayType) type).getComponentType();
                }
                if (type2 instanceof PsiEllipsisType) {
                    type2 = ((PsiArrayType) type2).getComponentType();
                }
                zArr[i4] = true;
            } else {
                psiParameterArr = parameters;
                psiMethod2 = element;
                z3 = z4;
            }
            psiTypeArrCreateArray[i4] = type;
            psiTypeArrCreateArray2[i4] = type2;
            i4++;
            z4 = z3;
            iMax = i5;
            element = psiMethod2;
            element2 = element2;
            parameters = psiParameterArr;
        }
        PsiMethod psiMethod3 = element;
        PsiMethod psiMethod4 = element2;
        boolean z6 = z4;
        boolean[] zArr2 = new boolean[2];
        PsiElement psiElement = this.myArgumentsList;
        PsiExpression[] expressions = psiElement instanceof PsiExpressionList ? ((PsiExpressionList) psiElement).getExpressions() : null;
        int i6 = 0;
        boolean z7 = true;
        while (i6 < psiTypeArrCreateArray.length) {
            ProgressManager.checkCanceled();
            if (!zArr[i6]) {
                PsiExpression psiExpression = (expressions == null || i6 >= expressions.length) ? null : expressions[i6];
                PsiType psiType = (this.myActualParameterTypes == null || (i3 = i6 + i2) >= getActualParametersLength()) ? null : this.myActualParameterTypes[i3];
                if (psiExpression != null || psiType != null) {
                    if (isBoxingUsed(substitutor.substitute(psiTypeArrCreateArray[i6]), psiType, psiExpression)) {
                        zArr2[0] = true;
                        z = true;
                    } else {
                        z = false;
                    }
                    if (isBoxingUsed(substitutor2.substitute(psiTypeArrCreateArray2[i6]), psiType, psiExpression)) {
                        zArr2[1] = true;
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    z7 &= z == z2;
                }
            }
            i6++;
        }
        boolean z8 = zArr2[0];
        if (!z8 && zArr2[1]) {
            return Specifics.FIRST;
        }
        if (z8 && !zArr2[1]) {
            return Specifics.SECOND;
        }
        if (z7) {
            PsiSubstitutor psiSubstitutorPutAll = getSiteSubstitutor(methodCandidateInfo).putAll(getSiteSubstitutor(methodCandidateInfo2));
            PsiType[] psiTypeArrTypesAtSite = typesAtSite(psiTypeArrCreateArray2, psiSubstitutorPutAll);
            PsiType[] psiTypeArrTypesAtSite2 = typesAtSite(psiTypeArrCreateArray, psiSubstitutorPutAll);
            psiSubstitutor2 = substitutor;
            PsiSubstitutor psiSubstitutorCalculateMethodSubstitutor = calculateMethodSubstitutor(typeParameters, psiMethod3, psiSubstitutorPutAll, psiTypeArrCreateArray, psiTypeArrTypesAtSite, this.myLanguageLevel);
            psiSubstitutor = substitutor2;
            psiClass = containingClass;
            boolean zIsApplicableTo = isApplicableTo(psiTypeArrTypesAtSite, psiMethod3, this.myLanguageLevel, z6, psiSubstitutorCalculateMethodSubstitutor, psiMethod4, psiSubstitutorPutAll);
            PsiSubstitutor psiSubstitutorCalculateMethodSubstitutor2 = calculateMethodSubstitutor(typeParameters2, psiMethod4, psiSubstitutorPutAll, psiTypeArrCreateArray2, psiTypeArrTypesAtSite2, this.myLanguageLevel);
            boolean zIsApplicableTo2 = isApplicableTo(psiTypeArrTypesAtSite2, psiMethod4, this.myLanguageLevel, z6, psiSubstitutorCalculateMethodSubstitutor2, psiMethod3, psiSubstitutorPutAll);
            if (!this.myLanguageLevel.isAtLeast(LanguageLevel.JDK_1_8)) {
                boolean zIsTypeArgumentsApplicable = GenericsUtil.isTypeArgumentsApplicable(typeParameters, psiSubstitutorCalculateMethodSubstitutor, this.myArgumentsList, !zIsApplicableTo2);
                boolean zIsTypeArgumentsApplicable2 = GenericsUtil.isTypeArgumentsApplicable(typeParameters2, psiSubstitutorCalculateMethodSubstitutor2, this.myArgumentsList, !zIsApplicableTo);
                if (!zIsTypeArgumentsApplicable) {
                    zIsApplicableTo = false;
                }
                if (!zIsTypeArgumentsApplicable2) {
                    zIsApplicableTo2 = false;
                }
            }
            if (!zIsApplicableTo && !zIsApplicableTo2) {
                psiMethod = psiMethod3;
                psiMethod4 = psiMethod4;
            } else {
                if (zIsApplicableTo && !zIsApplicableTo2) {
                    return Specifics.SECOND;
                }
                if (!zIsApplicableTo) {
                    return Specifics.FIRST;
                }
                psiMethod = psiMethod3;
                boolean z9 = psiMethod.hasModifierProperty("abstract") || psiMethod.hasModifierProperty("default");
                boolean z10 = psiMethod4.hasModifierProperty("abstract") || psiMethod4.hasModifierProperty("default");
                if (z9 && !z10) {
                    return Specifics.SECOND;
                }
                if (z10 && !z9) {
                    return Specifics.FIRST;
                }
                if (z9 && MethodSignatureUtil.areOverrideEquivalent(psiMethod, psiMethod4)) {
                    PsiType psiTypeSubstitute = psiSubstitutorPutAll.substitute(psiMethod.getReturnType());
                    PsiType psiTypeSubstitute2 = psiSubstitutorPutAll.substitute(psiMethod4.getReturnType());
                    if (psiTypeSubstitute != null && psiTypeSubstitute2 != null && psiTypeSubstitute.isAssignableFrom(psiTypeSubstitute2)) {
                        return Specifics.SECOND;
                    }
                    return Specifics.FIRST;
                }
            }
        } else {
            psiSubstitutor = substitutor2;
            psiSubstitutor2 = substitutor;
            psiClass = containingClass;
            psiMethod = psiMethod3;
            psiMethod4 = psiMethod4;
            if (z6) {
                PsiType psiTypeSubstitute3 = psiSubstitutor2.substitute(psiTypeArrCreateArray[psiTypeArrCreateArray.length - 1]);
                PsiType psiTypeSubstitute4 = psiSubstitutor.substitute(psiTypeArrCreateArray2[psiTypeArrCreateArray.length - 1]);
                boolean zIsAssignable = TypeConversionUtil.isAssignable(psiTypeSubstitute4, psiTypeSubstitute3);
                boolean zIsAssignable2 = TypeConversionUtil.isAssignable(psiTypeSubstitute3, psiTypeSubstitute4);
                if (zIsAssignable && !zIsAssignable2) {
                    return Specifics.FIRST;
                }
                if (zIsAssignable2 && !zIsAssignable) {
                    return Specifics.SECOND;
                }
            }
        }
        PsiClass psiClass2 = psiClass;
        if (psiClass2 != containingClass2 && (psiMethod.hasModifierProperty("static") || psiMethod4.hasModifierProperty("static"))) {
            if (containingClass2.isInheritor(psiClass2, true)) {
                if (MethodSignatureUtil.isSubsignature(psiMethod.getSignature(psiSubstitutor2), psiMethod4.getSignature(psiSubstitutor))) {
                    return Specifics.SECOND;
                }
            } else if (psiClass2.isInheritor(containingClass2, true) && MethodSignatureUtil.isSubsignature(psiMethod4.getSignature(psiSubstitutor), psiMethod.getSignature(psiSubstitutor2))) {
                return Specifics.FIRST;
            }
        }
        boolean zIsVarargs = methodCandidateInfo.isVarargs();
        if (zIsVarargs != methodCandidateInfo2.isVarargs()) {
            return zIsVarargs ? Specifics.SECOND : Specifics.FIRST;
        }
        return Specifics.NEITHER;
    }

    private PsiSubstitutor getSiteSubstitutor(MethodCandidateInfo methodCandidateInfo) {
        if (methodCandidateInfo == null) {
            $$$reportNull$$$0(23);
        }
        PsiSubstitutor siteSubstitutor = methodCandidateInfo.getSiteSubstitutor();
        if (!this.myLanguageLevel.isAtLeast(LanguageLevel.JDK_1_8)) {
            return siteSubstitutor;
        }
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        PsiClass containingClass = methodCandidateInfo.getElement().mo6092getContainingClass();
        if (containingClass != null) {
            for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(containingClass)) {
                psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameter, siteSubstitutor.substitute(psiTypeParameter));
            }
        }
        return psiSubstitutorPut;
    }

    private static boolean isBoxingUsed(PsiType psiType, PsiType psiType2, PsiExpression psiExpression) {
        boolean zIsExpressionOfPrimitiveType;
        ProgressManager.checkCanceled();
        if (psiType2 != null) {
            zIsExpressionOfPrimitiveType = psiType2 instanceof PsiPrimitiveType;
        } else {
            zIsExpressionOfPrimitiveType = PsiPolyExpressionUtil.isExpressionOfPrimitiveType(psiExpression);
        }
        return (psiType instanceof PsiPrimitiveType) != zIsExpressionOfPrimitiveType;
    }

    private boolean isApplicableTo(PsiType[] psiTypeArr, PsiMethod psiMethod, final LanguageLevel languageLevel, boolean z, PsiSubstitutor psiSubstitutor, PsiMethod psiMethod2, final PsiSubstitutor psiSubstitutor2) {
        if (psiMethod == null) {
            $$$reportNull$$$0(24);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(25);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(26);
        }
        if (psiMethod2 == null) {
            $$$reportNull$$$0(27);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(28);
        }
        if (languageLevel.isAtLeast(LanguageLevel.JDK_1_8) && psiMethod.getTypeParameters().length > 0) {
            PsiElement psiElement = this.myArgumentsList;
            if ((psiElement instanceof PsiExpressionList) && (psiElement.getMyParent() instanceof PsiCallExpression)) {
                return InferenceSession.isMoreSpecific(psiMethod2, psiMethod, psiSubstitutor2, ((PsiExpressionList) this.myArgumentsList).getExpressions(), this.myArgumentsList, z);
            }
        }
        return PsiUtil.getApplicabilityLevel(psiMethod, psiSubstitutor, psiTypeArr, languageLevel, false, z, new PsiUtil.ApplicabilityChecker() { // from class: org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil.ApplicabilityChecker
            public final boolean isApplicable(PsiType psiType, PsiType psiType2, boolean z2, int i) {
                return this.f$0.m6694xee0645e2(psiSubstitutor2, languageLevel, psiType, psiType2, z2, i);
            }
        }) > 1;
    }

    /* JADX INFO: renamed from: lambda$isApplicableTo$1$org-jetbrains-kotlin-com-intellij-psi-scope-conflictResolvers-JavaMethodsConflictResolver, reason: not valid java name */
    /* synthetic */ boolean m6694xee0645e2(PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel, PsiType psiType, PsiType psiType2, boolean z, int i) {
        if (psiType2 instanceof PsiClassType) {
            PsiClass psiClassResolve = ((PsiClassType) psiType2).resolve();
            if (psiClassResolve instanceof PsiTypeParameter) {
                psiType2 = new PsiImmediateClassType(psiClassResolve, psiSubstitutor);
            }
        }
        return languageLevel.isAtLeast(LanguageLevel.JDK_1_8) ? isTypeMoreSpecific(psiType, psiType2, i) : TypeConversionUtil.isAssignable(psiType, psiType2, z);
    }

    private boolean isTypeMoreSpecific(PsiType psiType, PsiType psiType2, int i) {
        if (TypeConversionUtil.isAssignable(psiType, psiType2, false)) {
            return true;
        }
        PsiElement psiElement = this.myArgumentsList;
        if (psiElement instanceof PsiExpressionList) {
            PsiExpression[] expressions = ((PsiExpressionList) psiElement).getExpressions();
            if (i < expressions.length) {
                return isFunctionalTypeMoreSpecific(expressions[i], psiType2, psiType);
            }
        }
        return false;
    }

    private static PsiType[] typesAtSite(PsiType[] psiTypeArr, PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(29);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(30);
        }
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(psiTypeArr.length);
        for (int i = 0; i < psiTypeArr.length; i++) {
            psiTypeArrCreateArray[i] = psiSubstitutor.substitute(psiTypeArr[i]);
        }
        if (psiTypeArrCreateArray == null) {
            $$$reportNull$$$0(31);
        }
        return psiTypeArrCreateArray;
    }

    private static PsiSubstitutor calculateMethodSubstitutor(PsiTypeParameter[] psiTypeParameterArr, PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, LanguageLevel languageLevel) {
        if (psiMethod == null) {
            $$$reportNull$$$0(32);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(33);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(34);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(35);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(36);
        }
        if (psiTypeArr2 == null) {
            $$$reportNull$$$0(37);
        }
        PsiSubstitutor psiSubstitutorInferTypeArguments = PsiResolveHelper.SERVICE.getInstance(psiMethod.getProject()).inferTypeArguments(psiTypeParameterArr, psiTypeArr, psiTypeArr2, languageLevel);
        Iterator<PsiTypeParameter> it = PsiUtil.typeParametersIterable(psiMethod).iterator();
        while (it.hasNext()) {
            PsiTypeParameter next = it.next();
            ProgressManager.checkCanceled();
            LOG.assertTrue(next != null);
            if (!psiSubstitutorInferTypeArguments.getSubstitutionMap().containsKey(next)) {
                PsiType psiTypeSubstitute = psiSubstitutor.substitute(next);
                if ((psiTypeSubstitute instanceof PsiClassType) && next.getOwner() == psiMethod) {
                    PsiClass psiClassResolve = ((PsiClassType) psiTypeSubstitute).resolve();
                    if ((psiClassResolve instanceof PsiTypeParameter) && ((PsiTypeParameter) psiClassResolve).getOwner() == psiMethod) {
                        psiTypeSubstitute = TypeConversionUtil.erasure(psiTypeSubstitute, psiSubstitutor);
                    }
                }
                psiSubstitutorInferTypeArguments = psiSubstitutorInferTypeArguments.put(next, psiTypeSubstitute);
            } else {
                PsiType psiTypeSubstitute2 = psiSubstitutorInferTypeArguments.substitute(next);
                if (psiTypeSubstitute2 instanceof PsiClassType) {
                    PsiClass psiClassResolve2 = ((PsiClassType) psiTypeSubstitute2).resolve();
                    if (psiClassResolve2 instanceof PsiTypeParameter) {
                        psiSubstitutorInferTypeArguments = psiSubstitutorInferTypeArguments.put(next, JavaPsiFacade.getElementFactory(psiClassResolve2.getProject()).createType(psiClassResolve2, psiSubstitutor));
                    }
                }
            }
        }
        if (psiSubstitutorInferTypeArguments == null) {
            $$$reportNull$$$0(38);
        }
        return psiSubstitutorInferTypeArguments;
    }

    private void checkPrimitiveVarargs(List<? extends CandidateInfo> list, int i) {
        int i2;
        if (list == null) {
            $$$reportNull$$$0(39);
        }
        if (JavaVersionService.getInstance().isAtLeast(this.myArgumentsList, JavaSdkVersion.JDK_1_7)) {
            return;
        }
        CandidateInfo candidateInfo = null;
        for (CandidateInfo candidateInfo2 : list) {
            ProgressManager.checkCanceled();
            PsiMethod psiMethod = (PsiMethod) candidateInfo2.getElement();
            int parametersCount = psiMethod.getParameterList().getParametersCount();
            if (psiMethod.isVarArgs() && (i2 = parametersCount - 1) == i && Comparing.equal((PsiClassType) ((PsiArrayType) psiMethod.getParameterList().getParameters()[i2].getType()).getComponentType(), PsiType.getJavaLangObject(psiMethod.getManager(), GlobalSearchScope.allScope(psiMethod.getProject())))) {
                candidateInfo = candidateInfo2;
            }
        }
        if (candidateInfo != null) {
            for (CandidateInfo candidateInfo3 : list) {
                ProgressManager.checkCanceled();
                PsiMethod psiMethod2 = (PsiMethod) candidateInfo3.getElement();
                if (psiMethod2 != candidateInfo.getElement() && psiMethod2.isVarArgs()) {
                    int parametersCount2 = psiMethod2.getParameterList().getParametersCount() - 1;
                    PsiType componentType = ((PsiArrayType) psiMethod2.getParameterList().getParameters()[parametersCount2].getType()).getComponentType();
                    if (i == parametersCount2 && (componentType instanceof PsiPrimitiveType)) {
                        list.remove(candidateInfo);
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFunctionalTypeMoreSpecific(PsiExpression psiExpression, final PsiType psiType, final PsiType psiType2) {
        if (psiExpression instanceof PsiParenthesizedExpression) {
            return isFunctionalTypeMoreSpecific(((PsiParenthesizedExpression) psiExpression).getExpression(), psiType, psiType2);
        }
        if (psiExpression instanceof PsiConditionalExpression) {
            PsiConditionalExpression psiConditionalExpression = (PsiConditionalExpression) psiExpression;
            return isFunctionalTypeMoreSpecific(psiConditionalExpression.getThenExpression(), psiType, psiType2) && isFunctionalTypeMoreSpecific(psiConditionalExpression.getElseExpression(), psiType, psiType2);
        }
        if (psiExpression instanceof PsiSwitchExpression) {
            return PsiUtil.getSwitchResultExpressions((PsiSwitchExpression) psiExpression).stream().allMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return JavaMethodsConflictResolver.isFunctionalTypeMoreSpecific((PsiExpression) obj, psiType, psiType2);
                }
            });
        }
        if (!(psiExpression instanceof PsiFunctionalExpression) || ((psiExpression instanceof PsiLambdaExpression) && !((PsiLambdaExpression) psiExpression).hasFormalParameterTypes())) {
            return false;
        }
        if ((!(psiExpression instanceof PsiMethodReferenceExpression) || ((PsiMethodReferenceExpression) psiExpression).isExact()) && LambdaUtil.isFunctionalType(psiType) && LambdaUtil.isFunctionalType(psiType2) && !TypeConversionUtil.erasure(psiType2).isAssignableFrom(psiType) && !TypeConversionUtil.erasure(psiType).isAssignableFrom(psiType2)) {
            return InferenceSession.isFunctionalTypeMoreSpecificOnExpression(psiType, psiType2, psiExpression);
        }
        return false;
    }
}
