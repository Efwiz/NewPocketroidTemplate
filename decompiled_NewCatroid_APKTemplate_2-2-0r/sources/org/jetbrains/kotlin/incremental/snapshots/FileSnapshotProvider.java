package org.jetbrains.kotlin.incremental.snapshots;

import java.io.File;
import kotlin.Metadata;

/* JADX INFO: compiled from: FileSnapshotProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H¦\u0002ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshotProvider;", "", "get", "Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshot;", "file", "Ljava/io/File;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FileSnapshotProvider {
    FileSnapshot get(File file);
}
