package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IncrementalCacheGuard.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\u000bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheGuard;", "", "cacheDir", "", "(Ljava/lang/String;)V", "cacheRoot", "Ljava/io/File;", "guardFile", "acquire", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheGuard$AcquireStatus;", "release", "", "tryAcquire", "AcquireStatus", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalCacheGuard {
    private final File cacheRoot;
    private final File guardFile;

    /* JADX INFO: compiled from: IncrementalCacheGuard.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheGuard$AcquireStatus;", "", "(Ljava/lang/String;I)V", "OK", "CACHE_CLEARED", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum AcquireStatus {
        OK,
        CACHE_CLEARED;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<AcquireStatus> getEntries() {
            return $ENTRIES;
        }
    }

    public IncrementalCacheGuard(String cacheDir) {
        Intrinsics.checkNotNullParameter(cacheDir, "cacheDir");
        File file = new File(cacheDir);
        this.cacheRoot = file;
        this.guardFile = FilesKt.resolve(file, "cache.guard");
    }

    public final AcquireStatus acquire() throws IOException {
        boolean zExists = this.guardFile.exists();
        if (zExists) {
            FilesKt.deleteRecursively(this.cacheRoot);
        }
        tryAcquire();
        return zExists ? AcquireStatus.CACHE_CLEARED : AcquireStatus.OK;
    }

    public final void tryAcquire() throws IOException {
        this.cacheRoot.mkdirs();
        this.guardFile.createNewFile();
    }

    public final void release() {
        this.guardFile.delete();
    }
}
