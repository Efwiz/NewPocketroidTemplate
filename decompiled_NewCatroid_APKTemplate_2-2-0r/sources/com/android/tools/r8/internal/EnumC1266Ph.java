package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.Ph, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC1266Ph {
    public static final EnumC1266Ph a = new EnumC1266Ph(Namer.CLASS_KIND_OBJECT, 0);
    public static final EnumC1266Ph b = new EnumC1266Ph("BOOLEAN", 1);
    public static final EnumC1266Ph c = new EnumC1266Ph("BYTE", 2);
    public static final EnumC1266Ph d = new EnumC1266Ph("CHAR", 3);
    public static final EnumC1266Ph e = new EnumC1266Ph("SHORT", 4);
    public static final EnumC1266Ph f = new EnumC1266Ph("INT", 5);
    public static final EnumC1266Ph g = new EnumC1266Ph("FLOAT", 6);
    public static final EnumC1266Ph h = new EnumC1266Ph("LONG", 7);
    public static final EnumC1266Ph i = new EnumC1266Ph("DOUBLE", 8);

    private EnumC1266Ph(String str, int i2) {
        super(str, i2);
    }

    public static EnumC1266Ph a(char c2) {
        if (c2 == 'F') {
            return g;
        }
        if (c2 != 'L') {
            if (c2 == 'S') {
                return e;
            }
            if (c2 == 'V') {
                throw new C3089ts("No member type for void type.");
            }
            if (c2 == 'I') {
                return f;
            }
            if (c2 == 'J') {
                return h;
            }
            if (c2 == 'Z') {
                return b;
            }
            if (c2 != '[') {
                switch (c2) {
                    case 'B':
                        return c;
                    case 'C':
                        return d;
                    case 'D':
                        return i;
                    default:
                        throw new C3405y10("Invalid descriptor char '" + c2 + FormatHelper.QUOTE);
                }
            }
        }
        return a;
    }

    public static EnumC1266Ph a(C0767v2 c0767v2) {
        return a((char) c0767v2.e.e[0]);
    }
}
