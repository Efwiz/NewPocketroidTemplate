package org.jetbrains.kotlin.resolve.deprecation;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.config.KotlinCompilerVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.MavenComparableVersion;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;

/* JADX INFO: compiled from: CommonDeprecationUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"createVersion", "Lorg/jetbrains/kotlin/config/MavenComparableVersion;", "version", "", "isFulfilled", "", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonDeprecationUtilsKt {

    /* JADX INFO: compiled from: CommonDeprecationUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.VersionRequirement.VersionKind.values().length];
            try {
                iArr[ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.VersionRequirement.VersionKind.API_VERSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean isFulfilled(VersionRequirement versionRequirement, LanguageVersionSettings languageVersionSettings) {
        MavenComparableVersion mavenComparableVersion;
        String version;
        String strSubstringBefore$default;
        Intrinsics.checkNotNullParameter(versionRequirement, "<this>");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        MavenComparableVersion mavenComparableVersionCreateVersion = createVersion(versionRequirement.getVersion().asString());
        int i = WhenMappings.$EnumSwitchMapping$0[versionRequirement.getKind().ordinal()];
        if (i == 1) {
            mavenComparableVersion = new MavenComparableVersion(languageVersionSettings.getLanguageVersion().getVersionString());
        } else if (i != 2) {
            mavenComparableVersion = null;
            if (i == 3 && (version = KotlinCompilerVersion.getVersion()) != null && (strSubstringBefore$default = StringsKt.substringBefore$default(version, '-', (String) null, 2, (Object) null)) != null) {
                mavenComparableVersion = createVersion(strSubstringBefore$default);
            }
        } else {
            mavenComparableVersion = languageVersionSettings.getApiVersion().getVersion();
        }
        return mavenComparableVersion == null || mavenComparableVersion.compareTo(mavenComparableVersionCreateVersion) >= 0;
    }

    private static final MavenComparableVersion createVersion(String str) {
        try {
            return new MavenComparableVersion(str);
        } catch (Exception unused) {
            return null;
        }
    }
}
