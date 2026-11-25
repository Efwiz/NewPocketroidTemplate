package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0002\u0010\u0004J\u0017\u0010\u0005\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¢\u0006\u0002\u0010\bJ\u001f\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010\u000eR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/NullableValueExternalizer;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "valueExternalizer", "(Lcom/intellij/util/io/DataExternalizer;)V", "read", "input", "Ljava/io/DataInput;", "(Ljava/io/DataInput;)Ljava/lang/Object;", "save", "", "output", "Ljava/io/DataOutput;", "value", "(Ljava/io/DataOutput;Ljava/lang/Object;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullableValueExternalizer<T> implements DataExternalizer<T> {
    private final DataExternalizer<T> valueExternalizer;

    public NullableValueExternalizer(DataExternalizer<T> valueExternalizer) {
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        this.valueExternalizer = valueExternalizer;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, T value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        output.writeBoolean(value != null);
        if (value != null) {
            this.valueExternalizer.save(output, value);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public T read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        if (input.readBoolean()) {
            return this.valueExternalizer.read(input);
        }
        return null;
    }
}
