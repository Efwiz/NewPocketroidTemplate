package org.jetbrains.kotlin.backend.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor;
import org.jetbrains.kotlin.library.UnresolvedLibraryKt;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImpl;
import org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImplKt;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: CommonKLibResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bJ&\u0010\f\u001a\u00020\r2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/CommonKLibResolver;", "", "()V", "resolve", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", "libraries", "", "", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "zipAccessor", "Lorg/jetbrains/kotlin/konan/file/ZipFileSystemAccessor;", "resolveWithoutDependencies", "Lorg/jetbrains/kotlin/backend/common/KLibResolution;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonKLibResolver {
    public static final CommonKLibResolver INSTANCE = new CommonKLibResolver();

    private CommonKLibResolver() {
    }

    public static /* synthetic */ KotlinLibraryResolveResult resolve$default(CommonKLibResolver commonKLibResolver, Collection collection, Logger logger, ZipFileSystemAccessor zipFileSystemAccessor, int i, Object obj) {
        if ((i & 4) != 0) {
            zipFileSystemAccessor = null;
        }
        return commonKLibResolver.resolve(collection, logger, zipFileSystemAccessor);
    }

    public final KotlinLibraryResolveResult resolve(Collection<String> libraries, Logger logger, ZipFileSystemAccessor zipAccessor) {
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(logger, "logger");
        return resolveWithoutDependencies(libraries, logger, zipAccessor).resolveWithDependencies();
    }

    public final KLibResolution resolveWithoutDependencies(Collection<String> libraries, Logger logger, ZipFileSystemAccessor zipAccessor) {
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(logger, "logger");
        Collection<String> collection = libraries;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(UnresolvedLibraryKt.UnresolvedLibrary((String) it.next(), null));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it2 = collection.iterator();
        while (it2.hasNext()) {
            arrayList3.add(new File((String) it2.next()).getAbsolutePath());
        }
        KotlinLibraryResolverImpl kotlinLibraryResolverImplLibraryResolver$default = KotlinLibraryResolverImplKt.libraryResolver$default(new KLibResolverHelper(CollectionsKt.emptyList(), arrayList3, null, null, false, logger, zipAccessor), false, 1, null);
        return new KLibResolution(kotlinLibraryResolverImplLibraryResolver$default, kotlinLibraryResolverImplLibraryResolver$default.resolveWithoutDependencies(arrayList2, true, true, true));
    }
}
