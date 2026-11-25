package org.catrobat.catroid.content;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastMessageBrick;
import org.catrobat.catroid.content.bricks.CloneAndNameBrick;
import org.catrobat.catroid.content.bricks.CloneBrick;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.io.XStreamFieldKeyOrder;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.utils.FileMetaDataExtractor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/Scene.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias("scene")
@XStreamFieldKeyOrder({"sceneId", "name", "objectList"})
public class Scene implements Nameable, Serializable {
    private static final long serialVersionUID = 1;
    public transient boolean firstStart;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    @XStreamAlias("name")
    private String f518name;
    private transient PhysicsWorld physicsWorld;
    private transient Project project;

    @XStreamAsAttribute
    private String sceneId;

    @XStreamAlias("objectList")
    private List<Sprite> spriteList;

    public Scene() {
        this.spriteList = new ArrayList();
        this.firstStart = true;
    }

    public Scene(String name2, Project project) {
        this.spriteList = new ArrayList();
        this.firstStart = true;
        this.f518name = name2;
        this.project = project;
        this.sceneId = UUID.randomUUID().toString();
    }

    public String getSceneId() {
        if (this.sceneId == null) {
            this.sceneId = UUID.randomUUID().toString();
        }
        return this.sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(getSceneId(), ((Scene) o).getSceneId());
    }

    public int hashCode() {
        return Objects.hash(getSceneId());
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f518name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public synchronized void setName(String name2) {
        this.f518name = name2;
    }

    public Project getProject() {
        return this.project;
    }

    public synchronized void setProject(Project project) {
        this.project = project;
    }

    public List<Sprite> getSpriteList() {
        return this.spriteList;
    }

    public Sprite getSprite(String spriteName) {
        for (Sprite sprite : this.spriteList) {
            if (spriteName.equals(sprite.getName())) {
                return sprite;
            }
        }
        return null;
    }

    public Sprite getSpriteAll(String spriteName) {
        for (Sprite sprite : this.project.getSpriteListWithClones()) {
            if (spriteName.equals(sprite.getName())) {
                return sprite;
            }
        }
        return null;
    }

    public Sprite getBackgroundSprite() {
        if (this.spriteList.size() > 0) {
            return this.spriteList.get(0);
        }
        return null;
    }

    public synchronized void addSprite(Sprite sprite) {
        if (this.spriteList.contains(sprite)) {
            return;
        }
        this.spriteList.add(sprite);
    }

    public synchronized boolean removeSprite(Sprite sprite) {
        return this.spriteList.remove(sprite);
    }

    public PhysicsWorld getPhysicsWorld() {
        if (this.physicsWorld == null) {
            resetPhysicsWorld();
        }
        return this.physicsWorld;
    }

    public Sprite getSpriteByRuntimeName(String runtimeName) {
        if (runtimeName != null && !runtimeName.isEmpty()) {
            for (Sprite sprite : this.project.getSpriteListWithClones()) {
                if (runtimeName.equals(sprite.getRuntimeName())) {
                    return sprite;
                }
            }
        }
        return null;
    }

    public synchronized PhysicsWorld resetPhysicsWorld() {
        PhysicsWorld physicsWorld;
        physicsWorld = new PhysicsWorld(this.project.getXmlHeader().getVirtualScreenWidth(), this.project.getXmlHeader().getVirtualScreenHeight(), this.project);
        this.physicsWorld = physicsWorld;
        return physicsWorld;
    }

    public synchronized void setPhysicsWorld(PhysicsWorld world) {
        this.physicsWorld = world;
    }

    public File getDirectory() {
        if (this.project == null) {
            return new File(BackpackListManager.getInstance().backpackSceneDirectory, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.f518name));
        }
        return new File(this.project.getDirectory(), FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.f518name));
    }

    public File getDirectoryNew() {
        if (this.project == null) {
            return new File(BackpackListManager.getInstance().backpackSceneDirectory, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.f518name));
        }
        return new File(this.project.getDirectory() + "scenes/", FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.f518name));
    }

    public void removeClonedSprites() {
        Iterator<Sprite> it = this.spriteList.iterator();
        while (it.hasNext()) {
            if (it.next().isClone) {
                it.remove();
            }
        }
    }

    public void removeSpriteFromCloneBricks(Sprite spriteToDelete) {
        for (Sprite sprite : this.spriteList) {
            if (!sprite.equals(spriteToDelete)) {
                for (Script script : sprite.getScriptList()) {
                    ArrayList arrayList = new ArrayList();
                    script.addToFlatList(arrayList);
                    for (Brick brick : arrayList) {
                        if (brick instanceof CloneBrick) {
                            CloneBrick cloneBrick = (CloneBrick) brick;
                            if (cloneBrick.getSelectedItem() != null && cloneBrick.getSelectedItem().equals(spriteToDelete)) {
                                cloneBrick.resetSpinner();
                            }
                        } else if (brick instanceof CloneAndNameBrick) {
                            CloneAndNameBrick cloneAndNameBrick = (CloneAndNameBrick) brick;
                            if (cloneAndNameBrick.getSelectedItem() != null && cloneAndNameBrick.getSelectedItem().equals(spriteToDelete)) {
                                cloneAndNameBrick.resetSpinner();
                            }
                        }
                    }
                }
            }
        }
    }

    public Set<String> getBroadcastMessagesInUse() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<Sprite> it = this.spriteList.iterator();
        while (it.hasNext()) {
            for (Script script : it.next().getScriptList()) {
                if (script instanceof BroadcastScript) {
                    linkedHashSet.add(((BroadcastScript) script).getBroadcastMessage());
                }
                ArrayList arrayList = new ArrayList();
                script.addToFlatList(arrayList);
                for (Brick brick : arrayList) {
                    if (brick instanceof BroadcastMessageBrick) {
                        linkedHashSet.add(((BroadcastMessageBrick) brick).getBroadcastMessage());
                    }
                }
            }
        }
        return linkedHashSet;
    }

    public void editBroadcastMessagesInUse(String oldMessage, String newMessage) {
        Iterator<Sprite> it = this.spriteList.iterator();
        while (it.hasNext()) {
            for (Script script : it.next().getScriptList()) {
                if (script instanceof BroadcastScript) {
                    BroadcastScript broadcastScript = (BroadcastScript) script;
                    if (broadcastScript.getBroadcastMessage().equals(oldMessage)) {
                        broadcastScript.setBroadcastMessage(newMessage);
                    }
                }
                ArrayList arrayList = new ArrayList();
                script.addToFlatList(arrayList);
                for (Brick brick : arrayList) {
                    if (brick instanceof BroadcastMessageBrick) {
                        BroadcastMessageBrick broadcastMessageBrick = (BroadcastMessageBrick) brick;
                        if (broadcastMessageBrick.getBroadcastMessage().equals(oldMessage)) {
                            broadcastMessageBrick.setBroadcastMessage(newMessage);
                        }
                    }
                }
            }
        }
    }

    public void updateUserDataReferences(String oldName, String newName, UserData<?> item) {
        if (ProjectManager.getInstance().getCurrentProject().isGlobalVariable(item)) {
            Iterator<Sprite> it = this.spriteList.iterator();
            while (it.hasNext()) {
                it.next().updateUserDataReferences(oldName, newName, item);
            }
            return;
        }
        ProjectManager.getInstance().getCurrentSprite().updateUserDataReferences(oldName, newName, item);
    }

    public void deselectElements(List<UserData<?>> elements) {
        Iterator<Sprite> it = this.spriteList.iterator();
        while (it.hasNext()) {
            it.next().deselectElements(elements);
        }
    }

    public void checkForInvisibleSprites() {
        for (Sprite sprite : this.spriteList) {
            if ((sprite instanceof GroupItemSprite) && isInvisibleSprite(this.spriteList.indexOf(sprite))) {
                sprite.setConvertToSprite(true);
                Sprite spriteConvert = sprite.convert();
                List<Sprite> list = this.spriteList;
                list.set(list.indexOf(sprite), spriteConvert);
            }
        }
    }

    private boolean isInvisibleSprite(int index) {
        for (int i = index - 1; i > 0; i--) {
            Sprite sprite = this.spriteList.get(i);
            if (!(sprite instanceof GroupItemSprite)) {
                return !(sprite instanceof GroupSprite);
            }
        }
        return true;
    }
}
