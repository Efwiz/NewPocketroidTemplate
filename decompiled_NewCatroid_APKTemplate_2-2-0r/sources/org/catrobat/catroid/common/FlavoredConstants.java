package org.catrobat.catroid.common;

import android.os.Environment;
import java.io.File;
import org.catrobat.catroid.CatroidApplication;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/FlavoredConstants.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public final class FlavoredConstants {
    public static final String BASE_UPLOAD_URL = "https://upload.catrob.at/pocketcode/";
    public static final String BASE_URL_HTTPS = "https://t.me/NewCatroidDevs";
    public static final String CATEGORY_URL = "https://t.me/NewCatroidDevs#home-projects__";
    public static final String CATROBAT_HELP_URL = "https://catrob.at/help";
    public static final String COMMUNITY_URL = "http://newcatroid.sois.site";
    public static final String FLAVOR_NAME = "pocketcode";
    public static final String LIBRARY_BACKGROUNDS_URL_LANDSCAPE = "https://t.me/NewCatroidDevsmedia-library/backgrounds-landscape";
    public static final String LIBRARY_BACKGROUNDS_URL_PORTRAIT = "https://t.me/NewCatroidDevsmedia-library/backgrounds-portrait";
    public static final String LIBRARY_BASE_URL = "https://t.me/NewCatroidDevs";
    public static final String LIBRARY_LOOKS_URL = "https://t.me/NewCatroidDevsmedia-library/looks";
    public static final String LIBRARY_OBJECT_URL = "https://t.me/NewCatroidDevsmedia-library/objects";
    public static final String LIBRARY_SOUNDS_URL = "https://t.me/NewCatroidDevsmedia-library/sounds";
    public static final String PRIVACY_POLICY_URL = "http://e95814zx.beget.tech/newcatroid/policy.html";
    public static final File DEFAULT_ROOT_DIRECTORY = CatroidApplication.getAppContext().getFilesDir();
    public static final String POCKET_CODE_EXTERNAL_STORAGE_FOLDER_NAME = "Pocket Code";
    public static final File EXTERNAL_STORAGE_ROOT_DIRECTORY = new File(Environment.getExternalStorageDirectory(), POCKET_CODE_EXTERNAL_STORAGE_FOLDER_NAME);

    private FlavoredConstants() {
        throw new AssertionError("No.");
    }
}
