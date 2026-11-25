package com.huawei.hms.mlsdk.asr.o;

import android.os.Build;
import com.huawei.hms.framework.common.EmuiUtil;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: EmuiUtil.java */
/* JADX INFO: loaded from: classes10.dex */
public class g {
    private static int a = -1;

    static {
        Object obj;
        int iIntValue;
        String str;
        try {
            try {
                Class<?> cls = Class.forName(EmuiUtil.BUILDEX_VERSION);
                Field declaredField = cls.getDeclaredField(EmuiUtil.EMUI_SDK_INT);
                AccessibleObject.setAccessible(new Field[]{declaredField}, true);
                obj = declaredField.get(cls);
                if (obj != null) {
                    try {
                        iIntValue = ((Integer) obj).intValue();
                    } catch (ClassCastException unused) {
                        SmartLogger.e("EmuiUtil", "ClassCastException: getEMUIVersionCode is not a number " + obj);
                        iIntValue = 0;
                    }
                } else {
                    iIntValue = 0;
                }
            } catch (ClassCastException unused2) {
                obj = null;
            }
        } catch (ClassNotFoundException unused3) {
            SmartLogger.e("EmuiUtil", "ClassNotFoundException: ");
        } catch (IllegalAccessException unused4) {
            SmartLogger.e("EmuiUtil", "IllegalAccessException: ");
        } catch (NoSuchFieldException unused5) {
            SmartLogger.e("EmuiUtil", "NoSuchFieldException: ");
        }
        SmartLogger.i("EmuiUtil", "emuiVersionCodeValue: " + iIntValue);
        SmartLogger.d("EmuiUtil", "getEmuiType emuiVersionCode=" + iIntValue);
        if (iIntValue >= 17) {
            a = 90;
        } else if (iIntValue >= 15) {
            a = 81;
        } else if (iIntValue >= 14) {
            a = 60;
        } else if (iIntValue >= 11) {
            a = 50;
        } else if (iIntValue >= 10) {
            a = 41;
        } else if (iIntValue >= 9) {
            a = 40;
        } else if (iIntValue >= 8) {
            a = 31;
        } else if (iIntValue >= 7) {
            a = 30;
        }
        if (a == -1) {
            try {
                Class<?> cls2 = Class.forName("android.os.SystemProperties");
                Method declaredMethod = cls2.getDeclaredMethod("get", String.class);
                str = Build.BRAND.equals("HONOR") ? (String) declaredMethod.invoke(cls2, "ro.build.version.magic") : (String) declaredMethod.invoke(cls2, "ro.build.version.emui");
            } catch (ClassNotFoundException unused6) {
                SmartLogger.e("EmuiUtil", Constant.CLASS_NOT_FIND_EXCEPTION);
                str = "";
            } catch (IllegalAccessException unused7) {
                SmartLogger.e("EmuiUtil", "IllegalAccessException");
                str = "";
            } catch (NoSuchMethodException unused8) {
                SmartLogger.e("EmuiUtil", Constant.NO_SUCH_METHOD_EXCEPTION);
                str = "";
            } catch (InvocationTargetException unused9) {
                SmartLogger.e("EmuiUtil", "InvocationTargetException");
                str = "";
            }
            SmartLogger.i("EmuiUtil", "Emui version name: " + str);
            if (str != null) {
                if (str.contains("EmotionUI_3.0")) {
                    a = 30;
                    return;
                }
                if (str.contains("EmotionUI_3.1")) {
                    a = 31;
                    return;
                }
                if (str.contains("EmotionUI_4.0")) {
                    a = 40;
                    return;
                }
                if (str.contains("EmotionUI_4.1")) {
                    a = 41;
                } else if (str.contains("EmotionUI_5.0")) {
                    a = 50;
                } else if (str.contains("EmotionUI_6.0")) {
                    a = 60;
                }
            }
        }
    }

    public static boolean a() {
        return a != -1;
    }
}
