package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.nio.file.Path;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class J0 {
    private final Path a;
    private final AbstractC1639am b;
    private final Origin c;
    private final Position d;

    public J0(Path path, List<String> list, Origin origin, Position position) {
        this.a = path;
        this.b = AbstractC1639am.a(list);
        this.c = origin;
        this.d = position;
    }

    public final Origin a() {
        return this.c;
    }

    public final Path b() {
        return this.a;
    }

    public final Position c() {
        return this.d;
    }

    public final String toString() {
        if (this.b.isEmpty()) {
            return this.a.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append('(');
        AbstractC2963s10 it = this.b.iterator();
        boolean z = true;
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!z) {
                sb.append(',');
            }
            sb.append(str);
            z = false;
        }
        sb.append(')');
        return sb.toString();
    }

    private static boolean a(int i, int i2, String str, String str2) {
        if (i2 >= str2.length()) {
            return i == str.length();
        }
        char cCharAt = str2.charAt(i2);
        if (cCharAt == '*') {
            int i3 = i2 + 1;
            boolean z = str2.length() > i3 && str2.charAt(i3) == '*';
            if (z) {
                i2 = i3;
            }
            int i4 = i2 + 1;
            String strSubstring = str2.substring(i4);
            if (strSubstring.indexOf(42) == -1) {
                int length = strSubstring.length();
                if (i + length > str.length()) {
                    return false;
                }
                if (z || str.substring(i, str.length() - length).indexOf(47) == -1) {
                    return a(str.length() - length, i4, str, str2);
                }
            } else {
                while (i < str.length()) {
                    if (!z && str.charAt(i) == '/') {
                        return false;
                    }
                    if (a(i, i4, str, str2)) {
                        return true;
                    }
                    i++;
                }
            }
        } else {
            if (i >= str.length()) {
                return false;
            }
            if (cCharAt == '?' || cCharAt == str.charAt(i)) {
                return a(i + 1, i2 + 1, str, str2);
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean a(String str) {
        if (this.b.isEmpty()) {
            return true;
        }
        AbstractC2963s10 it = this.b.iterator();
        boolean z = 0;
        while (it.hasNext()) {
            String str2 = (String) it.next();
            int i = str2.charAt(0) == '!' ? 1 : 0;
            if (a(0, i, str, str2)) {
                return i ^ 1;
            }
            z = i;
        }
        return z;
    }
}
