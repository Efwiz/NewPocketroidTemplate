package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PostWebRequestBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PostWebRequestBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.post_web_request_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_post_web_request;
    }

    public PostWebRequestBrick() {
        addAllowedBrickField(Brick.BrickField.URL, R.id.brick_post_web_request_edit_url);
        addAllowedBrickField(Brick.BrickField.HEADER, R.id.brick_post_web_request_edit_header);
        addAllowedBrickField(Brick.BrickField.BODY, R.id.brick_post_web_request_edit_body);
    }

    public PostWebRequestBrick(String val1, String val2, String val3) {
        this(new Formula(val1), new Formula(val2), new Formula(val3), null);
    }

    public PostWebRequestBrick(Formula rurl, Formula header, Formula body, UserVariable userVariable) {
        this();
        setFormulaWithBrickField(Brick.BrickField.URL, rurl);
        setFormulaWithBrickField(Brick.BrickField.HEADER, header);
        setFormulaWithBrickField(Brick.BrickField.BODY, body);
        this.userVariable = userVariable;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPostWebRequestAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.URL), getFormulaWithBrickField(Brick.BrickField.HEADER), getFormulaWithBrickField(Brick.BrickField.BODY), this.userVariable));
    }
}
