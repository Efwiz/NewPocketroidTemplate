package org.jetbrains.kotlin.analysis.decompiler.stub;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader;
import org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder;

/* JADX INFO: compiled from: ClsStubBuilderContext.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u001e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderComponents;", "", "classDataFinder", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "annotationLoader", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationLoader;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithArgs;", "virtualFileForDebug", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "serializationProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "classFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "(Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationLoader;Lcom/intellij/openapi/vfs/VirtualFile;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;)V", "getAnnotationLoader", "()Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationLoader;", "getClassDataFinder", "()Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "getClassFinder", "()Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "getJvmMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "getSerializationProtocol", "()Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "getVirtualFileForDebug", "()Lcom/intellij/openapi/vfs/VirtualFile;", "createContext", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClsStubBuilderComponents {
    private final AnnotationLoader<AnnotationWithArgs> annotationLoader;
    private final ClassDataFinder classDataFinder;
    private final KotlinClassFinder classFinder;
    private final JvmMetadataVersion jvmMetadataVersion;
    private final SerializerExtensionProtocol serializationProtocol;
    private final VirtualFile virtualFileForDebug;

    public ClsStubBuilderComponents(ClassDataFinder classDataFinder, AnnotationLoader<AnnotationWithArgs> annotationLoader, VirtualFile virtualFileForDebug, SerializerExtensionProtocol serializationProtocol, KotlinClassFinder kotlinClassFinder, JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(classDataFinder, "classDataFinder");
        Intrinsics.checkNotNullParameter(annotationLoader, "annotationLoader");
        Intrinsics.checkNotNullParameter(virtualFileForDebug, "virtualFileForDebug");
        Intrinsics.checkNotNullParameter(serializationProtocol, "serializationProtocol");
        this.classDataFinder = classDataFinder;
        this.annotationLoader = annotationLoader;
        this.virtualFileForDebug = virtualFileForDebug;
        this.serializationProtocol = serializationProtocol;
        this.classFinder = kotlinClassFinder;
        this.jvmMetadataVersion = jvmMetadataVersion;
    }

    public /* synthetic */ ClsStubBuilderComponents(ClassDataFinder classDataFinder, AnnotationLoader annotationLoader, VirtualFile virtualFile, SerializerExtensionProtocol serializerExtensionProtocol, KotlinClassFinder kotlinClassFinder, JvmMetadataVersion jvmMetadataVersion, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(classDataFinder, annotationLoader, virtualFile, serializerExtensionProtocol, (i & 16) != 0 ? null : kotlinClassFinder, (i & 32) != 0 ? null : jvmMetadataVersion);
    }

    public final ClassDataFinder getClassDataFinder() {
        return this.classDataFinder;
    }

    public final AnnotationLoader<AnnotationWithArgs> getAnnotationLoader() {
        return this.annotationLoader;
    }

    public final VirtualFile getVirtualFileForDebug() {
        return this.virtualFileForDebug;
    }

    public final SerializerExtensionProtocol getSerializationProtocol() {
        return this.serializationProtocol;
    }

    public final KotlinClassFinder getClassFinder() {
        return this.classFinder;
    }

    public final JvmMetadataVersion getJvmMetadataVersion() {
        return this.jvmMetadataVersion;
    }

    public final ClsStubBuilderContext createContext(NameResolver nameResolver, FqName packageFqName, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return new ClsStubBuilderContext(this, nameResolver, packageFqName, EmptyTypeParameters.INSTANCE, typeTable, null);
    }
}
