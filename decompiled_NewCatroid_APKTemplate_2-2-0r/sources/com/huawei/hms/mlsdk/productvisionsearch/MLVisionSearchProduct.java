package com.huawei.hms.mlsdk.productvisionsearch;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MLVisionSearchProduct {
    private String a;
    private List<MLVisionSearchProductImage> b;
    private String c;
    private String d;

    private static final class b implements Serializable, Comparator<MLVisionSearchProductImage> {
        /* synthetic */ b(a aVar) {
        }

        @Override // java.util.Comparator
        public int compare(MLVisionSearchProductImage mLVisionSearchProductImage, MLVisionSearchProductImage mLVisionSearchProductImage2) {
            return Float.compare(mLVisionSearchProductImage.getPossibility(), mLVisionSearchProductImage2.getPossibility());
        }
    }

    public MLVisionSearchProduct() {
        this(null, null);
    }

    public String getCustomContent() {
        return this.d;
    }

    public List<MLVisionSearchProductImage> getImageList() {
        return this.b;
    }

    public float getPossibility() {
        if (com.huawei.hms.mlsdk.dynamic.a.a(this.b)) {
            return 0.0f;
        }
        return ((MLVisionSearchProductImage) Collections.max(this.b, new b(null))).getPossibility();
    }

    public String getProductId() {
        return this.a;
    }

    public String getProductUrl() {
        return this.c;
    }

    public void setCustomContent(String str) {
        this.d = str;
    }

    public void setImageList(List<MLVisionSearchProductImage> list) {
        this.b = list;
    }

    public void setProductId(String str) {
        this.a = str;
    }

    public void setProductUrl(String str) {
        this.c = str;
    }

    public MLVisionSearchProduct(String str, List<MLVisionSearchProductImage> list) {
        this(str, list, null, null);
    }

    public MLVisionSearchProduct(String str, List<MLVisionSearchProductImage> list, String str2, String str3) {
        this.a = str;
        this.b = list;
        this.c = str2;
        this.d = str3;
    }
}
