package org.jetbrains.kotlin.cli.jvm.modules;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.DeprecatedVirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: CoreJrtFileSystem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u0005H\u0016J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u0005H\u0016R/\u0010\u0003\u001a#\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u0004¢\u0006\u0002\b\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/modules/CoreJrtFileSystem;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/DeprecatedVirtualFileSystem;", "()V", PsiTreeChangeEvent.PROP_ROOTS, "Ljava/util/concurrent/ConcurrentMap;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/cli/jvm/modules/CoreJrtVirtualFile;", "Lorg/jetbrains/annotations/NotNull;", "clearRoots", "", "findFileByPath", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "path", "getProtocol", "refresh", "asynchronous", "", "refreshAndFindFileByPath", "Companion", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoreJrtFileSystem extends DeprecatedVirtualFileSystem {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ConcurrentMap<String, FileSystem> globalJrtFsCache;
    private final ConcurrentMap<String, CoreJrtVirtualFile> roots;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public void refresh(boolean asynchronous) {
    }

    public CoreJrtFileSystem() {
        final Function1<String, CoreJrtVirtualFile> function1 = new Function1<String, CoreJrtVirtualFile>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CoreJrtFileSystem$roots$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CoreJrtVirtualFile invoke(String str) {
                FileSystem fileSystem = (FileSystem) CoreJrtFileSystem.globalJrtFsCache.get(str);
                if (fileSystem == null) {
                    return null;
                }
                CoreJrtFileSystem coreJrtFileSystem = this.this$0;
                Intrinsics.checkNotNull(str);
                Path path = fileSystem.getPath("", new String[0]);
                Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                return new CoreJrtVirtualFile(coreJrtFileSystem, str, path, null);
            }
        };
        ConcurrentMap<String, CoreJrtVirtualFile> concurrentMapCreateMap = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CoreJrtFileSystem$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return CoreJrtFileSystem.roots$lambda$0(function1, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(concurrentMapCreateMap, "createMap(...)");
        this.roots = concurrentMapCreateMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CoreJrtVirtualFile roots$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (CoreJrtVirtualFile) tmp0.invoke(obj);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public String getProtocol() {
        return StandardFileSystems.JRT_PROTOCOL;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile findFileByPath(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        Pair<String, String> pairSplitPath = INSTANCE.splitPath(path);
        String strComponent1 = pairSplitPath.component1();
        String strComponent2 = pairSplitPath.component2();
        CoreJrtVirtualFile coreJrtVirtualFile = this.roots.get(strComponent1);
        if (coreJrtVirtualFile == null) {
            return null;
        }
        return strComponent2.length() == 0 ? coreJrtVirtualFile : coreJrtVirtualFile.findFileByRelativePath(strComponent2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile refreshAndFindFileByPath(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return findFileByPath(path);
    }

    public final void clearRoots() {
        this.roots.clear();
    }

    /* JADX INFO: compiled from: CoreJrtFileSystem.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0012\u0010\r\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u000f2\u0006\u0010\u0010\u001a\u00020\u0005R/\u0010\u0003\u001a#\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u0004¢\u0006\u0002\b\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/modules/CoreJrtFileSystem$Companion;", "", "()V", "globalJrtFsCache", "Ljava/util/concurrent/ConcurrentMap;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Ljava/nio/file/FileSystem;", "Lorg/jetbrains/annotations/NotNull;", "isModularJdk", "", "jdkHome", "Ljava/io/File;", "loadJrtFsJar", "splitPath", "Lkotlin/Pair;", "path", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final File loadJrtFsJar(File jdkHome) {
            File file = new File(jdkHome, "lib/jrt-fs.jar");
            if (file.exists()) {
                return file;
            }
            return null;
        }

        public final boolean isModularJdk(File jdkHome) {
            Intrinsics.checkNotNullParameter(jdkHome, "jdkHome");
            return loadJrtFsJar(jdkHome) != null;
        }

        public final Pair<String, String> splitPath(String path) {
            Intrinsics.checkNotNullParameter(path, "path");
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) path, "!/", 0, false, 6, (Object) null);
            if (iIndexOf$default < 0) {
                throw new IllegalArgumentException("Path in CoreJrtFileSystem must contain a separator: " + path);
            }
            String strSubstring = path.substring(0, iIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            String strSubstring2 = path.substring(iIndexOf$default + 2);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
            return new Pair<>(strSubstring, strSubstring2);
        }
    }

    static {
        final CoreJrtFileSystem$Companion$globalJrtFsCache$1 coreJrtFileSystem$Companion$globalJrtFsCache$1 = new Function1<String, FileSystem>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CoreJrtFileSystem$Companion$globalJrtFsCache$1
            @Override // kotlin.jvm.functions.Function1
            public final FileSystem invoke(String str) {
                File file = new File(str);
                File fileLoadJrtFsJar = CoreJrtFileSystem.INSTANCE.loadJrtFsJar(file);
                if (fileLoadJrtFsJar == null) {
                    return null;
                }
                URI uriCreate = URI.create("jrt:/");
                if (JavaVersionUtilsKt.isAtLeastJava9()) {
                    return FileSystems.newFileSystem(uriCreate, (Map<String, ?>) MapsKt.mapOf(TuplesKt.to("java.home", file.getAbsolutePath())));
                }
                return FileSystems.newFileSystem(uriCreate, MapsKt.emptyMap(), new URLClassLoader(new URL[]{fileLoadJrtFsJar.toURI().toURL()}, null));
            }
        };
        ConcurrentMap<String, FileSystem> concurrentMapCreateMap = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CoreJrtFileSystem$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return CoreJrtFileSystem.globalJrtFsCache$lambda$1(coreJrtFileSystem$Companion$globalJrtFsCache$1, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(concurrentMapCreateMap, "createMap(...)");
        globalJrtFsCache = concurrentMapCreateMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FileSystem globalJrtFsCache$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (FileSystem) tmp0.invoke(obj);
    }
}
