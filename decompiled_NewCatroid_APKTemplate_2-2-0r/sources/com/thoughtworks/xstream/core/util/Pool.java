package com.thoughtworks.xstream.core.util;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/Pool.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Pool {
    private final Factory factory;
    private final int initialPoolSize;
    private final int maxPoolSize;
    private transient Object mutex = new Object();
    private transient int nextAvailable;
    private transient Object[] pool;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/Pool$Factory.class
      classes11.dex
     */
    public interface Factory {
        Object newInstance();
    }

    public Pool(int i, int i2, Factory factory) {
        this.initialPoolSize = i;
        this.maxPoolSize = i2;
        this.factory = factory;
    }

    public Object fetchFromPool() {
        int i;
        Object objNewInstance;
        synchronized (this.mutex) {
            if (this.pool == null) {
                this.pool = new Object[this.maxPoolSize];
                this.nextAvailable = this.initialPoolSize;
                while (this.nextAvailable > 0) {
                    putInPool(this.factory.newInstance());
                }
            }
            while (true) {
                i = this.nextAvailable;
                if (i != this.maxPoolSize) {
                    break;
                }
                try {
                    this.mutex.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(new StringBuffer().append("Interrupted whilst waiting for a free item in the pool : ").append(e.getMessage()).toString());
                }
            }
            Object[] objArr = this.pool;
            this.nextAvailable = i + 1;
            objNewInstance = objArr[i];
            if (objNewInstance == null) {
                objNewInstance = this.factory.newInstance();
                putInPool(objNewInstance);
                this.nextAvailable++;
            }
        }
        return objNewInstance;
    }

    protected void putInPool(Object obj) {
        synchronized (this.mutex) {
            Object[] objArr = this.pool;
            int i = this.nextAvailable - 1;
            this.nextAvailable = i;
            objArr[i] = obj;
            this.mutex.notify();
        }
    }

    private Object readResolve() {
        this.mutex = new Object();
        return this;
    }
}
