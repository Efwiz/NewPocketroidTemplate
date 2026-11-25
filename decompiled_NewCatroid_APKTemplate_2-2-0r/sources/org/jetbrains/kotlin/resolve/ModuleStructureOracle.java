package org.jetbrains.kotlin.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: ModuleStructureOracle.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = SingleModule.class)
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\nJ\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ModuleStructureOracle;", "", "findAllDependsOnPaths", "", "Lorg/jetbrains/kotlin/resolve/ModulePath;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "findAllReversedDependsOnPaths", "hasImplementingModules", "", "SingleModule", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ModuleStructureOracle {
    List<ModulePath> findAllDependsOnPaths(ModuleDescriptor module);

    List<ModulePath> findAllReversedDependsOnPaths(ModuleDescriptor module);

    boolean hasImplementingModules(ModuleDescriptor module);

    /* JADX INFO: compiled from: ModuleStructureOracle.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ModuleStructureOracle$SingleModule;", "Lorg/jetbrains/kotlin/resolve/ModuleStructureOracle;", "()V", "findAllDependsOnPaths", "", "Lorg/jetbrains/kotlin/resolve/ModulePath;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "findAllReversedDependsOnPaths", "hasImplementingModules", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SingleModule implements ModuleStructureOracle {
        public static final SingleModule INSTANCE = new SingleModule();

        @Override // org.jetbrains.kotlin.resolve.ModuleStructureOracle
        public boolean hasImplementingModules(ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(module, "module");
            return false;
        }

        private SingleModule() {
        }

        @Override // org.jetbrains.kotlin.resolve.ModuleStructureOracle
        public List<ModulePath> findAllReversedDependsOnPaths(ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(module, "module");
            return CollectionsKt.listOf(new ModulePath(module));
        }

        @Override // org.jetbrains.kotlin.resolve.ModuleStructureOracle
        public List<ModulePath> findAllDependsOnPaths(ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(module, "module");
            return CollectionsKt.listOf(new ModulePath(module));
        }
    }
}
