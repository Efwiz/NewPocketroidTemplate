package org.catrobat.catroid.content.bricks;

import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenClonedWithNameScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenClonedWithNameBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenClonedWithNameBrick extends FormulaBrick implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private WhenClonedWithNameScript script;

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
        return R.layout.brick_when_cloned_with_name;
    }

    public WhenClonedWithNameBrick() {
        this(new WhenClonedWithNameScript());
    }

    public WhenClonedWithNameBrick(WhenClonedWithNameScript script) {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_when_cloned_with_name_edit_text);
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
        this.formulaMap = script.getFormulaMap();
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenClonedWithNameBrick whenClonedWithNameBrick = (WhenClonedWithNameBrick) super.clone();
        WhenClonedWithNameScript whenClonedWithNameScript = (WhenClonedWithNameScript) this.script.clone();
        whenClonedWithNameBrick.script = whenClonedWithNameScript;
        whenClonedWithNameScript.setScriptBrick(whenClonedWithNameBrick);
        whenClonedWithNameBrick.formulaMap = whenClonedWithNameBrick.script.getFormulaMap();
        return whenClonedWithNameBrick;
    }

    public Formula getNameFormula() {
        return getFormulaWithBrickField(Brick.BrickField.VALUE_1);
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
