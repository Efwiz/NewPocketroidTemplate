package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/HttpCreateBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HttpCreateBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_http_create;
    }

    public HttpCreateBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_http_create_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_http_create_method_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_http_create_url_field);
    }

    public HttpCreateBrick(String requestId, String method, String url) {
        this(new Formula(requestId), new Formula(method), new Formula(url));
    }

    public HttpCreateBrick(Formula requestId, Formula method, Formula url) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, requestId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, method);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, url);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createHttpCreateAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
