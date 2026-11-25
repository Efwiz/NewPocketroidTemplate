package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Base64ToFileBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Base64ToFileBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_base64_to_file;
    }

    public Base64ToFileBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_base64_to_file_source);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_base64_to_file_destination);
    }

    public Base64ToFileBrick(String base64String, String destinationFileName) {
        this(new Formula(base64String), new Formula(destinationFileName));
    }

    public Base64ToFileBrick(Formula base64String, Formula destinationFileName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, base64String);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, destinationFileName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createBase64ToFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
