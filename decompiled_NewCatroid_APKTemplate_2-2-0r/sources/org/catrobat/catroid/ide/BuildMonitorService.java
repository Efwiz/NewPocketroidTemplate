package org.catrobat.catroid.ide;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CancellationException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt__JobKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/BuildMonitorService.class
  classes14.dex
 */
/* JADX INFO: compiled from: BuildMonitorService.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0002J\"\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u0004H\u0002J(\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0018\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J.\u0010\u001e\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0082@¢\u0006\u0002\u0010!J\u0014\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010&\u001a\u00020\rH\u0016J\"\u0010'\u001a\u00020\u00062\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010(\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u0006H\u0016J\u001a\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\"\u0010,\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u00042\b\u0010.\u001a\u0004\u0018\u00010+H\u0002J\"\u0010/\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/catrobat/catroid/ide/BuildMonitorService;", "Landroid/app/Service;", "()V", "CHANNEL_ID", "", "DONE_NOTIFY_ID", "", "PROGRESS_NOTIFY_ID", "serviceJob", "Lkotlinx/coroutines/CompletableJob;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "createNotificationChannel", "", "createProgressNotification", "Landroid/app/Notification;", "title", "percent", "text", "downloadWithProgress", "client", "Lokhttp3/OkHttpClient;", "url", "token", "featureName", "extractApkFromZip", "zipFile", "Ljava/io/File;", "outputStream", "Ljava/io/OutputStream;", "monitorBuild", FirebaseAnalytics.Event.LOGIN, ConfigConstants.CONFIG_BRANCH_SECTION, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onBind", "Landroid/os/IBinder;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "onDestroy", "onStartCommand", OverlayablePolicy.NAME_flags, "startId", "saveApkToDownloads", "Landroid/net/Uri;", "showFinalNotification", CrashHianalyticsData.MESSAGE, "fileUri", "updateProgress", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuildMonitorService extends Service {
    private final CompletableJob serviceJob;
    private final CoroutineScope serviceScope;
    private final String CHANNEL_ID = "BuildMonitorChannel";
    private final int PROGRESS_NOTIFY_ID = 1337;
    private final int DONE_NOTIFY_ID = 1338;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/BuildMonitorService$monitorBuild$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ide.BuildMonitorService$monitorBuild$1, reason: invalid class name */
    /* JADX INFO: compiled from: BuildMonitorService.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ide.BuildMonitorService", f = "BuildMonitorService.kt", i = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2}, l = {60, 88, 117}, m = "monitorBuild", n = {"this", "token", FirebaseAnalytics.Event.LOGIN, ConfigConstants.CONFIG_BRANCH_SECTION, "featureName", "client", "runId", "this", "token", FirebaseAnalytics.Event.LOGIN, "featureName", "client", "runId", "runsUrl", "attempts", "this", "token", FirebaseAnalytics.Event.LOGIN, "featureName", "client", "runId", "isCompleted", "isSuccess"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "I$0", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7"})
    static final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BuildMonitorService.this.monitorBuild(null, null, null, null, this);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public BuildMonitorService() {
        CompletableJob completableJobJob$default = JobKt__JobKt.Job$default((Job) null, 1, (Object) null);
        this.serviceJob = completableJobJob$default;
        this.serviceScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(completableJobJob$default));
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        String stringExtra;
        String stringExtra2;
        String stringExtra3;
        if (intent == null || (stringExtra = intent.getStringExtra("TOKEN")) == null || (stringExtra2 = intent.getStringExtra("LOGIN")) == null || (stringExtra3 = intent.getStringExtra("BRANCH")) == null) {
            return 2;
        }
        String stringExtra4 = intent.getStringExtra("FEATURE_NAME");
        if (stringExtra4 == null) {
            stringExtra4 = "Feature";
        }
        createNotificationChannel();
        startForeground(this.PROGRESS_NOTIFY_ID, createProgressNotification$default(this, "Связь с сервером...", 0, null, 4, null));
        BuildersKt__Builders_commonKt.launch$default(this.serviceScope, null, null, new C41711(stringExtra, stringExtra2, stringExtra3, stringExtra4, null), 3, null);
        return 1;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/BuildMonitorService$onStartCommand$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ide.BuildMonitorService$onStartCommand$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BuildMonitorService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ide.BuildMonitorService$onStartCommand$1", f = "BuildMonitorService.kt", i = {}, l = {44}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41711 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $branch;
        final /* synthetic */ String $featureName;
        final /* synthetic */ String $login;
        final /* synthetic */ String $token;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41711(String str, String str2, String str3, String str4, Continuation<? super C41711> continuation) {
            super(2, continuation);
            this.$token = str;
            this.$login = str2;
            this.$branch = str3;
            this.$featureName = str4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BuildMonitorService.this.new C41711(this.$token, this.$login, this.$branch, this.$featureName, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41711) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.label = 1;
                    if (BuildMonitorService.this.monitorBuild(this.$token, this.$login, this.$branch, this.$featureName, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
                BuildMonitorService buildMonitorService = BuildMonitorService.this;
                String message = e.getMessage();
                if (message == null) {
                    message = "Неизвестная ошибка";
                }
                buildMonitorService.showFinalNotification("Ошибка сборки", message, null);
                BuildMonitorService.this.stopForeground(true);
                BuildMonitorService.this.stopSelf();
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:129:0x0320 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:59:0x0240  */
    /* JADX WARN: Code duplicated, block: B:61:0x0252  */
    /* JADX WARN: Code duplicated, block: B:64:0x026d  */
    /* JADX WARN: Code duplicated, block: B:67:0x02bf A[Catch: all -> 0x0351, TryCatch #1 {all -> 0x0351, blocks: (B:65:0x02b4, B:67:0x02bf, B:70:0x02c7, B:72:0x0304, B:73:0x0316), top: B:119:0x02b4 }] */
    /* JADX WARN: Code duplicated, block: B:69:0x02c5  */
    /* JADX WARN: Code duplicated, block: B:72:0x0304 A[Catch: all -> 0x0351, TryCatch #1 {all -> 0x0351, blocks: (B:65:0x02b4, B:67:0x02bf, B:70:0x02c7, B:72:0x0304, B:73:0x0316), top: B:119:0x02b4 }] */
    /* JADX WARN: Code duplicated, block: B:78:0x033d A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Code duplicated, block: B:80:0x0347 A[LOOP:0: B:62:0x0267->B:80:0x0347, LOOP_END] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v21, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v12, types: [T, java.lang.String] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x0221 -> B:48:0x0224). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:50:0x022b -> B:49:0x0225). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:77:0x033b -> B:79:0x033e). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public final java.lang.Object monitorBuild(java.lang.String r31, java.lang.String r32, java.lang.String r33, java.lang.String r34, kotlin.coroutines.Continuation<? super kotlin.Unit> r35) {
        /*
            Method dump skipped, instruction units count: 1083
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.ide.BuildMonitorService.monitorBuild(java.lang.String, java.lang.String, java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final void downloadWithProgress(OkHttpClient client, String url, String token, String featureName) throws IOException {
        Response responseExecute = client.newCall(new Request.Builder().url(url).header("Authorization", "Bearer " + token).build()).execute();
        try {
            ResponseBody responseBodyBody = responseExecute.body();
            if (responseBodyBody == null) {
                CloseableKt.closeFinally(responseExecute, null);
                return;
            }
            long contentLength = responseBodyBody.getContentLength();
            InputStream inputStreamByteStream = responseBodyBody.byteStream();
            File file = new File(getCacheDir(), "temp_build.zip");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[8192];
            long j = 0;
            while (true) {
                int i = inputStreamByteStream.read(bArr);
                if (i == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, i);
                j += (long) i;
                int i2 = (int) ((((long) 100) * j) / contentLength);
                updateProgress("Скачивание APK...", i2 + "%", i2);
            }
            fileOutputStream.close();
            updateProgress$default(this, "Распаковка...", "Пожалуйста, подождите", 0, 4, null);
            Uri uriSaveApkToDownloads = saveApkToDownloads(file, featureName);
            file.delete();
            if (uriSaveApkToDownloads != null) {
                showFinalNotification("Билд готов!", "Нажмите, чтобы отправить или установить APK", uriSaveApkToDownloads);
            } else {
                showFinalNotification("Ошибка", "Не удалось сохранить файл в Загрузки", null);
            }
            stopForeground(true);
            stopSelf();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(responseExecute, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(responseExecute, th);
                throw th2;
            }
        }
    }

    private final Uri saveApkToDownloads(File zipFile, String featureName) {
        String str = "NewCatroid_" + featureName + "_" + (System.currentTimeMillis() / ((long) 1000)) + ".apk";
        ContentResolver contentResolver = getContentResolver();
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("_display_name", str);
                contentValues.put("mime_type", "application/vnd.android.package-archive");
                contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS);
                Uri uriInsert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
                if (uriInsert == null) {
                    return null;
                }
                OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
                if (outputStreamOpenOutputStream != null) {
                    OutputStream outputStream = outputStreamOpenOutputStream;
                    try {
                        extractApkFromZip(zipFile, outputStream);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(outputStream, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(outputStream, th);
                            throw th2;
                        }
                    }
                }
                return uriInsert;
            }
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!externalStoragePublicDirectory.exists()) {
                externalStoragePublicDirectory.mkdirs();
            }
            File file = new File(externalStoragePublicDirectory, str);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                extractApkFromZip(zipFile, fileOutputStream);
                Unit unit2 = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream, null);
                return FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(fileOutputStream, th3);
                    throw th4;
                }
            }
        } catch (Exception unused) {
            return null;
        }
        return null;
    }

    private final void extractApkFromZip(File zipFile, OutputStream outputStream) {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        try {
            ZipInputStream zipInputStream2 = zipInputStream;
            for (ZipEntry nextEntry = zipInputStream2.getNextEntry(); nextEntry != null; nextEntry = zipInputStream2.getNextEntry()) {
                String name2 = nextEntry.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (StringsKt.endsWith$default(name2, ".apk", false, 2, (Object) null)) {
                    ByteStreamsKt.copyTo$default(zipInputStream2, outputStream, 0, 2, null);
                    break;
                }
                zipInputStream2.closeEntry();
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipInputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(zipInputStream, th);
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showFinalNotification(String title, String message, Uri fileUri) {
        Intent intent;
        PendingIntent activity = null;
        if (fileUri != null) {
            intent = new Intent("android.intent.action.SEND");
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra("android.intent.extra.STREAM", fileUri);
            intent.addFlags(1);
        } else {
            intent = null;
        }
        if (intent != null) {
            activity = PendingIntent.getActivity(this, 0, Intent.createChooser(intent, "Открыть APK"), 201326592);
        }
        NotificationCompat.Builder autoCancel = new NotificationCompat.Builder(this, this.CHANNEL_ID).setSmallIcon(R.drawable.stat_sys_download_done).setContentTitle(title).setContentText(message).setPriority(1).setAutoCancel(true);
        Intrinsics.checkNotNullExpressionValue(autoCancel, "setAutoCancel(...)");
        if (activity != null) {
            autoCancel.setContentIntent(activity);
        }
        ((NotificationManager) getSystemService(NotificationManager.class)).notify(this.DONE_NOTIFY_ID, autoCancel.build());
    }

    static /* synthetic */ void updateProgress$default(BuildMonitorService buildMonitorService, String str, String str2, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = -1;
        }
        buildMonitorService.updateProgress(str, str2, i);
    }

    private final void updateProgress(String title, String text, int percent) {
        ((NotificationManager) getSystemService(NotificationManager.class)).notify(this.PROGRESS_NOTIFY_ID, createProgressNotification(title, percent, text));
    }

    static /* synthetic */ Notification createProgressNotification$default(BuildMonitorService buildMonitorService, String str, int i, String str2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str2 = "";
        }
        return buildMonitorService.createProgressNotification(str, i, str2);
    }

    private final Notification createProgressNotification(String title, int percent, String text) {
        Notification notificationBuild = new NotificationCompat.Builder(this, this.CHANNEL_ID).setSmallIcon(R.drawable.stat_sys_upload).setContentTitle(title).setContentText(text).setOngoing(true).setOnlyAlertOnce(true).setProgress(100, percent >= 0 ? percent : 0, percent < 0).build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "build(...)");
        return notificationBuild;
    }

    private final void createNotificationChannel() {
        ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(this.CHANNEL_ID, "Build Status", 2));
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Job.DefaultImpls.cancel$default((Job) this.serviceJob, (CancellationException) null, 1, (Object) null);
    }
}
