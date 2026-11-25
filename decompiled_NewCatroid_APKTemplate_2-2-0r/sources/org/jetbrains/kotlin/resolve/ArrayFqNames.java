package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.lower.VarargLoweringKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ArrayFqNames.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00070\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00070\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ArrayFqNames;", "", "()V", "ARRAY_CALL_FQ_NAMES", "", "Lorg/jetbrains/kotlin/name/FqName;", "ARRAY_CALL_NAMES", "Lorg/jetbrains/kotlin/name/Name;", "getARRAY_CALL_NAMES", "()Ljava/util/Set;", "ARRAY_OF_FUNCTION", "getARRAY_OF_FUNCTION", "()Lorg/jetbrains/kotlin/name/Name;", "EMPTY_ARRAY", "getEMPTY_ARRAY", "PRIMITIVE_TYPE_TO_ARRAY", "", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPRIMITIVE_TYPE_TO_ARRAY", "()Ljava/util/Map;", "UNSIGNED_TYPE_TO_ARRAY", "getUNSIGNED_TYPE_TO_ARRAY", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayFqNames {
    public static final Set<FqName> ARRAY_CALL_FQ_NAMES;
    private static final Set<Name> ARRAY_CALL_NAMES;
    private static final Name ARRAY_OF_FUNCTION;
    private static final Name EMPTY_ARRAY;
    public static final ArrayFqNames INSTANCE = new ArrayFqNames();
    private static final Map<PrimitiveType, Name> PRIMITIVE_TYPE_TO_ARRAY;
    private static final Map<FqName, Name> UNSIGNED_TYPE_TO_ARRAY;

    private ArrayFqNames() {
    }

    public final Map<PrimitiveType, Name> getPRIMITIVE_TYPE_TO_ARRAY() {
        return PRIMITIVE_TYPE_TO_ARRAY;
    }

    static {
        HashMap mapHashMapOf = MapsKt.hashMapOf(TuplesKt.to(PrimitiveType.BOOLEAN, Name.identifier("booleanArrayOf")), TuplesKt.to(PrimitiveType.CHAR, Name.identifier("charArrayOf")), TuplesKt.to(PrimitiveType.INT, Name.identifier("intArrayOf")), TuplesKt.to(PrimitiveType.BYTE, Name.identifier("byteArrayOf")), TuplesKt.to(PrimitiveType.SHORT, Name.identifier("shortArrayOf")), TuplesKt.to(PrimitiveType.FLOAT, Name.identifier("floatArrayOf")), TuplesKt.to(PrimitiveType.LONG, Name.identifier("longArrayOf")), TuplesKt.to(PrimitiveType.DOUBLE, Name.identifier("doubleArrayOf")));
        PRIMITIVE_TYPE_TO_ARRAY = mapHashMapOf;
        HashMap mapHashMapOf2 = MapsKt.hashMapOf(TuplesKt.to(StandardNames.FqNames.uByteFqName, Name.identifier("ubyteArrayOf")), TuplesKt.to(StandardNames.FqNames.uShortFqName, Name.identifier("ushortArrayOf")), TuplesKt.to(StandardNames.FqNames.uIntFqName, Name.identifier("uintArrayOf")), TuplesKt.to(StandardNames.FqNames.uLongFqName, Name.identifier("ulongArrayOf")));
        UNSIGNED_TYPE_TO_ARRAY = mapHashMapOf2;
        Name nameIdentifier = Name.identifier(VarargLoweringKt.ARRAY_OF_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        ARRAY_OF_FUNCTION = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("emptyArray");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        EMPTY_ARRAY = nameIdentifier2;
        Set<Name> setPlus = SetsKt.plus(SetsKt.plus(SetsKt.setOf((Object[]) new Name[]{nameIdentifier, nameIdentifier2}), (Iterable) CollectionsKt.toSet(mapHashMapOf.values())), (Iterable) CollectionsKt.toSet(mapHashMapOf2.values()));
        ARRAY_CALL_NAMES = setPlus;
        Set<Name> set = setPlus;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(new FqName("kotlin." + ((Name) it.next()).getIdentifier()));
        }
        ARRAY_CALL_FQ_NAMES = CollectionsKt.toSet(arrayList);
    }

    public final Map<FqName, Name> getUNSIGNED_TYPE_TO_ARRAY() {
        return UNSIGNED_TYPE_TO_ARRAY;
    }

    public final Name getARRAY_OF_FUNCTION() {
        return ARRAY_OF_FUNCTION;
    }

    public final Name getEMPTY_ARRAY() {
        return EMPTY_ARRAY;
    }

    public final Set<Name> getARRAY_CALL_NAMES() {
        return ARRAY_CALL_NAMES;
    }
}
