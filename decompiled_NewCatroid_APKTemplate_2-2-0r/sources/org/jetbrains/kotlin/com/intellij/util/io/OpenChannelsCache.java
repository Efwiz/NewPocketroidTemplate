package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;

/* JADX INFO: loaded from: classes3.dex */
final class OpenChannelsCache {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Map<Path, ChannelDescriptor> myCache;
    private final int myCacheSizeLimit;
    private final Object myLock = new Object();

    @FunctionalInterface
    interface ChannelProcessor<T> {
        T process(FileChannel fileChannel) throws IOException;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "path";
        } else {
            objArr[0] = "processor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/OpenChannelsCache";
        objArr[2] = "useChannel";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    OpenChannelsCache(int i) {
        this.myCacheSizeLimit = i;
        this.myCache = new LinkedHashMap(i, 0.5f, true);
    }

    <T> T useChannel(Path path, ChannelProcessor<T> channelProcessor, boolean z) throws IOException {
        ChannelDescriptor channelDescriptor;
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        if (channelProcessor == null) {
            $$$reportNull$$$0(1);
        }
        synchronized (this.myLock) {
            channelDescriptor = this.myCache.get(path);
            if (channelDescriptor == null) {
                releaseOverCachedChannels();
                channelDescriptor = new ChannelDescriptor(path, z);
                this.myCache.put(path, channelDescriptor);
            }
            if (!z && channelDescriptor.isReadOnly()) {
                if (channelDescriptor.isLocked()) {
                    channelDescriptor = new ChannelDescriptor(path, false);
                } else {
                    closeChannel(path);
                    channelDescriptor = new ChannelDescriptor(path, false);
                    this.myCache.put(path, channelDescriptor);
                }
            }
            channelDescriptor.lock();
        }
        try {
            T tProcess = channelProcessor.process(channelDescriptor.getChannel());
            synchronized (this.myLock) {
                channelDescriptor.unlock();
            }
            return tProcess;
        } catch (Throwable th) {
            synchronized (this.myLock) {
                channelDescriptor.unlock();
                throw th;
            }
        }
    }

    void closeChannel(Path path) throws IOException {
        synchronized (this.myLock) {
            ChannelDescriptor channelDescriptorRemove = this.myCache.remove(path);
            if (channelDescriptorRemove != null) {
                channelDescriptorRemove.close();
            }
        }
    }

    private void releaseOverCachedChannels() throws IOException {
        int size = this.myCache.size() - this.myCacheSizeLimit;
        if (size >= 0) {
            ArrayList arrayList = new ArrayList();
            for (Map.Entry<Path, ChannelDescriptor> entry : this.myCache.entrySet()) {
                if (size < 0) {
                    break;
                } else if (!entry.getValue().isLocked()) {
                    size--;
                    arrayList.add(entry.getKey());
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                closeChannel((Path) it.next());
            }
        }
    }

    static final class ChannelDescriptor implements Closeable {
        private static final OpenOption[] MODIFIABLE_OPTS = {StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE};
        private static final OpenOption[] READ_ONLY_OPTS = {StandardOpenOption.READ};
        private final UnInterruptibleFileChannel myChannel;
        private int myLockCount;
        private final boolean myReadOnly;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "file";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/OpenChannelsCache$ChannelDescriptor";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/OpenChannelsCache$ChannelDescriptor";
            } else {
                objArr[1] = "getChannel";
            }
            if (i != 1) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        ChannelDescriptor(final Path path, final boolean z) throws IOException {
            if (path == null) {
                $$$reportNull$$$0(0);
            }
            this.myLockCount = 0;
            this.myReadOnly = z;
            this.myChannel = (UnInterruptibleFileChannel) Objects.requireNonNull((UnInterruptibleFileChannel) FileUtilRt.doIOOperation(new FileUtilRt.RepeatableIOOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.OpenChannelsCache$ChannelDescriptor$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.RepeatableIOOperation
                public final Object execute(boolean z2) {
                    return OpenChannelsCache.ChannelDescriptor.lambda$new$0(path, z, z2);
                }
            }));
        }

        static /* synthetic */ UnInterruptibleFileChannel lambda$new$0(Path path, boolean z, boolean z2) throws IOException {
            try {
                return new UnInterruptibleFileChannel(path, z ? READ_ONLY_OPTS : MODIFIABLE_OPTS);
            } catch (NoSuchFileException e) {
                Path parent = path.getParent();
                if (!z) {
                    if (!Files.exists(parent, new LinkOption[0])) {
                        Files.createDirectories(parent, new FileAttribute[0]);
                    }
                    if (!z2) {
                        return null;
                    }
                }
                throw e;
            }
        }

        boolean isReadOnly() {
            return this.myReadOnly;
        }

        void lock() {
            this.myLockCount++;
        }

        void unlock() {
            this.myLockCount--;
        }

        boolean isLocked() {
            return this.myLockCount != 0;
        }

        UnInterruptibleFileChannel getChannel() {
            UnInterruptibleFileChannel unInterruptibleFileChannel = this.myChannel;
            if (unInterruptibleFileChannel == null) {
                $$$reportNull$$$0(1);
            }
            return unInterruptibleFileChannel;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.myChannel.close();
        }
    }
}
