package org.jetbrains.kotlin.resolve.deprecation;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.checkers.OperatorCallCheckerKt;

/* JADX INFO: compiled from: Deprecation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0001HÆ\u0003J\u001d\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0001HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cHÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedOperatorMod;", "Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "currentDeprecation", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;)V", "getCurrentDeprecation", "()Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getTarget", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DeprecatedOperatorMod extends DescriptorBasedDeprecationInfo {
    private final DescriptorBasedDeprecationInfo currentDeprecation;
    private final LanguageVersionSettings languageVersionSettings;

    public static /* synthetic */ DeprecatedOperatorMod copy$default(DeprecatedOperatorMod deprecatedOperatorMod, LanguageVersionSettings languageVersionSettings, DescriptorBasedDeprecationInfo descriptorBasedDeprecationInfo, int i, Object obj) {
        if ((i & 1) != 0) {
            languageVersionSettings = deprecatedOperatorMod.languageVersionSettings;
        }
        if ((i & 2) != 0) {
            descriptorBasedDeprecationInfo = deprecatedOperatorMod.currentDeprecation;
        }
        return deprecatedOperatorMod.copy(languageVersionSettings, descriptorBasedDeprecationInfo);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final DescriptorBasedDeprecationInfo getCurrentDeprecation() {
        return this.currentDeprecation;
    }

    public final DeprecatedOperatorMod copy(LanguageVersionSettings languageVersionSettings, DescriptorBasedDeprecationInfo currentDeprecation) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(currentDeprecation, "currentDeprecation");
        return new DeprecatedOperatorMod(languageVersionSettings, currentDeprecation);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeprecatedOperatorMod)) {
            return false;
        }
        DeprecatedOperatorMod deprecatedOperatorMod = (DeprecatedOperatorMod) other;
        return Intrinsics.areEqual(this.languageVersionSettings, deprecatedOperatorMod.languageVersionSettings) && Intrinsics.areEqual(this.currentDeprecation, deprecatedOperatorMod.currentDeprecation);
    }

    public int hashCode() {
        return (this.languageVersionSettings.hashCode() * 31) + this.currentDeprecation.hashCode();
    }

    public String toString() {
        return "DeprecatedOperatorMod(languageVersionSettings=" + this.languageVersionSettings + ", currentDeprecation=" + this.currentDeprecation + ')';
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final DescriptorBasedDeprecationInfo getCurrentDeprecation() {
        return this.currentDeprecation;
    }

    public DeprecatedOperatorMod(LanguageVersionSettings languageVersionSettings, DescriptorBasedDeprecationInfo currentDeprecation) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(currentDeprecation, "currentDeprecation");
        this.languageVersionSettings = languageVersionSettings;
        this.currentDeprecation = currentDeprecation;
        OperatorCallCheckerKt.shouldWarnAboutDeprecatedModFromBuiltIns(languageVersionSettings);
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public DeprecationLevelValue getDeprecationLevel() {
        ApiVersion apiVersion = this.languageVersionSettings.getApiVersion();
        if (Intrinsics.areEqual(apiVersion, ApiVersion.KOTLIN_1_1) ? true : Intrinsics.areEqual(apiVersion, ApiVersion.KOTLIN_1_2)) {
            return DeprecationLevelValue.WARNING;
        }
        return Intrinsics.areEqual(apiVersion, ApiVersion.KOTLIN_1_3) ? DeprecationLevelValue.ERROR : DeprecationLevelValue.ERROR;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public String getMessage() {
        return this.currentDeprecation.getMessage();
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo
    public DeclarationDescriptor getTarget() {
        return this.currentDeprecation.getTarget();
    }
}
