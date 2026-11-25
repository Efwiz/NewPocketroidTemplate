package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LoadFromInternalStorageBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LoadFromInternalStorageBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_load_from_internal;
    }

    public LoadFromInternalStorageBrick() {
        addAllowedBrickField(Brick.BrickField.FILE_NAME, R.id.brick_load_from_internal_path);
    }

    public LoadFromInternalStorageBrick(String internalPath) {
        this(new Formula(internalPath));
    }

    public LoadFromInternalStorageBrick(Formula internalPath) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE_NAME, internalPath);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLoadFromInternalStorageAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE_NAME)));
    }
}
