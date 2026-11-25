package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmPrimitiveType;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmPrimitiveTypeKind;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiPrimitiveType extends PsiType.Stub implements JvmPrimitiveType {
    private static final Map<String, PsiPrimitiveType> ourQNameToUnboxed = new HashMap();
    private final JvmPrimitiveTypeKind myKind;
    private final String myName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 9:
            case 14:
            case 15:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
                i2 = 2;
                break;
            case 9:
            case 14:
            case 15:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 9:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 2:
            case 4:
                objArr[0] = "name";
                break;
            case 5:
            default:
                objArr[0] = "annotations";
                break;
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiPrimitiveType";
                break;
            case 14:
                objArr[0] = "text";
                break;
            case 15:
                objArr[0] = "visitor";
                break;
            case 17:
                objArr[0] = "context";
                break;
            case 18:
                objArr[0] = "manager";
                break;
            case 19:
                objArr[0] = "resolveScope";
                break;
        }
        switch (i) {
            case 6:
            case 8:
                objArr[1] = "getName";
                break;
            case 7:
                objArr[1] = "getKind";
                break;
            case 9:
            case 14:
            case 15:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiPrimitiveType";
                break;
            case 10:
                objArr[1] = "annotate";
                break;
            case 11:
                objArr[1] = "getPresentableText";
                break;
            case 12:
                objArr[1] = "getCanonicalText";
                break;
            case 13:
                objArr[1] = "getInternalCanonicalText";
                break;
            case 16:
                objArr[1] = "getSuperTypes";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
                break;
            case 9:
                objArr[2] = "annotate";
                break;
            case 14:
                objArr[2] = "equalsToText";
                break;
            case 15:
                objArr[2] = "accept";
                break;
            case 17:
            case 18:
            case 19:
                objArr[2] = "getBoxedType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
                throw new IllegalStateException(str2);
            case 9:
            case 14:
            case 15:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        return null;
    }

    PsiPrimitiveType(JvmPrimitiveTypeKind jvmPrimitiveTypeKind) {
        super(PsiAnnotation.EMPTY_ARRAY);
        this.myKind = jvmPrimitiveTypeKind;
        if (jvmPrimitiveTypeKind != null) {
            ourQNameToUnboxed.put(jvmPrimitiveTypeKind.getBoxedFqn(), this);
        }
        this.myName = getName(jvmPrimitiveTypeKind);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiPrimitiveType(JvmPrimitiveTypeKind jvmPrimitiveTypeKind, PsiAnnotation[] psiAnnotationArr) {
        super(psiAnnotationArr);
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(0);
        }
        this.myKind = jvmPrimitiveTypeKind;
        this.myName = getName(jvmPrimitiveTypeKind);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiPrimitiveType(JvmPrimitiveTypeKind jvmPrimitiveTypeKind, TypeAnnotationProvider typeAnnotationProvider) {
        super(typeAnnotationProvider);
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(1);
        }
        this.myKind = jvmPrimitiveTypeKind;
        this.myName = getName(jvmPrimitiveTypeKind);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiPrimitiveType(String str, TypeAnnotationProvider typeAnnotationProvider) {
        super(typeAnnotationProvider);
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(3);
        }
        JvmPrimitiveTypeKind kindByName = JvmPrimitiveTypeKind.getKindByName(str);
        if (kindByName == null) {
            throw new NoSuchElementException("Cannot find primitive type: " + str);
        }
        this.myKind = kindByName;
        this.myName = str;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @Deprecated
    public PsiPrimitiveType(String str, PsiAnnotation[] psiAnnotationArr) {
        super(psiAnnotationArr);
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(5);
        }
        this.myKind = null;
        this.myName = str;
    }

    private static String getName(JvmPrimitiveTypeKind jvmPrimitiveTypeKind) {
        String name2 = jvmPrimitiveTypeKind == null ? "null" : jvmPrimitiveTypeKind.getName();
        if (name2 == null) {
            $$$reportNull$$$0(6);
        }
        return name2;
    }

    public JvmPrimitiveTypeKind getKind() {
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind = (JvmPrimitiveTypeKind) Objects.requireNonNull(this.myKind, "getKind() called on PsiType.NULL\nIf your code works with JvmElement API then this should not happen unless some implementation improperly returns PsiType.NULL from JvmMethod.getReturnType() (or any other available methods).\nIf your code works with PsiType-s then you must check if this type is PsiType.NULL type before calling this method");
        if (jvmPrimitiveTypeKind == null) {
            $$$reportNull$$$0(7);
        }
        return jvmPrimitiveTypeKind;
    }

    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiPrimitiveType annotate(TypeAnnotationProvider typeAnnotationProvider) {
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(9);
        }
        PsiPrimitiveType psiPrimitiveType = (PsiPrimitiveType) super.annotate(typeAnnotationProvider);
        if (psiPrimitiveType == null) {
            $$$reportNull$$$0(10);
        }
        return psiPrimitiveType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText(boolean z) {
        String text = getText(false, z);
        if (text == null) {
            $$$reportNull$$$0(11);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText(boolean z) {
        String text = getText(true, z);
        if (text == null) {
            $$$reportNull$$$0(12);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        String canonicalText = getCanonicalText(true);
        if (canonicalText == null) {
            $$$reportNull$$$0(13);
        }
        return canonicalText;
    }

    private String getText(boolean z, boolean z2) {
        PsiAnnotation[] annotations = z2 ? getAnnotations() : PsiAnnotation.EMPTY_ARRAY;
        if (annotations.length == 0) {
            return this.myName;
        }
        StringBuilder sb = new StringBuilder();
        PsiNameHelper.appendAnnotations(sb, annotations, z);
        sb.append(this.myName);
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        for (PsiAnnotation psiAnnotation : getAnnotations()) {
            if (!psiAnnotation.isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        return this.myName.equals(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(15);
        }
        return psiTypeVisitor.visitPrimitiveType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        PsiType[] psiTypeArr = EMPTY_ARRAY;
        if (psiTypeArr == null) {
            $$$reportNull$$$0(16);
        }
        return psiTypeArr;
    }

    public static PsiPrimitiveType getUnboxedType(PsiType psiType) {
        PsiClass psiClassResolve;
        PsiPrimitiveType psiPrimitiveType;
        if (!(psiType instanceof PsiClassType)) {
            return null;
        }
        PsiUtil.ensureValidType(psiType);
        PsiClassType psiClassType = (PsiClassType) psiType;
        if (!psiClassType.getLanguageLevel().isAtLeast(LanguageLevel.JDK_1_5) || (psiClassResolve = psiClassType.resolve()) == null || (psiPrimitiveType = ourQNameToUnboxed.get(psiClassResolve.getQualifiedName())) == null) {
            return null;
        }
        return psiPrimitiveType.annotate(psiType.getAnnotationProvider());
    }

    public static PsiPrimitiveType getOptionallyUnboxedType(PsiType psiType) {
        return psiType instanceof PsiPrimitiveType ? (PsiPrimitiveType) psiType : getUnboxedType(psiType);
    }

    public static PsiPrimitiveType fromJvmTypeDescriptor(char c) {
        if (c == 'F') {
            return PsiType.FLOAT;
        }
        if (c == 'S') {
            return PsiType.SHORT;
        }
        if (c == 'Z') {
            return PsiType.BOOLEAN;
        }
        if (c == 'I') {
            return PsiType.INT;
        }
        if (c != 'J') {
            switch (c) {
                case 'B':
                    return PsiType.BYTE;
                case 'C':
                    return PsiType.CHAR;
                case 'D':
                    return PsiType.DOUBLE;
                default:
                    return null;
            }
        }
        return PsiType.LONG;
    }

    public String getBoxedTypeName() {
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind = this.myKind;
        if (jvmPrimitiveTypeKind == null) {
            return null;
        }
        return jvmPrimitiveTypeKind.getBoxedFqn();
    }

    public PsiClassType getBoxedType(PsiElement psiElement) {
        String boxedTypeName;
        JavaPsiFacade javaPsiFacade;
        PsiClass psiClassFindClass;
        if (psiElement == null) {
            $$$reportNull$$$0(17);
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile == null) {
            return null;
        }
        LanguageLevel languageLevel = PsiUtil.getLanguageLevel(containingFile);
        if (!languageLevel.isAtLeast(LanguageLevel.JDK_1_5) || (boxedTypeName = getBoxedTypeName()) == null || (psiClassFindClass = (javaPsiFacade = JavaPsiFacade.getInstance(containingFile.getProject())).findClass(boxedTypeName, containingFile.getResolveScope())) == null) {
            return null;
        }
        return javaPsiFacade.getElementFactory().createType(psiClassFindClass, PsiSubstitutor.EMPTY, languageLevel).annotate(getAnnotationProvider());
    }

    public PsiClassType getBoxedType(PsiManager psiManager, GlobalSearchScope globalSearchScope) {
        PsiClass psiClassFindClass;
        if (psiManager == null) {
            $$$reportNull$$$0(18);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(19);
        }
        String boxedTypeName = getBoxedTypeName();
        if (boxedTypeName == null || (psiClassFindClass = JavaPsiFacade.getInstance(psiManager.getProject()).findClass(boxedTypeName, globalSearchScope)) == null) {
            return null;
        }
        return JavaPsiFacade.getElementFactory(psiManager.getProject()).createType(psiClassFindClass);
    }

    @Deprecated
    public static Collection<String> getAllBoxedTypeNames() {
        return JvmPrimitiveTypeKind.getBoxedFqns();
    }

    public int hashCode() {
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind = this.myKind;
        if (jvmPrimitiveTypeKind == null) {
            return 0;
        }
        return jvmPrimitiveTypeKind.hashCode();
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof PsiPrimitiveType) && this.myKind == ((PsiPrimitiveType) obj).myKind);
    }
}
