package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0005J\u001e\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0015\u0010\t\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\bH$¢\u0006\u0002\u0010\nJ&\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003H\u0016J\u001d\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/StringMapExternalizer;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "", "", "()V", "read", "input", "Ljava/io/DataInput;", "readValue", "(Ljava/io/DataInput;)Ljava/lang/Object;", "save", "", "output", "Ljava/io/DataOutput;", "map", "writeValue", "value", "(Ljava/io/DataOutput;Ljava/lang/Object;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class StringMapExternalizer<T> implements DataExternalizer<Map<String, ? extends T>> {
    protected abstract T readValue(DataInput input);

    protected abstract void writeValue(DataOutput output, T value);

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, Map<String, ? extends T> map) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNull(map);
        output.writeInt(map.size());
        for (Map.Entry<String, ? extends T> entry : map.entrySet()) {
            String key = entry.getKey();
            T value = entry.getValue();
            ExternalizersKt.writeString(output, key);
            writeValue(output, value);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public Map<String, T> read(DataInput input) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        int i = input.readInt();
        HashMap map = new HashMap(i);
        for (int i2 = 0; i2 < i; i2++) {
            map.put(ExternalizersKt.readString(input), readValue(input));
        }
        return map;
    }
}
