package org.jetbrains.kotlin.com.intellij.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: XmlElement.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\b\u0087\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\tJ\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u0015\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003JE\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0002\u001a\u00020\u0003J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u0002\u001a\u00020\u0003J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00000\u00072\u0006\u0010\u0002\u001a\u00020\u0003J\t\u0010\u0018\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u001c\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/XmlElement;", "", "name", "", "attributes", "", "children", "", FirebaseAnalytics.Param.CONTENT, "(Ljava/lang/String;Ljava/util/Map;Ljava/util/List;Ljava/lang/String;)V", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "count", "", Namer.EQUALS_METHOD_NAME, "", "other", "getAttributeValue", "defaultValue", "getChild", "getChildren", "hashCode", "toString", "intellij.platform.util.xmlDom"}, k = 1, mv = {1, 5, 1})
public final /* data */ class XmlElement {
    public final Map<String, String> attributes;
    public final List<XmlElement> children;
    public final String content;
    public final String name;

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof XmlElement)) {
            return false;
        }
        XmlElement xmlElement = (XmlElement) other;
        return Intrinsics.areEqual(this.name, xmlElement.name) && Intrinsics.areEqual(this.attributes, xmlElement.attributes) && Intrinsics.areEqual(this.children, xmlElement.children) && Intrinsics.areEqual(this.content, xmlElement.content);
    }

    public int hashCode() {
        String str = this.name;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        Map<String, String> map = this.attributes;
        int iHashCode2 = (iHashCode + (map != null ? map.hashCode() : 0)) * 31;
        List<XmlElement> list = this.children;
        int iHashCode3 = (iHashCode2 + (list != null ? list.hashCode() : 0)) * 31;
        String str2 = this.content;
        return iHashCode3 + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "XmlElement(name=" + this.name + ", attributes=" + this.attributes + ", children=" + this.children + ", content=" + this.content + Constant.AFTER_QUTO;
    }

    public XmlElement(String name2, Map<String, String> attributes, List<XmlElement> children, String str) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(children, "children");
        this.name = name2;
        this.attributes = attributes;
        this.children = children;
        this.content = str;
    }

    public final String getAttributeValue(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.attributes.get(name2);
    }

    public final String getAttributeValue(String name2, String defaultValue) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String str = this.attributes.get(name2);
        return str != null ? str : defaultValue;
    }

    public final XmlElement getChild(String name2) {
        Object next;
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = this.children.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(((XmlElement) next).name, name2)) {
                return (XmlElement) next;
            }
        }
        next = null;
        return (XmlElement) next;
    }
}
