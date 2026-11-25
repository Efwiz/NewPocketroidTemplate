package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: smartCastUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0000H\u0016J\b\u0010\u0007\u001a\u00020\u0003H\u0016J\u0010\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0003H\u0016R\u000e\u0010\u0004\u001a\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastReceiverValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "original", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastReceiverValue;)V", "getOriginal", "getType", "replaceType", "newType", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SmartCastReceiverValue implements ReceiverValue {
    private final SmartCastReceiverValue original;
    private final KotlinType type;

    public SmartCastReceiverValue(KotlinType type, SmartCastReceiverValue smartCastReceiverValue) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.original = smartCastReceiverValue == null ? this : smartCastReceiverValue;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    public KotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    /* JADX INFO: renamed from: replaceType */
    public SmartCastReceiverValue mo7466replaceType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        return new SmartCastReceiverValue(newType, this.original);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    public SmartCastReceiverValue getOriginal() {
        return this.original;
    }
}
