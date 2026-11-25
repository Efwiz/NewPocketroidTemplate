package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.jvm.NotFoundPackagesCachingStrategy;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplNotFoundPackagesCachingStrategy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ReplNotFoundPackagesCachingStrategy;", "Lorg/jetbrains/kotlin/resolve/jvm/NotFoundPackagesCachingStrategy;", "()V", "chooseStrategy", "Lorg/jetbrains/kotlin/resolve/jvm/NotFoundPackagesCachingStrategy$CacheType;", "isLibrarySearchScope", "", "qualifiedName", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplNotFoundPackagesCachingStrategy implements NotFoundPackagesCachingStrategy {
    public static final ReplNotFoundPackagesCachingStrategy INSTANCE = new ReplNotFoundPackagesCachingStrategy();

    private ReplNotFoundPackagesCachingStrategy() {
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.NotFoundPackagesCachingStrategy
    public NotFoundPackagesCachingStrategy.CacheType chooseStrategy(boolean isLibrarySearchScope, String qualifiedName) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        return NotFoundPackagesCachingStrategy.CacheType.NO_CACHING;
    }
}
