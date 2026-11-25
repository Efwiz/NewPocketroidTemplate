package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.utils.LoopUtil;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RepeatBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RepeatBrick extends FormulaBrick implements CompositeBrick {
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

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_repeat;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return false;
    }

    public RepeatBrick() {
        this.endBrick = new EndBrick(this);
        this.loopBricks = new ArrayList();
        addAllowedBrickField(Brick.BrickField.TIMES_TO_REPEAT, R.id.brick_repeat_edit_text);
    }

    public RepeatBrick(Formula condition) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TIMES_TO_REPEAT, condition);
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

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        RepeatBrick repeatBrick = (RepeatBrick) super.clone();
        repeatBrick.endBrick = new EndBrick(repeatBrick);
        repeatBrick.loopBricks = new ArrayList();
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            repeatBrick.addBrick(it.next().clone());
        }
        return repeatBrick;
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

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        TextView textView = (TextView) this.view.findViewById(R.id.brick_repeat_time_text_view);
        if (getFormulaWithBrickField(Brick.BrickField.TIMES_TO_REPEAT).isNumber()) {
            try {
                ProjectManager projectManager = ProjectManager.getInstance();
                textView.setText(this.view.getResources().getQuantityString(R.plurals.time_plural, Utils.convertDoubleToPluralInteger(getFormulaWithBrickField(Brick.BrickField.TIMES_TO_REPEAT).interpretDouble(new Scope(projectManager.getCurrentProject(), projectManager.getCurrentSprite(), null)).doubleValue())));
            } catch (InterpretationException e) {
                Log.d(getClass().getSimpleName(), "Couldn't interpret Formula", e);
            }
        } else {
            textView.setText(this.view.getResources().getQuantityString(R.plurals.time_plural, Utils.TRANSLATION_PLURAL_OTHER_INTEGER));
        }
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(sequence.getScript());
        boolean zCheckLoopBrickForLoopDelay = LoopUtil.checkLoopBrickForLoopDelay(this, sequence.getScript());
        for (Brick brick : this.loopBricks) {
            if (!brick.isCommentedOut()) {
                brick.addActionToSequence(sprite, scriptSequenceAction);
            }
        }
        sequence.addAction(sprite.getActionFactory().createRepeatAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TIMES_TO_REPEAT), scriptSequenceAction, zCheckLoopBrickForLoopDelay));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        super.addRequiredResources(requiredResourcesSet);
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(requiredResourcesSet);
        }
    }
}
