package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.math.raw.Nat;

/* JADX INFO: loaded from: classes13.dex */
public class FixedPointCombMultiplier extends AbstractECMultiplier {
    protected int getWidthForCombSize(int i) {
        return i > 257 ? 6 : 5;
    }

    @Override // org.bouncycastle.math.ec.AbstractECMultiplier
    protected ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        ECCurve curve = eCPoint.getCurve();
        int combSize = FixedPointUtil.getCombSize(curve);
        if (bigInteger.bitLength() > combSize) {
            throw new IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
        }
        FixedPointPreCompInfo fixedPointPreCompInfoPrecompute = FixedPointUtil.precompute(eCPoint);
        ECLookupTable lookupTable = fixedPointPreCompInfoPrecompute.getLookupTable();
        int width = fixedPointPreCompInfoPrecompute.getWidth();
        int i = ((combSize + width) - 1) / width;
        ECPoint infinity = curve.getInfinity();
        int i2 = width * i;
        int[] iArrFromBigInteger = Nat.fromBigInteger(i2, bigInteger);
        int i3 = i2 - 1;
        for (int i4 = 0; i4 < i; i4++) {
            int bit = 0;
            for (int i5 = i3 - i4; i5 >= 0; i5 -= i) {
                bit = (bit << 1) | Nat.getBit(iArrFromBigInteger, i5);
            }
            infinity = infinity.twicePlus(lookupTable.lookup(bit));
        }
        return infinity.add(fixedPointPreCompInfoPrecompute.getOffset());
    }
}
