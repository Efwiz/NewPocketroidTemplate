package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.model.psi.PsiSymbolReference;
import org.jetbrains.kotlin.com.intellij.model.psi.PsiSymbolService;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.stub.JavaStubImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class PsiEnumConstantImpl extends JavaStubPsiElement<PsiFieldStub> implements PsiEnumConstant {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiEnumConstantImpl.class);
    private final MyReference myReference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 4:
            case 10:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
                i2 = 2;
                break;
            case 4:
            case 10:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiEnumConstantImpl";
                break;
            case 4:
            case 10:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "visitor";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[1] = "getOrCreateInitializingClass";
                break;
            case 4:
            case 10:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiEnumConstantImpl";
                break;
            case 5:
                objArr[1] = "getType";
                break;
            case 6:
                objArr[1] = "resolveMethodGenerics";
                break;
            case 7:
                objArr[1] = "getNameIdentifier";
                break;
            case 8:
            case 9:
                objArr[1] = "getName";
                break;
            case 11:
                objArr[1] = "getOwnReferences";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
                break;
            case 4:
                objArr[2] = "hasModifierProperty";
                break;
            case 10:
                objArr[2] = "setName";
                break;
            default:
                objArr[2] = "accept";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
                throw new IllegalStateException(str2);
            case 4:
            case 10:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
    }

    public PsiEnumConstantImpl(PsiFieldStub psiFieldStub) {
        super(psiFieldStub, JavaStubElementTypes.ENUM_CONSTANT);
        this.myReference = new MyReference();
    }

    public PsiEnumConstantImpl(ASTNode aSTNode) {
        super(aSTNode);
        this.myReference = new MyReference();
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiEnumConstant:" + get$name();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitEnumConstant(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant, org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiExpressionList getArgumentList() {
        return (PsiExpressionList) calcTreeElement().findChildByRoleAsPsiElement(73);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant
    public PsiEnumConstantInitializer getInitializingClass() {
        return (PsiEnumConstantInitializer) getStubOrPsiChild(JavaStubElementTypes.ENUM_CONSTANT_INITIALIZER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant
    public PsiEnumConstantInitializer getOrCreateInitializingClass() {
        PsiEnumConstantInitializer initializingClass = getInitializingClass();
        if (initializingClass != null) {
            if (initializingClass == null) {
                $$$reportNull$$$0(1);
            }
            return initializingClass;
        }
        PsiEnumConstantInitializer initializingClass2 = JavaPsiFacade.getElementFactory(getProject()).createEnumConstantFromText("foo{}", null).getInitializingClass();
        LOG.assertTrue(initializingClass2 != null);
        PsiExpressionList argumentList = getArgumentList();
        if (argumentList != null) {
            PsiEnumConstantInitializer psiEnumConstantInitializer = (PsiEnumConstantInitializer) addAfter(initializingClass2, argumentList);
            if (psiEnumConstantInitializer == null) {
                $$$reportNull$$$0(2);
            }
            return psiEnumConstantInitializer;
        }
        PsiEnumConstantInitializer psiEnumConstantInitializer2 = (PsiEnumConstantInitializer) addAfter(initializingClass2, mo7392getNameIdentifier());
        if (psiEnumConstantInitializer2 == null) {
            $$$reportNull$$$0(3);
        }
        return psiEnumConstantInitializer2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public PsiClass getContainingClass() {
        PsiElement parent = getLightParent();
        if (parent instanceof PsiClass) {
            return (PsiClass) parent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        PsiClass psiClassMo6092getContainingClass = getContainingClass();
        return psiClassMo6092getContainingClass != null ? psiClassMo6092getContainingClass : super.getContext();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return (PsiModifierList) getStubOrPsiChild(JavaStubElementTypes.MODIFIER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return "public".equals(str) || "static".equals(str) || "final".equals(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField
    public PsiType getType() {
        PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(getProject()).createType(getContainingClass());
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(5);
        }
        return psiClassTypeCreateType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiMethod resolveMethod() {
        return (PsiMethod) resolveMethodGenerics().getElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public JavaResolveResult resolveMethodGenerics() {
        JavaResolveResult javaResolveResult = (JavaResolveResult) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiEnumConstantImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6650x2c3e395();
            }
        });
        if (javaResolveResult == null) {
            $$$reportNull$$$0(6);
        }
        return javaResolveResult;
    }

    /* JADX INFO: renamed from: lambda$resolveMethodGenerics$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiEnumConstantImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6650x2c3e395() {
        PsiClass psiClassMo6092getContainingClass = getContainingClass();
        LOG.assertTrue(psiClassMo6092getContainingClass != null);
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(getProject());
        return new CachedValueProvider.Result(javaPsiFacade.getResolveHelper().resolveConstructor(javaPsiFacade.getElementFactory().createType(psiClassMo6092getContainingClass), getArgumentList(), this), PsiModificationTracker.MODIFICATION_COUNT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier psiIdentifier = (PsiIdentifier) calcTreeElement().findChildByRoleAsPsiElement(9);
        if (psiIdentifier == null) {
            $$$reportNull$$$0(7);
        }
        return psiIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get$name() {
        PsiFieldStub psiFieldStub = (PsiFieldStub) getGreenStub();
        if (psiFieldStub != null) {
            String name2 = psiFieldStub.getName();
            if (name2 == null) {
                $$$reportNull$$$0(8);
            }
            return name2;
        }
        String text = mo7392getNameIdentifier().getText();
        if (text == null) {
            $$$reportNull$$$0(9);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        PsiImplUtil.setName(mo7392getNameIdentifier(), str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return (PsiDocComment) calcTreeElement().findChildByRoleAsPsiElement(7);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return JavaStubImplUtil.isMemberDeprecated(this, (PsiMemberStub) getGreenStub());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return this.myReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Collection<? extends PsiSymbolReference> getOwnReferences() {
        List listSingletonList = Collections.singletonList(PsiSymbolService.getInstance().asSymbolReference(this.myReference));
        if (listSingletonList == null) {
            $$$reportNull$$$0(11);
        }
        return listSingletonList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstructorCall
    public PsiMethod resolveConstructor() {
        return resolveMethod();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, PlatformIcons.FIELD_ICON, ElementPresentationUtil.getFlags(this, false)));
    }

    private class MyReference implements PsiJavaReference {
        /* JADX WARN: Code duplicated, block: B:23:0x0033  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4 || i == 5 || i == 10) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 10) ? 3 : 2];
            if (i == 3) {
                objArr[0] = "newElementName";
            } else if (i == 4) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else if (i == 5) {
                objArr[0] = "processor";
            } else if (i != 10) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiEnumConstantImpl$MyReference";
            } else {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            }
            switch (i) {
                case 1:
                case 2:
                    objArr[1] = "getRangeInElement";
                    break;
                case 3:
                case 4:
                case 5:
                case 10:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiEnumConstantImpl$MyReference";
                    break;
                case 6:
                    objArr[1] = "multiResolve";
                    break;
                case 7:
                case 8:
                    objArr[1] = "advancedResolve";
                    break;
                case 9:
                    objArr[1] = "getCanonicalText";
                    break;
                default:
                    objArr[1] = "getElement";
                    break;
            }
            if (i == 3) {
                objArr[2] = "handleElementRename";
            } else if (i == 4) {
                objArr[2] = "bindToElement";
            } else if (i == 5) {
                objArr[2] = "processVariants";
            } else if (i == 10) {
                objArr[2] = "isReferenceTo";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4 && i != 5 && i != 10) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public boolean isSoft() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
        public void processVariants(PsiScopeProcessor psiScopeProcessor) {
            if (psiScopeProcessor == null) {
                $$$reportNull$$$0(5);
            }
        }

        private MyReference() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        /* JADX INFO: renamed from: getElement */
        public PsiElement getPsiElement() {
            PsiEnumConstantImpl psiEnumConstantImpl = PsiEnumConstantImpl.this;
            if (psiEnumConstantImpl == null) {
                $$$reportNull$$$0(0);
            }
            return psiEnumConstantImpl;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public TextRange getRangeInElement() {
            PsiIdentifier psiIdentifierMo7392getNameIdentifier = PsiEnumConstantImpl.this.mo7392getNameIdentifier();
            int startOffsetInParent = psiIdentifierMo7392getNameIdentifier.getStartOffsetInParent();
            if (Registry.is("java.empty.enum.constructor.ref")) {
                TextRange textRangeFrom = TextRange.from(startOffsetInParent + psiIdentifierMo7392getNameIdentifier.getTextLength(), 0);
                if (textRangeFrom == null) {
                    $$$reportNull$$$0(1);
                }
                return textRangeFrom;
            }
            TextRange textRangeFrom2 = TextRange.from(startOffsetInParent, psiIdentifierMo7392getNameIdentifier.getTextLength());
            if (textRangeFrom2 == null) {
                $$$reportNull$$$0(2);
            }
            return textRangeFrom2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement handleElementRename(String str) throws IncorrectOperationException {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            return getPsiElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
            if (psiElement == null) {
                $$$reportNull$$$0(4);
            }
            throw new IncorrectOperationException("Invalid operation");
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
        public JavaResolveResult[] multiResolve(boolean z) {
            JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(PsiEnumConstantImpl.this.getProject());
            JavaResolveResult[] javaResolveResultArrMultiResolveConstructor = javaPsiFacade.getResolveHelper().multiResolveConstructor(javaPsiFacade.getElementFactory().createType(PsiEnumConstantImpl.this.getContainingClass()), PsiEnumConstantImpl.this.getArgumentList(), getPsiElement());
            if (javaResolveResultArrMultiResolveConstructor == null) {
                $$$reportNull$$$0(6);
            }
            return javaResolveResultArrMultiResolveConstructor;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
        public JavaResolveResult advancedResolve(boolean z) {
            JavaResolveResult[] javaResolveResultArrMultiResolve = multiResolve(z);
            if (javaResolveResultArrMultiResolve.length == 1) {
                JavaResolveResult javaResolveResult = javaResolveResultArrMultiResolve[0];
                if (javaResolveResult == null) {
                    $$$reportNull$$$0(7);
                }
                return javaResolveResult;
            }
            JavaResolveResult javaResolveResult2 = JavaResolveResult.EMPTY;
            if (javaResolveResult2 == null) {
                $$$reportNull$$$0(8);
            }
            return javaResolveResult2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement resolve() {
            return advancedResolve(false).getElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public String getCanonicalText() {
            String name2 = PsiEnumConstantImpl.this.getContainingClass().get$name();
            if (name2 == null) {
                $$$reportNull$$$0(9);
            }
            return name2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public boolean isReferenceTo(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(10);
            }
            if (psiElement instanceof PsiMethod) {
                PsiMethod psiMethod = (PsiMethod) psiElement;
                if (psiMethod.isConstructor() && psiMethod.getContainingClass() == PsiEnumConstantImpl.this.getContainingClass() && PsiEnumConstantImpl.this.getManager().areElementsEquivalent(resolve(), psiElement)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    /* JADX INFO: renamed from: setInitializer */
    public void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isFieldEquivalentTo(this, psiElement);
    }
}
