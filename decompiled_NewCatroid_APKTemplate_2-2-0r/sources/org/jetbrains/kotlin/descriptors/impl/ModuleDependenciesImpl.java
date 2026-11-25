package org.jetbrains.kotlin.descriptors.impl;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ModuleDescriptorImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\n\u0018\u00002\u00020\u0001B=\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\u0002\u0010\tR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/ModuleDependenciesImpl;", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDependencies;", "allDependencies", "", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "modulesWhoseInternalsAreVisible", "", "directExpectedByDependencies", "allExpectedByDependencies", "(Ljava/util/List;Ljava/util/Set;Ljava/util/List;Ljava/util/Set;)V", "getAllDependencies", "()Ljava/util/List;", "getAllExpectedByDependencies", "()Ljava/util/Set;", "getDirectExpectedByDependencies", "getModulesWhoseInternalsAreVisible", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleDependenciesImpl implements ModuleDependencies {
    private final List<ModuleDescriptorImpl> allDependencies;
    private final Set<ModuleDescriptorImpl> allExpectedByDependencies;
    private final List<ModuleDescriptorImpl> directExpectedByDependencies;
    private final Set<ModuleDescriptorImpl> modulesWhoseInternalsAreVisible;

    public ModuleDependenciesImpl(List<ModuleDescriptorImpl> allDependencies, Set<ModuleDescriptorImpl> modulesWhoseInternalsAreVisible, List<ModuleDescriptorImpl> directExpectedByDependencies, Set<ModuleDescriptorImpl> allExpectedByDependencies) {
        Intrinsics.checkNotNullParameter(allDependencies, "allDependencies");
        Intrinsics.checkNotNullParameter(modulesWhoseInternalsAreVisible, "modulesWhoseInternalsAreVisible");
        Intrinsics.checkNotNullParameter(directExpectedByDependencies, "directExpectedByDependencies");
        Intrinsics.checkNotNullParameter(allExpectedByDependencies, "allExpectedByDependencies");
        this.allDependencies = allDependencies;
        this.modulesWhoseInternalsAreVisible = modulesWhoseInternalsAreVisible;
        this.directExpectedByDependencies = directExpectedByDependencies;
        this.allExpectedByDependencies = allExpectedByDependencies;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleDependencies
    public List<ModuleDescriptorImpl> getAllDependencies() {
        return this.allDependencies;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleDependencies
    public Set<ModuleDescriptorImpl> getModulesWhoseInternalsAreVisible() {
        return this.modulesWhoseInternalsAreVisible;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleDependencies
    public List<ModuleDescriptorImpl> getDirectExpectedByDependencies() {
        return this.directExpectedByDependencies;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleDependencies
    public Set<ModuleDescriptorImpl> getAllExpectedByDependencies() {
        return this.allExpectedByDependencies;
    }
}
