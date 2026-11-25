package org.jetbrains.kotlin.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DeserializedDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirementTable;
import org.jetbrains.kotlin.protobuf.MessageLite;

/* JADX INFO: compiled from: DeserializedMemberDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001dÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeserializedDescriptor;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DescriptorWithContainerSource;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "proto", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "getProto", "()Lorg/jetbrains/kotlin/protobuf/MessageLite;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "getTypeTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;", "getVersionRequirementTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;", "versionRequirements", "", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "getVersionRequirements", "()Ljava/util/List;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DeserializedMemberDescriptor extends DeserializedDescriptor, MemberDescriptor, DescriptorWithContainerSource {
    DeserializedContainerSource getContainerSource();

    NameResolver getNameResolver();

    MessageLite getProto();

    TypeTable getTypeTable();

    VersionRequirementTable getVersionRequirementTable();

    default List<VersionRequirement> getVersionRequirements() {
        return VersionRequirement.INSTANCE.create(getProto(), getNameResolver(), getVersionRequirementTable());
    }
}
