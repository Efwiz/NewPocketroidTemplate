package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SuperCallReceiverValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/SuperCallReceiverValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "thisType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getThisType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SuperCallReceiverValue extends ReceiverValue {
    KotlinType getThisType();
}
