package org.jetbrains.kotlin.incremental.components;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LookupTracker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\bHÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cHÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/incremental/components/LookupInfo;", "Ljava/io/Serializable;", "filePath", "", "position", "Lorg/jetbrains/kotlin/incremental/components/Position;", "scopeFqName", "scopeKind", "Lorg/jetbrains/kotlin/incremental/components/ScopeKind;", "name", "(Ljava/lang/String;Lorg/jetbrains/kotlin/incremental/components/Position;Ljava/lang/String;Lorg/jetbrains/kotlin/incremental/components/ScopeKind;Ljava/lang/String;)V", "getFilePath", "()Ljava/lang/String;", "getName", "getPosition", "()Lorg/jetbrains/kotlin/incremental/components/Position;", "getScopeFqName", "getScopeKind", "()Lorg/jetbrains/kotlin/incremental/components/ScopeKind;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LookupInfo implements Serializable {
    private final String filePath;
    private final String name;
    private final Position position;
    private final String scopeFqName;
    private final ScopeKind scopeKind;

    public static /* synthetic */ LookupInfo copy$default(LookupInfo lookupInfo, String str, Position position, String str2, ScopeKind scopeKind, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = lookupInfo.filePath;
        }
        if ((i & 2) != 0) {
            position = lookupInfo.position;
        }
        Position position2 = position;
        if ((i & 4) != 0) {
            str2 = lookupInfo.scopeFqName;
        }
        String str4 = str2;
        if ((i & 8) != 0) {
            scopeKind = lookupInfo.scopeKind;
        }
        ScopeKind scopeKind2 = scopeKind;
        if ((i & 16) != 0) {
            str3 = lookupInfo.name;
        }
        return lookupInfo.copy(str, position2, str4, scopeKind2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getFilePath() {
        return this.filePath;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Position getPosition() {
        return this.position;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getScopeFqName() {
        return this.scopeFqName;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final ScopeKind getScopeKind() {
        return this.scopeKind;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getName() {
        return this.name;
    }

    public final LookupInfo copy(String filePath, Position position, String scopeFqName, ScopeKind scopeKind, String name2) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(scopeFqName, "scopeFqName");
        Intrinsics.checkNotNullParameter(scopeKind, "scopeKind");
        Intrinsics.checkNotNullParameter(name2, "name");
        return new LookupInfo(filePath, position, scopeFqName, scopeKind, name2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LookupInfo)) {
            return false;
        }
        LookupInfo lookupInfo = (LookupInfo) other;
        return Intrinsics.areEqual(this.filePath, lookupInfo.filePath) && Intrinsics.areEqual(this.position, lookupInfo.position) && Intrinsics.areEqual(this.scopeFqName, lookupInfo.scopeFqName) && this.scopeKind == lookupInfo.scopeKind && Intrinsics.areEqual(this.name, lookupInfo.name);
    }

    public int hashCode() {
        return (((((((this.filePath.hashCode() * 31) + this.position.hashCode()) * 31) + this.scopeFqName.hashCode()) * 31) + this.scopeKind.hashCode()) * 31) + this.name.hashCode();
    }

    public String toString() {
        return "LookupInfo(filePath=" + this.filePath + ", position=" + this.position + ", scopeFqName=" + this.scopeFqName + ", scopeKind=" + this.scopeKind + ", name=" + this.name + ')';
    }

    public LookupInfo(String filePath, Position position, String scopeFqName, ScopeKind scopeKind, String name2) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(scopeFqName, "scopeFqName");
        Intrinsics.checkNotNullParameter(scopeKind, "scopeKind");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.filePath = filePath;
        this.position = position;
        this.scopeFqName = scopeFqName;
        this.scopeKind = scopeKind;
        this.name = name2;
    }

    public final String getFilePath() {
        return this.filePath;
    }

    public final Position getPosition() {
        return this.position;
    }

    public final String getScopeFqName() {
        return this.scopeFqName;
    }

    public final ScopeKind getScopeKind() {
        return this.scopeKind;
    }

    public final String getName() {
        return this.name;
    }
}
