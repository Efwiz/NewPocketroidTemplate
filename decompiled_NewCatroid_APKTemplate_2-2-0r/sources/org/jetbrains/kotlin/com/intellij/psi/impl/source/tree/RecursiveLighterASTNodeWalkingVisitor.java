package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTTokenNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.util.WalkingState;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;

/* JADX INFO: loaded from: classes3.dex */
public abstract class RecursiveLighterASTNodeWalkingVisitor extends LighterASTNodeVisitor {
    private final LighterAST ast;
    private final WalkingState<IndexedLighterASTNode> myWalkingState;
    private final Stack<IndexedLighterASTNode> parentStack;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "ast";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/RecursiveLighterASTNodeWalkingVisitor";
        if (i == 1) {
            objArr[2] = "elementFinished";
        } else if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "visitNode";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected void elementFinished(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    private static class IndexedLighterASTNode {
        private static final IndexedLighterASTNode[] EMPTY_ARRAY = new IndexedLighterASTNode[0];
        private IndexedLighterASTNode next;
        private final LighterASTNode node;
        private final IndexedLighterASTNode prev;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "node", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/RecursiveLighterASTNodeWalkingVisitor$IndexedLighterASTNode", "<init>"));
        }

        IndexedLighterASTNode(LighterASTNode lighterASTNode, IndexedLighterASTNode indexedLighterASTNode) {
            if (lighterASTNode == null) {
                $$$reportNull$$$0(0);
            }
            this.node = lighterASTNode;
            this.prev = indexedLighterASTNode;
        }
    }

    private class LighterASTGuide implements WalkingState.TreeGuide<IndexedLighterASTNode> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/RecursiveLighterASTNodeWalkingVisitor$LighterASTGuide";
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

        private LighterASTGuide() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public IndexedLighterASTNode getNextSibling(IndexedLighterASTNode indexedLighterASTNode) {
            if (indexedLighterASTNode == null) {
                $$$reportNull$$$0(0);
            }
            return indexedLighterASTNode.next;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public IndexedLighterASTNode getPrevSibling(IndexedLighterASTNode indexedLighterASTNode) {
            if (indexedLighterASTNode == null) {
                $$$reportNull$$$0(1);
            }
            return indexedLighterASTNode.prev;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public IndexedLighterASTNode getFirstChild(IndexedLighterASTNode indexedLighterASTNode) {
            if (indexedLighterASTNode == null) {
                $$$reportNull$$$0(2);
            }
            List<LighterASTNode> children = RecursiveLighterASTNodeWalkingVisitor.this.ast.getChildren(indexedLighterASTNode.node);
            IndexedLighterASTNode[] indexedLighterASTNodeArr = children.isEmpty() ? IndexedLighterASTNode.EMPTY_ARRAY : new IndexedLighterASTNode[children.size()];
            int i = 0;
            while (true) {
                if (i >= children.size()) {
                    break;
                }
                IndexedLighterASTNode indexedLighterASTNode2 = new IndexedLighterASTNode(children.get(i), i != 0 ? indexedLighterASTNodeArr[i - 1] : null);
                indexedLighterASTNodeArr[i] = indexedLighterASTNode2;
                if (i != 0) {
                    indexedLighterASTNodeArr[i - 1].next = indexedLighterASTNode2;
                }
                i++;
            }
            RecursiveLighterASTNodeWalkingVisitor.this.parentStack.push(indexedLighterASTNode);
            if (children.isEmpty()) {
                return null;
            }
            return indexedLighterASTNodeArr[0];
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public IndexedLighterASTNode getParent(IndexedLighterASTNode indexedLighterASTNode) {
            if (indexedLighterASTNode == null) {
                $$$reportNull$$$0(3);
            }
            return (IndexedLighterASTNode) RecursiveLighterASTNodeWalkingVisitor.this.parentStack.peek();
        }
    }

    protected RecursiveLighterASTNodeWalkingVisitor(LighterAST lighterAST) {
        if (lighterAST == null) {
            $$$reportNull$$$0(0);
        }
        this.parentStack = new Stack<>();
        this.ast = lighterAST;
        this.myWalkingState = new WalkingState<IndexedLighterASTNode>(new LighterASTGuide()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveLighterASTNodeWalkingVisitor.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                } else {
                    objArr[0] = "iNode";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/RecursiveLighterASTNodeWalkingVisitor$1";
                if (i != 1) {
                    objArr[2] = "elementFinished";
                } else {
                    objArr[2] = "visit";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState
            public void elementFinished(IndexedLighterASTNode indexedLighterASTNode) {
                if (indexedLighterASTNode == null) {
                    $$$reportNull$$$0(0);
                }
                RecursiveLighterASTNodeWalkingVisitor.this.elementFinished(indexedLighterASTNode.node);
                if (RecursiveLighterASTNodeWalkingVisitor.this.parentStack.peek() == indexedLighterASTNode) {
                    RecursiveLighterASTNodeWalkingVisitor.this.parentStack.pop();
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState
            public void visit(IndexedLighterASTNode indexedLighterASTNode) {
                if (indexedLighterASTNode == null) {
                    $$$reportNull$$$0(1);
                }
                ProgressManager.checkCanceled();
                LighterASTNode lighterASTNode = indexedLighterASTNode.node;
                RecursiveLighterASTNodeWalkingVisitor recursiveLighterASTNodeWalkingVisitor = RecursiveLighterASTNodeWalkingVisitor.this;
                if (lighterASTNode instanceof LighterLazyParseableNode) {
                    recursiveLighterASTNodeWalkingVisitor.visitLazyParseableNode((LighterLazyParseableNode) lighterASTNode);
                } else if (lighterASTNode instanceof LighterASTTokenNode) {
                    recursiveLighterASTNodeWalkingVisitor.visitTokenNode((LighterASTTokenNode) lighterASTNode);
                } else {
                    recursiveLighterASTNodeWalkingVisitor.visitNode(lighterASTNode);
                }
            }
        };
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LighterASTNodeVisitor
    public void visitNode(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(2);
        }
        this.myWalkingState.elementStarted(new IndexedLighterASTNode(lighterASTNode, null));
    }

    public void stopWalking() {
        this.myWalkingState.stopWalking();
    }
}
