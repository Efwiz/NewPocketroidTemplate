package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiImmediateClassType extends PsiClassType.Stub {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private String myCanonicalText;
    private String myCanonicalTextAnnotated;
    private final PsiClass myClass;
    private final PsiClassType.ClassResolveResult myClassResolveResult;
    private String myInternalCanonicalText;
    private final PsiManager myManager;
    private String myPresentableText;
    private String myPresentableTextAnnotated;
    private final PsiSubstitutor mySubstitutor;

    private enum TextType {
        PRESENTABLE,
        CANONICAL,
        INT_CANONICAL
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 26 && i != 27) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 26 && i != 27) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 8:
            case 22:
                objArr[0] = "substitutor";
                break;
            case 2:
            case 4:
            case 7:
            case 21:
            default:
                objArr[0] = "aClass";
                break;
            case 6:
                objArr[0] = "annotations";
                break;
            case 9:
                objArr[0] = org.catrobat.catroid.common.Constants.EXTRA_PROVIDER;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 26:
            case 27:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImmediateClassType";
                break;
            case 20:
            case 24:
                objArr[0] = "textType";
                break;
            case 23:
                objArr[0] = "buffer";
                break;
            case 25:
                objArr[0] = "text";
                break;
            case 28:
                objArr[0] = FirebaseAnalytics.Param.LEVEL;
                break;
        }
        if (i == 26) {
            objArr[1] = "getResolveScope";
        } else if (i != 27) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                    objArr[1] = "getParameters";
                    break;
                case 13:
                    objArr[1] = "resolveGenerics";
                    break;
                case 14:
                    objArr[1] = "rawType";
                    break;
                case 15:
                case 16:
                case 17:
                    objArr[1] = "getPresentableText";
                    break;
                case 18:
                    objArr[1] = "getCanonicalText";
                    break;
                case 19:
                    objArr[1] = "getInternalCanonicalText";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImmediateClassType";
                    break;
            }
        } else {
            objArr[1] = "getLanguageLevel";
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 26:
            case 27:
                break;
            case 20:
                objArr[2] = "getText";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "buildText";
                break;
            case 25:
                objArr[2] = "equalsToText";
                break;
            case 28:
                objArr[2] = "setLanguageLevel";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 26 && i != 27) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiImmediateClassType(PsiClass psiClass, PsiSubstitutor psiSubstitutor) {
        this(psiClass, psiSubstitutor, (LanguageLevel) null, TypeAnnotationProvider.EMPTY);
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiImmediateClassType(PsiClass psiClass, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel) {
        this(psiClass, psiSubstitutor, languageLevel, TypeAnnotationProvider.EMPTY);
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiImmediateClassType(PsiClass psiClass, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel, PsiAnnotation... psiAnnotationArr) {
        super(languageLevel, psiAnnotationArr);
        if (psiClass == null) {
            $$$reportNull$$$0(4);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(6);
        }
        this.myClassResolveResult = new PsiClassType.ClassResolveResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImmediateClassType.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImmediateClassType$1", "getSubstitutor"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public PsiElement getCurrentFileResolveScope() {
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public boolean isAccessible() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public boolean isPackagePrefixPackageReference() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public boolean isStaticsScopeCorrect() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
            public boolean isValidResult() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.ClassResolveResult, org.jetbrains.kotlin.com.intellij.psi.ResolveResult
            public PsiClass getElement() {
                return PsiImmediateClassType.this.myClass;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public PsiSubstitutor getSubstitutor() {
                PsiSubstitutor psiSubstitutor2 = PsiImmediateClassType.this.mySubstitutor;
                if (psiSubstitutor2 == null) {
                    $$$reportNull$$$0(0);
                }
                return psiSubstitutor2;
            }
        };
        this.myClass = psiClass;
        this.myManager = psiClass.getManager();
        this.mySubstitutor = psiSubstitutor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiImmediateClassType(PsiClass psiClass, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel, TypeAnnotationProvider typeAnnotationProvider) {
        super(languageLevel, typeAnnotationProvider);
        if (psiClass == null) {
            $$$reportNull$$$0(7);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(8);
        }
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(9);
        }
        this.myClassResolveResult = new PsiClassType.ClassResolveResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImmediateClassType.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImmediateClassType$1", "getSubstitutor"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public PsiElement getCurrentFileResolveScope() {
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public boolean isAccessible() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public boolean isPackagePrefixPackageReference() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public boolean isStaticsScopeCorrect() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
            public boolean isValidResult() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.ClassResolveResult, org.jetbrains.kotlin.com.intellij.psi.ResolveResult
            public PsiClass getElement() {
                return PsiImmediateClassType.this.myClass;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
            public PsiSubstitutor getSubstitutor() {
                PsiSubstitutor psiSubstitutor2 = PsiImmediateClassType.this.mySubstitutor;
                if (psiSubstitutor2 == null) {
                    $$$reportNull$$$0(0);
                }
                return psiSubstitutor2;
            }
        };
        this.myClass = psiClass;
        this.myManager = psiClass.getManager();
        this.mySubstitutor = psiSubstitutor;
        psiSubstitutor.ensureValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiClass resolve() {
        return this.myClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public String getClassName() {
        return this.myClass.getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public int getParameterCount() {
        PsiTypeParameterList typeParameterList = this.myClass.mo6093getTypeParameterList();
        if (typeParameterList == null) {
            return 0;
        }
        PsiTypeParameter[] typeParameters = typeParameterList.getTypeParameters();
        for (PsiTypeParameter psiTypeParameter : typeParameters) {
            if (this.mySubstitutor.substitute(psiTypeParameter) == null) {
                return 0;
            }
        }
        return typeParameters.length;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiType[] getParameters() {
        PsiTypeParameter[] typeParameters = this.myClass.getTypeParameters();
        if (typeParameters.length == 0) {
            PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
            if (psiTypeArr == null) {
                $$$reportNull$$$0(10);
            }
            return psiTypeArr;
        }
        PsiType[] psiTypeArr2 = new PsiType[typeParameters.length];
        int length = typeParameters.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            PsiType psiTypeSubstitute = this.mySubstitutor.substitute(typeParameters[i]);
            if (psiTypeSubstitute == null) {
                PsiType[] psiTypeArr3 = PsiType.EMPTY_ARRAY;
                if (psiTypeArr3 == null) {
                    $$$reportNull$$$0(11);
                }
                return psiTypeArr3;
            }
            psiTypeArr2[i2] = psiTypeSubstitute;
            i++;
            i2++;
        }
        return psiTypeArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiClassType.ClassResolveResult resolveGenerics() {
        PsiClassType.ClassResolveResult classResolveResult = this.myClassResolveResult;
        if (classResolveResult == null) {
            $$$reportNull$$$0(13);
        }
        return classResolveResult;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiClassType rawType() {
        PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(this.myClass.getProject()).createType(this.myClass);
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(14);
        }
        return psiClassTypeCreateType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText(boolean z) {
        String str;
        if (z) {
            str = this.myPresentableTextAnnotated;
            if (str == null) {
                String text = getText(TextType.PRESENTABLE, true);
                this.myPresentableTextAnnotated = text;
                if (text == null) {
                    $$$reportNull$$$0(15);
                }
                return text;
            }
        } else {
            str = this.myPresentableText;
            if (str == null) {
                String text2 = getText(TextType.PRESENTABLE, false);
                this.myPresentableText = text2;
                if (text2 == null) {
                    $$$reportNull$$$0(16);
                }
                return text2;
            }
        }
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText(boolean z) {
        String text = z ? this.myCanonicalTextAnnotated : this.myCanonicalText;
        if (text == null) {
            text = getText(TextType.CANONICAL, z);
            if (z) {
                this.myCanonicalTextAnnotated = text;
            } else {
                this.myCanonicalText = text;
            }
        }
        if (text == null) {
            $$$reportNull$$$0(18);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        String text = this.myInternalCanonicalText;
        if (text == null) {
            text = getText(TextType.INT_CANONICAL, true);
            this.myInternalCanonicalText = text;
        }
        if (text == null) {
            $$$reportNull$$$0(19);
        }
        return text;
    }

    private String getText(TextType textType, boolean z) {
        if (textType == null) {
            $$$reportNull$$$0(20);
        }
        this.mySubstitutor.ensureValid();
        StringBuilder sb = new StringBuilder();
        buildText(this.myClass, this.mySubstitutor, sb, textType, z);
        return sb.toString();
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0070  */
    private void buildText(PsiClass psiClass, PsiSubstitutor psiSubstitutor, StringBuilder sb, TextType textType, boolean z) {
        PsiClass psiClass2;
        String qualifiedName;
        if (psiClass == null) {
            $$$reportNull$$$0(21);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(22);
        }
        if (sb == null) {
            $$$reportNull$$$0(23);
        }
        if (textType == null) {
            $$$reportNull$$$0(24);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            PsiAnonymousClass psiAnonymousClass = (PsiAnonymousClass) psiClass;
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiAnonymousClass.getBaseClassType().resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (textType == TextType.INT_CANONICAL) {
                sb.append("anonymous ");
            }
            if (element != null) {
                buildText(element, classResolveResultResolveGenerics.getSubstitutor(), sb, textType, false);
                return;
            } else {
                sb.append(psiAnonymousClass.getBaseClassReference().getCanonicalText());
                return;
            }
        }
        boolean z2 = textType != TextType.PRESENTABLE;
        if (psiClass.hasModifierProperty("static")) {
            psiClass2 = null;
        } else {
            PsiElement parent = psiClass.getLightParent();
            if (!(parent instanceof PsiClass) || (parent instanceof PsiAnonymousClass)) {
                psiClass2 = null;
            } else {
                psiClass2 = (PsiClass) parent;
            }
        }
        PsiClass psiClass3 = psiClass2;
        if (psiClass3 != null) {
            buildText(psiClass3, psiSubstitutor, sb, textType, false);
            sb.append('.');
        } else if (z2 && (qualifiedName = psiClass.getQualifiedName()) != null) {
            String packageName = StringUtil.getPackageName(qualifiedName);
            if (!StringUtil.isEmpty(packageName)) {
                sb.append(packageName);
                sb.append('.');
            }
        }
        if (z) {
            PsiNameHelper.appendAnnotations(sb, getAnnotations(), z2);
        }
        sb.append(psiClass.getName());
        PsiTypeParameter[] typeParameters = psiClass.getTypeParameters();
        if (typeParameters.length > 0) {
            int length = sb.length();
            sb.append('<');
            for (int i = 0; i < typeParameters.length; i++) {
                PsiTypeParameter psiTypeParameter = typeParameters[i];
                PsiUtilCore.ensureValid(psiTypeParameter);
                if (i > 0) {
                    sb.append(',');
                    if (textType == TextType.PRESENTABLE) {
                        sb.append(io.netty.util.internal.StringUtil.SPACE);
                    }
                }
                PsiType psiTypeSubstitute = psiSubstitutor.substitute(psiTypeParameter);
                if (psiTypeSubstitute == null) {
                    sb.setLength(length);
                    length = -1;
                    break;
                }
                PsiUtil.ensureValidType(psiTypeSubstitute);
                if (textType == TextType.PRESENTABLE) {
                    sb.append(psiTypeSubstitute.getPresentableText());
                } else if (textType == TextType.CANONICAL) {
                    sb.append(psiTypeSubstitute.getCanonicalText(z));
                } else {
                    sb.append(psiTypeSubstitute.getInternalCanonicalText());
                }
            }
            if (length >= 0) {
                sb.append('>');
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        for (PsiAnnotation psiAnnotation : getAnnotations()) {
            if (!psiAnnotation.isValid()) {
                return false;
            }
        }
        return this.myClass.isValid() && this.mySubstitutor.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        String name2 = this.myClass.getName();
        if (name2 != null && str.contains(name2)) {
            if (str.equals(getCanonicalText(false))) {
                return true;
            }
            try {
                return equals(JavaPsiFacade.getElementFactory(this.myManager.getProject()).createTypeFromText(str, this.myClass));
            } catch (IncorrectOperationException unused) {
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope resolveScope = this.myClass.getResolveScope();
        if (resolveScope == null) {
            $$$reportNull$$$0(26);
        }
        return resolveScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public LanguageLevel getLanguageLevel() {
        LanguageLevel languageLevel = this.myLanguageLevel != null ? this.myLanguageLevel : PsiUtil.getLanguageLevel(this.myClass);
        if (languageLevel == null) {
            $$$reportNull$$$0(27);
        }
        return languageLevel;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiClassType setLanguageLevel(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(28);
        }
        return languageLevel.equals(this.myLanguageLevel) ? this : new PsiImmediateClassType(this.myClass, this.mySubstitutor, languageLevel, getAnnotationProvider());
    }
}
