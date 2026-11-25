package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.Arrays;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: loaded from: classes3.dex */
public class ASTStructure implements FlyweightCapableTreeStructure<ASTNode> {
    private final ASTNode myRoot;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ASTStructure";
                break;
            case 2:
            case 5:
            case 7:
            case 8:
                objArr[0] = "node";
                break;
            case 3:
                objArr[0] = "astNode";
                break;
            case 4:
                objArr[0] = "into";
                break;
            default:
                objArr[0] = "root";
                break;
        }
        if (i == 1) {
            objArr[1] = "getRoot";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ASTStructure";
        } else {
            objArr[1] = "toString";
        }
        switch (i) {
            case 1:
            case 6:
                break;
            case 2:
                objArr[2] = "getParent";
                break;
            case 3:
            case 4:
                objArr[2] = "getChildren";
                break;
            case 5:
                objArr[2] = "toString";
                break;
            case 7:
                objArr[2] = "getStartOffset";
                break;
            case 8:
                objArr[2] = "getEndOffset";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
    public void disposeChildren(ASTNode[] aSTNodeArr, int i) {
    }

    public ASTStructure(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        this.myRoot = aSTNode;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
    public ASTNode getRoot() {
        ASTNode aSTNode = this.myRoot;
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        return aSTNode;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
    public ASTNode getParent(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        return aSTNode.getTreeParent();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
    public int getChildren(ASTNode aSTNode, Ref<ASTNode[]> ref) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (ref == null) {
            $$$reportNull$$$0(4);
        }
        ASTNode firstChildNode = aSTNode.getFirstChildNode();
        int i = 0;
        if (firstChildNode == null) {
            return 0;
        }
        ASTNode[] aSTNodeArr = ref.get();
        if (aSTNodeArr == null) {
            aSTNodeArr = new ASTNode[10];
            ref.set(aSTNodeArr);
        }
        while (firstChildNode != null) {
            if (i >= aSTNodeArr.length) {
                aSTNodeArr = (ASTNode[]) Arrays.copyOf(aSTNodeArr, (i * 3) / 2);
                ref.set(aSTNodeArr);
            }
            aSTNodeArr[i] = firstChildNode;
            firstChildNode = firstChildNode.getTreeNext();
            i++;
        }
        return i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
    public CharSequence toString(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        CharSequence chars = aSTNode.getChars();
        if (chars == null) {
            $$$reportNull$$$0(6);
        }
        return chars;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
    public int getStartOffset(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(7);
        }
        return aSTNode.getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
    public int getEndOffset(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(8);
        }
        return aSTNode.getStartOffset() + aSTNode.getTextLength();
    }
}
