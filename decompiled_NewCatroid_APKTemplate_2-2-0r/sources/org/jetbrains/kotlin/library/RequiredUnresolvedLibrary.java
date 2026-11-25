package org.jetbrains.kotlin.library;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: UnresolvedLibrary.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\u0010\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0003H\u0016J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/library/RequiredUnresolvedLibrary;", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "path", "", "libraryVersion", "(Ljava/lang/String;Ljava/lang/String;)V", "getLibraryVersion", "()Ljava/lang/String;", "getPath", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "substitutePath", "newPath", "toString", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final /* data */ class RequiredUnresolvedLibrary extends UnresolvedLibrary {
    private final String libraryVersion;
    private final String path;

    public static /* synthetic */ RequiredUnresolvedLibrary copy$default(RequiredUnresolvedLibrary requiredUnresolvedLibrary, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = requiredUnresolvedLibrary.path;
        }
        if ((i & 2) != 0) {
            str2 = requiredUnresolvedLibrary.libraryVersion;
        }
        return requiredUnresolvedLibrary.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getLibraryVersion() {
        return this.libraryVersion;
    }

    public final RequiredUnresolvedLibrary copy(String path, String libraryVersion) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new RequiredUnresolvedLibrary(path, libraryVersion);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RequiredUnresolvedLibrary)) {
            return false;
        }
        RequiredUnresolvedLibrary requiredUnresolvedLibrary = (RequiredUnresolvedLibrary) other;
        return Intrinsics.areEqual(this.path, requiredUnresolvedLibrary.path) && Intrinsics.areEqual(this.libraryVersion, requiredUnresolvedLibrary.libraryVersion);
    }

    public int hashCode() {
        int iHashCode = this.path.hashCode() * 31;
        String str = this.libraryVersion;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "RequiredUnresolvedLibrary(path=" + this.path + ", libraryVersion=" + this.libraryVersion + ')';
    }

    @Override // org.jetbrains.kotlin.library.UnresolvedLibrary
    public String getPath() {
        return this.path;
    }

    @Override // org.jetbrains.kotlin.library.UnresolvedLibrary
    public String getLibraryVersion() {
        return this.libraryVersion;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RequiredUnresolvedLibrary(String path, String str) {
        super(null);
        Intrinsics.checkNotNullParameter(path, "path");
        this.path = path;
        this.libraryVersion = str;
    }

    @Override // org.jetbrains.kotlin.library.UnresolvedLibrary
    public RequiredUnresolvedLibrary substitutePath(String newPath) {
        Intrinsics.checkNotNullParameter(newPath, "newPath");
        return copy$default(this, newPath, null, 2, null);
    }
}
