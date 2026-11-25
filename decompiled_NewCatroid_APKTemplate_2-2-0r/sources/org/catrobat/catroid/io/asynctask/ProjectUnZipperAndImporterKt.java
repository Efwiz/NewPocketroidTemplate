package org.catrobat.catroid.io.asynctask;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.content.backwardcompatibility.ProjectMetaDataParser;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectUnZipperAndImporterKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectUnZipperAndImporter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\n\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u0006H\u0002\u001a\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\"\u0016\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "copyProject", "", ProjectUploadActivityKt.PROJECT_DIR, "Ljava/io/File;", "destinationDirectory", "projectName", "errorWhileImporting", "getProjectName", "importProject", "", "importStandardProject", "cachedProjectDir", "unzipAndImportProject", "Lorg/catrobat/catroid/io/asynctask/ImportResult;", "projectZipFile", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProjectUnZipperAndImporterKt {
    private static final String TAG = "ProjectUnZipperAndImporter";

    /* JADX INFO: Access modifiers changed from: private */
    public static final ImportResult unzipAndImportProject(File file) {
        try {
            File file2 = new File(Constants.CACHE_DIRECTORY, StorageOperations.getSanitizedFileName(file.getName()) + "_temp_import");
            if (file2.isDirectory()) {
                StorageOperations.deleteDir(file2);
            }
            file2.mkdirs();
            new ZipArchiver().unzip(file, file2);
            File file3 = new File(file2, Constants.CODE_XML_FILE_NAME);
            File file4 = new File(file2, "init.luno.txt");
            File file5 = new File(file2, "init.bin");
            if (file3.exists()) {
                if (importStandardProject(file2)) {
                    StorageOperations.deleteDir(file2);
                    return ImportResult.Success.INSTANCE;
                }
                return ImportResult.Failure.INSTANCE;
            }
            if (!file4.exists() && !file5.exists()) {
                Log.e(TAG, "Invalid project structure: No code.xml and no init script found.");
                return ImportResult.Failure.INSTANCE;
            }
            Log.d(TAG, "Detected baked project in: " + file2.getAbsolutePath());
            return new ImportResult.BakedProject(file2);
        } catch (IOException e) {
            Log.e(TAG, "Cannot unzip project " + file.getName(), e);
            return ImportResult.Failure.INSTANCE;
        }
    }

    private static final boolean importStandardProject(File file) throws Throwable {
        String projectName = getProjectName(file);
        if (projectName == null) {
            return false;
        }
        String uniqueName = new UniqueNameProvider().getUniqueName(projectName, FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY));
        File file2 = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(uniqueName));
        try {
            Intrinsics.checkNotNull(uniqueName);
            copyProject(file, file2, uniqueName);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Something went wrong while importing project", e);
            errorWhileImporting(file, file2);
            return false;
        }
    }

    private static final String getProjectName(File file) {
        File file2 = new File(file, Constants.CODE_XML_FILE_NAME);
        if (!file2.exists()) {
            Log.e(TAG, "No xml file found for project " + file.getName());
            return null;
        }
        try {
            return new ProjectMetaDataParser(file2).getProjectMetaData().getName();
        } catch (IOException e) {
            Log.d(TAG, "Cannot extract projectName from xml", e);
            return null;
        }
    }

    private static final boolean importProject(File file) throws Throwable {
        String projectName = getProjectName(file);
        if (projectName == null) {
            return false;
        }
        String uniqueName = new UniqueNameProvider().getUniqueName(projectName, FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY));
        Intrinsics.checkNotNullExpressionValue(uniqueName, "getUniqueName(...)");
        File file2 = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(uniqueName));
        try {
            copyProject(file, file2, uniqueName);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Something went wrong while importing project " + file.getName(), e);
            errorWhileImporting(file, file2);
            return false;
        }
    }

    private static final void copyProject(File file, File file2, String str) throws Throwable {
        StorageOperations.copyDir(file, file2);
        XstreamSerializer.renameProject(new File(file2, Constants.CODE_XML_FILE_NAME), str);
    }

    private static final void errorWhileImporting(File file, File file2) {
        if (file2.isDirectory()) {
            Log.e(TAG, "Folder exists, trying to delete folder.");
            try {
                StorageOperations.deleteDir(file);
            } catch (IOException e) {
                Log.e(TAG, "Cannot delete folder " + file, e);
            }
        }
    }
}
