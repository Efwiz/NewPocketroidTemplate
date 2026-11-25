package org.jetbrains.kotlin.analysis.decompiler.stub;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: ClsStubBuilderContext.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "", "components", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderComponents;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "containerFqName", "Lorg/jetbrains/kotlin/name/FqName;", "typeParameters", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeParameters;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "protoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "(Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderComponents;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeParameters;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;)V", "getComponents", "()Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderComponents;", "getContainerFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getProtoContainer", "()Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "getTypeParameters", "()Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeParameters;", "getTypeTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClsStubBuilderContext {
    private final ClsStubBuilderComponents components;
    private final FqName containerFqName;
    private final NameResolver nameResolver;
    private final ProtoContainer.Class protoContainer;
    private final TypeParameters typeParameters;
    private final TypeTable typeTable;

    public ClsStubBuilderContext(ClsStubBuilderComponents components, NameResolver nameResolver, FqName containerFqName, TypeParameters typeParameters, TypeTable typeTable, ProtoContainer.Class r7) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(containerFqName, "containerFqName");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        this.components = components;
        this.nameResolver = nameResolver;
        this.containerFqName = containerFqName;
        this.typeParameters = typeParameters;
        this.typeTable = typeTable;
        this.protoContainer = r7;
    }

    public final ClsStubBuilderComponents getComponents() {
        return this.components;
    }

    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }

    public final FqName getContainerFqName() {
        return this.containerFqName;
    }

    public final TypeParameters getTypeParameters() {
        return this.typeParameters;
    }

    public final TypeTable getTypeTable() {
        return this.typeTable;
    }

    public final ProtoContainer.Class getProtoContainer() {
        return this.protoContainer;
    }
}
