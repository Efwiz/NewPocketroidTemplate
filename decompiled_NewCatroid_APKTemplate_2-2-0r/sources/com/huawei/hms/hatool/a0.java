package com.huawei.hms.hatool;

import android.util.Log;

/* JADX INFO: loaded from: classes10.dex */
public class a0 {
    public boolean a = false;
    public int b = 4;

    public static String a() {
        return "FormalHASDK_2.2.0.313" + l1.a();
    }

    public void a(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator()).append("======================================= ").append(System.lineSeparator()).append(a()).append("").append(System.lineSeparator()).append("=======================================");
        Log.i("FormalHASDK", sb.toString());
        this.b = i;
        this.a = true;
    }

    public void a(int i, String str, String str2) {
        if (i == 3) {
            Log.d(str, str2);
            return;
        }
        if (i == 5) {
            Log.w(str, str2);
        } else if (i != 6) {
            Log.i(str, str2);
        } else {
            Log.e(str, str2);
        }
    }

    public void b(int i, String str, String str2) {
        a(i, "FormalHASDK", str + "=> " + str2);
    }

    public boolean b(int i) {
        return this.a && i >= this.b;
    }
}
