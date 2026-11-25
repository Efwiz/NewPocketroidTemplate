package org.catrobat.catroid.ai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveManager.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveManager.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0086@¢\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0011\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0004H\u0087 J\u0011\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0004H\u0087 J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0010\u0010\u001e\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u001f\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0018\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/ai/KoveManager;", "", "()V", "MODEL_DIR_NAME", "", "MODEL_URL", "TAG", "downloadAndExtractModel", "", "context", "Landroid/content/Context;", "progressDialog", "Landroid/app/ProgressDialog;", "getAutocompleteSuggestion", "prefix", "suffix", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getModelDirectory", "Ljava/io/File;", "initIfEnabled", "", "isModelDownloaded", "nativeCompleteKove", "prompt", "nativeInitKove", "modelPath", "restartApplication", "showDownloadDialog", "callback", "Lorg/catrobat/catroid/ai/KoveCallback;", "showRestartDialog", "startDownload", "unzip", "zipFile", "targetDirectory", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KoveManager {
    public static final KoveManager INSTANCE = new KoveManager();
    private static final String MODEL_DIR_NAME = "kove_model";
    private static final String MODEL_URL = "https://github.com/Danveyd/Kove/releases/download/release/kove_v1.zip";
    private static final String TAG = "KoveManager";

    @JvmStatic
    public static final native String nativeCompleteKove(String prompt);

    @JvmStatic
    public static final native boolean nativeInitKove(String modelPath);

    private KoveManager() {
    }

    static {
        try {
            System.loadLibrary(Constants.FLAVOR_POCKET_CODE);
            Log.i(TAG, "Native library 'catroid' loaded successfully.");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Failed to load native library 'catroid'", e);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveManager$getAutocompleteSuggestion$2.class
      classes9.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ai.KoveManager$getAutocompleteSuggestion$2, reason: invalid class name */
    /* JADX INFO: compiled from: KoveManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ai.KoveManager$getAutocompleteSuggestion$2", f = "KoveManager.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
        final /* synthetic */ String $prefix;
        final /* synthetic */ String $suffix;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, String str2, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$prefix = str;
            this.$suffix = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$prefix, this.$suffix, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            Context appContext = CatroidApplication.getAppContext();
            Intrinsics.checkNotNull(appContext);
            if (!KoveManager.isModelDownloaded(appContext)) {
                return "ERROR: Model not downloaded";
            }
            return KoveManager.nativeCompleteKove("<|fim_prefix|>" + this.$prefix + "<|fim_suffix|>" + this.$suffix + "<|fim_middle|>");
        }
    }

    public final Object getAutocompleteSuggestion(String str, String str2, Continuation<? super String> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(str, str2, null), continuation);
    }

    public final File getModelDirectory(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new File(context.getFilesDir(), MODEL_DIR_NAME);
    }

    @JvmStatic
    public static final boolean isModelDownloaded(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        File modelDirectory = INSTANCE.getModelDirectory(context);
        return new File(modelDirectory, "llm.mnn").exists() && new File(modelDirectory, "llm.mnn.weight").exists() && new File(modelDirectory, "llm_config.json").exists() && new File(modelDirectory, "tokenizer.txt").exists();
    }

    @JvmStatic
    public static final void initIfEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean z = defaultSharedPreferences.getBoolean("setting_developer_mode_enabled", false);
        boolean z2 = defaultSharedPreferences.getBoolean("setting_kove_ai_autocomplete", false);
        if (z && z2) {
            KoveManager koveManager = INSTANCE;
            if (isModelDownloaded(context)) {
                final String absolutePath = koveManager.getModelDirectory(context).getAbsolutePath();
                new Thread(new Runnable() { // from class: org.catrobat.catroid.ai.KoveManager$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        KoveManager.initIfEnabled$lambda$0(absolutePath);
                    }
                }).start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initIfEnabled$lambda$0(String str) {
        Intrinsics.checkNotNull(str);
        if (nativeInitKove(str)) {
            Log.i(TAG, "Kove JNI initialization complete. Self-test passed.");
        } else {
            Log.e(TAG, "Kove JNI initialization failed.");
        }
    }

    @JvmStatic
    public static final void showDownloadDialog(final Context context, final KoveCallback callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(callback, "callback");
        new AlertDialog.Builder(context).setTitle(R.string.ai_download_dialog_title).setMessage(R.string.ai_download_dialog_message).setCancelable(false).setPositiveButton(R.string.button_download, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ai.KoveManager$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                KoveManager.showDownloadDialog$lambda$1(context, callback, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ai.KoveManager$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                KoveManager.showDownloadDialog$lambda$2(callback, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDownloadDialog$lambda$1(Context context, KoveCallback callback, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(callback, "$callback");
        INSTANCE.startDownload(context, callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDownloadDialog$lambda$2(KoveCallback callback, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(callback, "$callback");
        dialogInterface.dismiss();
        callback.onResult(false);
    }

    private final void startDownload(final Context context, final KoveCallback callback) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(R.string.ai_download_dialog_title);
        progressDialog.setMessage(context.getString(R.string.ai_downloading_progress, 0));
        progressDialog.setProgressStyle(1);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() { // from class: org.catrobat.catroid.ai.KoveManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                KoveManager.startDownload$lambda$5(context, progressDialog, callback);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startDownload$lambda$5(final Context context, ProgressDialog progressDialog, final KoveCallback callback) throws Throwable {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(progressDialog, "$progressDialog");
        Intrinsics.checkNotNullParameter(callback, "$callback");
        final boolean zDownloadAndExtractModel = INSTANCE.downloadAndExtractModel(context, progressDialog);
        progressDialog.dismiss();
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ai.KoveManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    KoveManager.startDownload$lambda$5$lambda$4(zDownloadAndExtractModel, context, callback);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startDownload$lambda$5$lambda$4(boolean z, Context context, KoveCallback callback) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(callback, "$callback");
        if (z) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("setting_kove_ai_autocomplete", true).apply();
            INSTANCE.showRestartDialog(context);
            callback.onResult(true);
        } else {
            Toast.makeText(context, R.string.ai_download_failed, 1).show();
            callback.onResult(false);
        }
    }

    /* JADX WARN: Code duplicated, block: B:62:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:66:0x00fa  */
    private final boolean downloadAndExtractModel(final Context context, final ProgressDialog progressDialog) throws Throwable {
        HttpURLConnection httpURLConnection;
        HttpURLConnection httpURLConnection2;
        File modelDirectory = getModelDirectory(context);
        if (!modelDirectory.exists()) {
            modelDirectory.mkdirs();
        }
        File file = new File(modelDirectory, "kove_v1.zip");
        try {
            URLConnection uRLConnectionOpenConnection = new URL(MODEL_URL).openConnection();
            Intrinsics.checkNotNull(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection3 = (HttpURLConnection) uRLConnectionOpenConnection;
            try {
                httpURLConnection3.connect();
                if (httpURLConnection3.getResponseCode() == 200) {
                    int contentLength = httpURLConnection3.getContentLength();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection3.getInputStream());
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] bArr = new byte[4096];
                    Ref.IntRef intRef = new Ref.IntRef();
                    long j = 0;
                    while (true) {
                        int i = bufferedInputStream.read(bArr);
                        intRef.element = i;
                        if (i == -1) {
                            break;
                        }
                        try {
                            j += (long) intRef.element;
                            if (contentLength > 0) {
                                httpURLConnection2 = httpURLConnection3;
                                try {
                                    final int i2 = (int) ((((long) 100) * j) / ((long) contentLength));
                                    progressDialog.setProgress(i2);
                                    Activity activity = context instanceof Activity ? (Activity) context : null;
                                    if (activity != null) {
                                        activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ai.KoveManager$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                KoveManager.downloadAndExtractModel$lambda$7(progressDialog, context, i2);
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    e = e;
                                    httpURLConnection = httpURLConnection2;
                                    Log.e(TAG, "Error downloading or extracting model", e);
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    return false;
                                } catch (Throwable th) {
                                    th = th;
                                    httpURLConnection = httpURLConnection2;
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    throw th;
                                }
                            } else {
                                httpURLConnection2 = httpURLConnection3;
                            }
                            fileOutputStream.write(bArr, 0, intRef.element);
                            httpURLConnection3 = httpURLConnection2;
                        } catch (Exception e2) {
                            e = e2;
                            httpURLConnection2 = httpURLConnection3;
                            httpURLConnection = httpURLConnection2;
                            Log.e(TAG, "Error downloading or extracting model", e);
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            return false;
                        } catch (Throwable th2) {
                            th = th2;
                            httpURLConnection2 = httpURLConnection3;
                            httpURLConnection = httpURLConnection2;
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            throw th;
                        }
                    }
                    httpURLConnection2 = httpURLConnection3;
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    bufferedInputStream.close();
                    Activity activity2 = context instanceof Activity ? (Activity) context : null;
                    if (activity2 != null) {
                        activity2.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ai.KoveManager$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                KoveManager.downloadAndExtractModel$lambda$8(progressDialog, context);
                            }
                        });
                    }
                    try {
                        unzip(file, modelDirectory);
                        file.delete();
                        httpURLConnection2.disconnect();
                        return true;
                    } catch (Exception e3) {
                        e = e3;
                    } catch (Throwable th3) {
                        th = th3;
                        httpURLConnection = httpURLConnection2;
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        throw th;
                    }
                } else {
                    httpURLConnection3.disconnect();
                    return false;
                }
            } catch (Exception e4) {
                e = e4;
                httpURLConnection2 = httpURLConnection3;
            } catch (Throwable th4) {
                th = th4;
                httpURLConnection2 = httpURLConnection3;
            }
            httpURLConnection = httpURLConnection2;
        } catch (Exception e5) {
            e = e5;
            httpURLConnection = null;
        } catch (Throwable th5) {
            th = th5;
            httpURLConnection = null;
        }
        try {
            Log.e(TAG, "Error downloading or extracting model", e);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return false;
        } catch (Throwable th6) {
            th = th6;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadAndExtractModel$lambda$7(ProgressDialog progressDialog, Context context, int i) {
        Intrinsics.checkNotNullParameter(progressDialog, "$progressDialog");
        Intrinsics.checkNotNullParameter(context, "$context");
        progressDialog.setMessage(context.getString(R.string.ai_downloading_progress, Integer.valueOf(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadAndExtractModel$lambda$8(ProgressDialog progressDialog, Context context) {
        Intrinsics.checkNotNullParameter(progressDialog, "$progressDialog");
        Intrinsics.checkNotNullParameter(context, "$context");
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getString(R.string.ai_unzipping));
    }

    private final void unzip(File zipFile, File targetDirectory) {
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipInputStream zipInputStream2 = zipInputStream;
            byte[] bArr = new byte[4096];
            while (true) {
                ZipEntry nextEntry = zipInputStream2.getNextEntry();
                if (nextEntry != null) {
                    Intrinsics.checkNotNull(nextEntry);
                    if (!nextEntry.isDirectory()) {
                        File file = new File(targetDirectory, new File(nextEntry.getName()).getName());
                        String canonicalPath = file.getCanonicalPath();
                        Intrinsics.checkNotNull(canonicalPath);
                        String canonicalPath2 = targetDirectory.getCanonicalPath();
                        Intrinsics.checkNotNullExpressionValue(canonicalPath2, "getCanonicalPath(...)");
                        if (!StringsKt.startsWith$default(canonicalPath, canonicalPath2, false, 2, (Object) null)) {
                            throw new SecurityException("ZIP path traversal security error");
                        }
                        File parentFile = file.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                        try {
                            BufferedOutputStream bufferedOutputStream2 = bufferedOutputStream;
                            while (true) {
                                int i = zipInputStream2.read(bArr);
                                if (i <= 0) {
                                    break;
                                } else {
                                    bufferedOutputStream2.write(bArr, 0, i);
                                }
                            }
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(bufferedOutputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(bufferedOutputStream, th);
                                throw th2;
                            }
                        }
                    }
                } else {
                    Unit unit2 = Unit.INSTANCE;
                    CloseableKt.closeFinally(zipInputStream, null);
                    Log.i(TAG, "Unzip complete. Path flattening and tokenizer renaming applied successfully.");
                    return;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(zipInputStream, th3);
                throw th4;
            }
        }
    }

    private final void showRestartDialog(final Context context) {
        new AlertDialog.Builder(context).setTitle(R.string.ai_download_complete).setMessage(R.string.ai_download_complete).setCancelable(false).setPositiveButton(R.string.button_restart, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ai.KoveManager$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                KoveManager.showRestartDialog$lambda$13(context, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showRestartDialog$lambda$13(Context context, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(context, "$context");
        INSTANCE.restartApplication(context);
    }

    private final void restartApplication(Context context) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntentForPackage != null) {
            context.startActivity(Intent.makeRestartActivityTask(launchIntentForPackage.getComponent()));
            Runtime.getRuntime().exit(0);
        }
    }
}
