package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: ConeTypeContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ErrorTypeConstructor;", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "reason", "", "(Ljava/lang/String;)V", "getReason", "()Ljava/lang/String;", "toString", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorTypeConstructor implements TypeConstructorMarker {
    private final String reason;

    public ErrorTypeConstructor(String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.reason = reason;
    }

    public final String getReason() {
        return this.reason;
    }

    public String toString() {
        return this.reason;
    }
}
