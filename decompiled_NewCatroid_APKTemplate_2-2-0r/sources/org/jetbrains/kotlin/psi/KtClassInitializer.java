package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtAnonymousInitializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00000\u00020\u00012\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002¢\u0006\u0002\u0010\bJ=\u0010\u0017\u001a\n \u0019*\u0004\u0018\u0001H\u0018H\u0018\"\u0004\b\u0000\u0010\u0018\"\u0004\b\u0001\u0010\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u001a0\u001c2\u0006\u0010\u001d\u001a\u0002H\u001aH\u0016¢\u0006\u0002\u0010\u001eR\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u00128F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0014¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtClassInitializer;", "Lorg/jetbrains/kotlin/psi/KtDeclarationStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;)V", "body", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getBody", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "containingDeclaration", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getContainingDeclaration", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "initKeyword", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getInitKeyword", "()Lcom/intellij/psi/PsiElement;", "openBraceNode", "getOpenBraceNode", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtClassInitializer extends KtDeclarationStub<KotlinPlaceHolderStub<KtClassInitializer>> implements KtAnonymousInitializer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClassInitializer(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClassInitializer(KotlinPlaceHolderStub<KtClassInitializer> stub) {
        super(stub, KtStubElementTypes.CLASS_INITIALIZER);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitClassInitializer(this, data);
    }

    @Override // org.jetbrains.kotlin.psi.KtAnonymousInitializer
    public KtExpression getBody() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    public final PsiElement getOpenBraceNode() {
        KtExpression body = getBody();
        KtBlockExpression ktBlockExpression = body instanceof KtBlockExpression ? (KtBlockExpression) body : null;
        if (ktBlockExpression != null) {
            return ktBlockExpression.getLBrace();
        }
        return null;
    }

    public final PsiElement getInitKeyword() {
        PsiElement psiElementFindChildByType = findChildByType(KtTokens.INIT_KEYWORD);
        Intrinsics.checkNotNull(psiElementFindChildByType);
        return psiElementFindChildByType;
    }

    @Override // org.jetbrains.kotlin.psi.KtAnonymousInitializer
    public KtClassOrObject getContainingDeclaration() {
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(this, KtClassOrObject.class, true);
        if (parentOfType != null) {
            return (KtClassOrObject) parentOfType;
        }
        throw new AssertionError("Should only be present in class or object");
    }
}
