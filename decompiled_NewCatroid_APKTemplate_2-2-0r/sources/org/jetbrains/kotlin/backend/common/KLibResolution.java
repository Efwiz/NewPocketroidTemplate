package org.jetbrains.kotlin.backend.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolver;

/* JADX INFO: compiled from: CommonKLibResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\n\u001a\u00020\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/KLibResolution;", "", "libraryResolver", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolver;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "libraries", "", "(Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolver;Ljava/util/List;)V", "getLibraries", "()Ljava/util/List;", "resolveWithDependencies", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KLibResolution {
    private final List<KotlinLibrary> libraries;
    private final KotlinLibraryResolver<KotlinLibrary> libraryResolver;

    /* JADX WARN: Multi-variable type inference failed */
    public KLibResolution(KotlinLibraryResolver<KotlinLibrary> libraryResolver, List<? extends KotlinLibrary> libraries) {
        Intrinsics.checkNotNullParameter(libraryResolver, "libraryResolver");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        this.libraryResolver = libraryResolver;
        this.libraries = libraries;
    }

    public final List<KotlinLibrary> getLibraries() {
        return this.libraries;
    }

    public final KotlinLibraryResolveResult resolveWithDependencies() {
        return this.libraryResolver.resolveDependencies(this.libraries);
    }
}
