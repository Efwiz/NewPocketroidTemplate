package org.jetbrains.kotlin.builtins.functions;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: FunctionInterfaceFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\u000eH\u0016J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J*\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001a0\u0019H\u0016J\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00142\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00142\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0\u000eH\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0\u000eH\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionInterfaceMemberScope;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "classDescriptorFactory", "Lorg/jetbrains/kotlin/descriptors/deserialization/ClassDescriptorFactory;", "packageName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/descriptors/deserialization/ClassDescriptorFactory;Lorg/jetbrains/kotlin/name/FqName;)V", "classifiers", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getPackageName", "()Lorg/jetbrains/kotlin/name/FqName;", "getClassifierNames", "", "getContributedClassifier", "name", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getFunctionNames", "getVariableNames", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionInterfaceMemberScope extends MemberScopeImpl {
    private final ClassDescriptorFactory classDescriptorFactory;
    private final Map<Name, ClassifierDescriptor> classifiers;
    private final FqName packageName;

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        return null;
    }

    public final FqName getPackageName() {
        return this.packageName;
    }

    public FunctionInterfaceMemberScope(ClassDescriptorFactory classDescriptorFactory, FqName packageName) {
        Intrinsics.checkNotNullParameter(classDescriptorFactory, "classDescriptorFactory");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        this.classDescriptorFactory = classDescriptorFactory;
        this.packageName = packageName;
        this.classifiers = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<ClassDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.classDescriptorFactory.getAllContributedClassesIfPossible(this.packageName);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        throw new NotImplementedError(null, 1, null);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (!this.classDescriptorFactory.shouldCreateClass(this.packageName, name2)) {
            return null;
        }
        Map<Name, ClassifierDescriptor> map = this.classifiers;
        ClassDescriptor classDescriptor = map.get(name2);
        if (classDescriptor == null) {
            ClassDescriptorFactory classDescriptorFactory = this.classDescriptorFactory;
            ClassId classId = ClassId.topLevel(this.packageName.child(name2));
            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
            ClassDescriptor classDescriptorCreateClass = classDescriptorFactory.createClass(classId);
            Intrinsics.checkNotNull(classDescriptorCreateClass);
            classDescriptor = classDescriptorCreateClass;
            map.put(name2, classDescriptor);
        }
        return classDescriptor;
    }
}
