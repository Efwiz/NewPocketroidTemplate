package org.jetbrains.kotlin.platform.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.platform.TargetPlatformVersion;

/* JADX INFO: compiled from: JvmPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/platform/jvm/JdkPlatform;", "Lorg/jetbrains/kotlin/platform/jvm/JvmPlatform;", "targetVersion", "Lorg/jetbrains/kotlin/config/JvmTarget;", "(Lorg/jetbrains/kotlin/config/JvmTarget;)V", "oldFashionedDescription", "", "getOldFashionedDescription", "()Ljava/lang/String;", "targetPlatformVersion", "Lorg/jetbrains/kotlin/platform/TargetPlatformVersion;", "getTargetPlatformVersion", "()Lorg/jetbrains/kotlin/platform/TargetPlatformVersion;", "getTargetVersion", "()Lorg/jetbrains/kotlin/config/JvmTarget;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JdkPlatform extends JvmPlatform {
    private final JvmTarget targetVersion;

    public JdkPlatform(JvmTarget targetVersion) {
        Intrinsics.checkNotNullParameter(targetVersion, "targetVersion");
        this.targetVersion = targetVersion;
    }

    public final JvmTarget getTargetVersion() {
        return this.targetVersion;
    }

    @Override // org.jetbrains.kotlin.platform.SimplePlatform
    public String toString() {
        return getPlatformName() + " (" + this.targetVersion + ')';
    }

    @Override // org.jetbrains.kotlin.platform.jvm.JvmPlatform, org.jetbrains.kotlin.platform.SimplePlatform
    public String getOldFashionedDescription() {
        return "JVM " + this.targetVersion.getDescription();
    }

    @Override // org.jetbrains.kotlin.platform.SimplePlatform
    public TargetPlatformVersion getTargetPlatformVersion() {
        return this.targetVersion;
    }

    public boolean equals(Object other) {
        return other instanceof JdkPlatform;
    }

    public int hashCode() {
        return Reflection.getOrCreateKotlinClass(JdkPlatform.class).hashCode();
    }
}
