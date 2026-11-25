package org.jetbrains.kotlin.com.intellij.psi.util;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpressionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiTypesUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiTypesUtil.class);
    private static final Map<String, String> ourBoxedTypes;
    private static final Map<String, String> ourUnboxedTypes;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 2:
            case 19:
            case 22:
            case 23:
            case 31:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
                i2 = 3;
                break;
            case 2:
            case 19:
            case 22:
            case 23:
            case 31:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "psiClass";
                break;
            case 2:
            case 19:
            case 22:
            case 23:
            case 31:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTypesUtil";
                break;
            case 3:
            case 16:
            case 17:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 4:
            case 7:
                objArr[0] = "methodExpression";
                break;
            case 5:
            case 8:
            case 11:
                objArr[0] = "method";
                break;
            case 6:
                objArr[0] = "call";
                break;
            case 9:
                objArr[0] = "condition";
                break;
            case 10:
                objArr[0] = "languageLevel";
                break;
            case 12:
            case 15:
            case 27:
            case 28:
            case 32:
                objArr[0] = "context";
                break;
            case 13:
            case 14:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 18:
                objArr[0] = "parameters";
                break;
            case 20:
            case 25:
            case 33:
                objArr[0] = "typeParameters";
                break;
            case 21:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 24:
                objArr[0] = "superReturnTypeInBaseClassType";
                break;
            case 26:
                objArr[0] = "parameter";
                break;
            case 29:
                objArr[0] = "targetType";
                break;
            case 30:
                objArr[0] = "newType";
                break;
            case 34:
                objArr[0] = "wantedTypeParameter";
                break;
            case 35:
                objArr[0] = "qualifiedClassName";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTypesUtil";
                break;
            case 2:
                objArr[1] = "getClassType";
                break;
            case 19:
                objArr[1] = "getParameterType";
                break;
            case 22:
            case 23:
                objArr[1] = "filterUnusedTypeParameters";
                break;
            case 31:
                objArr[1] = "createArrayType";
                break;
            default:
                objArr[1] = "getDefaultValueOfType";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "getClassType";
                break;
            case 3:
                objArr[2] = "getLowestUpperBoundClassType";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "patchMethodGetClassReturnType";
                break;
            case 11:
                objArr[2] = "isGetClass";
                break;
            case 12:
                objArr[2] = "createJavaLangClassType";
                break;
            case 13:
                objArr[2] = "getExpectedTypeByParent";
                break;
            case 14:
                objArr[2] = "getMethodReturnType";
                break;
            case 15:
                objArr[2] = "isDenotableType";
                break;
            case 16:
                objArr[2] = "hasTypeAnnotation";
                break;
            case 17:
                objArr[2] = "hasUnresolvedComponents";
                break;
            case 18:
                objArr[2] = "getParameterType";
                break;
            case 20:
            case 21:
            case 24:
            case 25:
                objArr[2] = "filterUnusedTypeParameters";
                break;
            case 26:
            case 27:
                objArr[2] = "isAccessibleAt";
                break;
            case 28:
            case 29:
                objArr[2] = "allTypeParametersResolved";
                break;
            case 30:
                objArr[2] = "createArrayType";
                break;
            case 32:
                objArr[2] = "getTypeByMethod";
                break;
            case 33:
            case 34:
                objArr[2] = "mentionsTypeParameters";
                break;
            case 35:
                objArr[2] = "classNameEquals";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
                throw new IllegalArgumentException(str2);
            case 2:
            case 19:
            case 22:
            case 23:
            case 31:
            default:
                throw new IllegalStateException(str2);
        }
    }

    static {
        HashMap map = new HashMap();
        ourUnboxedTypes = map;
        HashMap map2 = new HashMap();
        ourBoxedTypes = map2;
        map.put(CommonClassNames.JAVA_LANG_BOOLEAN, "boolean");
        map.put(CommonClassNames.JAVA_LANG_BYTE, "byte");
        map.put(CommonClassNames.JAVA_LANG_SHORT, "short");
        map.put(CommonClassNames.JAVA_LANG_INTEGER, "int");
        map.put(CommonClassNames.JAVA_LANG_LONG, "long");
        map.put(CommonClassNames.JAVA_LANG_FLOAT, "float");
        map.put(CommonClassNames.JAVA_LANG_DOUBLE, "double");
        map.put(CommonClassNames.JAVA_LANG_CHARACTER, PsiKeyword.CHAR);
        map2.put("boolean", CommonClassNames.JAVA_LANG_BOOLEAN);
        map2.put("byte", CommonClassNames.JAVA_LANG_BYTE);
        map2.put("short", CommonClassNames.JAVA_LANG_SHORT);
        map2.put("int", CommonClassNames.JAVA_LANG_INTEGER);
        map2.put("long", CommonClassNames.JAVA_LANG_LONG);
        map2.put("float", CommonClassNames.JAVA_LANG_FLOAT);
        map2.put("double", CommonClassNames.JAVA_LANG_DOUBLE);
        map2.put(PsiKeyword.CHAR, CommonClassNames.JAVA_LANG_CHARACTER);
    }

    private PsiTypesUtil() {
    }

    public static Object getDefaultValue(PsiType psiType) {
        if (!(psiType instanceof PsiPrimitiveType)) {
            return null;
        }
        String canonicalText = psiType.getCanonicalText();
        canonicalText.hashCode();
        switch (canonicalText) {
            case "double":
                return Double.valueOf(0.0d);
            case "int":
                return 0;
            case "byte":
                return (byte) 0;
            case "char":
                return (char) 0;
            case "long":
                return 0L;
            case "boolean":
                return false;
            case "float":
                return Float.valueOf(0.0f);
            case "short":
                return (short) 0;
            default:
                return null;
        }
    }

    public static String getDefaultValueOfType(PsiType psiType) {
        return getDefaultValueOfType(psiType, false);
    }

    public static String getDefaultValueOfType(PsiType psiType, boolean z) {
        if (psiType instanceof PsiArrayType) {
            int arrayDimensions = psiType.getArrayDimensions() - 1;
            PsiType deepComponentType = psiType.getDeepComponentType();
            if ((deepComponentType instanceof PsiClassType) && (((PsiClassType) deepComponentType).resolve() instanceof PsiTypeParameter)) {
                return "null";
            }
            PsiType psiTypeErasure = TypeConversionUtil.erasure(deepComponentType);
            StringBuilder sb = new StringBuilder("new ");
            sb.append(psiTypeErasure.getCanonicalText());
            sb.append("[0]");
            for (int i = 0; i < arrayDimensions; i++) {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            String string = sb.toString();
            if (string == null) {
                $$$reportNull$$$0(0);
            }
            return string;
        }
        if (psiType instanceof PsiPrimitiveType) {
            return PsiType.BOOLEAN.equals(psiType) ? "false" : "0";
        }
        if (z) {
            PsiClassType psiClassTypeRawType = psiType instanceof PsiClassType ? ((PsiClassType) psiType).rawType() : null;
            if (psiClassTypeRawType != null && psiClassTypeRawType.equalsToText(CommonClassNames.JAVA_UTIL_OPTIONAL)) {
                return "java.util.Optional.empty()";
            }
        }
        return "null";
    }

    public static String unboxIfPossible(String str) {
        if (str == null) {
            return null;
        }
        String str2 = ourUnboxedTypes.get(str);
        return str2 == null ? str : str2;
    }

    public static String boxIfPossible(String str) {
        if (str == null) {
            return null;
        }
        String str2 = ourBoxedTypes.get(str);
        return str2 == null ? str : str2;
    }

    public static PsiClass getPsiClass(PsiType psiType) {
        if (psiType instanceof PsiClassType) {
            return ((PsiClassType) psiType).resolve();
        }
        return null;
    }

    public static PsiClassType getClassType(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(psiClass.getProject()).createType(psiClass);
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(2);
        }
        return psiClassTypeCreateType;
    }

    public static PsiClassType getLowestUpperBoundClassType(PsiDisjunctionType psiDisjunctionType) {
        PsiClassType psiClassType;
        PsiClass psiClassResolve;
        if (psiDisjunctionType == null) {
            $$$reportNull$$$0(3);
        }
        PsiType leastUpperBound = psiDisjunctionType.getLeastUpperBound();
        if (leastUpperBound instanceof PsiClassType) {
            return (PsiClassType) leastUpperBound;
        }
        if (!(leastUpperBound instanceof PsiIntersectionType)) {
            return null;
        }
        for (PsiType psiType : ((PsiIntersectionType) leastUpperBound).getConjuncts()) {
            if ((psiType instanceof PsiClassType) && (psiClassResolve = (psiClassType = (PsiClassType) psiType).resolve()) != null && !psiClassResolve.isInterface()) {
                return psiClassType;
            }
        }
        return null;
    }

    public static PsiType patchMethodGetClassReturnType(PsiMethodReferenceExpression psiMethodReferenceExpression, PsiMethod psiMethod) {
        PsiType qualifierType;
        if (psiMethodReferenceExpression == null) {
            $$$reportNull$$$0(4);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(5);
        }
        if (!isGetClass(psiMethod) || (qualifierType = PsiMethodReferenceUtil.getQualifierType(psiMethodReferenceExpression)) == null) {
            return null;
        }
        return createJavaLangClassType(psiMethodReferenceExpression, qualifierType, true);
    }

    public static PsiType patchMethodGetClassReturnType(PsiExpression psiExpression, PsiReferenceExpression psiReferenceExpression, PsiMethod psiMethod, Condition<? super IElementType> condition, LanguageLevel languageLevel) {
        if (psiExpression == null) {
            $$$reportNull$$$0(6);
        }
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(7);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(8);
        }
        if (condition == null) {
            $$$reportNull$$$0(9);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(10);
        }
        PsiType psiTypeCreateType = null;
        if (!languageLevel.isAtLeast(LanguageLevel.JDK_1_5) || !isGetClass(psiMethod)) {
            return null;
        }
        PsiExpression qualifierExpression = psiReferenceExpression.getQualifierExpression();
        Project project = psiExpression.getProject();
        if (qualifierExpression != null) {
            psiTypeCreateType = TypeConversionUtil.erasure(qualifierExpression.getType());
        } else {
            PsiElement context = psiExpression.getContext();
            while (context != null) {
                if (!condition.value(context instanceof StubBasedPsiElement ? ((StubBasedPsiElement) context).getElementType() : context.getNode().getElementType())) {
                    break;
                }
                context = context.getContext();
            }
            if (context != null) {
                psiTypeCreateType = JavaPsiFacade.getElementFactory(project).createType((PsiClass) context);
            }
        }
        if (PsiType.NULL.equals(psiTypeCreateType)) {
            LOG.error("Unexpected null qualifier", new Attachment("expression.txt", psiExpression.getText()));
        }
        return createJavaLangClassType(psiReferenceExpression, psiTypeCreateType, true);
    }

    public static boolean isGetClass(PsiMethod psiMethod) {
        PsiClass containingClass;
        if (psiMethod == null) {
            $$$reportNull$$$0(11);
        }
        return "getClass".equals(psiMethod.getName()) && (containingClass = psiMethod.getContainingClass()) != null && CommonClassNames.JAVA_LANG_OBJECT.equals(containingClass.getQualifiedName());
    }

    public static PsiType createJavaLangClassType(PsiElement psiElement, PsiType psiType, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        if (psiType == null) {
            return null;
        }
        PsiUtil.ensureValidType(psiType);
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(psiElement.getProject());
        PsiClass psiClassFindClass = javaPsiFacade.findClass(CommonClassNames.JAVA_LANG_CLASS, psiElement.getResolveScope());
        if (psiClassFindClass == null || psiClassFindClass.getTypeParameters().length != 1) {
            return null;
        }
        PsiClassType psiClassTypeCreateType = javaPsiFacade.getElementFactory().createType(psiClassFindClass, PsiSubstitutor.EMPTY.put(psiClassFindClass.getTypeParameters()[0], PsiWildcardType.createExtends(psiElement.getManager(), psiType)), PsiUtil.getLanguageLevel(psiElement));
        return z ? PsiUtil.captureToplevelWildcards(psiClassTypeCreateType, psiElement) : psiClassTypeCreateType;
    }

    public static PsiType getExpectedTypeByParent(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiElement.getLightParent());
        if (psiElementSkipParenthesizedExprUp instanceof PsiVariable) {
            PsiVariable psiVariable = (PsiVariable) psiElementSkipParenthesizedExprUp;
            if (!PsiUtil.checkSameExpression(psiElement, psiVariable.getInitializer())) {
                return null;
            }
            PsiTypeElement typeElement = psiVariable.getTypeElement();
            if (typeElement == null || !typeElement.isInferredType()) {
                return psiVariable.getType();
            }
            return null;
        }
        if (psiElementSkipParenthesizedExprUp instanceof PsiAssignmentExpression) {
            PsiAssignmentExpression psiAssignmentExpression = (PsiAssignmentExpression) psiElementSkipParenthesizedExprUp;
            if (psiAssignmentExpression.getOperationSign().getTokenType() != JavaTokenType.EQ || !PsiUtil.checkSameExpression(psiElement, psiAssignmentExpression.getRExpression())) {
                return null;
            }
            PsiType type = psiAssignmentExpression.getLExpression().getType();
            if (PsiType.NULL.equals(type)) {
                return null;
            }
            return type;
        }
        if (psiElementSkipParenthesizedExprUp instanceof PsiReturnStatement) {
            PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElementSkipParenthesizedExprUp, (Class<? extends PsiElement>[]) new Class[]{PsiLambdaExpression.class, PsiMethod.class});
            if (!(parentOfType instanceof PsiLambdaExpression) && (parentOfType instanceof PsiMethod)) {
                return ((PsiMethod) parentOfType).getReturnType();
            }
            return null;
        }
        if (PsiUtil.isCondition(psiElement, psiElementSkipParenthesizedExprUp)) {
            return PsiType.BOOLEAN;
        }
        if (!(psiElementSkipParenthesizedExprUp instanceof PsiArrayInitializerExpression)) {
            return null;
        }
        PsiElement lightParent = psiElementSkipParenthesizedExprUp.getLightParent();
        if (lightParent instanceof PsiNewExpression) {
            PsiType type2 = ((PsiNewExpression) lightParent).getType();
            if (type2 instanceof PsiArrayType) {
                return ((PsiArrayType) type2).getComponentType();
            }
            return null;
        }
        if (lightParent instanceof PsiVariable) {
            PsiType type3 = ((PsiVariable) lightParent).getType();
            if (type3 instanceof PsiArrayType) {
                return ((PsiArrayType) type3).getComponentType();
            }
            return null;
        }
        if (!(lightParent instanceof PsiArrayInitializerExpression)) {
            return null;
        }
        PsiType expectedTypeByParent = getExpectedTypeByParent(psiElementSkipParenthesizedExprUp);
        if (expectedTypeByParent instanceof PsiArrayType) {
            return ((PsiArrayType) expectedTypeByParent).getComponentType();
        }
        return null;
    }

    public static PsiType getMethodReturnType(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElement, (Class<? extends PsiElement>[]) new Class[]{PsiMethod.class, PsiLambdaExpression.class});
        if (parentOfType instanceof PsiMethod) {
            return ((PsiMethod) parentOfType).getReturnType();
        }
        if (parentOfType instanceof PsiLambdaExpression) {
            return LambdaUtil.getFunctionalInterfaceReturnType((PsiLambdaExpression) parentOfType);
        }
        return null;
    }

    public static boolean compareTypes(PsiType psiType, PsiType psiType2, boolean z) {
        if (z) {
            if (psiType instanceof PsiEllipsisType) {
                psiType = ((PsiEllipsisType) psiType).toArrayType();
            }
            if (psiType2 instanceof PsiEllipsisType) {
                psiType2 = ((PsiEllipsisType) psiType2).toArrayType();
            }
        }
        return Comparing.equal(psiType, psiType2);
    }

    public static boolean isDenotableType(PsiType psiType, PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        if (psiType != null && !(psiType instanceof PsiWildcardType)) {
            try {
                return psiType.equals(JavaPsiFacade.getElementFactory(psiElement.getProject()).createTypeElementFromText(psiType.getCanonicalText(), psiElement).getType());
            } catch (IncorrectOperationException unused) {
            }
        }
        return false;
    }

    public static boolean hasTypeAnnotation(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(16);
        }
        return ((Boolean) psiType.accept(new PsiTypeVisitor<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "classType";
                } else if (i == 2) {
                    objArr[0] = "arrayType";
                } else if (i == 3) {
                    objArr[0] = "wildcardType";
                } else if (i == 4) {
                    objArr[0] = "intersectionType";
                } else if (i != 5) {
                    objArr[0] = ModuleXmlParser.TYPE;
                } else {
                    objArr[0] = "disjunctionType";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTypesUtil$1";
                if (i == 1) {
                    objArr[2] = "visitClassType";
                } else if (i == 2) {
                    objArr[2] = "visitArrayType";
                } else if (i == 3) {
                    objArr[2] = "visitWildcardType";
                } else if (i == 4) {
                    objArr[2] = "visitIntersectionType";
                } else if (i != 5) {
                    objArr[2] = "visitType";
                } else {
                    objArr[2] = "visitDisjunctionType";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitType(PsiType psiType2) {
                if (psiType2 == null) {
                    $$$reportNull$$$0(0);
                }
                return Boolean.valueOf(psiType2.getAnnotations().length > 0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitClassType(PsiClassType psiClassType) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(1);
                }
                if (((Boolean) super.visitClassType(psiClassType)).booleanValue()) {
                    return true;
                }
                for (PsiType psiType2 : psiClassType.getParameters()) {
                    if (((Boolean) psiType2.accept(this)).booleanValue()) {
                        return true;
                    }
                }
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(2);
                }
                return Boolean.valueOf(((Boolean) super.visitArrayType(psiArrayType)).booleanValue() || ((Boolean) psiArrayType.getComponentType().accept(this)).booleanValue());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitWildcardType(PsiWildcardType psiWildcardType) {
                if (psiWildcardType == null) {
                    $$$reportNull$$$0(3);
                }
                return Boolean.valueOf(((Boolean) super.visitWildcardType(psiWildcardType)).booleanValue() || (psiWildcardType.getBound() != null && ((Boolean) psiWildcardType.getBound().accept(this)).booleanValue()));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitIntersectionType(PsiIntersectionType psiIntersectionType) {
                if (psiIntersectionType == null) {
                    $$$reportNull$$$0(4);
                }
                for (PsiType psiType2 : psiIntersectionType.getConjuncts()) {
                    if (((Boolean) psiType2.accept(this)).booleanValue()) {
                        return true;
                    }
                }
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitDisjunctionType(PsiDisjunctionType psiDisjunctionType) {
                if (psiDisjunctionType == null) {
                    $$$reportNull$$$0(5);
                }
                Iterator<PsiType> it = psiDisjunctionType.getDisjunctions().iterator();
                while (it.hasNext()) {
                    if (((Boolean) it.next().accept(this)).booleanValue()) {
                        return true;
                    }
                }
                return false;
            }
        })).booleanValue();
    }

    public static boolean hasUnresolvedComponents(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(17);
        }
        return ((Boolean) psiType.accept(new PsiTypeVisitor<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 3 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "arrayType";
                } else if (i == 2) {
                    objArr[0] = "wildcardType";
                } else if (i == 3) {
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTypesUtil$2";
                } else if (i != 4) {
                    objArr[0] = "classType";
                } else {
                    objArr[0] = ModuleXmlParser.TYPE;
                }
                if (i != 3) {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTypesUtil$2";
                } else {
                    objArr[1] = "visitWildcardType";
                }
                if (i == 1) {
                    objArr[2] = "visitArrayType";
                } else if (i == 2) {
                    objArr[2] = "visitWildcardType";
                } else if (i != 3) {
                    if (i != 4) {
                        objArr[2] = "visitClassType";
                    } else {
                        objArr[2] = "visitType";
                    }
                }
                String str2 = String.format(str, objArr);
                if (i == 3) {
                    throw new IllegalStateException(str2);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitClassType(PsiClassType psiClassType) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(0);
                }
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
                PsiClass element = classResolveResultResolveGenerics.getElement();
                if (element == null) {
                    return true;
                }
                PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
                Iterator<PsiTypeParameter> it = PsiUtil.typeParametersIterable(element).iterator();
                while (it.hasNext()) {
                    PsiType psiTypeSubstitute = substitutor.substitute(it.next());
                    if (psiTypeSubstitute != null && ((Boolean) psiTypeSubstitute.accept(this)).booleanValue()) {
                        return true;
                    }
                }
                return (Boolean) super.visitClassType(psiClassType);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(1);
                }
                return (Boolean) psiArrayType.getComponentType().accept(this);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitWildcardType(PsiWildcardType psiWildcardType) {
                if (psiWildcardType == null) {
                    $$$reportNull$$$0(2);
                }
                PsiType bound = psiWildcardType.getBound();
                Boolean boolValueOf = Boolean.valueOf(bound != null && ((Boolean) bound.accept(this)).booleanValue());
                if (boolValueOf == null) {
                    $$$reportNull$$$0(3);
                }
                return boolValueOf;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitType(PsiType psiType2) {
                if (psiType2 == null) {
                    $$$reportNull$$$0(4);
                }
                return false;
            }
        })).booleanValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PsiType getParameterType(PsiParameter[] psiParameterArr, int i, boolean z) throws Exception {
        if (psiParameterArr == null) {
            $$$reportNull$$$0(18);
        }
        if (i >= psiParameterArr.length) {
            i = psiParameterArr.length - 1;
        }
        PsiParameter psiParameter = psiParameterArr[i];
        PsiType type = psiParameter.getType();
        if ((type instanceof PsiEllipsisType) && z) {
            type = ((PsiEllipsisType) type).getComponentType();
        }
        if (!type.isValid()) {
            PsiUtil.ensureValidType(type, "Invalid type of parameter " + psiParameter + " of " + psiParameter.getClass());
        }
        if (type == null) {
            $$$reportNull$$$0(19);
        }
        return type;
    }

    public static PsiTypeParameter[] filterUnusedTypeParameters(PsiTypeParameter[] psiTypeParameterArr, PsiType... psiTypeArr) {
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(20);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(21);
        }
        if (psiTypeParameterArr.length == 0) {
            PsiTypeParameter[] psiTypeParameterArr2 = PsiTypeParameter.EMPTY_ARRAY;
            if (psiTypeParameterArr2 == null) {
                $$$reportNull$$$0(22);
            }
            return psiTypeParameterArr2;
        }
        TypeParameterSearcher typeParameterSearcher = new TypeParameterSearcher();
        for (PsiType psiType : psiTypeArr) {
            psiType.accept(typeParameterSearcher);
        }
        PsiTypeParameter[] psiTypeParameterArr3 = (PsiTypeParameter[]) typeParameterSearcher.getTypeParameters().toArray(PsiTypeParameter.EMPTY_ARRAY);
        if (psiTypeParameterArr3 == null) {
            $$$reportNull$$$0(23);
        }
        return psiTypeParameterArr3;
    }

    public static PsiTypeParameter[] filterUnusedTypeParameters(PsiType psiType, PsiTypeParameter[] psiTypeParameterArr) {
        if (psiType == null) {
            $$$reportNull$$$0(24);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(25);
        }
        return filterUnusedTypeParameters(psiTypeParameterArr, psiType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAccessibleAt(PsiTypeParameter psiTypeParameter, PsiElement psiElement) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(26);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(27);
        }
        PsiTypeParameterListOwner owner = psiTypeParameter.getOwner();
        if (owner instanceof PsiMethod) {
            return PsiTreeUtil.isAncestor(owner, psiElement, false);
        }
        return (owner instanceof PsiClass) && PsiTreeUtil.isAncestor(owner, psiElement, false) && InheritanceUtil.hasEnclosingInstanceInScope((PsiClass) owner, psiElement, false, false);
    }

    public static boolean allTypeParametersResolved(final PsiElement psiElement, PsiType psiType) {
        if (psiElement == null) {
            $$$reportNull$$$0(28);
        }
        if (psiType == null) {
            $$$reportNull$$$0(29);
        }
        TypeParameterSearcher typeParameterSearcher = new TypeParameterSearcher();
        psiType.accept(typeParameterSearcher);
        return typeParameterSearcher.getTypeParameters().stream().allMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PsiTypesUtil.isAccessibleAt((PsiTypeParameter) obj, psiElement);
            }
        });
    }

    public static PsiType createArrayType(PsiType psiType, int i) {
        if (psiType == null) {
            $$$reportNull$$$0(30);
        }
        for (int i2 = 0; i2 < i; i2++) {
            psiType = psiType.createArrayType();
        }
        if (psiType == null) {
            $$$reportNull$$$0(31);
        }
        return psiType;
    }

    public static PsiTypeElement replaceWithExplicitType(PsiTypeElement psiTypeElement) {
        PsiType type = psiTypeElement.getType();
        if (!isDenotableType(type, psiTypeElement)) {
            return null;
        }
        Project project = psiTypeElement.getProject();
        return (PsiTypeElement) CodeStyleManager.getInstance(project).reformat(JavaCodeStyleManager.getInstance(project).shortenClassReferences(psiTypeElement.replace(JavaPsiFacade.getElementFactory(project).createTypeElement(type))));
    }

    public static PsiType getTypeByMethod(PsiElement psiElement, PsiExpressionList psiExpressionList, PsiElement psiElement2, boolean z, PsiSubstitutor psiSubstitutor, boolean z2) {
        if (psiElement == null) {
            $$$reportNull$$$0(32);
        }
        if (!(psiElement2 instanceof PsiMethod)) {
            return null;
        }
        PsiMethod psiMethod = (PsiMethod) psiElement2;
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        if (parameters.length == 0) {
            return null;
        }
        PsiExpression[] expressions = psiExpressionList.getExpressions();
        if (!psiMethod.isVarArgs() && parameters.length != expressions.length && !z2) {
            return null;
        }
        while (psiElement.getLightParent() instanceof PsiParenthesizedExpression) {
            psiElement = psiElement.getLightParent();
        }
        int iFind = ArrayUtilRt.find(expressions, psiElement);
        if (iFind < 0) {
            return null;
        }
        PsiType psiTypeSubstitute = psiSubstitutor != null ? psiSubstitutor.substitute(getParameterType(parameters, iFind, z)) : null;
        return (psiSubstitutor == null || !PsiUtil.isRawSubstitutor(psiMethod, psiSubstitutor)) ? psiTypeSubstitute : TypeConversionUtil.erasure(psiTypeSubstitute);
    }

    public static boolean mentionsTypeParameters(PsiType psiType, final Set<PsiTypeParameter> set) {
        if (set == null) {
            $$$reportNull$$$0(33);
        }
        Objects.requireNonNull(set);
        return mentionsTypeParametersOrUnboundedWildcard(psiType, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return set.contains((PsiTypeParameter) obj);
            }
        });
    }

    public static boolean mentionsTypeParameters(PsiType psiType, Predicate<PsiTypeParameter> predicate) {
        if (predicate == null) {
            $$$reportNull$$$0(34);
        }
        return mentionsTypeParametersOrUnboundedWildcard(psiType, predicate);
    }

    public static boolean isUncheckedCall(JavaResolveResult javaResolveResult) {
        PsiElement element = javaResolveResult.getElement();
        if (element instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) element;
            if (PsiUtil.isRawSubstitutor(psiMethod, javaResolveResult.getSubstitutor())) {
                for (PsiParameter psiParameter : psiMethod.getParameterList().getParameters()) {
                    PsiType deepComponentType = psiParameter.getType().getDeepComponentType();
                    if (deepComponentType instanceof PsiClassType) {
                        PsiClassType psiClassType = (PsiClassType) deepComponentType;
                        PsiClass element2 = psiClassType.resolveGenerics().getElement();
                        if (element2 instanceof PsiTypeParameter) {
                            return true;
                        }
                        if (element2 != null && PsiUtil.typeParametersIterator(element2).hasNext() && !psiClassType.isRaw()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean mentionsTypeParametersOrUnboundedWildcard(PsiType psiType, final Predicate<PsiTypeParameter> predicate) {
        if (psiType == null) {
            return false;
        }
        return ((Boolean) psiType.accept(new PsiTypeVisitor<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                switch (i) {
                    case 1:
                        objArr[0] = "wildcardType";
                        break;
                    case 2:
                        objArr[0] = "classType";
                        break;
                    case 3:
                        objArr[0] = "intersectionType";
                        break;
                    case 4:
                        objArr[0] = "methodReferenceType";
                        break;
                    case 5:
                        objArr[0] = "lambdaExpressionType";
                        break;
                    case 6:
                        objArr[0] = "arrayType";
                        break;
                    default:
                        objArr[0] = ModuleXmlParser.TYPE;
                        break;
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTypesUtil$3";
                switch (i) {
                    case 1:
                        objArr[2] = "visitWildcardType";
                        break;
                    case 2:
                        objArr[2] = "visitClassType";
                        break;
                    case 3:
                        objArr[2] = "visitIntersectionType";
                        break;
                    case 4:
                        objArr[2] = "visitMethodReferenceType";
                        break;
                    case 5:
                        objArr[2] = "visitLambdaExpressionType";
                        break;
                    case 6:
                        objArr[2] = "visitArrayType";
                        break;
                    default:
                        objArr[2] = "visitType";
                        break;
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitType(PsiType psiType2) {
                if (psiType2 == null) {
                    $$$reportNull$$$0(0);
                }
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitWildcardType(PsiWildcardType psiWildcardType) {
                if (psiWildcardType == null) {
                    $$$reportNull$$$0(1);
                }
                PsiType bound = psiWildcardType.getBound();
                if (bound != null) {
                    return (Boolean) bound.accept(this);
                }
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitClassType(PsiClassType psiClassType) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(2);
                }
                PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
                PsiClass element = classResolveResultResolveGenerics.getElement();
                if (element != null) {
                    PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
                    Iterator<PsiTypeParameter> it = PsiUtil.typeParametersIterable(element).iterator();
                    while (it.hasNext()) {
                        PsiType psiTypeSubstitute = substitutor.substitute(it.next());
                        if (psiTypeSubstitute != null && ((Boolean) psiTypeSubstitute.accept(this)).booleanValue()) {
                            return true;
                        }
                    }
                }
                return Boolean.valueOf((element instanceof PsiTypeParameter) && predicate.test((PsiTypeParameter) element));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitIntersectionType(PsiIntersectionType psiIntersectionType) {
                if (psiIntersectionType == null) {
                    $$$reportNull$$$0(3);
                }
                for (PsiType psiType2 : psiIntersectionType.getConjuncts()) {
                    if (((Boolean) psiType2.accept(this)).booleanValue()) {
                        return true;
                    }
                }
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitMethodReferenceType(PsiMethodReferenceType psiMethodReferenceType) {
                if (psiMethodReferenceType == null) {
                    $$$reportNull$$$0(4);
                }
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitLambdaExpressionType(PsiLambdaExpressionType psiLambdaExpressionType) {
                if (psiLambdaExpressionType == null) {
                    $$$reportNull$$$0(5);
                }
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public Boolean visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(6);
                }
                return (Boolean) psiArrayType.getComponentType().accept(this);
            }
        })).booleanValue();
    }

    public static boolean classNameEquals(PsiType psiType, String str) {
        PsiClassType psiClassType;
        String className;
        PsiClass psiClassResolve;
        if (str == null) {
            $$$reportNull$$$0(35);
        }
        if ((psiType instanceof PsiClassType) && (className = (psiClassType = (PsiClassType) psiType).getClassName()) != null && str.endsWith(className) && (psiClassResolve = psiClassType.resolve()) != null) {
            return str.equals(psiClassResolve.getQualifiedName());
        }
        return false;
    }

    public static class TypeParameterSearcher extends PsiTypeVisitor<Boolean> {
        private final Set<PsiTypeParameter> myTypeParams = new HashSet();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 3 : 2];
            if (i == 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else if (i == 2) {
                objArr[0] = "arrayType";
            } else if (i == 3) {
                objArr[0] = "classType";
            } else if (i != 4) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTypesUtil$TypeParameterSearcher";
            } else {
                objArr[0] = "wildcardType";
            }
            if (i == 1 || i == 2 || i == 3 || i == 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTypesUtil$TypeParameterSearcher";
            } else {
                objArr[1] = "getTypeParameters";
            }
            if (i == 1) {
                objArr[2] = "visitType";
            } else if (i == 2) {
                objArr[2] = "visitArrayType";
            } else if (i == 3) {
                objArr[2] = "visitClassType";
            } else if (i == 4) {
                objArr[2] = "visitWildcardType";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 3 && i != 4) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        public Set<PsiTypeParameter> getTypeParameters() {
            Set<PsiTypeParameter> set = this.myTypeParams;
            if (set == null) {
                $$$reportNull$$$0(0);
            }
            return set;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public Boolean visitType(PsiType psiType) {
            if (psiType == null) {
                $$$reportNull$$$0(1);
            }
            return false;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public Boolean visitArrayType(PsiArrayType psiArrayType) {
            if (psiArrayType == null) {
                $$$reportNull$$$0(2);
            }
            return (Boolean) psiArrayType.getComponentType().accept(this);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public Boolean visitClassType(PsiClassType psiClassType) {
            if (psiClassType == null) {
                $$$reportNull$$$0(3);
            }
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element instanceof PsiTypeParameter) {
                this.myTypeParams.add((PsiTypeParameter) element);
            }
            if (element != null) {
                PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
                Iterator<PsiTypeParameter> it = PsiUtil.typeParametersIterable(element).iterator();
                while (it.hasNext()) {
                    PsiType psiTypeSubstitute = substitutor.substitute(it.next());
                    if (psiTypeSubstitute != null) {
                        psiTypeSubstitute.accept(this);
                    }
                }
            }
            return false;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public Boolean visitWildcardType(PsiWildcardType psiWildcardType) {
            if (psiWildcardType == null) {
                $$$reportNull$$$0(4);
            }
            PsiType bound = psiWildcardType.getBound();
            if (bound != null) {
                bound.accept(this);
            }
            return false;
        }
    }
}
