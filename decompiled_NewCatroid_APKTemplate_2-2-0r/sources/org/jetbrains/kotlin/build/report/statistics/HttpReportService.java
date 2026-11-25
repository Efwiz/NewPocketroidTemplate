package org.jetbrains.kotlin.build.report.statistics;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.transport.AmazonS3;
import org.jetbrains.kotlin.buildtools.api.KotlinLogger;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.google.gson.Gson;

/* JADX INFO: compiled from: HttpReportService.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0016\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/build/report/statistics/HttpReportService;", "Ljava/io/Serializable;", "url", "", AmazonS3.Keys.PASSWORD, ConfigConstants.CONFIG_USER_SECTION, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "invalidUrl", "", "requestPreviousFailed", "checkResponseAndLog", "", "connection", "Ljava/net/HttpURLConnection;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Lorg/jetbrains/kotlin/buildtools/api/KotlinLogger;", "sendData", "data", "", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HttpReportService implements Serializable {
    private boolean invalidUrl;
    private final String password;
    private boolean requestPreviousFailed;
    private final String url;
    private final String user;

    public HttpReportService(String url, String str, String str2) {
        Intrinsics.checkNotNullParameter(url, "url");
        this.url = url;
        this.password = str;
        this.user = str2;
    }

    private final void checkResponseAndLog(HttpURLConnection connection, KotlinLogger log) throws IOException {
        int responseCode = connection.getResponseCode();
        boolean z = false;
        if (200 <= responseCode && responseCode < 300) {
            z = true;
        }
        if (z) {
            return;
        }
        String str = "Failed to send statistic to " + connection.getURL() + " with " + connection.getResponseCode() + ": " + connection.getResponseMessage();
        if (!this.requestPreviousFailed) {
            log.warn(str);
        } else {
            log.debug(str);
        }
        this.requestPreviousFailed = true;
    }

    public final void sendData(Object data, KotlinLogger log) {
        HttpURLConnection httpURLConnection;
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(log, "log");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.invalidUrl) {
            return;
        }
        try {
            try {
                URLConnection uRLConnectionOpenConnection = new URL(this.url).openConnection();
                Intrinsics.checkNotNull(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                try {
                    if (this.user != null && this.password != null) {
                        Base64.Encoder encoder = Base64.getEncoder();
                        byte[] bytes = (this.user + ':' + this.password).getBytes(Charsets.UTF_8);
                        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                        byte[] bArrEncode = encoder.encode(bytes);
                        Intrinsics.checkNotNullExpressionValue(bArrEncode, "encode(...)");
                        httpURLConnection.addRequestProperty("Authorization", "Basic ".concat(new String(bArrEncode, Charsets.UTF_8)));
                    }
                    httpURLConnection.addRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    try {
                        String json = new Gson().toJson(data);
                        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
                        byte[] bytes2 = json.getBytes(Charsets.UTF_8);
                        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
                        outputStream.write(bytes2);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(outputStream, null);
                        httpURLConnection.connect();
                        checkResponseAndLog(httpURLConnection, log);
                        httpURLConnection.disconnect();
                        log.debug("Report statistic by http takes " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(outputStream, th);
                            throw th2;
                        }
                    }
                } catch (Exception e) {
                    log.warn("Unexpected exception happened " + e.getMessage() + ": " + e.getStackTrace());
                    checkResponseAndLog(httpURLConnection, log);
                }
            } catch (IOException e2) {
                log.warn("Unable to open connection to " + this.url + ": " + e2.getMessage());
                this.invalidUrl = true;
            }
        } catch (Throwable th3) {
            httpURLConnection.disconnect();
            throw th3;
        }
    }
}
