package org.jetbrains.kotlin.psi.stubs.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;

/* JADX INFO: compiled from: KtStringTemplateExpressionElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\b\b\u0001\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtStringTemplateExpressionElementType;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtPlaceHolderStubElementType;", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "debugName", "", "(Ljava/lang/String;)V", "shouldCreateStub", "", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtStringTemplateExpressionElementType extends KtPlaceHolderStubElementType<KtStringTemplateExpression> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtStringTemplateExpressionElementType(String debugName) {
        super(debugName, KtStringTemplateExpression.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public boolean shouldCreateStub(ASTNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        ASTNode treeParent = node.getTreeParent();
        if (Intrinsics.areEqual(treeParent != null ? treeParent.getElementType() : null, KtStubElementTypes.VALUE_ARGUMENT)) {
            return super.shouldCreateStub(node);
        }
        return false;
    }
}
