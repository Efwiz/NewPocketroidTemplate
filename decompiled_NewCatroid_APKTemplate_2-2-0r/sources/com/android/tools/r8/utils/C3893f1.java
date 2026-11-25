package com.android.tools.r8.utils;

import com.sun.management.HotSpotDiagnosticMXBean;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;

/* JADX INFO: renamed from: com.android.tools.r8.utils.f1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3893f1 {
    private static volatile HotSpotDiagnosticMXBean a;

    public static void a(Path path, boolean z) throws IOException {
        if (a == null) {
            synchronized (C3893f1.class) {
                if (a == null) {
                    a = (HotSpotDiagnosticMXBean) ManagementFactory.newPlatformMXBeanProxy(ManagementFactory.getPlatformMBeanServer(), "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
                }
            }
        }
        a.dumpHeap(path.toString(), z);
    }
}
