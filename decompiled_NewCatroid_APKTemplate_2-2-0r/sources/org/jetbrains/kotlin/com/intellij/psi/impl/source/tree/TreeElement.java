package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.project.ProjectCoreUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.ReparseableASTNode;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.testFramework.ReadOnlyLightVirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TreeElement extends ElementBase implements ASTNode, ReparseableASTNode, Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final TreeElement[] EMPTY_ARRAY = new TreeElement[0];
    private TreeElement myNextSibling;
    private CompositeElement myParent;
    private TreeElement myPrevSibling;
    private volatile int myStartOffsetInParent;
    private final IElementType myType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "each";
                break;
            case 2:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/TreeElement";
                break;
            case 3:
                objArr[0] = "buffer";
                break;
            case 4:
                objArr[0] = "seq";
                break;
            case 5:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 6:
                objArr[0] = "newChild";
                break;
            case 7:
            case 8:
            case 9:
                objArr[0] = "firstNew";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 2) {
            objArr[1] = "clone";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/TreeElement";
        } else {
            objArr[1] = "getElementType";
        }
        switch (i) {
            case 1:
                objArr[2] = "getCachedFile";
                break;
            case 2:
            case 10:
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "textMatches";
                break;
            case 6:
                objArr[2] = "applyReplaceOnReparse";
                break;
            case 7:
                objArr[2] = "rawInsertBeforeMe";
                break;
            case 8:
                objArr[2] = "rawInsertAfterMe";
                break;
            case 9:
                objArr[2] = "rawInsertAfterMeWithoutNotifications";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract void acceptTree(TreeElementVisitor treeElementVisitor);

    public void clearCaches() {
    }

    public final boolean equals(Object obj) {
        return obj == this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public abstract LeafElement findLeafElementAt(int i);

    public abstract int getCachedLength();

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public abstract TreeElement getFirstChildNode();

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public abstract TreeElement getLastChildNode();

    public abstract int hc();

    protected abstract int textMatches(CharSequence charSequence, int i);

    public abstract char[] textToCharArray();

    public TreeElement(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        this.myStartOffsetInParent = -1;
        this.myType = iElementType;
    }

    private static PsiFileImpl getCachedFile(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(1);
        }
        FileElement fileElement = (FileElement) SharedImplUtil.findFileElement(treeElement);
        if (fileElement == null) {
            return null;
        }
        return (PsiFileImpl) fileElement.getCachedPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public Object clone() {
        TreeElement treeElement = (TreeElement) super.clone();
        treeElement.myNextSibling = null;
        treeElement.myPrevSibling = null;
        treeElement.myParent = null;
        treeElement.myStartOffsetInParent = -1;
        if (treeElement == null) {
            $$$reportNull$$$0(2);
        }
        return treeElement;
    }

    public ASTNode copyElement() {
        return ChangeUtil.copyElement(this, SharedImplUtil.findCharTableByTree(this));
    }

    public PsiManagerEx getManager() {
        Project projectTheOnlyOpenProject = ProjectCoreUtil.theOnlyOpenProject();
        if (projectTheOnlyOpenProject != null) {
            return PsiManagerEx.getInstanceEx(projectTheOnlyOpenProject);
        }
        TreeElement treeElement = this;
        while (true) {
            CompositeElement treeParent = treeElement.getTreeParent();
            if (treeParent == null) {
                break;
            }
            treeElement = treeParent;
        }
        if (treeElement instanceof FileElement) {
            return treeElement.getManager();
        }
        CompositeElement treeParent2 = getTreeParent();
        if (treeParent2 != null) {
            return treeParent2.getManager();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public TextRange getTextRange() {
        int startOffset = getStartOffset();
        return new TextRange(startOffset, getTextLength() + startOffset);
    }

    public int getStartOffset() {
        int startOffsetInParent = 0;
        for (TreeElement treeElement = this; treeElement.myParent != null; treeElement = treeElement.myParent) {
            startOffsetInParent += treeElement.getStartOffsetInParent();
        }
        return startOffsetInParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public final int getStartOffsetInParent() {
        if (this.myParent == null) {
            return -1;
        }
        int textLength = this.myStartOffsetInParent;
        if (textLength != -1) {
            return textLength;
        }
        if (DebugUtil.CHECK_INSIDE_ATOMIC_ACTION_ENABLED) {
            assertReadAccessAllowed();
        }
        TreeElement treeElement = this;
        while (true) {
            TreeElement treePrev = treeElement.getTreePrev();
            if (treePrev == null) {
                break;
            }
            textLength = treePrev.myStartOffsetInParent;
            if (textLength != -1) {
                treeElement = treePrev;
                break;
            }
            treeElement = treePrev;
        }
        if (textLength == -1) {
            textLength = 0;
            treeElement.myStartOffsetInParent = 0;
        }
        while (treeElement != this) {
            TreeElement treeNext = treeElement.getTreeNext();
            textLength += treeElement.getTextLength();
            treeNext.myStartOffsetInParent = textLength;
            treeElement = treeNext;
        }
        return textLength;
    }

    public int getTextOffset() {
        return getStartOffset();
    }

    public boolean textMatches(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(3);
        }
        return textMatches(charSequence, i) == i2;
    }

    public boolean textMatches(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        return textMatches(charSequence, 0, charSequence.length());
    }

    public boolean textMatches(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        return getTextLength() == psiElement.getTextLength() && textMatches(psiElement.getText());
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "Element(" + getElementType() + Constant.AFTER_QUTO;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public final CompositeElement getTreeParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public final TreeElement getTreePrev() {
        return this.myPrevSibling;
    }

    final void setTreeParent(CompositeElement compositeElement) {
        if (compositeElement == this.myParent) {
            return;
        }
        PsiFileImpl cachedFile = getCachedFile(this);
        if (cachedFile != null) {
            cachedFile.beforeAstChange();
        }
        this.myParent = compositeElement;
        if (compositeElement == null || compositeElement.getElementType() == TokenType.DUMMY_HOLDER) {
            return;
        }
        DebugUtil.revalidateNode(this);
    }

    final void setTreePrev(TreeElement treeElement) {
        this.myPrevSibling = treeElement;
        clearRelativeOffsets(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public final TreeElement getTreeNext() {
        return this.myNextSibling;
    }

    final void setTreeNext(TreeElement treeElement) {
        this.myNextSibling = treeElement;
        clearRelativeOffsets(treeElement);
    }

    static void clearRelativeOffsets(TreeElement treeElement) {
        while (treeElement != null && treeElement.myStartOffsetInParent != -1) {
            treeElement.myStartOffsetInParent = -1;
            treeElement = treeElement.getTreeNext();
        }
    }

    protected void onInvalidated() {
        DebugUtil.onInvalidated(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ReparseableASTNode
    public final void applyReplaceOnReparse(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        TreeElement treeElement = (TreeElement) aSTNode;
        treeElement.rawRemove();
        rawReplaceWithList(treeElement);
        treeElement.clearCaches();
        if (treeElement instanceof FileElement) {
            return;
        }
        treeElement.getTreeParent().subtreeChanged();
    }

    public void rawInsertBeforeMe(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(7);
        }
        TreeElement treePrev = getTreePrev();
        if (treePrev == null) {
            treeElement.rawRemoveUpToLast();
            CompositeElement treeParent = getTreeParent();
            if (treeParent != null) {
                treeParent.setFirstChildNode(treeElement);
            }
            while (true) {
                TreeElement treeNext = treeElement.getTreeNext();
                treeElement.setTreeParent(treeParent);
                if (treeNext == null) {
                    break;
                } else {
                    treeElement = treeNext;
                }
            }
            setTreePrev(treeElement);
            treeElement.setTreeNext(this);
            if (treeParent != null) {
                treeParent.subtreeChanged();
            }
        } else {
            treePrev.rawInsertAfterMe(treeElement);
        }
        DebugUtil.checkTreeStructure(this);
    }

    public void rawInsertAfterMe(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(8);
        }
        rawInsertAfterMeWithoutNotifications(treeElement);
        CompositeElement treeParent = getTreeParent();
        if (treeParent != null) {
            treeParent.subtreeChanged();
        }
    }

    final void rawInsertAfterMeWithoutNotifications(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(9);
        }
        treeElement.rawRemoveUpToWithoutNotifications(null, false);
        CompositeElement treeParent = getTreeParent();
        TreeElement treeNext = getTreeNext();
        treeElement.setTreePrev(this);
        setTreeNext(treeElement);
        while (true) {
            TreeElement treeNext2 = treeElement.getTreeNext();
            treeElement.setTreeParent(treeParent);
            if (treeNext2 == null) {
                break;
            } else {
                treeElement = treeNext2;
            }
        }
        if (treeNext != null) {
            treeElement.setTreeNext(treeNext);
            treeNext.setTreePrev(treeElement);
        } else if (treeParent != null) {
            treeElement.setTreeParent(treeParent);
            treeParent.setLastChildNode(treeElement);
        }
        DebugUtil.checkTreeStructure(this);
    }

    public void rawRemove() {
        TreeElement treeNext = getTreeNext();
        CompositeElement treeParent = getTreeParent();
        TreeElement treePrev = getTreePrev();
        if (treePrev != null) {
            treePrev.setTreeNext(treeNext);
        } else if (treeParent != null) {
            treeParent.setFirstChildNode(treeNext);
        }
        if (treeNext != null) {
            treeNext.setTreePrev(treePrev);
        } else if (treeParent != null) {
            treeParent.setLastChildNode(treePrev);
        }
        DebugUtil.checkTreeStructure(treeParent);
        DebugUtil.checkTreeStructure(treePrev);
        DebugUtil.checkTreeStructure(treeNext);
        invalidate();
    }

    public void rawReplaceWithList(TreeElement treeElement) {
        if (treeElement != null) {
            rawInsertAfterMeWithoutNotifications(treeElement);
        }
        rawRemove();
    }

    protected void invalidate() {
        CompositeElement treeParent = getTreeParent();
        if (treeParent != null) {
            treeParent.subtreeChanged();
        }
        onInvalidated();
        setTreeNext(null);
        setTreePrev(null);
        setTreeParent(null);
    }

    public void rawRemoveUpToLast() {
        rawRemoveUpTo(null);
    }

    public void rawRemoveUpTo(TreeElement treeElement) {
        CompositeElement treeParent = getTreeParent();
        rawRemoveUpToWithoutNotifications(treeElement, true);
        if (treeParent != null) {
            treeParent.subtreeChanged();
        }
    }

    final void rawRemoveUpToWithoutNotifications(TreeElement treeElement, boolean z) {
        if (this == treeElement) {
            return;
        }
        CompositeElement treeParent = getTreeParent();
        TreeElement treePrev = getTreePrev();
        TreeElement treePrev2 = treeElement != null ? treeElement.getTreePrev() : null;
        if (treeElement != null) {
            for (TreeElement treeNext = this; treeNext != treeElement && treeNext != null; treeNext = treeNext.getTreeNext()) {
            }
        }
        if (treeParent != null) {
            if (getTreePrev() == null) {
                treeParent.setFirstChildNode(treeElement);
            }
            if (treeElement == null) {
                treeParent.setLastChildNode(treePrev);
            }
        }
        if (treePrev != null) {
            treePrev.setTreeNext(treeElement);
        }
        if (treeElement != null) {
            treeElement.setTreePrev(treePrev);
        }
        setTreePrev(null);
        if (treePrev2 != null) {
            treePrev2.setTreeNext(null);
        }
        if (treeParent != null) {
            for (TreeElement treeNext2 = this; treeNext2 != null; treeNext2 = treeNext2.getTreeNext()) {
                treeNext2.setTreeParent(null);
                if (z) {
                    treeNext2.onInvalidated();
                }
            }
        }
        DebugUtil.checkTreeStructure(treeParent);
        DebugUtil.checkTreeStructure(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public IElementType getElementType() {
        IElementType iElementType = this.myType;
        if (iElementType == null) {
            $$$reportNull$$$0(10);
        }
        return iElementType;
    }

    void assertReadAccessAllowed() {
        if (ApplicationManager.getApplication().isReadAccessAllowed()) {
            return;
        }
        FileElement fileElement = TreeUtil.getFileElement(this);
        PsiElement cachedPsi = fileElement == null ? null : fileElement.getCachedPsi();
        if (cachedPsi == null) {
            return;
        }
        FileViewProvider viewProvider = cachedPsi instanceof PsiFile ? ((PsiFile) cachedPsi).getViewProvider() : null;
        if (viewProvider == null || !(viewProvider.getVirtualFile() instanceof ReadOnlyLightVirtualFile)) {
            ApplicationManager.getApplication().assertReadAccessAllowed();
        }
    }
}
