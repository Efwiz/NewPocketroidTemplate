package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: StdlibClassFinder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0005\u001a\u00020\u0002*\u00020\u0006H\u0000\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"STDLIB_CLASS_FINDER_CAPABILITY", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "Lorg/jetbrains/kotlin/resolve/StdlibClassFinder;", "getSTDLIB_CLASS_FINDER_CAPABILITY", "()Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "getStdlibClassFinder", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class StdlibClassFinderKt {
    private static final ModuleCapability<StdlibClassFinder> STDLIB_CLASS_FINDER_CAPABILITY = new ModuleCapability<>("StdlibClassFinder");

    public static final ModuleCapability<StdlibClassFinder> getSTDLIB_CLASS_FINDER_CAPABILITY() {
        return STDLIB_CLASS_FINDER_CAPABILITY;
    }

    public static final StdlibClassFinder getStdlibClassFinder(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        StdlibClassFinder stdlibClassFinder = (StdlibClassFinder) moduleDescriptor.getCapability(STDLIB_CLASS_FINDER_CAPABILITY);
        return stdlibClassFinder == null ? CliStdlibClassFinderImpl.INSTANCE : stdlibClassFinder;
    }
}
