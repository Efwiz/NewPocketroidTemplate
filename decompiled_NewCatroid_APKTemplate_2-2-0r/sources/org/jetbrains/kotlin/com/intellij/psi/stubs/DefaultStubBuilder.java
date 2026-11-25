package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.StubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.containers.BooleanStack;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultStubBuilder implements StubBuilder {
    private static final Logger LOG = Logger.getInstance((Class<?>) DefaultStubBuilder.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5) ? 2 : 3];
        switch (i) {
            case 2:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/DefaultStubBuilder";
                break;
            case 3:
                objArr[0] = "root";
                break;
            case 4:
                objArr[0] = "parentStub";
                break;
            case 6:
                objArr[0] = "parent";
                break;
            case 7:
                objArr[0] = "node";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i == 2) {
            objArr[1] = "createStubForFile";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/DefaultStubBuilder";
        } else {
            objArr[1] = "buildStubTreeFor";
        }
        switch (i) {
            case 1:
                objArr[2] = "createStubForFile";
                break;
            case 2:
            case 5:
                break;
            case 3:
            case 4:
                objArr[2] = "buildStubTreeFor";
                break;
            case 6:
            case 7:
                objArr[2] = "skipChildProcessingWhenBuildingStubs";
                break;
            default:
                objArr[2] = "buildStubTree";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.StubBuilder
    public boolean skipChildProcessingWhenBuildingStubs(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        if (aSTNode2 != null) {
            return false;
        }
        $$$reportNull$$$0(7);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.StubBuilder
    public StubElement buildStubTree(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        return buildStubTreeFor(psiFile.getNode(), createStubForFile(psiFile));
    }

    protected StubElement createStubForFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiFileStubImpl(psiFile);
    }

    protected final StubElement buildStubTreeFor(ASTNode aSTNode, StubElement stubElement) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(4);
        }
        new StubBuildingWalkingVisitor(aSTNode, stubElement).buildStubTree();
        if (stubElement == null) {
            $$$reportNull$$$0(5);
        }
        return stubElement;
    }

    protected class StubBuildingWalkingVisitor {
        private final Stack<ASTNode> parentNodes;
        private final BooleanStack parentNodesStubbed;
        private final Stack<StubElement> parentStubs;

        protected StubBuildingWalkingVisitor(ASTNode aSTNode, StubElement stubElement) {
            Stack<StubElement> stack = new Stack<>();
            this.parentStubs = stack;
            Stack<ASTNode> stack2 = new Stack<>();
            this.parentNodes = stack2;
            BooleanStack booleanStack = new BooleanStack();
            this.parentNodesStubbed = booleanStack;
            stack2.push(aSTNode);
            stack.push(stubElement);
            booleanStack.push(true);
        }

        public final void buildStubTree() {
            while (!this.parentStubs.isEmpty()) {
                visitNode(this.parentStubs.pop(), this.parentNodes.pop(), this.parentNodesStubbed.pop());
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        protected void visitNode(StubElement stubElement, ASTNode aSTNode, boolean z) {
            Stub stubCreateStub = createStub(stubElement, aSTNode);
            if (stubCreateStub != null && !z) {
                ((ObjectStubBase) stubCreateStub).markDangling();
            }
            boolean z2 = (aSTNode instanceof FileElement) || stubCreateStub != null;
            if (stubCreateStub != null) {
                stubElement = stubCreateStub;
            }
            pushChildren(aSTNode, z2, stubElement);
        }

        protected final ASTNode peekNextElement() {
            if (this.parentNodes.isEmpty()) {
                return null;
            }
            return this.parentNodes.peek();
        }

        protected StubElement createStub(StubElement stubElement, ASTNode aSTNode) {
            IElementType elementType = aSTNode.getElementType();
            if (!(elementType instanceof IStubElementType)) {
                return null;
            }
            IStubElementType iStubElementType = (IStubElementType) elementType;
            if (!iStubElementType.shouldCreateStub(aSTNode)) {
                return null;
            }
            PsiElement psi = aSTNode.getPsi();
            if (!(psi instanceof StubBasedPsiElement)) {
                DefaultStubBuilder.LOG.error("Non-StubBasedPsiElement requests stub creation. Stub type: " + iStubElementType + ", PSI: " + psi + ", language: #" + iStubElementType.getLanguage());
            }
            StubElement stubElementCreateStub = iStubElementType.createStub(psi, stubElement);
            DefaultStubBuilder.LOG.assertTrue(stubElementCreateStub != null, psi);
            return stubElementCreateStub;
        }

        private void pushChildren(ASTNode aSTNode, boolean z, StubElement stubElement) {
            for (ASTNode lastChildNode = aSTNode.getLastChildNode(); lastChildNode != null; lastChildNode = lastChildNode.getTreePrev()) {
                if (!DefaultStubBuilder.this.skipChildProcessingWhenBuildingStubs(aSTNode, lastChildNode)) {
                    this.parentNodes.push(lastChildNode);
                    this.parentStubs.push(stubElement);
                    this.parentNodesStubbed.push(z);
                }
            }
        }
    }
}
