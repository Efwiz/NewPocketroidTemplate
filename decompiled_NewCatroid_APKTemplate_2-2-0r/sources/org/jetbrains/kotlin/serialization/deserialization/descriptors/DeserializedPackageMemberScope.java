package org.jetbrains.kotlin.serialization.deserialization.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory;
import org.jetbrains.kotlin.incremental.UtilsKt;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirementTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationContext;

/* JADX INFO: compiled from: DeserializedPackageMemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0011¢\u0006\u0002\u0010\u0014J*\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001e0\u001dH\u0014J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0013H\u0014J\u001a\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010!\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%H\u0016J*\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001b0'2\u0006\u0010(\u001a\u00020)2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\u0010\u0010*\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010+H\u0014J\u000e\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00130+H\u0014J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00130+H\u0014J\u0010\u0010.\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0013H\u0014J\u0018\u0010/\u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%H\u0016J\b\u00100\u001a\u00020\u000fH\u0016R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedPackageMemberScope;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope;", "packageDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "components", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "debugName", "", "classNames", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "addEnumEntryDescriptors", "", "result", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "nameFilter", "Lkotlin/Function1;", "", "createClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "name", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "getNonDeclaredClassifierNames", "", "getNonDeclaredFunctionNames", "getNonDeclaredVariableNames", "hasClass", "recordLookup", "toString", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DeserializedPackageMemberScope extends DeserializedMemberScope {
    private final String debugName;
    private final PackageFragmentDescriptor packageDescriptor;
    private final FqName packageFqName;

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
    protected void addEnumEntryDescriptors(Collection<DeclarationDescriptor> result, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public /* bridge */ /* synthetic */ Collection getContributedDescriptors(DescriptorKindFilter descriptorKindFilter, Function1 function1) {
        return getContributedDescriptors(descriptorKindFilter, (Function1<? super Name, Boolean>) function1);
    }

    public DeserializedPackageMemberScope(PackageFragmentDescriptor packageDescriptor, ProtoBuf.Package proto, NameResolver nameResolver, BinaryVersion metadataVersion, DeserializedContainerSource deserializedContainerSource, DeserializationComponents components, String debugName, Function0<? extends Collection<Name>> classNames) {
        Intrinsics.checkNotNullParameter(packageDescriptor, "packageDescriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(classNames, "classNames");
        ProtoBuf.TypeTable typeTable = proto.getTypeTable();
        Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
        TypeTable typeTable2 = new TypeTable(typeTable);
        VersionRequirementTable.Companion companion = VersionRequirementTable.INSTANCE;
        ProtoBuf.VersionRequirementTable versionRequirementTable = proto.getVersionRequirementTable();
        Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
        DeserializationContext deserializationContextCreateContext = components.createContext(packageDescriptor, nameResolver, typeTable2, companion.create(versionRequirementTable), metadataVersion, deserializedContainerSource);
        List<ProtoBuf.Function> functionList = proto.getFunctionList();
        Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
        List<ProtoBuf.Property> propertyList = proto.getPropertyList();
        Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
        List<ProtoBuf.TypeAlias> typeAliasList = proto.getTypeAliasList();
        Intrinsics.checkNotNullExpressionValue(typeAliasList, "getTypeAliasList(...)");
        super(deserializationContextCreateContext, functionList, propertyList, typeAliasList, classNames);
        this.packageDescriptor = packageDescriptor;
        this.debugName = debugName;
        this.packageFqName = packageDescriptor.getFqName();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public List<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Collection<DeclarationDescriptor> collectionComputeDescriptors = computeDescriptors(kindFilter, nameFilter, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
        Iterable<ClassDescriptorFactory> fictitiousClassDescriptorFactories = getC().getComponents().getFictitiousClassDescriptorFactories();
        ArrayList arrayList = new ArrayList();
        Iterator<ClassDescriptorFactory> it = fictitiousClassDescriptorFactories.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, it.next().getAllContributedClassesIfPossible(this.packageFqName));
        }
        return CollectionsKt.plus((Collection) collectionComputeDescriptors, (Iterable) arrayList);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
    protected boolean hasClass(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (!super.hasClass(name2)) {
            Iterable<ClassDescriptorFactory> fictitiousClassDescriptorFactories = getC().getComponents().getFictitiousClassDescriptorFactories();
            if (!(fictitiousClassDescriptorFactories instanceof Collection) || !((Collection) fictitiousClassDescriptorFactories).isEmpty()) {
                Iterator<ClassDescriptorFactory> it = fictitiousClassDescriptorFactories.iterator();
                while (it.hasNext()) {
                    if (it.next().shouldCreateClass(this.packageFqName, name2)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
    protected ClassId createClassId(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return new ClassId(this.packageFqName, name2);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo7479recordLookup(name2, location);
        return super.mo7465getContributedClassifier(name2, location);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        UtilsKt.record(getC().getComponents().getLookupTracker(), location, this.packageDescriptor, name2);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
    protected Set<Name> getNonDeclaredFunctionNames() {
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
    protected Set<Name> getNonDeclaredVariableNames() {
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
    protected Set<Name> getNonDeclaredClassifierNames() {
        return SetsKt.emptySet();
    }

    /* JADX INFO: renamed from: toString, reason: from getter */
    public String getDebugName() {
        return this.debugName;
    }
}
