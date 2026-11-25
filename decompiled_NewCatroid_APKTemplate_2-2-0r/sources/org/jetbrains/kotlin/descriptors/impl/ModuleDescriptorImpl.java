package org.jetbrains.kotlin.descriptors.impl;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.InvalidModuleExceptionKt;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: ModuleDescriptorImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002BS\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u001a\b\u0002\u0010\u000b\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\f\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0010J\b\u0010D\u001a\u00020EH\u0016J#\u0010F\u001a\u0004\u0018\u0001HG\"\u0004\b\u0000\u0010G2\f\u0010H\u001a\b\u0012\u0004\u0012\u0002HG0\rH\u0016¢\u0006\u0002\u0010IJ\u0010\u0010J\u001a\u00020;2\u0006\u0010K\u001a\u00020:H\u0016J*\u0010L\u001a\b\u0012\u0004\u0012\u00020:0M2\u0006\u0010K\u001a\u00020:2\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020$0OH\u0016J\u000e\u0010P\u001a\u00020E2\u0006\u0010Q\u001a\u00020*J\u001f\u0010R\u001a\u00020E2\u0012\u0010S\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000T\"\u00020\u0000¢\u0006\u0002\u0010UJ\u0014\u0010R\u001a\u00020E2\f\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00000\u0012J\"\u0010R\u001a\u00020E2\f\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00000\u00122\f\u0010V\u001a\b\u0012\u0004\u0012\u00020\u00000\u0016J\u000e\u0010R\u001a\u00020E2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0010\u0010W\u001a\u00020$2\u0006\u0010X\u001a\u00020\u0002H\u0016J\b\u0010Y\u001a\u00020 H\u0016R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR \u0010\u000b\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0014R\u0014\u0010\u001f\u001a\u00020 8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020$8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b#\u0010%R\u001a\u0010&\u001a\u00020$X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010%\"\u0004\b'\u0010(R\u0011\u0010)\u001a\u00020*8F¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0010\u0010-\u001a\u0004\u0018\u00010*X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010.\u001a\u00020*8F¢\u0006\u0006\u001a\u0004\b/\u0010,R\u001b\u00100\u001a\u0002018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u00105\u001a\u0004\b2\u00103R\u000e\u00106\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00108\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020;09X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010@\u001a\b\u0012\u0004\u0012\u00020\u00000\u00128FX\u0087\u0004¢\u0006\f\u0012\u0004\bA\u0010B\u001a\u0004\bC\u0010\u0014¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/impl/DeclarationDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleName", "Lorg/jetbrains/kotlin/name/Name;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "capabilities", "", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "", "stableName", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/platform/TargetPlatform;Ljava/util/Map;Lorg/jetbrains/kotlin/name/Name;)V", "allDependencyModules", "", "getAllDependencyModules", "()Ljava/util/List;", "allExpectedByModules", "", "getAllExpectedByModules", "()Ljava/util/Set;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "dependencies", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDependencies;", "expectedByModules", "getExpectedByModules", "id", "", "getId", "()Ljava/lang/String;", "isInitialized", "", "()Z", "isValid", "setValid", "(Z)V", "packageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "getPackageFragmentProvider", "()Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "packageFragmentProviderForModuleContent", "packageFragmentProviderForModuleContentWithoutDependencies", "getPackageFragmentProviderForModuleContentWithoutDependencies", "packageFragmentProviderForWholeModuleWithDependencies", "Lorg/jetbrains/kotlin/descriptors/impl/CompositePackageFragmentProvider;", "getPackageFragmentProviderForWholeModuleWithDependencies", "()Lorg/jetbrains/kotlin/descriptors/impl/CompositePackageFragmentProvider;", "packageFragmentProviderForWholeModuleWithDependencies$delegate", "Lkotlin/Lazy;", "packageViewDescriptorFactory", "Lorg/jetbrains/kotlin/descriptors/impl/PackageViewDescriptorFactory;", "packages", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getStableName", "()Lorg/jetbrains/kotlin/name/Name;", "testOnly_AllDependentModules", "getTestOnly_AllDependentModules$annotations", "()V", "getTestOnly_AllDependentModules", "assertValid", "", "getCapability", ExifInterface.GPS_DIRECTION_TRUE, "capability", "(Lorg/jetbrains/kotlin/descriptors/ModuleCapability;)Ljava/lang/Object;", "getPackage", "fqName", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "initialize", "providerForModuleContent", "setDependencies", "descriptors", "", "([Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;)V", "friends", "shouldSeeInternalsOf", "targetModule", "toString"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleDescriptorImpl extends DeclarationDescriptorImpl implements ModuleDescriptor {
    private final KotlinBuiltIns builtIns;
    private final Map<ModuleCapability<?>, Object> capabilities;
    private ModuleDependencies dependencies;
    private boolean isValid;
    private PackageFragmentProvider packageFragmentProviderForModuleContent;

    /* JADX INFO: renamed from: packageFragmentProviderForWholeModuleWithDependencies$delegate, reason: from kotlin metadata */
    private final Lazy packageFragmentProviderForWholeModuleWithDependencies;
    private final PackageViewDescriptorFactory packageViewDescriptorFactory;
    private final MemoizedFunctionToNotNull<FqName, PackageViewDescriptor> packages;
    private final TargetPlatform platform;
    private final Name stableName;
    private final StorageManager storageManager;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ModuleDescriptorImpl(Name moduleName, StorageManager storageManager, KotlinBuiltIns builtIns) {
        this(moduleName, storageManager, builtIns, null, null, null, 56, null);
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ModuleDescriptorImpl(Name moduleName, StorageManager storageManager, KotlinBuiltIns builtIns, TargetPlatform targetPlatform) {
        this(moduleName, storageManager, builtIns, targetPlatform, null, null, 48, null);
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ModuleDescriptorImpl(Name moduleName, StorageManager storageManager, KotlinBuiltIns builtIns, TargetPlatform targetPlatform, Map<ModuleCapability<?>, ? extends Object> capabilities) {
        this(moduleName, storageManager, builtIns, targetPlatform, capabilities, null, 32, null);
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(capabilities, "capabilities");
    }

    @Deprecated(message = "This method is not going to be supported. Please do not use it")
    public static /* synthetic */ void getTestOnly_AllDependentModules$annotations() {
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
        return (R) ModuleDescriptor.DefaultImpls.accept(this, declarationDescriptorVisitor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getContainingDeclaration() {
        return ModuleDescriptor.DefaultImpls.getContainingDeclaration(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public TargetPlatform getPlatform() {
        return this.platform;
    }

    public /* synthetic */ ModuleDescriptorImpl(Name name2, StorageManager storageManager, KotlinBuiltIns kotlinBuiltIns, TargetPlatform targetPlatform, Map map, Name name3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(name2, storageManager, kotlinBuiltIns, (i & 8) != 0 ? null : targetPlatform, (i & 16) != 0 ? MapsKt.emptyMap() : map, (i & 32) != 0 ? null : name3);
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public Name getStableName() {
        return this.stableName;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModuleDescriptorImpl(Name moduleName, StorageManager storageManager, KotlinBuiltIns builtIns, TargetPlatform targetPlatform, Map<ModuleCapability<?>, ? extends Object> capabilities, Name name2) {
        super(Annotations.INSTANCE.getEMPTY(), moduleName);
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(capabilities, "capabilities");
        this.storageManager = storageManager;
        this.builtIns = builtIns;
        this.platform = targetPlatform;
        this.stableName = name2;
        if (!moduleName.isSpecial()) {
            throw new IllegalArgumentException("Module name must be special: " + moduleName);
        }
        this.capabilities = capabilities;
        PackageViewDescriptorFactory.Default r2 = (PackageViewDescriptorFactory) getCapability(PackageViewDescriptorFactory.INSTANCE.getCAPABILITY());
        this.packageViewDescriptorFactory = r2 == null ? PackageViewDescriptorFactory.Default.INSTANCE : r2;
        this.isValid = true;
        this.packages = storageManager.createMemoizedFunction(new Function1<FqName, PackageViewDescriptor>() { // from class: org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl$packages$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PackageViewDescriptor invoke(FqName fqName) {
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                PackageViewDescriptorFactory packageViewDescriptorFactory = this.this$0.packageViewDescriptorFactory;
                ModuleDescriptorImpl moduleDescriptorImpl = this.this$0;
                return packageViewDescriptorFactory.compute(moduleDescriptorImpl, fqName, moduleDescriptorImpl.storageManager);
            }
        });
        this.packageFragmentProviderForWholeModuleWithDependencies = LazyKt.lazy(new Function0<CompositePackageFragmentProvider>() { // from class: org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl$packageFragmentProviderForWholeModuleWithDependencies$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final CompositePackageFragmentProvider invoke() {
                ModuleDependencies moduleDependencies = this.this$0.dependencies;
                ModuleDescriptorImpl moduleDescriptorImpl = this.this$0;
                if (moduleDependencies != null) {
                    List<ModuleDescriptorImpl> allDependencies = moduleDependencies.getAllDependencies();
                    this.this$0.mo7478assertValid();
                    allDependencies.contains(this.this$0);
                    List<ModuleDescriptorImpl> list = allDependencies;
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        ((ModuleDescriptorImpl) it.next()).isInitialized();
                    }
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    Iterator<T> it2 = list.iterator();
                    while (it2.hasNext()) {
                        PackageFragmentProvider packageFragmentProvider = ((ModuleDescriptorImpl) it2.next()).packageFragmentProviderForModuleContent;
                        Intrinsics.checkNotNull(packageFragmentProvider);
                        arrayList.add(packageFragmentProvider);
                    }
                    return new CompositePackageFragmentProvider(arrayList, "CompositeProvider@ModuleDescriptor for " + this.this$0.getName());
                }
                throw new AssertionError("Dependencies of module " + moduleDescriptorImpl.getId() + " were not set before querying module content");
            }
        });
    }

    public final PackageFragmentProvider getPackageFragmentProviderForModuleContentWithoutDependencies() {
        PackageFragmentProvider packageFragmentProvider = this.packageFragmentProviderForModuleContent;
        if (packageFragmentProvider != null) {
            return packageFragmentProvider;
        }
        throw new IllegalStateException("Module " + getId() + " was not initialized by the time it's content without dependencies was queried");
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    /* JADX INFO: renamed from: isValid, reason: from getter */
    public boolean getIsValid() {
        return this.isValid;
    }

    public void setValid(boolean z) {
        this.isValid = z;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    /* JADX INFO: renamed from: assertValid */
    public void mo7478assertValid() {
        if (getIsValid()) {
            return;
        }
        InvalidModuleExceptionKt.moduleInvalidated(this);
    }

    public final List<ModuleDescriptorImpl> getTestOnly_AllDependentModules() {
        ModuleDependencies moduleDependencies = this.dependencies;
        Intrinsics.checkNotNull(moduleDependencies);
        return moduleDependencies.getAllDependencies();
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public List<ModuleDescriptor> getAllDependencyModules() {
        ModuleDependencies moduleDependencies = this.dependencies;
        if (moduleDependencies == null) {
            throw new AssertionError("Dependencies of module " + getId() + " were not set");
        }
        List<ModuleDescriptorImpl> allDependencies = moduleDependencies.getAllDependencies();
        ArrayList arrayList = new ArrayList();
        for (Object obj : allDependencies) {
            if (!Intrinsics.areEqual((ModuleDescriptorImpl) obj, this)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public List<ModuleDescriptor> getExpectedByModules() {
        ModuleDependencies moduleDependencies = this.dependencies;
        if (moduleDependencies != null) {
            return moduleDependencies.getDirectExpectedByDependencies();
        }
        throw new AssertionError("Dependencies of module " + getId() + " were not set");
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public Set<ModuleDescriptor> getAllExpectedByModules() {
        ModuleDependencies moduleDependencies = this.dependencies;
        if (moduleDependencies != null) {
            return moduleDependencies.getAllExpectedByDependencies();
        }
        throw new AssertionError("Dependencies of module " + getId() + " were not set");
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public PackageViewDescriptor getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        mo7478assertValid();
        return this.packages.invoke(fqName);
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        mo7478assertValid();
        return getPackageFragmentProvider().getSubPackagesOf(fqName, nameFilter);
    }

    private final CompositePackageFragmentProvider getPackageFragmentProviderForWholeModuleWithDependencies() {
        return (CompositePackageFragmentProvider) this.packageFragmentProviderForWholeModuleWithDependencies.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isInitialized() {
        return this.packageFragmentProviderForModuleContent != null;
    }

    public final void setDependencies(ModuleDependencies dependencies) {
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        this.dependencies = dependencies;
    }

    public final void setDependencies(ModuleDescriptorImpl... descriptors) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        setDependencies(ArraysKt.toList(descriptors));
    }

    public final void setDependencies(List<ModuleDescriptorImpl> descriptors) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        setDependencies(descriptors, SetsKt.emptySet());
    }

    public final void setDependencies(List<ModuleDescriptorImpl> descriptors, Set<ModuleDescriptorImpl> friends) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        Intrinsics.checkNotNullParameter(friends, "friends");
        setDependencies(new ModuleDependenciesImpl(descriptors, friends, CollectionsKt.emptyList(), SetsKt.emptySet()));
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public boolean shouldSeeInternalsOf(ModuleDescriptor targetModule) {
        Intrinsics.checkNotNullParameter(targetModule, "targetModule");
        if (Intrinsics.areEqual(this, targetModule)) {
            return true;
        }
        ModuleDependencies moduleDependencies = this.dependencies;
        Intrinsics.checkNotNull(moduleDependencies);
        return CollectionsKt.contains(moduleDependencies.getModulesWhoseInternalsAreVisible(), targetModule) || getExpectedByModules().contains(targetModule) || targetModule.getExpectedByModules().contains(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getId() {
        String string = getName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final void initialize(PackageFragmentProvider providerForModuleContent) {
        Intrinsics.checkNotNullParameter(providerForModuleContent, "providerForModuleContent");
        isInitialized();
        this.packageFragmentProviderForModuleContent = providerForModuleContent;
    }

    public final PackageFragmentProvider getPackageFragmentProvider() {
        mo7478assertValid();
        return getPackageFragmentProviderForWholeModuleWithDependencies();
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public <T> T getCapability(ModuleCapability<T> capability) {
        Intrinsics.checkNotNullParameter(capability, "capability");
        T t = (T) this.capabilities.get(capability);
        if (t == null) {
            return null;
        }
        return t;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl
    public String toString() {
        Class<?> cls;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        if (!getIsValid()) {
            sb.append(" !isValid");
        }
        sb.append(" packageFragmentProvider: ");
        PackageFragmentProvider packageFragmentProvider = this.packageFragmentProviderForModuleContent;
        sb.append((packageFragmentProvider == null || (cls = packageFragmentProvider.getClass()) == null) ? null : cls.getSimpleName());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
