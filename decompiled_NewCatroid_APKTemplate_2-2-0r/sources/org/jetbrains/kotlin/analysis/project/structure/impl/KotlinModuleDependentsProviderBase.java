package org.jetbrains.kotlin.analysis.project.structure.impl;

import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.project.structure.KotlinModuleDependentsProvider;
import org.jetbrains.kotlin.analysis.project.structure.KtModule;

/* JADX INFO: compiled from: KotlinModuleDependentsProviderBase.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0004J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/impl/KotlinModuleDependentsProviderBase;", "Lorg/jetbrains/kotlin/analysis/project/structure/KotlinModuleDependentsProvider;", "()V", "computeTransitiveDependents", "", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "module", "getTransitiveDependents", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinModuleDependentsProviderBase extends KotlinModuleDependentsProvider {
    @Override // org.jetbrains.kotlin.analysis.project.structure.KotlinModuleDependentsProvider
    public Set<KtModule> getTransitiveDependents(KtModule module) {
        Intrinsics.checkNotNullParameter(module, "module");
        return computeTransitiveDependents(module);
    }

    protected final Set<KtModule> computeTransitiveDependents(KtModule module) {
        Intrinsics.checkNotNullParameter(module, "module");
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        computeTransitiveDependents$lambda$0$visit(setCreateSetBuilder, this, module);
        setCreateSetBuilder.remove(module);
        return SetsKt.build(setCreateSetBuilder);
    }

    private static final void computeTransitiveDependents$lambda$0$visit(Set<KtModule> set, KotlinModuleDependentsProviderBase kotlinModuleDependentsProviderBase, KtModule ktModule) {
        if (set.contains(ktModule)) {
            return;
        }
        set.add(ktModule);
        Iterator<T> it = kotlinModuleDependentsProviderBase.getDirectDependents(ktModule).iterator();
        while (it.hasNext()) {
            computeTransitiveDependents$lambda$0$visit(set, kotlinModuleDependentsProviderBase, (KtModule) it.next());
        }
    }
}
