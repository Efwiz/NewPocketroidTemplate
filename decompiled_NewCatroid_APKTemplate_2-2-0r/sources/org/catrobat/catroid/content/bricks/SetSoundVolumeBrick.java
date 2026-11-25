package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetSoundVolumeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetSoundVolumeBrick extends FormulaBrick implements BrickSpinner.OnItemSelectedListener<SoundInfo>, NewItemInterface<SoundInfo> {
    private static final long serialVersionUID = 1;
    private SoundInfo sound;
    private transient BrickSpinner<SoundInfo> spinner;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_sound_volume;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    public SetSoundVolumeBrick() {
        addAllowedBrickField(Brick.BrickField.VOLUME, R.id.brick_set_sound_volume_edit_text);
    }

    public SetSoundVolumeBrick(Double volume) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VOLUME, new Formula(volume));
    }

    public SetSoundVolumeBrick(SoundInfo sound, Formula volume) {
        this();
        this.sound = sound;
        setFormulaWithBrickField(Brick.BrickField.VOLUME, volume);
    }

    public SoundInfo getSound() {
        return this.sound;
    }

    public void setSound(SoundInfo sound) {
        this.sound = sound;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        SetSoundVolumeBrick setSoundVolumeBrick = (SetSoundVolumeBrick) super.clone();
        setSoundVolumeBrick.spinner = null;
        return setSoundVolumeBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.new_option)));
        arrayList.addAll(ProjectManager.getInstance().getCurrentSprite().getSoundList());
        BrickSpinner<SoundInfo> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_set_sound_volume_spinner), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.sound);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetSoundVolumeAction(sprite, sequence, this.sound, getFormulaWithBrickField(Brick.BrickField.VOLUME)));
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, SoundInfo item) {
        this.sound = item;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView instanceof SpriteActivity) {
            SpriteActivity spriteActivity = (SpriteActivity) activityFromView;
            spriteActivity.registerOnNewSoundListener(this);
            spriteActivity.handleAddSoundButton();
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface
    public void addItem(SoundInfo item) {
        this.spinner.add(item);
        this.spinner.setSelection(item);
    }
}
