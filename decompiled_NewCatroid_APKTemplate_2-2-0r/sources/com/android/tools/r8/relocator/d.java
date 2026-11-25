package com.android.tools.r8.relocator;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.references.PackageReference;
import com.android.tools.r8.utils.C3970u0;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class d {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final IdentityHashMap b = new IdentityHashMap();

    public d(C0702m c0702m) {
        this.a = c0702m;
    }

    public final AbstractC3649o0 a(Map map) {
        Iterator<C0729q2> it = this.a.f().d().iterator();
        while (it.hasNext()) {
            for (W0 w0 : it.next().M1()) {
                if (w0.a1() != null) {
                    w0.a1().K();
                }
            }
        }
        C1946em c1946emE = AbstractC2178hm.e();
        for (PackageReference packageReference : map.keySet()) {
            String packageName = packageReference.getPackageName();
            String packageName2 = ((PackageReference) map.get(packageReference)).getPackageName();
            if (!packageName.equals(packageName2)) {
                if (packageName.isEmpty()) {
                    if (!c && packageName2.isEmpty()) {
                        throw new AssertionError();
                    }
                    packageName2 = packageName2 + ".";
                }
                String strG = C3970u0.g(packageName);
                String strG2 = C3970u0.g(packageName2);
                c1946emE.a(strG, strG2);
                final C0759u2 c0759u2C = this.a.a().c("L" + strG);
                final C0759u2 c0759u2C2 = this.a.a().c("L" + strG2);
                this.a.a().a(new Consumer() { // from class: com.android.tools.r8.relocator.d$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(c0759u2C, c0759u2C2, (C0767v2) obj);
                    }
                });
            }
        }
        return new c(this.b, c1946emE.a(), this.a.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0759u2 c0759u2, C0759u2 c0759u3, C0767v2 c0767v2) {
        C0759u2 c0759u4 = c0767v2.e;
        int i = c0759u4.d;
        int i2 = c0759u2.d;
        if (i <= i2) {
            return;
        }
        if ((i2 == 1 || c0759u4.e[i2] == 47) && c0759u4.a(c0759u2.e)) {
            if (this.b.containsKey(c0767v2)) {
                this.a.Y().e.error(b.a(c0767v2));
                this.a.Y().e.a();
            }
            this.b.put(c0767v2, c0767v2.e.a(c0759u2, c0759u3, this.a.a()));
        }
    }
}
