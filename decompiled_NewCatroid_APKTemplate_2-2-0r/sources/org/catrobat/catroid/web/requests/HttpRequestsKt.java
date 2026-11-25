package org.catrobat.catroid.web.requests;

import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.catrobat.catroid.transfers.project.ProjectUploadData;
import org.catrobat.catroid.transfers.project.ProjectUploadServiceKt;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/requests/HttpRequestsKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: HttpRequests.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"DEVICE_LANGUAGE", "", "FILE_UPLOAD_TAG", "FILE_UPLOAD_URL", "MEDIA_TYPE_ZIPFILE", "Lokhttp3/MediaType;", "PROJECT_CHECKSUM_TAG", "PROJECT_DESCRIPTION_TAG", "PROJECT_NAME_TAG", "USER_EMAIL", "createUploadRequest", "Lokhttp3/Request;", "uploadData", "Lorg/catrobat/catroid/transfers/project/ProjectUploadData;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class HttpRequestsKt {
    private static final String DEVICE_LANGUAGE = "deviceLanguage";
    private static final String FILE_UPLOAD_TAG = "upload";
    private static final String FILE_UPLOAD_URL = "https://upload.catrob.at/pocketcode/api/upload/upload.json";
    private static final MediaType MEDIA_TYPE_ZIPFILE = MediaType.INSTANCE.parse("application/zip");
    private static final String PROJECT_CHECKSUM_TAG = "fileChecksum";
    private static final String PROJECT_DESCRIPTION_TAG = "projectDescription";
    private static final String PROJECT_NAME_TAG = "projectTitle";
    private static final String USER_EMAIL = "userEmail";

    public static final Request createUploadRequest(ProjectUploadData uploadData) throws Throwable {
        Intrinsics.checkNotNullParameter(uploadData, "uploadData");
        Log.v(ServerCalls.TAG, "Building request to upload to: https://upload.catrob.at/pocketcode/api/upload/upload.json");
        MultipartBody.Builder builderAddFormDataPart = new MultipartBody.Builder(null, 1, null).setType(MultipartBody.FORM).addFormDataPart(FILE_UPLOAD_TAG, ProjectUploadServiceKt.UPLOAD_FILE_NAME, RequestBody.INSTANCE.create(MEDIA_TYPE_ZIPFILE, uploadData.getProjectArchive())).addFormDataPart(PROJECT_NAME_TAG, uploadData.getProjectName()).addFormDataPart("projectDescription", uploadData.getProjectDescription()).addFormDataPart("userEmail", uploadData.getUserEmail());
        String strMd5Checksum = Utils.md5Checksum(uploadData.getProjectArchive());
        Intrinsics.checkNotNullExpressionValue(strMd5Checksum, "md5Checksum(...)");
        return new Request.Builder().url(FILE_UPLOAD_URL).post(builderAddFormDataPart.addFormDataPart(PROJECT_CHECKSUM_TAG, strMd5Checksum).addFormDataPart("token", uploadData.getToken()).addFormDataPart("username", uploadData.getUsername()).addFormDataPart("deviceLanguage", uploadData.getLanguage()).build()).build();
    }
}
