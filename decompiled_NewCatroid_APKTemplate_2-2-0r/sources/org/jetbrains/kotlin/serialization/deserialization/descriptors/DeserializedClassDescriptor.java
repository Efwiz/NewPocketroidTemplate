package org.jetbrains.kotlin.serialization.deserialization.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.DeserializedDeclarationsFromSupertypeConflictDataKey;
import org.jetbrains.kotlin.descriptors.DeserializedDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScopesHolderForClass;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ClassConstructorDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.EnumEntrySyntheticClassDescriptor;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ReceiverParameterDescriptorImpl;
import org.jetbrains.kotlin.incremental.UtilsKt;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirementTable;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.CliSealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.NonReportingOverrideStrategy;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.StaticScopeForKotlinEnum;
import org.jetbrains.kotlin.resolve.scopes.receivers.ContextClassReceiver;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationContext;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.serialization.deserialization.MemberDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlagsUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.TypeDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.ValueClassUtilKt;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.AbstractClassTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: DeserializedClassDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002:\u0003stuB-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\n\u0010D\u001a\u0004\u0018\u00010\u001bH\u0002J\u000e\u0010E\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0002J\n\u0010F\u001a\u0004\u0018\u00010\u001fH\u0002J\u000e\u0010G\u001a\b\u0012\u0004\u0012\u00020\u001f0>H\u0002J\u000e\u0010H\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001eH\u0002J\u0010\u0010I\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010;H\u0002J\n\u0010J\u001a\u0004\u0018\u00010\u001bH\u0016J\u000e\u0010K\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J\b\u0010L\u001a\u00020!H\u0016J\u000e\u0010M\u001a\b\u0012\u0004\u0012\u00020N0>H\u0016J\u000e\u0010O\u001a\b\u0012\u0004\u0012\u00020P0>H\u0016J\n\u0010Q\u001a\u0004\u0018\u00010<H\u0016J\b\u0010R\u001a\u00020%H\u0016J\b\u0010S\u001a\u00020/H\u0016J\u000e\u0010T\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001eH\u0016J\b\u0010U\u001a\u00020\fH\u0016J\b\u0010V\u001a\u000203H\u0016J\b\u0010W\u001a\u00020XH\u0016J\u0010\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020\\H\u0014J\n\u0010]\u001a\u0004\u0018\u00010\u001fH\u0016J\u0012\u0010^\u001a\u0004\u0018\u00010<2\u0006\u0010_\u001a\u00020`H\u0002J\u0010\u0010a\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010;H\u0016J\b\u0010b\u001a\u00020CH\u0016J\u0015\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020`H\u0000¢\u0006\u0002\bfJ\b\u0010g\u001a\u00020dH\u0016J\b\u0010h\u001a\u00020dH\u0016J\b\u0010i\u001a\u00020dH\u0016J\b\u0010j\u001a\u00020dH\u0016J\b\u0010k\u001a\u00020dH\u0016J\b\u0010l\u001a\u00020dH\u0016J\b\u0010m\u001a\u00020dH\u0016J\b\u0010n\u001a\u00020dH\u0016J\b\u0010o\u001a\u00020dH\u0016J\b\u0010p\u001a\u00020dH\u0016J\b\u0010q\u001a\u00020rH\u0016R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0018\u00010#R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010&\u001a\u00060'R\u00020\u00008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0018\u0010*\u001a\f\u0012\b\u0012\u00060'R\u00020\u00000+X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020/X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00100\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001e0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00104\u001a\u000205X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u0012\u00108\u001a\u000609R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020<0;0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010=\u001a\b\u0012\u0004\u0012\u00020?0>8F¢\u0006\u0006\u001a\u0004\b@\u0010AR\u000e\u0010B\u001a\u00020CX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006v"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/AbstractClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeserializedDescriptor;", "outerContext", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationContext;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "sourceElement", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationContext;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "c", "getC", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationContext;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "companionObjectDescriptor", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "constructors", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "enumEntries", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor$EnumEntryClassDescriptors;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "memberScope", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor$DeserializedClassMemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor$DeserializedClassMemberScope;", "memberScopeHolder", "Lorg/jetbrains/kotlin/descriptors/ScopesHolderForClass;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "primaryConstructor", "sealedSubclasses", "staticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "thisAsProtoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "getThisAsProtoContainer$deserialization", "()Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "typeConstructor", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor$DeserializedClassTypeConstructor;", "valueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/types/SimpleType;", "versionRequirements", "", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "getVersionRequirements", "()Ljava/util/List;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "computeCompanionObjectDescriptor", "computeConstructors", "computePrimaryConstructor", "computeSecondaryConstructors", "computeSubclassesForSealedClass", "computeValueClassRepresentation", "getCompanionObjectDescriptor", "getConstructors", "getContainingDeclaration", "getContextReceivers", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDeclaredTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getDefaultFunctionTypeForSamInterface", "getKind", "getModality", "getSealedSubclasses", "getSource", "getStaticScope", "getTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getUnsubstitutedMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getUnsubstitutedPrimaryConstructor", "getValueClassPropertyType", "propertyName", "Lorg/jetbrains/kotlin/name/Name;", "getValueClassRepresentation", "getVisibility", "hasNestedClass", "", "name", "hasNestedClass$deserialization", "isActual", "isCompanionObject", "isData", "isDefinitelyNotSamInterface", "isExpect", "isExternal", "isFun", "isInline", "isInner", "isValue", "toString", "", "DeserializedClassMemberScope", "DeserializedClassTypeConstructor", "EnumEntryClassDescriptors", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeserializedClassDescriptor extends AbstractClassDescriptor implements DeserializedDescriptor {
    private final Annotations annotations;
    private final DeserializationContext c;
    private final ClassId classId;
    private final ProtoBuf.Class classProto;
    private final NullableLazyValue<ClassDescriptor> companionObjectDescriptor;
    private final NotNullLazyValue<Collection<ClassConstructorDescriptor>> constructors;
    private final DeclarationDescriptor containingDeclaration;
    private final EnumEntryClassDescriptors enumEntries;
    private final ClassKind kind;
    private final ScopesHolderForClass<DeserializedClassMemberScope> memberScopeHolder;
    private final BinaryVersion metadataVersion;
    private final Modality modality;
    private final NullableLazyValue<ClassConstructorDescriptor> primaryConstructor;
    private final NotNullLazyValue<Collection<ClassDescriptor>> sealedSubclasses;
    private final SourceElement sourceElement;
    private final MemberScopeImpl staticScope;
    private final ProtoContainer.Class thisAsProtoContainer;
    private final DeserializedClassTypeConstructor typeConstructor;
    private final NullableLazyValue<ValueClassRepresentation<SimpleType>> valueClassRepresentation;
    private final DescriptorVisibility visibility;

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return false;
    }

    public final ProtoBuf.Class getClassProto() {
        return this.classProto;
    }

    public final BinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeserializedClassDescriptor(DeserializationContext outerContext, ProtoBuf.Class classProto, NameResolver nameResolver, BinaryVersion metadataVersion, SourceElement sourceElement) {
        MemberScope.Empty staticScopeForKotlinEnum;
        NonEmptyDeserializedAnnotations nonEmptyDeserializedAnnotations;
        super(outerContext.getStorageManager(), NameResolverUtilKt.getClassId(nameResolver, classProto.getFqName()).getShortClassName());
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
        this.classProto = classProto;
        this.metadataVersion = metadataVersion;
        this.sourceElement = sourceElement;
        this.classId = NameResolverUtilKt.getClassId(nameResolver, classProto.getFqName());
        this.modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(classProto.getFlags()));
        this.visibility = ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(classProto.getFlags()));
        ClassKind classKind = ProtoEnumFlags.INSTANCE.classKind(Flags.CLASS_KIND.get(classProto.getFlags()));
        this.kind = classKind;
        List<ProtoBuf.TypeParameter> typeParameterList = classProto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        ProtoBuf.TypeTable typeTable = classProto.getTypeTable();
        Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
        TypeTable typeTable2 = new TypeTable(typeTable);
        VersionRequirementTable.Companion companion = VersionRequirementTable.INSTANCE;
        ProtoBuf.VersionRequirementTable versionRequirementTable = classProto.getVersionRequirementTable();
        Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
        DeserializationContext deserializationContextChildContext = outerContext.childContext(this, typeParameterList, nameResolver, typeTable2, companion.create(versionRequirementTable), metadataVersion);
        this.c = deserializationContextChildContext;
        if (classKind == ClassKind.ENUM_CLASS) {
            Boolean bool = Flags.HAS_ENUM_ENTRIES.get(classProto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            boolean z = true;
            if (!bool.booleanValue() && !Intrinsics.areEqual((Object) deserializationContextChildContext.getComponents().getEnumEntriesDeserializationSupport().canSynthesizeEnumEntries(), (Object) true)) {
                z = false;
            }
            staticScopeForKotlinEnum = new StaticScopeForKotlinEnum(deserializationContextChildContext.getStorageManager(), this, z);
        } else {
            staticScopeForKotlinEnum = MemberScope.Empty.INSTANCE;
        }
        this.staticScope = staticScopeForKotlinEnum;
        this.typeConstructor = new DeserializedClassTypeConstructor();
        this.memberScopeHolder = ScopesHolderForClass.INSTANCE.create(this, deserializationContextChildContext.getStorageManager(), deserializationContextChildContext.getComponents().getKotlinTypeChecker().getKotlinTypeRefiner(), new DeserializedClassDescriptor$memberScopeHolder$1(this));
        this.enumEntries = classKind == ClassKind.ENUM_CLASS ? new EnumEntryClassDescriptors() : null;
        DeclarationDescriptor containingDeclaration = outerContext.getContainingDeclaration();
        this.containingDeclaration = containingDeclaration;
        this.primaryConstructor = deserializationContextChildContext.getStorageManager().createNullableLazyValue(new Function0<ClassConstructorDescriptor>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$primaryConstructor$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassConstructorDescriptor invoke() {
                return this.this$0.computePrimaryConstructor();
            }
        });
        this.constructors = deserializationContextChildContext.getStorageManager().createLazyValue(new Function0<Collection<? extends ClassConstructorDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$constructors$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends ClassConstructorDescriptor> invoke() {
                return this.this$0.computeConstructors();
            }
        });
        this.companionObjectDescriptor = deserializationContextChildContext.getStorageManager().createNullableLazyValue(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$companionObjectDescriptor$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptor invoke() {
                return this.this$0.computeCompanionObjectDescriptor();
            }
        });
        this.sealedSubclasses = deserializationContextChildContext.getStorageManager().createLazyValue(new Function0<Collection<? extends ClassDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$sealedSubclasses$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends ClassDescriptor> invoke() {
                return this.this$0.computeSubclassesForSealedClass();
            }
        });
        this.valueClassRepresentation = deserializationContextChildContext.getStorageManager().createNullableLazyValue(new Function0<ValueClassRepresentation<SimpleType>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$valueClassRepresentation$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ValueClassRepresentation<SimpleType> invoke() {
                return this.this$0.computeValueClassRepresentation();
            }
        });
        NameResolver nameResolver2 = deserializationContextChildContext.getNameResolver();
        TypeTable typeTable3 = deserializationContextChildContext.getTypeTable();
        DeserializedClassDescriptor deserializedClassDescriptor = containingDeclaration instanceof DeserializedClassDescriptor ? (DeserializedClassDescriptor) containingDeclaration : null;
        this.thisAsProtoContainer = new ProtoContainer.Class(classProto, nameResolver2, typeTable3, sourceElement, deserializedClassDescriptor != null ? deserializedClassDescriptor.thisAsProtoContainer : null);
        if (!Flags.HAS_ANNOTATIONS.get(classProto.getFlags()).booleanValue()) {
            nonEmptyDeserializedAnnotations = Annotations.INSTANCE.getEMPTY();
        } else {
            nonEmptyDeserializedAnnotations = new NonEmptyDeserializedAnnotations(deserializationContextChildContext.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$annotations$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends AnnotationDescriptor> invoke() {
                    return CollectionsKt.toList(this.this$0.getC().getComponents().getAnnotationAndConstantLoader().loadClassAnnotations(this.this$0.getThisAsProtoContainer()));
                }
            });
        }
        this.annotations = nonEmptyDeserializedAnnotations;
    }

    public final DeserializationContext getC() {
        return this.c;
    }

    private final DeserializedClassMemberScope getMemberScope() {
        return (DeserializedClassMemberScope) this.memberScopeHolder.getScope(this.c.getComponents().getKotlinTypeChecker().getKotlinTypeRefiner());
    }

    /* JADX INFO: renamed from: getThisAsProtoContainer$deserialization, reason: from getter */
    public final ProtoContainer.Class getThisAsProtoContainer() {
        return this.thisAsProtoContainer;
    }

    public final List<VersionRequirement> getVersionRequirements() {
        return VersionRequirement.INSTANCE.create(this.classProto, this.c.getNameResolver(), this.c.getVersionRequirementTable());
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getContainingDeclaration() {
        return this.containingDeclaration;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ClassKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return this.modality;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        return this.visibility;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    /* JADX INFO: renamed from: isInner */
    public boolean getIsInner() {
        Boolean bool = Flags.IS_INNER.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isData() {
        Boolean bool = Flags.IS_DATA.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isInline() {
        Boolean bool = Flags.IS_VALUE_CLASS.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue() && this.metadataVersion.isAtMost(1, 4, 1);
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        Boolean bool = Flags.IS_EXPECT_CLASS.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        Boolean bool = Flags.IS_EXTERNAL_CLASS.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isFun() {
        Boolean bool = Flags.IS_FUN_INTERFACE.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isValue() {
        Boolean bool = Flags.IS_VALUE_CLASS.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue() && this.metadataVersion.isAtLeast(1, 4, 2);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
    protected MemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this.memberScopeHolder.getScope(kotlinTypeRefiner);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScopeImpl getStaticScope() {
        return this.staticScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isCompanionObject() {
        return Flags.CLASS_KIND.get(this.classProto.getFlags()) == ProtoBuf.Class.Kind.COMPANION_OBJECT;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassConstructorDescriptor computePrimaryConstructor() {
        Object next;
        if (this.kind.isSingleton()) {
            ClassConstructorDescriptorImpl classConstructorDescriptorImplCreatePrimaryConstructorForObject = DescriptorFactory.createPrimaryConstructorForObject(this, SourceElement.NO_SOURCE);
            classConstructorDescriptorImplCreatePrimaryConstructorForObject.setReturnType(getDefaultType());
            return classConstructorDescriptorImplCreatePrimaryConstructorForObject;
        }
        List<ProtoBuf.Constructor> constructorList = this.classProto.getConstructorList();
        Intrinsics.checkNotNullExpressionValue(constructorList, "getConstructorList(...)");
        Iterator<T> it = constructorList.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (Flags.IS_SECONDARY.get(((ProtoBuf.Constructor) next).getFlags()).booleanValue());
        ProtoBuf.Constructor constructor = (ProtoBuf.Constructor) next;
        if (constructor != null) {
            return this.c.getMemberDeserializer().loadConstructor(constructor, true);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    public ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
        return this.primaryConstructor.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<ClassConstructorDescriptor> computeConstructors() {
        return CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) computeSecondaryConstructors(), (Iterable) CollectionsKt.listOfNotNull(mo6347getUnsubstitutedPrimaryConstructor())), (Iterable) this.c.getComponents().getAdditionalClassPartsProvider().getConstructors(this));
    }

    private final List<ClassConstructorDescriptor> computeSecondaryConstructors() {
        List<ProtoBuf.Constructor> constructorList = this.classProto.getConstructorList();
        Intrinsics.checkNotNullExpressionValue(constructorList, "getConstructorList(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : constructorList) {
            Boolean bool = Flags.IS_SECONDARY.get(((ProtoBuf.Constructor) obj).getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            if (bool.booleanValue()) {
                arrayList.add(obj);
            }
        }
        ArrayList<ProtoBuf.Constructor> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (ProtoBuf.Constructor constructor : arrayList2) {
            MemberDeserializer memberDeserializer = this.c.getMemberDeserializer();
            Intrinsics.checkNotNull(constructor);
            arrayList3.add(memberDeserializer.loadConstructor(constructor, false));
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassConstructorDescriptor> getConstructors() {
        return this.constructors.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ReceiverParameterDescriptor> getContextReceivers() {
        List<ProtoBuf.Type> listContextReceiverTypes = ProtoTypeTableUtilKt.contextReceiverTypes(this.classProto, this.c.getTypeTable());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listContextReceiverTypes, 10));
        Iterator<T> it = listContextReceiverTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(new ReceiverParameterDescriptorImpl(getThisAsReceiverParameter(), new ContextClassReceiver(this, this.c.getTypeDeserializer().type((ProtoBuf.Type) it.next()), null, null), Annotations.INSTANCE.getEMPTY()));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor computeCompanionObjectDescriptor() {
        if (!this.classProto.hasCompanionObjectName()) {
            return null;
        }
        ClassifierDescriptor classifierDescriptorMo7465getContributedClassifier = getMemberScope().mo7465getContributedClassifier(NameResolverUtilKt.getName(this.c.getNameResolver(), this.classProto.getCompanionObjectName()), NoLookupLocation.FROM_DESERIALIZATION);
        if (classifierDescriptorMo7465getContributedClassifier instanceof ClassDescriptor) {
            return (ClassDescriptor) classifierDescriptorMo7465getContributedClassifier;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor */
    public ClassDescriptor mo6346getCompanionObjectDescriptor() {
        return this.companionObjectDescriptor.invoke();
    }

    public final boolean hasNestedClass$deserialization(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getMemberScope().getClassNames$deserialization().contains(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<ClassDescriptor> computeSubclassesForSealedClass() {
        if (this.modality != Modality.SEALED) {
            return CollectionsKt.emptyList();
        }
        List<Integer> sealedSubclassFqNameList = this.classProto.getSealedSubclassFqNameList();
        Intrinsics.checkNotNull(sealedSubclassFqNameList);
        if (sealedSubclassFqNameList.isEmpty()) {
            return CliSealedClassInheritorsProvider.INSTANCE.computeSealedSubclasses(this, false);
        }
        ArrayList arrayList = new ArrayList();
        for (Integer num : sealedSubclassFqNameList) {
            DeserializationComponents components = this.c.getComponents();
            NameResolver nameResolver = this.c.getNameResolver();
            Intrinsics.checkNotNull(num);
            ClassDescriptor classDescriptorDeserializeClass = components.deserializeClass(NameResolverUtilKt.getClassId(nameResolver, num.intValue()));
            if (classDescriptorDeserializeClass != null) {
                arrayList.add(classDescriptorDeserializeClass);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassDescriptor> getSealedSubclasses() {
        return this.sealedSubclasses.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
        return this.valueClassRepresentation.invoke();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$computeValueClassRepresentation$1, reason: invalid class name */
    /* JADX INFO: compiled from: DeserializedClassDescriptor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<ProtoBuf.Type, SimpleType> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "computeValueClassRepresentation$simpleType(Lorg/jetbrains/kotlin/serialization/deserialization/TypeDeserializer;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;)Lorg/jetbrains/kotlin/types/SimpleType;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "simpleType";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final SimpleType invoke(ProtoBuf.Type p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return TypeDeserializer.simpleType$default((TypeDeserializer) this.receiver, p0, false, 2, null);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$computeValueClassRepresentation$2, reason: invalid class name */
    /* JADX INFO: compiled from: DeserializedClassDescriptor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<Name, SimpleType> {
        AnonymousClass2(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getValueClassPropertyType(Lorg/jetbrains/kotlin/name/Name;)Lorg/jetbrains/kotlin/types/SimpleType;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getValueClassPropertyType";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(DeserializedClassDescriptor.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final SimpleType invoke(Name p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((DeserializedClassDescriptor) this.receiver).getValueClassPropertyType(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ValueClassRepresentation<SimpleType> computeValueClassRepresentation() {
        if (!isInline() && !isValue()) {
            return null;
        }
        ValueClassRepresentation<SimpleType> valueClassRepresentationLoadValueClassRepresentation = ValueClassUtilKt.loadValueClassRepresentation(this.classProto, this.c.getNameResolver(), this.c.getTypeTable(), new AnonymousClass1(this.c.getTypeDeserializer()), new AnonymousClass2(this));
        if (valueClassRepresentationLoadValueClassRepresentation != null) {
            return valueClassRepresentationLoadValueClassRepresentation;
        }
        if (this.metadataVersion.isAtLeast(1, 5, 1)) {
            return null;
        }
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            throw new IllegalStateException(("Inline class has no primary constructor: " + this).toString());
        }
        List<ValueParameterDescriptor> valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        Name name2 = ((ValueParameterDescriptor) CollectionsKt.first((List) valueParameters)).getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        SimpleType valueClassPropertyType = getValueClassPropertyType(name2);
        if (valueClassPropertyType == null) {
            throw new IllegalStateException(("Value class has no underlying property: " + this).toString());
        }
        return new InlineClassRepresentation(name2, valueClassPropertyType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SimpleType getValueClassPropertyType(Name propertyName) {
        Iterator<T> it = getMemberScope().getContributedVariables(propertyName, NoLookupLocation.FROM_DESERIALIZATION).iterator();
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
            if (((PropertyDescriptor) next).getExtensionReceiverParameter() == null) {
                if (!z) {
                    z = true;
                    obj = next;
                }
            }
            obj = null;
            break;
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) obj;
        return (SimpleType) (propertyDescriptor != null ? propertyDescriptor.getType() : null);
    }

    public String toString() {
        return "deserialized " + (get$isExpect() ? "expect " : "") + ExternalAnnotationProvider.CLASS_PREFIX + getShortName();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource, reason: from getter */
    public SourceElement getSourceElement() {
        return this.sourceElement;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return this.c.getTypeDeserializer().getOwnTypeParameters();
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public SimpleType getDefaultFunctionTypeForSamInterface() {
        return this.c.getComponents().getSamConversionResolver().resolveFunctionTypeIfSamInterface(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isDefinitelyNotSamInterface() {
        return !isFun();
    }

    /* JADX INFO: compiled from: DeserializedClassDescriptor.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0014J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor$DeserializedClassTypeConstructor;", "Lorg/jetbrains/kotlin/types/AbstractClassTypeConstructor;", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;)V", "parameters", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "computeSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;", "getParameters", "isDenotable", "", "toString", "", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class DeserializedClassTypeConstructor extends AbstractClassTypeConstructor {
        private final NotNullLazyValue<List<TypeParameterDescriptor>> parameters;

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public boolean isDenotable() {
            return true;
        }

        public DeserializedClassTypeConstructor() {
            super(DeserializedClassDescriptor.this.getC().getStorageManager());
            this.parameters = DeserializedClassDescriptor.this.getC().getStorageManager().createLazyValue(new Function0<List<? extends TypeParameterDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassTypeConstructor$parameters$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends TypeParameterDescriptor> invoke() {
                    return TypeParameterUtilsKt.computeConstructorTypeParameters(deserializedClassDescriptor);
                }
            });
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected Collection<KotlinType> computeSupertypes() {
            String strAsString;
            FqName fqNameAsSingleFqName;
            List<ProtoBuf.Type> listSupertypes = ProtoTypeTableUtilKt.supertypes(DeserializedClassDescriptor.this.getClassProto(), DeserializedClassDescriptor.this.getC().getTypeTable());
            DeserializedClassDescriptor deserializedClassDescriptor = DeserializedClassDescriptor.this;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSupertypes, 10));
            Iterator<T> it = listSupertypes.iterator();
            while (it.hasNext()) {
                arrayList.add(deserializedClassDescriptor.getC().getTypeDeserializer().type((ProtoBuf.Type) it.next()));
            }
            List listPlus = CollectionsKt.plus((Collection) arrayList, (Iterable) DeserializedClassDescriptor.this.getC().getComponents().getAdditionalClassPartsProvider().getSupertypes(DeserializedClassDescriptor.this));
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = listPlus.iterator();
            while (it2.hasNext()) {
                ClassifierDescriptor this$0 = ((KotlinType) it2.next()).getConstructor().getThis$0();
                NotFoundClasses.MockClassDescriptor mockClassDescriptor = this$0 instanceof NotFoundClasses.MockClassDescriptor ? (NotFoundClasses.MockClassDescriptor) this$0 : null;
                if (mockClassDescriptor != null) {
                    arrayList2.add(mockClassDescriptor);
                }
            }
            ArrayList arrayList3 = arrayList2;
            if (!arrayList3.isEmpty()) {
                ErrorReporter errorReporter = DeserializedClassDescriptor.this.getC().getComponents().getErrorReporter();
                DeserializedClassDescriptor deserializedClassDescriptor2 = DeserializedClassDescriptor.this;
                ArrayList<NotFoundClasses.MockClassDescriptor> arrayList4 = arrayList3;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
                for (NotFoundClasses.MockClassDescriptor mockClassDescriptor2 : arrayList4) {
                    ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) mockClassDescriptor2);
                    if (classId == null || (fqNameAsSingleFqName = classId.asSingleFqName()) == null || (strAsString = fqNameAsSingleFqName.asString()) == null) {
                        strAsString = mockClassDescriptor2.getShortName().asString();
                    }
                    arrayList5.add(strAsString);
                }
                errorReporter.reportIncompleteHierarchy(deserializedClassDescriptor2, arrayList5);
            }
            return CollectionsKt.toList(listPlus);
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public List<TypeParameterDescriptor> getParameters() {
            return this.parameters.invoke();
        }

        @Override // org.jetbrains.kotlin.types.AbstractClassTypeConstructor, org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor, org.jetbrains.kotlin.types.TypeConstructor
        /* JADX INFO: renamed from: getDeclarationDescriptor, reason: from getter */
        public DeserializedClassDescriptor getThis$0() {
            return DeserializedClassDescriptor.this;
        }

        public String toString() {
            String string = DeserializedClassDescriptor.this.getShortName().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            return SupertypeLoopChecker.EMPTY.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DeserializedClassDescriptor.kt */
    @Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014H\u0014J\u001e\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00152\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0014J\u001e\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00152\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001aH\u0014J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u0018\u001a\u00020\u0015H\u0014J6\u0010!\u001a\u00020\u0010\"\b\b\u0000\u0010\"*\u00020#2\u0006\u0010\u0018\u001a\u00020\u00152\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\"0\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\"0\u001aH\u0002J\u001a\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010'\u001a\u00020(H\u0016J*\u0010)\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010*\u001a\u00020+2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014H\u0016J\u001e\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00072\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010'\u001a\u00020(H\u0016J\u001e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00072\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010'\u001a\u00020(H\u0016J\u0010\u0010.\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010/H\u0014J\u000e\u00100\u001a\b\u0012\u0004\u0012\u00020\u00150/H\u0014J\u000e\u00101\u001a\b\u0012\u0004\u0012\u00020\u00150/H\u0014J\u0010\u00102\u001a\u00020\u00162\u0006\u00103\u001a\u00020\u001bH\u0014J\u0018\u00104\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010'\u001a\u00020(H\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor$DeserializedClassMemberScope;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberScope;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "allDescriptors", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;", "refinedSupertypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "addEnumEntryDescriptors", "", "result", "", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "computeNonDeclaredFunctions", "name", "functions", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "computeNonDeclaredProperties", "descriptors", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "createClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "generateFakeOverrides", "D", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "fromSupertypes", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "getContributedFunctions", "getContributedVariables", "getNonDeclaredClassifierNames", "", "getNonDeclaredFunctionNames", "getNonDeclaredVariableNames", "isDeclaredFunctionAvailable", "function", "recordLookup", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class DeserializedClassMemberScope extends DeserializedMemberScope {
        private final NotNullLazyValue<Collection<DeclarationDescriptor>> allDescriptors;
        private final KotlinTypeRefiner kotlinTypeRefiner;
        private final NotNullLazyValue<Collection<KotlinType>> refinedSupertypes;
        final /* synthetic */ DeserializedClassDescriptor this$0;

        public DeserializedClassMemberScope(DeserializedClassDescriptor deserializedClassDescriptor, KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            this.this$0 = deserializedClassDescriptor;
            DeserializationContext c = deserializedClassDescriptor.getC();
            List<ProtoBuf.Function> functionList = deserializedClassDescriptor.getClassProto().getFunctionList();
            Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
            List<ProtoBuf.Property> propertyList = deserializedClassDescriptor.getClassProto().getPropertyList();
            Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
            List<ProtoBuf.TypeAlias> typeAliasList = deserializedClassDescriptor.getClassProto().getTypeAliasList();
            Intrinsics.checkNotNullExpressionValue(typeAliasList, "getTypeAliasList(...)");
            List<Integer> nestedClassNameList = deserializedClassDescriptor.getClassProto().getNestedClassNameList();
            Intrinsics.checkNotNullExpressionValue(nestedClassNameList, "getNestedClassNameList(...)");
            List<Integer> list = nestedClassNameList;
            NameResolver nameResolver = deserializedClassDescriptor.getC().getNameResolver();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(NameResolverUtilKt.getName(nameResolver, ((Number) it.next()).intValue()));
            }
            final ArrayList arrayList2 = arrayList;
            super(c, functionList, propertyList, typeAliasList, new Function0<List<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassMemberScope$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends Name> invoke() {
                    return arrayList2;
                }
            });
            this.kotlinTypeRefiner = kotlinTypeRefiner;
            this.allDescriptors = getC().getStorageManager().createLazyValue(new Function0<Collection<? extends DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassMemberScope$allDescriptors$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Collection<? extends DeclarationDescriptor> invoke() {
                    return this.this$0.computeDescriptors(DescriptorKindFilter.ALL, MemberScope.INSTANCE.getALL_NAME_FILTER(), NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
                }
            });
            this.refinedSupertypes = getC().getStorageManager().createLazyValue(new Function0<Collection<? extends KotlinType>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassMemberScope$refinedSupertypes$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Collection<? extends KotlinType> invoke() {
                    return this.this$0.kotlinTypeRefiner.refineSupertypes(this.this$0.getThis$0());
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: getClassDescriptor, reason: from getter */
        public final DeserializedClassDescriptor getThis$0() {
            return this.this$0;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
            Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            return this.allDescriptors.invoke();
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            mo7479recordLookup(name2, location);
            return super.getContributedFunctions(name2, location);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            mo7479recordLookup(name2, location);
            return super.getContributedVariables(name2, location);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
        protected boolean isDeclaredFunctionAvailable(SimpleFunctionDescriptor function) {
            Intrinsics.checkNotNullParameter(function, "function");
            return getC().getComponents().getPlatformDependentDeclarationFilter().isFunctionAvailable(this.this$0, function);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
        protected void computeNonDeclaredFunctions(Name name2, List<SimpleFunctionDescriptor> functions) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(functions, "functions");
            ArrayList arrayList = new ArrayList();
            Iterator<KotlinType> it = this.refinedSupertypes.invoke().iterator();
            while (it.hasNext()) {
                arrayList.addAll(it.next().getMemberScope().getContributedFunctions(name2, NoLookupLocation.FOR_ALREADY_TRACKED));
            }
            functions.addAll(getC().getComponents().getAdditionalClassPartsProvider().getFunctions(name2, this.this$0));
            generateFakeOverrides(name2, arrayList, functions);
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
        protected void computeNonDeclaredProperties(Name name2, List<PropertyDescriptor> descriptors) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            ArrayList arrayList = new ArrayList();
            Iterator<KotlinType> it = this.refinedSupertypes.invoke().iterator();
            while (it.hasNext()) {
                arrayList.addAll(it.next().getMemberScope().getContributedVariables(name2, NoLookupLocation.FOR_ALREADY_TRACKED));
            }
            generateFakeOverrides(name2, arrayList, descriptors);
        }

        private final <D extends CallableMemberDescriptor> void generateFakeOverrides(Name name2, Collection<? extends D> fromSupertypes, final List<D> result) {
            getC().getComponents().getKotlinTypeChecker().getOverridingUtil().generateOverridesInFunctionGroup(name2, fromSupertypes, new ArrayList(result), getThis$0(), new NonReportingOverrideStrategy() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassMemberScope$generateFakeOverrides$1
                /* JADX WARN: Type inference incomplete: some casts might be missing */
                @Override // org.jetbrains.kotlin.resolve.OverridingStrategy
                public void addFakeOverride(CallableMemberDescriptor fakeOverride) {
                    Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
                    OverridingUtil.resolveUnknownVisibilityForMember(fakeOverride, null);
                    result.add((D) fakeOverride);
                }

                @Override // org.jetbrains.kotlin.resolve.NonReportingOverrideStrategy
                protected void conflict(CallableMemberDescriptor fromSuper, CallableMemberDescriptor fromCurrent) {
                    Intrinsics.checkNotNullParameter(fromSuper, "fromSuper");
                    Intrinsics.checkNotNullParameter(fromCurrent, "fromCurrent");
                    if (fromCurrent instanceof FunctionDescriptorImpl) {
                        ((FunctionDescriptorImpl) fromCurrent).putInUserDataMap(DeserializedDeclarationsFromSupertypeConflictDataKey.INSTANCE, fromSuper);
                    }
                }
            });
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
        protected Set<Name> getNonDeclaredFunctionNames() {
            List supertypes = getThis$0().typeConstructor.mo7438getSupertypes();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator it = supertypes.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(linkedHashSet, ((KotlinType) it.next()).getMemberScope().getFunctionNames());
            }
            linkedHashSet.addAll(getC().getComponents().getAdditionalClassPartsProvider().getFunctionsNames(this.this$0));
            return linkedHashSet;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
        protected Set<Name> getNonDeclaredVariableNames() {
            List supertypes = getThis$0().typeConstructor.mo7438getSupertypes();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator it = supertypes.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(linkedHashSet, ((KotlinType) it.next()).getMemberScope().getVariableNames());
            }
            return linkedHashSet;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
        protected Set<Name> getNonDeclaredClassifierNames() {
            List supertypes = getThis$0().typeConstructor.mo7438getSupertypes();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator it = supertypes.iterator();
            while (it.hasNext()) {
                Set<Name> classifierNames = ((KotlinType) it.next()).getMemberScope().getClassifierNames();
                if (classifierNames == null) {
                    linkedHashSet = null;
                    break;
                }
                CollectionsKt.addAll(linkedHashSet, classifierNames);
            }
            return linkedHashSet;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        /* JADX INFO: renamed from: getContributedClassifier */
        public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
            ClassDescriptor classDescriptorFindEnumEntry;
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            mo7479recordLookup(name2, location);
            EnumEntryClassDescriptors enumEntryClassDescriptors = getThis$0().enumEntries;
            if (enumEntryClassDescriptors == null || (classDescriptorFindEnumEntry = enumEntryClassDescriptors.findEnumEntry(name2)) == null) {
                return super.mo7465getContributedClassifier(name2, location);
            }
            return classDescriptorFindEnumEntry;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
        protected ClassId createClassId(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            ClassId classIdCreateNestedClassId = this.this$0.classId.createNestedClassId(name2);
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
            return classIdCreateNestedClassId;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
        protected void addEnumEntryDescriptors(Collection<DeclarationDescriptor> result, Function1<? super Name, Boolean> nameFilter) {
            Intrinsics.checkNotNullParameter(result, "result");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            EnumEntryClassDescriptors enumEntryClassDescriptors = getThis$0().enumEntries;
            List listAll = enumEntryClassDescriptors != null ? enumEntryClassDescriptors.all() : null;
            if (listAll == null) {
                listAll = CollectionsKt.emptyList();
            }
            result.addAll(listAll);
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        /* JADX INFO: renamed from: recordLookup */
        public void mo7479recordLookup(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            UtilsKt.record(getC().getComponents().getLookupTracker(), location, getThis$0(), name2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DeserializedClassDescriptor.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u000fJ\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\u0002J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0012\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor$EnumEntryClassDescriptors;", "", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;)V", "enumEntryByName", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "enumEntryProtos", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "enumMemberNames", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", DevModeOverwritingStrategies.ALL, "", "computeEnumMemberNames", "findEnumEntry", "name", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class EnumEntryClassDescriptors {
        private final MemoizedFunctionToNullable<Name, ClassDescriptor> enumEntryByName;
        private final Map<Name, ProtoBuf.EnumEntry> enumEntryProtos;
        private final NotNullLazyValue<Set<Name>> enumMemberNames;

        public EnumEntryClassDescriptors() {
            List<ProtoBuf.EnumEntry> enumEntryList = DeserializedClassDescriptor.this.getClassProto().getEnumEntryList();
            Intrinsics.checkNotNullExpressionValue(enumEntryList, "getEnumEntryList(...)");
            List<ProtoBuf.EnumEntry> list = enumEntryList;
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
            for (Object obj : list) {
                linkedHashMap.put(NameResolverUtilKt.getName(DeserializedClassDescriptor.this.getC().getNameResolver(), ((ProtoBuf.EnumEntry) obj).getName()), obj);
            }
            this.enumEntryProtos = linkedHashMap;
            StorageManager storageManager = DeserializedClassDescriptor.this.getC().getStorageManager();
            final DeserializedClassDescriptor deserializedClassDescriptor = DeserializedClassDescriptor.this;
            this.enumEntryByName = storageManager.createMemoizedFunctionWithNullableValues(new Function1<Name, ClassDescriptor>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$EnumEntryClassDescriptors$enumEntryByName$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ClassDescriptor invoke(Name name2) {
                    EnumEntrySyntheticClassDescriptor enumEntrySyntheticClassDescriptorCreate;
                    Intrinsics.checkNotNullParameter(name2, "name");
                    final ProtoBuf.EnumEntry enumEntry = (ProtoBuf.EnumEntry) this.this$0.enumEntryProtos.get(name2);
                    if (enumEntry != null) {
                        final DeserializedClassDescriptor deserializedClassDescriptor2 = deserializedClassDescriptor;
                        enumEntrySyntheticClassDescriptorCreate = EnumEntrySyntheticClassDescriptor.create(deserializedClassDescriptor2.getC().getStorageManager(), deserializedClassDescriptor2, name2, this.this$0.enumMemberNames, new DeserializedAnnotations(deserializedClassDescriptor2.getC().getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$EnumEntryClassDescriptors$enumEntryByName$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final List<? extends AnnotationDescriptor> invoke() {
                                return CollectionsKt.toList(deserializedClassDescriptor2.getC().getComponents().getAnnotationAndConstantLoader().loadEnumEntryAnnotations(deserializedClassDescriptor2.getThisAsProtoContainer(), enumEntry));
                            }
                        }), SourceElement.NO_SOURCE);
                    } else {
                        enumEntrySyntheticClassDescriptorCreate = null;
                    }
                    return enumEntrySyntheticClassDescriptorCreate;
                }
            });
            this.enumMemberNames = DeserializedClassDescriptor.this.getC().getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor$EnumEntryClassDescriptors$enumMemberNames$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Set<? extends Name> invoke() {
                    return this.this$0.computeEnumMemberNames();
                }
            });
        }

        public final ClassDescriptor findEnumEntry(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return this.enumEntryByName.invoke(name2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Set<Name> computeEnumMemberNames() {
            HashSet hashSet = new HashSet();
            Iterator<KotlinType> it = DeserializedClassDescriptor.this.getTypeConstructor().mo7438getSupertypes().iterator();
            while (it.hasNext()) {
                for (DeclarationDescriptor declarationDescriptor : ResolutionScope.DefaultImpls.getContributedDescriptors$default(it.next().getMemberScope(), null, null, 3, null)) {
                    if ((declarationDescriptor instanceof SimpleFunctionDescriptor) || (declarationDescriptor instanceof PropertyDescriptor)) {
                        hashSet.add(declarationDescriptor.getShortName());
                    }
                }
            }
            List<ProtoBuf.Function> functionList = DeserializedClassDescriptor.this.getClassProto().getFunctionList();
            Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
            DeserializedClassDescriptor deserializedClassDescriptor = DeserializedClassDescriptor.this;
            Iterator<T> it2 = functionList.iterator();
            while (it2.hasNext()) {
                hashSet.add(NameResolverUtilKt.getName(deserializedClassDescriptor.getC().getNameResolver(), ((ProtoBuf.Function) it2.next()).getName()));
            }
            HashSet hashSet2 = hashSet;
            HashSet hashSet3 = hashSet2;
            List<ProtoBuf.Property> propertyList = DeserializedClassDescriptor.this.getClassProto().getPropertyList();
            Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
            DeserializedClassDescriptor deserializedClassDescriptor2 = DeserializedClassDescriptor.this;
            Iterator<T> it3 = propertyList.iterator();
            while (it3.hasNext()) {
                hashSet2.add(NameResolverUtilKt.getName(deserializedClassDescriptor2.getC().getNameResolver(), ((ProtoBuf.Property) it3.next()).getName()));
            }
            return SetsKt.plus((Set) hashSet3, (Iterable) hashSet2);
        }

        public final Collection<ClassDescriptor> all() {
            Set<Name> setKeySet = this.enumEntryProtos.keySet();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = setKeySet.iterator();
            while (it.hasNext()) {
                ClassDescriptor classDescriptorFindEnumEntry = findEnumEntry((Name) it.next());
                if (classDescriptorFindEnumEntry != null) {
                    arrayList.add(classDescriptorFindEnumEntry);
                }
            }
            return arrayList;
        }
    }
}
