package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/IfLogicBeginBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class IfLogicBeginBrick extends FormulaBrick implements CompositeBrick {
    private static final long serialVersionUID = 1;
    protected List<Brick> elseBranchBricks;
    private transient ElseBrick elseBrick;
    private transient EndBrick endBrick;
    protected List<Brick> ifBranchBricks;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean consistsOfMultipleParts() {
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_if_begin_if;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return true;
    }

    public IfLogicBeginBrick() {
        this.elseBrick = new ElseBrick(this);
        this.endBrick = new EndBrick(this, R.layout.brick_if_end_if);
        this.ifBranchBricks = new ArrayList();
        this.elseBranchBricks = new ArrayList();
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_if_begin_edit_text);
    }

    public IfLogicBeginBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getNestedBricks() {
        return this.ifBranchBricks;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getSecondaryNestedBricks() {
        return this.elseBranchBricks;
    }

    public boolean addBrickToIfBranch(Brick brick) {
        return this.ifBranchBricks.add(brick);
    }

    public boolean addBrickToElseBranch(Brick brick) {
        return this.elseBranchBricks.add(brick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        Iterator<Brick> it = this.ifBranchBricks.iterator();
        while (it.hasNext()) {
            it.next().setCommentedOut(commentedOut);
        }
        Iterator<Brick> it2 = this.elseBranchBricks.iterator();
        while (it2.hasNext()) {
            it2.next().setCommentedOut(commentedOut);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        IfLogicBeginBrick ifLogicBeginBrick = (IfLogicBeginBrick) super.clone();
        ifLogicBeginBrick.elseBrick = new ElseBrick(ifLogicBeginBrick);
        ifLogicBeginBrick.endBrick = new EndBrick(ifLogicBeginBrick, R.layout.brick_if_end_if);
        ifLogicBeginBrick.ifBranchBricks = new ArrayList();
        ifLogicBeginBrick.elseBranchBricks = new ArrayList();
        Iterator<Brick> it = this.ifBranchBricks.iterator();
        while (it.hasNext()) {
            ifLogicBeginBrick.addBrickToIfBranch(it.next().clone());
        }
        Iterator<Brick> it2 = this.elseBranchBricks.iterator();
        while (it2.hasNext()) {
            ifLogicBeginBrick.addBrickToElseBranch(it2.next().clone());
        }
        return ifLogicBeginBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getAllParts() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this);
        arrayList.add(this.elseBrick);
        arrayList.add(this.endBrick);
        return arrayList;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addToFlatList(List<Brick> bricks) {
        super.addToFlatList(bricks);
        Iterator<Brick> it = this.ifBranchBricks.iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(bricks);
        }
        bricks.add(this.elseBrick);
        Iterator<Brick> it2 = this.elseBranchBricks.iterator();
        while (it2.hasNext()) {
            it2.next().addToFlatList(bricks);
        }
        bricks.add(this.endBrick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setParent(Brick parent) {
        super.setParent(parent);
        Iterator<Brick> it = this.ifBranchBricks.iterator();
        while (it.hasNext()) {
            it.next().setParent(this);
        }
        Iterator<Brick> it2 = this.elseBranchBricks.iterator();
        while (it2.hasNext()) {
            it2.next().setParent(this.elseBrick);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return this.ifBranchBricks;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean removeChild(Brick brick) {
        if (this.ifBranchBricks.remove(brick) || this.elseBranchBricks.remove(brick)) {
            return true;
        }
        Iterator<Brick> it = this.ifBranchBricks.iterator();
        while (it.hasNext()) {
            if (it.next().removeChild(brick)) {
                return true;
            }
        }
        Iterator<Brick> it2 = this.elseBranchBricks.iterator();
        while (it2.hasNext()) {
            if (it2.next().removeChild(brick)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        prototypeView.findViewById(R.id.if_else_prototype_punctuation).setVisibility(0);
        prototypeView.findViewById(R.id.if_prototype_else).setVisibility(0);
        prototypeView.findViewById(R.id.if_else_prototype_punctuation2).setVisibility(0);
        return prototypeView;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(sequence.getScript());
        ScriptSequenceAction scriptSequenceAction2 = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(sequence.getScript());
        for (Brick brick : this.ifBranchBricks) {
            if (!brick.isCommentedOut()) {
                brick.addActionToSequence(sprite, scriptSequenceAction);
            }
        }
        for (Brick brick2 : this.elseBranchBricks) {
            if (!brick2.isCommentedOut()) {
                brick2.addActionToSequence(sprite, scriptSequenceAction2);
            }
        }
        sequence.addAction(sprite.getActionFactory().createIfLogicAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), scriptSequenceAction, scriptSequenceAction2));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        super.addRequiredResources(requiredResourcesSet);
        Iterator<Brick> it = this.ifBranchBricks.iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(requiredResourcesSet);
        }
        Iterator<Brick> it2 = this.elseBranchBricks.iterator();
        while (it2.hasNext()) {
            it2.next().addRequiredResources(requiredResourcesSet);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/IfLogicBeginBrick$ElseBrick.class
      classes14.dex
     */
    public static class ElseBrick extends BrickBaseType {
        @Override // org.catrobat.catroid.content.bricks.Brick
        public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public boolean consistsOfMultipleParts() {
            return true;
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public int getPositionInDragAndDropTargetList() {
            return -1;
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType
        public int getViewResource() {
            return R.layout.brick_if_else;
        }

        ElseBrick(IfLogicBeginBrick ifBrick) {
            this.parent = ifBrick;
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public boolean isCommentedOut() {
            return this.parent.isCommentedOut();
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public List<Brick> getAllParts() {
            return this.parent.getAllParts();
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public void addToFlatList(List<Brick> bricks) {
            this.parent.addToFlatList(bricks);
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public List<Brick> getDragAndDropTargetList() {
            return ((IfLogicBeginBrick) this.parent).elseBranchBricks;
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public UUID getBrickID() {
            return this.parent.getBrickID();
        }
    }
}
