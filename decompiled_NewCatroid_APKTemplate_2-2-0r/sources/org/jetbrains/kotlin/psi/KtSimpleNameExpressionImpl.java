package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KtSimpleNameExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \u00152\u00020\u00012\u00020\u0002:\u0001\u0015B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J5\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\fJ\n\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtSimpleNameExpressionImpl;", "Lorg/jetbrains/kotlin/psi/KtExpressionImpl;", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getReferencedName", "", "getReferencedNameAsName", "Lorg/jetbrains/kotlin/name/Name;", "getReferencedNameElementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtSimpleNameExpressionImpl extends KtExpressionImpl implements KtSimpleNameExpression {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSimpleNameExpressionImpl(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public PsiElement getIdentifier() {
        return findChildByType(KtTokens.IDENTIFIER);
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public IElementType getReferencedNameElementType() {
        return INSTANCE.getReferencedNameElementTypeImpl(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSimpleNameExpression(this, data);
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public Name getReferencedNameAsName() {
        return INSTANCE.getReferencedNameAsNameImpl(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public String getReferencedName() {
        return INSTANCE.getReferencedNameImpl(this);
    }

    /* JADX INFO: compiled from: KtSimpleNameExpression.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtSimpleNameExpressionImpl$Companion;", "", "()V", "getReferencedNameAsNameImpl", "Lorg/jetbrains/kotlin/name/Name;", "expresssion", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "getReferencedNameElementTypeImpl", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "expression", "getReferencedNameImpl", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IElementType getReferencedNameElementTypeImpl(KtSimpleNameExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            ASTNode node = expression.getReferencedNameElement().getNode();
            Intrinsics.checkNotNull(node);
            IElementType elementType = node.getElementType();
            Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
            return elementType;
        }

        public final Name getReferencedNameAsNameImpl(KtSimpleNameExpression expresssion) {
            Intrinsics.checkNotNullParameter(expresssion, "expresssion");
            Name nameIdentifier = Name.identifier(expresssion.getReferencedName());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            return nameIdentifier;
        }

        public final String getReferencedNameImpl(KtSimpleNameExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            ASTNode node = expression.getReferencedNameElement().getNode();
            Intrinsics.checkNotNull(node);
            String text = node.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            String strUnquoteIdentifierOrFieldReference = KtPsiUtil.unquoteIdentifierOrFieldReference(text);
            Intrinsics.checkNotNullExpressionValue(strUnquoteIdentifierOrFieldReference, "unquoteIdentifierOrFieldReference(...)");
            return strUnquoteIdentifierOrFieldReference;
        }
    }
}
