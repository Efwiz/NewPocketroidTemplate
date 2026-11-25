package com.android.tools.r8.naming;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.position.TextPosition;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.StringDiagnostic;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.naming.u, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3664u implements AutoCloseable {
    static final /* synthetic */ boolean c = true;
    private final BufferedReader a;
    private final Path b;

    public C3664u(Path path) {
        this.b = path;
        this.a = Files.newBufferedReader(path);
    }

    public final String a(O2 o2) throws IOException {
        if (!c && this.a == null) {
            throw new AssertionError();
        }
        StringBuilder sb = new StringBuilder();
        int i = 1;
        while (true) {
            int i2 = this.a.read();
            if (i2 == -1) {
                return sb.toString();
            }
            char c2 = (char) i2;
            if ((sb.length() == 0 || !Character.isJavaIdentifierPart(c2)) && !(sb.length() == 0 && Character.isJavaIdentifierStart(c2))) {
                boolean z = c2 == '#';
                boolean z2 = c2 == '\n' || c2 == '\r';
                if (z || z2) {
                    if (z) {
                        this.a.readLine();
                    }
                    i++;
                }
                if (z2 && sb.length() != 0) {
                    return sb.toString();
                }
                sb = new StringBuilder();
                if (!z2) {
                    o2.info(new StringDiagnostic("Invalid character in dictionary '" + c2 + FormatHelper.QUOTE, new PathOrigin(this.b), new TextPosition(0L, i, -1)));
                    this.a.readLine();
                    i++;
                }
            } else {
                sb.append(c2);
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() throws IOException {
        BufferedReader bufferedReader = this.a;
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }

    public static AbstractC1639am a(O2 o2, Path path) {
        if (path != null) {
            HashSet hashSet = new HashSet();
            C1507Xl c1507Xl = new C1507Xl();
            try {
                C3664u c3664u = new C3664u(path);
                try {
                    String strA = c3664u.a(o2);
                    while (!strA.isEmpty()) {
                        if (!hashSet.add(strA)) {
                            o2.error(new StringDiagnostic("Duplicate entry for '" + strA + "' in dictionary", new PathOrigin(path)));
                        }
                        c1507Xl.a(strA);
                        strA = c3664u.a(o2);
                    }
                    c3664u.close();
                } catch (Throwable th) {
                    try {
                        c3664u.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e) {
                o2.error(new ExceptionDiagnostic(e, new PathOrigin(path)));
            }
            return c1507Xl.a();
        }
        return AbstractC1639am.i();
    }
}
