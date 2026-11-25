package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.yt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class EnumC3463yt implements Iterator {
    public static final EnumC3463yt a = new EnumC3463yt();

    private EnumC3463yt() {
        super(JvmAbi.INSTANCE_FIELD, 0);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return false;
    }

    @Override // java.util.Iterator
    public final Object next() {
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public final void remove() {
        AbstractC3123uM.b("no calls to next() since the last call to remove()", false);
    }
}
