package org.jetbrains.kotlin.com.intellij.lang;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class TreeBackedLighterAST extends LighterAST {
    private final FileASTNode myRoot;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 6:
                objArr[0] = "node";
                break;
            case 2:
                objArr[0] = "parent";
                break;
            case 3:
            case 4:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/TreeBackedLighterAST";
                break;
            default:
                objArr[0] = "root";
                break;
        }
        if (i == 3 || i == 4) {
            objArr[1] = "getChildren";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/TreeBackedLighterAST";
        } else {
            objArr[1] = "unwrap";
        }
        switch (i) {
            case 1:
                objArr[2] = "getParent";
                break;
            case 2:
                objArr[2] = "getChildren";
                break;
            case 3:
            case 4:
            case 7:
                break;
            case 5:
                objArr[2] = "wrap";
                break;
            case 6:
                objArr[2] = "unwrap";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBackedLighterAST(FileASTNode fileASTNode) {
        super(fileASTNode.getCharTable());
        if (fileASTNode == null) {
            $$$reportNull$$$0(0);
        }
        this.myRoot = fileASTNode;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.LighterAST
    public LighterASTNode getRoot() {
        return wrap(this.myRoot);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.LighterAST
    public LighterASTNode getParent(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(1);
        }
        ASTNode treeParent = ((NodeWrapper) lighterASTNode).myNode.getTreeParent();
        if (treeParent == null) {
            return null;
        }
        return wrap(treeParent);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.LighterAST
    public List<LighterASTNode> getChildren(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(2);
        }
        ASTNode[] children = ((NodeWrapper) lighterASTNode).myNode.getChildren(null);
        if (children.length == 0) {
            List<LighterASTNode> listEmptyList = ContainerUtil.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(3);
            }
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList(children.length);
        for (ASTNode aSTNode : children) {
            arrayList.add(wrap(aSTNode));
        }
        return arrayList;
    }

    public static LighterASTNode wrap(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        return (aSTNode.getFirstChildNode() != null || aSTNode.getTextLength() <= 0) ? new NodeWrapper(aSTNode) : new TokenNodeWrapper(aSTNode);
    }

    public ASTNode unwrap(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(6);
        }
        ASTNode aSTNode = ((NodeWrapper) lighterASTNode).myNode;
        if (aSTNode == null) {
            $$$reportNull$$$0(7);
        }
        return aSTNode;
    }

    private static class NodeWrapper implements LighterASTNode {
        protected final ASTNode myNode;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/lang/TreeBackedLighterAST$NodeWrapper", "getTokenType"));
        }

        NodeWrapper(ASTNode aSTNode) {
            this.myNode = aSTNode;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public IElementType getTokenType() {
            IElementType elementType = this.myNode.getElementType();
            if (elementType == null) {
                $$$reportNull$$$0(0);
            }
            return elementType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public int getStartOffset() {
            return this.myNode.getStartOffset();
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public int getEndOffset() {
            return this.myNode.getStartOffset() + this.myNode.getTextLength();
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
        public int getTextLength() {
            return this.myNode.getTextLength();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NodeWrapper)) {
                return false;
            }
            ASTNode aSTNode = this.myNode;
            ASTNode aSTNode2 = ((NodeWrapper) obj).myNode;
            return aSTNode == null ? aSTNode2 == null : aSTNode.equals(aSTNode2);
        }

        public int hashCode() {
            return this.myNode.hashCode();
        }

        public String toString() {
            return "node wrapper[" + this.myNode + "]";
        }
    }

    private static class TokenNodeWrapper extends NodeWrapper implements LighterASTTokenNode {
        TokenNodeWrapper(ASTNode aSTNode) {
            super(aSTNode);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterASTTokenNode
        public CharSequence getText() {
            return this.myNode.getText();
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.TreeBackedLighterAST.NodeWrapper
        public String toString() {
            return "token wrapper[" + this.myNode + "]";
        }
    }
}
