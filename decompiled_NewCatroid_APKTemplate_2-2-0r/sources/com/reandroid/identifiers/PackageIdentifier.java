package com.reandroid.identifiers;

import com.reandroid.apk.XmlHelper;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.arsc.value.Entry;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.io.IOUtil;
import com.reandroid.xml.XMLFactory;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class PackageIdentifier extends IdentifierMap<TypeIdentifier> {
    private PackageBlock mPackageBlock;
    private int mPackageLoadStamp;

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ Collection getItems() {
        return super.getItems();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ int getMaxId() {
        return super.getMaxId();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ boolean hasDuplicates() {
        return super.hasDuplicates();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ List list() {
        return super.list();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ List listDuplicates() {
        return super.listDuplicates();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ List listNames() {
        return super.listNames();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ void reloadNameMap() {
        super.reloadNameMap();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // com.reandroid.identifiers.IdentifierMap, com.reandroid.identifiers.Identifier
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public PackageIdentifier(int i, String str) {
        super(i, str);
    }

    public PackageIdentifier() {
        this(0, null);
    }

    private void initializePackageJson(PackageBlock packageBlock) {
        File fileSearchPackageJsonFromTag = searchPackageJsonFromTag();
        if (fileSearchPackageJsonFromTag == null) {
            return;
        }
        try {
            packageBlock.fromJson(new JSONObject((InputStream) new FileInputStream(fileSearchPackageJsonFromTag)));
            if (getName() == null) {
                setName(packageBlock.getName());
            }
        } catch (FileNotFoundException unused) {
        }
    }

    private File searchPackageJsonFromTag() {
        File parentFile;
        File parentFile2;
        File parentFile3;
        Object tag = getTag();
        if (!(tag instanceof File) || (parentFile = ((File) tag).getParentFile()) == null || !CompoundEntry.NAME_values.equals(parentFile.getName()) || (parentFile2 = parentFile.getParentFile()) == null || (parentFile3 = parentFile2.getParentFile()) == null) {
            return null;
        }
        File file = new File(parentFile3, "package.json");
        if (file.isFile()) {
            return file;
        }
        return null;
    }

    public List<ResourceIdentifier> listDuplicateResources() {
        ArrayList arrayList = new ArrayList();
        Iterator it = list().iterator();
        while (it.hasNext()) {
            arrayList.addAll(((TypeIdentifier) it.next()).listDuplicates());
        }
        return arrayList;
    }

    public boolean hasDuplicateResources() {
        Iterator it = getItems().iterator();
        while (it.hasNext()) {
            if (((TypeIdentifier) it.next()).hasDuplicates()) {
                return true;
            }
        }
        return false;
    }

    public List<ResourceIdentifier> ensureUniqueResourceNames() {
        ArrayList arrayList = new ArrayList();
        Iterator it = list().iterator();
        while (it.hasNext()) {
            arrayList.addAll(((TypeIdentifier) it.next()).ensureUniqueResourceNames());
        }
        return arrayList;
    }

    public void setResourceNamesToPackage() {
        setResourceNamesToPackage(getPackageBlock());
    }

    public void setResourceNamesToPackage(PackageBlock packageBlock) {
        if (packageBlock == null) {
            return;
        }
        Iterator<SpecTypePair> it = packageBlock.listSpecTypePairs().iterator();
        while (it.hasNext()) {
            Iterator<ResourceEntry> resources = it.next().getResources();
            while (resources.hasNext()) {
                setResourceNamesToEntry(resources.next());
            }
        }
    }

    public void setResourceNamesToEntry(ResourceEntry resourceEntry) {
        ResourceIdentifier resourceIdentifier = getResourceIdentifier(resourceEntry.getResourceId());
        if (resourceIdentifier == null) {
            return;
        }
        resourceEntry.setName(resourceIdentifier.getName());
    }

    public ResourceIdentifier getResourceIdentifier(int i) {
        TypeIdentifier typeIdentifier = get((i >> 16) & 255);
        if (typeIdentifier != null) {
            return typeIdentifier.get(i & 65535);
        }
        return null;
    }

    public ResourceIdentifier getResourceIdentifier(String str, String str2) {
        TypeIdentifier typeIdentifier = get(str);
        if (typeIdentifier != null) {
            return typeIdentifier.get(str2);
        }
        return null;
    }

    public int getResourcesCount() {
        Iterator it = getItems().iterator();
        int size = 0;
        while (it.hasNext()) {
            size += ((TypeIdentifier) it.next()).size();
        }
        return size;
    }

    public void writePublicXml(File file) throws IOException {
        XmlSerializer xmlSerializerNewSerializer = XMLFactory.newSerializer(file);
        write(xmlSerializerNewSerializer);
        IOUtil.close(xmlSerializerNewSerializer);
    }

    public void writePublicXml(OutputStream outputStream) throws IOException {
        XmlSerializer xmlSerializerNewSerializer = XMLFactory.newSerializer(outputStream);
        xmlSerializerNewSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        write(xmlSerializerNewSerializer);
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startDocument("utf-8", null);
        xmlSerializer.text("\n");
        xmlSerializer.startTag(null, XmlHelper.RESOURCES_TAG);
        writePackageInfo(xmlSerializer);
        writeTypes(xmlSerializer);
        xmlSerializer.text("\n");
        xmlSerializer.endTag(null, XmlHelper.RESOURCES_TAG);
        xmlSerializer.endDocument();
        closeSerializer(xmlSerializer);
    }

    private void writePackageInfo(XmlSerializer xmlSerializer) throws IOException {
        String name2 = getName();
        if (name2 != null) {
            xmlSerializer.attribute(null, "package", name2);
        }
        int id2 = getId();
        if (id2 != 0) {
            xmlSerializer.attribute(null, "id", HexUtil.toHex2((byte) id2));
        }
    }

    private void writeTypes(XmlSerializer xmlSerializer) throws IOException {
        Iterator it = list().iterator();
        while (it.hasNext()) {
            ((TypeIdentifier) it.next()).write(xmlSerializer);
        }
    }

    public void load(PackageBlock packageBlock) {
        setId(packageBlock.getId());
        setName(packageBlock.getName());
        loadEntryGroups(packageBlock);
        setPackageBlock(packageBlock);
    }

    private void loadEntryGroups(PackageBlock packageBlock) {
        Iterator<SpecTypePair> it = packageBlock.listSpecTypePairs().iterator();
        while (it.hasNext()) {
            Iterator<ResourceEntry> resources = it.next().getResources();
            while (resources.hasNext()) {
                add(resources.next());
            }
        }
    }

    public void loadPublicXml(File file) throws XmlPullParserException, IOException {
        loadPublicXml(XMLFactory.newPullParser(file));
    }

    public void loadPublicXml(InputStream inputStream) throws XmlPullParserException, IOException {
        loadPublicXml(XMLFactory.newPullParser(inputStream));
    }

    public void loadPublicXml(Reader reader) throws XmlPullParserException, IOException {
        loadPublicXml(XMLFactory.newPullParser(reader));
    }

    public void loadPublicXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        boolean zEquals = false;
        while (true) {
            int iNextToken = xmlPullParser.nextToken();
            if (iNextToken == 1) {
                closeParser(xmlPullParser);
                return;
            } else if (iNextToken == 2) {
                if (!zEquals) {
                    zEquals = xmlPullParser.getName().equals(XmlHelper.RESOURCES_TAG);
                    if (!zEquals) {
                        throw new XmlPullParserException("Invalid public.xml, expecting first tag '" + getName() + "' " + xmlPullParser.getPositionDescription());
                    }
                    loadPackageInfo(xmlPullParser);
                } else {
                    parseEntry(xmlPullParser);
                }
            }
        }
    }

    private void closeParser(XmlPullParser xmlPullParser) {
        if (xmlPullParser instanceof Closeable) {
            try {
                ((Closeable) xmlPullParser).close();
            } catch (IOException unused) {
            }
        }
    }

    private void closeSerializer(XmlSerializer xmlSerializer) {
        if (xmlSerializer instanceof Closeable) {
            try {
                ((Closeable) xmlSerializer).close();
            } catch (IOException unused) {
            }
        }
    }

    private void loadPackageInfo(XmlPullParser xmlPullParser) {
        int iIntValue;
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if ("package".equals(xmlPullParser.getAttributeName(i))) {
                setName(xmlPullParser.getAttributeValue(i));
            } else if ("id".equals(xmlPullParser.getAttributeName(i)) && (iIntValue = Integer.decode(xmlPullParser.getAttributeValue(i)).intValue()) != 0) {
                setId(iIntValue);
            }
        }
    }

    private void parseEntry(XmlPullParser xmlPullParser) throws XmlPullParserException {
        if (!"public".equals(xmlPullParser.getName())) {
            throw new XmlPullParserException("Invalid tag, expecting 'public' " + xmlPullParser.getPositionDescription());
        }
        int attributeCount = xmlPullParser.getAttributeCount();
        String str = null;
        String str2 = null;
        String str3 = null;
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if ("id".equals(attributeName)) {
                str3 = attributeValue;
            } else if (ModuleXmlParser.TYPE.equals(attributeName)) {
                str = attributeValue;
            } else if ("name".equals(attributeName)) {
                str2 = attributeValue;
            }
        }
        if (str == null) {
            throw new XmlPullParserException("Missing attribute 'type' " + xmlPullParser.getPositionDescription());
        }
        if (str3 == null) {
            throw new XmlPullParserException("Missing attribute 'id' " + xmlPullParser.getPositionDescription());
        }
        if (str2 == null) {
            throw new XmlPullParserException("Missing attribute 'name' " + xmlPullParser.getPositionDescription());
        }
        int iLongValue = (int) Long.decode(str3).longValue();
        int i2 = (iLongValue >> 24) & 255;
        getOrCreate((iLongValue >> 16) & 255, str).add(new ResourceIdentifier(iLongValue & 65535, str2));
        if (getId() == 0) {
            setId(i2);
        }
    }

    public PackageBlock getPackageBlock() {
        return this.mPackageBlock;
    }

    public void setPackageBlock(PackageBlock packageBlock) {
        this.mPackageBlock = packageBlock;
    }

    public void add(ResourceEntry resourceEntry) {
        add(resourceEntry.get());
    }

    public void add(Entry entry) {
        if (entry == null || entry.isNull()) {
            return;
        }
        TypeBlock typeBlock = entry.getTypeBlock();
        getOrCreate(typeBlock.getId(), typeBlock.getTypeName()).add(new ResourceIdentifier(entry.getId(), entry.getName()));
    }

    private TypeIdentifier getOrCreate(int i, String str) {
        TypeIdentifier typeIdentifier = get(i);
        if (typeIdentifier == null) {
            return (TypeIdentifier) super.add(new TypeIdentifier(i, str));
        }
        if (str == null || typeIdentifier.getName() != null) {
            return typeIdentifier;
        }
        typeIdentifier.setName(str);
        return (TypeIdentifier) super.add(typeIdentifier);
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public void clear() {
        Iterator it = getItems().iterator();
        while (it.hasNext()) {
            ((TypeIdentifier) it.next()).clear();
        }
        super.clear();
    }

    public int renameSpecs() {
        Iterator it = getItems().iterator();
        int iRenameSpecs = 0;
        while (it.hasNext()) {
            iRenameSpecs += ((TypeIdentifier) it.next()).renameSpecs();
        }
        return iRenameSpecs;
    }

    public int renameDuplicateSpecs() {
        Iterator it = getItems().iterator();
        int iRenameDuplicateSpecs = 0;
        while (it.hasNext()) {
            iRenameDuplicateSpecs += ((TypeIdentifier) it.next()).renameDuplicateSpecs();
        }
        return iRenameDuplicateSpecs;
    }

    public int renameBadSpecs() {
        Iterator it = getItems().iterator();
        int iRenameBadSpecs = 0;
        while (it.hasNext()) {
            iRenameBadSpecs += ((TypeIdentifier) it.next()).renameBadSpecs();
        }
        return iRenameBadSpecs;
    }

    public String validateSpecNames() {
        int iRenameDuplicateSpecs = renameDuplicateSpecs();
        int iRenameBadSpecs = renameBadSpecs();
        if (iRenameDuplicateSpecs == 0 && iRenameBadSpecs == 0) {
            return null;
        }
        return "Spec names validated, duplicates = " + iRenameDuplicateSpecs + ", bad = " + iRenameBadSpecs;
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    void setCaseInsensitive(boolean z) {
        super.setCaseInsensitive(z);
        Iterator it = getItems().iterator();
        while (it.hasNext()) {
            ((TypeIdentifier) it.next()).setCaseInsensitive(z);
        }
    }
}
