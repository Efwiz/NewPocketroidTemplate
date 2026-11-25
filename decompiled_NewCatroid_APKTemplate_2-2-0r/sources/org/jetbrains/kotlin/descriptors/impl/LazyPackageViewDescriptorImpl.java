package org.jetbrains.kotlin.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderKt;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.scopes.ChainedMemberScope;
import org.jetbrains.kotlin.resolve.scopes.LazyScopeAdapter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: LazyPackageViewDescriptorImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ5\u0010\u001e\u001a\u0002H\u001f\"\u0004\b\u0000\u0010\u001f\"\u0004\b\u0001\u0010 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H\u001f\u0012\u0004\u0012\u0002H 0\"2\u0006\u0010#\u001a\u0002H H\u0016¢\u0006\u0002\u0010$J\u0013\u0010%\u001a\u00020\u000b2\b\u0010&\u001a\u0004\u0018\u00010'H\u0096\u0002J\n\u0010(\u001a\u0004\u0018\u00010\u0002H\u0016J\b\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020\u000bH\u0016R\u001b\u0010\n\u001a\u00020\u000b8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u000f\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0018\u001a\u00020\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/LazyPackageViewDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/impl/DeclarationDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "module", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "empty", "", "getEmpty", "()Z", "empty$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "fragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getFragments", "()Ljava/util/List;", "fragments$delegate", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", Namer.EQUALS_METHOD_NAME, "other", "", "getContainingDeclaration", "hashCode", "", "isEmpty", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LazyPackageViewDescriptorImpl extends DeclarationDescriptorImpl implements PackageViewDescriptor {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyPackageViewDescriptorImpl.class), "fragments", "getFragments()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyPackageViewDescriptorImpl.class), "empty", "getEmpty()Z"))};

    /* JADX INFO: renamed from: empty$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue empty;
    private final FqName fqName;

    /* JADX INFO: renamed from: fragments$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue fragments;
    private final MemberScope memberScope;
    private final ModuleDescriptorImpl module;

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    /* JADX INFO: renamed from: getModule, reason: from getter */
    public ModuleDescriptorImpl getModuleDescriptor() {
        return this.module;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public FqName getFqName() {
        return this.fqName;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyPackageViewDescriptorImpl(ModuleDescriptorImpl module, FqName fqName, StorageManager storageManager) {
        super(Annotations.INSTANCE.getEMPTY(), fqName.shortNameOrSpecial());
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.module = module;
        this.fqName = fqName;
        this.fragments = storageManager.createLazyValue(new Function0<List<? extends PackageFragmentDescriptor>>() { // from class: org.jetbrains.kotlin.descriptors.impl.LazyPackageViewDescriptorImpl$fragments$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends PackageFragmentDescriptor> invoke() {
                return PackageFragmentProviderKt.packageFragments(this.this$0.getModuleDescriptor().getPackageFragmentProvider(), this.this$0.getFqName());
            }
        });
        this.empty = storageManager.createLazyValue(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.descriptors.impl.LazyPackageViewDescriptorImpl$empty$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(PackageFragmentProviderKt.isEmpty(this.this$0.getModuleDescriptor().getPackageFragmentProvider(), this.this$0.getFqName()));
            }
        });
        this.memberScope = new LazyScopeAdapter(storageManager, new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.descriptors.impl.LazyPackageViewDescriptorImpl$memberScope$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MemberScope invoke() {
                if (this.this$0.isEmpty()) {
                    return MemberScope.Empty.INSTANCE;
                }
                List<PackageFragmentDescriptor> fragments = this.this$0.getFragments();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(fragments, 10));
                Iterator<T> it = fragments.iterator();
                while (it.hasNext()) {
                    arrayList.add(((PackageFragmentDescriptor) it.next()).getScope());
                }
                return ChainedMemberScope.INSTANCE.create("package view scope for " + this.this$0.getFqName() + " in " + this.this$0.getModuleDescriptor().getShortName(), CollectionsKt.plus((Collection<? extends SubpackagesScope>) arrayList, new SubpackagesScope(this.this$0.getModuleDescriptor(), this.this$0.getFqName())));
            }
        });
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public List<PackageFragmentDescriptor> getFragments() {
        return (List) StorageKt.getValue(this.fragments, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    protected final boolean getEmpty() {
        return ((Boolean) StorageKt.getValue(this.empty, this, (KProperty<?>) $$delegatedProperties[1])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public boolean isEmpty() {
        return getEmpty();
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public MemberScope getMemberScope() {
        return this.memberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public PackageViewDescriptor getContainingDeclaration() {
        if (getFqName().isRoot()) {
            return null;
        }
        ModuleDescriptorImpl module = getModuleDescriptor();
        FqName fqNameParent = getFqName().parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        return module.getPackage(fqNameParent);
    }

    public boolean equals(Object other) {
        PackageViewDescriptor packageViewDescriptor = other instanceof PackageViewDescriptor ? (PackageViewDescriptor) other : null;
        return packageViewDescriptor != null && Intrinsics.areEqual(getFqName(), packageViewDescriptor.getFqName()) && Intrinsics.areEqual(getModuleDescriptor(), packageViewDescriptor.getModuleDescriptor());
    }

    public int hashCode() {
        return (getModuleDescriptor().hashCode() * 31) + getFqName().hashCode();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitPackageViewDescriptor(this, data);
    }
}
