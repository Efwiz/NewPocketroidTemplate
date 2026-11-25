package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateDiskBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateDiskBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_disk;
    }

    public CreateDiskBrick() {
        addAllowedBrickField(Brick.BrickField.VM_DISK_NAME, R.id.brick_disk_name_edit);
        addAllowedBrickField(Brick.BrickField.VM_DISK_SIZE, R.id.brick_disk_size_edit);
    }

    public CreateDiskBrick(String diskName, String diskSize) {
        this(new Formula(diskName), new Formula(diskSize));
    }

    public CreateDiskBrick(Formula diskName, Formula diskSize) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VM_DISK_NAME, diskName);
        setFormulaWithBrickField(Brick.BrickField.VM_DISK_SIZE, diskSize);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDiskAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VM_DISK_NAME), getFormulaWithBrickField(Brick.BrickField.VM_DISK_SIZE)));
    }
}
