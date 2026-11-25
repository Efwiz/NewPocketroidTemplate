package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtForExpression;

/* JADX INFO: compiled from: Fir2IrVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0017\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"isChildOfForLoop", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/KtSourceElement;)Z", "operationToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "getOperationToken", "(Lorg/jetbrains/kotlin/KtSourceElement;)Lcom/intellij/psi/tree/IElementType;", "fir2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrVisitorKt {
    public static final boolean isChildOfForLoop(KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        if (ktSourceElement instanceof KtPsiSourceElement) {
            return ((KtPsiSourceElement) ktSourceElement).getPsi().getLightParent() instanceof KtForExpression;
        }
        LighterASTNode parent = ktSourceElement.getTreeStructure().getParent(ktSourceElement.getLighterASTNode());
        return Intrinsics.areEqual(parent != null ? parent.getTokenType() : null, KtNodeTypes.FOR);
    }

    public static final IElementType getOperationToken(KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        Intrinsics.areEqual(ktSourceElement.getElementType(), KtNodeTypes.BINARY_EXPRESSION);
        if (ktSourceElement instanceof KtPsiSourceElement) {
            PsiElement psi = ((KtPsiSourceElement) ktSourceElement).getPsi();
            KtBinaryExpression ktBinaryExpression = psi instanceof KtBinaryExpression ? (KtBinaryExpression) psi : null;
            if (ktBinaryExpression != null) {
                return ktBinaryExpression.getOperationToken();
            }
            return null;
        }
        FlyweightCapableTreeStructure<LighterASTNode> treeStructure = ktSourceElement.getTreeStructure();
        LighterASTNode lighterASTNode = ktSourceElement.getLighterASTNode();
        IElementType OPERATION_REFERENCE = KtNodeTypes.OPERATION_REFERENCE;
        Intrinsics.checkNotNullExpressionValue(OPERATION_REFERENCE, "OPERATION_REFERENCE");
        LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(treeStructure, lighterASTNode, OPERATION_REFERENCE);
        IElementType tokenType = lighterASTNodeFindChildByType != null ? lighterASTNodeFindChildByType.getTokenType() : null;
        if (tokenType != null) {
            return tokenType;
        }
        throw new IllegalStateException(("No operation reference for binary expression: " + ktSourceElement.getLighterASTNode()).toString());
    }
}
