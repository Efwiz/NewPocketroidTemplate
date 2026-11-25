package org.catrobat.catroid.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/Backpack.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class Backpack implements Serializable {
    private static final long serialVersionUID = 1;
    public List<Scene> backpackedScenes = new CopyOnWriteArrayList();
    public List<Sprite> backpackedSprites = new CopyOnWriteArrayList();
    public List<SoundInfo> backpackedSounds = new CopyOnWriteArrayList();
    public List<LookData> backpackedLooks = new CopyOnWriteArrayList();
    public HashMap<String, List<Script>> backpackedScripts = new HashMap<>();
    public HashMap<String, List<UserDefinedBrick>> backpackedUserDefinedBricks = new HashMap<>();
    public HashMap<String, HashMap<String, Integer>> backpackedUserVariables = new HashMap<>();
    public HashMap<String, HashMap<String, Integer>> backpackedUserLists = new HashMap<>();
}
