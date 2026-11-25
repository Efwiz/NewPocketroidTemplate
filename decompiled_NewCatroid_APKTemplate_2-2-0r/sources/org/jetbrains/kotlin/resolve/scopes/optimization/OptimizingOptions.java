package org.jetbrains.kotlin.resolve.scopes.optimization;

import kotlin.Metadata;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: OptimizingOptions.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\u0006J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;", "", "shouldCalculateAllNamesForLazyImportScopeOptimizing", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "Default", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface OptimizingOptions {
    boolean shouldCalculateAllNamesForLazyImportScopeOptimizing(ModuleDescriptor moduleDescriptor);

    /* JADX INFO: compiled from: OptimizingOptions.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions$Default;", "Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;", "()V", "shouldCalculateAllNamesForLazyImportScopeOptimizing", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements OptimizingOptions {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.resolve.scopes.optimization.OptimizingOptions
        public boolean shouldCalculateAllNamesForLazyImportScopeOptimizing(ModuleDescriptor moduleDescriptor) {
            return true;
        }

        private Default() {
        }
    }
}
