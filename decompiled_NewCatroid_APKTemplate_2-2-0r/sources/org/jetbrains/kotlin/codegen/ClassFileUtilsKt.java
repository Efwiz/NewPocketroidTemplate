package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.load.kotlin.ModuleMappingUtilKt;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.JvmModuleProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.BinaryModuleData;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMapping;
import org.jetbrains.kotlin.metadata.jvm.deserialization.PackageParts;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.serialization.StringTableImpl;

/* JADX INFO: compiled from: classFileUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\"\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0001*\b\u0012\u0004\u0012\u00020\u000f0\u0001\u001a\u0010\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0003*\u00020\u0011\u001a\u000e\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u0005H\u0002¨\u0006\u0014"}, d2 = {"addCompiledParts", "", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/PackageParts;", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "addCompiledPartsAndSort", "addDataFromCompiledModule", "", "Lorg/jetbrains/kotlin/metadata/jvm/JvmModuleProtoBuf$Module$Builder;", "registry", "Lorg/jetbrains/kotlin/codegen/PackagePartRegistry;", "stringTable", "Lorg/jetbrains/kotlin/serialization/StringTableImpl;", "filterClassFiles", "Lorg/jetbrains/kotlin/backend/common/output/OutputFile;", "getClassFiles", "Lorg/jetbrains/kotlin/codegen/ClassFileFactory;", "loadCompiledModule", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassFileUtilsKt {
    public static final Iterable<OutputFile> getClassFiles(ClassFileFactory classFileFactory) {
        Intrinsics.checkNotNullParameter(classFileFactory, "<this>");
        List<OutputFile> listAsList = classFileFactory.asList();
        Intrinsics.checkNotNullExpressionValue(listAsList, "asList(...)");
        return filterClassFiles(listAsList);
    }

    public static final List<OutputFile> filterClassFiles(List<? extends OutputFile> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (StringsKt.endsWith$default(((OutputFile) obj).getRelativePath(), ".class", false, 2, (Object) null)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final void addDataFromCompiledModule(JvmModuleProtoBuf.Module.Builder builder, PackagePartRegistry registry, StringTableImpl stringTable, GenerationState state) {
        ArrayList arrayListEmptyList;
        BinaryModuleData moduleData;
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(registry, "registry");
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        Intrinsics.checkNotNullParameter(state, "state");
        Iterator<PackageParts> it = addCompiledPartsAndSort(registry.getParts().values(), state).iterator();
        while (it.hasNext()) {
            it.next().addTo(builder);
        }
        Set set = CollectionsKt.toSet(registry.getOptionalAnnotations());
        ModuleMapping moduleMappingLoadCompiledModule = loadCompiledModule(state);
        if (moduleMappingLoadCompiledModule == null || (moduleData = moduleMappingLoadCompiledModule.getModuleData()) == null) {
            arrayListEmptyList = null;
        } else {
            List<ProtoBuf.Class> optionalAnnotations = moduleData.getOptionalAnnotations();
            ArrayList arrayList = new ArrayList();
            for (ProtoBuf.Class r3 : optionalAnnotations) {
                ModuleDescriptor module = state.getModule();
                ClassId classIdFromString = ClassId.fromString(moduleData.getNameResolver().getQualifiedClassName(r3.getFqName()));
                Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
                ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(module, classIdFromString);
                if (classDescriptorFindClassAcrossModuleDependencies != null) {
                    arrayList.add(classDescriptorFindClassAcrossModuleDependencies);
                }
            }
            arrayListEmptyList = arrayList;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        Set setPlus = SetsKt.plus(set, (Iterable) arrayListEmptyList);
        DescriptorSerializer descriptorSerializerCreateTopLevel$default = DescriptorSerializer.Companion.createTopLevel$default(DescriptorSerializer.INSTANCE, new JvmOptionalAnnotationSerializerExtension(stringTable), CommonConfigurationKeysKt.getLanguageVersionSettings(state.getConfiguration()), null, 4, null);
        Iterator it2 = setPlus.iterator();
        while (it2.hasNext()) {
            builder.addOptionalAnnotationClass(descriptorSerializerCreateTopLevel$default.classProto((ClassDescriptor) it2.next()));
        }
    }

    private static final List<PackageParts> addCompiledPartsAndSort(Iterable<PackageParts> iterable, GenerationState generationState) {
        return CollectionsKt.sortedWith(addCompiledParts(iterable, generationState), new Comparator() { // from class: org.jetbrains.kotlin.codegen.ClassFileUtilsKt$addCompiledPartsAndSort$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((PackageParts) t).getPackageFqName(), ((PackageParts) t2).getPackageFqName());
            }
        });
    }

    private static final List<PackageParts> addCompiledParts(Iterable<PackageParts> iterable, GenerationState generationState) {
        Collection<String> obsoletePackageParts;
        ModuleMapping moduleMappingLoadCompiledModule = loadCompiledModule(generationState);
        if (moduleMappingLoadCompiledModule == null) {
            return CollectionsKt.toList(iterable);
        }
        IncrementalCache incrementalCacheForThisTarget = generationState.getIncrementalCacheForThisTarget();
        if (incrementalCacheForThisTarget != null && (obsoletePackageParts = incrementalCacheForThisTarget.getObsoletePackageParts()) != null) {
            for (String str : obsoletePackageParts) {
                String strAsString = JvmClassName.byInternalName(str).getPackageFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                PackageParts packagePartsFindPackageParts = moduleMappingLoadCompiledModule.findPackageParts(strAsString);
                if (packagePartsFindPackageParts != null) {
                    packagePartsFindPackageParts.removePart(str);
                }
            }
        }
        List listPlus = CollectionsKt.plus((Iterable) iterable, (Iterable) moduleMappingLoadCompiledModule.getPackageFqName2Parts().values());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : listPlus) {
            String packageFqName = ((PackageParts) obj).getPackageFqName();
            Object obj2 = linkedHashMap.get(packageFqName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(packageFqName, obj2);
            }
            ((List) obj2).add(obj);
        }
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            String str2 = (String) entry.getKey();
            List list = (List) entry.getValue();
            PackageParts packageParts = new PackageParts(str2);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                packageParts.plusAssign((PackageParts) it.next());
            }
            arrayList.add(packageParts);
        }
        return arrayList;
    }

    private static final ModuleMapping loadCompiledModule(GenerationState generationState) {
        byte[] moduleMappingData;
        IncrementalCache incrementalCacheForThisTarget = generationState.getIncrementalCacheForThisTarget();
        if (incrementalCacheForThisTarget == null || (moduleMappingData = incrementalCacheForThisTarget.getModuleMappingData()) == null) {
            return null;
        }
        return ModuleMappingUtilKt.loadModuleMapping(ModuleMapping.INSTANCE, moduleMappingData, "<incremental>", generationState.getDeserializationConfiguration(), new Function1<JvmMetadataVersion, Unit>() { // from class: org.jetbrains.kotlin.codegen.ClassFileUtilsKt.loadCompiledModule.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JvmMetadataVersion jvmMetadataVersion) {
                invoke2(jvmMetadataVersion);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JvmMetadataVersion version) {
                Intrinsics.checkNotNullParameter(version, "version");
                throw new IllegalStateException("Version of the generated module cannot be incompatible: " + version);
            }
        });
    }
}
