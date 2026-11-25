package org.jetbrains.kotlin.com.intellij.psi;

import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmAnnotation;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmPrimitiveTypeKind;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiType implements JvmType, PsiAnnotationOwner, Cloneable {
    private TypeAnnotationProvider myAnnotationProvider;
    public static final PsiPrimitiveType BYTE = new PsiPrimitiveType(JvmPrimitiveTypeKind.BYTE);
    public static final PsiPrimitiveType CHAR = new PsiPrimitiveType(JvmPrimitiveTypeKind.CHAR);
    public static final PsiPrimitiveType DOUBLE = new PsiPrimitiveType(JvmPrimitiveTypeKind.DOUBLE);
    public static final PsiPrimitiveType FLOAT = new PsiPrimitiveType(JvmPrimitiveTypeKind.FLOAT);
    public static final PsiPrimitiveType INT = new PsiPrimitiveType(JvmPrimitiveTypeKind.INT);
    public static final PsiPrimitiveType LONG = new PsiPrimitiveType(JvmPrimitiveTypeKind.LONG);
    public static final PsiPrimitiveType SHORT = new PsiPrimitiveType(JvmPrimitiveTypeKind.SHORT);
    public static final PsiPrimitiveType BOOLEAN = new PsiPrimitiveType(JvmPrimitiveTypeKind.BOOLEAN);
    public static final PsiPrimitiveType VOID = new PsiPrimitiveType(JvmPrimitiveTypeKind.VOID);
    public static final PsiPrimitiveType NULL = new PsiPrimitiveType(null);
    public static final PsiType[] EMPTY_ARRAY = new PsiType[0];
    public static final ArrayFactory<PsiType> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiType$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiType.lambda$static$0(i);
        }
    };

    /* JADX WARN: Code duplicated, block: B:12:0x001d A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:40:0x0071  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 2 && i != 3 && i != 6 && i != 31 && i != 32) {
            switch (i) {
                default:
                    switch (i) {
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                            break;
                        default:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 1 && i != 2 && i != 3 && i != 6 && i != 31 && i != 32) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    i2 = 3;
                    break;
                default:
                    switch (i) {
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                            i2 = 3;
                            break;
                        default:
                            i2 = 2;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 1 || i == 2) {
            objArr[0] = "annotations";
        } else if (i == 3) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else if (i == 6) {
            objArr[0] = "annotations";
        } else if (i != 31 && i != 32) {
            switch (i) {
                case 10:
                case 11:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 12:
                    objArr[0] = "qName";
                    break;
                case 13:
                    objArr[0] = "project";
                    break;
                default:
                    switch (i) {
                        case 16:
                        case 18:
                        case 20:
                        case 22:
                        case 24:
                        case 26:
                            objArr[0] = "manager";
                            break;
                        case 17:
                        case 19:
                        case 21:
                        case 23:
                        case 25:
                        case 27:
                            break;
                        default:
                            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiType";
                            break;
                    }
                case 14:
                    objArr[0] = "resolveScope";
                    break;
            }
        } else {
            objArr[0] = "qualifiedName";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 31:
            case 32:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiType";
                break;
            case 4:
            case 5:
                objArr[1] = "annotate";
                break;
            case 7:
                objArr[1] = "getPresentableText";
                break;
            case 8:
                objArr[1] = "getCanonicalText";
                break;
            case 9:
                objArr[1] = "getInternalCanonicalText";
                break;
            case 15:
                objArr[1] = "getTypeByName";
                break;
            case 28:
                objArr[1] = "getDeepComponentType";
                break;
            case 29:
                objArr[1] = "getAnnotationProvider";
                break;
            case 30:
                objArr[1] = "getAnnotations";
                break;
            case 33:
                objArr[1] = "getApplicableAnnotations";
                break;
            default:
                objArr[1] = "createArray";
                break;
        }
        if (i == 1 || i == 2) {
            objArr[2] = "<init>";
        } else if (i == 3) {
            objArr[2] = "annotate";
        } else if (i == 6) {
            objArr[2] = "createArrayType";
        } else if (i == 31) {
            objArr[2] = "findAnnotation";
        } else if (i != 32) {
            switch (i) {
                case 10:
                    objArr[2] = "isAssignableFrom";
                    break;
                case 11:
                    objArr[2] = "isConvertibleFrom";
                    break;
                case 12:
                case 13:
                case 14:
                    objArr[2] = "getTypeByName";
                    break;
                default:
                    switch (i) {
                        case 16:
                        case 17:
                            objArr[2] = "getJavaLangObject";
                            break;
                        case 18:
                        case 19:
                            objArr[2] = "getJavaLangClass";
                            break;
                        case 20:
                        case 21:
                            objArr[2] = "getJavaLangThrowable";
                            break;
                        case 22:
                        case 23:
                            objArr[2] = "getJavaLangString";
                            break;
                        case 24:
                        case 25:
                            objArr[2] = "getJavaLangError";
                            break;
                        case 26:
                        case 27:
                            objArr[2] = "getJavaLangRuntimeException";
                            break;
                    }
                    break;
            }
        } else {
            objArr[2] = "addAnnotation";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 6 && i != 31 && i != 32) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    switch (i) {
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                            break;
                        default:
                            throw new IllegalStateException(str2);
                    }
                    break;
            }
        }
        throw new IllegalArgumentException(str2);
    }

    public abstract <A> A accept(PsiTypeVisitor<A> psiTypeVisitor);

    public abstract boolean equalsToText(String str);

    public abstract String getCanonicalText();

    public abstract String getPresentableText();

    public abstract GlobalSearchScope getResolveScope();

    public abstract PsiType[] getSuperTypes();

    public abstract boolean isValid();

    static /* synthetic */ PsiType[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiType[i];
    }

    public static PsiType[] createArray(int i) {
        PsiType[] psiTypeArrCreate = ARRAY_FACTORY.create(i);
        if (psiTypeArrCreate == null) {
            $$$reportNull$$$0(0);
        }
        return psiTypeArrCreate;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected PsiType(PsiAnnotation[] psiAnnotationArr) {
        this(TypeAnnotationProvider.Static.create(psiAnnotationArr));
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(1);
        }
    }

    protected PsiType(TypeAnnotationProvider typeAnnotationProvider) {
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(2);
        }
        this.myAnnotationProvider = typeAnnotationProvider;
    }

    public PsiType annotate(TypeAnnotationProvider typeAnnotationProvider) {
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(3);
        }
        if (typeAnnotationProvider == this.myAnnotationProvider) {
            return this;
        }
        try {
            PsiType psiType = (PsiType) clone();
            psiType.myAnnotationProvider = typeAnnotationProvider;
            if (psiType == null) {
                $$$reportNull$$$0(5);
            }
            return psiType;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public PsiArrayType createArrayType() {
        return new PsiArrayType(this);
    }

    @Deprecated
    public PsiArrayType createArrayType(PsiAnnotation... psiAnnotationArr) {
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(6);
        }
        return new PsiArrayType(this, psiAnnotationArr);
    }

    public String getPresentableText(boolean z) {
        String presentableText = getPresentableText();
        if (presentableText == null) {
            $$$reportNull$$$0(7);
        }
        return presentableText;
    }

    public String getCanonicalText(boolean z) {
        String canonicalText = getCanonicalText();
        if (canonicalText == null) {
            $$$reportNull$$$0(8);
        }
        return canonicalText;
    }

    public String getInternalCanonicalText() {
        String canonicalText = getCanonicalText();
        if (canonicalText == null) {
            $$$reportNull$$$0(9);
        }
        return canonicalText;
    }

    public boolean isAssignableFrom(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(10);
        }
        return TypeConversionUtil.isAssignable(this, psiType);
    }

    public boolean isConvertibleFrom(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(11);
        }
        return TypeConversionUtil.areTypesConvertible(psiType, this);
    }

    public static PsiClassType getTypeByName(String str, Project project, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (project == null) {
            $$$reportNull$$$0(13);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(14);
        }
        PsiClassType psiClassTypeCreateTypeByFQClassName = JavaPsiFacade.getElementFactory(project).createTypeByFQClassName(str, globalSearchScope);
        if (psiClassTypeCreateTypeByFQClassName == null) {
            $$$reportNull$$$0(15);
        }
        return psiClassTypeCreateTypeByFQClassName;
    }

    public static PsiClassType getJavaLangObject(PsiManager psiManager, GlobalSearchScope globalSearchScope) {
        if (psiManager == null) {
            $$$reportNull$$$0(16);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(17);
        }
        return getTypeByName(CommonClassNames.JAVA_LANG_OBJECT, psiManager.getProject(), globalSearchScope);
    }

    public static PsiClassType getJavaLangClass(PsiManager psiManager, GlobalSearchScope globalSearchScope) {
        if (psiManager == null) {
            $$$reportNull$$$0(18);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(19);
        }
        return getTypeByName(CommonClassNames.JAVA_LANG_CLASS, psiManager.getProject(), globalSearchScope);
    }

    public static PsiClassType getJavaLangThrowable(PsiManager psiManager, GlobalSearchScope globalSearchScope) {
        if (psiManager == null) {
            $$$reportNull$$$0(20);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(21);
        }
        return getTypeByName(CommonClassNames.JAVA_LANG_THROWABLE, psiManager.getProject(), globalSearchScope);
    }

    public static PsiClassType getJavaLangString(PsiManager psiManager, GlobalSearchScope globalSearchScope) {
        if (psiManager == null) {
            $$$reportNull$$$0(22);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(23);
        }
        return getTypeByName(CommonClassNames.JAVA_LANG_STRING, psiManager.getProject(), globalSearchScope);
    }

    public static PsiClassType getJavaLangError(PsiManager psiManager, GlobalSearchScope globalSearchScope) {
        if (psiManager == null) {
            $$$reportNull$$$0(24);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(25);
        }
        return getTypeByName(CommonClassNames.JAVA_LANG_ERROR, psiManager.getProject(), globalSearchScope);
    }

    public static PsiClassType getJavaLangRuntimeException(PsiManager psiManager, GlobalSearchScope globalSearchScope) {
        if (psiManager == null) {
            $$$reportNull$$$0(26);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(27);
        }
        return getTypeByName(CommonClassNames.JAVA_LANG_RUNTIME_EXCEPTION, psiManager.getProject(), globalSearchScope);
    }

    public final int getArrayDimensions() {
        int i = 0;
        for (PsiType psiTypeM6526getComponentType = this; psiTypeM6526getComponentType instanceof PsiArrayType; psiTypeM6526getComponentType = ((PsiArrayType) psiTypeM6526getComponentType).getComponentType()) {
            i++;
        }
        return i;
    }

    public final PsiType getDeepComponentType() {
        PsiType psiTypeM6526getComponentType = this;
        while (psiTypeM6526getComponentType instanceof PsiArrayType) {
            psiTypeM6526getComponentType = ((PsiArrayType) psiTypeM6526getComponentType).getComponentType();
        }
        if (psiTypeM6526getComponentType == null) {
            $$$reportNull$$$0(28);
        }
        return psiTypeM6526getComponentType;
    }

    public final TypeAnnotationProvider getAnnotationProvider() {
        TypeAnnotationProvider typeAnnotationProvider = this.myAnnotationProvider;
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(29);
        }
        return typeAnnotationProvider;
    }

    @Override // 
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] annotations = this.myAnnotationProvider.getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(30);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(31);
        }
        for (PsiAnnotation psiAnnotation : getAnnotations()) {
            if (str.equals(psiAnnotation.getQualifiedName())) {
                return psiAnnotation;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] annotations = getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(33);
        }
        return annotations;
    }

    public String toString() {
        return "PsiType:" + getPresentableText();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static abstract class Stub extends PsiType {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
            if (i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiType$Stub";
            } else {
                objArr[0] = "annotations";
            }
            if (i == 2) {
                objArr[1] = "getPresentableText";
            } else if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiType$Stub";
            } else {
                objArr[1] = "getCanonicalText";
            }
            if (i != 2 && i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
        public abstract String getCanonicalText(boolean z);

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
        public abstract String getPresentableText(boolean z);

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
        public /* bridge */ /* synthetic */ JvmAnnotation[] getAnnotations() {
            return super.getAnnotations();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected Stub(PsiAnnotation[] psiAnnotationArr) {
            super(psiAnnotationArr);
            if (psiAnnotationArr == null) {
                $$$reportNull$$$0(0);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected Stub(TypeAnnotationProvider typeAnnotationProvider) {
            super(typeAnnotationProvider);
            if (typeAnnotationProvider == null) {
                $$$reportNull$$$0(1);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
        public final String getPresentableText() {
            String presentableText = getPresentableText(false);
            if (presentableText == null) {
                $$$reportNull$$$0(2);
            }
            return presentableText;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
        public final String getCanonicalText() {
            String canonicalText = getCanonicalText(false);
            if (canonicalText == null) {
                $$$reportNull$$$0(3);
            }
            return canonicalText;
        }
    }
}
