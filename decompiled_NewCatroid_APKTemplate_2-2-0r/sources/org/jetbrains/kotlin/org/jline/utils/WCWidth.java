package org.jetbrains.kotlin.org.jline.utils;

import com.badlogic.gdx.graphics.GL20;
import com.gaurav.avnc.vnc.XKeySym;
import com.huawei.hms.support.api.entity.auth.AuthCode;
import net.bytebuddy.description.modifier.ModifierContributor;
import okio.Utf8;

/* JADX INFO: loaded from: classes4.dex */
public final class WCWidth {
    static Interval[] combining = {new Interval(768, 879), new Interval(1155, 1158), new Interval(1160, 1161), new Interval(1425, 1469), new Interval(XKeySym.XK_Arabic_question_mark, XKeySym.XK_Arabic_question_mark), new Interval(XKeySym.XK_Arabic_hamza, XKeySym.XK_Arabic_maddaonalef), new Interval(XKeySym.XK_Arabic_hamzaonwaw, XKeySym.XK_Arabic_hamzaunderalef), new Interval(XKeySym.XK_Arabic_alef, XKeySym.XK_Arabic_alef), new Interval(1536, 1539), new Interval(1552, 1557), new Interval(1611, 1630), new Interval(1648, 1648), new Interval(XKeySym.XK_Cyrillic_zhe, XKeySym.XK_Cyrillic_DE), new Interval(XKeySym.XK_Cyrillic_GHE, XKeySym.XK_Cyrillic_HA), new Interval(XKeySym.XK_Cyrillic_SHORTI, XKeySym.XK_Cyrillic_EM), new Interval(1807, 1807), new Interval(1809, 1809), new Interval(1840, 1866), new Interval(1958, 1968), new Interval(2027, XKeySym.XK_Greek_finalsmallsigma), new Interval(GL20.GL_CCW, 2306), new Interval(2364, 2364), new Interval(2369, 2376), new Interval(2381, 2381), new Interval(2385, 2388), new Interval(2402, 2403), new Interval(2433, 2433), new Interval(2492, 2492), new Interval(2497, 2500), new Interval(2509, 2509), new Interval(XKeySym.XK_ht, XKeySym.XK_ff), new Interval(2561, 2562), new Interval(2620, 2620), new Interval(2625, 2626), new Interval(2631, 2632), new Interval(2635, 2637), new Interval(2672, 2673), new Interval(2689, 2690), new Interval(XKeySym.XK_leftanglebracket, XKeySym.XK_leftanglebracket), new Interval(2753, XKeySym.XK_fiveeighths), new Interval(2759, 2760), new Interval(XKeySym.XK_rightopentriangle, XKeySym.XK_rightopentriangle), new Interval(XKeySym.XK_openrectbullet, XKeySym.XK_opentribulletup), new Interval(2817, 2817), new Interval(2876, 2876), new Interval(2879, 2879), new Interval(2881, 2883), new Interval(2893, 2893), new Interval(2902, 2902), new Interval(2946, 2946), new Interval(XKeySym.XK_overbar, XKeySym.XK_overbar), new Interval(3021, 3021), new Interval(3134, 3136), new Interval(3142, 3144), new Interval(3146, 3149), new Interval(3157, 3158), new Interval(3260, 3260), new Interval(3263, 3263), new Interval(3270, 3270), new Interval(3276, 3277), new Interval(3298, 3299), new Interval(3393, 3395), new Interval(3405, 3405), new Interval(XKeySym.XK_Thai_sosua, XKeySym.XK_Thai_sosua), new Interval(XKeySym.XK_Thai_saraaa, XKeySym.XK_Thai_sarai), new Interval(XKeySym.XK_Thai_saraue, XKeySym.XK_Thai_saraue), new Interval(3633, 3633), new Interval(3636, 3642), new Interval(3655, 3662), new Interval(XKeySym.XK_Hangul_Mieum, XKeySym.XK_Hangul_Mieum), new Interval(XKeySym.XK_Hangul_PieubSios, XKeySym.XK_Hangul_SsangJieuj), new Interval(XKeySym.XK_Hangul_Khieuq, XKeySym.XK_Hangul_Tieut), new Interval(XKeySym.XK_Hangul_WA, XKeySym.XK_Hangul_WEO), new Interval(3864, 3865), new Interval(3893, 3893), new Interval(3895, 3895), new Interval(3897, 3897), new Interval(3953, 3966), new Interval(Utf8.MASK_2BYTES, 3972), new Interval(3974, 3975), new Interval(3984, 3991), new Interval(3993, 4028), new Interval(4038, 4038), new Interval(4141, 4144), new Interval(4146, 4146), new Interval(4150, 4151), new Interval(4153, 4153), new Interval(4184, 4185), new Interval(4448, 4607), new Interval(4959, 4959), new Interval(5906, 5908), new Interval(5938, 5940), new Interval(5970, 5971), new Interval(AuthCode.StatusCode.AUTH_INFO_NOT_EXIST, AuthCode.StatusCode.CERT_FINGERPRINT_ERROR), new Interval(6068, 6069), new Interval(6071, 6077), new Interval(6086, 6086), new Interval(6089, 6099), new Interval(6109, 6109), new Interval(6155, 6157), new Interval(6313, 6313), new Interval(6432, 6434), new Interval(6439, 6440), new Interval(6450, 6450), new Interval(6457, 6459), new Interval(6679, 6680), new Interval(6912, 6915), new Interval(6964, 6964), new Interval(6966, 6970), new Interval(6972, 6972), new Interval(6978, 6978), new Interval(7019, 7027), new Interval(7616, 7626), new Interval(7678, ModifierContributor.ForMethod.MASK), new Interval(8203, 8207), new Interval(8234, 8238), new Interval(8288, 8291), new Interval(8298, 8303), new Interval(8400, 8431), new Interval(12330, 12335), new Interval(12441, 12442), new Interval(43014, 43014), new Interval(43019, 43019), new Interval(43045, 43046), new Interval(64286, 64286), new Interval(65024, XKeySym.XK_ISO_Last_Group_Lock), new Interval(XKeySym.XK_ISO_Left_Tab, XKeySym.XK_ISO_Partial_Line_Up), new Interval(65279, 65279), new Interval(XKeySym.XK_braille_dot_9, 65531), new Interval(68097, 68099), new Interval(68101, 68102), new Interval(68108, 68111), new Interval(68152, 68154), new Interval(68159, 68159), new Interval(119143, 119145), new Interval(119155, 119170), new Interval(119173, 119179), new Interval(119210, 119213), new Interval(119362, 119364), new Interval(917505, 917505), new Interval(917536, 917631), new Interval(917760, 917999)};

    public static int wcwidth(int i) {
        int i2 = 0;
        if (i == 0) {
            return 0;
        }
        if (i < 32) {
            return -1;
        }
        if (i >= 127 && i < 160) {
            return -1;
        }
        Interval[] intervalArr = combining;
        if (bisearch(i, intervalArr, intervalArr.length - 1)) {
            return 0;
        }
        if (i >= 4352 && (i <= 4447 || i == 9001 || i == 9002 || ((i >= 11904 && i <= 42191 && i != 12351) || ((i >= 44032 && i <= 55203) || ((i >= 63744 && i <= 64255) || ((i >= 65040 && i <= 65049) || ((i >= 65072 && i <= 65135) || ((i >= 65280 && i <= 65376) || ((i >= 65504 && i <= 65510) || ((i >= 131072 && i <= 196605) || (i >= 196608 && i <= 262141))))))))))) {
            i2 = 1;
        }
        return 1 + i2;
    }

    private static class Interval {
        public final int first;
        public final int last;

        public Interval(int i, int i2) {
            this.first = i;
            this.last = i2;
        }
    }

    private static boolean bisearch(int i, Interval[] intervalArr, int i2) {
        if (i >= intervalArr[0].first && i <= intervalArr[i2].last) {
            int i3 = 0;
            while (i2 >= i3) {
                int i4 = (i3 + i2) / 2;
                if (i > intervalArr[i4].last) {
                    i3 = i4 + 1;
                } else {
                    if (i >= intervalArr[i4].first) {
                        return true;
                    }
                    i2 = i4 - 1;
                }
            }
        }
        return false;
    }
}
