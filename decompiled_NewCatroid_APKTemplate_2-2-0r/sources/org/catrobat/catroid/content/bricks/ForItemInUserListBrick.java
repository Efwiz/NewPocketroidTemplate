package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.utils.LoopUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ForItemInUserListBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ForItemInUserListBrick extends UserDataBrick implements CompositeBrick {
    private transient EndBrick endBrick = new EndBrick(this);
    private List<Brick> loopBricks = new ArrayList();

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
        return R.layout.brick_for_item_in_userlist;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return false;
    }

    public ForItemInUserListBrick() {
        addAllowedBrickData(Brick.BrickData.FOR_ITEM_IN_USERLIST_LIST, R.id.for_item_in_userlist_list_spinner);
        addAllowedBrickData(Brick.BrickData.FOR_ITEM_IN_USERLIST_VARIABLE, R.id.for_item_in_userlist_variable_spinner);
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

    @Override // org.catrobat.catroid.content.bricks.UserDataBrick, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        ForItemInUserListBrick forItemInUserListBrick = (ForItemInUserListBrick) super.clone();
        forItemInUserListBrick.endBrick = new EndBrick(forItemInUserListBrick);
        forItemInUserListBrick.loopBricks = new ArrayList();
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            forItemInUserListBrick.addBrick(it.next().clone());
        }
        return forItemInUserListBrick;
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
        UserVariable userVariableWithBrickData = getUserVariableWithBrickData(Brick.BrickData.FOR_ITEM_IN_USERLIST_VARIABLE);
        UserList userListWithBrickData = getUserListWithBrickData(Brick.BrickData.FOR_ITEM_IN_USERLIST_LIST);
        boolean zCheckLoopBrickForLoopDelay = LoopUtil.checkLoopBrickForLoopDelay(this, sequence.getScript());
        if (userVariableWithBrickData == null || userVariableWithBrickData.getName() == null) {
            userVariableWithBrickData = new UserVariable("NoVariableSet", CatroidApplication.getAppContext().getString(R.string.no_variable_selected));
            userVariableWithBrickData.setDummy(true);
        }
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(sequence.getScript());
        for (Brick brick : this.loopBricks) {
            if (!brick.isCommentedOut()) {
                brick.addActionToSequence(sprite, scriptSequenceAction);
            }
        }
        sequence.addAction(sprite.getActionFactory().createForItemInUserListAction(userListWithBrickData, userVariableWithBrickData, scriptSequenceAction, zCheckLoopBrickForLoopDelay));
    }
}
