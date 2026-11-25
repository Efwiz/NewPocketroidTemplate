package org.jetbrains.kotlin.fir.deserialization;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;

/* JADX INFO: compiled from: AbstractFirDeserializedSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR-\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R-\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0013\u001a\u0004\b\u0015\u0010\u0011R-\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0013\u001a\u0004\b\u0018\u0010\u0011¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/PackagePartsCacheData;", "", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "context", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;)V", "getContext", "()Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "topLevelFunctionNameIndex", "", "Lorg/jetbrains/kotlin/name/Name;", "", "", "getTopLevelFunctionNameIndex", "()Ljava/util/Map;", "topLevelFunctionNameIndex$delegate", "Lkotlin/Lazy;", "topLevelPropertyNameIndex", "getTopLevelPropertyNameIndex", "topLevelPropertyNameIndex$delegate", "typeAliasNameIndex", "getTypeAliasNameIndex", "typeAliasNameIndex$delegate", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackagePartsCacheData {
    private final FirDeserializationContext context;
    private final ProtoBuf.Package proto;

    /* JADX INFO: renamed from: topLevelFunctionNameIndex$delegate, reason: from kotlin metadata */
    private final Lazy topLevelFunctionNameIndex;

    /* JADX INFO: renamed from: topLevelPropertyNameIndex$delegate, reason: from kotlin metadata */
    private final Lazy topLevelPropertyNameIndex;

    /* JADX INFO: renamed from: typeAliasNameIndex$delegate, reason: from kotlin metadata */
    private final Lazy typeAliasNameIndex;

    public PackagePartsCacheData(ProtoBuf.Package proto, FirDeserializationContext context) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(context, "context");
        this.proto = proto;
        this.context = context;
        this.topLevelFunctionNameIndex = LazyKt.lazy(new Function0<Map<Name, ? extends List<? extends Integer>>>() { // from class: org.jetbrains.kotlin.fir.deserialization.PackagePartsCacheData$topLevelFunctionNameIndex$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends List<? extends Integer>> invoke() {
                List<ProtoBuf.Function> functionList = this.this$0.getProto().getFunctionList();
                Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
                Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(functionList);
                PackagePartsCacheData packagePartsCacheData = this.this$0;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (IndexedValue indexedValue : iterableWithIndex) {
                    Name name2 = NameResolverUtilKt.getName(packagePartsCacheData.getContext().getNameResolver(), ((ProtoBuf.Function) indexedValue.getValue()).getName());
                    Object obj = linkedHashMap.get(name2);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        linkedHashMap.put(name2, obj);
                    }
                    ((List) obj).add(Integer.valueOf(indexedValue.getIndex()));
                }
                return linkedHashMap;
            }
        });
        this.topLevelPropertyNameIndex = LazyKt.lazy(new Function0<Map<Name, ? extends List<? extends Integer>>>() { // from class: org.jetbrains.kotlin.fir.deserialization.PackagePartsCacheData$topLevelPropertyNameIndex$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends List<? extends Integer>> invoke() {
                List<ProtoBuf.Property> propertyList = this.this$0.getProto().getPropertyList();
                Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
                Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(propertyList);
                PackagePartsCacheData packagePartsCacheData = this.this$0;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (IndexedValue indexedValue : iterableWithIndex) {
                    Name name2 = NameResolverUtilKt.getName(packagePartsCacheData.getContext().getNameResolver(), ((ProtoBuf.Property) indexedValue.getValue()).getName());
                    Object obj = linkedHashMap.get(name2);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        linkedHashMap.put(name2, obj);
                    }
                    ((List) obj).add(Integer.valueOf(indexedValue.getIndex()));
                }
                return linkedHashMap;
            }
        });
        this.typeAliasNameIndex = LazyKt.lazy(new Function0<Map<Name, ? extends List<? extends Integer>>>() { // from class: org.jetbrains.kotlin.fir.deserialization.PackagePartsCacheData$typeAliasNameIndex$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends List<? extends Integer>> invoke() {
                List<ProtoBuf.TypeAlias> typeAliasList = this.this$0.getProto().getTypeAliasList();
                Intrinsics.checkNotNullExpressionValue(typeAliasList, "getTypeAliasList(...)");
                Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(typeAliasList);
                PackagePartsCacheData packagePartsCacheData = this.this$0;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (IndexedValue indexedValue : iterableWithIndex) {
                    Name name2 = NameResolverUtilKt.getName(packagePartsCacheData.getContext().getNameResolver(), ((ProtoBuf.TypeAlias) indexedValue.getValue()).getName());
                    Object obj = linkedHashMap.get(name2);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        linkedHashMap.put(name2, obj);
                    }
                    ((List) obj).add(Integer.valueOf(indexedValue.getIndex()));
                }
                return linkedHashMap;
            }
        });
    }

    public final ProtoBuf.Package getProto() {
        return this.proto;
    }

    public final FirDeserializationContext getContext() {
        return this.context;
    }

    public final Map<Name, List<Integer>> getTopLevelFunctionNameIndex() {
        return (Map) this.topLevelFunctionNameIndex.getValue();
    }

    public final Map<Name, List<Integer>> getTopLevelPropertyNameIndex() {
        return (Map) this.topLevelPropertyNameIndex.getValue();
    }

    public final Map<Name, List<Integer>> getTypeAliasNameIndex() {
        return (Map) this.typeAliasNameIndex.getValue();
    }
}
