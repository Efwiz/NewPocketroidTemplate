package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: smartCastUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¨\u0006\u0005"}, d2 = {"getReceiverValueWithSmartCast", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "receiverArgument", "smartCastType", "Lorg/jetbrains/kotlin/types/KotlinType;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SmartCastUtilKt {
    public static final ReceiverValue getReceiverValueWithSmartCast(ReceiverValue receiverValue, KotlinType kotlinType) {
        return kotlinType != null ? new SmartCastReceiverValue(kotlinType, null) : receiverValue;
    }
}
