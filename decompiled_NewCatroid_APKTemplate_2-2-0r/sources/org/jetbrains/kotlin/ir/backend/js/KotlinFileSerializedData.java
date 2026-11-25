package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.SerializedIrFile;

/* JADX INFO: compiled from: klib.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/KotlinFileSerializedData;", "", "metadata", "", "irData", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "([BLorg/jetbrains/kotlin/library/SerializedIrFile;)V", "getIrData", "()Lorg/jetbrains/kotlin/library/SerializedIrFile;", "getMetadata", "()[B", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinFileSerializedData {
    private final SerializedIrFile irData;
    private final byte[] metadata;

    public KotlinFileSerializedData(byte[] metadata, SerializedIrFile irData) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(irData, "irData");
        this.metadata = metadata;
        this.irData = irData;
    }

    public final SerializedIrFile getIrData() {
        return this.irData;
    }

    public final byte[] getMetadata() {
        return this.metadata;
    }
}
