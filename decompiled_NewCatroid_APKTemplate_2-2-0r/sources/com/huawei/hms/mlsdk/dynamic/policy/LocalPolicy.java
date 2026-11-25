package com.huawei.hms.mlsdk.dynamic.policy;

import android.content.Context;

/* JADX INFO: loaded from: classes10.dex */
public class LocalPolicy extends AbstractContextPolicy {
    @Override // com.huawei.hms.mlsdk.dynamic.policy.AbstractContextPolicy
    public Context getPolicyContext(Context context, String str) {
        return context;
    }
}
