package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/HttpAttachFileBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HttpAttachFileBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_http_attach_file;
    }

    public HttpAttachFileBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_http_attach_file_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_http_attach_file_name_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_http_attach_file_parameter_field);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_http_attach_file_mime_field);
    }

    public HttpAttachFileBrick(String requestId, String fileName, String parameterName, String mimeType) {
        this(new Formula(requestId), new Formula(fileName), new Formula(parameterName), new Formula(mimeType));
    }

    public HttpAttachFileBrick(Formula requestId, Formula fileName, Formula parameterName, Formula mimeType) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, requestId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, fileName);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, parameterName);
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, mimeType);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createHttpAttachFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4)));
    }
}
