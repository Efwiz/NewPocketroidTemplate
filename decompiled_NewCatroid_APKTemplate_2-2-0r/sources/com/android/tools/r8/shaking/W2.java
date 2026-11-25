package com.android.tools.r8.shaking;

import com.android.tools.r8.origin.Origin;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class W2 implements T2 {
    private final Path a;
    private final List b;
    private final Origin c;

    public W2(List list, Path path, Origin origin) {
        this.a = path;
        this.b = list;
        this.c = origin;
    }

    public static W2 a(List<String> list) {
        return new W2(list, Paths.get("", new String[0]), Origin.unknown());
    }

    @Override // com.android.tools.r8.shaking.T2
    public final String get() {
        return String.join(System.lineSeparator(), this.b);
    }

    @Override // com.android.tools.r8.shaking.T2
    public final String getName() {
        return "<no file>";
    }

    @Override // com.android.tools.r8.shaking.T2
    public final Origin getOrigin() {
        return this.c;
    }

    @Override // com.android.tools.r8.shaking.T2
    public final Path a() {
        return this.a;
    }
}
