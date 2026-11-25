package com.gaurav.avnc.vnc;

import kotlin.Metadata;

/* JADX INFO: compiled from: XKeySymAndroid.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/gaurav/avnc/vnc/XKeySymAndroid;", "", "()V", "AndroidKeyCodeToXKeySym", "", "getKeySymForAndroidKeyCode", "", "keyCode", "vncclient_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class XKeySymAndroid {
    public static final XKeySymAndroid INSTANCE = new XKeySymAndroid();
    private static final int[] AndroidKeyCodeToXKeySym = {0, 0, 0, 0, 0, 0, 0, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 42, 35, XKeySym.XK_Up, XKeySym.XK_Down, XKeySym.XK_Left, XKeySym.XK_Right, 0, XKeySym.XF86XK_AudioRaiseVolume, XKeySym.XF86XK_AudioLowerVolume, 0, 0, 0, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 44, 46, XKeySym.XK_Alt_L, XKeySym.XK_Alt_R, XKeySym.XK_Shift_L, XKeySym.XK_Shift_R, XKeySym.XK_Tab, 32, 0, 0, 0, XKeySym.XK_Return, XKeySym.XK_BackSpace, 96, 45, 61, 91, 93, 92, 59, 39, 47, 64, 0, 0, 0, 43, XKeySym.XK_Menu, 0, 0, 0, 0, 0, 0, 0, 0, 0, 65365, 65366, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, XKeySym.XK_Escape, 65535, XKeySym.XK_Control_L, XKeySym.XK_Control_R, XKeySym.XK_Caps_Lock, 65300, XKeySym.XK_Super_L, XKeySym.XK_Super_R, 0, 65301, XKeySym.XK_Break, XKeySym.XK_Home, XKeySym.XK_End, XKeySym.XK_Insert, 0, 0, 0, 0, 0, 0, XKeySym.XK_F1, XKeySym.XK_F2, XKeySym.XK_F3, XKeySym.XK_F4, XKeySym.XK_F5, XKeySym.XK_F6, XKeySym.XK_F7, XKeySym.XK_F8, XKeySym.XK_F9, XKeySym.XK_F10, 65480, 65481, XKeySym.XK_Num_Lock, XKeySym.XK_KP_0, XKeySym.XK_KP_1, XKeySym.XK_KP_2, XKeySym.XK_KP_3, XKeySym.XK_KP_4, XKeySym.XK_KP_5, XKeySym.XK_KP_6, XKeySym.XK_KP_7, XKeySym.XK_KP_8, XKeySym.XK_KP_9, XKeySym.XK_KP_Divide, XKeySym.XK_KP_Multiply, XKeySym.XK_KP_Subtract, XKeySym.XK_KP_Add, XKeySym.XK_KP_Decimal, XKeySym.XK_KP_Separator, XKeySym.XK_KP_Enter, XKeySym.XK_KP_Equal, 0, 0, XKeySym.XF86XK_AudioMute, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private XKeySymAndroid() {
    }

    public final int getKeySymForAndroidKeyCode(int keyCode) {
        if (keyCode < 0) {
            return 0;
        }
        int[] iArr = AndroidKeyCodeToXKeySym;
        if (keyCode < iArr.length) {
            return iArr[keyCode];
        }
        return 0;
    }
}
