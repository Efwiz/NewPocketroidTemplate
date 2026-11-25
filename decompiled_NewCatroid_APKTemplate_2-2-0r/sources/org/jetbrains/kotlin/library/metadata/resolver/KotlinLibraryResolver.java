package org.jetbrains.kotlin.library.metadata.resolver;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.SearchPathResolver;
import org.jetbrains.kotlin.library.UnresolvedLibrary;

/* JADX INFO: compiled from: KotlinLibraryResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J4\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000eH\u0016J:\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000eH&J\u0012\u0010\u0012\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\u00020\u000bH&R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolver;", "L", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "", "searchPathResolver", "Lorg/jetbrains/kotlin/library/SearchPathResolver;", "getSearchPathResolver", "()Lorg/jetbrains/kotlin/library/SearchPathResolver;", "resolveWithDependencies", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", "unresolvedLibraries", "", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "noStdLib", "", "noDefaultLibs", "noEndorsedLibs", "resolveWithoutDependencies", "resolveDependencies", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinLibraryResolver<L extends KotlinLibrary> {
    SearchPathResolver<L> getSearchPathResolver();

    KotlinLibraryResolveResult resolveDependencies(List<? extends KotlinLibrary> list);

    List<KotlinLibrary> resolveWithoutDependencies(List<? extends UnresolvedLibrary> unresolvedLibraries, boolean noStdLib, boolean noDefaultLibs, boolean noEndorsedLibs);

    static /* synthetic */ KotlinLibraryResolveResult resolveWithDependencies$default(KotlinLibraryResolver kotlinLibraryResolver, List list, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resolveWithDependencies");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        if ((i & 8) != 0) {
            z3 = false;
        }
        return kotlinLibraryResolver.resolveWithDependencies(list, z, z2, z3);
    }

    default KotlinLibraryResolveResult resolveWithDependencies(List<? extends UnresolvedLibrary> unresolvedLibraries, boolean noStdLib, boolean noDefaultLibs, boolean noEndorsedLibs) {
        Intrinsics.checkNotNullParameter(unresolvedLibraries, "unresolvedLibraries");
        return resolveDependencies(resolveWithoutDependencies(unresolvedLibraries, noStdLib, noDefaultLibs, noEndorsedLibs));
    }

    static /* synthetic */ List resolveWithoutDependencies$default(KotlinLibraryResolver kotlinLibraryResolver, List list, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resolveWithoutDependencies");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        if ((i & 8) != 0) {
            z3 = false;
        }
        return kotlinLibraryResolver.resolveWithoutDependencies(list, z, z2, z3);
    }
}
