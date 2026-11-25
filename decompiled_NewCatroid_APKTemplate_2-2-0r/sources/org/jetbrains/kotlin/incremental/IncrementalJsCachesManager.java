package org.jetbrains.kotlin.incremental;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;

/* JADX INFO: compiled from: IncrementalCachesManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJsCachesManager;", "Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;", "Lorg/jetbrains/kotlin/incremental/IncrementalJsCache;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "serializerProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "cachesRootDir", "Ljava/io/File;", "(Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;Ljava/io/File;)V", "jsCacheFile", "platformCache", "getPlatformCache", "()Lorg/jetbrains/kotlin/incremental/IncrementalJsCache;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalJsCachesManager extends IncrementalCachesManager<IncrementalJsCache> {
    private final File jsCacheFile;
    private final IncrementalJsCache platformCache;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IncrementalJsCachesManager(IncrementalCompilationContext icContext, SerializerExtensionProtocol serializerProtocol, File cachesRootDir) {
        super(icContext, cachesRootDir);
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        Intrinsics.checkNotNullParameter(serializerProtocol, "serializerProtocol");
        Intrinsics.checkNotNullParameter(cachesRootDir, "cachesRootDir");
        File file = new File(cachesRootDir, "js");
        file.mkdirs();
        this.jsCacheFile = file;
        IncrementalJsCache incrementalJsCache = new IncrementalJsCache(file, icContext, serializerProtocol);
        registerCache(incrementalJsCache);
        this.platformCache = incrementalJsCache;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCachesManager
    public IncrementalJsCache getPlatformCache() {
        return this.platformCache;
    }
}
