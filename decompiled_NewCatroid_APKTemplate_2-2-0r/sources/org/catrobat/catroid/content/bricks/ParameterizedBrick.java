package org.catrobat.catroid.content.bricks;

import android.widget.TextView;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.ParameterizedData;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.utils.LoopUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ParameterizedBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: ParameterizedBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\bJ\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0016\u0010\u001f\u001a\u00020\u00142\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\b\u0010!\u001a\u00020\bH\u0016J\b\u0010\"\u001a\u00020\u001aH\u0016J\u001a\u0010#\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020'0%0$H\u0002J\b\u0010(\u001a\u00020\u0014H\u0002J\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020\b0$H\u0016J\u000e\u0010*\u001a\b\u0012\u0004\u0012\u00020\b0$H\u0016J\u000e\u0010+\u001a\b\u0012\u0004\u0012\u00020\b0$H\u0016J\u0010\u0010,\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010$H\u0016J\b\u0010-\u001a\u00020\u0010H\u0016J\b\u0010.\u001a\u00020\u001aH\u0016J\u0016\u0010/\u001a\u00020\u00142\f\u00100\u001a\b\u0012\u0004\u0012\u00020&0$H\u0016J\u0010\u00101\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\bH\u0016J\u0010\u00102\u001a\u00020\u00142\u0006\u00103\u001a\u00020\u001aH\u0016J\u0010\u00104\u001a\u00020\u00142\u0006\u00105\u001a\u00020\bH\u0016J\b\u00106\u001a\u00020\u0014H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u00067"}, d2 = {"Lorg/catrobat/catroid/content/bricks/ParameterizedBrick;", "Lorg/catrobat/catroid/content/bricks/ListSelectorBrick;", "Lorg/catrobat/catroid/content/bricks/CompositeBrick;", "()V", "endBrick", "Lorg/catrobat/catroid/content/bricks/ParameterizedEndBrick;", "loopBricks", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "parameterizedData", "Lorg/catrobat/catroid/common/ParameterizedData;", "getParameterizedData", "()Lorg/catrobat/catroid/common/ParameterizedData;", "setParameterizedData", "(Lorg/catrobat/catroid/common/ParameterizedData;)V", "selectorId", "", "getSelectorId", "()I", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "addBrick", "", "brick", "addRequiredResources", "requiredResourcesSet", "Lorg/catrobat/catroid/content/bricks/Brick$ResourcesSet;", "addToFlatList", "bricks", "clone", "consistsOfMultipleParts", "createLinkedPair", "", "Lkotlin/Pair;", "Lorg/catrobat/catroid/formulaeditor/UserList;", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "createLinkedVariables", "getAllParts", "getDragAndDropTargetList", "getNestedBricks", "getSecondaryNestedBricks", "getViewResource", "hasSecondaryList", "onUserListSelected", "userLists", "removeChild", "setCommentedOut", "commentedOut", "setParent", "parent", "updateSelectorText", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ParameterizedBrick extends ListSelectorBrick implements CompositeBrick {
    private List<Brick> loopBricks = new ArrayList();
    private ParameterizedEndBrick endBrick = new ParameterizedEndBrick(this);

    @XStreamOmitField
    private transient ParameterizedData parameterizedData = new ParameterizedData(0, 0, null, null, null, 31, null);

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean consistsOfMultipleParts() {
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getSecondaryNestedBricks() {
        return null;
    }

    @Override // org.catrobat.catroid.content.bricks.ListSelectorBrick
    protected int getSelectorId() {
        return R.id.brick_param_list_of_list_text;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_parameterized_input;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public boolean hasSecondaryList() {
        return false;
    }

    public final ParameterizedData getParameterizedData() {
        return this.parameterizedData;
    }

    public final void setParameterizedData(ParameterizedData parameterizedData) {
        Intrinsics.checkNotNullParameter(parameterizedData, "<set-?>");
        this.parameterizedData = parameterizedData;
    }

    @Override // org.catrobat.catroid.content.bricks.CompositeBrick
    public List<Brick> getNestedBricks() {
        return this.loopBricks;
    }

    public final boolean addBrick(Brick brick) {
        Intrinsics.checkNotNullParameter(brick, "brick");
        return this.loopBricks.add(brick);
    }

    @Override // org.catrobat.catroid.content.bricks.ListSelectorBrick
    protected void updateSelectorText() {
        super.updateSelectorText();
        int size = getUserLists().size();
        ((TextView) this.view.findViewById(R.id.brick_param_first_label)).setText(this.view.getResources().getQuantityString(R.plurals.brick_parameterized_foreach_plural, size, Integer.valueOf(size)));
        ((TextView) this.view.findViewById(R.id.brick_param_second_label)).setText(this.view.getResources().getQuantityString(R.plurals.brick_parameterized_stored_plural, size, Integer.valueOf(size)));
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            it.next().setCommentedOut(commentedOut);
        }
        this.endBrick.setCommentedOut(commentedOut);
    }

    @Override // org.catrobat.catroid.content.bricks.ListSelectorBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        Brick brickClone = super.clone();
        Intrinsics.checkNotNull(brickClone, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.ParameterizedBrick");
        ParameterizedBrick parameterizedBrick = (ParameterizedBrick) brickClone;
        parameterizedBrick.endBrick = new ParameterizedEndBrick(parameterizedBrick);
        parameterizedBrick.loopBricks = new ArrayList();
        parameterizedBrick.parameterizedData = new ParameterizedData(0, 0, null, null, null, 31, null);
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            Brick brickClone2 = it.next().clone();
            Intrinsics.checkNotNullExpressionValue(brickClone2, "clone(...)");
            parameterizedBrick.addBrick(brickClone2);
        }
        return parameterizedBrick;
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
        Intrinsics.checkNotNullParameter(bricks, "bricks");
        super.addToFlatList(bricks);
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(bricks);
        }
        bricks.add(this.endBrick);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setParent(Brick parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        super.setParent(parent);
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            it.next().setParent(this);
        }
        this.endBrick.parent = this;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return this.loopBricks;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean removeChild(Brick brick) {
        Intrinsics.checkNotNullParameter(brick, "brick");
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
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        Action actionCreateScriptSequenceAction = ActionFactory.createScriptSequenceAction(sequence.getScript());
        Intrinsics.checkNotNull(actionCreateScriptSequenceAction, "null cannot be cast to non-null type org.catrobat.catroid.content.actions.ScriptSequenceAction");
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) actionCreateScriptSequenceAction;
        Script script = sequence.getScript();
        Intrinsics.checkNotNullExpressionValue(script, "getScript(...)");
        boolean zCheckLoopBrickForLoopDelay = LoopUtil.checkLoopBrickForLoopDelay(this, script);
        List<Brick> list = this.loopBricks;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!((Brick) obj).isCommentedOut()) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Brick) it.next()).addActionToSequence(sprite, scriptSequenceAction);
        }
        this.endBrick.addActionToSequence(sprite, scriptSequenceAction);
        ParameterizedData parameterizedData = this.parameterizedData;
        if (parameterizedData != null) {
            parameterizedData.reset();
        }
        sequence.addAction(sprite.getActionFactory().createRepeatParameterizedAction(sprite, this.parameterizedData, createLinkedPair(), getPositionInformation(), scriptSequenceAction, zCheckLoopBrickForLoopDelay));
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(Brick.ResourcesSet requiredResourcesSet) {
        Intrinsics.checkNotNullParameter(requiredResourcesSet, "requiredResourcesSet");
        super.addRequiredResources(requiredResourcesSet);
        Iterator<Brick> it = this.loopBricks.iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(requiredResourcesSet);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.ListSelectorBrick, org.catrobat.catroid.ui.recyclerview.fragment.ListSelectorFragment.ListSelectorInterface
    public void onUserListSelected(List<? extends UserList> userLists) {
        Intrinsics.checkNotNullParameter(userLists, "userLists");
        super.onUserListSelected(userLists);
        createLinkedVariables();
    }

    private final void createLinkedVariables() {
        ProjectManager projectManager = ProjectManager.getInstance();
        Project currentProject = projectManager.getCurrentProject();
        Sprite currentSprite = projectManager.getCurrentSprite();
        List<UserList> userLists = currentProject.getUserLists();
        if (userLists == null) {
            userLists = CollectionsKt.emptyList();
        }
        List<UserVariable> userVariables = currentProject.getUserVariables();
        if (userVariables == null) {
            userVariables = CollectionsKt.emptyList();
        }
        List<UserVariable> userVariables2 = currentSprite != null ? currentSprite.getUserVariables() : null;
        if (userVariables2 == null) {
            userVariables2 = CollectionsKt.emptyList();
        }
        for (UserList userList : getUserLists()) {
            List<UserVariable> list = userVariables;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (Intrinsics.areEqual(((UserVariable) it.next()).getName(), userList.getName())) {
                        }
                    }
                }
            }
            List<UserVariable> list2 = userVariables2;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator<T> it2 = list2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (Intrinsics.areEqual(((UserVariable) it2.next()).getName(), userList.getName())) {
                        }
                    }
                }
            }
            if (userLists.contains(userList)) {
                currentProject.addUserVariable(new UserVariable(userList.getName()));
            } else {
                currentSprite.addUserVariable(new UserVariable(userList.getName()));
            }
        }
    }

    private final List<Pair<UserList, UserVariable>> createLinkedPair() {
        UserVariable userVariable;
        ArrayList arrayList = new ArrayList();
        ProjectManager projectManager = ProjectManager.getInstance();
        Project currentProject = projectManager.getCurrentProject();
        Sprite currentSprite = projectManager.getCurrentSprite();
        for (UserList userList : getUserLists()) {
            if (currentProject == null || (userVariable = currentProject.getUserVariable(userList.getName())) == null) {
                userVariable = currentSprite != null ? currentSprite.getUserVariable(userList.getName()) : null;
                if (userVariable == null) {
                }
            }
            arrayList.add(new Pair(userList, userVariable));
        }
        return arrayList;
    }
}
