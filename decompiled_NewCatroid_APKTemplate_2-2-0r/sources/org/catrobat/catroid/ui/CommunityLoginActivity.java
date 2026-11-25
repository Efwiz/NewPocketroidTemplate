package org.catrobat.catroid.ui;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hcaptcha.sdk.HCaptcha;
import com.hcaptcha.sdk.HCaptchaConfig;
import com.hcaptcha.sdk.HCaptchaException;
import com.hcaptcha.sdk.HCaptchaTokenResponse;
import com.hcaptcha.sdk.tasks.OnFailureListener;
import com.hcaptcha.sdk.tasks.OnSuccessListener;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.Closeable;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.databinding.ActivityCommunityLoginBinding;
import org.catrobat.catroid.utils.community.CommunityTokenManager;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.transport.AmazonS3;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/CommunityLoginActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: CommunityLoginActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\fH\u0002J\n\u0010\u0010\u001a\u0004\u0018\u00010\fH\u0002J\u0012\u0010\u0011\u001a\u00020\n2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\u0012\u0010\u0014\u001a\u00020\f2\b\u0010\u0015\u001a\u0004\u0018\u00010\fH\u0002J\u0010\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\fH\u0002J\b\u0010\u0018\u001a\u00020\nH\u0002J&\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\f2\u0014\u0010\u001b\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\n0\u001cH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/ui/CommunityLoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lorg/catrobat/catroid/databinding/ActivityCommunityLoginBinding;", "client", "Lokhttp3/OkHttpClient;", "isRegisterMode", "", "executeFinalAuthRequest", "", FirebaseAnalytics.Event.LOGIN, "", "email", "pass", "captchaToken", "fetchCaptchaSiteKey", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "parseError", "errorJson", "showToastOnMain", CrashHianalyticsData.MESSAGE, "startAuthFlow", "verifyHCaptcha", "siteKey", "onResult", "Lkotlin/Function1;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommunityLoginActivity extends AppCompatActivity {
    private ActivityCommunityLoginBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    private boolean isRegisterMode;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCommunityLoginBinding activityCommunityLoginBindingInflate = ActivityCommunityLoginBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityCommunityLoginBindingInflate, "inflate(...)");
        this.binding = activityCommunityLoginBindingInflate;
        ActivityCommunityLoginBinding activityCommunityLoginBinding = null;
        if (activityCommunityLoginBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBindingInflate = null;
        }
        setContentView(activityCommunityLoginBindingInflate.getRoot());
        ActivityCommunityLoginBinding activityCommunityLoginBinding2 = this.binding;
        if (activityCommunityLoginBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBinding2 = null;
        }
        activityCommunityLoginBinding2.tvSwitchMode.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.CommunityLoginActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CommunityLoginActivity.onCreate$lambda$1(this.f$0, view);
            }
        });
        ActivityCommunityLoginBinding activityCommunityLoginBinding3 = this.binding;
        if (activityCommunityLoginBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityCommunityLoginBinding = activityCommunityLoginBinding3;
        }
        activityCommunityLoginBinding.btnAction.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.CommunityLoginActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CommunityLoginActivity.onCreate$lambda$2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(CommunityLoginActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(250L);
        ActivityCommunityLoginBinding activityCommunityLoginBinding = this$0.binding;
        ActivityCommunityLoginBinding activityCommunityLoginBinding2 = null;
        if (activityCommunityLoginBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBinding = null;
        }
        TransitionManager.beginDelayedTransition(activityCommunityLoginBinding.getRoot(), autoTransition);
        boolean z = this$0.isRegisterMode;
        this$0.isRegisterMode = !z;
        if (!z) {
            ActivityCommunityLoginBinding activityCommunityLoginBinding3 = this$0.binding;
            if (activityCommunityLoginBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityLoginBinding3 = null;
            }
            activityCommunityLoginBinding3.tilEmail.setVisibility(0);
            ActivityCommunityLoginBinding activityCommunityLoginBinding4 = this$0.binding;
            if (activityCommunityLoginBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityLoginBinding4 = null;
            }
            activityCommunityLoginBinding4.tvTitle.setText(this$0.getString(R.string.community_register_title));
            ActivityCommunityLoginBinding activityCommunityLoginBinding5 = this$0.binding;
            if (activityCommunityLoginBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityLoginBinding5 = null;
            }
            activityCommunityLoginBinding5.btnAction.setText(this$0.getString(R.string.community_register_btn));
            ActivityCommunityLoginBinding activityCommunityLoginBinding6 = this$0.binding;
            if (activityCommunityLoginBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityCommunityLoginBinding2 = activityCommunityLoginBinding6;
            }
            activityCommunityLoginBinding2.tvSwitchMode.setText(this$0.getString(R.string.community_have_account_hint));
            return;
        }
        ActivityCommunityLoginBinding activityCommunityLoginBinding7 = this$0.binding;
        if (activityCommunityLoginBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBinding7 = null;
        }
        activityCommunityLoginBinding7.tilEmail.setVisibility(8);
        ActivityCommunityLoginBinding activityCommunityLoginBinding8 = this$0.binding;
        if (activityCommunityLoginBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBinding8 = null;
        }
        activityCommunityLoginBinding8.tvTitle.setText(this$0.getString(R.string.community_login_title));
        ActivityCommunityLoginBinding activityCommunityLoginBinding9 = this$0.binding;
        if (activityCommunityLoginBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBinding9 = null;
        }
        activityCommunityLoginBinding9.btnAction.setText(this$0.getString(R.string.community_login_btn));
        ActivityCommunityLoginBinding activityCommunityLoginBinding10 = this$0.binding;
        if (activityCommunityLoginBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityCommunityLoginBinding2 = activityCommunityLoginBinding10;
        }
        activityCommunityLoginBinding2.tvSwitchMode.setText(this$0.getString(R.string.community_no_account_hint));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(CommunityLoginActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startAuthFlow();
    }

    private final void startAuthFlow() {
        ActivityCommunityLoginBinding activityCommunityLoginBinding = this.binding;
        ActivityCommunityLoginBinding activityCommunityLoginBinding2 = null;
        if (activityCommunityLoginBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBinding = null;
        }
        String string = StringsKt.trim((CharSequence) String.valueOf(activityCommunityLoginBinding.etLogin.getText())).toString();
        ActivityCommunityLoginBinding activityCommunityLoginBinding3 = this.binding;
        if (activityCommunityLoginBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBinding3 = null;
        }
        String string2 = StringsKt.trim((CharSequence) String.valueOf(activityCommunityLoginBinding3.etPassword.getText())).toString();
        ActivityCommunityLoginBinding activityCommunityLoginBinding4 = this.binding;
        if (activityCommunityLoginBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityLoginBinding4 = null;
        }
        String string3 = StringsKt.trim((CharSequence) String.valueOf(activityCommunityLoginBinding4.etEmail.getText())).toString();
        if (string.length() == 0 || string2.length() == 0) {
            Toast.makeText(this, R.string.community_error_empty_fields, 0).show();
            return;
        }
        if (this.isRegisterMode && string3.length() == 0) {
            Toast.makeText(this, R.string.community_error_empty_email, 0).show();
            return;
        }
        ActivityCommunityLoginBinding activityCommunityLoginBinding5 = this.binding;
        if (activityCommunityLoginBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityCommunityLoginBinding2 = activityCommunityLoginBinding5;
        }
        activityCommunityLoginBinding2.btnAction.setEnabled(false);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C41871(string, string3, string2, null), 3, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/CommunityLoginActivity$startAuthFlow$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.CommunityLoginActivity$startAuthFlow$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CommunityLoginActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.CommunityLoginActivity$startAuthFlow$1", f = "CommunityLoginActivity.kt", i = {}, l = {79}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41871 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $email;
        final /* synthetic */ String $login;
        final /* synthetic */ String $password;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41871(String str, String str2, String str3, Continuation<? super C41871> continuation) {
            super(2, continuation);
            this.$login = str;
            this.$email = str2;
            this.$password = str3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CommunityLoginActivity.this.new C41871(this.$login, this.$email, this.$password, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41871) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            ActivityCommunityLoginBinding activityCommunityLoginBinding = null;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.label = 1;
                    obj = BuildersKt.withContext(Dispatchers.getIO(), new CommunityLoginActivity$startAuthFlow$1$siteKey$1(CommunityLoginActivity.this, null), this);
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
                if (str == null) {
                    CommunityLoginActivity communityLoginActivity = CommunityLoginActivity.this;
                    String string = communityLoginActivity.getString(R.string.community_error_config_failed);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    communityLoginActivity.showToastOnMain(string);
                    ActivityCommunityLoginBinding activityCommunityLoginBinding2 = CommunityLoginActivity.this.binding;
                    if (activityCommunityLoginBinding2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        activityCommunityLoginBinding2 = null;
                    }
                    activityCommunityLoginBinding2.btnAction.setEnabled(true);
                    return Unit.INSTANCE;
                }
                CommunityLoginActivity communityLoginActivity2 = CommunityLoginActivity.this;
                final CommunityLoginActivity communityLoginActivity3 = CommunityLoginActivity.this;
                final String str2 = this.$login;
                final String str3 = this.$email;
                final String str4 = this.$password;
                communityLoginActivity2.verifyHCaptcha(str, new Function1<String, Unit>() { // from class: org.catrobat.catroid.ui.CommunityLoginActivity.startAuthFlow.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(String str5) {
                        invoke2(str5);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(String str5) {
                        if (communityLoginActivity3.isFinishing() || communityLoginActivity3.isDestroyed()) {
                            return;
                        }
                        if (str5 != null) {
                            communityLoginActivity3.executeFinalAuthRequest(str2, str3, str4, str5);
                            return;
                        }
                        CommunityLoginActivity communityLoginActivity4 = communityLoginActivity3;
                        String string2 = communityLoginActivity4.getString(R.string.community_captcha_cancelled);
                        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                        communityLoginActivity4.showToastOnMain(string2);
                        ActivityCommunityLoginBinding activityCommunityLoginBinding3 = communityLoginActivity3.binding;
                        if (activityCommunityLoginBinding3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            activityCommunityLoginBinding3 = null;
                        }
                        activityCommunityLoginBinding3.btnAction.setEnabled(true);
                    }
                });
                return Unit.INSTANCE;
            } catch (Exception e) {
                CommunityLoginActivity communityLoginActivity4 = CommunityLoginActivity.this;
                String message = e.getMessage();
                if (message == null) {
                    message = "";
                }
                String string2 = communityLoginActivity4.getString(R.string.community_error_template, new Object[]{message});
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                communityLoginActivity4.showToastOnMain(string2);
                ActivityCommunityLoginBinding activityCommunityLoginBinding3 = CommunityLoginActivity.this.binding;
                if (activityCommunityLoginBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activityCommunityLoginBinding = activityCommunityLoginBinding3;
                }
                activityCommunityLoginBinding.btnAction.setEnabled(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String fetchCaptchaSiteKey() {
        String string;
        String strString;
        try {
            Response responseExecute = this.client.newCall(new Request.Builder().url("https://backend.sois.site/config").get().build()).execute();
            try {
                Response response = responseExecute;
                if (response.isSuccessful()) {
                    ResponseBody responseBodyBody = response.body();
                    if (responseBodyBody != null && (strString = responseBodyBody.string()) != null) {
                        string = new JSONObject(strString).getString("hcaptcha_site_key");
                    }
                    CloseableKt.closeFinally(responseExecute, null);
                    return null;
                }
                string = null;
                CloseableKt.closeFinally(responseExecute, null);
                return string;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(responseExecute, th);
                    throw th2;
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void verifyHCaptcha(String siteKey, final Function1<? super String, Unit> onResult) {
        HCaptchaConfig hCaptchaConfigBuild = HCaptchaConfig.builder().siteKey(siteKey).build();
        Intrinsics.checkNotNullExpressionValue(hCaptchaConfigBuild, "build(...)");
        HCaptcha.getClient(this).verifyWithHCaptcha(hCaptchaConfigBuild).addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.ui.CommunityLoginActivity$$ExternalSyntheticLambda1
            @Override // com.hcaptcha.sdk.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                CommunityLoginActivity.verifyHCaptcha$lambda$4(onResult, (HCaptchaTokenResponse) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.ui.CommunityLoginActivity$$ExternalSyntheticLambda2
            @Override // com.hcaptcha.sdk.tasks.OnFailureListener
            public final void onFailure(HCaptchaException hCaptchaException) {
                CommunityLoginActivity.verifyHCaptcha$lambda$5(onResult, hCaptchaException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void verifyHCaptcha$lambda$4(Function1 onResult, HCaptchaTokenResponse hCaptchaTokenResponse) {
        Intrinsics.checkNotNullParameter(onResult, "$onResult");
        onResult.invoke(hCaptchaTokenResponse.getTokenResult());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void verifyHCaptcha$lambda$5(Function1 onResult, HCaptchaException hCaptchaException) {
        Intrinsics.checkNotNullParameter(onResult, "$onResult");
        onResult.invoke(null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/CommunityLoginActivity$executeFinalAuthRequest$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.CommunityLoginActivity$executeFinalAuthRequest$1, reason: invalid class name */
    /* JADX INFO: compiled from: CommunityLoginActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.CommunityLoginActivity$executeFinalAuthRequest$1", f = "CommunityLoginActivity.kt", i = {}, l = {175, 179, 184}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $captchaToken;
        final /* synthetic */ String $email;
        final /* synthetic */ String $login;
        final /* synthetic */ String $pass;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, String str2, String str3, String str4, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$login = str;
            this.$email = str2;
            this.$pass = str3;
            this.$captchaToken = str4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CommunityLoginActivity.this.new AnonymousClass1(this.$login, this.$email, this.$pass, this.$captchaToken, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r1v1, types: [java.io.Closeable] */
        /* JADX WARN: Type inference failed for: r1v13, types: [java.io.Closeable] */
        /* JADX WARN: Type inference failed for: r1v21 */
        /* JADX WARN: Type inference failed for: r1v22 */
        /* JADX WARN: Type inference failed for: r1v23 */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            ?? r1 = this.label;
            try {
                try {
                    if (r1 == 0) {
                        ResultKt.throwOnFailure(obj);
                        String str = CommunityLoginActivity.this.isRegisterMode ? "https://backend.sois.site/auth/register" : "https://backend.sois.site/auth/login";
                        JSONObject jSONObject = new JSONObject();
                        CommunityLoginActivity communityLoginActivity = CommunityLoginActivity.this;
                        String str2 = this.$login;
                        String str3 = this.$email;
                        String str4 = this.$pass;
                        String str5 = this.$captchaToken;
                        if (communityLoginActivity.isRegisterMode) {
                            jSONObject.put("username", str2);
                            jSONObject.put("email", str3);
                        } else {
                            jSONObject.put(FirebaseAnalytics.Event.LOGIN, str2);
                        }
                        jSONObject.put(AmazonS3.Keys.PASSWORD, str4);
                        jSONObject.put("h-captcha-response", str5);
                        jSONObject.put("website", "");
                        RequestBody.Companion companion = RequestBody.INSTANCE;
                        String string = jSONObject.toString();
                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                        Response responseExecute = CommunityLoginActivity.this.client.newCall(new Request.Builder().url(str).post(companion.create(string, MediaType.INSTANCE.get("application/json; charset=utf-8"))).build()).execute();
                        CommunityLoginActivity communityLoginActivity2 = CommunityLoginActivity.this;
                        Response response = responseExecute;
                        ResponseBody responseBodyBody = response.body();
                        String strString = responseBodyBody != null ? responseBodyBody.string() : null;
                        if (!response.isSuccessful() || strString == null) {
                            communityLoginActivity2.showToastOnMain(communityLoginActivity2.parseError(strString));
                            MainCoroutineDispatcher main = Dispatchers.getMain();
                            CommunityLoginActivity$executeFinalAuthRequest$1$1$2 communityLoginActivity$executeFinalAuthRequest$1$1$2 = new CommunityLoginActivity$executeFinalAuthRequest$1$1$2(communityLoginActivity2, null);
                            this.L$0 = responseExecute;
                            this.label = 2;
                            r1 = responseExecute;
                            if (BuildersKt.withContext(main, communityLoginActivity$executeFinalAuthRequest$1$1$2, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        } else {
                            JSONObject jSONObject2 = new JSONObject(strString);
                            String string2 = jSONObject2.getString("token");
                            JSONObject jSONObject3 = jSONObject2.getJSONObject(ConfigConstants.CONFIG_USER_SECTION);
                            String string3 = jSONObject3.getString("username");
                            String string4 = jSONObject3.toString();
                            Intrinsics.checkNotNullExpressionValue(string4, "toString(...)");
                            Intrinsics.checkNotNull(string2);
                            Intrinsics.checkNotNull(string3);
                            CommunityTokenManager.saveSession(communityLoginActivity2, string2, string3, string4);
                            String string5 = communityLoginActivity2.isRegisterMode ? communityLoginActivity2.getString(R.string.community_register_success) : communityLoginActivity2.getString(R.string.community_login_success);
                            Intrinsics.checkNotNull(string5);
                            communityLoginActivity2.showToastOnMain(string5);
                            MainCoroutineDispatcher main2 = Dispatchers.getMain();
                            CommunityLoginActivity$executeFinalAuthRequest$1$1$1 communityLoginActivity$executeFinalAuthRequest$1$1$1 = new CommunityLoginActivity$executeFinalAuthRequest$1$1$1(communityLoginActivity2, null);
                            this.L$0 = responseExecute;
                            this.label = 1;
                            r1 = responseExecute;
                            if (BuildersKt.withContext(main2, communityLoginActivity$executeFinalAuthRequest$1$1$1, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
                    } else {
                        if (r1 == 1 || r1 == 2) {
                            Closeable closeable = (Closeable) this.L$0;
                            ResultKt.throwOnFailure(obj);
                            r1 = closeable;
                        } else {
                            if (r1 != 3) {
                                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        return Unit.INSTANCE;
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(r1, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(r1, th);
                        throw th2;
                    }
                }
            } catch (Exception unused) {
                CommunityLoginActivity communityLoginActivity3 = CommunityLoginActivity.this;
                String string6 = communityLoginActivity3.getString(R.string.community_error_connection);
                Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
                communityLoginActivity3.showToastOnMain(string6);
                this.L$0 = null;
                this.label = 3;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass2(CommunityLoginActivity.this, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/CommunityLoginActivity$executeFinalAuthRequest$1$2.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.CommunityLoginActivity$executeFinalAuthRequest$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: CommunityLoginActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.CommunityLoginActivity$executeFinalAuthRequest$1$2", f = "CommunityLoginActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ CommunityLoginActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(CommunityLoginActivity communityLoginActivity, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = communityLoginActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                ActivityCommunityLoginBinding activityCommunityLoginBinding = this.this$0.binding;
                if (activityCommunityLoginBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activityCommunityLoginBinding = null;
                }
                activityCommunityLoginBinding.btnAction.setEnabled(true);
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void executeFinalAuthRequest(String login, String email, String pass, String captchaToken) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new AnonymousClass1(login, email, pass, captchaToken, null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String parseError(String errorJson) {
        String string;
        try {
            String str = errorJson;
            if (str == null || str.length() == 0) {
                string = getString(R.string.community_error_unknown);
            } else {
                string = new JSONObject(errorJson).getString(ProductAction.ACTION_DETAIL);
            }
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception unused) {
            String string2 = getString(R.string.community_error_server);
            Intrinsics.checkNotNull(string2);
            return string2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showToastOnMain(final String message) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityLoginActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CommunityLoginActivity.showToastOnMain$lambda$6(this.f$0, message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showToastOnMain$lambda$6(CommunityLoginActivity this$0, String message) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(message, "$message");
        Toast.makeText(this$0, message, 1).show();
    }
}
