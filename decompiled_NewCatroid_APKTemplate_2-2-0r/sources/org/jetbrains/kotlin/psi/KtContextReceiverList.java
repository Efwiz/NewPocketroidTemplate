package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtContextReceiverList.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00000\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0015\b\u0016\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002¢\u0006\u0002\u0010\u0007J5\u0010\b\u001a\u0002H\t\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\nH\u0016¢\u0006\u0002\u0010\u000eJ\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010J\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtContextReceiverList;", "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "contextReceivers", "", "Lorg/jetbrains/kotlin/psi/KtContextReceiver;", "typeReferences", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtContextReceiverList extends KtElementImplStub<KotlinPlaceHolderStub<KtContextReceiverList>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtContextReceiverList(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtContextReceiverList(KotlinPlaceHolderStub<KtContextReceiverList> stub) {
        super(stub, KtStubElementTypes.CONTEXT_RECEIVER_LIST);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitContextReceiverList(this, data);
    }

    public final List<KtContextReceiver> contextReceivers() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.CONTEXT_RECEIVER);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        return stubOrPsiChildrenAsList;
    }

    public final List<KtTypeReference> typeReferences() {
        List<KtContextReceiver> listContextReceivers = contextReceivers();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listContextReceivers.iterator();
        while (it.hasNext()) {
            KtTypeReference ktTypeReferenceTypeReference = ((KtContextReceiver) it.next()).typeReference();
            if (ktTypeReferenceTypeReference != null) {
                arrayList.add(ktTypeReferenceTypeReference);
            }
        }
        return arrayList;
    }
}
