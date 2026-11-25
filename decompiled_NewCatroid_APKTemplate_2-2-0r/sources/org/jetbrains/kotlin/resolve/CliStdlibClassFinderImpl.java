package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: StdlibClassFinder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CliStdlibClassFinderImpl;", "Lorg/jetbrains/kotlin/resolve/StdlibClassFinder;", "()V", "findEnumEntriesClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CliStdlibClassFinderImpl implements StdlibClassFinder {
    public static final CliStdlibClassFinderImpl INSTANCE = new CliStdlibClassFinderImpl();

    private CliStdlibClassFinderImpl() {
    }

    @Override // org.jetbrains.kotlin.resolve.StdlibClassFinder
    public ClassDescriptor findEnumEntriesClass(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        return FindClassInModuleKt.findClassAcrossModuleDependencies(moduleDescriptor, StandardClassIds.INSTANCE.getEnumEntries());
    }
}
