package org.catrobat.catroid.content;

import android.text.TextUtils;
import com.reandroid.arsc.value.ResConfigBase;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.catrobat.catroid.common.ScreenModes;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/XmlHeader.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class XmlHeader implements Serializable {
    private static final long serialVersionUID = 1;
    private double catrobatLanguageVersion;
    private String description;
    private boolean landscapeMode;
    private String notesAndCredits;
    private String programName;

    @XStreamAlias(ResConfigBase.NAME_screenWidth)
    public int virtualScreenWidth = 0;

    @XStreamAlias(ResConfigBase.NAME_screenHeight)
    public int virtualScreenHeight = 0;

    @XStreamAlias("physicsWidthArea")
    public float physicsWidthArea = 3.0f;

    @XStreamAlias("physicsHeightArea")
    public float physicsHeightArea = 2.0f;
    public ScreenModes screenMode = ScreenModes.STRETCH;
    public Boolean customResolution = false;
    private boolean isCastProject = false;
    public boolean scenesEnabled = true;
    private String listeningLanguageTag = "";
    private String applicationBuildName = "";
    private int applicationBuildNumber = 0;
    private String applicationName = "";
    private String applicationVersion = "";
    private String applicationBuildType = "";
    private String deviceName = "";
    private String platform = "";
    private String platformVersion = "";
    private String tags = "";

    @XStreamAlias("gitRemoteUrl")
    private String gitRemoteUrl = "";

    @XStreamAlias("remixOf")
    private String remixGrandparentsUrlString = "";

    @XStreamAlias("url")
    private String remixParentsUrlString = "";
    private String userHandle = "";
    private String dateTimeUpload = "";
    private String mediaLicense = "";
    private String programLicense = "";

    public String getGitRemoteUrl() {
        String str = this.gitRemoteUrl;
        return str == null ? "" : str;
    }

    public void setGitRemoteUrl(String url) {
        this.gitRemoteUrl = url;
    }

    public int getVirtualScreenHeight() {
        return this.virtualScreenHeight;
    }

    public int getVirtualScreenWidth() {
        return this.virtualScreenWidth;
    }

    public void setVirtualScreenHeight(int height) {
        this.virtualScreenHeight = height;
    }

    public void setVirtualScreenWidth(int width) {
        this.virtualScreenWidth = width;
    }

    public String getProjectName() {
        return this.programName;
    }

    public void setProjectName(String programName) {
        this.programName = programName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getNotesAndCredits() {
        return this.notesAndCredits;
    }

    public String getUserHandle() {
        return this.userHandle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNotesAndCredits(String notesAndCredits) {
        this.notesAndCredits = notesAndCredits;
    }

    public double getCatrobatLanguageVersion() {
        return this.catrobatLanguageVersion;
    }

    public void setCatrobatLanguageVersion(double catrobatLanguageVersion) {
        this.catrobatLanguageVersion = catrobatLanguageVersion;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getApplicationBuildName() {
        return this.applicationBuildName;
    }

    public void setApplicationBuildName(String applicationBuildName) {
        this.applicationBuildName = applicationBuildName;
    }

    public int getApplicationBuildNumber() {
        return this.applicationBuildNumber;
    }

    public void setApplicationBuildNumber(int applicationBuildNumber) {
        this.applicationBuildNumber = applicationBuildNumber;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setPhysicsWidthArea(float value) {
        this.physicsWidthArea = value;
    }

    public void setPhysicsHeightArea(float value) {
        this.physicsHeightArea = value;
    }

    public float getPhysicsWidthArea() {
        return this.physicsWidthArea;
    }

    public float getPhysicsHeightArea() {
        return this.physicsHeightArea;
    }

    public String getApplicationVersion() {
        return this.applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPlatformVersion() {
        return this.platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public void setScreenMode(ScreenModes screenMode) {
        this.screenMode = screenMode;
    }

    public ScreenModes getScreenMode() {
        return this.screenMode;
    }

    public void setCustomResolution(Boolean screenMode) {
        this.customResolution = screenMode;
    }

    public Boolean getCustomResolution() {
        return this.customResolution;
    }

    public boolean islandscapeMode() {
        return this.landscapeMode;
    }

    public void setlandscapeMode(boolean landscapeMode) {
        this.landscapeMode = landscapeMode;
    }

    public void setIsCastProject(boolean isCastProject) {
        this.isCastProject = isCastProject;
    }

    public boolean isCastProject() {
        return this.isCastProject;
    }

    public List<String> getTags() {
        return Arrays.asList(this.tags.split(","));
    }

    public void setTags(List<String> tags) {
        this.tags = TextUtils.join(",", tags);
    }

    public String getRemixParentsUrlString() {
        return this.remixParentsUrlString;
    }

    public void setRemixParentsUrlString(String remixParentsUrlString) {
        this.remixParentsUrlString = remixParentsUrlString;
    }

    public String getApplicationBuildType() {
        return this.applicationBuildType;
    }

    public void setApplicationBuildType(String applicationBuildType) {
        this.applicationBuildType = applicationBuildType;
    }

    public String getListeningLanguageTag() {
        return this.listeningLanguageTag;
    }

    public void setListeningLanguageTag(String listeningLanguageTag) {
        this.listeningLanguageTag = listeningLanguageTag;
    }
}
