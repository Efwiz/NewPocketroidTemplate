package org.jetbrains.kotlin.resolve.jvm.extensions;

import kotlin.Metadata;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: PackageFragmentProviderExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010J<\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/extensions/PackageFragmentProviderExtension;", "", "getPackageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PackageFragmentProviderExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    PackageFragmentProvider getPackageFragmentProvider(Project project, ModuleDescriptor module, StorageManager storageManager, BindingTrace trace, ModuleInfo moduleInfo, LookupTracker lookupTracker);

    /* JADX INFO: compiled from: PackageFragmentProviderExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/extensions/PackageFragmentProviderExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/resolve/jvm/extensions/PackageFragmentProviderExtension;", "()V", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<PackageFragmentProviderExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.packageFragmentProviderExtension", PackageFragmentProviderExtension.class);
        }
    }
}
