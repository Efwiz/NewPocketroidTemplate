package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/FileUrlBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FileUrlBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_file_url;
    }

    public FileUrlBrick() {
        addAllowedBrickField(Brick.BrickField.FILE_URL, R.id.brick_file_url_edit_text);
        addAllowedBrickField(Brick.BrickField.FILE, R.id.brick_file_url_edit_file);
    }

    public FileUrlBrick(String value, String value2) {
        this(new Formula(value), new Formula(value2));
    }

    public FileUrlBrick(Formula formula, Formula formula2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE_URL, formula);
        setFormulaWithBrickField(Brick.BrickField.FILE, formula2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFileUrlAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE_URL), getFormulaWithBrickField(Brick.BrickField.FILE)));
    }
}
