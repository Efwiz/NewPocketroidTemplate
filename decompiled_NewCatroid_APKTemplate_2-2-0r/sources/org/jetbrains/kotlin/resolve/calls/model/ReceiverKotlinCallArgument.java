package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.scopes.receivers.DetailedReceiver;

/* JADX INFO: compiled from: KotlinCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "isSafeCall", "", "()Z", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReceiverKotlinCallArgument extends KotlinCallArgument {
    DetailedReceiver getReceiver();

    boolean isSafeCall();
}
