package org.jetbrains.kotlin.library;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SearchPathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a)\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"KOTLIN_STDLIB_NAME", "", "resolve", "L", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/SearchPathResolver;", "unresolved", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "(Lorg/jetbrains/kotlin/library/SearchPathResolver;Lorg/jetbrains/kotlin/library/UnresolvedLibrary;)Lorg/jetbrains/kotlin/library/KotlinLibrary;", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class SearchPathResolverKt {
    public static final String KOTLIN_STDLIB_NAME = "stdlib";

    public static final <L extends KotlinLibrary> L resolve(SearchPathResolver<L> searchPathResolver, UnresolvedLibrary unresolved) {
        Intrinsics.checkNotNullParameter(searchPathResolver, "<this>");
        Intrinsics.checkNotNullParameter(unresolved, "unresolved");
        if (unresolved instanceof LenientUnresolvedLibrary) {
            return (L) SearchPathResolver.resolve$default((SearchPathResolver) searchPathResolver, (LenientUnresolvedLibrary) unresolved, false, 2, (Object) null);
        }
        if (unresolved instanceof RequiredUnresolvedLibrary) {
            return (L) SearchPathResolver.resolve$default((SearchPathResolver) searchPathResolver, (RequiredUnresolvedLibrary) unresolved, false, 2, (Object) null);
        }
        throw new NoWhenBranchMatchedException();
    }
}
