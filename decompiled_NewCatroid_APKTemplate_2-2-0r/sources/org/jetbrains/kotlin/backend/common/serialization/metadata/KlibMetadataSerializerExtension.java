package org.jetbrains.kotlin.backend.common.serialization.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.library.metadata.KlibMetadataSerializerProtocol;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase;
import org.jetbrains.kotlin.serialization.StringTableImpl;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: KlibMetadataSerializerExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0017\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002¢\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J \u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020 2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J \u0010&\u001a\u00020\u00162\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020%2\u0006\u0010*\u001a\u00020%H\u0016J*\u0010+\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020,2\u0006\u0010\u0018\u001a\u00020-2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J*\u0010.\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020/2\u0006\u0010\u0018\u001a\u0002002\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u00101\u001a\u00020\tH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializerExtension;", "Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "stringTable", "Lorg/jetbrains/kotlin/serialization/StringTableImpl;", "allowErrorTypes", "", "exportKDoc", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lorg/jetbrains/kotlin/serialization/StringTableImpl;ZZ)V", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getStringTable", "()Lorg/jetbrains/kotlin/serialization/StringTableImpl;", "descriptorFileId", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;)Ljava/lang/Integer;", "serializeClass", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "serializeConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "serializeErrorType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "serializeFlexibleType", "flexibleType", "Lorg/jetbrains/kotlin/types/FlexibleType;", "lowerProto", "upperProto", "serializeFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializeProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "shouldUseTypeTable", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataSerializerExtension extends KotlinSerializerExtensionBase {
    private final boolean allowErrorTypes;
    private final boolean exportKDoc;
    private final LanguageVersionSettings languageVersionSettings;
    private final BinaryVersion metadataVersion;
    private final StringTableImpl stringTable;

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    /* JADX INFO: renamed from: shouldUseTypeTable */
    public boolean getUseTypeTable() {
        return true;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public BinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public StringTableImpl getStringTable() {
        return this.stringTable;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KlibMetadataSerializerExtension(LanguageVersionSettings languageVersionSettings, BinaryVersion metadataVersion, StringTableImpl stringTable, boolean z, boolean z2) {
        super(KlibMetadataSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        this.languageVersionSettings = languageVersionSettings;
        this.metadataVersion = metadataVersion;
        this.stringTable = stringTable;
        this.allowErrorTypes = z;
        this.exportKDoc = z2;
    }

    private final Integer descriptorFileId(DeclarationDescriptorWithSource descriptor) {
        String name_ = descriptor.getSourceElement().getContainingFile().getName_();
        if (name_ == null) {
            return null;
        }
        return Integer.valueOf(getStringTable().getStringIndex(name_));
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeFlexibleType(FlexibleType flexibleType, ProtoBuf.Type.Builder lowerProto, ProtoBuf.Type.Builder upperProto) {
        Intrinsics.checkNotNullParameter(flexibleType, "flexibleType");
        Intrinsics.checkNotNullParameter(lowerProto, "lowerProto");
        Intrinsics.checkNotNullParameter(upperProto, "upperProto");
        lowerProto.setFlexibleTypeCapabilitiesId(getStringTable().getStringIndex("kotlin.DynamicType"));
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeErrorType(KotlinType type, ProtoBuf.Type.Builder builder) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(builder, "builder");
        if (this.allowErrorTypes) {
            return;
        }
        super.serializeErrorType(type, builder);
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeClass(ClassDescriptor descriptor, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        String strFindKDocString;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        ClassDescriptor classDescriptor = descriptor;
        Integer numDescriptorFileId = descriptorFileId(classDescriptor);
        if (numDescriptorFileId != null) {
            proto.setExtension(KlibMetadataProtoBuf.classFile, Integer.valueOf(numDescriptorFileId.intValue()));
        }
        if (this.exportKDoc && (strFindKDocString = KlibMetadataSerializerExtensionKt.findKDocString(classDescriptor)) != null) {
            proto.setExtension(KlibMetadataProtoBuf.classKdoc, strFindKDocString);
        }
        super.serializeClass(descriptor, proto, versionRequirementTable, childSerializer);
        ProtoBuf.TypeTable typeTableSerialize = childSerializer.getTypeTable().serialize();
        if (typeTableSerialize != null) {
            proto.mergeTypeTable(typeTableSerialize);
        }
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeConstructor(ConstructorDescriptor descriptor, ProtoBuf.Constructor.Builder proto, DescriptorSerializer childSerializer) {
        String strFindKDocString;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        if (this.exportKDoc && (strFindKDocString = KlibMetadataSerializerExtensionKt.findKDocString(descriptor)) != null) {
            proto.setExtension(KlibMetadataProtoBuf.constructorKdoc, strFindKDocString);
        }
        super.serializeConstructor(descriptor, proto, childSerializer);
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeProperty(PropertyDescriptor descriptor, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        String strFindKDocString;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        PropertyDescriptor propertyDescriptor = descriptor;
        Integer numDescriptorFileId = descriptorFileId(propertyDescriptor);
        if (numDescriptorFileId != null) {
            proto.setExtension(KlibMetadataProtoBuf.propertyFile, Integer.valueOf(numDescriptorFileId.intValue()));
        }
        if (this.exportKDoc && (strFindKDocString = KlibMetadataSerializerExtensionKt.findKDocString(propertyDescriptor)) != null) {
            proto.setExtension(KlibMetadataProtoBuf.propertyKdoc, strFindKDocString);
        }
        super.serializeProperty(descriptor, proto, versionRequirementTable, childSerializer);
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeFunction(FunctionDescriptor descriptor, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        String strFindKDocString;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        FunctionDescriptor functionDescriptor = descriptor;
        Integer numDescriptorFileId = descriptorFileId(functionDescriptor);
        if (numDescriptorFileId != null) {
            proto.setExtension(KlibMetadataProtoBuf.functionFile, Integer.valueOf(numDescriptorFileId.intValue()));
        }
        if (this.exportKDoc && (strFindKDocString = KlibMetadataSerializerExtensionKt.findKDocString(functionDescriptor)) != null) {
            proto.setExtension(KlibMetadataProtoBuf.functionKdoc, strFindKDocString);
        }
        super.serializeFunction(descriptor, proto, versionRequirementTable, childSerializer);
    }
}
