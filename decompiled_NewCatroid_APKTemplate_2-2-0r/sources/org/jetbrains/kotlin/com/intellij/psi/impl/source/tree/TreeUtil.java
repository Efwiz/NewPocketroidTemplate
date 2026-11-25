package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.util.Couple;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.OuterLanguageElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStrongWhitespaceHolderElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public final class TreeUtil {
    private static final Key<String> UNCLOSED_ELEMENT_PROPERTY = Key.create("UNCLOSED_ELEMENT_PROPERTY");
    public static final Key<FileElement> CONTAINING_FILE_KEY_AFTER_REPARSE = Key.create("CONTAINING_FILE_KEY_AFTER_REPARSE");

    public static final class CommonParentState {
        boolean isStrongElementOnRisingSlope = true;
        public ASTNode nextLeafBranchStart;
        TreeElement startLeafBranchStart;
        CompositeElement strongWhiteSpaceHolder;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 2:
            case 7:
            case 8:
            case 11:
            case 14:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 3:
            default:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 4:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 5:
            case 6:
            case 12:
                objArr[0] = "node";
                break;
            case 9:
            case 10:
                objArr[0] = "start";
                break;
            case 13:
                objArr[0] = "alsoSkip";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/TreeUtil";
        switch (i) {
            case 1:
                objArr[2] = "skipElementsBack";
                break;
            case 2:
            case 3:
                objArr[2] = "findParent";
                break;
            case 4:
                objArr[2] = "clearCaches";
                break;
            case 5:
            case 6:
            case 9:
            case 10:
                objArr[2] = "nextLeaf";
                break;
            case 7:
            case 8:
                objArr[2] = "getFileElement";
                break;
            case 11:
                objArr[2] = "findFirstLeafOrType";
                break;
            case 12:
                objArr[2] = "containsOuterLanguageElements";
                break;
            case 13:
                objArr[2] = "skipWhitespaceCommentsAndTokens";
                break;
            case 14:
                objArr[2] = "isWhitespaceOrComment";
                break;
            default:
                objArr[2] = "skipElements";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static void ensureParsed(ASTNode aSTNode) {
        if (aSTNode != null) {
            aSTNode.getFirstChildNode();
        }
    }

    public static boolean isCollapsedChameleon(ASTNode aSTNode) {
        return (aSTNode instanceof LazyParseableElement) && !((LazyParseableElement) aSTNode).isParsed();
    }

    public static ASTNode findChildBackward(ASTNode aSTNode, IElementType iElementType) {
        if (DebugUtil.CHECK_INSIDE_ATOMIC_ACTION_ENABLED && (aSTNode instanceof TreeElement)) {
            ((TreeElement) aSTNode).assertReadAccessAllowed();
        }
        for (ASTNode lastChildNode = aSTNode.getLastChildNode(); lastChildNode != null; lastChildNode = lastChildNode.getTreePrev()) {
            if (lastChildNode.getElementType() == iElementType) {
                return lastChildNode;
            }
        }
        return null;
    }

    public static ASTNode skipElements(ASTNode aSTNode, TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(0);
        }
        while (aSTNode != null && tokenSet.contains(aSTNode.getElementType())) {
            aSTNode = aSTNode.getTreeNext();
        }
        return aSTNode;
    }

    public static ASTNode skipElementsBack(ASTNode aSTNode, TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(1);
        }
        while (aSTNode != null && tokenSet.contains(aSTNode.getElementType())) {
            aSTNode = aSTNode.getTreePrev();
        }
        return aSTNode;
    }

    public static ASTNode findParent(ASTNode aSTNode, IElementType iElementType) {
        do {
            aSTNode = aSTNode.getTreeParent();
            if (aSTNode == null) {
                return null;
            }
        } while (aSTNode.getElementType() != iElementType);
        return aSTNode;
    }

    public static ASTNode findParent(ASTNode aSTNode, TokenSet tokenSet) {
        do {
            aSTNode = aSTNode.getTreeParent();
            if (aSTNode == null) {
                return null;
            }
        } while (!tokenSet.contains(aSTNode.getElementType()));
        return aSTNode;
    }

    public static ASTNode findParent(ASTNode aSTNode, TokenSet tokenSet, TokenSet tokenSet2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(3);
        }
        while (true) {
            aSTNode = aSTNode.getTreeParent();
            if (aSTNode != null) {
                if (!tokenSet.contains(aSTNode.getElementType())) {
                    if (tokenSet2 != null && tokenSet2.contains(aSTNode.getElementType())) {
                        break;
                    }
                } else {
                    return aSTNode;
                }
            } else {
                break;
            }
        }
        return null;
    }

    public static LeafElement findFirstLeaf(ASTNode aSTNode) {
        return (LeafElement) findFirstLeaf(aSTNode, true);
    }

    public static ASTNode findFirstLeaf(ASTNode aSTNode, boolean z) {
        if (aSTNode instanceof LeafElement) {
            return aSTNode;
        }
        if (!z && isCollapsedChameleon(aSTNode)) {
            return aSTNode;
        }
        for (ASTNode firstChildNode = aSTNode.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            ASTNode aSTNodeFindFirstLeaf = findFirstLeaf(firstChildNode, z);
            if (aSTNodeFindFirstLeaf != null) {
                return aSTNodeFindFirstLeaf;
            }
        }
        return null;
    }

    public static ASTNode findLastLeaf(ASTNode aSTNode) {
        return findLastLeaf(aSTNode, true);
    }

    public static ASTNode findLastLeaf(ASTNode aSTNode, boolean z) {
        if (aSTNode instanceof LeafElement) {
            return aSTNode;
        }
        if (!z && isCollapsedChameleon(aSTNode)) {
            return aSTNode;
        }
        for (ASTNode lastChildNode = aSTNode.getLastChildNode(); lastChildNode != null; lastChildNode = lastChildNode.getTreePrev()) {
            ASTNode aSTNodeFindLastLeaf = findLastLeaf(lastChildNode);
            if (aSTNodeFindLastLeaf != null) {
                return aSTNodeFindLastLeaf;
            }
        }
        return null;
    }

    public static ASTNode findSibling(ASTNode aSTNode, IElementType iElementType) {
        while (aSTNode != null) {
            if (aSTNode.getElementType() == iElementType) {
                return aSTNode;
            }
            aSTNode = aSTNode.getTreeNext();
        }
        return null;
    }

    public static ASTNode findSibling(ASTNode aSTNode, TokenSet tokenSet) {
        while (aSTNode != null) {
            if (tokenSet.contains(aSTNode.getElementType())) {
                return aSTNode;
            }
            aSTNode = aSTNode.getTreeNext();
        }
        return null;
    }

    public static ASTNode findSiblingBackward(ASTNode aSTNode, IElementType iElementType) {
        while (aSTNode != null) {
            if (aSTNode.getElementType() == iElementType) {
                return aSTNode;
            }
            aSTNode = aSTNode.getTreePrev();
        }
        return null;
    }

    public static ASTNode findSiblingBackward(ASTNode aSTNode, TokenSet tokenSet) {
        while (aSTNode != null) {
            if (tokenSet.contains(aSTNode.getElementType())) {
                return aSTNode;
            }
            aSTNode = aSTNode.getTreePrev();
        }
        return null;
    }

    public static ASTNode findCommonParent(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == aSTNode2) {
            return aSTNode;
        }
        HashSet hashSet = new HashSet(20);
        while (aSTNode != null) {
            hashSet.add(aSTNode);
            aSTNode = aSTNode.getTreeParent();
        }
        while (aSTNode2 != null) {
            if (hashSet.contains(aSTNode2)) {
                return aSTNode2;
            }
            aSTNode2 = aSTNode2.getTreeParent();
        }
        return null;
    }

    public static Couple<ASTNode> findTopmostSiblingParents(ASTNode aSTNode, ASTNode aSTNode2) {
        ASTNode aSTNode3;
        ASTNode aSTNode4;
        if (aSTNode == aSTNode2) {
            return Couple.of(null, null);
        }
        LinkedList linkedList = new LinkedList();
        while (aSTNode != null) {
            linkedList.add(aSTNode);
            aSTNode = aSTNode.getTreeParent();
        }
        LinkedList linkedList2 = new LinkedList();
        while (aSTNode2 != null) {
            linkedList2.add(aSTNode2);
            aSTNode2 = aSTNode2.getTreeParent();
        }
        do {
            aSTNode3 = (ASTNode) linkedList.pollLast();
            aSTNode4 = (ASTNode) linkedList2.pollLast();
            if (aSTNode3 != aSTNode4) {
                break;
            }
        } while (aSTNode3 != null);
        return Couple.of(aSTNode3, aSTNode4);
    }

    public static void clearCaches(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(4);
        }
        treeElement.acceptTree(new RecursiveTreeElementWalkingVisitor(false) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor
            protected void visitNode(TreeElement treeElement2) {
                treeElement2.clearCaches();
                super.visitNode(treeElement2);
            }
        });
    }

    public static ASTNode nextLeaf(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        return nextLeaf((TreeElement) aSTNode, (CommonParentState) null);
    }

    public static LeafElement nextLeaf(LeafElement leafElement) {
        if (leafElement == null) {
            $$$reportNull$$$0(6);
        }
        return nextLeaf(leafElement, (CommonParentState) null);
    }

    public static FileElement getFileElement(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(7);
        }
        TreeElement treeParent = treeElement;
        while (treeParent != null && !(treeParent instanceof FileElement)) {
            treeParent = treeParent.getTreeParent();
        }
        if (treeParent == null) {
            treeParent = (TreeElement) treeElement.getUserData(CONTAINING_FILE_KEY_AFTER_REPARSE);
        }
        return (FileElement) treeParent;
    }

    public static FileASTNode getFileElement(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(8);
        }
        ASTNode treeParent = aSTNode;
        while (treeParent != null && !(treeParent instanceof FileASTNode)) {
            treeParent = treeParent.getTreeParent();
        }
        if (treeParent == null) {
            treeParent = (ASTNode) aSTNode.getUserData(CONTAINING_FILE_KEY_AFTER_REPARSE);
        }
        return (FileASTNode) treeParent;
    }

    public static ASTNode prevLeaf(ASTNode aSTNode) {
        return prevLeaf((TreeElement) aSTNode, (CommonParentState) null);
    }

    public static boolean isStrongWhitespaceHolder(IElementType iElementType) {
        return iElementType instanceof IStrongWhitespaceHolderElementType;
    }

    public static String getTokenText(Lexer lexer) {
        return lexer.getBufferSequence().subSequence(lexer.getTokenStart(), lexer.getTokenEnd()).toString();
    }

    public static LeafElement nextLeaf(TreeElement treeElement, CommonParentState commonParentState) {
        if (treeElement == null) {
            $$$reportNull$$$0(9);
        }
        return (LeafElement) nextLeaf(treeElement, commonParentState, null, true);
    }

    public static TreeElement nextLeaf(TreeElement treeElement, CommonParentState commonParentState, IElementType iElementType, boolean z) {
        if (treeElement == null) {
            $$$reportNull$$$0(10);
        }
        while (true) {
            TreeElement treeElementFindFirstLeafOrType = null;
            if (treeElement == null) {
                return null;
            }
            if (commonParentState != null) {
                commonParentState.startLeafBranchStart = treeElement;
                initStrongWhitespaceHolder(commonParentState, treeElement, true);
            }
            TreeElement treeNext = treeElement;
            while (treeElementFindFirstLeafOrType == null) {
                treeNext = treeNext.getTreeNext();
                if (treeNext == null) {
                    break;
                }
                if (treeNext.getElementType() == iElementType) {
                    return treeNext;
                }
                treeElementFindFirstLeafOrType = findFirstLeafOrType(treeNext, iElementType, commonParentState, z);
            }
            if (treeElementFindFirstLeafOrType != null) {
                if (commonParentState != null) {
                    commonParentState.nextLeafBranchStart = treeNext;
                }
                return treeElementFindFirstLeafOrType;
            }
            treeElement = treeElement.getTreeParent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initStrongWhitespaceHolder(CommonParentState commonParentState, ASTNode aSTNode, boolean z) {
        if (aSTNode instanceof CompositeElement) {
            if (isStrongWhitespaceHolder(aSTNode.getElementType()) || (z && aSTNode.getUserData(UNCLOSED_ELEMENT_PROPERTY) != null)) {
                commonParentState.strongWhiteSpaceHolder = (CompositeElement) aSTNode;
                commonParentState.isStrongElementOnRisingSlope = z;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil$1MyVisitor] */
    private static TreeElement findFirstLeafOrType(TreeElement treeElement, final IElementType iElementType, final CommonParentState commonParentState, final boolean z) {
        if (treeElement == 0) {
            $$$reportNull$$$0(11);
        }
        ?? r0 = new RecursiveTreeElementWalkingVisitor(z) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil.1MyVisitor
            private TreeElement result;

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor
            protected void visitNode(TreeElement treeElement2) {
                if (this.result != null) {
                    return;
                }
                CommonParentState commonParentState2 = commonParentState;
                if (commonParentState2 != null) {
                    TreeUtil.initStrongWhitespaceHolder(commonParentState2, treeElement2, false);
                }
                if ((!z && TreeUtil.isCollapsedChameleon(treeElement2)) || (treeElement2 instanceof LeafElement) || treeElement2.getElementType() == iElementType) {
                    this.result = treeElement2;
                } else {
                    super.visitNode(treeElement2);
                }
            }
        };
        treeElement.acceptTree(r0);
        return ((C1MyVisitor) r0).result;
    }

    public static ASTNode prevLeaf(TreeElement treeElement, CommonParentState commonParentState) {
        while (true) {
            ASTNode aSTNodeFindLastLeaf = null;
            if (treeElement == null) {
                return null;
            }
            if (commonParentState != null) {
                if (commonParentState.strongWhiteSpaceHolder != null && treeElement.getUserData(UNCLOSED_ELEMENT_PROPERTY) != null) {
                    commonParentState.strongWhiteSpaceHolder = (CompositeElement) treeElement;
                }
                commonParentState.nextLeafBranchStart = treeElement;
            }
            ASTNode treePrev = treeElement;
            while (aSTNodeFindLastLeaf == null) {
                treePrev = treePrev.getTreePrev();
                if (treePrev == null) {
                    break;
                }
                aSTNodeFindLastLeaf = findLastLeaf(treePrev);
            }
            if (aSTNodeFindLastLeaf != null) {
                if (commonParentState != null) {
                    commonParentState.startLeafBranchStart = (TreeElement) treePrev;
                }
                return aSTNodeFindLastLeaf;
            }
            treeElement = treeElement.getTreeParent();
        }
    }

    public static ASTNode nextLeaf(ASTNode aSTNode, boolean z) {
        while (aSTNode != null) {
            for (ASTNode treeNext = aSTNode.getTreeNext(); treeNext != null; treeNext = treeNext.getTreeNext()) {
                ASTNode aSTNodeFindFirstLeaf = findFirstLeaf(treeNext, z);
                if (aSTNodeFindFirstLeaf != null) {
                    return aSTNodeFindFirstLeaf;
                }
            }
            aSTNode = aSTNode.getTreeParent();
        }
        return null;
    }

    public static ASTNode prevLeaf(ASTNode aSTNode, boolean z) {
        while (aSTNode != null) {
            for (ASTNode treePrev = aSTNode.getTreePrev(); treePrev != null; treePrev = treePrev.getTreePrev()) {
                ASTNode aSTNodeFindLastLeaf = findLastLeaf(treePrev, z);
                if (aSTNodeFindLastLeaf != null) {
                    return aSTNodeFindLastLeaf;
                }
            }
            aSTNode = aSTNode.getTreeParent();
        }
        return null;
    }

    public static ASTNode getLastChild(ASTNode aSTNode) {
        ASTNode aSTNode2 = aSTNode;
        while (aSTNode != null) {
            aSTNode2 = aSTNode;
            aSTNode = aSTNode.getLastChildNode();
        }
        return aSTNode2;
    }

    public static boolean containsOuterLanguageElements(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(12);
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ((TreeElement) aSTNode).acceptTree(new RecursiveTreeElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil.2
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor
            protected void visitNode(TreeElement treeElement) {
                if (treeElement instanceof OuterLanguageElement) {
                    atomicBoolean.set(true);
                    stopWalking();
                } else {
                    super.visitNode(treeElement);
                }
            }
        });
        return atomicBoolean.get();
    }

    public static ASTNode skipWhitespaceAndComments(ASTNode aSTNode, boolean z) {
        return skipWhitespaceCommentsAndTokens(aSTNode, TokenSet.EMPTY, z);
    }

    public static ASTNode skipWhitespaceCommentsAndTokens(ASTNode aSTNode, TokenSet tokenSet, boolean z) {
        if (tokenSet == null) {
            $$$reportNull$$$0(13);
        }
        while (aSTNode != null) {
            if (!isWhitespaceOrComment(aSTNode) && !tokenSet.contains(aSTNode.getElementType())) {
                return aSTNode;
            }
            aSTNode = z ? aSTNode.getTreeNext() : aSTNode.getTreePrev();
        }
        return null;
    }

    public static boolean isWhitespaceOrComment(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(14);
        }
        return (aSTNode.getPsi() instanceof PsiWhiteSpace) || (aSTNode.getPsi() instanceof PsiComment);
    }
}
