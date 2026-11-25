package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TryCatchFinallyBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TryCatchFinallyBrick extends BrickBaseType implements CompositeBrick {
    private static final long serialVersionUID = 1;
    private transient CatchBrick catchBrickPart = new CatchBrick(this);
    private transient FinallyBrick finallyBrickPart = new FinallyBrick(this);
    private transient EndBrick endBrick = new EndBrick(this);
    protected List<Brick> tryBricks = new ArrayList();
    protected List<Brick> catchBricks = new ArrayList();
    protected List<Brick> finallyBricks = new ArrayList();

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean consistsOfMultipleParts() {
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_try;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction scriptSequenceAction = new ScriptSequenceAction(sequence.getScript());
        for (Brick brick : this.tryBricks) {
            if (!brick.isCommentedOut()) {
                brick.addActionToSequence(sprite, scriptSequenceAction);
            }
        }
        ScriptSequenceAction scriptSequenceAction2 = new ScriptSequenceAction(sequence.getScript());
        for (Brick brick2 : this.catchBricks) {
            if (!brick2.isCommentedOut()) {
                brick2.addActionToSequence(sprite, scriptSequenceAction2);
            }
        }
        ScriptSequenceAction scriptSequenceAction3 = new ScriptSequenceAction(sequence.getScript());
        for (Brick brick3 : this.finallyBricks) {
            if (!brick3.isCommentedOut()) {
                brick3.addActionToSequence(sprite, scriptSequenceAction3);
            }
        }
        sequence.addAction(sprite.getActionFactory().createTryCatchFinallyAction(scriptSequenceAction, scriptSequenceAction2, scriptSequenceAction3, this.catchBrickPart.userVariable));
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return this.tryBricks;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getNestedBricks() {
        return this.tryBricks;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getSecondaryNestedBricks() {
        return this.catchBricks;
    }

    public List<Brick> getThirdNestedBricks() {
        return this.finallyBricks;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getAllParts() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this);
        arrayList.add(this.catchBrickPart);
        arrayList.add(this.finallyBrickPart);
        arrayList.add(this.endBrick);
        return arrayList;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addToFlatList(List<Brick> bricks) {
        super.addToFlatList(bricks);
        Iterator<Brick> it = this.tryBricks.iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(bricks);
        }
        bricks.add(this.catchBrickPart);
        Iterator<Brick> it2 = this.catchBricks.iterator();
        while (it2.hasNext()) {
            it2.next().addToFlatList(bricks);
        }
        bricks.add(this.finallyBrickPart);
        Iterator<Brick> it3 = this.finallyBricks.iterator();
        while (it3.hasNext()) {
            it3.next().addToFlatList(bricks);
        }
        bricks.add(this.endBrick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setParent(Brick parent) {
        super.setParent(parent);
        Iterator<Brick> it = this.tryBricks.iterator();
        while (it.hasNext()) {
            it.next().setParent(this);
        }
        Iterator<Brick> it2 = this.catchBricks.iterator();
        while (it2.hasNext()) {
            it2.next().setParent(this.catchBrickPart);
        }
        Iterator<Brick> it3 = this.finallyBricks.iterator();
        while (it3.hasNext()) {
            it3.next().setParent(this.finallyBrickPart);
        }
        this.endBrick.setParent(this);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean removeChild(Brick brick) {
        if (this.tryBricks.remove(brick) || this.catchBricks.remove(brick) || this.finallyBricks.remove(brick)) {
            return true;
        }
        Iterator<Brick> it = this.tryBricks.iterator();
        while (it.hasNext()) {
            if (it.next().removeChild(brick)) {
                return true;
            }
        }
        Iterator<Brick> it2 = this.catchBricks.iterator();
        while (it2.hasNext()) {
            if (it2.next().removeChild(brick)) {
                return true;
            }
        }
        Iterator<Brick> it3 = this.finallyBricks.iterator();
        while (it3.hasNext()) {
            if (it3.next().removeChild(brick)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        TryCatchFinallyBrick tryCatchFinallyBrick = (TryCatchFinallyBrick) super.clone();
        tryCatchFinallyBrick.catchBrickPart = new CatchBrick(tryCatchFinallyBrick);
        tryCatchFinallyBrick.finallyBrickPart = new FinallyBrick(tryCatchFinallyBrick);
        tryCatchFinallyBrick.endBrick = new EndBrick(tryCatchFinallyBrick);
        tryCatchFinallyBrick.tryBricks = new ArrayList();
        Iterator<Brick> it = this.tryBricks.iterator();
        while (it.hasNext()) {
            tryCatchFinallyBrick.tryBricks.add(it.next().clone());
        }
        tryCatchFinallyBrick.catchBricks = new ArrayList();
        Iterator<Brick> it2 = this.catchBricks.iterator();
        while (it2.hasNext()) {
            tryCatchFinallyBrick.catchBricks.add(it2.next().clone());
        }
        tryCatchFinallyBrick.finallyBricks = new ArrayList();
        Iterator<Brick> it3 = this.finallyBricks.iterator();
        while (it3.hasNext()) {
            tryCatchFinallyBrick.finallyBricks.add(it3.next().clone());
        }
        tryCatchFinallyBrick.catchBrickPart.userVariable = this.catchBrickPart.userVariable;
        return tryCatchFinallyBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        Iterator<Brick> it = this.tryBricks.iterator();
        while (it.hasNext()) {
            it.next().setCommentedOut(commentedOut);
        }
        Iterator<Brick> it2 = this.catchBricks.iterator();
        while (it2.hasNext()) {
            it2.next().setCommentedOut(commentedOut);
        }
        Iterator<Brick> it3 = this.finallyBricks.iterator();
        while (it3.hasNext()) {
            it3.next().setCommentedOut(commentedOut);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(Brick.ResourcesSet requiredResourcesSet) {
        super.addRequiredResources(requiredResourcesSet);
        Iterator<Brick> it = this.tryBricks.iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(requiredResourcesSet);
        }
        Iterator<Brick> it2 = this.catchBricks.iterator();
        while (it2.hasNext()) {
            it2.next().addRequiredResources(requiredResourcesSet);
        }
        Iterator<Brick> it3 = this.finallyBricks.iterator();
        while (it3.hasNext()) {
            it3.next().addRequiredResources(requiredResourcesSet);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TryCatchFinallyBrick$CatchBrick.class
      classes14.dex
     */
    public class CatchBrick extends UserVariableBrick {
        @Override // org.catrobat.catroid.content.bricks.Brick
        public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public boolean consistsOfMultipleParts() {
            return true;
        }

        @Override // org.catrobat.catroid.content.bricks.UserVariableBrick
        protected int getSpinnerId() {
            return R.id.catch_error_variable_spinner;
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType
        public int getViewResource() {
            return R.layout.brick_catch;
        }

        CatchBrick(TryCatchFinallyBrick parent) {
            this.parent = parent;
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public List<Brick> getDragAndDropTargetList() {
            return ((TryCatchFinallyBrick) this.parent).catchBricks;
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
        public UUID getBrickID() {
            return this.parent.getBrickID();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TryCatchFinallyBrick$FinallyBrick.class
      classes14.dex
     */
    public class FinallyBrick extends BrickBaseType {
        @Override // org.catrobat.catroid.content.bricks.Brick
        public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public boolean consistsOfMultipleParts() {
            return true;
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType
        public int getViewResource() {
            return R.layout.brick_finally;
        }

        FinallyBrick(TryCatchFinallyBrick parent) {
            this.parent = parent;
        }

        @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
        public List<Brick> getDragAndDropTargetList() {
            return ((TryCatchFinallyBrick) this.parent).finallyBricks;
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
        public UUID getBrickID() {
            return this.parent.getBrickID();
        }
    }
}
