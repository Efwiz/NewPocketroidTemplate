package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.List;
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
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.Printer;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: StaticScopeForKotlinEnum.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J*\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\n2\u0006\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00070\u001fH\u0016J&\u0010 \u001a\u0010\u0012\f\u0012\n \"*\u0004\u0018\u00010\u000b0\u000b0!2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00110$2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR!\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000f\u001a\u0004\b\u0012\u0010\r¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/StaticScopeForKotlinEnum;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "containingClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "enumEntriesCanBeUsed", "", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Z)V", "functions", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getFunctions", "()Ljava/util/List;", "functions$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "properties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getProperties", "properties$delegate", "getContributedClassifier", "", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "getContributedFunctions", "Lorg/jetbrains/kotlin/utils/SmartList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getContributedVariables", "", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StaticScopeForKotlinEnum extends MemberScopeImpl {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(StaticScopeForKotlinEnum.class), "functions", "getFunctions()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(StaticScopeForKotlinEnum.class), "properties", "getProperties()Ljava/util/List;"))};
    private final ClassDescriptor containingClass;
    private final boolean enumEntriesCanBeUsed;

    /* JADX INFO: renamed from: functions$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue functions;

    /* JADX INFO: renamed from: properties$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue properties;

    public Void getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation lookupLocation) {
        return (ClassifierDescriptor) getContributedClassifier(name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public /* bridge */ /* synthetic */ Collection getContributedDescriptors(DescriptorKindFilter descriptorKindFilter, Function1 function1) {
        return getContributedDescriptors(descriptorKindFilter, (Function1<? super Name, Boolean>) function1);
    }

    public StaticScopeForKotlinEnum(StorageManager storageManager, ClassDescriptor containingClass, boolean z) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        this.containingClass = containingClass;
        this.enumEntriesCanBeUsed = z;
        containingClass.getKind();
        ClassKind classKind = ClassKind.ENUM_CLASS;
        this.functions = storageManager.createLazyValue(new Function0<List<? extends SimpleFunctionDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.scopes.StaticScopeForKotlinEnum$functions$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends SimpleFunctionDescriptor> invoke() {
                return CollectionsKt.listOf((Object[]) new SimpleFunctionDescriptor[]{DescriptorFactory.createEnumValueOfMethod(this.this$0.containingClass), DescriptorFactory.createEnumValuesMethod(this.this$0.containingClass)});
            }
        });
        this.properties = storageManager.createLazyValue(new Function0<List<? extends PropertyDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.scopes.StaticScopeForKotlinEnum$properties$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends PropertyDescriptor> invoke() {
                if (this.this$0.enumEntriesCanBeUsed) {
                    return CollectionsKt.listOfNotNull(DescriptorFactory.createEnumEntriesProperty(this.this$0.containingClass));
                }
                return CollectionsKt.emptyList();
            }
        });
    }

    private final List<SimpleFunctionDescriptor> getFunctions() {
        return (List) StorageKt.getValue(this.functions, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final List<PropertyDescriptor> getProperties() {
        return (List) StorageKt.getValue(this.properties, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public List<CallableMemberDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return CollectionsKt.plus((Collection) getFunctions(), (Iterable) getProperties());
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public SmartList<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        List<SimpleFunctionDescriptor> functions = getFunctions();
        SmartList smartList = new SmartList();
        for (Object obj : functions) {
            if (Intrinsics.areEqual(((SimpleFunctionDescriptor) obj).getShortName(), name2)) {
                smartList.add(obj);
            }
        }
        return smartList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        List<PropertyDescriptor> properties = getProperties();
        SmartList smartList = new SmartList();
        for (Object obj : properties) {
            if (Intrinsics.areEqual(((PropertyDescriptor) obj).getShortName(), name2)) {
                smartList.add(obj);
            }
        }
        return smartList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println("Static scope for " + this.containingClass);
    }
}
