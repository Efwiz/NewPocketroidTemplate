package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import io.netty.util.internal.StringUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ConstantData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\t\n\u0002\b\u0004\u001a\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0001H\u0002\u001a\n\u0010\t\u001a\u00020\n*\u00020\u000b\u001a\n\u0010\t\u001a\u00020\n*\u00020\u0001\u001a\"\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"BYTE_SIZE_BYTES", "", "CHAR_SIZE_BYTES", "INT_SIZE_BYTES", "LONG_SIZE_BYTES", "SHORT_SIZE_BYTES", "addressToString", "", "address", "toLittleEndianBytes", "", "", "toLittleEndianBytesTo", "", "", "to", TypedValues.CycleType.S_WAVE_OFFSET, "size", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstantDataKt {
    public static final int BYTE_SIZE_BYTES = 1;
    public static final int CHAR_SIZE_BYTES = 2;
    public static final int INT_SIZE_BYTES = 4;
    public static final int LONG_SIZE_BYTES = 8;
    public static final int SHORT_SIZE_BYTES = 2;

    /* JADX INFO: Access modifiers changed from: private */
    public static final String addressToString(int i) {
        return StringsKt.padEnd(String.valueOf(i), 6, StringUtil.SPACE);
    }

    public static final void toLittleEndianBytesTo(long j, byte[] to, int i, int i2) {
        Intrinsics.checkNotNullParameter(to, "to");
        for (int i3 = 0; i3 < i2; i3++) {
            to[i + i3] = (byte) (j >>> (i3 * 8));
        }
    }

    public static final byte[] toLittleEndianBytes(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = (byte) (i >>> (i2 * 8));
        }
        return bArr;
    }

    public static final byte[] toLittleEndianBytes(char c) {
        return new byte[]{(byte) (c & 255), (byte) (c >>> '\b')};
    }
}
