package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.function.Predicate;
import java.util.stream.Stream;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.SyntheticElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordCanonicalConstructor;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordField;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaPsiRecordUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "component";
                break;
            case 2:
                objArr[0] = "parameter";
                break;
            case 3:
                objArr[0] = "field";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "method";
                break;
            case 8:
                objArr[0] = "recordClass";
                break;
            default:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/JavaPsiRecordUtil";
        switch (i) {
            case 1:
                objArr[2] = "getFieldForComponent";
                break;
            case 2:
                objArr[2] = "getComponentForCanonicalConstructorParameter";
                break;
            case 3:
                objArr[2] = "getComponentForField";
                break;
            case 4:
                objArr[2] = "isCompactConstructor";
                break;
            case 5:
                objArr[2] = "isExplicitCanonicalConstructor";
                break;
            case 6:
                objArr[2] = "isCanonicalConstructor";
                break;
            case 7:
                objArr[2] = "hasCanonicalSignature";
                break;
            case 8:
                objArr[2] = "findCanonicalConstructor";
                break;
            default:
                objArr[2] = "getRecordComponentForAccessor";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static PsiRecordComponent getRecordComponentForAccessor(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(0);
        }
        PsiClass containingClass = psiMethod.mo6092getContainingClass();
        if (containingClass == null) {
            PsiElement parent = psiMethod.getMyParent();
            if (parent instanceof DummyHolder) {
                containingClass = (PsiClass) ObjectUtils.tryCast(parent.getContext(), PsiClass.class);
            }
        }
        if (containingClass == null || !containingClass.isRecord() || !psiMethod.getParameterList().isEmpty()) {
            return null;
        }
        String myName = psiMethod.getMyName();
        for (PsiRecordComponent psiRecordComponent : containingClass.getRecordComponents()) {
            if (myName.equals(psiRecordComponent.getMyName())) {
                return psiRecordComponent;
            }
        }
        return null;
    }

    public static PsiField getFieldForComponent(PsiRecordComponent psiRecordComponent) {
        if (psiRecordComponent == null) {
            $$$reportNull$$$0(1);
        }
        PsiClass containingClass = psiRecordComponent.mo6092getContainingClass();
        if (containingClass == null) {
            return null;
        }
        String name2 = psiRecordComponent.getMyName();
        for (PsiField psiField : containingClass.getFields()) {
            if (psiField.getMyName().equals(name2) && !psiField.hasModifierProperty("static")) {
                return psiField;
            }
        }
        return null;
    }

    public static PsiRecordComponent getComponentForCanonicalConstructorParameter(PsiParameter psiParameter) {
        if (psiParameter == null) {
            $$$reportNull$$$0(2);
        }
        PsiClass psiClass = (PsiClass) PsiTreeUtil.getParentOfType(psiParameter, PsiClass.class);
        if (psiClass == null) {
            return null;
        }
        final String myName = psiParameter.getMyName();
        return (PsiRecordComponent) Stream.of((Object[]) psiClass.getRecordComponents()).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.JavaPsiRecordUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return myName.equals(((PsiRecordComponent) obj).getMyName());
            }
        }).findFirst().orElse(null);
    }

    public static PsiRecordComponent getComponentForField(PsiField psiField) {
        if (psiField == null) {
            $$$reportNull$$$0(3);
        }
        if (psiField instanceof LightRecordField) {
            return ((LightRecordField) psiField).getRecordComponent();
        }
        return null;
    }

    public static boolean isCompactConstructor(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(4);
        }
        return psiMethod.isConstructor() && psiMethod.getParameterList().getText() == null;
    }

    public static boolean isExplicitCanonicalConstructor(PsiMethod psiMethod) {
        PsiClass containingClass;
        if (psiMethod == null) {
            $$$reportNull$$$0(5);
        }
        if (!psiMethod.isConstructor() || isCompactConstructor(psiMethod) || (psiMethod instanceof SyntheticElement) || (containingClass = psiMethod.mo6092getContainingClass()) == null || !containingClass.isRecord()) {
            return false;
        }
        return hasCanonicalSignature(psiMethod, containingClass.getRecordComponents());
    }

    public static boolean isCanonicalConstructor(PsiMethod psiMethod) {
        PsiClass containingClass;
        if (psiMethod == null) {
            $$$reportNull$$$0(6);
        }
        if (psiMethod instanceof LightRecordCanonicalConstructor) {
            return true;
        }
        if (psiMethod.isConstructor() && (containingClass = psiMethod.mo6092getContainingClass()) != null && containingClass.isRecord()) {
            return psiMethod.getParameterList().getText() == null || hasCanonicalSignature(psiMethod, containingClass.getRecordComponents());
        }
        return false;
    }

    private static boolean hasCanonicalSignature(PsiMethod psiMethod, PsiRecordComponent[] psiRecordComponentArr) {
        if (psiMethod == null) {
            $$$reportNull$$$0(7);
        }
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        if (psiRecordComponentArr.length != parameters.length) {
            return false;
        }
        for (int i = 0; i < parameters.length; i++) {
            PsiType type = psiRecordComponentArr[i].getType();
            PsiType type2 = parameters[i].getType();
            if (type instanceof PsiEllipsisType) {
                type = ((PsiEllipsisType) type).toArrayType();
            }
            if (type2 instanceof PsiEllipsisType) {
                type2 = ((PsiEllipsisType) type2).toArrayType();
            }
            if (!TypeConversionUtil.erasure(type).equals(TypeConversionUtil.erasure(type2))) {
                return false;
            }
        }
        return true;
    }

    public static PsiMethod findCanonicalConstructor(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(8);
        }
        if (!psiClass.isRecord()) {
            return null;
        }
        PsiMethod[] constructors = psiClass.getConstructors();
        if (constructors.length == 0) {
            return null;
        }
        PsiRecordComponent[] recordComponents = psiClass.getRecordComponents();
        for (PsiMethod psiMethod : constructors) {
            if (isCompactConstructor(psiMethod) || hasCanonicalSignature(psiMethod, recordComponents)) {
                return psiMethod;
            }
        }
        return null;
    }
}
