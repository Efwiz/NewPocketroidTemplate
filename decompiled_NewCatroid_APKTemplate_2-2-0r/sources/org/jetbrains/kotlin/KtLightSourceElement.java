package org.jetbrains.kotlin;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0096\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0005H\u0016J\b\u0010 \u001a\u0004\u0018\u00010!R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/KtLightSourceElement;", "Lorg/jetbrains/kotlin/KtSourceElement;", "lighterASTNode", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "startOffset", "", "endOffset", "treeStructure", "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/KtSourceElementKind;", "(Lcom/intellij/lang/LighterASTNode;IILcom/intellij/util/diff/FlyweightCapableTreeStructure;Lorg/jetbrains/kotlin/KtSourceElementKind;)V", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "getElementType", "()Lcom/intellij/psi/tree/IElementType;", "getEndOffset", "()I", "getKind", "()Lorg/jetbrains/kotlin/KtSourceElementKind;", "getLighterASTNode", "()Lcom/intellij/lang/LighterASTNode;", "getStartOffset", "getTreeStructure", "()Lcom/intellij/util/diff/FlyweightCapableTreeStructure;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getElementTextInContextForDebug", "", "hashCode", "unwrapToKtPsiSourceElement", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightSourceElement extends KtSourceElement {
    private final int endOffset;
    private final KtSourceElementKind kind;
    private final LighterASTNode lighterASTNode;
    private final int startOffset;
    private final FlyweightCapableTreeStructure<LighterASTNode> treeStructure;

    @Override // org.jetbrains.kotlin.KtSourceElement
    public LighterASTNode getLighterASTNode() {
        return this.lighterASTNode;
    }

    @Override // org.jetbrains.kotlin.AbstractKtSourceElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.AbstractKtSourceElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.KtSourceElement
    public FlyweightCapableTreeStructure<LighterASTNode> getTreeStructure() {
        return this.treeStructure;
    }

    public /* synthetic */ KtLightSourceElement(LighterASTNode lighterASTNode, int i, int i2, FlyweightCapableTreeStructure flyweightCapableTreeStructure, KtRealSourceElementKind ktRealSourceElementKind, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(lighterASTNode, i, i2, flyweightCapableTreeStructure, (i3 & 16) != 0 ? KtRealSourceElementKind.INSTANCE : ktRealSourceElementKind);
    }

    @Override // org.jetbrains.kotlin.KtSourceElement
    public KtSourceElementKind getKind() {
        return this.kind;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightSourceElement(LighterASTNode lighterASTNode, int i, int i2, FlyweightCapableTreeStructure<LighterASTNode> treeStructure, KtSourceElementKind kind) {
        super(null);
        Intrinsics.checkNotNullParameter(lighterASTNode, "lighterASTNode");
        Intrinsics.checkNotNullParameter(treeStructure, "treeStructure");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.lighterASTNode = lighterASTNode;
        this.startOffset = i;
        this.endOffset = i2;
        this.treeStructure = treeStructure;
        this.kind = kind;
    }

    @Override // org.jetbrains.kotlin.KtSourceElement
    public IElementType getElementType() {
        IElementType tokenType = getLighterASTNode().getTokenType();
        Intrinsics.checkNotNullExpressionValue(tokenType, "getTokenType(...)");
        return tokenType;
    }

    public final KtPsiSourceElement unwrapToKtPsiSourceElement() {
        PsiElement psi;
        if (!(getTreeStructure() instanceof KtPsiSourceElement.WrappedTreeStructure) || (psi = ((KtPsiSourceElement.WrappedTreeStructure) getTreeStructure()).unwrap(getLighterASTNode()).getPsi()) == null) {
            return null;
        }
        KtSourceElementKind kind = getKind();
        if (kind instanceof KtRealSourceElementKind) {
            return new KtRealPsiSourceElement(psi);
        }
        if (kind instanceof KtFakeSourceElementKind) {
            return new KtFakeSourceElement(psi, (KtFakeSourceElementKind) kind);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.KtSourceElement
    public String getElementTextInContextForDebug() {
        return getTreeStructure().toString(getLighterASTNode()).toString();
    }

    @Override // org.jetbrains.kotlin.KtSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.KtLightSourceElement");
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) other;
        return Intrinsics.areEqual(getLighterASTNode(), ktLightSourceElement.getLighterASTNode()) && getStartOffset() == ktLightSourceElement.getStartOffset() && getEndOffset() == ktLightSourceElement.getEndOffset() && Intrinsics.areEqual(getTreeStructure(), ktLightSourceElement.getTreeStructure()) && Intrinsics.areEqual(getKind(), ktLightSourceElement.getKind());
    }

    @Override // org.jetbrains.kotlin.KtSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public int hashCode() {
        return (((((((getLighterASTNode().hashCode() * 31) + getStartOffset()) * 31) + getEndOffset()) * 31) + getTreeStructure().hashCode()) * 31) + getKind().hashCode();
    }
}
