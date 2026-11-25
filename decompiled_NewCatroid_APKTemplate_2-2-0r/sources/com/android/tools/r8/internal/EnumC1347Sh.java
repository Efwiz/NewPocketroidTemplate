package com.android.tools.r8.internal;

import java.lang.reflect.Field;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.Sh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class EnumC1347Sh implements InterfaceC1373Th {
    public static final EnumC1347Sh a = new EnumC1347Sh() { // from class: com.android.tools.r8.internal.Rh
        @Override // com.android.tools.r8.internal.InterfaceC1373Th
        public final String a(Field field) {
            return field.getName();
        }
    };

    private EnumC1347Sh(String str, int i) {
        super(str, i);
    }

    /* synthetic */ EnumC1347Sh(String str, int i, int i2) {
        this(str, i);
    }
}
