package com.android.tools.r8.origin;

import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Origin implements Comparable<Origin> {
    private static final Origin b = new d();
    private static final Origin c = new e();
    static final /* synthetic */ boolean d = true;
    private final Origin a;

    private Origin() {
        this.a = null;
    }

    public static Origin root() {
        return b;
    }

    public static Origin unknown() {
        return c;
    }

    List c(int i) {
        List listC = parent().c(i + 1);
        listC.add(part());
        return listC;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Origin)) {
            return false;
        }
        Origin originParent = (Origin) obj;
        Origin originParent2 = this;
        while (originParent2 != null && originParent != null && originParent2.part().equals(originParent.part())) {
            originParent2 = originParent2.parent();
            originParent = originParent.parent();
        }
        return originParent2 == originParent;
    }

    public <T extends Origin> T getFromHierarchy(Class<T> cls) {
        T t = (T) this;
        while (!cls.isInstance(t)) {
            t = (T) t.parent();
            if (t == null) {
                return null;
            }
        }
        return t;
    }

    public int hashCode() {
        Iterator<String> it = parts().iterator();
        int iHashCode = 1;
        while (it.hasNext()) {
            iHashCode = (iHashCode * 31) + it.next().hashCode();
        }
        return iHashCode;
    }

    public Origin parent() {
        return this.a;
    }

    public abstract String part();

    public List<String> parts() {
        return c(0);
    }

    public String toString() {
        return String.join(":", parts());
    }

    @Override // java.lang.Comparable
    public int compareTo(Origin origin) {
        List<String> listParts = parts();
        List<String> listParts2 = origin.parts();
        int iMin = Math.min(listParts.size(), listParts2.size());
        for (int i = 0; i < iMin; i++) {
            int iCompareTo = listParts.get(i).compareTo(listParts2.get(i));
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return Integer.compare(listParts.size(), listParts2.size());
    }

    protected Origin(Origin origin) {
        if (!d && origin == null) {
            throw new AssertionError();
        }
        this.a = origin;
    }
}
