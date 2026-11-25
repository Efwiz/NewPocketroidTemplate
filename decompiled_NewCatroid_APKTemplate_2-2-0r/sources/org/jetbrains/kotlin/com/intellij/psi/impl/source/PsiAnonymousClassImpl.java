package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiAnonymousClassImpl extends PsiClassImpl implements PsiAnonymousClass {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Key<PsiAnonymousClassImpl> STUB_BASE_CLASS_REFERENCE_HOLDER = Key.create("STUB_BASE_CLASS_REFERENCE_HOLDER");
    private SoftReference<PsiClassType> myCachedBaseType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 5:
                objArr[0] = "stub";
                break;
            case 6:
                objArr[0] = "name";
                break;
            case 7:
                objArr[0] = "visitor";
                break;
            case 8:
                objArr[0] = "processor";
                break;
            case 9:
                objArr[0] = "state";
                break;
            case 10:
                objArr[0] = "place";
                break;
            case 11:
            case 12:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiAnonymousClassImpl";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                objArr[1] = "getBaseClassType";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiAnonymousClassImpl";
                break;
            default:
                objArr[1] = "getBaseClassReference";
                break;
        }
        switch (i) {
            case 5:
                objArr[2] = "isDiamond";
                break;
            case 6:
                objArr[2] = "hasModifierProperty";
                break;
            case 7:
                objArr[2] = "accept";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "processDeclarations";
                break;
            case 11:
                objArr[2] = "isBaseClassReference";
                break;
            case 12:
                objArr[2] = "isBaseClassReferenceHolder";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getExtendsList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getImplementsList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getOriginalElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isAnnotationType() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isEnum() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        return false;
    }

    public PsiAnonymousClassImpl(PsiClassStub psiClassStub) {
        super(psiClassStub, JavaStubElementTypes.ANONYMOUS_CLASS);
    }

    public PsiAnonymousClassImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    protected Object clone() {
        PsiAnonymousClassImpl psiAnonymousClassImpl = (PsiAnonymousClassImpl) super.clone();
        psiAnonymousClassImpl.myCachedBaseType = null;
        return psiAnonymousClassImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        super.subtreeChanged();
        this.myCachedBaseType = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiExpressionList getArgumentList() {
        return (PsiExpressionList) getNode().findChildByRoleAsPsiElement(73);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiJavaCodeReferenceElement getBaseClassReference() {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) getFirstChild();
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(0);
        }
        return psiJavaCodeReferenceElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiClassType getBaseClassType() {
        PsiClassType javaLangObject;
        PsiClassStub<?> psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub == null) {
            this.myCachedBaseType = null;
            PsiClassType typeByTree = getTypeByTree();
            if (typeByTree == null) {
                $$$reportNull$$$0(1);
            }
            return typeByTree;
        }
        PsiClassType psiClassType = (PsiClassType) SoftReference.dereference(this.myCachedBaseType);
        if (psiClassType != null) {
            if (psiClassType == null) {
                $$$reportNull$$$0(2);
            }
            return psiClassType;
        }
        if (!isInQualifiedNew() && !isDiamond(psiClassStub)) {
            String baseClassReferenceText = psiClassStub.getBaseClassReferenceText();
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getProject());
            try {
                PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceFromText = elementFactory.createReferenceFromText(baseClassReferenceText, this);
                ((PsiJavaCodeReferenceElementImpl) psiJavaCodeReferenceElementCreateReferenceFromText).setKindWhenDummy(PsiJavaCodeReferenceElementImpl.Kind.CLASS_NAME_KIND);
                psiJavaCodeReferenceElementCreateReferenceFromText.getContainingFile().putUserData(STUB_BASE_CLASS_REFERENCE_HOLDER, this);
                javaLangObject = elementFactory.createType(psiJavaCodeReferenceElementCreateReferenceFromText);
            } catch (IncorrectOperationException unused) {
                javaLangObject = PsiType.getJavaLangObject(getManager(), getResolveScope());
            }
            this.myCachedBaseType = new SoftReference<>(javaLangObject);
            if (javaLangObject == null) {
                $$$reportNull$$$0(3);
            }
            return javaLangObject;
        }
        PsiClassType typeByTree2 = getTypeByTree();
        if (typeByTree2 == null) {
            $$$reportNull$$$0(4);
        }
        return typeByTree2;
    }

    private boolean isDiamond(PsiClassStub<?> psiClassStub) {
        String baseClassReferenceText;
        if (psiClassStub == null) {
            $$$reportNull$$$0(5);
        }
        if (PsiUtil.isLanguageLevel9OrHigher(this) && (baseClassReferenceText = psiClassStub.getBaseClassReferenceText()) != null && baseClassReferenceText.endsWith(">")) {
            return StringUtil.trimEnd(baseClassReferenceText, ">").trim().endsWith("<");
        }
        return false;
    }

    private PsiClassType getTypeByTree() {
        return JavaPsiFacade.getElementFactory(getProject()).createType(getBaseClassReference());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return str.equals("final");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAnonymousClass(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiAnonymousClass";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(8);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(10);
        }
        if (psiElement instanceof PsiExpressionList) {
            return true;
        }
        if (psiElement == null || !isBaseClassReference(psiElement)) {
            return super.processDeclarations(psiScopeProcessor, resolveState, psiElement, psiElement2);
        }
        return true;
    }

    public boolean isBaseClassReference(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        if (psiElement instanceof PsiJavaCodeReferenceElement) {
            PsiElement lightParent = psiElement.getMyParent();
            return (lightParent == this && psiElement == getBaseClassReference()) || isBaseClassReferenceHolder(lightParent);
        }
        return isBaseClassReferenceHolder(psiElement);
    }

    private boolean isBaseClassReferenceHolder(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        return (psiElement instanceof DummyHolder) && psiElement.getUserData(STUB_BASE_CLASS_REFERENCE_HOLDER) == this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public boolean isInQualifiedNew() {
        PsiClassStub psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub != null) {
            return psiClassStub.isAnonymousInQualifiedNew();
        }
        PsiElement parent = getMyParent();
        return (parent instanceof PsiNewExpression) && ((PsiNewExpression) parent).getQualifier() != null;
    }
}
