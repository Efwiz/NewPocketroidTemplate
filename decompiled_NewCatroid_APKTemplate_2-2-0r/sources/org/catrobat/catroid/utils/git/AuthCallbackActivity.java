package org.catrobat.catroid.utils.git;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/AuthCallbackActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: AuthCallbackActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0004H\u0082@¢\u0006\u0002\u0010\bJ\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/utils/git/AuthCallbackActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "GITHUB_CLIENT_ID", "", "GITHUB_CLIENT_SECRET", "exchangeCodeForToken", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AuthCallbackActivity extends AppCompatActivity {
    private final String GITHUB_CLIENT_ID = "Ov23liKoq3h0cTgAbVYA";
    private final String GITHUB_CLIENT_SECRET = "936da4332f8a31ebed1bc97aa5d2f89a989a56d2";

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        if (data != null) {
            String string = data.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            if (StringsKt.startsWith$default(string, "newcatroid://github-callback", false, 2, (Object) null)) {
                String queryParameter = data.getQueryParameter(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY);
                if (queryParameter != null) {
                    BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(queryParameter, null), 3, null);
                    return;
                } else {
                    finish();
                    return;
                }
            }
        }
        finish();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/AuthCallbackActivity$onCreate$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.utils.git.AuthCallbackActivity$onCreate$1, reason: invalid class name */
    /* JADX INFO: compiled from: AuthCallbackActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.utils.git.AuthCallbackActivity$onCreate$1", f = "AuthCallbackActivity.kt", i = {}, l = {28}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $code;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$code = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AuthCallbackActivity.this.new AnonymousClass1(this.$code, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = AuthCallbackActivity.this.exchangeCodeForToken(this.$code, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            String str = (String) obj;
            if (str != null) {
                TokenManager.INSTANCE.saveToken(AuthCallbackActivity.this, str);
            } else {
                Log.e("AuthCallback", "Failed to get access token");
            }
            AuthCallbackActivity.this.finish();
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/AuthCallbackActivity$exchangeCodeForToken$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.utils.git.AuthCallbackActivity$exchangeCodeForToken$2, reason: invalid class name */
    /* JADX INFO: compiled from: AuthCallbackActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.utils.git.AuthCallbackActivity$exchangeCodeForToken$2", f = "AuthCallbackActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
        final /* synthetic */ String $code;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$code = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AuthCallbackActivity.this.new AnonymousClass2(this.$code, continuation);
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
            try {
                Response responseExecute = new OkHttpClient().newCall(new Request.Builder().url("https://github.com/login/oauth/access_token").header("Accept", "application/json").post(new FormBody.Builder(null, 1, null).add("client_id", AuthCallbackActivity.this.GITHUB_CLIENT_ID).add("client_secret", AuthCallbackActivity.this.GITHUB_CLIENT_SECRET).add(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, this.$code).build()).build()).execute();
                try {
                    Response response = responseExecute;
                    if (!response.isSuccessful()) {
                        CloseableKt.closeFinally(responseExecute, null);
                        return null;
                    }
                    ResponseBody responseBodyBody = response.body();
                    String strString = responseBodyBody != null ? responseBodyBody.string() : null;
                    String strOptString = strString != null ? new JSONObject(strString).optString("access_token", null) : null;
                    CloseableKt.closeFinally(responseExecute, null);
                    return strOptString;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(responseExecute, th);
                        throw th2;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object exchangeCodeForToken(String str, Continuation<? super String> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(str, null), continuation);
    }
}
