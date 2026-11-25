package org.catrobat.catroid.apkbuild;

import android.R;
import android.content.Context;
import android.util.Log;
import com.android.apksig.ApkSigner;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.apk.ApkModule;
import com.reandroid.apk.PathMap;
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
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.security.auth.x500.X500Principal;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FileTreeWalk;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.time.DateUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/apkbuild/ApkToolboxManager.class
  classes12.dex
 */
/* JADX INFO: compiled from: ApkToolboxManager.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u000256B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J\u001e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004J\u0016\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004J\u001e\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004J \u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0002J&\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0004J\u0018\u0010 \u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J$\u0010!\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u00160#H\u0002J\u0016\u0010%\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\t2\u0006\u0010'\u001a\u00020\tJ\u0018\u0010(\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0002J<\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u00042\b\u0010/\u001a\u0004\u0018\u00010\u00042\b\u00100\u001a\u0004\u0018\u00010\u00042\b\u00101\u001a\u0004\u0018\u00010\u0004J\u0016\u00102\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u00103\u001a\u000204R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/catrobat/catroid/apkbuild/ApkToolboxManager;", "", "()V", "TAG", "", "addFileToApk", "", "apkPath", "sourceFile", "Ljava/io/File;", "pathInsideApk", "addFolderToApk", "sourceFolder", "destPathInApk", "customizeApk", "options", "Lorg/catrobat/catroid/apkbuild/ApkToolboxManager$BuildOptions;", "deleteFromApk", "pathPattern", "extractFileFromApk", "outputLocalPath", "fixManifestAttributes", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lcom/reandroid/arsc/chunk/xml/ResXmlElement;", "oldPkg", "newPkg", "generateKeyStore", "outputPath", PathMap.NAME_alias, "pass", "commonName", "makeClassNamesAbsolute", "modifyApk", "action", "Lkotlin/Function1;", "Lcom/reandroid/apk/ApkModule;", "recompressApk", "inputFile", "outputFile", "renameAppIdentityPrefixes", "value", "signApk", "context", "Landroid/content/Context;", "inputApkPath", "outputApkPath", "keyStorePath", "keyAlias", "keyPass", "updateManifest", "config", "Lorg/catrobat/catroid/apkbuild/ApkToolboxManager$ManifestConfig;", "BuildOptions", "ManifestConfig", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApkToolboxManager {
    public static final ApkToolboxManager INSTANCE = new ApkToolboxManager();
    private static final String TAG = "ApkToolbox";

    private ApkToolboxManager() {
    }

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/apkbuild/ApkToolboxManager$ManifestConfig.class
      classes12.dex
     */
    /* JADX INFO: compiled from: ApkToolboxManager.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b \b\u0086\b\u0018\u00002\u00020\u0001B}\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b\u0012\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\u0006HÆ\u0003¢\u0006\u0002\u0010\u0016J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0010\u0010#\u001a\u0004\u0018\u00010\u0006HÆ\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010$\u001a\u0004\u0018\u00010\u0006HÆ\u0003¢\u0006\u0002\u0010\u0016J\u0011\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000bHÆ\u0003J\u0011\u0010&\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000bHÆ\u0003J\u0010\u0010'\u001a\u0004\u0018\u00010\u000eHÆ\u0003¢\u0006\u0002\u0010\u0013J\u0086\u0001\u0010(\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b2\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eHÆ\u0001¢\u0006\u0002\u0010)J\u0013\u0010*\u001a\u00020\u000e2\b\u0010+\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010,\u001a\u00020\u0006HÖ\u0001J\t\u0010-\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0015\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0019\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0015\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001c\u0010\u0016R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001d\u0010\u0016R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0011¨\u0006."}, d2 = {"Lorg/catrobat/catroid/apkbuild/ApkToolboxManager$ManifestConfig;", "", "appName", "", "packageName", "versionCode", "", "versionName", "minSdkVersion", "targetSdkVersion", "permissionsToAdd", "", "permissionsToRemove", "debuggable", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/util/List;Ljava/util/List;Ljava/lang/Boolean;)V", "getAppName", "()Ljava/lang/String;", "getDebuggable", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getMinSdkVersion", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getPackageName", "getPermissionsToAdd", "()Ljava/util/List;", "getPermissionsToRemove", "getTargetSdkVersion", "getVersionCode", "getVersionName", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/util/List;Ljava/util/List;Ljava/lang/Boolean;)Lorg/catrobat/catroid/apkbuild/ApkToolboxManager$ManifestConfig;", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ManifestConfig {
        private final String appName;
        private final Boolean debuggable;
        private final Integer minSdkVersion;
        private final String packageName;
        private final List<String> permissionsToAdd;
        private final List<String> permissionsToRemove;
        private final Integer targetSdkVersion;
        private final Integer versionCode;
        private final String versionName;

        public ManifestConfig() {
            this(null, null, null, null, null, null, null, null, null, 511, null);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getAppName() {
            return this.appName;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getPackageName() {
            return this.packageName;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final Integer getVersionCode() {
            return this.versionCode;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final String getVersionName() {
            return this.versionName;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final Integer getMinSdkVersion() {
            return this.minSdkVersion;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final Integer getTargetSdkVersion() {
            return this.targetSdkVersion;
        }

        public final List<String> component7() {
            return this.permissionsToAdd;
        }

        public final List<String> component8() {
            return this.permissionsToRemove;
        }

        /* JADX INFO: renamed from: component9, reason: from getter */
        public final Boolean getDebuggable() {
            return this.debuggable;
        }

        public final ManifestConfig copy(String appName, String packageName, Integer versionCode, String versionName, Integer minSdkVersion, Integer targetSdkVersion, List<String> permissionsToAdd, List<String> permissionsToRemove, Boolean debuggable) {
            return new ManifestConfig(appName, packageName, versionCode, versionName, minSdkVersion, targetSdkVersion, permissionsToAdd, permissionsToRemove, debuggable);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ManifestConfig)) {
                return false;
            }
            ManifestConfig manifestConfig = (ManifestConfig) other;
            return Intrinsics.areEqual(this.appName, manifestConfig.appName) && Intrinsics.areEqual(this.packageName, manifestConfig.packageName) && Intrinsics.areEqual(this.versionCode, manifestConfig.versionCode) && Intrinsics.areEqual(this.versionName, manifestConfig.versionName) && Intrinsics.areEqual(this.minSdkVersion, manifestConfig.minSdkVersion) && Intrinsics.areEqual(this.targetSdkVersion, manifestConfig.targetSdkVersion) && Intrinsics.areEqual(this.permissionsToAdd, manifestConfig.permissionsToAdd) && Intrinsics.areEqual(this.permissionsToRemove, manifestConfig.permissionsToRemove) && Intrinsics.areEqual(this.debuggable, manifestConfig.debuggable);
        }

        public int hashCode() {
            String str = this.appName;
            int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
            String str2 = this.packageName;
            int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
            Integer num = this.versionCode;
            int iHashCode3 = (iHashCode2 + (num == null ? 0 : num.hashCode())) * 31;
            String str3 = this.versionName;
            int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
            Integer num2 = this.minSdkVersion;
            int iHashCode5 = (iHashCode4 + (num2 == null ? 0 : num2.hashCode())) * 31;
            Integer num3 = this.targetSdkVersion;
            int iHashCode6 = (iHashCode5 + (num3 == null ? 0 : num3.hashCode())) * 31;
            List<String> list = this.permissionsToAdd;
            int iHashCode7 = (iHashCode6 + (list == null ? 0 : list.hashCode())) * 31;
            List<String> list2 = this.permissionsToRemove;
            int iHashCode8 = (iHashCode7 + (list2 == null ? 0 : list2.hashCode())) * 31;
            Boolean bool = this.debuggable;
            return iHashCode8 + (bool != null ? bool.hashCode() : 0);
        }

        public String toString() {
            return "ManifestConfig(appName=" + this.appName + ", packageName=" + this.packageName + ", versionCode=" + this.versionCode + ", versionName=" + this.versionName + ", minSdkVersion=" + this.minSdkVersion + ", targetSdkVersion=" + this.targetSdkVersion + ", permissionsToAdd=" + this.permissionsToAdd + ", permissionsToRemove=" + this.permissionsToRemove + ", debuggable=" + this.debuggable + Constant.AFTER_QUTO;
        }

        public ManifestConfig(String str, String str2, Integer num, String str3, Integer num2, Integer num3, List<String> list, List<String> list2, Boolean bool) {
            this.appName = str;
            this.packageName = str2;
            this.versionCode = num;
            this.versionName = str3;
            this.minSdkVersion = num2;
            this.targetSdkVersion = num3;
            this.permissionsToAdd = list;
            this.permissionsToRemove = list2;
            this.debuggable = bool;
        }

        public /* synthetic */ ManifestConfig(String str, String str2, Integer num, String str3, Integer num2, Integer num3, List list, List list2, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : num2, (i & 32) != 0 ? null : num3, (i & 64) != 0 ? null : list, (i & 128) != 0 ? null : list2, (i & 256) == 0 ? bool : null);
        }

        public final String getAppName() {
            return this.appName;
        }

        public final String getPackageName() {
            return this.packageName;
        }

        public final Integer getVersionCode() {
            return this.versionCode;
        }

        public final String getVersionName() {
            return this.versionName;
        }

        public final Integer getMinSdkVersion() {
            return this.minSdkVersion;
        }

        public final Integer getTargetSdkVersion() {
            return this.targetSdkVersion;
        }

        public final List<String> getPermissionsToAdd() {
            return this.permissionsToAdd;
        }

        public final List<String> getPermissionsToRemove() {
            return this.permissionsToRemove;
        }

        public final Boolean getDebuggable() {
            return this.debuggable;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/apkbuild/ApkToolboxManager$BuildOptions.class
      classes12.dex
     */
    /* JADX INFO: compiled from: ApkToolboxManager.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\r\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\r¢\u0006\u0002\u0010\u0010J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00030\rHÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0007HÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\nHÆ\u0003J\t\u0010&\u001a\u00020\nHÆ\u0003J\u000f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00030\rHÆ\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00030\rHÆ\u0003J\u0081\u0001\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\rHÆ\u0001J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010-\u001a\u00020\u0007HÖ\u0001J\t\u0010.\u001a\u00020\u0003HÖ\u0001R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\r¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014¨\u0006/"}, d2 = {"Lorg/catrobat/catroid/apkbuild/ApkToolboxManager$BuildOptions;", "", "appName", "", "packageName", "versionName", "versionCode", "", ResConfigBase.NAME_orientation, "iconFile", "Ljava/io/File;", "projectZipFile", "excludedArches", "", "excludedLibraries", "allowedPermissions", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/io/File;Ljava/io/File;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getAllowedPermissions", "()Ljava/util/List;", "getAppName", "()Ljava/lang/String;", "getExcludedArches", "getExcludedLibraries", "getIconFile", "()Ljava/io/File;", "getOrientation", "getPackageName", "getProjectZipFile", "getVersionCode", "()I", "getVersionName", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class BuildOptions {
        private final List<String> allowedPermissions;
        private final String appName;
        private final List<String> excludedArches;
        private final List<String> excludedLibraries;
        private final File iconFile;
        private final String orientation;
        private final String packageName;
        private final File projectZipFile;
        private final int versionCode;
        private final String versionName;

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getAppName() {
            return this.appName;
        }

        public final List<String> component10() {
            return this.allowedPermissions;
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

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final String getOrientation() {
            return this.orientation;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final File getIconFile() {
            return this.iconFile;
        }

        /* JADX INFO: renamed from: component7, reason: from getter */
        public final File getProjectZipFile() {
            return this.projectZipFile;
        }

        public final List<String> component8() {
            return this.excludedArches;
        }

        public final List<String> component9() {
            return this.excludedLibraries;
        }

        public final BuildOptions copy(String appName, String packageName, String versionName, int versionCode, String orientation, File iconFile, File projectZipFile, List<String> excludedArches, List<String> excludedLibraries, List<String> allowedPermissions) {
            Intrinsics.checkNotNullParameter(appName, "appName");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            Intrinsics.checkNotNullParameter(versionName, "versionName");
            Intrinsics.checkNotNullParameter(orientation, "orientation");
            Intrinsics.checkNotNullParameter(projectZipFile, "projectZipFile");
            Intrinsics.checkNotNullParameter(excludedArches, "excludedArches");
            Intrinsics.checkNotNullParameter(excludedLibraries, "excludedLibraries");
            Intrinsics.checkNotNullParameter(allowedPermissions, "allowedPermissions");
            return new BuildOptions(appName, packageName, versionName, versionCode, orientation, iconFile, projectZipFile, excludedArches, excludedLibraries, allowedPermissions);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BuildOptions)) {
                return false;
            }
            BuildOptions buildOptions = (BuildOptions) other;
            return Intrinsics.areEqual(this.appName, buildOptions.appName) && Intrinsics.areEqual(this.packageName, buildOptions.packageName) && Intrinsics.areEqual(this.versionName, buildOptions.versionName) && this.versionCode == buildOptions.versionCode && Intrinsics.areEqual(this.orientation, buildOptions.orientation) && Intrinsics.areEqual(this.iconFile, buildOptions.iconFile) && Intrinsics.areEqual(this.projectZipFile, buildOptions.projectZipFile) && Intrinsics.areEqual(this.excludedArches, buildOptions.excludedArches) && Intrinsics.areEqual(this.excludedLibraries, buildOptions.excludedLibraries) && Intrinsics.areEqual(this.allowedPermissions, buildOptions.allowedPermissions);
        }

        public int hashCode() {
            int iHashCode = ((((((((this.appName.hashCode() * 31) + this.packageName.hashCode()) * 31) + this.versionName.hashCode()) * 31) + Integer.hashCode(this.versionCode)) * 31) + this.orientation.hashCode()) * 31;
            File file = this.iconFile;
            return ((((((((iHashCode + (file == null ? 0 : file.hashCode())) * 31) + this.projectZipFile.hashCode()) * 31) + this.excludedArches.hashCode()) * 31) + this.excludedLibraries.hashCode()) * 31) + this.allowedPermissions.hashCode();
        }

        public String toString() {
            return "BuildOptions(appName=" + this.appName + ", packageName=" + this.packageName + ", versionName=" + this.versionName + ", versionCode=" + this.versionCode + ", orientation=" + this.orientation + ", iconFile=" + this.iconFile + ", projectZipFile=" + this.projectZipFile + ", excludedArches=" + this.excludedArches + ", excludedLibraries=" + this.excludedLibraries + ", allowedPermissions=" + this.allowedPermissions + Constant.AFTER_QUTO;
        }

        public BuildOptions(String appName, String packageName, String versionName, int i, String orientation, File file, File projectZipFile, List<String> excludedArches, List<String> excludedLibraries, List<String> allowedPermissions) {
            Intrinsics.checkNotNullParameter(appName, "appName");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            Intrinsics.checkNotNullParameter(versionName, "versionName");
            Intrinsics.checkNotNullParameter(orientation, "orientation");
            Intrinsics.checkNotNullParameter(projectZipFile, "projectZipFile");
            Intrinsics.checkNotNullParameter(excludedArches, "excludedArches");
            Intrinsics.checkNotNullParameter(excludedLibraries, "excludedLibraries");
            Intrinsics.checkNotNullParameter(allowedPermissions, "allowedPermissions");
            this.appName = appName;
            this.packageName = packageName;
            this.versionName = versionName;
            this.versionCode = i;
            this.orientation = orientation;
            this.iconFile = file;
            this.projectZipFile = projectZipFile;
            this.excludedArches = excludedArches;
            this.excludedLibraries = excludedLibraries;
            this.allowedPermissions = allowedPermissions;
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

        public final String getOrientation() {
            return this.orientation;
        }

        public final File getIconFile() {
            return this.iconFile;
        }

        public final File getProjectZipFile() {
            return this.projectZipFile;
        }

        public final List<String> getExcludedArches() {
            return this.excludedArches;
        }

        public final List<String> getExcludedLibraries() {
            return this.excludedLibraries;
        }

        public final List<String> getAllowedPermissions() {
            return this.allowedPermissions;
        }
    }

    public final boolean customizeApk(String apkPath, final BuildOptions options) {
        Intrinsics.checkNotNullParameter(apkPath, "apkPath");
        Intrinsics.checkNotNullParameter(options, "options");
        return modifyApk(apkPath, new Function1<ApkModule, Unit>() { // from class: org.catrobat.catroid.apkbuild.ApkToolboxManager.customizeApk.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ApkModule apkModule) {
                invoke2(apkModule);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code duplicated, block: B:20:0x00a7  */
            /* JADX WARN: Type inference failed for: r1v18 */
            /* JADX WARN: Type inference failed for: r1v19, types: [boolean] */
            /* JADX WARN: Type inference failed for: r1v37 */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ApkModule module) {
                int i;
                String str;
                char c;
                char c2;
                Object obj;
                boolean z;
                char c3;
                char c4;
                ?? r1;
                Object obj2;
                Object[] objArr;
                Object[] objArr2;
                Iterator<ResXmlElement> it;
                boolean z2;
                int i2;
                Intrinsics.checkNotNullParameter(module, "module");
                AndroidManifestBlock androidManifestBlock = module.getAndroidManifestBlock();
                String packageName = androidManifestBlock.getPackageName();
                String packageName2 = options.getPackageName();
                ApkToolboxManager apkToolboxManager = ApkToolboxManager.INSTANCE;
                ResXmlElement manifestElement = androidManifestBlock.getManifestElement();
                Intrinsics.checkNotNullExpressionValue(manifestElement, "getManifestElement(...)");
                Intrinsics.checkNotNull(packageName);
                apkToolboxManager.makeClassNamesAbsolute(manifestElement, packageName);
                androidManifestBlock.setPackageName(packageName2);
                ApkToolboxManager apkToolboxManager2 = ApkToolboxManager.INSTANCE;
                ResXmlElement manifestElement2 = androidManifestBlock.getManifestElement();
                Intrinsics.checkNotNullExpressionValue(manifestElement2, "getManifestElement(...)");
                apkToolboxManager2.fixManifestAttributes(manifestElement2, packageName, packageName2);
                androidManifestBlock.setVersionCode(options.getVersionCode());
                androidManifestBlock.setVersionName(options.getVersionName());
                androidManifestBlock.getApplicationElement().getOrCreateAndroidAttribute(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, R.attr.label).setValueAsString(options.getAppName());
                ResXmlElement mainActivity = androidManifestBlock.getMainActivity();
                if (mainActivity != null) {
                    ResXmlAttribute orCreateAndroidAttribute = mainActivity.getOrCreateAndroidAttribute("screenOrientation", R.attr.screenOrientation);
                    String orientation = options.getOrientation();
                    int iHashCode = orientation.hashCode();
                    if (iHashCode != -905948230) {
                        if (iHashCode != 729267099) {
                            if (iHashCode == 1430647483 && orientation.equals("landscape")) {
                                i2 = 0;
                            } else {
                                i2 = -1;
                            }
                        } else if (orientation.equals("portrait")) {
                            i2 = 1;
                        } else {
                            i2 = -1;
                        }
                    } else if (orientation.equals("sensor")) {
                        i2 = 4;
                    } else {
                        i2 = -1;
                    }
                    if (i2 != -1) {
                        orCreateAndroidAttribute.setValueAsDecimal(i2);
                    } else {
                        mainActivity.removeAttribute(orCreateAndroidAttribute);
                    }
                }
                ResXmlElement manifestElement3 = androidManifestBlock.getManifestElement();
                List<ResXmlElement> listListElements = manifestElement3.listElements("uses-permission");
                ArrayList arrayList = new ArrayList();
                Iterator<ResXmlElement> it2 = listListElements.iterator();
                while (true) {
                    boolean zHasNext = it2.hasNext();
                    i = R.attr.name;
                    str = null;
                    if (!zHasNext) {
                        break;
                    }
                    ResXmlElement next = it2.next();
                    ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = next.searchAttributeByResourceId(R.attr.name);
                    String valueAsString = resXmlAttributeSearchAttributeByResourceId != null ? resXmlAttributeSearchAttributeByResourceId.getValueAsString() : null;
                    String str2 = valueAsString != null ? valueAsString : "";
                    if (str2.length() > 0 && !options.getAllowedPermissions().contains(str2) && StringsKt.startsWith$default(str2, "android.permission.", false, 2, (Object) null)) {
                        Intrinsics.checkNotNull(next);
                        arrayList.add(next);
                    }
                }
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    manifestElement3.remove((ResXmlElement) it3.next());
                }
                List<ResXmlElement> listListElements2 = manifestElement3.listElements("uses-feature");
                ArrayList arrayList2 = new ArrayList();
                Iterator<ResXmlElement> it4 = listListElements2.iterator();
                while (it4.hasNext()) {
                    ResXmlElement next2 = it4.next();
                    ResXmlAttribute resXmlAttributeSearchAttributeByResourceId2 = next2.searchAttributeByResourceId(i);
                    String valueAsString2 = resXmlAttributeSearchAttributeByResourceId2 != null ? resXmlAttributeSearchAttributeByResourceId2.getValueAsString() : str;
                    if (valueAsString2 == null) {
                        valueAsString2 = "";
                    }
                    String str3 = valueAsString2;
                    if (str3.length() > 0) {
                        boolean zContains = options.getAllowedPermissions().contains("android.permission.CAMERA");
                        boolean zContains2 = options.getAllowedPermissions().contains("android.permission.ACCESS_FINE_LOCATION");
                        boolean zContains3 = options.getAllowedPermissions().contains("android.permission.NFC");
                        boolean zContains4 = options.getAllowedPermissions().contains("android.permission.BLUETOOTH");
                        if (zContains) {
                            it = it4;
                            z2 = false;
                            str = null;
                        } else {
                            it = it4;
                            z2 = false;
                            str = null;
                            if (StringsKt.contains$default((CharSequence) str3, (CharSequence) "camera", false, 2, (Object) null)) {
                                Intrinsics.checkNotNull(next2);
                                arrayList2.add(next2);
                            }
                        }
                        if (!zContains2 && StringsKt.contains$default(str3, FirebaseAnalytics.Param.LOCATION, z2, 2, str)) {
                            Intrinsics.checkNotNull(next2);
                            arrayList2.add(next2);
                        }
                        if (!zContains3 && StringsKt.contains$default(str3, "nfc", z2, 2, str)) {
                            Intrinsics.checkNotNull(next2);
                            arrayList2.add(next2);
                        }
                        if (zContains4) {
                            it4 = it;
                            i = R.attr.name;
                        } else {
                            if (StringsKt.contains$default(str3, "bluetooth", z2, 2, str)) {
                                Intrinsics.checkNotNull(next2);
                                arrayList2.add(next2);
                            }
                            it4 = it;
                            i = R.attr.name;
                            str = null;
                        }
                    }
                }
                Iterator it5 = arrayList2.iterator();
                while (it5.hasNext()) {
                    manifestElement3.remove((ResXmlElement) it5.next());
                }
                if (module.hasTableBlock()) {
                    TableBlock tableBlock = module.getTableBlock();
                    Iterable<PackageBlock> iterableListPackages = tableBlock.listPackages();
                    Intrinsics.checkNotNullExpressionValue(iterableListPackages, "listPackages(...)");
                    for (PackageBlock packageBlock : iterableListPackages) {
                        if (Intrinsics.areEqual(packageBlock.getName(), packageName)) {
                            packageBlock.setName(packageName2);
                        }
                    }
                    if (options.getIconFile() != null && options.getIconFile().exists()) {
                        ArrayList arrayList3 = new ArrayList();
                        Iterable<PackageBlock> iterableListPackages2 = tableBlock.listPackages();
                        Intrinsics.checkNotNullExpressionValue(iterableListPackages2, "listPackages(...)");
                        Iterator<PackageBlock> it6 = iterableListPackages2.iterator();
                        while (it6.hasNext()) {
                            Iterable<SpecTypePair> iterableListSpecTypePairs = it6.next().listSpecTypePairs();
                            Intrinsics.checkNotNullExpressionValue(iterableListSpecTypePairs, "listSpecTypePairs(...)");
                            for (SpecTypePair specTypePair : iterableListSpecTypePairs) {
                                if (Intrinsics.areEqual(specTypePair.getTypeName(), "mipmap") || Intrinsics.areEqual(specTypePair.getTypeName(), "drawable")) {
                                    Iterator<ResourceEntry> resources = specTypePair.getResources();
                                    while (resources.hasNext()) {
                                        ResourceEntry next3 = resources.next();
                                        String name2 = next3.getName();
                                        if (name2 == null) {
                                            name2 = "";
                                        } else {
                                            Intrinsics.checkNotNull(name2);
                                        }
                                        if (Intrinsics.areEqual(name2, "ic_launcher_foreground") || Intrinsics.areEqual(name2, "ic_launcher_round_foreground")) {
                                            Iterator<Entry> it7 = next3.iterator();
                                            Intrinsics.checkNotNullExpressionValue(it7, "iterator(...)");
                                            while (it7.hasNext()) {
                                                Entry next4 = it7.next();
                                                ResValue resValue = next4.getResValue();
                                                String valueAsString3 = resValue != null ? resValue.getValueAsString() : null;
                                                if (valueAsString3 != null && StringsKt.contains$default((CharSequence) valueAsString3, (CharSequence) "res/", false, 2, (Object) null)) {
                                                    next4.setValueAsString("res/mipmap-xxxhdpi/ic_launcher_foreground.png");
                                                    if (StringsKt.endsWith$default(valueAsString3, ".xml", false, 2, (Object) null)) {
                                                        arrayList3.add(valueAsString3);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Iterator it8 = CollectionsKt.distinct(arrayList3).iterator();
                        while (it8.hasNext()) {
                            module.getZipEntryMap().remove((String) it8.next());
                        }
                    }
                }
                if (options.getIconFile() != null && options.getIconFile().exists()) {
                    ArrayList arrayList4 = new ArrayList();
                    List<InputSource> listListInputSources = module.getZipEntryMap().listInputSources();
                    Intrinsics.checkNotNullExpressionValue(listListInputSources, "listInputSources(...)");
                    Iterator<T> it9 = listListInputSources.iterator();
                    while (it9.hasNext()) {
                        String name3 = ((InputSource) it9.next()).getName();
                        Intrinsics.checkNotNull(name3);
                        if (StringsKt.endsWith$default(name3, Constants.DEFAULT_IMAGE_EXTENSION, false, 2, (Object) null) || StringsKt.endsWith$default(name3, ".webp", false, 2, (Object) null)) {
                            if (StringsKt.contains$default((CharSequence) name3, (CharSequence) "ic_launcher", false, 2, (Object) null)) {
                                arrayList4.add(name3);
                            }
                        }
                    }
                    arrayList4.add("res/mipmap-xxxhdpi/ic_launcher_foreground.png");
                    List<String> listDistinct = CollectionsKt.distinct(arrayList4);
                    BuildOptions buildOptions = options;
                    for (String str4 : listDistinct) {
                        module.getZipEntryMap().remove(str4);
                        module.add(new FileInputSource(buildOptions.getIconFile(), str4));
                    }
                }
                module.getZipEntryMap().remove("assets/project.zip");
                module.add(new FileInputSource(options.getProjectZipFile(), "assets/project.zip"));
                for (String str5 : options.getExcludedArches()) {
                    List<InputSource> listListInputSources2 = module.getZipEntryMap().listInputSources();
                    Intrinsics.checkNotNullExpressionValue(listListInputSources2, "listInputSources(...)");
                    ArrayList arrayList5 = new ArrayList();
                    for (Object obj3 : listListInputSources2) {
                        String name4 = ((InputSource) obj3).getName();
                        Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                        if (StringsKt.startsWith$default(name4, "lib/" + str5 + "/", false, 2, (Object) null)) {
                            arrayList5.add(obj3);
                        }
                    }
                    Iterator it10 = arrayList5.iterator();
                    while (it10.hasNext()) {
                        module.getZipEntryMap().remove(((InputSource) it10.next()).getName());
                    }
                }
                boolean zContains5 = options.getExcludedLibraries().contains("python");
                boolean zContains6 = options.getExcludedLibraries().contains("qemu");
                boolean zContains7 = options.getExcludedLibraries().contains("compiler");
                boolean zContains8 = options.getExcludedLibraries().contains("onnx");
                boolean zContains9 = options.getExcludedLibraries().contains("mnn");
                boolean zContains10 = options.getExcludedLibraries().contains("mkit");
                char c5 = 3;
                char c6 = 6;
                List listListOf = CollectionsKt.listOf((Object[]) new String[]{"assets/test.png", "assets/xz9.jpg", "assets/xz10.jpg", "assets/veryimportant.txt", "assets/rtx6090_download_without_registration_free.sh", "assets/stage/xz4.jpg", "assets/stage/xz5.jpg", "assets/stage/xz8.jpg", "assets/qemu_x86_64/xz12.jpg", "assets/gta_6_source_code_leak.cpp", "assets/stub_project.zip", "assets/trustedDomains.json", "assets/nolb_config.xml", "assets/hmsincas.nks", "assets/hmsincas.bks", "assets/hmsrootcas.bks", "assets/updatesdkcas.bks", "assets/grs_sp.bks", "assets/penguin.webp", "resources/new_api_database.ser"});
                List listListOf2 = CollectionsKt.listOf((Object[]) new String[]{"assets/activity.jar", "assets/activity-1.7.2.aar", "assets/annotation-1.2.0.jar", "assets/annotation-experimental-1.1.0.aar", "assets/annotations-13.0.jar", "assets/app-classes.jar", "assets/collection-1.1.0.jar", "assets/concurrent-futures-1.1.0.jar", "assets/core.jar", "assets/core-1.8.0.aar", "assets/core-common-2.2.0.jar", "assets/core-ktx-1.2.0.aar", "assets/core-lambda-stubs.jar", "assets/core-runtime-2.2.0.aar", "assets/customview-1.0.0.aar", "assets/fragment.jar", "assets/gdx.jar", "assets/koin-core.jar", "assets/kotlin-stdlib-1.8.20.jar", "assets/kotlin-stdlib-common-1.8.20.jar", "assets/kotlin-stdlib-jdk7-1.6.21.jar", "assets/kotlin-stdlib-jdk8-1.6.21.jar", "assets/kotlinx-coroutines-android-1.6.4.jar", "assets/kotlinx-coroutines-core-jvm-1.6.4.jar", "assets/lifecycle-common-2.6.1.jar", "assets/lifecycle-livedata-2.6.1.aar", "assets/lifecycle-livedata-core-2.6.1.aar", "assets/lifecycle-runtime-2.6.1.aar", "assets/lifecycle-viewmodel-2.6.1.aar", "assets/lifecycle-viewmodel-savedstate-2.6.1.aar", "assets/listenablefuture-1.0.jar", "assets/loader-1.0.0.aar", "assets/profileinstaller-1.3.0.aar", "assets/savedstate-1.2.1.aar", "assets/startup-runtime-1.1.1.aar", "assets/tracing-1.0.0.aar", "assets/versionedparcelable-1.1.1.aar", "assets/viewpager-1.0.0.aar", "assets/xmlpull-1.1.3.1.jar", "assets/xpp3_min-1.1.4c.jar", "assets/xstream.jar"});
                ArrayList arrayList6 = new ArrayList();
                List<InputSource> listListInputSources3 = module.getZipEntryMap().listInputSources();
                Intrinsics.checkNotNullExpressionValue(listListInputSources3, "listInputSources(...)");
                Iterator<T> it11 = listListInputSources3.iterator();
                while (it11.hasNext()) {
                    String name5 = ((InputSource) it11.next()).getName();
                    if (listListOf.contains(name5)) {
                        c = c5;
                        c2 = c6;
                    } else {
                        Intrinsics.checkNotNull(name5);
                        if (StringsKt.startsWith$default(name5, "keys/", false, 2, (Object) null) || StringsKt.startsWith$default(name5, "license/", false, 2, (Object) null) || StringsKt.startsWith$default(name5, "assets/catblocks/", false, 2, (Object) null) || StringsKt.startsWith$default(name5, "frameworks/", false, 2, (Object) null) || StringsKt.startsWith$default(name5, "assets/dexopt/", false, 2, (Object) null)) {
                            c = 3;
                            c2 = 6;
                        } else {
                            String str6 = name5;
                            if (StringsKt.contains$default((CharSequence) str6, (CharSequence) "grs_sdk", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str6, (CharSequence) "libcrashlytics", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str6, (CharSequence) "libexample_core.so", false, 2, (Object) null)) {
                                c = 3;
                                c2 = 6;
                            } else {
                                if (zContains7 && listListOf2.contains(name5)) {
                                    arrayList6.add(name5);
                                } else {
                                    if (zContains7) {
                                        obj = null;
                                        z = false;
                                        objArr2 = null;
                                        if (StringsKt.contains$default((CharSequence) str6, (CharSequence) "libquickjs.so", false, 2, (Object) null)) {
                                            arrayList6.add(name5);
                                        }
                                        c = 3;
                                        c2 = 6;
                                    } else {
                                        obj = null;
                                        z = false;
                                    }
                                    if (zContains5 && (StringsKt.startsWith$default(name5, "assets/python3.12/", z, 2, obj) || StringsKt.startsWith$default(name5, "assets/default_pylibs/", z, 2, obj) || StringsKt.contains$default(str6, "libpython3.12.so", z, 2, obj) || StringsKt.contains$default(str6, "libopenblas.so", z, 2, obj) || StringsKt.contains$default(str6, "libllm.so", z, 2, obj))) {
                                        arrayList6.add(name5);
                                    } else {
                                        if (zContains6) {
                                            obj = null;
                                            c3 = 0;
                                            objArr2 = null;
                                            objArr = null;
                                            if (StringsKt.startsWith$default(name5, "assets/qemu_x86_64/", false, 2, (Object) null)) {
                                                arrayList6.add(name5);
                                                c = 3;
                                                c2 = 6;
                                            } else {
                                                if (StringsKt.contains$default((CharSequence) str6, (CharSequence) "vnc", false, 2, (Object) null)) {
                                                    arrayList6.add(name5);
                                                }
                                                c = 3;
                                                c2 = 6;
                                            }
                                        } else {
                                            c3 = 0;
                                        }
                                        if (zContains5 && zContains6) {
                                            String[] strArr = new String[6];
                                            strArr[c3] = "libcrypto.so";
                                            strArr[1] = "libssl.so";
                                            strArr[2] = "libz.so";
                                            strArr[3] = "libexpat.so";
                                            strArr[4] = "libjpeg.so";
                                            strArr[5] = "libpng.so";
                                            List listListOf3 = CollectionsKt.listOf((Object[]) strArr);
                                            if (!(listListOf3 instanceof Collection) || !listListOf3.isEmpty()) {
                                                Iterator it12 = listListOf3.iterator();
                                                while (true) {
                                                    if (it12.hasNext()) {
                                                        Iterator it13 = it12;
                                                        if (StringsKt.contains$default((CharSequence) str6, (CharSequence) it12.next(), false, 2, (Object) null)) {
                                                            arrayList6.add(name5);
                                                        } else {
                                                            it12 = it13;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (zContains8) {
                                            objArr = null;
                                            c4 = 0;
                                            if (StringsKt.contains$default((CharSequence) str6, (CharSequence) "libonnxruntime.so", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str6, (CharSequence) "libonnxruntime4j_jni.so", false, 2, (Object) null)) {
                                                arrayList6.add(name5);
                                                c = 3;
                                                c2 = 6;
                                            }
                                        } else {
                                            c4 = 0;
                                        }
                                        if (zContains9) {
                                            String[] strArr2 = new String[7];
                                            strArr2[c4] = "libMNN.so";
                                            strArr2[1] = "libMNN_CL.so";
                                            strArr2[2] = "libMNN_Express.so";
                                            strArr2[3] = "libMNN_Vulkan.so";
                                            strArr2[4] = "libMNNAudio.so";
                                            strArr2[5] = "libmnncore.so";
                                            c2 = 6;
                                            strArr2[6] = "libMNNOpenCV.so";
                                            List listListOf4 = CollectionsKt.listOf((Object[]) strArr2);
                                            if (!(listListOf4 instanceof Collection) || !listListOf4.isEmpty()) {
                                                Iterator it14 = listListOf4.iterator();
                                                while (true) {
                                                    if (it14.hasNext()) {
                                                        Iterator it15 = it14;
                                                        if (StringsKt.contains$default((CharSequence) str6, (CharSequence) it14.next(), false, 2, (Object) null)) {
                                                            arrayList6.add(name5);
                                                        } else {
                                                            it14 = it15;
                                                        }
                                                    }
                                                    c = 3;
                                                }
                                            }
                                            obj2 = null;
                                            r1 = 0;
                                        } else {
                                            r1 = c4;
                                            obj2 = null;
                                            c2 = 6;
                                        }
                                        if (!zContains10) {
                                            c = 3;
                                        } else if (!StringsKt.startsWith$default(name5, "assets/mlkit_odt_default_classifier/", (boolean) r1, 2, obj2) && !StringsKt.startsWith$default(name5, "assets/mlkit_odt_localizer/", (boolean) r1, 2, obj2) && !StringsKt.startsWith$default(name5, "assets/mlkit_pose/", (boolean) r1, 2, obj2)) {
                                            if (StringsKt.startsWith$default(name5, "assets/kpms-mlkit/", (boolean) r1, 2, obj2) || Intrinsics.areEqual(name5, "assets/langid_model.smfb.jpg") || Intrinsics.areEqual(name5, "assets/tts_sdk_model_config.json")) {
                                                c = 3;
                                                arrayList6.add(name5);
                                            } else {
                                                String[] strArr3 = new String[4];
                                                strArr3[r1] = "libxeno_native.so";
                                                strArr3[1] = "libmlkitcommonpipeline.so";
                                                strArr3[2] = "liblanguage_id_jni.so";
                                                c = 3;
                                                strArr3[3] = "libml-vadenergy.so";
                                                List listListOf5 = CollectionsKt.listOf((Object[]) strArr3);
                                                if ((listListOf5 instanceof Collection) && listListOf5.isEmpty()) {
                                                    break;
                                                    break;
                                                }
                                                Iterator it16 = listListOf5.iterator();
                                                while (true) {
                                                    if (!it16.hasNext()) {
                                                        break;
                                                    }
                                                    Iterator it17 = it16;
                                                    if (StringsKt.contains$default((CharSequence) str6, (CharSequence) it16.next(), false, 2, (Object) null)) {
                                                        arrayList6.add(name5);
                                                        break;
                                                    }
                                                    it16 = it17;
                                                }
                                            }
                                        } else {
                                            c = 3;
                                            arrayList6.add(name5);
                                        }
                                    }
                                }
                                c = 3;
                                c2 = 6;
                            }
                        }
                        c5 = c;
                        c6 = c2;
                    }
                    Intrinsics.checkNotNull(name5);
                    arrayList6.add(name5);
                    c5 = c;
                    c6 = c2;
                }
                Iterator it18 = CollectionsKt.distinct(arrayList6).iterator();
                while (it18.hasNext()) {
                    module.getZipEntryMap().remove((String) it18.next());
                }
            }
        });
    }

    public final boolean recompressApk(File inputFile, File outputFile) {
        Intrinsics.checkNotNullParameter(inputFile, "inputFile");
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        File file = new File(outputFile.getAbsolutePath() + ".tmp");
        file.delete();
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            try {
                ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
                try {
                    ZipInputStream zipInputStream2 = zipInputStream;
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    try {
                        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
                        try {
                            ZipOutputStream zipOutputStream2 = zipOutputStream;
                            zipOutputStream2.setLevel(9);
                            while (true) {
                                ZipEntry nextEntry = zipInputStream2.getNextEntry();
                                if (nextEntry == null) {
                                    break;
                                }
                                Intrinsics.checkNotNull(nextEntry);
                                ZipEntry zipEntry = new ZipEntry(nextEntry.getName());
                                String name2 = nextEntry.getName();
                                Intrinsics.checkNotNull(name2);
                                if (StringsKt.endsWith$default(name2, ".dex", false, 2, (Object) null) || StringsKt.endsWith$default(name2, ".so", false, 2, (Object) null) || StringsKt.endsWith$default(name2, ".xml", false, 2, (Object) null) || StringsKt.startsWith$default(name2, "assets/", false, 2, (Object) null)) {
                                    zipEntry.setMethod(8);
                                } else {
                                    zipEntry.setMethod(nextEntry.getMethod());
                                    if (nextEntry.getMethod() == 0) {
                                        zipEntry.setSize(nextEntry.getSize());
                                        zipEntry.setCompressedSize(nextEntry.getCompressedSize());
                                        zipEntry.setCrc(nextEntry.getCrc());
                                    }
                                }
                                zipOutputStream2.putNextEntry(zipEntry);
                                ByteStreamsKt.copyTo$default(zipInputStream2, zipOutputStream2, 0, 2, null);
                                zipOutputStream2.closeEntry();
                                zipInputStream2.closeEntry();
                                try {
                                    throw th;
                                } catch (Throwable th) {
                                    CloseableKt.closeFinally(fileOutputStream, th);
                                    throw th;
                                }
                            }
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(zipOutputStream, null);
                            Unit unit2 = Unit.INSTANCE;
                            CloseableKt.closeFinally(fileOutputStream, null);
                            Unit unit3 = Unit.INSTANCE;
                            CloseableKt.closeFinally(zipInputStream, null);
                            Unit unit4 = Unit.INSTANCE;
                            CloseableKt.closeFinally(fileInputStream, null);
                            if (!file.exists()) {
                                return false;
                            }
                            outputFile.delete();
                            file.renameTo(outputFile);
                            return true;
                        } catch (Throwable th2) {
                            try {
                                throw th2;
                            } catch (Throwable th3) {
                                CloseableKt.closeFinally(zipOutputStream, th2);
                                throw th3;
                            }
                        }
                    } catch (Throwable th4) {
                        throw th4;
                    }
                } catch (Throwable th5) {
                    try {
                        throw th5;
                    } catch (Throwable th6) {
                        CloseableKt.closeFinally(zipInputStream, th5);
                        throw th6;
                    }
                }
            } catch (Throwable th7) {
                try {
                    throw th7;
                } catch (Throwable th8) {
                    CloseableKt.closeFinally(fileInputStream, th7);
                    throw th8;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to recompress APK with Level 9 Deflate", e);
            file.delete();
            return false;
        }
    }

    public final boolean updateManifest(String apkPath, final ManifestConfig config) {
        Intrinsics.checkNotNullParameter(apkPath, "apkPath");
        Intrinsics.checkNotNullParameter(config, "config");
        return modifyApk(apkPath, new Function1<ApkModule, Unit>() { // from class: org.catrobat.catroid.apkbuild.ApkToolboxManager.updateManifest.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ApkModule apkModule) {
                invoke2(apkModule);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ApkModule module) {
                Intrinsics.checkNotNullParameter(module, "module");
                AndroidManifestBlock androidManifestBlock = module.getAndroidManifestBlock();
                String packageName = androidManifestBlock.getPackageName();
                String packageName2 = config.getPackageName();
                if (packageName2 == null) {
                    packageName2 = packageName;
                }
                ApkToolboxManager apkToolboxManager = ApkToolboxManager.INSTANCE;
                ResXmlElement manifestElement = androidManifestBlock.getManifestElement();
                Intrinsics.checkNotNullExpressionValue(manifestElement, "getManifestElement(...)");
                Intrinsics.checkNotNull(packageName);
                apkToolboxManager.makeClassNamesAbsolute(manifestElement, packageName);
                if (config.getPackageName() != null) {
                    androidManifestBlock.setPackageName(packageName2);
                    ApkToolboxManager apkToolboxManager2 = ApkToolboxManager.INSTANCE;
                    ResXmlElement manifestElement2 = androidManifestBlock.getManifestElement();
                    Intrinsics.checkNotNullExpressionValue(manifestElement2, "getManifestElement(...)");
                    Intrinsics.checkNotNull(packageName2);
                    apkToolboxManager2.fixManifestAttributes(manifestElement2, packageName, packageName2);
                    if (module.hasTableBlock()) {
                        Iterable<PackageBlock> iterableListPackages = module.getTableBlock().listPackages();
                        Intrinsics.checkNotNullExpressionValue(iterableListPackages, "listPackages(...)");
                        for (PackageBlock packageBlock : iterableListPackages) {
                            if (Intrinsics.areEqual(packageBlock.getName(), packageName)) {
                                packageBlock.setName(packageName2);
                            }
                        }
                        module.getTableBlock().refresh();
                    }
                }
                if (config.getVersionCode() != null) {
                    androidManifestBlock.setVersionCode(config.getVersionCode().intValue());
                }
                if (config.getVersionName() != null) {
                    androidManifestBlock.setVersionName(config.getVersionName());
                }
                if (config.getMinSdkVersion() != null) {
                    androidManifestBlock.setMinSdkVersion(config.getMinSdkVersion().intValue());
                }
                if (config.getTargetSdkVersion() != null) {
                    androidManifestBlock.setTargetSdkVersion(config.getTargetSdkVersion().intValue());
                }
                if (config.getAppName() != null) {
                    androidManifestBlock.getApplicationElement().getOrCreateAndroidAttribute(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, R.attr.label).setValueAsString(config.getAppName());
                }
                if (config.getDebuggable() != null) {
                    androidManifestBlock.getApplicationElement().getOrCreateAndroidAttribute("debuggable", R.attr.debuggable).setValueAsBoolean(config.getDebuggable().booleanValue());
                }
                List<String> permissionsToAdd = config.getPermissionsToAdd();
                if (permissionsToAdd != null) {
                    Iterator<T> it = permissionsToAdd.iterator();
                    while (it.hasNext()) {
                        androidManifestBlock.addUsesPermission((String) it.next());
                    }
                }
                List<String> permissionsToRemove = config.getPermissionsToRemove();
                if (permissionsToRemove != null) {
                    for (String str : permissionsToRemove) {
                        ResXmlElement manifestElement3 = androidManifestBlock.getManifestElement();
                        List<ResXmlElement> listListElements = manifestElement3.listElements("uses-permission");
                        ArrayList arrayList = new ArrayList();
                        for (ResXmlElement resXmlElement : listListElements) {
                            ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = resXmlElement.searchAttributeByResourceId(R.attr.name);
                            if (Intrinsics.areEqual(resXmlAttributeSearchAttributeByResourceId != null ? resXmlAttributeSearchAttributeByResourceId.getValueAsString() : null, str)) {
                                Intrinsics.checkNotNull(resXmlElement);
                                arrayList.add(resXmlElement);
                            }
                        }
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            manifestElement3.remove((ResXmlElement) it2.next());
                        }
                    }
                }
            }
        });
    }

    public final boolean addFileToApk(String apkPath, final File sourceFile, final String pathInsideApk) {
        Intrinsics.checkNotNullParameter(apkPath, "apkPath");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(pathInsideApk, "pathInsideApk");
        if (!sourceFile.exists() || sourceFile.isDirectory()) {
            return false;
        }
        return modifyApk(apkPath, new Function1<ApkModule, Unit>() { // from class: org.catrobat.catroid.apkbuild.ApkToolboxManager.addFileToApk.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ApkModule apkModule) {
                invoke2(apkModule);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ApkModule module) {
                Intrinsics.checkNotNullParameter(module, "module");
                module.getZipEntryMap().remove(pathInsideApk);
                module.add(new FileInputSource(sourceFile, pathInsideApk));
            }
        });
    }

    public final boolean addFolderToApk(String apkPath, final File sourceFolder, final String destPathInApk) {
        Intrinsics.checkNotNullParameter(apkPath, "apkPath");
        Intrinsics.checkNotNullParameter(sourceFolder, "sourceFolder");
        Intrinsics.checkNotNullParameter(destPathInApk, "destPathInApk");
        if (sourceFolder.exists() && sourceFolder.isDirectory()) {
            return modifyApk(apkPath, new Function1<ApkModule, Unit>() { // from class: org.catrobat.catroid.apkbuild.ApkToolboxManager.addFolderToApk.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ApkModule apkModule) {
                    invoke2(apkModule);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ApkModule module) {
                    Intrinsics.checkNotNullParameter(module, "module");
                    FileTreeWalk fileTreeWalkWalk$default = FilesKt.walk$default(sourceFolder, null, 1, null);
                    File file = sourceFolder;
                    String str = destPathInApk;
                    for (File file2 : fileTreeWalkWalk$default) {
                        if (file2.isFile()) {
                            String relativeString = FilesKt.toRelativeString(file2, file);
                            if (str.length() != 0) {
                                relativeString = StringsKt.trimEnd(str, '/') + "/" + relativeString;
                            }
                            module.getZipEntryMap().remove(relativeString);
                            module.add(new FileInputSource(file2, relativeString));
                        }
                    }
                }
            });
        }
        return false;
    }

    public final boolean deleteFromApk(String apkPath, final String pathPattern) {
        Intrinsics.checkNotNullParameter(apkPath, "apkPath");
        Intrinsics.checkNotNullParameter(pathPattern, "pathPattern");
        return modifyApk(apkPath, new Function1<ApkModule, Unit>() { // from class: org.catrobat.catroid.apkbuild.ApkToolboxManager.deleteFromApk.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ApkModule apkModule) {
                invoke2(apkModule);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ApkModule module) {
                Intrinsics.checkNotNullParameter(module, "module");
                String strReplace$default = StringsKt.replace$default(pathPattern, "\\", "/", false, 4, (Object) null);
                ArrayList arrayList = new ArrayList();
                Iterator<InputSource> it = module.getZipEntryMap().listInputSources().iterator();
                while (it.hasNext()) {
                    String name2 = it.next().getName();
                    Intrinsics.checkNotNull(name2);
                    boolean zStartsWith$default = StringsKt.startsWith$default(name2, strReplace$default + "/", false, 2, (Object) null);
                    if (Intrinsics.areEqual(name2, strReplace$default) || zStartsWith$default) {
                        arrayList.add(name2);
                    }
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    module.getZipEntryMap().remove((String) it2.next());
                }
            }
        });
    }

    public final boolean extractFileFromApk(String apkPath, String pathInsideApk, String outputLocalPath) throws Throwable {
        Intrinsics.checkNotNullParameter(apkPath, "apkPath");
        Intrinsics.checkNotNullParameter(pathInsideApk, "pathInsideApk");
        Intrinsics.checkNotNullParameter(outputLocalPath, "outputLocalPath");
        boolean z = false;
        ApkModule apkModule = null;
        try {
            try {
                ApkModule apkModuleLoadApkFile = ApkModule.loadApkFile(new File(apkPath));
                try {
                    InputSource inputSource = apkModuleLoadApkFile.getZipEntryMap().getInputSource(pathInsideApk);
                    if (inputSource != null) {
                        File parentFile = new File(outputLocalPath).getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        InputStream inputStreamOpenStream = inputSource.openStream();
                        try {
                            InputStream inputStream = inputStreamOpenStream;
                            FileOutputStream fileOutputStream = new FileOutputStream(outputLocalPath);
                            try {
                                Intrinsics.checkNotNull(inputStream);
                                ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                                CloseableKt.closeFinally(fileOutputStream, null);
                                CloseableKt.closeFinally(inputStreamOpenStream, null);
                                z = true;
                            } catch (Throwable th) {
                                try {
                                    throw th;
                                } catch (Throwable th2) {
                                    CloseableKt.closeFinally(fileOutputStream, th);
                                    throw th2;
                                }
                            }
                        } catch (Throwable th3) {
                            try {
                                throw th3;
                            } catch (Throwable th4) {
                                CloseableKt.closeFinally(inputStreamOpenStream, th3);
                                throw th4;
                            }
                        }
                    }
                    if (apkModuleLoadApkFile != null) {
                        try {
                            apkModuleLoadApkFile.close();
                        } catch (Exception unused) {
                        }
                    }
                    return z;
                } catch (Exception e) {
                    e = e;
                    apkModule = apkModuleLoadApkFile;
                    e.printStackTrace();
                    if (apkModule != null) {
                        try {
                            apkModule.close();
                        } catch (Exception unused2) {
                        }
                    }
                    return false;
                } catch (Throwable th5) {
                    th = th5;
                    apkModule = apkModuleLoadApkFile;
                    if (apkModule != null) {
                        try {
                            apkModule.close();
                        } catch (Exception unused3) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th6) {
                th = th6;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void makeClassNamesAbsolute(ResXmlElement element, String oldPkg) {
        if (CollectionsKt.listOf((Object[]) new String[]{"activity", "service", "receiver", Constants.EXTRA_PROVIDER, "application"}).contains(element.getName())) {
            ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = element.searchAttributeByResourceId(R.attr.name);
            String valueAsString = resXmlAttributeSearchAttributeByResourceId != null ? resXmlAttributeSearchAttributeByResourceId.getValueAsString() : null;
            if (valueAsString != null) {
                if (!StringsKt.startsWith$default(valueAsString, ".", false, 2, (Object) null)) {
                    if (!StringsKt.contains$default((CharSequence) valueAsString, (CharSequence) ".", false, 2, (Object) null)) {
                        resXmlAttributeSearchAttributeByResourceId.setValueAsString(oldPkg + "." + valueAsString);
                    }
                } else {
                    resXmlAttributeSearchAttributeByResourceId.setValueAsString(oldPkg + valueAsString);
                }
            }
        }
        List<ResXmlElement> listListElements = element.listElements();
        Intrinsics.checkNotNullExpressionValue(listListElements, "listElements(...)");
        for (ResXmlElement resXmlElement : listListElements) {
            ApkToolboxManager apkToolboxManager = INSTANCE;
            Intrinsics.checkNotNull(resXmlElement);
            apkToolboxManager.makeClassNamesAbsolute(resXmlElement, oldPkg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fixManifestAttributes(ResXmlElement element, String oldPkg, String newPkg) {
        String name2 = element.getName();
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"activity", "service", "receiver", Constants.EXTRA_PROVIDER, "application", "activity-alias"});
        List listListOf2 = CollectionsKt.listOf((Object[]) new String[]{"name", "targetActivity", "manageSpaceActivity"});
        Iterator<ResXmlAttribute> attributes = element.getAttributes();
        Intrinsics.checkNotNullExpressionValue(attributes, "getAttributes(...)");
        while (attributes.hasNext()) {
            ResXmlAttribute next = attributes.next();
            String valueAsString = next.getValueAsString();
            if (valueAsString != null) {
                String name3 = next.getName();
                if (!listListOf.contains(name2) || !listListOf2.contains(name3)) {
                    next.setValueAsString(INSTANCE.renameAppIdentityPrefixes(valueAsString, newPkg));
                }
            }
        }
        List<ResXmlElement> listListElements = element.listElements();
        Intrinsics.checkNotNullExpressionValue(listListElements, "listElements(...)");
        for (ResXmlElement resXmlElement : listListElements) {
            ApkToolboxManager apkToolboxManager = INSTANCE;
            Intrinsics.checkNotNull(resXmlElement);
            apkToolboxManager.fixManifestAttributes(resXmlElement, oldPkg, newPkg);
        }
    }

    private final String renameAppIdentityPrefixes(String value, String newPkg) {
        String strReplace$default = value;
        for (String str : CollectionsKt.listOf((Object[]) new String[]{"org.DanVexTeam.NewCatroid", "org.danvexteam.newcatroid", "newcatroid", "org.catrobat.catroid"})) {
            if (StringsKt.contains$default((CharSequence) strReplace$default, (CharSequence) str, false, 2, (Object) null)) {
                strReplace$default = StringsKt.replace$default(strReplace$default, str, newPkg, false, 4, (Object) null);
            }
        }
        return strReplace$default;
    }

    public final boolean signApk(Context context, String inputApkPath, String outputApkPath, String keyStorePath, String keyAlias, String keyPass) throws Throwable {
        char[] charArray;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(inputApkPath, "inputApkPath");
        Intrinsics.checkNotNullParameter(outputApkPath, "outputApkPath");
        try {
            File file = new File(inputApkPath);
            File file2 = new File(outputApkPath);
            if (keyStorePath != null && new File(keyStorePath).exists()) {
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                FileInputStream fileInputStream = new FileInputStream(keyStorePath);
                try {
                    FileInputStream fileInputStream2 = fileInputStream;
                    char[] charArray2 = null;
                    if (keyPass != null) {
                        charArray = keyPass.toCharArray();
                        Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
                    } else {
                        charArray = null;
                    }
                    keyStore.load(fileInputStream2, charArray);
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(fileInputStream, null);
                    if (keyAlias == null) {
                        keyAlias = keyStore.aliases().nextElement();
                    }
                    if (keyPass != null) {
                        charArray2 = keyPass.toCharArray();
                        Intrinsics.checkNotNullExpressionValue(charArray2, "toCharArray(...)");
                    }
                    Key key = keyStore.getKey(keyAlias, charArray2);
                    Intrinsics.checkNotNull(key, "null cannot be cast to non-null type java.security.PrivateKey");
                    Certificate certificate = keyStore.getCertificate(keyAlias);
                    Intrinsics.checkNotNull(certificate, "null cannot be cast to non-null type java.security.cert.X509Certificate");
                    new ApkSigner.Builder((List<ApkSigner.SignerConfig>) CollectionsKt.listOf(new ApkSigner.SignerConfig.Builder("CERT", (PrivateKey) key, CollectionsKt.listOf((X509Certificate) certificate)).build())).setInputApk(file).setOutputApk(file2).setV1SigningEnabled(true).setV2SigningEnabled(true).build().sign();
                    return true;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileInputStream, th);
                        throw th2;
                    }
                }
            }
            File file3 = new File(context.getCacheDir(), "debug_auto.jks");
            if (!file3.exists()) {
                String absolutePath = file3.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                generateKeyStore(absolutePath, "debug", "android", "Debug User");
            }
            return signApk(context, inputApkPath, outputApkPath, file3.getAbsolutePath(), "debug", "android");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean generateKeyStore(String outputPath, String alias, String pass, String commonName) {
        Intrinsics.checkNotNullParameter(outputPath, "outputPath");
        Intrinsics.checkNotNullParameter(alias, "alias");
        Intrinsics.checkNotNullParameter(pass, "pass");
        Intrinsics.checkNotNullParameter(commonName, "commonName");
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPairGenerateKeyPair = keyPairGenerator.generateKeyPair();
            Date date = new Date(System.currentTimeMillis() - DateUtils.MILLIS_PER_DAY);
            Date date2 = new Date(System.currentTimeMillis() + 788400000000L);
            BigInteger bigIntegerValueOf = BigInteger.valueOf(System.currentTimeMillis());
            X500Principal x500Principal = new X500Principal("CN=" + commonName + ", OU=CatroidBuilder, O=NewCatroid, C=WW");
            X509Certificate certificate = new JcaX509CertificateConverter().getCertificate(new JcaX509v3CertificateBuilder(x500Principal, bigIntegerValueOf, date, date2, x500Principal, keyPairGenerateKeyPair.getPublic()).build(new JcaContentSignerBuilder("SHA256WithRSA").build(keyPairGenerateKeyPair.getPrivate())));
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            PrivateKey privateKey = keyPairGenerateKeyPair.getPrivate();
            char[] charArray = pass.toCharArray();
            Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
            keyStore.setKeyEntry(alias, privateKey, charArray, new X509Certificate[]{certificate});
            File parentFile = new File(outputPath).getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
            try {
                char[] charArray2 = pass.toCharArray();
                Intrinsics.checkNotNullExpressionValue(charArray2, "toCharArray(...)");
                keyStore.store(fileOutputStream, charArray2);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream, null);
                Log.d(TAG, "Successfully generated KeyStore: " + outputPath);
                return true;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Key generation failed", e);
            return false;
        }
    }

    private final boolean modifyApk(String apkPath, Function1<? super ApkModule, Unit> action) {
        File file = new File(apkPath + ".tmp");
        File file2 = new File(apkPath);
        ApkModule apkModule = null;
        try {
            if (!file2.exists()) {
                return false;
            }
            ApkModule apkModuleLoadApkFile = ApkModule.loadApkFile(file2);
            try {
                apkModuleLoadApkFile.setLoadDefaultFramework(false);
                if (apkModuleLoadApkFile.hasTableBlock()) {
                    apkModuleLoadApkFile.getTableBlock().getStringPool().setFlagSorted(false);
                    apkModuleLoadApkFile.getTableBlock().getStringPool().getStyleArray().clear();
                }
                action.invoke(apkModuleLoadApkFile);
                if (apkModuleLoadApkFile.hasTableBlock()) {
                    apkModuleLoadApkFile.getTableBlock().refresh();
                }
                apkModuleLoadApkFile.getAndroidManifestBlock().refresh();
                apkModuleLoadApkFile.writeApk(file);
                apkModuleLoadApkFile.close();
                if (file2.delete()) {
                    file.renameTo(file2);
                } else {
                    FilesKt.copyTo$default(file, file2, true, 0, 4, null);
                    file.delete();
                }
                return true;
            } catch (Exception e) {
                e = e;
                apkModule = apkModuleLoadApkFile;
            }
        } catch (Exception e2) {
            e = e2;
        }
        e.printStackTrace();
        if (apkModule != null) {
            try {
                apkModule.close();
            } catch (Exception unused) {
            }
        }
        file.delete();
        return false;
    }
}
