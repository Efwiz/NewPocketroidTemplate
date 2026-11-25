package org.jetbrains.kotlin.builtins;

import java.util.ServiceLoader;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: BuiltInsLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011J>\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/builtins/BuiltInsLoader;", "", "createPackageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "builtInsModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "classDescriptorFactories", "", "Lorg/jetbrains/kotlin/descriptors/deserialization/ClassDescriptorFactory;", "platformDependentDeclarationFilter", "Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;", "additionalClassPartsProvider", "Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider;", "isFallback", "", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BuiltInsLoader {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    PackageFragmentProvider createPackageFragmentProvider(StorageManager storageManager, ModuleDescriptor builtInsModule, Iterable<? extends ClassDescriptorFactory> classDescriptorFactories, PlatformDependentDeclarationFilter platformDependentDeclarationFilter, AdditionalClassPartsProvider additionalClassPartsProvider, boolean isFallback);

    /* JADX INFO: compiled from: BuiltInsLoader.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/builtins/BuiltInsLoader$Companion;", "", "()V", "Instance", "Lorg/jetbrains/kotlin/builtins/BuiltInsLoader;", "getInstance", "()Lorg/jetbrains/kotlin/builtins/BuiltInsLoader;", "Instance$delegate", "Lkotlin/Lazy;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        /* JADX INFO: renamed from: Instance$delegate, reason: from kotlin metadata */
        private static final Lazy<BuiltInsLoader> Instance = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<BuiltInsLoader>() { // from class: org.jetbrains.kotlin.builtins.BuiltInsLoader$Companion$Instance$2
            @Override // kotlin.jvm.functions.Function0
            public final BuiltInsLoader invoke() {
                ServiceLoader serviceLoaderLoad = ServiceLoader.load(BuiltInsLoader.class, BuiltInsLoader.class.getClassLoader());
                Intrinsics.checkNotNull(serviceLoaderLoad);
                BuiltInsLoader builtInsLoader = (BuiltInsLoader) CollectionsKt.firstOrNull(serviceLoaderLoad);
                if (builtInsLoader != null) {
                    return builtInsLoader;
                }
                throw new IllegalStateException("No BuiltInsLoader implementation was found. Please ensure that the META-INF/services/ is not stripped from your application and that the Java virtual machine is not running under a security manager");
            }
        });

        private Companion() {
        }

        public final BuiltInsLoader getInstance() {
            return Instance.getValue();
        }
    }
}
