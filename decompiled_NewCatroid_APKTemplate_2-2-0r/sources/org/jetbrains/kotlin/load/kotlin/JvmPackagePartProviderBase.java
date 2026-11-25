package org.jetbrains.kotlin.load.kotlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.BinaryModuleData;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMapping;
import org.jetbrains.kotlin.metadata.jvm.deserialization.PackageParts;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;

/* JADX INFO: compiled from: JvmPackagePartProviderBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b&\u0018\u0000 #*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0002#$B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\u0006\u0010\u0017\u001a\u00020\u0006H\u0016J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\u0006\u0010\u0017\u001a\u00020\u0006H\u0016J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0016H\u0016J\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00162\u0006\u0010\u001d\u001a\u00020\u0006H\u0016J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u0010\u0017\u001a\u00020\u0006H\u0002J\b\u0010!\u001a\u00020\"H\u0016R!\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00110\u0010X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartProviderBase;", "MappingsKey", "Lorg/jetbrains/kotlin/load/kotlin/PackageAndMetadataPartProvider;", "()V", "allPackageNames", "", "", "getAllPackageNames", "()Ljava/util/Set;", "allPackageNames$delegate", "Lkotlin/Lazy;", "deserializationConfiguration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "getDeserializationConfiguration", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "loadedModules", "", "Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartProviderBase$ModuleMappingInfo;", "getLoadedModules", "()Ljava/util/List;", "computePackageSetWithNonClassDeclarations", "findMetadataPackageParts", "", "packageFqName", "findPackageParts", "getAllOptionalAnnotationClasses", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "getAnnotationsOnBinaryModule", "Lorg/jetbrains/kotlin/name/ClassId;", "moduleName", "getPackageParts", "", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/PackageParts;", "mayHaveOptionalAnnotationClasses", "", "Companion", "ModuleMappingInfo", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JvmPackagePartProviderBase<MappingsKey> implements PackageAndMetadataPartProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: allPackageNames$delegate, reason: from kotlin metadata */
    private final Lazy allPackageNames = LazyKt.lazy(new Function0<Set<String>>(this) { // from class: org.jetbrains.kotlin.load.kotlin.JvmPackagePartProviderBase$allPackageNames$2
        final /* synthetic */ JvmPackagePartProviderBase<MappingsKey> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super(0);
            this.this$0 = this;
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<String> invoke() {
            List loadedModules = this.this$0.getLoadedModules();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator it = loadedModules.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(linkedHashSet, ((JvmPackagePartProviderBase.ModuleMappingInfo) it.next()).getMapping().getPackageFqName2Parts().keySet());
            }
            return linkedHashSet;
        }
    });

    public abstract DeserializationConfiguration getDeserializationConfiguration();

    protected abstract List<ModuleMappingInfo<MappingsKey>> getLoadedModules();

    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public boolean mayHaveOptionalAnnotationClasses() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: compiled from: JvmPackagePartProviderBase.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0084\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0010\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J2\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001¢\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0007HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartProviderBase$ModuleMappingInfo;", "MappingsKey", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "mapping", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;", "name", "", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;Ljava/lang/String;)V", "getKey", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getMapping", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;", "getName", "()Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;Ljava/lang/String;)Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartProviderBase$ModuleMappingInfo;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ModuleMappingInfo<MappingsKey> {
        private final MappingsKey key;
        private final ModuleMapping mapping;
        private final String name;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ModuleMappingInfo copy$default(ModuleMappingInfo moduleMappingInfo, Object obj, ModuleMapping moduleMapping, String str, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = moduleMappingInfo.key;
            }
            if ((i & 2) != 0) {
                moduleMapping = moduleMappingInfo.mapping;
            }
            if ((i & 4) != 0) {
                str = moduleMappingInfo.name;
            }
            return moduleMappingInfo.copy(obj, moduleMapping, str);
        }

        public final MappingsKey component1() {
            return this.key;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ModuleMapping getMapping() {
            return this.mapping;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getName() {
            return this.name;
        }

        public final ModuleMappingInfo<MappingsKey> copy(MappingsKey key, ModuleMapping mapping, String name2) {
            Intrinsics.checkNotNullParameter(mapping, "mapping");
            Intrinsics.checkNotNullParameter(name2, "name");
            return new ModuleMappingInfo<>(key, mapping, name2);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ModuleMappingInfo)) {
                return false;
            }
            ModuleMappingInfo moduleMappingInfo = (ModuleMappingInfo) other;
            return Intrinsics.areEqual(this.key, moduleMappingInfo.key) && Intrinsics.areEqual(this.mapping, moduleMappingInfo.mapping) && Intrinsics.areEqual(this.name, moduleMappingInfo.name);
        }

        public int hashCode() {
            MappingsKey mappingskey = this.key;
            return ((((mappingskey == null ? 0 : mappingskey.hashCode()) * 31) + this.mapping.hashCode()) * 31) + this.name.hashCode();
        }

        public String toString() {
            return "ModuleMappingInfo(key=" + this.key + ", mapping=" + this.mapping + ", name=" + this.name + ')';
        }

        public ModuleMappingInfo(MappingsKey mappingskey, ModuleMapping mapping, String name2) {
            Intrinsics.checkNotNullParameter(mapping, "mapping");
            Intrinsics.checkNotNullParameter(name2, "name");
            this.key = mappingskey;
            this.mapping = mapping;
            this.name = name2;
        }

        public final MappingsKey getKey() {
            return this.key;
        }

        public final ModuleMapping getMapping() {
            return this.mapping;
        }

        public final String getName() {
            return this.name;
        }
    }

    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public List<String> findPackageParts(String packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Collection<PackageParts> packageParts = getPackageParts(packageFqName);
        if (packageParts.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (PackageParts packageParts2 : packageParts) {
            for (String str : packageParts2.getParts()) {
                String multifileFacadeName = packageParts2.getMultifileFacadeName(str);
                if (multifileFacadeName == null || !linkedHashSet2.contains(multifileFacadeName)) {
                    linkedHashSet.add(str);
                }
            }
            Iterator<T> it = packageParts2.getParts().iterator();
            while (it.hasNext()) {
                String multifileFacadeName2 = packageParts2.getMultifileFacadeName((String) it.next());
                if (multifileFacadeName2 != null) {
                    linkedHashSet2.add(multifileFacadeName2);
                }
            }
        }
        return CollectionsKt.toList(linkedHashSet);
    }

    private final Set<String> getAllPackageNames() {
        return (Set) this.allPackageNames.getValue();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public Set<String> computePackageSetWithNonClassDeclarations() {
        return getAllPackageNames();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.MetadataPartProvider
    public List<String> findMetadataPackageParts(String packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Collection<PackageParts> packageParts = getPackageParts(packageFqName);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = packageParts.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PackageParts) it.next()).getMetadataParts());
        }
        return CollectionsKt.distinct(arrayList);
    }

    private final Collection<PackageParts> getPackageParts(String packageFqName) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (ModuleMappingInfo<MappingsKey> moduleMappingInfo : getLoadedModules()) {
            MappingsKey mappingskeyComponent1 = moduleMappingInfo.component1();
            PackageParts packagePartsFindPackageParts = moduleMappingInfo.getMapping().findPackageParts(packageFqName);
            if (packagePartsFindPackageParts != null) {
                PackageParts packageParts = (PackageParts) linkedHashMap.get(mappingskeyComponent1);
                if (packageParts != null) {
                    packageParts.plusAssign(packagePartsFindPackageParts);
                } else {
                    linkedHashMap.put(mappingskeyComponent1, packagePartsFindPackageParts);
                }
            }
        }
        return linkedHashMap.values();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public List<ClassId> getAnnotationsOnBinaryModule(String moduleName) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        List<ModuleMappingInfo<MappingsKey>> loadedModules = getLoadedModules();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it = loadedModules.iterator();
        while (it.hasNext()) {
            ModuleMappingInfo moduleMappingInfo = (ModuleMappingInfo) it.next();
            ModuleMapping mapping = moduleMappingInfo.getMapping();
            if (Intrinsics.areEqual(moduleMappingInfo.getName(), moduleName)) {
                List<String> annotations = mapping.getModuleData().getAnnotations();
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
                Iterator<T> it2 = annotations.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(ClassId.fromString((String) it2.next()));
                }
                arrayList = arrayList3;
            } else {
                arrayList = null;
            }
            if (arrayList != null) {
                arrayList2.add(arrayList);
            }
        }
        return CollectionsKt.flatten(arrayList2);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
    public List<ClassData> getAllOptionalAnnotationClasses() {
        List<ModuleMappingInfo<MappingsKey>> loadedModules = getLoadedModules();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = loadedModules.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, INSTANCE.getAllOptionalAnnotationClasses(((ModuleMappingInfo) it.next()).getMapping()));
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: JvmPackagePartProviderBase.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartProviderBase$Companion;", "", "()V", "getAllOptionalAnnotationClasses", "", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "module", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<ClassData> getAllOptionalAnnotationClasses(ModuleMapping module) {
            Intrinsics.checkNotNullParameter(module, "module");
            BinaryModuleData moduleData = module.getModuleData();
            List<ProtoBuf.Class> optionalAnnotations = moduleData.getOptionalAnnotations();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(optionalAnnotations, 10));
            for (ProtoBuf.Class r3 : optionalAnnotations) {
                NameResolver nameResolver = moduleData.getNameResolver();
                JvmMetadataVersion version = module.getVersion();
                SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                arrayList.add(new ClassData(nameResolver, r3, version, NO_SOURCE));
            }
            return arrayList;
        }
    }
}
