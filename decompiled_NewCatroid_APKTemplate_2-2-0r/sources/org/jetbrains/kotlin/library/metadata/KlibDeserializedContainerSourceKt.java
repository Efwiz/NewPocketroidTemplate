package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;

/* JADX INFO: compiled from: KlibDeserializedContainerSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"isInteropLibrary", "", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibDeserializedContainerSourceKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isInteropLibrary(KotlinLibrary kotlinLibrary) {
        return Intrinsics.areEqual(KotlinLibraryKt.getIrProviderName(kotlinLibrary), NativeLibraryConstantsKt.KLIB_INTEROP_IR_PROVIDER_IDENTIFIER);
    }
}
