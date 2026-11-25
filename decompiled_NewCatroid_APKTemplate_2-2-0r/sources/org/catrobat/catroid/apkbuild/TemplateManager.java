package org.catrobat.catroid.apkbuild;

import android.content.Context;
import android.util.Log;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.SpecialNames;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/apkbuild/TemplateManager.class
  classes12.dex
 */
/* JADX INFO: compiled from: TemplateManager.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0015B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J<\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rH\u0086@¢\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0086@¢\u0006\u0002\u0010\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/apkbuild/TemplateManager;", "", "()V", "client", "Lokhttp3/OkHttpClient;", "downloadTemplate", "Ljava/io/File;", "context", "Landroid/content/Context;", "downloadUrl", "", PsiTreeChangeEvent.PROP_FILE_NAME, "onProgress", "Lkotlin/Function1;", "", "", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchAvailableTemplates", "", "Lorg/catrobat/catroid/apkbuild/TemplateManager$TemplateRelease;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "TemplateRelease", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TemplateManager {
    public static final TemplateManager INSTANCE = new TemplateManager();
    private static final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).followRedirects(true).followSslRedirects(true).build();

    private TemplateManager() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/apkbuild/TemplateManager$TemplateRelease.class
      classes12.dex
     */
    /* JADX INFO: compiled from: TemplateManager.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/apkbuild/TemplateManager$TemplateRelease;", "", "tagName", "", PsiTreeChangeEvent.PROP_FILE_NAME, "downloadUrl", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDownloadUrl", "()Ljava/lang/String;", "getFileName", "getTagName", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class TemplateRelease {
        private final String downloadUrl;
        private final String fileName;
        private final String tagName;

        public static /* synthetic */ TemplateRelease copy$default(TemplateRelease templateRelease, String str, String str2, String str3, int i, Object obj) {
            if ((i & 1) != 0) {
                str = templateRelease.tagName;
            }
            if ((i & 2) != 0) {
                str2 = templateRelease.fileName;
            }
            if ((i & 4) != 0) {
                str3 = templateRelease.downloadUrl;
            }
            return templateRelease.copy(str, str2, str3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getTagName() {
            return this.tagName;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getFileName() {
            return this.fileName;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getDownloadUrl() {
            return this.downloadUrl;
        }

        public final TemplateRelease copy(String tagName, String fileName, String downloadUrl) {
            Intrinsics.checkNotNullParameter(tagName, "tagName");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
            return new TemplateRelease(tagName, fileName, downloadUrl);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TemplateRelease)) {
                return false;
            }
            TemplateRelease templateRelease = (TemplateRelease) other;
            return Intrinsics.areEqual(this.tagName, templateRelease.tagName) && Intrinsics.areEqual(this.fileName, templateRelease.fileName) && Intrinsics.areEqual(this.downloadUrl, templateRelease.downloadUrl);
        }

        public int hashCode() {
            return (((this.tagName.hashCode() * 31) + this.fileName.hashCode()) * 31) + this.downloadUrl.hashCode();
        }

        public String toString() {
            return "TemplateRelease(tagName=" + this.tagName + ", fileName=" + this.fileName + ", downloadUrl=" + this.downloadUrl + Constant.AFTER_QUTO;
        }

        public TemplateRelease(String tagName, String fileName, String downloadUrl) {
            Intrinsics.checkNotNullParameter(tagName, "tagName");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
            this.tagName = tagName;
            this.fileName = fileName;
            this.downloadUrl = downloadUrl;
        }

        public final String getTagName() {
            return this.tagName;
        }

        public final String getFileName() {
            return this.fileName;
        }

        public final String getDownloadUrl() {
            return this.downloadUrl;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/apkbuild/TemplateManager$fetchAvailableTemplates$2.class
      classes12.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.apkbuild.TemplateManager$fetchAvailableTemplates$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TemplateManager.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/catrobat/catroid/apkbuild/TemplateManager$TemplateRelease;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.apkbuild.TemplateManager$fetchAvailableTemplates$2", f = "TemplateManager.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41692 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<TemplateRelease>>, Object> {
        int label;

        C41692(Continuation<? super C41692> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41692(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<TemplateRelease>> continuation) {
            return ((C41692) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            String strString;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            Request requestBuild = new Request.Builder().url("https://raw.githubusercontent.com/Danveyd/NewCatroid-Templates/main/releases.json").header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36").header("Accept", "application/json").build();
            ArrayList arrayList = new ArrayList();
            try {
                Response responseExecute = TemplateManager.client.newCall(requestBuild).execute();
                try {
                    Response response = responseExecute;
                    if (response.isSuccessful()) {
                        ResponseBody responseBodyBody = response.body();
                        if (responseBodyBody != null && (strString = responseBodyBody.string()) != null) {
                            JSONArray jSONArray = new JSONArray(strString);
                            int length = jSONArray.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject jSONObject = jSONArray.getJSONObject(i);
                                String string = jSONObject.getString("tag_name");
                                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                                String string2 = jSONObject.getString("file_name");
                                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                                String string3 = jSONObject.getString("download_url");
                                Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                                arrayList.add(new TemplateRelease(string, string2, string3));
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(responseExecute, null);
                        return arrayList;
                    }
                    throw new IOException("HTTP error code: " + response.code());
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(responseExecute, th);
                        throw th2;
                    }
                }
            } catch (Exception e) {
                Log.e("TemplateManager", "Failed to fetch templates safely: " + e.getMessage(), e);
            }
        }
    }

    public final Object fetchAvailableTemplates(Continuation<? super List<TemplateRelease>> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new C41692(null), continuation);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/apkbuild/TemplateManager$downloadTemplate$2.class
      classes12.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.apkbuild.TemplateManager$downloadTemplate$2, reason: invalid class name */
    /* JADX INFO: compiled from: TemplateManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Ljava/io/File;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.apkbuild.TemplateManager$downloadTemplate$2", f = "TemplateManager.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super File>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ String $downloadUrl;
        final /* synthetic */ String $fileName;
        final /* synthetic */ Function1<Integer, Unit> $onProgress;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass2(Context context, String str, String str2, Function1<? super Integer, Unit> function1, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$context = context;
            this.$fileName = str;
            this.$downloadUrl = str2;
            this.$onProgress = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$context, this.$fileName, this.$downloadUrl, this.$onProgress, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super File> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            File file = new File(this.$context.getCacheDir(), "templates/" + this.$fileName);
            if (file.exists() && file.length() > 0) {
                return file;
            }
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                Boxing.boxBoolean(parentFile.mkdirs());
            }
            try {
                Response responseExecute = TemplateManager.client.newCall(new Request.Builder().url(this.$downloadUrl).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36").header("Accept", "*/*").build()).execute();
                Function1<Integer, Unit> function1 = this.$onProgress;
                try {
                    Response response = responseExecute;
                    if (!response.isSuccessful()) {
                        CloseableKt.closeFinally(responseExecute, null);
                        return null;
                    }
                    ResponseBody responseBodyBody = response.body();
                    if (responseBodyBody == null) {
                        CloseableKt.closeFinally(responseExecute, null);
                        return null;
                    }
                    long contentLength = responseBodyBody.getContentLength();
                    InputStream inputStreamByteStream = responseBodyBody.byteStream();
                    try {
                        InputStream inputStream = inputStreamByteStream;
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            FileOutputStream fileOutputStream2 = fileOutputStream;
                            byte[] bArr = new byte[8192];
                            long j = 0;
                            while (true) {
                                int i = inputStream.read(bArr);
                                if (i != -1) {
                                    fileOutputStream2.write(bArr, 0, i);
                                    j += (long) i;
                                    if (contentLength > 0) {
                                        function1.invoke(Boxing.boxInt((int) ((((long) 100) * j) / contentLength)));
                                    }
                                } else {
                                    Unit unit = Unit.INSTANCE;
                                    CloseableKt.closeFinally(fileOutputStream, null);
                                    Unit unit2 = Unit.INSTANCE;
                                    CloseableKt.closeFinally(inputStreamByteStream, null);
                                    CloseableKt.closeFinally(responseExecute, null);
                                    return file;
                                }
                                try {
                                    throw th;
                                } catch (Throwable th) {
                                    CloseableKt.closeFinally(inputStreamByteStream, th);
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            try {
                                throw th2;
                            } catch (Throwable th3) {
                                CloseableKt.closeFinally(fileOutputStream, th2);
                                throw th3;
                            }
                        }
                    } catch (Throwable th4) {
                        throw th4;
                    }
                } catch (Throwable th5) {
                    try {
                        throw th5;
                    } catch (Throwable th6) {
                        CloseableKt.closeFinally(responseExecute, th5);
                        throw th6;
                    }
                }
            } catch (Exception e) {
                Log.e("TemplateManager", "Failed to download template", e);
                file.delete();
                return null;
            }
            Log.e("TemplateManager", "Failed to download template", e);
            file.delete();
            return null;
        }
    }

    public final Object downloadTemplate(Context context, String str, String str2, Function1<? super Integer, Unit> function1, Continuation<? super File> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(context, str2, str, function1, null), continuation);
    }
}
