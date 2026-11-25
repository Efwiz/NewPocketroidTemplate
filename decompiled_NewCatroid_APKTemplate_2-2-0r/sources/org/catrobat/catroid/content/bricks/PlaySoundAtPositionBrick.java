package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlaySoundAtPositionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlaySoundAtPositionBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int loopSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_play_sound_at_pos;
    }

    public PlaySoundAtPositionBrick() {
        this.loopSelection = 0;
        addAllowedBrickField(Brick.BrickField.SOUND_NAME, R.id.brick_play_sound_3d_name_edit);
        addAllowedBrickField(Brick.BrickField.INSTANCE_NAME, R.id.brick_play_sound_3d_instance_edit);
        addAllowedBrickField(Brick.BrickField.POS_X, R.id.brick_play_sound_3d_x_edit);
        addAllowedBrickField(Brick.BrickField.POS_Y, R.id.brick_play_sound_3d_y_edit);
        addAllowedBrickField(Brick.BrickField.POS_Z, R.id.brick_play_sound_3d_z_edit);
        addAllowedBrickField(Brick.BrickField.VOLUME, R.id.brick_play_sound_3d_volume_edit);
        addAllowedBrickField(Brick.BrickField.PITCH, R.id.brick_play_sound_3d_pitch_edit);
    }

    public PlaySoundAtPositionBrick(String soundName, String instanceName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.SOUND_NAME, new Formula(soundName));
        setFormulaWithBrickField(Brick.BrickField.INSTANCE_NAME, new Formula(instanceName));
        setFormulaWithBrickField(Brick.BrickField.POS_X, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.POS_Y, new Formula((Integer) 10));
        setFormulaWithBrickField(Brick.BrickField.POS_Z, new Formula((Integer) (-10)));
        setFormulaWithBrickField(Brick.BrickField.VOLUME, new Formula((Integer) 100));
        setFormulaWithBrickField(Brick.BrickField.PITCH, new Formula((Integer) 100));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_play_sound_3d_loop_spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.simple_spinner_item_white_text, new String[]{"No", "Yes"});
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setSelection(this.loopSelection);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.PlaySoundAtPositionBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                PlaySoundAtPositionBrick.this.loopSelection = position;
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlaySoundAtPositionAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.SOUND_NAME), getFormulaWithBrickField(Brick.BrickField.INSTANCE_NAME), getFormulaWithBrickField(Brick.BrickField.POS_X), getFormulaWithBrickField(Brick.BrickField.POS_Y), getFormulaWithBrickField(Brick.BrickField.POS_Z), getFormulaWithBrickField(Brick.BrickField.VOLUME), getFormulaWithBrickField(Brick.BrickField.PITCH), this.loopSelection == 1));
    }
}
