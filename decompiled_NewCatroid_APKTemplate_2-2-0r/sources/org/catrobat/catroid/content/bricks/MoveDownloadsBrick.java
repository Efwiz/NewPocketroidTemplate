package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/MoveDownloadsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MoveDownloadsBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_move_downloads;
    }

    public MoveDownloadsBrick() {
        addAllowedBrickField(Brick.BrickField.FILE, R.id.brick_move_downloads_edit_text);
    }

    public MoveDownloadsBrick(String value) {
        this(new Formula(value));
    }

    public MoveDownloadsBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createMoveDownloadsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE)));
    }
}
