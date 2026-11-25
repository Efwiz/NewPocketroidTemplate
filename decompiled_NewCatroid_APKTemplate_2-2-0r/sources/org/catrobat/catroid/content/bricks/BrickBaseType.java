package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/BrickBaseType.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class BrickBaseType implements Brick {
    private static final long serialVersionUID = 1;
    private transient CheckBox checkbox;
    protected boolean commentedOut;
    protected transient Brick parent;
    public transient View view;
    protected UUID brickId = UUID.randomUUID();
    private transient boolean isPhantom = false;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public boolean consistsOfMultipleParts() {
        return false;
    }

    public abstract int getViewResource();

    @Override // org.catrobat.catroid.content.bricks.Brick
    public boolean hasHelpPage() {
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public boolean removeChild(Brick brick) {
        return false;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public boolean isPhantom() {
        return this.isPhantom;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void setPhantom(boolean isPhantom) {
        this.isPhantom = isPhantom;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public boolean isCommentedOut() {
        return this.commentedOut;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        this.commentedOut = commentedOut;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public CheckBox getCheckBox() {
        return this.checkbox;
    }

    @Override // 
    public Brick clone() throws CloneNotSupportedException {
        BrickBaseType brickBaseType = (BrickBaseType) super.clone();
        brickBaseType.view = null;
        brickBaseType.checkbox = null;
        brickBaseType.parent = null;
        brickBaseType.commentedOut = this.commentedOut;
        brickBaseType.brickId = UUID.randomUUID();
        return brickBaseType;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        View viewInflate = LayoutInflater.from(context).inflate(getViewResource(), (ViewGroup) null, false);
        this.view = viewInflate;
        this.checkbox = (CheckBox) viewInflate.findViewById(R.id.brick_checkbox);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public View getPrototypeView(Context context) {
        View view = getView(context);
        disableSpinners(view);
        return view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void disableSpinners() {
        disableSpinners(this.view);
    }

    private void disableSpinners(View view) {
        if (view instanceof Spinner) {
            view.setEnabled(false);
            view.setClickable(false);
            view.setFocusable(false);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                disableSpinners(viewGroup.getChildAt(i));
            }
        }
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getAllParts() {
        return Collections.singletonList(this);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addToFlatList(List<Brick> bricks) {
        bricks.add(this);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return getParent().getScript();
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public int getPositionInScript() {
        if (getParent() instanceof ScriptBrick) {
            return getScript().getBrickList().indexOf(this);
        }
        return getParent().getPositionInScript();
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public Brick getParent() {
        return this.parent;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void setParent(Brick parent) {
        this.parent = parent;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return getParent().getDragAndDropTargetList();
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public int getPositionInDragAndDropTargetList() {
        return getDragAndDropTargetList().indexOf(this);
    }

    void notifyDataSetChanged(AppCompatActivity activity) {
        ScriptFragment scriptFragment = (ScriptFragment) activity.getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG);
        if (scriptFragment != null) {
            scriptFragment.notifyDataSetChanged();
        }
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public String getHelpUrl(String category) {
        return "https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/" + getClass().getSimpleName();
    }

    protected String getPositionInformation() {
        int positionInScript;
        String simpleName;
        if (getParent() == null) {
            positionInScript = -1;
            simpleName = "unknown";
        } else {
            positionInScript = getPositionInScript();
            simpleName = getScript().getClass().getSimpleName();
        }
        return "Brick at position " + (positionInScript + 2) + "\nin \"" + simpleName + "\"";
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public UUID getBrickID() {
        return this.brickId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.catrobat.catroid.content.bricks.Brick
    public List<Brick> findBricksInNestedBricks(List<UUID> brickIds) {
        List<Brick> listFindBricksInNestedBricks;
        List<Brick> listFindBricksInNestedBricks2;
        if (!(this instanceof CompositeBrick)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        CompositeBrick compositeBrick = (CompositeBrick) this;
        for (Brick brick : compositeBrick.getNestedBricks()) {
            if (brickIds.contains(brick.getBrickID())) {
                arrayList.add(brick);
            } else if ((brick instanceof CompositeBrick) && (listFindBricksInNestedBricks2 = brick.findBricksInNestedBricks(brickIds)) != null) {
                return listFindBricksInNestedBricks2;
            }
            if (brickIds.size() == arrayList.size()) {
                break;
            }
        }
        if (arrayList.size() == 0 && compositeBrick.hasSecondaryList()) {
            for (Brick brick2 : compositeBrick.getSecondaryNestedBricks()) {
                if (brickIds.contains(brick2.getBrickID())) {
                    arrayList.add(brick2);
                } else if ((brick2 instanceof CompositeBrick) && (listFindBricksInNestedBricks = brick2.findBricksInNestedBricks(brickIds)) != null) {
                    return listFindBricksInNestedBricks;
                }
                if (brickIds.size() == arrayList.size()) {
                    break;
                }
            }
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.catrobat.catroid.content.bricks.Brick
    public boolean addBrickInNestedBrick(UUID parentBrickId, int subStackIndex, List<Brick> bricksToAdd) {
        if (!(this instanceof CompositeBrick)) {
            return false;
        }
        CompositeBrick compositeBrick = (CompositeBrick) this;
        if (getBrickID().equals(parentBrickId)) {
            if (subStackIndex == 0) {
                compositeBrick.getNestedBricks().addAll(0, bricksToAdd);
                return true;
            }
            if (subStackIndex == 1 && compositeBrick.hasSecondaryList()) {
                compositeBrick.getSecondaryNestedBricks().addAll(0, bricksToAdd);
                return true;
            }
        }
        int i = 0;
        for (Brick brick : compositeBrick.getNestedBricks()) {
            i++;
            if (subStackIndex == -1 && brick.getBrickID().equals(parentBrickId)) {
                compositeBrick.getNestedBricks().addAll(i, bricksToAdd);
            } else if ((brick instanceof CompositeBrick) && brick.addBrickInNestedBrick(parentBrickId, subStackIndex, bricksToAdd)) {
                return true;
            }
        }
        if (!compositeBrick.hasSecondaryList()) {
            return false;
        }
        int i2 = 0;
        for (Brick brick2 : compositeBrick.getSecondaryNestedBricks()) {
            i2++;
            if (subStackIndex == -1 && brick2.getBrickID().equals(parentBrickId)) {
                compositeBrick.getSecondaryNestedBricks().addAll(i2, bricksToAdd);
                return true;
            }
            if ((brick2 instanceof CompositeBrick) && brick2.addBrickInNestedBrick(parentBrickId, subStackIndex, bricksToAdd)) {
                return true;
            }
        }
        return false;
    }
}
