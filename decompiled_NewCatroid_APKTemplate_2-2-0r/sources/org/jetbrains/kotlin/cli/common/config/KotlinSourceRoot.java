package org.jetbrains.kotlin.cli.common.config;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ContentRoots.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J)\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/config/KotlinSourceRoot;", "Lorg/jetbrains/kotlin/cli/common/config/ContentRoot;", "path", "", "isCommon", "", "hmppModuleName", "(Ljava/lang/String;ZLjava/lang/String;)V", "getHmppModuleName", "()Ljava/lang/String;", "()Z", "getPath", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KotlinSourceRoot implements ContentRoot {
    private final String hmppModuleName;
    private final boolean isCommon;
    private final String path;

    public static /* synthetic */ KotlinSourceRoot copy$default(KotlinSourceRoot kotlinSourceRoot, String str, boolean z, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = kotlinSourceRoot.path;
        }
        if ((i & 2) != 0) {
            z = kotlinSourceRoot.isCommon;
        }
        if ((i & 4) != 0) {
            str2 = kotlinSourceRoot.hmppModuleName;
        }
        return kotlinSourceRoot.copy(str, z, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsCommon() {
        return this.isCommon;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getHmppModuleName() {
        return this.hmppModuleName;
    }

    public final KotlinSourceRoot copy(String path, boolean isCommon, String hmppModuleName) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new KotlinSourceRoot(path, isCommon, hmppModuleName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KotlinSourceRoot)) {
            return false;
        }
        KotlinSourceRoot kotlinSourceRoot = (KotlinSourceRoot) other;
        return Intrinsics.areEqual(this.path, kotlinSourceRoot.path) && this.isCommon == kotlinSourceRoot.isCommon && Intrinsics.areEqual(this.hmppModuleName, kotlinSourceRoot.hmppModuleName);
    }

    public int hashCode() {
        int iHashCode = ((this.path.hashCode() * 31) + Boolean.hashCode(this.isCommon)) * 31;
        String str = this.hmppModuleName;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "KotlinSourceRoot(path=" + this.path + ", isCommon=" + this.isCommon + ", hmppModuleName=" + this.hmppModuleName + ')';
    }

    public KotlinSourceRoot(String path, boolean z, String str) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.path = path;
        this.isCommon = z;
        this.hmppModuleName = str;
    }

    public final String getHmppModuleName() {
        return this.hmppModuleName;
    }

    public final String getPath() {
        return this.path;
    }

    public final boolean isCommon() {
        return this.isCommon;
    }
}
