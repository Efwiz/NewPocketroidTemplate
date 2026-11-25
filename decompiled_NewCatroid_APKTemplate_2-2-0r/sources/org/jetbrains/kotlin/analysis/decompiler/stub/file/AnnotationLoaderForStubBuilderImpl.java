package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analysis.decompiler.stub.AnnotationWithArgs;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.AnnotatedCallableKind;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: AnnotationLoaderForStubBuilderImpl.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J&\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\r\u001a\u00020\u0013H\u0016J\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u0015H\u0016J&\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u0018H\u0016J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u0018H\u0016J\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\u0007\u001a\u00020\u001b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\u0007\u001a\u00020\u001d2\u0006\u0010\t\u001a\u00020\nH\u0016J6\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0007\u001a\u00020\"H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/AnnotationLoaderForStubBuilderImpl;", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationLoader;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithArgs;", ConfigConstants.CONFIG_PROTOCOL_SECTION, "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "loadAnnotation", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "loadCallableAnnotations", "", "container", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "loadClassAnnotations", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "loadEnumEntryAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry;", "loadExtensionReceiverParameterAnnotations", "loadPropertyBackingFieldAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "loadPropertyDelegateFieldAnnotations", "loadTypeAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "loadTypeParameterAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "loadValueParameterAnnotations", "callableProto", "parameterIndex", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationLoaderForStubBuilderImpl implements AnnotationLoader<AnnotationWithArgs> {
    private final SerializerExtensionProtocol protocol;

    /* JADX INFO: compiled from: AnnotationLoaderForStubBuilderImpl.kt */
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

    public AnnotationLoaderForStubBuilderImpl(SerializerExtensionProtocol protocol) {
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        this.protocol = protocol;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationWithArgs> loadClassAnnotations(ProtoContainer.Class container) {
        Intrinsics.checkNotNullParameter(container, "container");
        List listEmptyList = (List) container.getClassProto().getExtension(this.protocol.getClassAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(loadAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationWithArgs> loadCallableAnnotations(ProtoContainer container, MessageLite proto, AnnotatedCallableKind kind) {
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
            arrayList.add(loadAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationWithArgs> loadPropertyBackingFieldAnnotations(ProtoContainer container, ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationWithArgs> loadPropertyDelegateFieldAnnotations(ProtoContainer container, ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationWithArgs> loadEnumEntryAnnotations(ProtoContainer container, ProtoBuf.EnumEntry proto) {
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
            arrayList.add(loadAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationWithArgs> loadValueParameterAnnotations(ProtoContainer container, MessageLite callableProto, AnnotatedCallableKind kind, int parameterIndex, ProtoBuf.ValueParameter proto) {
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
            arrayList.add(loadAnnotation((ProtoBuf.Annotation) it.next(), container.getNameResolver()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationWithArgs> loadExtensionReceiverParameterAnnotations(ProtoContainer container, MessageLite proto, AnnotatedCallableKind kind) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationWithArgs> loadTypeAnnotations(ProtoBuf.Type proto, NameResolver nameResolver) {
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
            arrayList.add(loadAnnotation((ProtoBuf.Annotation) it.next(), nameResolver));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<AnnotationWithArgs> loadTypeParameterAnnotations(ProtoBuf.TypeParameter proto, NameResolver nameResolver) {
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
            arrayList.add(loadAnnotation((ProtoBuf.Annotation) it.next(), nameResolver));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public AnnotationWithArgs loadAnnotation(ProtoBuf.Annotation proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List<ProtoBuf.Annotation.Argument> argumentList = proto.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
        List<ProtoBuf.Annotation.Argument> list = argumentList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (ProtoBuf.Annotation.Argument argument : list) {
            Name name2 = NameResolverUtilKt.getName(nameResolver, argument.getNameId());
            ProtoBuf.Annotation.Argument.Value value = argument.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            Pair pair = TuplesKt.to(name2, KotlinConstantValueKt.createConstantValue(value, nameResolver));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return new AnnotationWithArgs(NameResolverUtilKt.getClassId(nameResolver, proto.getId()), linkedHashMap);
    }
}
