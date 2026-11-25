package org.catrobat.catroid.common;

import java.io.File;
import java.io.Serializable;
import org.catrobat.catroid.utils.FileMetaDataExtractor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/ProjectData.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class ProjectData implements Nameable, Serializable, Comparable<ProjectData> {
    private static final long serialVersionUID = 1;
    private File directory;
    private boolean hasScenes;
    private double languageVersion;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f515name;

    public ProjectData(String name2, File directory, double languageVersion, boolean hasScenes) {
        this.f515name = name2;
        this.directory = directory;
        this.languageVersion = languageVersion;
        this.hasScenes = hasScenes;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f515name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        throw new RuntimeException("Do not set the project name through this. TODO: refactor nameable interface.");
    }

    public double getLanguageVersion() {
        return this.languageVersion;
    }

    public boolean hasScenes() {
        return this.hasScenes;
    }

    public long getLastUsed() {
        return new File(this.directory, Constants.CODE_XML_FILE_NAME).lastModified();
    }

    public File getDirectory() {
        return new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(getName()));
    }

    @Override // java.lang.Comparable
    public int compareTo(ProjectData projectData) {
        return this.f515name.compareTo(projectData.getName());
    }
}
