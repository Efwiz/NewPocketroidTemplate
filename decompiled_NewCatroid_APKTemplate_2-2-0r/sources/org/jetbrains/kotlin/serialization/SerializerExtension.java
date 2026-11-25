package org.jetbrains.kotlin.serialization;

import java.util.Collection;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SerializerExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001:\u0001HB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0004H\u0014J(\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J \u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020!2\u0006\u0010\u001a\u001a\u00020\"2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0018\u0010#\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020$H\u0016J\u0018\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H\u0016J \u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020)2\u0006\u0010.\u001a\u00020)H\u0016J*\u0010/\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u0002002\u0006\u0010\u001a\u001a\u0002012\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0018\u00102\u001a\u00020\u00172\u0006\u00103\u001a\u0002042\u0006\u0010\u001a\u001a\u000205H\u0016J*\u00106\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u0002072\u0006\u0010\u001a\u001a\u0002082\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0018\u00109\u001a\u00020\u00172\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001a\u001a\u00020)H\u0016J\u0018\u0010:\u001a\u00020\u00172\u0006\u0010;\u001a\u00020<2\u0006\u0010\u001a\u001a\u00020=H\u0016J\u0018\u0010>\u001a\u00020\u00172\u0006\u0010?\u001a\u00020@2\u0006\u0010\u001a\u001a\u00020AH\u0016J\u0018\u0010B\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020C2\u0006\u0010\u001a\u001a\u00020DH\u0016J\b\u0010E\u001a\u00020FH\u0016J\b\u0010G\u001a\u00020FH\u0016R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006I"}, d2 = {"Lorg/jetbrains/kotlin/serialization/SerializerExtension;", "", "()V", "annotationSerializer", "Lorg/jetbrains/kotlin/serialization/AnnotationSerializer;", "getAnnotationSerializer", "()Lorg/jetbrains/kotlin/serialization/AnnotationSerializer;", "annotationSerializer$delegate", "Lkotlin/Lazy;", "customClassMembersProducer", "Lorg/jetbrains/kotlin/serialization/SerializerExtension$ClassMembersProducer;", "getCustomClassMembersProducer", "()Lorg/jetbrains/kotlin/serialization/SerializerExtension$ClassMembersProducer;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "stringTable", "Lorg/jetbrains/kotlin/serialization/DescriptorAwareStringTable;", "getStringTable", "()Lorg/jetbrains/kotlin/serialization/DescriptorAwareStringTable;", "createAnnotationSerializer", "serializeClass", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "serializeConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "serializeEnumEntry", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry$Builder;", "serializeErrorType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "serializeFlexibleType", "flexibleType", "Lorg/jetbrains/kotlin/types/FlexibleType;", "lowerProto", "upperProto", "serializeFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializePackage", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package$Builder;", "serializeProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "serializeType", "serializeTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias$Builder;", "serializeTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Builder;", "serializeValueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter$Builder;", "shouldUseNormalizedVisibility", "", "shouldUseTypeTable", "ClassMembersProducer", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SerializerExtension {

    /* JADX INFO: renamed from: annotationSerializer$delegate, reason: from kotlin metadata */
    private final Lazy annotationSerializer = LazyKt.lazy(new Function0<AnnotationSerializer>() { // from class: org.jetbrains.kotlin.serialization.SerializerExtension$annotationSerializer$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final AnnotationSerializer invoke() {
            return this.this$0.createAnnotationSerializer();
        }
    });

    /* JADX INFO: compiled from: SerializerExtension.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/SerializerExtension$ClassMembersProducer;", "", "getCallableMembers", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ClassMembersProducer {
        Collection<CallableMemberDescriptor> getCallableMembers(ClassDescriptor classDescriptor);
    }

    public ClassMembersProducer getCustomClassMembersProducer() {
        return null;
    }

    public abstract BinaryVersion getMetadataVersion();

    public abstract DescriptorAwareStringTable getStringTable();

    public void serializeClass(ClassDescriptor descriptor, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
    }

    public void serializeConstructor(ConstructorDescriptor descriptor, ProtoBuf.Constructor.Builder proto, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
    }

    public void serializeEnumEntry(ClassDescriptor descriptor, ProtoBuf.EnumEntry.Builder proto) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public void serializeFlexibleType(FlexibleType flexibleType, ProtoBuf.Type.Builder lowerProto, ProtoBuf.Type.Builder upperProto) {
        Intrinsics.checkNotNullParameter(flexibleType, "flexibleType");
        Intrinsics.checkNotNullParameter(lowerProto, "lowerProto");
        Intrinsics.checkNotNullParameter(upperProto, "upperProto");
    }

    public void serializeFunction(FunctionDescriptor descriptor, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
    }

    public void serializePackage(FqName packageFqName, ProtoBuf.Package.Builder proto) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public void serializeProperty(PropertyDescriptor descriptor, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
    }

    public void serializeType(KotlinType type, ProtoBuf.Type.Builder proto) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public void serializeTypeAlias(TypeAliasDescriptor typeAlias, ProtoBuf.TypeAlias.Builder proto) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public void serializeTypeParameter(TypeParameterDescriptor typeParameter, ProtoBuf.TypeParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public void serializeValueParameter(ValueParameterDescriptor descriptor, ProtoBuf.ValueParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public boolean shouldUseNormalizedVisibility() {
        return false;
    }

    public boolean shouldUseTypeTable() {
        return false;
    }

    public final AnnotationSerializer getAnnotationSerializer() {
        return (AnnotationSerializer) this.annotationSerializer.getValue();
    }

    protected AnnotationSerializer createAnnotationSerializer() {
        return new AnnotationSerializer(getStringTable());
    }

    public void serializeErrorType(KotlinType type, ProtoBuf.Type.Builder builder) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(builder, "builder");
        throw new IllegalStateException("Cannot serialize error type: " + type);
    }
}
