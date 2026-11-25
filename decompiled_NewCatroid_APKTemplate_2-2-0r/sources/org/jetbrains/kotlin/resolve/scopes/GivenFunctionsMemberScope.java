package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.NonReportingOverrideStrategy;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.utils.Printer;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: GivenFunctionsMemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\bH$J\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\bH\u0002J*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u0019H\u0016J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00152\u0006\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u001e\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00152\u0006\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016R!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/GivenFunctionsMemberScope;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "containingClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "allDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getAllDescriptors", "()Ljava/util/List;", "allDescriptors$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "getContainingClass", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "computeDeclaredFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "createFakeOverrides", "functionsFromCurrent", "getContributedDescriptors", "", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class GivenFunctionsMemberScope extends MemberScopeImpl {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(GivenFunctionsMemberScope.class), "allDescriptors", "getAllDescriptors()Ljava/util/List;"))};

    /* JADX INFO: renamed from: allDescriptors$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue allDescriptors;
    private final ClassDescriptor containingClass;

    protected abstract List<FunctionDescriptor> computeDeclaredFunctions();

    protected final ClassDescriptor getContainingClass() {
        return this.containingClass;
    }

    public GivenFunctionsMemberScope(StorageManager storageManager, ClassDescriptor containingClass) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        this.containingClass = containingClass;
        this.allDescriptors = storageManager.createLazyValue(new Function0<List<? extends DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.scopes.GivenFunctionsMemberScope$allDescriptors$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends DeclarationDescriptor> invoke() {
                List<FunctionDescriptor> listComputeDeclaredFunctions = this.this$0.computeDeclaredFunctions();
                return CollectionsKt.plus((Collection) listComputeDeclaredFunctions, (Iterable) this.this$0.createFakeOverrides(listComputeDeclaredFunctions));
            }
        });
    }

    private final List<DeclarationDescriptor> getAllDescriptors() {
        return (List) StorageKt.getValue(this.allDescriptors, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return !kindFilter.acceptsKinds(DescriptorKindFilter.CALLABLES.getKindMask()) ? CollectionsKt.emptyList() : getAllDescriptors();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        SmartList smartListEmptyList;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        List<DeclarationDescriptor> allDescriptors = getAllDescriptors();
        if (allDescriptors.isEmpty()) {
            smartListEmptyList = CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj : allDescriptors) {
                if ((obj instanceof SimpleFunctionDescriptor) && Intrinsics.areEqual(((SimpleFunctionDescriptor) obj).getShortName(), name2)) {
                    smartList.add(obj);
                }
            }
            smartListEmptyList = smartList;
        }
        return smartListEmptyList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        SmartList smartListEmptyList;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        List<DeclarationDescriptor> allDescriptors = getAllDescriptors();
        if (allDescriptors.isEmpty()) {
            smartListEmptyList = CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj : allDescriptors) {
                if ((obj instanceof PropertyDescriptor) && Intrinsics.areEqual(((PropertyDescriptor) obj).getShortName(), name2)) {
                    smartList.add(obj);
                }
            }
            smartListEmptyList = smartList;
        }
        return smartListEmptyList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<DeclarationDescriptor> createFakeOverrides(List<? extends FunctionDescriptor> functionsFromCurrent) {
        ArrayList arrayListEmptyList;
        final ArrayList arrayList = new ArrayList(3);
        Collection<KotlinType> collectionMo7438getSupertypes = this.containingClass.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it = collectionMo7438getSupertypes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList2, ResolutionScope.DefaultImpls.getContributedDescriptors$default(((KotlinType) it.next()).getMemberScope(), null, null, 3, null));
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            if (obj instanceof CallableMemberDescriptor) {
                arrayList3.add(obj);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : arrayList3) {
            Name name2 = ((CallableMemberDescriptor) obj2).getShortName();
            Object obj3 = linkedHashMap.get(name2);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(name2, obj3);
            }
            ((List) obj3).add(obj2);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            Name name3 = (Name) entry.getKey();
            List list = (List) entry.getValue();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (Object obj4 : list) {
                Boolean boolValueOf = Boolean.valueOf(((CallableMemberDescriptor) obj4) instanceof FunctionDescriptor);
                Object obj5 = linkedHashMap2.get(boolValueOf);
                if (obj5 == null) {
                    obj5 = (List) new ArrayList();
                    linkedHashMap2.put(boolValueOf, obj5);
                }
                ((List) obj5).add(obj4);
            }
            for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
                boolean zBooleanValue = ((Boolean) entry2.getKey()).booleanValue();
                List list2 = (List) entry2.getValue();
                OverridingUtil overridingUtil = OverridingUtil.DEFAULT;
                List list3 = list2;
                if (!zBooleanValue) {
                    arrayListEmptyList = CollectionsKt.emptyList();
                } else {
                    ArrayList arrayList4 = new ArrayList();
                    for (Object obj6 : functionsFromCurrent) {
                        if (Intrinsics.areEqual(((FunctionDescriptor) obj6).getShortName(), name3)) {
                            arrayList4.add(obj6);
                        }
                    }
                    arrayListEmptyList = arrayList4;
                }
                overridingUtil.generateOverridesInFunctionGroup(name3, list3, arrayListEmptyList, this.containingClass, new NonReportingOverrideStrategy() { // from class: org.jetbrains.kotlin.resolve.scopes.GivenFunctionsMemberScope.createFakeOverrides.4
                    @Override // org.jetbrains.kotlin.resolve.OverridingStrategy
                    public void addFakeOverride(CallableMemberDescriptor fakeOverride) {
                        Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
                        OverridingUtil.resolveUnknownVisibilityForMember(fakeOverride, null);
                        arrayList.add(fakeOverride);
                    }

                    @Override // org.jetbrains.kotlin.resolve.NonReportingOverrideStrategy
                    protected void conflict(CallableMemberDescriptor fromSuper, CallableMemberDescriptor fromCurrent) {
                        Intrinsics.checkNotNullParameter(fromSuper, "fromSuper");
                        Intrinsics.checkNotNullParameter(fromCurrent, "fromCurrent");
                        throw new IllegalStateException(("Conflict in scope of " + this.getContainingClass() + ": " + fromSuper + " vs " + fromCurrent).toString());
                    }
                });
            }
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println("Scope of class: " + this.containingClass);
    }
}
