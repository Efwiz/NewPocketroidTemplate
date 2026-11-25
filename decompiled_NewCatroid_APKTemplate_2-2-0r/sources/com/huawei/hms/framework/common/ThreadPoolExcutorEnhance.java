package com.huawei.hms.framework.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public class ThreadPoolExcutorEnhance extends ThreadPoolExecutor {
    public ThreadPoolExcutorEnhance(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory) {
        super(i, i2, j, timeUnit, blockingQueue, threadFactory);
    }

    @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        super.execute(new RunnableEnhance(runnable));
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    protected void beforeExecute(Thread thread, Runnable runnable) {
        if (runnable instanceof RunnableEnhance) {
            String parentName = ((RunnableEnhance) runnable).getParentName();
            int iLastIndexOf = parentName.lastIndexOf(" -->");
            if (iLastIndexOf != -1) {
                parentName = StringUtils.substring(parentName, iLastIndexOf + " -->".length());
            }
            String name2 = thread.getName();
            int iLastIndexOf2 = name2.lastIndexOf(" -->");
            if (iLastIndexOf2 != -1) {
                name2 = StringUtils.substring(name2, iLastIndexOf2 + " -->".length());
            }
            thread.setName(parentName + " -->" + name2);
        }
        super.beforeExecute(thread, runnable);
    }
}
