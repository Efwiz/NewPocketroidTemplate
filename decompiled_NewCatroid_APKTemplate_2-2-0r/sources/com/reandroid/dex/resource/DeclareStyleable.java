package com.reandroid.dex.resource;

import com.reandroid.apk.XmlHelper;
import com.reandroid.arsc.model.ResourceName;
import com.reandroid.arsc.value.AttributeDataFormat;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.ArrayCollection;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class DeclareStyleable implements Comparable<DeclareStyleable>, Iterable<Attr> {
    private final ArrayCollection<Attr> attrList = new ArrayCollection<>();

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f246name;
    public static final String TAG = ObjectsUtil.of("declare-styleable");
    public static final String NAME_format = ObjectsUtil.of(ConfigConstants.CONFIG_KEY_FORMAT);

    public DeclareStyleable(String str) {
        this.f246name = str;
    }

    public String getName() {
        return this.f246name;
    }

    public List<Attr> getAttrList() {
        return this.attrList;
    }

    @Override // java.lang.Iterable
    public Iterator<Attr> iterator() {
        return getAttrList().iterator();
    }

    public int size() {
        return getAttrList().size();
    }

    public boolean isEmpty() {
        return getAttrList().isEmpty();
    }

    public void add(Attr attr) {
        if (attr != null) {
            ArrayCollection<Attr> arrayCollection = this.attrList;
            if (arrayCollection.contains(attr)) {
                return;
            }
            arrayCollection.add(attr);
        }
    }

    public void serialize(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(null, TAG);
        xmlSerializer.attribute(null, "name", getName());
        Iterator<Attr> it = iterator();
        while (it.hasNext()) {
            it.next().serialize(xmlSerializer);
        }
        XmlHelper.setIndent(xmlSerializer, true);
        xmlSerializer.endTag(null, TAG);
    }

    @Override // java.lang.Comparable
    public int compareTo(DeclareStyleable declareStyleable) {
        if (declareStyleable == this) {
            return 0;
        }
        return CompareUtil.compare(getName(), declareStyleable.getName());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DeclareStyleable declareStyleable = (DeclareStyleable) obj;
        return ObjectsUtil.equals(getName(), declareStyleable.getName()) && getAttrList().equals(declareStyleable.getAttrList());
    }

    public int hashCode() {
        return (ObjectsUtil.hash(getName()) * 31) + getAttrList().hashCode();
    }

    public static void serialize(List<DeclareStyleable> list, XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startDocument("utf8", null);
        XmlHelper.setIndent(xmlSerializer, true);
        xmlSerializer.startTag(null, XmlHelper.RESOURCES_TAG);
        Iterator<DeclareStyleable> it = list.iterator();
        while (it.hasNext()) {
            it.next().serialize(xmlSerializer);
        }
        XmlHelper.setIndent(xmlSerializer, true);
        xmlSerializer.endTag(null, XmlHelper.RESOURCES_TAG);
        xmlSerializer.endDocument();
    }

    public static class Attr {
        private final String format;

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        private final int f247id;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f248name;
        private final DeclareStyleable parent;

        public Attr(DeclareStyleable declareStyleable, int i, String str, String str2) {
            this.parent = declareStyleable;
            this.f247id = i;
            this.f248name = str;
            this.format = str2;
        }

        public DeclareStyleable getParent() {
            return this.parent;
        }

        public int getId() {
            return this.f247id;
        }

        public String getName() {
            return this.f248name;
        }

        public String getFormat() {
            return this.format;
        }

        public int getFormatFlags() {
            return AttributeDataFormat.sum(AttributeDataFormat.parseValueTypes(getFormat()));
        }

        public int getIndex() {
            return getParent().getAttrList().indexOf(this);
        }

        public ResourceName toResourceName() {
            String strSubstring;
            String name2 = getName();
            int iIndexOf = name2.indexOf(58);
            if (iIndexOf > 0) {
                strSubstring = name2.substring(0, iIndexOf);
                name2 = name2.substring(iIndexOf + 1);
            } else {
                strSubstring = null;
            }
            return new ResourceName(strSubstring, ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED, name2);
        }

        public void serialize(XmlSerializer xmlSerializer) throws IOException {
            xmlSerializer.startTag(null, ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED);
            XmlHelper.setIndent(xmlSerializer, false);
            xmlSerializer.attribute(null, "name", getName());
            xmlSerializer.attribute(null, DeclareStyleable.NAME_format, getFormat());
            XmlHelper.setIndent(xmlSerializer, true);
            xmlSerializer.endTag(null, ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.f247id == ((Attr) obj).f247id;
        }

        public int hashCode() {
            return this.f247id;
        }

        public String toString() {
            return "id=" + HexUtil.toHex8(this.f247id) + ", name='" + this.f248name + "', format='" + this.format + '\'';
        }
    }
}
