package org.jetbrains.kotlin.idea.references;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.MultiRangeReference;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: KtArrayAccessReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\b&\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\u0015B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0017\u0010\r\u001a\t\u0018\u00010\u000e¢\u0006\u0002\b\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\r\u0010\u0012\u001a\u00070\u000e¢\u0006\u0002\b\u000fH\u0016J\u0013\u0010\u0013\u001a\r\u0012\t\u0012\u00070\u000e¢\u0006\u0002\b\u000f0\u0014H\u0016R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtArrayAccessReference;", "Lorg/jetbrains/kotlin/idea/references/KtSimpleReference;", "Lorg/jetbrains/kotlin/psi/KtArrayAccessExpression;", "Lorg/jetbrains/kotlin/com/intellij/psi/MultiRangeReference;", "expression", "(Lorg/jetbrains/kotlin/psi/KtArrayAccessExpression;)V", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "canRename", "", "getBracketRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "Lorg/jetbrains/annotations/NotNull;", "bracketToken", "Lorg/jetbrains/kotlin/lexer/KtToken;", "getRangeInElement", "getRanges", "", "Companion", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtArrayAccessReference extends KtSimpleReference<KtArrayAccessExpression> implements MultiRangeReference {
    private static final List<Name> NAMES = CollectionsKt.listOf((Object[]) new Name[]{OperatorNameConventions.GET, OperatorNameConventions.SET});

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference
    public boolean canRename() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtArrayAccessReference(KtArrayAccessExpression expression) {
        super(expression);
        Intrinsics.checkNotNullParameter(expression, "expression");
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        return NAMES;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        TextRange textRangeShiftRight = ((KtArrayAccessExpression) getPsiElement()).getTextRange().shiftRight(-((KtArrayAccessExpression) getPsiElement()).getTextOffset());
        Intrinsics.checkNotNullExpressionValue(textRangeShiftRight, "shiftRight(...)");
        return textRangeShiftRight;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final TextRange getBracketRange(KtToken bracketToken) {
        TextRange textRange;
        ASTNode aSTNodeFindChildByType = ((KtArrayAccessExpression) getExpression()).getIndicesNode().getNode().findChildByType(bracketToken);
        if (aSTNodeFindChildByType == null || (textRange = aSTNodeFindChildByType.getTextRange()) == null) {
            return null;
        }
        return textRange.shiftRight(-((KtArrayAccessExpression) getExpression()).getTextOffset());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.MultiRangeReference
    public List<TextRange> getRanges() {
        KtSingleValueToken LBRACKET = KtTokens.LBRACKET;
        Intrinsics.checkNotNullExpressionValue(LBRACKET, "LBRACKET");
        KtSingleValueToken RBRACKET = KtTokens.RBRACKET;
        Intrinsics.checkNotNullExpressionValue(RBRACKET, "RBRACKET");
        return CollectionsKt.listOfNotNull((Object[]) new TextRange[]{getBracketRange(LBRACKET), getBracketRange(RBRACKET)});
    }
}
