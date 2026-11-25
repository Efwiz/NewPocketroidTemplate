package org.jetbrains.kotlin.serialization;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SerializerExtensionBase.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J \u0010\u0013\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0017H\u0016J*\u0010\u0018\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u001a2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u001eH\u0016J*\u0010\u001f\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020 2\u0006\u0010\r\u001a\u00020!2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020$2\u0006\u0010\r\u001a\u00020%H\u0016J\u0018\u0010&\u001a\u00020\n2\u0006\u0010'\u001a\u00020(2\u0006\u0010\r\u001a\u00020)H\u0016J\u0018\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020,2\u0006\u0010\r\u001a\u00020-H\u0016J\u0018\u0010.\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020/2\u0006\u0010\r\u001a\u000200H\u0016Jm\u00101\u001a\u00020\n\"\u000e\b\u0000\u00102*\b\u0012\u0004\u0012\u0002H203\"\u0014\b\u0001\u00104*\u000e\u0012\u0004\u0012\u0002H2\u0012\u0004\u0012\u0002H405\"\u0004\b\u0002\u00106*\u000e\u0012\u0004\u0012\u0002H2\u0012\u0004\u0012\u0002H4052\u0018\u00107\u001a\u0014\u0012\u0004\u0012\u0002H2\u0012\n\u0012\b\u0012\u0004\u0012\u0002H609082\b\u0010:\u001a\u0004\u0018\u0001H6H\u0002¢\u0006\u0002\u0010;R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "Lorg/jetbrains/kotlin/serialization/SerializerExtension;", ConfigConstants.CONFIG_PROTOCOL_SECTION, "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "stringTable", "Lorg/jetbrains/kotlin/serialization/StringTableImpl;", "getStringTable", "()Lorg/jetbrains/kotlin/serialization/StringTableImpl;", "serializeClass", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "serializeConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "serializeEnumEntry", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry$Builder;", "serializeFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializePackage", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package$Builder;", "serializeProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "serializeType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "serializeTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias$Builder;", "serializeTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Builder;", "serializeValueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter$Builder;", "addExtensionOrNull", "MessageType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableMessage;", "BuilderType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableBuilder;", "Type", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;", "", "value", "(Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableBuilder;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Ljava/lang/Object;)V", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinSerializerExtensionBase extends SerializerExtension {
    private final SerializerExtensionProtocol protocol;
    private final StringTableImpl stringTable;

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeTypeAlias(TypeAliasDescriptor typeAlias, ProtoBuf.TypeAlias.Builder proto) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public KotlinSerializerExtensionBase(SerializerExtensionProtocol protocol) {
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        this.protocol = protocol;
        this.stringTable = new StringTableImpl();
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public StringTableImpl getStringTable() {
        return this.stringTable;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeClass(ClassDescriptor descriptor, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(descriptor).iterator();
        while (it.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getClassAnnotation(), getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializePackage(FqName packageFqName, ProtoBuf.Package.Builder proto) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(proto, "proto");
        proto.setExtension(this.protocol.getPackageFqName(), Integer.valueOf(getStringTable().getPackageFqNameIndex(packageFqName)));
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeConstructor(ConstructorDescriptor descriptor, ProtoBuf.Constructor.Builder proto, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(descriptor).iterator();
        while (it.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getConstructorAnnotation(), getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeFunction(FunctionDescriptor descriptor, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        List<AnnotationDescriptor> listEmptyList;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(descriptor).iterator();
        while (it.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getFunctionAnnotation(), getAnnotationSerializer().serializeAnnotation(it.next()));
        }
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionExtensionReceiverAnnotation = this.protocol.getFunctionExtensionReceiverAnnotation();
        if (functionExtensionReceiverAnnotation != null) {
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                Intrinsics.checkNotNull(extensionReceiverParameter);
                listEmptyList = DescriptorUtilsKt.getNonSourceAnnotations(extensionReceiverParameter);
            } else {
                listEmptyList = null;
            }
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            Iterator<AnnotationDescriptor> it2 = listEmptyList.iterator();
            while (it2.hasNext()) {
                addExtensionOrNull(proto, functionExtensionReceiverAnnotation, getAnnotationSerializer().serializeAnnotation(it2.next()));
            }
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeProperty(PropertyDescriptor descriptor, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        List<AnnotationDescriptor> listEmptyList;
        List<AnnotationDescriptor> listEmptyList2;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(descriptor).iterator();
        while (it.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getPropertyAnnotation(), getAnnotationSerializer().serializeAnnotation(it.next()));
        }
        PropertyGetterDescriptor getter = descriptor.getGetter();
        List<AnnotationDescriptor> listEmptyList3 = null;
        List<AnnotationDescriptor> nonSourceAnnotations = getter != null ? DescriptorUtilsKt.getNonSourceAnnotations(getter) : null;
        if (nonSourceAnnotations == null) {
            nonSourceAnnotations = CollectionsKt.emptyList();
        }
        Iterator<AnnotationDescriptor> it2 = nonSourceAnnotations.iterator();
        while (it2.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getPropertyGetterAnnotation(), getAnnotationSerializer().serializeAnnotation(it2.next()));
        }
        PropertySetterDescriptor setter = descriptor.getSetter();
        List<AnnotationDescriptor> nonSourceAnnotations2 = setter != null ? DescriptorUtilsKt.getNonSourceAnnotations(setter) : null;
        if (nonSourceAnnotations2 == null) {
            nonSourceAnnotations2 = CollectionsKt.emptyList();
        }
        Iterator<AnnotationDescriptor> it3 = nonSourceAnnotations2.iterator();
        while (it3.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getPropertySetterAnnotation(), getAnnotationSerializer().serializeAnnotation(it3.next()));
        }
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyExtensionReceiverAnnotation = this.protocol.getPropertyExtensionReceiverAnnotation();
        if (propertyExtensionReceiverAnnotation != null) {
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                Intrinsics.checkNotNull(extensionReceiverParameter);
                listEmptyList2 = DescriptorUtilsKt.getNonSourceAnnotations(extensionReceiverParameter);
            } else {
                listEmptyList2 = null;
            }
            if (listEmptyList2 == null) {
                listEmptyList2 = CollectionsKt.emptyList();
            }
            Iterator<AnnotationDescriptor> it4 = listEmptyList2.iterator();
            while (it4.hasNext()) {
                addExtensionOrNull(proto, propertyExtensionReceiverAnnotation, getAnnotationSerializer().serializeAnnotation(it4.next()));
            }
        }
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyBackingFieldAnnotation = this.protocol.getPropertyBackingFieldAnnotation();
        if (propertyBackingFieldAnnotation != null) {
            FieldDescriptor backingField = descriptor.getBackingField();
            if (backingField != null) {
                Intrinsics.checkNotNull(backingField);
                listEmptyList = DescriptorUtilsKt.getNonSourceAnnotations(backingField);
            } else {
                listEmptyList = null;
            }
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            Iterator<AnnotationDescriptor> it5 = listEmptyList.iterator();
            while (it5.hasNext()) {
                addExtensionOrNull(proto, propertyBackingFieldAnnotation, getAnnotationSerializer().serializeAnnotation(it5.next()));
            }
        }
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyDelegatedFieldAnnotation = this.protocol.getPropertyDelegatedFieldAnnotation();
        if (propertyDelegatedFieldAnnotation != null) {
            FieldDescriptor delegateField = descriptor.getDelegateField();
            if (delegateField != null) {
                Intrinsics.checkNotNull(delegateField);
                listEmptyList3 = DescriptorUtilsKt.getNonSourceAnnotations(delegateField);
            }
            if (listEmptyList3 == null) {
                listEmptyList3 = CollectionsKt.emptyList();
            }
            Iterator<AnnotationDescriptor> it6 = listEmptyList3.iterator();
            while (it6.hasNext()) {
                addExtensionOrNull(proto, propertyDelegatedFieldAnnotation, getAnnotationSerializer().serializeAnnotation(it6.next()));
            }
        }
        ConstantValue<?> compileTimeInitializer = descriptor.mo6754getCompileTimeInitializer();
        if (compileTimeInitializer == null || (compileTimeInitializer instanceof NullValue)) {
            return;
        }
        proto.setExtension(this.protocol.getCompileTimeValue(), getAnnotationSerializer().valueProto(compileTimeInitializer).build());
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeEnumEntry(ClassDescriptor descriptor, ProtoBuf.EnumEntry.Builder proto) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(descriptor).iterator();
        while (it.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getEnumEntryAnnotation(), getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeValueParameter(ValueParameterDescriptor descriptor, ProtoBuf.ValueParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(descriptor).iterator();
        while (it.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getParameterAnnotation(), getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeType(KotlinType type, ProtoBuf.Type.Builder proto) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(type).iterator();
        while (it.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getTypeAnnotation(), getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeTypeParameter(TypeParameterDescriptor typeParameter, ProtoBuf.TypeParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(typeParameter).iterator();
        while (it.hasNext()) {
            addExtensionOrNull(proto, this.protocol.getTypeParameterAnnotation(), getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    private final <MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>, Type> void addExtensionOrNull(GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType> extendableBuilder, GeneratedMessageLite.GeneratedExtension<MessageType, List<Type>> generatedExtension, Type type) {
        if (type != null) {
            extendableBuilder.addExtension(generatedExtension, type);
        }
    }
}
