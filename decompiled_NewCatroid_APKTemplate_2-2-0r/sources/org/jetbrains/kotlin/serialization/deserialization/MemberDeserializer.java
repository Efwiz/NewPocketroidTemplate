package org.jetbrains.kotlin.serialization.deserialization;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ContractProvider;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirementTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedAnnotations;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassConstructorDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedTypeAliasDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.NonEmptyDeserializedAnnotations;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: MemberDeserializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Æ\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\u0018\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0014J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\t\u001a\u00020\u001cJ\u0010\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\fH\u0002J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010\t\u001a\u00020\u0012J\u000e\u0010!\u001a\u00020\"2\u0006\u0010\t\u001a\u00020#J,\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020'0%2\u0006\u0010(\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010)\u001a\u0004\u0018\u00010**\u00020+H\u0002J~\u0010,\u001a\u00020-*\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u00102\b\u00100\u001a\u0004\u0018\u00010\u00102\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00100%2\f\u00102\u001a\b\u0012\u0004\u0012\u0002030%2\f\u00104\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00105\u001a\u0004\u0018\u0001062\b\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020:2\u0016\u0010;\u001a\u0012\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030=\u0012\u0002\b\u00030<H\u0002J&\u0010>\u001a\u0004\u0018\u00010\u0010*\u00020?2\u0006\u0010@\u001a\u00020\u00032\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\fH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/MemberDeserializer;", "", "c", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationContext;", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationContext;)V", "annotationDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationDeserializer;", "getAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "proto", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", OverlayablePolicy.NAME_flags, "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "getDispatchReceiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getPropertyFieldAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "isDelegate", "", "getReceiverParameterAnnotations", "loadConstructor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "isPrimary", "loadFunction", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "loadOldFlags", "oldFlags", "loadProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "loadTypeAlias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "valueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "callable", "asProtoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "initializeWithCoroutinesExperimentalityStatus", "", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedSimpleFunctionDescriptor;", "extensionReceiverParameter", "dispatchReceiverParameter", "contextReceiverParameters", "typeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "unsubstitutedValueParameters", "unsubstitutedReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "userDataMap", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "toContextReceiver", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "deserializationContext", "callableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "index", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MemberDeserializer {
    private final AnnotationDeserializer annotationDeserializer;
    private final DeserializationContext c;

    private final int loadOldFlags(int oldFlags) {
        return (oldFlags & 63) + ((oldFlags >> 8) << 6);
    }

    public MemberDeserializer(DeserializationContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.c = c;
        this.annotationDeserializer = new AnnotationDeserializer(c.getComponents().getModuleDescriptor(), c.getComponents().getNotFoundClasses());
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r12v2 org.jetbrains.kotlin.metadata.ProtoBuf$Property
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.dex.visitors.ModVisitor.anonymousCallArgMod(ModVisitor.java:535)
        	at jadx.core.dex.visitors.ModVisitor.processAnonymousConstructor(ModVisitor.java:523)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:111)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    public final org.jetbrains.kotlin.descriptors.PropertyDescriptor loadProperty(final org.jetbrains.kotlin.metadata.ProtoBuf.Property r36) {
        /*
            Method dump skipped, instruction units count: 933
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.serialization.deserialization.MemberDeserializer.loadProperty(org.jetbrains.kotlin.metadata.ProtoBuf$Property):org.jetbrains.kotlin.descriptors.PropertyDescriptor");
    }

    private final void initializeWithCoroutinesExperimentalityStatus(DeserializedSimpleFunctionDescriptor deserializedSimpleFunctionDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor2, List<? extends ReceiverParameterDescriptor> list, List<? extends TypeParameterDescriptor> list2, List<? extends ValueParameterDescriptor> list3, KotlinType kotlinType, Modality modality, DescriptorVisibility descriptorVisibility, Map<? extends CallableDescriptor.UserDataKey<?>, ?> map) {
        deserializedSimpleFunctionDescriptor.initialize(receiverParameterDescriptor, receiverParameterDescriptor2, list, list2, list3, kotlinType, modality, descriptorVisibility, map);
    }

    public final SimpleFunctionDescriptor loadFunction(ProtoBuf.Function proto) {
        Annotations empty;
        VersionRequirementTable versionRequirementTable;
        KotlinType kotlinTypeType;
        Intrinsics.checkNotNullParameter(proto, "proto");
        int flags = proto.hasFlags() ? proto.getFlags() : loadOldFlags(proto.getOldFlags());
        ProtoBuf.Function function = proto;
        Annotations annotations = getAnnotations(function, flags, AnnotatedCallableKind.FUNCTION);
        if (ProtoTypeTableUtilKt.hasReceiver(proto)) {
            empty = getReceiverParameterAnnotations(function, AnnotatedCallableKind.FUNCTION);
        } else {
            empty = Annotations.INSTANCE.getEMPTY();
        }
        if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(this.c.getContainingDeclaration()).child(NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName())), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
            versionRequirementTable = VersionRequirementTable.INSTANCE.getEMPTY();
        } else {
            versionRequirementTable = this.c.getVersionRequirementTable();
        }
        DeserializedSimpleFunctionDescriptor deserializedSimpleFunctionDescriptor = new DeserializedSimpleFunctionDescriptor(this.c.getContainingDeclaration(), null, annotations, NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName()), ProtoEnumFlagsUtilsKt.memberKind(ProtoEnumFlags.INSTANCE, Flags.MEMBER_KIND.get(flags)), proto, this.c.getNameResolver(), this.c.getTypeTable(), versionRequirementTable, this.c.getContainerSource(), null, 1024, null);
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        DeserializationContext deserializationContextChildContext$default = DeserializationContext.childContext$default(this.c, deserializedSimpleFunctionDescriptor, typeParameterList, null, null, null, null, 60, null);
        ProtoBuf.Type typeReceiverType = ProtoTypeTableUtilKt.receiverType(proto, this.c.getTypeTable());
        ReceiverParameterDescriptor receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = (typeReceiverType == null || (kotlinTypeType = deserializationContextChildContext$default.getTypeDeserializer().type(typeReceiverType)) == null) ? null : DescriptorFactory.createExtensionReceiverParameterForCallable(deserializedSimpleFunctionDescriptor, kotlinTypeType, empty);
        ReceiverParameterDescriptor dispatchReceiverParameter = getDispatchReceiverParameter();
        List<ProtoBuf.Type> listContextReceiverTypes = ProtoTypeTableUtilKt.contextReceiverTypes(proto, this.c.getTypeTable());
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : listContextReceiverTypes) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ReceiverParameterDescriptor contextReceiver = toContextReceiver((ProtoBuf.Type) obj, deserializationContextChildContext$default, deserializedSimpleFunctionDescriptor, i);
            if (contextReceiver != null) {
                arrayList.add(contextReceiver);
            }
            i = i2;
        }
        List<TypeParameterDescriptor> ownTypeParameters = deserializationContextChildContext$default.getTypeDeserializer().getOwnTypeParameters();
        MemberDeserializer memberDeserializer = deserializationContextChildContext$default.getMemberDeserializer();
        List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
        initializeWithCoroutinesExperimentalityStatus(deserializedSimpleFunctionDescriptor, receiverParameterDescriptorCreateExtensionReceiverParameterForCallable, dispatchReceiverParameter, arrayList, ownTypeParameters, memberDeserializer.valueParameters(valueParameterList, function, AnnotatedCallableKind.FUNCTION), deserializationContextChildContext$default.getTypeDeserializer().type(ProtoTypeTableUtilKt.returnType(proto, this.c.getTypeTable())), ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags)), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(flags)), MapsKt.emptyMap());
        Boolean bool = Flags.IS_OPERATOR.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        deserializedSimpleFunctionDescriptor.setOperator(bool.booleanValue());
        Boolean bool2 = Flags.IS_INFIX.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
        deserializedSimpleFunctionDescriptor.setInfix(bool2.booleanValue());
        Boolean bool3 = Flags.IS_EXTERNAL_FUNCTION.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool3, "get(...)");
        deserializedSimpleFunctionDescriptor.setExternal(bool3.booleanValue());
        Boolean bool4 = Flags.IS_INLINE.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool4, "get(...)");
        deserializedSimpleFunctionDescriptor.setInline(bool4.booleanValue());
        Boolean bool5 = Flags.IS_TAILREC.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool5, "get(...)");
        deserializedSimpleFunctionDescriptor.setTailrec(bool5.booleanValue());
        Boolean bool6 = Flags.IS_SUSPEND.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool6, "get(...)");
        deserializedSimpleFunctionDescriptor.setSuspend(bool6.booleanValue());
        Boolean bool7 = Flags.IS_EXPECT_FUNCTION.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool7, "get(...)");
        deserializedSimpleFunctionDescriptor.setExpect(bool7.booleanValue());
        deserializedSimpleFunctionDescriptor.setHasStableParameterNames(!Flags.IS_FUNCTION_WITH_NON_STABLE_PARAMETER_NAMES.get(flags).booleanValue());
        Pair<CallableDescriptor.UserDataKey<?>, ContractProvider> pairDeserializeContractFromFunction = this.c.getComponents().getContractDeserializer().deserializeContractFromFunction(proto, deserializedSimpleFunctionDescriptor, this.c.getTypeTable(), deserializationContextChildContext$default.getTypeDeserializer());
        if (pairDeserializeContractFromFunction != null) {
            deserializedSimpleFunctionDescriptor.putInUserDataMap(pairDeserializeContractFromFunction.getFirst(), pairDeserializeContractFromFunction.getSecond());
        }
        return deserializedSimpleFunctionDescriptor;
    }

    public final TypeAliasDescriptor loadTypeAlias(ProtoBuf.TypeAlias proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Annotations.Companion companion = Annotations.INSTANCE;
        List<ProtoBuf.Annotation> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<ProtoBuf.Annotation> list = annotationList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ProtoBuf.Annotation annotation : list) {
            AnnotationDeserializer annotationDeserializer = this.annotationDeserializer;
            Intrinsics.checkNotNull(annotation);
            arrayList.add(annotationDeserializer.deserializeAnnotation(annotation, this.c.getNameResolver()));
        }
        DeserializedTypeAliasDescriptor deserializedTypeAliasDescriptor = new DeserializedTypeAliasDescriptor(this.c.getStorageManager(), this.c.getContainingDeclaration(), companion.create(arrayList), NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName()), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(proto.getFlags())), proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource());
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        DeserializationContext deserializationContextChildContext$default = DeserializationContext.childContext$default(this.c, deserializedTypeAliasDescriptor, typeParameterList, null, null, null, null, 60, null);
        deserializedTypeAliasDescriptor.initialize(deserializationContextChildContext$default.getTypeDeserializer().getOwnTypeParameters(), deserializationContextChildContext$default.getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.underlyingType(proto, this.c.getTypeTable()), false), deserializationContextChildContext$default.getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.expandedType(proto, this.c.getTypeTable()), false));
        return deserializedTypeAliasDescriptor;
    }

    private final ReceiverParameterDescriptor getDispatchReceiverParameter() {
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor != null) {
            return classDescriptor.getThisAsReceiverParameter();
        }
        return null;
    }

    public final ClassConstructorDescriptor loadConstructor(ProtoBuf.Constructor proto, boolean isPrimary) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        ProtoBuf.Constructor constructor = proto;
        DeserializedClassConstructorDescriptor deserializedClassConstructorDescriptor = new DeserializedClassConstructorDescriptor(classDescriptor, null, getAnnotations(constructor, proto.getFlags(), AnnotatedCallableKind.FUNCTION), isPrimary, CallableMemberDescriptor.Kind.DECLARATION, proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource(), null, 1024, null);
        MemberDeserializer memberDeserializer = DeserializationContext.childContext$default(this.c, deserializedClassConstructorDescriptor, CollectionsKt.emptyList(), null, null, null, null, 60, null).getMemberDeserializer();
        List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
        deserializedClassConstructorDescriptor.initialize(memberDeserializer.valueParameters(valueParameterList, constructor, AnnotatedCallableKind.FUNCTION), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(proto.getFlags())));
        deserializedClassConstructorDescriptor.setReturnType(classDescriptor.getDefaultType());
        deserializedClassConstructorDescriptor.setExpect(classDescriptor.get$isExpect());
        deserializedClassConstructorDescriptor.setHasStableParameterNames(!Flags.IS_CONSTRUCTOR_WITH_NON_STABLE_PARAMETER_NAMES.get(proto.getFlags()).booleanValue());
        return deserializedClassConstructorDescriptor;
    }

    private final Annotations getAnnotations(final MessageLite proto, int flags, final AnnotatedCallableKind kind) {
        if (!Flags.HAS_ANNOTATIONS.get(flags).booleanValue()) {
            return Annotations.INSTANCE.getEMPTY();
        }
        return new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.MemberDeserializer.getAnnotations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends AnnotationDescriptor> invoke() {
                List<? extends AnnotationDescriptor> list;
                MemberDeserializer memberDeserializer = MemberDeserializer.this;
                ProtoContainer protoContainerAsProtoContainer = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                if (protoContainerAsProtoContainer != null) {
                    list = CollectionsKt.toList(MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader().loadCallableAnnotations(protoContainerAsProtoContainer, proto, kind));
                } else {
                    list = null;
                }
                return list == null ? CollectionsKt.emptyList() : list;
            }
        });
    }

    private final Annotations getPropertyFieldAnnotations(final ProtoBuf.Property proto, final boolean isDelegate) {
        if (!Flags.HAS_ANNOTATIONS.get(proto.getFlags()).booleanValue()) {
            return Annotations.INSTANCE.getEMPTY();
        }
        return new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.MemberDeserializer.getPropertyFieldAnnotations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends AnnotationDescriptor> invoke() {
                List<? extends AnnotationDescriptor> list;
                MemberDeserializer memberDeserializer = MemberDeserializer.this;
                ProtoContainer protoContainerAsProtoContainer = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                if (protoContainerAsProtoContainer != null) {
                    boolean z = isDelegate;
                    MemberDeserializer memberDeserializer2 = MemberDeserializer.this;
                    ProtoBuf.Property property = proto;
                    list = z ? CollectionsKt.toList(memberDeserializer2.c.getComponents().getAnnotationAndConstantLoader().loadPropertyDelegateFieldAnnotations(protoContainerAsProtoContainer, property)) : CollectionsKt.toList(memberDeserializer2.c.getComponents().getAnnotationAndConstantLoader().loadPropertyBackingFieldAnnotations(protoContainerAsProtoContainer, property));
                } else {
                    list = null;
                }
                return list == null ? CollectionsKt.emptyList() : list;
            }
        });
    }

    private final Annotations getReceiverParameterAnnotations(final MessageLite proto, final AnnotatedCallableKind kind) {
        return new DeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.MemberDeserializer.getReceiverParameterAnnotations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends AnnotationDescriptor> invoke() {
                List<AnnotationDescriptor> listLoadExtensionReceiverParameterAnnotations;
                MemberDeserializer memberDeserializer = MemberDeserializer.this;
                ProtoContainer protoContainerAsProtoContainer = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                if (protoContainerAsProtoContainer != null) {
                    listLoadExtensionReceiverParameterAnnotations = MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader().loadExtensionReceiverParameterAnnotations(protoContainerAsProtoContainer, proto, kind);
                } else {
                    listLoadExtensionReceiverParameterAnnotations = null;
                }
                return listLoadExtensionReceiverParameterAnnotations == null ? CollectionsKt.emptyList() : listLoadExtensionReceiverParameterAnnotations;
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:17:0x008d  */
    private final List<ValueParameterDescriptor> valueParameters(List<ProtoBuf.ValueParameter> valueParameters, final MessageLite callable, final AnnotatedCallableKind kind) {
        NonEmptyDeserializedAnnotations empty;
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
        CallableDescriptor callableDescriptor = (CallableDescriptor) containingDeclaration;
        DeclarationDescriptor containingDeclaration2 = callableDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
        final ProtoContainer protoContainerAsProtoContainer = asProtoContainer(containingDeclaration2);
        List<ProtoBuf.ValueParameter> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            final ProtoBuf.ValueParameter valueParameter = (ProtoBuf.ValueParameter) obj;
            int flags = valueParameter.hasFlags() ? valueParameter.getFlags() : 0;
            if (protoContainerAsProtoContainer != null) {
                Boolean bool = Flags.HAS_ANNOTATIONS.get(flags);
                Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
                if (bool.booleanValue()) {
                    final int i3 = i;
                    empty = new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.MemberDeserializer$valueParameters$1$annotations$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final List<? extends AnnotationDescriptor> invoke() {
                            return CollectionsKt.toList(this.this$0.c.getComponents().getAnnotationAndConstantLoader().loadValueParameterAnnotations(protoContainerAsProtoContainer, callable, kind, i3, valueParameter));
                        }
                    });
                } else {
                    empty = Annotations.INSTANCE.getEMPTY();
                }
            } else {
                empty = Annotations.INSTANCE.getEMPTY();
            }
            Name name2 = NameResolverUtilKt.getName(this.c.getNameResolver(), valueParameter.getName());
            KotlinType kotlinTypeType = this.c.getTypeDeserializer().type(ProtoTypeTableUtilKt.type(valueParameter, this.c.getTypeTable()));
            Boolean bool2 = Flags.DECLARES_DEFAULT_VALUE.get(flags);
            Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
            boolean zBooleanValue = bool2.booleanValue();
            Boolean bool3 = Flags.IS_CROSSINLINE.get(flags);
            Intrinsics.checkNotNullExpressionValue(bool3, "get(...)");
            boolean zBooleanValue2 = bool3.booleanValue();
            Boolean bool4 = Flags.IS_NOINLINE.get(flags);
            Intrinsics.checkNotNullExpressionValue(bool4, "get(...)");
            boolean zBooleanValue3 = bool4.booleanValue();
            ProtoBuf.Type typeVarargElementType = ProtoTypeTableUtilKt.varargElementType(valueParameter, this.c.getTypeTable());
            KotlinType kotlinTypeType2 = typeVarargElementType != null ? this.c.getTypeDeserializer().type(typeVarargElementType) : null;
            SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
            ArrayList arrayList2 = arrayList;
            arrayList2.add(new ValueParameterDescriptorImpl(callableDescriptor, null, i, empty, name2, kotlinTypeType, zBooleanValue, zBooleanValue2, zBooleanValue3, kotlinTypeType2, NO_SOURCE));
            arrayList = arrayList2;
            i = i2;
        }
        return CollectionsKt.toList(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ProtoContainer asProtoContainer(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof PackageFragmentDescriptor) {
            return new ProtoContainer.Package(((PackageFragmentDescriptor) declarationDescriptor).getFqName(), this.c.getNameResolver(), this.c.getTypeTable(), this.c.getContainerSource());
        }
        if (declarationDescriptor instanceof DeserializedClassDescriptor) {
            return ((DeserializedClassDescriptor) declarationDescriptor).getThisAsProtoContainer();
        }
        return null;
    }

    private final ReceiverParameterDescriptor toContextReceiver(ProtoBuf.Type type, DeserializationContext deserializationContext, CallableDescriptor callableDescriptor, int i) {
        return DescriptorFactory.createContextReceiverParameterForCallable(callableDescriptor, deserializationContext.getTypeDeserializer().type(type), null, Annotations.INSTANCE.getEMPTY(), i);
    }
}
