package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2939rh {
    public static final InterfaceC2653ny a(C2576my c2576my, InterfaceC1475Wi interfaceC1475Wi) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        InterfaceC3343xE.a.getClass();
        Iterator it = C3267wE.a().iterator();
        InterfaceC2653ny interfaceC2653ny = null;
        while (it.hasNext()) {
            InterfaceC2653ny interfaceC2653ny2 = (InterfaceC2653ny) interfaceC1475Wi.a((InterfaceC3343xE) it.next());
            if (interfaceC2653ny2 != null) {
                if (interfaceC2653ny != null) {
                    throw new IllegalStateException("Multiple extensions handle the same extension type: " + c2576my);
                }
                interfaceC2653ny = interfaceC2653ny2;
            }
        }
        return interfaceC2653ny;
    }

    public static final InterfaceC2501ly a(ArrayList arrayList, C2576my c2576my) {
        AbstractC0936Ds.c(arrayList, "<this>");
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        Iterator it = arrayList.iterator();
        InterfaceC2501ly interfaceC2501ly = null;
        while (it.hasNext()) {
            InterfaceC2501ly interfaceC2501ly2 = (InterfaceC2501ly) it.next();
            if (AbstractC0936Ds.a(interfaceC2501ly2.getType(), c2576my)) {
                if (interfaceC2501ly != null) {
                    throw new IllegalStateException("Multiple extensions handle the same extension type: " + c2576my);
                }
                interfaceC2501ly = interfaceC2501ly2;
            }
        }
        if (interfaceC2501ly != null) {
            return interfaceC2501ly;
        }
        throw new IllegalStateException("No extensions handle the extension type: " + c2576my);
    }
}
