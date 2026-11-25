package com.reandroid.dex.model;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.data.ClassData;
import com.reandroid.dex.data.FieldDef;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.io.IOUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class RClass extends DexClass {
    static final TableBlock EMPTY_TABLE = new TableBlock();
    private static final String SIMPLE_NAME_PREFIX = "R$";

    public RClass(DexFile dexFile, ClassId classId) {
        super(dexFile, classId);
    }

    public RField load(ResourceEntry resourceEntry) {
        if (resourceEntry.isEmpty()) {
            return null;
        }
        RField orCreateStaticField = getOrCreateStaticField(new FieldKey(getKey().getTypeName(), RField.sanitizeResourceName(resourceEntry.getName()), TypeKey.TYPE_I.getTypeName()));
        orCreateStaticField.setResourceId(resourceEntry.getResourceId());
        return orCreateStaticField;
    }

    public String toJavaDeclare() {
        return toJavaDeclare(true);
    }

    public String toJavaDeclare(boolean z) {
        StringBuilder sb = new StringBuilder("    public static class ");
        sb.append(getResourceType());
        sb.append(" {");
        Iterator<DexField> staticFields = getStaticFields();
        while (staticFields.hasNext()) {
            sb.append("\n        ");
            sb.append(((RField) staticFields.next()).toJavaDeclare(z));
        }
        sb.append("\n    }");
        return sb.toString();
    }

    public String getResourceType() {
        return toResourceTypeName(getDefining().getTypeName());
    }

    @Override // com.reandroid.dex.model.DexClass
    public RField getOrCreateStaticField(FieldKey fieldKey) {
        return new RField(this, getOrCreateStatic(fieldKey));
    }

    @Override // com.reandroid.dex.model.DexClass
    public Iterator<DexField> getStaticFields() {
        ClassData classData = getClassData();
        if (classData != null) {
            return ComputeIterator.of(classData.getStaticFields(), new Function() { // from class: com.reandroid.dex.model.RClass$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.createRField((FieldDef) obj);
                }
            });
        }
        return EmptyIterator.of();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RField createRField(FieldDef fieldDef) {
        if (fieldDef.isStatic() && RField.isResourceIdValue(fieldDef.getStaticInitialValue())) {
            return new RField(this, fieldDef);
        }
        return null;
    }

    public void initialize() {
        ClassId id2 = getId();
        id2.setSuperClass(TypeKey.OBJECT);
        id2.setSourceFile("R.java");
        initializeAnnotations();
    }

    private void initializeAnnotations() {
        ClassId id2 = getId();
        id2.getOrCreateDalvikEnclosingClass();
        id2.getOrCreateDalvikInnerClass();
    }

    @Override // com.reandroid.dex.model.DexClass, com.reandroid.dex.model.DexDeclaration, com.reandroid.dex.model.Dex
    public String toString() {
        return toJavaDeclare();
    }

    static boolean isRClassName(ClassId classId) {
        if (classId != null) {
            return isRClassName(classId.getName());
        }
        return false;
    }

    static boolean isRClassName(String str) {
        return (str == null || toResourceTypeName(str) == null) ? false : true;
    }

    private static String toResourceTypeName(String str) {
        String simpleName = DexUtils.getSimpleName(str);
        if (simpleName.length() < 4 || !simpleName.startsWith(SIMPLE_NAME_PREFIX)) {
            return null;
        }
        String strSubstring = simpleName.substring(SIMPLE_NAME_PREFIX.length());
        if (strSubstring.indexOf(36) >= 0) {
            return null;
        }
        return strSubstring;
    }

    public static void serializePublicXml(Collection<RField> collection, XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startDocument("utf-8", null);
        xmlSerializer.text("\n");
        xmlSerializer.startTag(null, PackageBlock.TAG_resources);
        ArrayCollection arrayCollection = new ArrayCollection();
        arrayCollection.addAll(collection);
        arrayCollection.sort(CompareUtil.getComparableComparator());
        Iterator<T> it = arrayCollection.iterator();
        while (it.hasNext()) {
            ((RField) it.next()).serializePublicXml(xmlSerializer);
        }
        xmlSerializer.text("\n");
        xmlSerializer.endTag(null, PackageBlock.TAG_resources);
        xmlSerializer.endDocument();
        xmlSerializer.flush();
        IOUtil.close(xmlSerializer);
    }
}
