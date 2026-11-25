package org.catrobat.catroid.common;

import java.io.Serializable;
import java.util.Objects;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/NfcTagData.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class NfcTagData implements Cloneable, Comparable<NfcTagData>, Nameable, Serializable {
    private static final long serialVersionUID = 1;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f514name;
    private String uid;

    public NfcTagData clone() {
        NfcTagData nfcTagData = new NfcTagData();
        nfcTagData.f514name = this.f514name;
        nfcTagData.uid = this.uid;
        return nfcTagData;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f514name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f514name = name2;
    }

    public void setNfcTagUid(String uid) {
        this.uid = uid;
    }

    public String getNfcTagUid() {
        return this.uid;
    }

    @Override // java.lang.Comparable
    public int compareTo(NfcTagData nfcTagData) {
        return this.uid.compareTo(nfcTagData.uid);
    }

    public String toString() {
        return this.f514name;
    }

    public boolean equals(Object obj) {
        if (obj instanceof NfcTagData) {
            return this.uid.equals(((NfcTagData) obj).getNfcTagUid());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.f514name, this.uid);
    }
}
