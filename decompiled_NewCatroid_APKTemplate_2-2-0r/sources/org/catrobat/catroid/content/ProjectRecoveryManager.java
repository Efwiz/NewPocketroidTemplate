package org.catrobat.catroid.content;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/ProjectRecoveryManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ProjectRecoveryManager {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/ProjectRecoveryManager$LostProjectInfo.class
      classes14.dex
     */
    public static class LostProjectInfo {
        public String folderName;
        public File path;
        public String projectName;

        public LostProjectInfo(String folderName, String projectName, File path) {
            this.folderName = folderName;
            this.projectName = projectName;
            this.path = path;
        }
    }

    public static List<LostProjectInfo> scanForLostProjects(Context context) {
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = context.getFilesDir().listFiles();
        if (fileArrListFiles == null) {
            return arrayList;
        }
        for (File file : fileArrListFiles) {
            if (file.isDirectory()) {
                File file2 = new File(file, Constants.CODE_XML_FILE_NAME);
                if (file2.exists()) {
                    String projectNameFromXml = getProjectNameFromXml(file2);
                    if (projectNameFromXml == null) {
                        projectNameFromXml = file.getName();
                    }
                    arrayList.add(new LostProjectInfo(file.getName(), projectNameFromXml, file));
                }
            }
        }
        return arrayList;
    }

    private static String getProjectNameFromXml(File xmlFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(xmlFile);
            try {
                XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                xmlPullParserNewPullParser.setInput(new InputStreamReader(fileInputStream));
                for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
                    if (eventType == 2 && ReplaceExistingProjectDialogFragment.BUNDLE_KEY_PROGRAM_NAME.equals(xmlPullParserNewPullParser.getName())) {
                        String strNextText = xmlPullParserNewPullParser.nextText();
                        fileInputStream.close();
                        return strNextText;
                    }
                    Log.e("Recovery", "Error parsing xml: " + xmlFile.getName());
                    return null;
                }
                fileInputStream.close();
                return null;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception unused) {
            Log.e("Recovery", "Error parsing xml: " + xmlFile.getName());
            return null;
        }
    }
}
