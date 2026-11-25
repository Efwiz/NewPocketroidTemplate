package com.android.tools.r8.utils;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.MapIdEnvironment;
import com.android.tools.r8.MapIdProvider;
import com.android.tools.r8.internal.AbstractC2178hm;
import java.util.Iterator;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class Y1 implements MapIdProvider {
    private static final AbstractC2178hm c = AbstractC2178hm.e().a(a(), new MapIdProvider() { // from class: com.android.tools.r8.utils.Y1$$ExternalSyntheticLambda0
        @Override // com.android.tools.r8.MapIdProvider
        public final String get(MapIdEnvironment mapIdEnvironment) {
            return mapIdEnvironment.getMapHash();
        }
    }).a();
    static final /* synthetic */ boolean d = true;
    private final String a;
    private String b = null;

    private Y1(String str) {
        this.a = str;
    }

    private static String a() {
        return "%MAP_HASH";
    }

    @Override // com.android.tools.r8.MapIdProvider
    public final String get(final MapIdEnvironment mapIdEnvironment) {
        if (this.b == null) {
            this.b = this.a;
            c.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.Y1$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(mapIdEnvironment, (String) obj, (MapIdProvider) obj2);
                }
            });
        }
        return this.b;
    }

    public static MapIdProvider a(String str, DiagnosticsHandler diagnosticsHandler) {
        String strReplace = str;
        for (String str2 : c.keySet()) {
            strReplace = strReplace.replace(str2, " " + str2.substring(1));
        }
        if (!d && str.length() != strReplace.length()) {
            throw new AssertionError();
        }
        int iIndexOf = strReplace.indexOf(37);
        if (iIndexOf < 0) {
            return new Y1(str);
        }
        while (iIndexOf >= 0) {
            Iterator it = c.keySet().iterator();
            int iMax = 0;
            while (it.hasNext()) {
                iMax = Math.max(iMax, ((String) it.next()).length());
            }
            diagnosticsHandler.error(new StringDiagnostic("Invalid template variable starting with " + str.substring(iIndexOf, Math.min(iMax + iIndexOf, str.length()))));
            iIndexOf = strReplace.indexOf(37, iIndexOf + 1);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MapIdEnvironment mapIdEnvironment, String str, MapIdProvider mapIdProvider) {
        this.b = this.b.replace(str, mapIdProvider.get(mapIdEnvironment));
    }
}
