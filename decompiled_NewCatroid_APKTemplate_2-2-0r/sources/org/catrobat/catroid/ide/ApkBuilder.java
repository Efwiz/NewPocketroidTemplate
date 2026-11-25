package org.catrobat.catroid.ide;

import android.R;
import android.content.Context;
import com.android.apksig.ApkSigner;
import com.android.dex.DexFormat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.apk.ApkModule;
import com.reandroid.apk.DexFileInputSource;
import com.reandroid.archive.FileInputSource;
import com.reandroid.archive.InputSource;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.chunk.xml.ResXmlElement;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfigBase;
import com.reandroid.arsc.value.ResValue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/ApkBuilder.class
  classes14.dex
 */
/* JADX INFO: compiled from: ApkBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u0017\u0018B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002JP\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00040\u0016¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/ide/ApkBuilder;", "", "()V", "addAssets", "", "module", "Lcom/reandroid/apk/ApkModule;", "folder", "Ljava/io/File;", "parentPath", "", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/catrobat/catroid/ide/ApkBuilder$BuildResult;", "context", "Landroid/content/Context;", Constants.EXTRA_PROJECT_PATH, "dexFiles", "", "config", "Lorg/catrobat/catroid/ide/ApkBuilder$BuildConfig;", "extraAssets", "onProgress", "Lkotlin/Function1;", "BuildConfig", "BuildResult", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApkBuilder {
    public static final ApkBuilder INSTANCE = new ApkBuilder();

    private ApkBuilder() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/ApkBuilder$BuildConfig.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ApkBuilder.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0007\u0012\u0006\u0010\u0010\u001a\u00020\u0007¢\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0007HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0007HÆ\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00030\tHÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\fHÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\t\u0010*\u001a\u00020\u0007HÆ\u0003Jw\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u0007HÆ\u0001J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010/\u001a\u00020\u0007HÖ\u0001J\t\u00100\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0010\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0013¨\u00061"}, d2 = {"Lorg/catrobat/catroid/ide/ApkBuilder$BuildConfig;", "", "appName", "", "packageName", "versionName", "versionCode", "", "permissions", "", ResConfigBase.NAME_orientation, "iconFile", "Ljava/io/File;", "signing", "Lorg/catrobat/catroid/ide/SigningConfig;", "minSdk", "targetSdk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/util/List;Ljava/lang/String;Ljava/io/File;Lorg/catrobat/catroid/ide/SigningConfig;II)V", "getAppName", "()Ljava/lang/String;", "getIconFile", "()Ljava/io/File;", "getMinSdk", "()I", "getOrientation", "getPackageName", "getPermissions", "()Ljava/util/List;", "getSigning", "()Lorg/catrobat/catroid/ide/SigningConfig;", "getTargetSdk", "getVersionCode", "getVersionName", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class BuildConfig {
        private final String appName;
        private final File iconFile;
        private final int minSdk;
        private final String orientation;
        private final String packageName;
        private final List<String> permissions;
        private final SigningConfig signing;
        private final int targetSdk;
        private final int versionCode;
        private final String versionName;

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getAppName() {
            return this.appName;
        }

        /* JADX INFO: renamed from: component10, reason: from getter */
        public final int getTargetSdk() {
            return this.targetSdk;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getPackageName() {
            return this.packageName;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getVersionName() {
            return this.versionName;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final int getVersionCode() {
            return this.versionCode;
        }

        public final List<String> component5() {
            return this.permissions;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final String getOrientation() {
            return this.orientation;
        }

        /* JADX INFO: renamed from: component7, reason: from getter */
        public final File getIconFile() {
            return this.iconFile;
        }

        /* JADX INFO: renamed from: component8, reason: from getter */
        public final SigningConfig getSigning() {
            return this.signing;
        }

        /* JADX INFO: renamed from: component9, reason: from getter */
        public final int getMinSdk() {
            return this.minSdk;
        }

        public final BuildConfig copy(String appName, String packageName, String versionName, int versionCode, List<String> permissions, String orientation, File iconFile, SigningConfig signing, int minSdk, int targetSdk) {
            Intrinsics.checkNotNullParameter(appName, "appName");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            Intrinsics.checkNotNullParameter(versionName, "versionName");
            Intrinsics.checkNotNullParameter(permissions, "permissions");
            Intrinsics.checkNotNullParameter(orientation, "orientation");
            return new BuildConfig(appName, packageName, versionName, versionCode, permissions, orientation, iconFile, signing, minSdk, targetSdk);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BuildConfig)) {
                return false;
            }
            BuildConfig buildConfig = (BuildConfig) other;
            return Intrinsics.areEqual(this.appName, buildConfig.appName) && Intrinsics.areEqual(this.packageName, buildConfig.packageName) && Intrinsics.areEqual(this.versionName, buildConfig.versionName) && this.versionCode == buildConfig.versionCode && Intrinsics.areEqual(this.permissions, buildConfig.permissions) && Intrinsics.areEqual(this.orientation, buildConfig.orientation) && Intrinsics.areEqual(this.iconFile, buildConfig.iconFile) && Intrinsics.areEqual(this.signing, buildConfig.signing) && this.minSdk == buildConfig.minSdk && this.targetSdk == buildConfig.targetSdk;
        }

        public int hashCode() {
            int iHashCode = ((((((((((this.appName.hashCode() * 31) + this.packageName.hashCode()) * 31) + this.versionName.hashCode()) * 31) + Integer.hashCode(this.versionCode)) * 31) + this.permissions.hashCode()) * 31) + this.orientation.hashCode()) * 31;
            File file = this.iconFile;
            int iHashCode2 = (iHashCode + (file == null ? 0 : file.hashCode())) * 31;
            SigningConfig signingConfig = this.signing;
            return ((((iHashCode2 + (signingConfig != null ? signingConfig.hashCode() : 0)) * 31) + Integer.hashCode(this.minSdk)) * 31) + Integer.hashCode(this.targetSdk);
        }

        public String toString() {
            return "BuildConfig(appName=" + this.appName + ", packageName=" + this.packageName + ", versionName=" + this.versionName + ", versionCode=" + this.versionCode + ", permissions=" + this.permissions + ", orientation=" + this.orientation + ", iconFile=" + this.iconFile + ", signing=" + this.signing + ", minSdk=" + this.minSdk + ", targetSdk=" + this.targetSdk + Constant.AFTER_QUTO;
        }

        public BuildConfig(String appName, String packageName, String versionName, int i, List<String> permissions, String orientation, File file, SigningConfig signingConfig, int i2, int i3) {
            Intrinsics.checkNotNullParameter(appName, "appName");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            Intrinsics.checkNotNullParameter(versionName, "versionName");
            Intrinsics.checkNotNullParameter(permissions, "permissions");
            Intrinsics.checkNotNullParameter(orientation, "orientation");
            this.appName = appName;
            this.packageName = packageName;
            this.versionName = versionName;
            this.versionCode = i;
            this.permissions = permissions;
            this.orientation = orientation;
            this.iconFile = file;
            this.signing = signingConfig;
            this.minSdk = i2;
            this.targetSdk = i3;
        }

        public final String getAppName() {
            return this.appName;
        }

        public final String getPackageName() {
            return this.packageName;
        }

        public final String getVersionName() {
            return this.versionName;
        }

        public final int getVersionCode() {
            return this.versionCode;
        }

        public final List<String> getPermissions() {
            return this.permissions;
        }

        public final String getOrientation() {
            return this.orientation;
        }

        public final File getIconFile() {
            return this.iconFile;
        }

        public final SigningConfig getSigning() {
            return this.signing;
        }

        public final int getMinSdk() {
            return this.minSdk;
        }

        public final int getTargetSdk() {
            return this.targetSdk;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/ApkBuilder$BuildResult.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ApkBuilder.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/ide/ApkBuilder$BuildResult;", "", "apkFile", "Ljava/io/File;", "error", "", "(Ljava/io/File;Ljava/lang/Throwable;)V", "getApkFile", "()Ljava/io/File;", "getError", "()Ljava/lang/Throwable;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class BuildResult {
        private final File apkFile;
        private final Throwable error;

        public static /* synthetic */ BuildResult copy$default(BuildResult buildResult, File file, Throwable th, int i, Object obj) {
            if ((i & 1) != 0) {
                file = buildResult.apkFile;
            }
            if ((i & 2) != 0) {
                th = buildResult.error;
            }
            return buildResult.copy(file, th);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final File getApkFile() {
            return this.apkFile;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Throwable getError() {
            return this.error;
        }

        public final BuildResult copy(File apkFile, Throwable error) {
            return new BuildResult(apkFile, error);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BuildResult)) {
                return false;
            }
            BuildResult buildResult = (BuildResult) other;
            return Intrinsics.areEqual(this.apkFile, buildResult.apkFile) && Intrinsics.areEqual(this.error, buildResult.error);
        }

        public int hashCode() {
            File file = this.apkFile;
            int iHashCode = (file == null ? 0 : file.hashCode()) * 31;
            Throwable th = this.error;
            return iHashCode + (th != null ? th.hashCode() : 0);
        }

        public String toString() {
            return "BuildResult(apkFile=" + this.apkFile + ", error=" + this.error + Constant.AFTER_QUTO;
        }

        public BuildResult(File file, Throwable th) {
            this.apkFile = file;
            this.error = th;
        }

        public /* synthetic */ BuildResult(File file, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(file, (i & 2) != 0 ? null : th);
        }

        public final File getApkFile() {
            return this.apkFile;
        }

        public final Throwable getError() {
            return this.error;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BuildResult build$default(ApkBuilder apkBuilder, Context context, String str, List list, BuildConfig buildConfig, List list2, Function1 function1, int i, Object obj) {
        if ((i & 16) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        return apkBuilder.build(context, str, list, buildConfig, list2, function1);
    }

    /* JADX WARN: Code duplicated, block: B:242:0x05b1 A[Catch: Exception -> 0x05e7, LOOP:12: B:240:0x05ab->B:242:0x05b1, LOOP_END, TryCatch #14 {Exception -> 0x05e7, blocks: (B:237:0x05a0, B:239:0x05a5, B:240:0x05ab, B:242:0x05b1, B:243:0x05bf, B:244:0x05c5, B:246:0x05cb), top: B:463:0x05a0 }] */
    /* JADX WARN: Code duplicated, block: B:246:0x05cb A[Catch: Exception -> 0x05e7, LOOP:13: B:244:0x05c5->B:246:0x05cb, LOOP_END, TRY_LEAVE, TryCatch #14 {Exception -> 0x05e7, blocks: (B:237:0x05a0, B:239:0x05a5, B:240:0x05ab, B:242:0x05b1, B:243:0x05bf, B:244:0x05c5, B:246:0x05cb), top: B:463:0x05a0 }] */
    /* JADX WARN: Code duplicated, block: B:257:0x0601 A[Catch: Exception -> 0x09e7, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:260:0x060b A[Catch: Exception -> 0x09e7, LOOP:14: B:258:0x0605->B:260:0x060b, LOOP_END, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:263:0x0642 A[Catch: Exception -> 0x09e7, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:264:0x0648  */
    /* JADX WARN: Code duplicated, block: B:268:0x0662 A[Catch: Exception -> 0x09e7, TRY_ENTER, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:270:0x067a A[Catch: Exception -> 0x09e7, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:272:0x0685 A[Catch: Exception -> 0x09e7, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:274:0x0696 A[Catch: Exception -> 0x09e7, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:279:0x06aa A[Catch: Exception -> 0x09e7, TRY_LEAVE, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:285:0x06c9  */
    /* JADX WARN: Code duplicated, block: B:286:0x06ce A[Catch: Exception -> 0x085d, TRY_ENTER, TRY_LEAVE, TryCatch #8 {Exception -> 0x085d, blocks: (B:282:0x06b6, B:286:0x06ce), top: B:452:0x06b6 }] */
    /* JADX WARN: Code duplicated, block: B:290:0x06e4  */
    /* JADX WARN: Code duplicated, block: B:291:0x06e6 A[Catch: Exception -> 0x085f, TryCatch #7 {Exception -> 0x085f, blocks: (B:298:0x0717, B:288:0x06de, B:291:0x06e6, B:294:0x06fe), top: B:450:0x0717 }] */
    /* JADX WARN: Code duplicated, block: B:293:0x06fb  */
    /* JADX WARN: Code duplicated, block: B:294:0x06fe A[Catch: Exception -> 0x085f, TryCatch #7 {Exception -> 0x085f, blocks: (B:298:0x0717, B:288:0x06de, B:291:0x06e6, B:294:0x06fe), top: B:450:0x0717 }] */
    /* JADX WARN: Code duplicated, block: B:296:0x0713  */
    /* JADX WARN: Code duplicated, block: B:297:0x0716  */
    /* JADX WARN: Code duplicated, block: B:302:0x072b A[Catch: all -> 0x084f, TRY_LEAVE, TryCatch #26 {all -> 0x084f, blocks: (B:299:0x071e, B:300:0x0725, B:302:0x072b), top: B:485:0x071e }] */
    /* JADX WARN: Code duplicated, block: B:307:0x0749 A[Catch: all -> 0x0835, TryCatch #11 {all -> 0x0835, blocks: (B:305:0x0743, B:307:0x0749, B:310:0x075d), top: B:457:0x0743 }] */
    /* JADX WARN: Code duplicated, block: B:309:0x075a  */
    /* JADX WARN: Code duplicated, block: B:310:0x075d A[Catch: all -> 0x0835, TRY_LEAVE, TryCatch #11 {all -> 0x0835, blocks: (B:305:0x0743, B:307:0x0749, B:310:0x075d), top: B:457:0x0743 }] */
    /* JADX WARN: Code duplicated, block: B:312:0x076a  */
    /* JADX WARN: Code duplicated, block: B:313:0x076d  */
    /* JADX WARN: Code duplicated, block: B:374:0x0882 A[Catch: Exception -> 0x09e7, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:377:0x0892 A[Catch: Exception -> 0x09e7, TryCatch #16 {Exception -> 0x09e7, blocks: (B:11:0x00b6, B:12:0x00c9, B:14:0x00cf, B:17:0x00dd, B:18:0x00e2, B:20:0x00e8, B:24:0x010c, B:23:0x00f3, B:25:0x011a, B:26:0x012b, B:29:0x0133, B:31:0x014b, B:33:0x0155, B:34:0x0159, B:36:0x015f, B:37:0x016d, B:39:0x0192, B:40:0x01a4, B:76:0x0205, B:78:0x020c, B:77:0x0209, B:42:0x01a9, B:46:0x01b4, B:50:0x01bf, B:54:0x01ca, B:58:0x01d6, B:62:0x01e1, B:66:0x01ec, B:70:0x01f7, B:79:0x021a, B:81:0x0237, B:82:0x023a, B:84:0x0240, B:85:0x0247, B:87:0x0251, B:88:0x025b, B:91:0x026e, B:93:0x027a, B:97:0x0284, B:99:0x0298, B:102:0x02b4, B:104:0x02c2, B:106:0x02cf, B:107:0x02d2, B:109:0x02db, B:110:0x02de, B:111:0x02e9, B:113:0x02ef, B:115:0x02fb, B:119:0x0305, B:121:0x0313, B:124:0x032f, B:126:0x0336, B:128:0x0346, B:129:0x034e, B:130:0x0381, B:132:0x0387, B:136:0x039e, B:138:0x03a4, B:141:0x03ac, B:143:0x03bc, B:145:0x03c7, B:146:0x03ca, B:148:0x03d6, B:150:0x03df, B:152:0x03e5, B:154:0x03f3, B:155:0x03f7, B:156:0x03fb, B:158:0x0401, B:161:0x040b, B:163:0x0411, B:165:0x041b, B:255:0x05f1, B:257:0x0601, B:258:0x0605, B:260:0x060b, B:261:0x0633, B:263:0x0642, B:265:0x064a, B:268:0x0662, B:270:0x067a, B:272:0x0685, B:274:0x0696, B:275:0x0699, B:276:0x069c, B:277:0x06a4, B:279:0x06aa, B:371:0x0873, B:372:0x0875, B:374:0x0882, B:375:0x088c, B:377:0x0892, B:379:0x089e, B:381:0x08ab, B:382:0x08d2, B:389:0x098f, B:407:0x09db, B:408:0x09de, B:405:0x09d7, B:253:0x05ec, B:409:0x09df, B:410:0x09e6, B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab, B:404:0x09b1), top: B:467:0x00b6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:385:0x08ec A[Catch: all -> 0x09ac, Exception -> 0x09af, TRY_LEAVE, TryCatch #1 {Exception -> 0x09af, blocks: (B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab), top: B:441:0x08e6, outer: #5 }] */
    /* JADX WARN: Code duplicated, block: B:398:0x09a3 A[Catch: all -> 0x09ac, Exception -> 0x09af, TryCatch #1 {Exception -> 0x09af, blocks: (B:383:0x08e6, B:385:0x08ec, B:388:0x091a, B:396:0x099f, B:397:0x09a2, B:398:0x09a3, B:399:0x09ab), top: B:441:0x08e6, outer: #5 }] */
    /* JADX WARN: Code duplicated, block: B:496:0x0771 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:551:0x0699 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:561:0x082a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:562:0x081b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:566:0x089e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:569:0x088c A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4, types: [int] */
    /* JADX WARN: Type inference failed for: r12v51 */
    public final BuildResult build(Context context, String projectPath, List<? extends File> dexFiles, BuildConfig config, List<? extends File> extraAssets, Function1<? super String, Unit> onProgress) {
        Exception exc;
        ApkModule apkModuleLoadApkFile;
        String str;
        boolean z;
        File file;
        File file2;
        boolean z2;
        File file3;
        SigningConfig signing;
        FileInputStream fileInputStream;
        File file4;
        File[] fileArrListFiles;
        ArrayList arrayList;
        int length;
        ?? r12;
        Iterator it;
        File file5;
        Iterator it2;
        boolean z3;
        String name2;
        String str2;
        String name3;
        String name4;
        String name5;
        String str3;
        ZipFile zipFile;
        Throwable th;
        ZipFile zipFile2;
        Enumeration<? extends ZipEntry> enumerationEntries;
        ZipEntry zipEntryNextElement;
        String name6;
        Enumeration<? extends ZipEntry> enumeration;
        String str4;
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        File file6;
        Iterator it3;
        Exception exc2;
        String valueAsString;
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        String valueAsString2;
        int i;
        String str5 = "next(...)";
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        Intrinsics.checkNotNullParameter(dexFiles, "dexFiles");
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(extraAssets, "extraAssets");
        Intrinsics.checkNotNullParameter(onProgress, "onProgress");
        File file7 = new File(projectPath);
        File file8 = new File(file7, JsonPOJOBuilder.DEFAULT_BUILD_METHOD);
        if (!file8.exists()) {
            file8.mkdirs();
        }
        File file9 = new File(context.getCacheDir(), "temp_template.apk");
        File file10 = new File(file8, "app-unsigned.apk");
        File file11 = new File(file8, "app-release.apk");
        try {
            onProgress.invoke("Подготовка шаблона...");
            InputStream inputStreamOpen = context.getAssets().open("template.apk");
            try {
                InputStream inputStream2 = inputStreamOpen;
                FileOutputStream fileOutputStream2 = new FileOutputStream(file9);
                try {
                    Intrinsics.checkNotNull(inputStream2);
                    ByteStreamsKt.copyTo$default(inputStream2, fileOutputStream2, 0, 2, null);
                    CloseableKt.closeFinally(fileOutputStream2, null);
                    CloseableKt.closeFinally(inputStreamOpen, null);
                    apkModuleLoadApkFile = ApkModule.loadApkFile(file9);
                    try {
                        onProgress.invoke("Внедрение кода...");
                        Intrinsics.checkNotNull(apkModuleLoadApkFile);
                        Iterator<DexFileInputSource> it4 = apkModuleLoadApkFile.listDexFiles().iterator();
                        int i2 = 1;
                        int i3 = 1;
                        while (it4.hasNext()) {
                            int dexNumber = it4.next().getDexNumber();
                            if (dexNumber > i3) {
                                i3 = dexNumber;
                            }
                        }
                        int i4 = i3 + 1;
                        Iterator<? extends File> it5 = dexFiles.iterator();
                        while (it5.hasNext()) {
                            apkModuleLoadApkFile.add(new FileInputSource(it5.next(), i4 == i2 ? DexFormat.DEX_IN_JAR_NAME : "classes" + i4 + ".dex"));
                            i4++;
                            i2 = 1;
                        }
                        ArrayList arrayList2 = new ArrayList();
                        Iterator<InputSource> it6 = apkModuleLoadApkFile.getZipEntryMap().listInputSources().iterator();
                        while (true) {
                            str = "getName(...)";
                            if (!it6.hasNext()) {
                                break;
                            }
                            InputSource next = it6.next();
                            String name7 = next.getName();
                            Intrinsics.checkNotNullExpressionValue(name7, "getName(...)");
                            Iterator<InputSource> it7 = it6;
                            if (StringsKt.startsWith$default(name7, "META-INF/", false, 2, (Object) null)) {
                                arrayList2.add(next.getName());
                            }
                            it6 = it7;
                        }
                        Iterator it8 = arrayList2.iterator();
                        while (it8.hasNext()) {
                            apkModuleLoadApkFile.getZipEntryMap().remove((String) it8.next());
                        }
                        onProgress.invoke("Настройка манифеста...");
                        AndroidManifestBlock androidManifestBlock = apkModuleLoadApkFile.getAndroidManifestBlock();
                        androidManifestBlock.setPackageName(config.getPackageName());
                        androidManifestBlock.setVersionName(config.getVersionName());
                        androidManifestBlock.setVersionCode(config.getVersionCode());
                        ResXmlElement mainActivity = androidManifestBlock.getMainActivity();
                        if (mainActivity != null) {
                            ResXmlAttribute orCreateAndroidAttribute = mainActivity.getOrCreateAndroidAttribute("screenOrientation", R.attr.screenOrientation);
                            String orientation = config.getOrientation();
                            switch (orientation.hashCode()) {
                                case -1883156447:
                                    i = orientation.equals("sensorLandscape") ? 6 : -1;
                                    break;
                                case -1392832198:
                                    if (orientation.equals("behind")) {
                                        i = 3;
                                    }
                                    break;
                                case -905948230:
                                    if (orientation.equals("sensor")) {
                                        i = 4;
                                    }
                                    break;
                                case -486008459:
                                    if (orientation.equals("sensorPortrait")) {
                                        i = 7;
                                    }
                                    break;
                                case 3599307:
                                    if (orientation.equals(ConfigConstants.CONFIG_USER_SECTION)) {
                                        i = 2;
                                    }
                                    break;
                                case 729267099:
                                    if (orientation.equals("portrait")) {
                                        i = 1;
                                    }
                                    break;
                                case 1430647483:
                                    if (orientation.equals("landscape")) {
                                        i = 0;
                                    }
                                    break;
                                case 1553288379:
                                    if (orientation.equals("nosensor")) {
                                        i = 5;
                                    }
                                    break;
                                default:
                                    break;
                            }
                            if (i != -1) {
                                orCreateAndroidAttribute.setValueAsDecimal(i);
                            } else {
                                mainActivity.removeAttribute(orCreateAndroidAttribute);
                            }
                            mainActivity.getOrCreateAndroidAttribute("configChanges", R.attr.configChanges).setValueAsDecimal(1184);
                        }
                        ResXmlElement applicationElement = androidManifestBlock.getApplicationElement();
                        applicationElement.getOrCreateAndroidAttribute(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, R.attr.label).setValueAsString(config.getAppName());
                        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId2 = applicationElement.searchAttributeByResourceId(R.attr.shortcutDisabledMessage);
                        if (resXmlAttributeSearchAttributeByResourceId2 != null) {
                            applicationElement.removeAttribute(resXmlAttributeSearchAttributeByResourceId2);
                        }
                        ResXmlAttribute resXmlAttributeSearchAttributeByName = applicationElement.searchAttributeByName("extractNativeLibs");
                        if (resXmlAttributeSearchAttributeByName == null) {
                            resXmlAttributeSearchAttributeByName = applicationElement.getOrCreateAndroidAttribute("extractNativeLibs", R.attr.glEsVersion);
                        }
                        resXmlAttributeSearchAttributeByName.setValueAsBoolean(true);
                        ResXmlElement manifestElement = androidManifestBlock.getManifestElement();
                        if (manifestElement == null) {
                            throw new RuntimeException("Manifest root element not found");
                        }
                        Iterator<ResXmlElement> it9 = manifestElement.listElements("permission").iterator();
                        while (true) {
                            boolean zHasNext = it9.hasNext();
                            String str6 = str;
                            int i5 = R.attr.name;
                            if (zHasNext) {
                                ResXmlAttribute resXmlAttributeSearchAttributeByResourceId3 = it9.next().searchAttributeByResourceId(R.attr.name);
                                String valueAsString3 = resXmlAttributeSearchAttributeByResourceId3 != null ? resXmlAttributeSearchAttributeByResourceId3.getValueAsString() : null;
                                if (valueAsString3 == null) {
                                    valueAsString3 = "";
                                }
                                Iterator<ResXmlElement> it10 = it9;
                                String str7 = str5;
                                File file12 = file7;
                                if (StringsKt.contains$default((CharSequence) valueAsString3, (CharSequence) "DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION", false, 2, (Object) null)) {
                                    String str8 = config.getPackageName() + ".DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION";
                                    if (resXmlAttributeSearchAttributeByResourceId3 != null) {
                                        resXmlAttributeSearchAttributeByResourceId3.setValueAsString(str8);
                                    }
                                }
                                str = str6;
                                it9 = it10;
                                file7 = file12;
                                str5 = str7;
                            } else {
                                String str9 = str5;
                                File file13 = file7;
                                ResXmlAttribute resXmlAttributeSearchAttributeByResourceId4 = applicationElement.searchAttributeByResourceId(R.attr.debuggable);
                                if (resXmlAttributeSearchAttributeByResourceId4 != null) {
                                    applicationElement.removeAttribute(resXmlAttributeSearchAttributeByResourceId4);
                                }
                                ResXmlAttribute resXmlAttributeSearchAttributeByResourceId5 = applicationElement.searchAttributeByResourceId(R.attr.testOnly);
                                if (resXmlAttributeSearchAttributeByResourceId5 != null) {
                                    applicationElement.removeAttribute(resXmlAttributeSearchAttributeByResourceId5);
                                }
                                Iterator<ResXmlElement> it11 = manifestElement.listElements("uses-permission").iterator();
                                while (it11.hasNext()) {
                                    ResXmlAttribute resXmlAttributeSearchAttributeByResourceId6 = it11.next().searchAttributeByResourceId(i5);
                                    String valueAsString4 = resXmlAttributeSearchAttributeByResourceId6 != null ? resXmlAttributeSearchAttributeByResourceId6.getValueAsString() : null;
                                    if (valueAsString4 == null) {
                                        valueAsString4 = "";
                                    }
                                    if (StringsKt.contains$default((CharSequence) valueAsString4, (CharSequence) "DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION", false, 2, (Object) null)) {
                                        String str10 = config.getPackageName() + ".DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION";
                                        if (resXmlAttributeSearchAttributeByResourceId6 != null) {
                                            resXmlAttributeSearchAttributeByResourceId6.setValueAsString(str10);
                                        }
                                    }
                                    i5 = R.attr.name;
                                }
                                onProgress.invoke("Настройка SDK версий...");
                                ResXmlElement element = androidManifestBlock.getManifestElement().getElement("uses-sdk");
                                if (element == null) {
                                    element = androidManifestBlock.getManifestElement().createChildElement("uses-sdk");
                                }
                                element.getOrCreateAndroidAttribute("minSdkVersion", R.attr.minSdkVersion).setValueAsDecimal(config.getMinSdk());
                                element.getOrCreateAndroidAttribute("targetSdkVersion", R.attr.targetSdkVersion).setValueAsDecimal(config.getTargetSdk());
                                androidManifestBlock.refresh();
                                onProgress.invoke("Оптимизация манифеста...");
                                ArrayList arrayList3 = new ArrayList();
                                Iterator<ResXmlElement> elements = applicationElement.getElements();
                                while (elements.hasNext()) {
                                    ResXmlElement next2 = elements.next();
                                    if (Intrinsics.areEqual(next2.getName(), Constants.EXTRA_PROVIDER)) {
                                        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId7 = next2.searchAttributeByResourceId(R.attr.name);
                                        String valueAsString5 = resXmlAttributeSearchAttributeByResourceId7 != null ? resXmlAttributeSearchAttributeByResourceId7.getValueAsString() : null;
                                        if (valueAsString5 != null && (StringsKt.contains$default((CharSequence) valueAsString5, (CharSequence) "androidx.startup", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) valueAsString5, (CharSequence) "androidx.profileinstaller", false, 2, (Object) null))) {
                                            arrayList3.add(next2);
                                        }
                                    }
                                    if (Intrinsics.areEqual(next2.getName(), "receiver") && (resXmlAttributeSearchAttributeByResourceId = next2.searchAttributeByResourceId(R.attr.name)) != null && (valueAsString2 = resXmlAttributeSearchAttributeByResourceId.getValueAsString()) != null && StringsKt.contains$default((CharSequence) valueAsString2, (CharSequence) "androidx.profileinstaller", false, 2, (Object) null)) {
                                        arrayList3.add(next2);
                                    }
                                }
                                Iterator it12 = arrayList3.iterator();
                                while (it12.hasNext()) {
                                    try {
                                        ((ResXmlElement) it12.next()).removeSelf();
                                    } catch (Exception unused) {
                                    }
                                }
                                if (config.getIconFile() == null || !config.getIconFile().exists()) {
                                    z = false;
                                } else {
                                    onProgress.invoke("Обновление графики...");
                                    try {
                                        apkModuleLoadApkFile.getTableBlock();
                                        ArrayList<String> arrayList4 = new ArrayList();
                                        ArrayList arrayList5 = new ArrayList();
                                        if (config.getIconFile() == null || !config.getIconFile().exists()) {
                                            z = false;
                                        } else {
                                            onProgress.invoke("Обновление графики...");
                                            try {
                                                TableBlock tableBlock = apkModuleLoadApkFile.getTableBlock();
                                                ArrayList arrayList6 = new ArrayList();
                                                ArrayList arrayList7 = new ArrayList();
                                                Iterable<PackageBlock> iterableListPackages = tableBlock.listPackages();
                                                Intrinsics.checkNotNullExpressionValue(iterableListPackages, "listPackages(...)");
                                                Iterator<PackageBlock> it13 = iterableListPackages.iterator();
                                                while (it13.hasNext()) {
                                                    Iterable<SpecTypePair> iterableListSpecTypePairs = it13.next().listSpecTypePairs();
                                                    Intrinsics.checkNotNullExpressionValue(iterableListSpecTypePairs, "listSpecTypePairs(...)");
                                                    Iterator<SpecTypePair> it14 = iterableListSpecTypePairs.iterator();
                                                    while (it14.hasNext()) {
                                                        SpecTypePair next3 = it14.next();
                                                        if (Intrinsics.areEqual(next3.getTypeName(), "mipmap")) {
                                                            Iterator<ResourceEntry> resources = next3.getResources();
                                                            while (resources.hasNext()) {
                                                                ResourceEntry next4 = resources.next();
                                                                String str11 = str9;
                                                                Intrinsics.checkNotNullExpressionValue(next4, str11);
                                                                ResourceEntry resourceEntry = next4;
                                                                String name8 = resourceEntry.getName();
                                                                if (name8 == null) {
                                                                    name8 = "";
                                                                } else {
                                                                    Intrinsics.checkNotNull(name8);
                                                                }
                                                                Iterator<PackageBlock> it15 = it13;
                                                                try {
                                                                    if (StringsKt.contains$default((CharSequence) name8, (CharSequence) "ic_launcher", false, 2, (Object) null)) {
                                                                        Iterator<Entry> it16 = resourceEntry.iterator();
                                                                        Intrinsics.checkNotNullExpressionValue(it16, "iterator(...)");
                                                                        while (it16.hasNext()) {
                                                                            Entry next5 = it16.next();
                                                                            Intrinsics.checkNotNullExpressionValue(next5, str11);
                                                                            ResValue resValue = next5.getResValue();
                                                                            if (resValue != null) {
                                                                                try {
                                                                                    valueAsString = resValue.getValueAsString();
                                                                                } catch (Exception e) {
                                                                                    exc2 = e;
                                                                                    z = false;
                                                                                    try {
                                                                                        exc2.printStackTrace();
                                                                                        it3 = arrayList5.iterator();
                                                                                        while (it3.hasNext()) {
                                                                                            apkModuleLoadApkFile.getZipEntryMap().remove((String) it3.next());
                                                                                        }
                                                                                        for (String str12 : arrayList4) {
                                                                                            apkModuleLoadApkFile.getZipEntryMap().remove(str12);
                                                                                            apkModuleLoadApkFile.add(new FileInputSource(config.getIconFile(), str12));
                                                                                        }
                                                                                    } catch (Exception e2) {
                                                                                        e = e2;
                                                                                        e.printStackTrace();
                                                                                    }
                                                                                    onProgress.invoke("Упаковка ресурсов...");
                                                                                    if (!extraAssets.isEmpty()) {
                                                                                        for (File file14 : extraAssets) {
                                                                                            apkModuleLoadApkFile.add(new FileInputSource(file14, "assets/" + file14.getName()));
                                                                                        }
                                                                                    }
                                                                                    file = new File(file13, "assets");
                                                                                    if (file.exists()) {
                                                                                        addAssets(apkModuleLoadApkFile, file, "assets");
                                                                                    }
                                                                                    onProgress.invoke("Упаковка библиотек (Native)...");
                                                                                    z2 = z;
                                                                                    file2 = new File(projectPath, "libs");
                                                                                    if (file2.exists()) {
                                                                                        file4 = new File(context.getCacheDir(), "temp_libs_apk");
                                                                                        FilesKt.deleteRecursively(file4);
                                                                                        file4.mkdirs();
                                                                                        fileArrListFiles = file2.listFiles();
                                                                                        if (fileArrListFiles != null) {
                                                                                            arrayList = new ArrayList();
                                                                                            length = fileArrListFiles.length;
                                                                                            for (r12 = z2; r12 < length; r12++) {
                                                                                                file6 = fileArrListFiles[r12];
                                                                                                Intrinsics.checkNotNull(file6);
                                                                                                if (Intrinsics.areEqual(FilesKt.getExtension(file6), "jar")) {
                                                                                                    arrayList.add(file6);
                                                                                                }
                                                                                            }
                                                                                            it = arrayList.iterator();
                                                                                            while (it.hasNext()) {
                                                                                                file5 = (File) it.next();
                                                                                                try {
                                                                                                    name2 = file5.getName();
                                                                                                    str6 = str6;
                                                                                                    try {
                                                                                                        Intrinsics.checkNotNullExpressionValue(name2, str6);
                                                                                                        str2 = "armeabi-v7a";
                                                                                                        if (StringsKt.contains$default(name2, "arm64-v8a", z2, 2, (Object) null)) {
                                                                                                            it2 = it;
                                                                                                            str3 = "arm64-v8a";
                                                                                                        } else {
                                                                                                            name3 = file5.getName();
                                                                                                            Intrinsics.checkNotNullExpressionValue(name3, str6);
                                                                                                            it2 = it;
                                                                                                            if (StringsKt.contains$default(name3, "armeabi-v7a", z2, 2, (Object) null)) {
                                                                                                                str3 = "armeabi-v7a";
                                                                                                            } else {
                                                                                                                name4 = file5.getName();
                                                                                                                Intrinsics.checkNotNullExpressionValue(name4, str6);
                                                                                                                if (StringsKt.contains$default(name4, "x86_64", z2, 2, (Object) null)) {
                                                                                                                    str3 = "x86_64";
                                                                                                                } else {
                                                                                                                    name5 = file5.getName();
                                                                                                                    Intrinsics.checkNotNullExpressionValue(name5, str6);
                                                                                                                    if (StringsKt.contains$default(name5, "x86", z2, 2, (Object) null)) {
                                                                                                                        str3 = "x86";
                                                                                                                    } else {
                                                                                                                        str3 = null;
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                        try {
                                                                                                            zipFile = new ZipFile(file5);
                                                                                                            try {
                                                                                                                zipFile2 = zipFile;
                                                                                                                enumerationEntries = zipFile2.entries();
                                                                                                                while (enumerationEntries.hasMoreElements()) {
                                                                                                                    zipEntryNextElement = enumerationEntries.nextElement();
                                                                                                                    try {
                                                                                                                        name6 = zipEntryNextElement.getName();
                                                                                                                        Intrinsics.checkNotNull(name6);
                                                                                                                        str3 = str3;
                                                                                                                        str6 = str6;
                                                                                                                        str2 = str2;
                                                                                                                        try {
                                                                                                                            if (StringsKt.endsWith$default(name6, ".so", false, 2, (Object) null)) {
                                                                                                                                enumeration = enumerationEntries;
                                                                                                                                if (StringsKt.contains$default((CharSequence) name6, (CharSequence) "lib/arm64-v8a", false, 2, (Object) null)) {
                                                                                                                                    str4 = "arm64-v8a";
                                                                                                                                } else if (StringsKt.contains$default((CharSequence) name6, (CharSequence) "lib/armeabi-v7a", false, 2, (Object) null)) {
                                                                                                                                    str4 = str2;
                                                                                                                                } else {
                                                                                                                                    str4 = str3;
                                                                                                                                }
                                                                                                                                if (str4 != null) {
                                                                                                                                    try {
                                                                                                                                        String name9 = new File(name6).getName();
                                                                                                                                        String str13 = "lib/" + str4 + "/" + name9;
                                                                                                                                        File file15 = new File(file4, str4 + "_" + name9);
                                                                                                                                        inputStream = zipFile2.getInputStream(zipEntryNextElement);
                                                                                                                                        try {
                                                                                                                                            InputStream inputStream3 = inputStream;
                                                                                                                                            fileOutputStream = new FileOutputStream(file15);
                                                                                                                                            try {
                                                                                                                                                Intrinsics.checkNotNull(inputStream3);
                                                                                                                                                file4 = file4;
                                                                                                                                                ZipFile zipFile3 = zipFile2;
                                                                                                                                                z3 = false;
                                                                                                                                                try {
                                                                                                                                                    ByteStreamsKt.copyTo$default(inputStream3, fileOutputStream, 0, 2, null);
                                                                                                                                                    try {
                                                                                                                                                        CloseableKt.closeFinally(fileOutputStream, null);
                                                                                                                                                        try {
                                                                                                                                                            CloseableKt.closeFinally(inputStream, null);
                                                                                                                                                            apkModuleLoadApkFile.getZipEntryMap().remove(str13);
                                                                                                                                                            apkModuleLoadApkFile.add(new FileInputSource(file15, str13));
                                                                                                                                                            str3 = str3;
                                                                                                                                                            str2 = str2;
                                                                                                                                                            z2 = false;
                                                                                                                                                            enumerationEntries = enumeration;
                                                                                                                                                            file4 = file4;
                                                                                                                                                            zipFile2 = zipFile3;
                                                                                                                                                        } catch (Throwable th2) {
                                                                                                                                                            th = th2;
                                                                                                                                                            th = th;
                                                                                                                                                            try {
                                                                                                                                                                throw th;
                                                                                                                                                            } catch (Throwable th3) {
                                                                                                                                                                CloseableKt.closeFinally(zipFile, th);
                                                                                                                                                                throw th3;
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    } catch (Throwable th4) {
                                                                                                                                                        th = th4;
                                                                                                                                                        Throwable th5 = th;
                                                                                                                                                        try {
                                                                                                                                                            throw th5;
                                                                                                                                                        } catch (Throwable th6) {
                                                                                                                                                            CloseableKt.closeFinally(inputStream, th5);
                                                                                                                                                            throw th6;
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                } catch (Throwable th7) {
                                                                                                                                                    th = th7;
                                                                                                                                                    Throwable th8 = th;
                                                                                                                                                    try {
                                                                                                                                                        throw th8;
                                                                                                                                                    } catch (Throwable th9) {
                                                                                                                                                        CloseableKt.closeFinally(fileOutputStream, th8);
                                                                                                                                                        throw th9;
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            } catch (Throwable th10) {
                                                                                                                                                th = th10;
                                                                                                                                                file4 = file4;
                                                                                                                                                z3 = false;
                                                                                                                                            }
                                                                                                                                        } catch (Throwable th11) {
                                                                                                                                            th = th11;
                                                                                                                                            file4 = file4;
                                                                                                                                            z3 = false;
                                                                                                                                        }
                                                                                                                                    } catch (Throwable th12) {
                                                                                                                                        th = th12;
                                                                                                                                        z3 = false;
                                                                                                                                        th = th;
                                                                                                                                        throw th;
                                                                                                                                    }
                                                                                                                                } else {
                                                                                                                                    z2 = false;
                                                                                                                                    enumerationEntries = enumeration;
                                                                                                                                    file4 = file4;
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                z2 = false;
                                                                                                                            }
                                                                                                                        } catch (Throwable th13) {
                                                                                                                            th = th13;
                                                                                                                            file4 = file4;
                                                                                                                            z3 = false;
                                                                                                                        }
                                                                                                                    } catch (Throwable th14) {
                                                                                                                        th = th14;
                                                                                                                        str6 = str6;
                                                                                                                    }
                                                                                                                }
                                                                                                                file4 = file4;
                                                                                                                z3 = z2;
                                                                                                                str6 = str6;
                                                                                                                Unit unit = Unit.INSTANCE;
                                                                                                                try {
                                                                                                                    CloseableKt.closeFinally(zipFile, null);
                                                                                                                } catch (Exception unused2) {
                                                                                                                }
                                                                                                                it = it2;
                                                                                                                z2 = z3;
                                                                                                                file4 = file4;
                                                                                                            } catch (Throwable th15) {
                                                                                                                th = th15;
                                                                                                                file4 = file4;
                                                                                                                z3 = z2;
                                                                                                                str6 = str6;
                                                                                                            }
                                                                                                        } catch (Exception unused3) {
                                                                                                            file4 = file4;
                                                                                                            z3 = z2;
                                                                                                            str6 = str6;
                                                                                                        }
                                                                                                    } catch (Exception unused4) {
                                                                                                        it2 = it;
                                                                                                    }
                                                                                                } catch (Exception unused5) {
                                                                                                    it2 = it;
                                                                                                    file4 = file4;
                                                                                                    z3 = z2;
                                                                                                }
                                                                                            }
                                                                                            Unit unit2 = Unit.INSTANCE;
                                                                                        }
                                                                                    }
                                                                                    file3 = new File(file13, "jniLibs");
                                                                                    if (file3.exists()) {
                                                                                        while (r2.hasNext()) {
                                                                                            if (!file.isFile()) {
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    onProgress.invoke("Сборка APK...");
                                                                                    file3 = file10;
                                                                                    apkModuleLoadApkFile.writeApk(file3);
                                                                                    apkModuleLoadApkFile.close();
                                                                                    onProgress.invoke("Подпись...");
                                                                                    try {
                                                                                        signing = config.getSigning();
                                                                                        if (signing != null) {
                                                                                            KeyStore keyStore = KeyStore.getInstance("PKCS12");
                                                                                            fileInputStream = new FileInputStream(new File(projectPath, signing.getKeystorePath()));
                                                                                            try {
                                                                                                char[] charArray = signing.getKeystorePass().toCharArray();
                                                                                                Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
                                                                                                keyStore.load(fileInputStream, charArray);
                                                                                                Unit unit3 = Unit.INSTANCE;
                                                                                                CloseableKt.closeFinally(fileInputStream, null);
                                                                                                String keyAlias = signing.getKeyAlias();
                                                                                                char[] charArray2 = signing.getKeyPass().toCharArray();
                                                                                                Intrinsics.checkNotNullExpressionValue(charArray2, "toCharArray(...)");
                                                                                                Key key = keyStore.getKey(keyAlias, charArray2);
                                                                                                Intrinsics.checkNotNull(key, "null cannot be cast to non-null type java.security.PrivateKey");
                                                                                                Certificate certificate = keyStore.getCertificate(signing.getKeyAlias());
                                                                                                Intrinsics.checkNotNull(certificate, "null cannot be cast to non-null type java.security.cert.X509Certificate");
                                                                                                Pair pair = new Pair((PrivateKey) key, (X509Certificate) certificate);
                                                                                                new ApkSigner.Builder((List<ApkSigner.SignerConfig>) CollectionsKt.listOf(new ApkSigner.SignerConfig.Builder("CERT", (PrivateKey) pair.component1(), CollectionsKt.listOf((X509Certificate) pair.component2())).build())).setInputApk(file3).setOutputApk(file11).setV1SigningEnabled(true).setV2SigningEnabled(true).build().sign();
                                                                                                onProgress.invoke("Готово! V2 Signature OK.");
                                                                                                file3.delete();
                                                                                                return new BuildResult(file11, null, 2, false ? 1 : 0);
                                                                                            } catch (Throwable th16) {
                                                                                                try {
                                                                                                    throw th16;
                                                                                                } catch (Throwable th17) {
                                                                                                    CloseableKt.closeFinally(fileInputStream, th16);
                                                                                                    throw th17;
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                        throw new RuntimeException("Для Target SDK 30+ нужна настройка Keystore!");
                                                                                    } catch (Exception e3) {
                                                                                        e3.printStackTrace();
                                                                                        onProgress.invoke("Ошибка подписи: " + e3.getMessage());
                                                                                        BuildResult buildResult = new BuildResult(null, e3);
                                                                                        file3.delete();
                                                                                        return buildResult;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                valueAsString = null;
                                                                            }
                                                                            if (valueAsString != null) {
                                                                                Iterator<Entry> it17 = it16;
                                                                                Iterator<SpecTypePair> it18 = it14;
                                                                                try {
                                                                                    if (StringsKt.contains$default((CharSequence) valueAsString, (CharSequence) "res/", false, 2, (Object) null)) {
                                                                                        if (StringsKt.endsWith$default(valueAsString, ".xml", false, 2, (Object) null)) {
                                                                                            resValue.setType((byte) 0);
                                                                                            resValue.setData(0);
                                                                                            arrayList7.add(valueAsString);
                                                                                        } else {
                                                                                            z = false;
                                                                                            try {
                                                                                                if (StringsKt.endsWith$default(valueAsString, Constants.DEFAULT_IMAGE_EXTENSION, false, 2, (Object) null) || StringsKt.endsWith$default(valueAsString, ".webp", false, 2, (Object) null)) {
                                                                                                    arrayList6.add(valueAsString);
                                                                                                }
                                                                                            } catch (Exception e4) {
                                                                                                e = e4;
                                                                                                exc2 = e;
                                                                                                exc2.printStackTrace();
                                                                                                it3 = arrayList5.iterator();
                                                                                                while (it3.hasNext()) {
                                                                                                    apkModuleLoadApkFile.getZipEntryMap().remove((String) it3.next());
                                                                                                }
                                                                                                while (r1.hasNext()) {
                                                                                                    apkModuleLoadApkFile.getZipEntryMap().remove(str12);
                                                                                                    apkModuleLoadApkFile.add(new FileInputSource(config.getIconFile(), str12));
                                                                                                }
                                                                                                onProgress.invoke("Упаковка ресурсов...");
                                                                                                if (!extraAssets.isEmpty()) {
                                                                                                    while (r1.hasNext()) {
                                                                                                        apkModuleLoadApkFile.add(new FileInputSource(file14, "assets/" + file14.getName()));
                                                                                                    }
                                                                                                }
                                                                                                file = new File(file13, "assets");
                                                                                                if (file.exists()) {
                                                                                                    addAssets(apkModuleLoadApkFile, file, "assets");
                                                                                                }
                                                                                                onProgress.invoke("Упаковка библиотек (Native)...");
                                                                                                z2 = z;
                                                                                                file2 = new File(projectPath, "libs");
                                                                                                if (file2.exists()) {
                                                                                                    file4 = new File(context.getCacheDir(), "temp_libs_apk");
                                                                                                    FilesKt.deleteRecursively(file4);
                                                                                                    file4.mkdirs();
                                                                                                    fileArrListFiles = file2.listFiles();
                                                                                                    if (fileArrListFiles != null) {
                                                                                                        arrayList = new ArrayList();
                                                                                                        length = fileArrListFiles.length;
                                                                                                        while (r12 < length) {
                                                                                                            file6 = fileArrListFiles[r12];
                                                                                                            Intrinsics.checkNotNull(file6);
                                                                                                            if (Intrinsics.areEqual(FilesKt.getExtension(file6), "jar")) {
                                                                                                                arrayList.add(file6);
                                                                                                            }
                                                                                                        }
                                                                                                        it = arrayList.iterator();
                                                                                                        while (it.hasNext()) {
                                                                                                            file5 = (File) it.next();
                                                                                                            name2 = file5.getName();
                                                                                                            str6 = str6;
                                                                                                            Intrinsics.checkNotNullExpressionValue(name2, str6);
                                                                                                            str2 = "armeabi-v7a";
                                                                                                            if (StringsKt.contains$default(name2, "arm64-v8a", z2, 2, (Object) null)) {
                                                                                                                it2 = it;
                                                                                                                str3 = "arm64-v8a";
                                                                                                            } else {
                                                                                                                name3 = file5.getName();
                                                                                                                Intrinsics.checkNotNullExpressionValue(name3, str6);
                                                                                                                it2 = it;
                                                                                                                if (StringsKt.contains$default(name3, "armeabi-v7a", z2, 2, (Object) null)) {
                                                                                                                    str3 = "armeabi-v7a";
                                                                                                                } else {
                                                                                                                    name4 = file5.getName();
                                                                                                                    Intrinsics.checkNotNullExpressionValue(name4, str6);
                                                                                                                    if (StringsKt.contains$default(name4, "x86_64", z2, 2, (Object) null)) {
                                                                                                                        str3 = "x86_64";
                                                                                                                    } else {
                                                                                                                        name5 = file5.getName();
                                                                                                                        Intrinsics.checkNotNullExpressionValue(name5, str6);
                                                                                                                        if (StringsKt.contains$default(name5, "x86", z2, 2, (Object) null)) {
                                                                                                                            str3 = "x86";
                                                                                                                        } else {
                                                                                                                            str3 = null;
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                            zipFile = new ZipFile(file5);
                                                                                                            zipFile2 = zipFile;
                                                                                                            enumerationEntries = zipFile2.entries();
                                                                                                            while (enumerationEntries.hasMoreElements()) {
                                                                                                                zipEntryNextElement = enumerationEntries.nextElement();
                                                                                                                name6 = zipEntryNextElement.getName();
                                                                                                                Intrinsics.checkNotNull(name6);
                                                                                                                str3 = str3;
                                                                                                                str6 = str6;
                                                                                                                str2 = str2;
                                                                                                                if (StringsKt.endsWith$default(name6, ".so", false, 2, (Object) null)) {
                                                                                                                    enumeration = enumerationEntries;
                                                                                                                    if (StringsKt.contains$default((CharSequence) name6, (CharSequence) "lib/arm64-v8a", false, 2, (Object) null)) {
                                                                                                                        str4 = "arm64-v8a";
                                                                                                                    } else if (StringsKt.contains$default((CharSequence) name6, (CharSequence) "lib/armeabi-v7a", false, 2, (Object) null)) {
                                                                                                                        str4 = str2;
                                                                                                                    } else {
                                                                                                                        str4 = str3;
                                                                                                                    }
                                                                                                                    if (str4 != null) {
                                                                                                                        String name10 = new File(name6).getName();
                                                                                                                        String str14 = "lib/" + str4 + "/" + name10;
                                                                                                                        File file16 = new File(file4, str4 + "_" + name10);
                                                                                                                        inputStream = zipFile2.getInputStream(zipEntryNextElement);
                                                                                                                        InputStream inputStream4 = inputStream;
                                                                                                                        fileOutputStream = new FileOutputStream(file16);
                                                                                                                        Intrinsics.checkNotNull(inputStream4);
                                                                                                                        file4 = file4;
                                                                                                                        ZipFile zipFile4 = zipFile2;
                                                                                                                        z3 = false;
                                                                                                                        ByteStreamsKt.copyTo$default(inputStream4, fileOutputStream, 0, 2, null);
                                                                                                                        CloseableKt.closeFinally(fileOutputStream, null);
                                                                                                                        CloseableKt.closeFinally(inputStream, null);
                                                                                                                        apkModuleLoadApkFile.getZipEntryMap().remove(str14);
                                                                                                                        apkModuleLoadApkFile.add(new FileInputSource(file16, str14));
                                                                                                                        str3 = str3;
                                                                                                                        str2 = str2;
                                                                                                                        z2 = false;
                                                                                                                        enumerationEntries = enumeration;
                                                                                                                        file4 = file4;
                                                                                                                        zipFile2 = zipFile4;
                                                                                                                    } else {
                                                                                                                        z2 = false;
                                                                                                                        enumerationEntries = enumeration;
                                                                                                                        file4 = file4;
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    z2 = false;
                                                                                                                }
                                                                                                            }
                                                                                                            file4 = file4;
                                                                                                            z3 = z2;
                                                                                                            str6 = str6;
                                                                                                            Unit unit4 = Unit.INSTANCE;
                                                                                                            CloseableKt.closeFinally(zipFile, null);
                                                                                                            it = it2;
                                                                                                            z2 = z3;
                                                                                                            file4 = file4;
                                                                                                        }
                                                                                                        Unit unit5 = Unit.INSTANCE;
                                                                                                    }
                                                                                                }
                                                                                                file3 = new File(file13, "jniLibs");
                                                                                                if (file3.exists()) {
                                                                                                    while (r2.hasNext()) {
                                                                                                        if (!file.isFile()) {
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                onProgress.invoke("Сборка APK...");
                                                                                                file3 = file10;
                                                                                                apkModuleLoadApkFile.writeApk(file3);
                                                                                                apkModuleLoadApkFile.close();
                                                                                                onProgress.invoke("Подпись...");
                                                                                                signing = config.getSigning();
                                                                                                if (signing != null) {
                                                                                                    KeyStore keyStore2 = KeyStore.getInstance("PKCS12");
                                                                                                    fileInputStream = new FileInputStream(new File(projectPath, signing.getKeystorePath()));
                                                                                                    char[] charArray3 = signing.getKeystorePass().toCharArray();
                                                                                                    Intrinsics.checkNotNullExpressionValue(charArray3, "toCharArray(...)");
                                                                                                    keyStore2.load(fileInputStream, charArray3);
                                                                                                    Unit unit6 = Unit.INSTANCE;
                                                                                                    CloseableKt.closeFinally(fileInputStream, null);
                                                                                                    String keyAlias2 = signing.getKeyAlias();
                                                                                                    char[] charArray4 = signing.getKeyPass().toCharArray();
                                                                                                    Intrinsics.checkNotNullExpressionValue(charArray4, "toCharArray(...)");
                                                                                                    Key key2 = keyStore2.getKey(keyAlias2, charArray4);
                                                                                                    Intrinsics.checkNotNull(key2, "null cannot be cast to non-null type java.security.PrivateKey");
                                                                                                    Certificate certificate2 = keyStore2.getCertificate(signing.getKeyAlias());
                                                                                                    Intrinsics.checkNotNull(certificate2, "null cannot be cast to non-null type java.security.cert.X509Certificate");
                                                                                                    Pair pair2 = new Pair((PrivateKey) key2, (X509Certificate) certificate2);
                                                                                                    new ApkSigner.Builder((List<ApkSigner.SignerConfig>) CollectionsKt.listOf(new ApkSigner.SignerConfig.Builder("CERT", (PrivateKey) pair2.component1(), CollectionsKt.listOf((X509Certificate) pair2.component2())).build())).setInputApk(file3).setOutputApk(file11).setV1SigningEnabled(true).setV2SigningEnabled(true).build().sign();
                                                                                                    onProgress.invoke("Готово! V2 Signature OK.");
                                                                                                    file3.delete();
                                                                                                    return new BuildResult(file11, null, 2, false ? 1 : 0);
                                                                                                }
                                                                                                throw new RuntimeException("Для Target SDK 30+ нужна настройка Keystore!");
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    it16 = it17;
                                                                                    it14 = it18;
                                                                                } catch (Exception e5) {
                                                                                    e = e5;
                                                                                    z = false;
                                                                                    exc2 = e;
                                                                                    exc2.printStackTrace();
                                                                                    it3 = arrayList5.iterator();
                                                                                    while (it3.hasNext()) {
                                                                                        apkModuleLoadApkFile.getZipEntryMap().remove((String) it3.next());
                                                                                    }
                                                                                    while (r1.hasNext()) {
                                                                                        apkModuleLoadApkFile.getZipEntryMap().remove(str12);
                                                                                        apkModuleLoadApkFile.add(new FileInputSource(config.getIconFile(), str12));
                                                                                    }
                                                                                    onProgress.invoke("Упаковка ресурсов...");
                                                                                    if (!extraAssets.isEmpty()) {
                                                                                        while (r1.hasNext()) {
                                                                                            apkModuleLoadApkFile.add(new FileInputSource(file14, "assets/" + file14.getName()));
                                                                                        }
                                                                                    }
                                                                                    file = new File(file13, "assets");
                                                                                    if (file.exists()) {
                                                                                        addAssets(apkModuleLoadApkFile, file, "assets");
                                                                                    }
                                                                                    onProgress.invoke("Упаковка библиотек (Native)...");
                                                                                    z2 = z;
                                                                                    file2 = new File(projectPath, "libs");
                                                                                    if (file2.exists()) {
                                                                                        file4 = new File(context.getCacheDir(), "temp_libs_apk");
                                                                                        FilesKt.deleteRecursively(file4);
                                                                                        file4.mkdirs();
                                                                                        fileArrListFiles = file2.listFiles();
                                                                                        if (fileArrListFiles != null) {
                                                                                            arrayList = new ArrayList();
                                                                                            length = fileArrListFiles.length;
                                                                                            while (r12 < length) {
                                                                                                file6 = fileArrListFiles[r12];
                                                                                                Intrinsics.checkNotNull(file6);
                                                                                                if (Intrinsics.areEqual(FilesKt.getExtension(file6), "jar")) {
                                                                                                    arrayList.add(file6);
                                                                                                }
                                                                                            }
                                                                                            it = arrayList.iterator();
                                                                                            while (it.hasNext()) {
                                                                                                file5 = (File) it.next();
                                                                                                name2 = file5.getName();
                                                                                                str6 = str6;
                                                                                                Intrinsics.checkNotNullExpressionValue(name2, str6);
                                                                                                str2 = "armeabi-v7a";
                                                                                                if (StringsKt.contains$default(name2, "arm64-v8a", z2, 2, (Object) null)) {
                                                                                                    it2 = it;
                                                                                                    str3 = "arm64-v8a";
                                                                                                } else {
                                                                                                    name3 = file5.getName();
                                                                                                    Intrinsics.checkNotNullExpressionValue(name3, str6);
                                                                                                    it2 = it;
                                                                                                    if (StringsKt.contains$default(name3, "armeabi-v7a", z2, 2, (Object) null)) {
                                                                                                        str3 = "armeabi-v7a";
                                                                                                    } else {
                                                                                                        name4 = file5.getName();
                                                                                                        Intrinsics.checkNotNullExpressionValue(name4, str6);
                                                                                                        if (StringsKt.contains$default(name4, "x86_64", z2, 2, (Object) null)) {
                                                                                                            str3 = "x86_64";
                                                                                                        } else {
                                                                                                            name5 = file5.getName();
                                                                                                            Intrinsics.checkNotNullExpressionValue(name5, str6);
                                                                                                            if (StringsKt.contains$default(name5, "x86", z2, 2, (Object) null)) {
                                                                                                                str3 = "x86";
                                                                                                            } else {
                                                                                                                str3 = null;
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                zipFile = new ZipFile(file5);
                                                                                                zipFile2 = zipFile;
                                                                                                enumerationEntries = zipFile2.entries();
                                                                                                while (enumerationEntries.hasMoreElements()) {
                                                                                                    zipEntryNextElement = enumerationEntries.nextElement();
                                                                                                    name6 = zipEntryNextElement.getName();
                                                                                                    Intrinsics.checkNotNull(name6);
                                                                                                    str3 = str3;
                                                                                                    str6 = str6;
                                                                                                    str2 = str2;
                                                                                                    if (StringsKt.endsWith$default(name6, ".so", false, 2, (Object) null)) {
                                                                                                        enumeration = enumerationEntries;
                                                                                                        if (StringsKt.contains$default((CharSequence) name6, (CharSequence) "lib/arm64-v8a", false, 2, (Object) null)) {
                                                                                                            str4 = "arm64-v8a";
                                                                                                        } else if (StringsKt.contains$default((CharSequence) name6, (CharSequence) "lib/armeabi-v7a", false, 2, (Object) null)) {
                                                                                                            str4 = str2;
                                                                                                        } else {
                                                                                                            str4 = str3;
                                                                                                        }
                                                                                                        if (str4 != null) {
                                                                                                            String name11 = new File(name6).getName();
                                                                                                            String str15 = "lib/" + str4 + "/" + name11;
                                                                                                            File file17 = new File(file4, str4 + "_" + name11);
                                                                                                            inputStream = zipFile2.getInputStream(zipEntryNextElement);
                                                                                                            InputStream inputStream5 = inputStream;
                                                                                                            fileOutputStream = new FileOutputStream(file17);
                                                                                                            Intrinsics.checkNotNull(inputStream5);
                                                                                                            file4 = file4;
                                                                                                            ZipFile zipFile5 = zipFile2;
                                                                                                            z3 = false;
                                                                                                            ByteStreamsKt.copyTo$default(inputStream5, fileOutputStream, 0, 2, null);
                                                                                                            CloseableKt.closeFinally(fileOutputStream, null);
                                                                                                            CloseableKt.closeFinally(inputStream, null);
                                                                                                            apkModuleLoadApkFile.getZipEntryMap().remove(str15);
                                                                                                            apkModuleLoadApkFile.add(new FileInputSource(file17, str15));
                                                                                                            str3 = str3;
                                                                                                            str2 = str2;
                                                                                                            z2 = false;
                                                                                                            enumerationEntries = enumeration;
                                                                                                            file4 = file4;
                                                                                                            zipFile2 = zipFile5;
                                                                                                        } else {
                                                                                                            z2 = false;
                                                                                                            enumerationEntries = enumeration;
                                                                                                            file4 = file4;
                                                                                                        }
                                                                                                    } else {
                                                                                                        z2 = false;
                                                                                                    }
                                                                                                }
                                                                                                file4 = file4;
                                                                                                z3 = z2;
                                                                                                str6 = str6;
                                                                                                Unit unit7 = Unit.INSTANCE;
                                                                                                CloseableKt.closeFinally(zipFile, null);
                                                                                                it = it2;
                                                                                                z2 = z3;
                                                                                                file4 = file4;
                                                                                            }
                                                                                            Unit unit8 = Unit.INSTANCE;
                                                                                        }
                                                                                    }
                                                                                    file3 = new File(file13, "jniLibs");
                                                                                    if (file3.exists()) {
                                                                                        while (r2.hasNext()) {
                                                                                            if (!file.isFile()) {
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    onProgress.invoke("Сборка APK...");
                                                                                    file3 = file10;
                                                                                    apkModuleLoadApkFile.writeApk(file3);
                                                                                    apkModuleLoadApkFile.close();
                                                                                    onProgress.invoke("Подпись...");
                                                                                    signing = config.getSigning();
                                                                                    if (signing != null) {
                                                                                        KeyStore keyStore3 = KeyStore.getInstance("PKCS12");
                                                                                        fileInputStream = new FileInputStream(new File(projectPath, signing.getKeystorePath()));
                                                                                        char[] charArray5 = signing.getKeystorePass().toCharArray();
                                                                                        Intrinsics.checkNotNullExpressionValue(charArray5, "toCharArray(...)");
                                                                                        keyStore3.load(fileInputStream, charArray5);
                                                                                        Unit unit9 = Unit.INSTANCE;
                                                                                        CloseableKt.closeFinally(fileInputStream, null);
                                                                                        String keyAlias3 = signing.getKeyAlias();
                                                                                        char[] charArray6 = signing.getKeyPass().toCharArray();
                                                                                        Intrinsics.checkNotNullExpressionValue(charArray6, "toCharArray(...)");
                                                                                        Key key3 = keyStore3.getKey(keyAlias3, charArray6);
                                                                                        Intrinsics.checkNotNull(key3, "null cannot be cast to non-null type java.security.PrivateKey");
                                                                                        Certificate certificate3 = keyStore3.getCertificate(signing.getKeyAlias());
                                                                                        Intrinsics.checkNotNull(certificate3, "null cannot be cast to non-null type java.security.cert.X509Certificate");
                                                                                        Pair pair3 = new Pair((PrivateKey) key3, (X509Certificate) certificate3);
                                                                                        new ApkSigner.Builder((List<ApkSigner.SignerConfig>) CollectionsKt.listOf(new ApkSigner.SignerConfig.Builder("CERT", (PrivateKey) pair3.component1(), CollectionsKt.listOf((X509Certificate) pair3.component2())).build())).setInputApk(file3).setOutputApk(file11).setV1SigningEnabled(true).setV2SigningEnabled(true).build().sign();
                                                                                        onProgress.invoke("Готово! V2 Signature OK.");
                                                                                        file3.delete();
                                                                                        return new BuildResult(file11, null, 2, false ? 1 : 0);
                                                                                    }
                                                                                    throw new RuntimeException("Для Target SDK 30+ нужна настройка Keystore!");
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    str9 = str11;
                                                                    it13 = it15;
                                                                } catch (Exception e6) {
                                                                    e = e6;
                                                                    z = false;
                                                                }
                                                            }
                                                        }
                                                        str9 = str9;
                                                        it13 = it13;
                                                        it14 = it14;
                                                    }
                                                }
                                                z = false;
                                                Iterator it19 = CollectionsKt.distinct(arrayList7).iterator();
                                                while (it19.hasNext()) {
                                                    apkModuleLoadApkFile.getZipEntryMap().remove((String) it19.next());
                                                }
                                                for (String str16 : CollectionsKt.distinct(arrayList6)) {
                                                    apkModuleLoadApkFile.getZipEntryMap().remove(str16);
                                                    apkModuleLoadApkFile.add(new FileInputSource(config.getIconFile(), str16));
                                                }
                                            } catch (Exception e7) {
                                                e = e7;
                                                z = false;
                                            }
                                        }
                                        it3 = arrayList5.iterator();
                                        while (it3.hasNext()) {
                                            apkModuleLoadApkFile.getZipEntryMap().remove((String) it3.next());
                                        }
                                        while (r1.hasNext()) {
                                            apkModuleLoadApkFile.getZipEntryMap().remove(str12);
                                            apkModuleLoadApkFile.add(new FileInputSource(config.getIconFile(), str12));
                                        }
                                    } catch (Exception e8) {
                                        e = e8;
                                        z = false;
                                    }
                                }
                                onProgress.invoke("Упаковка ресурсов...");
                                if (!extraAssets.isEmpty()) {
                                    while (r1.hasNext()) {
                                        apkModuleLoadApkFile.add(new FileInputSource(file14, "assets/" + file14.getName()));
                                    }
                                }
                                file = new File(file13, "assets");
                                if (file.exists()) {
                                    addAssets(apkModuleLoadApkFile, file, "assets");
                                }
                                onProgress.invoke("Упаковка библиотек (Native)...");
                                z2 = z;
                                file2 = new File(projectPath, "libs");
                                if (file2.exists()) {
                                    file4 = new File(context.getCacheDir(), "temp_libs_apk");
                                    FilesKt.deleteRecursively(file4);
                                    file4.mkdirs();
                                    fileArrListFiles = file2.listFiles();
                                    if (fileArrListFiles != null) {
                                        arrayList = new ArrayList();
                                        length = fileArrListFiles.length;
                                        while (r12 < length) {
                                            file6 = fileArrListFiles[r12];
                                            Intrinsics.checkNotNull(file6);
                                            if (Intrinsics.areEqual(FilesKt.getExtension(file6), "jar")) {
                                                arrayList.add(file6);
                                            }
                                        }
                                        it = arrayList.iterator();
                                        while (it.hasNext()) {
                                            file5 = (File) it.next();
                                            name2 = file5.getName();
                                            str6 = str6;
                                            Intrinsics.checkNotNullExpressionValue(name2, str6);
                                            str2 = "armeabi-v7a";
                                            if (StringsKt.contains$default(name2, "arm64-v8a", z2, 2, (Object) null)) {
                                                it2 = it;
                                                str3 = "arm64-v8a";
                                            } else {
                                                name3 = file5.getName();
                                                Intrinsics.checkNotNullExpressionValue(name3, str6);
                                                it2 = it;
                                                if (StringsKt.contains$default(name3, "armeabi-v7a", z2, 2, (Object) null)) {
                                                    str3 = "armeabi-v7a";
                                                } else {
                                                    name4 = file5.getName();
                                                    Intrinsics.checkNotNullExpressionValue(name4, str6);
                                                    if (StringsKt.contains$default(name4, "x86_64", z2, 2, (Object) null)) {
                                                        str3 = "x86_64";
                                                    } else {
                                                        name5 = file5.getName();
                                                        Intrinsics.checkNotNullExpressionValue(name5, str6);
                                                        if (StringsKt.contains$default(name5, "x86", z2, 2, (Object) null)) {
                                                            str3 = "x86";
                                                        } else {
                                                            str3 = null;
                                                        }
                                                    }
                                                }
                                            }
                                            zipFile = new ZipFile(file5);
                                            zipFile2 = zipFile;
                                            enumerationEntries = zipFile2.entries();
                                            while (enumerationEntries.hasMoreElements()) {
                                                zipEntryNextElement = enumerationEntries.nextElement();
                                                name6 = zipEntryNextElement.getName();
                                                Intrinsics.checkNotNull(name6);
                                                str3 = str3;
                                                str6 = str6;
                                                str2 = str2;
                                                if (StringsKt.endsWith$default(name6, ".so", false, 2, (Object) null)) {
                                                    enumeration = enumerationEntries;
                                                    if (StringsKt.contains$default((CharSequence) name6, (CharSequence) "lib/arm64-v8a", false, 2, (Object) null)) {
                                                        str4 = "arm64-v8a";
                                                    } else if (StringsKt.contains$default((CharSequence) name6, (CharSequence) "lib/armeabi-v7a", false, 2, (Object) null)) {
                                                        str4 = str2;
                                                    } else {
                                                        str4 = str3;
                                                    }
                                                    if (str4 != null) {
                                                        String name12 = new File(name6).getName();
                                                        String str17 = "lib/" + str4 + "/" + name12;
                                                        File file18 = new File(file4, str4 + "_" + name12);
                                                        inputStream = zipFile2.getInputStream(zipEntryNextElement);
                                                        InputStream inputStream6 = inputStream;
                                                        fileOutputStream = new FileOutputStream(file18);
                                                        Intrinsics.checkNotNull(inputStream6);
                                                        file4 = file4;
                                                        ZipFile zipFile6 = zipFile2;
                                                        z3 = false;
                                                        ByteStreamsKt.copyTo$default(inputStream6, fileOutputStream, 0, 2, null);
                                                        CloseableKt.closeFinally(fileOutputStream, null);
                                                        CloseableKt.closeFinally(inputStream, null);
                                                        apkModuleLoadApkFile.getZipEntryMap().remove(str17);
                                                        apkModuleLoadApkFile.add(new FileInputSource(file18, str17));
                                                        str3 = str3;
                                                        str2 = str2;
                                                        z2 = false;
                                                        enumerationEntries = enumeration;
                                                        file4 = file4;
                                                        zipFile2 = zipFile6;
                                                    } else {
                                                        z2 = false;
                                                        enumerationEntries = enumeration;
                                                        file4 = file4;
                                                    }
                                                } else {
                                                    z2 = false;
                                                }
                                            }
                                            file4 = file4;
                                            z3 = z2;
                                            str6 = str6;
                                            Unit unit10 = Unit.INSTANCE;
                                            CloseableKt.closeFinally(zipFile, null);
                                            it = it2;
                                            z2 = z3;
                                            file4 = file4;
                                        }
                                        Unit unit11 = Unit.INSTANCE;
                                    }
                                }
                                file3 = new File(file13, "jniLibs");
                                if (file3.exists()) {
                                    for (File file19 : FilesKt.walkTopDown(file3)) {
                                        if (!file19.isFile() && Intrinsics.areEqual(FilesKt.getExtension(file19), "so")) {
                                            String str18 = "lib/" + FilesKt.toRelativeString(file19, file3);
                                            apkModuleLoadApkFile.getZipEntryMap().remove(str18);
                                            apkModuleLoadApkFile.add(new FileInputSource(file19, str18));
                                        }
                                    }
                                }
                                try {
                                    onProgress.invoke("Сборка APK...");
                                    file3 = file10;
                                    apkModuleLoadApkFile.writeApk(file3);
                                    apkModuleLoadApkFile.close();
                                    onProgress.invoke("Подпись...");
                                    signing = config.getSigning();
                                    if (signing != null) {
                                        KeyStore keyStore4 = KeyStore.getInstance("PKCS12");
                                        fileInputStream = new FileInputStream(new File(projectPath, signing.getKeystorePath()));
                                        char[] charArray7 = signing.getKeystorePass().toCharArray();
                                        Intrinsics.checkNotNullExpressionValue(charArray7, "toCharArray(...)");
                                        keyStore4.load(fileInputStream, charArray7);
                                        Unit unit12 = Unit.INSTANCE;
                                        CloseableKt.closeFinally(fileInputStream, null);
                                        String keyAlias4 = signing.getKeyAlias();
                                        char[] charArray8 = signing.getKeyPass().toCharArray();
                                        Intrinsics.checkNotNullExpressionValue(charArray8, "toCharArray(...)");
                                        Key key4 = keyStore4.getKey(keyAlias4, charArray8);
                                        Intrinsics.checkNotNull(key4, "null cannot be cast to non-null type java.security.PrivateKey");
                                        Certificate certificate4 = keyStore4.getCertificate(signing.getKeyAlias());
                                        Intrinsics.checkNotNull(certificate4, "null cannot be cast to non-null type java.security.cert.X509Certificate");
                                        Pair pair4 = new Pair((PrivateKey) key4, (X509Certificate) certificate4);
                                        new ApkSigner.Builder((List<ApkSigner.SignerConfig>) CollectionsKt.listOf(new ApkSigner.SignerConfig.Builder("CERT", (PrivateKey) pair4.component1(), CollectionsKt.listOf((X509Certificate) pair4.component2())).build())).setInputApk(file3).setOutputApk(file11).setV1SigningEnabled(true).setV2SigningEnabled(true).build().sign();
                                        onProgress.invoke("Готово! V2 Signature OK.");
                                        file3.delete();
                                        return new BuildResult(file11, null, 2, false ? 1 : 0);
                                    }
                                    throw new RuntimeException("Для Target SDK 30+ нужна настройка Keystore!");
                                } catch (Throwable th18) {
                                    file3.delete();
                                    throw th18;
                                }
                            }
                        }
                    } catch (Exception e9) {
                        exc = e9;
                        exc.printStackTrace();
                        onProgress.invoke("Ошибка: " + exc.getMessage());
                        try {
                            ApkModule apkModule = apkModuleLoadApkFile;
                            if (apkModuleLoadApkFile != null) {
                                apkModuleLoadApkFile.close();
                                Unit unit13 = Unit.INSTANCE;
                            }
                        } catch (Exception unused6) {
                        }
                        return new BuildResult(null, exc);
                    }
                } catch (Throwable th19) {
                    try {
                        throw th19;
                    } catch (Throwable th20) {
                        CloseableKt.closeFinally(fileOutputStream2, th19);
                        throw th20;
                    }
                }
            } catch (Throwable th21) {
                try {
                    throw th21;
                } catch (Throwable th22) {
                    CloseableKt.closeFinally(inputStreamOpen, th21);
                    throw th22;
                }
            }
        } catch (Exception e10) {
            exc = e10;
            apkModuleLoadApkFile = null;
        }
    }

    private final void addAssets(ApkModule module, File folder, String parentPath) {
        File[] fileArrListFiles = folder.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    ApkBuilder apkBuilder = INSTANCE;
                    Intrinsics.checkNotNull(file);
                    apkBuilder.addAssets(module, file, parentPath + "/" + file.getName());
                } else {
                    module.add(new FileInputSource(file, parentPath + "/" + file.getName()));
                }
            }
        }
    }
}
