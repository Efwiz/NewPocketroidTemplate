package com.huawei.hms.update.ui;

import java.io.Serializable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes10.dex */
public class UpdateBean implements Serializable {
    public boolean a;
    public boolean b;
    public String c;
    public int d;
    public String e;
    public String f;
    public ArrayList g;
    public boolean h = true;

    public static <T> T a(T t) {
        return t;
    }

    public String getClientAppId() {
        return (String) a(this.e);
    }

    public String getClientAppName() {
        return (String) a(this.f);
    }

    public String getClientPackageName() {
        return (String) a(this.c);
    }

    public int getClientVersionCode() {
        return ((Integer) a(Integer.valueOf(this.d))).intValue();
    }

    public boolean getResolutionInstallHMS() {
        return this.b;
    }

    public ArrayList getTypeList() {
        return (ArrayList) a(this.g);
    }

    public boolean isHmsOrApkUpgrade() {
        return ((Boolean) a(Boolean.valueOf(this.a))).booleanValue();
    }

    public boolean isNeedConfirm() {
        return ((Boolean) a(Boolean.valueOf(this.h))).booleanValue();
    }

    public void setClientAppId(String str) {
        this.e = str;
    }

    public void setClientAppName(String str) {
        this.f = str;
    }

    public void setClientPackageName(String str) {
        this.c = str;
    }

    public void setClientVersionCode(int i) {
        this.d = i;
    }

    public void setHmsOrApkUpgrade(boolean z) {
        this.a = z;
    }

    public void setNeedConfirm(boolean z) {
        this.h = z;
    }

    public void setResolutionInstallHMS(boolean z) {
        this.b = z;
    }

    public void setTypeList(ArrayList arrayList) {
        this.g = arrayList;
    }
}
