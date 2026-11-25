package com.android.tools.r8;

import com.android.tools.r8.internal.AbstractC2619nY;
import com.android.tools.r8.utils.Y2;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ParseFlagPrinter {
    static final /* synthetic */ boolean g = true;
    private final ArrayList a = new ArrayList();
    private String b = "  ";
    private int c = 25;
    private String d = " # ";
    private StringBuilder e = null;
    private int f = -1;

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        if (b()) {
            if (!g && !b()) {
                throw new AssertionError();
            }
            this.e.append(Y2.c);
            this.f = -1;
        }
        if (!g && b()) {
            throw new AssertionError();
        }
        this.f = 0;
        c(this.b);
        c(str);
    }

    private boolean b() {
        return this.f >= 0;
    }

    private void c(String str) {
        if (!g && !b()) {
            throw new AssertionError();
        }
        this.e.append(str);
        this.f = str.length() + this.f;
    }

    public static void main(String[] strArr) {
        D8.main(new String[]{"--help"});
    }

    public ParseFlagPrinter addFlags(List<ParseFlagInfo> list) {
        this.a.addAll(list);
        return this;
    }

    public void appendLinesToBuilder(StringBuilder sb) {
        boolean z = g;
        if (!z && this.e != null) {
            throw new AssertionError();
        }
        if (!z && this.f != -1) {
            throw new AssertionError();
        }
        this.e = sb;
        a();
        this.e = null;
        this.f = -1;
    }

    public ParseFlagPrinter setHelpColumn(int i) {
        this.c = i;
        return this;
    }

    public ParseFlagPrinter setHelpSeparator(String str) {
        this.d = str;
        return this;
    }

    public ParseFlagPrinter setIndent(int i) {
        return setPrefix(AbstractC2619nY.a(i, " "));
    }

    public ParseFlagPrinter setPrefix(String str) {
        this.b = str;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        if (this.f > this.c) {
            if (!g && !b()) {
                throw new AssertionError();
            }
            this.e.append(Y2.c);
            this.f = -1;
        }
        if (!b()) {
            if (!g && b()) {
                throw new AssertionError();
            }
            this.f = 0;
            c(this.b);
        }
        int i = this.c - this.f;
        if (!g && !b()) {
            throw new AssertionError();
        }
        for (int i2 = 0; i2 < i; i2++) {
            this.e.append(StringUtil.SPACE);
        }
        this.f += i;
        c(this.d);
        c(str);
        if (!g && !b()) {
            throw new AssertionError();
        }
        this.e.append(Y2.c);
        this.f = -1;
    }

    private void a() {
        for (ParseFlagInfo parseFlagInfo : this.a) {
            a(parseFlagInfo.getFlagFormat());
            parseFlagInfo.getFlagFormatAlternatives().forEach(new Consumer() { // from class: com.android.tools.r8.ParseFlagPrinter$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((String) obj);
                }
            });
            parseFlagInfo.getFlagHelp().forEach(new Consumer() { // from class: com.android.tools.r8.ParseFlagPrinter$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((String) obj);
                }
            });
        }
    }
}
