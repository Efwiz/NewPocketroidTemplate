package com.huawei.hms.ml.language.common.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
public class LanguageCodeUtil {
    public static final String AR = "ar";
    public static final String BG = "bg";
    public static final String BN = "bn";
    public static final String BS = "bs";
    public static final String CA = "ca";
    public static final String CS = "cs";
    public static final String DA = "da";
    public static final String DE = "de";
    public static final String EL = "el";
    public static final String EN = "en";
    public static final String ES = "es";
    public static final String ET = "et";
    public static final String FA = "fa";
    public static final String FI = "fi";
    public static final String FR = "fr";
    public static final String GU = "gu";
    public static final String HE = "he";
    public static final String HI = "hi";
    public static final String HR = "hr";
    public static final String HU = "hu";
    public static final String ID = "id";
    public static final String IS = "is";
    public static final String IT = "it";
    public static final String JA = "ja";
    public static final String KA = "ka";
    public static final String KM = "km";
    public static final String KO = "ko";
    private static final Set<String> LOCAL_LANGUAGE_NOAH;
    private static final Set<String> LOCAL_LANGUAGE_TC;
    private static final Set<String> LOCAL_LANGUAGE_ZH;
    public static final String LV = "lv";
    public static final String MG = "mg";
    public static final String MK = "mk";
    public static final String MR = "mr";
    public static final String MS = "ms";
    public static final String MT = "mt";
    public static final String MY = "my";
    public static final String NL = "nl";
    public static final String NO = "no";
    public static final String PA = "pa";
    public static final String PL = "pl";
    public static final String PT = "pt";
    public static final String RO = "ro";
    public static final String RU = "ru";
    public static final String SK = "sk";
    public static final String SL = "sl";
    public static final String SR = "sr";
    public static final String SV = "sv";
    public static final String TA = "ta";
    public static final String TE = "te";
    public static final String TH = "th";
    public static final String TL = "tl";
    public static final String TR = "tr";
    public static final String UK = "uk";
    public static final String UR = "ur";
    public static final String VI = "vi";
    public static final String ZH = "zh";
    public static final String ZHHK = "zh-HK";
    public static final String ZHTW = "zh-TW";

    static {
        HashSet hashSet = new HashSet();
        LOCAL_LANGUAGE_NOAH = hashSet;
        hashSet.add(EN);
        hashSet.add("zh");
        hashSet.add(FR);
        hashSet.add(RU);
        hashSet.add("it");
        hashSet.add("es");
        hashSet.add(DE);
        hashSet.add("ar");
        hashSet.add(PT);
        hashSet.add(JA);
        hashSet.add(TH);
        hashSet.add(TR);
        hashSet.add(DA);
        hashSet.add(FI);
        hashSet.add(KO);
        hashSet.add(PL);
        hashSet.add(SV);
        hashSet.add("no");
        hashSet.add(MS);
        hashSet.add(NL);
        HashSet hashSet2 = new HashSet();
        LOCAL_LANGUAGE_TC = hashSet2;
        hashSet2.add(VI);
        hashSet2.add(CS);
        hashSet2.add(EL);
        hashSet2.add(SR);
        hashSet2.add(HE);
        hashSet2.add("id");
        hashSet2.add(RO);
        hashSet2.add(HI);
        hashSet2.add(TL);
        hashSet2.add(FA);
        hashSet2.add(ET);
        hashSet2.add(SK);
        hashSet2.add(LV);
        hashSet2.add(KM);
        hashSet2.add(TA);
        hashSet2.add(HU);
        hashSet2.add(MY);
        hashSet2.add(MT);
        hashSet2.add(MK);
        hashSet2.add(IS);
        hashSet2.add(UR);
        hashSet2.add(BS);
        hashSet2.add(BG);
        hashSet2.add(HR);
        hashSet2.add(UK);
        hashSet2.add(CA);
        hashSet2.add(SL);
        hashSet2.add(BN);
        hashSet2.add(KA);
        hashSet2.add(MR);
        hashSet2.add(GU);
        hashSet2.add(PA);
        hashSet2.add(TE);
        hashSet2.add(MG);
        HashSet hashSet3 = new HashSet();
        LOCAL_LANGUAGE_ZH = hashSet3;
        hashSet3.add(ZHHK);
    }

    private LanguageCodeUtil() {
    }

    public static Set<String> getNoahSet() {
        return Collections.unmodifiableSet(LOCAL_LANGUAGE_NOAH);
    }

    public static Set<String> getMttlSet() {
        return Collections.unmodifiableSet(LOCAL_LANGUAGE_TC);
    }

    public static Set<String> getZHSet() {
        return Collections.unmodifiableSet(LOCAL_LANGUAGE_ZH);
    }
}
