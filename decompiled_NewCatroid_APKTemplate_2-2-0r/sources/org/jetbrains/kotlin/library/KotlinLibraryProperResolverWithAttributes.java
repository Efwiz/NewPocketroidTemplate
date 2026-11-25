package org.jetbrains.kotlin.library;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: SearchPathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004BS\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\u0010J\u001d\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020\u0016H\u0016¢\u0006\u0002\u0010\u0017R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/library/KotlinLibraryProperResolverWithAttributes;", "L", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/KotlinLibrarySearchPathResolver;", "Lorg/jetbrains/kotlin/library/SearchPathResolver;", "repositories", "", "", "directLibs", "distributionKlib", "localKotlinDir", "skipCurrentDir", "", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "knownIrProviders", "(Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;ZLorg/jetbrains/kotlin/util/Logger;Ljava/util/List;)V", "getLogger", "()Lorg/jetbrains/kotlin/util/Logger;", "libraryMatch", "candidate", "unresolved", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;Lorg/jetbrains/kotlin/library/UnresolvedLibrary;)Z", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class KotlinLibraryProperResolverWithAttributes<L extends KotlinLibrary> extends KotlinLibrarySearchPathResolver<L> implements SearchPathResolver<L> {
    private final List<String> knownIrProviders;
    private final Logger logger;

    @Override // org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver, org.jetbrains.kotlin.util.WithLogger
    public Logger getLogger() {
        return this.logger;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinLibraryProperResolverWithAttributes(List<String> repositories, List<String> directLibs, String str, String str2, boolean z, Logger logger, List<String> knownIrProviders) {
        super(repositories, directLibs, str, str2, z, logger);
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        Intrinsics.checkNotNullParameter(directLibs, "directLibs");
        Intrinsics.checkNotNullParameter(logger, "logger");
        Intrinsics.checkNotNullParameter(knownIrProviders, "knownIrProviders");
        this.logger = logger;
        this.knownIrProviders = knownIrProviders;
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver, org.jetbrains.kotlin.library.SearchPathResolver
    public boolean libraryMatch(L candidate, UnresolvedLibrary unresolved) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(unresolved, "unresolved");
        String absolutePath = candidate.getLibraryFile().getAbsolutePath();
        String compilerVersion = candidate.getVersions().getCompilerVersion();
        KotlinAbiVersion abiVersion = candidate.getVersions().getAbiVersion();
        String libraryVersion = candidate.getVersions().getLibraryVersion();
        if (abiVersion == null || !abiVersion.isCompatible()) {
            getLogger().warning("skipping " + absolutePath + ". Incompatible abi version. The current default is '" + KotlinAbiVersion.INSTANCE.getCURRENT() + "', found '" + abiVersion + "'. The library produced by " + compilerVersion + " compiler");
            return false;
        }
        if (!Intrinsics.areEqual(libraryVersion, unresolved.getLibraryVersion()) && libraryVersion != null && unresolved.getLibraryVersion() != null) {
            getLogger().warning("skipping " + absolutePath + ". The library versions don't match. Expected '" + unresolved.getLibraryVersion() + "', found '" + libraryVersion + '\'');
            return false;
        }
        String irProviderName = KotlinLibraryKt.getIrProviderName(candidate);
        if (irProviderName == null || this.knownIrProviders.contains(irProviderName)) {
            return true;
        }
        getLogger().warning("skipping " + absolutePath + ". The library requires unknown IR provider " + irProviderName + '.');
        return false;
    }
}
