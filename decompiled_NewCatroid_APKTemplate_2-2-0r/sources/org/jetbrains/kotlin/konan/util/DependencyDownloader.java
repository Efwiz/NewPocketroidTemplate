package org.jetbrains.kotlin.konan.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FileAlreadyExistsException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: DependencyDownloader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 72\u00020\u0001:\u0004789:Bp\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012U\b\u0002\u0010\u0006\u001aO\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0007j\u0004\u0018\u0001`\u000f¢\u0006\u0002\u0010\u0010J8\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010(\u001a\u00020)H\u0002J \u0010*\u001a\u00020'2\u0006\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020'2\b\b\u0002\u0010-\u001a\u00020.J \u0010/\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020#2\u0006\u00100\u001a\u0002012\u0006\u0010&\u001a\u00020'H\u0002J\u0018\u00102\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0002J(\u00103\u001a\u00020\u000e*\u0002012\u0006\u0010\"\u001a\u00020#2\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020)05H\u0002J\u001e\u00103\u001a\u00020\u000e*\u0002012\u0006\u00106\u001a\u00020\u00032\b\b\u0002\u0010\"\u001a\u00020#H\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cRW\u0010\u001d\u001aK\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u000e0\u0007j\u0002`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001e\u001a\u00020\b*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/DependencyDownloader;", "", "maxAttempts", "", "attemptIntervalMs", "", "customProgressCallback", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", "url", "currentBytes", "totalBytes", "", "Lorg/jetbrains/kotlin/konan/util/ProgressCallback;", "(IJLkotlin/jvm/functions/Function3;)V", "getAttemptIntervalMs", "()J", "setAttemptIntervalMs", "(J)V", "executor", "Ljava/util/concurrent/ExecutorCompletionService;", "getExecutor", "()Ljava/util/concurrent/ExecutorCompletionService;", "getMaxAttempts", "()I", "setMaxAttempts", "(I)V", "progressCallback", "humanReadable", "getHumanReadable", "(J)Ljava/lang/String;", "doDownload", "originalUrl", "Ljava/net/URL;", "connection", "Ljava/net/URLConnection;", "tmpFile", "Ljava/io/File;", "append", "", "download", FirebaseAnalytics.Param.SOURCE, FirebaseAnalytics.Param.DESTINATION, SchemaSymbols.ATTVAL_REPLACE, "Lorg/jetbrains/kotlin/konan/util/DependencyDownloader$ReplacingMode;", "resumeDownload", "originalConnection", "Ljava/net/HttpURLConnection;", "tryDownload", "checkHTTPResponse", "predicate", "Lkotlin/Function1;", "expected", "Companion", "DownloadingProgress", "HTTPResponseException", "ReplacingMode", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DependencyDownloader {
    public static final long DEFAULT_ATTEMPT_INTERVAL_MS = 3000;
    public static final int DEFAULT_MAX_ATTEMPTS = 10;
    public static final String TMP_SUFFIX = "part";
    private long attemptIntervalMs;
    private final ExecutorCompletionService<Unit> executor;
    private int maxAttempts;
    private final Function3<String, Long, Long, Unit> progressCallback;

    /* JADX INFO: compiled from: DependencyDownloader.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/DependencyDownloader$ReplacingMode;", "", "(Ljava/lang/String;I)V", "REPLACE", "THROW", "RETURN_EXISTING", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum ReplacingMode {
        REPLACE,
        THROW,
        RETURN_EXISTING
    }

    /* JADX INFO: compiled from: DependencyDownloader.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ReplacingMode.values().length];
            try {
                iArr[ReplacingMode.RETURN_EXISTING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ReplacingMode.THROW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ReplacingMode.REPLACE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DependencyDownloader() {
        this(0, 0L, null, 7, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DependencyDownloader(int i, long j, Function3<? super String, ? super Long, ? super Long, Unit> function3) {
        this.maxAttempts = i;
        this.attemptIntervalMs = j;
        if (function3 == 0) {
            throw new NotImplementedError(null, 1, null);
        }
        this.progressCallback = function3;
        this.executor = new ExecutorCompletionService<>(Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: org.jetbrains.kotlin.konan.util.DependencyDownloader$executor$1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("konan-dependency-downloader");
                thread.setDaemon(true);
                return thread;
            }
        }));
    }

    public /* synthetic */ DependencyDownloader(int i, long j, Function3 function3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 10 : i, (i2 & 2) != 0 ? DEFAULT_ATTEMPT_INTERVAL_MS : j, (i2 & 4) != 0 ? null : function3);
    }

    public final int getMaxAttempts() {
        return this.maxAttempts;
    }

    public final void setMaxAttempts(int i) {
        this.maxAttempts = i;
    }

    public final long getAttemptIntervalMs() {
        return this.attemptIntervalMs;
    }

    public final void setAttemptIntervalMs(long j) {
        this.attemptIntervalMs = j;
    }

    public final ExecutorCompletionService<Unit> getExecutor() {
        return this.executor;
    }

    /* JADX INFO: compiled from: DependencyDownloader.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/DependencyDownloader$HTTPResponseException;", "Ljava/io/IOException;", "url", "Ljava/net/URL;", "responseCode", "", "(Ljava/net/URL;I)V", "getResponseCode", "()I", "getUrl", "()Ljava/net/URL;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class HTTPResponseException extends IOException {
        private final int responseCode;
        private final URL url;

        public final int getResponseCode() {
            return this.responseCode;
        }

        public final URL getUrl() {
            return this.url;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public HTTPResponseException(URL url, int i) {
            super("Server returned HTTP response code: " + i + " for URL: " + url);
            Intrinsics.checkNotNullParameter(url, "url");
            this.url = url;
            this.responseCode = i;
        }
    }

    /* JADX INFO: compiled from: DependencyDownloader.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/DependencyDownloader$DownloadingProgress;", "", "currentBytes", "", "(J)V", "getCurrentBytes", "()J", "setCurrentBytes", ConfigConstants.CONFIG_KEY_UPDATE, "", "readBytes", "", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class DownloadingProgress {
        private volatile long currentBytes;

        public DownloadingProgress(long j) {
            this.currentBytes = j;
        }

        public final long getCurrentBytes() {
            return this.currentBytes;
        }

        public final void setCurrentBytes(long j) {
            this.currentBytes = j;
        }

        public final void update(int readBytes) {
            this.currentBytes += (long) readBytes;
        }
    }

    private final void checkHTTPResponse(HttpURLConnection httpURLConnection, int i, URL url) throws HTTPResponseException {
        if (httpURLConnection.getResponseCode() != i) {
            throw new HTTPResponseException(url, httpURLConnection.getResponseCode());
        }
    }

    private final void checkHTTPResponse(HttpURLConnection httpURLConnection, URL url, Function1<? super Integer, Boolean> function1) throws HTTPResponseException {
        if (!function1.invoke(Integer.valueOf(httpURLConnection.getResponseCode())).booleanValue()) {
            throw new HTTPResponseException(url, httpURLConnection.getResponseCode());
        }
    }

    private final void doDownload(URL originalUrl, final URLConnection connection, final File tmpFile, long currentBytes, final long totalBytes, final boolean append) throws Throwable {
        Future<Unit> futurePoll;
        final DownloadingProgress downloadingProgress = new DownloadingProgress(currentBytes);
        this.executor.submit(new Callable() { // from class: org.jetbrains.kotlin.konan.util.DependencyDownloader.doDownload.1
            @Override // java.util.concurrent.Callable
            public /* bridge */ /* synthetic */ Object call() throws IOException {
                call();
                return Unit.INSTANCE;
            }

            @Override // java.util.concurrent.Callable
            public final void call() throws IOException {
                InputStream inputStream = connection.getInputStream();
                File file = tmpFile;
                boolean z = append;
                DownloadingProgress downloadingProgress2 = downloadingProgress;
                long j = totalBytes;
                try {
                    InputStream inputStream2 = inputStream;
                    FileOutputStream fileOutputStream = new FileOutputStream(file, z);
                    try {
                        FileOutputStream fileOutputStream2 = fileOutputStream;
                        byte[] bArr = new byte[8192];
                        for (int i = inputStream2.read(bArr); i != -1; i = inputStream2.read(bArr)) {
                            if (Thread.interrupted()) {
                                throw new InterruptedException();
                            }
                            fileOutputStream2.write(bArr, 0, i);
                            downloadingProgress2.update(i);
                        }
                        if (downloadingProgress2.getCurrentBytes() != j) {
                            throw new EOFException("The stream closed before end of downloading.");
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(fileOutputStream, null);
                        Unit unit2 = Unit.INSTANCE;
                        CloseableKt.closeFinally(inputStream, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(fileOutputStream, th);
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    try {
                        throw th3;
                    } catch (Throwable th4) {
                        CloseableKt.closeFinally(inputStream, th3);
                        throw th4;
                    }
                }
            }
        });
        do {
            Function3<String, Long, Long, Unit> function3 = this.progressCallback;
            String string = originalUrl.toString();
            Intrinsics.checkNotNullExpressionValue(string, "originalUrl.toString()");
            function3.invoke(string, Long.valueOf(downloadingProgress.getCurrentBytes()), Long.valueOf(totalBytes));
            futurePoll = this.executor.poll(1L, TimeUnit.SECONDS);
        } while (futurePoll == null);
        Function3<String, Long, Long, Unit> function4 = this.progressCallback;
        String string2 = originalUrl.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "originalUrl.toString()");
        function4.invoke(string2, Long.valueOf(downloadingProgress.getCurrentBytes()), Long.valueOf(totalBytes));
        try {
            futurePoll.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                throw cause;
            }
            throw e;
        }
    }

    private final void resumeDownload(URL originalUrl, HttpURLConnection originalConnection, File tmpFile) throws Throwable {
        originalConnection.connect();
        long contentLengthLong = originalConnection.getContentLengthLong();
        long length = tmpFile.length();
        if (length >= contentLengthLong || !Intrinsics.areEqual(originalConnection.getHeaderField("Accept-Ranges"), "bytes")) {
            doDownload(originalUrl, originalConnection, tmpFile, 0L, contentLengthLong, false);
            return;
        }
        originalConnection.disconnect();
        URLConnection uRLConnectionOpenConnection = originalUrl.openConnection();
        Intrinsics.checkNotNull(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
        httpURLConnection.setRequestProperty("range", "bytes=" + length + '-');
        httpURLConnection.connect();
        checkHTTPResponse(httpURLConnection, originalUrl, new Function1<Integer, Boolean>() { // from class: org.jetbrains.kotlin.konan.util.DependencyDownloader.resumeDownload.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final Boolean invoke(int i) {
                return Boolean.valueOf(i == 206 || i == 200);
            }
        });
        doDownload(originalUrl, httpURLConnection, tmpFile, length, contentLengthLong, true);
    }

    private final void tryDownload(URL url, File tmpFile) throws Throwable {
        URLConnection connection = url.openConnection();
        boolean z = connection instanceof HttpURLConnection;
        HttpURLConnection httpURLConnection = z ? (HttpURLConnection) connection : null;
        if (httpURLConnection != null) {
            checkHTTPResponse(httpURLConnection, 200, url);
        }
        if (z && tmpFile.exists()) {
            resumeDownload(url, (HttpURLConnection) connection, tmpFile);
            return;
        }
        connection.connect();
        long contentLengthLong = connection.getContentLengthLong();
        Intrinsics.checkNotNullExpressionValue(connection, "connection");
        doDownload(url, connection, tmpFile, 0L, contentLengthLong, false);
    }

    public static /* synthetic */ File download$default(DependencyDownloader dependencyDownloader, URL url, File file, ReplacingMode replacingMode, int i, Object obj) {
        if ((i & 4) != 0) {
            replacingMode = ReplacingMode.RETURN_EXISTING;
        }
        return dependencyDownloader.download(url, file, replacingMode);
    }

    public final File download(URL source, File destination, ReplacingMode replace) throws Throwable {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(replace, "replace");
        if (destination.exists()) {
            int i = WhenMappings.$EnumSwitchMapping$0[replace.ordinal()];
            if (i == 1) {
                return destination;
            }
            if (i == 2) {
                throw new FileAlreadyExistsException(destination, null, null, 6, null);
            }
        }
        File file = new File(destination.getCanonicalPath() + ".part");
        if (file.isDirectory()) {
            throw new IllegalStateException(("A temporary file is a directory: " + file.getCanonicalPath() + ". Remove it and try again.").toString());
        }
        if (destination.isDirectory()) {
            throw new IllegalStateException(("The destination file is a directory: " + destination.getCanonicalPath() + ". Remove it and try again.").toString());
        }
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = 1;
        final Ref.LongRef longRef = new Ref.LongRef();
        Function1 function1 = new Function1<Exception, Unit>() { // from class: org.jetbrains.kotlin.konan.util.DependencyDownloader$download$handleException$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Exception exc) throws Exception {
                invoke2(exc);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Exception e) throws Exception {
                Intrinsics.checkNotNullParameter(e, "e");
                if (intRef.element >= this.getMaxAttempts()) {
                    throw e;
                }
                intRef.element++;
                longRef.element += this.getAttemptIntervalMs();
                System.out.println((Object) ("Cannot download a dependency: " + e + "\nWaiting " + (longRef.element / ((double) 1000)) + " sec and trying again (attempt: " + intRef.element + '/' + this.getMaxAttempts() + ")."));
                Thread.sleep(longRef.element);
            }
        };
        while (true) {
            try {
                tryDownload(source, file);
                Files.move(file.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println((Object) "Done.");
                return destination;
            } catch (HTTPResponseException e) {
                if (e.getResponseCode() >= 500) {
                    function1.invoke(e);
                } else {
                    throw e;
                }
            } catch (IOException e2) {
                function1.invoke(e2);
            }
        }
    }
}
