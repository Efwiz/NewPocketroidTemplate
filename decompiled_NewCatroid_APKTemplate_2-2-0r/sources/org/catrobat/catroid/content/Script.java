package org.catrobat.catroid.content;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.ListSelectorBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.UserData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/Script.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class Script implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;

    @XStreamAsAttribute
    protected float posX;

    @XStreamAsAttribute
    protected float posY;
    protected transient ScriptBrick scriptBrick;
    protected List<Brick> brickList = new ArrayList();
    protected boolean commentedOut = false;
    protected UUID scriptId = UUID.randomUUID();

    public abstract EventId createEventId(Sprite sprite);

    public abstract ScriptBrick getScriptBrick();

    public List<Brick> getBrickList() {
        return this.brickList;
    }

    @Override // 
    public Script clone() throws CloneNotSupportedException {
        Script script = (Script) super.clone();
        script.brickList = new ArrayList();
        Iterator<Brick> it = this.brickList.iterator();
        while (it.hasNext()) {
            script.brickList.add(it.next().clone());
        }
        script.commentedOut = this.commentedOut;
        script.scriptBrick = null;
        script.posX = this.posX;
        script.posY = this.posY;
        script.scriptId = UUID.randomUUID();
        return script;
    }

    public float getPosX() {
        return this.posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return this.posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public UUID getScriptId() {
        return this.scriptId;
    }

    public boolean isCommentedOut() {
        return this.commentedOut;
    }

    public void setCommentedOut(boolean commentedOut) {
        this.commentedOut = commentedOut;
        Iterator<Brick> it = this.brickList.iterator();
        while (it.hasNext()) {
            it.next().setCommentedOut(commentedOut);
        }
    }

    public void setParents() {
        ScriptBrick scriptBrick = getScriptBrick();
        if (scriptBrick != null) {
            scriptBrick.setParent(null);
        }
        if (getBrickList() != null) {
            for (Brick brick : getBrickList()) {
                if (brick != null) {
                    brick.setParent(scriptBrick);
                }
            }
        }
    }

    public void setScriptBrick(ScriptBrick scriptBrick) {
        this.scriptBrick = scriptBrick;
    }

    public void run(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.commentedOut) {
            return;
        }
        for (Brick brick : this.brickList) {
            if (brick != null && !brick.isCommentedOut()) {
                brick.addActionToSequence(sprite, sequence);
            }
        }
    }

    public boolean addBrick(Brick brick) {
        return this.brickList.add(brick);
    }

    public void addBrick(int position, Brick brick) {
        this.brickList.add(position, brick);
    }

    public void addToFlatList(List<Brick> bricks) {
        ScriptBrick scriptBrick = getScriptBrick();
        if (scriptBrick != null) {
            bricks.add(scriptBrick);
        }
        for (Brick brick : this.brickList) {
            if (brick != null) {
                brick.addToFlatList(bricks);
            }
        }
    }

    public Brick getBrick(int index) {
        return this.brickList.get(index);
    }

    public boolean removeBrick(Brick brick) {
        if (this.brickList.remove(brick)) {
            return true;
        }
        for (Brick brick2 : this.brickList) {
            if (brick2 != null && brick2.removeChild(brick)) {
                return true;
            }
        }
        return false;
    }

    public void removeAllOccurrencesOfUserDefinedBrick(List<Brick> brickList, UserDefinedBrick userDefinedBrick) {
        int i = 0;
        while (i < brickList.size()) {
            Brick brick = brickList.get(i);
            if (brick instanceof CompositeBrick) {
                CompositeBrick compositeBrick = (CompositeBrick) brick;
                removeAllOccurrencesOfUserDefinedBrick(compositeBrick.getNestedBricks(), userDefinedBrick);
                if (compositeBrick.hasSecondaryList()) {
                    removeAllOccurrencesOfUserDefinedBrick(compositeBrick.getSecondaryNestedBricks(), userDefinedBrick);
                }
            }
            if ((brick instanceof UserDefinedBrick) && userDefinedBrick.isUserDefinedBrickDataEqual(brick)) {
                brickList.remove(i);
                i--;
            }
            i++;
        }
    }

    public void addRequiredResources(final Brick.ResourcesSet resourcesSet) {
        for (Brick brick : this.brickList) {
            if (brick != null && !brick.isCommentedOut()) {
                brick.addRequiredResources(resourcesSet);
            }
        }
    }

    public void updateUserDataReferences(String oldName, String newName, UserData<?> item) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        addToFlatList(arrayList);
        Iterator<Brick> it = arrayList.iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next() instanceof ListSelectorBrick) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        for (Brick brick : arrayList) {
            if (brick instanceof FormulaBrick) {
                ((FormulaBrick) brick).updateUserDataReference(oldName, newName, item, z);
            }
        }
    }

    public void deselectElements(List<UserData<?>> elements) {
        ArrayList arrayList = new ArrayList();
        addToFlatList(arrayList);
        for (Brick brick : arrayList) {
            if (brick instanceof ListSelectorBrick) {
                ((ListSelectorBrick) brick).deselectElements(elements);
            }
        }
    }

    public List<Brick> findBricksInScript(List<UUID> brickIds) {
        List<Brick> listFindBricksInNestedBricks;
        if (brickIds == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Brick brick : this.brickList) {
            if (brick != null) {
                if (brickIds.contains(brick.getBrickID())) {
                    arrayList.add(brick);
                } else if ((brick instanceof CompositeBrick) && (listFindBricksInNestedBricks = brick.findBricksInNestedBricks(brickIds)) != null) {
                    return listFindBricksInNestedBricks;
                }
                if (arrayList.size() == brickIds.size()) {
                    return arrayList;
                }
            }
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    public List<Brick> removeBricksFromScript(List<UUID> brickIds) {
        List<Brick> listFindBricksInScript = findBricksInScript(brickIds);
        if (listFindBricksInScript != null) {
            Iterator<Brick> it = listFindBricksInScript.iterator();
            while (it.hasNext()) {
                removeBrick(it.next());
            }
        }
        return listFindBricksInScript;
    }

    public boolean insertBrickAfter(UUID parentId, int subStackIndex, List<Brick> bricksToAdd) {
        boolean z;
        if (subStackIndex == -1) {
            if (this.scriptId.equals(parentId) || getScriptBrick().getBrickID().equals(parentId)) {
                this.brickList.addAll(0, bricksToAdd);
                return true;
            }
            Iterator<Brick> it = this.brickList.iterator();
            int i = 0;
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                i++;
                if (it.next().getBrickID().equals(parentId)) {
                    z = true;
                    break;
                }
            }
            if (z) {
                this.brickList.addAll(i, bricksToAdd);
                return true;
            }
        }
        for (Brick brick : this.brickList) {
            if (brick != null && brick.addBrickInNestedBrick(parentId, subStackIndex, bricksToAdd)) {
                return true;
            }
        }
        return false;
    }
}
