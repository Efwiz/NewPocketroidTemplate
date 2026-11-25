package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.IdentityHashMap;
import java.util.Map;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClass;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiSuperMethodUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
class TypeCorrector extends PsiTypeMapper {
    private final GlobalSearchScope myResolveScope;
    private final Map<PsiClassType, PsiClassType> myResultMap = new IdentityHashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
                objArr[0] = "classType";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/TypeCorrector";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[1] = "mapSubstitutor";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/TypeCorrector";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "correctType";
                break;
            case 2:
                objArr[2] = "visitClassType";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                break;
            default:
                objArr[2] = "visitType";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    TypeCorrector(GlobalSearchScope globalSearchScope) {
        this.myResolveScope = globalSearchScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper, org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitType(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(0);
        }
        return LambdaUtil.notInferredType(psiType) ? psiType : super.visitType(psiType);
    }

    public <T extends PsiType> T correctType(T t) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics;
        PsiClass element;
        PsiClass psiClassCorrectClassByScope;
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        if (t instanceof PsiClassType) {
            PsiClassType psiClassType = (PsiClassType) t;
            if (psiClassType.getParameterCount() == 0 && (element = (classResolveResultResolveGenerics = psiClassType.resolveGenerics()).getElement()) != null && classResolveResultResolveGenerics.getSubstitutor() == PsiSubstitutor.EMPTY && ((psiClassCorrectClassByScope = PsiSuperMethodUtil.correctClassByScope(element, this.myResolveScope)) == null || psiClassCorrectClassByScope == element)) {
                return psiClassType;
            }
        }
        return (T) t.accept(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper, org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitClassType(PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(2);
        }
        if (psiClassType instanceof PsiCorrectedClassType) {
            return this.myResolveScope.equals(psiClassType.getResolveScope()) ? psiClassType : visitClassType(((PsiCorrectedClassType) psiClassType).myDelegate);
        }
        PsiClassType psiClassType2 = this.myResultMap.get(psiClassType);
        if (psiClassType2 != null) {
            return psiClassType2;
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
        if (element == null || (element instanceof LightClass)) {
            return psiClassType;
        }
        PsiUtilCore.ensureValid(element);
        PsiClass psiClassCorrectClassByScope = PsiSuperMethodUtil.correctClassByScope(element, this.myResolveScope);
        if (psiClassCorrectClassByScope == null) {
            return psiClassType;
        }
        PsiCorrectedClassType psiCorrectedClassType = new PsiCorrectedClassType(psiClassType.getLanguageLevel(), psiClassType, new CorrectedResolveResult(element, psiClassCorrectClassByScope, substitutor, classResolveResultResolveGenerics));
        this.myResultMap.put(psiClassType, psiCorrectedClassType);
        return psiCorrectedClassType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PsiSubstitutor mapSubstitutor(PsiClass psiClass, PsiClass psiClass2, PsiSubstitutor psiSubstitutor) {
        PsiTypeParameter[] typeParameters = psiClass2.getTypeParameters();
        PsiTypeParameter[] typeParameters2 = psiClass.getTypeParameters();
        if (typeParameters.length != typeParameters2.length) {
            if (typeParameters2.length != 0) {
                if (psiSubstitutor == null) {
                    $$$reportNull$$$0(4);
                }
                return psiSubstitutor;
            }
            PsiSubstitutor psiSubstitutorCreateRawSubstitutor = JavaPsiFacade.getElementFactory(psiClass2.getProject()).createRawSubstitutor(psiClass2);
            if (psiSubstitutorCreateRawSubstitutor == null) {
                $$$reportNull$$$0(3);
            }
            return psiSubstitutorCreateRawSubstitutor;
        }
        Map<PsiTypeParameter, PsiType> substitutionMap = psiSubstitutor.getSubstitutionMap();
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        for (int i = 0; i < typeParameters2.length; i++) {
            if (substitutionMap.containsKey(typeParameters2[i])) {
                PsiType psiTypeSubstitute = psiSubstitutor.substitute(typeParameters2[i]);
                if (psiTypeSubstitute != null) {
                    PsiType psiTypeMapType = mapType(psiTypeSubstitute);
                    if (psiTypeMapType == null) {
                        if (psiSubstitutor == null) {
                            $$$reportNull$$$0(5);
                        }
                        return psiSubstitutor;
                    }
                    psiSubstitutorPut = psiSubstitutorPut.put(typeParameters[i], psiTypeMapType);
                } else {
                    psiSubstitutorPut = psiSubstitutorPut.put(typeParameters[i], null);
                }
            }
        }
        if (psiClass2.hasModifierProperty("static")) {
            if (psiSubstitutorPut == null) {
                $$$reportNull$$$0(6);
            }
            return psiSubstitutorPut;
        }
        PsiClass psiClassMo6092getContainingClass = psiClass2.mo6092getContainingClass();
        PsiClass psiClassMo6092getContainingClass2 = psiClass.mo6092getContainingClass();
        if ((psiClassMo6092getContainingClass != null) != (psiClassMo6092getContainingClass2 != null)) {
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(7);
            }
            return psiSubstitutor;
        }
        if (psiClassMo6092getContainingClass == null) {
            if (psiSubstitutorPut == null) {
                $$$reportNull$$$0(9);
            }
            return psiSubstitutorPut;
        }
        PsiSubstitutor psiSubstitutorPutAll = psiSubstitutorPut.putAll(mapSubstitutor(psiClassMo6092getContainingClass2, psiClassMo6092getContainingClass, psiSubstitutor));
        if (psiSubstitutorPutAll == null) {
            $$$reportNull$$$0(8);
        }
        return psiSubstitutorPutAll;
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class PsiCorrectedClassType extends PsiClassType.Stub {
        private final PsiClassType myDelegate;
        private final CorrectedResolveResult myResolveResult;
        final /* synthetic */ TypeCorrector this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 9:
                case 10:
                case 11:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                case 7:
                case 8:
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 9:
                case 10:
                case 11:
                    i2 = 2;
                    break;
                case 7:
                case 8:
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 9:
                case 10:
                case 11:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/TypeCorrector$PsiCorrectedClassType";
                    break;
                case 7:
                default:
                    objArr[0] = "languageLevel";
                    break;
                case 8:
                    objArr[0] = Constants.EXTRA_PROVIDER;
                    break;
                case 12:
                    objArr[0] = "text";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[1] = "resolve";
                    break;
                case 2:
                    objArr[1] = "getParameters";
                    break;
                case 3:
                    objArr[1] = "resolveGenerics";
                    break;
                case 4:
                    objArr[1] = "rawType";
                    break;
                case 5:
                    objArr[1] = "getResolveScope";
                    break;
                case 6:
                    objArr[1] = "getLanguageLevel";
                    break;
                case 7:
                case 8:
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/TypeCorrector$PsiCorrectedClassType";
                    break;
                case 9:
                    objArr[1] = "getPresentableText";
                    break;
                case 10:
                    objArr[1] = "getCanonicalText";
                    break;
                case 11:
                    objArr[1] = "getInternalCanonicalText";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 9:
                case 10:
                case 11:
                    break;
                case 7:
                    objArr[2] = "setLanguageLevel";
                    break;
                case 8:
                    objArr[2] = "annotate";
                    break;
                case 12:
                    objArr[2] = "equalsToText";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 9:
                case 10:
                case 11:
                    throw new IllegalStateException(str2);
                case 7:
                case 8:
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private PsiCorrectedClassType(TypeCorrector typeCorrector, LanguageLevel languageLevel, PsiClassType psiClassType, CorrectedResolveResult correctedResolveResult) {
            super(languageLevel, psiClassType.getAnnotationProvider());
            if (languageLevel == null) {
                $$$reportNull$$$0(0);
            }
            this.this$0 = typeCorrector;
            if (psiClassType instanceof PsiCorrectedClassType) {
                throw new IllegalArgumentException();
            }
            this.myDelegate = psiClassType;
            this.myResolveResult = correctedResolveResult;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public PsiClass resolve() {
            PsiClass psiClass = this.myResolveResult.myMappedClass;
            if (psiClass == null) {
                $$$reportNull$$$0(1);
            }
            return psiClass;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public String getClassName() {
            return this.myDelegate.getClassName();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public PsiElement getPsiContext() {
            return this.myDelegate.getPsiContext();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public PsiType[] getParameters() {
            PsiType[] psiTypeArr = (PsiType[]) ContainerUtil.map2Array(this.myDelegate.getParameters(), PsiType.class, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.TypeCorrector$PsiCorrectedClassType$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return this.f$0.m6577xbb1e2d4b((PsiType) obj);
                }
            });
            if (psiTypeArr == null) {
                $$$reportNull$$$0(2);
            }
            return psiTypeArr;
        }

        /* JADX INFO: renamed from: lambda$getParameters$0$org-jetbrains-kotlin-com-intellij-psi-impl-TypeCorrector$PsiCorrectedClassType, reason: not valid java name */
        /* synthetic */ PsiType m6577xbb1e2d4b(PsiType psiType) {
            if (psiType != null) {
                return this.this$0.mapType(psiType);
            }
            TypeCorrector.LOG.error(this.myDelegate + " of " + this.myDelegate.getClass() + "; substitutor=" + this.myDelegate.resolveGenerics().getSubstitutor());
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public int getParameterCount() {
            return this.myDelegate.getParameters().length;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public PsiClassType.ClassResolveResult resolveGenerics() {
            CorrectedResolveResult correctedResolveResult = this.myResolveResult;
            if (correctedResolveResult == null) {
                $$$reportNull$$$0(3);
            }
            return correctedResolveResult;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public PsiClassType rawType() {
            PsiClass psiClassResolve = resolve();
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClassResolve.getProject());
            PsiClassType psiClassTypeCreateType = elementFactory.createType(psiClassResolve, elementFactory.createRawSubstitutor(psiClassResolve));
            if (psiClassTypeCreateType == null) {
                $$$reportNull$$$0(4);
            }
            return psiClassTypeCreateType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType, org.jetbrains.kotlin.com.intellij.psi.PsiType
        public GlobalSearchScope getResolveScope() {
            GlobalSearchScope globalSearchScope = this.this$0.myResolveScope;
            if (globalSearchScope == null) {
                $$$reportNull$$$0(5);
            }
            return globalSearchScope;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public LanguageLevel getLanguageLevel() {
            LanguageLevel languageLevel = this.myLanguageLevel;
            if (languageLevel == null) {
                $$$reportNull$$$0(6);
            }
            return languageLevel;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
        public PsiClassType setLanguageLevel(LanguageLevel languageLevel) {
            if (languageLevel == null) {
                $$$reportNull$$$0(7);
            }
            return new PsiCorrectedClassType(this.this$0, languageLevel, this.myDelegate, this.myResolveResult);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiClassType, org.jetbrains.kotlin.com.intellij.psi.PsiType
        public PsiClassType annotate(TypeAnnotationProvider typeAnnotationProvider) {
            if (typeAnnotationProvider == null) {
                $$$reportNull$$$0(8);
            }
            PsiClassType psiClassTypeAnnotate = this.myDelegate.annotate(typeAnnotationProvider);
            return psiClassTypeAnnotate == this.myDelegate ? this : new PsiCorrectedClassType(this.this$0, this.myLanguageLevel, psiClassTypeAnnotate, this.myResolveResult);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
        public String getPresentableText(boolean z) {
            String presentableText = this.myDelegate.getPresentableText(z);
            if (presentableText == null) {
                $$$reportNull$$$0(9);
            }
            return presentableText;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
        public String getCanonicalText(boolean z) {
            String canonicalText = this.myDelegate.getCanonicalText(z);
            if (canonicalText == null) {
                $$$reportNull$$$0(10);
            }
            return canonicalText;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
        public String getInternalCanonicalText() {
            String internalCanonicalText = this.myDelegate.getInternalCanonicalText();
            if (internalCanonicalText == null) {
                $$$reportNull$$$0(11);
            }
            return internalCanonicalText;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
        public boolean isValid() {
            return this.myDelegate.isValid() && this.myResolveResult.myMappedClass.isValid() && this.myResolveResult.mySubstitutor.isValid();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
        public boolean equalsToText(String str) {
            if (str == null) {
                $$$reportNull$$$0(12);
            }
            return this.myDelegate.equalsToText(str);
        }
    }

    private class CorrectedResolveResult implements PsiClassType.ClassResolveResult {
        private final PsiClassType.ClassResolveResult myClassResolveResult;
        private volatile PsiSubstitutor myLazySubstitutor;
        private final PsiClass myMappedClass;
        private final PsiClass myPsiClass;
        private final PsiSubstitutor mySubstitutor;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/TypeCorrector$CorrectedResolveResult", "getSubstitutor"));
        }

        CorrectedResolveResult(PsiClass psiClass, PsiClass psiClass2, PsiSubstitutor psiSubstitutor, PsiClassType.ClassResolveResult classResolveResult) {
            this.myPsiClass = psiClass;
            this.myMappedClass = psiClass2;
            this.mySubstitutor = psiSubstitutor;
            this.myClassResolveResult = classResolveResult;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public PsiSubstitutor getSubstitutor() {
            PsiSubstitutor psiSubstitutorMapSubstitutor = this.myLazySubstitutor;
            if (psiSubstitutorMapSubstitutor == null) {
                psiSubstitutorMapSubstitutor = TypeCorrector.this.mapSubstitutor(this.myPsiClass, this.myMappedClass, this.mySubstitutor);
                this.myLazySubstitutor = psiSubstitutorMapSubstitutor;
            }
            if (psiSubstitutorMapSubstitutor == null) {
                $$$reportNull$$$0(0);
            }
            return psiSubstitutorMapSubstitutor;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.ClassResolveResult, org.jetbrains.kotlin.com.intellij.psi.ResolveResult
        public PsiClass getElement() {
            return this.myMappedClass;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isPackagePrefixPackageReference() {
            return this.myClassResolveResult.isPackagePrefixPackageReference();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isAccessible() {
            return this.myClassResolveResult.isAccessible();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isStaticsScopeCorrect() {
            return this.myClassResolveResult.isStaticsScopeCorrect();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public PsiElement getCurrentFileResolveScope() {
            return this.myClassResolveResult.getCurrentFileResolveScope();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
        public boolean isValidResult() {
            return this.myClassResolveResult.isValidResult();
        }
    }
}
