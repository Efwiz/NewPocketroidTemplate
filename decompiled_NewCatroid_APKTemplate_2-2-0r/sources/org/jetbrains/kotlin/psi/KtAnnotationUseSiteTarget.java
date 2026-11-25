package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.stubs.KotlinAnnotationUseSiteTargetStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtAnnotationUseSiteTarget.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u000f\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0002¢\u0006\u0002\u0010\u0007J=\u0010\b\u001a\n \n*\u0004\u0018\u0001H\tH\t\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0016¢\u0006\u0002\u0010\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtAnnotationUseSiteTarget;", "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinAnnotationUseSiteTargetStub;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinAnnotationUseSiteTargetStub;)V", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getAnnotationUseSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtAnnotationUseSiteTarget extends KtElementImplStub<KotlinAnnotationUseSiteTargetStub> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtAnnotationUseSiteTarget(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtAnnotationUseSiteTarget(KotlinAnnotationUseSiteTargetStub stub) {
        super(stub, KtStubElementTypes.ANNOTATION_TARGET);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitAnnotationUseSiteTarget(this, data);
    }

    public final AnnotationUseSiteTarget getAnnotationUseSiteTarget() {
        KotlinAnnotationUseSiteTargetStub kotlinAnnotationUseSiteTargetStub = (KotlinAnnotationUseSiteTargetStub) getStub();
        String useSiteTarget = kotlinAnnotationUseSiteTargetStub != null ? kotlinAnnotationUseSiteTargetStub.getUseSiteTarget() : null;
        if (useSiteTarget != null) {
            try {
                return AnnotationUseSiteTarget.valueOf(useSiteTarget);
            } catch (IllegalArgumentException unused) {
            }
        }
        ASTNode node = getFirstChild().getNode();
        IElementType elementType = node.getElementType();
        if (Intrinsics.areEqual(elementType, KtTokens.FIELD_KEYWORD)) {
            return AnnotationUseSiteTarget.FIELD;
        }
        if (Intrinsics.areEqual(elementType, KtTokens.FILE_KEYWORD)) {
            return AnnotationUseSiteTarget.FILE;
        }
        if (Intrinsics.areEqual(elementType, KtTokens.PROPERTY_KEYWORD)) {
            return AnnotationUseSiteTarget.PROPERTY;
        }
        if (Intrinsics.areEqual(elementType, KtTokens.GET_KEYWORD)) {
            return AnnotationUseSiteTarget.PROPERTY_GETTER;
        }
        if (Intrinsics.areEqual(elementType, KtTokens.SET_KEYWORD)) {
            return AnnotationUseSiteTarget.PROPERTY_SETTER;
        }
        if (Intrinsics.areEqual(elementType, KtTokens.RECEIVER_KEYWORD)) {
            return AnnotationUseSiteTarget.RECEIVER;
        }
        if (Intrinsics.areEqual(elementType, KtTokens.PARAM_KEYWORD)) {
            return AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER;
        }
        if (Intrinsics.areEqual(elementType, KtTokens.SETPARAM_KEYWORD)) {
            return AnnotationUseSiteTarget.SETTER_PARAMETER;
        }
        if (Intrinsics.areEqual(elementType, KtTokens.DELEGATE_KEYWORD)) {
            return AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD;
        }
        throw new IllegalStateException("Unknown annotation target " + node.getText());
    }
}
