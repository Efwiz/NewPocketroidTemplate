package com.android.tools.r8;

import com.android.tools.r8.origin.PathOrigin;
import java.nio.file.Path;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.f, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC0590f extends PathOrigin {
    private final String g;

    public AbstractC0590f(String str, Path path) {
        super(path);
        this.g = str;
    }

    @Override // com.android.tools.r8.origin.PathOrigin, com.android.tools.r8.origin.Origin
    public final String part() {
        return this.g + " '" + super.part() + FormatHelper.QUOTE;
    }
}
