package org.jetbrains.kotlin.library.resolver.impl;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.SearchPathResolver;
import org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImpl;
import org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImplKt;

/* JADX INFO: compiled from: Deprecated.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0087\b*J\b\u0007\u0010\u0007\u001a\u0004\b\u0000\u0010\u0002\"\b\u0012\u0004\u0012\u0002H\u00020\u00012\b\u0012\u0004\u0012\u0002H\u00020\u0001B*\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u001c\b\u000b\u0012\u0018\b\u000bB\u0014\b\f\u0012\u0006\b\r\u0012\u0002\b\f\u0012\b\b\u000e\u0012\u0004\b\b(\u000f*8\b\u0007\u0010\u0010\"\u00020\u00112\u00020\u0011B*\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u001c\b\u000b\u0012\u0018\b\u000bB\u0014\b\f\u0012\u0006\b\r\u0012\u0002\b\f\u0012\b\b\u000e\u0012\u0004\b\b(\u0012*8\b\u0007\u0010\u0013\"\u00020\u00142\u00020\u0014B*\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u001c\b\u000b\u0012\u0018\b\u000bB\u0014\b\f\u0012\u0006\b\r\u0012\u0002\b\f\u0012\b\b\u000e\u0012\u0004\b\b(\u0015¨\u0006\u0016"}, d2 = {"libraryResolver", "Lorg/jetbrains/kotlin/library/metadata/resolver/impl/KotlinLibraryResolverImpl;", "L", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/SearchPathResolver;", "resolveManifestDependenciesLenient", "", "KotlinLibraryResolverImpl", "Lkotlin/Deprecated;", CrashHianalyticsData.MESSAGE, "This class has been moved from package org.jetbrains.kotlin.library.resolver.impl to package org.jetbrains.kotlin.library.metadata.resolver.impl", "replaceWith", "Lkotlin/ReplaceWith;", "imports", "expression", "org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImpl", "KotlinLibraryResolverResultImpl", "Lorg/jetbrains/kotlin/library/metadata/resolver/impl/KotlinLibraryResolverResultImpl;", "org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverResultImpl", "KotlinResolvedLibraryImpl", "Lorg/jetbrains/kotlin/library/metadata/resolver/impl/KotlinResolvedLibraryImpl;", "org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinResolvedLibraryImpl", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedKt {
    @Deprecated(message = "This class has been moved from package org.jetbrains.kotlin.library.resolver.impl to package org.jetbrains.kotlin.library.metadata.resolver.impl", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverImpl", imports = {}))
    public static /* synthetic */ void KotlinLibraryResolverImpl$annotations() {
    }

    @Deprecated(message = "This class has been moved from package org.jetbrains.kotlin.library.resolver.impl to package org.jetbrains.kotlin.library.metadata.resolver.impl", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverResultImpl", imports = {}))
    public static /* synthetic */ void KotlinLibraryResolverResultImpl$annotations() {
    }

    @Deprecated(message = "This class has been moved from package org.jetbrains.kotlin.library.resolver.impl to package org.jetbrains.kotlin.library.metadata.resolver.impl", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinResolvedLibraryImpl", imports = {}))
    public static /* synthetic */ void KotlinResolvedLibraryImpl$annotations() {
    }

    public static /* synthetic */ KotlinLibraryResolverImpl libraryResolver$default(SearchPathResolver searchPathResolver, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(searchPathResolver, "<this>");
        return KotlinLibraryResolverImplKt.libraryResolver(searchPathResolver, z);
    }

    @Deprecated(message = "This extension method has been moved from package org.jetbrains.kotlin.library.resolver.impl to package org.jetbrains.kotlin.library.metadata.resolver.impl", replaceWith = @ReplaceWith(expression = "libraryResolver", imports = {"org.jetbrains.kotlin.library.metadata.resolver.impl.libraryResolver"}))
    public static final <L extends KotlinLibrary> KotlinLibraryResolverImpl<L> libraryResolver(SearchPathResolver<L> searchPathResolver, boolean z) {
        Intrinsics.checkNotNullParameter(searchPathResolver, "<this>");
        return KotlinLibraryResolverImplKt.libraryResolver(searchPathResolver, z);
    }
}
