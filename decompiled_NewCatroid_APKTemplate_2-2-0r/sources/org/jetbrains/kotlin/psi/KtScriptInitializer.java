package org.jetbrains.kotlin.psi;

import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: KtAnonymousInitializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J=\u0010\u0010\u001a\n \u0012*\u0004\u0018\u0001H\u0011H\u0011\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017J\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtScriptInitializer;", "Lorg/jetbrains/kotlin/psi/KtDeclarationImpl;", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "body", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getBody", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "containingDeclaration", "Lorg/jetbrains/kotlin/psi/KtScript;", "getContainingDeclaration", "()Lorg/jetbrains/kotlin/psi/KtScript;", "modificationStamp", "Ljava/util/concurrent/atomic/AtomicLong;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getModificationStamp", "", "subtreeChanged", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtScriptInitializer extends KtDeclarationImpl implements KtAnonymousInitializer {
    private final AtomicLong modificationStamp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtScriptInitializer(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
        this.modificationStamp = new AtomicLong();
    }

    @Override // org.jetbrains.kotlin.psi.KtAnonymousInitializer
    public KtExpression getBody() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtAnonymousInitializer
    public KtScript getContainingDeclaration() {
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(this, KtScript.class, true);
        if (parentOfType != null) {
            return (KtScript) parentOfType;
        }
        throw new AssertionError("Should only be present in script");
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitScriptInitializer(this, data);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        super.subtreeChanged();
        this.modificationStamp.getAndIncrement();
    }

    public final long getModificationStamp() {
        return this.modificationStamp.get();
    }
}
