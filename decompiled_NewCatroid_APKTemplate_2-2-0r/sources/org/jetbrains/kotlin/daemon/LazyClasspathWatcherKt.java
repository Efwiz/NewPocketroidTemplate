package org.jetbrains.kotlin.daemon;

import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LazyClasspathWatcher.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\u001a\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u001a\n\u0010\u000e\u001a\u00020\u000f*\u00020\r\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0014\u0010\b\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\u0010"}, d2 = {"CLASSPATH_FILE_ID_DIGEST", "", "getCLASSPATH_FILE_ID_DIGEST", "()Ljava/lang/String;", "DEFAULT_CLASSPATH_DIGEST_WATCH_PERIOD_MS", "", "getDEFAULT_CLASSPATH_DIGEST_WATCH_PERIOD_MS", "()J", "DEFAULT_CLASSPATH_WATCH_PERIOD_MS", "getDEFAULT_CLASSPATH_WATCH_PERIOD_MS", "isClasspathFile", "", "file", "Ljava/io/File;", "md5Digest", "", "kotlin-daemon"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LazyClasspathWatcherKt {
    private static final String CLASSPATH_FILE_ID_DIGEST = "MD5";
    private static final long DEFAULT_CLASSPATH_DIGEST_WATCH_PERIOD_MS = 300000;
    private static final long DEFAULT_CLASSPATH_WATCH_PERIOD_MS = 1000;

    public static final String getCLASSPATH_FILE_ID_DIGEST() {
        return CLASSPATH_FILE_ID_DIGEST;
    }

    public static final long getDEFAULT_CLASSPATH_WATCH_PERIOD_MS() {
        return DEFAULT_CLASSPATH_WATCH_PERIOD_MS;
    }

    public static final long getDEFAULT_CLASSPATH_DIGEST_WATCH_PERIOD_MS() {
        return DEFAULT_CLASSPATH_DIGEST_WATCH_PERIOD_MS;
    }

    public static final boolean isClasspathFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!file.isFile()) {
            return false;
        }
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"class", "jar"});
        String lowerCase = FilesKt.getExtension(file).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return listListOf.contains(lowerCase);
    }

    public static final byte[] md5Digest(File file) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(file, "<this>");
        MessageDigest messageDigest = MessageDigest.getInstance(CLASSPATH_FILE_ID_DIGEST);
        DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(file), messageDigest);
        try {
            DigestInputStream digestInputStream2 = digestInputStream;
            while (digestInputStream2.read(new byte[1024]) != -1) {
            }
            digestInputStream2.close();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(digestInputStream, null);
            byte[] bArrDigest = messageDigest.digest();
            Intrinsics.checkNotNullExpressionValue(bArrDigest, "digest(...)");
            return bArrDigest;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(digestInputStream, th);
                throw th2;
            }
        }
    }
}
