package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.bricks.brickspinner.PickableDrum;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;
import org.catrobat.catroid.pocketmusic.note.Drum;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PlayDrumForBeatsAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlayDrumForBeatsAction extends TemporalAction {
    private Formula beats;
    private Scope scope;
    private PickableDrum selectedDrum;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        try {
            Drum drumFromProgram = Drum.getDrumFromProgram(this.selectedDrum.getValue());
            Formula formula = this.beats;
            float fFloatValue = (formula == null ? Float.valueOf(0.0f) : formula.interpretFloat(this.scope)).floatValue();
            MidiSoundManager.getInstance().playDrumForBeats(drumFromProgram, fFloatValue, this.scope.getSprite());
            super.setDuration(MidiSoundManager.getInstance().getDurationForBeats(fFloatValue) / 1000.0f);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setDrum(PickableDrum selectedDrum) {
        this.selectedDrum = selectedDrum;
    }

    public void setBeats(Formula beats) {
        this.beats = beats;
    }
}
