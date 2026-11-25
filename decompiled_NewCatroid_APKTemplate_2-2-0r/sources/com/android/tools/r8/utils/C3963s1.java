package com.android.tools.r8.utils;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.GlobalSyntheticsConsumer;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.utils.s1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3963s1 extends AbstractC3975v1 implements ClassFileConsumer {
    public C3963s1(GlobalSyntheticsConsumer globalSyntheticsConsumer, C0702m c0702m) {
        super(globalSyntheticsConsumer, c0702m);
    }

    @Override // com.android.tools.r8.utils.AbstractC3975v1
    final ProgramResource.Kind a() {
        return ProgramResource.Kind.CF;
    }

    @Override // com.android.tools.r8.ClassFileConsumer
    public final void accept(ByteDataView byteDataView, String str, DiagnosticsHandler diagnosticsHandler) {
        ((AbstractC3975v1) this).c.put(((AbstractC3975v1) this).a.a().e(str), byteDataView.copyByteData());
    }
}
