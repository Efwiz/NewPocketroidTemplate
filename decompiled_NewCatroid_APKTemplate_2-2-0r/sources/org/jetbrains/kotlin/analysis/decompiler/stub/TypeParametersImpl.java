package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;

/* JADX INFO: compiled from: ClsStubBuilderContext.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0001¢\u0006\u0002\u0010\bJ\u0011\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000bH\u0096\u0002R\u000e\u0010\u0007\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeParametersImpl;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeParameters;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeParameterProtos", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "parent", "(Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Ljava/util/Collection;Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeParameters;)V", "typeParametersById", "", "", "Lorg/jetbrains/kotlin/name/Name;", "get", "id", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeParametersImpl implements TypeParameters {
    private final TypeParameters parent;
    private final Map<Integer, Name> typeParametersById;

    public TypeParametersImpl(NameResolver nameResolver, Collection<ProtoBuf.TypeParameter> typeParameterProtos, TypeParameters parent) {
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeParameterProtos, "typeParameterProtos");
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.parent = parent;
        Collection<ProtoBuf.TypeParameter> collection = typeParameterProtos;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (ProtoBuf.TypeParameter typeParameter : collection) {
            arrayList.add(new Pair(Integer.valueOf(typeParameter.getId()), NameResolverUtilKt.getName(nameResolver, typeParameter.getName())));
        }
        this.typeParametersById = MapsKt.toMap(arrayList);
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.TypeParameters
    public Name get(int id2) {
        Name name2 = this.typeParametersById.get(Integer.valueOf(id2));
        return name2 == null ? this.parent.get(id2) : name2;
    }
}
