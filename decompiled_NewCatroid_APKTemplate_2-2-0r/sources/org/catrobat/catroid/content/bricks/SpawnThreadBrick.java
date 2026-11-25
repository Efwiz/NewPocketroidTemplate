package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SpawnThreadBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SpawnThreadBrick extends FormulaBrick implements CompositeBrick {
    private static final long serialVersionUID = 1;
    private transient EndBrick endBrick;
    private List<Brick> threadBricks;

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
        return R.layout.brick_spawn_thread;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return false;
    }

    public SpawnThreadBrick() {
        this.endBrick = new EndBrick(this);
        this.threadBricks = new ArrayList();
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_spawn_thread_id_edit);
    }

    public SpawnThreadBrick(Formula threadId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, threadId);
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getNestedBricks() {
        return this.threadBricks;
    }

    public boolean addBrick(Brick brick) {
        return this.threadBricks.add(brick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        Iterator<Brick> it = this.threadBricks.iterator();
        while (it.hasNext()) {
            it.next().setCommentedOut(commentedOut);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        SpawnThreadBrick spawnThreadBrick = (SpawnThreadBrick) super.clone();
        spawnThreadBrick.endBrick = new EndBrick(spawnThreadBrick);
        spawnThreadBrick.threadBricks = new ArrayList();
        Iterator<Brick> it = this.threadBricks.iterator();
        while (it.hasNext()) {
            spawnThreadBrick.addBrick(it.next().clone());
        }
        return spawnThreadBrick;
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
        Iterator<Brick> it = this.threadBricks.iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(bricks);
        }
        bricks.add(this.endBrick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setParent(Brick parent) {
        super.setParent(parent);
        Iterator<Brick> it = this.threadBricks.iterator();
        while (it.hasNext()) {
            it.next().setParent(this);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return this.threadBricks;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean removeChild(Brick brick) {
        if (this.threadBricks.remove(brick)) {
            return true;
        }
        Iterator<Brick> it = this.threadBricks.iterator();
        while (it.hasNext()) {
            if (it.next().removeChild(brick)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSpawnThreadAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), this.threadBricks, sequence.getScript()));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        super.addRequiredResources(requiredResourcesSet);
        Iterator<Brick> it = this.threadBricks.iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(requiredResourcesSet);
        }
    }
}
