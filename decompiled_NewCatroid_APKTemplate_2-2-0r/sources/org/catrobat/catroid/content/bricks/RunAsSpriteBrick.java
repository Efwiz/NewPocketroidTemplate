package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RunAsSpriteBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias("RunAsSpriteBrick")
public class RunAsSpriteBrick extends FormulaBrick implements CompositeBrick {
    private static final long serialVersionUID = 1;
    private transient EndBrick endBrick;
    private List<Brick> nestedBricks;

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
        return R.layout.brick_run_as_sprite;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return false;
    }

    public RunAsSpriteBrick() {
        this.endBrick = new EndBrick(this);
        this.nestedBricks = new ArrayList();
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_run_as_sprite_name);
    }

    public RunAsSpriteBrick(Formula spriteName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, spriteName);
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

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        RunAsSpriteBrick runAsSpriteBrick = (RunAsSpriteBrick) super.clone();
        runAsSpriteBrick.endBrick = new EndBrick(runAsSpriteBrick);
        runAsSpriteBrick.nestedBricks = new ArrayList();
        Iterator<Brick> it = this.nestedBricks.iterator();
        while (it.hasNext()) {
            runAsSpriteBrick.addBrick(it.next().clone());
        }
        return runAsSpriteBrick;
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

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        return super.getView(context);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRunAsSpriteAction(sprite, sequence, sequence.getScript(), getFormulaWithBrickField(Brick.BrickField.NAME), this.nestedBricks));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        super.addRequiredResources(requiredResourcesSet);
        Iterator<Brick> it = this.nestedBricks.iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(requiredResourcesSet);
        }
    }
}
