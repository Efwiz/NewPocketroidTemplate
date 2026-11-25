package org.jetbrains.kotlin.metadata.jvm.deserialization;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: JvmMetadataVersion.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0013\b\u0016\u0012\n\u0010\u0002\u001a\u00020\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0015\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0000J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0000H\u0002J\b\u0010\u000f\u001a\u00020\bH\u0016J\u000e\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0000H\u0002J\u0006\u0010\u0013\u001a\u00020\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "numbers", "", "", "([I)V", "versionArray", "isStrictSemantics", "", "([IZ)V", "()Z", "isCompatible", "metadataVersionFromLanguageVersion", "isCompatibleInternal", "limitVersion", "isCompatibleWithCurrentCompilerVersion", "lastSupportedVersionWithThisLanguageVersion", "newerThan", "other", ES6Iterator.NEXT_METHOD, "Companion", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmMetadataVersion extends BinaryVersion {
    public static final JvmMetadataVersion INSTANCE;
    public static final JvmMetadataVersion INSTANCE_NEXT;
    public static final JvmMetadataVersion INVALID_VERSION;
    private final boolean isStrictSemantics;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmMetadataVersion(int[] versionArray, boolean z) {
        super(Arrays.copyOf(versionArray, versionArray.length));
        Intrinsics.checkNotNullParameter(versionArray, "versionArray");
        this.isStrictSemantics = z;
    }

    /* JADX INFO: renamed from: isStrictSemantics, reason: from getter */
    public final boolean getIsStrictSemantics() {
        return this.isStrictSemantics;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JvmMetadataVersion(int... numbers) {
        this(numbers, false);
        Intrinsics.checkNotNullParameter(numbers, "numbers");
    }

    public final JvmMetadataVersion lastSupportedVersionWithThisLanguageVersion(boolean isStrictSemantics) {
        JvmMetadataVersion jvmMetadataVersion = isStrictSemantics ? INSTANCE : INSTANCE_NEXT;
        return jvmMetadataVersion.newerThan(this) ? jvmMetadataVersion : this;
    }

    @Override // org.jetbrains.kotlin.metadata.deserialization.BinaryVersion
    public boolean isCompatibleWithCurrentCompilerVersion() {
        return isCompatibleInternal(INSTANCE_NEXT);
    }

    public final boolean isCompatible(JvmMetadataVersion metadataVersionFromLanguageVersion) {
        Intrinsics.checkNotNullParameter(metadataVersionFromLanguageVersion, "metadataVersionFromLanguageVersion");
        if (getMajor() == 2 && getMinor() == 0) {
            JvmMetadataVersion jvmMetadataVersion = INSTANCE;
            if (jvmMetadataVersion.getMajor() == 1 && jvmMetadataVersion.getMinor() == 8) {
                return true;
            }
        }
        return isCompatibleInternal(metadataVersionFromLanguageVersion.lastSupportedVersionWithThisLanguageVersion(this.isStrictSemantics));
    }

    private final boolean isCompatibleInternal(JvmMetadataVersion limitVersion) {
        if ((getMajor() == 1 && getMinor() == 0) || getMajor() == 0) {
            return false;
        }
        return !newerThan(limitVersion);
    }

    public final JvmMetadataVersion next() {
        return (getMajor() == 1 && getMinor() == 9) ? new JvmMetadataVersion(2, 0, 0) : new JvmMetadataVersion(getMajor(), getMinor() + 1, 0);
    }

    private final boolean newerThan(JvmMetadataVersion other) {
        if (getMajor() > other.getMajor()) {
            return true;
        }
        return getMajor() >= other.getMajor() && getMinor() > other.getMinor();
    }

    static {
        JvmMetadataVersion jvmMetadataVersion = new JvmMetadataVersion(1, 9, 0);
        INSTANCE = jvmMetadataVersion;
        INSTANCE_NEXT = jvmMetadataVersion.next();
        INVALID_VERSION = new JvmMetadataVersion(new int[0]);
    }
}
