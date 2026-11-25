package org.jetbrains.kotlin.ir.backend.jvm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.UnresolvedLibraryKt;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImplKt;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: JvmLibraryResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b2\u0006\u0010\t\u001a\u00020\n\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u000b"}, d2 = {"jvmLibrariesProvidedByDefault", "", "", "getJvmLibrariesProvidedByDefault", "()Ljava/util/Set;", "jvmResolveLibraries", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", "libraries", "", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "ir.serialization.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmLibraryResolverKt {
    private static final Set<String> jvmLibrariesProvidedByDefault = SetsKt.setOf((Object[]) new String[]{"stdlib", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME});

    public static final Set<String> getJvmLibrariesProvidedByDefault() {
        return jvmLibrariesProvidedByDefault;
    }

    public static final KotlinLibraryResolveResult jvmResolveLibraries(List<String> libraries, Logger logger) {
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(logger, "logger");
        List<String> list = libraries;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(UnresolvedLibraryKt.UnresolvedLibrary((String) it.next(), null));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList3.add(new File((String) it2.next()).getAbsolutePath());
        }
        return KotlinLibraryResolverImplKt.libraryResolver$default(new JvmLibraryResolver(CollectionsKt.emptyList(), arrayList3, null, null, false, logger), false, 1, null).resolveWithDependencies(arrayList2, true, true, true);
    }
}
