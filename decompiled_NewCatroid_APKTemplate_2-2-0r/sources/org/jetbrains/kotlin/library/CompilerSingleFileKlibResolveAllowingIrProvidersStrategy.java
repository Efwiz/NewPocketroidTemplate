package org.jetbrains.kotlin.library;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: SearchPathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/CompilerSingleFileKlibResolveAllowingIrProvidersStrategy;", "Lorg/jetbrains/kotlin/library/SingleFileKlibResolveStrategy;", "knownIrProviders", "", "", "(Ljava/util/List;)V", "resolve", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "libraryFile", "Lorg/jetbrains/kotlin/konan/file/File;", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CompilerSingleFileKlibResolveAllowingIrProvidersStrategy implements SingleFileKlibResolveStrategy {
    private final List<String> knownIrProviders;

    public CompilerSingleFileKlibResolveAllowingIrProvidersStrategy(List<String> knownIrProviders) {
        Intrinsics.checkNotNullParameter(knownIrProviders, "knownIrProviders");
        this.knownIrProviders = knownIrProviders;
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [org.jetbrains.kotlin.library.KotlinLibrary] */
    @Override // org.jetbrains.kotlin.library.SingleFileKlibResolveStrategy
    public KotlinLibrary resolve(File libraryFile, Logger logger) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(logger, "logger");
        return new SingleKlibComponentResolver(libraryFile.getAbsolutePath(), logger, this.knownIrProviders).resolve(libraryFile.getAbsolutePath());
    }
}
