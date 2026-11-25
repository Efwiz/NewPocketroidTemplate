package org.jetbrains.kotlin.serialization.deserialization;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.protobuf.MessageLite;

/* JADX INFO: compiled from: AnnotationLoader.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0002J\u001d\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¢\u0006\u0002\u0010\bJ&\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u000b\u001a\u00020\u0011H&J\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0013H&J&\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0016H&J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0016H&J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u0004\u001a\u00020\u00192\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u0004\u001a\u00020\u001b2\u0006\u0010\u0006\u001a\u00020\u0007H&J6\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0004\u001a\u00020 H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006!À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationLoader;", "A", "", "loadAnnotation", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;)Ljava/lang/Object;", "loadCallableAnnotations", "", "container", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "loadClassAnnotations", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "loadEnumEntryAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry;", "loadExtensionReceiverParameterAnnotations", "loadPropertyBackingFieldAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "loadPropertyDelegateFieldAnnotations", "loadTypeAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "loadTypeParameterAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "loadValueParameterAnnotations", "callableProto", "parameterIndex", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AnnotationLoader<A> {
    A loadAnnotation(ProtoBuf.Annotation proto, NameResolver nameResolver);

    List<A> loadCallableAnnotations(ProtoContainer container, MessageLite proto, AnnotatedCallableKind kind);

    List<A> loadClassAnnotations(ProtoContainer.Class container);

    List<A> loadEnumEntryAnnotations(ProtoContainer container, ProtoBuf.EnumEntry proto);

    List<A> loadExtensionReceiverParameterAnnotations(ProtoContainer container, MessageLite proto, AnnotatedCallableKind kind);

    List<A> loadPropertyBackingFieldAnnotations(ProtoContainer container, ProtoBuf.Property proto);

    List<A> loadPropertyDelegateFieldAnnotations(ProtoContainer container, ProtoBuf.Property proto);

    List<A> loadTypeAnnotations(ProtoBuf.Type proto, NameResolver nameResolver);

    List<A> loadTypeParameterAnnotations(ProtoBuf.TypeParameter proto, NameResolver nameResolver);

    List<A> loadValueParameterAnnotations(ProtoContainer container, MessageLite callableProto, AnnotatedCallableKind kind, int parameterIndex, ProtoBuf.ValueParameter proto);
}
