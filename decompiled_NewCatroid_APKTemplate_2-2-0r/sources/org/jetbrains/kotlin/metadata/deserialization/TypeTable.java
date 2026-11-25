package org.jetbrains.kotlin.metadata.deserialization;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: TypeTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "", "typeTable", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;)V", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "getTypes", "()Ljava/util/List;", "get", "index", "", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeTable {
    private final List<ProtoBuf.Type> types;

    public TypeTable(ProtoBuf.TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        ArrayList typeList = typeTable.getTypeList();
        if (typeTable.hasFirstNullable()) {
            int firstNullable = typeTable.getFirstNullable();
            List<ProtoBuf.Type> typeList2 = typeTable.getTypeList();
            Intrinsics.checkNotNullExpressionValue(typeList2, "getTypeList(...)");
            List<ProtoBuf.Type> list = typeList2;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            int i = 0;
            for (Object obj : list) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                ProtoBuf.Type typeBuild = (ProtoBuf.Type) obj;
                if (i >= firstNullable) {
                    typeBuild = typeBuild.toBuilder().setNullable(true).build();
                }
                arrayList.add(typeBuild);
                i = i2;
            }
            typeList = arrayList;
        }
        Intrinsics.checkNotNullExpressionValue(typeList, "run(...)");
        this.types = typeList;
    }

    public final List<ProtoBuf.Type> getTypes() {
        return this.types;
    }

    public final ProtoBuf.Type get(int index) {
        return this.types.get(index);
    }
}
