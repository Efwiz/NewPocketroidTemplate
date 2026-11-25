package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KtExpressionWithLabel.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J=\u0010\t\u001a\n \u000b*\u0004\u0018\u0001H\nH\n\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\f0\u000e2\u0006\u0010\u000f\u001a\u0002H\fH\u0016¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012J\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtExpressionWithLabel;", "Lorg/jetbrains/kotlin/psi/KtExpressionImpl;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "labelQualifier", "Lorg/jetbrains/kotlin/psi/KtContainerNode;", "getLabelQualifier", "()Lorg/jetbrains/kotlin/psi/KtContainerNode;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getLabelName", "", "getLabelNameAsName", "Lorg/jetbrains/kotlin/name/Name;", "getTargetLabel", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtExpressionWithLabel extends KtExpressionImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtExpressionWithLabel(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    public final KtSimpleNameExpression getTargetLabel() {
        KtContainerNode labelQualifier = getLabelQualifier();
        KtSimpleNameExpression ktSimpleNameExpression = labelQualifier != null ? (KtSimpleNameExpression) labelQualifier.findChildByType(KtNodeTypes.LABEL) : null;
        if (ktSimpleNameExpression instanceof KtSimpleNameExpression) {
            return ktSimpleNameExpression;
        }
        return null;
    }

    public final KtContainerNode getLabelQualifier() {
        return (KtContainerNode) findChildByType(KtNodeTypes.LABEL_QUALIFIER);
    }

    public final String getLabelName() {
        KtSimpleNameExpression targetLabel = getTargetLabel();
        if (targetLabel != null) {
            return targetLabel.getReferencedName();
        }
        return null;
    }

    public final Name getLabelNameAsName() {
        KtSimpleNameExpression targetLabel = getTargetLabel();
        if (targetLabel != null) {
            return targetLabel.getReferencedNameAsName();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitExpressionWithLabel(this, data);
    }
}
