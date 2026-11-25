package org.jetbrains.kotlin.resolve.jvm.multiplatform;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: OptionalAnnotationPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000=\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000*\u0001\u000f\b\u0002\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\u000b0\n¢\u0006\u0002\u0010\rJ\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0010\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/multiplatform/PackageFragmentForOptionalAnnotations;", "Lorg/jetbrains/kotlin/descriptors/impl/PackageFragmentDescriptorImpl;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "classNames", "", "Lorg/jetbrains/kotlin/name/Name;", "classDescriptors", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/name/FqName;Ljava/util/Set;Lorg/jetbrains/kotlin/storage/NotNullLazyValue;)V", "scope", "org/jetbrains/kotlin/resolve/jvm/multiplatform/PackageFragmentForOptionalAnnotations$scope$1", "Lorg/jetbrains/kotlin/resolve/jvm/multiplatform/PackageFragmentForOptionalAnnotations$scope$1;", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PackageFragmentForOptionalAnnotations extends PackageFragmentDescriptorImpl {
    private final PackageFragmentForOptionalAnnotations$scope$1 scope;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v1, types: [org.jetbrains.kotlin.resolve.jvm.multiplatform.PackageFragmentForOptionalAnnotations$scope$1] */
    public PackageFragmentForOptionalAnnotations(ModuleDescriptor module, FqName fqName, final Set<Name> classNames, final NotNullLazyValue<? extends Map<Name, ? extends ClassDescriptor>> classDescriptors) {
        super(module, fqName);
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(classNames, "classNames");
        Intrinsics.checkNotNullParameter(classDescriptors, "classDescriptors");
        this.scope = new MemberScopeImpl() { // from class: org.jetbrains.kotlin.resolve.jvm.multiplatform.PackageFragmentForOptionalAnnotations$scope$1
            @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            /* JADX INFO: renamed from: getContributedClassifier */
            public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                return classDescriptors.invoke().get(name2);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
                Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
                Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
                return kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getCLASSIFIERS_MASK()) ? classDescriptors.invoke().values() : CollectionsKt.emptyList();
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
            public Set<Name> getClassifierNames() {
                return classNames;
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
            /* JADX INFO: renamed from: printScopeStructure */
            public void mo6105printScopeStructure(Printer p) {
                Intrinsics.checkNotNullParameter(p, "p");
                p.print("PackageFragmentForOptionalAnnotations{" + CollectionsKt.joinToString$default(classNames, null, null, null, 0, null, PackageFragmentForOptionalAnnotations$scope$1$printScopeStructure$1.INSTANCE, 31, null) + '}');
            }
        };
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    /* JADX INFO: renamed from: getMemberScope */
    public MemberScope getScope() {
        return this.scope;
    }
}
