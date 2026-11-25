package org.catrobat.catroid.transfers.project;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/project/ProjectUploadData.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectUploadData.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003¢\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003JO\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020!HÖ\u0001J\t\u0010\"\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/transfers/project/ProjectUploadData;", "", "projectName", "", Constants.EXTRA_PROJECT_DESCRIPTION, "projectArchive", "Ljava/io/File;", Constants.EXTRA_USER_EMAIL, "language", "token", "username", "(Ljava/lang/String;Ljava/lang/String;Ljava/io/File;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getLanguage", "()Ljava/lang/String;", "getProjectArchive", "()Ljava/io/File;", "getProjectDescription", "getProjectName", "getToken", "getUserEmail", "getUsername", "component1", "component2", "component3", "component4", "component5", "component6", "component7", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ProjectUploadData {
    private final String language;
    private final File projectArchive;
    private final String projectDescription;
    private final String projectName;
    private final String token;
    private final String userEmail;
    private final String username;

    public static /* synthetic */ ProjectUploadData copy$default(ProjectUploadData projectUploadData, String str, String str2, File file, String str3, String str4, String str5, String str6, int i, Object obj) {
        if ((i & 1) != 0) {
            str = projectUploadData.projectName;
        }
        if ((i & 2) != 0) {
            str2 = projectUploadData.projectDescription;
        }
        String str7 = str2;
        if ((i & 4) != 0) {
            file = projectUploadData.projectArchive;
        }
        File file2 = file;
        if ((i & 8) != 0) {
            str3 = projectUploadData.userEmail;
        }
        String str8 = str3;
        if ((i & 16) != 0) {
            str4 = projectUploadData.language;
        }
        String str9 = str4;
        if ((i & 32) != 0) {
            str5 = projectUploadData.token;
        }
        String str10 = str5;
        if ((i & 64) != 0) {
            str6 = projectUploadData.username;
        }
        return projectUploadData.copy(str, str7, file2, str8, str9, str10, str6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getProjectName() {
        return this.projectName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getProjectDescription() {
        return this.projectDescription;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final File getProjectArchive() {
        return this.projectArchive;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getUserEmail() {
        return this.userEmail;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getLanguage() {
        return this.language;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getToken() {
        return this.token;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getUsername() {
        return this.username;
    }

    public final ProjectUploadData copy(String projectName, String projectDescription, File projectArchive, String userEmail, String language, String token, String username) {
        Intrinsics.checkNotNullParameter(projectName, "projectName");
        Intrinsics.checkNotNullParameter(projectDescription, "projectDescription");
        Intrinsics.checkNotNullParameter(projectArchive, "projectArchive");
        Intrinsics.checkNotNullParameter(userEmail, "userEmail");
        Intrinsics.checkNotNullParameter(language, "language");
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(username, "username");
        return new ProjectUploadData(projectName, projectDescription, projectArchive, userEmail, language, token, username);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProjectUploadData)) {
            return false;
        }
        ProjectUploadData projectUploadData = (ProjectUploadData) other;
        return Intrinsics.areEqual(this.projectName, projectUploadData.projectName) && Intrinsics.areEqual(this.projectDescription, projectUploadData.projectDescription) && Intrinsics.areEqual(this.projectArchive, projectUploadData.projectArchive) && Intrinsics.areEqual(this.userEmail, projectUploadData.userEmail) && Intrinsics.areEqual(this.language, projectUploadData.language) && Intrinsics.areEqual(this.token, projectUploadData.token) && Intrinsics.areEqual(this.username, projectUploadData.username);
    }

    public int hashCode() {
        return (((((((((((this.projectName.hashCode() * 31) + this.projectDescription.hashCode()) * 31) + this.projectArchive.hashCode()) * 31) + this.userEmail.hashCode()) * 31) + this.language.hashCode()) * 31) + this.token.hashCode()) * 31) + this.username.hashCode();
    }

    public String toString() {
        return "ProjectUploadData(projectName=" + this.projectName + ", projectDescription=" + this.projectDescription + ", projectArchive=" + this.projectArchive + ", userEmail=" + this.userEmail + ", language=" + this.language + ", token=" + this.token + ", username=" + this.username + Constant.AFTER_QUTO;
    }

    public ProjectUploadData(String projectName, String projectDescription, File projectArchive, String userEmail, String language, String token, String username) {
        Intrinsics.checkNotNullParameter(projectName, "projectName");
        Intrinsics.checkNotNullParameter(projectDescription, "projectDescription");
        Intrinsics.checkNotNullParameter(projectArchive, "projectArchive");
        Intrinsics.checkNotNullParameter(userEmail, "userEmail");
        Intrinsics.checkNotNullParameter(language, "language");
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(username, "username");
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectArchive = projectArchive;
        this.userEmail = userEmail;
        this.language = language;
        this.token = token;
        this.username = username;
    }

    public final String getProjectName() {
        return this.projectName;
    }

    public final String getProjectDescription() {
        return this.projectDescription;
    }

    public final File getProjectArchive() {
        return this.projectArchive;
    }

    public final String getUserEmail() {
        return this.userEmail;
    }

    public final String getLanguage() {
        return this.language;
    }

    public final String getToken() {
        return this.token;
    }

    public final String getUsername() {
        return this.username;
    }
}
