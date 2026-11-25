package org.jetbrains.kotlin.library.metadata.resolver;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.PackageAccessHandler;

/* JADX INFO: compiled from: KotlinResolvedLibrary.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0004R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinResolvedLibrary;", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "isDefault", "", "()Z", "isNeededForLink", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "getLibrary", "()Lorg/jetbrains/kotlin/library/KotlinLibrary;", "resolvedDependencies", "", "getResolvedDependencies", "()Ljava/util/List;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinResolvedLibrary extends PackageAccessHandler {
    KotlinLibrary getLibrary();

    List<KotlinResolvedLibrary> getResolvedDependencies();

    boolean isDefault();

    boolean isNeededForLink();
}
