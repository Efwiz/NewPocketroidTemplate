package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.KotlinContextReceiverStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtContextReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u000f\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0002¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u0004\u0018\u00010\tJ\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\b\u0010\f\u001a\u0004\u0018\u00010\tJ\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtContextReceiver;", "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinContextReceiverStub;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinContextReceiverStub;)V", "labelName", "", "labelNameAsName", "Lorg/jetbrains/kotlin/name/Name;", "name", "targetLabel", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "typeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtContextReceiver extends KtElementImplStub<KotlinContextReceiverStub> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtContextReceiver(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtContextReceiver(KotlinContextReceiverStub stub) {
        super(stub, KtStubElementTypes.CONTEXT_RECEIVER);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    public final KtSimpleNameExpression targetLabel() {
        KtContainerNode ktContainerNode = (KtContainerNode) findChildByType(KtNodeTypes.LABEL_QUALIFIER);
        if (ktContainerNode != null) {
            return (KtSimpleNameExpression) ktContainerNode.findChildByType(KtNodeTypes.LABEL);
        }
        return null;
    }

    public final String labelName() {
        KotlinContextReceiverStub kotlinContextReceiverStub = (KotlinContextReceiverStub) getStub();
        if (kotlinContextReceiverStub == null) {
            KtSimpleNameExpression ktSimpleNameExpressionTargetLabel = targetLabel();
            if (ktSimpleNameExpressionTargetLabel != null) {
                return ktSimpleNameExpressionTargetLabel.getReferencedName();
            }
            return null;
        }
        return kotlinContextReceiverStub.getLabel();
    }

    public final Name labelNameAsName() {
        KotlinContextReceiverStub kotlinContextReceiverStub = (KotlinContextReceiverStub) getStub();
        if (kotlinContextReceiverStub != null) {
            String label = kotlinContextReceiverStub.getLabel();
            if (label != null) {
                return Name.identifier(label);
            }
            return null;
        }
        KtSimpleNameExpression ktSimpleNameExpressionTargetLabel = targetLabel();
        if (ktSimpleNameExpressionTargetLabel != null) {
            return ktSimpleNameExpressionTargetLabel.getReferencedNameAsName();
        }
        return null;
    }

    public final KtTypeReference typeReference() {
        return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
    }

    public final String name() {
        String strLabelName = labelName();
        if (strLabelName != null) {
            return strLabelName;
        }
        KtTypeReference ktTypeReferenceTypeReference = typeReference();
        if (ktTypeReferenceTypeReference != null) {
            return ktTypeReferenceTypeReference.nameForReceiverLabel();
        }
        return null;
    }
}
