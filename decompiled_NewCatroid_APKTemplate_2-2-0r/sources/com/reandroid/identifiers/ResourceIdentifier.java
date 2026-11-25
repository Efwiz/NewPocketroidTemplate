package com.reandroid.identifiers;

import com.reandroid.apkeditor.refactor.RefactorUtil;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.utils.HexUtil;
import java.io.IOException;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class ResourceIdentifier extends Identifier {
    public static final int NAME_LENGTH_MAX = 100;
    public static final int NAME_LENGTH_MIN = 1;
    private Boolean mHasGoodName;

    private static boolean isAtoZ(char c) {
        if (c < 'A' || c > 'Z') {
            return c >= 'a' && c <= 'z';
        }
        return true;
    }

    private static boolean isDigits(char c) {
        return c >= '0' && c <= '9';
    }

    public ResourceIdentifier(int i, String str) {
        super(i, str);
    }

    public ResourceIdentifier() {
        this(0, null);
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.text("\n  ");
        xmlSerializer.startTag(null, "public");
        xmlSerializer.attribute(null, "id", getHexId());
        xmlSerializer.attribute(null, ModuleXmlParser.TYPE, getTypeName());
        xmlSerializer.attribute(null, "name", getName());
        xmlSerializer.endTag(null, "public");
    }

    public TypeIdentifier getTypeIdentifier() {
        return (TypeIdentifier) getParent();
    }

    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        setParent(typeIdentifier);
    }

    public PackageIdentifier getPackageIdentifier() {
        TypeIdentifier typeIdentifier = getTypeIdentifier();
        if (typeIdentifier != null) {
            return typeIdentifier.getPackageIdentifier();
        }
        return null;
    }

    public String getTypeName() {
        TypeIdentifier typeIdentifier = getTypeIdentifier();
        if (typeIdentifier != null) {
            return typeIdentifier.getName();
        }
        return null;
    }

    public String getPackageName() {
        TypeIdentifier typeIdentifier = getTypeIdentifier();
        if (typeIdentifier != null) {
            return typeIdentifier.getPackageName();
        }
        return null;
    }

    public int getTypeId() {
        TypeIdentifier typeIdentifier = getTypeIdentifier();
        if (typeIdentifier != null) {
            return typeIdentifier.getId();
        }
        return 0;
    }

    public int getPackageId() {
        TypeIdentifier typeIdentifier = getTypeIdentifier();
        if (typeIdentifier != null) {
            return typeIdentifier.getPackageId();
        }
        return 0;
    }

    public int getResourceId() {
        return (getPackageId() << 24) | (getTypeId() << 16) | getId();
    }

    @Override // com.reandroid.identifiers.Identifier
    public void setId(int i) {
        super.setId(i & 65535);
    }

    @Override // com.reandroid.identifiers.Identifier
    public String getHexId() {
        return HexUtil.toHex8(getResourceId());
    }

    public String getResourceName() {
        return getResourceName(null);
    }

    public String getResourceName(PackageIdentifier packageIdentifier) {
        return getResourceName('@', packageIdentifier != getPackageIdentifier(), true);
    }

    public String getResourceName(char c, boolean z, boolean z2) {
        return buildResourceName(c, z ? getPackageName() : null, z2 ? getTypeName() : null, getName());
    }

    @Override // com.reandroid.identifiers.Identifier
    public void setName(String str) {
        super.setName(str);
        this.mHasGoodName = null;
    }

    @Override // com.reandroid.identifiers.Identifier
    long getUniqueId() {
        return ((long) getResourceId()) & 4294967295L;
    }

    @Override // com.reandroid.identifiers.Identifier
    public void setTag(Object obj) {
        TypeIdentifier typeIdentifier = getTypeIdentifier();
        if (typeIdentifier == null) {
            super.setTag(obj);
            return;
        }
        Object tag = getTag();
        if (tag != null) {
            typeIdentifier.removeTag(tag);
        }
        typeIdentifier.addTag(obj, this);
        super.setTag(obj);
    }

    public String generateUniqueName() {
        String typeName = getTypeName();
        if (typeName == null) {
            typeName = RefactorUtil.RES_DIR;
        }
        return typeName + "_" + getHexId();
    }

    public boolean isGeneratedName() {
        String name2 = getName();
        if (name2 != null && name2.contains("_0x")) {
            return generateUniqueName().equals(name2);
        }
        return false;
    }

    public boolean renameSpecGenerated() {
        setName(generateUniqueName());
        return renameSpec();
    }

    public boolean renameBadSpec() {
        if (hasGoodName()) {
            return renameDollarPrefix();
        }
        setName(generateUniqueName());
        return renameSpec();
    }

    public boolean renameDollarPrefix() {
        if (!Identifier.isAapt()) {
            return false;
        }
        String name2 = getName();
        if (name2.charAt(0) != '$') {
            return false;
        }
        setName(name2.substring(1));
        return renameSpec();
    }

    public boolean renameSpec() {
        ResourceEntry resource;
        String name2;
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock == null || (resource = packageBlock.getResource(getResourceId())) == null || (name2 = getName()) == null || name2.equals(resource.getName())) {
            return false;
        }
        resource.setName(name2);
        return true;
    }

    private PackageBlock getPackageBlock() {
        PackageIdentifier packageIdentifier = getPackageIdentifier();
        if (packageIdentifier != null) {
            return packageIdentifier.getPackageBlock();
        }
        return null;
    }

    public boolean hasGoodName() {
        if (this.mHasGoodName == null) {
            this.mHasGoodName = Boolean.valueOf(isGoodName(getName()));
        }
        return this.mHasGoodName.booleanValue();
    }

    @Override // com.reandroid.identifiers.Identifier
    public String toString() {
        return getHexId() + " " + getResourceName();
    }

    public static String buildResourceName(char c, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        if (c != 0) {
            sb.append(c);
        }
        if (str != null) {
            sb.append(str);
            sb.append(':');
        }
        if (str2 != null) {
            sb.append(str2);
            sb.append('/');
        }
        sb.append(str3);
        return sb.toString();
    }

    public static boolean isGoodName(String str) {
        int length;
        if (str == null || (length = str.length()) < 1 || length > 100) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (!isGoodFirstChar(charArray[0])) {
            return false;
        }
        int length2 = charArray.length;
        for (int i = 1; i < length2; i++) {
            if (!isGoodNameChar(charArray[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean isGoodNameChar(char c) {
        return isAtoZ(c) || isDigits(c) || c == '_' || c == '.' || c == '$' || c == '-';
    }

    private static boolean isGoodFirstChar(char c) {
        return isAtoZ(c) || c == '_' || c == '$';
    }
}
