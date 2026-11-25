package org.catrobat.catroid.retrofit.models;

import com.google.android.gms.cast.MediaTrack;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/models/ProjectResponseApi.class
  classes14.dex
 */
/* JADX INFO: compiled from: Models.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b/\b\u0086\b\u0018\u00002\u00020\u0001B\u0093\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\u0006\u0010\u0014\u001a\u00020\u0003\u0012\u0006\u0010\u0015\u001a\u00020\u0003\u0012\u0006\u0010\u0016\u001a\u00020\u0003\u0012\u0006\u0010\u0017\u001a\u00020\u0018¢\u0006\u0002\u0010\u0019J\t\u00101\u001a\u00020\u0003HÆ\u0003J\u000f\u00102\u001a\b\u0012\u0004\u0012\u00020\u00030\u000fHÆ\u0003J\t\u00103\u001a\u00020\u0011HÆ\u0003J\t\u00104\u001a\u00020\u0003HÆ\u0003J\t\u00105\u001a\u00020\u0003HÆ\u0003J\t\u00106\u001a\u00020\u0003HÆ\u0003J\t\u00107\u001a\u00020\u0003HÆ\u0003J\t\u00108\u001a\u00020\u0003HÆ\u0003J\t\u00109\u001a\u00020\u0018HÆ\u0003J\t\u0010:\u001a\u00020\u0003HÆ\u0003J\t\u0010;\u001a\u00020\u0003HÆ\u0003J\t\u0010<\u001a\u00020\u0003HÆ\u0003J\t\u0010=\u001a\u00020\u0003HÆ\u0003J\t\u0010>\u001a\u00020\tHÆ\u0003J\t\u0010?\u001a\u00020\tHÆ\u0003J\t\u0010@\u001a\u00020\fHÆ\u0003J\t\u0010A\u001a\u00020\u0003HÆ\u0003J¹\u0001\u0010B\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u0018HÆ\u0001J\u0013\u0010C\u001a\u00020\f2\b\u0010D\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010E\u001a\u00020\tHÖ\u0001J\t\u0010F\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0016\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0015\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001bR\u0011\u0010\u0013\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001bR\u0011\u0010\u0014\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001bR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000f¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\u0012\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001bR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001e¨\u0006G"}, d2 = {"Lorg/catrobat/catroid/retrofit/models/ProjectResponseApi;", "", "id", "", "name", "author", MediaTrack.ROLE_DESCRIPTION, "version", "views", "", "download", "private", "", "flavor", "tags", "", "uploaded", "", "uploaded_string", "screenshot_large", "screenshot_small", "project_url", "download_url", "filesize", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIZLjava/lang/String;Ljava/util/List;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;D)V", "getAuthor", "()Ljava/lang/String;", "getDescription", "getDownload", "()I", "getDownload_url", "getFilesize", "()D", "getFlavor", "getId", "getName", "getPrivate", "()Z", "getProject_url", "getScreenshot_large", "getScreenshot_small", "getTags", "()Ljava/util/List;", "getUploaded", "()J", "getUploaded_string", "getVersion", "getViews", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ProjectResponseApi {
    private final String author;
    private final String description;
    private final int download;
    private final String download_url;
    private final double filesize;
    private final String flavor;
    private final String id;
    private final String name;
    private final boolean private;
    private final String project_url;
    private final String screenshot_large;
    private final String screenshot_small;
    private final List<String> tags;
    private final long uploaded;
    private final String uploaded_string;
    private final String version;
    private final int views;

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
    public final String getUploaded_string() {
        return this.uploaded_string;
    }

    /* JADX INFO: renamed from: component13, reason: from getter */
    public final String getScreenshot_large() {
        return this.screenshot_large;
    }

    /* JADX INFO: renamed from: component14, reason: from getter */
    public final String getScreenshot_small() {
        return this.screenshot_small;
    }

    /* JADX INFO: renamed from: component15, reason: from getter */
    public final String getProject_url() {
        return this.project_url;
    }

    /* JADX INFO: renamed from: component16, reason: from getter */
    public final String getDownload_url() {
        return this.download_url;
    }

    /* JADX INFO: renamed from: component17, reason: from getter */
    public final double getFilesize() {
        return this.filesize;
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

    public final ProjectResponseApi copy(String id2, String name2, String author, String description, String version, int views, int download, boolean z, String flavor, List<String> tags, long uploaded, String uploaded_string, String screenshot_large, String screenshot_small, String project_url, String download_url, double filesize) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(flavor, "flavor");
        Intrinsics.checkNotNullParameter(tags, "tags");
        Intrinsics.checkNotNullParameter(uploaded_string, "uploaded_string");
        Intrinsics.checkNotNullParameter(screenshot_large, "screenshot_large");
        Intrinsics.checkNotNullParameter(screenshot_small, "screenshot_small");
        Intrinsics.checkNotNullParameter(project_url, "project_url");
        Intrinsics.checkNotNullParameter(download_url, "download_url");
        return new ProjectResponseApi(id2, name2, author, description, version, views, download, z, flavor, tags, uploaded, uploaded_string, screenshot_large, screenshot_small, project_url, download_url, filesize);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProjectResponseApi)) {
            return false;
        }
        ProjectResponseApi projectResponseApi = (ProjectResponseApi) other;
        return Intrinsics.areEqual(this.id, projectResponseApi.id) && Intrinsics.areEqual(this.name, projectResponseApi.name) && Intrinsics.areEqual(this.author, projectResponseApi.author) && Intrinsics.areEqual(this.description, projectResponseApi.description) && Intrinsics.areEqual(this.version, projectResponseApi.version) && this.views == projectResponseApi.views && this.download == projectResponseApi.download && this.private == projectResponseApi.private && Intrinsics.areEqual(this.flavor, projectResponseApi.flavor) && Intrinsics.areEqual(this.tags, projectResponseApi.tags) && this.uploaded == projectResponseApi.uploaded && Intrinsics.areEqual(this.uploaded_string, projectResponseApi.uploaded_string) && Intrinsics.areEqual(this.screenshot_large, projectResponseApi.screenshot_large) && Intrinsics.areEqual(this.screenshot_small, projectResponseApi.screenshot_small) && Intrinsics.areEqual(this.project_url, projectResponseApi.project_url) && Intrinsics.areEqual(this.download_url, projectResponseApi.download_url) && Double.compare(this.filesize, projectResponseApi.filesize) == 0;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.author.hashCode()) * 31) + this.description.hashCode()) * 31) + this.version.hashCode()) * 31) + Integer.hashCode(this.views)) * 31) + Integer.hashCode(this.download)) * 31) + Boolean.hashCode(this.private)) * 31) + this.flavor.hashCode()) * 31) + this.tags.hashCode()) * 31) + Long.hashCode(this.uploaded)) * 31) + this.uploaded_string.hashCode()) * 31) + this.screenshot_large.hashCode()) * 31) + this.screenshot_small.hashCode()) * 31) + this.project_url.hashCode()) * 31) + this.download_url.hashCode()) * 31) + Double.hashCode(this.filesize);
    }

    public String toString() {
        return "ProjectResponseApi(id=" + this.id + ", name=" + this.name + ", author=" + this.author + ", description=" + this.description + ", version=" + this.version + ", views=" + this.views + ", download=" + this.download + ", private=" + this.private + ", flavor=" + this.flavor + ", tags=" + this.tags + ", uploaded=" + this.uploaded + ", uploaded_string=" + this.uploaded_string + ", screenshot_large=" + this.screenshot_large + ", screenshot_small=" + this.screenshot_small + ", project_url=" + this.project_url + ", download_url=" + this.download_url + ", filesize=" + this.filesize + Constant.AFTER_QUTO;
    }

    public ProjectResponseApi(String id2, String name2, String author, String description, String version, int i, int i2, boolean z, String flavor, List<String> tags, long j, String uploaded_string, String screenshot_large, String screenshot_small, String project_url, String download_url, double d) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(flavor, "flavor");
        Intrinsics.checkNotNullParameter(tags, "tags");
        Intrinsics.checkNotNullParameter(uploaded_string, "uploaded_string");
        Intrinsics.checkNotNullParameter(screenshot_large, "screenshot_large");
        Intrinsics.checkNotNullParameter(screenshot_small, "screenshot_small");
        Intrinsics.checkNotNullParameter(project_url, "project_url");
        Intrinsics.checkNotNullParameter(download_url, "download_url");
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
        this.uploaded_string = uploaded_string;
        this.screenshot_large = screenshot_large;
        this.screenshot_small = screenshot_small;
        this.project_url = project_url;
        this.download_url = download_url;
        this.filesize = d;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getAuthor() {
        return this.author;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getVersion() {
        return this.version;
    }

    public final int getViews() {
        return this.views;
    }

    public final int getDownload() {
        return this.download;
    }

    public final boolean getPrivate() {
        return this.private;
    }

    public final String getFlavor() {
        return this.flavor;
    }

    public final List<String> getTags() {
        return this.tags;
    }

    public final long getUploaded() {
        return this.uploaded;
    }

    public final String getUploaded_string() {
        return this.uploaded_string;
    }

    public final String getScreenshot_large() {
        return this.screenshot_large;
    }

    public final String getScreenshot_small() {
        return this.screenshot_small;
    }

    public final String getProject_url() {
        return this.project_url;
    }

    public final String getDownload_url() {
        return this.download_url;
    }

    public final double getFilesize() {
        return this.filesize;
    }
}
