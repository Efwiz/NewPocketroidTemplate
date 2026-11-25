package org.catrobat.catroid.ide;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.value.ResConfigBase;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/ProjectConfig.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b(\b\u0086\b\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\u0006\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\r\u0012\b\b\u0002\u0010\u0011\u001a\u00020\r\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0007¢\u0006\u0002\u0010\u0014J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\rHÆ\u0003J\t\u0010'\u001a\u00020\rHÆ\u0003J\t\u0010(\u001a\u00020\u0007HÆ\u0003J\t\u0010)\u001a\u00020\u0007HÆ\u0003J\t\u0010*\u001a\u00020\u0003HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0007HÆ\u0003J\t\u0010-\u001a\u00020\u0003HÆ\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00030\nHÆ\u0003J\u000f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00030\nHÆ\u0003J\t\u00100\u001a\u00020\rHÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u000fHÆ\u0003J\u0099\u0001\u00102\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\b\b\u0002\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\r2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u0007HÆ\u0001J\u0013\u00103\u001a\u00020\r2\b\u00104\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00105\u001a\u00020\u0007HÖ\u0001J\t\u00106\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0011\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0015R\u0011\u0010\u0010\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0015R\u0011\u0010\u0012\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\n¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0013\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0017¨\u00067"}, d2 = {"Lorg/catrobat/catroid/ide/ProjectConfig;", "", "name", "", "packageName", "version", "versionCode", "", ResConfigBase.NAME_orientation, "permissions", "", "repositories", "runConsole", "", "signing", "Lorg/catrobat/catroid/ide/SigningConfig;", "isProtected", "isObfuscated", "minSdk", "targetSdk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;Ljava/util/List;ZLorg/catrobat/catroid/ide/SigningConfig;ZZII)V", "()Z", "getMinSdk", "()I", "getName", "()Ljava/lang/String;", "getOrientation", "getPackageName", "getPermissions", "()Ljava/util/List;", "getRepositories", "getRunConsole", "getSigning", "()Lorg/catrobat/catroid/ide/SigningConfig;", "getTargetSdk", "getVersion", "getVersionCode", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ProjectConfig {
    private final boolean isObfuscated;
    private final boolean isProtected;
    private final int minSdk;
    private final String name;
    private final String orientation;
    private final String packageName;
    private final List<String> permissions;
    private final List<String> repositories;
    private final boolean runConsole;
    private final SigningConfig signing;
    private final int targetSdk;
    private final String version;
    private final int versionCode;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final boolean getIsProtected() {
        return this.isProtected;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final boolean getIsObfuscated() {
        return this.isObfuscated;
    }

    /* JADX INFO: renamed from: component12, reason: from getter */
    public final int getMinSdk() {
        return this.minSdk;
    }

    /* JADX INFO: renamed from: component13, reason: from getter */
    public final int getTargetSdk() {
        return this.targetSdk;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPackageName() {
        return this.packageName;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getVersion() {
        return this.version;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getVersionCode() {
        return this.versionCode;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getOrientation() {
        return this.orientation;
    }

    public final List<String> component6() {
        return this.permissions;
    }

    public final List<String> component7() {
        return this.repositories;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final boolean getRunConsole() {
        return this.runConsole;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final SigningConfig getSigning() {
        return this.signing;
    }

    public final ProjectConfig copy(String name2, String packageName, String version, int versionCode, String orientation, List<String> permissions, List<String> repositories, boolean runConsole, SigningConfig signing, boolean isProtected, boolean isObfuscated, int minSdk, int targetSdk) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(orientation, "orientation");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        return new ProjectConfig(name2, packageName, version, versionCode, orientation, permissions, repositories, runConsole, signing, isProtected, isObfuscated, minSdk, targetSdk);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProjectConfig)) {
            return false;
        }
        ProjectConfig projectConfig = (ProjectConfig) other;
        return Intrinsics.areEqual(this.name, projectConfig.name) && Intrinsics.areEqual(this.packageName, projectConfig.packageName) && Intrinsics.areEqual(this.version, projectConfig.version) && this.versionCode == projectConfig.versionCode && Intrinsics.areEqual(this.orientation, projectConfig.orientation) && Intrinsics.areEqual(this.permissions, projectConfig.permissions) && Intrinsics.areEqual(this.repositories, projectConfig.repositories) && this.runConsole == projectConfig.runConsole && Intrinsics.areEqual(this.signing, projectConfig.signing) && this.isProtected == projectConfig.isProtected && this.isObfuscated == projectConfig.isObfuscated && this.minSdk == projectConfig.minSdk && this.targetSdk == projectConfig.targetSdk;
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((this.name.hashCode() * 31) + this.packageName.hashCode()) * 31) + this.version.hashCode()) * 31) + Integer.hashCode(this.versionCode)) * 31) + this.orientation.hashCode()) * 31) + this.permissions.hashCode()) * 31) + this.repositories.hashCode()) * 31) + Boolean.hashCode(this.runConsole)) * 31;
        SigningConfig signingConfig = this.signing;
        return ((((((((iHashCode + (signingConfig == null ? 0 : signingConfig.hashCode())) * 31) + Boolean.hashCode(this.isProtected)) * 31) + Boolean.hashCode(this.isObfuscated)) * 31) + Integer.hashCode(this.minSdk)) * 31) + Integer.hashCode(this.targetSdk);
    }

    public String toString() {
        return "ProjectConfig(name=" + this.name + ", packageName=" + this.packageName + ", version=" + this.version + ", versionCode=" + this.versionCode + ", orientation=" + this.orientation + ", permissions=" + this.permissions + ", repositories=" + this.repositories + ", runConsole=" + this.runConsole + ", signing=" + this.signing + ", isProtected=" + this.isProtected + ", isObfuscated=" + this.isObfuscated + ", minSdk=" + this.minSdk + ", targetSdk=" + this.targetSdk + Constant.AFTER_QUTO;
    }

    public ProjectConfig(String name2, String packageName, String version, int i, String orientation, List<String> permissions, List<String> repositories, boolean z, SigningConfig signingConfig, boolean z2, boolean z3, int i2, int i3) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(orientation, "orientation");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        this.name = name2;
        this.packageName = packageName;
        this.version = version;
        this.versionCode = i;
        this.orientation = orientation;
        this.permissions = permissions;
        this.repositories = repositories;
        this.runConsole = z;
        this.signing = signingConfig;
        this.isProtected = z2;
        this.isObfuscated = z3;
        this.minSdk = i2;
        this.targetSdk = i3;
    }

    public /* synthetic */ ProjectConfig(String str, String str2, String str3, int i, String str4, List list, List list2, boolean z, SigningConfig signingConfig, boolean z2, boolean z3, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, i, str4, list, list2, z, (i4 & 256) != 0 ? null : signingConfig, (i4 & 512) != 0 ? false : z2, (i4 & 1024) != 0 ? false : z3, (i4 & 2048) != 0 ? 21 : i2, (i4 & 4096) != 0 ? 34 : i3);
    }

    public final String getName() {
        return this.name;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getVersion() {
        return this.version;
    }

    public final int getVersionCode() {
        return this.versionCode;
    }

    public final String getOrientation() {
        return this.orientation;
    }

    public final List<String> getPermissions() {
        return this.permissions;
    }

    public final List<String> getRepositories() {
        return this.repositories;
    }

    public final boolean getRunConsole() {
        return this.runConsole;
    }

    public final SigningConfig getSigning() {
        return this.signing;
    }

    public final boolean isProtected() {
        return this.isProtected;
    }

    public final boolean isObfuscated() {
        return this.isObfuscated;
    }

    public final int getMinSdk() {
        return this.minSdk;
    }

    public final int getTargetSdk() {
        return this.targetSdk;
    }
}
