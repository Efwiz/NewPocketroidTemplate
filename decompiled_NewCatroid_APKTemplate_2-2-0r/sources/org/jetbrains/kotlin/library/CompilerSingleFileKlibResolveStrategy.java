package org.jetbrains.kotlin.library;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: SearchPathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/library/CompilerSingleFileKlibResolveStrategy;", "Lorg/jetbrains/kotlin/library/SingleFileKlibResolveStrategy;", "()V", "resolve", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "libraryFile", "Lorg/jetbrains/kotlin/konan/file/File;", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CompilerSingleFileKlibResolveStrategy implements SingleFileKlibResolveStrategy {
    public static final CompilerSingleFileKlibResolveStrategy INSTANCE = new CompilerSingleFileKlibResolveStrategy();

    private CompilerSingleFileKlibResolveStrategy() {
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [org.jetbrains.kotlin.library.KotlinLibrary] */
    @Override // org.jetbrains.kotlin.library.SingleFileKlibResolveStrategy
    public KotlinLibrary resolve(File libraryFile, Logger logger) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(logger, "logger");
        return new SingleKlibComponentResolver(libraryFile.getAbsolutePath(), logger, CollectionsKt.emptyList()).resolve(libraryFile.getAbsolutePath());
    }
}
