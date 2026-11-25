package com.gaurav.avnc.vnc;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;

/* JADX INFO: compiled from: XTKeyCode.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/gaurav/avnc/vnc/XTKeyCode;", "", "()V", "LinuxToQnum", "", "fromAndroidScancode", "", "scancode", "vncclient_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class XTKeyCode {
    public static final XTKeyCode INSTANCE = new XTKeyCode();
    private static final int[] LinuxToQnum = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 118, 86, 87, 88, 115, 120, 119, 121, 112, 123, 92, 156, 157, 181, 84, 184, 91, 199, 200, 201, 203, 205, 207, 208, 209, 210, 211, XKeySym.XK_idiaeresis, 160, 174, 176, 222, 89, 206, 198, 139, 126, 114, 113, 125, 219, 220, 221, XKeySym.XK_egrave, 133, 134, 135, 140, 248, 100, 101, 193, 188, 245, 158, 161, 102, 223, XKeySym.XK_atilde, 103, 104, 105, 147, 159, 151, 130, 106, 146, 107, 166, XKeySym.XK_igrave, XKeySym.XK_ae, XKeySym.XK_ediaeresis, XKeySym.XK_ecircumflex, XKeySym.XK_eacute, 163, 108, 253, 153, 162, 144, 164, 177, 152, 99, 0, 129, 178, XKeySym.XK_ccedilla, 0, 0, 136, 117, 143, 246, 251, 137, 138, 93, 94, 95, 85, 131, 247, 132, 90, 116, 249, 109, 111, 149, 150, 154, 155, 167, 168, 169, 171, 172, 173, 165, 175, 179, 180, 182, 185, 186, 187, 189, 190, 191, 192, XKeySym.XK_aring, 194, 195, 196, 197, 148, 202, 204, 212, XKeySym.XK_iacute, 214, 215, 216, 217, 218, XKeySym.XK_adiaeresis, 142, 213, 240, XKeySym.XK_ntilde, 242, 243, 244};

    private XTKeyCode() {
    }

    public final int fromAndroidScancode(int scancode) {
        Integer orNull = ArraysKt.getOrNull(LinuxToQnum, scancode);
        if (orNull != null) {
            return orNull.intValue();
        }
        return 0;
    }
}
