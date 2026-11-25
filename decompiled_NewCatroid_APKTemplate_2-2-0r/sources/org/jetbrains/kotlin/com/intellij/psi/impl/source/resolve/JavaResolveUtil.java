package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaVersionService;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.FileResolveScopeProvider;
import org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiExpressionListImpl;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaResolveUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 15 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 15 ? 3 : 2];
        switch (i) {
            case 1:
            case 3:
            case 5:
                objArr[0] = "member";
                break;
            case 2:
            case 4:
            case 7:
            case 8:
            case 9:
            case 16:
                objArr[0] = "place";
                break;
            case 6:
                objArr[0] = "memberClass";
                break;
            case 10:
            case 12:
                objArr[0] = "ref";
                break;
            case 11:
                objArr[0] = "result";
                break;
            case 13:
                objArr[0] = "resolver";
                break;
            case 14:
                objArr[0] = "containingFile";
                break;
            case 15:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/JavaResolveUtil";
                break;
            case 17:
                objArr[0] = "project";
                break;
            case 18:
                objArr[0] = "superClassWhichTheSuperCallMustResolveTo";
                break;
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        if (i != 15) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/JavaResolveUtil";
        } else {
            objArr[1] = "resolveWithContainingFile";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                objArr[2] = "isAccessible";
                break;
            case 5:
            case 6:
                objArr[2] = "canAccessProtectedMember";
                break;
            case 7:
                objArr[2] = "isInClassAnnotationParameterList";
                break;
            case 8:
                objArr[2] = "isInJavaDoc";
                break;
            case 9:
                objArr[2] = "getTopLevelClass";
                break;
            case 10:
            case 11:
                objArr[2] = "substituteResults";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "resolveWithContainingFile";
                break;
            case 15:
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "resolveImaginarySuperCallInThisPlace";
                break;
            case 19:
                objArr[2] = "getContainingPackage";
                break;
            default:
                objArr[2] = "getContextClass";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 15) {
            throw new IllegalStateException(str2);
        }
    }

    public static PsiClass getContextClass(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        PsiElement context = psiElement.getContext();
        while (true) {
            PsiElement psiElement2 = context;
            PsiElement psiElement3 = psiElement;
            psiElement = psiElement2;
            if (psiElement == null) {
                return null;
            }
            if ((psiElement instanceof PsiClass) && ((psiElement3 instanceof PsiMember) || (psiElement3 instanceof PsiDocComment))) {
                return (PsiClass) psiElement;
            }
            context = psiElement.getContext();
        }
    }

    public static PsiElement findParentContextOfClass(PsiElement psiElement, Class<?> cls, boolean z) {
        if (z) {
            psiElement = psiElement.getContext();
        }
        while (psiElement != null && !cls.isInstance(psiElement)) {
            psiElement = psiElement.getContext();
        }
        return psiElement;
    }

    public static boolean isAccessible(PsiMember psiMember, PsiClass psiClass, PsiModifierList psiModifierList, PsiElement psiElement, PsiClass psiClass2, PsiElement psiElement2) {
        if (psiMember == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if ((psiElement instanceof PsiDirectory) && psiModifierList != null && psiClass2 == null && PsiUtil.getAccessLevel(psiModifierList) == 2) {
            PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage((PsiDirectory) psiElement);
            return psiPackage != null && JavaPsiFacade.getInstance(psiMember.getProject()).isInPackage(psiMember, psiPackage);
        }
        return isAccessible(psiMember, psiClass, psiModifierList, psiElement, psiClass2, psiElement2, psiElement.getContainingFile());
    }

    /* JADX WARN: Failed to calculate best type for var: r0v11 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v11 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v2 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v2 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v6 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v6 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r11v0 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r11v0 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r11v0 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r11v0 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r11v1 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r11v1 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r11v2 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r11v2 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r11v3 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r11v3 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r11v4 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r11v4 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r21v0 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r21v0 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r5v1 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v1 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r5v1 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v1 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to set immutable type for var: r21v0 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r21v0 ??, new type: org.jetbrains.kotlin.com.intellij.psi.PsiClass
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:73)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setImmutableType(TypeInferenceVisitor.java:111)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:102)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:102)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r5v1 ??, new type: int
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException
        */
    public static boolean isAccessible(org.jetbrains.kotlin.com.intellij.psi.PsiMember r17, org.jetbrains.kotlin.com.intellij.psi.PsiClass r18, org.jetbrains.kotlin.com.intellij.psi.PsiModifierList r19, org.jetbrains.kotlin.com.intellij.psi.PsiElement r20, org.jetbrains.kotlin.com.intellij.psi.PsiClass r21, org.jetbrains.kotlin.com.intellij.psi.PsiElement r22, org.jetbrains.kotlin.com.intellij.psi.PsiFile r23) {
        /*
            Method dump skipped, instruction units count: 389
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveUtil.isAccessible(org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiFile):boolean");
    }

    public static boolean canAccessProtectedMember(PsiMember psiMember, PsiClass psiClass, PsiClass psiClass2, PsiClass psiClass3, boolean z) {
        if (psiMember == null) {
            $$$reportNull$$$0(5);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        while (psiClass3 != null) {
            if (InheritanceUtil.isInheritorOrSelf(psiClass3, psiClass, true) && ((psiMember instanceof PsiClass) || z || psiClass2 == null || InheritanceUtil.isInheritorOrSelf(psiClass2, psiClass3, true))) {
                return true;
            }
            psiClass3 = getContextClass(psiClass3);
        }
        return false;
    }

    private static boolean isInClassAnnotationParameterList(PsiElement psiElement, PsiClass psiClass) {
        PsiAnnotation psiAnnotation;
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        return (psiClass == null || (psiAnnotation = (PsiAnnotation) PsiTreeUtil.getContextOfType(psiElement, PsiAnnotation.class, true)) == null || !PsiTreeUtil.isAncestor(psiClass.getModifierList(), psiAnnotation, false)) ? false : true;
    }

    private static boolean ignoreReferencedElementAccessibility(PsiFile psiFile) {
        return (psiFile instanceof FileResolveScopeProvider) && ((FileResolveScopeProvider) psiFile).ignoreReferencedElementAccessibility() && !PsiImplUtil.isInServerPage(psiFile);
    }

    public static boolean isInJavaDoc(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        while (psiElement != null) {
            if (psiElement instanceof PsiDocComment) {
                return true;
            }
            if ((psiElement instanceof PsiMember) || (psiElement instanceof PsiMethodCallExpression) || (psiElement instanceof PsiFile)) {
                break;
            }
            psiElement = psiElement.getContext();
        }
        return false;
    }

    private static PsiClass getTopLevelClass(PsiElement psiElement, PsiClass psiClass) {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        PsiClass psiClass2 = null;
        Boolean boolValueOf = null;
        while (psiElement != null) {
            if ((psiElement instanceof PsiClass) && !(psiElement instanceof PsiAnonymousClass)) {
                boolean z = psiElement instanceof PsiTypeParameter;
                if (z && boolValueOf == null) {
                    boolValueOf = Boolean.valueOf(JavaVersionService.getInstance().isAtLeast(psiElement, JavaSdkVersion.JDK_1_7));
                }
                if (!z || boolValueOf.booleanValue()) {
                    psiClass2 = (PsiClass) psiElement;
                    if (psiClass != null && psiClass2.isInheritor(psiClass, true)) {
                        return psiClass2;
                    }
                }
            }
            psiElement = psiElement.getContext();
        }
        return psiClass2;
    }

    public static boolean processImplicitlyImportedPackages(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiManager psiManager) {
        PsiPackage psiPackageFindPackage = JavaPsiFacade.getInstance(psiManager.getProject()).findPackage("");
        if (psiPackageFindPackage != null && !psiPackageFindPackage.processDeclarations(psiScopeProcessor, resolveState, null, psiElement)) {
            return false;
        }
        PsiPackage psiPackageFindPackage2 = JavaPsiFacade.getInstance(psiManager.getProject()).findPackage(CommonClassNames.DEFAULT_PACKAGE);
        return psiPackageFindPackage2 == null || psiPackageFindPackage2.processDeclarations(psiScopeProcessor, resolveState, null, psiElement);
    }

    public static void substituteResults(final PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, JavaResolveResult[] javaResolveResultArr) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(10);
        }
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(11);
        }
        if (javaResolveResultArr.length > 0) {
            if (javaResolveResultArr[0].getElement() instanceof PsiClass) {
                for (int i = 0; i < javaResolveResultArr.length; i++) {
                    CandidateInfo candidateInfo = (CandidateInfo) javaResolveResultArr[i];
                    final PsiElement element = candidateInfo.getElement();
                    if ((element instanceof PsiClass) && ((PsiClass) element).hasTypeParameters()) {
                        javaResolveResultArr[i] = new CandidateInfo(candidateInfo, candidateInfo.getSubstitutor()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveUtil.1
                            private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/JavaResolveUtil$1", "getSubstitutor"));
                            }

                            @Override // org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo, org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
                            public PsiSubstitutor getSubstitutor() {
                                PsiSubstitutor psiSubstitutorPutAll = super.getSubstitutor().putAll((PsiClass) element, psiJavaCodeReferenceElement.getTypeParameters());
                                if (psiSubstitutorPutAll == null) {
                                    $$$reportNull$$$0(0);
                                }
                                return psiSubstitutorPutAll;
                            }
                        };
                    }
                }
            }
        }
    }

    public static <T extends PsiPolyVariantReference> JavaResolveResult[] resolveWithContainingFile(T t, ResolveCache.PolyVariantContextResolver<T> polyVariantContextResolver, boolean z, boolean z2, PsiFile psiFile) {
        if (t == null) {
            $$$reportNull$$$0(12);
        }
        if (polyVariantContextResolver == null) {
            $$$reportNull$$$0(13);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(14);
        }
        ResolveResult[] resolveResultArrResolveWithCaching = ResolveCache.getInstance(psiFile.getProject()).resolveWithCaching(t, polyVariantContextResolver, z, z2, psiFile);
        JavaResolveResult[] javaResolveResultArr = resolveResultArrResolveWithCaching.length == 0 ? JavaResolveResult.EMPTY_ARRAY : (JavaResolveResult[]) resolveResultArrResolveWithCaching;
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(15);
        }
        return javaResolveResultArr;
    }

    public static PsiElement resolveImaginarySuperCallInThisPlace(PsiMember psiMember, Project project, PsiClass psiClass) {
        if (psiMember == null) {
            $$$reportNull$$$0(16);
        }
        if (project == null) {
            $$$reportNull$$$0(17);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(18);
        }
        PsiExpressionListImpl psiExpressionListImpl = new PsiExpressionListImpl();
        DummyHolderFactory.createHolder(PsiManager.getInstance(project), psiMember).getTreeElement().rawAddChildren((TreeElement) psiExpressionListImpl.getNode());
        return PsiResolveHelper.SERVICE.getInstance(project).resolveConstructor(PsiTypesUtil.getClassType(psiClass), psiExpressionListImpl, psiMember).getElement();
    }

    public static PsiPackage getContainingPackage(PsiElement psiElement) {
        PsiDirectory containingDirectory;
        if (psiElement == null) {
            $$$reportNull$$$0(19);
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile == null || (containingDirectory = containingFile.getContainingDirectory()) == null) {
            return null;
        }
        return JavaDirectoryService.getInstance().getPackage(containingDirectory);
    }
}
