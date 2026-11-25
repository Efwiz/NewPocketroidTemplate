package io.noties.markwon.image.svg;

import android.util.Log;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SvgSupport {
    private static final boolean HAS_SVG;

    static {
        boolean z;
        try {
            Class.forName("com.caverock.androidsvg.SVG");
            z = true;
        } catch (Throwable unused) {
            Log.w("MarkwonImagesPlugin", missingMessage());
            z = false;
        }
        HAS_SVG = z;
    }

    public static boolean hasSvgSupport() {
        return HAS_SVG;
    }

    static String missingMessage() {
        return "`com.caverock:androidsvg:*` dependency is missing, please add to your project explicitly if you wish to use SVG media-decoder";
    }

    private SvgSupport() {
    }
}
