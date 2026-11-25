package org.catrobat.catroid.content.bricks;

import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenConditionScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenConditionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenConditionBrick extends FormulaBrick implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private WhenConditionScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public int getPositionInDragAndDropTargetList() {
        return -1;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public int getPositionInScript() {
        return -1;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_condition_true;
    }

    public WhenConditionBrick() {
        this(new WhenConditionScript());
    }

    public WhenConditionBrick(WhenConditionScript script) {
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_when_condition_edit_text);
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
        this.formulaMap = script.getFormulaMap();
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenConditionBrick whenConditionBrick = (WhenConditionBrick) super.clone();
        WhenConditionScript whenConditionScript = (WhenConditionScript) this.script.clone();
        whenConditionBrick.script = whenConditionScript;
        whenConditionScript.setScriptBrick(whenConditionBrick);
        whenConditionBrick.formulaMap = whenConditionBrick.script.getFormulaMap();
        return whenConditionBrick;
    }

    public Formula getConditionFormula() {
        return getFormulaWithBrickField(Brick.BrickField.IF_CONDITION);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addToFlatList(List<Brick> bricks) {
        super.addToFlatList(bricks);
        Iterator<Brick> it = getScript().getBrickList().iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(bricks);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return getScript().getBrickList();
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }
}
