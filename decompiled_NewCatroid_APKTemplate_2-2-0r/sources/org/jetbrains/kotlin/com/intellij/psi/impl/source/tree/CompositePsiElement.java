package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.ide.util.PsiNavigationSupport;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.project.ProjectCoreUtil;
import org.jetbrains.kotlin.com.intellij.pom.Navigatable;
import org.jetbrains.kotlin.com.intellij.psi.NavigatablePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.SharedPsiElementImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CompositePsiElement extends CompositeElement implements NavigatablePsiElement {
    private static final Logger LOG = Logger.getInstance((Class<?>) CompositePsiElement.class);
    protected static int ourHC;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 3:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                i2 = 3;
                break;
            case 3:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 11:
                objArr[0] = "visitor";
                break;
            case 3:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/CompositePsiElement";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 8:
                objArr[0] = "first";
                break;
            case 9:
                objArr[0] = "last";
                break;
            case 10:
                objArr[0] = "newElement";
                break;
            case 12:
                objArr[0] = "processor";
                break;
            case 13:
                objArr[0] = "state";
                break;
            case 14:
                objArr[0] = "place";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getChildren";
                break;
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/CompositePsiElement";
                break;
            case 3:
                objArr[1] = "getReferences";
                break;
            case 15:
                objArr[1] = "getNavigationElement";
                break;
            case 16:
                objArr[1] = "getResolveScope";
                break;
            case 17:
                objArr[1] = "getUseScope";
                break;
            case 18:
            case 19:
                objArr[1] = "getProject";
                break;
            case 20:
                objArr[1] = "getLanguage";
                break;
            case 21:
                objArr[1] = "getNode";
                break;
            default:
                objArr[1] = "clone";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "acceptChildren";
                break;
            case 4:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 5:
                objArr[2] = "addBefore";
                break;
            case 6:
                objArr[2] = "addAfter";
                break;
            case 7:
                objArr[2] = "checkAdd";
                break;
            case 8:
            case 9:
                objArr[2] = "addRangeBefore";
                break;
            case 10:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 11:
                objArr[2] = "accept";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "processDeclarations";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                throw new IllegalArgumentException(str2);
            case 3:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public String getName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public ASTNode getNode() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getOriginalElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return null;
    }

    public PsiReference getReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return this == psiElement;
    }

    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(12);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElement2 != null) {
            return true;
        }
        $$$reportNull$$$0(14);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ PsiManager getManager() {
        return super.getManager();
    }

    protected CompositePsiElement(IElementType iElementType) {
        super(iElementType);
        setPsi(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public CompositePsiElement clone() {
        CompositePsiElement compositePsiElement = (CompositePsiElement) super.clone();
        compositePsiElement.setPsi(compositePsiElement);
        if (compositePsiElement == null) {
            $$$reportNull$$$0(0);
        }
        return compositePsiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] childrenAsPsiElements = getChildrenAsPsiElements((TokenSet) null, PsiElement.ARRAY_FACTORY);
        if (childrenAsPsiElements == null) {
            $$$reportNull$$$0(1);
        }
        return childrenAsPsiElements;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getFirstChild() {
        TreeElement firstChildNode = getFirstChildNode();
        if (firstChildNode != null) {
            return firstChildNode.getPsi();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getLastChild() {
        TreeElement lastChildNode = getLastChildNode();
        if (lastChildNode != null) {
            return lastChildNode.getPsi();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void acceptChildren(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            firstChild.accept(psiElementVisitor);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        CompositeElement treeParent = getTreeParent();
        if (treeParent != null) {
            return treeParent.getPsi();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNextSibling() {
        TreeElement treeNext = getTreeNext();
        if (treeNext != null) {
            return treeNext.getPsi();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getPrevSibling() {
        TreeElement treePrev = getTreePrev();
        if (treePrev != null) {
            return treePrev.getPsi();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = SharedImplUtil.getContainingFile(this);
        if (containingFile != null) {
            return containingFile;
        }
        throw new PsiInvalidElementAccessException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int i) {
        return SourceTreeToPsiMap.treeElementToPsi(findLeafElementAt(i));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference findReferenceAt(int i) {
        return SharedPsiElementImplUtil.findReferenceAt(this, i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return SourceTreeToPsiMap.treeElementToPsi(copyElement());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return SharedImplUtil.isValid(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return SharedImplUtil.isWritable(this);
    }

    public PsiReference[] getReferences() {
        PsiReference[] references = SharedPsiElementImplUtil.getReferences(this);
        if (references == null) {
            $$$reportNull$$$0(3);
        }
        return references;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        return addInnerBefore(psiElement, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        return addInnerBefore(psiElement, psiElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        return ChangeUtil.decodeInformation(addInternal(treeElementCopyToElement, treeElementCopyToElement, SourceTreeToPsiMap.psiElementToTree(psiElement2), Boolean.FALSE)).getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final PsiElement addRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        return SharedImplUtil.addRange(this, psiElement, psiElement2, null, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final PsiElement addRangeBefore(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(9);
        }
        return SharedImplUtil.addRange(this, psiElement, psiElement2, SourceTreeToPsiMap.psiElementToTree(psiElement3), Boolean.TRUE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        return SharedImplUtil.addRange(this, psiElement, psiElement2, SourceTreeToPsiMap.psiElementToTree(psiElement3), Boolean.FALSE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        LOG.assertTrue(getTreeParent() != null, "Parent not found for " + this);
        CheckUtil.checkWritable(this);
        getTreeParent().deleteChildInternal(this);
        invalidate();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkDelete() throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void deleteChildRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        ASTNode node = psiElement.getNode();
        ASTNode node2 = psiElement2.getNode();
        Logger logger = LOG;
        logger.assertTrue(node.getTreeParent() == this);
        logger.assertTrue(node2.getTreeParent() == this);
        CodeEditUtil.removeChildren(this, node, node2);
    }

    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        return SharedImplUtil.doReplace(this, this, psiElement);
    }

    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(11);
        }
        psiElementVisitor.visitElement(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiElement(" + getElementType() + Constant.AFTER_QUTO;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return getLightParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        PsiFile containingFile = getContainingFile();
        return containingFile != null && containingFile.getIsPhysical();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope elementResolveScope = ResolveScopeManager.getElementResolveScope(this);
        if (elementResolveScope == null) {
            $$$reportNull$$$0(16);
        }
        return elementResolveScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        GlobalSearchScope elementUseScope = ResolveScopeManager.getElementUseScope(this);
        if (elementUseScope == null) {
            $$$reportNull$$$0(17);
        }
        return elementUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public void navigate(boolean z) {
        Navigatable descriptor = PsiNavigationSupport.getInstance().getDescriptor(this);
        if (descriptor != null) {
            descriptor.navigate(z);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigate() {
        return PsiNavigationSupport.getInstance().canNavigate(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigateToSource() {
        return canNavigate();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Project getProject() {
        Project projectTheOnlyOpenProject = ProjectCoreUtil.theOnlyOpenProject();
        if (projectTheOnlyOpenProject != null) {
            if (projectTheOnlyOpenProject == null) {
                $$$reportNull$$$0(18);
            }
            return projectTheOnlyOpenProject;
        }
        PsiManagerEx manager = getManager();
        if (manager == null) {
            throw new PsiInvalidElementAccessException(this);
        }
        Project project = manager.getProject();
        if (project == null) {
            $$$reportNull$$$0(19);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        Language language = getElementType().getLanguage();
        if (language == null) {
            $$$reportNull$$$0(20);
        }
        return language;
    }

    private PsiElement addInnerBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        TreeElement treeElementAddInternal = addInternal(treeElementCopyToElement, treeElementCopyToElement, SourceTreeToPsiMap.psiElementToTree(psiElement2), Boolean.TRUE);
        if (treeElementAddInternal != null) {
            return ChangeUtil.decodeInformation(treeElementAddInternal).getPsi();
        }
        throw new IncorrectOperationException("Element cannot be added");
    }
}
