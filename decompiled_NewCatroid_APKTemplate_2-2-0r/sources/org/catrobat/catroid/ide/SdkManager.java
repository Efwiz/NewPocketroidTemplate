package org.catrobat.catroid.ide;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/SdkManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: SdkManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rH\u0002J0\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rJ\u0012\u0010\u0015\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/ide/SdkManager;", "", "()V", "BASE_URL", "", "REPO_XML_URL", "downloadFile", "", "url", "Ljava/net/URL;", "dest", "Ljava/io/File;", "onProgress", "Lkotlin/Function2;", "Lorg/catrobat/catroid/ide/DownloadStatus;", "", "downloadPlatform", "context", "Landroid/content/Context;", "apiLevel", "", "getPlatformUrlFromRepositoryXml", "unzipPlatform", "zipFile", "destDir", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SdkManager {
    private static final String BASE_URL = "https://dl.google.com/android/repository";
    public static final SdkManager INSTANCE = new SdkManager();
    private static final String REPO_XML_URL = "https://dl.google.com/android/repository/repository2-1.xml";

    private SdkManager() {
    }

    public final boolean downloadPlatform(Context context, int apiLevel, Function2<? super String, ? super DownloadStatus, Unit> onProgress) throws Throwable {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onProgress, "onProgress");
        File file = null;
        try {
            try {
                onProgress.invoke("Поиск API " + apiLevel + "...", DownloadStatus.Connecting.INSTANCE);
                URL platformUrlFromRepositoryXml = getPlatformUrlFromRepositoryXml(apiLevel);
                if (platformUrlFromRepositoryXml == null) {
                    onProgress.invoke("Версия API " + apiLevel + " не найдена", new DownloadStatus.Error("404 Not Found"));
                    return false;
                }
                File file2 = new File(context.getCacheDir(), "temp_sdk.zip");
                try {
                    if (file2.exists()) {
                        file2.delete();
                    }
                    if (!downloadFile(platformUrlFromRepositoryXml, file2, onProgress)) {
                        try {
                            if (file2.exists()) {
                                file2.delete();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                    onProgress.invoke("Распаковка архива...", new DownloadStatus.Downloading(1.0f));
                    File file3 = new File(IdeSettings.INSTANCE.getSdkDir(context), "platforms/android-" + apiLevel);
                    if (file3.exists()) {
                        FilesKt.deleteRecursively(file3);
                    }
                    unzipPlatform(file2, file3);
                    if (new File(file3, "android.jar").exists()) {
                        onProgress.invoke("Установка завершена!", DownloadStatus.Success.INSTANCE);
                        try {
                            if (file2.exists()) {
                                file2.delete();
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        return true;
                    }
                    FilesKt.deleteRecursively(file3);
                    onProgress.invoke("Ошибка: в архиве нет android.jar", new DownloadStatus.Error("Invalid SDK structure"));
                    try {
                        if (file2.exists()) {
                            file2.delete();
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    return false;
                } catch (Exception e4) {
                    e = e4;
                    file = file2;
                    e.printStackTrace();
                    String str = "Ошибка: " + e.getMessage();
                    String message = e.getMessage();
                    if (message == null) {
                        message = "Unknown Error";
                    }
                    onProgress.invoke(str, new DownloadStatus.Error(message));
                    if (file != null) {
                        try {
                            if (file.exists()) {
                                file.delete();
                            }
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    file = file2;
                    if (file != null) {
                        try {
                            if (file.exists()) {
                                file.delete();
                            }
                        } catch (Exception e6) {
                            e6.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e7) {
                e = e7;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private final URL getPlatformUrlFromRepositoryXml(int apiLevel) {
        try {
            String str = new String(TextStreamsKt.readBytes(new URL(REPO_XML_URL)), Charsets.UTF_8);
            XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlPullParserNewPullParser.setInput(new StringReader(str));
            boolean z = false;
            for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
                if (eventType != 2) {
                    if (eventType == 3 && Intrinsics.areEqual(xmlPullParserNewPullParser.getName(), "remotePackage")) {
                        z = false;
                    }
                } else {
                    if (Intrinsics.areEqual(xmlPullParserNewPullParser.getName(), "remotePackage") && Intrinsics.areEqual(xmlPullParserNewPullParser.getAttributeValue(null, "path"), "platforms;android-" + apiLevel)) {
                        z = true;
                    }
                    if (z && Intrinsics.areEqual(xmlPullParserNewPullParser.getName(), "url")) {
                        return new URL("https://dl.google.com/android/repository/" + xmlPullParserNewPullParser.nextText());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private final boolean downloadFile(URL url, File dest, Function2<? super String, ? super DownloadStatus, Unit> onProgress) {
        Throwable th;
        Throwable th2;
        int i = 0;
        try {
            URLConnection uRLConnectionOpenConnection = url.openConnection();
            Intrinsics.checkNotNull(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() != 200) {
                onProgress.invoke("Ошибка сервера: " + httpURLConnection.getResponseCode(), new DownloadStatus.Error("HTTP " + httpURLConnection.getResponseCode()));
                return false;
            }
            long contentLengthLong = httpURLConnection.getContentLengthLong();
            float f = contentLengthLong;
            float f2 = (f / 1024.0f) / 1024.0f;
            InputStream inputStream = httpURLConnection.getInputStream();
            try {
                InputStream inputStream2 = inputStream;
                FileOutputStream fileOutputStream = new FileOutputStream(dest);
                try {
                    FileOutputStream fileOutputStream2 = fileOutputStream;
                    byte[] bArr = new byte[16384];
                    long j = 0;
                    long j2 = 0;
                    while (true) {
                        int i2 = inputStream2.read(bArr);
                        if (i2 != -1) {
                            try {
                                fileOutputStream2.write(bArr, i, i2);
                                inputStream = inputStream;
                                j += (long) i2;
                                try {
                                    long jCurrentTimeMillis = System.currentTimeMillis();
                                    if (jCurrentTimeMillis - j2 > 100 || j == contentLengthLong) {
                                        if (contentLengthLong > 0) {
                                            float f3 = j;
                                            float f4 = (f3 / 1024.0f) / 1024.0f;
                                            float f5 = f3 / f;
                                            String str = String.format("Загрузка: %.1f / %.1f MB", Arrays.copyOf(new Object[]{Float.valueOf(f4), Float.valueOf(f2)}, 2));
                                            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                                            onProgress.invoke(str, new DownloadStatus.Downloading(f5));
                                        } else {
                                            String str2 = String.format("Загружено: %.1f MB", Arrays.copyOf(new Object[]{Float.valueOf((j / 1024.0f) / 1024.0f)}, 1));
                                            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                                            onProgress.invoke(str2, DownloadStatus.Connecting.INSTANCE);
                                        }
                                        f = f;
                                        j2 = jCurrentTimeMillis;
                                    }
                                    i = 0;
                                } catch (Throwable th3) {
                                    th2 = th3;
                                    inputStream = inputStream;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                th2 = th;
                            }
                        } else {
                            InputStream inputStream3 = inputStream;
                            try {
                                Unit unit = Unit.INSTANCE;
                                try {
                                    CloseableKt.closeFinally(fileOutputStream, null);
                                    Unit unit2 = Unit.INSTANCE;
                                    CloseableKt.closeFinally(inputStream3, null);
                                    return true;
                                } catch (Throwable th5) {
                                    th = th5;
                                    inputStream = inputStream3;
                                    th = th;
                                    try {
                                        throw th;
                                    } catch (Throwable th6) {
                                        CloseableKt.closeFinally(inputStream, th);
                                        throw th6;
                                    }
                                }
                            } catch (Throwable th7) {
                                th = th7;
                                inputStream = inputStream3;
                                th2 = th;
                                throw th2;
                            }
                        }
                        th2 = th;
                        try {
                            throw th2;
                        } catch (Throwable th8) {
                            CloseableKt.closeFinally(fileOutputStream, th2);
                            throw th8;
                        }
                    }
                } catch (Throwable th9) {
                    th = th9;
                }
            } catch (Throwable th10) {
                th = th10;
                th = th;
                throw th;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String str3 = "Сбой загрузки: " + e.getMessage();
            String message = e.getMessage();
            if (message == null) {
                message = "Network Error";
            }
            onProgress.invoke(str3, new DownloadStatus.Error(message));
            return false;
        }
    }

    private final void unzipPlatform(File zipFile, File destDir) {
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        try {
            ZipInputStream zipInputStream2 = zipInputStream;
            for (ZipEntry nextEntry = zipInputStream2.getNextEntry(); nextEntry != null; nextEntry = zipInputStream2.getNextEntry()) {
                String name2 = nextEntry.getName();
                Intrinsics.checkNotNull(name2);
                int iIndexOf$default = StringsKt.indexOf$default((CharSequence) name2, '/', 0, false, 6, (Object) null);
                if (iIndexOf$default != -1) {
                    String strSubstring = name2.substring(iIndexOf$default + 1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    if (strSubstring.length() > 0 && !nextEntry.isDirectory()) {
                        File file = new File(destDir, strSubstring);
                        File parentFile = file.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            FileOutputStream fileOutputStream2 = fileOutputStream;
                            byte[] bArr = new byte[4096];
                            while (true) {
                                int i = zipInputStream2.read(bArr);
                                if (i <= 0) {
                                    break;
                                } else {
                                    fileOutputStream2.write(bArr, 0, i);
                                }
                            }
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(fileOutputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileOutputStream, th);
                                throw th2;
                            }
                        }
                    }
                }
                zipInputStream2.closeEntry();
            }
            Unit unit2 = Unit.INSTANCE;
            CloseableKt.closeFinally(zipInputStream, null);
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(zipInputStream, th3);
                throw th4;
            }
        }
    }
}
