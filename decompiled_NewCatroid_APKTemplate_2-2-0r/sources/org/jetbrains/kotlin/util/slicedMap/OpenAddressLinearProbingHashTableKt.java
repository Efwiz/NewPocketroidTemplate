package org.jetbrains.kotlin.util.slicedMap;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: OpenAddressLinearProbingHashTable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\u001a7\u0010\b\u001a\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00022\b\u0010\r\u001a\u0004\u0018\u00010\u0002H\u0002¢\u0006\u0002\u0010\u000e\u001a\u0015\u0010\u000f\u001a\u00020\u0005*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0005H\u0082\b\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"EMPTY_ARRAY", "", "", "[Ljava/lang/Object;", "MAGIC", "", "MAX_SHIFT", "THRESHOLD", "put", "", "array", "aShift", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "([Ljava/lang/Object;ILjava/lang/Object;Ljava/lang/Object;)Z", "computeHash", "shift", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OpenAddressLinearProbingHashTableKt {
    private static final Object[] EMPTY_ARRAY = new Object[0];

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean put(Object[] objArr, int i, Object obj, Object obj2) {
        int iHashCode = ((obj.hashCode() * (-1640531527)) >>> i) << 1;
        while (true) {
            Object obj3 = objArr[iHashCode];
            if (obj3 == null) {
                objArr[iHashCode] = obj;
                objArr[iHashCode + 1] = obj2;
                return true;
            }
            if (Intrinsics.areEqual(obj3, obj)) {
                objArr[iHashCode + 1] = obj2;
                return false;
            }
            if (iHashCode == 0) {
                iHashCode = objArr.length;
            }
            iHashCode -= 2;
        }
    }
}
