package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/InstantBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InstantBrick extends BrickBaseType implements CompositeBrick {
    private static final long serialVersionUID = 1;
    private transient EndBrick endBrick = new EndBrick(this);
    private List<Brick> nestedBricks = new ArrayList();

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean consistsOfMultipleParts() {
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getSecondaryNestedBricks() {
        return null;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_instant;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return false;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getNestedBricks() {
        return this.nestedBricks;
    }

    public boolean addBrick(Brick brick) {
        return this.nestedBricks.add(brick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        Iterator<Brick> it = this.nestedBricks.iterator();
        while (it.hasNext()) {
            it.next().setCommentedOut(commentedOut);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        InstantBrick instantBrick = (InstantBrick) super.clone();
        instantBrick.endBrick = new EndBrick(instantBrick);
        instantBrick.nestedBricks = new ArrayList();
        Iterator<Brick> it = this.nestedBricks.iterator();
        while (it.hasNext()) {
            instantBrick.addBrick(it.next().clone());
        }
        return instantBrick;
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
        Iterator<Brick> it = this.nestedBricks.iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(bricks);
        }
        bricks.add(this.endBrick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setParent(Brick parent) {
        super.setParent(parent);
        Iterator<Brick> it = this.nestedBricks.iterator();
        while (it.hasNext()) {
            it.next().setParent(this);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return this.nestedBricks;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean removeChild(Brick brick) {
        if (this.nestedBricks.remove(brick)) {
            return true;
        }
        Iterator<Brick> it = this.nestedBricks.iterator();
        while (it.hasNext()) {
            if (it.next().removeChild(brick)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(sequence.getScript());
        for (Brick brick : this.nestedBricks) {
            if (!brick.isCommentedOut()) {
                brick.addActionToSequence(sprite, scriptSequenceAction);
            }
        }
        sequence.addAction(sprite.getActionFactory().createInstantAction(sprite, sequence, scriptSequenceAction));
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        super.addRequiredResources(requiredResourcesSet);
        Iterator<Brick> it = this.nestedBricks.iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(requiredResourcesSet);
        }
    }
}
