package org.catrobat.catroid.ui.recyclerview.controller;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.SceneStartBrick;
import org.catrobat.catroid.content.bricks.SceneTransitionBrick;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.utils.FileMetaDataExtractor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/controller/SceneController.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SceneController {
    private static final String TAG = "SceneController";
    private UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();
    private SpriteController spriteController = new SpriteController();

    public static Scene newSceneWithBackgroundSprite(String sceneName, String backgroundName, Project dstProject) {
        Scene scene = new Scene(sceneName, dstProject);
        Sprite sprite = new Sprite(backgroundName);
        sprite.look.setZIndex(0);
        scene.addSprite(sprite);
        return scene;
    }

    public boolean rename(Scene sceneToRename, String name2) {
        String name3 = sceneToRename.getName();
        File file = new File(sceneToRename.getProject().getDirectory(), FileMetaDataExtractor.encodeSpecialCharsForFileSystem(name2));
        File directory = sceneToRename.getDirectory();
        boolean zRenameTo = directory.exists() ? directory.renameTo(file) : true;
        if (zRenameTo) {
            sceneToRename.setName(name2);
            Iterator<Scene> it = ProjectManager.getInstance().getCurrentProject().getSceneList().iterator();
            while (it.hasNext()) {
                Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
                while (it2.hasNext()) {
                    for (Brick brick : it2.next().getAllBricks()) {
                        if (brick instanceof SceneStartBrick) {
                            SceneStartBrick sceneStartBrick = (SceneStartBrick) brick;
                            if (sceneStartBrick.getSceneToStart().equals(name3)) {
                                sceneStartBrick.setSceneToStart(name2);
                            }
                        }
                        if (brick instanceof SceneTransitionBrick) {
                            SceneTransitionBrick sceneTransitionBrick = (SceneTransitionBrick) brick;
                            if (sceneTransitionBrick.getSceneForTransition().equals(name3)) {
                                sceneTransitionBrick.setSceneForTransition(name2);
                            }
                        }
                    }
                }
            }
        }
        return zRenameTo;
    }

    public Scene copy(Scene sceneToCopy, Project dstProject) throws IOException {
        String uniqueNameInNameables = this.uniqueNameProvider.getUniqueNameInNameables(sceneToCopy.getName(), dstProject.getSceneList());
        Scene scene = new Scene();
        scene.setName(uniqueNameInNameables);
        scene.setProject(dstProject);
        if (!createDirectory(scene.getDirectory())) {
            throw new IOException("Directory for Scene " + uniqueNameInNameables + " could not be created.");
        }
        scene.setPhysicsWorld(new PhysicsWorld(dstProject));
        Iterator<Sprite> it = sceneToCopy.getSpriteList().iterator();
        while (it.hasNext()) {
            scene.getSpriteList().add(this.spriteController.copy(it.next(), dstProject, scene));
        }
        copyScreenshot(sceneToCopy.getDirectory(), scene.getDirectory());
        return scene;
    }

    private void copyScreenshot(File sourceDirectory, File destinationDirectory) {
        File file = new File(sourceDirectory, Constants.SCREENSHOT_MANUAL_FILE_NAME);
        if (!file.exists()) {
            file = new File(sourceDirectory, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
        }
        if (file.exists()) {
            try {
                StorageOperations.copyFileToDir(file, destinationDirectory);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }

    public void delete(Scene sceneToDelete) throws IOException {
        ProjectManager.getInstance().getCurrentProject().removeScene(sceneToDelete);
        StorageOperations.deleteDir(sceneToDelete.getDirectory());
    }

    public Scene pack(Scene sceneToPack) throws IOException {
        String uniqueNameInNameables = this.uniqueNameProvider.getUniqueNameInNameables(sceneToPack.getName(), BackpackListManager.getInstance().getScenes());
        Scene scene = new Scene();
        scene.setName(uniqueNameInNameables);
        scene.setProject(null);
        if (!createDirectory(scene.getDirectory())) {
            throw new IOException("Directory for Scene " + uniqueNameInNameables + " could not be created.");
        }
        Iterator<Sprite> it = sceneToPack.getSpriteList().iterator();
        while (it.hasNext()) {
            scene.getSpriteList().add(this.spriteController.copy(it.next(), null, scene));
        }
        copyScreenshot(sceneToPack.getDirectory(), scene.getDirectory());
        return scene;
    }

    public Scene unpack(Scene sceneToUnpack, Project dstProject) throws IOException {
        return copy(sceneToUnpack, dstProject);
    }

    private boolean createDirectory(File dir) {
        File file = new File(dir, Constants.IMAGE_DIRECTORY_NAME);
        File file2 = new File(dir, Constants.SOUND_DIRECTORY_NAME);
        dir.mkdir();
        file.mkdir();
        file2.mkdir();
        if (file.isDirectory() && file2.isDirectory()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        try {
            StorageOperations.deleteDir(dir);
            return false;
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }
}
