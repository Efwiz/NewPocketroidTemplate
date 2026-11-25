package org.eclipse.jgit.internal.storage.dfs;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes13.dex */
public class DfsReaderOptions {
    public static final int KiB = 1024;
    public static final int MiB = 1048576;
    private int deltaBaseCacheLimit;
    private int streamFileThreshold;
    private int streamPackBufferSize;

    public DfsReaderOptions() {
        setDeltaBaseCacheLimit(10485760);
        setStreamFileThreshold(52428800);
    }

    public int getDeltaBaseCacheLimit() {
        return this.deltaBaseCacheLimit;
    }

    public DfsReaderOptions setDeltaBaseCacheLimit(int i) {
        this.deltaBaseCacheLimit = Math.max(0, i);
        return this;
    }

    public int getStreamFileThreshold() {
        return this.streamFileThreshold;
    }

    public DfsReaderOptions setStreamFileThreshold(int i) {
        this.streamFileThreshold = Math.max(0, i);
        return this;
    }

    public int getStreamPackBufferSize() {
        return this.streamPackBufferSize;
    }

    public DfsReaderOptions setStreamPackBufferSize(int i) {
        this.streamPackBufferSize = Math.max(0, i);
        return this;
    }

    public DfsReaderOptions fromConfig(Config config) {
        setDeltaBaseCacheLimit(config.getInt(ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_DFS_SECTION, ConfigConstants.CONFIG_KEY_DELTA_BASE_CACHE_LIMIT, getDeltaBaseCacheLimit()));
        setStreamFileThreshold((int) Math.min(Math.min(config.getLong(ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_DFS_SECTION, ConfigConstants.CONFIG_KEY_STREAM_FILE_TRESHOLD, getStreamFileThreshold()), Runtime.getRuntime().maxMemory() / 4), 2147483647L));
        setStreamPackBufferSize(config.getInt(ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_DFS_SECTION, ConfigConstants.CONFIG_KEY_STREAM_BUFFER, getStreamPackBufferSize()));
        return this;
    }
}
