package org.jetbrains.kotlin.konan.library;

import kotlin.Metadata;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.SearchPathResolver;

/* JADX INFO: compiled from: SearchPathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/SearchPathResolverWithTarget;", "L", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/SearchPathResolver;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface SearchPathResolverWithTarget<L extends KotlinLibrary> extends SearchPathResolver<L> {
    KonanTarget getTarget();
}
