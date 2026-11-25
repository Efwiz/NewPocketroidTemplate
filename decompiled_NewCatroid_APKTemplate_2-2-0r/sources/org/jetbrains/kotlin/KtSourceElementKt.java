package org.jetbrains.kotlin;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\n\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u001a\n\u0010\u0010\u001a\u00020\u0007*\u00020\u0007\u001a9\u0010\u0011\u001a\u00020\u0012*\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000eH\u0086\b\u001a\u0017\u0010\u0018\u001a\u00020\u0019*\u00020\u00012\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0086\b\"\u0019\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u0004\u0018\u00010\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u001a"}, d2 = {"psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "getPsi", "(Lorg/jetbrains/kotlin/AbstractKtSourceElement;)Lcom/intellij/psi/PsiElement;", "text", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "getText", "(Lorg/jetbrains/kotlin/KtSourceElement;)Ljava/lang/CharSequence;", "fakeElement", "newKind", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "startOffset", "", "endOffset", "realElement", "toKtLightSourceElement", "Lorg/jetbrains/kotlin/KtLightSourceElement;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/KtSourceElementKind;", "toKtPsiSourceElement", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtSourceElementKt {
    public static /* synthetic */ KtSourceElement fakeElement$default(KtSourceElement ktSourceElement, KtFakeSourceElementKind ktFakeSourceElementKind, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = -1;
        }
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        return fakeElement(ktSourceElement, ktFakeSourceElementKind, i, i2);
    }

    public static final KtSourceElement fakeElement(KtSourceElement ktSourceElement, KtFakeSourceElementKind newKind, int i, int i2) {
        KtFakeSourceElementWithOffsets ktFakeSourceElement;
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        Intrinsics.checkNotNullParameter(newKind, "newKind");
        if (Intrinsics.areEqual(ktSourceElement.getKind(), newKind)) {
            return ktSourceElement;
        }
        if (ktSourceElement instanceof KtLightSourceElement) {
            LighterASTNode lighterASTNode = ktSourceElement.getLighterASTNode();
            if (i == -1) {
                i = ktSourceElement.getStartOffset();
            }
            int i3 = i;
            if (i2 == -1) {
                i2 = ktSourceElement.getEndOffset();
            }
            return new KtLightSourceElement(lighterASTNode, i3, i2, ktSourceElement.getTreeStructure(), newKind);
        }
        if (!(ktSourceElement instanceof KtPsiSourceElement)) {
            throw new NoWhenBranchMatchedException();
        }
        if (i != -1 && i2 != -1) {
            ktFakeSourceElement = new KtFakeSourceElementWithOffsets(((KtPsiSourceElement) ktSourceElement).getPsi(), newKind, i, i2);
        } else {
            ktFakeSourceElement = new KtFakeSourceElement(((KtPsiSourceElement) ktSourceElement).getPsi(), newKind);
        }
        return ktFakeSourceElement;
    }

    public static final KtSourceElement realElement(KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        if (ktSourceElement instanceof KtRealPsiSourceElement) {
            return ktSourceElement;
        }
        if (ktSourceElement instanceof KtLightSourceElement) {
            return new KtLightSourceElement(ktSourceElement.getLighterASTNode(), ktSourceElement.getStartOffset(), ktSourceElement.getEndOffset(), ktSourceElement.getTreeStructure(), KtRealSourceElementKind.INSTANCE);
        }
        if (ktSourceElement instanceof KtPsiSourceElement) {
            return new KtRealPsiSourceElement(((KtPsiSourceElement) ktSourceElement).getPsi());
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final PsiElement getPsi(AbstractKtSourceElement abstractKtSourceElement) {
        KtPsiSourceElement ktPsiSourceElement = abstractKtSourceElement instanceof KtPsiSourceElement ? (KtPsiSourceElement) abstractKtSourceElement : null;
        if (ktPsiSourceElement != null) {
            return ktPsiSourceElement.getPsi();
        }
        return null;
    }

    public static final CharSequence getText(KtSourceElement ktSourceElement) {
        if (ktSourceElement instanceof KtPsiSourceElement) {
            return ((KtPsiSourceElement) ktSourceElement).getPsi().getText();
        }
        if (ktSourceElement instanceof KtLightSourceElement) {
            return ktSourceElement.getTreeStructure().toString(ktSourceElement.getLighterASTNode());
        }
        return null;
    }

    public static /* synthetic */ KtPsiSourceElement toKtPsiSourceElement$default(PsiElement psiElement, KtSourceElementKind kind, int i, Object obj) {
        if ((i & 1) != 0) {
            kind = KtRealSourceElementKind.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (kind instanceof KtRealSourceElementKind) {
            return new KtRealPsiSourceElement(psiElement);
        }
        if (kind instanceof KtFakeSourceElementKind) {
            return new KtFakeSourceElement(psiElement, (KtFakeSourceElementKind) kind);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final KtPsiSourceElement toKtPsiSourceElement(PsiElement psiElement, KtSourceElementKind kind) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (kind instanceof KtRealSourceElementKind) {
            return new KtRealPsiSourceElement(psiElement);
        }
        if (kind instanceof KtFakeSourceElementKind) {
            return new KtFakeSourceElement(psiElement, (KtFakeSourceElementKind) kind);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static /* synthetic */ KtLightSourceElement toKtLightSourceElement$default(LighterASTNode lighterASTNode, FlyweightCapableTreeStructure tree, KtSourceElementKind ktSourceElementKind, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            ktSourceElementKind = KtRealSourceElementKind.INSTANCE;
        }
        KtSourceElementKind kind = ktSourceElementKind;
        if ((i3 & 4) != 0) {
            i = lighterASTNode.getStartOffset();
        }
        int i4 = i;
        if ((i3 & 8) != 0) {
            i2 = lighterASTNode.getEndOffset();
        }
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return new KtLightSourceElement(lighterASTNode, i4, i2, tree, kind);
    }

    public static final KtLightSourceElement toKtLightSourceElement(LighterASTNode lighterASTNode, FlyweightCapableTreeStructure<LighterASTNode> tree, KtSourceElementKind kind, int i, int i2) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return new KtLightSourceElement(lighterASTNode, i, i2, tree, kind);
    }
}
