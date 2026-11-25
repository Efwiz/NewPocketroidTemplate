package org.jetbrains.kotlin.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirementTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: DeserializedMemberDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002BW\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016¢\u0006\u0002\u0010\u0017J\b\u00101\u001a\u00020\u001fH\u0016J\u000e\u00102\u001a\b\u0012\u0004\u0012\u00020*0)H\u0014J$\u00103\u001a\u0002042\f\u00105\u001a\b\u0012\u0004\u0012\u00020*0)2\u0006\u0010-\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001fJ\u0010\u00106\u001a\u0002072\u0006\u00108\u001a\u000209H\u0016R\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010!\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f@RX\u0096.¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u001e\u0010-\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f@RX\u0096.¢\u0006\b\n\u0000\u001a\u0004\b.\u0010#R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u00100¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedTypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/AbstractTypeAliasDescriptor;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "defaultTypeImpl", "Lorg/jetbrains/kotlin/types/SimpleType;", "<set-?>", "expandedType", "getExpandedType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "typeConstructorParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getTypeTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "underlyingType", "getUnderlyingType", "getVersionRequirementTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;", "getDefaultType", "getTypeConstructorTypeParameters", "initialize", "", "declaredTypeParameters", "substitute", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeserializedTypeAliasDescriptor extends AbstractTypeAliasDescriptor implements DeserializedMemberDescriptor {
    private final DeserializedContainerSource containerSource;
    private SimpleType defaultTypeImpl;
    private SimpleType expandedType;
    private final NameResolver nameResolver;
    private final ProtoBuf.TypeAlias proto;
    private List<? extends TypeParameterDescriptor> typeConstructorParameters;
    private final TypeTable typeTable;
    private SimpleType underlyingType;
    private final VersionRequirementTable versionRequirementTable;

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor
    public ProtoBuf.TypeAlias getProto() {
        return this.proto;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor
    public NameResolver getNameResolver() {
        return this.nameResolver;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor
    public TypeTable getTypeTable() {
        return this.typeTable;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor
    public VersionRequirementTable getVersionRequirementTable() {
        return this.versionRequirementTable;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor, org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public DeserializedTypeAliasDescriptor(StorageManager storageManager, DeclarationDescriptor containingDeclaration, Annotations annotations, Name name2, DescriptorVisibility visibility, ProtoBuf.TypeAlias proto, NameResolver nameResolver, TypeTable typeTable, VersionRequirementTable versionRequirementTable, DeserializedContainerSource deserializedContainerSource) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        super(storageManager, containingDeclaration, annotations, name2, NO_SOURCE, visibility);
        this.proto = proto;
        this.nameResolver = nameResolver;
        this.typeTable = typeTable;
        this.versionRequirementTable = versionRequirementTable;
        this.containerSource = deserializedContainerSource;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public SimpleType getUnderlyingType() {
        SimpleType simpleType = this.underlyingType;
        if (simpleType != null) {
            return simpleType;
        }
        Intrinsics.throwUninitializedPropertyAccessException("underlyingType");
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public SimpleType getExpandedType() {
        SimpleType simpleType = this.expandedType;
        if (simpleType != null) {
            return simpleType;
        }
        Intrinsics.throwUninitializedPropertyAccessException("expandedType");
        return null;
    }

    public final void initialize(List<? extends TypeParameterDescriptor> declaredTypeParameters, SimpleType underlyingType, SimpleType expandedType) {
        Intrinsics.checkNotNullParameter(declaredTypeParameters, "declaredTypeParameters");
        Intrinsics.checkNotNullParameter(underlyingType, "underlyingType");
        Intrinsics.checkNotNullParameter(expandedType, "expandedType");
        initialize(declaredTypeParameters);
        this.underlyingType = underlyingType;
        this.expandedType = expandedType;
        this.typeConstructorParameters = TypeParameterUtilsKt.computeConstructorTypeParameters(this);
        this.defaultTypeImpl = computeDefaultType();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public ClassDescriptor getClassDescriptor() {
        if (KotlinTypeKt.isError(getExpandedType())) {
            return null;
        }
        ClassifierDescriptor this$0 = getExpandedType().getConstructor().getThis$0();
        if (this$0 instanceof ClassDescriptor) {
            return (ClassDescriptor) this$0;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public SimpleType getDefaultType() {
        SimpleType simpleType = this.defaultTypeImpl;
        if (simpleType != null) {
            return simpleType;
        }
        Intrinsics.throwUninitializedPropertyAccessException("defaultTypeImpl");
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    public TypeAliasDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        if (substitutor.isEmpty()) {
            return this;
        }
        StorageManager storageManager = getStorageManager();
        DeclarationDescriptor containingDeclaration = getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        Annotations annotations = getAnnotations();
        Intrinsics.checkNotNullExpressionValue(annotations, "<get-annotations>(...)");
        Name name2 = getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        DeserializedTypeAliasDescriptor deserializedTypeAliasDescriptor = new DeserializedTypeAliasDescriptor(storageManager, containingDeclaration, annotations, name2, getVisibilityImpl(), getProto(), getNameResolver(), getTypeTable(), getVersionRequirementTable(), getContainerSource());
        List<TypeParameterDescriptor> declaredTypeParameters = getDeclaredTypeParameters();
        KotlinType kotlinTypeSafeSubstitute = substitutor.safeSubstitute(getUnderlyingType(), Variance.INVARIANT);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
        SimpleType simpleTypeAsSimpleType = TypeSubstitutionKt.asSimpleType(kotlinTypeSafeSubstitute);
        KotlinType kotlinTypeSafeSubstitute2 = substitutor.safeSubstitute(getExpandedType(), Variance.INVARIANT);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute2, "safeSubstitute(...)");
        deserializedTypeAliasDescriptor.initialize(declaredTypeParameters, simpleTypeAsSimpleType, TypeSubstitutionKt.asSimpleType(kotlinTypeSafeSubstitute2));
        return deserializedTypeAliasDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor
    protected List<TypeParameterDescriptor> getTypeConstructorTypeParameters() {
        List list = this.typeConstructorParameters;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typeConstructorParameters");
        return null;
    }
}
