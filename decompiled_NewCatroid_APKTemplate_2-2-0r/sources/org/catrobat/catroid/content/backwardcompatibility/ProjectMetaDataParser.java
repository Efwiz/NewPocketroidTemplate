package org.catrobat.catroid.content.backwardcompatibility;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import org.catrobat.catroid.common.ProjectData;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/backwardcompatibility/ProjectMetaDataParser.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ProjectMetaDataParser {
    private File xmlFile;

    public ProjectMetaDataParser(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public ProjectData getProjectMetaData() throws IOException {
        if (!this.xmlFile.exists()) {
            throw new FileNotFoundException(this.xmlFile.getAbsolutePath() + " does not exist.");
        }
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[]{"org.catrobat.catroid.**"});
        xStream.processAnnotations(ProjectMetaData.class);
        xStream.ignoreUnknownElements();
        try {
            ProjectMetaData projectMetaData = (ProjectMetaData) xStream.fromXML(this.xmlFile);
            projectMetaData.setFile(this.xmlFile);
            return new ProjectData(projectMetaData.getName(), projectMetaData.getDirectory(), projectMetaData.getLanguageVersion(), projectMetaData.hasScenes());
        } catch (Exception e) {
            throw new IOException("Project metadata invalid", e);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/backwardcompatibility/ProjectMetaDataParser$ProjectMetaData.class
      classes14.dex
     */
    @XStreamAlias(ConfigConstants.CONFIG_KEY_PROGRAM)
    private static class ProjectMetaData implements Serializable {
        private static final long serialVersionUID = 1;
        private XmlHeaderMetaData header;
        private File xmlFile;

        private ProjectMetaData() {
        }

        public String getName() {
            return this.header.programName;
        }

        public void setFile(File xmlFile) {
            this.xmlFile = xmlFile;
        }

        public File getDirectory() {
            return this.xmlFile.getParentFile();
        }

        public double getLanguageVersion() {
            return this.header.catrobatLanguageVersion;
        }

        public boolean hasScenes() {
            return this.header.scenesEnabled;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/backwardcompatibility/ProjectMetaDataParser$ProjectMetaData$XmlHeaderMetaData.class
          classes14.dex
         */
        private static final class XmlHeaderMetaData implements Serializable {
            private static final long serialVersionUID = 1;
            private double catrobatLanguageVersion;
            private String programName;
            private boolean scenesEnabled = false;

            private XmlHeaderMetaData() {
            }
        }
    }
}
