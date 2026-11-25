package org.jetbrains.kotlin.psi;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtIntersectionType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00000\u00020\u00012\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002¢\u0006\u0002\u0010\bJ5\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0016¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u0014H\u0016¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtIntersectionType;", "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getLeftTypeRef", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "getRightTypeRef", "getTypeArgumentsAsTypes", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtIntersectionType extends KtElementImplStub<KotlinPlaceHolderStub<KtIntersectionType>> implements KtTypeElement {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtIntersectionType(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtIntersectionType(KotlinPlaceHolderStub<KtIntersectionType> stub) {
        super(stub, KtStubElementTypes.INTERSECTION_TYPE);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeElement
    public List<KtTypeReference> getTypeArgumentsAsTypes() {
        return CollectionsKt.emptyList();
    }

    public final KtTypeReference getLeftTypeRef() {
        List<PsiT> stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.TYPE_REFERENCE);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        return (KtTypeReference) CollectionsKt.getOrNull(stubOrPsiChildrenAsList, 0);
    }

    public final KtTypeReference getRightTypeRef() {
        List<PsiT> stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.TYPE_REFERENCE);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        return (KtTypeReference) CollectionsKt.getOrNull(stubOrPsiChildrenAsList, 1);
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitIntersectionType(this, data);
    }
}
