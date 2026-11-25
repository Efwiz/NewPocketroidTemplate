package org.jetbrains.kotlin.cli.jvm.index;

import java.util.EnumSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmDependenciesIndex.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u001b2\u00020\u0001:\u0002\u001a\u001bB!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "prefixFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lcom/intellij/openapi/vfs/VirtualFile;Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;Lorg/jetbrains/kotlin/name/FqName;)V", "getFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "getPrefixFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getType", "()Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "RootType", "RootTypes", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JavaRoot {
    private static final Set<RootType> OnlyBinary;
    private static final Set<RootType> OnlySource;

    /* JADX INFO: renamed from: RootTypes, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<RootType> SourceAndBinary;
    private final VirtualFile file;
    private final FqName prefixFqName;
    private final RootType type;

    /* JADX INFO: compiled from: JvmDependenciesIndex.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "", "(Ljava/lang/String;I)V", "SOURCE", "BINARY", "BINARY_SIG", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum RootType {
        SOURCE,
        BINARY,
        BINARY_SIG;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<RootType> getEntries() {
            return $ENTRIES;
        }
    }

    public static /* synthetic */ JavaRoot copy$default(JavaRoot javaRoot, VirtualFile virtualFile, RootType rootType, FqName fqName, int i, Object obj) {
        if ((i & 1) != 0) {
            virtualFile = javaRoot.file;
        }
        if ((i & 2) != 0) {
            rootType = javaRoot.type;
        }
        if ((i & 4) != 0) {
            fqName = javaRoot.prefixFqName;
        }
        return javaRoot.copy(virtualFile, rootType, fqName);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final VirtualFile getFile() {
        return this.file;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final RootType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final FqName getPrefixFqName() {
        return this.prefixFqName;
    }

    public final JavaRoot copy(VirtualFile file, RootType type, FqName prefixFqName) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(type, "type");
        return new JavaRoot(file, type, prefixFqName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JavaRoot)) {
            return false;
        }
        JavaRoot javaRoot = (JavaRoot) other;
        return Intrinsics.areEqual(this.file, javaRoot.file) && this.type == javaRoot.type && Intrinsics.areEqual(this.prefixFqName, javaRoot.prefixFqName);
    }

    public int hashCode() {
        int iHashCode = ((this.file.hashCode() * 31) + this.type.hashCode()) * 31;
        FqName fqName = this.prefixFqName;
        return iHashCode + (fqName == null ? 0 : fqName.hashCode());
    }

    public String toString() {
        return "JavaRoot(file=" + this.file + ", type=" + this.type + ", prefixFqName=" + this.prefixFqName + ')';
    }

    public JavaRoot(VirtualFile file, RootType type, FqName fqName) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(type, "type");
        this.file = file;
        this.type = type;
        this.prefixFqName = fqName;
    }

    public /* synthetic */ JavaRoot(VirtualFile virtualFile, RootType rootType, FqName fqName, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(virtualFile, rootType, (i & 4) != 0 ? null : fqName);
    }

    public final VirtualFile getFile() {
        return this.file;
    }

    public final FqName getPrefixFqName() {
        return this.prefixFqName;
    }

    public final RootType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.jvm.index.JavaRoot$RootTypes, reason: from kotlin metadata */
    /* JADX INFO: compiled from: JvmDependenciesIndex.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootTypes;", "", "()V", "OnlyBinary", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "getOnlyBinary", "()Ljava/util/Set;", "OnlySource", "getOnlySource", "SourceAndBinary", "getSourceAndBinary", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<RootType> getOnlyBinary() {
            return JavaRoot.OnlyBinary;
        }

        public final Set<RootType> getOnlySource() {
            return JavaRoot.OnlySource;
        }

        public final Set<RootType> getSourceAndBinary() {
            return JavaRoot.SourceAndBinary;
        }
    }

    static {
        EnumSet enumSetOf = EnumSet.of(RootType.BINARY, RootType.BINARY_SIG);
        Intrinsics.checkNotNullExpressionValue(enumSetOf, "of(...)");
        OnlyBinary = enumSetOf;
        EnumSet enumSetOf2 = EnumSet.of(RootType.SOURCE);
        Intrinsics.checkNotNullExpressionValue(enumSetOf2, "of(...)");
        OnlySource = enumSetOf2;
        EnumSet enumSetOf3 = EnumSet.of(RootType.BINARY, RootType.BINARY_SIG, RootType.SOURCE);
        Intrinsics.checkNotNullExpressionValue(enumSetOf3, "of(...)");
        SourceAndBinary = enumSetOf3;
    }
}
