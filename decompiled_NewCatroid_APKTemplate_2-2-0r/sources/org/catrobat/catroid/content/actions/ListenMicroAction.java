package org.catrobat.catroid.content.actions;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.AudioAnalyzerController;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.VolumeManager;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ListenMicroAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ListenMicroAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0014J\u0006\u0010\u0015\u001a\u00020\u0014J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/ListenMicroAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "audioAnalyzer", "Lorg/catrobat/catroid/content/AudioAnalyzerController;", "countDownTimer", "Landroid/os/CountDownTimer;", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "time", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getTime", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setTime", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "begin", "", "stop", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ListenMicroAction extends TemporalAction {
    private AudioAnalyzerController audioAnalyzer;
    private CountDownTimer countDownTimer;
    private Scope scope;
    private Formula time;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
    }

    public ListenMicroAction() {
        Context appContext = CatroidApplication.getAppContext();
        Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
        this.audioAnalyzer = new AudioAnalyzerController(appContext);
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getTime() {
        return this.time;
    }

    public final void setTime(Formula formula) {
        this.time = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        Object objInterpretObject;
        String string;
        Long longOrNull;
        super.begin();
        Log.d("ListenMicro", "Started");
        Formula formula = this.time;
        final long jLongValue = (formula == null || (objInterpretObject = formula.interpretObject(this.scope)) == null || (string = objInterpretObject.toString()) == null || (longOrNull = StringsKt.toLongOrNull(string)) == null) ? 100L : longOrNull.longValue();
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.ListenMicroAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ListenMicroAction.begin$lambda$1(this.f$0, jLongValue);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v2, types: [org.catrobat.catroid.content.actions.ListenMicroAction$begin$1$1] */
    public static final void begin$lambda$1(final ListenMicroAction this$0, final long j) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.audioAnalyzer.start();
        ?? r0 = new CountDownTimer(j) { // from class: org.catrobat.catroid.content.actions.ListenMicroAction$begin$1$1
            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                VolumeManager.INSTANCE.setVolume(Integer.valueOf(this$0.audioAnalyzer.getCurrentVolume()));
                VolumeManager.INSTANCE.setFrequency(Float.valueOf(this$0.audioAnalyzer.getCurrentFrequency()));
            }

            @Override // android.os.CountDownTimer
            public void onFinish() throws InterruptedException {
                this$0.audioAnalyzer.stop();
                Log.d("ListenMicro", "Timer Ended");
            }
        };
        r0.start();
        this$0.countDownTimer = (CountDownTimer) r0;
    }

    public final void stop() throws InterruptedException {
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.audioAnalyzer.stop();
    }
}
