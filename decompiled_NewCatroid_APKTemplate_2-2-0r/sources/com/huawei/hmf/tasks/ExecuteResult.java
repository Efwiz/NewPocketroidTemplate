package com.huawei.hmf.tasks;

/* JADX INFO: loaded from: classes10.dex */
public interface ExecuteResult<TResult> {
    void cancel();

    void onComplete(Task<TResult> task);
}
