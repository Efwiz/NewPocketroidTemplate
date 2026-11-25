package org.catrobat.catroid.stage;

import android.content.DialogInterface;
import android.util.Log;
import android.view.ContextThemeWrapper;
import androidx.appcompat.app.AlertDialog;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.mlsdk.model.download.MLLocalModelManager;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadListener;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadStrategy;
import com.huawei.hms.mlsdk.tts.MLTtsConfig;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import com.huawei.hms.mlsdk.tts.MLTtsEngine;
import com.huawei.hms.mlsdk.tts.MLTtsLocalModel;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/HuaweiTextToSpeechHolder.class
  classes14.dex
 */
/* JADX INFO: compiled from: HuaweiTextToSpeechHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0006¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/stage/HuaweiTextToSpeechHolder;", "", "()V", "downloadModel", "", "person", "", "initTextToSpeech", "stageActivity", "Lorg/catrobat/catroid/stage/StageActivity;", "stageResourceHolder", "Lorg/catrobat/catroid/stage/StageResourceHolder;", "textToSpeech", "text", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HuaweiTextToSpeechHolder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static HuaweiTextToSpeechHolder instance = new HuaweiTextToSpeechHolder();
    private static final MLTtsConfig mlTtsConfig;
    private static final MLTtsEngine mlTtsEngine;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadModel$lambda$4(long j, long j2) {
    }

    public static final HuaweiTextToSpeechHolder getInstance() {
        return INSTANCE.getInstance();
    }

    public static final void setInstance(HuaweiTextToSpeechHolder huaweiTextToSpeechHolder) {
        INSTANCE.setInstance(huaweiTextToSpeechHolder);
    }

    public final void initTextToSpeech(final StageActivity stageActivity, final StageResourceHolder stageResourceHolder) {
        Intrinsics.checkNotNullParameter(stageActivity, "stageActivity");
        Intrinsics.checkNotNullParameter(stageResourceHolder, "stageResourceHolder");
        mlTtsEngine.updateConfig(mlTtsConfig);
        MLLocalModelManager.getInstance().isModelExist(new MLTtsLocalModel.Factory(MLTtsConstants.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE).create()).addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.stage.HuaweiTextToSpeechHolder$$ExternalSyntheticLambda3
            @Override // com.huawei.hmf.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                HuaweiTextToSpeechHolder.initTextToSpeech$lambda$2(stageResourceHolder, stageActivity, this, (Boolean) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.stage.HuaweiTextToSpeechHolder$$ExternalSyntheticLambda4
            @Override // com.huawei.hmf.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                HuaweiTextToSpeechHolder.initTextToSpeech$lambda$3(stageResourceHolder, exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTextToSpeech$lambda$2(final StageResourceHolder stageResourceHolder, StageActivity stageActivity, final HuaweiTextToSpeechHolder this$0, Boolean bool) {
        Intrinsics.checkNotNullParameter(stageResourceHolder, "$stageResourceHolder");
        Intrinsics.checkNotNullParameter(stageActivity, "$stageActivity");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(bool);
        if (bool.booleanValue()) {
            stageResourceHolder.resourceInitialized();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(stageActivity, 2131952355));
        builder.setMessage(R.string.prestage_text_to_speech_engine_not_installed).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.HuaweiTextToSpeechHolder$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HuaweiTextToSpeechHolder.initTextToSpeech$lambda$2$lambda$0(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.HuaweiTextToSpeechHolder$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HuaweiTextToSpeechHolder.initTextToSpeech$lambda$2$lambda$1(stageResourceHolder, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTextToSpeech$lambda$2$lambda$0(HuaweiTextToSpeechHolder this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.downloadModel(MLTtsConstants.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTextToSpeech$lambda$2$lambda$1(StageResourceHolder stageResourceHolder, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(stageResourceHolder, "$stageResourceHolder");
        stageResourceHolder.resourceFailed(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTextToSpeech$lambda$3(StageResourceHolder stageResourceHolder, Exception exc) {
        Intrinsics.checkNotNullParameter(stageResourceHolder, "$stageResourceHolder");
        Log.e(HuaweiTextToSpeechHolderKt.TAG, "Failed to download HMS Text-to-speech engine", exc);
        stageResourceHolder.resourceFailed(1);
    }

    public final void textToSpeech(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        mlTtsEngine.speak(text, 6);
    }

    private final void downloadModel(String person) {
        MLLocalModelManager.getInstance().downloadModel(new MLTtsLocalModel.Factory(person).create(), new MLModelDownloadStrategy.Factory().needWifi().create(), new MLModelDownloadListener() { // from class: org.catrobat.catroid.stage.HuaweiTextToSpeechHolder$$ExternalSyntheticLambda0
            @Override // com.huawei.hms.mlsdk.model.download.MLModelDownloadListener
            public final void onProcess(long j, long j2) {
                HuaweiTextToSpeechHolder.downloadModel$lambda$4(j, j2);
            }
        }).addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.stage.HuaweiTextToSpeechHolder$$ExternalSyntheticLambda1
            @Override // com.huawei.hmf.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                HuaweiTextToSpeechHolder.downloadModel$lambda$5((Void) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.stage.HuaweiTextToSpeechHolder$$ExternalSyntheticLambda2
            @Override // com.huawei.hmf.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                HuaweiTextToSpeechHolder.downloadModel$lambda$6(exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadModel$lambda$5(Void r1) {
        mlTtsEngine.updateConfig(mlTtsConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadModel$lambda$6(Exception exc) {
        Log.e(HuaweiTextToSpeechHolderKt.TAG, "Failed to download HMS Text-to-speech engine", exc);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/HuaweiTextToSpeechHolder$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: HuaweiTextToSpeechHolder.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/stage/HuaweiTextToSpeechHolder$Companion;", "", "()V", "instance", "Lorg/catrobat/catroid/stage/HuaweiTextToSpeechHolder;", "getInstance$annotations", "getInstance", "()Lorg/catrobat/catroid/stage/HuaweiTextToSpeechHolder;", "setInstance", "(Lorg/catrobat/catroid/stage/HuaweiTextToSpeechHolder;)V", "mlTtsConfig", "Lcom/huawei/hms/mlsdk/tts/MLTtsConfig;", "getMlTtsConfig", "()Lcom/huawei/hms/mlsdk/tts/MLTtsConfig;", "mlTtsEngine", "Lcom/huawei/hms/mlsdk/tts/MLTtsEngine;", "getMlTtsEngine", "()Lcom/huawei/hms/mlsdk/tts/MLTtsEngine;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getInstance$annotations() {
        }

        private Companion() {
        }

        public final HuaweiTextToSpeechHolder getInstance() {
            return HuaweiTextToSpeechHolder.instance;
        }

        public final void setInstance(HuaweiTextToSpeechHolder huaweiTextToSpeechHolder) {
            Intrinsics.checkNotNullParameter(huaweiTextToSpeechHolder, "<set-?>");
            HuaweiTextToSpeechHolder.instance = huaweiTextToSpeechHolder;
        }

        public final MLTtsConfig getMlTtsConfig() {
            return HuaweiTextToSpeechHolder.mlTtsConfig;
        }

        public final MLTtsEngine getMlTtsEngine() {
            return HuaweiTextToSpeechHolder.mlTtsEngine;
        }
    }

    static {
        MLTtsConfig synthesizeMode = new MLTtsConfig().setLanguage("en-US").setPerson(MLTtsConstants.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE).setSynthesizeMode(MLTtsConstants.TTS_OFFLINE_MODE);
        Intrinsics.checkNotNullExpressionValue(synthesizeMode, "setSynthesizeMode(...)");
        mlTtsConfig = synthesizeMode;
        mlTtsEngine = new MLTtsEngine(synthesizeMode);
    }
}
