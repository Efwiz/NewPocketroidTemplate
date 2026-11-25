package org.jetbrains.kotlin.com.intellij.lang;

import java.util.AbstractList;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: loaded from: classes3.dex */
public class FCTSBackedLighterAST extends LighterAST {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final FlyweightCapableTreeStructure<LighterASTNode> myTreeStructure;

    /* JADX WARN: Code duplicated, block: B:21:0x0033  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "treeStructure";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/FCTSBackedLighterAST";
        } else if (i == 3) {
            objArr[0] = "node";
        } else if (i == 4) {
            objArr[0] = "parent";
        } else if (i != 5) {
            objArr[0] = "charTable";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/FCTSBackedLighterAST";
        }
        if (i == 2) {
            objArr[1] = "getRoot";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/FCTSBackedLighterAST";
        } else {
            objArr[1] = "getChildren";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "getParent";
            } else if (i == 4) {
                objArr[2] = "getChildren";
            } else if (i != 5) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FCTSBackedLighterAST(CharTable charTable, FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure) {
        super(charTable);
        if (charTable == null) {
            $$$reportNull$$$0(0);
        }
        if (flyweightCapableTreeStructure == null) {
            $$$reportNull$$$0(1);
        }
        this.myTreeStructure = flyweightCapableTreeStructure;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.LighterAST
    public LighterASTNode getRoot() {
        LighterASTNode root = this.myTreeStructure.getRoot();
        if (root == null) {
            $$$reportNull$$$0(2);
        }
        return root;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.LighterAST
    public LighterASTNode getParent(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(3);
        }
        return this.myTreeStructure.getParent(lighterASTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.LighterAST
    public List<LighterASTNode> getChildren(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(4);
        }
        Ref<LighterASTNode[]> ref = new Ref<>();
        int children = this.myTreeStructure.getChildren(lighterASTNode, ref);
        if (children == 0) {
            List<LighterASTNode> listEmptyList = ContainerUtil.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(5);
            }
            return listEmptyList;
        }
        return new LighterASTNodeList(children, ref.get());
    }

    private static class LighterASTNodeList extends AbstractList<LighterASTNode> {
        private final LighterASTNode[] myElements;
        private final int mySize;

        LighterASTNodeList(int i, LighterASTNode[] lighterASTNodeArr) {
            this.mySize = i;
            this.myElements = lighterASTNodeArr;
        }

        @Override // java.util.AbstractList, java.util.List
        public LighterASTNode get(int i) {
            if (i < 0 || i >= this.mySize) {
                throw new IndexOutOfBoundsException("index:" + i + " size:" + this.mySize);
            }
            return this.myElements[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.mySize;
        }
    }
}
