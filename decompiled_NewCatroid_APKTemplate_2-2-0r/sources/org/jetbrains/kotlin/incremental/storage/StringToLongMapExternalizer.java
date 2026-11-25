package org.jetbrains.kotlin.incremental.storage;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¢\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0002H\u0014¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/StringToLongMapExternalizer;", "Lorg/jetbrains/kotlin/incremental/storage/StringMapExternalizer;", "", "()V", "readValue", "input", "Ljava/io/DataInput;", "(Ljava/io/DataInput;)Ljava/lang/Long;", "writeValue", "", "output", "Ljava/io/DataOutput;", "value", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StringToLongMapExternalizer extends StringMapExternalizer<Long> {
    public static final StringToLongMapExternalizer INSTANCE = new StringToLongMapExternalizer();

    private StringToLongMapExternalizer() {
    }

    @Override // org.jetbrains.kotlin.incremental.storage.StringMapExternalizer
    public /* bridge */ /* synthetic */ void writeValue(DataOutput dataOutput, Long l) throws IOException {
        writeValue(dataOutput, l.longValue());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.incremental.storage.StringMapExternalizer
    public Long readValue(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return Long.valueOf(input.readLong());
    }

    protected void writeValue(DataOutput output, long value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        output.writeLong(value);
    }
}
