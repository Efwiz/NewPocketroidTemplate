package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;
import org.jetbrains.kotlin.com.intellij.util.diff.ShallowNodeComparator;

/* JADX INFO: loaded from: classes3.dex */
public class ASTShallowComparator implements ShallowNodeComparator<ASTNode, ASTNode> {
    private final ProgressIndicator myIndicator;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "oldNode";
                break;
            case 2:
                objArr[0] = "newNode";
                break;
            case 3:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ASTShallowComparator";
                break;
            case 4:
                objArr[0] = "root";
                break;
            case 5:
                objArr[0] = "text";
                break;
            case 6:
            case 8:
                objArr[0] = "n1";
                break;
            case 7:
            case 9:
                objArr[0] = "n2";
                break;
            default:
                objArr[0] = "indicator";
                break;
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ASTShallowComparator";
        } else {
            objArr[1] = "deepEqual";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "deepEqual";
                break;
            case 3:
                break;
            case 4:
            case 5:
                objArr[2] = "compareTreeToText";
                break;
            case 6:
            case 7:
                objArr[2] = "typesEqual";
                break;
            case 8:
            case 9:
                objArr[2] = "hashCodesEqual";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    public ASTShallowComparator(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(0);
        }
        this.myIndicator = progressIndicator;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.ShallowNodeComparator
    public ThreeState deepEqual(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(2);
        }
        ThreeState threeStateTextMatches = textMatches(aSTNode, aSTNode2);
        if (threeStateTextMatches == null) {
            $$$reportNull$$$0(3);
        }
        return threeStateTextMatches;
    }

    private ThreeState textMatches(ASTNode aSTNode, ASTNode aSTNode2) {
        this.myIndicator.checkCanceled();
        String text = TreeUtil.isCollapsedChameleon(aSTNode) ? aSTNode.getText() : null;
        String text2 = TreeUtil.isCollapsedChameleon(aSTNode2) ? aSTNode2.getText() : null;
        if (text != null && text2 != null) {
            return text.equals(text2) ? ThreeState.YES : ThreeState.UNSURE;
        }
        if (text != null) {
            return compareTreeToText((TreeElement) aSTNode2, text) ? ThreeState.YES : ThreeState.UNSURE;
        }
        if (text2 != null) {
            return compareTreeToText((TreeElement) aSTNode, text2) ? ThreeState.YES : ThreeState.UNSURE;
        }
        if (aSTNode instanceof ForeignLeafPsiElement) {
            return ((aSTNode2 instanceof ForeignLeafPsiElement) && aSTNode.getText().equals(aSTNode2.getText())) ? ThreeState.YES : ThreeState.NO;
        }
        if (aSTNode2 instanceof ForeignLeafPsiElement) {
            return ThreeState.NO;
        }
        if (aSTNode instanceof LeafElement) {
            return ((LeafElement) aSTNode).textMatches(aSTNode2.getChars()) ? ThreeState.YES : ThreeState.NO;
        }
        if (aSTNode2 instanceof LeafElement) {
            return ((LeafElement) aSTNode2).textMatches(aSTNode.getChars()) ? ThreeState.YES : ThreeState.NO;
        }
        return ((aSTNode instanceof PsiErrorElement) && (aSTNode2 instanceof PsiErrorElement) && !Objects.equals(((PsiErrorElement) aSTNode).getErrorDescription(), ((PsiErrorElement) aSTNode2).getErrorDescription())) ? ThreeState.NO : ThreeState.UNSURE;
    }

    private boolean compareTreeToText(TreeElement treeElement, final String str) {
        if (treeElement == null) {
            $$$reportNull$$$0(4);
        }
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        final int[] iArr = {0};
        treeElement.acceptTree(new RecursiveTreeElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ASTShallowComparator.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
            public void visitLeaf(LeafElement leafElement) {
                matchText(leafElement);
            }

            private void matchText(TreeElement treeElement2) {
                int[] iArr2 = iArr;
                iArr2[0] = treeElement2.textMatches(str, iArr2[0]);
                if (iArr[0] < 0) {
                    stopWalking();
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
            public void visitComposite(CompositeElement compositeElement) {
                ASTShallowComparator.this.myIndicator.checkCanceled();
                if ((compositeElement instanceof LazyParseableElement) && !((LazyParseableElement) compositeElement).isParsed()) {
                    matchText(compositeElement);
                } else {
                    super.visitComposite(compositeElement);
                }
            }
        });
        return iArr[0] == str.length();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.ShallowNodeComparator
    public boolean typesEqual(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(7);
        }
        return aSTNode.getElementType() == aSTNode2.getElementType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.ShallowNodeComparator
    public boolean hashCodesEqual(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(8);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(9);
        }
        if ((aSTNode instanceof LeafElement) && (aSTNode2 instanceof LeafElement)) {
            return textMatches(aSTNode, aSTNode2) == ThreeState.YES;
        }
        return !((aSTNode instanceof PsiErrorElement) && (aSTNode2 instanceof PsiErrorElement) && !Objects.equals(((PsiErrorElement) aSTNode).getErrorDescription(), ((PsiErrorElement) aSTNode2).getErrorDescription())) && ((TreeElement) aSTNode).hc() == ((TreeElement) aSTNode2).hc();
    }
}
