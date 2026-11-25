package org.jetbrains.kotlin.resolve.calls.model;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;

/* JADX INFO: compiled from: ArgumentMapping.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u0006J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0003R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchImpl;", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatch;", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;)V", "_status", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchStatus;", NotificationCompat.CATEGORY_STATUS, "getStatus", "()Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchStatus;", "getValueParameter", "()Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "recordMatchStatus", "", "replaceValueParameter", "newValueParameter", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentMatchImpl implements ArgumentMatch {
    private ArgumentMatchStatus _status;
    private final ValueParameterDescriptor valueParameter;

    public ArgumentMatchImpl(ValueParameterDescriptor valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        this.valueParameter = valueParameter;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch
    public ValueParameterDescriptor getValueParameter() {
        return this.valueParameter;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch
    public ArgumentMatchStatus getStatus() {
        ArgumentMatchStatus argumentMatchStatus = this._status;
        return argumentMatchStatus == null ? ArgumentMatchStatus.UNKNOWN : argumentMatchStatus;
    }

    public final void recordMatchStatus(ArgumentMatchStatus status) {
        Intrinsics.checkNotNullParameter(status, "status");
        this._status = status;
    }

    public final ArgumentMatchImpl replaceValueParameter(ValueParameterDescriptor newValueParameter) {
        Intrinsics.checkNotNullParameter(newValueParameter, "newValueParameter");
        ArgumentMatchImpl argumentMatchImpl = new ArgumentMatchImpl(newValueParameter);
        argumentMatchImpl._status = this._status;
        return argumentMatchImpl;
    }
}
