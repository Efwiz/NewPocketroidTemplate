package org.catrobat.catroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.DefaultProjectHandler;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ScreenModes;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.LegoEV3Setting;
import org.catrobat.catroid.content.LegoNXTSetting;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Setting;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenBounceOffScript;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.content.backwardcompatibility.BrickTreeBuilder;
import org.catrobat.catroid.content.bricks.ArduinoSendPWMValueBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundByIndexAndWaitBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundByIndexBrick;
import org.catrobat.catroid.content.bricks.SetPenColorBrick;
import org.catrobat.catroid.exceptions.CompatibilityProjectException;
import org.catrobat.catroid.exceptions.LoadingProjectException;
import org.catrobat.catroid.exceptions.OutdatedVersionProjectException;
import org.catrobat.catroid.exceptions.ProjectException;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.physics.PhysicsCollisionListener;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ProjectManager.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class ProjectManager {
    private static final String TAG = "ProjectManager";
    private static ProjectManager instance;
    private static Deque<ProjectManagerState> projectCallStack = new ArrayDeque();
    private Context applicationContext;
    private Sprite currentSprite;
    private Scene currentlyEditedScene;
    private Scene currentlyPlayingScene;
    private HashMap<String, Boolean> downloadedProjects;
    private final String downloadedProjectsName = "downloaded_projects";
    private boolean needsPhysicsCacheWarning = false;
    private Project project;
    private Scene startScene;

    public Context getApplicationContext() {
        return this.applicationContext;
    }

    public ProjectManager(Context applicationContext) {
        this.applicationContext = applicationContext;
        if (instance == null) {
            instance = this;
            loadDownloadedProjects();
        }
    }

    @Deprecated
    public static ProjectManager getInstance() {
        return instance;
    }

    public void loadProject(File projectDir) throws ProjectException {
        loadProject(projectDir, this.applicationContext);
    }

    @Deprecated
    public void loadProject(File projectDir, Context context) throws ProjectException {
        Project project = this.project;
        try {
            Project projectLoadProject = XstreamSerializer.getInstance().loadProject(projectDir, context);
            this.project = projectLoadProject;
            if (projectLoadProject.getCatrobatLanguageVersion() < 1.17d) {
                this.needsPhysicsCacheWarning = true;
            }
            if (this.project.getCatrobatLanguageVersion() > 1.18d) {
                restorePreviousProject(project);
                throw new OutdatedVersionProjectException(context.getString(org.DanVexTeam.NewCatroidApkTemplate.R.string.error_outdated_version));
            }
            if (this.project.getCatrobatLanguageVersion() < 0.9d && this.project.getCatrobatLanguageVersion() != 0.8d) {
                restorePreviousProject(project);
                throw new CompatibilityProjectException(context.getString(org.DanVexTeam.NewCatroidApkTemplate.R.string.error_project_compatibility));
            }
            if (this.project.getCatrobatLanguageVersion() <= 0.91d) {
                this.project.setScreenMode(ScreenModes.STRETCH);
            }
            if (this.project.getCatrobatLanguageVersion() <= 0.992d) {
                updateCollisionFormulasTo993(this.project);
            }
            if (this.project.getCatrobatLanguageVersion() <= 0.993d) {
                updateSetPenColorFormulasTo994(this.project);
            }
            if (this.project.getCatrobatLanguageVersion() <= 0.994d) {
                updateArduinoValuesTo995(this.project);
            }
            if (this.project.getCatrobatLanguageVersion() <= 0.995d) {
                updateCollisionScriptsTo996(this.project);
            }
            if (this.project.getCatrobatLanguageVersion() <= 0.999d) {
                makeShallowCopiesDeepAgain(this.project);
            }
            if (this.project.getCatrobatLanguageVersion() <= 0.9993d) {
                updateScriptsToTreeStructure(this.project);
            }
            if (this.project.getCatrobatLanguageVersion() <= 0.99992d) {
                removePermissionsFile(this.project);
            }
            if (this.project.getCatrobatLanguageVersion() <= 0.9999995d) {
                updateBackgroundIndexTo9999995(this.project);
            }
            if (this.project.getCatrobatLanguageVersion() < 999.9d) {
                flattenAllLists(this.project);
            }
            if (this.project.getCatrobatLanguageVersion() <= 1.03d) {
                updateDirectionProperty(this.project);
            }
            XmlHeader xmlHeader = this.project.getXmlHeader();
            if (this.project.getCatrobatLanguageVersion() <= 1.13d) {
                xmlHeader.setCustomResolution(false);
            }
            if (this.project.getCatrobatLanguageVersion() <= 1.14d) {
                xmlHeader.setPhysicsWidthArea(3.0f);
                xmlHeader.setPhysicsHeightArea(2.0f);
            }
            if (this.project.getCatrobatLanguageVersion() <= 1.15d) {
                xmlHeader.setGitRemoteUrl("");
            }
            if (this.project.getCatrobatLanguageVersion() < 1.17d && migrateProjectToV1_17(this.project)) {
                try {
                    XstreamSerializer.getInstance().saveProject(this.project);
                    Log.i(TAG, "Project '" + this.project.getName() + "' was migrated to language version 1.17 with new UUIDs.");
                } catch (Exception e) {
                    throw new LoadingProjectException("Failed to save project after migration to 1.17: " + e.getMessage());
                }
            }
            if (this.project.getCatrobatLanguageVersion() < 1.18d && migrateProjectToV1_18(this.project)) {
                try {
                    XstreamSerializer.getInstance().saveProject(this.project);
                    Log.i(TAG, "Project '" + this.project.getName() + "' was migrated to language version 1.18.");
                } catch (Exception e2) {
                    throw new LoadingProjectException("Failed to save project after migration to 1.18: " + e2.getMessage());
                }
            }
            this.project.setCatrobatLanguageVersion(1.18d);
            this.project.setCatrobatLanguageVersion(1.18d);
            localizeBackgroundSprites(this.project, context.getString(org.DanVexTeam.NewCatroidApkTemplate.R.string.background));
            initializeScripts(this.project);
            loadLegoNXTSettingsFromProject(this.project, context);
            loadLegoEV3SettingsFromProject(this.project, context);
            Brick.ResourcesSet requiredResources = this.project.getRequiredResources();
            if (requiredResources.contains(10)) {
                SettingsFragment.setPhiroSharedPreferenceEnabled(context, true);
            }
            if (requiredResources.contains(6)) {
                SettingsFragment.setArduinoSharedPreferenceEnabled(context, true);
            }
            if (requiredResources.contains(23)) {
                SettingsFragment.setAISpeechReconitionPreferenceEnabled(context, true);
            }
            if (requiredResources.contains(4)) {
                SettingsFragment.setAIFaceDetectionPreferenceEnabled(context, true);
            }
            if (requiredResources.contains(27)) {
                SettingsFragment.setAIPoseDetectionPreferenceEnabled(context, true);
            }
            if (requiredResources.contains(1)) {
                SettingsFragment.setAISpeechSynthetizationPreferenceEnabled(context, true);
            }
            if (requiredResources.contains(5)) {
                SettingsFragment.setAITextRecognitionPreferenceEnabled(context, true);
            }
            if (requiredResources.contains(28)) {
                SettingsFragment.setAIObjectDetectionPreferenceEnabled(context, true);
            }
            this.currentlyPlayingScene = this.project.getDefaultScene();
            this.currentSprite = null;
        } catch (IOException e3) {
            Log.e(TAG, Log.getStackTraceString(e3));
            restorePreviousProject(project);
            throw new LoadingProjectException(context.getString(org.DanVexTeam.NewCatroidApkTemplate.R.string.error_load_project));
        }
    }

    private void restorePreviousProject(Project previousProject) {
        this.project = previousProject;
        if (previousProject != null) {
            this.currentlyPlayingScene = previousProject.getDefaultScene();
        }
    }

    private boolean migrateProjectToV1_18(Project project) {
        if (project == null) {
            return false;
        }
        project.setDisableScientificNotation(false);
        project.setCatrobatLanguageVersion(1.18d);
        return true;
    }

    private boolean migrateProjectToV1_17(Project project) {
        boolean z = false;
        if (project != null && project.getSceneList() != null) {
            for (Scene scene : project.getSceneList()) {
                if (scene.getSceneId() == null || scene.getSceneId().isEmpty()) {
                    scene.setSceneId(UUID.randomUUID().toString());
                    z = true;
                }
                if (scene.getSpriteList() != null) {
                    for (Sprite sprite : scene.getSpriteList()) {
                        if (sprite.getSpriteId() == null || sprite.getSpriteId().isEmpty()) {
                            sprite.setSpriteId(UUID.randomUUID().toString());
                            z = true;
                        }
                        if (sprite.getLookList() != null) {
                            for (LookData lookData : sprite.getLookList()) {
                                if (lookData.getLookId() == null || lookData.getLookId().isEmpty()) {
                                    lookData.setLookId(UUID.randomUUID().toString());
                                    z = true;
                                }
                            }
                        }
                        if (sprite.getSoundList() != null) {
                            for (SoundInfo soundInfo : sprite.getSoundList()) {
                                if (soundInfo.getSoundId() == null || soundInfo.getSoundId().isEmpty()) {
                                    soundInfo.setSoundId(UUID.randomUUID().toString());
                                    z = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return z;
    }

    public static void makeShallowCopiesDeepAgain(Project project) {
        for (Scene scene : project.getSceneList()) {
            ArrayList arrayList = new ArrayList();
            for (Sprite sprite : scene.getSpriteList()) {
                Iterator<LookData> it = sprite.getLookList().iterator();
                while (it.hasNext()) {
                    LookData next = it.next();
                    if (arrayList.contains(next.getFile().getName())) {
                        try {
                            next.setFile(StorageOperations.duplicateFile(next.getFile()));
                        } catch (IOException unused) {
                            it.remove();
                            Log.e(TAG, "Cannot copy: " + next.getFile().getAbsolutePath() + ", removing LookData " + next.getName() + " from " + project.getName() + ", " + scene.getName() + ", " + sprite.getName() + ".");
                        }
                    }
                    arrayList.add(next.getFile().getName());
                }
                Iterator<SoundInfo> it2 = sprite.getSoundList().iterator();
                while (it2.hasNext()) {
                    SoundInfo next2 = it2.next();
                    if (arrayList.contains(next2.getFile().getName())) {
                        try {
                            next2.setFile(StorageOperations.duplicateFile(next2.getFile()));
                        } catch (IOException unused2) {
                            it2.remove();
                            Log.e(TAG, "Cannot copy: " + next2.getFile().getAbsolutePath() + ", removing SoundInfo " + next2.getName() + " from " + project.getName() + ", " + scene.getName() + ", " + sprite.getName() + ".");
                        }
                    }
                    arrayList.add(next2.getFile().getName());
                }
            }
        }
    }

    private void localizeBackgroundSprites(Project project, String localizedBackgroundName) {
        for (Scene scene : project.getSceneList()) {
            if (!scene.getSpriteList().isEmpty()) {
                Sprite sprite = scene.getSpriteList().get(0);
                sprite.renameSpriteAndUpdateCollisionFormulas(localizedBackgroundName, scene);
                sprite.look.setZIndex(0);
            }
        }
    }

    private static void initializeScripts(Project project) {
        for (Scene scene : project.getSceneList()) {
            Iterator<Sprite> it = scene.getSpriteList().iterator();
            while (it.hasNext()) {
                for (Script script : it.next().getScriptList()) {
                    script.setParents();
                    if (script instanceof WhenBounceOffScript) {
                        ((WhenBounceOffScript) script).updateSpriteToCollideWith(scene);
                    }
                }
            }
        }
    }

    private static void loadLegoNXTSettingsFromProject(Project project, Context context) {
        for (Setting setting : project.getSettings()) {
            if (setting instanceof LegoNXTSetting) {
                SettingsFragment.enableLegoMindstormsNXTBricks(context);
                SettingsFragment.setLegoMindstormsNXTSensorMapping(context, ((LegoNXTSetting) setting).getSensorMapping());
                return;
            }
        }
    }

    private static void loadLegoEV3SettingsFromProject(Project project, Context context) {
        for (Setting setting : project.getSettings()) {
            if (setting instanceof LegoEV3Setting) {
                SettingsFragment.enableLegoMindstormsEV3Bricks(context);
                SettingsFragment.setLegoMindstormsEV3SensorMapping(context, ((LegoEV3Setting) setting).getSensorMapping());
                return;
            }
        }
    }

    public List<UserVariable> getGlobalVariableConflicts(Project project1, Project project2) {
        List<UserVariable> userVariables = project1.getUserVariables();
        List<UserVariable> userVariables2 = project2.getUserVariables();
        ArrayList arrayList = new ArrayList();
        for (UserVariable userVariable : userVariables) {
            for (UserVariable userVariable2 : userVariables2) {
                if (userVariable.getName().equals(userVariable2.getName()) && !userVariable.getValue().equals(userVariable2.getValue())) {
                    arrayList.add(userVariable);
                }
            }
        }
        return arrayList;
    }

    public List<UserList> getGlobalListConflicts(Project project1, Project project2) {
        List<UserList> userLists = project1.getUserLists();
        List<UserList> userLists2 = project2.getUserLists();
        ArrayList arrayList = new ArrayList();
        for (UserList userList : userLists) {
            for (UserList userList2 : userLists2) {
                if (userList.getName().equals(userList2.getName()) && !userList.getValue().equals(userList2.getValue())) {
                    arrayList.add(userList);
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<Object> checkForVariablesConflicts(List<Object> globalVariables, List<Object> localVariables) {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (Object obj : localVariables) {
            if (globalVariables.contains(obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static void flattenAllLists(Project project) {
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                for (Script script : it2.next().getScriptList()) {
                    ArrayList arrayList = new ArrayList();
                    script.addToFlatList(arrayList);
                    for (Brick brick : arrayList) {
                        if (brick instanceof FormulaBrick) {
                            Iterator<Formula> it3 = ((FormulaBrick) brick).getFormulas().iterator();
                            while (it3.hasNext()) {
                                it3.next().flattenAllLists();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void updateCollisionFormulasTo993(Project project) {
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                for (Script script : it2.next().getScriptList()) {
                    ArrayList arrayList = new ArrayList();
                    script.addToFlatList(arrayList);
                    for (Brick brick : arrayList) {
                        if (brick instanceof FormulaBrick) {
                            Iterator<Formula> it3 = ((FormulaBrick) brick).getFormulas().iterator();
                            while (it3.hasNext()) {
                                it3.next().updateCollisionFormulasToVersion();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void updateSetPenColorFormulasTo994(Project project) {
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                Iterator<Script> it3 = it2.next().getScriptList().iterator();
                while (it3.hasNext()) {
                    for (Brick brick : it3.next().getBrickList()) {
                        if (brick instanceof SetPenColorBrick) {
                            SetPenColorBrick setPenColorBrick = (SetPenColorBrick) brick;
                            setPenColorBrick.replaceFormulaBrickField(Brick.BrickField.PHIRO_LIGHT_RED, Brick.BrickField.PEN_COLOR_RED);
                            setPenColorBrick.replaceFormulaBrickField(Brick.BrickField.PHIRO_LIGHT_GREEN, Brick.BrickField.PEN_COLOR_GREEN);
                            setPenColorBrick.replaceFormulaBrickField(Brick.BrickField.PHIRO_LIGHT_BLUE, Brick.BrickField.PEN_COLOR_BLUE);
                        }
                    }
                }
            }
        }
    }

    public static void updateArduinoValuesTo995(Project project) {
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                Iterator<Script> it3 = it2.next().getScriptList().iterator();
                while (it3.hasNext()) {
                    for (Brick brick : it3.next().getBrickList()) {
                        if (brick instanceof ArduinoSendPWMValueBrick) {
                            ((ArduinoSendPWMValueBrick) brick).updateArduinoValues994to995();
                        }
                    }
                }
            }
        }
    }

    public static void updateCollisionScriptsTo996(Project project) {
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            for (Sprite sprite : it.next().getSpriteList()) {
                for (Script script : sprite.getScriptList()) {
                    if (script instanceof WhenBounceOffScript) {
                        WhenBounceOffScript whenBounceOffScript = (WhenBounceOffScript) script;
                        String[] strArrSplit = whenBounceOffScript.getSpriteToBounceOffName().split(PhysicsCollisionListener.COLLISION_MESSAGE_CONNECTOR);
                        String str = strArrSplit[0];
                        if (str.equals(sprite.getName())) {
                            str = strArrSplit[1];
                        }
                        whenBounceOffScript.setSpriteToBounceOffName(str);
                    }
                }
            }
        }
    }

    public static void updateBackgroundIndexTo9999995(Project project) {
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                Iterator<Script> it3 = it2.next().getScriptList().iterator();
                while (it3.hasNext()) {
                    for (Brick brick : it3.next().getBrickList()) {
                        if (brick instanceof SetBackgroundByIndexBrick) {
                            ((FormulaBrick) brick).replaceFormulaBrickField(Brick.BrickField.LOOK_INDEX, Brick.BrickField.BACKGROUND_INDEX);
                        } else if (brick instanceof SetBackgroundByIndexAndWaitBrick) {
                            ((FormulaBrick) brick).replaceFormulaBrickField(Brick.BrickField.LOOK_INDEX, Brick.BrickField.BACKGROUND_WAIT_INDEX);
                        }
                    }
                }
            }
        }
    }

    public static void updateScriptsToTreeStructure(Project project) {
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                for (Script script : it2.next().getScriptList()) {
                    BrickTreeBuilder brickTreeBuilder = new BrickTreeBuilder();
                    brickTreeBuilder.convertBricks(script.getBrickList());
                    script.getBrickList().clear();
                    script.getBrickList().addAll(brickTreeBuilder.toList());
                }
            }
        }
    }

    public static void removePermissionsFile(Project project) {
        File file = new File(project.getDirectory(), Constants.PERMISSIONS_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void updateDirectionProperty(Project project) {
        Iterator<Scene> it = project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                for (Script script : it2.next().getScriptList()) {
                    ArrayList arrayList = new ArrayList();
                    script.addToFlatList(arrayList);
                    for (Brick brick : arrayList) {
                        if (brick instanceof FormulaBrick) {
                            Iterator<Formula> it3 = ((FormulaBrick) brick).getFormulas().iterator();
                            while (it3.hasNext()) {
                                it3.next().updateDirectionPropertyToVersion();
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean initializeDefaultProject() {
        return initializeDefaultProject(this.applicationContext);
    }

    @Deprecated
    public boolean initializeDefaultProject(Context context) {
        try {
            Project projectCreateAndSaveDefaultProject = DefaultProjectHandler.createAndSaveDefaultProject(context);
            this.project = projectCreateAndSaveDefaultProject;
            this.currentSprite = null;
            Scene defaultScene = projectCreateAndSaveDefaultProject.getDefaultScene();
            this.currentlyEditedScene = defaultScene;
            this.currentlyPlayingScene = defaultScene;
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Cannot initialize default project.", e);
            return false;
        }
    }

    public void createNewEmptyProject(String name2, boolean landscapeMode, boolean castEnabled) throws IOException {
        createNewEmptyProject(name2, this.applicationContext, landscapeMode, castEnabled);
    }

    @Deprecated
    public void createNewEmptyProject(String name2, Context context, boolean landscapeMode, boolean castEnabled) throws IOException {
        Project projectCreateAndSaveEmptyProject = DefaultProjectHandler.createAndSaveEmptyProject(name2, context, landscapeMode, castEnabled);
        this.project = projectCreateAndSaveEmptyProject;
        this.currentSprite = null;
        Scene defaultScene = projectCreateAndSaveEmptyProject.getDefaultScene();
        this.currentlyEditedScene = defaultScene;
        this.currentlyPlayingScene = defaultScene;
    }

    public void createNewExampleProject(String name2, DefaultProjectHandler.ProjectCreatorType projectCreatorType, boolean landscapeMode) throws IOException {
        createNewExampleProject(name2, this.applicationContext, projectCreatorType, landscapeMode);
    }

    @Deprecated
    public void createNewExampleProject(String name2, Context context, DefaultProjectHandler.ProjectCreatorType projectCreatorType, boolean landscapeMode) throws IOException {
        DefaultProjectHandler.getInstance().setDefaultProjectCreator(projectCreatorType);
        Project projectCreateAndSaveDefaultProject = DefaultProjectHandler.createAndSaveDefaultProject(name2, context, landscapeMode);
        this.project = projectCreateAndSaveDefaultProject;
        this.currentSprite = null;
        Scene defaultScene = projectCreateAndSaveDefaultProject.getDefaultScene();
        this.currentlyEditedScene = defaultScene;
        this.currentlyPlayingScene = defaultScene;
    }

    public Project getCurrentProject() {
        return this.project;
    }

    public Scene getCurrentlyPlayingScene() {
        if (this.currentlyPlayingScene == null) {
            this.currentlyPlayingScene = getCurrentlyEditedScene();
        }
        return this.currentlyPlayingScene;
    }

    public void setCurrentlyPlayingScene(Scene scene) {
        this.currentlyPlayingScene = scene;
    }

    public Scene getStartScene() {
        if (this.startScene == null) {
            this.startScene = getCurrentlyEditedScene();
        }
        return this.startScene;
    }

    public void setStartScene(Scene scene) {
        this.startScene = scene;
    }

    public Scene getCurrentlyEditedScene() {
        Project project;
        if (this.currentlyEditedScene == null && (project = this.project) != null) {
            this.currentlyEditedScene = project.getDefaultScene();
        }
        return this.currentlyEditedScene;
    }

    public boolean isCurrentProjectLandscapeMode() {
        return getCurrentProject().getXmlHeader().getVirtualScreenWidth() > getCurrentProject().getXmlHeader().getVirtualScreenHeight();
    }

    public void setCurrentProject(Project project) {
        this.currentSprite = null;
        this.project = project;
        if (project == null || project.getSceneList().isEmpty()) {
            return;
        }
        Scene defaultScene = project.getDefaultScene();
        this.currentlyEditedScene = defaultScene;
        this.currentlyPlayingScene = defaultScene;
    }

    public Sprite getCurrentSprite() {
        Sprite sprite = this.currentSprite;
        return sprite != null ? sprite : this.project.getDefaultScene().getBackgroundSprite();
    }

    public void setCurrentSprite(Sprite sprite) {
        this.currentSprite = sprite;
    }

    public boolean setCurrentSceneAndSprite(String sceneName, String spriteName) {
        for (Scene scene : this.project.getSceneList()) {
            if (scene.getName().equals(sceneName)) {
                setCurrentlyEditedScene(scene);
                for (Sprite sprite : scene.getSpriteList()) {
                    if (sprite.getName().equals(spriteName)) {
                        this.currentSprite = sprite;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setCurrentlyEditedScene(Scene scene) {
        this.currentlyEditedScene = scene;
        this.currentlyPlayingScene = scene;
    }

    public void addNewDownloadedProject(String projectName) {
        Boolean bool = this.downloadedProjects.get(projectName);
        if (bool == null || bool.booleanValue()) {
            this.downloadedProjects.put(projectName, false);
            saveDownloadedProjects();
        }
    }

    public void resetChangedFlag(Project project) {
        String name2 = project.getName();
        Boolean bool = this.downloadedProjects.get(name2);
        if (bool == null || !bool.booleanValue()) {
            return;
        }
        this.downloadedProjects.put(name2, false);
        saveDownloadedProjects();
    }

    public boolean isChangedProject(Project project) {
        Boolean bool = this.downloadedProjects.get(project.getName());
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public void deleteDownloadedProjectInformation(String projectName) {
        this.downloadedProjects.remove(projectName);
    }

    public void changedProject(String projectName) {
        Boolean bool = this.downloadedProjects.get(projectName);
        if (bool == null || bool.booleanValue()) {
            return;
        }
        this.downloadedProjects.put(projectName, true);
        saveDownloadedProjects();
    }

    public void saveDownloadedProjects() {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.applicationContext).edit();
        editorEdit.putString("downloaded_projects", new Gson().toJson(this.downloadedProjects));
        editorEdit.apply();
    }

    public void loadDownloadedProjects() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.applicationContext);
        Gson gson = new Gson();
        if (defaultSharedPreferences != null) {
            String string = defaultSharedPreferences.getString("downloaded_projects", null);
            if (string != null) {
                this.downloadedProjects = (HashMap) gson.fromJson(string, new TypeToken<HashMap<String, Boolean>>() { // from class: org.catrobat.catroid.ProjectManager.1
                }.getType());
            } else {
                this.downloadedProjects = new HashMap<>();
            }
        }
    }

    public void moveChangedFlag(String source, String destination) {
        if (this.downloadedProjects.get(source) != null) {
            this.downloadedProjects.remove(source);
            this.downloadedProjects.put(destination, true);
            saveDownloadedProjects();
        }
    }

    public void resetProjectManager() {
        this.currentlyEditedScene = null;
        this.currentlyPlayingScene = null;
        this.currentSprite = null;
    }

    public boolean isNeedsPhysicsCacheWarning() {
        return this.needsPhysicsCacheWarning;
    }

    public void setNeedsPhysicsCacheWarning(boolean needsPhysicsCacheWarning) {
        this.needsPhysicsCacheWarning = needsPhysicsCacheWarning;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ProjectManager$ProjectManagerState.class
      classes.dex
     */
    public static class ProjectManagerState {
        public Sprite currentSprite;
        public Scene currentlyEditedScene;
        public Scene currentlyPlayingScene;
        public Project project;
        public Scene startScene;

        public ProjectManagerState(Project project, Scene currentlyEditedScene, Scene currentlyPlayingScene, Scene startScene, Sprite currentSprite) {
            this.project = project;
            this.currentlyEditedScene = currentlyEditedScene;
            this.currentlyPlayingScene = currentlyPlayingScene;
            this.startScene = startScene;
            this.currentSprite = currentSprite;
        }
    }

    public static void pushProjectHistory() {
        ProjectManager projectManager = instance;
        if (projectManager == null || projectManager.project == null) {
            return;
        }
        ProjectManager projectManager2 = instance;
        projectCallStack.push(new ProjectManagerState(projectManager2.project, projectManager2.currentlyEditedScene, projectManager2.currentlyPlayingScene, projectManager2.startScene, projectManager2.currentSprite));
    }

    @Deprecated
    public static void pushProjectHistory(String projectPath) {
        pushProjectHistory();
    }

    public static boolean popProjectHistory() {
        if (projectCallStack.isEmpty()) {
            return false;
        }
        ProjectManagerState projectManagerStatePop = projectCallStack.pop();
        ProjectManager projectManager = instance;
        if (projectManager == null || projectManagerStatePop == null) {
            return false;
        }
        projectManager.project = projectManagerStatePop.project;
        instance.currentlyEditedScene = projectManagerStatePop.currentlyEditedScene;
        instance.currentlyPlayingScene = projectManagerStatePop.currentlyPlayingScene;
        instance.startScene = projectManagerStatePop.startScene;
        instance.currentSprite = projectManagerStatePop.currentSprite;
        return true;
    }
}
