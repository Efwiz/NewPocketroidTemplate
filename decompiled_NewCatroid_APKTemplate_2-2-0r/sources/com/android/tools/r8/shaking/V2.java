package com.android.tools.r8.shaking;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class V2 implements T2 {
    private final Path a;

    public V2(Path path) {
        this.a = path;
    }

    @Override // com.android.tools.r8.shaking.T2
    public final Path a() {
        Path parent = this.a.getParent();
        return parent == null ? Paths.get("", new String[0]) : parent;
    }

    @Override // com.android.tools.r8.shaking.T2
    public final String get() {
        return new String(Files.readAllBytes(this.a), StandardCharsets.UTF_8);
    }

    @Override // com.android.tools.r8.shaking.T2
    public final String getName() {
        return this.a.toString();
    }

    @Override // com.android.tools.r8.shaking.T2
    public final Origin getOrigin() {
        return new PathOrigin(this.a);
    }
}
