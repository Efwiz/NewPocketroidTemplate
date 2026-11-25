package org.jetbrains.kotlin.context;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010 \n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J!\u0010\n\u001a\u00020\u00072\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\f\"\u00020\u0003H\u0016¢\u0006\u0002\u0010\rJ\u0016\u0010\n\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/context/MutableModuleContext;", "Lorg/jetbrains/kotlin/context/ModuleContext;", "module", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "initializeModuleContents", "", "packageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "setDependencies", "dependencies", "", "([Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;)V", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MutableModuleContext extends ModuleContext {
    @Override // org.jetbrains.kotlin.context.ModuleContext
    ModuleDescriptorImpl getModule();

    default void setDependencies(ModuleDescriptorImpl... dependencies) {
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        getModule().setDependencies((ModuleDescriptorImpl[]) Arrays.copyOf(dependencies, dependencies.length));
    }

    default void setDependencies(List<ModuleDescriptorImpl> dependencies) {
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        getModule().setDependencies(dependencies);
    }

    default void initializeModuleContents(PackageFragmentProvider packageFragmentProvider) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "packageFragmentProvider");
        getModule().initialize(packageFragmentProvider);
    }
}
