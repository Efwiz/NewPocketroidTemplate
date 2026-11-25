package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.AdapterViewOnItemSelectedListenerImpl;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroIfLogicBeginBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroIfLogicBeginBrick extends BrickBaseType implements CompositeBrick {
    private static final long serialVersionUID = 1;
    private int sensorSpinnerPosition = 0;
    private transient ElseBrick elseBrick = new ElseBrick(this);
    private transient EndBrick endBrick = new EndBrick(this, R.layout.brick_if_end_if);
    private List<Brick> ifBranchBricks = new ArrayList();
    private List<Brick> elseBranchBricks = new ArrayList();

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean consistsOfMultipleParts() {
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_phiro_if_sensor;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return true;
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

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        PhiroIfLogicBeginBrick phiroIfLogicBeginBrick = (PhiroIfLogicBeginBrick) super.clone();
        phiroIfLogicBeginBrick.elseBrick = new ElseBrick(phiroIfLogicBeginBrick);
        phiroIfLogicBeginBrick.endBrick = new EndBrick(phiroIfLogicBeginBrick, R.layout.brick_if_end_if);
        phiroIfLogicBeginBrick.ifBranchBricks = new ArrayList();
        phiroIfLogicBeginBrick.elseBranchBricks = new ArrayList();
        Iterator<Brick> it = this.ifBranchBricks.iterator();
        while (it.hasNext()) {
            phiroIfLogicBeginBrick.addBrickToIfBranch(it.next().clone());
        }
        Iterator<Brick> it2 = this.elseBranchBricks.iterator();
        while (it2.hasNext()) {
            phiroIfLogicBeginBrick.addBrickToElseBranch(it2.next().clone());
        }
        return phiroIfLogicBeginBrick;
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
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_phiro_sensor_action_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(this.view.getContext(), R.array.brick_phiro_select_sensor_spinner, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.sensorSpinnerPosition);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.PhiroIfLogicBeginBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4945xf1f7fc2b((Integer) obj);
            }
        }));
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-PhiroIfLogicBeginBrick, reason: not valid java name */
    /* synthetic */ Unit m4945xf1f7fc2b(Integer num) {
        this.sensorSpinnerPosition = num.intValue();
        return Unit.INSTANCE;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 10);
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
        sequence.addAction(sprite.getActionFactory().createPhiroSendSelectedSensorAction(sprite, sequence, this.sensorSpinnerPosition, scriptSequenceAction, scriptSequenceAction2));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroIfLogicBeginBrick$ElseBrick.class
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

        ElseBrick(PhiroIfLogicBeginBrick ifBrick) {
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
            return ((PhiroIfLogicBeginBrick) this.parent).elseBranchBricks;
        }
    }
}
