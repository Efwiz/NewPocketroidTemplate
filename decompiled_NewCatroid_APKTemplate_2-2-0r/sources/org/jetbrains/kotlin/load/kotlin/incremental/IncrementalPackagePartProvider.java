package org.jetbrains.kotlin.load.kotlin.incremental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartProviderBase;
import org.jetbrains.kotlin.load.kotlin.ModuleMappingUtilKt;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMapping;
import org.jetbrains.kotlin.metadata.jvm.deserialization.PackageParts;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;

/* JADX INFO: compiled from: IncrementalPackagePartProvider.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\u0006\u0010\u001b\u001a\u00020\tH\u0016J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0004H\u0016J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00042\u0006\u0010 \u001a\u00020\tH\u0016J\b\u0010!\u001a\u00020\"H\u0016R!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R!\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\r\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackagePartProvider;", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "parent", "incrementalCaches", "", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "(Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;Ljava/util/List;)V", "allPackageNames", "", "", "getAllPackageNames", "()Ljava/util/Set;", "allPackageNames$delegate", "Lkotlin/Lazy;", "deserializationConfiguration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "getDeserializationConfiguration", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "setDeserializationConfiguration", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;)V", "moduleMappings", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;", "getModuleMappings", "()Ljava/util/List;", "moduleMappings$delegate", "computePackageSetWithNonClassDeclarations", "findPackageParts", "packageFqName", "getAllOptionalAnnotationClasses", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "getAnnotationsOnBinaryModule", "Lorg/jetbrains/kotlin/name/ClassId;", "moduleName", "mayHaveOptionalAnnotationClasses", "", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalPackagePartProvider implements PackagePartProvider {

    /* JADX INFO: renamed from: allPackageNames$delegate, reason: from kotlin metadata */
    private final Lazy allPackageNames;
    public DeserializationConfiguration deserializationConfiguration;

    /* JADX INFO: renamed from: moduleMappings$delegate, reason: from kotlin metadata */
    private final Lazy moduleMappings;
    private final PackagePartProvider parent;

    public IncrementalPackagePartProvider(PackagePartProvider parent, final List<? extends IncrementalCache> incrementalCaches) {
        DeserializationConfiguration deserializationConfiguration;
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(incrementalCaches, "incrementalCaches");
        this.parent = parent;
        JvmPackagePartProviderBase jvmPackagePartProviderBase = parent instanceof JvmPackagePartProviderBase ? (JvmPackagePartProviderBase) parent : null;
        if (jvmPackagePartProviderBase != null && (deserializationConfiguration = jvmPackagePartProviderBase.getDeserializationConfiguration()) != null) {
            setDeserializationConfiguration(deserializationConfiguration);
        }
        this.moduleMappings = LazyKt.lazy(new Function0<List<? extends ModuleMapping>>() { // from class: org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackagePartProvider$moduleMappings$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ModuleMapping> invoke() {
                List<IncrementalCache> list = incrementalCaches;
                IncrementalPackagePartProvider incrementalPackagePartProvider = this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(ModuleMappingUtilKt.loadModuleMapping(ModuleMapping.INSTANCE, ((IncrementalCache) it.next()).getModuleMappingData(), "<incremental>", incrementalPackagePartProvider.getDeserializationConfiguration(), new Function1<JvmMetadataVersion, Unit>() { // from class: org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackagePartProvider$moduleMappings$2$1$1
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(JvmMetadataVersion jvmMetadataVersion) {
                            invoke2(jvmMetadataVersion);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(JvmMetadataVersion version) {
                            Intrinsics.checkNotNullParameter(version, "version");
                            throw new IllegalStateException("Version of the generated module should not be incompatible: " + version);
                        }
                    }));
                }
                return arrayList;
            }
        });
        this.allPackageNames = LazyKt.lazy(new Function0<Set<? extends String>>() { // from class: org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackagePartProvider$allPackageNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends String> invoke() {
                IncrementalPackagePartProvider incrementalPackagePartProvider = this.this$0;
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                Iterator it = incrementalPackagePartProvider.getModuleMappings().iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(setCreateSetBuilder, ((ModuleMapping) it.next()).getPackageFqName2Parts().keySet());
                }
                setCreateSetBuilder.addAll(incrementalPackagePartProvider.parent.computePackageSetWithNonClassDeclarations());
                return SetsKt.build(setCreateSetBuilder);
            }
        });
    }

    public final DeserializationConfiguration getDeserializationConfiguration() {
        DeserializationConfiguration deserializationConfiguration = this.deserializationConfiguration;
        if (deserializationConfiguration != null) {
            return deserializationConfiguration;
        }
        Intrinsics.throwUninitializedPropertyAccessException("deserializationConfiguration");
        return null;
    }

    public final void setDeserializationConfiguration(DeserializationConfiguration deserializationConfiguration) {
        Intrinsics.checkNotNullParameter(deserializationConfiguration, "<set-?>");
        this.deserializationConfiguration = deserializationConfiguration;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<ModuleMapping> getModuleMappings() {
        return (List) this.moduleMappings.getValue();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public List<String> findPackageParts(String packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        List<ModuleMapping> moduleMappings = getModuleMappings();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = moduleMappings.iterator();
        while (it.hasNext()) {
            PackageParts packagePartsFindPackageParts = ((ModuleMapping) it.next()).findPackageParts(packageFqName);
            if (packagePartsFindPackageParts != null) {
                arrayList.add(packagePartsFindPackageParts);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, ((PackageParts) it2.next()).getParts());
        }
        return CollectionsKt.distinct(CollectionsKt.plus((Collection) arrayList2, (Iterable) this.parent.findPackageParts(packageFqName)));
    }

    private final Set<String> getAllPackageNames() {
        return (Set) this.allPackageNames.getValue();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public Set<String> computePackageSetWithNonClassDeclarations() {
        return getAllPackageNames();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public List<ClassId> getAnnotationsOnBinaryModule(String moduleName) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        return this.parent.getAnnotationsOnBinaryModule(moduleName);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public List<ClassData> getAllOptionalAnnotationClasses() {
        List<ModuleMapping> moduleMappings = getModuleMappings();
        JvmPackagePartProviderBase.Companion companion = JvmPackagePartProviderBase.INSTANCE;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = moduleMappings.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, companion.getAllOptionalAnnotationClasses((ModuleMapping) it.next()));
        }
        return CollectionsKt.plus((Collection) arrayList, (Iterable) this.parent.getAllOptionalAnnotationClasses());
    }

    /* JADX WARN: Code duplicated, block: B:16:0x003f A[ORIG_RETURN, RETURN] */
    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public boolean mayHaveOptionalAnnotationClasses() {
        List<ModuleMapping> moduleMappings = getModuleMappings();
        if (!(moduleMappings instanceof Collection) || !moduleMappings.isEmpty()) {
            Iterator<T> it = moduleMappings.iterator();
            while (it.hasNext()) {
                if (!((ModuleMapping) it.next()).getModuleData().getOptionalAnnotations().isEmpty()) {
                }
            }
            if (this.parent.mayHaveOptionalAnnotationClasses()) {
                return false;
            }
        } else if (this.parent.mayHaveOptionalAnnotationClasses()) {
            return false;
        }
        return true;
    }
}
