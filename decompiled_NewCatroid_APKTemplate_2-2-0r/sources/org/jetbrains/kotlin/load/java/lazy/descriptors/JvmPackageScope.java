package org.jetbrains.kotlin.load.java.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.UtilsKt;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeKt;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: JvmPackageScope.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015H\u0016J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J*\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020#0\"H\u0016J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u001d2\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u001d2\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u000e\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00160)H\u0016J\u000e\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00160)H\u0016J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J\u0018\u0010/\u001a\u00020,2\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u00100\u001a\u000201H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR)\u0010\r\u001a\u0010\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u00010\u00010\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/JvmPackageScope;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "jPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "packageFragment", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageFragment;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageFragment;)V", "javaScope", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope;", "getJavaScope$descriptors_jvm", "()Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope;", "kotlinScopes", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getKotlinScopes", "()[Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "kotlinScopes$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "getClassifierNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getFunctionNames", "", "getVariableNames", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "recordLookup", "toString", "", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPackageScope implements MemberScope {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmPackageScope.class), "kotlinScopes", "getKotlinScopes()[Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;"))};
    private final LazyJavaResolverContext c;
    private final LazyJavaPackageScope javaScope;

    /* JADX INFO: renamed from: kotlinScopes$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue kotlinScopes;
    private final LazyJavaPackageFragment packageFragment;

    public JvmPackageScope(LazyJavaResolverContext c, JavaPackage jPackage, LazyJavaPackageFragment packageFragment) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(jPackage, "jPackage");
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        this.c = c;
        this.packageFragment = packageFragment;
        this.javaScope = new LazyJavaPackageScope(c, jPackage, packageFragment);
        this.kotlinScopes = c.getStorageManager().createLazyValue(new Function0<MemberScope[]>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.JvmPackageScope$kotlinScopes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MemberScope[] invoke() {
                Collection<KotlinJvmBinaryClass> collectionValues = this.this$0.packageFragment.getBinaryClasses$descriptors_jvm().values();
                JvmPackageScope jvmPackageScope = this.this$0;
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = collectionValues.iterator();
                while (it.hasNext()) {
                    MemberScope memberScopeCreateKotlinPackagePartScope = jvmPackageScope.c.getComponents().getDeserializedDescriptorResolver().createKotlinPackagePartScope(jvmPackageScope.packageFragment, (KotlinJvmBinaryClass) it.next());
                    if (memberScopeCreateKotlinPackagePartScope != null) {
                        arrayList.add(memberScopeCreateKotlinPackagePartScope);
                    }
                }
                return (MemberScope[]) ScopeUtilsKt.listOfNonEmptyScopes(arrayList).toArray(new MemberScope[0]);
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        return MemberScope.DefaultImpls.definitelyDoesNotContainName(this, name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
        return MemberScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
    }

    /* JADX INFO: renamed from: getJavaScope$descriptors_jvm, reason: from getter */
    public final LazyJavaPackageScope getJavaScope() {
        return this.javaScope;
    }

    private final MemberScope[] getKotlinScopes() {
        return (MemberScope[]) StorageKt.getValue(this.kotlinScopes, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo7479recordLookup(name2, location);
        ClassDescriptor classDescriptorMo7465getContributedClassifier = this.javaScope.mo7465getContributedClassifier(name2, location);
        if (classDescriptorMo7465getContributedClassifier != null) {
            return classDescriptorMo7465getContributedClassifier;
        }
        ClassifierDescriptor classifierDescriptor = null;
        for (MemberScope memberScope : getKotlinScopes()) {
            ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(name2, location);
            if (contributedClassifier != null) {
                if (!(contributedClassifier instanceof ClassifierDescriptorWithTypeParameters) || !((ClassifierDescriptorWithTypeParameters) contributedClassifier).get$isExpect()) {
                    return contributedClassifier;
                }
                if (classifierDescriptor == null) {
                    classifierDescriptor = contributedClassifier;
                }
            }
        }
        return classifierDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo7479recordLookup(name2, location);
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        MemberScope[] kotlinScopes = getKotlinScopes();
        Collection<? extends PropertyDescriptor> contributedVariables = lazyJavaPackageScope.getContributedVariables(name2, location);
        int length = kotlinScopes.length;
        int i = 0;
        Collection collection = contributedVariables;
        while (i < length) {
            Collection collectionConcat = ScopeUtilsKt.concat(collection, kotlinScopes[i].getContributedVariables(name2, location));
            i++;
            collection = collectionConcat;
        }
        return collection == null ? SetsKt.emptySet() : collection;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo7479recordLookup(name2, location);
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        MemberScope[] kotlinScopes = getKotlinScopes();
        Collection<? extends SimpleFunctionDescriptor> contributedFunctions = lazyJavaPackageScope.getContributedFunctions(name2, location);
        int length = kotlinScopes.length;
        int i = 0;
        Collection collection = contributedFunctions;
        while (i < length) {
            Collection collectionConcat = ScopeUtilsKt.concat(collection, kotlinScopes[i].getContributedFunctions(name2, location));
            i++;
            collection = collectionConcat;
        }
        return collection == null ? SetsKt.emptySet() : collection;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        MemberScope[] kotlinScopes = getKotlinScopes();
        Collection<DeclarationDescriptor> contributedDescriptors = lazyJavaPackageScope.getContributedDescriptors(kindFilter, nameFilter);
        for (MemberScope memberScope : kotlinScopes) {
            contributedDescriptors = ScopeUtilsKt.concat(contributedDescriptors, memberScope.getContributedDescriptors(kindFilter, nameFilter));
        }
        return contributedDescriptors == null ? SetsKt.emptySet() : contributedDescriptors;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        MemberScope[] kotlinScopes = getKotlinScopes();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (MemberScope memberScope : kotlinScopes) {
            CollectionsKt.addAll(linkedHashSet, memberScope.getFunctionNames());
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        linkedHashSet2.addAll(this.javaScope.getFunctionNames());
        return linkedHashSet2;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        MemberScope[] kotlinScopes = getKotlinScopes();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (MemberScope memberScope : kotlinScopes) {
            CollectionsKt.addAll(linkedHashSet, memberScope.getVariableNames());
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        linkedHashSet2.addAll(this.javaScope.getVariableNames());
        return linkedHashSet2;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        Set<Name> setFlatMapClassifierNamesOrNull = MemberScopeKt.flatMapClassifierNamesOrNull(ArraysKt.asIterable(getKotlinScopes()));
        if (setFlatMapClassifierNamesOrNull == null) {
            return null;
        }
        setFlatMapClassifierNamesOrNull.addAll(this.javaScope.getClassifierNames());
        return setFlatMapClassifierNamesOrNull;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName(), " {");
        p.pushIndent();
        p.println("containingDeclaration: " + this.packageFragment);
        this.javaScope.mo6105printScopeStructure(p);
        for (MemberScope memberScope : getKotlinScopes()) {
            memberScope.mo6105printScopeStructure(p);
        }
        p.popIndent();
        p.println("}");
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        UtilsKt.record(this.c.getComponents().getLookupTracker(), location, this.packageFragment, name2);
    }

    public String toString() {
        return "scope for " + this.packageFragment;
    }
}
