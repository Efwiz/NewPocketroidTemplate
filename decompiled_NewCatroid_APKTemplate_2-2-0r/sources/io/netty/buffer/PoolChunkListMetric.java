package io.netty.buffer;

/* JADX INFO: loaded from: classes11.dex */
public interface PoolChunkListMetric extends Iterable<PoolChunkMetric> {
    int maxUsage();

    int minUsage();
}
