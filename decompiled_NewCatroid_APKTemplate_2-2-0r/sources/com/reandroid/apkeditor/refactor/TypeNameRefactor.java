package com.reandroid.apkeditor.refactor;

import android.R;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.apk.APKLogger;
import com.reandroid.apk.AndroidFrameworks;
import com.reandroid.apk.ApkModule;
import com.reandroid.apk.FrameworkApk;
import com.reandroid.apk.ResFile;
import com.reandroid.apk.UncompressedFiles;
import com.reandroid.app.AndroidManifest;
import com.reandroid.arsc.array.ResValueMapArray;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.chunk.xml.ResXmlElement;
import com.reandroid.arsc.item.TypeString;
import com.reandroid.arsc.model.FrameworkTable;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.AttributeDataFormat;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResTableMapEntry;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.arsc.value.array.ArrayBag;
import com.reandroid.arsc.value.attribute.AttributeBag;
import com.reandroid.arsc.value.plurals.PluralsBag;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.xml.XMLDocument;
import com.reandroid.xml.XMLElement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: loaded from: classes10.dex */
public class TypeNameRefactor {
    private APKLogger apkLogger;
    private final ApkModule apkModule;
    private Map<Integer, TypeString> mTypeStrings;
    private final TypeNameMap refactoredTypeMap = new TypeNameMap();

    public TypeNameRefactor(ApkModule apkModule) {
        this.apkModule = apkModule;
    }

    public void setApkLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    public void refactor() throws IOException {
        logMessage("Refactoring types ...");
        loadTypeStrings(this.apkModule.getTableBlock());
        logMessage("Refactoring from AndroidManifest ...");
        scanXml(this.apkModule.getAndroidManifest(), 0);
        scanResFiles();
        if (!isFinished()) {
            scanTableEntries(this.apkModule.getTableBlock());
        }
        logFinished();
    }

    private void logFinished() {
        StringBuilder sb = new StringBuilder("Finished type rename=");
        sb.append(this.refactoredTypeMap.count());
        if (this.refactoredTypeMap.count() > 0) {
            sb.append("\n");
            XMLElement documentElement = this.refactoredTypeMap.toXMLDocument().getDocumentElement();
            documentElement.setName("renamed");
            documentElement.setAttribute("count", String.valueOf(this.refactoredTypeMap.count()));
            sb.append(documentElement.getDebugText());
        }
        TypeNameMap typeNameMap = new TypeNameMap();
        for (Map.Entry<Integer, TypeString> entry : this.mTypeStrings.entrySet()) {
            typeNameMap.add(entry.getKey().intValue(), entry.getValue().get());
        }
        if (typeNameMap.count() > 0) {
            sb.append("\n");
            XMLDocument xMLDocument = typeNameMap.toXMLDocument();
            xMLDocument.getDocumentElement().setName("remain");
            sb.append(xMLDocument.getDebugText());
        }
        logMessage(sb.toString());
    }

    private void scanTableEntries(TableBlock tableBlock) {
        logMessage("Refactoring from TableBlock ...");
        for (PackageBlock packageBlock : tableBlock.listPackages()) {
            if (isFinished()) {
                return;
            } else {
                scanPackageEntries(packageBlock);
            }
        }
    }

    private void scanPackageEntries(PackageBlock packageBlock) {
        Iterator<ResourceEntry> resources = packageBlock.getResources();
        while (resources.hasNext() && !isFinished()) {
            checkEntryGroup(resources.next());
        }
    }

    private void checkEntryGroup(ResourceEntry resourceEntry) {
        if (hasRefactoredId(resourceEntry.getResourceId())) {
            return;
        }
        checkBag(resourceEntry);
    }

    private boolean checkBag(ResourceEntry resourceEntry) {
        if (!hasRefactoredName("style") || !hasRefactoredName(ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED)) {
            return false;
        }
        Iterator<Entry> it = resourceEntry.iterator(true);
        boolean z = false;
        while (it.hasNext()) {
            Entry next = it.next();
            if (!next.isComplex()) {
                return false;
            }
            ResTableMapEntry resTableMapEntry = (ResTableMapEntry) next.getTableEntry();
            if (checkPlurals(resTableMapEntry) || checkArray(resTableMapEntry)) {
                return true;
            }
            z = true;
        }
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean checkArray(ResTableMapEntry resTableMapEntry) {
        if (hasRefactoredName("array") || ((ResValueMapArray) resTableMapEntry.getValue()).size() < 2 || !ArrayBag.isArray(resTableMapEntry.getParentEntry())) {
            return false;
        }
        rename(resTableMapEntry.getParentEntry().getResourceId(), "array");
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean checkPlurals(ResTableMapEntry resTableMapEntry) {
        if (hasRefactoredName("plurals") || ((ResValueMapArray) resTableMapEntry.getValue()).size() < 2 || !PluralsBag.isPlurals(resTableMapEntry.getParentEntry())) {
            return false;
        }
        rename(resTableMapEntry.getParentEntry().getResourceId(), "plurals");
        return true;
    }

    private void scanResFiles() throws IOException {
        logMessage("Refactoring from resource files ...");
        for (ResFile resFile : this.apkModule.listResFiles()) {
            if (isFinished()) {
                return;
            }
            if (resFile.isBinaryXml()) {
                ResXmlDocument resXmlDocument = new ResXmlDocument();
                resXmlDocument.readBytes(resFile.getInputSource().openStream());
                scanXml(resXmlDocument, resFile.pickOne().getResourceId());
            }
        }
    }

    private void loadTypeStrings(TableBlock tableBlock) {
        this.mTypeStrings = new HashMap();
        for (PackageBlock packageBlock : tableBlock.listPackages()) {
            int id2 = packageBlock.getId();
            for (TypeString typeString : packageBlock.getTypeStringPool()) {
                this.mTypeStrings.put(Integer.valueOf((id2 << 24) | ((typeString.getId() & 255) << 16)), typeString);
            }
        }
    }

    private void scanXml(ResXmlDocument resXmlDocument, int i) {
        boolean z = resXmlDocument instanceof AndroidManifestBlock;
        if (z || i == 0 || hasRefactoredId(i) || !(checkLayout(resXmlDocument, i) || checkDrawable(resXmlDocument, i) || checkAnimator(resXmlDocument, i) || checkMenu(resXmlDocument, i) || checkXml(resXmlDocument, i) || checkAnim(resXmlDocument, i) || checkInterpolator(resXmlDocument, i))) {
            Iterator it = CollectionUtil.toList(resXmlDocument.recursiveAttributes()).iterator();
            while (it.hasNext()) {
                scanAttribute((ResXmlAttribute) it.next(), z);
            }
        }
    }

    private void scanAttribute(ResXmlAttribute resXmlAttribute, boolean z) {
        if (z) {
            if (checkString(resXmlAttribute)) {
                return;
            }
            checkStyle(resXmlAttribute);
            return;
        }
        boolean zCheckAttr = checkAttr(resXmlAttribute);
        if (hasRefactoredId(resXmlAttribute.getData())) {
            return;
        }
        if (!zCheckAttr) {
            zCheckAttr = checkId(resXmlAttribute);
        }
        if (!zCheckAttr) {
            zCheckAttr = checkDimen(resXmlAttribute);
        }
        if (!zCheckAttr) {
            zCheckAttr = checkInteger(resXmlAttribute);
        }
        if (!zCheckAttr) {
            zCheckAttr = checkColor(resXmlAttribute);
        }
        if (zCheckAttr) {
            return;
        }
        checkBool(resXmlAttribute);
    }

    private boolean checkInterpolator(ResXmlDocument resXmlDocument, int i) {
        ResXmlElement documentElement;
        if (hasRefactoredName("interpolator") || (documentElement = resXmlDocument.getDocumentElement()) == null) {
            return false;
        }
        String name2 = documentElement.getName();
        if ("pathInterpolator".equals(name2) || "linearInterpolator".equals(name2)) {
            return rename(i, "interpolator");
        }
        return false;
    }

    private boolean checkAnim(ResXmlDocument resXmlDocument, int i) {
        ResXmlElement documentElement;
        if (hasRefactoredName("anim") || !hasRefactoredName("animator") || (documentElement = resXmlDocument.getDocumentElement()) == null || !"alpha".equals(documentElement.getName()) || documentElement.searchAttributeByResourceId(R.attr.fromAlpha) == null) {
            return false;
        }
        return rename(i, "anim");
    }

    private boolean checkXml(ResXmlDocument resXmlDocument, int i) {
        ResXmlElement documentElement;
        if (hasRefactoredName("xml") || (documentElement = resXmlDocument.getDocumentElement()) == null || !isXml(documentElement)) {
            return false;
        }
        return rename(i, "xml");
    }

    private boolean checkMenu(ResXmlDocument resXmlDocument, int i) {
        ResXmlElement documentElement;
        if (hasRefactoredName(LineReader.MENU) || (documentElement = resXmlDocument.getDocumentElement()) == null || !LineReader.MENU.equals(documentElement.getName()) || documentElement.listElements("item").size() == 0) {
            return false;
        }
        return rename(i, LineReader.MENU);
    }

    private boolean checkAnimator(ResXmlDocument resXmlDocument, int i) {
        ResXmlElement documentElement;
        if (hasRefactoredName("animator") || (documentElement = resXmlDocument.getDocumentElement()) == null || !"selector".equals(documentElement.getName())) {
            return false;
        }
        boolean z = false;
        for (ResXmlElement resXmlElement : documentElement.listElements("item")) {
            if (resXmlElement.searchAttributeByResourceId(R.attr.state_enabled) != null) {
                z = resXmlElement.listElements("objectAnimator").size() > 0;
                if (z) {
                    break;
                }
            }
        }
        if (z) {
            return rename(i, "animator");
        }
        return false;
    }

    private boolean checkDrawable(ResXmlDocument resXmlDocument, int i) {
        ResXmlElement documentElement;
        if (hasRefactoredName("drawable") || (documentElement = resXmlDocument.getDocumentElement()) == null || !"vector".equals(documentElement.getName())) {
            return false;
        }
        Iterator<ResXmlElement> it = documentElement.listElements("path").iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (it.next().searchAttributeByResourceId(R.attr.pathData) != null) {
                z = true;
            }
        }
        if (z) {
            return rename(i, "drawable");
        }
        return false;
    }

    private boolean checkLayout(ResXmlDocument resXmlDocument, int i) {
        ResXmlElement documentElement;
        if (hasRefactoredName("layout") || (documentElement = resXmlDocument.getDocumentElement()) == null || !"LinearLayout".equals(documentElement.getName())) {
            return false;
        }
        return rename(i, "layout");
    }

    private boolean checkAttr(ResXmlAttribute resXmlAttribute) {
        if (hasRefactoredName(ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED)) {
            return false;
        }
        return rename(resXmlAttribute.getNameId(), ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED);
    }

    private boolean checkColor(ResXmlAttribute resXmlAttribute) {
        if (hasRefactoredName(TypedValues.Custom.S_COLOR) || !hasRefactoredName("drawable") || resXmlAttribute.getNameId() != 16842904) {
            return false;
        }
        if (resXmlAttribute.getValueType() != ValueType.REFERENCE) {
            return true;
        }
        rename(resXmlAttribute.getData(), TypedValues.Custom.S_COLOR);
        return true;
    }

    private boolean checkBool(ResXmlAttribute resXmlAttribute) {
        return checkWithAndroidAttribute("bool", resXmlAttribute, AttributeDataFormat.BOOL);
    }

    private boolean checkInteger(ResXmlAttribute resXmlAttribute) {
        return checkWithAndroidAttribute("integer", resXmlAttribute, AttributeDataFormat.INTEGER);
    }

    private boolean checkWithAndroidAttribute(String str, ResXmlAttribute resXmlAttribute, AttributeDataFormat attributeDataFormat) {
        int nameId;
        if (hasRefactoredName(str) || (nameId = resXmlAttribute.getNameId()) == 0) {
            return false;
        }
        if (resXmlAttribute.getValueType() != ValueType.REFERENCE) {
            return true;
        }
        if (!isEqualAndroidAttributeType(nameId, attributeDataFormat)) {
            return false;
        }
        rename(resXmlAttribute.getData(), str);
        return true;
    }

    private boolean checkDimen(ResXmlAttribute resXmlAttribute) {
        if (hasRefactoredName("dimen")) {
            return false;
        }
        int nameId = resXmlAttribute.getNameId();
        if (nameId != 16842996 && nameId != 16842997) {
            return false;
        }
        if (resXmlAttribute.getValueType() != ValueType.REFERENCE) {
            return true;
        }
        rename(resXmlAttribute.getData(), "dimen");
        return true;
    }

    private boolean checkId(ResXmlAttribute resXmlAttribute) {
        if (hasRefactoredName("id") || resXmlAttribute.getNameId() != AndroidManifest.ID_id) {
            return false;
        }
        if (resXmlAttribute.getValueType() != ValueType.REFERENCE) {
            return true;
        }
        rename(resXmlAttribute.getData(), "id");
        return true;
    }

    private boolean checkStyle(ResXmlAttribute resXmlAttribute) {
        if (hasRefactoredName("style") || resXmlAttribute.getNameId() != AndroidManifest.ID_theme) {
            return false;
        }
        if (resXmlAttribute.getValueType() != ValueType.REFERENCE) {
            return true;
        }
        rename(resXmlAttribute.getData(), "style");
        return true;
    }

    private boolean checkString(ResXmlAttribute resXmlAttribute) {
        if (hasRefactoredName("string") || resXmlAttribute.getNameId() != AndroidManifest.ID_label) {
            return false;
        }
        if (resXmlAttribute.getValueType() != ValueType.REFERENCE) {
            return true;
        }
        rename(resXmlAttribute.getData(), "string");
        return true;
    }

    private boolean isXml(ResXmlElement resXmlElement) {
        return isPaths(resXmlElement) || isPreferenceScreen(resXmlElement);
    }

    private boolean isPreferenceScreen(ResXmlElement resXmlElement) {
        if (!"PreferenceScreen".equals(resXmlElement.getName())) {
            return false;
        }
        Iterator<ResXmlElement> it = resXmlElement.listElements().iterator();
        while (it.hasNext()) {
            String name2 = it.next().getName();
            if ("PreferenceCategory".equals(name2) || "CheckBoxPreference".equals(name2)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPaths(ResXmlElement resXmlElement) {
        if (!UncompressedFiles.NAME_paths.equals(resXmlElement.getName())) {
            return false;
        }
        Iterator<ResXmlElement> it = resXmlElement.listElements().iterator();
        while (it.hasNext()) {
            String name2 = it.next().getName();
            if ("files-path".equals(name2) || "cache-path".equals(name2) || "external-path".equals(name2) || "root-path".equals(name2) || "external-files-path".equals(name2) || "external-cache-path".equals(name2)) {
                return true;
            }
        }
        return false;
    }

    private boolean rename(int i, String str) {
        TypeString typeString = getTypeString(i);
        if (typeString == null) {
            return false;
        }
        removeTypeString(i);
        addRefactored(i, str);
        if (str.equals(typeString.get())) {
            return true;
        }
        logMessage("Renamed: '" + typeString.get() + "' --> '" + str + FormatHelper.QUOTE);
        typeString.set(str);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean isEqualAndroidAttributeType(int i, AttributeDataFormat attributeDataFormat) {
        FrameworkTable tableBlock;
        ResourceEntry resource;
        Entry entry;
        AttributeBag attributeBagCreate;
        FrameworkApk current = AndroidFrameworks.getCurrent();
        if (current == null || (tableBlock = current.getTableBlock()) == null || (resource = tableBlock.getResource(i)) == null || (entry = resource.get()) == null || !entry.isComplex() || (attributeBagCreate = AttributeBag.create((ResValueMapArray) ((ResTableMapEntry) entry.getTableEntry()).getValue())) == null || attributeBagCreate.isFlag() || attributeBagCreate.isEnum()) {
            return false;
        }
        return attributeBagCreate.getFormat().isEqualType(attributeDataFormat);
    }

    private void addRefactored(int i, String str) {
        this.refactoredTypeMap.add(i, str);
    }

    private boolean isFinished() {
        return this.mTypeStrings.size() == 0;
    }

    private boolean hasRefactoredName(String str) {
        return this.refactoredTypeMap.contains(str);
    }

    private boolean hasRefactoredId(int i) {
        return this.refactoredTypeMap.contains(i);
    }

    private TypeString getTypeString(int i) {
        return this.mTypeStrings.get(Integer.valueOf(i & (-65536)));
    }

    private void removeTypeString(int i) {
        this.mTypeStrings.remove(Integer.valueOf(i & (-65536)));
    }

    private void logMessage(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(str);
        }
    }

    private void logVerbose(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logVerbose(str);
        }
    }
}
