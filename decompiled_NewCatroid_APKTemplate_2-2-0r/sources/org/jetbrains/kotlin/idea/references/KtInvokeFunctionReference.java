package org.jetbrains.kotlin.idea.references;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.MultiRangeReference;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: KtInvokeFunctionReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\b&\u0018\u0000 \u00142\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\u0014B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0013H\u0016R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtInvokeFunctionReference;", "Lorg/jetbrains/kotlin/idea/references/KtSimpleReference;", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "Lorg/jetbrains/kotlin/com/intellij/psi/MultiRangeReference;", "expression", "(Lorg/jetbrains/kotlin/psi/KtCallExpression;)V", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "canRename", "", "getRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "getRangeInElement", "getRanges", "", "Companion", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtInvokeFunctionReference extends KtSimpleReference<KtCallExpression> implements MultiRangeReference {
    private static final List<Name> NAMES = CollectionsKt.listOf(OperatorNameConventions.INVOKE);

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference
    public boolean canRename() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtInvokeFunctionReference(KtCallExpression expression) {
        super(expression);
        Intrinsics.checkNotNullParameter(expression, "expression");
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        return NAMES;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        TextRange textRangeShiftRight = ((KtCallExpression) getPsiElement()).getTextRange().shiftRight(-((KtCallExpression) getPsiElement()).getTextOffset());
        Intrinsics.checkNotNullExpressionValue(textRangeShiftRight, "shiftRight(...)");
        return textRangeShiftRight;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.MultiRangeReference
    public List<TextRange> getRanges() {
        ArrayList arrayList = new ArrayList();
        KtValueArgumentList valueArgumentList = ((KtCallExpression) getExpression()).getValueArgumentList();
        if (valueArgumentList != null) {
            List<KtValueArgument> arguments = valueArgumentList.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            if (!arguments.isEmpty()) {
                ASTNode node = valueArgumentList.getNode();
                Intrinsics.checkNotNullExpressionValue(node, "getNode(...)");
                ASTNode aSTNodeFindChildByType = node.findChildByType(KtTokens.LPAR);
                if (aSTNodeFindChildByType != null) {
                    arrayList.add(getRange(aSTNodeFindChildByType));
                }
                ASTNode aSTNodeFindChildByType2 = node.findChildByType(KtTokens.RPAR);
                if (aSTNodeFindChildByType2 != null) {
                    arrayList.add(getRange(aSTNodeFindChildByType2));
                }
            } else {
                ASTNode node2 = valueArgumentList.getNode();
                Intrinsics.checkNotNullExpressionValue(node2, "getNode(...)");
                arrayList.add(getRange(node2));
            }
        }
        List<KtLambdaArgument> lambdaArguments = ((KtCallExpression) getExpression()).getLambdaArguments();
        Intrinsics.checkNotNullExpressionValue(lambdaArguments, "getLambdaArguments(...)");
        Iterator<KtLambdaArgument> it = lambdaArguments.iterator();
        while (it.hasNext()) {
            KtLambdaExpression lambdaExpression = it.next().getLambdaExpression();
            if (lambdaExpression != null) {
                ASTNode leftCurlyBrace = lambdaExpression.getLeftCurlyBrace();
                Intrinsics.checkNotNullExpressionValue(leftCurlyBrace, "getLeftCurlyBrace(...)");
                arrayList.add(getRange(leftCurlyBrace));
                ASTNode rightCurlyBrace = lambdaExpression.getRightCurlyBrace();
                if (rightCurlyBrace != null) {
                    arrayList.add(getRange(rightCurlyBrace));
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final TextRange getRange(ASTNode node) {
        TextRange textRangeShiftRight = node.getTextRange().shiftRight(-((KtCallExpression) getExpression()).getTextOffset());
        Intrinsics.checkNotNullExpressionValue(textRangeShiftRight, "shiftRight(...)");
        return textRangeShiftRight;
    }
}
