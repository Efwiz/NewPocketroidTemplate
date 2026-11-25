package org.catrobat.catroid.content.actions;

import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.catrobat.catroid.content.Project;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/FilesUrlAction$downloadFileFromUrl$1$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: FilesUrlAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.content.actions.FilesUrlAction$downloadFileFromUrl$1$1", f = "FilesUrlAction.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class FilesUrlAction$downloadFileFromUrl$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $fileUrl;
    final /* synthetic */ String $newFileName;
    final /* synthetic */ Project $proj;
    int label;
    final /* synthetic */ FilesUrlAction this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FilesUrlAction$downloadFileFromUrl$1$1(String str, FilesUrlAction filesUrlAction, Project project, String str2, Continuation<? super FilesUrlAction$downloadFileFromUrl$1$1> continuation) {
        super(2, continuation);
        this.$fileUrl = str;
        this.this$0 = filesUrlAction;
        this.$proj = project;
        this.$newFileName = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FilesUrlAction$downloadFileFromUrl$1$1(this.$fileUrl, this.this$0, this.$proj, this.$newFileName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((FilesUrlAction$downloadFileFromUrl$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        URLConnection uRLConnectionOpenConnection = new URL(this.$fileUrl).openConnection();
        Intrinsics.checkNotNull(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() != 200) {
            Log.e("DownloadFile", "Ошибка: " + httpURLConnection.getResponseCode());
            this.this$0.showToast("Ошибка: " + httpURLConnection.getResponseCode());
            return Unit.INSTANCE;
        }
        InputStream inputStream = httpURLConnection.getInputStream();
        File filesDir = this.$proj.getFilesDir();
        filesDir.mkdirs();
        File file = new File(filesDir, this.$newFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        Intrinsics.checkNotNull(inputStream);
        ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
        Log.d("DownloadFile", "Файл скачан: " + file.getAbsolutePath());
        return Unit.INSTANCE;
    }
}
