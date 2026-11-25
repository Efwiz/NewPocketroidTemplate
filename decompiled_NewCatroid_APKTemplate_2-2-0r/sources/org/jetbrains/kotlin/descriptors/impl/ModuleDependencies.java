package org.jetbrains.kotlin.descriptors.impl;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;

/* JADX INFO: compiled from: ModuleDescriptorImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006R\u0018\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/ModuleDependencies;", "", "allDependencies", "", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "getAllDependencies", "()Ljava/util/List;", "allExpectedByDependencies", "", "getAllExpectedByDependencies", "()Ljava/util/Set;", "directExpectedByDependencies", "getDirectExpectedByDependencies", "modulesWhoseInternalsAreVisible", "getModulesWhoseInternalsAreVisible", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ModuleDependencies {
    List<ModuleDescriptorImpl> getAllDependencies();

    Set<ModuleDescriptorImpl> getAllExpectedByDependencies();

    List<ModuleDescriptorImpl> getDirectExpectedByDependencies();

    Set<ModuleDescriptorImpl> getModulesWhoseInternalsAreVisible();
}
