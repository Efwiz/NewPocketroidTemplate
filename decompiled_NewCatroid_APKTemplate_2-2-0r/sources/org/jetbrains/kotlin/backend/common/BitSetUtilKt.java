package org.jetbrains.kotlin.backend.common;

import java.util.BitSet;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: BitSetUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001\u001a$\u0010\u0002\u001a\u00020\u0003*\u00020\u00012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Ljava/util/BitSet;", "forEachBit", "", "block", "Lkotlin/Function1;", "", "backend-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BitSetUtilKt {
    public static final BitSet copy(BitSet bitSet) {
        Intrinsics.checkNotNullParameter(bitSet, "<this>");
        BitSet bitSet2 = new BitSet(bitSet.size());
        bitSet2.or(bitSet);
        return bitSet2;
    }

    public static final void forEachBit(BitSet bitSet, Function1<? super Integer, Unit> block) {
        Intrinsics.checkNotNullParameter(bitSet, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        int iNextSetBit = -1;
        while (true) {
            iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1);
            if (iNextSetBit < 0) {
                return;
            } else {
                block.invoke(Integer.valueOf(iNextSetBit));
            }
        }
    }
}
