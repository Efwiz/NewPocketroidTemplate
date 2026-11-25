package org.jetbrains.kotlin.resolve.jvm.modules;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JavaModule.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0003\u0017\u0018\u0019J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0018\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u000fH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001aÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;", "", "isSourceModule", "", "()Z", "moduleInfoFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getModuleInfoFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "moduleRoots", "", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Root;", "getModuleRoots", "()Ljava/util/List;", "name", "", "getName", "()Ljava/lang/String;", PsiKeyword.EXPORTS, "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "exportsTo", "moduleName", "Automatic", "Explicit", "Root", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaModule {
    boolean exports(FqName packageFqName);

    boolean exportsTo(FqName packageFqName, String moduleName);

    VirtualFile getModuleInfoFile();

    List<Root> getModuleRoots();

    String getName();

    boolean isSourceModule();

    /* JADX INFO: compiled from: JavaModule.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Root;", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "isBinary", "", "isBinarySignature", "(Lcom/intellij/openapi/vfs/VirtualFile;ZZ)V", "getFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "()Z", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Root {
        private final VirtualFile file;
        private final boolean isBinary;
        private final boolean isBinarySignature;

        public static /* synthetic */ Root copy$default(Root root, VirtualFile virtualFile, boolean z, boolean z2, int i, Object obj) {
            if ((i & 1) != 0) {
                virtualFile = root.file;
            }
            if ((i & 2) != 0) {
                z = root.isBinary;
            }
            if ((i & 4) != 0) {
                z2 = root.isBinarySignature;
            }
            return root.copy(virtualFile, z, z2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final VirtualFile getFile() {
            return this.file;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getIsBinary() {
            return this.isBinary;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getIsBinarySignature() {
            return this.isBinarySignature;
        }

        public final Root copy(VirtualFile file, boolean isBinary, boolean isBinarySignature) {
            Intrinsics.checkNotNullParameter(file, "file");
            return new Root(file, isBinary, isBinarySignature);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Root)) {
                return false;
            }
            Root root = (Root) other;
            return Intrinsics.areEqual(this.file, root.file) && this.isBinary == root.isBinary && this.isBinarySignature == root.isBinarySignature;
        }

        public int hashCode() {
            return (((this.file.hashCode() * 31) + Boolean.hashCode(this.isBinary)) * 31) + Boolean.hashCode(this.isBinarySignature);
        }

        public String toString() {
            return "Root(file=" + this.file + ", isBinary=" + this.isBinary + ", isBinarySignature=" + this.isBinarySignature + ')';
        }

        public Root(VirtualFile file, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(file, "file");
            this.file = file;
            this.isBinary = z;
            this.isBinarySignature = z2;
        }

        public /* synthetic */ Root(VirtualFile virtualFile, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(virtualFile, z, (i & 4) != 0 ? false : z2);
        }

        public final VirtualFile getFile() {
            return this.file;
        }

        public final boolean isBinary() {
            return this.isBinary;
        }

        public final boolean isBinarySignature() {
            return this.isBinarySignature;
        }
    }

    /* JADX INFO: compiled from: JavaModule.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\b\u0010\u0018\u001a\u00020\u0003H\u0016R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Automatic;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;", "name", "", "moduleRoots", "", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Root;", "(Ljava/lang/String;Ljava/util/List;)V", "isSourceModule", "", "()Z", "moduleInfoFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getModuleInfoFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "getModuleRoots", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", PsiKeyword.EXPORTS, "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "exportsTo", "moduleName", "toString", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Automatic implements JavaModule {
        private final List<Root> moduleRoots;
        private final String name;

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public boolean exports(FqName packageFqName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            return true;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public boolean exportsTo(FqName packageFqName, String moduleName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            return true;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public VirtualFile getModuleInfoFile() {
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public boolean isSourceModule() {
            return false;
        }

        public Automatic(String name2, List<Root> moduleRoots) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(moduleRoots, "moduleRoots");
            this.name = name2;
            this.moduleRoots = moduleRoots;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public List<Root> getModuleRoots() {
            return this.moduleRoots;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public String getName() {
            return this.name;
        }

        public String toString() {
            return getName();
        }
    }

    /* JADX INFO: compiled from: JavaModule.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0018\u0010\u001b\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u0015H\u0016J\b\u0010\u001d\u001a\u00020\u0015H\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\fR\u0014\u0010\r\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Explicit;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;", "moduleInfo", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo;", "moduleRoots", "", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Root;", "moduleInfoFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "isJdkModuleFromCtSym", "", "(Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo;Ljava/util/List;Lcom/intellij/openapi/vfs/VirtualFile;Z)V", "()Z", "isSourceModule", "getModuleInfo", "()Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo;", "getModuleInfoFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "getModuleRoots", "()Ljava/util/List;", "name", "", "getName", "()Ljava/lang/String;", PsiKeyword.EXPORTS, "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "exportsTo", "moduleName", "toString", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Explicit implements JavaModule {
        private final boolean isJdkModuleFromCtSym;
        private final JavaModuleInfo moduleInfo;
        private final VirtualFile moduleInfoFile;
        private final List<Root> moduleRoots;

        public Explicit(JavaModuleInfo moduleInfo, List<Root> moduleRoots, VirtualFile moduleInfoFile, boolean z) {
            Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
            Intrinsics.checkNotNullParameter(moduleRoots, "moduleRoots");
            Intrinsics.checkNotNullParameter(moduleInfoFile, "moduleInfoFile");
            this.moduleInfo = moduleInfo;
            this.moduleRoots = moduleRoots;
            this.moduleInfoFile = moduleInfoFile;
            this.isJdkModuleFromCtSym = z;
        }

        public /* synthetic */ Explicit(JavaModuleInfo javaModuleInfo, List list, VirtualFile virtualFile, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(javaModuleInfo, list, virtualFile, (i & 8) != 0 ? false : z);
        }

        public final JavaModuleInfo getModuleInfo() {
            return this.moduleInfo;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public List<Root> getModuleRoots() {
            return this.moduleRoots;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public VirtualFile getModuleInfoFile() {
            return this.moduleInfoFile;
        }

        /* JADX INFO: renamed from: isJdkModuleFromCtSym, reason: from getter */
        public final boolean getIsJdkModuleFromCtSym() {
            return this.isJdkModuleFromCtSym;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public String getName() {
            return this.moduleInfo.getModuleName();
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public boolean isSourceModule() {
            return Intrinsics.areEqual(getModuleInfoFile().getExtension(), SuffixConstants.EXTENSION_java) || Intrinsics.areEqual(getModuleInfoFile().getFileType(), JavaFileType.INSTANCE);
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public boolean exports(FqName packageFqName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            List<JavaModuleInfo.Exports> exports = this.moduleInfo.getExports();
            if ((exports instanceof Collection) && exports.isEmpty()) {
                return false;
            }
            for (JavaModuleInfo.Exports exports2 : exports) {
                FqName packageFqName2 = exports2.getPackageFqName();
                List<String> listComponent2 = exports2.component2();
                if (Intrinsics.areEqual(packageFqName2, packageFqName) && listComponent2.isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModule
        public boolean exportsTo(FqName packageFqName, String moduleName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            List<JavaModuleInfo.Exports> exports = this.moduleInfo.getExports();
            if ((exports instanceof Collection) && exports.isEmpty()) {
                return false;
            }
            for (JavaModuleInfo.Exports exports2 : exports) {
                FqName packageFqName2 = exports2.getPackageFqName();
                List<String> listComponent2 = exports2.component2();
                if (Intrinsics.areEqual(packageFqName2, packageFqName) && (listComponent2.isEmpty() || listComponent2.contains(moduleName))) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return getName();
        }
    }
}
