package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeDeclaration;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmReferenceType;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmSubstitutor;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmType;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmTypeResolveResult;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiClassType extends PsiType implements JvmReferenceType {
    protected final LanguageLevel myLanguageLevel;
    public static final PsiClassType[] EMPTY_ARRAY = new PsiClassType[0];
    public static final ArrayFactory<PsiClassType> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiClassType$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiClassType.lambda$static$0(i);
        }
    };

    public interface ClassResolveResult extends JavaResolveResult {
        public static final ClassResolveResult EMPTY = new ClassResolveResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiClassType.ClassResolveResult.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/PsiClassType$ClassResolveResult$1", "getSubstitutor"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public PsiElement getCurrentFileResolveScope() {
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.ClassResolveResult, org.jetbrains.kotlin.com.intellij.psi.ResolveResult
            public PsiClass getElement() {
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public boolean isAccessible() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public boolean isPackagePrefixPackageReference() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public boolean isStaticsScopeCorrect() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
            public boolean isValidResult() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public PsiSubstitutor getSubstitutor() {
                PsiSubstitutor psiSubstitutor = PsiSubstitutor.EMPTY;
                if (psiSubstitutor == null) {
                    $$$reportNull$$$0(0);
                }
                return psiSubstitutor;
            }
        };

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
        PsiClass getElement();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6 || i == 8 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6 || i == 8 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "annotations";
                break;
            case 2:
            case 3:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiClassType";
                break;
            case 7:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "languageLevel";
                break;
        }
        if (i == 4) {
            objArr[1] = "annotate";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getSuperTypes";
        } else if (i == 8) {
            objArr[1] = "getName";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiClassType";
        } else {
            objArr[1] = "typeArguments";
        }
        switch (i) {
            case 3:
                objArr[2] = "annotate";
                break;
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
                break;
            case 7:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6 && i != 8 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract String getClassName();

    public abstract LanguageLevel getLanguageLevel();

    public abstract PsiType[] getParameters();

    public PsiElement getPsiContext() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public abstract GlobalSearchScope getResolveScope();

    public abstract PsiClassType rawType();

    @Override // 
    public abstract PsiClass resolve();

    public abstract ClassResolveResult resolveGenerics();

    public abstract PsiClassType setLanguageLevel(LanguageLevel languageLevel);

    static /* synthetic */ PsiClassType[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiClassType[i];
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected PsiClassType(LanguageLevel languageLevel) {
        this(languageLevel, PsiAnnotation.EMPTY_ARRAY);
        if (languageLevel == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected PsiClassType(LanguageLevel languageLevel, PsiAnnotation[] psiAnnotationArr) {
        super(psiAnnotationArr);
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(1);
        }
        this.myLanguageLevel = languageLevel;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiClassType(LanguageLevel languageLevel, TypeAnnotationProvider typeAnnotationProvider) {
        super(typeAnnotationProvider);
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(2);
        }
        this.myLanguageLevel = languageLevel;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiClassType annotate(TypeAnnotationProvider typeAnnotationProvider) {
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(3);
        }
        PsiClassType psiClassType = (PsiClassType) super.annotate(typeAnnotationProvider);
        if (psiClassType == null) {
            $$$reportNull$$$0(4);
        }
        return psiClassType;
    }

    public int getParameterCount() {
        return getParameters().length;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PsiClassType)) {
            return (obj instanceof PsiCapturedWildcardType) && ((PsiCapturedWildcardType) obj).getLowerBound().equalsToText(CommonClassNames.JAVA_LANG_OBJECT) && equalsToText(CommonClassNames.JAVA_LANG_OBJECT);
        }
        PsiClassType psiClassType = (PsiClassType) obj;
        if (!Objects.equals(getClassName(), psiClassType.getClassName()) || getParameterCount() != psiClassType.getParameterCount()) {
            return false;
        }
        ClassResolveResult classResolveResultResolveGenerics = resolveGenerics();
        ClassResolveResult classResolveResultResolveGenerics2 = psiClassType.resolveGenerics();
        if (classResolveResultResolveGenerics == classResolveResultResolveGenerics2) {
            return true;
        }
        PsiClass element = classResolveResultResolveGenerics.getElement();
        PsiClass element2 = classResolveResultResolveGenerics2.getElement();
        if (element == null || element2 == null) {
            return element == element2;
        }
        return element.getManager().areElementsEquivalent(element, element2) && PsiUtil.equalOnEquivalentClasses(this, element, psiClassType, element2);
    }

    public boolean hasParameters() {
        ClassResolveResult classResolveResultResolveGenerics = resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (element == null) {
            return false;
        }
        Iterator<PsiTypeParameter> it = PsiUtil.typeParametersIterable(element).iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (classResolveResultResolveGenerics.getSubstitutor().substitute(it.next()) == null) {
                return false;
            }
            z = true;
        }
        return z;
    }

    public boolean hasNonTrivialParameters() {
        PsiClass superClass;
        ClassResolveResult classResolveResultResolveGenerics = resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (element == null) {
            return false;
        }
        for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(element)) {
            PsiType psiTypeSubstitute = classResolveResultResolveGenerics.getSubstitutor().substitute(psiTypeParameter);
            if (psiTypeSubstitute != null) {
                if (!(psiTypeSubstitute instanceof PsiWildcardType)) {
                    return true;
                }
                PsiWildcardType psiWildcardType = (PsiWildcardType) psiTypeSubstitute;
                PsiType bound = psiWildcardType.getBound();
                if (bound != null && (!psiWildcardType.isExtends() || (superClass = psiTypeParameter.getSuperClass()) == null || PsiUtil.resolveClassInType(bound) != superClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        String className = getClassName();
        if (className == null) {
            return 0;
        }
        return className.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        ClassResolveResult classResolveResultResolveGenerics = resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (element == null) {
            PsiClassType[] psiClassTypeArr = EMPTY_ARRAY;
            if (psiClassTypeArr == null) {
                $$$reportNull$$$0(5);
            }
            return psiClassTypeArr;
        }
        PsiClassType[] superTypes = element.getSuperTypes();
        PsiType[] psiTypeArrCreateArray = createArray(superTypes.length);
        for (int i = 0; i < superTypes.length; i++) {
            psiTypeArrCreateArray[i] = classResolveResultResolveGenerics.getSubstitutor().substitute(superTypes[i]);
        }
        if (psiTypeArrCreateArray == null) {
            $$$reportNull$$$0(6);
        }
        return psiTypeArrCreateArray;
    }

    public static boolean isRaw(ClassResolveResult classResolveResult) {
        PsiClass element = classResolveResult.getElement();
        return element != null && PsiUtil.isRawSubstitutor(element, classResolveResult.getSubstitutor());
    }

    public boolean isRaw() {
        return isRaw(resolveGenerics());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(7);
        }
        return psiTypeVisitor.visitClassType(this);
    }

    public String getName() {
        String className = getClassName();
        if (className == null) {
            $$$reportNull$$$0(8);
        }
        return className;
    }

    public JvmTypeResolveResult resolveType() {
        ClassResolveResult classResolveResultResolveGenerics = resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (element == null) {
            return null;
        }
        return new JvmTypeResolveResult(element, classResolveResultResolveGenerics) { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiClassType.1
            private final JvmSubstitutor mySubstitutor;
            final /* synthetic */ PsiClass val$clazz;
            final /* synthetic */ ClassResolveResult val$resolveResult;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiClassType$1";
                if (i != 1) {
                    objArr[1] = "getDeclaration";
                } else {
                    objArr[1] = "getSubstitutor";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            {
                this.val$clazz = element;
                this.val$resolveResult = classResolveResultResolveGenerics;
                this.mySubstitutor = new PsiJvmSubstitutor(element.getProject(), classResolveResultResolveGenerics.getSubstitutor());
            }

            public JvmTypeDeclaration getDeclaration() {
                PsiClass psiClass = this.val$clazz;
                if (psiClass == null) {
                    $$$reportNull$$$0(0);
                }
                return psiClass;
            }

            public JvmSubstitutor getSubstitutor() {
                JvmSubstitutor jvmSubstitutor = this.mySubstitutor;
                if (jvmSubstitutor == null) {
                    $$$reportNull$$$0(1);
                }
                return jvmSubstitutor;
            }
        };
    }

    public Iterable<JvmType> typeArguments() {
        List listAsList = Arrays.asList(getParameters());
        if (listAsList == null) {
            $$$reportNull$$$0(9);
        }
        return listAsList;
    }

    public static abstract class Stub extends PsiClassType {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
            if (i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiClassType$Stub";
            } else {
                objArr[0] = "annotations";
            }
            if (i == 2) {
                objArr[1] = "getPresentableText";
            } else if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiClassType$Stub";
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

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType, org.jetbrains.kotlin.com.intellij.psi.PsiType
        public /* bridge */ /* synthetic */ PsiType annotate(TypeAnnotationProvider typeAnnotationProvider) {
            return super.annotate(typeAnnotationProvider);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public /* bridge */ /* synthetic */ JvmTypeDeclaration resolve() {
            return super.resolve();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected Stub(LanguageLevel languageLevel, PsiAnnotation[] psiAnnotationArr) {
            super(languageLevel, psiAnnotationArr);
            if (psiAnnotationArr == null) {
                $$$reportNull$$$0(0);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected Stub(LanguageLevel languageLevel, TypeAnnotationProvider typeAnnotationProvider) {
            super(languageLevel, typeAnnotationProvider);
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
