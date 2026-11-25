package org.catrobat.catroid.stage;

import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.ContextThemeWrapper;
import androidx.appcompat.app.AlertDialog;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/TextToSpeechHolder.class
  classes14.dex
 */
/* JADX INFO: compiled from: TextToSpeechHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0016\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\u0004J6\u0010\u000b\u001a\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0013¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/stage/TextToSpeechHolder;", "", "()V", "deleteSpeechFiles", "", "initTextToSpeech", "stageActivity", "Lorg/catrobat/catroid/stage/StageActivity;", "stageResourceHolder", "Lorg/catrobat/catroid/stage/StageResourceHolder;", "shutDownTextToSpeech", "textToSpeech", "text", "", "speechFile", "Ljava/io/File;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Landroid/speech/tts/UtteranceProgressListener;", "speakParameter", "Ljava/util/HashMap;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TextToSpeechHolder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "TextToSpeechHolder";
    private static TextToSpeechHolder instance = new TextToSpeechHolder();
    private static TextToSpeech textToSpeech;
    private static UtteranceProgressListenerContainer utteranceProgressListenerContainer;

    public static final TextToSpeechHolder getInstance() {
        return INSTANCE.getInstance();
    }

    public static final void setInstance(TextToSpeechHolder textToSpeechHolder) {
        INSTANCE.setInstance(textToSpeechHolder);
    }

    private TextToSpeechHolder() {
    }

    public final void initTextToSpeech(final StageActivity stageActivity, final StageResourceHolder stageResourceHolder) {
        Intrinsics.checkNotNullParameter(stageActivity, "stageActivity");
        Intrinsics.checkNotNullParameter(stageResourceHolder, "stageResourceHolder");
        textToSpeech = new TextToSpeech(stageActivity, new TextToSpeech.OnInitListener() { // from class: org.catrobat.catroid.stage.TextToSpeechHolder$$ExternalSyntheticLambda2
            @Override // android.speech.tts.TextToSpeech.OnInitListener
            public final void onInit(int i) {
                TextToSpeechHolder.initTextToSpeech$lambda$2(stageResourceHolder, stageActivity, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTextToSpeech$lambda$2(final StageResourceHolder stageResourceHolder, final StageActivity stageActivity, int i) {
        Intrinsics.checkNotNullParameter(stageResourceHolder, "$stageResourceHolder");
        Intrinsics.checkNotNullParameter(stageActivity, "$stageActivity");
        if (i == 0) {
            UtteranceProgressListenerContainer utteranceProgressListenerContainer2 = new UtteranceProgressListenerContainer();
            utteranceProgressListenerContainer = utteranceProgressListenerContainer2;
            TextToSpeech textToSpeech2 = textToSpeech;
            if (textToSpeech2 != null) {
                textToSpeech2.setOnUtteranceProgressListener(utteranceProgressListenerContainer2);
            }
            stageResourceHolder.resourceInitialized();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(stageActivity, 2131952355));
        builder.setMessage(R.string.prestage_text_to_speech_engine_not_installed).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.TextToSpeechHolder$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                TextToSpeechHolder.initTextToSpeech$lambda$2$lambda$0(stageActivity, stageResourceHolder, dialogInterface, i2);
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.TextToSpeechHolder$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                TextToSpeechHolder.initTextToSpeech$lambda$2$lambda$1(stageResourceHolder, dialogInterface, i2);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTextToSpeech$lambda$2$lambda$0(StageActivity stageActivity, StageResourceHolder stageResourceHolder, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(stageActivity, "$stageActivity");
        Intrinsics.checkNotNullParameter(stageResourceHolder, "$stageResourceHolder");
        Intent intent = new Intent();
        intent.setAction("android.speech.tts.engine.INSTALL_TTS_DATA");
        stageActivity.startActivity(intent);
        stageResourceHolder.resourceFailed(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTextToSpeech$lambda$2$lambda$1(StageResourceHolder stageResourceHolder, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(stageResourceHolder, "$stageResourceHolder");
        stageResourceHolder.resourceFailed(1);
    }

    public final void shutDownTextToSpeech() {
        TextToSpeech textToSpeech2 = textToSpeech;
        if (textToSpeech2 != null) {
            textToSpeech2.stop();
        }
        TextToSpeech textToSpeech3 = textToSpeech;
        if (textToSpeech3 != null) {
            textToSpeech3.shutdown();
        }
    }

    public final void textToSpeech(String text, File speechFile, UtteranceProgressListener listener, HashMap<String, String> speakParameter) {
        Integer numValueOf;
        Intrinsics.checkNotNullParameter(speechFile, "speechFile");
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(speakParameter, "speakParameter");
        UtteranceProgressListenerContainer utteranceProgressListenerContainer2 = utteranceProgressListenerContainer;
        if (utteranceProgressListenerContainer2 == null || !utteranceProgressListenerContainer2.addUtteranceProgressListener(speechFile, listener, speakParameter.get("utteranceId"))) {
            return;
        }
        TextToSpeech textToSpeech2 = textToSpeech;
        if (textToSpeech2 != null) {
            if (text == null) {
                text = "";
            }
            numValueOf = Integer.valueOf(textToSpeech2.synthesizeToFile(text, speakParameter, speechFile.getAbsolutePath()));
        } else {
            numValueOf = null;
        }
        if (numValueOf != null && numValueOf.intValue() == -1) {
            Log.e(TAG, "File synthesizing failed");
        }
    }

    public final void deleteSpeechFiles() {
        File file = new File(Constants.TEXT_TO_SPEECH_TMP_PATH);
        if (file.isDirectory()) {
            Iterator<File> it = FilesKt.walk$default(file, null, 1, null).iterator();
            while (it.hasNext()) {
                it.next().delete();
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/TextToSpeechHolder$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: TextToSpeechHolder.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/stage/TextToSpeechHolder$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "instance", "Lorg/catrobat/catroid/stage/TextToSpeechHolder;", "getInstance$annotations", "getInstance", "()Lorg/catrobat/catroid/stage/TextToSpeechHolder;", "setInstance", "(Lorg/catrobat/catroid/stage/TextToSpeechHolder;)V", "textToSpeech", "Landroid/speech/tts/TextToSpeech;", "utteranceProgressListenerContainer", "Lorg/catrobat/catroid/stage/UtteranceProgressListenerContainer;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getInstance$annotations() {
        }

        private Companion() {
        }

        public final TextToSpeechHolder getInstance() {
            return TextToSpeechHolder.instance;
        }

        public final void setInstance(TextToSpeechHolder textToSpeechHolder) {
            Intrinsics.checkNotNullParameter(textToSpeechHolder, "<set-?>");
            TextToSpeechHolder.instance = textToSpeechHolder;
        }
    }
}
