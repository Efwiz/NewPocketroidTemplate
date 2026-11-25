package com.reandroid.identifiers;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.coder.xml.XmlCoder;
import com.reandroid.utils.HexUtil;
import java.io.File;

/* JADX INFO: loaded from: classes11.dex */
public class Identifier implements Comparable<Identifier> {
    public static final boolean CASE_INSENSITIVE_FS = new File("ABC").equals(new File("abc"));
    static final String XML_ATTRIBUTE_ID = "id";
    static final String XML_ATTRIBUTE_NAME = "name";
    static final String XML_ATTRIBUTE_PACKAGE = "package";
    static final String XML_ATTRIBUTE_TYPE = "type";
    static final String XML_TAG_PUBLIC = "public";
    static final String XML_TAG_RESOURCES = "resources";

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private int f258id;
    private Identifier mParent;
    private Object mTag;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f259name;

    public Identifier(int i, String str) {
        this.f258id = i;
        this.f259name = str;
    }

    public Object getTag() {
        return this.mTag;
    }

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public int getId() {
        return this.f258id;
    }

    public void setId(int i) {
        this.f258id = i;
    }

    public String getName() {
        return this.f259name;
    }

    public void setName(String str) {
        this.f259name = str;
    }

    Identifier getParent() {
        return this.mParent;
    }

    void setParent(Identifier identifier) {
        if (identifier == this) {
            return;
        }
        this.mParent = identifier;
    }

    public String getHexId() {
        return HexUtil.toHex2((byte) getId());
    }

    long getUniqueId() {
        return getId();
    }

    @Override // java.lang.Comparable
    public int compareTo(Identifier identifier) {
        return Long.compare(getUniqueId(), identifier.getUniqueId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && getUniqueId() == ((Identifier) obj).getUniqueId();
    }

    public int hashCode() {
        return Long.hashCode(getUniqueId());
    }

    public String toString() {
        return getName() + "(" + getHexId() + Constant.AFTER_QUTO;
    }

    public static boolean isAapt() {
        return XmlCoder.getInstance().getSetting().isAapt();
    }
}
