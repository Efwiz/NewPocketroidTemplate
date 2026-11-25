package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: compiled from: KtContainerNodeForControlStructureBody.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtContainerNodeForControlStructureBody;", "Lorg/jetbrains/kotlin/psi/KtContainerNode;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtContainerNodeForControlStructureBody extends KtContainerNode {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtContainerNodeForControlStructureBody(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    public final KtExpression getExpression() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }
}
