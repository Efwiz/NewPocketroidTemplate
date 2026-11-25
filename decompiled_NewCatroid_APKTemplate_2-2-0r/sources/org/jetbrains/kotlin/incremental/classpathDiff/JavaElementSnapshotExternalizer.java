package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.incremental.storage.LongExternalizer;
import org.jetbrains.kotlin.incremental.storage.StringExternalizer;

/* JADX INFO: compiled from: ClasspathSnapshotSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaElementSnapshotExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaElementSnapshot;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "value", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JavaElementSnapshotExternalizer implements DataExternalizer<JavaElementSnapshot> {
    public static final JavaElementSnapshotExternalizer INSTANCE = new JavaElementSnapshotExternalizer();

    private JavaElementSnapshotExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, JavaElementSnapshot value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(value, "value");
        StringExternalizer.INSTANCE.save(output, value.getName());
        LongExternalizer.INSTANCE.save(output, value.getAbiHash());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public JavaElementSnapshot read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new JavaElementSnapshot(StringExternalizer.INSTANCE.read(input), LongExternalizer.INSTANCE.read(input).longValue());
    }
}
