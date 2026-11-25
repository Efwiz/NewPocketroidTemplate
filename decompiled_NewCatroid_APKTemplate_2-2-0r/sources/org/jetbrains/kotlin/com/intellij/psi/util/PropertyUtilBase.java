package org.jetbrains.kotlin.com.intellij.psi.util;

import com.huawei.hms.framework.common.ContainerUtils;
import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationTargetUtil;
import org.jetbrains.kotlin.com.intellij.codeInsight.NullableNotNullManager;
import org.jetbrains.kotlin.com.intellij.lang.java.beans.PropertyKind;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.VariableKind;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public class PropertyUtilBase {
    protected static final String GET_PREFIX = PropertyKind.GETTER.prefix;
    protected static final String IS_PREFIX = PropertyKind.BOOLEAN_GETTER.prefix;
    protected static final String SET_PREFIX = PropertyKind.SETTER.prefix;
    private static final String[] ONLY_PUBLIC = {"public"};

    public enum GetterFlavour {
        BOOLEAN,
        GENERIC,
        NOT_A_GETTER
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 7:
            case 12:
            case 16:
            case 20:
            case 22:
            case 24:
            case 32:
            case 33:
            case 34:
            case 38:
            case 41:
            case 42:
            case 43:
            case 45:
            case 49:
            case 55:
            case 61:
            case 68:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 7:
            case 12:
            case 16:
            case 20:
            case 22:
            case 24:
            case 32:
            case 33:
            case 34:
            case 38:
            case 41:
            case 42:
            case 43:
            case 45:
            case 49:
            case 55:
            case 61:
            case 68:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 9:
            case 10:
            case 13:
            case 14:
            case 17:
            case 18:
                objArr[0] = "psiClass";
                break;
            case 3:
            case 5:
            case 8:
            case 11:
            case 15:
            case 19:
                objArr[0] = "visibilityLevels";
                break;
            case 4:
            case 7:
            case 12:
            case 16:
            case 20:
            case 22:
            case 24:
            case 32:
            case 33:
            case 34:
            case 38:
            case 41:
            case 42:
            case 43:
            case 45:
            case 49:
            case 55:
            case 61:
            case 68:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PropertyUtilBase";
                break;
            case 6:
            case 28:
            case 30:
                objArr[0] = "methods";
                break;
            case 21:
            case 23:
                objArr[0] = "aClass";
                break;
            case 25:
            case 26:
            case 27:
            case 29:
            case 48:
            case 50:
            case 51:
            case 52:
            case 53:
            case 66:
                objArr[0] = "propertyName";
                break;
            case 31:
            case 39:
            case 46:
            default:
                objArr[0] = "methodName";
                break;
            case 35:
            case 36:
            case 54:
            case 56:
            case 57:
            case 59:
                objArr[0] = "field";
                break;
            case 37:
                objArr[0] = "fieldName";
                break;
            case 40:
            case 62:
            case 63:
                objArr[0] = "method";
                break;
            case 44:
                objArr[0] = "setterMethod";
                break;
            case 47:
                objArr[0] = "prefix";
                break;
            case 58:
            case 60:
                objArr[0] = "containingClass";
                break;
            case 64:
            case 65:
                objArr[0] = "accessorName";
                break;
            case 67:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getAllProperties";
                break;
            case 7:
                objArr[1] = "getAllAccessors";
                break;
            case 12:
                objArr[1] = "getSetters";
                break;
            case 16:
                objArr[1] = "getGetters";
                break;
            case 20:
                objArr[1] = "getAccessors";
                break;
            case 22:
                objArr[1] = "getReadableProperties";
                break;
            case 24:
                objArr[1] = "getWritableProperties";
                break;
            case 32:
            case 33:
            case 34:
                objArr[1] = "getMethodNameGetterFlavour";
                break;
            case 38:
                objArr[1] = "suggestPropertyName";
                break;
            case 41:
            case 42:
            case 43:
                objArr[1] = "getPropertyNameByGetter";
                break;
            case 45:
                objArr[1] = "getPropertyNameBySetter";
                break;
            case 49:
                objArr[1] = "suggestGetterNames";
                break;
            case 55:
                objArr[1] = "generateGetterPrototype";
                break;
            case 61:
                objArr[1] = "generateSetterPrototype";
                break;
            case 68:
                objArr[1] = "getAccessorName";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PropertyUtilBase";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "getAllProperties";
                break;
            case 4:
            case 7:
            case 12:
            case 16:
            case 20:
            case 22:
            case 24:
            case 32:
            case 33:
            case 34:
            case 38:
            case 41:
            case 42:
            case 43:
            case 45:
            case 49:
            case 55:
            case 61:
            case 68:
                break;
            case 5:
            case 6:
                objArr[2] = "getAllAccessors";
                break;
            case 8:
                objArr[2] = "filterMethods";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "getSetters";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "getGetters";
                break;
            case 17:
            case 18:
            case 19:
                objArr[2] = "getAccessors";
                break;
            case 21:
                objArr[2] = "getReadableProperties";
                break;
            case 23:
                objArr[2] = "getWritableProperties";
                break;
            case 25:
                objArr[2] = "findPropertySetter";
                break;
            case 26:
                objArr[2] = "findPropertyGetter";
                break;
            case 27:
            case 28:
                objArr[2] = "findPropertyGetterWithType";
                break;
            case 29:
            case 30:
                objArr[2] = "findPropertySetterWithType";
                break;
            case 31:
                objArr[2] = "getMethodNameGetterFlavour";
                break;
            case 35:
            case 36:
            case 37:
                objArr[2] = "suggestPropertyName";
                break;
            case 39:
                objArr[2] = "isSetterName";
                break;
            case 40:
            default:
                objArr[2] = "getPropertyName";
                break;
            case 44:
                objArr[2] = "getPropertyNameBySetter";
                break;
            case 46:
            case 47:
                objArr[2] = "checkPrefix";
                break;
            case 48:
                objArr[2] = "suggestGetterNames";
                break;
            case 50:
            case 51:
                objArr[2] = "suggestGetterName";
                break;
            case 52:
            case 53:
                objArr[2] = "suggestSetterName";
                break;
            case 54:
                objArr[2] = "generateGetterPrototype";
                break;
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
                objArr[2] = "generateSetterPrototype";
                break;
            case 62:
                objArr[2] = "hasGetterSignature";
                break;
            case 63:
                objArr[2] = "getSingleReturnValue";
                break;
            case 64:
                objArr[2] = "getPropertyKind";
                break;
            case 65:
                objArr[2] = "getPropertyNameAndKind";
                break;
            case 66:
            case 67:
                objArr[2] = "getAccessorName";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 7:
            case 12:
            case 16:
            case 20:
            case 22:
            case 24:
            case 32:
            case 33:
            case 34:
            case 38:
            case 41:
            case 42:
            case 43:
            case 45:
            case 49:
            case 55:
            case 61:
            case 68:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public static String getPropertyName(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return StringUtil.getPropertyName(str);
    }

    public static Map<String, PsiMethod> getAllProperties(PsiClass psiClass, boolean z, boolean z2) {
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        return getAllProperties(psiClass, z, z2, true);
    }

    public static Map<String, PsiMethod> getAllProperties(PsiClass psiClass, boolean z, boolean z2, boolean z3) {
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        return getAllProperties(z, z2, z3 ? psiClass.getAllMethods() : psiClass.getMethods());
    }

    public static Map<String, PsiMethod> getAllProperties(boolean z, boolean z2, PsiMethod[] psiMethodArr) {
        return getAllProperties(z, z2, false, ONLY_PUBLIC, psiMethodArr);
    }

    public static Map<String, PsiMethod> getAllProperties(boolean z, boolean z2, boolean z3, String[] strArr, PsiMethod[] psiMethodArr) {
        if (strArr == null) {
            $$$reportNull$$$0(3);
        }
        HashMap map = new HashMap();
        for (PsiMethod psiMethod : psiMethodArr) {
            if (!filterMethods(psiMethod, z3, strArr) && ((z && isSimplePropertySetter(psiMethod)) || (z2 && isSimplePropertyGetter(psiMethod)))) {
                map.put(getPropertyName(psiMethod), psiMethod);
            }
        }
        return map;
    }

    public static Pair<Map<String, PsiMethod>, Map<String, PsiMethod>> getAllAccessors(boolean z, boolean z2, boolean z3, String[] strArr, PsiMethod[] psiMethodArr) {
        if (strArr == null) {
            $$$reportNull$$$0(5);
        }
        if (psiMethodArr == null) {
            $$$reportNull$$$0(6);
        }
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        for (PsiMethod psiMethod : psiMethodArr) {
            if (!filterMethods(psiMethod, z3, strArr)) {
                if (z2 && isSimplePropertyGetter(psiMethod)) {
                    map.put(getPropertyNameByGetter(psiMethod), psiMethod);
                }
                if (z && isSimplePropertySetter(psiMethod)) {
                    map2.put(getPropertyNameBySetter(psiMethod), psiMethod);
                }
            }
        }
        Pair<Map<String, PsiMethod>, Map<String, PsiMethod>> pairCreate = Pair.create(map, map2);
        if (pairCreate == null) {
            $$$reportNull$$$0(7);
        }
        return pairCreate;
    }

    private static boolean filterMethods(final PsiMethod psiMethod, boolean z, String[] strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(8);
        }
        if ((!z && psiMethod.hasModifierProperty("static")) || !ContainerUtil.exists(strArr, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PropertyUtilBase$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return psiMethod.hasModifierProperty((String) obj);
            }
        })) {
            return true;
        }
        PsiClass containingClass = psiMethod.getContainingClass();
        if (containingClass == null) {
            return false;
        }
        return CommonClassNames.JAVA_LANG_OBJECT.equals(containingClass.getQualifiedName());
    }

    public static List<PsiMethod> getSetters(PsiClass psiClass, String str) {
        if (psiClass == null) {
            $$$reportNull$$$0(9);
        }
        return getSetters(psiClass, str, false, ONLY_PUBLIC);
    }

    public static List<PsiMethod> getSetters(PsiClass psiClass, String str, boolean z, String[] strArr) {
        if (psiClass == null) {
            $$$reportNull$$$0(10);
        }
        if (strArr == null) {
            $$$reportNull$$$0(11);
        }
        PsiMethod[] psiMethodArrFindMethodsByName = psiClass.findMethodsByName(suggestSetterName(str), true);
        ArrayList arrayList = new ArrayList(psiMethodArrFindMethodsByName.length);
        for (PsiMethod psiMethod : psiMethodArrFindMethodsByName) {
            if (!filterMethods(psiMethod, z, strArr) && isSimplePropertySetter(psiMethod)) {
                arrayList.add(psiMethod);
            }
        }
        return arrayList;
    }

    public static List<PsiMethod> getGetters(PsiClass psiClass, String str) {
        if (psiClass == null) {
            $$$reportNull$$$0(13);
        }
        return getGetters(psiClass, str, false, ONLY_PUBLIC);
    }

    public static List<PsiMethod> getGetters(PsiClass psiClass, String str, boolean z, String[] strArr) {
        if (psiClass == null) {
            $$$reportNull$$$0(14);
        }
        if (strArr == null) {
            $$$reportNull$$$0(15);
        }
        String[] strArrSuggestGetterNames = suggestGetterNames(str);
        ArrayList arrayList = new ArrayList();
        for (String str2 : strArrSuggestGetterNames) {
            for (PsiMethod psiMethod : psiClass.findMethodsByName(str2, true)) {
                if (!filterMethods(psiMethod, z, strArr) && isSimplePropertyGetter(psiMethod)) {
                    arrayList.add(psiMethod);
                }
            }
        }
        return arrayList;
    }

    public static List<PsiMethod> getAccessors(PsiClass psiClass, String str) {
        if (psiClass == null) {
            $$$reportNull$$$0(17);
        }
        return getAccessors(psiClass, str, false, ONLY_PUBLIC);
    }

    public static List<PsiMethod> getAccessors(PsiClass psiClass, String str, boolean z, String[] strArr) {
        if (psiClass == null) {
            $$$reportNull$$$0(18);
        }
        if (strArr == null) {
            $$$reportNull$$$0(19);
        }
        List<PsiMethod> listConcat = ContainerUtil.concat((List) getGetters(psiClass, str, z, strArr), (List) getSetters(psiClass, str, z, strArr));
        if (listConcat == null) {
            $$$reportNull$$$0(20);
        }
        return listConcat;
    }

    public static String[] getReadableProperties(PsiClass psiClass, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(21);
        }
        ArrayList arrayList = new ArrayList();
        for (PsiMethod psiMethod : z ? psiClass.getAllMethods() : psiClass.getMethods()) {
            if (!CommonClassNames.JAVA_LANG_OBJECT.equals(psiMethod.getContainingClass().getQualifiedName()) && isSimplePropertyGetter(psiMethod)) {
                arrayList.add(getPropertyName(psiMethod));
            }
        }
        String[] stringArray = ArrayUtilRt.toStringArray(arrayList);
        if (stringArray == null) {
            $$$reportNull$$$0(22);
        }
        return stringArray;
    }

    public static String[] getWritableProperties(PsiClass psiClass, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(23);
        }
        ArrayList arrayList = new ArrayList();
        for (PsiMethod psiMethod : z ? psiClass.getAllMethods() : psiClass.getMethods()) {
            if (!CommonClassNames.JAVA_LANG_OBJECT.equals(psiMethod.getContainingClass().getQualifiedName()) && isSimplePropertySetter(psiMethod)) {
                arrayList.add(getPropertyName(psiMethod));
            }
        }
        String[] stringArray = ArrayUtilRt.toStringArray(arrayList);
        if (stringArray == null) {
            $$$reportNull$$$0(24);
        }
        return stringArray;
    }

    public static PsiType getPropertyType(PsiMember psiMember) {
        if (psiMember instanceof PsiField) {
            return ((PsiField) psiMember).getType();
        }
        if (!(psiMember instanceof PsiMethod)) {
            return null;
        }
        PsiMethod psiMethod = (PsiMethod) psiMember;
        if (isSimplePropertyGetter(psiMethod)) {
            return psiMethod.getReturnType();
        }
        if (isSimplePropertySetter(psiMethod)) {
            return psiMethod.getParameterList().getParameters()[0].getType();
        }
        return null;
    }

    public static PsiMethod findPropertySetter(PsiClass psiClass, String str, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        if (psiClass == null) {
            return null;
        }
        for (PsiMethod psiMethod : psiClass.findMethodsByName(suggestSetterName(str), z2)) {
            if (psiMethod.hasModifierProperty("static") == z && isSimplePropertySetter(psiMethod) && getPropertyNameBySetter(psiMethod).equals(str)) {
                return psiMethod;
            }
        }
        return null;
    }

    public static PsiField findPropertyField(PsiClass psiClass, String str, boolean z) {
        for (PsiField psiField : psiClass.getAllFields()) {
            if (psiField.hasModifierProperty("static") == z && str.equals(suggestPropertyName(psiField))) {
                return psiField;
            }
        }
        return null;
    }

    public static PsiMethod findPropertyGetter(PsiClass psiClass, String str, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        if (psiClass == null) {
            return null;
        }
        for (String str2 : suggestGetterNames(str)) {
            for (PsiMethod psiMethod : psiClass.findMethodsByName(str2, z2)) {
                if (psiMethod.hasModifierProperty("static") == z && isSimplePropertyGetter(psiMethod) && getPropertyNameByGetter(psiMethod).equals(str)) {
                    return psiMethod;
                }
            }
        }
        return null;
    }

    public static PsiMethod findPropertyGetterWithType(final String str, final boolean z, final PsiType psiType, Collection<? extends PsiMethod> collection) {
        if (str == null) {
            $$$reportNull$$$0(27);
        }
        if (collection == null) {
            $$$reportNull$$$0(28);
        }
        return (PsiMethod) ContainerUtil.find(collection, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PropertyUtilBase$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PropertyUtilBase.lambda$findPropertyGetterWithType$1(z, str, psiType, (PsiMethod) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$findPropertyGetterWithType$1(boolean z, String str, PsiType psiType, PsiMethod psiMethod) {
        return psiMethod.hasModifierProperty("static") == z && isSimplePropertyGetter(psiMethod) && getPropertyNameByGetter(psiMethod).equals(str) && psiType.equals(psiMethod.getReturnType());
    }

    public static boolean isSimplePropertyAccessor(PsiMethod psiMethod) {
        return isSimplePropertyGetter(psiMethod) || isSimplePropertySetter(psiMethod);
    }

    public static PsiMethod findPropertySetterWithType(final String str, final boolean z, final PsiType psiType, Collection<? extends PsiMethod> collection) {
        if (str == null) {
            $$$reportNull$$$0(29);
        }
        if (collection == null) {
            $$$reportNull$$$0(30);
        }
        return (PsiMethod) ContainerUtil.find(collection, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PropertyUtilBase$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PropertyUtilBase.lambda$findPropertySetterWithType$2(z, str, psiType, (PsiMethod) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$findPropertySetterWithType$2(boolean z, String str, PsiType psiType, PsiMethod psiMethod) {
        return psiMethod.hasModifierProperty("static") == z && isSimplePropertySetter(psiMethod) && getPropertyNameBySetter(psiMethod).equals(str) && psiType.equals(psiMethod.getParameterList().getParameters()[0].getType());
    }

    public static GetterFlavour getMethodNameGetterFlavour(String str) {
        if (str == null) {
            $$$reportNull$$$0(31);
        }
        if (checkPrefix(str, GET_PREFIX)) {
            GetterFlavour getterFlavour = GetterFlavour.GENERIC;
            if (getterFlavour == null) {
                $$$reportNull$$$0(32);
            }
            return getterFlavour;
        }
        if (checkPrefix(str, IS_PREFIX)) {
            GetterFlavour getterFlavour2 = GetterFlavour.BOOLEAN;
            if (getterFlavour2 == null) {
                $$$reportNull$$$0(33);
            }
            return getterFlavour2;
        }
        GetterFlavour getterFlavour3 = GetterFlavour.NOT_A_GETTER;
        if (getterFlavour3 == null) {
            $$$reportNull$$$0(34);
        }
        return getterFlavour3;
    }

    public static boolean hasAccessorName(PsiMethod psiMethod) {
        String str = psiMethod.get$name();
        return isSetterName(str) || getMethodNameGetterFlavour(str) != GetterFlavour.NOT_A_GETTER;
    }

    public static boolean isSimplePropertyGetter(PsiMethod psiMethod) {
        return hasGetterName(psiMethod) && psiMethod.getParameterList().isEmpty();
    }

    public static boolean hasGetterName(PsiMethod psiMethod) {
        if (psiMethod == null || psiMethod.isConstructor()) {
            return false;
        }
        int i = AnonymousClass1.$SwitchMap$com$intellij$psi$util$PropertyUtilBase$GetterFlavour[getMethodNameGetterFlavour(psiMethod.get$name()).ordinal()];
        if (i == 1) {
            return !PsiType.VOID.equals(psiMethod.getReturnType());
        }
        if (i != 2) {
            return false;
        }
        return isBoolean(psiMethod.getReturnType());
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.util.PropertyUtilBase$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$util$PropertyUtilBase$GetterFlavour;

        static {
            int[] iArr = new int[GetterFlavour.values().length];
            $SwitchMap$com$intellij$psi$util$PropertyUtilBase$GetterFlavour = iArr;
            try {
                iArr[GetterFlavour.GENERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$util$PropertyUtilBase$GetterFlavour[GetterFlavour.BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$util$PropertyUtilBase$GetterFlavour[GetterFlavour.NOT_A_GETTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static boolean isBoolean(PsiType psiType) {
        return PsiType.BOOLEAN.equals(psiType);
    }

    public static String suggestPropertyName(PsiField psiField) {
        if (psiField == null) {
            $$$reportNull$$$0(35);
        }
        return suggestPropertyName(psiField, psiField.get$name());
    }

    public static String suggestPropertyName(PsiField psiField, String str) {
        if (psiField == null) {
            $$$reportNull$$$0(36);
        }
        if (str == null) {
            $$$reportNull$$$0(37);
        }
        JavaCodeStyleManager javaCodeStyleManager = JavaCodeStyleManager.getInstance(psiField.getProject());
        String strVariableNameToPropertyName = javaCodeStyleManager.variableNameToPropertyName(str, javaCodeStyleManager.getVariableKind(psiField));
        if (!psiField.hasModifierProperty("static") && isBoolean(psiField.getType())) {
            String str2 = IS_PREFIX;
            if (strVariableNameToPropertyName.startsWith(str2) && strVariableNameToPropertyName.length() > str2.length() && Character.isUpperCase(strVariableNameToPropertyName.charAt(str2.length()))) {
                strVariableNameToPropertyName = Introspector.decapitalize(strVariableNameToPropertyName.substring(str2.length()));
            }
        }
        if (strVariableNameToPropertyName == null) {
            $$$reportNull$$$0(38);
        }
        return strVariableNameToPropertyName;
    }

    public static String suggestGetterName(PsiField psiField) {
        return suggestGetterName(suggestPropertyName(psiField), psiField.getType());
    }

    public static String suggestSetterName(PsiField psiField) {
        return suggestSetterName(suggestPropertyName(psiField));
    }

    public static String getPropertyName(PsiMember psiMember) {
        if (psiMember instanceof PsiMethod) {
            return getPropertyName((PsiMethod) psiMember);
        }
        if (psiMember instanceof PsiField) {
            return psiMember.get$name();
        }
        return null;
    }

    public static boolean isSimplePropertySetter(PsiMethod psiMethod) {
        if (psiMethod == null || psiMethod.isConstructor() || !isSetterName(psiMethod.get$name()) || psiMethod.getParameterList().getParametersCount() != 1) {
            return false;
        }
        PsiType returnType = psiMethod.getReturnType();
        if (returnType == null || PsiType.VOID.equals(returnType)) {
            return true;
        }
        return Comparing.equal(PsiUtil.resolveClassInType(TypeConversionUtil.erasure(returnType)), psiMethod.getContainingClass());
    }

    public static boolean isSetterName(String str) {
        if (str == null) {
            $$$reportNull$$$0(39);
        }
        return checkPrefix(str, SET_PREFIX);
    }

    public static String getPropertyName(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(40);
        }
        if (isSimplePropertyGetter(psiMethod)) {
            return getPropertyNameByGetter(psiMethod);
        }
        if (isSimplePropertySetter(psiMethod)) {
            return getPropertyNameBySetter(psiMethod);
        }
        return null;
    }

    public static String getPropertyNameByGetter(PsiMethod psiMethod) {
        String str = psiMethod.get$name();
        if (str.startsWith(GET_PREFIX)) {
            String strDecapitalize = StringUtil.decapitalize(str.substring(3));
            if (strDecapitalize == null) {
                $$$reportNull$$$0(41);
            }
            return strDecapitalize;
        }
        if (!str.startsWith(IS_PREFIX)) {
            if (str == null) {
                $$$reportNull$$$0(43);
            }
            return str;
        }
        String strDecapitalize2 = StringUtil.decapitalize(str.substring(2));
        if (strDecapitalize2 == null) {
            $$$reportNull$$$0(42);
        }
        return strDecapitalize2;
    }

    public static String getPropertyNameBySetter(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(44);
        }
        String strDecapitalize = Introspector.decapitalize(psiMethod.get$name().substring(3));
        if (strDecapitalize == null) {
            $$$reportNull$$$0(45);
        }
        return strDecapitalize;
    }

    private static boolean checkPrefix(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(46);
        }
        if (str2 == null) {
            $$$reportNull$$$0(47);
        }
        if (str.startsWith(str2) && str.length() > str2.length()) {
            if (!Character.isLowerCase(str.charAt(str2.length()))) {
                return true;
            }
            if (str.length() != str2.length() + 1 && !Character.isLowerCase(str.charAt(str2.length() + 1))) {
                return true;
            }
        }
        return false;
    }

    public static String[] suggestGetterNames(String str) {
        if (str == null) {
            $$$reportNull$$$0(48);
        }
        String strCapitalizeWithJavaBeanConvention = StringUtil.capitalizeWithJavaBeanConvention(StringUtil.sanitizeJavaIdentifier(str));
        return new String[]{IS_PREFIX + strCapitalizeWithJavaBeanConvention, GET_PREFIX + strCapitalizeWithJavaBeanConvention};
    }

    public static String suggestGetterName(String str, PsiType psiType) {
        if (str == null) {
            $$$reportNull$$$0(50);
        }
        return suggestGetterName(str, psiType, null);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002a  */
    public static String suggestGetterName(String str, PsiType psiType, String str2) {
        if (str == null) {
            $$$reportNull$$$0(51);
        }
        StringBuilder sb = new StringBuilder(StringUtil.capitalizeWithJavaBeanConvention(StringUtil.sanitizeJavaIdentifier(str)));
        if (!isBoolean(psiType)) {
            sb.insert(0, GET_PREFIX);
        } else if (str2 != null) {
            String str3 = GET_PREFIX;
            if (!str2.startsWith(str3)) {
                sb.insert(0, IS_PREFIX);
            } else {
                sb.insert(0, str3);
            }
        } else {
            sb.insert(0, IS_PREFIX);
        }
        return sb.toString();
    }

    public static String suggestSetterName(String str) {
        if (str == null) {
            $$$reportNull$$$0(52);
        }
        return suggestSetterName(str, SET_PREFIX);
    }

    public static String suggestSetterName(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(53);
        }
        String strSanitizeJavaIdentifier = StringUtil.sanitizeJavaIdentifier(str);
        if (StringUtil.isEmpty(str2)) {
            return strSanitizeJavaIdentifier;
        }
        StringBuilder sb = new StringBuilder(StringUtil.capitalizeWithJavaBeanConvention(strSanitizeJavaIdentifier));
        sb.insert(0, str2);
        return sb.toString();
    }

    public static PsiMethod generateGetterPrototype(PsiField psiField) {
        if (psiField == null) {
            $$$reportNull$$$0(54);
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiField.getProject());
        Project project = psiField.getProject();
        String str = psiField.get$name();
        PsiMethod psiMethodCreateMethod = elementFactory.createMethod(suggestGetterName(psiField), AnnotationTargetUtil.keepStrictlyTypeUseAnnotations(psiField.getModifierList(), psiField.getType()));
        PsiUtil.setModifierProperty(psiMethodCreateMethod, "public", true);
        if (psiField.hasModifierProperty("static")) {
            PsiUtil.setModifierProperty(psiMethodCreateMethod, "static", true);
        }
        NullableNotNullManager.getInstance(project).copyNullableOrNotNullAnnotation(psiField, psiMethodCreateMethod);
        ((PsiCodeBlock) Objects.requireNonNull(psiMethodCreateMethod.getBody())).replace(elementFactory.createCodeBlockFromText("{\nreturn " + str + ";\n}", null));
        PsiMethod psiMethod = (PsiMethod) CodeStyleManager.getInstance(project).reformat(psiMethodCreateMethod);
        if (psiMethod == null) {
            $$$reportNull$$$0(55);
        }
        return psiMethod;
    }

    public static PsiMethod generateSetterPrototype(PsiField psiField) {
        if (psiField == null) {
            $$$reportNull$$$0(56);
        }
        return generateSetterPrototype(psiField, psiField.getContainingClass());
    }

    public static PsiMethod generateSetterPrototype(PsiField psiField, PsiClass psiClass) {
        if (psiField == null) {
            $$$reportNull$$$0(57);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(58);
        }
        return generateSetterPrototype(psiField, psiClass, false);
    }

    public static PsiMethod generateSetterPrototype(PsiField psiField, PsiClass psiClass, boolean z) {
        if (psiField == null) {
            $$$reportNull$$$0(59);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(60);
        }
        Project project = psiField.getProject();
        JavaCodeStyleManager javaCodeStyleManager = JavaCodeStyleManager.getInstance(project);
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiField.getProject());
        String str = psiField.get$name();
        boolean zHasModifierProperty = psiField.hasModifierProperty("static");
        String strVariableNameToPropertyName = javaCodeStyleManager.variableNameToPropertyName(str, javaCodeStyleManager.getVariableKind(psiField));
        PsiMethod psiMethodCreateMethodFromText = elementFactory.createMethodFromText(elementFactory.createMethod(suggestSetterName(psiField), z ? elementFactory.createType(psiClass) : PsiType.VOID).getText(), psiField);
        String strPropertyNameToVariableName = javaCodeStyleManager.propertyNameToVariableName(strVariableNameToPropertyName, VariableKind.PARAMETER);
        PsiParameter psiParameterCreateParameter = elementFactory.createParameter(strPropertyNameToVariableName, AnnotationTargetUtil.keepStrictlyTypeUseAnnotations(psiField.getModifierList(), psiField.getType()));
        NullableNotNullManager.getInstance(project).copyNullableOrNotNullAnnotation(psiField, psiParameterCreateParameter);
        psiMethodCreateMethodFromText.getParameterList().add(psiParameterCreateParameter);
        PsiUtil.setModifierProperty(psiMethodCreateMethodFromText, "public", true);
        PsiUtil.setModifierProperty(psiMethodCreateMethodFromText, "static", zHasModifierProperty);
        StringBuilder sb = new StringBuilder("{\n");
        if (str.equals(strPropertyNameToVariableName)) {
            if (!zHasModifierProperty) {
                sb.append("this.");
            } else {
                String name2 = psiClass.get$name();
                if (name2 != null) {
                    sb.append(name2);
                    sb.append(".");
                }
            }
        }
        sb.append(str);
        sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
        sb.append(strPropertyNameToVariableName);
        sb.append(";\n");
        if (z) {
            sb.append("return this;\n");
        }
        sb.append("}");
        ((PsiCodeBlock) Objects.requireNonNull(psiMethodCreateMethodFromText.getBody())).replace(elementFactory.createCodeBlockFromText(sb.toString(), null));
        PsiMethod psiMethod = (PsiMethod) CodeStyleManager.getInstance(project).reformat(psiMethodCreateMethodFromText);
        if (psiMethod == null) {
            $$$reportNull$$$0(61);
        }
        return psiMethod;
    }

    public static PsiTypeElement getPropertyTypeElement(PsiMember psiMember) {
        if (psiMember instanceof PsiField) {
            return ((PsiField) psiMember).getTypeElement();
        }
        if (!(psiMember instanceof PsiMethod)) {
            return null;
        }
        PsiMethod psiMethod = (PsiMethod) psiMember;
        if (isSimplePropertyGetter(psiMethod)) {
            return psiMethod.getReturnTypeElement();
        }
        if (isSimplePropertySetter(psiMethod)) {
            return psiMethod.getParameterList().getParameters()[0].getTypeElement();
        }
        return null;
    }

    public static PsiIdentifier getPropertyNameIdentifier(PsiMember psiMember) {
        if (psiMember instanceof PsiField) {
            return ((PsiField) psiMember).mo7392getNameIdentifier();
        }
        if (psiMember instanceof PsiMethod) {
            return ((PsiMethod) psiMember).mo7392getNameIdentifier();
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x004d  */
    public static PsiField findPropertyFieldByMember(PsiMember psiMember) {
        PsiMethod psiMethod;
        PsiType returnType;
        PsiExpression returnValue;
        if (psiMember instanceof PsiField) {
            return (PsiField) psiMember;
        }
        if (!(psiMember instanceof PsiMethod) || (returnType = (psiMethod = (PsiMethod) psiMember).getReturnType()) == null) {
            return null;
        }
        PsiCodeBlock body = psiMethod.getBody();
        PsiStatement[] statements = body == null ? null : body.getStatements();
        PsiStatement psiStatement = (statements == null || statements.length != 1) ? null : statements[0];
        if (PsiType.VOID.equals(returnType)) {
            PsiExpression expression = psiStatement instanceof PsiExpressionStatement ? ((PsiExpressionStatement) psiStatement).getExpression() : null;
            if (expression instanceof PsiAssignmentExpression) {
                returnValue = ((PsiAssignmentExpression) expression).getLExpression();
            } else {
                returnValue = null;
            }
        } else if (psiStatement instanceof PsiReturnStatement) {
            returnValue = ((PsiReturnStatement) psiStatement).getReturnValue();
        } else {
            returnValue = null;
        }
        PsiElement psiElementResolve = returnValue instanceof PsiReferenceExpression ? ((PsiReferenceExpression) returnValue).resolve() : null;
        if (psiElementResolve instanceof PsiField) {
            PsiField psiField = (PsiField) psiElementResolve;
            PsiClass containingClass = psiMember.getContainingClass();
            PsiClass containingClass2 = psiField.getContainingClass();
            if (containingClass != null && containingClass2 != null && (containingClass == containingClass2 || containingClass.isInheritor(containingClass2, true))) {
                return psiField;
            }
        }
        return null;
    }

    public static PsiMethod findSetterForField(PsiField psiField) {
        return findPropertySetter(psiField.getContainingClass(), suggestPropertyName(psiField), psiField.hasModifierProperty("static"), true);
    }

    public static PsiMethod findGetterForField(PsiField psiField) {
        return findPropertyGetter(psiField.getContainingClass(), suggestPropertyName(psiField), psiField.hasModifierProperty("static"), true);
    }

    public static PsiExpression getGetterReturnExpression(PsiMethod psiMethod) {
        if (psiMethod == null || !hasGetterSignature(psiMethod)) {
            return null;
        }
        return getSingleReturnValue(psiMethod);
    }

    private static boolean hasGetterSignature(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(62);
        }
        return isSimplePropertyGetter(psiMethod);
    }

    public static PsiExpression getSingleReturnValue(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(63);
        }
        PsiCodeBlock body = psiMethod.getBody();
        if (body == null) {
            return null;
        }
        PsiStatement[] statements = body.getStatements();
        PsiStatement psiStatement = statements.length != 1 ? null : statements[0];
        if (psiStatement instanceof PsiReturnStatement) {
            return ((PsiReturnStatement) psiStatement).getReturnValue();
        }
        return null;
    }

    public static PropertyKind getPropertyKind(String str) {
        if (str == null) {
            $$$reportNull$$$0(64);
        }
        for (PropertyKind propertyKind : PropertyKind.values()) {
            String str2 = propertyKind.prefix;
            int length = str2.length();
            if (str.startsWith(str2) && str.length() > length) {
                return propertyKind;
            }
        }
        return null;
    }

    public static Pair<String, PropertyKind> getPropertyNameAndKind(String str) {
        if (str == null) {
            $$$reportNull$$$0(65);
        }
        PropertyKind propertyKind = getPropertyKind(str);
        if (propertyKind == null) {
            return null;
        }
        return Pair.create(Introspector.decapitalize(str.substring(propertyKind.prefix.length())), propertyKind);
    }

    public static String getAccessorName(String str, PropertyKind propertyKind) {
        if (str == null) {
            $$$reportNull$$$0(66);
        }
        if (propertyKind == null) {
            $$$reportNull$$$0(67);
        }
        String str2 = propertyKind.prefix + StringUtil.capitalizeWithJavaBeanConvention(str);
        if (str2 == null) {
            $$$reportNull$$$0(68);
        }
        return str2;
    }
}
