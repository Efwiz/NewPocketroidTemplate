package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PauseForBeatsAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PauseForBeatsAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0014J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/content/actions/PauseForBeatsAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "beats", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBeats", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBeats", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "begin", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PauseForBeatsAction extends TemporalAction {
    public Formula beats;
    public Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
    }

    public final Scope getScope() {
        Scope scope = this.scope;
        if (scope != null) {
            return scope;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scope");
        return null;
    }

    public final void setScope(Scope scope) {
        Intrinsics.checkNotNullParameter(scope, "<set-?>");
        this.scope = scope;
    }

    public final Formula getBeats() {
        Formula formula = this.beats;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("beats");
        return null;
    }

    public final void setBeats(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.beats = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        try {
            Float fInterpretFloat = getBeats().interpretFloat(getScope());
            Intrinsics.checkNotNullExpressionValue(fInterpretFloat, "interpretFloat(...)");
            super.setDuration(MidiSoundManager.getInstance().getDurationForBeats(fInterpretFloat.floatValue()) / 1000);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }
}
