package org.catrobat.catroid.retrofit.models;

import com.google.android.gms.cast.MediaTrack;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/models/ProjectResponse.class
  classes14.dex
 */
/* JADX INFO: compiled from: Models.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\bJ\b\u0087\b\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u009b\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0006\u0010\b\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u0004\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0004\u0012\u0006\u0010\u0014\u001a\u00020\u0004\u0012\u0006\u0010\u0015\u001a\u00020\u0004\u0012\u0006\u0010\u0016\u001a\u00020\u0004\u0012\u0006\u0010\u0017\u001a\u00020\u0004\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u0004¢\u0006\u0002\u0010\u001bJ\t\u0010L\u001a\u00020\u0004HÆ\u0003J\u000f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010HÆ\u0003J\t\u0010N\u001a\u00020\u0012HÆ\u0003J\t\u0010O\u001a\u00020\u0004HÆ\u0003J\t\u0010P\u001a\u00020\u0004HÆ\u0003J\t\u0010Q\u001a\u00020\u0004HÆ\u0003J\t\u0010R\u001a\u00020\u0004HÆ\u0003J\t\u0010S\u001a\u00020\u0004HÆ\u0003J\t\u0010T\u001a\u00020\u0019HÆ\u0003J\t\u0010U\u001a\u00020\u0004HÆ\u0003J\t\u0010V\u001a\u00020\u0004HÆ\u0003J\t\u0010W\u001a\u00020\u0004HÆ\u0003J\t\u0010X\u001a\u00020\u0004HÆ\u0003J\t\u0010Y\u001a\u00020\u0004HÆ\u0003J\t\u0010Z\u001a\u00020\nHÆ\u0003J\t\u0010[\u001a\u00020\nHÆ\u0003J\t\u0010\\\u001a\u00020\rHÆ\u0003J\t\u0010]\u001a\u00020\u0004HÆ\u0003JÃ\u0001\u0010^\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00042\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00042\b\b\u0002\u0010\u0014\u001a\u00020\u00042\b\b\u0002\u0010\u0015\u001a\u00020\u00042\b\b\u0002\u0010\u0016\u001a\u00020\u00042\b\b\u0002\u0010\u0017\u001a\u00020\u00042\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u0004HÆ\u0001J\u0013\u0010_\u001a\u00020\r2\b\u0010`\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010a\u001a\u00020\nHÖ\u0001J\t\u0010b\u001a\u00020\u0004HÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u001a\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001d\"\u0004\b!\u0010\u001fR\u001a\u0010\u0007\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001d\"\u0004\b#\u0010\u001fR\u001a\u0010\u000b\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010\u0017\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u001d\"\u0004\b)\u0010\u001fR\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010\u000e\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u001d\"\u0004\b/\u0010\u001fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u001d\"\u0004\b1\u0010\u001fR\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u001d\"\u0004\b3\u0010\u001fR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001a\u0010\u0016\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\u001d\"\u0004\b9\u0010\u001fR\u001a\u0010\u0014\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u001d\"\u0004\b;\u0010\u001fR\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\u001d\"\u0004\b=\u0010\u001fR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010\u0013\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010\u001d\"\u0004\bG\u0010\u001fR\u001a\u0010\b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\u001d\"\u0004\bI\u0010\u001fR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010%\"\u0004\bK\u0010'¨\u0006c"}, d2 = {"Lorg/catrobat/catroid/retrofit/models/ProjectResponse;", "", "()V", "id", "", "name", "author", MediaTrack.ROLE_DESCRIPTION, "version", "views", "", "download", "private", "", "flavor", "tags", "", "uploaded", "", "uploadedString", "screenshotLarge", "screenshotSmall", "projectUrl", "downloadUrl", "fileSize", "", "categoryType", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIZLjava/lang/String;Ljava/util/List;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;)V", "getAuthor", "()Ljava/lang/String;", "setAuthor", "(Ljava/lang/String;)V", "getCategoryType", "setCategoryType", "getDescription", "setDescription", "getDownload", "()I", "setDownload", "(I)V", "getDownloadUrl", "setDownloadUrl", "getFileSize", "()D", "setFileSize", "(D)V", "getFlavor", "setFlavor", "getId", "setId", "getName", "setName", "getPrivate", "()Z", "setPrivate", "(Z)V", "getProjectUrl", "setProjectUrl", "getScreenshotLarge", "setScreenshotLarge", "getScreenshotSmall", "setScreenshotSmall", "getTags", "()Ljava/util/List;", "setTags", "(Ljava/util/List;)V", "getUploaded", "()J", "setUploaded", "(J)V", "getUploadedString", "setUploadedString", "getVersion", "setVersion", "getViews", "setViews", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ProjectResponse {
    private String author;
    private String categoryType;
    private String description;
    private int download;
    private String downloadUrl;
    private double fileSize;
    private String flavor;
    private String id;
    private String name;
    private boolean private;
    private String projectUrl;
    private String screenshotLarge;
    private String screenshotSmall;
    private List<String> tags;
    private long uploaded;
    private String uploadedString;
    private String version;
    private int views;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    public final List<String> component10() {
        return this.tags;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final long getUploaded() {
        return this.uploaded;
    }

    /* JADX INFO: renamed from: component12, reason: from getter */
    public final String getUploadedString() {
        return this.uploadedString;
    }

    /* JADX INFO: renamed from: component13, reason: from getter */
    public final String getScreenshotLarge() {
        return this.screenshotLarge;
    }

    /* JADX INFO: renamed from: component14, reason: from getter */
    public final String getScreenshotSmall() {
        return this.screenshotSmall;
    }

    /* JADX INFO: renamed from: component15, reason: from getter */
    public final String getProjectUrl() {
        return this.projectUrl;
    }

    /* JADX INFO: renamed from: component16, reason: from getter */
    public final String getDownloadUrl() {
        return this.downloadUrl;
    }

    /* JADX INFO: renamed from: component17, reason: from getter */
    public final double getFileSize() {
        return this.fileSize;
    }

    /* JADX INFO: renamed from: component18, reason: from getter */
    public final String getCategoryType() {
        return this.categoryType;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getAuthor() {
        return this.author;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getVersion() {
        return this.version;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final int getViews() {
        return this.views;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final int getDownload() {
        return this.download;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final boolean getPrivate() {
        return this.private;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final String getFlavor() {
        return this.flavor;
    }

    public final ProjectResponse copy(String id2, String name2, String author, String description, String version, int views, int download, boolean z, String flavor, List<String> tags, long uploaded, String uploadedString, String screenshotLarge, String screenshotSmall, String projectUrl, String downloadUrl, double fileSize, String categoryType) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(flavor, "flavor");
        Intrinsics.checkNotNullParameter(tags, "tags");
        Intrinsics.checkNotNullParameter(uploadedString, "uploadedString");
        Intrinsics.checkNotNullParameter(screenshotLarge, "screenshotLarge");
        Intrinsics.checkNotNullParameter(screenshotSmall, "screenshotSmall");
        Intrinsics.checkNotNullParameter(projectUrl, "projectUrl");
        Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
        Intrinsics.checkNotNullParameter(categoryType, "categoryType");
        return new ProjectResponse(id2, name2, author, description, version, views, download, z, flavor, tags, uploaded, uploadedString, screenshotLarge, screenshotSmall, projectUrl, downloadUrl, fileSize, categoryType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProjectResponse)) {
            return false;
        }
        ProjectResponse projectResponse = (ProjectResponse) other;
        return Intrinsics.areEqual(this.id, projectResponse.id) && Intrinsics.areEqual(this.name, projectResponse.name) && Intrinsics.areEqual(this.author, projectResponse.author) && Intrinsics.areEqual(this.description, projectResponse.description) && Intrinsics.areEqual(this.version, projectResponse.version) && this.views == projectResponse.views && this.download == projectResponse.download && this.private == projectResponse.private && Intrinsics.areEqual(this.flavor, projectResponse.flavor) && Intrinsics.areEqual(this.tags, projectResponse.tags) && this.uploaded == projectResponse.uploaded && Intrinsics.areEqual(this.uploadedString, projectResponse.uploadedString) && Intrinsics.areEqual(this.screenshotLarge, projectResponse.screenshotLarge) && Intrinsics.areEqual(this.screenshotSmall, projectResponse.screenshotSmall) && Intrinsics.areEqual(this.projectUrl, projectResponse.projectUrl) && Intrinsics.areEqual(this.downloadUrl, projectResponse.downloadUrl) && Double.compare(this.fileSize, projectResponse.fileSize) == 0 && Intrinsics.areEqual(this.categoryType, projectResponse.categoryType);
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.author.hashCode()) * 31) + this.description.hashCode()) * 31) + this.version.hashCode()) * 31) + Integer.hashCode(this.views)) * 31) + Integer.hashCode(this.download)) * 31) + Boolean.hashCode(this.private)) * 31) + this.flavor.hashCode()) * 31) + this.tags.hashCode()) * 31) + Long.hashCode(this.uploaded)) * 31) + this.uploadedString.hashCode()) * 31) + this.screenshotLarge.hashCode()) * 31) + this.screenshotSmall.hashCode()) * 31) + this.projectUrl.hashCode()) * 31) + this.downloadUrl.hashCode()) * 31) + Double.hashCode(this.fileSize)) * 31) + this.categoryType.hashCode();
    }

    public String toString() {
        return "ProjectResponse(id=" + this.id + ", name=" + this.name + ", author=" + this.author + ", description=" + this.description + ", version=" + this.version + ", views=" + this.views + ", download=" + this.download + ", private=" + this.private + ", flavor=" + this.flavor + ", tags=" + this.tags + ", uploaded=" + this.uploaded + ", uploadedString=" + this.uploadedString + ", screenshotLarge=" + this.screenshotLarge + ", screenshotSmall=" + this.screenshotSmall + ", projectUrl=" + this.projectUrl + ", downloadUrl=" + this.downloadUrl + ", fileSize=" + this.fileSize + ", categoryType=" + this.categoryType + Constant.AFTER_QUTO;
    }

    public ProjectResponse(String id2, String name2, String author, String description, String version, int i, int i2, boolean z, String flavor, List<String> tags, long j, String uploadedString, String screenshotLarge, String screenshotSmall, String projectUrl, String downloadUrl, double d, String categoryType) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(flavor, "flavor");
        Intrinsics.checkNotNullParameter(tags, "tags");
        Intrinsics.checkNotNullParameter(uploadedString, "uploadedString");
        Intrinsics.checkNotNullParameter(screenshotLarge, "screenshotLarge");
        Intrinsics.checkNotNullParameter(screenshotSmall, "screenshotSmall");
        Intrinsics.checkNotNullParameter(projectUrl, "projectUrl");
        Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
        Intrinsics.checkNotNullParameter(categoryType, "categoryType");
        this.id = id2;
        this.name = name2;
        this.author = author;
        this.description = description;
        this.version = version;
        this.views = i;
        this.download = i2;
        this.private = z;
        this.flavor = flavor;
        this.tags = tags;
        this.uploaded = j;
        this.uploadedString = uploadedString;
        this.screenshotLarge = screenshotLarge;
        this.screenshotSmall = screenshotSmall;
        this.projectUrl = projectUrl;
        this.downloadUrl = downloadUrl;
        this.fileSize = d;
        this.categoryType = categoryType;
    }

    public final String getId() {
        return this.id;
    }

    public final void setId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final String getAuthor() {
        return this.author;
    }

    public final void setAuthor(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.author = str;
    }

    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.description = str;
    }

    public final String getVersion() {
        return this.version;
    }

    public final void setVersion(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.version = str;
    }

    public final int getViews() {
        return this.views;
    }

    public final void setViews(int i) {
        this.views = i;
    }

    public final int getDownload() {
        return this.download;
    }

    public final void setDownload(int i) {
        this.download = i;
    }

    public final boolean getPrivate() {
        return this.private;
    }

    public final void setPrivate(boolean z) {
        this.private = z;
    }

    public final String getFlavor() {
        return this.flavor;
    }

    public final void setFlavor(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.flavor = str;
    }

    public final List<String> getTags() {
        return this.tags;
    }

    public final void setTags(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.tags = list;
    }

    public final long getUploaded() {
        return this.uploaded;
    }

    public final void setUploaded(long j) {
        this.uploaded = j;
    }

    public final String getUploadedString() {
        return this.uploadedString;
    }

    public final void setUploadedString(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.uploadedString = str;
    }

    public final String getScreenshotLarge() {
        return this.screenshotLarge;
    }

    public final void setScreenshotLarge(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.screenshotLarge = str;
    }

    public final String getScreenshotSmall() {
        return this.screenshotSmall;
    }

    public final void setScreenshotSmall(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.screenshotSmall = str;
    }

    public final String getProjectUrl() {
        return this.projectUrl;
    }

    public final void setProjectUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.projectUrl = str;
    }

    public final String getDownloadUrl() {
        return this.downloadUrl;
    }

    public final void setDownloadUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.downloadUrl = str;
    }

    public final double getFileSize() {
        return this.fileSize;
    }

    public final void setFileSize(double d) {
        this.fileSize = d;
    }

    public final String getCategoryType() {
        return this.categoryType;
    }

    public final void setCategoryType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.categoryType = str;
    }

    public ProjectResponse() {
        this("", "", "", "", "", 0, 0, false, "", CollectionsKt.emptyList(), 0L, "", "", "", "", "", 0.0d, "");
    }
}
