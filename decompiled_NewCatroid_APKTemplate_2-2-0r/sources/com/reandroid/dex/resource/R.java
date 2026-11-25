package com.reandroid.dex.resource;

import com.reandroid.apk.XmlHelper;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.model.DexAnnotation;
import com.reandroid.dex.model.DexAnnotationElement;
import com.reandroid.dex.model.DexClass;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.dex.model.DexMethod;
import com.reandroid.dex.model.DexValue;
import com.reandroid.dex.model.DexValueArray;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.InstanceIterator;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.UniqueIterator;
import com.reandroid.xml.XMLFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class R implements Iterable<RTypeItem> {
    private final DexClass dexClass;
    private final ArrayCollection<RTypeItem> typeList = new ArrayCollection<>();
    private boolean typeListLoaded;

    public R(DexClass dexClass) {
        this.dexClass = dexClass;
    }

    @Override // java.lang.Iterable
    public Iterator<RTypeItem> iterator() {
        return getTypeList().iterator();
    }

    public Iterator<RStyleableType> getStyleables() {
        return InstanceIterator.of(getTypeList().iterator(), RStyleableType.class);
    }

    public Iterator<RDeclareStyleable> getRDeclareStyleables() {
        return new UniqueIterator(new IterableIterator<RStyleableType, RDeclareStyleable>(getStyleables()) { // from class: com.reandroid.dex.resource.R.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<RDeclareStyleable> iterator(RStyleableType rStyleableType) {
                return rStyleableType.getRDeclareStyleables();
            }
        });
    }

    public int size() {
        return getTypeList().size();
    }

    public List<RTypeItem> getTypeList() {
        loadTypes();
        return this.typeList;
    }

    private void loadTypes() {
        if (this.typeListLoaded) {
            return;
        }
        this.typeListLoaded = true;
        ArrayCollection<RTypeItem> arrayCollection = this.typeList;
        arrayCollection.clear();
        arrayCollection.addAll(ComputeIterator.of(getDexClass().getClassRepository().getDexClasses(), new Function() { // from class: com.reandroid.dex.resource.R$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.createWithCheck((DexClass) obj);
            }
        }));
    }

    RTypeItem createWithCheck(DexClass dexClass) {
        if (!isChild(dexClass)) {
            return null;
        }
        RType rTypeCreateType = createType(dexClass);
        if (rTypeCreateType != null && rTypeCreateType.isValid()) {
            return rTypeCreateType;
        }
        RStyleableType rStyleableTypeCreateStyleableType = createStyleableType(dexClass);
        if (rStyleableTypeCreateStyleableType == null || !rStyleableTypeCreateStyleableType.isValid()) {
            return null;
        }
        return rStyleableTypeCreateStyleableType;
    }

    RType createType(DexClass dexClass) {
        if (dexClass != null) {
            return new RType(dexClass);
        }
        return null;
    }

    RStyleableType createStyleableType(DexClass dexClass) {
        if (dexClass != null) {
            return new RStyleableType(dexClass);
        }
        return null;
    }

    boolean isChild(DexClass dexClass) {
        if (dexClass == null || dexClass.isAbstract() || dexClass.isInterface() || dexClass.isEnum()) {
            return false;
        }
        TypeKey key = getKey();
        TypeKey key2 = dexClass.getKey();
        if (key.equals(key2) || !key.equalsPackage(key2)) {
            return false;
        }
        if (containsOnMembers(key)) {
            return true;
        }
        return key.equals(key2.getEnclosingClass());
    }

    private boolean containsOnMembers(TypeKey typeKey) {
        DexValueArray dalvikMemberClasses = getDalvikMemberClasses();
        if (dalvikMemberClasses != null) {
            return CollectionUtil.contains(dalvikMemberClasses.getKeys(), typeKey);
        }
        return false;
    }

    private DexValueArray getDalvikMemberClasses() {
        DexAnnotationElement dexAnnotationElement;
        DexAnnotation annotation = getDexClass().getAnnotation(TypeKey.DALVIK_MemberClass);
        if (annotation == null || (dexAnnotationElement = annotation.get("value")) == null) {
            return null;
        }
        DexValue value = dexAnnotationElement.getValue();
        if (value instanceof DexValueArray) {
            return (DexValueArray) value;
        }
        return null;
    }

    public String getName() {
        return getKey().getSimpleInnerName();
    }

    public TypeKey getKey() {
        return getDexClass().getKey();
    }

    public DexClass getDexClass() {
        return this.dexClass;
    }

    public void refresh() {
        this.typeListLoaded = false;
        loadTypes();
    }

    public boolean isValid() {
        DexClass dexClass = getDexClass();
        if (dexClass.isAbstract() || dexClass.isInterface() || dexClass.isEnum() || dexClass.isSynthetic() || dexClass.getDeclaredFields().hasNext()) {
            return false;
        }
        Iterator<DexMethod> declaredMethods = dexClass.getDeclaredMethods();
        while (declaredMethods.hasNext()) {
            if (!declaredMethods.next().isConstructor()) {
                return false;
            }
        }
        return isTypesValid();
    }

    private boolean isTypesValid() {
        Iterator<RTypeItem> it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (!it.next().isValid()) {
                return false;
            }
            z = true;
        }
        return z;
    }

    public String toJavaString() {
        StringWriter stringWriter = new StringWriter();
        SmaliWriter smaliWriter = new SmaliWriter();
        smaliWriter.setWriter(stringWriter);
        try {
            appendJava(smaliWriter);
            smaliWriter.close();
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public void appendJava(SmaliWriter smaliWriter) throws IOException {
        loadTypes();
        DexClass dexClass = getDexClass();
        smaliWriter.append("package ");
        smaliWriter.append((CharSequence) dexClass.getKey().getPackageSourceName());
        smaliWriter.append(';');
        smaliWriter.newLine();
        smaliWriter.newLine();
        smaliWriter.appendModifiers(dexClass.getAccessFlags());
        smaliWriter.append(ExternalAnnotationProvider.CLASS_PREFIX);
        smaliWriter.append((CharSequence) getName());
        smaliWriter.append(" {");
        smaliWriter.indentPlus();
        for (RTypeItem rTypeItem : this) {
            smaliWriter.newLine();
            rTypeItem.appendJava(smaliWriter);
        }
        smaliWriter.indentMinus();
        smaliWriter.newLine();
        smaliWriter.append('}');
    }

    public String toXml(TableBlock tableBlock) throws IOException {
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XMLFactory.newSerializer(stringWriter);
        serialize(tableBlock, xmlSerializerNewSerializer);
        xmlSerializerNewSerializer.flush();
        stringWriter.close();
        return stringWriter.toString();
    }

    public void serialize(TableBlock tableBlock, XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startDocument("utf8", null);
        XmlHelper.setIndent(xmlSerializer, true);
        xmlSerializer.startTag(null, XmlHelper.RESOURCES_TAG);
        Iterator<RDeclareStyleable> rDeclareStyleables = getRDeclareStyleables();
        while (rDeclareStyleables.hasNext()) {
            rDeclareStyleables.next().serialize(tableBlock, xmlSerializer);
        }
        xmlSerializer.endTag(null, XmlHelper.RESOURCES_TAG);
        xmlSerializer.endDocument();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof R) {
            return getKey().equals(((R) obj).getKey());
        }
        return false;
    }

    public int hashCode() {
        return getKey().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean z = this.typeListLoaded;
        if (!z) {
            sb.append("NOT-LOADED: ");
        }
        sb.append(getKey());
        if (z) {
            sb.append(", size = ");
            sb.append(size());
        }
        return sb.toString();
    }

    static R createValid(DexClass dexClass) {
        R rCreate = create(dexClass);
        if (rCreate == null || !rCreate.isValid()) {
            return null;
        }
        return rCreate;
    }

    static R create(DexClass dexClass) {
        if (dexClass != null) {
            return new R(dexClass);
        }
        return null;
    }

    public static Iterator<R> findAll(DexClassRepository dexClassRepository) {
        return findAll(dexClassRepository.getDexClasses());
    }

    public static Iterator<R> findAll(Iterator<? extends DexClass> it) {
        return ComputeIterator.of(it, new Function() { // from class: com.reandroid.dex.resource.R$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return R.createValid((DexClass) obj);
            }
        });
    }
}
