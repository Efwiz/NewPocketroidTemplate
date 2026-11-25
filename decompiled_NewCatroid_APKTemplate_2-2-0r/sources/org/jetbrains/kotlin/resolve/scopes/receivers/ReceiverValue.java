package org.jetbrains.kotlin.resolve.scopes.receivers;

import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public interface ReceiverValue extends Receiver {
    ReceiverValue getOriginal();

    KotlinType getType();

    /* JADX INFO: renamed from: replaceType */
    ReceiverValue mo7466replaceType(KotlinType kotlinType);
}
