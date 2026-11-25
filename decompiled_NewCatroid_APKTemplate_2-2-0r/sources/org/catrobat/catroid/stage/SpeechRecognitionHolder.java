package org.catrobat.catroid.stage;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/SpeechRecognitionHolder.class
  classes14.dex
 */
/* JADX INFO: compiled from: SpeechRecognitionHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u0018\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0014H\u0002J\u0018\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u001a\u001a\u00020\u0010H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/stage/SpeechRecognitionHolder;", "Lorg/catrobat/catroid/stage/SpeechRecognitionHolderInterface;", "()V", "callback", "Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;", "getCallback", "()Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;", "setCallback", "(Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;)V", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Landroid/speech/RecognitionListener;", "speechIntent", "Landroid/content/Intent;", "speechRecognizer", "Landroid/speech/SpeechRecognizer;", "destroy", "", "forceSetLanguage", "initSpeechRecognition", "stageActivity", "Lorg/catrobat/catroid/stage/StageActivity;", "stageResourceHolder", "Lorg/catrobat/catroid/stage/StageResourceHolder;", "openSpeechRecognitionSettings", "activity", "showDialog", "startListening", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpeechRecognitionHolder implements SpeechRecognitionHolderInterface {
    private static final int FIVE_SECONDS = 5000;
    private static final int ONE_SECOND = 1000;
    private static final String QUICK_SEARCH_BOX_PKG = "com.google.android.googlequicksearchbox";
    private OnSpeechRecognitionResultCallback callback;
    private RecognitionListener listener;
    private Intent speechIntent;
    private SpeechRecognizer speechRecognizer;
    private static final String TAG = "SpeechRecognitionHolder";

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public OnSpeechRecognitionResultCallback getCallback() {
        return this.callback;
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void setCallback(OnSpeechRecognitionResultCallback onSpeechRecognitionResultCallback) {
        this.callback = onSpeechRecognitionResultCallback;
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void forceSetLanguage() {
        Intent intent = this.speechIntent;
        if (intent == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speechIntent");
            intent = null;
        }
        intent.putExtra("android.speech.extra.LANGUAGE", SensorHandler.getListeningLanguageSensor());
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void initSpeechRecognition(final StageActivity stageActivity, final StageResourceHolder stageResourceHolder) {
        Intrinsics.checkNotNullParameter(stageActivity, "stageActivity");
        Intrinsics.checkNotNullParameter(stageResourceHolder, "stageResourceHolder");
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", SensorHandler.getListeningLanguageSensor());
        intent.putExtra("android.speech.extra.ONLY_RETURN_LANGUAGE_PREFERENCE", true);
        intent.putExtra("calling_package", stageActivity.getPackageName());
        intent.putExtra("android.speech.extras.SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS", 1000);
        intent.putExtra("android.speech.extras.SPEECH_INPUT_MINIMUM_LENGTH_MILLIS", 5000);
        this.speechIntent = intent;
        SpeechRecognizer speechRecognizerCreateSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(stageActivity);
        Intrinsics.checkNotNullExpressionValue(speechRecognizerCreateSpeechRecognizer, "createSpeechRecognizer(...)");
        this.speechRecognizer = speechRecognizerCreateSpeechRecognizer;
        this.listener = new RecognitionListener() { // from class: org.catrobat.catroid.stage.SpeechRecognitionHolder.initSpeechRecognition.2
            @Override // android.speech.RecognitionListener
            public void onBufferReceived(byte[] buffer) {
                Intrinsics.checkNotNullParameter(buffer, "buffer");
            }

            @Override // android.speech.RecognitionListener
            public void onEvent(int eventType, Bundle params) {
                Intrinsics.checkNotNullParameter(params, "params");
            }

            @Override // android.speech.RecognitionListener
            public void onPartialResults(Bundle partialResults) {
                Intrinsics.checkNotNullParameter(partialResults, "partialResults");
            }

            @Override // android.speech.RecognitionListener
            public void onRmsChanged(float rmsdB) {
            }

            @Override // android.speech.RecognitionListener
            public void onReadyForSpeech(Bundle params) {
                Intrinsics.checkNotNullParameter(params, "params");
                Log.d(SpeechRecognitionHolder.TAG, "SpeechRecognizer: onReadyForSpeech" + params);
            }

            @Override // android.speech.RecognitionListener
            public void onBeginningOfSpeech() {
                Log.d(SpeechRecognitionHolder.TAG, "SpeechRecognizer: onBeginningOfSpeech");
            }

            @Override // android.speech.RecognitionListener
            public void onEndOfSpeech() {
                Log.d(SpeechRecognitionHolder.TAG, "SpeechRecognizer: onEndOfSpeech");
            }

            @Override // android.speech.RecognitionListener
            public void onError(int error) {
                Log.e(SpeechRecognitionHolder.TAG, "SpeechRecognizer onError: " + error);
                if (error == 2) {
                    SpeechRecognitionHolder.this.showDialog(stageActivity, stageResourceHolder);
                } else if (error == 7) {
                    SpeechRecognizer speechRecognizer = SpeechRecognitionHolder.this.speechRecognizer;
                    if (speechRecognizer == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("speechRecognizer");
                        speechRecognizer = null;
                    }
                    speechRecognizer.cancel();
                    SpeechRecognitionHolder.this.startListening();
                }
                Log.d(SpeechRecognitionHolder.TAG, "SpeechRecognizer restarted!");
            }

            @Override // android.speech.RecognitionListener
            public void onResults(Bundle results) {
                String str;
                Intrinsics.checkNotNullParameter(results, "results");
                ArrayList<String> stringArrayList = results.getStringArrayList("results_recognition");
                Log.d(SpeechRecognitionHolder.TAG, "SpeechRecognizer: onResults:" + stringArrayList);
                if (stringArrayList != null && !stringArrayList.isEmpty()) {
                    Object objFirst = CollectionsKt.first((List<? extends Object>) stringArrayList);
                    Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
                    str = (String) objFirst;
                } else {
                    str = "";
                }
                OnSpeechRecognitionResultCallback callback = SpeechRecognitionHolder.this.getCallback();
                if (callback != null) {
                    callback.onResult(str);
                }
                SensorHandler.startSensorListener(stageActivity);
            }
        };
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/SpeechRecognitionHolder$startListening$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.stage.SpeechRecognitionHolder$startListening$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SpeechRecognitionHolder.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.stage.SpeechRecognitionHolder$startListening$1", f = "SpeechRecognitionHolder.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41851 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C41851(Continuation<? super C41851> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SpeechRecognitionHolder.this.new C41851(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41851) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                SpeechRecognizer speechRecognizer = SpeechRecognitionHolder.this.speechRecognizer;
                Intent intent = null;
                if (speechRecognizer == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("speechRecognizer");
                    speechRecognizer = null;
                }
                RecognitionListener recognitionListener = SpeechRecognitionHolder.this.listener;
                if (recognitionListener == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
                    recognitionListener = null;
                }
                speechRecognizer.setRecognitionListener(recognitionListener);
                SpeechRecognizer speechRecognizer2 = SpeechRecognitionHolder.this.speechRecognizer;
                if (speechRecognizer2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("speechRecognizer");
                    speechRecognizer2 = null;
                }
                Intent intent2 = SpeechRecognitionHolder.this.speechIntent;
                if (intent2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("speechIntent");
                } else {
                    intent = intent2;
                }
                speechRecognizer2.startListening(intent);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void startListening() {
        SensorHandler.stopSensorListeners();
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain().getImmediate(), null, new C41851(null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/SpeechRecognitionHolder$destroy$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.stage.SpeechRecognitionHolder$destroy$1, reason: invalid class name */
    /* JADX INFO: compiled from: SpeechRecognitionHolder.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.stage.SpeechRecognitionHolder$destroy$1", f = "SpeechRecognitionHolder.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SpeechRecognitionHolder.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                SpeechRecognizer speechRecognizer = SpeechRecognitionHolder.this.speechRecognizer;
                SpeechRecognizer speechRecognizer2 = null;
                if (speechRecognizer == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("speechRecognizer");
                    speechRecognizer = null;
                }
                speechRecognizer.cancel();
                SpeechRecognizer speechRecognizer3 = SpeechRecognitionHolder.this.speechRecognizer;
                if (speechRecognizer3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("speechRecognizer");
                } else {
                    speechRecognizer2 = speechRecognizer3;
                }
                speechRecognizer2.destroy();
                return Unit.INSTANCE;
            }
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void destroy() {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain().getImmediate(), null, new AnonymousClass1(null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDialog(final StageActivity stageActivity, final StageResourceHolder stageResourceHolder) {
        AlertDialog.Builder title = new AlertDialog.Builder(stageActivity).setTitle(R.string.speech_recognition_offline_mode_error_dialog_title);
        String displayName = Locale.getDefault().getDisplayName(Locale.getDefault());
        Intrinsics.checkNotNullExpressionValue(displayName, "getDisplayName(...)");
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
        String upperCase = displayName.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        title.setMessage(stageActivity.getString(R.string.speech_recognition_offline_mode_missing_data_error_dialog_msg, new Object[]{upperCase})).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.SpeechRecognitionHolder$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SpeechRecognitionHolder.showDialog$lambda$1(stageResourceHolder, this, stageActivity, dialogInterface, i);
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.SpeechRecognitionHolder$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SpeechRecognitionHolder.showDialog$lambda$2(stageResourceHolder, dialogInterface, i);
            }
        }).create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDialog$lambda$1(StageResourceHolder stageResourceHolder, SpeechRecognitionHolder this$0, StageActivity stageActivity, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(stageResourceHolder, "$stageResourceHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(stageActivity, "$stageActivity");
        stageResourceHolder.endStageActivity();
        this$0.openSpeechRecognitionSettings(stageActivity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDialog$lambda$2(StageResourceHolder stageResourceHolder, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(stageResourceHolder, "$stageResourceHolder");
        stageResourceHolder.endStageActivity();
    }

    private final void openSpeechRecognitionSettings(StageActivity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(268435456);
        ComponentName[] componentNameArr = {new ComponentName(QUICK_SEARCH_BOX_PKG, "com.google.android.apps.gsa.settingsui.VoiceSearchPreferences"), new ComponentName("com.google.android.voicesearch", "com.google.android.voicesearch.VoiceSearchPreferences"), new ComponentName(QUICK_SEARCH_BOX_PKG, "com.google.android.voicesearch.VoiceSearchPreferences"), new ComponentName(QUICK_SEARCH_BOX_PKG, "com.google.android.apps.gsa.velvet.ui.settings.VoiceSearchPreferences")};
        for (int i = 0; i < 4; i++) {
            try {
                intent.setComponent(componentNameArr[i]);
                activity.startActivity(intent);
                return;
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "Speech Recognition Engine isn't installed" + e.getMessage());
            }
        }
    }
}
