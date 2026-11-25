package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0402a;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3080tj extends AbstractC3306wj {
    private String d;

    private C3080tj() {
        this.d = "";
    }

    private void a() {
        String str = this.d + "  ";
        this.d = str;
        e(str);
    }

    final C3080tj f(String str) {
        b("<li class=\"java8_table\"><code>" + str + "</code></li>");
        return this;
    }

    final void g(String str) {
        if (str.length() < 53 || str.contains("()")) {
            f(str);
            return;
        }
        StringBuilder sb = new StringBuilder();
        List<String> listA = com.android.tools.r8.utils.Y2.a(str, '(');
        sb.append(listA.get(0)).append("(<br>&nbsp;");
        if (listA.get(1).length() < 51) {
            sb.append(listA.get(1));
            f(sb.toString());
            return;
        }
        List<String> listA2 = com.android.tools.r8.utils.Y2.a(listA.get(1), ',');
        sb.append("&nbsp;");
        for (int i = 0; i < listA2.size(); i++) {
            sb.append(listA2.get(i));
            if (i != listA2.size() - 1) {
                sb.append(",<br>&nbsp;");
            }
        }
        f(sb.toString());
    }

    final void h(String str) {
        c("<code><br><br><div style=\"font-size:small;font-weight:bold;\">&nbsp;" + a(2, str) + "</div></code><br><br></td>");
    }

    final void i(String str) {
        b("<td><p>" + str + "</p></td>");
    }

    final void j(String str) {
        d("<td><code><em>" + a(4, str) + "</em></code><br>");
        if (str.startsWith("java.time")) {
            a("<a href=\"#java-time-customizations\">See customizations</a><br");
        } else if (str.startsWith("java.nio")) {
            a("<a href=\"#java-nio-customizations\">See customizations</a><br");
        }
    }

    final C3080tj k(String str) {
        String strA = AbstractC0402a.a(this.d, 2, 0);
        this.d = strA;
        this.c = strA;
        b("</" + str + ">");
        return this;
    }

    final C3080tj l(String str) {
        b("<" + str + ">");
        a();
        return this;
    }

    private static String a(int i, String str) {
        List<String> listA = com.android.tools.r8.utils.Y2.a(str, '.');
        if (listA.size() < i) {
            return str;
        }
        int i2 = i / 2;
        int length = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            length += listA.get(i3).length();
        }
        int i4 = length + i2;
        return str.substring(0, i4) + "<br>&nbsp;" + str.substring(i4);
    }
}
