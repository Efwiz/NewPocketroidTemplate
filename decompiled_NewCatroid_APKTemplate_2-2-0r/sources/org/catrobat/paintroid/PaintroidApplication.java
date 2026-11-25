package org.catrobat.paintroid;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PaintroidApplication.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/catrobat/paintroid/PaintroidApplication;", "", "()V", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PaintroidApplication {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static File cacheDir;

    public static final File getCacheDir() {
        return INSTANCE.getCacheDir();
    }

    public static final void setCacheDir(File file) {
        INSTANCE.setCacheDir(file);
    }

    private PaintroidApplication() {
        throw new IllegalArgumentException();
    }

    /* JADX INFO: compiled from: PaintroidApplication.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R&\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/catrobat/paintroid/PaintroidApplication$Companion;", "", "()V", "cacheDir", "Ljava/io/File;", "getCacheDir$annotations", "getCacheDir", "()Ljava/io/File;", "setCacheDir", "(Ljava/io/File;)V", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getCacheDir$annotations() {
        }

        private Companion() {
        }

        public final File getCacheDir() {
            return PaintroidApplication.cacheDir;
        }

        public final void setCacheDir(File file) {
            PaintroidApplication.cacheDir = file;
        }
    }
}
