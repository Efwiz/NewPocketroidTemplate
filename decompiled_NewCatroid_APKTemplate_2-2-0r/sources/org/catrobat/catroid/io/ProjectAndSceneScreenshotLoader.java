package org.catrobat.catroid.io;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.utils.ImageEditing;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/ProjectAndSceneScreenshotLoader.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ProjectAndSceneScreenshotLoader {
    private static final int CACHE_MAX_SIZE = 25;
    private static final int INITIAL_VALUE = 13;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int POOL_SIZE = 5;
    private static int[] placeholderImages = {R.drawable.catrobat, R.drawable.elephant, R.drawable.lynx, R.drawable.panda, R.drawable.pingu, R.drawable.racoon};
    private int thumbnailHeight;
    private int thumbnailWidth;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap());
    private Map<String, Bitmap> imageCache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(13, LOAD_FACTOR, true) { // from class: org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader.1
        private static final long serialVersionUID = 1;

        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<String, Bitmap> eldest) {
            return size() > 25;
        }
    });
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/ProjectAndSceneScreenshotLoader$ScreenshotData.class
      classes14.dex
     */
    private class ScreenshotData {
        public ImageView imageView;
        public boolean isBackpackScene;
        public String projectName;
        public String sceneName;

        ScreenshotData(String projectName, String sceneName, boolean isBackpackScene, ImageView imageView) {
            this.projectName = projectName;
            this.sceneName = sceneName;
            this.isBackpackScene = isBackpackScene;
            this.imageView = imageView;
        }
    }

    public ProjectAndSceneScreenshotLoader(int thumbnailWidth, int thumbnailHeight) {
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    public void loadAndShowScreenshot(String projectName, String sceneName, boolean isBackpackScene, ImageView imageView) {
        String strConcat = projectName != null ? projectName : "";
        if (sceneName != null) {
            strConcat = strConcat.concat(sceneName);
        }
        this.imageViews.put(imageView, strConcat);
        Bitmap bitmap = this.imageCache.get(strConcat);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageBitmap(null);
            this.executorService.submit(new ScreenshotLoader(new ScreenshotData(projectName, sceneName, isBackpackScene, imageView)));
        }
    }

    public File getScreenshotFile(String projectName, String sceneName, boolean isBackpackScene) {
        return new ScreenshotLoader(new ScreenshotData(projectName, sceneName, isBackpackScene, null)).getScreenshotFile();
    }

    public String getScreenshotSceneName(File projectDir) {
        FilenameFilter filenameFilter = new FilenameFilter() { // from class: org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader.2
            @Override // java.io.FilenameFilter
            public boolean accept(File f, String name2) {
                return name2.endsWith(Constants.DEFAULT_IMAGE_EXTENSION);
            }
        };
        File[] fileArrListFiles = projectDir.listFiles(filenameFilter);
        if (fileArrListFiles == null || fileArrListFiles.length != 0) {
            return null;
        }
        ArrayList<File> arrayList = new ArrayList();
        for (File file : projectDir.listFiles()) {
            File[] fileArrListFiles2 = file.listFiles(filenameFilter);
            if (fileArrListFiles2 != null && fileArrListFiles2.length > 0) {
                arrayList.addAll(Arrays.asList(fileArrListFiles2));
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        Collections.sort(arrayList, new Comparator<File>() { // from class: org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader.3
            @Override // java.util.Comparator
            public int compare(File screenshot2, File screenshot1) {
                return Long.compare(screenshot1.lastModified(), screenshot2.lastModified());
            }
        });
        for (File file2 : arrayList) {
            if (file2.getName().equals(Constants.SCREENSHOT_MANUAL_FILE_NAME)) {
                return file2.getParentFile().getName();
            }
        }
        return ((File) arrayList.get(0)).getParentFile().getName();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/ProjectAndSceneScreenshotLoader$ScreenshotLoader.class
      classes14.dex
     */
    class ScreenshotLoader implements Runnable {
        ScreenshotData projectAndSceneScreenshotData;

        ScreenshotLoader(ScreenshotData screenshotData) {
            this.projectAndSceneScreenshotData = screenshotData;
        }

        @Override // java.lang.Runnable
        public void run() {
            String strConcat;
            AppCompatActivity activityFromView;
            if (ProjectAndSceneScreenshotLoader.this.imageViewReused(this.projectAndSceneScreenshotData)) {
                return;
            }
            File screenshotFile = getScreenshotFile();
            String absolutePath = screenshotFile.getAbsolutePath();
            final Bitmap scaledBitmapFromPath = (!screenshotFile.exists() || ImageEditing.getImageDimensions(absolutePath)[0] < 0) ? null : ImageEditing.getScaledBitmapFromPath(absolutePath, ProjectAndSceneScreenshotLoader.this.thumbnailWidth, ProjectAndSceneScreenshotLoader.this.thumbnailHeight, ImageEditing.ResizeType.FILL_RECTANGLE_WITH_SAME_ASPECT_RATIO, true);
            if (this.projectAndSceneScreenshotData.projectName == null) {
                strConcat = "";
            } else {
                strConcat = this.projectAndSceneScreenshotData.projectName;
            }
            if (this.projectAndSceneScreenshotData.sceneName != null) {
                strConcat = strConcat.concat(this.projectAndSceneScreenshotData.sceneName);
            }
            ProjectAndSceneScreenshotLoader.this.imageCache.put(strConcat, scaledBitmapFromPath);
            if (ProjectAndSceneScreenshotLoader.this.imageViewReused(this.projectAndSceneScreenshotData) || (activityFromView = UiUtils.getActivityFromView(this.projectAndSceneScreenshotData.imageView)) == null) {
                return;
            }
            activityFromView.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader.ScreenshotLoader.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ProjectAndSceneScreenshotLoader.this.imageViewReused(ScreenshotLoader.this.projectAndSceneScreenshotData)) {
                        return;
                    }
                    if (scaledBitmapFromPath != null) {
                        ScreenshotLoader.this.projectAndSceneScreenshotData.imageView.setImageBitmap(scaledBitmapFromPath);
                    } else {
                        ScreenshotLoader.this.projectAndSceneScreenshotData.imageView.setImageBitmap(null);
                    }
                }
            });
        }

        File getScreenshotFile() {
            File file;
            File file2;
            if (this.projectAndSceneScreenshotData.sceneName != null) {
                if (this.projectAndSceneScreenshotData.isBackpackScene) {
                    File file3 = new File(BackpackListManager.getInstance().backpackSceneDirectory, this.projectAndSceneScreenshotData.sceneName);
                    file = new File(file3, Constants.SCREENSHOT_MANUAL_FILE_NAME);
                    file2 = new File(file3, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
                } else {
                    File file4 = new File(new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, this.projectAndSceneScreenshotData.projectName), this.projectAndSceneScreenshotData.sceneName);
                    file = new File(file4, Constants.SCREENSHOT_MANUAL_FILE_NAME);
                    file2 = new File(file4, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
                }
            } else {
                File file5 = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, this.projectAndSceneScreenshotData.projectName);
                file = new File(file5, Constants.SCREENSHOT_MANUAL_FILE_NAME);
                file2 = new File(file5, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
                if (!file2.exists() && !file.exists()) {
                    try {
                        ResourceImporter.createImageFileFromResourcesInDirectory(ProjectManager.getInstance().getApplicationContext().getResources(), ProjectAndSceneScreenshotLoader.placeholderImages[new Random().nextInt(ProjectAndSceneScreenshotLoader.placeholderImages.length)], file5, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME, 1.0d);
                    } catch (IOException e) {
                        Log.e("ProjectAndSceneScreenshotLoader", "Cannot create placeholder image for project" + file5.getAbsolutePath(), e);
                    }
                    file2 = new File(file5, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
                }
            }
            if (file.exists() && file.length() > 0) {
                file2.delete();
                return file;
            }
            file.delete();
            return file2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean imageViewReused(ScreenshotData projectScreenshotData) {
        String strConcat;
        String str = this.imageViews.get(projectScreenshotData.imageView);
        if (projectScreenshotData.projectName == null) {
            strConcat = "";
        } else {
            strConcat = projectScreenshotData.projectName;
        }
        if (projectScreenshotData.sceneName != null) {
            strConcat = strConcat.concat(projectScreenshotData.sceneName);
        }
        return str == null || !str.equals(strConcat);
    }
}
