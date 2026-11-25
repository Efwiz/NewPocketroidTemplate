package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: writeAheadLog.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B7\b\u0007\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006¢\u0006\u0002\u0010\fJ\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001b\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020\u000f¢\u0006\u0002\u0010\u0016J\u0006\u0010\u0017\u001a\u00020\u0012R\"\u0010\r\u001a\u0016\u0012\u0004\u0012\u00028\u0000\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/PersistentEnumeratorWal;", "Data", "Ljava/io/Closeable;", "dataDescriptor", "Lorg/jetbrains/kotlin/com/intellij/util/io/KeyDescriptor;", "useCompression", "", "file", "Ljava/nio/file/Path;", "walIoExecutor", "Ljava/util/concurrent/ExecutorService;", "compact", "(Lcom/intellij/util/io/KeyDescriptor;ZLjava/nio/file/Path;Ljava/util/concurrent/ExecutorService;Z)V", "underlying", "Lorg/jetbrains/kotlin/com/intellij/util/io/PersistentMapWal;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "close", "", "enumerate", "data", "id", "(Ljava/lang/Object;I)V", "flush", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
public final class PersistentEnumeratorWal<Data> implements Closeable {
    private final PersistentMapWal<Data, Integer> underlying;

    public PersistentEnumeratorWal(KeyDescriptor<Data> dataDescriptor, boolean z, Path file, ExecutorService walIoExecutor, boolean z2) throws IOException {
        Intrinsics.checkNotNullParameter(dataDescriptor, "dataDescriptor");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(walIoExecutor, "walIoExecutor");
        this.underlying = new PersistentMapWal<>(dataDescriptor, WriteAheadLogKt.getIntegerExternalizer(), z, file, walIoExecutor, z2);
    }

    public final void enumerate(Data data, int id2) throws IOException {
        this.underlying.put(data, Integer.valueOf(id2));
    }

    public final void flush() throws IOException {
        this.underlying.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.underlying.close();
    }
}
