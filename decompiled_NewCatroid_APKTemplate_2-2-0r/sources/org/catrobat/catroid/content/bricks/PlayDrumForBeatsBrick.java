package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.PickableDrum;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlayDrumForBeatsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlayDrumForBeatsBrick extends FormulaBrick implements BrickSpinner.OnItemSelectedListener<PickableDrum> {
    private PickableDrum drumSelection;

    protected int getSpinnerId() {
        return R.id.play_drum_for_beats_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_play_drum_for_beats;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    public PlayDrumForBeatsBrick() {
        this.drumSelection = PickableDrum.values()[0];
        addAllowedBrickField(Brick.BrickField.PLAY_DRUM, R.id.brick_play_drum_for_beats_edit_text);
    }

    public PlayDrumForBeatsBrick(int value) {
        this(new Formula(Integer.valueOf(value)));
    }

    private PlayDrumForBeatsBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PLAY_DRUM, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        for (PickableDrum pickableDrum : PickableDrum.values()) {
            pickableDrum.setName(pickableDrum.getString(context));
            arrayList.add(pickableDrum);
        }
        BrickSpinner brickSpinner = new BrickSpinner(Integer.valueOf(R.id.play_drum_for_beats_spinner), this.view, arrayList);
        brickSpinner.setSelection(PickableDrum.getIndexByValue(this.drumSelection.getValue()));
        brickSpinner.setOnItemSelectedListener(this);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlayDrumForBeatsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PLAY_DRUM), this.drumSelection));
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, PickableDrum item) {
        if (item != null) {
            this.drumSelection = item;
        }
    }
}
