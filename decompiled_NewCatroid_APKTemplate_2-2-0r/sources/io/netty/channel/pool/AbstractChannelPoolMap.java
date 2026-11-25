package io.netty.channel.pool;

import io.netty.channel.pool.ChannelPool;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import java.io.Closeable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractChannelPoolMap<K, P extends ChannelPool> implements ChannelPoolMap<K, P>, Iterable<Map.Entry<K, P>>, Closeable {
    private final ConcurrentMap<K, P> map = PlatformDependent.newConcurrentHashMap();

    protected abstract P newPool(K k);

    @Override // io.netty.channel.pool.ChannelPoolMap
    public final P get(K k) {
        P p = this.map.get(ObjectUtil.checkNotNull(k, ASTExpr.DEFAULT_MAP_KEY_NAME));
        if (p != null) {
            return p;
        }
        P p2 = (P) newPool(k);
        P pPutIfAbsent = this.map.putIfAbsent(k, p2);
        if (pPutIfAbsent == null) {
            return p2;
        }
        poolCloseAsyncIfSupported(p2);
        return pPutIfAbsent;
    }

    public final boolean remove(K k) {
        P pRemove = this.map.remove(ObjectUtil.checkNotNull(k, ASTExpr.DEFAULT_MAP_KEY_NAME));
        if (pRemove == null) {
            return false;
        }
        poolCloseAsyncIfSupported(pRemove);
        return true;
    }

    private Future<Boolean> removeAsyncIfSupported(K k) {
        P pRemove = this.map.remove(ObjectUtil.checkNotNull(k, ASTExpr.DEFAULT_MAP_KEY_NAME));
        if (pRemove != null) {
            final Promise promiseNewPromise = GlobalEventExecutor.INSTANCE.newPromise();
            poolCloseAsyncIfSupported(pRemove).addListener(new GenericFutureListener<Future<? super Void>>() { // from class: io.netty.channel.pool.AbstractChannelPoolMap.1
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        promiseNewPromise.setSuccess(Boolean.TRUE);
                    } else {
                        promiseNewPromise.setFailure(future.cause());
                    }
                }
            });
            return promiseNewPromise;
        }
        return GlobalEventExecutor.INSTANCE.newSucceededFuture(Boolean.FALSE);
    }

    private static Future<Void> poolCloseAsyncIfSupported(ChannelPool channelPool) {
        if (channelPool instanceof SimpleChannelPool) {
            return ((SimpleChannelPool) channelPool).closeAsync();
        }
        try {
            channelPool.close();
            return GlobalEventExecutor.INSTANCE.newSucceededFuture(null);
        } catch (Exception e) {
            return GlobalEventExecutor.INSTANCE.newFailedFuture(e);
        }
    }

    @Override // java.lang.Iterable
    public final Iterator<Map.Entry<K, P>> iterator() {
        return new ReadOnlyIterator(this.map.entrySet().iterator());
    }

    public final int size() {
        return this.map.size();
    }

    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // io.netty.channel.pool.ChannelPoolMap
    public final boolean contains(K k) {
        return this.map.containsKey(ObjectUtil.checkNotNull(k, ASTExpr.DEFAULT_MAP_KEY_NAME));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        Iterator<K> it = this.map.keySet().iterator();
        while (it.hasNext()) {
            removeAsyncIfSupported(it.next()).syncUninterruptibly();
        }
    }
}
