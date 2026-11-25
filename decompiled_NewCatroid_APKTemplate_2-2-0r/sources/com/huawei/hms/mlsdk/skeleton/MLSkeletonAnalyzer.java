package com.huawei.hms.mlsdk.skeleton;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlkit.skeleton.common.JointParcel;
import com.huawei.hms.mlkit.skeleton.common.SkeletonFrameParcel;
import com.huawei.hms.mlkit.skeleton.common.SkeletonOptionsParcel;
import com.huawei.hms.mlkit.skeleton.common.SkeletonParcel;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes10.dex */
public class MLSkeletonAnalyzer extends MLAnalyzer<MLSkeleton> {
    public static final String c = "MLSkeletonAnalyzer";
    public static Map<AppSettingHolder<MLSkeletonAnalyzerSetting>, MLSkeletonAnalyzer> d = new HashMap();
    public SkeletonOptionsParcel a;
    public MLApplication b;

    public class a implements Callable<List<MLSkeleton>> {
        public final /* synthetic */ MLFrame a;

        public a(MLFrame mLFrame) {
            this.a = mLFrame;
        }

        @Override // java.util.concurrent.Callable
        public List<MLSkeleton> call() throws Exception {
            return MLSkeletonAnalyzer.this.a(this.a);
        }
    }

    public MLSkeletonAnalyzer(MLApplication mLApplication, MLSkeletonAnalyzerSetting mLSkeletonAnalyzerSetting) {
        this.b = mLApplication;
        Bundle bundle = mLApplication.toBundle();
        bundle.putString("com.huawei.hms.client.service.name:ml-computer-vision", "ml-computer-vision-skeleton:3.7.0.302");
        this.a = new SkeletonOptionsParcel(mLSkeletonAnalyzerSetting.b(), true, bundle, mLSkeletonAnalyzerSetting.a());
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public SparseArray<MLSkeleton> analyseFrame(MLFrame mLFrame) {
        if (mLFrame == null) {
            throw new IllegalArgumentException("Missing frame.");
        }
        mLFrame.initialize();
        MLFrame frame = mLFrame.getFrame(false, true);
        SparseArray<MLSkeleton> sparseArray = new SparseArray<>();
        List<MLSkeleton> listA = a(frame);
        for (int i = 0; i < listA.size(); i++) {
            sparseArray.put(i, listA.get(i));
        }
        return sparseArray;
    }

    public Task<List<MLSkeleton>> asyncAnalyseFrame(MLFrame mLFrame) {
        if (mLFrame == null) {
            throw new IllegalArgumentException("Missing frame.");
        }
        mLFrame.initialize();
        return Tasks.callInBackground(new a(mLFrame.getFrame(false, true)));
    }

    public float caluteSimilarity(List<MLSkeleton> list, List<MLSkeleton> list2) {
        ArrayList arrayList = new ArrayList();
        for (MLSkeleton mLSkeleton : list) {
            ArrayList arrayList2 = new ArrayList();
            for (MLJoint mLJoint : mLSkeleton.getJoints()) {
                arrayList2.add(new JointParcel(Float.valueOf(mLJoint.getPointX()), Float.valueOf(mLJoint.getPointY()), mLJoint.getType(), Float.valueOf(mLJoint.getScore())));
            }
            arrayList.add(new SkeletonParcel(arrayList2));
        }
        ArrayList arrayList3 = new ArrayList();
        for (MLSkeleton mLSkeleton2 : list2) {
            ArrayList arrayList4 = new ArrayList();
            for (MLJoint mLJoint2 : mLSkeleton2.getJoints()) {
                arrayList4.add(new JointParcel(Float.valueOf(mLJoint2.getPointX()), Float.valueOf(mLJoint2.getPointY()), mLJoint2.getType(), Float.valueOf(mLJoint2.getScore())));
            }
            arrayList3.add(new SkeletonParcel(arrayList4));
        }
        return com.huawei.hms.mlsdk.skeleton.a.b.a.a(this.b.getAppContext(), arrayList, arrayList3, this.a);
    }

    public void stop() throws IOException {
        com.huawei.hms.mlsdk.skeleton.a.b.a.b(this.b.getAppContext());
    }

    public static synchronized MLSkeletonAnalyzer a(MLApplication mLApplication, MLSkeletonAnalyzerSetting mLSkeletonAnalyzerSetting) {
        MLSkeletonAnalyzer mLSkeletonAnalyzer;
        AppSettingHolder<MLSkeletonAnalyzerSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLSkeletonAnalyzerSetting);
        mLSkeletonAnalyzer = d.get(appSettingHolderCreate);
        if (mLSkeletonAnalyzer == null) {
            mLSkeletonAnalyzer = new MLSkeletonAnalyzer(mLApplication, mLSkeletonAnalyzerSetting);
            d.put(appSettingHolderCreate, mLSkeletonAnalyzer);
        }
        com.huawei.hms.mlsdk.skeleton.a.b.a.a(mLApplication.getAppContext());
        Bundle bundle = mLApplication.toBundle();
        bundle.putString("com.huawei.hms.client.service.name:ml-computer-vision", "ml-computer-vision-skeleton:3.7.0.302");
        if (com.huawei.hms.mlsdk.skeleton.a.b.a.a(new SkeletonOptionsParcel(mLSkeletonAnalyzerSetting.b(), true, bundle, mLSkeletonAnalyzerSetting.a())) < 0) {
            SmartLog.w(c, "Initial failed.");
        }
        return mLSkeletonAnalyzer;
    }

    public final List<MLSkeleton> a(MLFrame mLFrame) {
        Bundle bundle = MLApplication.getInstance().toBundle();
        bundle.putString("com.huawei.hms.client.service.name:ml-computer-vision", "ml-computer-vision-skeleton:3.7.0.302");
        com.huawei.hms.mlsdk.skeleton.a aVar = com.huawei.hms.mlsdk.skeleton.a.b.a;
        Context appContext = this.b.getAppContext();
        SkeletonFrameParcel skeletonFrameParcel = new SkeletonFrameParcel();
        MLFrame.Property propertyAcquireProperty = mLFrame.acquireProperty();
        skeletonFrameParcel.width = propertyAcquireProperty.getWidth();
        skeletonFrameParcel.height = propertyAcquireProperty.getHeight();
        skeletonFrameParcel.format = propertyAcquireProperty.getFormatType();
        skeletonFrameParcel.rotation = propertyAcquireProperty.getQuadrant();
        ByteBuffer byteBuffer = mLFrame.getByteBuffer();
        if (byteBuffer != null) {
            skeletonFrameParcel.bytes = byteBuffer.array();
        }
        if (mLFrame.readBitmap() == null) {
            skeletonFrameParcel.bitmap = null;
        } else {
            skeletonFrameParcel.bitmap = mLFrame.readBitmap();
        }
        List<SkeletonParcel> listA = aVar.a(appContext, bundle, skeletonFrameParcel, this.a);
        if (listA == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < listA.size(); i++) {
            SkeletonParcel skeletonParcel = listA.get(i);
            ArrayList arrayList2 = new ArrayList();
            for (JointParcel jointParcel : skeletonParcel.joints) {
                arrayList2.add(new MLJoint(jointParcel.x.floatValue(), jointParcel.y.floatValue(), jointParcel.type, jointParcel.score.floatValue()));
            }
            arrayList.add(new MLSkeleton(arrayList2));
        }
        return arrayList;
    }
}
