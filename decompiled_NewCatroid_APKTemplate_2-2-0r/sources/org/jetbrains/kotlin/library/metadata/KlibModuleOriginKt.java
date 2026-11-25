package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;

/* JADX INFO: compiled from: KlibModuleOrigin.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u001a\f\u0010\t\u001a\u00020\n*\u00020\u0001H\u0000\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"klibModuleOrigin", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleOrigin;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getKlibModuleOrigin", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/library/metadata/KlibModuleOrigin;", "kotlinLibrary", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "getKotlinLibrary", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/library/KotlinLibrary;", "isInteropLibrary", "", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibModuleOriginKt {
    public static final boolean isInteropLibrary(KlibModuleOrigin klibModuleOrigin) {
        Intrinsics.checkNotNullParameter(klibModuleOrigin, "<this>");
        if (klibModuleOrigin instanceof DeserializedKlibModuleOrigin) {
            return KotlinLibraryKt.isInterop(((DeserializedKlibModuleOrigin) klibModuleOrigin).getLibrary());
        }
        if (Intrinsics.areEqual(klibModuleOrigin, CurrentKlibModuleOrigin.INSTANCE) ? true : Intrinsics.areEqual(klibModuleOrigin, SyntheticModulesOrigin.INSTANCE)) {
            return false;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final KlibModuleOrigin getKlibModuleOrigin(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Object capability = moduleDescriptor.getCapability(KlibModuleOrigin.INSTANCE.getCAPABILITY());
        Intrinsics.checkNotNull(capability);
        return (KlibModuleOrigin) capability;
    }

    public static final KotlinLibrary getKotlinLibrary(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        KlibModuleOrigin klibModuleOrigin = getKlibModuleOrigin(moduleDescriptor);
        Intrinsics.checkNotNull(klibModuleOrigin, "null cannot be cast to non-null type org.jetbrains.kotlin.library.metadata.DeserializedKlibModuleOrigin");
        return ((DeserializedKlibModuleOrigin) klibModuleOrigin).getLibrary();
    }
}
