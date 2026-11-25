package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveVisitor;
import org.jetbrains.kotlin.com.intellij.util.WalkingState;

/* JADX INFO: loaded from: classes3.dex */
public abstract class RecursiveTreeElementWalkingVisitor extends TreeElementVisitor implements PsiRecursiveVisitor {
    private final boolean myDoTransform;
    private final WalkingState<ASTNode> myWalkingState;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/RecursiveTreeElementWalkingVisitor", "elementFinished"));
    }

    protected void elementFinished(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    protected RecursiveTreeElementWalkingVisitor() {
        this(true);
    }

    protected RecursiveTreeElementWalkingVisitor(boolean z) {
        this.myWalkingState = new WalkingState<ASTNode>(ASTTreeGuide.instance) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/RecursiveTreeElementWalkingVisitor$1";
                if (i != 1) {
                    objArr[2] = "elementFinished";
                } else {
                    objArr[2] = "visit";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState
            public void elementFinished(ASTNode aSTNode) {
                if (aSTNode == null) {
                    $$$reportNull$$$0(0);
                }
                RecursiveTreeElementWalkingVisitor.this.elementFinished(aSTNode);
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState
            public void visit(ASTNode aSTNode) {
                if (aSTNode == null) {
                    $$$reportNull$$$0(1);
                }
                ((TreeElement) aSTNode).acceptTree(RecursiveTreeElementWalkingVisitor.this);
            }
        };
        this.myDoTransform = z;
    }

    private static class ASTTreeGuide implements WalkingState.TreeGuide<ASTNode> {
        private static final ASTTreeGuide instance = new ASTTreeGuide();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/RecursiveTreeElementWalkingVisitor$ASTTreeGuide";
            if (i == 1) {
                objArr[2] = "getPrevSibling";
            } else if (i == 2) {
                objArr[2] = "getFirstChild";
            } else if (i != 3) {
                objArr[2] = "getNextSibling";
            } else {
                objArr[2] = "getParent";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private ASTTreeGuide() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public ASTNode getNextSibling(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return aSTNode.getTreeNext();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public ASTNode getPrevSibling(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(1);
            }
            return aSTNode.getTreePrev();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public ASTNode getFirstChild(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(2);
            }
            return aSTNode.getFirstChildNode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public ASTNode getParent(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(3);
            }
            return aSTNode.getTreeParent();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
    public void visitLeaf(LeafElement leafElement) {
        visitNode(leafElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
    public void visitComposite(CompositeElement compositeElement) {
        visitNode(compositeElement);
    }

    protected void visitNode(TreeElement treeElement) {
        if (this.myDoTransform || !TreeUtil.isCollapsedChameleon(treeElement)) {
            this.myWalkingState.elementStarted(treeElement);
        }
    }

    public void stopWalking() {
        this.myWalkingState.stopWalking();
    }
}
