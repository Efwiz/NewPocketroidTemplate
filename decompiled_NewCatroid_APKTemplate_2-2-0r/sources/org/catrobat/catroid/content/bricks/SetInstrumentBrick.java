package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.PickableMusicalInstrument;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetInstrumentBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetInstrumentBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<PickableMusicalInstrument> {
    public PickableMusicalInstrument instrumentSelection = PickableMusicalInstrument.values()[0];

    protected int getSpinnerId() {
        return R.id.set_instrument_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_instrument;
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

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        for (PickableMusicalInstrument pickableMusicalInstrument : PickableMusicalInstrument.values()) {
            pickableMusicalInstrument.setName(pickableMusicalInstrument.getString(context));
            arrayList.add(pickableMusicalInstrument);
        }
        BrickSpinner brickSpinner = new BrickSpinner(Integer.valueOf(R.id.set_instrument_spinner), this.view, arrayList);
        brickSpinner.setSelection(PickableMusicalInstrument.getIndexByValue(this.instrumentSelection.getValue()));
        brickSpinner.setOnItemSelectedListener(this);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetInstrumentAction(this.instrumentSelection));
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, PickableMusicalInstrument item) {
        if (item != null) {
            this.instrumentSelection = item;
        }
    }
}
