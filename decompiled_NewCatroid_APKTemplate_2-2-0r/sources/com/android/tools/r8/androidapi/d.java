package com.android.tools.r8.androidapi;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.EnumC3891f;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnectHandler;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class d {
    static void a(C0722p1 c0722p1, BiConsumer biConsumer) {
        int i;
        int i2 = 0;
        C0767v2[] c0767v2Arr = {c0722p1.v2, c0722p1.w2};
        int i3 = 0;
        while (true) {
            if (i3 >= 2) {
                break;
            }
            C0767v2 c0767v2 = c0767v2Arr[i3];
            C0684j2 c0684j2A = c0722p1.a(c0767v2, c0722p1.b(c0722p1.A1, new C0767v2[0]), "capacity");
            EnumC3891f enumC3891f = EnumC3891f.B;
            biConsumer.accept(c0684j2A, enumC3891f);
            C0767v2 c0767v3 = c0722p1.A1;
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0767v3, c0767v3), "codePointAt"), enumC3891f);
            C0767v2 c0767v4 = c0722p1.A1;
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0767v4, c0767v4), "codePointBefore"), enumC3891f);
            C0767v2 c0767v5 = c0722p1.A1;
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0767v5, c0767v5, c0767v5), "codePointCount"), enumC3891f);
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, c0722p1.A1), "ensureCapacity"), enumC3891f);
            C0767v2 c0767v6 = c0722p1.D1;
            C0767v2 c0767v7 = c0722p1.A1;
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0767v6, c0767v7, c0767v7, c0722p1.G1, c0767v7), "getChars"), enumC3891f);
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0722p1.A1, c0722p1.X1), "indexOf"), enumC3891f);
            C0767v2 c0767v8 = c0722p1.A1;
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0767v8, c0722p1.X1, c0767v8), "indexOf"), enumC3891f);
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0722p1.A1, c0722p1.X1), "lastIndexOf"), enumC3891f);
            C0767v2 c0767v9 = c0722p1.A1;
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0767v9, c0722p1.X1, c0767v9), "lastIndexOf"), enumC3891f);
            C0767v2 c0767v10 = c0722p1.A1;
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0767v10, c0767v10, c0767v10), "offsetByCodePoints"), enumC3891f);
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, c0722p1.A1, c0722p1.x1), "setCharAt"), enumC3891f);
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, c0722p1.A1), "setLength"), enumC3891f);
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0722p1.X1, c0722p1.A1), "substring"), enumC3891f);
            C0767v2 c0767v11 = c0722p1.X1;
            C0767v2 c0767v12 = c0722p1.A1;
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0767v11, c0767v12, c0767v12), "substring"), enumC3891f);
            biConsumer.accept(c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, new C0767v2[0]), "trimToSize"), enumC3891f);
            i3++;
        }
        biConsumer.accept(c0722p1.a(c0722p1.c3, c0722p1.b(c0722p1.b3, new C0767v2[0]), "getMap"), EnumC3891f.N);
        String[] strArr = {"Landroid/nfc/tech/Ndef;", "Landroid/nfc/tech/NfcA;", "Landroid/nfc/tech/NfcB;", "Landroid/nfc/tech/NfcBarcode;", "Landroid/nfc/tech/NfcF;", "Landroid/nfc/tech/NdefFormatable;", "Landroid/nfc/tech/IsoDep;", "Landroid/nfc/tech/MifareClassic;", "Landroid/nfc/tech/MifareUltralight;", "Landroid/nfc/tech/NfcV;"};
        C0767v2 c0767v2E = c0722p1.e("Landroid/nfc/Tag;");
        for (int i4 = 0; i4 < 10; i4++) {
            C0767v2 c0767v2E2 = c0722p1.e(strArr[i4]);
            C0684j2 c0684j2A2 = c0722p1.a(c0767v2E2, c0722p1.b(c0722p1.v1, new C0767v2[0]), "isConnected");
            EnumC3891f enumC3891f2 = EnumC3891f.K;
            biConsumer.accept(c0684j2A2, enumC3891f2);
            biConsumer.accept(c0722p1.a(c0767v2E2, c0722p1.b(c0767v2E, new C0767v2[0]), "getTag"), enumC3891f2);
            biConsumer.accept(c0722p1.a(c0767v2E2, c0722p1.b(c0722p1.D1, new C0767v2[0]), "close"), enumC3891f2);
            biConsumer.accept(c0722p1.a(c0767v2E2, c0722p1.b(c0722p1.D1, new C0767v2[0]), MqttConnectHandler.NAME), enumC3891f2);
        }
        C0767v2 c0767v2E3 = c0722p1.e("Landroid/webkit/CookieSyncManager;");
        C0737r2 c0737r2B = c0722p1.b(c0722p1.D1, new C0767v2[0]);
        String[] strArr2 = {"sync", "resetSync", "startSync", "stopSync", "run"};
        for (int i5 = 0; i5 < 5; i5++) {
            biConsumer.accept(c0722p1.a(c0767v2E3, c0737r2B, strArr2[i5]), EnumC3891f.I);
        }
        C0767v2 c0767v2E4 = c0722p1.e("Ljava/time/temporal/ValueRange;");
        C0767v2 c0767v2E5 = c0722p1.e("Ljava/time/chrono/ChronoLocalDate;");
        C0767v2 c0767v2E6 = c0722p1.e("Ljava/time/temporal/Temporal;");
        C0767v2 c0767v2E7 = c0722p1.e("Ljava/time/temporal/TemporalField;");
        C0767v2 c0767v2E8 = c0722p1.e("Ljava/time/temporal/TemporalUnit;");
        C0767v2 c0767v2E9 = c0722p1.e("Ljava/time/temporal/TemporalAmount;");
        C0767v2 c0767v2E10 = c0722p1.e("Ljava/time/temporal/TemporalAdjuster;");
        String str = "Ljava/time/chrono/HijrahDate;";
        String[] strArr3 = {"Ljava/time/chrono/JapaneseDate;", "Ljava/time/chrono/MinguoDate;", "Ljava/time/chrono/HijrahDate;", "Ljava/time/chrono/ThaiBuddhistDate;"};
        int i6 = 0;
        for (i = 4; i6 < i; i = 4) {
            C0767v2 c0767v2E11 = c0722p1.e(strArr3[i6]);
            C0684j2 c0684j2A3 = c0722p1.a(c0767v2E11, c0722p1.b(c0722p1.A1, new C0767v2[i2]), "lengthOfMonth");
            EnumC3891f enumC3891f3 = EnumC3891f.O;
            biConsumer.accept(c0684j2A3, enumC3891f3);
            String[] strArr4 = strArr3;
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0722p1.A1, new C0767v2[i2]), "lengthOfYear"), enumC3891f3);
            C0767v2 c0767v13 = c0722p1.v1;
            String str2 = str;
            C0767v2[] c0767v2Arr2 = new C0767v2[1];
            c0767v2Arr2[i2] = c0767v2E7;
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v13, c0767v2Arr2), "isSupported"), enumC3891f3);
            C0767v2[] c0767v2Arr3 = new C0767v2[1];
            c0767v2Arr3[i2] = c0767v2E7;
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v2E4, c0767v2Arr3), "range"), enumC3891f3);
            C0767v2 c0767v14 = c0722p1.B1;
            C0767v2[] c0767v2Arr4 = new C0767v2[1];
            c0767v2Arr4[i2] = c0767v2E7;
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v14, c0767v2Arr4), "getLong"), enumC3891f3);
            C0767v2 c0767v2E12 = c0722p1.e("Ljava/time/chrono/ChronoLocalDateTime;");
            C0767v2[] c0767v2Arr5 = new C0767v2[1];
            c0767v2Arr5[i2] = c0722p1.e("Ljava/time/LocalTime;");
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v2E12, c0767v2Arr5), "atTime"), enumC3891f3);
            C0767v2 c0767v2E13 = c0722p1.e("Ljava/time/chrono/ChronoPeriod;");
            C0767v2[] c0767v2Arr6 = new C0767v2[1];
            c0767v2Arr6[i2] = c0767v2E5;
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v2E13, c0767v2Arr6), "until"), enumC3891f3);
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0722p1.B1, new C0767v2[i2]), "toEpochDay"), enumC3891f3);
            C0767v2 c0767v15 = c0722p1.B1;
            C0767v2 c0767v16 = c0767v2E4;
            C0767v2[] c0767v2Arr7 = new C0767v2[2];
            c0767v2Arr7[i2] = c0767v2E6;
            c0767v2Arr7[1] = c0767v2E8;
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v15, c0767v2Arr7), "until"), enumC3891f3);
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0722p1.e("Ljava/time/chrono/Era;"), new C0767v2[i2]), "getEra"), enumC3891f3);
            biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0722p1.e("Ljava/time/chrono/Chronology;"), new C0767v2[i2]), "getChronology"), enumC3891f3);
            int i7 = 2;
            C0767v2[] c0767v2Arr8 = new C0767v2[2];
            c0767v2Arr8[i2] = c0767v2E5;
            boolean z = true;
            c0767v2Arr8[1] = c0767v2E6;
            int i8 = i2;
            while (i8 < i7) {
                C0767v2 c0767v17 = c0767v2Arr8[i8];
                C0767v2[] c0767v2Arr9 = new C0767v2[i7];
                c0767v2Arr9[i2] = c0722p1.B1;
                c0767v2Arr9[1] = c0767v2E8;
                C0684j2 c0684j2A4 = c0722p1.a(c0767v2E11, c0722p1.b(c0767v17, c0767v2Arr9), "minus");
                C0767v2[] c0767v2Arr10 = c0767v2Arr8;
                EnumC3891f enumC3891f4 = EnumC3891f.O;
                biConsumer.accept(c0684j2A4, enumC3891f4);
                biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v17, c0767v2E9), "minus"), enumC3891f4);
                biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v17, c0722p1.B1, c0767v2E8), "plus"), enumC3891f4);
                biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v17, c0767v2E9), "plus"), enumC3891f4);
                biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v17, c0767v2E7, c0722p1.B1), "with"), enumC3891f4);
                biConsumer.accept(c0722p1.a(c0767v2E11, c0722p1.b(c0767v17, c0767v2E10), "with"), enumC3891f4);
                i8++;
                z = true;
                c0767v2Arr8 = c0767v2Arr10;
                i7 = 2;
                i2 = 0;
            }
            i6++;
            strArr3 = strArr4;
            str = str2;
            c0767v2E4 = c0767v16;
            i2 = 0;
        }
        biConsumer.accept(c0722p1.a(c0722p1.e(str), c0722p1.b(c0722p1.v1, new C0767v2[0]), "isLeapYear"), EnumC3891f.O);
    }
}
