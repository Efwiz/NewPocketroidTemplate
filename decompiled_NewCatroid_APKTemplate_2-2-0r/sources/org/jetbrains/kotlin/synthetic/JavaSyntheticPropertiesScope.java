package org.jetbrains.kotlin.synthetic;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptorKt;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertySetterDescriptorImpl;
import org.jetbrains.kotlin.incremental.UtilsKt;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.PropertiesConventionUtilKt;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScope;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.DescriptorSubstitutor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JavaSyntheticPropertiesScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002:;B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJH\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00122\u000e\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000f2\u000e\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J,\u0010\u001c\u001a\u00020\u00102\b\u0010\u001d\u001a\u0004\u0018\u00010\u00132\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001f2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u000fH\u0002J$\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00130\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\"2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J,\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00130\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\"2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u001c\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\"H\u0016J,\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\"2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\"\u0010'\u001a\u0004\u0018\u00010\u00132\u0006\u0010(\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010)\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020&H\u0002J\u0018\u0010*\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020&2\u0006\u0010+\u001a\u00020&H\u0002J\u001a\u0010,\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u000eH\u0002J\u0018\u0010.\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u000eH\u0002J\u0018\u0010/\u001a\u00020\u00102\u0006\u0010-\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000fH\u0002J&\u00100\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012*\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00122\b\u00101\u001a\u0004\u0018\u00010\u0013H\u0002J(\u00102\u001a\u000203*\b\u0012\u0004\u0012\u00020\u0013042\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00160\u0019H\u0002J\"\u00105\u001a\u0004\u0018\u00010&*\u00020&2\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\t07H\u0002J\f\u00108\u001a\u00020\t*\u000209H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u000b\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r\u0012\u0004\u0012\u00020\u00100\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope;", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope$Default;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "typeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "supportJavaRecords", "", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;Z)V", "syntheticPropertyInClass", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope$SyntheticPropertyHolder;", "collectSyntheticPropertiesByName", "Lorg/jetbrains/kotlin/com/intellij/util/SmartList;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "result", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "name", "processedTypes", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "createSyntheticPropertyHolder", "descriptor", "lookedNames", "", "additionalName", "getSyntheticExtensionProperties", "", "receiverTypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "getSyntheticMemberFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getSyntheticPropertyAndRecordLookups", "classifier", "isGoodGetMethod", "isGoodSetMethod", "getMethod", "syntheticPropertyDescriptorForRecordComponent", "ownerClass", "syntheticPropertyHolderForBeanConvention", "syntheticPropertyInClassNotCached", ProductAction.ACTION_ADD, "property", "collectSyntheticProperties", "", "", "findOverridden", "condition", "Lkotlin/Function1;", "isRecord", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "MyPropertyDescriptor", "SyntheticPropertyHolder", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaSyntheticPropertiesScope extends SyntheticScope.Default {
    private final LookupTracker lookupTracker;
    private final boolean supportJavaRecords;
    private final MemoizedFunctionToNotNull<Pair<? extends ClassDescriptor, Name>, SyntheticPropertyHolder> syntheticPropertyInClass;
    private final KotlinTypeRefiner typeRefiner;

    public JavaSyntheticPropertiesScope(StorageManager storageManager, LookupTracker lookupTracker, KotlinTypeRefiner typeRefiner, boolean z) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(typeRefiner, "typeRefiner");
        this.lookupTracker = lookupTracker;
        this.typeRefiner = typeRefiner;
        this.supportJavaRecords = z;
        this.syntheticPropertyInClass = storageManager.createMemoizedFunction(new Function1<Pair<? extends ClassDescriptor, ? extends Name>, SyntheticPropertyHolder>() { // from class: org.jetbrains.kotlin.synthetic.JavaSyntheticPropertiesScope$syntheticPropertyInClass$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ JavaSyntheticPropertiesScope.SyntheticPropertyHolder invoke(Pair<? extends ClassDescriptor, ? extends Name> pair) {
                return invoke2((Pair<? extends ClassDescriptor, Name>) pair);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final JavaSyntheticPropertiesScope.SyntheticPropertyHolder invoke2(Pair<? extends ClassDescriptor, Name> pair) {
                Intrinsics.checkNotNullParameter(pair, "pair");
                return this.this$0.syntheticPropertyInClassNotCached(pair.getFirst(), pair.getSecond());
            }
        });
    }

    private final PropertyDescriptor getSyntheticPropertyAndRecordLookups(ClassDescriptor classifier, Name name2, LookupLocation location) {
        SyntheticPropertyHolder syntheticPropertyHolderInvoke = this.syntheticPropertyInClass.invoke(new Pair(classifier, name2));
        PropertyDescriptor descriptor = syntheticPropertyHolderInvoke.getDescriptor();
        List<Name> listComponent2 = syntheticPropertyHolderInvoke.component2();
        if (!(location instanceof NoLookupLocation)) {
            Iterator<T> it = listComponent2.iterator();
            while (it.hasNext()) {
                UtilsKt.record(this.lookupTracker, location, classifier, (Name) it.next());
            }
        }
        return descriptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SyntheticPropertyHolder syntheticPropertyInClassNotCached(ClassDescriptor ownerClass, Name name2) {
        SyntheticPropertyHolder syntheticPropertyHolderSyntheticPropertyHolderForBeanConvention = syntheticPropertyHolderForBeanConvention(name2, ownerClass);
        return (syntheticPropertyHolderSyntheticPropertyHolderForBeanConvention.getDescriptor() == null && isRecord(ownerClass)) ? createSyntheticPropertyHolder(syntheticPropertyDescriptorForRecordComponent(name2, ownerClass), syntheticPropertyHolderSyntheticPropertyHolderForBeanConvention.getLookedNames(), name2) : syntheticPropertyHolderSyntheticPropertyHolderForBeanConvention;
    }

    static /* synthetic */ SyntheticPropertyHolder createSyntheticPropertyHolder$default(JavaSyntheticPropertiesScope javaSyntheticPropertiesScope, PropertyDescriptor propertyDescriptor, List list, Name name2, int i, Object obj) {
        if ((i & 4) != 0) {
            name2 = null;
        }
        return javaSyntheticPropertiesScope.createSyntheticPropertyHolder(propertyDescriptor, list, name2);
    }

    private final SyntheticPropertyHolder createSyntheticPropertyHolder(PropertyDescriptor descriptor, List<Name> lookedNames, Name additionalName) {
        if (this.lookupTracker == LookupTracker.DO_NOTHING.INSTANCE) {
            return descriptor == null ? SyntheticPropertyHolder.INSTANCE.getEMPTY() : new SyntheticPropertyHolder(descriptor, CollectionsKt.emptyList());
        }
        ArrayList arrayList = new ArrayList(lookedNames.size() + (additionalName != null ? 1 : 0));
        arrayList.addAll(lookedNames);
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, additionalName);
        return new SyntheticPropertyHolder(descriptor, arrayList);
    }

    private final SyntheticPropertyHolder syntheticPropertyHolderForBeanConvention(Name name2, ClassDescriptor ownerClass) {
        List<Name> listPossibleGetMethodNames = PropertiesConventionUtilKt.possibleGetMethodNames(name2);
        if (listPossibleGetMethodNames.isEmpty()) {
            return SyntheticPropertyHolder.INSTANCE.getEMPTY();
        }
        MemberScope refinedUnsubstitutedMemberScopeIfPossible = ModuleAwareClassDescriptorKt.getRefinedUnsubstitutedMemberScopeIfPossible(ownerClass, this.typeRefiner);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listPossibleGetMethodNames.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, refinedUnsubstitutedMemberScopeIfPossible.getContributedFunctions((Name) it.next(), NoLookupLocation.FROM_SYNTHETIC_SCOPE));
        }
        Iterator it2 = arrayList.iterator();
        boolean z = false;
        boolean z2 = false;
        Object obj = null;
        while (true) {
            if (!it2.hasNext()) {
                if (!z2) {
                    break;
                }
                break;
            }
            Object next = it2.next();
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) next;
            if (SyntheticExtensionsUtilsKt.hasJavaOriginInHierarchy(simpleFunctionDescriptor) && isGoodGetMethod(simpleFunctionDescriptor)) {
                if (!z2) {
                    obj = next;
                    z2 = true;
                }
            }
            obj = null;
            break;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = (SimpleFunctionDescriptor) obj;
        if (simpleFunctionDescriptor2 == null) {
            return createSyntheticPropertyHolder$default(this, null, listPossibleGetMethodNames, null, 4, null);
        }
        Name name3 = simpleFunctionDescriptor2.getShortName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        Name methodName = PropertiesConventionUtilKt.setMethodName(name3);
        Iterator<T> it3 = refinedUnsubstitutedMemberScopeIfPossible.getContributedFunctions(methodName, NoLookupLocation.FROM_SYNTHETIC_SCOPE).iterator();
        Object obj2 = null;
        while (true) {
            if (!it3.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            Object next2 = it3.next();
            if (isGoodSetMethod((SimpleFunctionDescriptor) next2, simpleFunctionDescriptor2)) {
                if (!z) {
                    obj2 = next2;
                    z = true;
                }
            }
            obj2 = null;
            break;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor3 = (SimpleFunctionDescriptor) obj2;
        KotlinType returnType = simpleFunctionDescriptor2.getReturnType();
        Intrinsics.checkNotNull(returnType);
        MyPropertyDescriptor.Companion companion = MyPropertyDescriptor.INSTANCE;
        SimpleFunctionDescriptor original = simpleFunctionDescriptor2.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return createSyntheticPropertyHolder(companion.create(ownerClass, original, simpleFunctionDescriptor3 != null ? simpleFunctionDescriptor3.getOriginal() : null, name2, returnType), listPossibleGetMethodNames, methodName);
    }

    private final PropertyDescriptor syntheticPropertyDescriptorForRecordComponent(Name name2, ClassDescriptor ownerClass) {
        if (!this.supportJavaRecords) {
            return null;
        }
        Iterator<T> it = ModuleAwareClassDescriptorKt.getRefinedUnsubstitutedMemberScopeIfPossible(ownerClass, this.typeRefiner).getContributedFunctions(name2, NoLookupLocation.FROM_SYNTHETIC_SCOPE).iterator();
        boolean z = false;
        Object obj = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            Object next = it.next();
            if (isGoodGetMethod((FunctionDescriptor) next)) {
                if (!z) {
                    z = true;
                    obj = next;
                }
            }
            obj = null;
            break;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) obj;
        if (simpleFunctionDescriptor != null && (simpleFunctionDescriptor instanceof JavaMethodDescriptor)) {
            JavaMethodDescriptor javaMethodDescriptor = (JavaMethodDescriptor) simpleFunctionDescriptor;
            if (javaMethodDescriptor.isForRecordComponent()) {
                KotlinType returnType = javaMethodDescriptor.getReturnType();
                Intrinsics.checkNotNull(returnType);
                MyPropertyDescriptor.Companion companion = MyPropertyDescriptor.INSTANCE;
                SimpleFunctionDescriptor original = javaMethodDescriptor.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                return companion.create(ownerClass, original, null, name2, returnType);
            }
        }
        return null;
    }

    private final boolean isGoodGetMethod(FunctionDescriptor descriptor) {
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null || TypeUtilsKt.isUnit(returnType) || !descriptor.getValueParameters().isEmpty() || !descriptor.getTypeParameters().isEmpty()) {
            return false;
        }
        DescriptorVisibility visibility = descriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        if (SyntheticExtensionsUtilsKt.isVisibleOutside(visibility)) {
            return (descriptor.isHiddenForResolutionEverywhereBesideSupercalls() && Intrinsics.areEqual(descriptor.getShortName().asString(), "isEmpty")) ? false : true;
        }
        return false;
    }

    private final boolean isGoodSetMethod(FunctionDescriptor descriptor, final FunctionDescriptor getMethod) {
        KotlinType returnType = getMethod.getReturnType();
        if (returnType == null) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.singleOrNull((List) valueParameters);
        if (valueParameterDescriptor == null) {
            return false;
        }
        if (!TypeUtils.equalTypes(valueParameterDescriptor.getType(), returnType)) {
            KotlinType type = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (!TypeUtilsKt.isSubtypeOf(returnType, type) || findOverridden(descriptor, new Function1<FunctionDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.synthetic.JavaSyntheticPropertiesScope.isGoodSetMethod.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(FunctionDescriptor it) {
                    FunctionDescriptor getMethod2;
                    Intrinsics.checkNotNullParameter(it, "it");
                    SyntheticJavaPropertyDescriptor syntheticJavaPropertyDescriptorFindByGetterOrSetter = SyntheticJavaPropertyDescriptor.INSTANCE.findByGetterOrSetter(it, JavaSyntheticPropertiesScope.this);
                    return Boolean.valueOf(Intrinsics.areEqual((syntheticJavaPropertyDescriptorFindByGetterOrSetter == null || (getMethod2 = syntheticJavaPropertyDescriptorFindByGetterOrSetter.getGetMethod()) == null) ? null : getMethod2.getShortName(), getMethod.getShortName()));
                }
            }) == null) {
                return false;
            }
        }
        if (valueParameterDescriptor.getVarargElementType() != null || !descriptor.getTypeParameters().isEmpty()) {
            return false;
        }
        DescriptorVisibility visibility = descriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        if (SyntheticExtensionsUtilsKt.isVisibleOutside(visibility)) {
            return (descriptor.isHiddenForResolutionEverywhereBesideSupercalls() && Intrinsics.areEqual(descriptor.getShortName().asString(), "isEmpty")) ? false : true;
        }
        return false;
    }

    private final FunctionDescriptor findOverridden(FunctionDescriptor functionDescriptor, Function1<? super FunctionDescriptor, Boolean> function1) {
        for (FunctionDescriptor functionDescriptor2 : functionDescriptor.getOverriddenDescriptors()) {
            Intrinsics.checkNotNull(functionDescriptor2);
            if (function1.invoke(functionDescriptor2).booleanValue()) {
                return functionDescriptor2;
            }
            FunctionDescriptor functionDescriptorFindOverridden = findOverridden(functionDescriptor2, function1);
            if (functionDescriptorFindOverridden != null) {
                return functionDescriptorFindOverridden;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<PropertyDescriptor> getSyntheticExtensionProperties(Collection<? extends KotlinType> receiverTypes, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        HashSet hashSet = receiverTypes.size() > 1 ? new HashSet() : null;
        Iterator<? extends KotlinType> it = receiverTypes.iterator();
        SmartList<PropertyDescriptor> smartListCollectSyntheticPropertiesByName = null;
        while (it.hasNext()) {
            smartListCollectSyntheticPropertiesByName = collectSyntheticPropertiesByName(smartListCollectSyntheticPropertiesByName, it.next().getConstructor(), name2, hashSet, location);
        }
        if (smartListCollectSyntheticPropertiesByName == null) {
            return CollectionsKt.emptyList();
        }
        return smartListCollectSyntheticPropertiesByName.size() > 1 ? CollectionsKt.toSet(smartListCollectSyntheticPropertiesByName) : smartListCollectSyntheticPropertiesByName;
    }

    private final SmartList<PropertyDescriptor> collectSyntheticPropertiesByName(SmartList<PropertyDescriptor> result, TypeConstructor type, Name name2, Set<TypeConstructor> processedTypes, LookupLocation location) {
        if (processedTypes != null && !processedTypes.add(type)) {
            return result;
        }
        ClassifierDescriptor this$0 = type.getThis$0();
        if (this$0 instanceof ClassDescriptor) {
            return add(result, getSyntheticPropertyAndRecordLookups((ClassDescriptor) this$0, name2, location));
        }
        Collection<KotlinType> collectionMo7438getSupertypes = type.mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        Iterator<T> it = collectionMo7438getSupertypes.iterator();
        SmartList<PropertyDescriptor> smartListCollectSyntheticPropertiesByName = result;
        while (it.hasNext()) {
            smartListCollectSyntheticPropertiesByName = collectSyntheticPropertiesByName(smartListCollectSyntheticPropertiesByName, ((KotlinType) it.next()).getConstructor(), name2, processedTypes, location);
        }
        return smartListCollectSyntheticPropertiesByName;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<PropertyDescriptor> getSyntheticExtensionProperties(Collection<? extends KotlinType> receiverTypes, LookupLocation location) {
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        Intrinsics.checkNotNullParameter(location, "location");
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        Iterator<T> it = receiverTypes.iterator();
        while (it.hasNext()) {
            collectSyntheticProperties(arrayList, ((KotlinType) it.next()).getConstructor(), hashSet);
        }
        return arrayList;
    }

    private final void collectSyntheticProperties(List<PropertyDescriptor> list, TypeConstructor typeConstructor, Set<TypeConstructor> set) {
        if (set.add(typeConstructor)) {
            ClassifierDescriptor this$0 = typeConstructor.getThis$0();
            if (this$0 instanceof ClassDescriptor) {
                for (DeclarationDescriptor declarationDescriptor : ResolutionScope.DefaultImpls.getContributedDescriptors$default(ModuleAwareClassDescriptorKt.getRefinedUnsubstitutedMemberScopeIfPossible((ClassDescriptor) this$0, this.typeRefiner), DescriptorKindFilter.FUNCTIONS, null, 2, null)) {
                    if (declarationDescriptor instanceof FunctionDescriptor) {
                        SyntheticJavaPropertyDescriptor.Companion companion = SyntheticJavaPropertyDescriptor.INSTANCE;
                        Name name2 = declarationDescriptor.getShortName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        Name namePropertyNameByGetMethodName = companion.propertyNameByGetMethodName(name2);
                        if (namePropertyNameByGetMethodName != null) {
                            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, this.syntheticPropertyInClass.invoke(new Pair(this$0, namePropertyNameByGetMethodName)).getDescriptor());
                        }
                        if (isRecord(this$0)) {
                            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, this.syntheticPropertyInClass.invoke(new Pair(this$0, ((FunctionDescriptor) declarationDescriptor).getShortName())).getDescriptor());
                        }
                    }
                }
                return;
            }
            Collection<KotlinType> collectionMo7438getSupertypes = typeConstructor.mo7438getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
            Iterator<T> it = collectionMo7438getSupertypes.iterator();
            while (it.hasNext()) {
                collectSyntheticProperties(list, ((KotlinType) it.next()).getConstructor(), set);
            }
        }
    }

    private final boolean isRecord(ClassifierDescriptor classifierDescriptor) {
        return (classifierDescriptor instanceof JavaClassDescriptor) && ((JavaClassDescriptor) classifierDescriptor).isRecord();
    }

    private final SmartList<PropertyDescriptor> add(SmartList<PropertyDescriptor> smartList, PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            return smartList;
        }
        if (smartList == null) {
            smartList = new SmartList<>();
        }
        smartList.add(propertyDescriptor);
        return smartList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticMemberFunctions(Collection<? extends KotlinType> receiverTypes, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticMemberFunctions(Collection<? extends KotlinType> receiverTypes) {
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        return CollectionsKt.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: JavaSyntheticPropertiesScope.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\b\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u001d\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J%\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope$SyntheticPropertyHolder;", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "lookedNames", "", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Ljava/util/List;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getLookedNames", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class SyntheticPropertyHolder {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final SyntheticPropertyHolder EMPTY = new SyntheticPropertyHolder(null, CollectionsKt.emptyList());
        private final PropertyDescriptor descriptor;
        private final List<Name> lookedNames;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ SyntheticPropertyHolder copy$default(SyntheticPropertyHolder syntheticPropertyHolder, PropertyDescriptor propertyDescriptor, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                propertyDescriptor = syntheticPropertyHolder.descriptor;
            }
            if ((i & 2) != 0) {
                list = syntheticPropertyHolder.lookedNames;
            }
            return syntheticPropertyHolder.copy(propertyDescriptor, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final PropertyDescriptor getDescriptor() {
            return this.descriptor;
        }

        public final List<Name> component2() {
            return this.lookedNames;
        }

        public final SyntheticPropertyHolder copy(PropertyDescriptor descriptor, List<Name> lookedNames) {
            Intrinsics.checkNotNullParameter(lookedNames, "lookedNames");
            return new SyntheticPropertyHolder(descriptor, lookedNames);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SyntheticPropertyHolder)) {
                return false;
            }
            SyntheticPropertyHolder syntheticPropertyHolder = (SyntheticPropertyHolder) other;
            return Intrinsics.areEqual(this.descriptor, syntheticPropertyHolder.descriptor) && Intrinsics.areEqual(this.lookedNames, syntheticPropertyHolder.lookedNames);
        }

        public int hashCode() {
            PropertyDescriptor propertyDescriptor = this.descriptor;
            return ((propertyDescriptor == null ? 0 : propertyDescriptor.hashCode()) * 31) + this.lookedNames.hashCode();
        }

        public String toString() {
            return "SyntheticPropertyHolder(descriptor=" + this.descriptor + ", lookedNames=" + this.lookedNames + ')';
        }

        /* JADX INFO: compiled from: JavaSyntheticPropertiesScope.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope$SyntheticPropertyHolder$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope$SyntheticPropertyHolder;", "getEMPTY", "()Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope$SyntheticPropertyHolder;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final SyntheticPropertyHolder getEMPTY() {
                return SyntheticPropertyHolder.EMPTY;
            }
        }

        public SyntheticPropertyHolder(PropertyDescriptor propertyDescriptor, List<Name> lookedNames) {
            Intrinsics.checkNotNullParameter(lookedNames, "lookedNames");
            this.descriptor = propertyDescriptor;
            this.lookedNames = lookedNames;
        }

        public final PropertyDescriptor getDescriptor() {
            return this.descriptor;
        }

        public final List<Name> getLookedNames() {
            return this.lookedNames;
        }
    }

    /* JADX INFO: compiled from: JavaSyntheticPropertiesScope.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 )2\u00020\u00012\u00020\u0002:\u0001)BO\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015JB\u0010!\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\n2\u0006\u0010$\u001a\u00020\f2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u0012\u0010&\u001a\u0004\u0018\u00010\u00062\u0006\u0010'\u001a\u00020(H\u0016R+\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u00178V@RX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\"\u0010\u001f\u001a\u0004\u0018\u00010\u00172\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001a¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope$MyPropertyDescriptor;", "Lorg/jetbrains/kotlin/synthetic/SyntheticJavaPropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "original", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isVar", "", "name", "Lorg/jetbrains/kotlin/name/Name;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/descriptors/Modality;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;ZLorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "<set-?>", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getMethod", "getGetMethod", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "setGetMethod", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "getMethod$delegate", "Lkotlin/properties/ReadWriteProperty;", "setMethod", "getSetMethod", "createSubstitutedCopy", "newOwner", "newModality", "newVisibility", "newName", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class MyPropertyDescriptor extends PropertyDescriptorImpl implements SyntheticJavaPropertyDescriptor {
        static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(MyPropertyDescriptor.class), "getMethod", "getGetMethod()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;"))};

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        /* JADX INFO: renamed from: getMethod$delegate, reason: from kotlin metadata */
        private final ReadWriteProperty getMethod;
        private FunctionDescriptor setMethod;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MyPropertyDescriptor(DeclarationDescriptor containingDeclaration, PropertyDescriptor propertyDescriptor, Annotations annotations, Modality modality, DescriptorVisibility visibility, boolean z, Name name2, CallableMemberDescriptor.Kind kind, SourceElement source) {
            super(containingDeclaration, propertyDescriptor, annotations, modality, visibility, z, name2, kind, source, false, false, false, false, false, false);
            Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            Intrinsics.checkNotNullParameter(modality, "modality");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(source, "source");
            this.getMethod = Delegates.INSTANCE.notNull();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setGetMethod(FunctionDescriptor functionDescriptor) {
            this.getMethod.setValue(this, $$delegatedProperties[0], functionDescriptor);
        }

        @Override // org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor, org.jetbrains.kotlin.descriptors.SyntheticPropertyDescriptor
        public FunctionDescriptor getGetMethod() {
            return (FunctionDescriptor) this.getMethod.getValue(this, $$delegatedProperties[0]);
        }

        @Override // org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor, org.jetbrains.kotlin.descriptors.SyntheticPropertyDescriptor
        public FunctionDescriptor getSetMethod() {
            return this.setMethod;
        }

        /* JADX INFO: compiled from: JavaSyntheticPropertiesScope.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope$MyPropertyDescriptor$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope$MyPropertyDescriptor;", "ownerClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getMethod", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "setMethod", "name", "Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final MyPropertyDescriptor create(ClassDescriptor ownerClass, FunctionDescriptor getMethod, FunctionDescriptor setMethod, Name name2, KotlinType type) {
                Intrinsics.checkNotNullParameter(ownerClass, "ownerClass");
                Intrinsics.checkNotNullParameter(getMethod, "getMethod");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(type, "type");
                DescriptorVisibility descriptorVisibilitySyntheticVisibility = SyntheticExtensionsUtilsKt.syntheticVisibility(getMethod, true);
                ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(ownerClass);
                Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
                ModuleDescriptor moduleDescriptor = containingModule;
                Annotations empty = Annotations.INSTANCE.getEMPTY();
                Modality modality = Modality.FINAL;
                boolean z = setMethod != null;
                CallableMemberDescriptor.Kind kind = CallableMemberDescriptor.Kind.SYNTHESIZED;
                SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                MyPropertyDescriptor myPropertyDescriptor = new MyPropertyDescriptor(moduleDescriptor, null, empty, modality, descriptorVisibilitySyntheticVisibility, z, name2, kind, NO_SOURCE);
                myPropertyDescriptor.setGetMethod(getMethod);
                myPropertyDescriptor.setMethod = setMethod;
                List<TypeParameterDescriptor> parameters = ownerClass.getTypeConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                ArrayList arrayList = new ArrayList(parameters.size());
                TypeSubstitutor typeSubstitutorSubstituteTypeParameters = DescriptorSubstitutor.substituteTypeParameters(parameters, TypeSubstitution.EMPTY, myPropertyDescriptor, arrayList);
                Intrinsics.checkNotNullExpressionValue(typeSubstitutorSubstituteTypeParameters, "substituteTypeParameters(...)");
                KotlinType kotlinTypeSafeSubstitute = typeSubstitutorSubstituteTypeParameters.safeSubstitute(type, Variance.INVARIANT);
                Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
                KotlinType kotlinTypeSafeSubstitute2 = typeSubstitutorSubstituteTypeParameters.safeSubstitute(ownerClass.getDefaultType(), Variance.INVARIANT);
                Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute2, "safeSubstitute(...)");
                myPropertyDescriptor.setType(kotlinTypeSafeSubstitute, arrayList, null, DescriptorFactory.createExtensionReceiverParameterForCallable(myPropertyDescriptor, kotlinTypeSafeSubstitute2, Annotations.INSTANCE.getEMPTY()), CollectionsKt.emptyList());
                MyPropertyDescriptor myPropertyDescriptor2 = myPropertyDescriptor;
                PropertyGetterDescriptorImpl propertyGetterDescriptorImpl = new PropertyGetterDescriptorImpl(myPropertyDescriptor2, getMethod.getAnnotations(), Modality.FINAL, descriptorVisibilitySyntheticVisibility, false, getMethod.isExternal(), false, CallableMemberDescriptor.Kind.SYNTHESIZED, null, SourceElement.NO_SOURCE);
                propertyGetterDescriptorImpl.initialize(null);
                PropertySetterDescriptorImpl propertySetterDescriptorImpl = setMethod != null ? new PropertySetterDescriptorImpl(myPropertyDescriptor2, setMethod.getAnnotations(), Modality.FINAL, SyntheticExtensionsUtilsKt.syntheticVisibility(setMethod, true), false, setMethod.isExternal(), false, CallableMemberDescriptor.Kind.SYNTHESIZED, null, SourceElement.NO_SOURCE) : null;
                if (propertySetterDescriptorImpl != null) {
                    propertySetterDescriptorImpl.initializeDefault();
                }
                myPropertyDescriptor.initialize(propertyGetterDescriptorImpl, propertySetterDescriptorImpl);
                return myPropertyDescriptor;
            }
        }

        @Override // org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl
        protected PropertyDescriptorImpl createSubstitutedCopy(DeclarationDescriptor newOwner, Modality newModality, DescriptorVisibility newVisibility, PropertyDescriptor original, CallableMemberDescriptor.Kind kind, Name newName, SourceElement source) {
            Intrinsics.checkNotNullParameter(newOwner, "newOwner");
            Intrinsics.checkNotNullParameter(newModality, "newModality");
            Intrinsics.checkNotNullParameter(newVisibility, "newVisibility");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(newName, "newName");
            Intrinsics.checkNotNullParameter(source, "source");
            Annotations annotations = getAnnotations();
            boolean zIsVar = isVar();
            SourceElement source2 = getSourceElement();
            Intrinsics.checkNotNullExpressionValue(source2, "getSource(...)");
            MyPropertyDescriptor myPropertyDescriptor = new MyPropertyDescriptor(newOwner, this, annotations, newModality, newVisibility, zIsVar, newName, kind, source2);
            myPropertyDescriptor.setGetMethod(getGetMethod());
            myPropertyDescriptor.setMethod = getSetMethod();
            return myPropertyDescriptor;
        }

        @Override // org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl, org.jetbrains.kotlin.descriptors.VariableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
        public PropertyDescriptor substitute(TypeSubstitutor substitutor) {
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            MyPropertyDescriptor myPropertyDescriptor = (MyPropertyDescriptor) super.substitute(substitutor);
            if (myPropertyDescriptor == null) {
                return null;
            }
            if (Intrinsics.areEqual(myPropertyDescriptor, this)) {
                return myPropertyDescriptor;
            }
            DeclarationDescriptor packageFragment = getGetMethod().getPackageFragment();
            Intrinsics.checkNotNull(packageFragment, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            List<TypeParameterDescriptor> parameters = ((ClassDescriptor) packageFragment).getTypeConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            HashMap map = new HashMap();
            List<TypeParameterDescriptor> typeParameters = getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            for (Pair pair : CollectionsKt.zip(typeParameters, parameters)) {
                TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) pair.component1();
                TypeParameterDescriptor typeParameterDescriptor2 = (TypeParameterDescriptor) pair.component2();
                TypeSubstitution substitution = substitutor.getSubstitution();
                SimpleType defaultType = typeParameterDescriptor.getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                TypeProjection typeProjectionMo7475get = substitution.mo7475get(defaultType);
                if (typeProjectionMo7475get != null) {
                    TypeConstructor typeConstructor = typeParameterDescriptor2.getTypeConstructor();
                    Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
                    map.put(typeConstructor, typeProjectionMo7475get);
                }
            }
            TypeSubstitutor typeSubstitutorBuildSubstitutor = TypeConstructorSubstitution.INSTANCE.createByConstructorsMap(map, true).buildSubstitutor();
            FunctionDescriptor functionDescriptorSubstitute = getGetMethod().substitute(typeSubstitutorBuildSubstitutor);
            if (functionDescriptorSubstitute == null) {
                return null;
            }
            myPropertyDescriptor.setGetMethod(functionDescriptorSubstitute);
            FunctionDescriptor setMethod = getSetMethod();
            myPropertyDescriptor.setMethod = setMethod != null ? setMethod.substitute(typeSubstitutorBuildSubstitutor) : null;
            return myPropertyDescriptor;
        }
    }
}
