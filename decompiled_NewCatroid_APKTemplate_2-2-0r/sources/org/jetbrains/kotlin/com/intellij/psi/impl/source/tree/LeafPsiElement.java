package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.ide.util.PsiNavigationSupport;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.NavigationItem;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.project.ProjectCoreUtil;
import org.jetbrains.kotlin.com.intellij.pom.Navigatable;
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
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LeafPsiElement extends LeafElement implements NavigationItem, PsiElement {
    private static final Logger LOG = Logger.getInstance((Class<?>) LeafPsiElement.class);

    /* JADX WARN: Code duplicated, block: B:7:0x000c  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 3) {
            switch (i) {
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
        if (i != 1 && i != 3) {
            switch (i) {
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
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LeafPsiElement";
                break;
            case 2:
            case 11:
                objArr[0] = "visitor";
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
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 1) {
            objArr[1] = "getChildren";
        } else if (i != 3) {
            switch (i) {
                case 15:
                    objArr[1] = "getResolveScope";
                    break;
                case 16:
                    objArr[1] = "getUseScope";
                    break;
                case 17:
                case 18:
                    objArr[1] = "getProject";
                    break;
                case 19:
                    objArr[1] = "getLanguage";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LeafPsiElement";
                    break;
            }
        } else {
            objArr[1] = "getReferences";
        }
        switch (i) {
            case 1:
            case 3:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                break;
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
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            switch (i) {
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void acceptChildren(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int i) {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getFirstChild() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getLastChild() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public PsiElement getPsi() {
        return this;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LeafPsiElement(IElementType iElementType, CharSequence charSequence) {
        super(iElementType, charSequence);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getParent() {
        return SharedImplUtil.getParent(this);
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
        if (containingFile == null || !containingFile.isValid()) {
            invalid();
        }
        return containingFile;
    }

    private void invalid() {
        ProgressIndicatorProvider.checkCanceled();
        StringBuilder sb = new StringBuilder();
        for (ASTNode treeParent = this; treeParent != null; treeParent = treeParent.getTreeParent()) {
            if (treeParent != this) {
                sb.append(" / ");
            }
            sb.append(treeParent.getClass().getName()).append(':').append(treeParent.getElementType());
        }
        throw new PsiInvalidElementAccessException((PsiElement) this, sb.toString());
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
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRangeBefore(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(9);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        LOG.assertTrue(getTreeParent() != null);
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
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        return SharedImplUtil.doReplace(this, this, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiElement(" + getElementType().toString() + Constant.AFTER_QUTO;
    }

    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(11);
        }
        psiElementVisitor.visitElement(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return getParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isPhysical() {
        PsiFile containingFile = getContainingFile();
        return containingFile != null && containingFile.isPhysical();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope elementResolveScope = ResolveScopeManager.getElementResolveScope(this);
        if (elementResolveScope == null) {
            $$$reportNull$$$0(15);
        }
        return elementResolveScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        GlobalSearchScope elementUseScope = ResolveScopeManager.getElementUseScope(this);
        if (elementUseScope == null) {
            $$$reportNull$$$0(16);
        }
        return elementUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Project getProject() {
        Project projectTheOnlyOpenProject = ProjectCoreUtil.theOnlyOpenProject();
        if (projectTheOnlyOpenProject != null) {
            if (projectTheOnlyOpenProject == null) {
                $$$reportNull$$$0(17);
            }
            return projectTheOnlyOpenProject;
        }
        PsiManagerEx manager = getManager();
        if (manager == null) {
            invalid();
        }
        Project project = manager.getProject();
        if (project == null) {
            $$$reportNull$$$0(18);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        Language language = getElementType().getLanguage();
        if (language == null) {
            $$$reportNull$$$0(19);
        }
        return language;
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
}
