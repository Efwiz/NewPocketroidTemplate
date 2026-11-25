package com.android.tools.r8.origin;

import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class PathOrigin extends Origin {
    static final /* synthetic */ boolean f = true;
    private final Path e;

    public PathOrigin(Path path) {
        super(Origin.root());
        if (!f && path == null) {
            throw new AssertionError();
        }
        this.e = path;
    }

    public Path getPath() {
        return this.e;
    }

    @Override // com.android.tools.r8.origin.Origin
    public String part() {
        return this.e.toString();
    }
}
