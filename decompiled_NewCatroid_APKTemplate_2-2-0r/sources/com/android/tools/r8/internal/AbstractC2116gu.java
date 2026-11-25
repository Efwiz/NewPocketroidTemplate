package com.android.tools.r8.internal;

import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2116gu {
    public static final Class a(InterfaceC1955ev interfaceC1955ev) {
        AbstractC0936Ds.c(interfaceC1955ev, "<this>");
        Class clsA = ((C1062Ia) ((J9) interfaceC1955ev)).a();
        if (!clsA.isPrimitive()) {
            return clsA;
        }
        String name2 = clsA.getName();
        switch (name2.hashCode()) {
            case -1325958191:
                return !name2.equals("double") ? clsA : Double.class;
            case 104431:
                return !name2.equals("int") ? clsA : Integer.class;
            case 3039496:
                return !name2.equals("byte") ? clsA : Byte.class;
            case 3052374:
                return !name2.equals(PsiKeyword.CHAR) ? clsA : Character.class;
            case 3327612:
                return !name2.equals("long") ? clsA : Long.class;
            case 3625364:
                return !name2.equals(PsiKeyword.VOID) ? clsA : Void.class;
            case 64711720:
                return !name2.equals("boolean") ? clsA : Boolean.class;
            case 97526364:
                return !name2.equals("float") ? clsA : Float.class;
            case 109413500:
                return !name2.equals("short") ? clsA : Short.class;
            default:
                return clsA;
        }
    }
}
