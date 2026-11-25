package org.catrobat.catroid.content.backwardcompatibility;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Setting;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/backwardcompatibility/ProjectUntilLanguageVersion0999.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias(ConfigConstants.CONFIG_KEY_PROGRAM)
public class ProjectUntilLanguageVersion0999 implements Serializable {
    private static final long serialVersionUID = 1;
    private XmlHeader header = new XmlHeader();
    private List<Setting> settings = new ArrayList();
    private List<UserVariable> programVariableList = new ArrayList();
    private List<UserVariable> programMultiplayerVariableList = new ArrayList();
    private List<UserList> programListOfLists = new ArrayList();
    private List<SceneUntilLanguageVersion0999> scenes = new ArrayList();

    public Project toProject() {
        Project project = new Project();
        project.setXmlHeader(getXmlHeader());
        project.getSettings().addAll(getSettings());
        project.getUserVariables().addAll(getUserVariables());
        project.getMultiplayerVariables().addAll(getMultiplayerVariables());
        project.getUserLists().addAll(getUserLists());
        for (SceneUntilLanguageVersion0999 sceneUntilLanguageVersion0999 : getSceneList()) {
            Scene scene = new Scene(sceneUntilLanguageVersion0999.getName(), project);
            for (Sprite sprite : sceneUntilLanguageVersion0999.getSpriteList()) {
                sprite.getUserVariables().addAll(sceneUntilLanguageVersion0999.getSpriteUserVariables(sprite));
                sprite.getUserLists().addAll(sceneUntilLanguageVersion0999.getSpriteUserLists(sprite));
                scene.addSprite(sprite);
            }
            project.addScene(scene);
        }
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

    public List<UserVariable> getUserVariables() {
        return this.programVariableList;
    }

    public List<UserVariable> getMultiplayerVariables() {
        return this.programMultiplayerVariableList;
    }

    public List<UserList> getUserLists() {
        return this.programListOfLists;
    }

    public List<SceneUntilLanguageVersion0999> getSceneList() {
        return this.scenes;
    }
}
