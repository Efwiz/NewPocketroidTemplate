package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.List;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class LightMethod extends LightElement implements PsiMethod {
    protected final PsiClass myContainingClass;
    protected final PsiMethod myMethod;
    protected final PsiSubstitutor mySubstitutor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 20:
            case 23:
            case 26:
            case 33:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
                i2 = 2;
                break;
            case 20:
            case 23:
            case 26:
            case 33:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 11:
            case 15:
                objArr[0] = "method";
                break;
            case 2:
            case 9:
            case 18:
            case 26:
                objArr[0] = "substitutor";
                break;
            case 3:
            case 6:
            case 10:
            case 14:
                objArr[0] = "manager";
                break;
            case 5:
            case 8:
            case 12:
            case 16:
            default:
                objArr[0] = "containingClass";
                break;
            case 13:
            case 17:
                objArr[0] = "language";
                break;
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightMethod";
                break;
            case 20:
            case 23:
                objArr[0] = "name";
                break;
            case 33:
                objArr[0] = "visitor";
                break;
        }
        switch (i) {
            case 19:
                objArr[1] = "getTypeParameters";
                break;
            case 20:
            case 23:
            case 26:
            case 33:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightMethod";
                break;
            case 21:
                objArr[1] = "getName";
                break;
            case 22:
                objArr[1] = "getHierarchicalMethodSignature";
                break;
            case 24:
                objArr[1] = "getModifierList";
                break;
            case 25:
                objArr[1] = "getThrowsList";
                break;
            case 27:
                objArr[1] = "getSignature";
                break;
            case 28:
            case 29:
            case 30:
                objArr[1] = "findSuperMethods";
                break;
            case 31:
                objArr[1] = "findSuperMethodSignaturesIncludingStatic";
                break;
            case 32:
                objArr[1] = "findDeepestSuperMethods";
                break;
            case 34:
                objArr[1] = "getContainingClass";
                break;
            case 35:
                objArr[1] = "getUseScope";
                break;
        }
        switch (i) {
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
                break;
            case 20:
                objArr[2] = "setName";
                break;
            case 23:
                objArr[2] = "hasModifierProperty";
                break;
            case 26:
                objArr[2] = "getSignature";
                break;
            case 33:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
                throw new IllegalStateException(str2);
            case 20:
            case 23:
            case 26:
            case 33:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightMethod(PsiClass psiClass, PsiMethod psiMethod, PsiSubstitutor psiSubstitutor) {
        this(psiClass.getManager(), psiMethod, psiClass, psiClass.getLanguage(), psiSubstitutor);
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(1);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightMethod(PsiManager psiManager, PsiMethod psiMethod, PsiClass psiClass) {
        this(psiManager, psiMethod, psiClass, PsiSubstitutor.EMPTY);
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(4);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(5);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightMethod(PsiManager psiManager, PsiMethod psiMethod, PsiClass psiClass, PsiSubstitutor psiSubstitutor) {
        this(psiManager, psiMethod, psiClass, JavaLanguage.INSTANCE, psiSubstitutor);
        if (psiManager == null) {
            $$$reportNull$$$0(6);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(7);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(8);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(9);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightMethod(PsiManager psiManager, PsiMethod psiMethod, PsiClass psiClass, Language language) {
        this(psiManager, psiMethod, psiClass, language, PsiSubstitutor.EMPTY);
        if (psiManager == null) {
            $$$reportNull$$$0(10);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(11);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(12);
        }
        if (language == null) {
            $$$reportNull$$$0(13);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightMethod(PsiManager psiManager, PsiMethod psiMethod, PsiClass psiClass, Language language, PsiSubstitutor psiSubstitutor) {
        super(psiManager, language);
        if (psiManager == null) {
            $$$reportNull$$$0(14);
        }
        if (psiMethod == null) {
            $$$reportNull$$$0(15);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(16);
        }
        if (language == null) {
            $$$reportNull$$$0(17);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(18);
        }
        this.myMethod = psiMethod;
        this.myContainingClass = psiClass;
        this.mySubstitutor = psiSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return this.myMethod.hasTypeParameters();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = this.myMethod.getTypeParameters();
        if (typeParameters == null) {
            $$$reportNull$$$0(19);
        }
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return this.myMethod.mo6093getTypeParameterList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return this.myMethod.mo6091getDocComment();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return this.myMethod.isDeprecated();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        return this.myMethod.setName(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        String myName = this.myMethod.getMyName();
        if (myName == null) {
            $$$reportNull$$$0(21);
        }
        return myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public HierarchicalMethodSignature getHierarchicalMethodSignature() {
        HierarchicalMethodSignature hierarchicalMethodSignature = this.myMethod.getHierarchicalMethodSignature();
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(22);
        }
        return hierarchicalMethodSignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(23);
        }
        return this.myMethod.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return this.myMethod.getTextRange();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        PsiModifierList psiModifierListMo6095getModifierList = this.myMethod.mo6095getModifierList();
        if (psiModifierListMo6095getModifierList == null) {
            $$$reportNull$$$0(24);
        }
        return psiModifierListMo6095getModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        return this.mySubstitutor.substitute(this.myMethod.getReturnType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiTypeElement getReturnTypeElement() {
        return this.myMethod.getReturnTypeElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        if (this.mySubstitutor == PsiSubstitutor.EMPTY) {
            return this.myMethod.getParameterList();
        }
        return new LightParameterListWrapper(this.myMethod.getParameterList(), this.mySubstitutor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiReferenceList getThrowsList() {
        PsiReferenceList throwsList = this.myMethod.getThrowsList();
        if (throwsList == null) {
            $$$reportNull$$$0(25);
        }
        return throwsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiCodeBlock getBody() {
        return this.myMethod.getBody();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isConstructor() {
        return this.myMethod.isConstructor();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isVarArgs() {
        return this.myMethod.isVarArgs();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public MethodSignature getSignature(PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(26);
        }
        MethodSignature signature = this.myMethod.getSignature(psiSubstitutor);
        if (signature == null) {
            $$$reportNull$$$0(27);
        }
        return signature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return this.myMethod.mo7392getNameIdentifier();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods() {
        PsiMethod[] psiMethodArrFindSuperMethods = this.myMethod.findSuperMethods();
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(28);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(boolean z) {
        PsiMethod[] psiMethodArrFindSuperMethods = this.myMethod.findSuperMethods(z);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(29);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(PsiClass psiClass) {
        PsiMethod[] psiMethodArrFindSuperMethods = this.myMethod.findSuperMethods(psiClass);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(30);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean z) {
        List<MethodSignatureBackedByPsiMethod> listFindSuperMethodSignaturesIncludingStatic = this.myMethod.findSuperMethodSignaturesIncludingStatic(z);
        if (listFindSuperMethodSignaturesIncludingStatic == null) {
            $$$reportNull$$$0(31);
        }
        return listFindSuperMethodSignaturesIncludingStatic;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod findDeepestSuperMethod() {
        return this.myMethod.findDeepestSuperMethod();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findDeepestSuperMethods() {
        PsiMethod[] psiMethodArrFindDeepestSuperMethods = this.myMethod.findDeepestSuperMethods();
        if (psiMethodArrFindDeepestSuperMethods == null) {
            $$$reportNull$$$0(32);
        }
        return psiMethodArrFindDeepestSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myMethod.getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(33);
        }
        this.myMethod.accept(psiElementVisitor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new LightMethod(this.myManager, (PsiMethod) this.myMethod.copy(), this.myContainingClass);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return this.myContainingClass.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        PsiClass psiClass = this.myContainingClass;
        if (psiClass == null) {
            $$$reportNull$$$0(34);
        }
        return psiClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return this.myContainingClass.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "Light PSI method wrapper:" + getMyName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, hasModifierProperty("abstract") ? PlatformIcons.ABSTRACT_METHOD_ICON : PlatformIcons.METHOD_ICON, ElementPresentationUtil.getFlags(this, false)));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isMethodEquivalentTo(this, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope memberUseScope = PsiImplUtil.getMemberUseScope(this);
        if (memberUseScope == null) {
            $$$reportNull$$$0(35);
        }
        return memberUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return mo6092getContainingClass();
    }
}
