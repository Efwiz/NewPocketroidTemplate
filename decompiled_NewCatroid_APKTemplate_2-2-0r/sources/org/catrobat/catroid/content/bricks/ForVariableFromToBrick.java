package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.utils.LoopUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ForVariableFromToBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ForVariableFromToBrick extends UserVariableBrickWithFormula implements CompositeBrick {
    private transient EndBrick endBrick;
    private List<Brick> loopBricks;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean consistsOfMultipleParts() {
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getSecondaryNestedBricks() {
        return null;
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_for_variable_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_for_variable_from_to;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return false;
    }

    public ForVariableFromToBrick() {
        this.endBrick = new EndBrick(this);
        this.loopBricks = new ArrayList();
        addAllowedBrickField(Brick.BrickField.FOR_LOOP_FROM, R.id.brick_loop_from_edit);
        addAllowedBrickField(Brick.BrickField.FOR_LOOP_TO, R.id.brick_loop_to_edit);
    }

    public ForVariableFromToBrick(int from, int to) {
        this(new Formula(Integer.valueOf(from)), new Formula(Integer.valueOf(to)));
    }

    public ForVariableFromToBrick(Formula from, Formula to) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FOR_LOOP_FROM, from);
        setFormulaWithBrickField(Brick.BrickField.FOR_LOOP_TO, to);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.FOR_LOOP_FROM;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getNestedBricks() {
        return this.loopBricks;
    }

    public boolean addBrick(Brick brick) {
        return this.loopBricks.add(brick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            it.next().setCommentedOut(commentedOut);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        ForVariableFromToBrick forVariableFromToBrick = (ForVariableFromToBrick) super.clone();
        forVariableFromToBrick.endBrick = new EndBrick(forVariableFromToBrick);
        forVariableFromToBrick.loopBricks = new ArrayList();
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            forVariableFromToBrick.addBrick(it.next().clone());
        }
        return forVariableFromToBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getAllParts() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this);
        arrayList.add(this.endBrick);
        return arrayList;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addToFlatList(List<Brick> bricks) {
        super.addToFlatList(bricks);
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(bricks);
        }
        bricks.add(this.endBrick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setParent(Brick parent) {
        super.setParent(parent);
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            it.next().setParent(this);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return this.loopBricks;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean removeChild(Brick brick) {
        if (this.loopBricks.remove(brick)) {
            return true;
        }
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            if (it.next().removeChild(brick)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.userVariable == null || this.userVariable.getName() == null) {
            this.userVariable = new UserVariable("NoVariableSet", CatroidApplication.getAppContext().getString(R.string.no_variable_selected));
            this.userVariable.setDummy(true);
        }
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(sequence.getScript());
        boolean zCheckLoopBrickForLoopDelay = LoopUtil.checkLoopBrickForLoopDelay(this, sequence.getScript());
        for (Brick brick : this.loopBricks) {
            if (!brick.isCommentedOut()) {
                brick.addActionToSequence(sprite, scriptSequenceAction);
            }
        }
        sequence.addAction(sprite.getActionFactory().createForVariableFromToAction(sprite, sequence, this.userVariable, getFormulaWithBrickField(Brick.BrickField.FOR_LOOP_FROM), getFormulaWithBrickField(Brick.BrickField.FOR_LOOP_TO), scriptSequenceAction, zCheckLoopBrickForLoopDelay));
    }
}
