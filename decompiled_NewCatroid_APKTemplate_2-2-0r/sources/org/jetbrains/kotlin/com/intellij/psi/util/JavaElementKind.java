package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiLabeledStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiReceiverParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;

/* JADX INFO: loaded from: classes3.dex */
public enum JavaElementKind {
    ABSTRACT_METHOD("element.abstract_method"),
    ANNOTATION("element.annotation"),
    ANONYMOUS_CLASS("element.anonymous_class"),
    CLASS("element.class"),
    TYPE_PARAMETER("element.type.parameter"),
    CONSTANT("element.constant"),
    CONSTRUCTOR("element.constructor"),
    ENUM("element.enum"),
    ENUM_CONSTANT("element.enum_constant"),
    EXPRESSION("element.expression"),
    FIELD("element.field"),
    INITIALIZER("element.initializer"),
    INTERFACE("element.interface"),
    LABEL("element.label"),
    LOCAL_VARIABLE("element.local_variable"),
    METHOD("element.method"),
    MODULE("element.module"),
    PACKAGE("element.package"),
    PARAMETER("element.parameter"),
    PATTERN_VARIABLE("element.pattern_variable"),
    RECORD("element.record"),
    RECORD_COMPONENT("element.record_component"),
    STATEMENT("element.statement"),
    UNKNOWN("element.unknown"),
    VARIABLE("element.variable"),
    THROWS_LIST("element.throws.list"),
    EXTENDS_LIST("element.extends.list"),
    RECEIVER_PARAMETER("element.receiver.parameter"),
    METHOD_CALL("element.method.call"),
    TYPE_ARGUMENTS("element.type.arguments");

    private final String propertyKey;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 7 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 7 ? 2 : 3];
        if (i != 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/JavaElementKind";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        switch (i) {
            case 1:
                objArr[1] = "object";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[1] = "lessDescriptive";
                break;
            case 7:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/JavaElementKind";
                break;
            default:
                objArr[1] = "subject";
                break;
        }
        if (i == 7) {
            objArr[2] = "fromElement";
        }
        String str2 = String.format(str, objArr);
        if (i == 7) {
            throw new IllegalArgumentException(str2);
        }
    }

    JavaElementKind(String str) {
        this.propertyKey = str;
    }

    public String subject() {
        String strMessage = JavaPsiBundle.message(this.propertyKey, 0);
        if (strMessage == null) {
            $$$reportNull$$$0(0);
        }
        return strMessage;
    }

    public String object() {
        String strMessage = JavaPsiBundle.message(this.propertyKey, 1);
        if (strMessage == null) {
            $$$reportNull$$$0(1);
        }
        return strMessage;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.util.JavaElementKind$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$util$JavaElementKind;

        static {
            int[] iArr = new int[JavaElementKind.values().length];
            $SwitchMap$com$intellij$psi$util$JavaElementKind = iArr;
            try {
                iArr[JavaElementKind.ABSTRACT_METHOD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$util$JavaElementKind[JavaElementKind.LOCAL_VARIABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$util$JavaElementKind[JavaElementKind.PATTERN_VARIABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$intellij$psi$util$JavaElementKind[JavaElementKind.CONSTANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$intellij$psi$util$JavaElementKind[JavaElementKind.TYPE_PARAMETER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$intellij$psi$util$JavaElementKind[JavaElementKind.ANONYMOUS_CLASS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public JavaElementKind lessDescriptive() {
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$util$JavaElementKind[ordinal()]) {
            case 1:
                JavaElementKind javaElementKind = METHOD;
                if (javaElementKind == null) {
                    $$$reportNull$$$0(2);
                }
                return javaElementKind;
            case 2:
            case 3:
                JavaElementKind javaElementKind2 = VARIABLE;
                if (javaElementKind2 == null) {
                    $$$reportNull$$$0(3);
                }
                return javaElementKind2;
            case 4:
                JavaElementKind javaElementKind3 = FIELD;
                if (javaElementKind3 == null) {
                    $$$reportNull$$$0(4);
                }
                return javaElementKind3;
            case 5:
            case 6:
                JavaElementKind javaElementKind4 = CLASS;
                if (javaElementKind4 == null) {
                    $$$reportNull$$$0(5);
                }
                return javaElementKind4;
            default:
                return this;
        }
    }

    public static JavaElementKind fromElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElement instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) psiElement;
            if (psiClass instanceof PsiAnonymousClass) {
                return ANONYMOUS_CLASS;
            }
            if (psiClass.isEnum()) {
                return ENUM;
            }
            if (psiClass.isRecord()) {
                return RECORD;
            }
            if (psiClass.isAnnotationType()) {
                return ANNOTATION;
            }
            if (psiClass.isInterface()) {
                return INTERFACE;
            }
            if (psiClass instanceof PsiTypeParameter) {
                return TYPE_PARAMETER;
            }
            return CLASS;
        }
        if (psiElement instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) psiElement;
            if (psiMethod.isConstructor()) {
                return CONSTRUCTOR;
            }
            if (psiMethod.hasModifierProperty("abstract")) {
                return ABSTRACT_METHOD;
            }
            return METHOD;
        }
        if (psiElement instanceof PsiField) {
            PsiField psiField = (PsiField) psiElement;
            if (psiField instanceof PsiEnumConstant) {
                return ENUM_CONSTANT;
            }
            if (psiField.hasModifierProperty("static") && psiField.hasModifierProperty("final")) {
                return CONSTANT;
            }
            return FIELD;
        }
        if (psiElement instanceof PsiReferenceParameterList) {
            return TYPE_ARGUMENTS;
        }
        if (psiElement instanceof PsiReferenceList) {
            PsiReferenceList.Role role = ((PsiReferenceList) psiElement).getRole();
            if (role == PsiReferenceList.Role.THROWS_LIST) {
                return THROWS_LIST;
            }
            if (role == PsiReferenceList.Role.EXTENDS_LIST) {
                return EXTENDS_LIST;
            }
        }
        if (psiElement instanceof PsiAnnotation) {
            return ANNOTATION;
        }
        if (psiElement instanceof PsiRecordComponent) {
            return RECORD_COMPONENT;
        }
        if (psiElement instanceof PsiLocalVariable) {
            return LOCAL_VARIABLE;
        }
        if (psiElement instanceof PsiPatternVariable) {
            return PATTERN_VARIABLE;
        }
        if (psiElement instanceof PsiParameter) {
            return PARAMETER;
        }
        if (psiElement instanceof PsiReceiverParameter) {
            return RECEIVER_PARAMETER;
        }
        if (psiElement instanceof PsiVariable) {
            return VARIABLE;
        }
        if (psiElement instanceof PsiPackage) {
            return PACKAGE;
        }
        if (psiElement instanceof PsiJavaModule) {
            return MODULE;
        }
        if (psiElement instanceof PsiClassInitializer) {
            return INITIALIZER;
        }
        if (psiElement instanceof PsiLabeledStatement) {
            return LABEL;
        }
        if (psiElement instanceof PsiStatement) {
            return STATEMENT;
        }
        if (psiElement instanceof PsiMethodCallExpression) {
            return METHOD_CALL;
        }
        if (psiElement instanceof PsiExpression) {
            return EXPRESSION;
        }
        return UNKNOWN;
    }
}
