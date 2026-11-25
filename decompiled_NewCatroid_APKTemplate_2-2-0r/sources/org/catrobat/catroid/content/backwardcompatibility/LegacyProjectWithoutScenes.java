package org.catrobat.catroid.content.backwardcompatibility;

import android.content.Context;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Setting;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/backwardcompatibility/LegacyProjectWithoutScenes.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias(ConfigConstants.CONFIG_KEY_PROGRAM)
public class LegacyProjectWithoutScenes implements Serializable {
    private static final long serialVersionUID = 1;
    private XmlHeader header = new XmlHeader();
    private List<Sprite> objectList = new ArrayList();
    private LegacyDataContainer data = null;
    private List<Setting> settings = new ArrayList();

    public Project toProject(Context context) throws IOException {
        File directory = getDirectory();
        Project project = new Project();
        project.setXmlHeader(getXmlHeader());
        project.getSettings().addAll(getSettings());
        project.getUserVariables().addAll(getProjectUserVariables());
        project.getUserLists().addAll(getProjectUserLists());
        for (Sprite sprite : getSpriteList()) {
            sprite.getUserVariables().addAll(getSpriteUserVariables(sprite));
            sprite.getUserLists().addAll(getSpriteUserLists(sprite));
        }
        Scene scene = new Scene(context.getString(R.string.default_scene_name), project);
        scene.getSpriteList().addAll(getSpriteList());
        project.addScene(scene);
        StorageOperations.createSceneDirectory(scene.getDirectory());
        File file = new File(directory, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
        File file2 = new File(directory, Constants.SCREENSHOT_MANUAL_FILE_NAME);
        StorageOperations.copyDir(new File(directory, Constants.IMAGE_DIRECTORY_NAME), new File(scene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME));
        StorageOperations.copyDir(new File(directory, Constants.SOUND_DIRECTORY_NAME), new File(scene.getDirectory(), Constants.SOUND_DIRECTORY_NAME));
        if (file.exists()) {
            StorageOperations.copyFileToDir(file, scene.getDirectory());
            file.delete();
        }
        if (file2.exists()) {
            StorageOperations.copyFileToDir(file2, scene.getDirectory());
            file2.delete();
        }
        StorageOperations.deleteDir(new File(directory, Constants.IMAGE_DIRECTORY_NAME));
        StorageOperations.deleteDir(new File(directory, Constants.SOUND_DIRECTORY_NAME));
        return project;
    }

    public XmlHeader getXmlHeader() {
        return this.header;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public File getDirectory() {
        return new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.header.getProjectName()));
    }

    public List<Sprite> getSpriteList() {
        return this.objectList;
    }

    public List<UserVariable> getProjectUserVariables() {
        LegacyDataContainer legacyDataContainer = this.data;
        if (legacyDataContainer != null && legacyDataContainer.projectVariables != null) {
            return this.data.projectVariables;
        }
        return Collections.emptyList();
    }

    public List<UserList> getProjectUserLists() {
        LegacyDataContainer legacyDataContainer = this.data;
        if (legacyDataContainer != null && legacyDataContainer.projectLists != null) {
            return this.data.projectLists;
        }
        return Collections.emptyList();
    }

    public List<UserVariable> getSpriteUserVariables(Sprite sprite) {
        LegacyDataContainer legacyDataContainer = this.data;
        if (legacyDataContainer != null && legacyDataContainer.spriteVariables.get(sprite) != null) {
            return this.data.spriteVariables.get(sprite);
        }
        return Collections.emptyList();
    }

    public List<UserList> getSpriteUserLists(Sprite sprite) {
        LegacyDataContainer legacyDataContainer = this.data;
        if (legacyDataContainer != null && legacyDataContainer.spriteListOfLists.get(sprite) != null) {
            return this.data.spriteListOfLists.get(sprite);
        }
        return Collections.emptyList();
    }
}
