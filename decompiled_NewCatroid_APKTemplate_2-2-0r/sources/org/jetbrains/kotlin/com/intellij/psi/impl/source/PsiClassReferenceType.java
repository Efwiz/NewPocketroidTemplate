package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.LinkedHashSet;
import java.util.Objects;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotatedJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassTypeReference;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiClassReferenceType extends PsiClassType.Stub {
    private final ClassReferencePointer myReference;

    /* JADX WARN: Code duplicated, block: B:18:0x002e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 8:
            case 13:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                i2 = 2;
                break;
            case 8:
            case 13:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        if (i == 2) {
            objArr[0] = "annotations";
        } else if (i != 4) {
            switch (i) {
                case 6:
                    objArr[0] = org.catrobat.catroid.common.Constants.EXTRA_PROVIDER;
                    break;
                case 7:
                case 9:
                case 10:
                case 11:
                case 12:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiClassReferenceType";
                    break;
                case 8:
                    objArr[0] = "text";
                    break;
                case 13:
                    objArr[0] = "languageLevel";
                    break;
                default:
                    objArr[0] = "reference";
                    break;
            }
        } else {
            objArr[0] = org.catrobat.catroid.common.Constants.EXTRA_PROVIDER;
        }
        switch (i) {
            case 7:
                objArr[1] = "collectAnnotations";
                break;
            case 8:
            case 13:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiClassReferenceType";
                break;
            case 9:
                objArr[1] = "getResolveScope";
                break;
            case 10:
                objArr[1] = "getAnnotations";
                break;
            case 11:
            case 12:
                objArr[1] = "getLanguageLevel";
                break;
            case 14:
                objArr[1] = "setLanguageLevel";
                break;
            case 15:
                objArr[1] = "rawType";
                break;
            case 16:
                objArr[1] = "getParameters";
                break;
            case 17:
            case 18:
                objArr[1] = "getPresentableText";
                break;
            case 19:
                objArr[1] = "getCanonicalText";
                break;
            case 20:
                objArr[1] = "getInternalCanonicalText";
                break;
            case 21:
                objArr[1] = "getReference";
                break;
        }
        switch (i) {
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                break;
            case 8:
                objArr[2] = "equalsToText";
                break;
            case 13:
                objArr[2] = "setLanguageLevel";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                throw new IllegalStateException(str2);
            case 8:
            case 13:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiClassReferenceType(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, LanguageLevel languageLevel) {
        this(psiJavaCodeReferenceElement, languageLevel, collectAnnotations(psiJavaCodeReferenceElement));
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiClassReferenceType(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, LanguageLevel languageLevel, PsiAnnotation[] psiAnnotationArr) {
        super(languageLevel, psiAnnotationArr);
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(1);
        }
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(2);
        }
        this.myReference = ClassReferencePointer.constant(psiJavaCodeReferenceElement);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiClassReferenceType(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, LanguageLevel languageLevel, TypeAnnotationProvider typeAnnotationProvider) {
        this(ClassReferencePointer.constant(psiJavaCodeReferenceElement), languageLevel, typeAnnotationProvider);
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(3);
        }
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(4);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiClassReferenceType(ClassReferencePointer classReferencePointer, LanguageLevel languageLevel, TypeAnnotationProvider typeAnnotationProvider) {
        super(languageLevel, typeAnnotationProvider);
        if (classReferencePointer == null) {
            $$$reportNull$$$0(5);
        }
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(6);
        }
        this.myReference = classReferencePointer;
    }

    private static PsiAnnotation[] collectAnnotations(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        SmartList smartList = null;
        for (PsiElement firstChild = psiJavaCodeReferenceElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof PsiAnnotation) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add((PsiAnnotation) firstChild);
            }
        }
        PsiAnnotation[] psiAnnotationArr = PsiAnnotation.EMPTY_ARRAY;
        if (smartList != null) {
            psiAnnotationArr = (PsiAnnotation[]) smartList.toArray(psiAnnotationArr);
        }
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(7);
        }
        return psiAnnotationArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementRetrieveReference = this.myReference.retrieveReference();
        if (psiJavaCodeReferenceElementRetrieveReference == null || !psiJavaCodeReferenceElementRetrieveReference.isValid()) {
            return false;
        }
        for (PsiAnnotation psiAnnotation : getAnnotations(false)) {
            if (!psiAnnotation.isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        String referenceName = getReference().getCustomReferenceName();
        return (referenceName == null || str.contains(referenceName)) && Objects.equals(str, getCanonicalText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope resolveScope = getReference().getResolveScope();
        if (resolveScope == null) {
            $$$reportNull$$$0(9);
        }
        return resolveScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] annotations = getAnnotations(true);
        if (annotations == null) {
            $$$reportNull$$$0(10);
        }
        return annotations;
    }

    private PsiAnnotation[] getAnnotations(boolean z) {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementRetrieveReference;
        PsiAnnotation[] annotations = super.getAnnotations();
        if (!z || (psiJavaCodeReferenceElementRetrieveReference = this.myReference.retrieveReference()) == null || !psiJavaCodeReferenceElementRetrieveReference.isValid() || !psiJavaCodeReferenceElementRetrieveReference.isQualified()) {
            return annotations;
        }
        PsiAnnotation[] psiAnnotationArrCollectAnnotations = collectAnnotations(psiJavaCodeReferenceElementRetrieveReference);
        if (annotations.length > 0 && psiAnnotationArrCollectAnnotations.length > 0) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            ContainerUtil.addAll(linkedHashSet, annotations);
            ContainerUtil.addAll(linkedHashSet, psiAnnotationArrCollectAnnotations);
            return (PsiAnnotation[]) linkedHashSet.toArray(PsiAnnotation.EMPTY_ARRAY);
        }
        return (PsiAnnotation[]) ArrayUtil.mergeArrays(annotations, psiAnnotationArrCollectAnnotations);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public LanguageLevel getLanguageLevel() {
        if (this.myLanguageLevel != null) {
            LanguageLevel languageLevel = this.myLanguageLevel;
            if (languageLevel == null) {
                $$$reportNull$$$0(11);
            }
            return languageLevel;
        }
        LanguageLevel languageLevel2 = PsiUtil.getLanguageLevel(getReference());
        if (languageLevel2 == null) {
            $$$reportNull$$$0(12);
        }
        return languageLevel2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiClassType setLanguageLevel(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(13);
        }
        return languageLevel.equals(this.myLanguageLevel) ? this : new PsiClassReferenceType(getReference(), languageLevel, getAnnotationProvider());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiClass resolve() {
        return resolveGenerics().getElement();
    }

    private static final class DelegatingClassResolveResult implements PsiClassType.ClassResolveResult {
        private final JavaResolveResult myDelegate;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiClassReferenceType$DelegatingClassResolveResult";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiClassReferenceType$DelegatingClassResolveResult";
            } else {
                objArr[1] = "getSubstitutor";
            }
            if (i != 1) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        private DelegatingClassResolveResult(JavaResolveResult javaResolveResult) {
            if (javaResolveResult == null) {
                $$$reportNull$$$0(0);
            }
            this.myDelegate = javaResolveResult;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public PsiSubstitutor getSubstitutor() {
            PsiSubstitutor substitutor = this.myDelegate.getSubstitutor();
            if (substitutor == null) {
                $$$reportNull$$$0(1);
            }
            return substitutor;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
        public boolean isValidResult() {
            return this.myDelegate.isValidResult();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isAccessible() {
            return this.myDelegate.isAccessible();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isStaticsScopeCorrect() {
            return this.myDelegate.isStaticsScopeCorrect();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public PsiElement getCurrentFileResolveScope() {
            return this.myDelegate.getCurrentFileResolveScope();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isPackagePrefixPackageReference() {
            return this.myDelegate.isPackagePrefixPackageReference();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.ClassResolveResult, org.jetbrains.kotlin.com.intellij.psi.ResolveResult
        public PsiClass getElement() {
            PsiElement element = this.myDelegate.getElement();
            if (element instanceof PsiClass) {
                return (PsiClass) element;
            }
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiClassType.ClassResolveResult resolveGenerics() {
        PsiJavaCodeReferenceElement reference = getReference();
        if (!reference.isValid()) {
            if (reference instanceof LightClassTypeReference) {
                PsiUtil.ensureValidType(((LightClassTypeReference) reference).getType());
            }
            throw new PsiInvalidElementAccessException(reference, this.myReference.toString() + "; augmenters=" + PsiAugmentProvider.EP_NAME.getExtensionList());
        }
        JavaResolveResult javaResolveResultAdvancedResolve = reference.advancedResolve(false);
        return javaResolveResultAdvancedResolve.getElement() == null ? PsiClassType.ClassResolveResult.EMPTY : new DelegatingClassResolveResult(javaResolveResultAdvancedResolve);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiClassType rawType() {
        PsiJavaCodeReferenceElement reference = getReference();
        PsiElement psiElementResolve = reference.resolve();
        if (psiElementResolve instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) psiElementResolve;
            return !PsiUtil.typeParametersIterable(psiClass).iterator().hasNext() ? this : new PsiImmediateClassType(psiClass, JavaPsiFacade.getElementFactory(reference.getManager().getProject()).createRawSubstitutor(psiClass), getLanguageLevel(), getAnnotationProvider());
        }
        String qualifiedName = reference.getQualifiedName();
        String referenceName = reference.getCustomReferenceName();
        if (referenceName == null) {
            referenceName = "";
        }
        return new PsiClassReferenceType(new LightClassReference(reference.getManager(), referenceName, qualifiedName, reference.getResolveScope()), (LanguageLevel) null, getAnnotationProvider());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public String getClassName() {
        return getReference().getCustomReferenceName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiType[] getParameters() {
        PsiType[] typeParameters = getReference().getTypeParameters();
        if (typeParameters == null) {
            $$$reportNull$$$0(16);
        }
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText(boolean z) {
        PsiAnnotation[] annotations;
        PsiJavaCodeReferenceElement reference = getReference();
        if (!z) {
            String presentableText = PsiNameHelper.getPresentableText(reference);
            if (presentableText == null) {
                $$$reportNull$$$0(17);
            }
            return presentableText;
        }
        if (reference.getQualifier() != null) {
            annotations = (PsiAnnotation[]) ObjectUtils.notNull((PsiAnnotation[]) PsiTreeUtil.getChildrenOfType(reference, PsiAnnotation.class), PsiAnnotation.EMPTY_ARRAY);
        } else {
            annotations = getAnnotations(false);
        }
        String presentableText2 = PsiNameHelper.getPresentableText(reference.getCustomReferenceName(), annotations, reference.getTypeParameters());
        if (presentableText2 == null) {
            $$$reportNull$$$0(18);
        }
        return presentableText2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText(boolean z) {
        String text = getText(z);
        if (text == null) {
            $$$reportNull$$$0(19);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        String canonicalText = getCanonicalText(true);
        if (canonicalText == null) {
            $$$reportNull$$$0(20);
        }
        return canonicalText;
    }

    private String getText(boolean z) {
        PsiJavaCodeReferenceElement reference = getReference();
        if (reference instanceof PsiAnnotatedJavaCodeReferenceElement) {
            PsiAnnotatedJavaCodeReferenceElement psiAnnotatedJavaCodeReferenceElement = (PsiAnnotatedJavaCodeReferenceElement) reference;
            PsiAnnotation[] annotations = z ? getAnnotations(false) : PsiAnnotation.EMPTY_ARRAY;
            if (annotations.length == 0) {
                annotations = null;
            }
            return psiAnnotatedJavaCodeReferenceElement.getCanonicalText(z, annotations);
        }
        return reference.getCanonicalText();
    }

    public PsiJavaCodeReferenceElement getReference() {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementRetrieveNonNullReference = this.myReference.retrieveNonNullReference();
        if (psiJavaCodeReferenceElementRetrieveNonNullReference == null) {
            $$$reportNull$$$0(21);
        }
        return psiJavaCodeReferenceElementRetrieveNonNullReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassType
    public PsiElement getPsiContext() {
        return this.myReference.retrieveReference();
    }
}
