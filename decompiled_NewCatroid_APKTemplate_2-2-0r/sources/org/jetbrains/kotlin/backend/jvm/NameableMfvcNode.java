package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;

/* JADX INFO: compiled from: MfvcNode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\u0082\u0001\u0002\n\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "hasPureUnboxMethod", "", "getHasPureUnboxMethod", "()Z", "namedNodeImpl", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl;", "getNamedNodeImpl", "()Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl;", "Lorg/jetbrains/kotlin/backend/jvm/IntermediateMfvcNode;", "Lorg/jetbrains/kotlin/backend/jvm/LeafMfvcNode;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NameableMfvcNode extends MfvcNode {
    boolean getHasPureUnboxMethod();

    NameableMfvcNodeImpl getNamedNodeImpl();
}
