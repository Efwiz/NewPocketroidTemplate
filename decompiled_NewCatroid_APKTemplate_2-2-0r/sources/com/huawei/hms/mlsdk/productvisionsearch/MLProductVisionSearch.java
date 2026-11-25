package com.huawei.hms.mlsdk.productvisionsearch;

import android.graphics.Rect;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MLProductVisionSearch {
    private List<MLVisionSearchProduct> a;
    private String b;
    private Rect c;

    public MLProductVisionSearch() {
    }

    public Rect getBorder() {
        return this.c;
    }

    public List<MLVisionSearchProduct> getProductList() {
        return this.a;
    }

    public String getType() {
        return this.b;
    }

    public void setBorder(Rect rect) {
        this.c = rect;
    }

    public void setProductList(List<MLVisionSearchProduct> list) {
        this.a = list;
    }

    public void setType(String str) {
        this.b = str;
    }

    public MLProductVisionSearch(String str, Rect rect, List<MLVisionSearchProduct> list) {
        this.b = str;
        this.c = rect;
        this.a = list;
    }
}
