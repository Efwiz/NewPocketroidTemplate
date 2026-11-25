package org.jetbrains.kotlin.com.intellij.util.io;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap;

/* JADX INFO: loaded from: classes3.dex */
public class PagePool {
    public static final PagePool SHARED = new PagePool(SystemProperties.getIntProperty("idea.io.protected.pool.size", 256), SystemProperties.getIntProperty("idea.io.probatonal.pool.size", 256));
    private static int cache_misses;
    private static int finalization_queue_hits;
    private static int hits;
    private static int probational_queue_hits;
    private static int protected_queue_hits;
    private static int same_page_hits;
    private final Map<PoolPageKey, Page> myProbationalQueue;
    private final Map<PoolPageKey, Page> myProtectedQueue;
    private int finalizationId = 0;
    private final SortedMap<PoolPageKey, FinalizationRequest> myFinalizationQueue = new TreeMap();
    private final Object lock = new Object();
    private PoolPageKey lastFinalizedKey = null;
    private RandomAccessDataFile lastOwner = null;
    private long lastOffset = 0;
    private Page lastHit = null;

    public PagePool(final int i, final int i2) {
        float f = 1.0f;
        boolean z = true;
        this.myProbationalQueue = new LinkedHashMap<PoolPageKey, Page>(i2 * 2, f, z) { // from class: org.jetbrains.kotlin.com.intellij.util.io.PagePool.1
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap
            protected boolean removeEldestEntry(Map.Entry<PoolPageKey, Page> entry) {
                if (size() <= i2) {
                    return false;
                }
                PagePool.this.scheduleFinalization(entry.getValue());
                return true;
            }
        };
        this.myProtectedQueue = new LinkedHashMap<PoolPageKey, Page>(i, f, z) { // from class: org.jetbrains.kotlin.com.intellij.util.io.PagePool.2
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap
            protected boolean removeEldestEntry(Map.Entry<PoolPageKey, Page> entry) {
                if (size() <= i) {
                    return false;
                }
                PagePool.this.myProbationalQueue.put(entry.getKey(), entry.getValue());
                return true;
            }
        };
    }

    public void flushPages(RandomAccessDataFile randomAccessDataFile) {
        boolean zScanQueue;
        synchronized (this.lock) {
            if (this.lastOwner == randomAccessDataFile) {
                scheduleFinalization(this.lastHit);
                this.lastHit = null;
                this.lastOwner = null;
            }
            zScanQueue = scanQueue(randomAccessDataFile, this.myProbationalQueue) | scanQueue(randomAccessDataFile, this.myProtectedQueue);
        }
        if (zScanQueue) {
            flushFinalizationQueue();
        }
    }

    private void flushFinalizationQueue() {
        while (true) {
            FinalizationRequest finalizationRequestRetrieveFinalizationRequest = retrieveFinalizationRequest();
            if (finalizationRequestRetrieveFinalizationRequest == null) {
                return;
            } else {
                processFinalizationRequest(finalizationRequestRetrieveFinalizationRequest);
            }
        }
    }

    private boolean scanQueue(RandomAccessDataFile randomAccessDataFile, Map<?, Page> map) {
        Iterator<Page> it = map.values().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Page next = it.next();
            if (next.getOwner() == randomAccessDataFile) {
                scheduleFinalization(next);
                it.remove();
                z = true;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleFinalization(Page page) {
        int i;
        synchronized (this.lock) {
            i = this.finalizationId + 1;
            this.finalizationId = i;
        }
        FinalizationRequest finalizationRequestPrepareForFinalization = page.prepareForFinalization(i);
        if (finalizationRequestPrepareForFinalization == null) {
            return;
        }
        synchronized (this.lock) {
            this.myFinalizationQueue.put(page.getKey(), finalizationRequestPrepareForFinalization);
        }
    }

    private void processFinalizationRequest(FinalizationRequest finalizationRequest) {
        Page page = finalizationRequest.page;
        try {
            page.flushIfFinalizationIdIsEqualTo(finalizationRequest.finalizationId);
            synchronized (this.lock) {
                this.myFinalizationQueue.remove(page.getKey());
            }
        } finally {
            synchronized (this.lock) {
                this.myFinalizationQueue.remove(page.getKey());
                page.recycleIfFinalizationIdIsEqualTo(finalizationRequest.finalizationId);
            }
        }
    }

    private FinalizationRequest retrieveFinalizationRequest() {
        FinalizationRequest finalizationRequest;
        PoolPageKey poolPageKeyFirstKey;
        synchronized (this.lock) {
            if (!this.myFinalizationQueue.isEmpty()) {
                PoolPageKey poolPageKey = this.lastFinalizedKey;
                if (poolPageKey == null) {
                    poolPageKeyFirstKey = this.myFinalizationQueue.firstKey();
                } else {
                    SortedMap<PoolPageKey, FinalizationRequest> sortedMapTailMap = this.myFinalizationQueue.tailMap(new PoolPageKey(poolPageKey.getOwner(), poolPageKey.getOwner().physicalLength()));
                    if (sortedMapTailMap.isEmpty()) {
                        sortedMapTailMap = this.myFinalizationQueue.tailMap(poolPageKey);
                    }
                    poolPageKeyFirstKey = sortedMapTailMap.isEmpty() ? this.myFinalizationQueue.firstKey() : sortedMapTailMap.firstKey();
                }
                this.lastFinalizedKey = poolPageKeyFirstKey;
                finalizationRequest = this.myFinalizationQueue.get(poolPageKeyFirstKey);
            } else {
                finalizationRequest = null;
                this.lastFinalizedKey = null;
            }
        }
        return finalizationRequest;
    }
}
