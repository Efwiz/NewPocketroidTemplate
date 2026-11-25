package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.StringJoiner;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.VisibilityUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiFormatUtil extends PsiFormatUtilBase {

    public @interface FormatClassOptions {
    }

    public @interface FormatMethodOptions {
    }

    public @interface FormatVariableOptions {
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 10 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 10 || i == 12) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "buffer";
                break;
            case 3:
            case 5:
            case 7:
                objArr[0] = "method";
                break;
            case 4:
            case 6:
            case 8:
            case 14:
                objArr[0] = "substitutor";
                break;
            case 9:
                objArr[0] = "aClass";
                break;
            case 10:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiFormatUtil";
                break;
            case 11:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 13:
                objArr[0] = "modifier";
                break;
            case 15:
                objArr[0] = "psiClass";
                break;
            default:
                objArr[0] = "variable";
                break;
        }
        if (i == 10) {
            objArr[1] = "formatClass";
        } else if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiFormatUtil";
        } else {
            objArr[1] = "formatModifiers";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "formatMethod";
                break;
            case 9:
                objArr[2] = "formatClass";
                break;
            case 10:
            case 12:
                break;
            case 11:
                objArr[2] = "formatModifiers";
                break;
            case 13:
                objArr[2] = "appendModifier";
                break;
            case 14:
                objArr[2] = "formatType";
                break;
            case 15:
                objArr[2] = "getPackageDisplayName";
                break;
            default:
                objArr[2] = "formatVariable";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static String formatVariable(PsiVariable psiVariable, int i, PsiSubstitutor psiSubstitutor) {
        if (psiVariable == null) {
            $$$reportNull$$$0(0);
        }
        StringBuilder sb = new StringBuilder();
        formatVariable(psiVariable, i, psiSubstitutor, sb);
        return sb.toString();
    }

    private static void formatVariable(PsiVariable psiVariable, int i, PsiSubstitutor psiSubstitutor, StringBuilder sb) {
        PsiExpression initializer;
        String name2;
        if (psiVariable == null) {
            $$$reportNull$$$0(1);
        }
        if (sb == null) {
            $$$reportNull$$$0(2);
        }
        if (BitUtil.isSet(i, 8) && !BitUtil.isSet(i, 16)) {
            formatModifiers(psiVariable, i, sb);
        }
        if (BitUtil.isSet(i, 2) && !BitUtil.isSet(i, 4)) {
            appendSpaceIfNeeded(sb);
            sb.append(formatTypeSafe(psiVariable, psiVariable.getType(), i, psiSubstitutor));
        }
        if ((psiVariable instanceof PsiField) && BitUtil.isSet(i, 4096)) {
            PsiClass containingClass = ((PsiField) psiVariable).mo6092getContainingClass();
            if (containingClass != null && (name2 = containingClass.getMyName()) != null) {
                appendSpaceIfNeeded(sb);
                if (BitUtil.isSet(i, 2048)) {
                    sb.append((String) ObjectUtils.notNull(containingClass.getQualifiedName(), name2));
                } else {
                    sb.append(name2);
                }
                sb.append('.');
            }
            if (BitUtil.isSet(i, 1)) {
                sb.append(psiVariable.getMyName());
            }
        } else if (BitUtil.isSet(i, 1)) {
            String name3 = psiVariable.getMyName();
            if (StringUtil.isNotEmpty(name3)) {
                appendSpaceIfNeeded(sb);
                sb.append(name3);
            }
        }
        if (BitUtil.isSet(i, 2) && BitUtil.isSet(i, 4)) {
            if (BitUtil.isSet(i, 1) && psiVariable.getMyName() != null) {
                sb.append(':');
            }
            sb.append(formatTypeSafe(psiVariable, psiVariable.getType(), i, psiSubstitutor));
        }
        if (BitUtil.isSet(i, 8) && BitUtil.isSet(i, 16)) {
            formatModifiers(psiVariable, i, sb);
        }
        if (!BitUtil.isSet(i, 128) || (initializer = psiVariable.getInitializer()) == null) {
            return;
        }
        sb.append(" = ");
        String strRender = PsiExpressionTrimRenderer.render(initializer);
        int iLastIndexOf = strRender.lastIndexOf(10);
        if (iLastIndexOf < 0) {
            iLastIndexOf = strRender.length();
        }
        int iLastIndexOf2 = strRender.lastIndexOf(13);
        if (iLastIndexOf2 < 0) {
            iLastIndexOf2 = strRender.length();
        }
        int iMin = Math.min(iLastIndexOf, iLastIndexOf2);
        sb.append((CharSequence) strRender, 0, iMin);
        if (iMin < strRender.length()) {
            sb.append(" ...");
        }
    }

    public static String formatMethod(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, int i, int i2) {
        if (psiMethod == null) {
            $$$reportNull$$$0(3);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(4);
        }
        return formatMethod(psiMethod, psiSubstitutor, i, i2, 7);
    }

    public static String formatMethod(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, int i, int i2, int i3) {
        if (psiMethod == null) {
            $$$reportNull$$$0(5);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(6);
        }
        StringBuilder sb = new StringBuilder();
        formatMethod(psiMethod, psiSubstitutor, i, i2, i3, sb);
        return sb.toString();
    }

    private static void formatMethod(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, int i, int i2, int i3, StringBuilder sb) {
        PsiType returnType;
        PsiType returnType2;
        if (psiMethod == null) {
            $$$reportNull$$$0(7);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(8);
        }
        if (BitUtil.isSet(i, 8) && !BitUtil.isSet(i, 16)) {
            formatModifiers(psiMethod, i, sb);
        }
        if (BitUtil.isSet(i, 2) && !BitUtil.isSet(i, 4) && (returnType2 = psiMethod.getReturnType()) != null) {
            appendSpaceIfNeeded(sb);
            sb.append(formatTypeSafe(psiMethod, returnType2, i, psiSubstitutor));
        }
        if (BitUtil.isSet(i, 4096)) {
            PsiClass containingClass = psiMethod.mo6092getContainingClass();
            if (containingClass != null) {
                appendSpaceIfNeeded(sb);
                String name2 = containingClass.getMyName();
                if (name2 != null) {
                    if (BitUtil.isSet(i, 2048)) {
                        sb.append((String) ObjectUtils.notNull(containingClass.getQualifiedName(), name2));
                    } else {
                        sb.append(name2);
                    }
                    sb.append('.');
                }
            }
            if (BitUtil.isSet(i, 1)) {
                sb.append(psiMethod.getMyName());
            }
        } else if (BitUtil.isSet(i, 1)) {
            appendSpaceIfNeeded(sb);
            sb.append(psiMethod.getMyName());
        }
        if (BitUtil.isSet(i, 256)) {
            sb.append('(');
            PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
            for (int i4 = 0; i4 < Math.min(parameters.length, i3); i4++) {
                if (i4 > 0) {
                    sb.append(", ");
                }
                sb.append(formatVariable(parameters[i4], i2, psiSubstitutor));
            }
            if (parameters.length > i3) {
                sb.append(", ...");
            }
            sb.append(')');
        }
        if (BitUtil.isSet(i, 2) && BitUtil.isSet(i, 4) && (returnType = psiMethod.getReturnType()) != null) {
            if (sb.length() > 0) {
                sb.append(':');
            }
            sb.append(formatTypeSafe(psiMethod, returnType, i, psiSubstitutor));
        }
        if (BitUtil.isSet(i, 8) && BitUtil.isSet(i, 16)) {
            formatModifiers(psiMethod, i, sb);
        }
        if (BitUtil.isSet(i, 512)) {
            String referenceList = formatReferenceList(psiMethod.getThrowsList(), i);
            if (referenceList.isEmpty()) {
                return;
            }
            appendSpaceIfNeeded(sb);
            sb.append("throws ").append(referenceList);
        }
    }

    private static String formatTypeSafe(PsiElement psiElement, PsiType psiType, int i, PsiSubstitutor psiSubstitutor) {
        try {
            return formatType(psiType, i, psiSubstitutor);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            if (ExceptionUtil.getRootCause(th) instanceof PsiInvalidElementAccessException) {
                throw new RuntimeException("Invalid type in " + psiElement.getClass(), th);
            }
            throw th;
        }
    }

    public static String formatClass(PsiClass psiClass, int i) {
        String qualifiedName;
        if (psiClass == null) {
            $$$reportNull$$$0(9);
        }
        StringBuilder sb = new StringBuilder();
        if (BitUtil.isSet(i, 8) && !BitUtil.isSet(i, 16)) {
            formatModifiers(psiClass, i, sb);
        }
        if (BitUtil.isSet(i, 1)) {
            if ((psiClass instanceof PsiAnonymousClass) && BitUtil.isSet(i, 32768)) {
                PsiClassType baseClassType = ((PsiAnonymousClass) psiClass).getBaseClassType();
                PsiClass psiClassResolve = baseClassType.resolve();
                sb.append(JavaPsiBundle.message("anonymous.class.derived.display", psiClassResolve == null ? baseClassType.getPresentableText() : formatClass(psiClassResolve, i)));
            } else if (psiClass.getMyName() != null) {
                appendSpaceIfNeeded(sb);
                if (BitUtil.isSet(i, 2048) && (qualifiedName = psiClass.getQualifiedName()) != null) {
                    sb.append(qualifiedName);
                } else {
                    sb.append(psiClass.getMyName());
                }
            }
        }
        if (BitUtil.isSet(i, 8) && BitUtil.isSet(i, 16)) {
            formatModifiers(psiClass, i, sb);
        }
        if (BitUtil.isSet(i, 1024)) {
            PsiReferenceList extendsList = psiClass.getExtendsList();
            if (extendsList != null) {
                String referenceList = formatReferenceList(extendsList, i);
                if (!referenceList.isEmpty()) {
                    appendSpaceIfNeeded(sb);
                    sb.append("extends ").append(referenceList);
                }
            }
            PsiReferenceList implementsList = psiClass.getImplementsList();
            if (implementsList != null) {
                String referenceList2 = formatReferenceList(implementsList, i);
                if (!referenceList2.isEmpty()) {
                    appendSpaceIfNeeded(sb);
                    sb.append("implements ").append(referenceList2);
                }
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(10);
        }
        return string;
    }

    public static String formatModifiers(PsiModifierListOwner psiModifierListOwner, int i) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(11);
        }
        StringBuilder sb = new StringBuilder();
        formatModifiers(psiModifierListOwner, i, sb);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(12);
        }
        return string;
    }

    private static void formatModifiers(PsiModifierListOwner psiModifierListOwner, int i, StringBuilder sb) {
        PsiModifierList psiModifierListMo6095getModifierList = psiModifierListOwner.mo6095getModifierList();
        if (psiModifierListMo6095getModifierList == null) {
            return;
        }
        if (BitUtil.isSet(i, 32) ? psiModifierListMo6095getModifierList.hasModifierProperty("public") : psiModifierListMo6095getModifierList.hasExplicitModifier("public")) {
            appendModifier(sb, "public");
        }
        if (psiModifierListMo6095getModifierList.hasModifierProperty("protected")) {
            appendModifier(sb, "protected");
        }
        if (psiModifierListMo6095getModifierList.hasModifierProperty("private")) {
            appendModifier(sb, "private");
        }
        if (BitUtil.isSet(i, 32) ? psiModifierListMo6095getModifierList.hasModifierProperty(PsiModifier.PACKAGE_LOCAL) : psiModifierListMo6095getModifierList.hasExplicitModifier(PsiModifier.PACKAGE_LOCAL)) {
            if ((psiModifierListOwner instanceof PsiClass) && (psiModifierListOwner.getMyParent() instanceof PsiDeclarationStatement)) {
                append(sb, JavaPsiBundle.message("local.class.preposition", new Object[0]));
            } else {
                appendModifier(sb, PsiModifier.PACKAGE_LOCAL);
            }
        }
        if (BitUtil.isSet(i, 32) ? psiModifierListMo6095getModifierList.hasModifierProperty("static") : psiModifierListMo6095getModifierList.hasExplicitModifier("static")) {
            if (!BitUtil.isSet(i, 16384) || !(psiModifierListOwner instanceof PsiClass) || !((PsiClass) psiModifierListOwner).isEnum()) {
                appendModifier(sb, "static");
            }
        }
        boolean z = psiModifierListOwner instanceof PsiClass;
        if ((!z || !((PsiClass) psiModifierListOwner).isInterface()) && (BitUtil.isSet(i, 32) ? psiModifierListMo6095getModifierList.hasModifierProperty("abstract") : psiModifierListMo6095getModifierList.hasExplicitModifier("abstract"))) {
            appendModifier(sb, "abstract");
        }
        if (BitUtil.isSet(i, 32) ? psiModifierListMo6095getModifierList.hasModifierProperty("final") : psiModifierListMo6095getModifierList.hasExplicitModifier("final")) {
            if (!BitUtil.isSet(i, 16384) || !z || !((PsiClass) psiModifierListOwner).isEnum()) {
                appendModifier(sb, "final");
            }
        }
        if (psiModifierListMo6095getModifierList.hasModifierProperty("native") && !BitUtil.isSet(i, 16384)) {
            appendModifier(sb, "native");
        }
        if (psiModifierListMo6095getModifierList.hasModifierProperty("synchronized") && !BitUtil.isSet(i, 16384)) {
            appendModifier(sb, "synchronized");
        }
        if (psiModifierListMo6095getModifierList.hasModifierProperty("strictfp") && !BitUtil.isSet(i, 16384)) {
            appendModifier(sb, "strictfp");
        }
        if (psiModifierListMo6095getModifierList.hasModifierProperty("transient") && (psiModifierListOwner instanceof PsiVariable)) {
            appendModifier(sb, "transient");
        }
        if (psiModifierListMo6095getModifierList.hasModifierProperty("volatile")) {
            appendModifier(sb, "volatile");
        }
    }

    private static void appendModifier(StringBuilder sb, String str) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        append(sb, VisibilityUtil.toPresentableText(str));
    }

    private static void append(StringBuilder sb, String str) {
        appendSpaceIfNeeded(sb);
        sb.append(str);
    }

    private static String formatReferenceList(PsiReferenceList psiReferenceList, int i) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        int i2 = 0;
        if (BitUtil.isSet(i, 65536)) {
            PsiClassType[] referencedTypes = psiReferenceList.getReferencedTypes();
            int length = referencedTypes.length;
            while (i2 < length) {
                stringJoiner.add(formatType(referencedTypes[i2], i, PsiSubstitutor.EMPTY));
                i2++;
            }
        } else {
            PsiJavaCodeReferenceElement[] referenceElements = psiReferenceList.getReferenceElements();
            int length2 = referenceElements.length;
            while (i2 < length2) {
                stringJoiner.add(formatReference(referenceElements[i2], i));
                i2++;
            }
        }
        return stringJoiner.toString();
    }

    public static String formatType(PsiType psiType, int i, PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(14);
        }
        PsiType psiTypeSubstitute = psiSubstitutor.substitute(psiType);
        if (BitUtil.isSet(i, 65536)) {
            psiTypeSubstitute = TypeConversionUtil.erasure(psiTypeSubstitute);
        } else if (BitUtil.isSet(i, 131072) && !(PsiUtil.resolveClassInType(psiTypeSubstitute) instanceof PsiTypeParameter)) {
            boolean z = psiTypeSubstitute instanceof PsiEllipsisType;
            psiTypeSubstitute = TypeConversionUtil.erasure(psiTypeSubstitute);
            if (z && (psiTypeSubstitute instanceof PsiArrayType)) {
                psiTypeSubstitute = new PsiEllipsisType(((PsiArrayType) psiTypeSubstitute).getComponentType());
            }
        }
        if (psiTypeSubstitute == null) {
            return "null";
        }
        if (BitUtil.isSet(i, 8192)) {
            return !BitUtil.isSet(i, 262144) ? psiTypeSubstitute.getCanonicalText(false) : psiTypeSubstitute.getInternalCanonicalText();
        }
        return psiTypeSubstitute.getPresentableText(false);
    }

    public static String formatReference(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, int i) {
        return BitUtil.isSet(i, 8192) ? psiJavaCodeReferenceElement.getCanonicalText() : psiJavaCodeReferenceElement.getText();
    }

    public static String getExternalName(PsiModifierListOwner psiModifierListOwner) {
        return getExternalName(psiModifierListOwner, true);
    }

    public static String getExternalName(PsiModifierListOwner psiModifierListOwner, boolean z) {
        return getExternalName(psiModifierListOwner, z, 7);
    }

    public static String getExternalName(PsiModifierListOwner psiModifierListOwner, boolean z, int i) {
        if (psiModifierListOwner instanceof PsiPackage) {
            return ((PsiPackage) psiModifierListOwner).getQualifiedName();
        }
        StringBuilder sb = new StringBuilder();
        if (psiModifierListOwner instanceof PsiClass) {
            ClassUtil.formatClassName((PsiClass) psiModifierListOwner, sb);
            return sb.toString();
        }
        PsiClass psiClass = (PsiClass) PsiTreeUtil.getContextOfType((PsiElement) psiModifierListOwner, PsiClass.class, false);
        if (psiClass == null) {
            return null;
        }
        ClassUtil.formatClassName(psiClass, sb);
        if (psiModifierListOwner instanceof PsiMethod) {
            sb.append(io.netty.util.internal.StringUtil.SPACE);
            formatMethod((PsiMethod) psiModifierListOwner, PsiSubstitutor.EMPTY, 10499, z ? 8195 : 8194, i, sb);
        } else if (psiModifierListOwner instanceof PsiField) {
            sb.append(io.netty.util.internal.StringUtil.SPACE).append(((PsiField) psiModifierListOwner).getMyName());
        } else {
            if (!(psiModifierListOwner instanceof PsiParameter)) {
                return null;
            }
            PsiParameter psiParameter = (PsiParameter) psiModifierListOwner;
            PsiElement declarationScope = psiParameter.getMethod();
            if (!(declarationScope instanceof PsiMethod)) {
                return null;
            }
            PsiMethod psiMethod = (PsiMethod) declarationScope;
            sb.append(io.netty.util.internal.StringUtil.SPACE);
            formatMethod(psiMethod, PsiSubstitutor.EMPTY, 10499, z ? 8195 : 8194, i, sb);
            sb.append(io.netty.util.internal.StringUtil.SPACE);
            if (z) {
                formatVariable((PsiVariable) psiModifierListOwner, 1, PsiSubstitutor.EMPTY, sb);
            } else {
                sb.append(psiMethod.getParameterList().getParameterIndex(psiParameter));
            }
        }
        return sb.toString();
    }

    public static String getPackageDisplayName(PsiClass psiClass) {
        String name2;
        if (psiClass == null) {
            $$$reportNull$$$0(15);
        }
        if (psiClass instanceof PsiTypeParameter) {
            PsiTypeParameterListOwner owner = ((PsiTypeParameter) psiClass).getOwner();
            if (owner instanceof PsiClass) {
                name2 = ((PsiClass) owner).getQualifiedName();
                if (name2 == null) {
                    name2 = owner.getMyName();
                }
            } else {
                name2 = owner instanceof PsiMethod ? owner.getMyName() : null;
            }
            return name2 == null ? "type parameter" : "type parameter of " + name2;
        }
        if (PsiUtil.isLocalClass(psiClass)) {
            return "local class";
        }
        String qualifiedName = psiClass.getQualifiedName();
        String strSubstring = (qualifiedName == null || qualifiedName.lastIndexOf(46) <= 0) ? "" : qualifiedName.substring(0, qualifiedName.lastIndexOf(46));
        return strSubstring.isEmpty() ? "default package" : strSubstring;
    }
}
