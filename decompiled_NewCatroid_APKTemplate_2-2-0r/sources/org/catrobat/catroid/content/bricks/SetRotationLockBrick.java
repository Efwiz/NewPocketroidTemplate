package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.actions.SetRotationLockAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetRotationLockBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetRotationLockBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    public boolean lockX;
    public boolean lockY;
    public boolean lockZ;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_rotation_lock;
    }

    public SetRotationLockBrick() {
        this.lockX = false;
        this.lockY = false;
        this.lockZ = false;
        addAllowedBrickField(Brick.BrickField.STRING, R.id.brick_rotation_lock_object_id);
    }

    public SetRotationLockBrick(String objectId, boolean x, boolean y, boolean z) {
        this();
        setFormulaWithBrickField(Brick.BrickField.STRING, new Formula(objectId));
        this.lockX = x;
        this.lockY = y;
        this.lockZ = z;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        CheckBox checkBox = (CheckBox) this.view.findViewById(R.id.brick_rotation_lock_x);
        CheckBox checkBox2 = (CheckBox) this.view.findViewById(R.id.brick_rotation_lock_y);
        CheckBox checkBox3 = (CheckBox) this.view.findViewById(R.id.brick_rotation_lock_z);
        checkBox.setChecked(this.lockX);
        checkBox2.setChecked(this.lockY);
        checkBox3.setChecked(this.lockZ);
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.content.bricks.SetRotationLockBrick$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m4974xa752d576(compoundButton, z);
            }
        };
        checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox2.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox3.setOnCheckedChangeListener(onCheckedChangeListener);
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-SetRotationLockBrick, reason: not valid java name */
    /* synthetic */ void m4974xa752d576(CompoundButton compoundButton, boolean z) {
        int id2 = compoundButton.getId();
        if (id2 == R.id.brick_rotation_lock_x) {
            this.lockX = z;
        } else if (id2 == R.id.brick_rotation_lock_y) {
            this.lockY = z;
        } else if (id2 == R.id.brick_rotation_lock_z) {
            this.lockZ = z;
        }
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        SetRotationLockAction setRotationLockAction = (SetRotationLockAction) sprite.getActionFactory().createSetRotationLockAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.STRING));
        setRotationLockAction.setLockX(this.lockX);
        setRotationLockAction.setLockY(this.lockY);
        setRotationLockAction.setLockZ(this.lockZ);
        sequence.addAction(setRotationLockAction);
    }
}
