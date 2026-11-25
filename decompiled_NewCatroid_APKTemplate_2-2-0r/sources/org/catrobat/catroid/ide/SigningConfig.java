package org.catrobat.catroid.ide;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/SigningConfig.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/ide/SigningConfig;", "", "keystorePath", "", "keystorePass", "keyAlias", "keyPass", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getKeyAlias", "()Ljava/lang/String;", "getKeyPass", "getKeystorePass", "getKeystorePath", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SigningConfig {
    private final String keyAlias;
    private final String keyPass;
    private final String keystorePass;
    private final String keystorePath;

    public static /* synthetic */ SigningConfig copy$default(SigningConfig signingConfig, String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = signingConfig.keystorePath;
        }
        if ((i & 2) != 0) {
            str2 = signingConfig.keystorePass;
        }
        if ((i & 4) != 0) {
            str3 = signingConfig.keyAlias;
        }
        if ((i & 8) != 0) {
            str4 = signingConfig.keyPass;
        }
        return signingConfig.copy(str, str2, str3, str4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getKeystorePath() {
        return this.keystorePath;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getKeystorePass() {
        return this.keystorePass;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getKeyAlias() {
        return this.keyAlias;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getKeyPass() {
        return this.keyPass;
    }

    public final SigningConfig copy(String keystorePath, String keystorePass, String keyAlias, String keyPass) {
        Intrinsics.checkNotNullParameter(keystorePath, "keystorePath");
        Intrinsics.checkNotNullParameter(keystorePass, "keystorePass");
        Intrinsics.checkNotNullParameter(keyAlias, "keyAlias");
        Intrinsics.checkNotNullParameter(keyPass, "keyPass");
        return new SigningConfig(keystorePath, keystorePass, keyAlias, keyPass);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SigningConfig)) {
            return false;
        }
        SigningConfig signingConfig = (SigningConfig) other;
        return Intrinsics.areEqual(this.keystorePath, signingConfig.keystorePath) && Intrinsics.areEqual(this.keystorePass, signingConfig.keystorePass) && Intrinsics.areEqual(this.keyAlias, signingConfig.keyAlias) && Intrinsics.areEqual(this.keyPass, signingConfig.keyPass);
    }

    public int hashCode() {
        return (((((this.keystorePath.hashCode() * 31) + this.keystorePass.hashCode()) * 31) + this.keyAlias.hashCode()) * 31) + this.keyPass.hashCode();
    }

    public String toString() {
        return "SigningConfig(keystorePath=" + this.keystorePath + ", keystorePass=" + this.keystorePass + ", keyAlias=" + this.keyAlias + ", keyPass=" + this.keyPass + Constant.AFTER_QUTO;
    }

    public SigningConfig(String keystorePath, String keystorePass, String keyAlias, String keyPass) {
        Intrinsics.checkNotNullParameter(keystorePath, "keystorePath");
        Intrinsics.checkNotNullParameter(keystorePass, "keystorePass");
        Intrinsics.checkNotNullParameter(keyAlias, "keyAlias");
        Intrinsics.checkNotNullParameter(keyPass, "keyPass");
        this.keystorePath = keystorePath;
        this.keystorePass = keystorePass;
        this.keyAlias = keyAlias;
        this.keyPass = keyPass;
    }

    public final String getKeystorePath() {
        return this.keystorePath;
    }

    public final String getKeystorePass() {
        return this.keystorePass;
    }

    public final String getKeyAlias() {
        return this.keyAlias;
    }

    public final String getKeyPass() {
        return this.keyPass;
    }
}
