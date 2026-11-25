package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotatedJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class ClsJavaCodeReferenceElementImpl extends ClsElementImpl implements PsiAnnotatedJavaCodeReferenceElement {
    private final TypeAnnotationContainer myAnnotations;
    private final String myCanonicalText;
    private final PsiElement myParent;
    private final String myQualifiedName;
    private final ClsJavaCodeReferenceElementImpl myQualifier;
    private final PsiReferenceParameterList myRefParameterList;
    private String myShortName;

    /* JADX WARN: Code duplicated, block: B:8:0x0011 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 24 && i != 25) {
            switch (i) {
                default:
                    switch (i) {
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 24 && i != 25) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 12:
                        case 13:
                        case 14:
                        case 15:
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
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "canonicalText";
                break;
            case 2:
            default:
                objArr[0] = "parent";
                break;
            case 4:
                objArr[0] = "annotations";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
            case 15:
            case 24:
            case 25:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsJavaCodeReferenceElementImpl";
                break;
            case 10:
            case 16:
                objArr[0] = "containingFile";
                break;
            case 11:
                objArr[0] = "psiClass";
                break;
            case 17:
                objArr[0] = "processor";
                break;
            case 18:
                objArr[0] = "newElementName";
                break;
            case 19:
            case 20:
            case 22:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 21:
                objArr[0] = "buffer";
                break;
            case 23:
                objArr[0] = "visitor";
                break;
        }
        if (i == 24) {
            objArr[1] = "getElement";
        } else if (i != 25) {
            switch (i) {
                case 5:
                case 6:
                    objArr[1] = "getChildren";
                    break;
                case 7:
                case 8:
                case 9:
                    objArr[1] = "getCanonicalText";
                    break;
                default:
                    switch (i) {
                        case 12:
                        case 13:
                            objArr[1] = "advancedResolve";
                            break;
                        case 14:
                        case 15:
                            objArr[1] = "multiResolve";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsJavaCodeReferenceElementImpl";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getTypeParameters";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
            case 15:
            case 24:
            case 25:
                break;
            case 10:
                objArr[2] = "advancedResolveImpl";
                break;
            case 11:
                objArr[2] = "collectOuterClassTypeArgs";
                break;
            case 16:
                objArr[2] = "resolveElement";
                break;
            case 17:
                objArr[2] = "processVariants";
                break;
            case 18:
                objArr[2] = "handleElementRename";
                break;
            case 19:
                objArr[2] = "bindToElement";
                break;
            case 20:
                objArr[2] = "isReferenceTo";
                break;
            case 21:
                objArr[2] = "appendMirrorText";
                break;
            case 22:
                objArr[2] = "setMirror";
                break;
            case 23:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 24 && i != 25) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    break;
                default:
                    switch (i) {
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public PsiElement getQualifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiElement getReferenceNameElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public boolean isQualified() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClsJavaCodeReferenceElementImpl(PsiElement psiElement, String str) {
        this(psiElement, str, TypeAnnotationContainer.EMPTY);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
    }

    public ClsJavaCodeReferenceElementImpl(PsiElement psiElement, String str, TypeAnnotationContainer typeAnnotationContainer) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (typeAnnotationContainer == null) {
            $$$reportNull$$$0(4);
        }
        this.myParent = psiElement;
        String strInternFrequentType = TypeInfo.internFrequentType(str);
        this.myCanonicalText = strInternFrequentType;
        String strInternFrequentType2 = TypeInfo.internFrequentType(PsiNameHelper.getQualifiedClassName(strInternFrequentType, false));
        this.myQualifiedName = strInternFrequentType2.equals(strInternFrequentType) ? strInternFrequentType : strInternFrequentType2;
        String[] classParametersText = PsiNameHelper.getClassParametersText(str);
        this.myRefParameterList = classParametersText.length == 0 ? null : new ClsReferenceParameterListImpl(this, classParametersText, typeAnnotationContainer);
        this.myAnnotations = typeAnnotationContainer;
        String outerClassReference = PsiNameHelper.getOuterClassReference(str);
        TypeAnnotationContainer typeAnnotationContainerForEnclosingClass = outerClassReference.isEmpty() ? TypeAnnotationContainer.EMPTY : typeAnnotationContainer.forEnclosingClass();
        this.myQualifier = typeAnnotationContainerForEnclosingClass.isEmpty() ? null : new ClsJavaCodeReferenceElementImpl(this, outerClassReference, typeAnnotationContainerForEnclosingClass);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        ClsJavaCodeReferenceElementImpl clsJavaCodeReferenceElementImpl = this.myQualifier;
        if (clsJavaCodeReferenceElementImpl != null) {
            PsiReferenceParameterList psiReferenceParameterList = this.myRefParameterList;
            return psiReferenceParameterList != null ? new PsiElement[]{clsJavaCodeReferenceElementImpl, psiReferenceParameterList} : new PsiElement[]{clsJavaCodeReferenceElementImpl};
        }
        PsiReferenceParameterList psiReferenceParameterList2 = this.myRefParameterList;
        PsiElement[] psiElementArr = psiReferenceParameterList2 != null ? new PsiElement[]{psiReferenceParameterList2} : PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return PsiNameHelper.getPresentableText(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextLength() {
        return getText().length();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String str = this.myCanonicalText;
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotatedJavaCodeReferenceElement
    public String getCanonicalText(boolean z, PsiAnnotation[] psiAnnotationArr) {
        int length;
        String canonicalText = getCanonicalText();
        if (!z || psiAnnotationArr == null) {
            if (canonicalText == null) {
                $$$reportNull$$$0(8);
            }
            return canonicalText;
        }
        StringBuilder sb = new StringBuilder();
        String outerClassReference = PsiNameHelper.getOuterClassReference(canonicalText);
        if (StringUtil.isEmpty(outerClassReference)) {
            length = 0;
        } else {
            ClsJavaCodeReferenceElementImpl clsJavaCodeReferenceElementImpl = this.myQualifier;
            if (clsJavaCodeReferenceElementImpl != null) {
                sb.append(clsJavaCodeReferenceElementImpl.getCanonicalText(true, clsJavaCodeReferenceElementImpl.myAnnotations.getProvider(clsJavaCodeReferenceElementImpl).getAnnotations()));
            } else {
                sb.append(outerClassReference);
            }
            sb.append('.');
            length = outerClassReference.length() + 1;
        }
        PsiNameHelper.appendAnnotations(sb, (List<? extends PsiAnnotation>) Arrays.asList(psiAnnotationArr), true);
        int iIndexOf = canonicalText.indexOf(60, length);
        if (iIndexOf == -1) {
            sb.append((CharSequence) canonicalText, length, canonicalText.length());
        } else {
            sb.append((CharSequence) canonicalText, length, iIndexOf);
            PsiNameHelper.appendTypeArgs(sb, getTypeParameters(), true, true);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(9);
        }
        return string;
    }

    private static class Resolver implements ResolveCache.PolyVariantContextResolver<ClsJavaCodeReferenceElementImpl> {
        public static final Resolver INSTANCE = new Resolver();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "containingFile";
            } else if (i != 2) {
                objArr[0] = "ref";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsJavaCodeReferenceElementImpl$Resolver";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsJavaCodeReferenceElementImpl$Resolver";
            } else {
                objArr[1] = "resolve";
            }
            if (i != 2) {
                objArr[2] = "resolve";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        private Resolver() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.PolyVariantContextResolver
        public JavaResolveResult[] resolve(ClsJavaCodeReferenceElementImpl clsJavaCodeReferenceElementImpl, PsiFile psiFile, boolean z) {
            if (clsJavaCodeReferenceElementImpl == null) {
                $$$reportNull$$$0(0);
            }
            if (psiFile == null) {
                $$$reportNull$$$0(1);
            }
            JavaResolveResult javaResolveResultAdvancedResolveImpl = clsJavaCodeReferenceElementImpl.advancedResolveImpl(psiFile);
            JavaResolveResult[] javaResolveResultArr = javaResolveResultAdvancedResolveImpl == null ? JavaResolveResult.EMPTY_ARRAY : new JavaResolveResult[]{javaResolveResultAdvancedResolveImpl};
            if (javaResolveResultArr == null) {
                $$$reportNull$$$0(2);
            }
            return javaResolveResultArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JavaResolveResult advancedResolveImpl(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(10);
        }
        PsiReferenceParameterList psiReferenceParameterList = this.myRefParameterList;
        PsiTypeElement[] typeParameterElements = psiReferenceParameterList == null ? PsiTypeElement.EMPTY_ARRAY : psiReferenceParameterList.getTypeParameterElements();
        PsiElement psiElementResolveElement = resolveElement(psiFile);
        if (psiElementResolveElement == null) {
            return null;
        }
        if (psiElementResolveElement instanceof PsiClass) {
            HashMap map = new HashMap();
            PsiClass psiClass = (PsiClass) psiElementResolveElement;
            int i = 0;
            for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(psiClass)) {
                if (i >= typeParameterElements.length) {
                    map.put(psiTypeParameter, null);
                } else {
                    map.put(psiTypeParameter, typeParameterElements[i].getType());
                }
                i++;
            }
            collectOuterClassTypeArgs(psiClass, this.myCanonicalText, map);
            return new CandidateInfo(psiElementResolveElement, PsiSubstitutor.createSubstitutor(map));
        }
        return new CandidateInfo(psiElementResolveElement, PsiSubstitutor.EMPTY);
    }

    private void collectOuterClassTypeArgs(PsiClass psiClass, String str, Map<PsiTypeParameter, PsiType> map) {
        if (psiClass == null) {
            $$$reportNull$$$0(11);
        }
        PsiClass psiClassMo6092getContainingClass = psiClass.mo6092getContainingClass();
        if (psiClassMo6092getContainingClass != null) {
            String outerClassReference = PsiNameHelper.getOuterClassReference(str);
            String[] classParametersText = PsiNameHelper.getClassParametersText(outerClassReference);
            PsiType[] typeArguments = classParametersText.length == 0 ? null : new ClsReferenceParameterListImpl(this, classParametersText, TypeAnnotationContainer.EMPTY).getTypeArguments();
            PsiTypeParameter[] typeParameters = psiClassMo6092getContainingClass.getTypeParameters();
            for (int i = 0; i < typeParameters.length; i++) {
                if (typeArguments != null) {
                    if (i < typeArguments.length) {
                        map.put(typeParameters[i], typeArguments[i]);
                    }
                } else {
                    map.put(typeParameters[i], null);
                }
            }
            if (psiClassMo6092getContainingClass.hasModifierProperty("static")) {
                return;
            }
            collectOuterClassTypeArgs(psiClassMo6092getContainingClass, outerClassReference, map);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        JavaResolveResult[] javaResolveResultArrMultiResolve = multiResolve(z);
        if (javaResolveResultArrMultiResolve.length == 1) {
            JavaResolveResult javaResolveResult = javaResolveResultArrMultiResolve[0];
            if (javaResolveResult == null) {
                $$$reportNull$$$0(12);
            }
            return javaResolveResult;
        }
        JavaResolveResult javaResolveResult2 = JavaResolveResult.EMPTY;
        if (javaResolveResult2 == null) {
            $$$reportNull$$$0(13);
        }
        return javaResolveResult2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        PsiFile containingFile = getContainingFile();
        if (containingFile == null) {
            return diagnoseNoFile();
        }
        ResolveResult[] resolveResultArrResolveWithCaching = ResolveCache.getInstance(containingFile.getProject()).resolveWithCaching(this, (ResolveCache.PolyVariantContextResolver<ClsJavaCodeReferenceElementImpl>) Resolver.INSTANCE, true, z, containingFile);
        if (resolveResultArrResolveWithCaching.length == 0) {
            JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
            if (javaResolveResultArr == null) {
                $$$reportNull$$$0(14);
            }
            return javaResolveResultArr;
        }
        JavaResolveResult[] javaResolveResultArr2 = (JavaResolveResult[]) resolveResultArrResolveWithCaching;
        if (javaResolveResultArr2 == null) {
            $$$reportNull$$$0(15);
        }
        return javaResolveResultArr2;
    }

    private JavaResolveResult[] diagnoseNoFile() {
        PsiElement psiElementLast = SyntaxTraverser.psiApi().parents(this).last();
        PsiUtilCore.ensureValid((PsiElement) Objects.requireNonNull(psiElementLast));
        throw new PsiInvalidElementAccessException(this, "parent=" + this.myParent + ", root=" + psiElementLast + ", canonicalText=" + this.myCanonicalText);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        return advancedResolve(false).getElement();
    }

    private PsiElement resolveElement(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(16);
        }
        for (PsiElement parent = getMyParent(); parent != null && !(parent instanceof PsiFile); parent = parent.getMyParent()) {
            if (parent instanceof PsiMethod) {
                for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable((PsiMethod) parent)) {
                    if (this.myQualifiedName.equals(psiTypeParameter.getMyName())) {
                        return psiTypeParameter;
                    }
                }
            } else if ((parent instanceof PsiClass) && !(parent instanceof PsiTypeParameter)) {
                PsiClass psiClass = (PsiClass) parent;
                if (this.myQualifiedName.equals(psiClass.getQualifiedName())) {
                    return parent;
                }
                for (PsiTypeParameter psiTypeParameter2 : PsiUtil.typeParametersIterable(psiClass)) {
                    if (this.myQualifiedName.equals(psiTypeParameter2.getMyName())) {
                        return psiTypeParameter2;
                    }
                }
                for (PsiClass psiClass2 : psiClass.getInnerClasses()) {
                    if (this.myQualifiedName.equals(psiClass2.getQualifiedName())) {
                        return psiClass2;
                    }
                }
            }
        }
        Project project = psiFile.getProject();
        return JavaPsiFacade.getInstance(project).findClass(this.myQualifiedName, ResolveScopeManager.getInstance(project).getResolveScope(this));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public void processVariants(PsiScopeProcessor psiScopeProcessor) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(17);
        }
        throw new RuntimeException("Variants are not available for light references");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiReferenceParameterList getParameterList() {
        return this.myRefParameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        return getCanonicalText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    /* JADX INFO: renamed from: getReferenceName */
    public String getCustomReferenceName() {
        String str = this.myShortName;
        if (str != null) {
            return str;
        }
        String shortClassName = PsiNameHelper.getShortClassName(this.myCanonicalText);
        this.myShortName = shortClassName;
        return shortClassName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(19);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(20);
        }
        if (psiElement instanceof PsiClass) {
            return this.myCanonicalText.equals(((PsiClass) psiElement).getQualifiedName()) || getManager().areElementsEquivalent(resolve(), psiElement);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        throw new RuntimeException("Variants are not available for references to compiled code");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(21);
        }
        sb.append(getCanonicalText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(22);
        }
        setMirrorCheckingType(treeElement, JavaElementType.JAVA_CODE_REFERENCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(23);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return new TextRange(0, getTextLength());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiType[] getTypeParameters() {
        PsiReferenceParameterList psiReferenceParameterList = this.myRefParameterList;
        PsiType[] typeArguments = psiReferenceParameterList == null ? PsiType.EMPTY_ARRAY : psiReferenceParameterList.getTypeArguments();
        if (typeArguments == null) {
            $$$reportNull$$$0(25);
        }
        return typeArguments;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiJavaCodeReferenceElement:" + getText();
    }
}
