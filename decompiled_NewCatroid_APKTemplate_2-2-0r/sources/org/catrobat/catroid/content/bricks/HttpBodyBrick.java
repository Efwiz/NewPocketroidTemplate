package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/HttpBodyBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HttpBodyBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_http_body;
    }

    public HttpBodyBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_http_body_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_http_body_text_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_http_body_content_type_field);
    }

    public HttpBodyBrick(String requestId, String bodyText, String contentType) {
        this(new Formula(requestId), new Formula(bodyText), new Formula(contentType));
    }

    public HttpBodyBrick(Formula requestId, Formula bodyText, Formula contentType) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, requestId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, bodyText);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, contentType);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createHttpBodyAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
