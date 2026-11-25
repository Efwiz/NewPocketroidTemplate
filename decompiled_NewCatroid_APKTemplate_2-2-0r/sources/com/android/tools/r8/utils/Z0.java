package com.android.tools.r8.utils;

import com.android.tools.r8.origin.Origin;
import java.nio.file.Path;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class Z0 extends Origin {
    private final Path e;

    protected Z0(Path path) {
        super(Origin.root());
        this.e = path;
    }

    @Override // com.android.tools.r8.origin.Origin
    public final String part() {
        return "flag file argument: '@" + this.e + FormatHelper.QUOTE;
    }
}
