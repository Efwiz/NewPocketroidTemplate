package org.jetbrains.kotlin.library;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: SingleFileResolve.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"resolveSingleFileKlib", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "libraryFile", "Lorg/jetbrains/kotlin/konan/file/File;", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "strategy", "Lorg/jetbrains/kotlin/library/SingleFileKlibResolveStrategy;", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class SingleFileResolveKt {
    public static /* synthetic */ KotlinLibrary resolveSingleFileKlib$default(File file, Logger logger, SingleFileKlibResolveStrategy singleFileKlibResolveStrategy, int i, Object obj) {
        if ((i & 2) != 0) {
            logger = new Logger() { // from class: org.jetbrains.kotlin.library.SingleFileResolveKt.resolveSingleFileKlib.1
                @Override // org.jetbrains.kotlin.util.Logger
                public void log(String message) {
                    Intrinsics.checkNotNullParameter(message, "message");
                }

                @Override // org.jetbrains.kotlin.util.Logger
                public void warning(String message) {
                    Intrinsics.checkNotNullParameter(message, "message");
                }

                @Override // org.jetbrains.kotlin.util.Logger
                /* JADX INFO: renamed from: error, reason: merged with bridge method [inline-methods] */
                public Void mo7307error(String message) {
                    Intrinsics.checkNotNullParameter(message, "message");
                    throw new IllegalStateException(("e: " + message).toString());
                }

                @Override // org.jetbrains.kotlin.util.Logger
                public Void fatal(String message) {
                    Intrinsics.checkNotNullParameter(message, "message");
                    throw new IllegalStateException(("e: " + message).toString());
                }
            };
        }
        if ((i & 4) != 0) {
            singleFileKlibResolveStrategy = CompilerSingleFileKlibResolveStrategy.INSTANCE;
        }
        return resolveSingleFileKlib(file, logger, singleFileKlibResolveStrategy);
    }

    public static final KotlinLibrary resolveSingleFileKlib(File libraryFile, Logger logger, SingleFileKlibResolveStrategy strategy) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(logger, "logger");
        Intrinsics.checkNotNullParameter(strategy, "strategy");
        return strategy.resolve(libraryFile, logger);
    }
}
