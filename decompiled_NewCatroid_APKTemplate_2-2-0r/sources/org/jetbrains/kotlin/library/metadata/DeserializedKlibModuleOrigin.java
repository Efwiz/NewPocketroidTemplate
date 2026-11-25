package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;

/* JADX INFO: compiled from: KlibModuleOrigin.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/DeserializedKlibModuleOrigin;", "Lorg/jetbrains/kotlin/library/metadata/CompiledKlibModuleOrigin;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)V", "getLibrary", "()Lorg/jetbrains/kotlin/library/KotlinLibrary;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeserializedKlibModuleOrigin extends CompiledKlibModuleOrigin {
    private final KotlinLibrary library;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeserializedKlibModuleOrigin(KotlinLibrary library) {
        super(null);
        Intrinsics.checkNotNullParameter(library, "library");
        this.library = library;
    }

    public final KotlinLibrary getLibrary() {
        return this.library;
    }
}
