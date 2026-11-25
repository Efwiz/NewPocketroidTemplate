package com.android.tools.r8.utils;

import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.ProgramResource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.utils.j, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3911j implements ClassFileResourceProvider {
    final /* synthetic */ Map a;

    C3911j(HashMap map) {
        this.a = map;
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final Set getClassDescriptors() {
        return this.a.keySet();
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final ProgramResource getProgramResource(String str) {
        return (ProgramResource) this.a.get(str);
    }
}
