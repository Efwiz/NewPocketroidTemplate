package org.jetbrains.kotlin;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.TreeBackedLighterAST;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PsiUtilsKt;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0001#B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\b\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\nH\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\fR!\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00188VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0012\u001a\u0004\b\u0019\u0010\u001a\u0082\u0001\u0002$%¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/KtPsiSourceElement;", "Lorg/jetbrains/kotlin/KtSourceElement;", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)V", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "getElementType", "()Lcom/intellij/psi/tree/IElementType;", "endOffset", "", "getEndOffset", "()I", "lighterASTNode", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "getLighterASTNode", "()Lcom/intellij/lang/LighterASTNode;", "lighterASTNode$delegate", "Lkotlin/Lazy;", "getPsi", "()Lcom/intellij/psi/PsiElement;", "startOffset", "getStartOffset", "treeStructure", "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "getTreeStructure", "()Lcom/intellij/util/diff/FlyweightCapableTreeStructure;", "treeStructure$delegate", Namer.EQUALS_METHOD_NAME, "", "other", "", "getElementTextInContextForDebug", "", "hashCode", "WrappedTreeStructure", "Lorg/jetbrains/kotlin/KtFakeSourceElement;", "Lorg/jetbrains/kotlin/KtRealPsiSourceElement;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtPsiSourceElement extends KtSourceElement {

    /* JADX INFO: renamed from: lighterASTNode$delegate, reason: from kotlin metadata */
    private final Lazy lighterASTNode;
    private final PsiElement psi;

    /* JADX INFO: renamed from: treeStructure$delegate, reason: from kotlin metadata */
    private final Lazy treeStructure;

    public /* synthetic */ KtPsiSourceElement(PsiElement psiElement, DefaultConstructorMarker defaultConstructorMarker) {
        this(psiElement);
    }

    private KtPsiSourceElement(PsiElement psiElement) {
        super(null);
        this.psi = psiElement;
        this.lighterASTNode = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<LighterASTNode>() { // from class: org.jetbrains.kotlin.KtPsiSourceElement$lighterASTNode$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LighterASTNode invoke() {
                LighterASTNode lighterASTNodeWrap = TreeBackedLighterAST.wrap(this.this$0.getPsi().getNode());
                Intrinsics.checkNotNullExpressionValue(lighterASTNodeWrap, "wrap(...)");
                return lighterASTNodeWrap;
            }
        });
        this.treeStructure = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<WrappedTreeStructure>() { // from class: org.jetbrains.kotlin.KtPsiSourceElement$treeStructure$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtPsiSourceElement.WrappedTreeStructure invoke() {
                PsiFile containingFile = this.this$0.getPsi().getContainingFile();
                Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
                return new KtPsiSourceElement.WrappedTreeStructure(containingFile);
            }
        });
    }

    public final PsiElement getPsi() {
        return this.psi;
    }

    @Override // org.jetbrains.kotlin.KtSourceElement
    public IElementType getElementType() {
        ASTNode node = this.psi.getNode();
        if (node != null) {
            return node.getElementType();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.AbstractKtSourceElement
    public int getStartOffset() {
        return this.psi.getTextRange().getStartOffset();
    }

    @Override // org.jetbrains.kotlin.AbstractKtSourceElement
    public int getEndOffset() {
        return this.psi.getTextRange().getEndOffset();
    }

    @Override // org.jetbrains.kotlin.KtSourceElement
    public LighterASTNode getLighterASTNode() {
        return (LighterASTNode) this.lighterASTNode.getValue();
    }

    @Override // org.jetbrains.kotlin.KtSourceElement
    public FlyweightCapableTreeStructure<LighterASTNode> getTreeStructure() {
        return (FlyweightCapableTreeStructure) this.treeStructure.getValue();
    }

    @Override // org.jetbrains.kotlin.KtSourceElement
    public String getElementTextInContextForDebug() {
        return PsiUtilsKt.getElementTextWithContext(this.psi);
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J'\u0010\b\u001a\u00020\t2\u0010\u0010\n\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0002\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016¢\u0006\u0002\u0010\u000eJ$\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00022\u0012\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000b0\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0010\u001a\u00020\u0002H\u0016J\b\u0010\u0017\u001a\u00020\u0002H\u0016J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016J\u0013\u0010\u001b\u001a\u00070\u001c¢\u0006\u0002\b\u001d2\u0006\u0010\u0010\u001a\u00020\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/KtPsiSourceElement$WrappedTreeStructure;", "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "file", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "(Lcom/intellij/psi/PsiFile;)V", "lighterAST", "Lorg/jetbrains/kotlin/com/intellij/lang/TreeBackedLighterAST;", "disposeChildren", "", "p0", "", "p1", "", "([Lcom/intellij/lang/LighterASTNode;I)V", "getChildren", "node", "nodesRef", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Ref;", "getEndOffset", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getParent", "getRoot", "getStartOffset", "toString", "", "unwrap", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "Lorg/jetbrains/annotations/NotNull;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WrappedTreeStructure implements FlyweightCapableTreeStructure<LighterASTNode> {
        private final TreeBackedLighterAST lighterAST;

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public void disposeChildren(LighterASTNode[] lighterASTNodeArr, int i) {
        }

        public WrappedTreeStructure(PsiFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            this.lighterAST = new TreeBackedLighterAST(file.getNode());
        }

        public final ASTNode unwrap(LighterASTNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            ASTNode aSTNodeUnwrap = this.lighterAST.unwrap(node);
            Intrinsics.checkNotNullExpressionValue(aSTNodeUnwrap, "unwrap(...)");
            return aSTNodeUnwrap;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public CharSequence toString(LighterASTNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            String text = unwrap(node).getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            return text;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public LighterASTNode getRoot() {
            LighterASTNode root = this.lighterAST.getRoot();
            Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
            return root;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public LighterASTNode getParent(LighterASTNode node) {
            ASTNode node2;
            Intrinsics.checkNotNullParameter(node, "node");
            PsiElement parent = unwrap(node).getPsi().getParent();
            if (parent == null || (node2 = parent.getNode()) == null) {
                return null;
            }
            return TreeBackedLighterAST.wrap(node2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public int getChildren(LighterASTNode node, Ref<LighterASTNode[]> nodesRef) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(nodesRef, "nodesRef");
            PsiElement psi = unwrap(node).getPsi();
            ArrayList arrayList = new ArrayList();
            for (PsiElement firstChild = psi.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                arrayList.add(firstChild);
            }
            if (arrayList.isEmpty()) {
                nodesRef.set(LighterASTNode.EMPTY_ARRAY);
            } else {
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    arrayList3.add(TreeBackedLighterAST.wrap(((PsiElement) it.next()).getNode()));
                }
                nodesRef.set(arrayList3.toArray(new LighterASTNode[0]));
            }
            return arrayList.size();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public int getStartOffset(LighterASTNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            PsiElement psi = unwrap(node).getPsi();
            Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
            return getStartOffset(psi);
        }

        private final int getStartOffset(PsiElement element) {
            PsiElement firstChild = element.getFirstChild();
            if (firstChild != null) {
                while (true) {
                    if (!(firstChild instanceof PsiComment) && !(firstChild instanceof PsiWhiteSpace)) {
                        break;
                    }
                    firstChild = firstChild.getNextSibling();
                }
                if (firstChild != null) {
                    return getStartOffset(firstChild);
                }
            }
            return element.getTextRange().getStartOffset();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure
        public int getEndOffset(LighterASTNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            PsiElement psi = unwrap(node).getPsi();
            Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
            return getEndOffset(psi);
        }

        private final int getEndOffset(PsiElement element) {
            PsiElement lastChild = element.getLastChild();
            if (lastChild != null) {
                while (true) {
                    if (!(lastChild instanceof PsiComment) && !(lastChild instanceof PsiWhiteSpace)) {
                        break;
                    }
                    lastChild = lastChild.getPrevSibling();
                }
                if (lastChild != null) {
                    return getEndOffset(lastChild);
                }
            }
            return element.getTextRange().getEndOffset();
        }
    }

    @Override // org.jetbrains.kotlin.KtSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.KtPsiSourceElement");
        return Intrinsics.areEqual(this.psi, ((KtPsiSourceElement) other).psi);
    }

    @Override // org.jetbrains.kotlin.KtSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public int hashCode() {
        return this.psi.hashCode();
    }
}
