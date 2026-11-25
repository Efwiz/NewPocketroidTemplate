package org.jetbrains.kotlin.serialization.deserialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AnnotationAndConstantLoaderImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0001B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0018\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J&\u0010\u0012\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J&\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u001eH\u0016J\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020 H\u0016J&\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u0015H\u0016J&\u0010#\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u0015H\u0016J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00020\u00192\u0006\u0010\u000e\u001a\u00020&2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u001e\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00020\u00192\u0006\u0010\u000e\u001a\u00020(2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J6\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010*\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020,2\u0006\u0010\u000e\u001a\u00020-H\u0016R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationAndConstantLoaderImpl;", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationAndConstantLoader;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", ConfigConstants.CONFIG_PROTOCOL_SECTION, "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "deserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationDeserializer;", "loadAnnotation", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "loadAnnotationDefaultValue", "container", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "loadCallableAnnotations", "", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "loadClassAnnotations", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "loadEnumEntryAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry;", "loadExtensionReceiverParameterAnnotations", "loadPropertyBackingFieldAnnotations", "loadPropertyConstant", "loadPropertyDelegateFieldAnnotations", "loadTypeAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "loadTypeParameterAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "loadValueParameterAnnotations", "callableProto", "parameterIndex", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationAndConstantLoaderImpl implements AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> {
    private final AnnotationDeserializer deserializer;
    private final SerializerExtensionProtocol protocol;

    /* JADX INFO: compiled from: AnnotationAndConstantLoaderImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnnotatedCallableKind.values().length];
            try {
                iArr[AnnotatedCallableKind.PROPERTY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AnnotatedCallableKind.PROPERTY_GETTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[AnnotatedCallableKind.PROPERTY_SETTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoader
    public ConstantValue<?> loadAnnotationDefaultValue(ProtoContainer container, ProtoBuf.Property proto, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        return null;
    }

    public AnnotationAndConstantLoaderImpl(ModuleDescriptor module, NotFoundClasses notFoundClasses, SerializerExtensionProtocol protocol) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        this.protocol = protocol;
        this.deserializer = new AnnotationDeserializer(module, notFoundClasses);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationDescriptor> loadClassAnnotations(ProtoContainer.Class container) {
        Intrinsics.checkNotNullParameter(container, "container");
        List listEmptyList = (List) container.getClassProto().getExtension(this.protocol.getClassAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public AnnotationDescriptor loadAnnotation(ProtoBuf.Annotation proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        return this.deserializer.deserializeAnnotation(proto, nameResolver);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationDescriptor> loadCallableAnnotations(ProtoContainer container, MessageLite proto, AnnotatedCallableKind kind) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (proto instanceof ProtoBuf.Constructor) {
            listEmptyList = (List) ((ProtoBuf.Constructor) proto).getExtension(this.protocol.getConstructorAnnotation());
        } else if (proto instanceof ProtoBuf.Function) {
            listEmptyList = (List) ((ProtoBuf.Function) proto).getExtension(this.protocol.getFunctionAnnotation());
        } else {
            if (!(proto instanceof ProtoBuf.Property)) {
                throw new IllegalStateException(("Unknown message: " + proto).toString());
            }
            int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
            if (i == 1) {
                listEmptyList = (List) ((ProtoBuf.Property) proto).getExtension(this.protocol.getPropertyAnnotation());
            } else if (i == 2) {
                listEmptyList = (List) ((ProtoBuf.Property) proto).getExtension(this.protocol.getPropertyGetterAnnotation());
            } else if (i == 3) {
                listEmptyList = (List) ((ProtoBuf.Property) proto).getExtension(this.protocol.getPropertySetterAnnotation());
            } else {
                throw new IllegalStateException("Unsupported callable kind with property proto".toString());
            }
        }
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationDescriptor> loadPropertyBackingFieldAnnotations(ProtoContainer container, ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyBackingFieldAnnotation = this.protocol.getPropertyBackingFieldAnnotation();
        List listEmptyList = propertyBackingFieldAnnotation != null ? (List) proto.getExtension(propertyBackingFieldAnnotation) : null;
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationDescriptor> loadPropertyDelegateFieldAnnotations(ProtoContainer container, ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyDelegatedFieldAnnotation = this.protocol.getPropertyDelegatedFieldAnnotation();
        List listEmptyList = propertyDelegatedFieldAnnotation != null ? (List) proto.getExtension(propertyDelegatedFieldAnnotation) : null;
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationDescriptor> loadEnumEntryAnnotations(ProtoContainer container, ProtoBuf.EnumEntry proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        List listEmptyList = (List) proto.getExtension(this.protocol.getEnumEntryAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationDescriptor> loadValueParameterAnnotations(ProtoContainer container, MessageLite callableProto, AnnotatedCallableKind kind, int parameterIndex, ProtoBuf.ValueParameter proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(callableProto, "callableProto");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(proto, "proto");
        List listEmptyList = (List) proto.getExtension(this.protocol.getParameterAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationDescriptor> loadExtensionReceiverParameterAnnotations(ProtoContainer container, MessageLite proto, AnnotatedCallableKind kind) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(kind, "kind");
        List listEmptyList = null;
        if (proto instanceof ProtoBuf.Function) {
            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionExtensionReceiverAnnotation = this.protocol.getFunctionExtensionReceiverAnnotation();
            if (functionExtensionReceiverAnnotation != null) {
                listEmptyList = (List) ((ProtoBuf.Function) proto).getExtension(functionExtensionReceiverAnnotation);
            }
        } else {
            if (!(proto instanceof ProtoBuf.Property)) {
                throw new IllegalStateException(("Unknown message: " + proto).toString());
            }
            int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyExtensionReceiverAnnotation = this.protocol.getPropertyExtensionReceiverAnnotation();
                if (propertyExtensionReceiverAnnotation != null) {
                    listEmptyList = (List) ((ProtoBuf.Property) proto).getExtension(propertyExtensionReceiverAnnotation);
                }
            } else {
                throw new IllegalStateException(("Unsupported callable kind with property proto for receiver annotations: " + kind).toString());
            }
        }
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationDescriptor> loadTypeAnnotations(ProtoBuf.Type proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List listEmptyList = (List) proto.getExtension(this.protocol.getTypeAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation) it.next(), nameResolver));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationDescriptor> loadTypeParameterAnnotations(ProtoBuf.TypeParameter proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List listEmptyList = (List) proto.getExtension(this.protocol.getTypeParameterAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation) it.next(), nameResolver));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoader
    public ConstantValue<?> loadPropertyConstant(ProtoContainer container, ProtoBuf.Property proto, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        ProtoBuf.Annotation.Argument.Value value = (ProtoBuf.Annotation.Argument.Value) ProtoBufUtilKt.getExtensionOrNull(proto, this.protocol.getCompileTimeValue());
        if (value == null) {
            return null;
        }
        return this.deserializer.resolveValue(expectedType, value, container.getNameResolver());
    }
}
