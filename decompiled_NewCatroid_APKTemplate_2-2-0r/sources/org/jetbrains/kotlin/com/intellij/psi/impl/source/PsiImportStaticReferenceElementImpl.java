package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.filters.ClassFilter;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.StaticImportResolveProcessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiImportStaticReferenceElementImpl extends CompositePsiElement implements PsiImportStaticReferenceElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiImportStaticReferenceElementImpl.class);
    private volatile String myCanonicalText;

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
                str = "@NotNull method %s.%s must not return null";
                break;
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
                i2 = 2;
                break;
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
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImportStaticReferenceElementImpl";
                break;
            case 7:
            case 9:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 8:
                objArr[0] = "newElementName";
                break;
            case 10:
                objArr[0] = "processor";
                break;
            case 11:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "child";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getTypeParameters";
                break;
            case 2:
                objArr[1] = "getElement";
                break;
            case 3:
                objArr[1] = "getCanonicalText";
                break;
            case 4:
            case 5:
                objArr[1] = "advancedResolve";
                break;
            case 6:
                objArr[1] = "multiResolve";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImportStaticReferenceElementImpl";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                break;
            case 7:
                objArr[2] = "isReferenceTo";
                break;
            case 8:
                objArr[2] = "handleElementRename";
                break;
            case 9:
                objArr[2] = "bindToElement";
                break;
            case 10:
                objArr[2] = "processVariants";
                break;
            case 11:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "getChildRole";
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
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiReferenceParameterList getParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    public PsiImportStaticReferenceElementImpl() {
        super(JavaElementType.IMPORT_STATIC_REFERENCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        ASTNode aSTNodeFindChildByRole = findChildByRole(53);
        if (aSTNodeFindChildByRole != null) {
            return aSTNodeFindChildByRole.getStartOffset();
        }
        return super.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        this.myCanonicalText = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public final ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        switch (i) {
            case 53:
                return findChildByType(JavaTokenType.IDENTIFIER);
            case 54:
                TreeElement firstChildNode = getFirstChildNode();
                if (firstChildNode.getElementType() == JavaElementType.JAVA_CODE_REFERENCE) {
                    return firstChildNode;
                }
                return null;
            case 55:
                return findChildByType(JavaTokenType.DOT);
            default:
                return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public final int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaElementType.JAVA_CODE_REFERENCE) {
            return 54;
        }
        if (elementType == JavaTokenType.DOT) {
            return 55;
        }
        return elementType == JavaTokenType.IDENTIFIER ? 53 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiElement getReferenceNameElement() {
        return findChildByRoleAsPsiElement(53);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiType[] getTypeParameters() {
        PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
        if (psiTypeArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public PsiElement getQualifier() {
        return findChildByRoleAsPsiElement(54);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticReferenceElement
    public PsiJavaCodeReferenceElement getClassReference() {
        return (PsiJavaCodeReferenceElement) findChildByRoleAsPsiElement(54);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticReferenceElement
    public PsiImportStaticStatement bindToTargetClass(PsiClass psiClass) throws IncorrectOperationException {
        String qualifiedName = psiClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IncorrectOperationException();
        }
        CompositeElement compositeElement = (CompositeElement) JavaPsiFacade.getInstance(getProject()).getParserFacade().createReferenceFromText(qualifiedName, null).getNode();
        if (getQualifier() != null) {
            replaceChildInternal(findChildByRole(54), compositeElement);
            return (PsiImportStaticStatement) getLightParent();
        }
        LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.DOT, ".", 0, 1, SharedImplUtil.findCharTableByTree(compositeElement), getManager());
        compositeElement.rawInsertAfterMe(leafElementCreateSingleLeafElement);
        CompositeElement compositeElementCreateErrorElement = Factory.createErrorElement(JavaPsiBundle.message("import.statement.identifier.or.asterisk.expected.", new Object[0]));
        leafElementCreateSingleLeafElement.rawInsertAfterMe(compositeElementCreateErrorElement);
        CompositeElement compositeElement2 = (CompositeElement) SourceTreeToPsiMap.psiElementToTree(getLightParent());
        compositeElement2.addInternal(compositeElement, compositeElementCreateErrorElement, this, Boolean.TRUE);
        compositeElement2.deleteChildInternal(this);
        return (PsiImportStaticStatement) SourceTreeToPsiMap.treeElementToPsi(compositeElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public boolean isQualified() {
        return findChildByRole(54) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        return getCanonicalText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    /* JADX INFO: renamed from: getReferenceName */
    public String getCustomReferenceName() {
        ASTNode aSTNodeFindChildByRole = findChildByRole(53);
        if (aSTNodeFindChildByRole == null) {
            return "";
        }
        return aSTNodeFindChildByRole.getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        TreeElement treeElement = (TreeElement) findChildByRole(53);
        if (treeElement == null) {
            return new TextRange(0, getTextLength());
        }
        int startOffsetInParent = treeElement.getStartOffsetInParent();
        return new TextRange(startOffsetInParent, treeElement.getTextLength() + startOffsetInParent);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String strCalcCanonicalText = this.myCanonicalText;
        if (strCalcCanonicalText == null) {
            strCalcCanonicalText = calcCanonicalText();
            this.myCanonicalText = strCalcCanonicalText;
        }
        if (strCalcCanonicalText == null) {
            $$$reportNull$$$0(3);
        }
        return strCalcCanonicalText;
    }

    private String calcCanonicalText() {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) getQualifier();
        if (psiJavaCodeReferenceElement == null) {
            return getCustomReferenceName();
        }
        return psiJavaCodeReferenceElement.getCanonicalText() + "." + getCustomReferenceName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiImportStaticReferenceElement:" + getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        JavaResolveResult[] javaResolveResultArrMultiResolve = multiResolve(z);
        if (javaResolveResultArrMultiResolve.length == 1) {
            JavaResolveResult javaResolveResult = javaResolveResultArrMultiResolve[0];
            if (javaResolveResult == null) {
                $$$reportNull$$$0(4);
            }
            return javaResolveResult;
        }
        JavaResolveResult javaResolveResult2 = JavaResolveResult.EMPTY;
        if (javaResolveResult2 == null) {
            $$$reportNull$$$0(5);
        }
        return javaResolveResult2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        PsiFile containingFile = getContainingFile();
        ResolveResult[] resolveResultArrResolveWithCaching = ResolveCache.getInstance(containingFile.getProject()).resolveWithCaching(this, (ResolveCache.PolyVariantResolver<PsiImportStaticReferenceElementImpl>) OurGenericsResolver.INSTANCE, false, z, containingFile);
        JavaResolveResult[] javaResolveResultArr = resolveResultArrResolveWithCaching instanceof JavaResolveResult[] ? (JavaResolveResult[]) resolveResultArrResolveWithCaching : JavaResolveResult.EMPTY_ARRAY;
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(6);
        }
        return javaResolveResultArr;
    }

    private static final class OurGenericsResolver implements ResolveCache.PolyVariantResolver<PsiImportStaticReferenceElementImpl> {
        private static final OurGenericsResolver INSTANCE = new OurGenericsResolver();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
            if (i == 1 || i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImportStaticReferenceElementImpl$OurGenericsResolver";
            } else {
                objArr[0] = "referenceElement";
            }
            if (i == 1 || i == 2 || i == 3) {
                objArr[1] = "resolve";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImportStaticReferenceElementImpl$OurGenericsResolver";
            }
            if (i != 1 && i != 2 && i != 3) {
                objArr[2] = "resolve";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        private OurGenericsResolver() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.PolyVariantResolver
        public JavaResolveResult[] resolve(PsiImportStaticReferenceElementImpl psiImportStaticReferenceElementImpl, boolean z) {
            if (psiImportStaticReferenceElementImpl == null) {
                $$$reportNull$$$0(0);
            }
            PsiElement qualifier = psiImportStaticReferenceElementImpl.getQualifier();
            if (!(qualifier instanceof PsiJavaCodeReferenceElement)) {
                JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
                if (javaResolveResultArr == null) {
                    $$$reportNull$$$0(1);
                }
                return javaResolveResultArr;
            }
            PsiElement psiElementResolve = ((PsiJavaCodeReferenceElement) qualifier).resolve();
            if (!(psiElementResolve instanceof PsiClass)) {
                JavaResolveResult[] javaResolveResultArr2 = JavaResolveResult.EMPTY_ARRAY;
                if (javaResolveResultArr2 == null) {
                    $$$reportNull$$$0(2);
                }
                return javaResolveResultArr2;
            }
            StaticImportResolveProcessor staticImportResolveProcessor = new StaticImportResolveProcessor(psiImportStaticReferenceElementImpl);
            psiElementResolve.processDeclarations(staticImportResolveProcessor, ResolveState.initial(), psiImportStaticReferenceElementImpl, psiImportStaticReferenceElementImpl);
            JavaResolveResult[] results = staticImportResolveProcessor.getResults();
            if (results == null) {
                $$$reportNull$$$0(3);
            }
            return results;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        return advancedResolve(false).getElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        String customReferenceName = getCustomReferenceName();
        if (customReferenceName != null && (psiElement instanceof PsiNamedElement) && customReferenceName.equals(((PsiNamedElement) psiElement).get$name())) {
            for (JavaResolveResult javaResolveResult : multiResolve(false)) {
                if (getManager().areElementsEquivalent(javaResolveResult.getElement(), psiElement)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        PsiElement psiElementFindChildByRoleAsPsiElement = findChildByRoleAsPsiElement(53);
        if (psiElementFindChildByRoleAsPsiElement == null) {
            throw new IncorrectOperationException();
        }
        psiElementFindChildByRoleAsPsiElement.replace(JavaPsiFacade.getElementFactory(getProject()).createIdentifier(str));
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        if ((psiElement instanceof PsiMember) && (psiElement instanceof PsiNamedElement)) {
            PsiNamedElement psiNamedElement = (PsiNamedElement) psiElement;
            if (psiNamedElement.get$name() != null) {
                if (!((PsiModifierListOwner) psiElement).hasModifierProperty("static")) {
                    if (psiElement instanceof PsiClass) {
                        PsiClass psiClass = (PsiClass) psiElement;
                        if (psiClass.getContainingClass() == null) {
                            return replaceWithRegularImport(psiClass);
                        }
                    }
                    throw new IncorrectOperationException();
                }
                PsiClass psiClassMo6092getContainingClass = ((PsiMember) psiElement).getContainingClass();
                if (psiClassMo6092getContainingClass == null) {
                    throw new IncorrectOperationException();
                }
                PsiElement qualifier = getQualifier();
                if (qualifier == null) {
                    throw new IncorrectOperationException();
                }
                ((PsiReference) qualifier).bindToElement(psiClassMo6092getContainingClass);
                PsiElement psiElementFindChildByRoleAsPsiElement = findChildByRoleAsPsiElement(53);
                if (psiElementFindChildByRoleAsPsiElement == null) {
                    throw new IncorrectOperationException();
                }
                psiElementFindChildByRoleAsPsiElement.replace(JavaPsiFacade.getElementFactory(getProject()).createIdentifier(psiNamedElement.get$name()));
                return this;
            }
        }
        throw new IncorrectOperationException();
    }

    private PsiElement replaceWithRegularImport(PsiClass psiClass) throws IncorrectOperationException {
        return ((PsiImportStatement) ((PsiImportStaticStatement) PsiTreeUtil.getParentOfType(getPsiElement(), PsiImportStaticStatement.class)).replace(JavaPsiFacade.getElementFactory(getProject()).createImportStatement(psiClass))).getImportReference();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public void processVariants(PsiScopeProcessor psiScopeProcessor) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(10);
        }
        PsiScopesUtil.resolveAndWalk(new FilterScopeProcessor(new ClassFilter(PsiModifierListOwner.class), psiScopeProcessor), this, null, true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(11);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitImportStaticReferenceElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }
}
