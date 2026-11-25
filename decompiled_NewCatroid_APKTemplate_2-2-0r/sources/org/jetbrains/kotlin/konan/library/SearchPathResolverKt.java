package org.jetbrains.kotlin.konan.library;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.impl.KonanLibraryImplKt;
import org.jetbrains.kotlin.konan.target.Distribution;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver;
import org.jetbrains.kotlin.library.SearchPathResolver;
import org.jetbrains.kotlin.util.DummyLogger;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: SearchPathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aL\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001aT\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\u0014"}, d2 = {"defaultResolver", "Lorg/jetbrains/kotlin/konan/library/SearchPathResolverWithTarget;", "Lorg/jetbrains/kotlin/konan/library/KonanLibrary;", "repositories", "", "", "directLibs", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "distribution", "Lorg/jetbrains/kotlin/konan/target/Distribution;", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "skipCurrentDir", "", "resolverByName", "Lorg/jetbrains/kotlin/library/SearchPathResolver;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "distributionKlib", "localKotlinDir", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class SearchPathResolverKt {
    public static final SearchPathResolverWithTarget<KonanLibrary> defaultResolver(List<String> repositories, KonanTarget target, Distribution distribution) {
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(distribution, "distribution");
        return defaultResolver$default(repositories, CollectionsKt.emptyList(), target, distribution, null, false, 48, null);
    }

    public static /* synthetic */ SearchPathResolverWithTarget defaultResolver$default(List list, List list2, KonanTarget konanTarget, Distribution distribution, Logger logger, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            logger = DummyLogger.INSTANCE;
        }
        Logger logger2 = logger;
        if ((i & 32) != 0) {
            z = false;
        }
        return defaultResolver(list, list2, konanTarget, distribution, logger2, z);
    }

    public static final SearchPathResolverWithTarget<KonanLibrary> defaultResolver(List<String> repositories, List<String> directLibs, KonanTarget target, Distribution distribution, Logger logger, boolean z) {
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        Intrinsics.checkNotNullParameter(directLibs, "directLibs");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(distribution, "distribution");
        Intrinsics.checkNotNullParameter(logger, "logger");
        return new KonanLibraryProperResolver(repositories, directLibs, target, distribution.getKlib(), distribution.getLocalKonanDir().getAbsolutePath(), z, logger);
    }

    public static /* synthetic */ SearchPathResolver resolverByName$default(List list, List list2, String str, String str2, boolean z, Logger logger, int i, Object obj) {
        if ((i & 2) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        List list3 = list2;
        String str3 = (i & 4) != 0 ? null : str;
        String str4 = (i & 8) != 0 ? null : str2;
        if ((i & 16) != 0) {
            z = false;
        }
        return resolverByName(list, list3, str3, str4, z, logger);
    }

    public static final SearchPathResolver<KotlinLibrary> resolverByName(List<String> repositories, List<String> directLibs, String str, String str2, boolean z, Logger logger) {
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        Intrinsics.checkNotNullParameter(directLibs, "directLibs");
        Intrinsics.checkNotNullParameter(logger, "logger");
        return new KotlinLibrarySearchPathResolver<KotlinLibrary>(repositories, directLibs, str, str2, z, logger) { // from class: org.jetbrains.kotlin.konan.library.SearchPathResolverKt.resolverByName.1
            @Override // org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver
            public List<KonanLibrary> libraryComponentBuilder(File file, boolean isDefault) {
                Intrinsics.checkNotNullParameter(file, "file");
                return KonanLibraryImplKt.createKonanLibraryComponents(file, null, isDefault);
            }
        };
    }
}
