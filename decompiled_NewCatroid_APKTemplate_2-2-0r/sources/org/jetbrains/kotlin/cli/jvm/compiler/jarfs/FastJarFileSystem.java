package org.jetbrains.kotlin.cli.jvm.compiler.jarfs;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Couple;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.DeprecatedVirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache;

/* JADX INFO: compiled from: FastJarFileSystem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000  2\u00020\u0001:\u0001 B \b\u0002\u0012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\u0016\u001a\u00020\u0005J\u0006\u0010\u0017\u001a\u00020\u0005J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0012H\u0016J\b\u0010\u001b\u001a\u00020\u0012H\u0016J\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0012\u0010\u001f\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0012H\u0016R0\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00040\u000bj\u0002`\r0\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R%\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarFileSystem;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/DeprecatedVirtualFileSystem;", "unmapBuffer", "Lkotlin/Function1;", "Ljava/nio/MappedByteBuffer;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;)V", "cachedOpenFileHandles", "Lorg/jetbrains/kotlin/com/intellij/util/io/FileAccessorCache;", "Ljava/io/File;", "Lkotlin/Pair;", "Ljava/io/RandomAccessFile;", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/RandomAccessFileAndBuffer;", "getCachedOpenFileHandles$cli_base", "()Lcom/intellij/util/io/FileAccessorCache;", "myHandlers", "", "", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarHandler;", "getUnmapBuffer$cli_base", "()Lkotlin/jvm/functions/Function1;", "cleanOpenFilesCache", "clearHandlersCache", "findFileByPath", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "path", "getProtocol", "refresh", "asynchronous", "", "refreshAndFindFileByPath", "Companion", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FastJarFileSystem extends DeprecatedVirtualFileSystem {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FileAccessorCache<File, Pair<RandomAccessFile, MappedByteBuffer>> cachedOpenFileHandles;
    private final Map<String, FastJarHandler> myHandlers;
    private final Function1<MappedByteBuffer, Unit> unmapBuffer;

    public /* synthetic */ FastJarFileSystem(Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public void refresh(boolean asynchronous) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    private FastJarFileSystem(Function1<? super MappedByteBuffer, Unit> function1) {
        this.unmapBuffer = function1;
        final Function1<String, FastJarHandler> function2 = new Function1<String, FastJarHandler>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.jarfs.FastJarFileSystem$myHandlers$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final FastJarHandler invoke(String key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return new FastJarHandler(this.this$0, key);
            }
        };
        ConcurrentMap concurrentMapCreateMap = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.jarfs.FastJarFileSystem$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return FastJarFileSystem.myHandlers$lambda$0(function2, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(concurrentMapCreateMap, "createMap(...)");
        this.myHandlers = concurrentMapCreateMap;
        this.cachedOpenFileHandles = (FileAccessorCache) new FileAccessorCache<File, Pair<? extends RandomAccessFile, ? extends MappedByteBuffer>>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.jarfs.FastJarFileSystem$cachedOpenFileHandles$1
            {
                super(20, 10);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
            public Pair<RandomAccessFile, MappedByteBuffer> createAccessor(File file) throws IOException {
                Intrinsics.checkNotNullParameter(file, "file");
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                return new Pair<>(randomAccessFile, randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, randomAccessFile.length()));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
            public void disposeAccessor(Pair<? extends RandomAccessFile, ? extends MappedByteBuffer> fileAccessor) throws IOException {
                Intrinsics.checkNotNullParameter(fileAccessor, "fileAccessor");
                fileAccessor.getFirst().close();
                this.this$0.getUnmapBuffer$cli_base().invoke(fileAccessor.getSecond());
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache, org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
            public boolean isEqual(File val1, File val2) {
                Intrinsics.checkNotNullParameter(val1, "val1");
                Intrinsics.checkNotNullParameter(val2, "val2");
                return Intrinsics.areEqual(val1, val2);
            }
        };
    }

    public final Function1<MappedByteBuffer, Unit> getUnmapBuffer$cli_base() {
        return this.unmapBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FastJarHandler myHandlers$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (FastJarHandler) tmp0.invoke(obj);
    }

    public final FileAccessorCache<File, Pair<RandomAccessFile, MappedByteBuffer>> getCachedOpenFileHandles$cli_base() {
        return this.cachedOpenFileHandles;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public String getProtocol() {
        return "jar";
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile findFileByPath(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        Couple<String> coupleSplitPath = INSTANCE.splitPath(path);
        FastJarHandler fastJarHandler = this.myHandlers.get(coupleSplitPath.first);
        Intrinsics.checkNotNull(fastJarHandler);
        B second = coupleSplitPath.second;
        Intrinsics.checkNotNullExpressionValue(second, "second");
        return fastJarHandler.findFileByPath((String) second);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile refreshAndFindFileByPath(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return findFileByPath(path);
    }

    public final void clearHandlersCache() {
        this.myHandlers.clear();
        cleanOpenFilesCache();
    }

    public final void cleanOpenFilesCache() {
        this.cachedOpenFileHandles.clear();
    }

    /* JADX INFO: compiled from: FastJarFileSystem.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarFileSystem$Companion;", "", "()V", "createIfUnmappingPossible", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarFileSystem;", "splitPath", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Couple;", "", "path", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Couple<String> splitPath(String path) {
            Intrinsics.checkNotNullParameter(path, "path");
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) path, "!/", 0, false, 6, (Object) null);
            if (iIndexOf$default < 0) {
                throw new IllegalArgumentException(("Path in JarFileSystem must contain a separator: " + path).toString());
            }
            String strSubstring = path.substring(0, iIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            String strSubstring2 = path.substring(iIndexOf$default + 2);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
            Couple<String> coupleOf = Couple.of(strSubstring, strSubstring2);
            Intrinsics.checkNotNullExpressionValue(coupleOf, "of(...)");
            return coupleOf;
        }

        public final FastJarFileSystem createIfUnmappingPossible() {
            Function1 function1PrepareCleanerCallback = FastJarFileSystemKt.prepareCleanerCallback();
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (function1PrepareCleanerCallback == null) {
                return null;
            }
            return new FastJarFileSystem(function1PrepareCleanerCallback, defaultConstructorMarker);
        }
    }
}
