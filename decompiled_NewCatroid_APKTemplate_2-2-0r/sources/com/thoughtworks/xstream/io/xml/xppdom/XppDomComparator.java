package com.thoughtworks.xstream.io.xml.xppdom;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/xppdom/XppDomComparator.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XppDomComparator implements Comparator {
    private final ThreadLocal xpath;

    public XppDomComparator() {
        this(null);
    }

    public XppDomComparator(ThreadLocal threadLocal) {
        this.xpath = threadLocal;
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        StringBuffer stringBuffer = new StringBuffer("/");
        int iCompareInternal = compareInternal((XppDom) obj, (XppDom) obj2, stringBuffer, -1);
        ThreadLocal threadLocal = this.xpath;
        if (threadLocal != null) {
            if (iCompareInternal != 0) {
                threadLocal.set(stringBuffer.toString());
            } else {
                threadLocal.set(null);
            }
        }
        return iCompareInternal;
    }

    private int compareInternal(XppDom xppDom, XppDom xppDom2, StringBuffer stringBuffer, int i) {
        int length = stringBuffer.length();
        String name2 = xppDom.getName();
        int iCompareTo = name2.compareTo(xppDom2.getName());
        stringBuffer.append(name2);
        if (i >= 0) {
            stringBuffer.append('[').append(i).append(']');
        }
        if (iCompareTo != 0) {
            stringBuffer.append(MangleConstant.Q_MARK);
            return iCompareTo;
        }
        String[] attributeNames = xppDom.getAttributeNames();
        String[] attributeNames2 = xppDom2.getAttributeNames();
        int length2 = attributeNames.length;
        int length3 = attributeNames2.length - length2;
        if (length3 != 0) {
            stringBuffer.append("::count(@*)");
            return length3 < 0 ? 1 : -1;
        }
        Arrays.sort(attributeNames);
        Arrays.sort(attributeNames2);
        for (int i2 = 0; i2 < length2; i2++) {
            String str = attributeNames[i2];
            int iCompareTo2 = str.compareTo(attributeNames2[i2]);
            if (iCompareTo2 != 0) {
                stringBuffer.append("[@").append(str).append("?]");
                return iCompareTo2;
            }
            int iCompareTo3 = xppDom.getAttribute(str).compareTo(xppDom2.getAttribute(str));
            if (iCompareTo3 != 0) {
                stringBuffer.append("[@").append(str).append(']');
                return iCompareTo3;
            }
        }
        int childCount = xppDom.getChildCount();
        int childCount2 = xppDom2.getChildCount() - childCount;
        if (childCount2 != 0) {
            stringBuffer.append("::count(*)");
            return childCount2 < 0 ? 1 : -1;
        }
        if (childCount > 0) {
            if (xppDom.getValue() != null || xppDom2.getValue() != null) {
                throw new IllegalArgumentException(new StringBuffer("XppDom cannot handle mixed mode at ").append((Object) stringBuffer).append("::text()").toString());
            }
            stringBuffer.append('/');
            HashMap map = new HashMap();
            for (int i3 = 0; i3 < childCount; i3++) {
                XppDom child = xppDom.getChild(i3);
                XppDom child2 = xppDom2.getChild(i3);
                String name3 = child.getName();
                if (!map.containsKey(name3)) {
                    map.put(name3, new int[1]);
                }
                int[] iArr = (int[]) map.get(name3);
                int i4 = iArr[0];
                iArr[0] = i4 + 1;
                childCount2 = compareInternal(child, child2, stringBuffer, i4);
                if (childCount2 != 0) {
                    return childCount2;
                }
            }
        } else {
            String value = xppDom2.getValue();
            String value2 = xppDom.getValue();
            if (value2 == null) {
                childCount2 = value == null ? 0 : -1;
            } else {
                childCount2 = value != null ? value2.compareTo(value) : 1;
            }
            if (childCount2 != 0) {
                stringBuffer.append("::text()");
                return childCount2;
            }
        }
        stringBuffer.setLength(length);
        return childCount2;
    }
}
