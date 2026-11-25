package org.jetbrains.kotlin.platform;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TargetPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0012\u0010\u0005\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0007R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/platform/SimplePlatform;", "", "platformName", "", "(Ljava/lang/String;)V", "oldFashionedDescription", "getOldFashionedDescription", "()Ljava/lang/String;", "getPlatformName", "targetName", "getTargetName", "targetPlatformVersion", "Lorg/jetbrains/kotlin/platform/TargetPlatformVersion;", "getTargetPlatformVersion", "()Lorg/jetbrains/kotlin/platform/TargetPlatformVersion;", "toString", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SimplePlatform {
    private final String platformName;
    private final TargetPlatformVersion targetPlatformVersion;

    public abstract String getOldFashionedDescription();

    public SimplePlatform(String platformName) {
        Intrinsics.checkNotNullParameter(platformName, "platformName");
        this.platformName = platformName;
        this.targetPlatformVersion = TargetPlatformVersion.NoVersion.INSTANCE;
    }

    public final String getPlatformName() {
        return this.platformName;
    }

    public String toString() {
        String targetName = getTargetName();
        return targetName.length() > 0 ? this.platformName + " (" + targetName + ')' : this.platformName;
    }

    public String getTargetName() {
        return getTargetPlatformVersion().getDescription();
    }

    public TargetPlatformVersion getTargetPlatformVersion() {
        return this.targetPlatformVersion;
    }
}
