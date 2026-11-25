package org.catrobat.catroid.scratchconverter.protocol;

import androidx.core.app.NotificationCompat;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/JsonKeys.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum JsonKeys {
    CATEGORY("category"),
    DATA("data"),
    TYPE(ModuleXmlParser.TYPE);

    private final String rawValue;

    JsonKeys(final String rawValue) {
        this.rawValue = rawValue;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.rawValue;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/JsonKeys$JsonDataKeys.class
      classes14.dex
     */
    public enum JsonDataKeys {
        MSG(NotificationCompat.CATEGORY_MESSAGE),
        JOB_ID("jobID"),
        JOB_TITLE("jobTitle"),
        JOB_IMAGE_URL("jobImageURL"),
        LINES("lines"),
        PROGRESS("progress"),
        URL("url"),
        CACHED_UTC_DATE("cachedUTCDate"),
        JOBS_INFO("jobsInfo"),
        CATROBAT_LANGUAGE_VERSION("catLangVers"),
        CLIENT_ID("clientID");

        private final String rawValue;

        JsonDataKeys(final String rawValue) {
            this.rawValue = rawValue;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.rawValue;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/JsonKeys$JsonJobDataKeys.class
      classes14.dex
     */
    public enum JsonJobDataKeys {
        STATE("state"),
        JOB_ID("jobID"),
        TITLE("title"),
        IMAGE_URL("imageURL"),
        PROGRESS("progress"),
        DOWNLOAD_URL("downloadURL");

        private final String rawValue;

        JsonJobDataKeys(final String rawValue) {
            this.rawValue = rawValue;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.rawValue;
        }
    }
}
