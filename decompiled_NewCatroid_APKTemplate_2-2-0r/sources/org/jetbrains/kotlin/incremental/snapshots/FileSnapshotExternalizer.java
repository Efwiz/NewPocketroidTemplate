package org.jetbrains.kotlin.incremental.snapshots;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: FileSnapshotExternalizer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshotExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshot;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "out", "Ljava/io/DataOutput;", "value", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileSnapshotExternalizer implements DataExternalizer<FileSnapshot> {
    public static final FileSnapshotExternalizer INSTANCE = new FileSnapshotExternalizer();

    private FileSnapshotExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput out, FileSnapshot value) throws IOException {
        Intrinsics.checkNotNullParameter(out, "out");
        Intrinsics.checkNotNullParameter(value, "value");
        out.writeUTF(FilesKt.normalize(value.getFile()).getAbsolutePath());
        out.writeLong(value.getLength());
        out.writeInt(value.getHash().length);
        out.write(value.getHash());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public FileSnapshot read(DataInput input) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        File file = new File(input.readUTF());
        long j = input.readLong();
        byte[] bArr = new byte[input.readInt()];
        input.readFully(bArr);
        return new FileSnapshot(file, j, bArr);
    }
}
