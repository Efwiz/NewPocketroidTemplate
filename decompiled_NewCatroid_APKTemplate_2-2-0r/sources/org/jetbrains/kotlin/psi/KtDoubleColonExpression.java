package org.jetbrains.kotlin.psi;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: KtDoubleColonExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J5\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u00120\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0016J\n\u0010\u0017\u001a\u0004\u0018\u00010\u0003H&J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0001H\u0016R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\tR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0005R\u0016\u0010\r\u001a\u0004\u0018\u00010\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtDoubleColonExpression;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "doubleColonTokenReference", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getDoubleColonTokenReference", "()Lcom/intellij/psi/PsiElement;", "hasQuestionMarks", "", "getHasQuestionMarks", "()Z", "isEmptyLHS", "lhs", "getLhs", "receiverExpression", "getReceiverExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "findColonColon", "setReceiverExpression", "", "newReceiverExpression", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtDoubleColonExpression extends KtExpression {
    PsiElement findColonColon();

    default KtExpression getReceiverExpression() {
        PsiElement psi = getNode().getFirstChildNode().getPsi();
        if (psi instanceof KtExpression) {
            return (KtExpression) psi;
        }
        return null;
    }

    default boolean getHasQuestionMarks() {
        Iterator it = SequencesKt.generateSequence(getNode().getFirstChildNode(), KtDoubleColonExpression$hasQuestionMarks$1.INSTANCE).iterator();
        while (it.hasNext()) {
            IElementType elementType = ((ASTNode) it.next()).getElementType();
            if (Intrinsics.areEqual(elementType, KtTokens.QUEST)) {
                return true;
            }
            if (Intrinsics.areEqual(elementType, KtTokens.COLONCOLON)) {
                return false;
            }
        }
        throw new IllegalStateException(("Double colon expression must have '::': " + getText()).toString());
    }

    default PsiElement getDoubleColonTokenReference() {
        PsiElement psiElementFindColonColon = findColonColon();
        Intrinsics.checkNotNull(psiElementFindColonColon);
        return psiElementFindColonColon;
    }

    default PsiElement getLhs() {
        return getDoubleColonTokenReference().getPrevSibling();
    }

    default void setReceiverExpression(KtExpression newReceiverExpression) {
        Intrinsics.checkNotNullParameter(newReceiverExpression, "newReceiverExpression");
        KtExpression receiverExpression = getReceiverExpression();
        if (receiverExpression == null || receiverExpression.replace(newReceiverExpression) == null) {
            addBefore(newReceiverExpression, getDoubleColonTokenReference());
        }
    }

    default boolean isEmptyLHS() {
        return getLhs() == null;
    }

    @Override // org.jetbrains.kotlin.psi.KtExpression
    default <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitDoubleColonExpression(this, data);
    }
}
