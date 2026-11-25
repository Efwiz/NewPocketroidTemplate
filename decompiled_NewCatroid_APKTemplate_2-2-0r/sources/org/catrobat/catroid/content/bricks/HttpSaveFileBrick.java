package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/HttpSaveFileBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HttpSaveFileBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_http_save_file;
    }

    public HttpSaveFileBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_http_save_file_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_http_save_file_dest_field);
    }

    public HttpSaveFileBrick(String requestId, String destinationFileName) {
        this(new Formula(requestId), new Formula(destinationFileName));
    }

    public HttpSaveFileBrick(Formula requestId, Formula destinationFileName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, requestId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, destinationFileName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createHttpSaveFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
