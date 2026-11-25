package org.jetbrains.kotlin.incremental.storage;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0002H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/FloatExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "", "()V", "read", "input", "Ljava/io/DataInput;", "(Ljava/io/DataInput;)Ljava/lang/Float;", "save", "", "output", "Ljava/io/DataOutput;", "value", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FloatExternalizer implements DataExternalizer<Float> {
    public static final FloatExternalizer INSTANCE = new FloatExternalizer();

    private FloatExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public /* bridge */ /* synthetic */ void save(DataOutput dataOutput, Object obj) throws IOException {
        save(dataOutput, ((Number) obj).floatValue());
    }

    public void save(DataOutput output, float value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        output.writeFloat(value);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public Float read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return Float.valueOf(input.readFloat());
    }
}
