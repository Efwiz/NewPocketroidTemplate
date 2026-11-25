package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
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
public class OwnBufferLeafPsiElement extends LeafElement implements PsiElement {
    private static final Logger LOG = Logger.getInstance((Class<?>) LeafPsiElement.class);

    /* JADX WARN: Code duplicated, block: B:7:0x000c  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 4) {
            switch (i) {
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
        if (i != 2 && i != 4) {
            switch (i) {
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
                objArr[0] = "text";
                break;
            case 2:
            case 4:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/OwnBufferLeafPsiElement";
                break;
            case 3:
            case 12:
                objArr[0] = "visitor";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 9:
                objArr[0] = "first";
                break;
            case 10:
                objArr[0] = "last";
                break;
            case 11:
                objArr[0] = "newElement";
                break;
            case 13:
                objArr[0] = "processor";
                break;
            case 14:
                objArr[0] = "state";
                break;
            case 15:
                objArr[0] = "place";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 2) {
            objArr[1] = "getChildren";
        } else if (i != 4) {
            switch (i) {
                case 16:
                    objArr[1] = "getResolveScope";
                    break;
                case 17:
                    objArr[1] = "getUseScope";
                    break;
                case 18:
                    objArr[1] = "getProject";
                    break;
                case 19:
                    objArr[1] = "getLanguage";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/OwnBufferLeafPsiElement";
                    break;
            }
        } else {
            objArr[1] = "getReferences";
        }
        switch (i) {
            case 2:
            case 4:
            case 16:
            case 17:
            case 18:
            case 19:
                break;
            case 3:
                objArr[2] = "acceptChildren";
                break;
            case 5:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 6:
                objArr[2] = "addBefore";
                break;
            case 7:
                objArr[2] = "addAfter";
                break;
            case 8:
                objArr[2] = "checkAdd";
                break;
            case 9:
            case 10:
                objArr[2] = "addRangeBefore";
                break;
            case 11:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 12:
                objArr[2] = "accept";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "processDeclarations";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            switch (i) {
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
            $$$reportNull$$$0(3);
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
            $$$reportNull$$$0(13);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(14);
        }
        if (psiElement2 != null) {
            return true;
        }
        $$$reportNull$$$0(15);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ PsiManager getManager() {
        return super.getManager();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OwnBufferLeafPsiElement(IElementType iElementType, CharSequence charSequence) {
        super(iElementType, charSequence);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
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
            throw new PsiInvalidElementAccessException(this);
        }
        return containingFile;
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
            $$$reportNull$$$0(4);
        }
        return references;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
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
            $$$reportNull$$$0(9);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(10);
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
            $$$reportNull$$$0(11);
        }
        LOG.assertTrue(getTreeParent() != null);
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        getTreeParent().replaceChildInternal(this, treeElementCopyToElement);
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(ChangeUtil.decodeInformation(treeElementCopyToElement));
        invalidate();
        return psiElementTreeElementToPsi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiElement(" + getElementType().toString() + Constant.AFTER_QUTO;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(12);
        }
        psiElementVisitor.visitElement(this);
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Project getProject() {
        PsiManagerEx manager = getManager();
        if (manager == null) {
            throw new PsiInvalidElementAccessException(this);
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
}
