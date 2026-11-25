package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmClassKind;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmEnumField;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmModifier;
import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationAttributeValue;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmReferenceType;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.RuntimeExceptionWithAttachments;

/* JADX INFO: loaded from: classes3.dex */
final class PsiJvmConversionHelper {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiJvmConversionHelper.class);
    private static final Map<JvmModifier, String> MODIFIERS;

    /* JADX WARN: Code duplicated, block: B:27:0x0049  */
    /* JADX WARN: Code duplicated, block: B:8:0x0010 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 20) {
            switch (i) {
                default:
                    switch (i) {
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 9:
                case 10:
                case 11:
                case 12:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 20) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i != 1) {
            if (i != 3 && i != 5) {
                switch (i) {
                    case 7:
                        objArr[0] = "modifier";
                        break;
                    case 8:
                    case 13:
                    case 14:
                        objArr[0] = "psiClass";
                        break;
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 20:
                        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiJvmConversionHelper";
                        break;
                    case 19:
                    case 21:
                        objArr[0] = "pair";
                        break;
                    default:
                        objArr[0] = "modifierListOwner";
                        break;
                }
            } else {
                objArr[0] = "fqn";
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiJvmConversionHelper";
        }
        if (i == 1) {
            objArr[1] = "getListAnnotations";
        } else if (i != 20) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                    objArr[1] = "getJvmClassKind";
                    break;
                default:
                    switch (i) {
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                            objArr[1] = "getClassInterfaces";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiJvmConversionHelper";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getAnnotationAttributeName";
        }
        switch (i) {
            case 1:
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
                break;
            case 2:
            case 3:
                objArr[2] = "getListAnnotation";
                break;
            case 4:
            case 5:
                objArr[2] = "hasListAnnotation";
                break;
            case 6:
            case 7:
                objArr[2] = "hasListModifier";
                break;
            case 8:
                objArr[2] = "getJvmClassKind";
                break;
            case 13:
                objArr[2] = "getClassSuperType";
                break;
            case 14:
                objArr[2] = "getClassInterfaces";
                break;
            case 19:
                objArr[2] = "getAnnotationAttributeName";
                break;
            case 21:
                objArr[2] = "getAnnotationAttributeValue";
                break;
            default:
                objArr[2] = "getListAnnotations";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 20) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                    break;
                default:
                    switch (i) {
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    PsiJvmConversionHelper() {
    }

    static {
        EnumMap enumMap = new EnumMap(JvmModifier.class);
        enumMap.put(JvmModifier.PUBLIC, "public");
        enumMap.put(JvmModifier.PROTECTED, "protected");
        enumMap.put(JvmModifier.PRIVATE, "private");
        enumMap.put(JvmModifier.PACKAGE_LOCAL, PsiModifier.PACKAGE_LOCAL);
        enumMap.put(JvmModifier.STATIC, "static");
        enumMap.put(JvmModifier.ABSTRACT, "abstract");
        enumMap.put(JvmModifier.FINAL, "final");
        enumMap.put(JvmModifier.NATIVE, "native");
        enumMap.put(JvmModifier.SYNCHRONIZED, "synchronized");
        enumMap.put(JvmModifier.STRICTFP, "strictfp");
        enumMap.put(JvmModifier.TRANSIENT, "transient");
        enumMap.put(JvmModifier.VOLATILE, "volatile");
        enumMap.put(JvmModifier.TRANSITIVE, "transitive");
        MODIFIERS = Collections.unmodifiableMap(enumMap);
    }

    static PsiAnnotation[] getListAnnotations(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(0);
        }
        PsiModifierList psiModifierListMo6095getModifierList = psiModifierListOwner.mo6095getModifierList();
        PsiAnnotation[] annotations = psiModifierListMo6095getModifierList == null ? PsiAnnotation.EMPTY_ARRAY : psiModifierListMo6095getModifierList.getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        return annotations;
    }

    static PsiAnnotation getListAnnotation(PsiModifierListOwner psiModifierListOwner, String str) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(2);
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        PsiModifierList psiModifierListMo6095getModifierList = psiModifierListOwner.mo6095getModifierList();
        if (psiModifierListMo6095getModifierList == null) {
            return null;
        }
        return psiModifierListMo6095getModifierList.findAnnotation(str);
    }

    static boolean hasListAnnotation(PsiModifierListOwner psiModifierListOwner, String str) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(4);
        }
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        PsiModifierList psiModifierListMo6095getModifierList = psiModifierListOwner.mo6095getModifierList();
        return psiModifierListMo6095getModifierList != null && psiModifierListMo6095getModifierList.hasAnnotation(str);
    }

    static boolean hasListModifier(PsiModifierListOwner psiModifierListOwner, JvmModifier jvmModifier) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(6);
        }
        if (jvmModifier == null) {
            $$$reportNull$$$0(7);
        }
        return psiModifierListOwner.hasModifierProperty(MODIFIERS.get(jvmModifier));
    }

    static JvmClassKind getJvmClassKind(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(8);
        }
        if (psiClass.isAnnotationType()) {
            JvmClassKind jvmClassKind = JvmClassKind.ANNOTATION;
            if (jvmClassKind == null) {
                $$$reportNull$$$0(9);
            }
            return jvmClassKind;
        }
        if (psiClass.isInterface()) {
            JvmClassKind jvmClassKind2 = JvmClassKind.INTERFACE;
            if (jvmClassKind2 == null) {
                $$$reportNull$$$0(10);
            }
            return jvmClassKind2;
        }
        if (psiClass.isEnum()) {
            JvmClassKind jvmClassKind3 = JvmClassKind.ENUM;
            if (jvmClassKind3 == null) {
                $$$reportNull$$$0(11);
            }
            return jvmClassKind3;
        }
        JvmClassKind jvmClassKind4 = JvmClassKind.CLASS;
        if (jvmClassKind4 == null) {
            $$$reportNull$$$0(12);
        }
        return jvmClassKind4;
    }

    static JvmReferenceType getClassSuperType(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(13);
        }
        if (psiClass.isInterface()) {
            return null;
        }
        if (psiClass.isEnum()) {
            return PsiType.getTypeByName(CommonClassNames.JAVA_LANG_ENUM, psiClass.getProject(), psiClass.getResolveScope());
        }
        if (psiClass instanceof PsiAnonymousClass) {
            PsiClassType baseClassType = ((PsiAnonymousClass) psiClass).getBaseClassType();
            PsiClass psiClassM6528resolve = baseClassType.resolve();
            return (psiClassM6528resolve == null || !psiClassM6528resolve.isInterface()) ? baseClassType : PsiType.getJavaLangObject(psiClass.getManager(), psiClass.getResolveScope());
        }
        if (CommonClassNames.JAVA_LANG_OBJECT.equals(psiClass.getQualifiedName())) {
            return null;
        }
        PsiClassType[] extendsListTypes = psiClass.getExtendsListTypes();
        return extendsListTypes.length != 1 ? PsiType.getJavaLangObject(psiClass.getManager(), psiClass.getResolveScope()) : extendsListTypes[0];
    }

    static JvmReferenceType[] getClassInterfaces(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(14);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            PsiClassType baseClassType = ((PsiAnonymousClass) psiClass).getBaseClassType();
            PsiClass psiClassM6528resolve = baseClassType.resolve();
            if (psiClassM6528resolve != null && psiClassM6528resolve.isInterface()) {
                return new JvmReferenceType[]{baseClassType};
            }
            JvmReferenceType[] jvmReferenceTypeArr = JvmReferenceType.EMPTY_ARRAY;
            if (jvmReferenceTypeArr == null) {
                $$$reportNull$$$0(16);
            }
            return jvmReferenceTypeArr;
        }
        PsiReferenceList extendsList = psiClass.isInterface() ? psiClass.getExtendsList() : psiClass.getImplementsList();
        if (extendsList == null) {
            JvmReferenceType[] jvmReferenceTypeArr2 = JvmReferenceType.EMPTY_ARRAY;
            if (jvmReferenceTypeArr2 == null) {
                $$$reportNull$$$0(17);
            }
            return jvmReferenceTypeArr2;
        }
        PsiClassType[] referencedTypes = extendsList.getReferencedTypes();
        if (referencedTypes == null) {
            $$$reportNull$$$0(18);
        }
        return referencedTypes;
    }

    static String getAnnotationAttributeName(PsiNameValuePair psiNameValuePair) {
        if (psiNameValuePair == null) {
            $$$reportNull$$$0(19);
        }
        String name2 = psiNameValuePair.getName();
        if (name2 == null) {
            name2 = "value";
        }
        if (name2 == null) {
            $$$reportNull$$$0(20);
        }
        return name2;
    }

    static JvmAnnotationAttributeValue getAnnotationAttributeValue(PsiNameValuePair psiNameValuePair) {
        if (psiNameValuePair == null) {
            $$$reportNull$$$0(21);
        }
        return getAnnotationAttributeValue(psiNameValuePair.getValue());
    }

    static JvmAnnotationAttributeValue getAnnotationAttributeValue(PsiAnnotationMemberValue psiAnnotationMemberValue) {
        if (psiAnnotationMemberValue instanceof PsiClassObjectAccessExpression) {
            return new PsiAnnotationClassValue((PsiClassObjectAccessExpression) psiAnnotationMemberValue);
        }
        if (psiAnnotationMemberValue instanceof PsiAnnotation) {
            return new PsiNestedAnnotationValue((PsiAnnotation) psiAnnotationMemberValue);
        }
        if (psiAnnotationMemberValue instanceof PsiArrayInitializerMemberValue) {
            return new PsiAnnotationArrayValue((PsiArrayInitializerMemberValue) psiAnnotationMemberValue);
        }
        if (psiAnnotationMemberValue instanceof PsiReferenceExpression) {
            PsiReferenceExpression psiReferenceExpression = (PsiReferenceExpression) psiAnnotationMemberValue;
            PsiElement psiElementResolve = psiReferenceExpression.resolve();
            if (psiElementResolve instanceof JvmEnumField) {
                return new PsiAnnotationEnumFieldValue(psiReferenceExpression, (JvmEnumField) psiElementResolve);
            }
        }
        if (psiAnnotationMemberValue instanceof PsiExpression) {
            return new PsiAnnotationConstantValue((PsiExpression) psiAnnotationMemberValue);
        }
        if (psiAnnotationMemberValue == null) {
            return null;
        }
        LOG.warn(new RuntimeExceptionWithAttachments("Not implemented: " + psiAnnotationMemberValue.getClass(), new Attachment("text", psiAnnotationMemberValue.getText())));
        return null;
    }
}
