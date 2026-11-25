package org.jetbrains.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H¦\u0002J\b\u0010\u0017\u001a\u00020\u0018H&J\b\u0010\u0019\u001a\u00020\u001aH&R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u0082\u0001\u0002\u001b\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/KtSourceElement;", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "()V", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "getElementType", "()Lcom/intellij/psi/tree/IElementType;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/KtSourceElementKind;", "getKind", "()Lorg/jetbrains/kotlin/KtSourceElementKind;", "lighterASTNode", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "getLighterASTNode", "()Lcom/intellij/lang/LighterASTNode;", "treeStructure", "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "getTreeStructure", "()Lcom/intellij/util/diff/FlyweightCapableTreeStructure;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getElementTextInContextForDebug", "", "hashCode", "", "Lorg/jetbrains/kotlin/KtLightSourceElement;", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtSourceElement extends AbstractKtSourceElement {
    public /* synthetic */ KtSourceElement(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.AbstractKtSourceElement
    public abstract boolean equals(Object other);

    public abstract String getElementTextInContextForDebug();

    public abstract IElementType getElementType();

    public abstract KtSourceElementKind getKind();

    public abstract LighterASTNode getLighterASTNode();

    public abstract FlyweightCapableTreeStructure<LighterASTNode> getTreeStructure();

    @Override // org.jetbrains.kotlin.AbstractKtSourceElement
    public abstract int hashCode();

    private KtSourceElement() {
        super(null);
    }
}
