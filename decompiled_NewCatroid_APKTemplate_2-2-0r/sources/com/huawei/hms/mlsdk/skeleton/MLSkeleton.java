package com.huawei.hms.mlsdk.skeleton;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MLSkeleton {
    public final List<MLJoint> a;

    public MLSkeleton(List<MLJoint> list) {
        ArrayList arrayList = new ArrayList();
        this.a = arrayList;
        arrayList.addAll(list);
    }

    public MLJoint getJointPoint(int i) {
        List<MLJoint> list = this.a;
        if (list == null || list.size() <= 0) {
            return null;
        }
        for (int i2 = 0; i2 < this.a.size(); i2++) {
            MLJoint mLJoint = this.a.get(i2);
            if (mLJoint != null && mLJoint.getType() == i) {
                return mLJoint;
            }
        }
        return null;
    }

    public List<MLJoint> getJoints() {
        return this.a;
    }
}
