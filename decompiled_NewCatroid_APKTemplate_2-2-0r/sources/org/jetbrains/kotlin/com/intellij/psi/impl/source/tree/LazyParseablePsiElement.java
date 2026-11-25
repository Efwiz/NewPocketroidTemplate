package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.ide.util.PsiNavigationSupport;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.NavigationItem;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.project.ProjectCoreUtil;
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
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LazyParseablePsiElement extends LazyParseableElement implements NavigationItem, PsiElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) LazyParseablePsiElement.class);

    /* JADX WARN: Code duplicated, block: B:9:0x0012  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 2 && i != 3 && i != 5) {
            switch (i) {
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 2 && i != 3 && i != 5) {
            switch (i) {
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
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
            case 2:
            case 3:
            case 5:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LazyParseablePsiElement";
                break;
            case 4:
            case 13:
                objArr[0] = "visitor";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 10:
                objArr[0] = "first";
                break;
            case 11:
                objArr[0] = "last";
                break;
            case 12:
                objArr[0] = "newElement";
                break;
            case 14:
                objArr[0] = "processor";
                break;
            case 15:
                objArr[0] = "state";
                break;
            case 16:
                objArr[0] = "place";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 1) {
            objArr[1] = "clone";
        } else if (i == 2) {
            objArr[1] = "getChildren";
        } else if (i == 3) {
            objArr[1] = "findChildrenByClass";
        } else if (i != 5) {
            switch (i) {
                case 17:
                    objArr[1] = "getNavigationElement";
                    break;
                case 18:
                    objArr[1] = "getResolveScope";
                    break;
                case 19:
                    objArr[1] = "getUseScope";
                    break;
                case 20:
                case 21:
                    objArr[1] = "getProject";
                    break;
                case 22:
                    objArr[1] = "getLanguage";
                    break;
                case 23:
                    objArr[1] = "getNode";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LazyParseablePsiElement";
                    break;
            }
        } else {
            objArr[1] = "getReferences";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                break;
            case 4:
                objArr[2] = "acceptChildren";
                break;
            case 6:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 7:
                objArr[2] = "addBefore";
                break;
            case 8:
                objArr[2] = "addAfter";
                break;
            case 9:
                objArr[2] = "checkAdd";
                break;
            case 10:
            case 11:
                objArr[2] = "addRangeBefore";
                break;
            case 12:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 13:
                objArr[2] = "accept";
                break;
            case 14:
            case 15:
            case 16:
                objArr[2] = "processDeclarations";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 5) {
            switch (i) {
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return this == psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(14);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(15);
        }
        if (psiElement2 != null) {
            return true;
        }
        $$$reportNull$$$0(16);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ PsiManager getManager() {
        return super.getManager();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyParseablePsiElement(IElementType iElementType, CharSequence charSequence) {
        super(iElementType, charSequence);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        setPsi(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public LazyParseablePsiElement clone() {
        LazyParseablePsiElement lazyParseablePsiElement = (LazyParseablePsiElement) super.clone();
        lazyParseablePsiElement.setPsi(lazyParseablePsiElement);
        if (lazyParseablePsiElement == null) {
            $$$reportNull$$$0(1);
        }
        return lazyParseablePsiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] childrenAsPsiElements = getChildrenAsPsiElements((TokenSet) null, PsiElement.ARRAY_FACTORY);
        if (childrenAsPsiElements == null) {
            $$$reportNull$$$0(2);
        }
        return childrenAsPsiElements;
    }

    protected <T> T findChildByClass(Class<T> cls) {
        for (T t = (T) getFirstChild(); t != null; t = (T) ((PsiElement) t).getNextSibling()) {
            if (cls.isInstance(t)) {
                return t;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T> T[] findChildrenByClass(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (cls.isInstance(firstChild)) {
                arrayList.add(firstChild);
            }
        }
        T[] tArr = (T[]) arrayList.toArray(ArrayUtil.newArray(cls, arrayList.size()));
        if (tArr == null) {
            $$$reportNull$$$0(3);
        }
        return tArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getFirstChild() {
        TreeElement firstChildNode = getFirstChildNode();
        if (firstChildNode == null) {
            return null;
        }
        return firstChildNode.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getLastChild() {
        TreeElement lastChildNode = getLastChildNode();
        if (lastChildNode == null) {
            return null;
        }
        return lastChildNode.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void acceptChildren(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            firstChild.accept(psiElementVisitor);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        ASTNode treeParent = getTreeParent();
        if (treeParent == null) {
            return null;
        }
        return treeParent instanceof PsiElement ? (PsiElement) treeParent : treeParent.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNextSibling() {
        return SharedImplUtil.getNextSibling(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getPrevSibling() {
        return SharedImplUtil.getPrevSibling(this);
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        PsiReference[] references = SharedPsiElementImplUtil.getReferences(this);
        if (references == null) {
            $$$reportNull$$$0(5);
        }
        return references;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        return addInnerBefore(psiElement, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        return addInnerBefore(psiElement, psiElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        return ChangeUtil.decodeInformation(addInternal(treeElementCopyToElement, treeElementCopyToElement, SourceTreeToPsiMap.psiElementToTree(psiElement2), Boolean.FALSE)).getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
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
            $$$reportNull$$$0(10);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(11);
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
        ASTNode aSTNodePsiElementToTree = SourceTreeToPsiMap.psiElementToTree(psiElement);
        ASTNode aSTNodePsiElementToTree2 = SourceTreeToPsiMap.psiElementToTree(psiElement2);
        Logger logger = LOG;
        logger.assertTrue(aSTNodePsiElementToTree.getTreeParent() == this);
        logger.assertTrue(aSTNodePsiElementToTree2.getTreeParent() == this);
        CodeEditUtil.removeChildren(this, aSTNodePsiElementToTree, aSTNodePsiElementToTree2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        return SharedImplUtil.doReplace(this, this, psiElement);
    }

    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(13);
        }
        psiElementVisitor.visitElement(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiElement(" + getElementType() + Constant.AFTER_QUTO;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return getMyParent();
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
            $$$reportNull$$$0(18);
        }
        return elementResolveScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        GlobalSearchScope elementUseScope = ResolveScopeManager.getElementUseScope(this);
        if (elementUseScope == null) {
            $$$reportNull$$$0(19);
        }
        return elementUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public void navigate(boolean z) {
        PsiNavigationSupport.getInstance().getDescriptor(this).navigate(z);
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
                $$$reportNull$$$0(20);
            }
            return projectTheOnlyOpenProject;
        }
        PsiManagerEx manager = getManager();
        if (manager == null) {
            throw new PsiInvalidElementAccessException(this);
        }
        Project project = manager.getProject();
        if (project == null) {
            $$$reportNull$$$0(21);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        Language language = getElementType().getLanguage();
        if (language == null) {
            $$$reportNull$$$0(22);
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
