package com.android.tools.r8.internal;

import java.util.List;
import java.util.ServiceLoader;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3192vE extends AbstractC3416yA implements InterfaceC1423Ui {
    public static final C3192vE a = new C3192vE();

    C3192vE() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1423Ui
    public final Object a() {
        ServiceLoader serviceLoaderLoad = ServiceLoader.load(InterfaceC3343xE.class, InterfaceC3343xE.class.getClassLoader());
        AbstractC0936Ds.b(serviceLoaderLoad, "load(MetadataExtensions:…::class.java.classLoader)");
        List listB = AbstractC0823Ab.b(serviceLoaderLoad);
        if (listB.isEmpty()) {
            throw new IllegalStateException("No MetadataExtensions instances found in the classpath. Please ensure that the META-INF/services/ is not stripped from your application and that the Java virtual machine is not running under a security manager".toString());
        }
        return listB;
    }
}
