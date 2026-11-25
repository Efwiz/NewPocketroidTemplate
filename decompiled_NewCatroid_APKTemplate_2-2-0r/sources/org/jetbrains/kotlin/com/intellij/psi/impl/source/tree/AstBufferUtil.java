package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayCharSequence;

/* JADX INFO: loaded from: classes3.dex */
public final class AstBufferUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/AstBufferUtil";
        if (i != 2) {
            objArr[2] = "toBuffer";
        } else {
            objArr[2] = "getTextSkippingWhitespaceComments";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private AstBufferUtil() {
    }

    public static int toBuffer(ASTNode aSTNode, char[] cArr, int i) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        return toBuffer(aSTNode, cArr, i, false);
    }

    public static int toBuffer(ASTNode aSTNode, char[] cArr, int i, boolean z) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        BufferVisitor bufferVisitor = new BufferVisitor(z, z, i, cArr);
        ((TreeElement) aSTNode).acceptTree(bufferVisitor);
        return bufferVisitor.end;
    }

    public static String getTextSkippingWhitespaceComments(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        char[] cArr = new char[toBuffer(aSTNode, null, 0, true)];
        toBuffer(aSTNode, cArr, 0, true);
        return new String(cArr);
    }

    public static class BufferVisitor extends RecursiveTreeElementWalkingVisitor {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected final char[] buffer;
        protected int end;
        protected final int offset;
        private final boolean skipComments;
        private final boolean skipWhitespace;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/AstBufferUtil$BufferVisitor", "getBuffer"));
        }

        public BufferVisitor(PsiElement psiElement, boolean z, boolean z2) {
            this(z, z2, 0, new char[psiElement.getTextLength()]);
            ((TreeElement) psiElement.getNode()).acceptTree(this);
        }

        public BufferVisitor(boolean z, boolean z2, int i, char[] cArr) {
            super(false);
            this.skipWhitespace = z;
            this.skipComments = z2;
            this.buffer = cArr;
            this.offset = i;
            this.end = i;
        }

        public int getEnd() {
            return this.end;
        }

        public char[] getBuffer() {
            char[] cArr = this.buffer;
            if (cArr == null) {
                $$$reportNull$$$0(0);
            }
            return cArr;
        }

        public CharSequence createCharSequence() {
            return new CharArrayCharSequence(this.buffer, this.offset, this.end);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
        public void visitLeaf(LeafElement leafElement) {
            ProgressIndicatorProvider.checkCanceled();
            if (isIgnored(leafElement)) {
                return;
            }
            this.end = leafElement.copyTo(this.buffer, this.end);
        }

        protected boolean isIgnored(LeafElement leafElement) {
            return (leafElement instanceof ForeignLeafPsiElement) || (this.skipWhitespace && (leafElement instanceof PsiWhiteSpace)) || (this.skipComments && (leafElement instanceof PsiComment));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
        public void visitComposite(CompositeElement compositeElement) {
            int iCopyTo;
            if ((compositeElement instanceof LazyParseableElement) && (iCopyTo = ((LazyParseableElement) compositeElement).copyTo(this.buffer, this.end)) >= 0) {
                this.end = iCopyTo;
            } else {
                super.visitComposite(compositeElement);
            }
        }
    }
}
