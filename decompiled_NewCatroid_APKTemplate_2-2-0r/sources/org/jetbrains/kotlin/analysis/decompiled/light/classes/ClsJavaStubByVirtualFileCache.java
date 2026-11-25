package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl;
import org.jetbrains.kotlin.com.intellij.util.cls.ClsFormatException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: ClsJavaStubByVirtualFileCache.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00102\u00020\u0001:\u0002\u000f\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0005H\u0002J\u0010\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u0005R/\u0010\u0003\u001a#\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u0004¢\u0006\u0002\b\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/ClsJavaStubByVirtualFileCache;", "", "()V", ResponseCacheMiddleware.CACHE, "Ljava/util/concurrent/ConcurrentMap;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/ClsJavaStubByVirtualFileCache$CachedJavaStub;", "Lorg/jetbrains/annotations/NotNull;", "createStub", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/PsiJavaFileStub;", "file", "get", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiJavaFileStubImpl;", "classFile", "CachedJavaStub", "Companion", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClsJavaStubByVirtualFileCache {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Logger LOG;
    private final ConcurrentMap<VirtualFile, CachedJavaStub> cache;

    /* JADX INFO: compiled from: ClsJavaStubByVirtualFileCache.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/ClsJavaStubByVirtualFileCache$CachedJavaStub;", "", "modificationStamp", "", "javaFileStub", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiJavaFileStubImpl;", "(JLcom/intellij/psi/impl/java/stubs/impl/PsiJavaFileStubImpl;)V", "getJavaFileStub", "()Lcom/intellij/psi/impl/java/stubs/impl/PsiJavaFileStubImpl;", "getModificationStamp", "()J", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CachedJavaStub {
        private final PsiJavaFileStubImpl javaFileStub;
        private final long modificationStamp;

        public CachedJavaStub(long j, PsiJavaFileStubImpl javaFileStub) {
            Intrinsics.checkNotNullParameter(javaFileStub, "javaFileStub");
            this.modificationStamp = j;
            this.javaFileStub = javaFileStub;
        }

        public final PsiJavaFileStubImpl getJavaFileStub() {
            return this.javaFileStub;
        }

        public final long getModificationStamp() {
            return this.modificationStamp;
        }
    }

    public ClsJavaStubByVirtualFileCache() {
        ConcurrentMap<VirtualFile, CachedJavaStub> concurrentMapCreateConcurrentWeakKeySoftValueMap = ContainerUtil.createConcurrentWeakKeySoftValueMap();
        Intrinsics.checkNotNullExpressionValue(concurrentMapCreateConcurrentWeakKeySoftValueMap, "createConcurrentWeakKeySoftValueMap(...)");
        this.cache = concurrentMapCreateConcurrentWeakKeySoftValueMap;
    }

    public final PsiJavaFileStubImpl get(VirtualFile classFile) {
        Intrinsics.checkNotNullParameter(classFile, "classFile");
        CachedJavaStub cachedJavaStub = this.cache.get(classFile);
        long modificationStamp = classFile.getModificationStamp();
        if (cachedJavaStub != null && cachedJavaStub.getModificationStamp() == modificationStamp) {
            return cachedJavaStub.getJavaFileStub();
        }
        PsiJavaFileStubImpl psiJavaFileStubImpl = (PsiJavaFileStubImpl) createStub(classFile);
        if (psiJavaFileStubImpl == null) {
            return null;
        }
        this.cache.put(classFile, new CachedJavaStub(modificationStamp, psiJavaFileStubImpl));
        return psiJavaFileStubImpl;
    }

    private final PsiJavaFileStub createStub(VirtualFile file) {
        if (!Intrinsics.areEqual(file.getExtension(), JavaClassFileType.INSTANCE.getDefaultExtension()) && file.getFileType() != JavaClassFileType.INSTANCE) {
            return null;
        }
        try {
            return ClsFileImpl.buildFileStub(file, file.contentsToByteArray(false));
        } catch (IOException e) {
            Logger logger = LOG;
            StringBuilder sb = new StringBuilder("Failed to build java cls class for ");
            String canonicalPath = file.getCanonicalPath();
            Intrinsics.checkNotNull(canonicalPath);
            logger.warn(sb.append(canonicalPath).toString(), e);
            return null;
        } catch (ClsFormatException e2) {
            Logger logger2 = LOG;
            StringBuilder sb2 = new StringBuilder("Failed to build java cls class for ");
            String canonicalPath2 = file.getCanonicalPath();
            Intrinsics.checkNotNull(canonicalPath2);
            logger2.warn(sb2.append(canonicalPath2).toString(), e2);
            return null;
        }
    }

    /* JADX INFO: compiled from: ClsJavaStubByVirtualFileCache.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/ClsJavaStubByVirtualFileCache$Companion;", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "getInstance", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/ClsJavaStubByVirtualFileCache;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ClsJavaStubByVirtualFileCache getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(ClsJavaStubByVirtualFileCache.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (ClsJavaStubByVirtualFileCache) service;
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) ClsJavaStubByVirtualFileCache.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
