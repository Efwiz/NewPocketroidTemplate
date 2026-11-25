package org.jetbrains.kotlin.com.intellij.extapi.psi;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.project.ProjectCoreUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ASTDelegatePsiElement extends PsiElementBase {
    private static final Logger LOG = Logger.getInstance((Class<?>) ASTDelegatePsiElement.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 14:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                i2 = 3;
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 14:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 4:
            case 5:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 14:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/extapi/psi/ASTDelegatePsiElement";
                break;
            case 10:
            case 11:
            case 12:
                objArr[0] = "elementType";
                break;
            case 13:
                objArr[0] = "arrayClass";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 21:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 19:
                objArr[0] = "first";
                break;
            case 20:
                objArr[0] = "last";
                break;
            case 22:
                objArr[0] = "child";
                break;
            case 23:
                objArr[0] = "newElement";
                break;
            case 24:
                objArr[0] = "text";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[1] = "getChildren";
                break;
            case 3:
                objArr[1] = "textToCharArray";
                break;
            case 4:
            case 5:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/extapi/psi/ASTDelegatePsiElement";
                break;
            case 6:
                objArr[1] = "getLanguage";
                break;
            case 7:
            case 8:
                objArr[1] = "findNotNullChildByType";
                break;
            case 9:
            case 14:
                objArr[1] = "findChildrenByType";
                break;
            default:
                objArr[1] = "EMPTY";
                break;
        }
        switch (i) {
            case 4:
                objArr[2] = "getCopyableUserData";
                break;
            case 5:
                objArr[2] = "putCopyableUserData";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "findChildrenByType";
                break;
            case 15:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 16:
                objArr[2] = "addBefore";
                break;
            case 17:
                objArr[2] = "addAfter";
                break;
            case 18:
                objArr[2] = "checkAdd";
                break;
            case 19:
            case 20:
                objArr[2] = "addRangeBefore";
                break;
            case 21:
                objArr[2] = "deleteElementFromParent";
                break;
            case 22:
                objArr[2] = "deleteChildInternal";
                break;
            case 23:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 24:
                objArr[2] = "textMatches";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                throw new IllegalArgumentException(str2);
            case 6:
            case 7:
            case 8:
            case 9:
            case 14:
            default:
                throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public abstract ASTNode getNode();

    public void subtreeChanged() {
    }

    private static <T> List<T> EMPTY() {
        List<T> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(0);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return SharedImplUtil.getContainingFile(getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManagerEx getManager() {
        Project projectTheOnlyOpenProject = ProjectCoreUtil.theOnlyOpenProject();
        if (projectTheOnlyOpenProject != null) {
            return PsiManagerEx.getInstanceEx(projectTheOnlyOpenProject);
        }
        PsiElement parent = this;
        while (parent instanceof ASTDelegatePsiElement) {
            parent = parent.getLightParent();
        }
        if (parent == null) {
            throw new PsiInvalidElementAccessException(this);
        }
        return (PsiManagerEx) parent.getManager();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement firstChild = getFirstChild();
        if (firstChild == null) {
            PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
            if (psiElementArr == null) {
                $$$reportNull$$$0(1);
            }
            return psiElementArr;
        }
        ArrayList arrayList = new ArrayList();
        while (firstChild != null) {
            if (firstChild.getNode() instanceof CompositeElement) {
                arrayList.add(firstChild);
            }
            firstChild = firstChild.getNextSibling();
        }
        PsiElement[] psiElementArray = PsiUtilCore.toPsiElementArray(arrayList);
        if (psiElementArray == null) {
            $$$reportNull$$$0(2);
        }
        return psiElementArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getFirstChild() {
        return SharedImplUtil.getFirstChild(getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getLastChild() {
        return SharedImplUtil.getLastChild(getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNextSibling() {
        return SharedImplUtil.getNextSibling(getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getPrevSibling() {
        return SharedImplUtil.getPrevSibling(getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return getNode().getTextRange();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        return getNode().getStartOffset() - getNode().getTreeParent().getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextLength() {
        return getNode().getTextLength();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int i) {
        return SourceTreeToPsiMap.treeElementToPsi(getNode().findLeafElementAt(i));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return getNode().getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return getNode().getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public char[] textToCharArray() {
        char[] charArray = getNode().getText().toCharArray();
        if (charArray == null) {
            $$$reportNull$$$0(3);
        }
        return charArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textContains(char c) {
        return getNode().textContains(c);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public <T> T getCopyableUserData(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(4);
        }
        return (T) getNode().getCopyableUserData(key);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public <T> void putCopyableUserData(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(5);
        }
        getNode().putCopyableUserData(key, t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        Language language = getNode().getElementType().getLanguage();
        if (language == null) {
            $$$reportNull$$$0(6);
        }
        return language;
    }

    protected <T extends PsiElement> T findChildByType(IElementType iElementType) {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(iElementType);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return (T) aSTNodeFindChildByType.getPsi();
    }

    protected <T extends PsiElement> T findLastChildByType(IElementType iElementType) {
        for (T t = (T) getLastChild(); t != null; t = (T) t.getPrevSibling()) {
            ASTNode node = t.getNode();
            if (node != null && node.getElementType() == iElementType) {
                return t;
            }
        }
        return null;
    }

    protected <T extends PsiElement> T findNotNullChildByType(IElementType iElementType) {
        T t = (T) notNullChild(findChildByType(iElementType));
        if (t == null) {
            $$$reportNull$$$0(7);
        }
        return t;
    }

    protected <T extends PsiElement> T findChildByType(TokenSet tokenSet) {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(tokenSet);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return (T) aSTNodeFindChildByType.getPsi();
    }

    protected <T extends PsiElement> T findNotNullChildByType(TokenSet tokenSet) {
        T t = (T) notNullChild(findChildByType(tokenSet));
        if (t == null) {
            $$$reportNull$$$0(8);
        }
        return t;
    }

    protected PsiElement findChildByFilter(TokenSet tokenSet) {
        ASTNode[] children = getNode().getChildren(tokenSet);
        if (children.length == 0) {
            return null;
        }
        return children[0].getPsi();
    }

    protected <T extends PsiElement> T[] findChildrenByType(IElementType iElementType, Class<T> cls) {
        T[] tArr = (T[]) ((PsiElement[]) ContainerUtil.map2Array(SharedImplUtil.getChildrenOfType(getNode(), iElementType), cls, new Function() { // from class: org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((ASTNode) obj).getPsi();
            }
        }));
        if (tArr == null) {
            $$$reportNull$$$0(9);
        }
        return tArr;
    }

    protected <T extends PsiElement> List<T> findChildrenByType(TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(10);
        }
        List listEMPTY = EMPTY();
        for (ASTNode firstChildNode = getNode().getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (tokenSet.contains(firstChildNode.getElementType())) {
                if (listEMPTY == EMPTY()) {
                    listEMPTY = new ArrayList();
                }
                listEMPTY.add(firstChildNode.getPsi());
            }
        }
        return listEMPTY;
    }

    protected <T extends PsiElement> List<T> findChildrenByType(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(11);
        }
        List listEMPTY = EMPTY();
        for (ASTNode firstChildNode = getNode().getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (iElementType == firstChildNode.getElementType()) {
                if (listEMPTY == EMPTY()) {
                    listEMPTY = new ArrayList();
                }
                listEMPTY.add(firstChildNode.getPsi());
            }
        }
        return listEMPTY;
    }

    protected <T extends PsiElement> T[] findChildrenByType(TokenSet tokenSet, Class<T> cls) {
        if (tokenSet == null) {
            $$$reportNull$$$0(12);
        }
        if (cls == null) {
            $$$reportNull$$$0(13);
        }
        T[] tArr = (T[]) ((PsiElement[]) ContainerUtil.map2Array(getNode().getChildren(tokenSet), cls, new Function() { // from class: org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((ASTNode) obj).getPsi();
            }
        }));
        if (tArr == null) {
            $$$reportNull$$$0(14);
        }
        return tArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return getNode().copyElement().getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        return addInnerBefore(psiElement, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(16);
        }
        return addInnerBefore(psiElement, psiElement2);
    }

    private PsiElement addInnerBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        ASTNode aSTNodeAddInternal = addInternal(treeElementCopyToElement, treeElementCopyToElement, SourceTreeToPsiMap.psiElementToTree(psiElement2), Boolean.TRUE);
        if (aSTNodeAddInternal != null) {
            if (aSTNodeAddInternal instanceof TreeElement) {
                return ChangeUtil.decodeInformation((TreeElement) aSTNodeAddInternal).getPsi();
            }
            return aSTNodeAddInternal.getPsi();
        }
        throw new IncorrectOperationException("Element cannot be added");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(17);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        ASTNode aSTNodeAddInternal = addInternal(treeElementCopyToElement, treeElementCopyToElement, SourceTreeToPsiMap.psiElementToTree(psiElement2), Boolean.FALSE);
        if (aSTNodeAddInternal instanceof TreeElement) {
            return ChangeUtil.decodeInformation((TreeElement) aSTNodeAddInternal).getPsi();
        }
        return aSTNodeAddInternal.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(18);
        }
        CheckUtil.checkWritable(this);
    }

    public ASTNode addInternal(ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3, Boolean bool) {
        return CodeEditUtil.addChildren(getNode(), aSTNode, aSTNode2, getAnchorNode(aSTNode3, bool));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        return SharedImplUtil.addRange(this, psiElement, psiElement2, null, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRangeBefore(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(19);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(20);
        }
        return SharedImplUtil.addRange(this, psiElement, psiElement2, SourceTreeToPsiMap.psiElementToTree(psiElement3), Boolean.TRUE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        return SharedImplUtil.addRange(this, psiElement, psiElement2, SourceTreeToPsiMap.psiElementToTree(psiElement3), Boolean.FALSE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        deleteElementFromParent(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void deleteElementFromParent(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(21);
        }
        PsiElement parent = psiElement.getLightParent();
        if (parent instanceof ASTDelegatePsiElement) {
            CheckUtil.checkWritable(psiElement);
            ((ASTDelegatePsiElement) parent).deleteChildInternal(psiElement.getNode());
        } else if (parent instanceof CompositeElement) {
            CheckUtil.checkWritable(psiElement);
            ((CompositeElement) parent).deleteChildInternal(psiElement.getNode());
        } else {
            if (parent instanceof PsiFile) {
                CheckUtil.checkWritable(psiElement);
                parent.deleteChildRange(psiElement, psiElement);
                return;
            }
            throw new UnsupportedOperationException(psiElement.getClass().getName() + " under " + (parent == 0 ? "null" : parent.getClass().getName()));
        }
    }

    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(22);
        }
        ((CompositeElement) getNode()).deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkDelete() throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void deleteChildRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        ASTNode aSTNodePsiElementToTree = SourceTreeToPsiMap.psiElementToTree(psiElement);
        ASTNode aSTNodePsiElementToTree2 = SourceTreeToPsiMap.psiElementToTree(psiElement2);
        Logger logger = LOG;
        logger.assertTrue(aSTNodePsiElementToTree.getTreeParent() == getNode());
        logger.assertTrue(aSTNodePsiElementToTree2.getTreeParent() == getNode());
        CodeEditUtil.removeChildren(getNode(), aSTNodePsiElementToTree, aSTNodePsiElementToTree2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(23);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        if (getLightParent() instanceof ASTDelegatePsiElement) {
            ((ASTDelegatePsiElement) getLightParent()).replaceChildInternal(this, treeElementCopyToElement);
        } else {
            CodeEditUtil.replaceChild(getLightParent().getNode(), getNode(), treeElementCopyToElement);
        }
        return SourceTreeToPsiMap.treeElementToPsi(ChangeUtil.decodeInformation(treeElementCopyToElement));
    }

    public void replaceChildInternal(PsiElement psiElement, TreeElement treeElement) {
        CodeEditUtil.replaceChild(getNode(), psiElement.getNode(), treeElement);
    }

    private ASTNode getAnchorNode(ASTNode aSTNode, Boolean bool) {
        if (aSTNode != null) {
            return bool.booleanValue() ? aSTNode : aSTNode.getTreeNext();
        }
        if (bool == null || bool.booleanValue()) {
            return null;
        }
        return getNode().getFirstChildNode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(24);
        }
        ASTNode node = getNode();
        return node instanceof TreeElement ? ((TreeElement) node).textMatches(charSequence) : super.textMatches(charSequence);
    }
}
