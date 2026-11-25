package org.jetbrains.kotlin.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;

/* JADX INFO: compiled from: DescriptorSerializerPlugin.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aJ0\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J2\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u00102\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J2\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00122\u0006\u0010\u0006\u001a\u00020\u00132\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J2\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u00162\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J2\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00182\u0006\u0010\u0006\u001a\u00020\u00192\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/DescriptorSerializerPlugin;", "", "afterClass", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/serialization/SerializerExtension;", "afterConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "afterFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "afterProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "afterTypealias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias$Builder;", "Companion", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DescriptorSerializerPlugin {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    default void afterClass(ClassDescriptor descriptor, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer, SerializerExtension extension) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Intrinsics.checkNotNullParameter(extension, "extension");
    }

    default void afterConstructor(ConstructorDescriptor descriptor, ProtoBuf.Constructor.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer, SerializerExtension extension) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Intrinsics.checkNotNullParameter(extension, "extension");
    }

    default void afterFunction(FunctionDescriptor descriptor, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer, SerializerExtension extension) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Intrinsics.checkNotNullParameter(extension, "extension");
    }

    default void afterProperty(PropertyDescriptor descriptor, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer, SerializerExtension extension) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Intrinsics.checkNotNullParameter(extension, "extension");
    }

    default void afterTypealias(TypeAliasDescriptor descriptor, ProtoBuf.TypeAlias.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer, SerializerExtension extension) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        Intrinsics.checkNotNullParameter(extension, "extension");
    }

    /* JADX INFO: compiled from: DescriptorSerializerPlugin.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/serialization/DescriptorSerializerPlugin$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializerPlugin;", "()V", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<DescriptorSerializerPlugin> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.DescriptorSerializerPlugin", DescriptorSerializerPlugin.class);
        }
    }
}
