package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.framework.common.ContainerUtils;
import org.apache.commons.compress.compressors.bzip2.BZip2Constants;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H8 {
    static final /* synthetic */ boolean e = true;
    public final int a;
    public final EnumC1812d20 b;
    public final C0767v2 c;
    private final K8 d;

    private H8(int i, K8 k8) {
        this.a = i;
        this.d = k8;
        this.b = k8.a();
        this.c = k8.b();
    }

    public static boolean a(int i) {
        return i >= 100000;
    }

    public static int b(int i) {
        boolean z = e;
        if (!z && !a(i)) {
            throw new AssertionError();
        }
        if (z || i >= 100000) {
            return i - BZip2Constants.BASEBLOCKSIZE;
        }
        throw new AssertionError();
    }

    public final String toString() {
        int i = this.a;
        if (i < 100000) {
            return i + ContainerUtils.KEY_VALUE_DELIMITER + this.d;
        }
        return "s" + (i - BZip2Constants.BASEBLOCKSIZE) + ContainerUtils.KEY_VALUE_DELIMITER + this.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static H8 a(int i, K8 k8) {
        return new H8(i + BZip2Constants.BASEBLOCKSIZE, k8);
    }

    public final boolean a() {
        K8 k8 = this.d;
        k8.getClass();
        return k8 instanceof J8;
    }
}
