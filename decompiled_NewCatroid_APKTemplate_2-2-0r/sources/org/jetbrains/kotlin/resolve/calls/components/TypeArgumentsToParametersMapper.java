package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.SimpleTypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgumentPlaceholder;
import org.jetbrains.kotlin.resolve.calls.model.WrongCountOfTypeArguments;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: TypeArgumentsToParametersMapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper;", "", "()V", "mapTypeArguments", "Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping;", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "TypeArgumentsMapping", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeArgumentsToParametersMapper {

    /* JADX INFO: compiled from: TypeArgumentsToParametersMapper.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00020\u0001:\u0002\u000e\u000fB\u0015\b\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H&R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0001\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping;", "", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "(Ljava/util/List;)V", "getDiagnostics", "()Ljava/util/List;", "getTypeArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "typeParameterDescriptor", "NoExplicitArguments", "TypeArgumentsMappingImpl", "Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping$NoExplicitArguments;", "Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping$TypeArgumentsMappingImpl;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class TypeArgumentsMapping implements Iterable<Map.Entry<? extends TypeParameterDescriptor, ? extends KotlinType>>, KMappedMarker {
        private final List<KotlinCallDiagnostic> diagnostics;

        public /* synthetic */ TypeArgumentsMapping(List list, DefaultConstructorMarker defaultConstructorMarker) {
            this(list);
        }

        public abstract TypeArgument getTypeArgument(TypeParameterDescriptor typeParameterDescriptor);

        @Override // java.lang.Iterable
        public Iterator<Map.Entry<? extends TypeParameterDescriptor, ? extends KotlinType>> iterator() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        /* JADX WARN: Multi-variable type inference failed */
        private TypeArgumentsMapping(List<? extends KotlinCallDiagnostic> list) {
            this.diagnostics = list;
        }

        public final List<KotlinCallDiagnostic> getDiagnostics() {
            return this.diagnostics;
        }

        /* JADX INFO: compiled from: TypeArgumentsToParametersMapper.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0010&\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001b\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u0004H\u0096\u0002R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping$NoExplicitArguments;", "Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping;", "()V", "emptyIterator", "", "", "", "getTypeArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "iterator", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NoExplicitArguments extends TypeArgumentsMapping {
            public static final NoExplicitArguments INSTANCE = new NoExplicitArguments();
            private static final Iterator<Map.Entry> emptyIterator = MapsKt.emptyMap().entrySet().iterator();

            private NoExplicitArguments() {
                super(CollectionsKt.emptyList(), null);
            }

            @Override // org.jetbrains.kotlin.resolve.calls.components.TypeArgumentsToParametersMapper.TypeArgumentsMapping
            public TypeArgument getTypeArgument(TypeParameterDescriptor typeParameterDescriptor) {
                Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
                return TypeArgumentPlaceholder.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.components.TypeArgumentsToParametersMapper.TypeArgumentsMapping, java.lang.Iterable
            public Iterator<Map.Entry<? extends TypeParameterDescriptor, ? extends KotlinType>> iterator() {
                return emptyIterator;
            }
        }

        /* JADX INFO: compiled from: TypeArgumentsToParametersMapper.kt */
        @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010(\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B'\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0007H\u0016J\u001d\u0010\f\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e0\rH\u0096\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping$TypeArgumentsMappingImpl;", "Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "typeParameterToArgumentMap", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "(Ljava/util/List;Ljava/util/Map;)V", "getTypeArgument", "typeParameterDescriptor", "iterator", "", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class TypeArgumentsMappingImpl extends TypeArgumentsMapping {
            private final Map<TypeParameterDescriptor, TypeArgument> typeParameterToArgumentMap;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public TypeArgumentsMappingImpl(List<? extends KotlinCallDiagnostic> diagnostics, Map<TypeParameterDescriptor, ? extends TypeArgument> typeParameterToArgumentMap) {
                super(diagnostics, null);
                Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
                Intrinsics.checkNotNullParameter(typeParameterToArgumentMap, "typeParameterToArgumentMap");
                this.typeParameterToArgumentMap = typeParameterToArgumentMap;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.components.TypeArgumentsToParametersMapper.TypeArgumentsMapping
            public TypeArgument getTypeArgument(TypeParameterDescriptor typeParameterDescriptor) {
                Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
                TypeArgument typeArgument = this.typeParameterToArgumentMap.get(typeParameterDescriptor);
                return typeArgument == null ? TypeArgumentPlaceholder.INSTANCE : typeArgument;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.components.TypeArgumentsToParametersMapper.TypeArgumentsMapping, java.lang.Iterable
            public Iterator<Map.Entry<? extends TypeParameterDescriptor, ? extends KotlinType>> iterator() {
                Map<TypeParameterDescriptor, TypeArgument> map = this.typeParameterToArgumentMap;
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
                Iterator<T> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    UnwrappedType type = null;
                    SimpleTypeArgument simpleTypeArgument = value instanceof SimpleTypeArgument ? (SimpleTypeArgument) value : null;
                    if (simpleTypeArgument != null) {
                        type = simpleTypeArgument.getType();
                    }
                    linkedHashMap.put(key, type);
                }
                return linkedHashMap.entrySet().iterator();
            }
        }
    }

    public final TypeArgumentsMapping mapTypeArguments(KotlinCall call, CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (call.getTypeArguments().isEmpty()) {
            return TypeArgumentsMapping.NoExplicitArguments.INSTANCE;
        }
        if (call.getTypeArguments().size() != descriptor.getTypeParameters().size()) {
            return new TypeArgumentsMapping.TypeArgumentsMappingImpl(CollectionsKt.listOf(new WrongCountOfTypeArguments(descriptor, call.getTypeArguments().size())), MapsKt.emptyMap());
        }
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<Pair> listZip = CollectionsKt.zip(typeParameters, call.getTypeArguments());
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZip, 10)), 16));
        for (Pair pair : listZip) {
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return new TypeArgumentsMapping.TypeArgumentsMappingImpl(CollectionsKt.emptyList(), linkedHashMap);
    }
}
