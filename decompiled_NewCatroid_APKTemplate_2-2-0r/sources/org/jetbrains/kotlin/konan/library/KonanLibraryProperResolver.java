package org.jetbrains.kotlin.konan.library;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.FileKt;
import org.jetbrains.kotlin.konan.library.impl.KonanLibraryImplKt;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.library.KotlinLibraryProperResolverWithAttributes;
import org.jetbrains.kotlin.library.UnresolvedLibrary;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: SearchPathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003BM\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\rH\u0016J\u0018\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/konan/library/KonanLibraryProperResolver;", "Lorg/jetbrains/kotlin/library/KotlinLibraryProperResolverWithAttributes;", "Lorg/jetbrains/kotlin/konan/library/KonanLibrary;", "Lorg/jetbrains/kotlin/konan/library/SearchPathResolverWithTarget;", "repositories", "", "", "directLibs", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "distributionKlib", "localKonanDir", "skipCurrentDir", "", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "(Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/konan/target/KonanTarget;Ljava/lang/String;Ljava/lang/String;ZLorg/jetbrains/kotlin/util/Logger;)V", "distPlatformHead", "Lorg/jetbrains/kotlin/konan/file/File;", "getDistPlatformHead", "()Lorg/jetbrains/kotlin/konan/file/File;", "getLogger", "()Lorg/jetbrains/kotlin/util/Logger;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "libraryComponentBuilder", "file", "isDefault", "libraryMatch", "candidate", "unresolved", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class KonanLibraryProperResolver extends KotlinLibraryProperResolverWithAttributes<KonanLibrary> implements SearchPathResolverWithTarget<KonanLibrary> {
    private final Logger logger;
    private final KonanTarget target;

    @Override // org.jetbrains.kotlin.konan.library.SearchPathResolverWithTarget
    public KonanTarget getTarget() {
        return this.target;
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryProperResolverWithAttributes, org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver, org.jetbrains.kotlin.util.WithLogger
    public Logger getLogger() {
        return this.logger;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KonanLibraryProperResolver(List<String> repositories, List<String> directLibs, KonanTarget target, String str, String str2, boolean z, Logger logger) {
        super(repositories, directLibs, str, str2, z, logger, CollectionsKt.listOf(NativeLibraryConstantsKt.KLIB_INTEROP_IR_PROVIDER_IDENTIFIER));
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        Intrinsics.checkNotNullParameter(directLibs, "directLibs");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(logger, "logger");
        this.target = target;
        this.logger = logger;
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver
    public List<KonanLibrary> libraryComponentBuilder(File file, boolean isDefault) {
        Intrinsics.checkNotNullParameter(file, "file");
        return KonanLibraryImplKt.createKonanLibraryComponents(file, getTarget(), isDefault);
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver
    public File getDistPlatformHead() {
        File File;
        File fileChild;
        String distributionKlib = getDistributionKlib();
        if (distributionKlib == null || (File = FileKt.File(distributionKlib)) == null || (fileChild = File.child(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR)) == null) {
            return null;
        }
        return fileChild.child(getTarget().getVisibleName());
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibraryProperResolverWithAttributes, org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver, org.jetbrains.kotlin.library.SearchPathResolver
    public boolean libraryMatch(KonanLibrary candidate, UnresolvedLibrary unresolved) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(unresolved, "unresolved");
        KonanTarget target = getTarget();
        String absolutePath = candidate.getLibraryFile().getAbsolutePath();
        if (!candidate.getTargetList().contains(target.getVisibleName())) {
            getLogger().warning("skipping " + absolutePath + ". The target doesn't match. Expected '" + target + "', found " + candidate.getTargetList());
            return false;
        }
        return super.libraryMatch(candidate, unresolved);
    }
}
