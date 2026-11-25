package com.reandroid.app;

import androidx.exifinterface.media.ExifInterface;
import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.catrobat.catroid.BuildConfig;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes10.dex */
public class AndroidApiLevel implements Comparable<AndroidApiLevel> {
    public static final AndroidApiLevel ANDROID_PLATFORM;
    public static final AndroidApiLevel B;
    public static final AndroidApiLevel B_1_1;
    public static final AndroidApiLevel C;
    public static final AndroidApiLevel D;
    public static final AndroidApiLevel E;
    public static final AndroidApiLevel E_0_1;
    public static final AndroidApiLevel E_MR1;
    public static final AndroidApiLevel F;
    public static final AndroidApiLevel G;
    public static final AndroidApiLevel G_MR1;
    public static final AndroidApiLevel H;
    public static final AndroidApiLevel H_MR1;
    public static final AndroidApiLevel H_MR2;
    public static final AndroidApiLevel I;
    public static final AndroidApiLevel I_MR1;
    public static final AndroidApiLevel J;
    public static final AndroidApiLevel J_MR1;
    public static final AndroidApiLevel J_MR2;
    public static final AndroidApiLevel K;
    public static final AndroidApiLevel K_WATCH;
    public static final AndroidApiLevel L;
    public static final AndroidApiLevel LATEST;
    public static final AndroidApiLevel L_MR1;
    public static final AndroidApiLevel M;
    public static final AndroidApiLevel N;
    public static final AndroidApiLevel N_MR1;
    public static final AndroidApiLevel O;
    public static final AndroidApiLevel O_MR1;
    public static final AndroidApiLevel P;
    public static final AndroidApiLevel Q;
    public static final AndroidApiLevel R;
    public static final AndroidApiLevel S;
    public static final AndroidApiLevel Sv2;
    public static final AndroidApiLevel T;
    public static final AndroidApiLevel U;
    public static final AndroidApiLevel V;
    private static final AndroidApiLevel[] VALUES;
    private final int api;
    private final String description;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f219name;
    private final String version;
    private final String versionMax;

    public boolean equals(Object obj) {
        return obj == this;
    }

    static {
        AndroidApiLevel androidApiLevel = new AndroidApiLevel("B", 1, BuildConfig.VERSION_NAME, "Android 1.0");
        B = androidApiLevel;
        AndroidApiLevel androidApiLevel2 = new AndroidApiLevel("B_1_1", 2, CompilerOptions.VERSION_1_1, "Petit Four");
        B_1_1 = androidApiLevel2;
        AndroidApiLevel androidApiLevel3 = new AndroidApiLevel("C", 3, CompilerOptions.VERSION_1_5, "Cupcake");
        C = androidApiLevel3;
        AndroidApiLevel androidApiLevel4 = new AndroidApiLevel("D", 4, CompilerOptions.VERSION_1_6, "Donut");
        D = androidApiLevel4;
        AndroidApiLevel androidApiLevel5 = new AndroidApiLevel(ExifInterface.LONGITUDE_EAST, 5, "2.0", "Eclair");
        E = androidApiLevel5;
        AndroidApiLevel androidApiLevel6 = new AndroidApiLevel("E_0_1", 6, "2.0.1", "Eclair");
        E_0_1 = androidApiLevel6;
        AndroidApiLevel androidApiLevel7 = new AndroidApiLevel("E_MR1", 7, "2.1", "Eclair");
        E_MR1 = androidApiLevel7;
        AndroidApiLevel androidApiLevel8 = new AndroidApiLevel("F", 8, "2.2", "2.2.3", "Froyo");
        F = androidApiLevel8;
        AndroidApiLevel androidApiLevel9 = new AndroidApiLevel("G", 9, "2.3", "2.3.2", "Gingerbread");
        G = androidApiLevel9;
        AndroidApiLevel androidApiLevel10 = new AndroidApiLevel("G_MR1", 10, "2.3.3", "2.3.7", "Gingerbread");
        G_MR1 = androidApiLevel10;
        AndroidApiLevel androidApiLevel11 = new AndroidApiLevel("H", 11, "3.0", "Honeycomb");
        H = androidApiLevel11;
        AndroidApiLevel androidApiLevel12 = new AndroidApiLevel("H_MR1", 12, "3.1", "Honeycomb");
        H_MR1 = androidApiLevel12;
        AndroidApiLevel androidApiLevel13 = new AndroidApiLevel("H_MR2", 13, "3.2", "3.2.6", "Honeycomb");
        H_MR2 = androidApiLevel13;
        AndroidApiLevel androidApiLevel14 = new AndroidApiLevel("I", 14, "4.0", "4.0.2", "Ice Cream Sandwich");
        I = androidApiLevel14;
        AndroidApiLevel androidApiLevel15 = new AndroidApiLevel("I_MR1", 15, "4.0.3", "4.0.4", "Ice Cream Sandwich");
        I_MR1 = androidApiLevel15;
        AndroidApiLevel androidApiLevel16 = new AndroidApiLevel("J", 16, "4.1", "4.1.2", "Jelly Bean");
        J = androidApiLevel16;
        AndroidApiLevel androidApiLevel17 = new AndroidApiLevel("J_MR1", 17, "4.2", "4.2.2", "Jelly Bean");
        J_MR1 = androidApiLevel17;
        AndroidApiLevel androidApiLevel18 = new AndroidApiLevel("J_MR2", 18, "4.3", "4.3.1", "Jelly Bean");
        J_MR2 = androidApiLevel18;
        AndroidApiLevel androidApiLevel19 = new AndroidApiLevel("K", 19, "4.4", "4.4.4", "Key Lime Pie");
        K = androidApiLevel19;
        AndroidApiLevel androidApiLevel20 = new AndroidApiLevel("K_WATCH", 20, "4.4", "4.4.2", "Key Lime Pie");
        K_WATCH = androidApiLevel20;
        AndroidApiLevel androidApiLevel21 = new AndroidApiLevel("L", 21, "5.0", "5.0.2", "Lemon Meringue Pie");
        L = androidApiLevel21;
        AndroidApiLevel androidApiLevel22 = new AndroidApiLevel("L_MR1", 22, "5.1", "5.1.1", "Lemon Meringue Pie");
        L_MR1 = androidApiLevel22;
        AndroidApiLevel androidApiLevel23 = new AndroidApiLevel("M", 23, "6.0", "6.0.1", "Macadamia Nut Cookie");
        M = androidApiLevel23;
        AndroidApiLevel androidApiLevel24 = new AndroidApiLevel("N", 24, "7.0", "New York Cheesecake");
        N = androidApiLevel24;
        AndroidApiLevel androidApiLevel25 = new AndroidApiLevel("N_MR1", 25, "7.1", "7.1.2", "New York Cheesecake");
        N_MR1 = androidApiLevel25;
        AndroidApiLevel androidApiLevel26 = new AndroidApiLevel("O", 26, "8.0", "Oatmeal Cookie");
        O = androidApiLevel26;
        AndroidApiLevel androidApiLevel27 = new AndroidApiLevel("O_MR1", 27, "8.1", "Oatmeal Cookie");
        O_MR1 = androidApiLevel27;
        AndroidApiLevel androidApiLevel28 = new AndroidApiLevel("P", 28, "9", "Pistachio Ice Cream");
        P = androidApiLevel28;
        AndroidApiLevel androidApiLevel29 = new AndroidApiLevel("Q", 29, "10", "Quince Tart");
        Q = androidApiLevel29;
        AndroidApiLevel androidApiLevel30 = new AndroidApiLevel("R", 30, "11", "Red Velvet Cake");
        R = androidApiLevel30;
        AndroidApiLevel androidApiLevel31 = new AndroidApiLevel(ExifInterface.LATITUDE_SOUTH, 31, "12", "Snow Cone");
        S = androidApiLevel31;
        AndroidApiLevel androidApiLevel32 = new AndroidApiLevel("Sv2", 32, "12.1", "Snow Cone v2");
        Sv2 = androidApiLevel32;
        AndroidApiLevel androidApiLevel33 = new AndroidApiLevel(ExifInterface.GPS_DIRECTION_TRUE, 33, "13", "Tiramisu");
        T = androidApiLevel33;
        AndroidApiLevel androidApiLevel34 = new AndroidApiLevel("U", 34, "14", "Upside Down Cake");
        U = androidApiLevel34;
        AndroidApiLevel androidApiLevel35 = new AndroidApiLevel(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, 35, "15", "Vanilla Ice Cream");
        V = androidApiLevel35;
        AndroidApiLevel androidApiLevel36 = new AndroidApiLevel("ANDROID_PLATFORM", 10000, "10000", "ANDROID_PLATFORM");
        ANDROID_PLATFORM = androidApiLevel36;
        LATEST = androidApiLevel35;
        VALUES = new AndroidApiLevel[]{androidApiLevel, androidApiLevel2, androidApiLevel3, androidApiLevel4, androidApiLevel5, androidApiLevel6, androidApiLevel7, androidApiLevel8, androidApiLevel9, androidApiLevel10, androidApiLevel11, androidApiLevel12, androidApiLevel13, androidApiLevel14, androidApiLevel15, androidApiLevel16, androidApiLevel17, androidApiLevel18, androidApiLevel19, androidApiLevel20, androidApiLevel21, androidApiLevel22, androidApiLevel23, androidApiLevel24, androidApiLevel25, androidApiLevel26, androidApiLevel27, androidApiLevel28, androidApiLevel29, androidApiLevel30, androidApiLevel31, androidApiLevel32, androidApiLevel33, androidApiLevel34, androidApiLevel35, androidApiLevel36};
    }

    private AndroidApiLevel(String str, int i, String str2, String str3, String str4) {
        this.f219name = str;
        this.api = i;
        this.version = str2;
        this.versionMax = str3;
        this.description = str4;
    }

    private AndroidApiLevel(String str, int i, String str2, String str3) {
        this(str, i, str2, null, str3);
    }

    public int getApi() {
        return this.api;
    }

    public String getName() {
        return this.f219name;
    }

    public String getVersion() {
        return this.version;
    }

    public String getVersionMax() {
        return this.versionMax;
    }

    public String getDescription() {
        return this.description;
    }

    @Override // java.lang.Comparable
    public int compareTo(AndroidApiLevel androidApiLevel) {
        return Integer.compare(this.api, androidApiLevel.api);
    }

    public int hashCode() {
        return this.api;
    }

    public String toString() {
        return this.f219name + Parameters.DEFAULT_OPTION_PREFIXES + this.api + " (" + this.description + " - " + this.version + Constant.AFTER_QUTO;
    }

    public static AndroidApiLevel forApi(int i) {
        for (AndroidApiLevel androidApiLevel : VALUES) {
            if (i == androidApiLevel.getApi()) {
                return androidApiLevel;
            }
        }
        return null;
    }

    public static AndroidApiLevel getMinAndroidApiLevelForDex(int i) {
        switch (i) {
            case 35:
                return B;
            case 36:
            default:
                return null;
            case 37:
                return N;
            case 38:
                return O;
            case 39:
                return P;
            case 40:
                return R;
            case 41:
                return ANDROID_PLATFORM;
        }
    }

    public static AndroidApiLevel[] values() {
        return (AndroidApiLevel[]) VALUES.clone();
    }
}
