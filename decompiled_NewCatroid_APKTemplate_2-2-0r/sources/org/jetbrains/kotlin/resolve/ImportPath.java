package org.jetbrains.kotlin.resolve;

import com.reandroid.apk.PathMap;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.renderer.RenderingUtilsKt;

/* JADX INFO: compiled from: ImportPath.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB#\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0006\u0010\u001a\u001a\u00020\u0005J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\b\u0010\u001d\u001a\u00020\u0011H\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\r\u001a\u0004\u0018\u00010\u00078F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ImportPath;", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "isAllUnder", "", PathMap.NAME_alias, "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/FqName;ZLorg/jetbrains/kotlin/name/Name;)V", "getAlias", "()Lorg/jetbrains/kotlin/name/Name;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "importedName", "getImportedName", "()Z", "pathStr", "", "getPathStr", "()Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hasAlias", "hashCode", "", "toString", "Companion", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ImportPath {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Name alias;
    private final FqName fqName;
    private final boolean isAllUnder;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ImportPath(FqName fqName, boolean z) {
        this(fqName, z, null, 4, null);
        Intrinsics.checkNotNullParameter(fqName, "fqName");
    }

    public static /* synthetic */ ImportPath copy$default(ImportPath importPath, FqName fqName, boolean z, Name name2, int i, Object obj) {
        if ((i & 1) != 0) {
            fqName = importPath.fqName;
        }
        if ((i & 2) != 0) {
            z = importPath.isAllUnder;
        }
        if ((i & 4) != 0) {
            name2 = importPath.alias;
        }
        return importPath.copy(fqName, z, name2);
    }

    @JvmStatic
    public static final ImportPath fromString(String str) {
        return INSTANCE.fromString(str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FqName getFqName() {
        return this.fqName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsAllUnder() {
        return this.isAllUnder;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Name getAlias() {
        return this.alias;
    }

    public final ImportPath copy(FqName fqName, boolean isAllUnder, Name alias) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return new ImportPath(fqName, isAllUnder, alias);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ImportPath)) {
            return false;
        }
        ImportPath importPath = (ImportPath) other;
        return Intrinsics.areEqual(this.fqName, importPath.fqName) && this.isAllUnder == importPath.isAllUnder && Intrinsics.areEqual(this.alias, importPath.alias);
    }

    public int hashCode() {
        int iHashCode = ((this.fqName.hashCode() * 31) + Boolean.hashCode(this.isAllUnder)) * 31;
        Name name2 = this.alias;
        return iHashCode + (name2 == null ? 0 : name2.hashCode());
    }

    public ImportPath(FqName fqName, boolean z, Name name2) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.fqName = fqName;
        this.isAllUnder = z;
        this.alias = name2;
    }

    public /* synthetic */ ImportPath(FqName fqName, boolean z, Name name2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(fqName, z, (i & 4) != 0 ? null : name2);
    }

    public final Name getAlias() {
        return this.alias;
    }

    public final FqName getFqName() {
        return this.fqName;
    }

    public final boolean isAllUnder() {
        return this.isAllUnder;
    }

    public final String getPathStr() {
        StringBuilder sb = new StringBuilder();
        FqNameUnsafe unsafe = this.fqName.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        return sb.append(RenderingUtilsKt.render(unsafe)).append(this.isAllUnder ? ".*" : "").toString();
    }

    public String toString() {
        return getPathStr() + (this.alias != null ? " as " + this.alias.asString() : "");
    }

    public final boolean hasAlias() {
        return this.alias != null;
    }

    public final Name getImportedName() {
        if (this.isAllUnder) {
            return null;
        }
        Name name2 = this.alias;
        return name2 == null ? this.fqName.shortName() : name2;
    }

    /* JADX INFO: compiled from: ImportPath.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ImportPath$Companion;", "", "()V", "fromString", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "pathStr", "", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final ImportPath fromString(String pathStr) {
            Intrinsics.checkNotNullParameter(pathStr, "pathStr");
            if (StringsKt.endsWith$default(pathStr, ".*", false, 2, (Object) null)) {
                String strSubstring = pathStr.substring(0, pathStr.length() - 2);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                return new ImportPath(new FqName(strSubstring), true, null, 4, null);
            }
            return new ImportPath(new FqName(pathStr), false, null, 4, null);
        }
    }
}
