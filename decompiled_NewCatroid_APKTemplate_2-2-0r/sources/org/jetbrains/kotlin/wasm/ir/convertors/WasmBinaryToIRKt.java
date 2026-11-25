package org.jetbrains.kotlin.wasm.ir.convertors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.wasm.ir.OperatorsKt;

/* JADX INFO: compiled from: WasmBinaryToIR.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0010\b\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"twoByteOpcodes", "", "", "getTwoByteOpcodes", "()Ljava/util/Set;", "wasm.ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class WasmBinaryToIRKt {
    private static final Set<Integer> twoByteOpcodes;

    public static final Set<Integer> getTwoByteOpcodes() {
        return twoByteOpcodes;
    }

    static {
        Set<Integer> setKeySet = OperatorsKt.getOpcodesToOp().keySet();
        ArrayList arrayList = new ArrayList();
        for (Object obj : setKeySet) {
            if (((Number) obj).intValue() > 255) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(Integer.valueOf(((Number) it.next()).intValue() >>> 8));
        }
        twoByteOpcodes = CollectionsKt.toSet(arrayList3);
    }
}
