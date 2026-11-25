package org.jetbrains.kotlin.backend.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryProperResolverWithAttributes;
import org.jetbrains.kotlin.library.impl.KotlinLibraryImplKt;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: CommonKLibResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BO\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\nH\u0016R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/KLibResolverHelper;", "Lorg/jetbrains/kotlin/library/KotlinLibraryProperResolverWithAttributes;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "repositories", "", "", "directLibs", "distributionKlib", "localKotlinDir", "skipCurrentDir", "", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "zipAccessor", "Lorg/jetbrains/kotlin/konan/file/ZipFileSystemAccessor;", "(Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;ZLorg/jetbrains/kotlin/util/Logger;Lorg/jetbrains/kotlin/konan/file/ZipFileSystemAccessor;)V", "libraryComponentBuilder", "file", "Lorg/jetbrains/kotlin/konan/file/File;", "isDefault", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KLibResolverHelper extends KotlinLibraryProperResolverWithAttributes<KotlinLibrary> {
    private final ZipFileSystemAccessor zipAccessor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KLibResolverHelper(List<String> repositories, List<String> directLibs, String str, String str2, boolean z, Logger logger, ZipFileSystemAccessor zipFileSystemAccessor) {
        super(repositories, directLibs, str, str2, z, logger, CollectionsKt.emptyList());
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        Intrinsics.checkNotNullParameter(directLibs, "directLibs");
        Intrinsics.checkNotNullParameter(logger, "logger");
        this.zipAccessor = zipFileSystemAccessor;
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver
    public List<KotlinLibrary> libraryComponentBuilder(File file, boolean isDefault) {
        Intrinsics.checkNotNullParameter(file, "file");
        return KotlinLibraryImplKt.createKotlinLibraryComponents(file, isDefault, this.zipAccessor);
    }
}
