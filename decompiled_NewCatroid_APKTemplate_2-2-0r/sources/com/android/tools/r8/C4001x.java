package com.android.tools.r8;

import com.android.tools.r8.dex.InterfaceC0407b;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.A1;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.x, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C4001x {
    private final Map a;

    private C4001x(Map map) {
        this.a = map;
    }

    public static void a(D8Command d8Command, Boolean bool, Map<String, Integer> map) throws CompilationFailedException {
        A1 a1B = d8Command.b();
        a1B.t0 = true;
        a1B.A0 = A1.g.OFF;
        a1B.L0 = false;
        a1B.p1 = bool.booleanValue();
        final C4001x c4001x = new C4001x(map);
        a1B.g = new InterfaceC0407b() { // from class: com.android.tools.r8.x$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.dex.InterfaceC0407b
            public final C0729q2 a(C0729q2 c0729q2, C0729q2 c0729q3) {
                return this.f$0.a(c0729q2, c0729q3);
            }
        };
        D8.a(d8Command.a(), a1B);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0729q2 a(C0729q2 c0729q2, C0729q2 c0729q3) {
        String strPart = c0729q2.c.parent().part();
        String strPart2 = c0729q3.c.parent().part();
        Integer num = (Integer) this.a.get(strPart);
        Integer num2 = (Integer) this.a.get(strPart2);
        if (num != null && num2 != null) {
            return num.intValue() <= num2.intValue() ? c0729q2 : c0729q3;
        }
        StringBuilder sb = new StringBuilder("Class parent paths not found among input paths: ");
        if (num == null) {
            sb.append(strPart);
        }
        if (num2 == null) {
            if (num == null) {
                sb.append(", ");
            }
            sb.append(strPart2);
        }
        throw new RuntimeException(sb.toString());
    }
}
