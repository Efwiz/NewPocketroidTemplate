package org.catrobat.catroid.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.common.Backpack;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.io.BackpackSerializer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/controller/BackpackListManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BackpackListManager {
    private static BackpackListManager instance;
    private Backpack backpack;
    public final File backpackDirectory;
    public final File backpackFile;
    public final File backpackImageDirectory;
    public final File backpackSceneDirectory;
    private final BackpackSerializer backpackSerializer;
    public final File backpackSoundDirectory;

    public static BackpackListManager getInstance() {
        if (instance == null) {
            instance = new BackpackListManager();
        }
        return instance;
    }

    private BackpackListManager() {
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, Constants.BACKPACK_DIRECTORY_NAME);
        this.backpackDirectory = file;
        File file2 = new File(file, Constants.BACKPACK_JSON_FILE_NAME);
        this.backpackFile = file2;
        this.backpackSceneDirectory = new File(file, "scenes");
        this.backpackSoundDirectory = new File(file, Constants.BACKPACK_SOUND_DIRECTORY_NAME);
        this.backpackImageDirectory = new File(file, Constants.BACKPACK_IMAGE_DIRECTORY_NAME);
        this.backpack = new Backpack();
        this.backpackSerializer = new BackpackSerializer(file2);
        createBackpackDirectories();
    }

    private void createBackpackDirectories() {
        createDirectory(this.backpackDirectory);
        createDirectory(this.backpackSceneDirectory);
        createDirectory(this.backpackImageDirectory);
        createDirectory(this.backpackSoundDirectory);
    }

    private void createDirectory(File directory) {
        directory.mkdir();
        if (!directory.exists()) {
            throw new RuntimeException("Could not create directory: " + directory.getAbsolutePath());
        }
    }

    public Backpack getBackpack() {
        return this.backpack;
    }

    public void removeItemFromScriptBackPack(String scriptGroup) {
        getBackpack().backpackedScripts.remove(scriptGroup);
        getBackpack().backpackedUserVariables.remove(scriptGroup);
        getBackpack().backpackedUserLists.remove(scriptGroup);
        getBackpack().backpackedUserDefinedBricks.remove(scriptGroup);
    }

    public List<Scene> getScenes() {
        return getBackpack().backpackedScenes;
    }

    public List<Sprite> getSprites() {
        return getBackpack().backpackedSprites;
    }

    public List<String> getBackpackedScriptGroups() {
        return new ArrayList(getBackpack().backpackedScripts.keySet());
    }

    public HashMap<String, List<Script>> getBackpackedScripts() {
        return getBackpack().backpackedScripts;
    }

    public HashMap<String, List<UserDefinedBrick>> getBackpackedUserDefinedBricks() {
        return getBackpack().backpackedUserDefinedBricks;
    }

    public void addScriptToBackPack(String scriptGroup, List<Script> scripts) {
        getBackpack().backpackedScripts.put(scriptGroup, scripts);
    }

    public void addUserDefinedBrickToBackPack(String scriptGroup, List<UserDefinedBrick> userDefinedBricks) {
        getBackpack().backpackedUserDefinedBricks.put(scriptGroup, userDefinedBricks);
    }

    public List<LookData> getBackpackedLooks() {
        return getBackpack().backpackedLooks;
    }

    public List<SoundInfo> getBackpackedSounds() {
        return getBackpack().backpackedSounds;
    }

    public boolean isBackpackEmpty() {
        return getBackpackedLooks().isEmpty() && getBackpackedScriptGroups().isEmpty() && getBackpackedSounds().isEmpty() && getSprites().isEmpty();
    }

    public void saveBackpack() {
        this.backpackSerializer.saveBackpack(getBackpack());
    }

    public void loadBackpack() {
        this.backpack = this.backpackSerializer.loadBackpack();
        for (Scene scene : getScenes()) {
            setSpriteFileReferences(scene.getSpriteList(), scene.getDirectory());
        }
        for (Sprite sprite : getSprites()) {
            setLookFileReferences(sprite.getLookList(), this.backpackImageDirectory);
            setSoundFileReferences(sprite.getSoundList(), this.backpackSoundDirectory);
        }
        setLookFileReferences(getBackpackedLooks(), this.backpackImageDirectory);
        setSoundFileReferences(getBackpackedSounds(), this.backpackSoundDirectory);
        Iterator<List<Script>> it = getBackpackedScripts().values().iterator();
        while (it.hasNext()) {
            Iterator<T> it2 = it.next().iterator();
            while (it2.hasNext()) {
                ((Script) it2.next()).setParents();
            }
        }
    }

    private void setSpriteFileReferences(List<Sprite> sprites, File parentDir) {
        for (Sprite sprite : sprites) {
            setLookFileReferences(sprite.getLookList(), new File(parentDir, Constants.IMAGE_DIRECTORY_NAME));
            setSoundFileReferences(sprite.getSoundList(), new File(parentDir, Constants.SOUND_DIRECTORY_NAME));
        }
    }

    private void setLookFileReferences(List<LookData> looks, File parentDir) {
        Iterator<LookData> it = looks.iterator();
        while (it.hasNext()) {
            LookData next = it.next();
            File file = new File(parentDir, next.getXstreamFileName());
            if (file.exists()) {
                next.setFile(file);
            } else {
                it.remove();
            }
        }
    }

    private void setSoundFileReferences(List<SoundInfo> sounds, File parentDir) {
        Iterator<SoundInfo> it = sounds.iterator();
        while (it.hasNext()) {
            SoundInfo next = it.next();
            File file = new File(parentDir, next.getXstreamFileName());
            if (file.exists()) {
                next.setFile(file);
            } else {
                it.remove();
            }
        }
    }
}
