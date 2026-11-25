package io.github.rosemoe.sora.lang.completion;

import kotlin.Metadata;

/* JADX INFO: compiled from: filters.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006¨\u0006\u000b"}, d2 = {"Lio/github/rosemoe/sora/lang/completion/Arrow;", "", "()V", "Diag", "", "getDiag", "()I", "Left", "getLeft", "LeftLeft", "getLeftLeft", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Arrow {
    public static final Arrow INSTANCE = new Arrow();
    private static final int Diag = 1;
    private static final int Left = 2;
    private static final int LeftLeft = 3;

    private Arrow() {
    }

    public final int getDiag() {
        return Diag;
    }

    public final int getLeft() {
        return Left;
    }

    public final int getLeftLeft() {
        return LeftLeft;
    }
}
