package org.jetbrains.kotlin.library.metadata.resolver.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.SearchPathResolver;

/* JADX INFO: compiled from: KotlinLibraryResolverImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"libraryResolver", "Lorg/jetbrains/kotlin/library/metadata/resolver/impl/KotlinLibraryResolverImpl;", "L", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/SearchPathResolver;", "resolveManifestDependenciesLenient", "", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinLibraryResolverImplKt {
    public static /* synthetic */ KotlinLibraryResolverImpl libraryResolver$default(SearchPathResolver searchPathResolver, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return libraryResolver(searchPathResolver, z);
    }

    public static final <L extends KotlinLibrary> KotlinLibraryResolverImpl<L> libraryResolver(SearchPathResolver<L> searchPathResolver, boolean z) {
        Intrinsics.checkNotNullParameter(searchPathResolver, "<this>");
        return new KotlinLibraryResolverImpl<>(searchPathResolver, z);
    }
}
