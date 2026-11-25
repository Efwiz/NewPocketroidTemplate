package org.jetbrains.kotlin.library.metadata;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;

/* JADX INFO: compiled from: KlibResolvedModuleDescriptorsFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\n\u0018\u00002\u00020\u0001B7\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KotlinResolvedModuleDescriptors;", "", "resolvedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "forwardDeclarationsModule", "friendModules", "", "refinesModules", "(Ljava/util/List;Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;Ljava/util/Set;Ljava/util/Set;)V", "getForwardDeclarationsModule", "()Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "getFriendModules", "()Ljava/util/Set;", "getRefinesModules", "getResolvedDescriptors", "()Ljava/util/List;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinResolvedModuleDescriptors {
    private final ModuleDescriptorImpl forwardDeclarationsModule;
    private final Set<ModuleDescriptorImpl> friendModules;
    private final Set<ModuleDescriptorImpl> refinesModules;
    private final List<ModuleDescriptorImpl> resolvedDescriptors;

    public KotlinResolvedModuleDescriptors(List<ModuleDescriptorImpl> resolvedDescriptors, ModuleDescriptorImpl forwardDeclarationsModule, Set<ModuleDescriptorImpl> friendModules, Set<ModuleDescriptorImpl> refinesModules) {
        Intrinsics.checkNotNullParameter(resolvedDescriptors, "resolvedDescriptors");
        Intrinsics.checkNotNullParameter(forwardDeclarationsModule, "forwardDeclarationsModule");
        Intrinsics.checkNotNullParameter(friendModules, "friendModules");
        Intrinsics.checkNotNullParameter(refinesModules, "refinesModules");
        this.resolvedDescriptors = resolvedDescriptors;
        this.forwardDeclarationsModule = forwardDeclarationsModule;
        this.friendModules = friendModules;
        this.refinesModules = refinesModules;
    }

    public final List<ModuleDescriptorImpl> getResolvedDescriptors() {
        return this.resolvedDescriptors;
    }

    public final ModuleDescriptorImpl getForwardDeclarationsModule() {
        return this.forwardDeclarationsModule;
    }

    public final Set<ModuleDescriptorImpl> getFriendModules() {
        return this.friendModules;
    }

    public final Set<ModuleDescriptorImpl> getRefinesModules() {
        return this.refinesModules;
    }
}
