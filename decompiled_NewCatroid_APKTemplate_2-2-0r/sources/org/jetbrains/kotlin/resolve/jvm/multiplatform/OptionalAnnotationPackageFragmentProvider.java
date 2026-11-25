package org.jetbrains.kotlin.resolve.jvm.multiplatform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.EnumEntriesDeserializationSupportImpl;
import org.jetbrains.kotlin.resolve.jvm.JvmCompilerDeserializationConfiguration;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoaderImpl;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.ContractDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.LocalClassifierTypeSettings;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: OptionalAnnotationPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u001e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\u0019H\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u001b2\u0006\u0010\u0017\u001a\u00020\u000fH\u0017J*\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001d2\u0006\u0010\u0017\u001a\u00020\u000f2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u001fH\u0016J\u0010\u0010\"\u001a\u00020!2\u0006\u0010\u0017\u001a\u00020\u000fH\u0016R'\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/multiplatform/OptionalAnnotationPackageFragmentProvider;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProviderOptimized;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "packagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;)V", "packages", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getPackages", "()Ljava/util/Map;", "packages$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "collectPackageFragments", "", "fqName", "packageFragments", "", "getPackageFragments", "", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "isEmpty", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptionalAnnotationPackageFragmentProvider implements PackageFragmentProviderOptimized {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(OptionalAnnotationPackageFragmentProvider.class), "packages", "getPackages()Ljava/util/Map;"))};

    /* JADX INFO: renamed from: packages$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue packages;

    public OptionalAnnotationPackageFragmentProvider(final ModuleDescriptor module, final StorageManager storageManager, final NotFoundClasses notFoundClasses, final LanguageVersionSettings languageVersionSettings, final PackagePartProvider packagePartProvider) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        this.packages = storageManager.createLazyValue(new Function0<Map<FqName, ? extends PackageFragmentDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.jvm.multiplatform.OptionalAnnotationPackageFragmentProvider$packages$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<FqName, ? extends PackageFragmentDescriptor> invoke() {
                List<ClassData> allOptionalAnnotationClasses = packagePartProvider.getAllOptionalAnnotationClasses();
                if (allOptionalAnnotationClasses.isEmpty()) {
                    return MapsKt.emptyMap();
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                final StorageManager storageManager2 = storageManager;
                final ModuleDescriptor moduleDescriptor = module;
                final LanguageVersionSettings languageVersionSettings2 = languageVersionSettings;
                final NotFoundClasses notFoundClasses2 = notFoundClasses;
                final OptionalAnnotationPackageFragmentProvider optionalAnnotationPackageFragmentProvider = this;
                final BuiltInSerializerProtocol builtInSerializerProtocol = BuiltInSerializerProtocol.INSTANCE;
                final OptionalAnnotationClassDataFinder optionalAnnotationClassDataFinder = new OptionalAnnotationClassDataFinder(allOptionalAnnotationClasses);
                final NotNullLazyValue notNullLazyValueCreateLazyValue = storageManager2.createLazyValue(new Function0<DeserializationComponents>() { // from class: org.jetbrains.kotlin.resolve.jvm.multiplatform.OptionalAnnotationPackageFragmentProvider$packages$2$1$components$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final DeserializationComponents invoke() {
                        StorageManager storageManager3 = storageManager2;
                        ModuleDescriptor moduleDescriptor2 = moduleDescriptor;
                        JvmCompilerDeserializationConfiguration jvmCompilerDeserializationConfiguration = new JvmCompilerDeserializationConfiguration(languageVersionSettings2);
                        OptionalAnnotationClassDataFinder optionalAnnotationClassDataFinder2 = optionalAnnotationClassDataFinder;
                        AnnotationAndConstantLoaderImpl annotationAndConstantLoaderImpl = new AnnotationAndConstantLoaderImpl(moduleDescriptor, notFoundClasses2, builtInSerializerProtocol);
                        OptionalAnnotationPackageFragmentProvider optionalAnnotationPackageFragmentProvider2 = optionalAnnotationPackageFragmentProvider;
                        LocalClassifierTypeSettings.Default r8 = LocalClassifierTypeSettings.Default.INSTANCE;
                        ErrorReporter DO_NOTHING = ErrorReporter.DO_NOTHING;
                        Intrinsics.checkNotNullExpressionValue(DO_NOTHING, "DO_NOTHING");
                        return new DeserializationComponents(storageManager3, moduleDescriptor2, jvmCompilerDeserializationConfiguration, optionalAnnotationClassDataFinder2, annotationAndConstantLoaderImpl, optionalAnnotationPackageFragmentProvider2, r8, DO_NOTHING, LookupTracker.DO_NOTHING.INSTANCE, FlexibleTypeDeserializer.ThrowException.INSTANCE, CollectionsKt.emptyList(), notFoundClasses2, ContractDeserializer.INSTANCE.getDEFAULT(), null, null, builtInSerializerProtocol.getExtensionRegistry(), null, SamConversionResolver.Empty.INSTANCE, null, new EnumEntriesDeserializationSupportImpl(moduleDescriptor.getPlatform()), 352256, null);
                    }
                });
                Set<Map.Entry<ClassId, ClassData>> setEntrySet = optionalAnnotationClassDataFinder.getClassIdToData().entrySet();
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                for (Object obj : setEntrySet) {
                    FqName packageFqName = ((ClassId) ((Map.Entry) obj).getKey()).getPackageFqName();
                    Object obj2 = linkedHashMap2.get(packageFqName);
                    if (obj2 == null) {
                        obj2 = (List) new ArrayList();
                        linkedHashMap2.put(packageFqName, obj2);
                    }
                    ((List) obj2).add(obj);
                }
                for (Map.Entry entry : linkedHashMap2.entrySet()) {
                    FqName fqName = (FqName) entry.getKey();
                    final List list = (List) entry.getValue();
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ClassId classId = (ClassId) ((Map.Entry) it.next()).getKey();
                        Name shortClassName = classId.getShortClassName();
                        if (classId.isNestedClass()) {
                            shortClassName = null;
                        }
                        if (shortClassName != null) {
                            arrayList.add(shortClassName);
                        }
                    }
                    Set set = CollectionsKt.toSet(arrayList);
                    NotNullLazyValue notNullLazyValueCreateLazyValue2 = storageManager2.createLazyValue(new Function0<Map<Name, ? extends ClassDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.jvm.multiplatform.OptionalAnnotationPackageFragmentProvider$packages$2$1$classDescriptors$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Map<Name, ? extends ClassDescriptor> invoke() {
                            List<Map.Entry<ClassId, ClassData>> list2 = list;
                            NotNullLazyValue<DeserializationComponents> notNullLazyValue = notNullLazyValueCreateLazyValue;
                            ArrayList arrayList2 = new ArrayList();
                            Iterator<T> it2 = list2.iterator();
                            while (it2.hasNext()) {
                                Map.Entry entry2 = (Map.Entry) it2.next();
                                ClassDescriptor classDescriptorDeserializeClass = notNullLazyValue.invoke().getClassDeserializer().deserializeClass((ClassId) entry2.getKey(), (ClassData) entry2.getValue());
                                if (classDescriptorDeserializeClass != null) {
                                    arrayList2.add(classDescriptorDeserializeClass);
                                }
                            }
                            ArrayList arrayList3 = arrayList2;
                            LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList3, 10)), 16));
                            for (Object obj3 : arrayList3) {
                                linkedHashMap3.put(((ClassDescriptor) obj3).getShortName(), obj3);
                            }
                            return linkedHashMap3;
                        }
                    });
                    Intrinsics.checkNotNull(fqName);
                    linkedHashMap.put(fqName, new PackageFragmentForOptionalAnnotations(moduleDescriptor, fqName, set, notNullLazyValueCreateLazyValue2));
                }
                return linkedHashMap;
            }
        });
    }

    public final Map<FqName, PackageFragmentDescriptor> getPackages() {
        return (Map) StorageKt.getValue(this.packages, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> packageFragments) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(packageFragments, getPackages().get(fqName));
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public boolean isEmpty(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return !getPackages().containsKey(fqName);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    @Deprecated(message = "for usages use #packageFragments(FqName) at final point, for impl use #collectPackageFragments(FqName, MutableCollection<PackageFragmentDescriptor>)")
    public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        PackageFragmentDescriptor packageFragmentDescriptor = getPackages().get(fqName);
        List<PackageFragmentDescriptor> listListOf = packageFragmentDescriptor != null ? CollectionsKt.listOf(packageFragmentDescriptor) : null;
        return listListOf == null ? CollectionsKt.emptyList() : listListOf;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return CollectionsKt.emptyList();
    }
}
