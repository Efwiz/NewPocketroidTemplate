package org.jetbrains.kotlin.incremental;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: IncrementalCachesManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bR\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCachesManager;", "Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", ModuleXmlParser.OUTPUT_DIR, "Ljava/io/File;", "cachesRootDir", "(Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;Ljava/io/File;Ljava/io/File;)V", "jvmCacheDir", "platformCache", "getPlatformCache", "()Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IncrementalJvmCachesManager extends IncrementalCachesManager<IncrementalJvmCache> {
    private final File jvmCacheDir;
    private final IncrementalJvmCache platformCache;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IncrementalJvmCachesManager(IncrementalCompilationContext icContext, File file, File cachesRootDir) {
        super(icContext, cachesRootDir);
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        Intrinsics.checkNotNullParameter(cachesRootDir, "cachesRootDir");
        File file2 = new File(cachesRootDir, "jvm");
        file2.mkdirs();
        this.jvmCacheDir = file2;
        IncrementalJvmCache incrementalJvmCache = new IncrementalJvmCache(file2, icContext, file);
        registerCache(incrementalJvmCache);
        this.platformCache = incrementalJvmCache;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCachesManager
    public IncrementalJvmCache getPlatformCache() {
        return this.platformCache;
    }
}
