package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PlayNoteForBeatsAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlayNoteForBeatsAction extends TemporalAction {
    private Formula beats;
    private Formula midiValue;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        try {
            Formula formula = this.midiValue;
            int iIntValue = formula != null ? formula.interpretInteger(this.scope).intValue() : 0;
            Formula formula2 = this.beats;
            float fFloatValue = formula2 != null ? formula2.interpretFloat(this.scope).floatValue() : 0.0f;
            MidiSoundManager.getInstance().playNoteForBeats(iIntValue, fFloatValue);
            super.setDuration(MidiSoundManager.getInstance().getDurationForBeats(fFloatValue) / 1000.0f);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setMidiValue(Formula midiValue) {
        this.midiValue = midiValue;
    }

    public void setBeats(Formula beats) {
        this.beats = beats;
    }
}
