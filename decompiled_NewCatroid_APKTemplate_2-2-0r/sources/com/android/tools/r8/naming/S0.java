package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.O2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class S0 {
    private final AbstractC2178hm a;
    private final Set b;
    private final O2 c;

    private S0(AbstractC2178hm abstractC2178hm, HashSet hashSet, O2 o2) {
        this.c = o2;
        C1946em c1946emE = AbstractC2178hm.e();
        for (Map.Entry entry : abstractC2178hm.entrySet()) {
            c1946emE.a((String) entry.getKey(), ((C3629i) entry.getValue()).a());
        }
        this.a = c1946emE.a();
        this.b = hashSet;
        c();
    }

    public static S0 a(O2 o2, Path path) throws IOException {
        G0 g0 = new G0(o2, M.a(new BufferedReader(new InputStreamReader(Files.newInputStream(path, new OpenOption[0]), StandardCharsets.UTF_8))), MapVersion.MAP_VERSION_NONE, false, false);
        try {
            R0 r0 = new R0(o2);
            g0.a(r0);
            r0.c.a();
            S0 s0 = new S0(AbstractC2178hm.a(r0.a), r0.b, r0.c);
            g0.close();
            return s0;
        } catch (Throwable th) {
            try {
                g0.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void c() {
        HashMap map = new HashMap();
        AbstractC2963s10 it = this.a.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            C3631j c3631j = (C3631j) this.a.get(str);
            String str2 = (String) map.put(c3631j.b, str);
            if (str2 != null) {
                this.c.error(w0.a(C3970u0.b(str), C3970u0.b(str2), C3970u0.a(c3631j.b), c3631j.c));
            }
        }
        this.c.a();
    }

    public final boolean b(C0767v2 c0767v2) {
        return this.a.containsKey(c0767v2.e.toString());
    }

    public final Set b() {
        return this.b;
    }

    public final C3631j a(C0767v2 c0767v2) {
        return (C3631j) this.a.get(c0767v2.e.toString());
    }

    public final AbstractC1048Hm a() {
        return this.a.keySet();
    }

    public final C3631j a(String str) {
        return (C3631j) this.a.get(str);
    }
}
