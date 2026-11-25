package io.github.rosemoe.sora.util;

import android.os.Handler;
import android.os.Looper;
import kotlin.Metadata;

/* JADX INFO: compiled from: EditorHandler.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lio/github/rosemoe/sora/util/EditorHandler;", "Landroid/os/Handler;", "()V", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EditorHandler extends Handler {
    public static final EditorHandler INSTANCE = new EditorHandler();

    private EditorHandler() {
        super(Looper.getMainLooper());
    }
}
