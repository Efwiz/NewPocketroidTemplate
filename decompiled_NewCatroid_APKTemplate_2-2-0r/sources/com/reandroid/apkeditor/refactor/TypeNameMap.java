package com.reandroid.apkeditor.refactor;

import com.reandroid.apk.XmlHelper;
import com.reandroid.xml.XMLDocument;
import com.reandroid.xml.XMLElement;
import com.reandroid.xml.XMLNode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: loaded from: classes10.dex */
public class TypeNameMap implements Comparator<TypeName> {
    private final Map<Integer, TypeName> map = new HashMap();

    public boolean contains(String str) {
        Iterator<TypeName> it = this.map.values().iterator();
        while (it.hasNext()) {
            if (Objects.equals(str, it.next().getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(int i) {
        return this.map.containsKey(Integer.valueOf(i & (-65536)));
    }

    public XMLDocument toXMLDocument() {
        XMLDocument xMLDocument = new XMLDocument();
        XMLElement xMLElement = new XMLElement(XmlHelper.RESOURCES_TAG);
        xMLDocument.setDocumentElement(xMLElement);
        Iterator<TypeName> it = listTypeNames().iterator();
        while (it.hasNext()) {
            xMLElement.add((XMLNode) it.next().toXMLElement());
        }
        return xMLDocument;
    }

    public List<TypeName> listTypeNames() {
        ArrayList arrayList = new ArrayList(this.map.values());
        arrayList.sort(this);
        return arrayList;
    }

    public int count() {
        return this.map.size();
    }

    public void add(XMLDocument xMLDocument) {
        XMLElement documentElement = xMLDocument.getDocumentElement();
        if (documentElement == null) {
            return;
        }
        Iterator<? extends XMLElement> elements = documentElement.getElements();
        while (elements.hasNext()) {
            add(TypeName.fromXMLElement(elements.next()));
        }
    }

    public void add(int i, String str) {
        add(new TypeName(i, str));
    }

    public void add(TypeName typeName) {
        this.map.remove(Integer.valueOf(typeName.getPackageTypeId()));
        this.map.put(Integer.valueOf(typeName.getPackageTypeId()), typeName);
    }

    @Override // java.util.Comparator
    public int compare(TypeName typeName, TypeName typeName2) {
        return typeName.compareTo(typeName2);
    }

    public static class TypeName implements Comparable<TypeName> {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f218name;
        private final int packageTypeId;

        public TypeName(int i, String str) {
            this.packageTypeId = i & (-65536);
            this.f218name = str;
        }

        public XMLElement toXMLElement() {
            XMLElement xMLElement = new XMLElement(ModuleXmlParser.TYPE);
            xMLElement.setAttribute("id", getHexId());
            xMLElement.setAttribute("name", getName());
            return xMLElement;
        }

        public String getHexId() {
            return String.format("0x%08x", Integer.valueOf(getPackageTypeId()));
        }

        public int getPackageTypeId() {
            return this.packageTypeId;
        }

        public String getName() {
            return this.f218name;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.packageTypeId == ((TypeName) obj).packageTypeId;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.packageTypeId));
        }

        public String toString() {
            return getHexId() + ":" + getName();
        }

        public static TypeName fromXMLElement(XMLElement xMLElement) {
            return new TypeName(TypeNameMap.decodeHex(xMLElement.getAttributeValue("id")), xMLElement.getAttributeValue("name"));
        }

        @Override // java.lang.Comparable
        public int compareTo(TypeName typeName) {
            return Integer.compare(getPackageTypeId() >> 16, typeName.getPackageTypeId() >> 16);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int decodeHex(String str) {
        return (int) Long.decode(str).longValue();
    }
}
