package org.jetbrains.kotlin.incremental.snapshots;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FileSnapshotProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0096\u0002¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/snapshots/SimpleFileSnapshotProviderImpl;", "Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshotProvider;", "()V", "get", "Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshot;", "file", "Ljava/io/File;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleFileSnapshotProviderImpl implements FileSnapshotProvider {
    @Override // org.jetbrains.kotlin.incremental.snapshots.FileSnapshotProvider
    public FileSnapshot get(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new FileSnapshot(file, file.length(), HashUtilKt.getMd5(file));
    }
}
