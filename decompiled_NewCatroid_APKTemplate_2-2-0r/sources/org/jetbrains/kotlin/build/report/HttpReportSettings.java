package org.jetbrains.kotlin.build.report;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.transport.AmazonS3;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: reportSettings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J?\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/build/report/HttpReportSettings;", "Ljava/io/Serializable;", "url", "", AmazonS3.Keys.PASSWORD, ConfigConstants.CONFIG_USER_SECTION, "verboseEnvironment", "", "includeGitBranchName", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZ)V", "getIncludeGitBranchName", "()Z", "getPassword", "()Ljava/lang/String;", "getUrl", "getUser", "getVerboseEnvironment", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class HttpReportSettings implements Serializable {
    public static final long serialVersionUID = 0;
    private final boolean includeGitBranchName;
    private final String password;
    private final String url;
    private final String user;
    private final boolean verboseEnvironment;

    public static /* synthetic */ HttpReportSettings copy$default(HttpReportSettings httpReportSettings, String str, String str2, String str3, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = httpReportSettings.url;
        }
        if ((i & 2) != 0) {
            str2 = httpReportSettings.password;
        }
        String str4 = str2;
        if ((i & 4) != 0) {
            str3 = httpReportSettings.user;
        }
        String str5 = str3;
        if ((i & 8) != 0) {
            z = httpReportSettings.verboseEnvironment;
        }
        boolean z3 = z;
        if ((i & 16) != 0) {
            z2 = httpReportSettings.includeGitBranchName;
        }
        return httpReportSettings.copy(str, str4, str5, z3, z2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPassword() {
        return this.password;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getUser() {
        return this.user;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getVerboseEnvironment() {
        return this.verboseEnvironment;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getIncludeGitBranchName() {
        return this.includeGitBranchName;
    }

    public final HttpReportSettings copy(String url, String password, String user, boolean verboseEnvironment, boolean includeGitBranchName) {
        Intrinsics.checkNotNullParameter(url, "url");
        return new HttpReportSettings(url, password, user, verboseEnvironment, includeGitBranchName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HttpReportSettings)) {
            return false;
        }
        HttpReportSettings httpReportSettings = (HttpReportSettings) other;
        return Intrinsics.areEqual(this.url, httpReportSettings.url) && Intrinsics.areEqual(this.password, httpReportSettings.password) && Intrinsics.areEqual(this.user, httpReportSettings.user) && this.verboseEnvironment == httpReportSettings.verboseEnvironment && this.includeGitBranchName == httpReportSettings.includeGitBranchName;
    }

    public int hashCode() {
        int iHashCode = this.url.hashCode() * 31;
        String str = this.password;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.user;
        return ((((iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31) + Boolean.hashCode(this.verboseEnvironment)) * 31) + Boolean.hashCode(this.includeGitBranchName);
    }

    public String toString() {
        return "HttpReportSettings(url=" + this.url + ", password=" + this.password + ", user=" + this.user + ", verboseEnvironment=" + this.verboseEnvironment + ", includeGitBranchName=" + this.includeGitBranchName + ')';
    }

    public HttpReportSettings(String url, String str, String str2, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(url, "url");
        this.url = url;
        this.password = str;
        this.user = str2;
        this.verboseEnvironment = z;
        this.includeGitBranchName = z2;
    }

    public final String getUrl() {
        return this.url;
    }

    public final String getPassword() {
        return this.password;
    }

    public final String getUser() {
        return this.user;
    }

    public final boolean getVerboseEnvironment() {
        return this.verboseEnvironment;
    }

    public final boolean getIncludeGitBranchName() {
        return this.includeGitBranchName;
    }
}
