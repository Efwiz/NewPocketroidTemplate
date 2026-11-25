package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.KotlinNameReferenceExpressionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtNameReferenceExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001c2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\u001cB\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\bJ5\u0010\f\u001a\u0002H\r\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00102\u0006\u0010\u0011\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0012J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0014H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\t\u0010\u000b¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtNameReferenceExpression;", "Lorg/jetbrains/kotlin/psi/KtExpressionImplStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinNameReferenceExpressionStub;", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinNameReferenceExpressionStub;)V", "isPlaceholder", "", "()Z", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getReferencedName", "", "getReferencedNameAsName", "Lorg/jetbrains/kotlin/name/Name;", "getReferencedNameElement", "getReferencedNameElementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtNameReferenceExpression extends KtExpressionImplStub<KotlinNameReferenceExpressionStub> implements KtSimpleNameExpression {
    private static final TokenSet NAME_REFERENCE_EXPRESSIONS;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNameReferenceExpression(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNameReferenceExpression(KotlinNameReferenceExpressionStub stub) {
        super(stub, KtStubElementTypes.REFERENCE_EXPRESSION);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public String getReferencedName() {
        KotlinNameReferenceExpressionStub kotlinNameReferenceExpressionStub = (KotlinNameReferenceExpressionStub) getStub();
        if (kotlinNameReferenceExpressionStub != null) {
            return kotlinNameReferenceExpressionStub.getReferencedName();
        }
        return KtSimpleNameExpressionImpl.INSTANCE.getReferencedNameImpl(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public Name getReferencedNameAsName() {
        return KtSimpleNameExpressionImpl.INSTANCE.getReferencedNameAsNameImpl(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public PsiElement getReferencedNameElement() {
        PsiElement psiElementFindChildByType = findChildByType(NAME_REFERENCE_EXPRESSIONS);
        return psiElementFindChildByType == null ? this : psiElementFindChildByType;
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public PsiElement getIdentifier() {
        return findChildByType(KtTokens.IDENTIFIER);
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public IElementType getReferencedNameElementType() {
        return KtSimpleNameExpressionImpl.INSTANCE.getReferencedNameElementTypeImpl(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImplStub, org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSimpleNameExpression(this, data);
    }

    public final boolean isPlaceholder() {
        String text;
        PsiElement identifier = getIdentifier();
        return (identifier == null || (text = identifier.getText()) == null || !text.equals("_")) ? false : true;
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.IDENTIFIER, KtTokens.THIS_KEYWORD, KtTokens.SUPER_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        NAME_REFERENCE_EXPRESSIONS = tokenSetCreate;
    }
}
