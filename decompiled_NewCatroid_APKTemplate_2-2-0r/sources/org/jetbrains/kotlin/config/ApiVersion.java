package org.jetbrains.kotlin.config;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.DescriptionAware;

/* JADX INFO: compiled from: ApiVersion.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u00022\u00020\u0003:\u0001\u0019B\u0017\b\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u0015\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0016J\b\u0010\u0018\u001a\u00020\u0007H\u0016R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0014\u0010\f\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000bR\u0014\u0010\r\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/config/ApiVersion;", "", "Lorg/jetbrains/kotlin/utils/DescriptionAware;", "Lorg/jetbrains/kotlin/config/LanguageOrApiVersion;", "version", "Lorg/jetbrains/kotlin/config/MavenComparableVersion;", "versionString", "", "(Lorg/jetbrains/kotlin/config/MavenComparableVersion;Ljava/lang/String;)V", "isDeprecated", "", "()Z", "isStable", "isUnsupported", "getVersion", "()Lorg/jetbrains/kotlin/config/MavenComparableVersion;", "getVersionString", "()Ljava/lang/String;", "compareTo", "", "other", Namer.EQUALS_METHOD_NAME, "", "hashCode", "toString", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApiVersion implements Comparable<ApiVersion>, LanguageOrApiVersion, DescriptionAware {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final ApiVersion FIRST_NON_DEPRECATED;
    public static final ApiVersion FIRST_SUPPORTED;
    public static final ApiVersion KOTLIN_1_0;
    public static final ApiVersion KOTLIN_1_1;
    public static final ApiVersion KOTLIN_1_2;
    public static final ApiVersion KOTLIN_1_3;
    public static final ApiVersion KOTLIN_1_4;
    public static final ApiVersion KOTLIN_1_5;
    public static final ApiVersion KOTLIN_1_6;
    public static final ApiVersion KOTLIN_1_7;
    public static final ApiVersion KOTLIN_1_8;
    public static final ApiVersion KOTLIN_1_9;
    public static final ApiVersion KOTLIN_2_0;
    public static final ApiVersion KOTLIN_2_1;
    public static final ApiVersion LATEST;
    public static final ApiVersion LATEST_STABLE;
    private final MavenComparableVersion version;
    private final String versionString;

    public /* synthetic */ ApiVersion(MavenComparableVersion mavenComparableVersion, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(mavenComparableVersion, str);
    }

    @JvmStatic
    public static final ApiVersion createByLanguageVersion(LanguageVersion languageVersion) {
        return INSTANCE.createByLanguageVersion(languageVersion);
    }

    private ApiVersion(MavenComparableVersion mavenComparableVersion, String str) {
        this.version = mavenComparableVersion;
        this.versionString = str;
    }

    public final MavenComparableVersion getVersion() {
        return this.version;
    }

    @Override // org.jetbrains.kotlin.config.LanguageOrApiVersion
    public String getVersionString() {
        return this.versionString;
    }

    @Override // org.jetbrains.kotlin.config.LanguageOrApiVersion
    public boolean isStable() {
        return compareTo(LATEST_STABLE) <= 0;
    }

    @Override // org.jetbrains.kotlin.config.LanguageOrApiVersion
    public boolean isDeprecated() {
        return FIRST_SUPPORTED.compareTo(this) <= 0 && compareTo(FIRST_NON_DEPRECATED) < 0;
    }

    @Override // org.jetbrains.kotlin.config.LanguageOrApiVersion
    public boolean isUnsupported() {
        return compareTo(FIRST_SUPPORTED) < 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(ApiVersion other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return this.version.compareTo(other.version);
    }

    public boolean equals(Object other) {
        ApiVersion apiVersion = other instanceof ApiVersion ? (ApiVersion) other : null;
        return Intrinsics.areEqual(apiVersion != null ? apiVersion.version : null, this.version);
    }

    public int hashCode() {
        return this.version.hashCode();
    }

    public String toString() {
        return getVersionString();
    }

    /* JADX INFO: compiled from: ApiVersion.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0018\u001a\u00020\u0019R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/config/ApiVersion$Companion;", "", "()V", "FIRST_NON_DEPRECATED", "Lorg/jetbrains/kotlin/config/ApiVersion;", "FIRST_SUPPORTED", "KOTLIN_1_0", "KOTLIN_1_1", "KOTLIN_1_2", "KOTLIN_1_3", "KOTLIN_1_4", "KOTLIN_1_5", "KOTLIN_1_6", "KOTLIN_1_7", "KOTLIN_1_8", "KOTLIN_1_9", "KOTLIN_2_0", "KOTLIN_2_1", "LATEST", "LATEST_STABLE", "createByLanguageVersion", "version", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "parse", "versionString", "", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final ApiVersion createByLanguageVersion(LanguageVersion version) {
            Intrinsics.checkNotNullParameter(version, "version");
            ApiVersion apiVersion = parse(version.getVersionString());
            Intrinsics.checkNotNull(apiVersion);
            return apiVersion;
        }

        public final ApiVersion parse(String versionString) {
            Intrinsics.checkNotNullParameter(versionString, "versionString");
            try {
                return new ApiVersion(new MavenComparableVersion(versionString), versionString, null);
            } catch (Exception unused) {
                return null;
            }
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        KOTLIN_1_0 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_0);
        KOTLIN_1_1 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_1);
        KOTLIN_1_2 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_2);
        KOTLIN_1_3 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_3);
        KOTLIN_1_4 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_4);
        KOTLIN_1_5 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_5);
        KOTLIN_1_6 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_6);
        KOTLIN_1_7 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_7);
        KOTLIN_1_8 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_8);
        KOTLIN_1_9 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_1_9);
        KOTLIN_2_0 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_2_0);
        KOTLIN_2_1 = companion.createByLanguageVersion(LanguageVersion.KOTLIN_2_1);
        LATEST = companion.createByLanguageVersion((LanguageVersion) ArraysKt.last(LanguageVersion.values()));
        LATEST_STABLE = companion.createByLanguageVersion(LanguageVersion.LATEST_STABLE);
        FIRST_SUPPORTED = companion.createByLanguageVersion(LanguageVersion.FIRST_API_SUPPORTED);
        FIRST_NON_DEPRECATED = companion.createByLanguageVersion(LanguageVersion.FIRST_NON_DEPRECATED);
    }
}
