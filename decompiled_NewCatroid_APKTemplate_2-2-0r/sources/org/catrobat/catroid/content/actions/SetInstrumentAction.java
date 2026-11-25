package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.bricks.brickspinner.PickableMusicalInstrument;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetInstrumentAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetInstrumentAction extends TemporalAction {
    PickableMusicalInstrument selectedInstrument;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        MidiSoundManager.getInstance().setInstrument(MusicalInstrument.getInstrumentFromProgram(this.selectedInstrument.getValue()));
    }

    public void setInstrument(PickableMusicalInstrument selectedInstrument) {
        this.selectedInstrument = selectedInstrument;
    }
}
