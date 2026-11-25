package com.android.tools.r8.internal;

import java.util.AbstractCollection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class U0 extends AbstractCollection implements ZJ {
    protected U0() {
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        InterfaceC1912eK it = iterator();
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                sb.append("}");
                return sb.toString();
            }
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            Object next = it.next();
            if (this == next) {
                sb.append("(this collection)");
            } else {
                sb.append(String.valueOf(next));
            }
            size = i;
        }
    }
}
