package org.catrobat.catroid.ui.recyclerview.controller;

import android.util.Log;
import java.io.IOException;
import java.util.Iterator;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.GroupItemSprite;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.fragment.SpriteFactory;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/controller/SpriteController.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SpriteController {
    public static final String TAG = "SpriteController";
    private UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();
    private ScriptController scriptController = new ScriptController();
    private LookController lookController = new LookController();
    private SoundController soundController = new SoundController();

    public Sprite convert(Sprite spriteToConvert) {
        return spriteToConvert.convert();
    }

    public Sprite copy(Sprite spriteToCopy, Project destinationProject, Scene destinationScene) throws IOException {
        Sprite spriteNewInstance = new SpriteFactory().newInstance(spriteToCopy.getClass().getSimpleName(), this.uniqueNameProvider.getUniqueNameInNameables(spriteToCopy.getName(), destinationScene.getSpriteList()));
        Iterator<LookData> it = spriteToCopy.getLookList().iterator();
        while (it.hasNext()) {
            spriteNewInstance.getLookList().add(this.lookController.copy(it.next(), destinationScene, spriteNewInstance));
        }
        Iterator<SoundInfo> it2 = spriteToCopy.getSoundList().iterator();
        while (it2.hasNext()) {
            spriteNewInstance.getSoundList().add(this.soundController.copy(it2.next(), destinationScene, spriteNewInstance));
        }
        Iterator<NfcTagData> it3 = spriteToCopy.getNfcTagList().iterator();
        while (it3.hasNext()) {
            spriteNewInstance.getNfcTagList().add(it3.next().clone());
        }
        Iterator<UserVariable> it4 = spriteToCopy.getUserVariables().iterator();
        while (it4.hasNext()) {
            spriteNewInstance.getUserVariables().add(new UserVariable(it4.next()));
        }
        Iterator<UserList> it5 = spriteToCopy.getUserLists().iterator();
        while (it5.hasNext()) {
            spriteNewInstance.getUserLists().add(new UserList(it5.next()));
        }
        Iterator<Brick> it6 = spriteToCopy.getUserDefinedBrickList().iterator();
        while (it6.hasNext()) {
            spriteNewInstance.getUserDefinedBrickList().add(new UserDefinedBrick((UserDefinedBrick) it6.next()));
        }
        Iterator<Script> it7 = spriteToCopy.getScriptList().iterator();
        while (it7.hasNext()) {
            try {
                spriteNewInstance.addScript(this.scriptController.copy(it7.next(), destinationProject, destinationScene, spriteNewInstance));
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (spriteToCopy instanceof GroupItemSprite) {
            ((GroupItemSprite) spriteNewInstance).setCollapsed(((GroupItemSprite) spriteToCopy).isCollapsed());
        }
        return spriteNewInstance;
    }

    public Sprite copyForCloneBrick(Sprite spriteToCopy) {
        Sprite sprite = new Sprite(spriteToCopy.getName() + "-c" + StageActivity.getAndIncrementNumberOfClonedSprites());
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Scene currentlyEditedScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        ScriptController scriptController = new ScriptController();
        sprite.isClone = true;
        sprite.setActionFactory(spriteToCopy.getActionFactory());
        for (LookData lookData : spriteToCopy.getLookList()) {
            sprite.getLookList().add(new LookData(lookData.getName(), lookData.getFile()));
        }
        sprite.getSoundList().addAll(spriteToCopy.getSoundList());
        sprite.getNfcTagList().addAll(spriteToCopy.getNfcTagList());
        for (UserVariable userVariable : spriteToCopy.getUserVariables()) {
            UserVariable userVariable2 = new UserVariable(userVariable);
            userVariable2.setDeviceValueKey(userVariable.getDeviceKey());
            sprite.getUserVariables().add(userVariable2);
        }
        for (UserList userList : spriteToCopy.getUserLists()) {
            new UserList(userList).setDeviceListKey(userList.getDeviceKey());
            sprite.getUserLists().add(new UserList(userList));
        }
        Iterator<Brick> it = spriteToCopy.getUserDefinedBrickList().iterator();
        while (it.hasNext()) {
            sprite.getUserDefinedBrickList().add(new UserDefinedBrick((UserDefinedBrick) it.next()));
        }
        Iterator<Script> it2 = spriteToCopy.getScriptList().iterator();
        while (it2.hasNext()) {
            try {
                sprite.addScript(scriptController.copy(it2.next(), currentProject, currentlyEditedScene, sprite));
            } catch (IOException | CloneNotSupportedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        sprite.resetSprite();
        int iIndexOf = spriteToCopy.getLookList().indexOf(spriteToCopy.look.getLookData());
        if (iIndexOf != -1) {
            sprite.look.setLookData(sprite.getLookList().get(iIndexOf));
        }
        spriteToCopy.look.copyTo(sprite.look);
        if (spriteToCopy.penConfiguration.isPenDown()) {
            sprite.penConfiguration.setPenDown(true);
            sprite.penConfiguration.addQueue();
        }
        if (spriteToCopy.plot.getIsPlotting()) {
            sprite.plot.resume();
            sprite.plot.startNewPlotLine();
        }
        return sprite;
    }

    public Sprite copyForCloneBrick(Sprite spriteToCopy, String name2) {
        Sprite sprite = new Sprite(name2);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Scene currentlyEditedScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        ScriptController scriptController = new ScriptController();
        sprite.isClone = true;
        sprite.setActionFactory(spriteToCopy.getActionFactory());
        for (LookData lookData : spriteToCopy.getLookList()) {
            sprite.getLookList().add(new LookData(lookData.getName(), lookData.getFile()));
        }
        sprite.getSoundList().addAll(spriteToCopy.getSoundList());
        sprite.getNfcTagList().addAll(spriteToCopy.getNfcTagList());
        for (UserVariable userVariable : spriteToCopy.getUserVariables()) {
            UserVariable userVariable2 = new UserVariable(userVariable);
            userVariable2.setDeviceValueKey(userVariable.getDeviceKey());
            sprite.getUserVariables().add(userVariable2);
        }
        for (UserList userList : spriteToCopy.getUserLists()) {
            new UserList(userList).setDeviceListKey(userList.getDeviceKey());
            sprite.getUserLists().add(new UserList(userList));
        }
        Iterator<Brick> it = spriteToCopy.getUserDefinedBrickList().iterator();
        while (it.hasNext()) {
            sprite.getUserDefinedBrickList().add(new UserDefinedBrick((UserDefinedBrick) it.next()));
        }
        Iterator<Script> it2 = spriteToCopy.getScriptList().iterator();
        while (it2.hasNext()) {
            try {
                sprite.addScript(scriptController.copy(it2.next(), currentProject, currentlyEditedScene, sprite));
            } catch (IOException | CloneNotSupportedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        sprite.resetSprite();
        int iIndexOf = spriteToCopy.getLookList().indexOf(spriteToCopy.look.getLookData());
        if (iIndexOf != -1) {
            sprite.look.setLookData(sprite.getLookList().get(iIndexOf));
        }
        spriteToCopy.look.copyTo(sprite.look);
        if (spriteToCopy.penConfiguration.isPenDown()) {
            sprite.penConfiguration.setPenDown(true);
            sprite.penConfiguration.addQueue();
        }
        if (spriteToCopy.plot.getIsPlotting()) {
            sprite.plot.resume();
            sprite.plot.startNewPlotLine();
        }
        return sprite;
    }

    public void delete(Sprite spriteToDelete) {
        if (spriteToDelete.isClone) {
            throw new IllegalStateException("You are deleting a clone: this means you also delete the files that are referenced by the original sprite because clones are shallow copies regarding files.");
        }
        Iterator<LookData> it = spriteToDelete.getLookList().iterator();
        while (it.hasNext()) {
            try {
                this.lookController.delete(it.next());
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        Iterator<SoundInfo> it2 = spriteToDelete.getSoundList().iterator();
        while (it2.hasNext()) {
            try {
                this.soundController.delete(it2.next());
            } catch (IOException e2) {
                Log.e(TAG, Log.getStackTraceString(e2));
            }
        }
        Scene currentlyEditedScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        if (currentlyEditedScene != null) {
            currentlyEditedScene.removeSpriteFromCloneBricks(spriteToDelete);
        }
    }

    public Sprite pack(Sprite spriteToPack) throws IOException {
        Sprite sprite = new Sprite(this.uniqueNameProvider.getUniqueNameInNameables(spriteToPack.getName(), BackpackListManager.getInstance().getSprites()));
        Iterator<LookData> it = spriteToPack.getLookList().iterator();
        while (it.hasNext()) {
            this.lookController.packForSprite(it.next(), sprite);
        }
        Iterator<SoundInfo> it2 = spriteToPack.getSoundList().iterator();
        while (it2.hasNext()) {
            this.soundController.packForSprite(it2.next(), sprite);
        }
        Iterator<NfcTagData> it3 = spriteToPack.getNfcTagList().iterator();
        while (it3.hasNext()) {
            sprite.getNfcTagList().add(it3.next().clone());
        }
        Iterator<Brick> it4 = spriteToPack.getUserDefinedBrickList().iterator();
        while (it4.hasNext()) {
            try {
                sprite.addUserDefinedBrick((UserDefinedBrick) it4.next().clone());
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        Iterator<Script> it5 = spriteToPack.getScriptList().iterator();
        while (it5.hasNext()) {
            try {
                this.scriptController.packForSprite(spriteToPack, it5.next(), sprite);
            } catch (CloneNotSupportedException e2) {
                Log.e(TAG, Log.getStackTraceString(e2));
            }
        }
        return sprite;
    }

    public Sprite unpack(Sprite spriteToUnpack, Project destinationProject, Scene destinationScene) throws IOException {
        Sprite sprite = new Sprite(this.uniqueNameProvider.getUniqueNameInNameables(spriteToUnpack.getName(), destinationScene.getSpriteList()));
        Iterator<LookData> it = spriteToUnpack.getLookList().iterator();
        while (it.hasNext()) {
            this.lookController.unpackForSprite(it.next(), destinationScene, sprite);
        }
        Iterator<SoundInfo> it2 = spriteToUnpack.getSoundList().iterator();
        while (it2.hasNext()) {
            this.soundController.unpackForSprite(it2.next(), destinationScene, sprite);
        }
        Iterator<NfcTagData> it3 = spriteToUnpack.getNfcTagList().iterator();
        while (it3.hasNext()) {
            sprite.getNfcTagList().add(it3.next().clone());
        }
        Iterator<Brick> it4 = spriteToUnpack.getUserDefinedBrickList().iterator();
        while (it4.hasNext()) {
            try {
                sprite.addUserDefinedBrick((UserDefinedBrick) it4.next().clone());
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        Iterator<Script> it5 = spriteToUnpack.getScriptList().iterator();
        while (it5.hasNext()) {
            try {
                this.scriptController.unpackForSprite(spriteToUnpack.getName(), it5.next(), destinationProject, destinationScene, sprite);
            } catch (CloneNotSupportedException e2) {
                Log.e(TAG, Log.getStackTraceString(e2));
            }
        }
        return sprite;
    }
}
