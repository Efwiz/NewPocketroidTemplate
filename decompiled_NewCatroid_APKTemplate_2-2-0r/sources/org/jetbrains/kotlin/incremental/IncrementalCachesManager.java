package org.jetbrains.kotlin.incremental;

import androidx.exifinterface.media.ExifInterface;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.common.io.Closer;
import org.jetbrains.kotlin.incremental.AbstractIncrementalCache;
import org.jetbrains.kotlin.incremental.storage.BasicMapsOwner;

/* JADX INFO: compiled from: IncrementalCachesManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\b&\u0018\u0000*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u00020\u0003:\u0001!B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u001b\u0010\u001e\u001a\u00020\u001d\"\b\b\u0001\u0010\u001f*\u00020\u000b*\u0002H\u001fH\u0004¢\u0006\u0002\u0010 R\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\nj\b\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;", "PlatformCache", "Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache;", "Ljava/io/Closeable;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "cachesRootDir", "Ljava/io/File;", "(Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;Ljava/io/File;)V", "caches", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/incremental/storage/BasicMapsOwner;", "Lkotlin/collections/ArrayList;", "inputSnapshotsCacheDir", "inputsCache", "Lorg/jetbrains/kotlin/incremental/InputsCache;", "getInputsCache", "()Lorg/jetbrains/kotlin/incremental/InputsCache;", "isClosed", "", "lookupCache", "Lorg/jetbrains/kotlin/incremental/LookupStorage;", "getLookupCache", "()Lorg/jetbrains/kotlin/incremental/LookupStorage;", "lookupCacheDir", "platformCache", "getPlatformCache", "()Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache;", "close", "", "registerCache", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/incremental/storage/BasicMapsOwner;)V", "CacheCloser", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IncrementalCachesManager<PlatformCache extends AbstractIncrementalCache<?>> implements Closeable {
    private final ArrayList<BasicMapsOwner> caches;
    private final File inputSnapshotsCacheDir;
    private final InputsCache inputsCache;
    private boolean isClosed;
    private final LookupStorage lookupCache;
    private final File lookupCacheDir;

    public abstract PlatformCache getPlatformCache();

    public IncrementalCachesManager(IncrementalCompilationContext icContext, File cachesRootDir) {
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        Intrinsics.checkNotNullParameter(cachesRootDir, "cachesRootDir");
        this.caches = new ArrayList<>();
        File file = new File(cachesRootDir, "inputs");
        file.mkdirs();
        this.inputSnapshotsCacheDir = file;
        File file2 = new File(cachesRootDir, "lookups");
        file2.mkdirs();
        this.lookupCacheDir = file2;
        InputsCache inputsCache = new InputsCache(file, icContext);
        registerCache(inputsCache);
        this.inputsCache = inputsCache;
        LookupStorage lookupStorage = new LookupStorage(file2, icContext);
        registerCache(lookupStorage);
        this.lookupCache = lookupStorage;
    }

    protected final synchronized <T extends BasicMapsOwner> void registerCache(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        if (this.isClosed) {
            throw new IllegalStateException("This cache storage has already been closed".toString());
        }
        this.caches.add(t);
    }

    public final InputsCache getInputsCache() {
        return this.inputsCache;
    }

    public final LookupStorage getLookupCache() {
        return this.lookupCache;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        if (this.isClosed) {
            throw new IllegalStateException("This cache storage has already been closed".toString());
        }
        Closer closerCreate = Closer.create();
        Iterator<T> it = this.caches.iterator();
        while (it.hasNext()) {
            closerCreate.register(new CacheCloser((BasicMapsOwner) it.next()));
        }
        closerCreate.close();
        this.isClosed = true;
    }

    /* JADX INFO: compiled from: IncrementalCachesManager.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager$CacheCloser;", "Ljava/io/Closeable;", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/incremental/storage/BasicMapsOwner;", "(Lorg/jetbrains/kotlin/incremental/storage/BasicMapsOwner;)V", "close", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CacheCloser implements Closeable {
        private final BasicMapsOwner cache;

        public CacheCloser(BasicMapsOwner cache) {
            Intrinsics.checkNotNullParameter(cache, "cache");
            this.cache = cache;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.cache.flush(false);
            this.cache.close();
        }
    }
}
