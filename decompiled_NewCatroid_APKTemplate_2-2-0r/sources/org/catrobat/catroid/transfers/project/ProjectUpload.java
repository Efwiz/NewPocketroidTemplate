package org.catrobat.catroid.transfers.project;

import android.content.SharedPreferences;
import android.util.Log;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.utils.ImageEditing;
import org.catrobat.catroid.web.ServerCalls;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/project/ProjectUpload.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectUpload.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 )2\u00020\u0001:\u0001)B]\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0003H\u0002J%\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0004\u001a\u00020\u00052\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\tH\u0002¢\u0006\u0002\u0010\u001aJi\u0010\u001b\u001a\u00020\u00192%\u0010\u001c\u001a!\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b( \u0012\u0004\u0012\u00020\u00190\u001dj\u0002`!2:\u0010\"\u001a6\u0012\u0013\u0012\u00110$¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b(%\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020\u00190#j\u0002`'J\u001a\u0010(\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003H\u0002R\u000e\u0010\n\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/catrobat/catroid/transfers/project/ProjectUpload;", "", "projectDirectory", "Ljava/io/File;", "projectName", "", Constants.EXTRA_PROJECT_DESCRIPTION, Constants.EXTRA_USER_EMAIL, Constants.EXTRA_SCENE_NAMES, "", "archiveDirectory", "zipArchiver", "Lorg/catrobat/catroid/io/ZipArchiver;", "screenshotLoader", "Lorg/catrobat/catroid/io/ProjectAndSceneScreenshotLoader;", "sharedPreferences", "Landroid/content/SharedPreferences;", "serverCalls", "Lorg/catrobat/catroid/web/ServerCalls;", "(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/io/File;Lorg/catrobat/catroid/io/ZipArchiver;Lorg/catrobat/catroid/io/ProjectAndSceneScreenshotLoader;Landroid/content/SharedPreferences;Lorg/catrobat/catroid/web/ServerCalls;)V", "[Ljava/lang/String;", "createUploadData", "Lorg/catrobat/catroid/transfers/project/ProjectUploadData;", "projectArchive", "scaleSceneScreenshots", "", "(Ljava/lang/String;[Ljava/lang/String;)V", "start", "successCallback", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", Constants.EXTRA_PROJECT_ID, "Lorg/catrobat/catroid/transfers/project/UploadProjectSuccessCallback;", "errorCallback", "Lkotlin/Function2;", "", "errorCode", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "Lorg/catrobat/catroid/transfers/project/UploadProjectErrorCallback;", "zipProjectToArchive", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectUpload {
    private static final String TAG = "ProjectUpload";
    public static final int UPLOAD_ZIP_ERROR = 32202;
    public static final String UPLOAD_ZIP_ERROR_MESSAGE = "Failed to zip directory for upload";
    private final File archiveDirectory;
    private final String projectDescription;
    private final File projectDirectory;
    private final String projectName;
    private final String[] sceneNames;
    private final ProjectAndSceneScreenshotLoader screenshotLoader;
    private final ServerCalls serverCalls;
    private final SharedPreferences sharedPreferences;
    private final String userEmail;
    private final ZipArchiver zipArchiver;

    public ProjectUpload(File projectDirectory, String projectName, String projectDescription, String userEmail, String[] strArr, File archiveDirectory, ZipArchiver zipArchiver, ProjectAndSceneScreenshotLoader screenshotLoader, SharedPreferences sharedPreferences, ServerCalls serverCalls) {
        Intrinsics.checkNotNullParameter(projectDirectory, "projectDirectory");
        Intrinsics.checkNotNullParameter(projectName, "projectName");
        Intrinsics.checkNotNullParameter(projectDescription, "projectDescription");
        Intrinsics.checkNotNullParameter(userEmail, "userEmail");
        Intrinsics.checkNotNullParameter(archiveDirectory, "archiveDirectory");
        Intrinsics.checkNotNullParameter(zipArchiver, "zipArchiver");
        Intrinsics.checkNotNullParameter(screenshotLoader, "screenshotLoader");
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        Intrinsics.checkNotNullParameter(serverCalls, "serverCalls");
        this.projectDirectory = projectDirectory;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.userEmail = userEmail;
        this.sceneNames = strArr;
        this.archiveDirectory = archiveDirectory;
        this.zipArchiver = zipArchiver;
        this.screenshotLoader = screenshotLoader;
        this.sharedPreferences = sharedPreferences;
        this.serverCalls = serverCalls;
    }

    public final void start(final Function1<? super String, Unit> successCallback, final Function2<? super Integer, ? super String, Unit> errorCallback) {
        Intrinsics.checkNotNullParameter(successCallback, "successCallback");
        Intrinsics.checkNotNullParameter(errorCallback, "errorCallback");
        final File fileZipProjectToArchive = zipProjectToArchive(this.projectDirectory, this.archiveDirectory);
        if (fileZipProjectToArchive == null) {
            errorCallback.invoke(Integer.valueOf(UPLOAD_ZIP_ERROR), UPLOAD_ZIP_ERROR_MESSAGE);
            return;
        }
        ProjectUploadData projectUploadDataCreateUploadData = createUploadData(fileZipProjectToArchive);
        scaleSceneScreenshots(this.projectName, this.sceneNames);
        this.serverCalls.uploadProject(projectUploadDataCreateUploadData, new ServerCalls.UploadSuccessCallback() { // from class: org.catrobat.catroid.transfers.project.ProjectUpload$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.web.ServerCalls.UploadSuccessCallback
            public final void onSuccess(String str, String str2, String str3) {
                ProjectUpload.start$lambda$0(this.f$0, successCallback, fileZipProjectToArchive, str, str2, str3);
            }
        }, new ServerCalls.UploadErrorCallback() { // from class: org.catrobat.catroid.transfers.project.ProjectUpload$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.web.ServerCalls.UploadErrorCallback
            public final void onError(int i, String str) {
                ProjectUpload.start$lambda$1(errorCallback, i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void start$lambda$0(ProjectUpload this$0, Function1 successCallback, File file, String str, String str2, String str3) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(successCallback, "$successCallback");
        this$0.sharedPreferences.edit().putString("token", str3).putString("username", str2).apply();
        Intrinsics.checkNotNull(str);
        successCallback.invoke(str);
        file.delete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void start$lambda$1(Function2 errorCallback, int i, String str) {
        Intrinsics.checkNotNullParameter(errorCallback, "$errorCallback");
        Integer numValueOf = Integer.valueOf(i);
        Intrinsics.checkNotNull(str);
        errorCallback.invoke(numValueOf, str);
    }

    private final ProjectUploadData createUploadData(File projectArchive) {
        String string = this.sharedPreferences.getString("token", Constants.NO_TOKEN);
        String string2 = this.sharedPreferences.getString("username", Constants.NO_USERNAME);
        String str = this.projectName;
        String str2 = this.projectDescription;
        String str3 = this.userEmail;
        String language = Locale.getDefault().getLanguage();
        Intrinsics.checkNotNullExpressionValue(language, "getLanguage(...)");
        return new ProjectUploadData(str, str2, projectArchive, str3, language, string == null ? Constants.NO_TOKEN : string, string2 == null ? Constants.NO_USERNAME : string2);
    }

    private final File zipProjectToArchive(File projectDirectory, File archiveDirectory) {
        try {
            File[] fileArrListFiles = projectDirectory.listFiles();
            Intrinsics.checkNotNull(fileArrListFiles);
            ArrayList arrayList = new ArrayList();
            for (File file : fileArrListFiles) {
                if (!Intrinsics.areEqual(file.getName(), Constants.DEVICE_VARIABLE_JSON_FILE_NAME)) {
                    arrayList.add(file);
                }
            }
            this.zipArchiver.zip(archiveDirectory, (File[]) arrayList.toArray(new File[0]));
            return archiveDirectory;
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            archiveDirectory.delete();
            return null;
        }
    }

    private final void scaleSceneScreenshots(String projectName, String[] sceneNames) {
        if (sceneNames != null) {
            ArrayList arrayList = new ArrayList();
            for (String str : sceneNames) {
                File screenshotFile = this.screenshotLoader.getScreenshotFile(projectName, str, false);
                if (screenshotFile != null) {
                    arrayList.add(screenshotFile);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                File file = (File) obj;
                if (file.exists() && file.length() > 0) {
                    arrayList2.add(obj);
                }
            }
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                try {
                    ImageEditing.scaleImageFile((File) it.next(), 480, 480);
                } catch (FileNotFoundException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
    }
}
