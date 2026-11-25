package org.jetbrains.kotlin.ir.backend.jvm;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryProperResolverWithAttributes;
import org.jetbrains.kotlin.library.UnresolvedLibrary;
import org.jetbrains.kotlin.library.impl.KotlinLibraryImplKt;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: JvmLibraryResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BE\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\nH\u0016¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/JvmLibraryResolver;", "Lorg/jetbrains/kotlin/library/KotlinLibraryProperResolverWithAttributes;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "repositories", "", "", "directLibs", "distributionKlib", "localKotlinDir", "skipCurrentDir", "", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "(Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;ZLorg/jetbrains/kotlin/util/Logger;)V", "isProvidedByDefault", "unresolved", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "libraryComponentBuilder", "file", "Lorg/jetbrains/kotlin/konan/file/File;", "isDefault", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmLibraryResolver extends KotlinLibraryProperResolverWithAttributes<KotlinLibrary> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmLibraryResolver(List<String> repositories, List<String> directLibs, String str, String str2, boolean z, Logger logger) {
        super(repositories, directLibs, str, str2, z, logger, CollectionsKt.emptyList());
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        Intrinsics.checkNotNullParameter(directLibs, "directLibs");
        Intrinsics.checkNotNullParameter(logger, "logger");
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver
    public List<KotlinLibrary> libraryComponentBuilder(File file, boolean isDefault) {
        Intrinsics.checkNotNullParameter(file, "file");
        return KotlinLibraryImplKt.createKotlinLibraryComponents$default(file, isDefault, null, 4, null);
    }

    @Override // org.jetbrains.kotlin.library.SearchPathResolver
    public boolean isProvidedByDefault(UnresolvedLibrary unresolved) {
        Intrinsics.checkNotNullParameter(unresolved, "unresolved");
        return JvmLibraryResolverKt.getJvmLibrariesProvidedByDefault().contains(unresolved.getPath());
    }
}
