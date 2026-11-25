package org.jetbrains.kotlin.incremental.snapshots;

import java.io.File;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FileSnapshot.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshot;", "", "file", "Ljava/io/File;", "length", "", "hash", "", "(Ljava/io/File;J[B)V", "getFile", "()Ljava/io/File;", "getHash", "()[B", "getLength", "()J", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileSnapshot {
    private final File file;
    private final byte[] hash;
    private final long length;

    public FileSnapshot(File file, long j, byte[] hash) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(hash, "hash");
        this.file = file;
        this.length = j;
        this.hash = hash;
        file.isDirectory();
    }

    public final File getFile() {
        return this.file;
    }

    public final long getLength() {
        return this.length;
    }

    public final byte[] getHash() {
        return this.hash;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual(other.getClass(), getClass())) {
            return false;
        }
        FileSnapshot fileSnapshot = (FileSnapshot) other;
        return Intrinsics.areEqual(this.file, fileSnapshot.file) && this.length == fileSnapshot.length && Arrays.equals(this.hash, fileSnapshot.hash);
    }

    public int hashCode() {
        return (((this.file.hashCode() * 31) + Long.hashCode(this.length)) * 31) + Arrays.hashCode(this.hash);
    }

    public String toString() {
        return "FileSnapshot(file=" + this.file + ", length=" + this.length + ", hash=" + Arrays.toString(this.hash) + ')';
    }
}
