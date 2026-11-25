package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract /* synthetic */ class AbstractC0908Cs {
    public static /* synthetic */ void a(int i, String str) {
        if (i == 0) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String name2 = AbstractC0936Ds.class.getName();
            int i2 = 0;
            while (!stackTrace[i2].getClassName().equals(name2)) {
                i2++;
            }
            while (stackTrace[i2].getClassName().equals(name2)) {
                i2++;
            }
            StackTraceElement stackTraceElement = stackTrace[i2];
            throw ((NullPointerException) AbstractC0936Ds.a(AbstractC0936Ds.class.getName(), (RuntimeException) new NullPointerException("Parameter specified as non-null is null: method " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ", parameter " + str)));
        }
    }
}
