package org.jetbrains.kotlin.com.intellij.util.io;

import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ShareableKey;

/* JADX INFO: loaded from: classes3.dex */
public final class PersistentEnumeratorCache {
    private static final int ENUMERATION_CACHE_SIZE;
    private static final SLRUMap<Object, Integer> ourEnumerationCache;
    private static final CacheKey ourFlyweight;

    static {
        int intProperty = SystemProperties.getIntProperty("idea.enumerationCacheSize", 8192);
        ENUMERATION_CACHE_SIZE = intProperty;
        ourFlyweight = new FlyweightKey();
        ourEnumerationCache = new SLRUMap<>(intProperty, intProperty);
    }

    static void cacheId(Object obj, int i, PersistentEnumeratorBase<?> persistentEnumeratorBase) {
        SLRUMap<Object, Integer> sLRUMap = ourEnumerationCache;
        synchronized (sLRUMap) {
            sLRUMap.put(new CacheKey(obj, persistentEnumeratorBase), Integer.valueOf(i));
        }
    }

    static int getCachedId(Object obj, PersistentEnumeratorBase<?> persistentEnumeratorBase) {
        SLRUMap<Object, Integer> sLRUMap = ourEnumerationCache;
        synchronized (sLRUMap) {
            Integer num = sLRUMap.get(sharedKey(obj, persistentEnumeratorBase));
            if (num == null) {
                return 0;
            }
            return num.intValue();
        }
    }

    private static class CacheKey implements ShareableKey {
        public Object key;
        public PersistentEnumeratorBase<?> owner;

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ShareableKey
        public ShareableKey getStableCopy() {
            return this;
        }

        private CacheKey(Object obj, PersistentEnumeratorBase<?> persistentEnumeratorBase) {
            this.key = obj;
            this.owner = persistentEnumeratorBase;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CacheKey)) {
                return false;
            }
            CacheKey cacheKey = (CacheKey) obj;
            return this.key.equals(cacheKey.key) && this.owner.equals(cacheKey.owner);
        }

        public int hashCode() {
            return this.key.hashCode();
        }
    }

    private static class FlyweightKey extends CacheKey {
        /* JADX WARN: Multi-variable type inference failed */
        FlyweightKey() {
            super(null, 0 == true ? 1 : 0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorCache.CacheKey, org.jetbrains.kotlin.com.intellij.util.containers.ShareableKey
        public ShareableKey getStableCopy() {
            return new CacheKey(this.key, this.owner);
        }
    }

    private static CacheKey sharedKey(Object obj, PersistentEnumeratorBase<?> persistentEnumeratorBase) {
        CacheKey cacheKey = ourFlyweight;
        cacheKey.key = obj;
        cacheKey.owner = persistentEnumeratorBase;
        return cacheKey;
    }
}
