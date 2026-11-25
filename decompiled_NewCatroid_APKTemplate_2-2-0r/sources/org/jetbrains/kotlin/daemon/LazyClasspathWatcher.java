package org.jetbrains.kotlin.daemon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.bouncycastle.cms.CMSAttributeTableGenerator;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: LazyClasspathWatcher.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001 B'\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\u0019\u001a\n \u001b*\u0004\u0018\u00010\u001a0\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001d¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/daemon/LazyClasspathWatcher;", "", ModuleXmlParser.CLASSPATH, "", "", "checkPeriod", "", "digestCheckPeriod", "(Ljava/lang/Iterable;JJ)V", "getCheckPeriod", "()J", "getDigestCheckPeriod", "fileIds", "", "Lorg/jetbrains/kotlin/daemon/LazyClasspathWatcher$FileId;", "fileIdsLock", "Ljava/util/concurrent/Semaphore;", "isChanged", "", "()Z", "lastChangedStatus", "Ljava/util/concurrent/atomic/AtomicBoolean;", "lastDigestUpdate", "Ljava/util/concurrent/atomic/AtomicLong;", "lastUpdate", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Ljava/util/logging/Logger;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getLog", "()Ljava/util/logging/Logger;", "log$delegate", "Lkotlin/Lazy;", "FileId", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyClasspathWatcher {
    private final long checkPeriod;
    private final long digestCheckPeriod;
    private List<FileId> fileIds;
    private final Semaphore fileIdsLock;
    private final AtomicBoolean lastChangedStatus;
    private final AtomicLong lastDigestUpdate;
    private final AtomicLong lastUpdate;

    /* JADX INFO: renamed from: log$delegate, reason: from kotlin metadata */
    private final Lazy log;

    public LazyClasspathWatcher(final Iterable<String> classpath, long j, long j2) throws InterruptedException {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        this.checkPeriod = j;
        this.digestCheckPeriod = j2;
        Semaphore semaphore = new Semaphore(1);
        this.fileIdsLock = semaphore;
        this.lastChangedStatus = new AtomicBoolean(false);
        this.lastUpdate = new AtomicLong(0L);
        this.lastDigestUpdate = new AtomicLong(0L);
        this.log = LazyKt.lazy(new Function0<Logger>() { // from class: org.jetbrains.kotlin.daemon.LazyClasspathWatcher$log$2
            @Override // kotlin.jvm.functions.Function0
            public final Logger invoke() {
                return Logger.getLogger("classpath watcher");
            }
        });
        semaphore.acquire();
        ThreadsKt.thread$default(true, true, null, null, 0, new Function0<Unit>() { // from class: org.jetbrains.kotlin.daemon.LazyClasspathWatcher.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                try {
                    try {
                        LazyClasspathWatcher lazyClasspathWatcher = LazyClasspathWatcher.this;
                        Iterable<String> iterable = classpath;
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                        Iterator<String> it = iterable.iterator();
                        while (it.hasNext()) {
                            arrayList.add(new File(it.next()));
                        }
                        lazyClasspathWatcher.fileIds = SequencesKt.toList(SequencesKt.map(SequencesKt.flatMap(CollectionsKt.asSequence(arrayList), new Function1<File, Sequence<? extends File>>() { // from class: org.jetbrains.kotlin.daemon.LazyClasspathWatcher.1.2

                            /* JADX INFO: renamed from: org.jetbrains.kotlin.daemon.LazyClasspathWatcher$1$2$1, reason: invalid class name and collision with other inner class name */
                            /* JADX INFO: compiled from: LazyClasspathWatcher.kt */
                            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                            /* synthetic */ class C03011 extends FunctionReference implements Function1<File, Boolean> {
                                public static final C03011 INSTANCE = new C03011();

                                C03011() {
                                    super(1);
                                }

                                @Override // kotlin.jvm.internal.CallableReference
                                public final String getSignature() {
                                    return "isClasspathFile(Ljava/io/File;)Z";
                                }

                                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                                public final String getName() {
                                    return "isClasspathFile";
                                }

                                @Override // kotlin.jvm.internal.CallableReference
                                public final KDeclarationContainer getOwner() {
                                    return Reflection.getOrCreateKotlinPackage(LazyClasspathWatcherKt.class, "kotlin-daemon");
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Boolean invoke(File p0) {
                                    Intrinsics.checkNotNullParameter(p0, "p0");
                                    return Boolean.valueOf(LazyClasspathWatcherKt.isClasspathFile(p0));
                                }
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Sequence<File> invoke(File it2) {
                                Intrinsics.checkNotNullParameter(it2, "it");
                                return SequencesKt.filter(FilesKt.walk$default(it2, null, 1, null), C03011.INSTANCE);
                            }
                        }), new Function1<File, FileId>() { // from class: org.jetbrains.kotlin.daemon.LazyClasspathWatcher.1.3
                            @Override // kotlin.jvm.functions.Function1
                            public final FileId invoke(File it2) {
                                Intrinsics.checkNotNullParameter(it2, "it");
                                return new FileId(it2, it2.lastModified(), LazyClasspathWatcherKt.md5Digest(it2));
                            }
                        }));
                        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
                        LazyClasspathWatcher.this.lastUpdate.set(millis);
                        LazyClasspathWatcher.this.lastDigestUpdate.set(millis);
                    } catch (IOException e) {
                        LazyClasspathWatcher.this.getLog().log(Level.WARNING, "Error on walking classpath", (Throwable) e);
                    }
                } finally {
                    LazyClasspathWatcher.this.fileIdsLock.release();
                }
            }
        }, 28, null);
    }

    public /* synthetic */ LazyClasspathWatcher(Iterable iterable, long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iterable, (i & 2) != 0 ? LazyClasspathWatcherKt.getDEFAULT_CLASSPATH_WATCH_PERIOD_MS() : j, (i & 4) != 0 ? LazyClasspathWatcherKt.getDEFAULT_CLASSPATH_DIGEST_WATCH_PERIOD_MS() : j2);
    }

    public final long getCheckPeriod() {
        return this.checkPeriod;
    }

    public final long getDigestCheckPeriod() {
        return this.digestCheckPeriod;
    }

    /* JADX INFO: compiled from: LazyClasspathWatcher.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/daemon/LazyClasspathWatcher$FileId;", "", "file", "Ljava/io/File;", "lastModified", "", CMSAttributeTableGenerator.DIGEST, "", "(Ljava/io/File;J[B)V", "getDigest", "()[B", "getFile", "()Ljava/io/File;", "getLastModified", "()J", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class FileId {
        private final byte[] digest;
        private final File file;
        private final long lastModified;

        public static /* synthetic */ FileId copy$default(FileId fileId, File file, long j, byte[] bArr, int i, Object obj) {
            if ((i & 1) != 0) {
                file = fileId.file;
            }
            if ((i & 2) != 0) {
                j = fileId.lastModified;
            }
            if ((i & 4) != 0) {
                bArr = fileId.digest;
            }
            return fileId.copy(file, j, bArr);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final File getFile() {
            return this.file;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final long getLastModified() {
            return this.lastModified;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final byte[] getDigest() {
            return this.digest;
        }

        public final FileId copy(File file, long lastModified, byte[] digest) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(digest, "digest");
            return new FileId(file, lastModified, digest);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FileId)) {
                return false;
            }
            FileId fileId = (FileId) other;
            return Intrinsics.areEqual(this.file, fileId.file) && this.lastModified == fileId.lastModified && Intrinsics.areEqual(this.digest, fileId.digest);
        }

        public int hashCode() {
            return (((this.file.hashCode() * 31) + Long.hashCode(this.lastModified)) * 31) + Arrays.hashCode(this.digest);
        }

        public String toString() {
            return "FileId(file=" + this.file + ", lastModified=" + this.lastModified + ", digest=" + Arrays.toString(this.digest) + ')';
        }

        public FileId(File file, long j, byte[] digest) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(digest, "digest");
            this.file = file;
            this.lastModified = j;
            this.digest = digest;
        }

        public final byte[] getDigest() {
            return this.digest;
        }

        public final File getFile() {
            return this.file;
        }

        public final long getLastModified() {
            return this.lastModified;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Logger getLog() {
        return (Logger) this.log.getValue();
    }

    public final boolean isChanged() throws InterruptedException {
        if (this.lastChangedStatus.get()) {
            return true;
        }
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
        if (millis - this.lastUpdate.get() < this.checkPeriod) {
            return false;
        }
        boolean z = millis - this.lastDigestUpdate.get() > this.digestCheckPeriod;
        this.fileIdsLock.acquire();
        this.fileIdsLock.release();
        List<FileId> list = this.fileIds;
        Object obj = null;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object next = it.next();
                    FileId fileId = (FileId) next;
                    try {
                        if (!fileId.getFile().exists()) {
                            getLog().info("cp changed: " + fileId.getFile() + " doesn't exist any more");
                        } else if (fileId.getFile().lastModified() != fileId.getLastModified() || z) {
                            if (!Arrays.equals(fileId.getDigest(), LazyClasspathWatcherKt.md5Digest(fileId.getFile()))) {
                                getLog().info("cp changed: " + fileId.getFile() + " digests differ");
                            }
                        }
                    } catch (IOException e) {
                        getLog().log(Level.INFO, "cp changed: " + fileId.getFile() + " access throws the exception", (Throwable) e);
                    }
                    obj = next;
                }
                obj = (FileId) obj;
            }
        }
        boolean z2 = obj != null;
        this.lastUpdate.set(TimeUnit.NANOSECONDS.toMillis(System.nanoTime()));
        if (z) {
            this.lastDigestUpdate.set(this.lastUpdate.get());
        }
        return z2;
    }
}
