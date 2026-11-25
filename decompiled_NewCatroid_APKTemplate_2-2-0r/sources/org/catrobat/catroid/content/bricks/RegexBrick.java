package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RegexBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RegexBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserListBrick
    protected int getSpinnerId() {
        return R.id.regex_userlist_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_regex;
    }

    public RegexBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_regex_edit_text);
        addAllowedBrickField(Brick.BrickField.REGEX, R.id.brick_regex_edit_regex);
    }

    public RegexBrick(String text, String regex) {
        this(new Formula(text), new Formula(regex));
    }

    public RegexBrick(Formula text, Formula regex) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT, text);
        setFormulaWithBrickField(Brick.BrickField.REGEX, regex);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRegexAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.REGEX), this.userList));
    }
}
