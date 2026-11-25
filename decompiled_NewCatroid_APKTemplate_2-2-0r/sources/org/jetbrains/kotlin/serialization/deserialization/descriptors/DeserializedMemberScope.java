package org.jetbrains.kotlin.serialization.deserialization.descriptors;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KProperty;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.AbstractMessageLite;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.protobuf.Parser;
import org.jetbrains.kotlin.resolve.MemberComparator;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationContext;
import org.jetbrains.kotlin.serialization.deserialization.MemberDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: DeserializedMemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001:\u0003JKLBM\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f¢\u0006\u0002\u0010\u000fJ*\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020$0#H$J2\u0010%\u001a\b\u0012\u0004\u0012\u00020!0\r2\u0006\u0010&\u001a\u00020'2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020$0#2\u0006\u0010(\u001a\u00020)H\u0004J\u001e\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u000e2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-H\u0014J\u001e\u0010/\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u000e2\f\u00100\u001a\b\u0012\u0004\u0012\u0002010-H\u0014J\u0010\u00102\u001a\u0002032\u0006\u0010+\u001a\u00020\u000eH$J2\u00104\u001a\u00020\u001c2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005H\u0002J\u0010\u00105\u001a\u00020$2\u0006\u0010+\u001a\u00020\u000eH\u0016J\u0012\u00106\u001a\u0004\u0018\u0001072\u0006\u0010+\u001a\u00020\u000eH\u0002J\u0010\u00108\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0012H\u0016J\u001a\u00109\u001a\u0004\u0018\u00010:2\u0006\u0010+\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)H\u0016J\u001e\u0010;\u001a\b\u0012\u0004\u0012\u00020.0\r2\u0006\u0010+\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)H\u0016J\u001e\u0010<\u001a\b\u0012\u0004\u0012\u0002010\r2\u0006\u0010+\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)H\u0016J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0012H\u0016J\u0010\u0010>\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0012H$J\u000e\u0010?\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0012H$J\u000e\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0012H$J\u0012\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010+\u001a\u00020\u000eH\u0002J\u000e\u0010C\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0012H\u0016J\u0010\u0010D\u001a\u00020$2\u0006\u0010+\u001a\u00020\u000eH\u0014J\u0010\u0010E\u001a\u00020$2\u0006\u0010F\u001a\u00020.H\u0014J\u0010\u0010G\u001a\u00020\u001e2\u0006\u0010H\u001a\u00020IH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R!\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00128@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R#\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0018\u0010\u0014R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006M"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "c", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationContext;", "functionList", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "propertyList", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "typeAliasList", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "classNames", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationContext;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lkotlin/jvm/functions/Function0;)V", "getC", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationContext;", "", "getClassNames$deserialization", "()Ljava/util/Set;", "classNames$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "classifierNamesLazy", "getClassifierNamesLazy", "classifierNamesLazy$delegate", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "impl", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope$Implementation;", "addEnumEntryDescriptors", "", "result", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "nameFilter", "Lkotlin/Function1;", "", "computeDescriptors", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "computeNonDeclaredFunctions", "name", "functions", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "computeNonDeclaredProperties", "descriptors", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "createClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "createImplementation", "definitelyDoesNotContainName", "deserializeClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassifierNames", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getContributedFunctions", "getContributedVariables", "getFunctionNames", "getNonDeclaredClassifierNames", "getNonDeclaredFunctionNames", "getNonDeclaredVariableNames", "getTypeAliasByName", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "getVariableNames", "hasClass", "isDeclaredFunctionAvailable", "function", "printScopeStructure", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "Implementation", "NoReorderImplementation", "OptimizedImplementation", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeserializedMemberScope extends MemberScopeImpl {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "classNames", "getClassNames$deserialization()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "classifierNamesLazy", "getClassifierNamesLazy()Ljava/util/Set;"))};
    private final DeserializationContext c;

    /* JADX INFO: renamed from: classNames$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue classNames;

    /* JADX INFO: renamed from: classifierNamesLazy$delegate, reason: from kotlin metadata */
    private final NullableLazyValue classifierNamesLazy;
    private final Implementation impl;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DeserializedMemberScope.kt */
    @Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bb\u0018\u00002\u00020\u0001J:\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u0016H&J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H&J\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00182\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001a\u001a\u00020\u0004H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope$Implementation;", "", "functionNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getFunctionNames", "()Ljava/util/Set;", "typeAliasNames", "getTypeAliasNames", "variableNames", "getVariableNames", "addFunctionsAndPropertiesTo", "", "result", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedFunctions", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getTypeAliasByName", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    interface Implementation {
        void addFunctionsAndPropertiesTo(Collection<DeclarationDescriptor> result, DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, LookupLocation location);

        Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location);

        Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location);

        Set<Name> getFunctionNames();

        TypeAliasDescriptor getTypeAliasByName(Name name2);

        Set<Name> getTypeAliasNames();

        Set<Name> getVariableNames();
    }

    protected abstract void addEnumEntryDescriptors(Collection<DeclarationDescriptor> result, Function1<? super Name, Boolean> nameFilter);

    protected void computeNonDeclaredFunctions(Name name2, List<SimpleFunctionDescriptor> functions) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(functions, "functions");
    }

    protected void computeNonDeclaredProperties(Name name2, List<PropertyDescriptor> descriptors) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
    }

    protected abstract ClassId createClassId(Name name2);

    protected abstract Set<Name> getNonDeclaredClassifierNames();

    protected abstract Set<Name> getNonDeclaredFunctionNames();

    protected abstract Set<Name> getNonDeclaredVariableNames();

    protected boolean isDeclaredFunctionAvailable(SimpleFunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return true;
    }

    protected final DeserializationContext getC() {
        return this.c;
    }

    protected DeserializedMemberScope(DeserializationContext c, List<ProtoBuf.Function> functionList, List<ProtoBuf.Property> propertyList, List<ProtoBuf.TypeAlias> typeAliasList, final Function0<? extends Collection<Name>> classNames) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(functionList, "functionList");
        Intrinsics.checkNotNullParameter(propertyList, "propertyList");
        Intrinsics.checkNotNullParameter(typeAliasList, "typeAliasList");
        Intrinsics.checkNotNullParameter(classNames, "classNames");
        this.c = c;
        this.impl = createImplementation(functionList, propertyList, typeAliasList);
        this.classNames = c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$classNames$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                return CollectionsKt.toSet(classNames.invoke());
            }
        });
        this.classifierNamesLazy = c.getStorageManager().createNullableLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$classifierNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                Set<Name> nonDeclaredClassifierNames = this.this$0.getNonDeclaredClassifierNames();
                if (nonDeclaredClassifierNames == null) {
                    return null;
                }
                return SetsKt.plus(SetsKt.plus((Set) this.this$0.getClassNames$deserialization(), (Iterable) this.this$0.impl.getTypeAliasNames()), (Iterable) nonDeclaredClassifierNames);
            }
        });
    }

    public final Set<Name> getClassNames$deserialization() {
        return (Set) StorageKt.getValue(this.classNames, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final Set<Name> getClassifierNamesLazy() {
        return (Set) StorageKt.getValue(this.classifierNamesLazy, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        return this.impl.getFunctionNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return this.impl.getVariableNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        return getClassifierNamesLazy();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return (this.impl.getFunctionNames().contains(name2) || this.impl.getVariableNames().contains(name2) || getClassNames$deserialization().contains(name2) || this.impl.getTypeAliasNames().contains(name2)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.impl.getContributedFunctions(name2, location);
    }

    private final TypeAliasDescriptor getTypeAliasByName(Name name2) {
        return this.impl.getTypeAliasByName(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.impl.getContributedVariables(name2, location);
    }

    protected final Collection<DeclarationDescriptor> computeDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, LookupLocation location) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Intrinsics.checkNotNullParameter(location, "location");
        ArrayList arrayList = new ArrayList(0);
        if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getSINGLETON_CLASSIFIERS_MASK())) {
            addEnumEntryDescriptors(arrayList, nameFilter);
        }
        ArrayList arrayList2 = arrayList;
        this.impl.addFunctionsAndPropertiesTo(arrayList2, kindFilter, nameFilter, location);
        if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getCLASSIFIERS_MASK())) {
            for (Name name2 : getClassNames$deserialization()) {
                if (nameFilter.invoke(name2).booleanValue()) {
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, deserializeClass(name2));
                }
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getTYPE_ALIASES_MASK())) {
            for (Name name3 : this.impl.getTypeAliasNames()) {
                if (nameFilter.invoke(name3).booleanValue()) {
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, this.impl.getTypeAliasByName(name3));
                }
            }
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (hasClass(name2)) {
            return deserializeClass(name2);
        }
        if (this.impl.getTypeAliasNames().contains(name2)) {
            return getTypeAliasByName(name2);
        }
        return null;
    }

    private final ClassDescriptor deserializeClass(Name name2) {
        return this.c.getComponents().deserializeClass(createClassId(name2));
    }

    protected boolean hasClass(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getClassNames$deserialization().contains(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName(), " {");
        p.pushIndent();
        p.println("containingDeclaration = " + this.c.getContainingDeclaration());
        p.popIndent();
        p.println("}");
    }

    private final Implementation createImplementation(List<ProtoBuf.Function> functionList, List<ProtoBuf.Property> propertyList, List<ProtoBuf.TypeAlias> typeAliasList) {
        if (this.c.getComponents().getConfiguration().getPreserveDeclarationsOrdering()) {
            return new NoReorderImplementation(this, functionList, propertyList, typeAliasList);
        }
        return new OptimizedImplementation(this, functionList, propertyList, typeAliasList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DeserializedMemberScope.kt */
    @Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B/\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003¢\u0006\u0002\u0010\tJ:\u0010$\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'2\u0006\u0010)\u001a\u00020*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020-0,2\u0006\u0010.\u001a\u00020/H\u0016JS\u00100\u001a\u00020%2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\f0\u00162\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020-0,2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'2\u0018\u00102\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u00160,H\u0082\bJa\u00103\u001a\b\u0012\u0004\u0012\u0002H40\u0016\"\b\b\u0000\u00105*\u000206\"\b\b\u0001\u00104*\u00020(2\f\u00107\u001a\b\u0012\u0004\u0012\u0002H50\u00162\u0014\u00108\u001a\u0010\u0012\u0004\u0012\u0002H5\u0012\u0006\u0012\u0004\u0018\u0001H40,2\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H40:\u0012\u0004\u0012\u00020%0,H\u0082\bJ}\u00103\u001a\b\u0012\u0004\u0012\u0002H40\u0016\"\b\b\u0000\u00105*\u000206\"\b\b\u0001\u00104*\u00020(2\u0006\u0010;\u001a\u00020\f2\u0012\u0010<\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H50>2\u0014\u00108\u001a\u0010\u0012\u0004\u0012\u0002H5\u0012\u0006\u0012\u0004\u0018\u0001H40,2\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H40:\u0012\u0004\u0012\u00020%0,H\u0082\bJ\u0016\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010;\u001a\u00020\fH\u0002J\u0016\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00190\u00162\u0006\u0010;\u001a\u00020\fH\u0002J\u0012\u0010A\u001a\u0004\u0018\u00010\u001d2\u0006\u0010;\u001a\u00020\fH\u0002J\u001e\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010;\u001a\u00020\f2\u0006\u0010.\u001a\u00020/H\u0016J\u001e\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00190\u00162\u0006\u0010;\u001a\u00020\f2\u0006\u0010.\u001a\u00020/H\u0016J\u0012\u0010D\u001a\u0004\u0018\u00010\u001d2\u0006\u0010;\u001a\u00020\fH\u0016JC\u0010E\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\u00030\u0012\"\b\b\u0000\u00105*\u000206*\b\u0012\u0004\u0012\u0002H50\u00162\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H5\u0012\u0004\u0012\u00020G0,H\u0082\bJ*\u0010H\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00130\u0012*\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020I0\u00160\u0012H\u0002R!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00160\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u001d0\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u000eR!\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u0010\u001a\u0004\b\"\u0010\u000e¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope$OptimizedImplementation;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope$Implementation;", "functionList", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "propertyList", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "typeAliasList", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "functionNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getFunctionNames", "()Ljava/util/Set;", "functionNames$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "functionProtosBytes", "", "", "functions", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "properties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "propertyProtosBytes", "typeAliasByName", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "typeAliasBytes", "typeAliasNames", "getTypeAliasNames", "variableNames", "getVariableNames", "variableNames$delegate", "addFunctionsAndPropertiesTo", "", "result", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "addMembers", "names", "descriptorsByName", "computeDescriptors", "D", "M", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "protos", "factory", "computeNonDeclared", "", "name", "bytesByName", "parser", "Lorg/jetbrains/kotlin/protobuf/Parser;", "computeFunctions", "computeProperties", "createTypeAlias", "getContributedFunctions", "getContributedVariables", "getTypeAliasByName", "groupByName", "getNameIndex", "", "packToByteArray", "Lorg/jetbrains/kotlin/protobuf/AbstractMessageLite;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class OptimizedImplementation implements Implementation {
        static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(OptimizedImplementation.class), "functionNames", "getFunctionNames()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(OptimizedImplementation.class), "variableNames", "getVariableNames()Ljava/util/Set;"))};

        /* JADX INFO: renamed from: functionNames$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue functionNames;
        private final Map<Name, byte[]> functionProtosBytes;
        private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> functions;
        private final MemoizedFunctionToNotNull<Name, Collection<PropertyDescriptor>> properties;
        private final Map<Name, byte[]> propertyProtosBytes;
        final /* synthetic */ DeserializedMemberScope this$0;
        private final MemoizedFunctionToNullable<Name, TypeAliasDescriptor> typeAliasByName;
        private final Map<Name, byte[]> typeAliasBytes;

        /* JADX INFO: renamed from: variableNames$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue variableNames;

        public OptimizedImplementation(DeserializedMemberScope deserializedMemberScope, List<ProtoBuf.Function> functionList, List<ProtoBuf.Property> propertyList, List<ProtoBuf.TypeAlias> typeAliasList) throws IOException {
            Map<Name, byte[]> mapEmptyMap;
            Intrinsics.checkNotNullParameter(functionList, "functionList");
            Intrinsics.checkNotNullParameter(propertyList, "propertyList");
            Intrinsics.checkNotNullParameter(typeAliasList, "typeAliasList");
            this.this$0 = deserializedMemberScope;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : functionList) {
                Name name2 = NameResolverUtilKt.getName(deserializedMemberScope.getC().getNameResolver(), ((ProtoBuf.Function) ((MessageLite) obj)).getName());
                Object obj2 = linkedHashMap.get(name2);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMap.put(name2, obj2);
                }
                ((List) obj2).add(obj);
            }
            this.functionProtosBytes = packToByteArray(linkedHashMap);
            DeserializedMemberScope deserializedMemberScope2 = this.this$0;
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (Object obj3 : propertyList) {
                Name name3 = NameResolverUtilKt.getName(deserializedMemberScope2.getC().getNameResolver(), ((ProtoBuf.Property) ((MessageLite) obj3)).getName());
                Object obj4 = linkedHashMap2.get(name3);
                if (obj4 == null) {
                    obj4 = (List) new ArrayList();
                    linkedHashMap2.put(name3, obj4);
                }
                ((List) obj4).add(obj3);
            }
            this.propertyProtosBytes = packToByteArray(linkedHashMap2);
            if (!this.this$0.getC().getComponents().getConfiguration().getTypeAliasesAllowed()) {
                mapEmptyMap = MapsKt.emptyMap();
            } else {
                DeserializedMemberScope deserializedMemberScope3 = this.this$0;
                LinkedHashMap linkedHashMap3 = new LinkedHashMap();
                for (Object obj5 : typeAliasList) {
                    Name name4 = NameResolverUtilKt.getName(deserializedMemberScope3.getC().getNameResolver(), ((ProtoBuf.TypeAlias) ((MessageLite) obj5)).getName());
                    Object obj6 = linkedHashMap3.get(name4);
                    if (obj6 == null) {
                        obj6 = (List) new ArrayList();
                        linkedHashMap3.put(name4, obj6);
                    }
                    ((List) obj6).add(obj5);
                }
                mapEmptyMap = packToByteArray(linkedHashMap3);
            }
            this.typeAliasBytes = mapEmptyMap;
            this.functions = this.this$0.getC().getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$OptimizedImplementation$functions$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Collection<SimpleFunctionDescriptor> invoke(Name it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.computeFunctions(it);
                }
            });
            this.properties = this.this$0.getC().getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends PropertyDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$OptimizedImplementation$properties$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Collection<PropertyDescriptor> invoke(Name it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.computeProperties(it);
                }
            });
            this.typeAliasByName = this.this$0.getC().getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Name, TypeAliasDescriptor>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$OptimizedImplementation$typeAliasByName$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final TypeAliasDescriptor invoke(Name it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.createTypeAlias(it);
                }
            });
            StorageManager storageManager = this.this$0.getC().getStorageManager();
            final DeserializedMemberScope deserializedMemberScope4 = this.this$0;
            this.functionNames = storageManager.createLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$OptimizedImplementation$functionNames$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Set<? extends Name> invoke() {
                    return SetsKt.plus(this.this$0.functionProtosBytes.keySet(), (Iterable) deserializedMemberScope4.getNonDeclaredFunctionNames());
                }
            });
            StorageManager storageManager2 = this.this$0.getC().getStorageManager();
            final DeserializedMemberScope deserializedMemberScope5 = this.this$0;
            this.variableNames = storageManager2.createLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$OptimizedImplementation$variableNames$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Set<? extends Name> invoke() {
                    return SetsKt.plus(this.this$0.propertyProtosBytes.keySet(), (Iterable) deserializedMemberScope5.getNonDeclaredVariableNames());
                }
            });
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Set<Name> getFunctionNames() {
            return (Set) StorageKt.getValue(this.functionNames, this, (KProperty<?>) $$delegatedProperties[0]);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Set<Name> getVariableNames() {
            return (Set) StorageKt.getValue(this.variableNames, this, (KProperty<?>) $$delegatedProperties[1]);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Set<Name> getTypeAliasNames() {
            return this.typeAliasBytes.keySet();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code duplicated, block: B:7:0x002e  */
        public final Collection<SimpleFunctionDescriptor> computeFunctions(Name name2) {
            List<ProtoBuf.Function> listEmptyList;
            Map<Name, byte[]> map = this.functionProtosBytes;
            Parser<ProtoBuf.Function> PARSER = ProtoBuf.Function.PARSER;
            Intrinsics.checkNotNullExpressionValue(PARSER, "PARSER");
            DeserializedMemberScope deserializedMemberScope = this.this$0;
            byte[] bArr = map.get(name2);
            if (bArr != null) {
                List list = SequencesKt.toList(SequencesKt.generateSequence(new DeserializedMemberScope$OptimizedImplementation$computeDescriptors$1$1(PARSER, new ByteArrayInputStream(bArr), this.this$0)));
                if (list != null) {
                    listEmptyList = list;
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            ArrayList arrayList = new ArrayList(listEmptyList.size());
            for (ProtoBuf.Function function : listEmptyList) {
                MemberDeserializer memberDeserializer = deserializedMemberScope.getC().getMemberDeserializer();
                Intrinsics.checkNotNull(function);
                SimpleFunctionDescriptor simpleFunctionDescriptorLoadFunction = memberDeserializer.loadFunction(function);
                if (!deserializedMemberScope.isDeclaredFunctionAvailable(simpleFunctionDescriptorLoadFunction)) {
                    simpleFunctionDescriptorLoadFunction = null;
                }
                if (simpleFunctionDescriptorLoadFunction != null) {
                    arrayList.add(simpleFunctionDescriptorLoadFunction);
                }
            }
            ArrayList arrayList2 = arrayList;
            deserializedMemberScope.computeNonDeclaredFunctions(name2, arrayList2);
            return org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code duplicated, block: B:7:0x002e  */
        public final Collection<PropertyDescriptor> computeProperties(Name name2) {
            List<ProtoBuf.Property> listEmptyList;
            Map<Name, byte[]> map = this.propertyProtosBytes;
            Parser<ProtoBuf.Property> PARSER = ProtoBuf.Property.PARSER;
            Intrinsics.checkNotNullExpressionValue(PARSER, "PARSER");
            DeserializedMemberScope deserializedMemberScope = this.this$0;
            byte[] bArr = map.get(name2);
            if (bArr != null) {
                List list = SequencesKt.toList(SequencesKt.generateSequence(new DeserializedMemberScope$OptimizedImplementation$computeDescriptors$1$1(PARSER, new ByteArrayInputStream(bArr), this.this$0)));
                if (list != null) {
                    listEmptyList = list;
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            ArrayList arrayList = new ArrayList(listEmptyList.size());
            for (ProtoBuf.Property property : listEmptyList) {
                MemberDeserializer memberDeserializer = deserializedMemberScope.getC().getMemberDeserializer();
                Intrinsics.checkNotNull(property);
                PropertyDescriptor propertyDescriptorLoadProperty = memberDeserializer.loadProperty(property);
                if (propertyDescriptorLoadProperty != null) {
                    arrayList.add(propertyDescriptorLoadProperty);
                }
            }
            ArrayList arrayList2 = arrayList;
            deserializedMemberScope.computeNonDeclaredProperties(name2, arrayList2);
            return org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TypeAliasDescriptor createTypeAlias(Name name2) {
            ProtoBuf.TypeAlias delimitedFrom;
            byte[] bArr = this.typeAliasBytes.get(name2);
            if (bArr == null || (delimitedFrom = ProtoBuf.TypeAlias.parseDelimitedFrom(new ByteArrayInputStream(bArr), this.this$0.getC().getComponents().getExtensionRegistryLite())) == null) {
                return null;
            }
            return this.this$0.getC().getMemberDeserializer().loadTypeAlias(delimitedFrom);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return !getFunctionNames().contains(name2) ? CollectionsKt.emptyList() : this.functions.invoke(name2);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public TypeAliasDescriptor getTypeAliasByName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return this.typeAliasByName.invoke(name2);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return !getVariableNames().contains(name2) ? CollectionsKt.emptyList() : this.properties.invoke(name2);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public void addFunctionsAndPropertiesTo(Collection<DeclarationDescriptor> result, DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, LookupLocation location) {
            Intrinsics.checkNotNullParameter(result, "result");
            Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            Intrinsics.checkNotNullParameter(location, "location");
            if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getVARIABLES_MASK())) {
                Set<Name> variableNames = getVariableNames();
                ArrayList arrayList = new ArrayList();
                for (Name name2 : variableNames) {
                    if (nameFilter.invoke(name2).booleanValue()) {
                        arrayList.addAll(getContributedVariables(name2, location));
                    }
                }
                MemberComparator.NameAndTypeMemberComparator INSTANCE = MemberComparator.NameAndTypeMemberComparator.INSTANCE;
                Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
                CollectionsKt.sortWith(arrayList, INSTANCE);
                result.addAll(arrayList);
            }
            if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getFUNCTIONS_MASK())) {
                Set<Name> functionNames = getFunctionNames();
                ArrayList arrayList2 = new ArrayList();
                for (Name name3 : functionNames) {
                    if (nameFilter.invoke(name3).booleanValue()) {
                        arrayList2.addAll(getContributedFunctions(name3, location));
                    }
                }
                MemberComparator.NameAndTypeMemberComparator INSTANCE2 = MemberComparator.NameAndTypeMemberComparator.INSTANCE;
                Intrinsics.checkNotNullExpressionValue(INSTANCE2, "INSTANCE");
                CollectionsKt.sortWith(arrayList2, INSTANCE2);
                result.addAll(arrayList2);
            }
        }

        private final Map<Name, byte[]> packToByteArray(Map<Name, ? extends Collection<? extends AbstractMessageLite>> map) throws IOException {
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
            Iterator<T> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Iterable iterable = (Iterable) entry.getValue();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                Iterator it2 = iterable.iterator();
                while (it2.hasNext()) {
                    ((AbstractMessageLite) it2.next()).writeDelimitedTo(byteArrayOutputStream);
                    arrayList.add(Unit.INSTANCE);
                }
                linkedHashMap.put(key, byteArrayOutputStream.toByteArray());
            }
            return linkedHashMap;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DeserializedMemberScope.kt */
    @Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010\u001e\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B/\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003¢\u0006\u0002\u0010\tJ:\u00105\u001a\u0002062\f\u00107\u001a\b\u0012\u0004\u0012\u000209082\u0006\u0010:\u001a\u00020;2\u0012\u0010<\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020>0=2\u0006\u0010?\u001a\u00020@H\u0016J\u000e\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003H\u0002J\u000e\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003H\u0002J\u000e\u0010C\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003H\u0002JO\u0010D\u001a\b\u0012\u0004\u0012\u0002HE0\u0003\"\b\b\u0000\u0010E*\u0002092\u0006\u0010F\u001a\u00020 2\f\u0010G\u001a\b\u0012\u0004\u0012\u0002HE0\u00032\u001e\u0010H\u001a\u001a\u0012\u0004\u0012\u00020 \u0012\n\u0012\b\u0012\u0004\u0012\u0002HE0J\u0012\u0004\u0012\u0002060IH\u0082\bJ\u0016\u0010K\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\u0006\u0010F\u001a\u00020 H\u0002J\u0016\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\u0006\u0010F\u001a\u00020 H\u0002J\u000e\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003H\u0002J\u000e\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00150\u0003H\u0002J\u001e\u0010O\u001a\b\u0012\u0004\u0012\u00020\u000b0&2\u0006\u0010F\u001a\u00020 2\u0006\u0010?\u001a\u00020@H\u0016J\u001e\u0010P\u001a\b\u0012\u0004\u0012\u00020\u00110&2\u0006\u0010F\u001a\u00020 2\u0006\u0010?\u001a\u00020@H\u0016J\u0012\u0010Q\u001a\u0004\u0018\u00010\u00152\u0006\u0010F\u001a\u00020 H\u0016J7\u0010R\u001a\b\u0012\u0004\u0012\u00020 0\u001f\"\b\b\u0000\u0010E*\u00020S*\b\u0012\u0004\u0012\u0002HE0\u00032\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u0002HE\u0012\u0004\u0012\u00020U0=H\u0082\bJN\u0010V\u001a\b\u0012\u0004\u0012\u0002HW0\u0003\"\b\b\u0000\u0010E*\u00020S\"\b\b\u0001\u0010W*\u00020X*\b\u0012\u0004\u0012\u0002HE0\u00032\u001f\u0010Y\u001a\u001b\u0012\u0004\u0012\u00020Z\u0012\u0004\u0012\u0002HE\u0012\u0006\u0012\u0004\u0018\u0001HW0I¢\u0006\u0002\b[H\u0082\bR!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR!\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000f\u001a\u0004\b\u0012\u0010\rR!\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u000f\u001a\u0004\b\u0016\u0010\rR!\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u000f\u001a\u0004\b\u0019\u0010\rR!\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00110\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u000f\u001a\u0004\b\u001c\u0010\rR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u000f\u001a\u0004\b!\u0010\"R-\u0010$\u001a\u0014\u0012\u0004\u0012\u00020 \u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0&0%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010\u000f\u001a\u0004\b'\u0010(R-\u0010*\u001a\u0014\u0012\u0004\u0012\u00020 \u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110&0%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u000f\u001a\u0004\b+\u0010(R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00020 0\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\"R'\u0010/\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00150%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\u000f\u001a\u0004\b0\u0010(R!\u00102\u001a\b\u0012\u0004\u0012\u00020 0\u001f8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\u000f\u001a\u0004\b3\u0010\"¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope$NoReorderImplementation;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope$Implementation;", "functionList", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "propertyList", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "typeAliasList", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "allFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getAllFunctions", "()Ljava/util/List;", "allFunctions$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "allProperties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getAllProperties", "allProperties$delegate", "allTypeAliases", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "getAllTypeAliases", "allTypeAliases$delegate", "declaredFunctions", "getDeclaredFunctions", "declaredFunctions$delegate", "declaredProperties", "getDeclaredProperties", "declaredProperties$delegate", "functionNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getFunctionNames", "()Ljava/util/Set;", "functionNames$delegate", "functionsByName", "", "", "getFunctionsByName", "()Ljava/util/Map;", "functionsByName$delegate", "propertiesByName", "getPropertiesByName", "propertiesByName$delegate", "typeAliasNames", "getTypeAliasNames", "typeAliasesByName", "getTypeAliasesByName", "typeAliasesByName$delegate", "variableNames", "getVariableNames", "variableNames$delegate", "addFunctionsAndPropertiesTo", "", "result", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "computeAllNonDeclaredFunctions", "computeAllNonDeclaredProperties", "computeFunctions", "computeNonDeclaredDescriptors", ExifInterface.GPS_DIRECTION_TRUE, "name", "declaredDescriptors", "computeNonDeclared", "Lkotlin/Function2;", "", "computeNonDeclaredFunctionsForName", "computeNonDeclaredPropertiesForName", "computeProperties", "computeTypeAliases", "getContributedFunctions", "getContributedVariables", "getTypeAliasByName", "mapToNames", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "getNameIndex", "", "mapWithDeserializer", "K", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "deserialize", "Lorg/jetbrains/kotlin/serialization/deserialization/MemberDeserializer;", "Lkotlin/ExtensionFunctionType;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class NoReorderImplementation implements Implementation {
        static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "declaredFunctions", "getDeclaredFunctions()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "declaredProperties", "getDeclaredProperties()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "allTypeAliases", "getAllTypeAliases()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "allFunctions", "getAllFunctions()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "allProperties", "getAllProperties()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "typeAliasesByName", "getTypeAliasesByName()Ljava/util/Map;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "functionsByName", "getFunctionsByName()Ljava/util/Map;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "propertiesByName", "getPropertiesByName()Ljava/util/Map;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "functionNames", "getFunctionNames()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(NoReorderImplementation.class), "variableNames", "getVariableNames()Ljava/util/Set;"))};

        /* JADX INFO: renamed from: allFunctions$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue allFunctions;

        /* JADX INFO: renamed from: allProperties$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue allProperties;

        /* JADX INFO: renamed from: allTypeAliases$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue allTypeAliases;

        /* JADX INFO: renamed from: declaredFunctions$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue declaredFunctions;

        /* JADX INFO: renamed from: declaredProperties$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue declaredProperties;
        private final List<ProtoBuf.Function> functionList;

        /* JADX INFO: renamed from: functionNames$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue functionNames;

        /* JADX INFO: renamed from: functionsByName$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue functionsByName;

        /* JADX INFO: renamed from: propertiesByName$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue propertiesByName;
        private final List<ProtoBuf.Property> propertyList;
        final /* synthetic */ DeserializedMemberScope this$0;
        private final List<ProtoBuf.TypeAlias> typeAliasList;

        /* JADX INFO: renamed from: typeAliasesByName$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue typeAliasesByName;

        /* JADX INFO: renamed from: variableNames$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue variableNames;

        public NoReorderImplementation(final DeserializedMemberScope deserializedMemberScope, List<ProtoBuf.Function> functionList, List<ProtoBuf.Property> propertyList, List<ProtoBuf.TypeAlias> typeAliasList) {
            Intrinsics.checkNotNullParameter(functionList, "functionList");
            Intrinsics.checkNotNullParameter(propertyList, "propertyList");
            Intrinsics.checkNotNullParameter(typeAliasList, "typeAliasList");
            this.this$0 = deserializedMemberScope;
            this.functionList = functionList;
            this.propertyList = propertyList;
            this.typeAliasList = deserializedMemberScope.getC().getComponents().getConfiguration().getTypeAliasesAllowed() ? typeAliasList : CollectionsKt.emptyList();
            this.declaredFunctions = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<List<? extends SimpleFunctionDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$declaredFunctions$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends SimpleFunctionDescriptor> invoke() {
                    return this.this$0.computeFunctions();
                }
            });
            this.declaredProperties = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<List<? extends PropertyDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$declaredProperties$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends PropertyDescriptor> invoke() {
                    return this.this$0.computeProperties();
                }
            });
            this.allTypeAliases = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<List<? extends TypeAliasDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$allTypeAliases$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends TypeAliasDescriptor> invoke() {
                    return this.this$0.computeTypeAliases();
                }
            });
            this.allFunctions = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<List<? extends SimpleFunctionDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$allFunctions$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends SimpleFunctionDescriptor> invoke() {
                    return CollectionsKt.plus((Collection) this.this$0.getDeclaredFunctions(), (Iterable) this.this$0.computeAllNonDeclaredFunctions());
                }
            });
            this.allProperties = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<List<? extends PropertyDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$allProperties$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends PropertyDescriptor> invoke() {
                    return CollectionsKt.plus((Collection) this.this$0.getDeclaredProperties(), (Iterable) this.this$0.computeAllNonDeclaredProperties());
                }
            });
            this.typeAliasesByName = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<Map<Name, ? extends TypeAliasDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$typeAliasesByName$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Map<Name, ? extends TypeAliasDescriptor> invoke() {
                    List allTypeAliases = this.this$0.getAllTypeAliases();
                    LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(allTypeAliases, 10)), 16));
                    for (Object obj : allTypeAliases) {
                        Name name2 = ((TypeAliasDescriptor) obj).getShortName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        linkedHashMap.put(name2, obj);
                    }
                    return linkedHashMap;
                }
            });
            this.functionsByName = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<Map<Name, ? extends List<? extends SimpleFunctionDescriptor>>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$functionsByName$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Map<Name, ? extends List<? extends SimpleFunctionDescriptor>> invoke() {
                    List allFunctions = this.this$0.getAllFunctions();
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Object obj : allFunctions) {
                        Name name2 = ((SimpleFunctionDescriptor) obj).getShortName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        Object obj2 = linkedHashMap.get(name2);
                        if (obj2 == null) {
                            obj2 = (List) new ArrayList();
                            linkedHashMap.put(name2, obj2);
                        }
                        ((List) obj2).add(obj);
                    }
                    return linkedHashMap;
                }
            });
            this.propertiesByName = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<Map<Name, ? extends List<? extends PropertyDescriptor>>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$propertiesByName$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Map<Name, ? extends List<? extends PropertyDescriptor>> invoke() {
                    List allProperties = this.this$0.getAllProperties();
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Object obj : allProperties) {
                        Name name2 = ((PropertyDescriptor) obj).getShortName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        Object obj2 = linkedHashMap.get(name2);
                        if (obj2 == null) {
                            obj2 = (List) new ArrayList();
                            linkedHashMap.put(name2, obj2);
                        }
                        ((List) obj2).add(obj);
                    }
                    return linkedHashMap;
                }
            });
            this.functionNames = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$functionNames$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Set<? extends Name> invoke() {
                    DeserializedMemberScope.NoReorderImplementation noReorderImplementation = this.this$0;
                    List list = noReorderImplementation.functionList;
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    DeserializedMemberScope deserializedMemberScope2 = noReorderImplementation.this$0;
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        linkedHashSet.add(NameResolverUtilKt.getName(deserializedMemberScope2.getC().getNameResolver(), ((ProtoBuf.Function) ((MessageLite) it.next())).getName()));
                    }
                    return SetsKt.plus((Set) linkedHashSet, (Iterable) deserializedMemberScope.getNonDeclaredFunctionNames());
                }
            });
            this.variableNames = deserializedMemberScope.getC().getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope$NoReorderImplementation$variableNames$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Set<? extends Name> invoke() {
                    DeserializedMemberScope.NoReorderImplementation noReorderImplementation = this.this$0;
                    List list = noReorderImplementation.propertyList;
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    DeserializedMemberScope deserializedMemberScope2 = noReorderImplementation.this$0;
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        linkedHashSet.add(NameResolverUtilKt.getName(deserializedMemberScope2.getC().getNameResolver(), ((ProtoBuf.Property) ((MessageLite) it.next())).getName()));
                    }
                    return SetsKt.plus((Set) linkedHashSet, (Iterable) deserializedMemberScope.getNonDeclaredVariableNames());
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<SimpleFunctionDescriptor> getDeclaredFunctions() {
            return (List) StorageKt.getValue(this.declaredFunctions, this, (KProperty<?>) $$delegatedProperties[0]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<PropertyDescriptor> getDeclaredProperties() {
            return (List) StorageKt.getValue(this.declaredProperties, this, (KProperty<?>) $$delegatedProperties[1]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<TypeAliasDescriptor> getAllTypeAliases() {
            return (List) StorageKt.getValue(this.allTypeAliases, this, (KProperty<?>) $$delegatedProperties[2]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<SimpleFunctionDescriptor> getAllFunctions() {
            return (List) StorageKt.getValue(this.allFunctions, this, (KProperty<?>) $$delegatedProperties[3]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<PropertyDescriptor> getAllProperties() {
            return (List) StorageKt.getValue(this.allProperties, this, (KProperty<?>) $$delegatedProperties[4]);
        }

        private final Map<Name, TypeAliasDescriptor> getTypeAliasesByName() {
            return (Map) StorageKt.getValue(this.typeAliasesByName, this, (KProperty<?>) $$delegatedProperties[5]);
        }

        private final Map<Name, Collection<SimpleFunctionDescriptor>> getFunctionsByName() {
            return (Map) StorageKt.getValue(this.functionsByName, this, (KProperty<?>) $$delegatedProperties[6]);
        }

        private final Map<Name, Collection<PropertyDescriptor>> getPropertiesByName() {
            return (Map) StorageKt.getValue(this.propertiesByName, this, (KProperty<?>) $$delegatedProperties[7]);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Set<Name> getFunctionNames() {
            return (Set) StorageKt.getValue(this.functionNames, this, (KProperty<?>) $$delegatedProperties[8]);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Set<Name> getVariableNames() {
            return (Set) StorageKt.getValue(this.variableNames, this, (KProperty<?>) $$delegatedProperties[9]);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Set<Name> getTypeAliasNames() {
            List<ProtoBuf.TypeAlias> list = this.typeAliasList;
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            DeserializedMemberScope deserializedMemberScope = this.this$0;
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                linkedHashSet.add(NameResolverUtilKt.getName(deserializedMemberScope.getC().getNameResolver(), ((ProtoBuf.TypeAlias) ((MessageLite) it.next())).getName()));
            }
            return linkedHashSet;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<SimpleFunctionDescriptor> computeFunctions() {
            List<ProtoBuf.Function> list = this.functionList;
            DeserializedMemberScope deserializedMemberScope = this.this$0;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                SimpleFunctionDescriptor simpleFunctionDescriptorLoadFunction = deserializedMemberScope.getC().getMemberDeserializer().loadFunction((ProtoBuf.Function) ((MessageLite) it.next()));
                if (!deserializedMemberScope.isDeclaredFunctionAvailable(simpleFunctionDescriptorLoadFunction)) {
                    simpleFunctionDescriptorLoadFunction = null;
                }
                SimpleFunctionDescriptor simpleFunctionDescriptor = simpleFunctionDescriptorLoadFunction;
                if (simpleFunctionDescriptor != null) {
                    arrayList.add(simpleFunctionDescriptor);
                }
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<PropertyDescriptor> computeProperties() {
            List<ProtoBuf.Property> list = this.propertyList;
            DeserializedMemberScope deserializedMemberScope = this.this$0;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                PropertyDescriptor propertyDescriptorLoadProperty = deserializedMemberScope.getC().getMemberDeserializer().loadProperty((ProtoBuf.Property) ((MessageLite) it.next()));
                if (propertyDescriptorLoadProperty != null) {
                    arrayList.add(propertyDescriptorLoadProperty);
                }
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<TypeAliasDescriptor> computeTypeAliases() {
            List<ProtoBuf.TypeAlias> list = this.typeAliasList;
            DeserializedMemberScope deserializedMemberScope = this.this$0;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                TypeAliasDescriptor typeAliasDescriptorLoadTypeAlias = deserializedMemberScope.getC().getMemberDeserializer().loadTypeAlias((ProtoBuf.TypeAlias) ((MessageLite) it.next()));
                if (typeAliasDescriptorLoadTypeAlias != null) {
                    arrayList.add(typeAliasDescriptorLoadTypeAlias);
                }
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<SimpleFunctionDescriptor> computeAllNonDeclaredFunctions() {
            Set<Name> nonDeclaredFunctionNames = this.this$0.getNonDeclaredFunctionNames();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = nonDeclaredFunctionNames.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, computeNonDeclaredFunctionsForName((Name) it.next()));
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<PropertyDescriptor> computeAllNonDeclaredProperties() {
            Set<Name> nonDeclaredVariableNames = this.this$0.getNonDeclaredVariableNames();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = nonDeclaredVariableNames.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, computeNonDeclaredPropertiesForName((Name) it.next()));
            }
            return arrayList;
        }

        private final List<SimpleFunctionDescriptor> computeNonDeclaredFunctionsForName(Name name2) {
            List<SimpleFunctionDescriptor> declaredFunctions = getDeclaredFunctions();
            DeserializedMemberScope deserializedMemberScope = this.this$0;
            ArrayList arrayList = new ArrayList();
            for (Object obj : declaredFunctions) {
                if (Intrinsics.areEqual(((DeclarationDescriptor) obj).getShortName(), name2)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            deserializedMemberScope.computeNonDeclaredFunctions(name2, arrayList2);
            return arrayList2.subList(size, arrayList2.size());
        }

        private final List<PropertyDescriptor> computeNonDeclaredPropertiesForName(Name name2) {
            List<PropertyDescriptor> declaredProperties = getDeclaredProperties();
            DeserializedMemberScope deserializedMemberScope = this.this$0;
            ArrayList arrayList = new ArrayList();
            for (Object obj : declaredProperties) {
                if (Intrinsics.areEqual(((DeclarationDescriptor) obj).getShortName(), name2)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            deserializedMemberScope.computeNonDeclaredProperties(name2, arrayList2);
            return arrayList2.subList(size, arrayList2.size());
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
            Collection<SimpleFunctionDescriptor> collection;
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return (getFunctionNames().contains(name2) && (collection = getFunctionsByName().get(name2)) != null) ? collection : CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public TypeAliasDescriptor getTypeAliasByName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return getTypeAliasesByName().get(name2);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
            Collection<PropertyDescriptor> collection;
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return (getVariableNames().contains(name2) && (collection = getPropertiesByName().get(name2)) != null) ? collection : CollectionsKt.emptyList();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope.Implementation
        public void addFunctionsAndPropertiesTo(Collection<DeclarationDescriptor> result, DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, LookupLocation location) {
            Intrinsics.checkNotNullParameter(result, "result");
            Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            Intrinsics.checkNotNullParameter(location, "location");
            if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getVARIABLES_MASK())) {
                for (Object obj : getAllProperties()) {
                    Name name2 = ((PropertyDescriptor) obj).getShortName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    if (nameFilter.invoke(name2).booleanValue()) {
                        result.add(obj);
                    }
                }
            }
            if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getFUNCTIONS_MASK())) {
                for (Object obj2 : getAllFunctions()) {
                    Name name3 = ((SimpleFunctionDescriptor) obj2).getShortName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    if (nameFilter.invoke(name3).booleanValue()) {
                        result.add(obj2);
                    }
                }
            }
        }
    }
}
