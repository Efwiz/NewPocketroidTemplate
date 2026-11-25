package org.catrobat.catroid.content;

import android.content.Context;
import android.os.Build;
import androidx.exifinterface.media.ExifInterface;
import com.badlogic.gdx.math.Rectangle;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.BroadcastMessageContainer;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ScreenModes;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.XStreamFieldKeyOrder;
import org.catrobat.catroid.physics.content.ActionPhysicsFactory;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.ScreenValueHandler;
import org.catrobat.catroid.utils.Utils;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/Project.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias(ConfigConstants.CONFIG_KEY_PROGRAM)
@XStreamFieldKeyOrder({"header", "settings", "scenes", "programVariableList", "programListOfLists", "programMultiplayerVariableList", "disableScientificNotation"})
public class Project implements Serializable {
    private static final long serialVersionUID = 1;
    private transient BroadcastMessageContainer broadcastMessageContainer;
    private transient File directory;

    @XStreamAlias("disableScientificNotation")
    private boolean disableScientificNotation;

    @XStreamAlias("programMultiplayerVariableList")
    private List<UserVariable> multiplayerVariables;

    @XStreamAlias("scenes")
    private List<Scene> sceneList;

    @XStreamAlias("settings")
    private List<Setting> settings;

    @XStreamAlias("programListOfLists")
    private List<UserList> userLists;

    @XStreamAlias("programVariableList")
    private List<UserVariable> userVariables;

    @XStreamAlias("header")
    private XmlHeader xmlHeader;

    public Project() {
        this.xmlHeader = new XmlHeader();
        this.settings = new ArrayList();
        this.userVariables = new ArrayList();
        this.multiplayerVariables = new ArrayList();
        this.userLists = new ArrayList();
        this.sceneList = new ArrayList();
        this.disableScientificNotation = false;
        this.broadcastMessageContainer = new BroadcastMessageContainer();
    }

    public Project(Context context, String name2, boolean landscapeMode, boolean isCastProject) {
        this.xmlHeader = new XmlHeader();
        this.settings = new ArrayList();
        this.userVariables = new ArrayList();
        this.multiplayerVariables = new ArrayList();
        this.userLists = new ArrayList();
        this.sceneList = new ArrayList();
        this.disableScientificNotation = false;
        this.broadcastMessageContainer = new BroadcastMessageContainer();
        this.xmlHeader.setProjectName(name2);
        this.xmlHeader.setDescription("");
        this.xmlHeader.setNotesAndCredits("");
        this.xmlHeader.setlandscapeMode(landscapeMode);
        if (ScreenValues.currentScreenResolution == null) {
            ScreenValueHandler.updateScreenWidthAndHeight(context);
        }
        if (landscapeMode) {
            ScreenValues.currentScreenResolution = ScreenValues.currentScreenResolution.flipToLandscape();
        } else {
            ScreenValues.currentScreenResolution = ScreenValues.currentScreenResolution.flipToPortrait();
        }
        this.xmlHeader.virtualScreenWidth = ScreenValues.currentScreenResolution.getWidth();
        this.xmlHeader.virtualScreenHeight = ScreenValues.currentScreenResolution.getHeight();
        if (isCastProject) {
            this.xmlHeader.virtualScreenHeight = ScreenValues.CAST_SCREEN_RESOLUTION.getHeight();
            this.xmlHeader.virtualScreenWidth = ScreenValues.CAST_SCREEN_RESOLUTION.getWidth();
            this.xmlHeader.setlandscapeMode(true);
            this.xmlHeader.setIsCastProject(true);
        }
        Scene scene = new Scene(context.getString(R.string.default_scene_name), this);
        Sprite sprite = new Sprite(context.getString(R.string.background));
        sprite.look.setZIndex(0);
        scene.addSprite(sprite);
        this.sceneList.add(scene);
        this.xmlHeader.scenesEnabled = true;
        setDeviceData(context);
    }

    public Project(Context context, String name2, boolean landscapeMode) {
        this(context, name2, landscapeMode, false);
    }

    public Project(Context context, String name2) {
        this(context, name2, false);
    }

    public File getDirectory() {
        if (this.directory == null) {
            this.directory = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(getName()));
        }
        return this.directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public List<Scene> getSceneList() {
        return this.sceneList;
    }

    public List<String> getSceneNames() {
        ArrayList arrayList = new ArrayList();
        Iterator<Scene> it = this.sceneList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        return arrayList;
    }

    public void addScene(Scene scene) {
        if (scene != null) {
            for (Scene scene2 : this.sceneList) {
                if (scene2.getSceneId() != null && scene2.getSceneId().equals(scene.getSceneId())) {
                    scene.setSceneId(UUID.randomUUID().toString());
                    if (scene.getSpriteList() == null) {
                        break;
                    }
                    for (Sprite sprite : scene.getSpriteList()) {
                        sprite.setSpriteId(UUID.randomUUID().toString());
                        if (sprite.getLookList() != null) {
                            Iterator<LookData> it = sprite.getLookList().iterator();
                            while (it.hasNext()) {
                                it.next().setLookId(UUID.randomUUID().toString());
                            }
                        }
                        if (sprite.getSoundList() != null) {
                            Iterator<SoundInfo> it2 = sprite.getSoundList().iterator();
                            while (it2.hasNext()) {
                                it2.next().setSoundId(UUID.randomUUID().toString());
                            }
                        }
                    }
                    break;
                }
            }
        }
        this.sceneList.add(scene);
    }

    public void removeScene(Scene scene) {
        if (scene != null) {
            for (int i = 0; i < this.sceneList.size(); i++) {
                if (this.sceneList.get(i).equals(scene)) {
                    this.sceneList.remove(i);
                    return;
                }
            }
        }
    }

    public boolean hasScene() {
        return this.sceneList.size() > 0;
    }

    public Scene getDefaultScene() {
        try {
            return this.sceneList.get(0);
        } catch (Exception unused) {
            return null;
        }
    }

    public boolean isDisableScientificNotation() {
        return this.disableScientificNotation;
    }

    public void setDisableScientificNotation(boolean disableScientificNotation) {
        this.disableScientificNotation = disableScientificNotation;
    }

    public String formatValue(Object value) {
        if (value == null) {
            return "";
        }
        if (this.disableScientificNotation) {
            if (value instanceof Number) {
                double dDoubleValue = ((Number) value).doubleValue();
                if (Double.isNaN(dDoubleValue) || Double.isInfinite(dDoubleValue)) {
                    return value.toString();
                }
                try {
                    return Project$$ExternalSyntheticBackportWithForwarding0.m(BigDecimal.valueOf(dDoubleValue)).toPlainString();
                } catch (ArithmeticException | NumberFormatException unused) {
                    return value.toString();
                }
            }
            if (value instanceof String) {
                String str = (String) value;
                if (str.contains("e") || str.contains(ExifInterface.LONGITUDE_EAST)) {
                    try {
                        double d = Double.parseDouble(str);
                        if (!Double.isNaN(d) && !Double.isInfinite(d)) {
                            return Project$$ExternalSyntheticBackportWithForwarding0.m(BigDecimal.valueOf(d)).toPlainString();
                        }
                    } catch (NumberFormatException unused2) {
                    }
                }
            }
        }
        return value.toString();
    }

    public <T> boolean hasUserDataChanged(List<T> newUserData, List<T> oldUserData) {
        if (newUserData.size() != oldUserData.size()) {
            return true;
        }
        Iterator<T> it = newUserData.iterator();
        while (it.hasNext()) {
            if (!checkEquality(it.next(), oldUserData)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> boolean checkEquality(T t, List<T> list) {
        for (T t2 : list) {
            if (t2.equals(t)) {
                if (t2.getClass() == UserVariable.class) {
                    return ((UserVariable) t2).hasSameValue((UserVariable) t);
                }
                return ((UserList) t2).hasSameListSize((UserList) t);
            }
        }
        return false;
    }

    public List<UserVariable> getUserVariables() {
        if (this.userVariables == null) {
            this.userVariables = new ArrayList();
        }
        return this.userVariables;
    }

    public List<UserVariable> getUserVariablesCopy() {
        if (this.userVariables == null) {
            this.userVariables = new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (UserVariable userVariable : this.userVariables) {
            arrayList.add(new UserVariable(userVariable.getName(), userVariable.getValue()));
        }
        return arrayList;
    }

    public <T> void restoreUserDataValues(List<T> list, List<T> list2) {
        for (T t : list) {
            for (T t2 : list2) {
                if (t.getClass() == UserVariable.class) {
                    UserVariable userVariable = (UserVariable) t;
                    UserVariable userVariable2 = (UserVariable) t2;
                    if (userVariable.getName().equals(userVariable2.getName())) {
                        userVariable.setValue(userVariable2.getValue());
                    }
                } else {
                    UserList userList = (UserList) t;
                    UserList userList2 = (UserList) t2;
                    if (userList.getName().equals(userList2.getName())) {
                        userList.setValue(userList2.getValue());
                    }
                }
            }
        }
    }

    public UserVariable getUserVariable(String name2) {
        for (UserVariable userVariable : this.userVariables) {
            if (userVariable.getName().equals(name2)) {
                return userVariable;
            }
        }
        return null;
    }

    public boolean addUserVariable(UserVariable userVariable) {
        return this.userVariables.add(userVariable);
    }

    public boolean removeUserVariable(String name2) {
        for (UserVariable userVariable : this.userVariables) {
            if (userVariable.getName().equals(name2)) {
                return this.userVariables.remove(userVariable);
            }
        }
        return false;
    }

    public List<UserList> getUserLists() {
        if (this.userLists == null) {
            this.userLists = new ArrayList();
        }
        return this.userLists;
    }

    public List<UserList> getUserListsCopy() {
        if (this.userLists == null) {
            this.userLists = new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (UserList userList : this.userLists) {
            arrayList.add(new UserList(userList.getName(), userList.getValue()));
        }
        return arrayList;
    }

    public UserList getUserList(String name2) {
        for (UserList userList : this.userLists) {
            if (userList.getName().equals(name2)) {
                return userList;
            }
        }
        return null;
    }

    public boolean addUserList(UserList userList) {
        return this.userLists.add(userList);
    }

    public boolean removeUserList(String name2) {
        for (UserList userList : this.userLists) {
            if (userList.getName().equals(name2)) {
                return this.userLists.remove(userList);
            }
        }
        return false;
    }

    public List<UserVariable> getMultiplayerVariablesCopy() {
        if (this.multiplayerVariables == null) {
            this.multiplayerVariables = new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (UserVariable userVariable : this.multiplayerVariables) {
            arrayList.add(new UserVariable(userVariable.getName(), userVariable.getValue()));
        }
        return arrayList;
    }

    public List<UserVariable> getMultiplayerVariables() {
        if (this.multiplayerVariables == null) {
            this.multiplayerVariables = new ArrayList();
        }
        return this.multiplayerVariables;
    }

    public UserVariable getMultiplayerVariable(String name2) {
        for (UserVariable userVariable : this.multiplayerVariables) {
            if (userVariable.getName().equals(name2)) {
                return userVariable;
            }
        }
        return null;
    }

    public boolean hasMultiplayerVariables() {
        return this.multiplayerVariables.size() > 0;
    }

    public boolean addMultiplayerVariable(UserVariable multiplayerVariable) {
        return this.multiplayerVariables.add(multiplayerVariable);
    }

    public void resetUserData() {
        Iterator<UserVariable> it = this.userVariables.iterator();
        while (it.hasNext()) {
            it.next().reset();
        }
        Iterator<UserList> it2 = this.userLists.iterator();
        while (it2.hasNext()) {
            it2.next().reset();
        }
        Iterator<UserVariable> it3 = this.multiplayerVariables.iterator();
        while (it3.hasNext()) {
            it3.next().reset();
        }
    }

    public List<Sprite> getSpriteListWithClones() {
        StageActivity stageActivity;
        List<Sprite> spritesFromStage;
        if (StageActivity.activeStageActivity != null && (stageActivity = StageActivity.activeStageActivity.get()) != null && stageActivity.stageListener != null && (spritesFromStage = stageActivity.stageListener.getSpritesFromStage()) != null) {
            return spritesFromStage;
        }
        Scene defaultScene = getDefaultScene();
        if (defaultScene != null && defaultScene.getSpriteList() != null) {
            return defaultScene.getSpriteList();
        }
        return new ArrayList();
    }

    public void fireToAllSprites(EventWrapper event) {
        Iterator<Sprite> it = getSpriteListWithClones().iterator();
        while (it.hasNext()) {
            it.next().look.fire(event);
        }
    }

    public String getName() {
        return this.xmlHeader.getProjectName();
    }

    public void setName(String name2) {
        this.xmlHeader.setProjectName(name2);
    }

    public String getDescription() {
        return this.xmlHeader.getDescription();
    }

    public void setDescription(String description) {
        this.xmlHeader.setDescription(description);
    }

    public String getNotesAndCredits() {
        return this.xmlHeader.getNotesAndCredits();
    }

    public void setNotesAndCredits(String notesAndCredits) {
        this.xmlHeader.setNotesAndCredits(notesAndCredits);
    }

    public ScreenModes getScreenMode() {
        return this.xmlHeader.getScreenMode();
    }

    public Boolean getCustomResolution() {
        return this.xmlHeader.getCustomResolution();
    }

    public void setScreenMode(ScreenModes screenMode) {
        this.xmlHeader.setScreenMode(screenMode);
    }

    public double getCatrobatLanguageVersion() {
        return this.xmlHeader.getCatrobatLanguageVersion();
    }

    public XmlHeader getXmlHeader() {
        return this.xmlHeader;
    }

    public File getFilesDir() {
        File file = new File(this.directory, "files");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public File getLibsDir() {
        File file = new File(this.directory, "libs");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public File getFile(String fileName) {
        File file = new File(getFilesDir(), fileName.replace('\\', '/'));
        try {
            if (!file.getCanonicalPath().startsWith(getFilesDir().getCanonicalPath())) {
                throw new SecurityException("No, this is ploho, ninada (getFile)");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    public File getLib(String fileName) {
        File file = new File(getLibsDir(), fileName.replace('\\', '/'));
        try {
            if (!file.getCanonicalPath().startsWith(getLibsDir().getCanonicalPath())) {
                throw new SecurityException("No, this is ploho, ninada (getLib)");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public String checkExtension(String filename, String extension) {
        return filename.contains(".") ? filename : filename + "." + extension;
    }

    public void addFile(File file) {
        moveFileToDirectory(file, getFilesDir());
    }

    public void deleteFile(String fileName) {
        new File(getFilesDir(), fileName).delete();
    }

    public void deleteFile(File file) {
        file.delete();
    }

    private static boolean moveFileToDirectory(File file, File targetDirectory) {
        if (file.exists()) {
            return file.renameTo(new File(targetDirectory, file.getName()));
        }
        return false;
    }

    public Rectangle getScreenRectangle() {
        int virtualScreenWidth = this.xmlHeader.getVirtualScreenWidth();
        int virtualScreenHeight = this.xmlHeader.getVirtualScreenHeight();
        return new Rectangle((-virtualScreenWidth) / 2, (-virtualScreenHeight) / 2, virtualScreenWidth, virtualScreenHeight);
    }

    public Brick.ResourcesSet getRequiredResources() {
        Brick.ResourcesSet resourcesSet = new Brick.ResourcesSet();
        if (isCastProject()) {
            resourcesSet.add((Integer) 22);
        }
        ActionPhysicsFactory actionPhysicsFactory = new ActionPhysicsFactory();
        ActionFactory actionFactory = new ActionFactory();
        Iterator<Scene> it = this.sceneList.iterator();
        while (it.hasNext()) {
            for (Sprite sprite : it.next().getSpriteList()) {
                sprite.addRequiredResources(resourcesSet);
                if (resourcesSet.contains(3)) {
                    sprite.setActionFactory(actionPhysicsFactory);
                    resourcesSet.remove(3);
                } else {
                    sprite.setActionFactory(actionFactory);
                }
            }
        }
        return resourcesSet;
    }

    public void setCatrobatLanguageVersion(double catrobatLanguageVersion) {
        this.xmlHeader.setCatrobatLanguageVersion(catrobatLanguageVersion);
    }

    public void setDeviceData(Context context) {
        this.xmlHeader.setPlatform("Android");
        this.xmlHeader.setPlatformVersion(String.valueOf(Build.VERSION.SDK_INT));
        this.xmlHeader.setDeviceName(Build.MODEL);
        this.xmlHeader.setCatrobatLanguageVersion(1.18d);
        this.xmlHeader.setApplicationBuildName("");
        this.xmlHeader.setApplicationBuildNumber(0);
        if (context == null) {
            this.xmlHeader.setApplicationVersion("unknown");
            this.xmlHeader.setApplicationName("unknown");
        } else {
            this.xmlHeader.setApplicationVersion(Utils.getVersionName(context));
            this.xmlHeader.setApplicationName(context.getString(R.string.app_name));
        }
    }

    public List<String> getTags() {
        return this.xmlHeader.getTags();
    }

    public void setTags(List<String> tags) {
        this.xmlHeader.setTags(tags);
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public Scene getSceneByName(String name2) {
        for (Scene scene : this.sceneList) {
            if (scene.getName().equals(name2)) {
                return scene;
            }
        }
        return null;
    }

    public Scene getSceneById(Integer id2) {
        if (id2 == null || id2.intValue() < 0 || id2.intValue() >= this.sceneList.size()) {
            return null;
        }
        return this.sceneList.get(id2.intValue());
    }

    public String getSceneNameById(Integer id2) {
        if (id2 == null || id2.intValue() < 0 || id2.intValue() >= this.sceneList.size()) {
            return null;
        }
        return this.sceneList.get(id2.intValue()).getName();
    }

    public boolean isCastProject() {
        return this.xmlHeader.isCastProject();
    }

    public BroadcastMessageContainer getBroadcastMessageContainer() {
        return this.broadcastMessageContainer;
    }

    public void setXmlHeader(XmlHeader xmlHeader) {
        this.xmlHeader = xmlHeader;
    }

    public void updateUserDataReferences(String oldName, String newName, UserData<?> item) {
        Iterator<Scene> it = this.sceneList.iterator();
        while (it.hasNext()) {
            it.next().updateUserDataReferences(oldName, newName, item);
        }
    }

    public void deselectElements(List<UserData<?>> elements) {
        Iterator<Scene> it = this.sceneList.iterator();
        while (it.hasNext()) {
            it.next().deselectElements(elements);
        }
    }

    public void setListeningLanguageTag() {
        if (!this.xmlHeader.getListeningLanguageTag().isEmpty() || Utils.SPEECH_RECOGNITION_SUPPORTED_LANGUAGES.isEmpty()) {
            return;
        }
        this.xmlHeader.setListeningLanguageTag(Utils.SPEECH_RECOGNITION_SUPPORTED_LANGUAGES.get(0));
    }

    public boolean isGlobalVariable(UserData<?> item) {
        return getUserVariables().contains(item) || getUserLists().contains(item) || getMultiplayerVariables().contains(item);
    }

    public void checkForInvisibleSprites() {
        Iterator<Scene> it = this.sceneList.iterator();
        while (it.hasNext()) {
            it.next().checkForInvisibleSprites();
        }
    }

    public List<String> getSpriteNames(List<Sprite> spriteList) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < spriteList.size(); i++) {
            arrayList.add(spriteList.get(i).getName());
        }
        return arrayList;
    }

    public void checkIfSpriteNameEqualBackground(Context context) {
        List<Sprite> spriteListWithClones;
        if (context == null || (spriteListWithClones = getSpriteListWithClones()) == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(spriteListWithClones);
        List<String> spriteNames = getSpriteNames(arrayList);
        for (int i = 1; i < arrayList.size(); i++) {
            if (arrayList.get(i) != null && arrayList.get(i).getName() != null && arrayList.get(i).getName().matches("[\\s]*" + context.getString(R.string.background) + "[\\s]*")) {
                arrayList.get(i).setName(new UniqueNameProvider().getUniqueName(context.getString(R.string.background), spriteNames));
                return;
            }
        }
    }
}
