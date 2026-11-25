package com.reandroid.arsc.model;

import com.reandroid.arsc.coder.ReferenceString;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes10.dex */
public class ResourceName implements Comparable<ResourceName> {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f229name;
    private String packageName;
    private final String type;

    public ResourceName(String str, String str2, String str3) {
        this.packageName = str;
        this.type = str2;
        this.f229name = str3;
    }

    public ResourceName(String str, String str2) {
        this(null, str, str2);
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String str) {
        if (StringsUtil.isEmpty(str)) {
            str = null;
        }
        this.packageName = str;
    }

    public boolean matchesPackageName(String str) {
        String packageName = getPackageName();
        if (packageName == null || StringsUtil.isEmpty(str)) {
            return true;
        }
        return packageName.equals(str);
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.f229name;
    }

    public String buildReference(boolean z, String str) {
        String packageName = getPackageName();
        return buildReference(Boolean.valueOf(z), (str == null || packageName == null || packageName.equals(str)) ? false : true);
    }

    public String buildReference(Boolean bool, boolean z) {
        String packageName;
        StringBuilder sb = new StringBuilder();
        if (bool != null) {
            if (bool.booleanValue()) {
                sb.append(MangleConstant.Q_MARK);
            } else {
                sb.append('@');
            }
        }
        if (z && (packageName = getPackageName()) != null) {
            sb.append(packageName);
            sb.append(':');
        }
        sb.append(getType());
        sb.append('/');
        sb.append(getName());
        return sb.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(ResourceName resourceName) {
        if (resourceName == this) {
            return 0;
        }
        int iCompare = CompareUtil.compare(getPackageName(), resourceName.getPackageName());
        if (iCompare == 0) {
            iCompare = CompareUtil.compare(getType(), resourceName.getType());
        }
        return iCompare == 0 ? CompareUtil.compare(getName(), resourceName.getName()) : iCompare;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResourceName)) {
            return false;
        }
        ResourceName resourceName = (ResourceName) obj;
        String packageName = getPackageName();
        String packageName2 = resourceName.getPackageName();
        if (packageName == null || packageName2 == null || packageName.equals(packageName2)) {
            return ObjectsUtil.equals(getType(), resourceName.getType()) && ObjectsUtil.equals(getName(), resourceName.getName());
        }
        return false;
    }

    public int hashCode() {
        return ObjectsUtil.hash(getType(), getName());
    }

    public String toString() {
        return buildReference((Boolean) false, true);
    }

    public static ResourceName parse(String str) {
        return from(ReferenceString.parseReference(str));
    }

    public static ResourceName from(ReferenceString referenceString) {
        if (referenceString != null) {
            return new ResourceName(referenceString.packageName, referenceString.type, referenceString.f225name);
        }
        return null;
    }
}
