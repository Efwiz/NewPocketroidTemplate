package org.eclipse.jgit.internal.storage.dfs;

import java.text.MessageFormat;
import java.util.function.Consumer;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes13.dex */
public class DfsBlockCacheConfig {
    public static final int KB = 1024;
    public static final int MB = 1048576;
    private long blockLimit;
    private int blockSize;
    private int concurrencyLevel;
    private Consumer<Long> refLock;
    private double streamRatio;

    public DfsBlockCacheConfig() {
        setBlockLimit(TagBits.HasUnresolvedSuperclass);
        setBlockSize(65536);
        setStreamRatio(0.3d);
        setConcurrencyLevel(32);
    }

    public long getBlockLimit() {
        return this.blockLimit;
    }

    public DfsBlockCacheConfig setBlockLimit(long j) {
        if (j <= 0) {
            throw new IllegalArgumentException(MessageFormat.format(JGitText.get().blockLimitNotPositive, Long.valueOf(j)));
        }
        this.blockLimit = j;
        return this;
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public DfsBlockCacheConfig setBlockSize(int i) {
        int iMax = Math.max(512, i);
        if (((iMax - 1) & iMax) != 0) {
            throw new IllegalArgumentException(JGitText.get().blockSizeNotPowerOf2);
        }
        this.blockSize = iMax;
        return this;
    }

    public int getConcurrencyLevel() {
        return this.concurrencyLevel;
    }

    public DfsBlockCacheConfig setConcurrencyLevel(int i) {
        this.concurrencyLevel = i;
        return this;
    }

    public double getStreamRatio() {
        return this.streamRatio;
    }

    public DfsBlockCacheConfig setStreamRatio(double d) {
        this.streamRatio = Math.max(0.0d, Math.min(d, 1.0d));
        return this;
    }

    public Consumer<Long> getRefLockWaitTimeConsumer() {
        return this.refLock;
    }

    public DfsBlockCacheConfig setRefLockWaitTimeConsumer(Consumer<Long> consumer) {
        this.refLock = consumer;
        return this;
    }

    public DfsBlockCacheConfig fromConfig(Config config) {
        long j = config.getLong(ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_DFS_SECTION, ConfigConstants.CONFIG_KEY_BLOCK_LIMIT, getBlockLimit());
        int i = config.getInt(ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_DFS_SECTION, ConfigConstants.CONFIG_KEY_BLOCK_SIZE, getBlockSize());
        long j2 = i;
        if (j % j2 != 0) {
            throw new IllegalArgumentException(MessageFormat.format(JGitText.get().blockLimitNotMultipleOfBlockSize, Long.valueOf(j), Long.valueOf(j2)));
        }
        setBlockLimit(j);
        setBlockSize(i);
        setConcurrencyLevel(config.getInt(ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_DFS_SECTION, ConfigConstants.CONFIG_KEY_CONCURRENCY_LEVEL, getConcurrencyLevel()));
        String string = config.getString(ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_DFS_SECTION, ConfigConstants.CONFIG_KEY_STREAM_RATIO);
        if (string != null) {
            try {
                setStreamRatio(Double.parseDouble(string));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(MessageFormat.format(JGitText.get().enumValueNotSupported3, ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_DFS_SECTION, ConfigConstants.CONFIG_KEY_STREAM_RATIO, string), e);
            }
        }
        return this;
    }
}
