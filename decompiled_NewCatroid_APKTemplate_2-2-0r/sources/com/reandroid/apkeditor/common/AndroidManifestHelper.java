package com.reandroid.apkeditor.common;

import com.reandroid.apk.APKLogger;
import com.reandroid.apk.ApkUtil;
import com.reandroid.app.AndroidManifest;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.chunk.xml.ResXmlElement;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.EmptyList;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;

/* JADX INFO: loaded from: classes10.dex */
public class AndroidManifestHelper {
    static final Predicate<ResXmlAttribute> NAME_FILTER = new Predicate() { // from class: com.reandroid.apkeditor.common.AndroidManifestHelper$$ExternalSyntheticLambda0
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            return AndroidManifestHelper.lambda$static$1((ResXmlAttribute) obj);
        }
    };

    public static List<ResXmlElement> listSplitRequired(ResXmlElement resXmlElement) {
        if (resXmlElement == null) {
            return EmptyList.of();
        }
        return CollectionUtil.toList(resXmlElement.getElements(new Predicate() { // from class: com.reandroid.apkeditor.common.AndroidManifestHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AndroidManifestHelper.lambda$listSplitRequired$0((ResXmlElement) obj);
            }
        }));
    }

    static /* synthetic */ boolean lambda$listSplitRequired$0(ResXmlElement resXmlElement) {
        ResXmlAttribute resXmlAttribute;
        String valueAsString;
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        String valueAsString2;
        if (!resXmlElement.equalsName(AndroidManifest.TAG_meta_data) || (resXmlAttribute = (ResXmlAttribute) CollectionUtil.getFirst(resXmlElement.getAttributes(NAME_FILTER))) == null || (valueAsString = resXmlAttribute.getValueAsString()) == null) {
            return false;
        }
        if (!valueAsString.equals("com.android.dynamic.apk.fused.modules") || (resXmlAttributeSearchAttributeByResourceId = resXmlElement.searchAttributeByResourceId(AndroidManifest.ID_value)) == null || (valueAsString2 = resXmlAttributeSearchAttributeByResourceId.getValueAsString()) == null || !valueAsString2.equals(ApkUtil.DEF_MODULE_NAME)) {
            return valueAsString.startsWith("com.android.vending.") || valueAsString.startsWith("com.android.stamp.");
        }
        return true;
    }

    public static void removeAttributeFromManifestByName(AndroidManifestBlock androidManifestBlock, String str, APKLogger aPKLogger) {
        ResXmlElement manifestElement = androidManifestBlock.getManifestElement();
        if (manifestElement == null) {
            if (aPKLogger != null) {
                aPKLogger.logMessage("WARN: AndroidManifest don't have <manifest>");
            }
        } else {
            if (manifestElement.removeAttributesWithName(str) <= 0 || aPKLogger == null) {
                return;
            }
            aPKLogger.logMessage("Removed-attribute : " + str);
        }
    }

    public static void removeAttributeFromManifestById(AndroidManifestBlock androidManifestBlock, int i, APKLogger aPKLogger) {
        ResXmlElement manifestElement = androidManifestBlock.getManifestElement();
        if (manifestElement == null) {
            if (aPKLogger != null) {
                aPKLogger.logMessage("WARN: AndroidManifest don't have <manifest>");
            }
        } else {
            if (manifestElement.removeAttributesWithId(i) <= 0 || aPKLogger == null) {
                return;
            }
            aPKLogger.logMessage("Removed-attribute : " + HexUtil.toHex8("@0x", i));
        }
    }

    public static void removeAttributeFromManifestAndApplication(AndroidManifestBlock androidManifestBlock, int i, APKLogger aPKLogger, String str) {
        if (i == 0) {
            return;
        }
        ResXmlElement manifestElement = androidManifestBlock.getManifestElement();
        if (manifestElement == null) {
            if (aPKLogger != null) {
                aPKLogger.logMessage("WARN: AndroidManifest don't have <manifest>");
                return;
            }
            return;
        }
        int iRemoveAttributesWithId = manifestElement.removeAttributesWithId(i);
        ResXmlElement element = manifestElement.getElement(AndroidManifest.TAG_application);
        if (iRemoveAttributesWithId > 1 && aPKLogger != null) {
            aPKLogger.logMessage("Duplicate attributes on <manifest> removed: " + HexUtil.toHex8(PsiLiteralUtil.HEX_PREFIX, i));
        }
        if (element == null) {
            return;
        }
        int iRemoveAttributesWithId2 = element.removeAttributesWithId(i);
        if (iRemoveAttributesWithId2 > 1 && aPKLogger != null) {
            aPKLogger.logMessage("Duplicate attributes on <application> removed: " + HexUtil.toHex8(PsiLiteralUtil.HEX_PREFIX, i));
        }
        if (iRemoveAttributesWithId2 <= 0 || aPKLogger == null) {
            return;
        }
        aPKLogger.logMessage("Removed-attribute " + (iRemoveAttributesWithId2 > 1 ? "(" + iRemoveAttributesWithId2 + "): " : ": ") + str);
    }

    @Deprecated
    public static int removeApplicationAttribute(AndroidManifestBlock androidManifestBlock, int i) {
        ResXmlElement applicationElement;
        if (i == 0 || (applicationElement = androidManifestBlock.getApplicationElement()) == null) {
            return 0;
        }
        return applicationElement.removeAttributesWithId(i);
    }

    public static String getNamedValue(ResXmlElement resXmlElement) {
        ResXmlAttribute resXmlAttribute = (ResXmlAttribute) CollectionUtil.getFirst(resXmlElement.getAttributes(new Predicate() { // from class: com.reandroid.apkeditor.common.AndroidManifestHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AndroidManifestHelper.isNameResourceId((ResXmlAttribute) obj);
            }
        }));
        if (resXmlAttribute == null) {
            return "<not name attribute>";
        }
        return resXmlAttribute.decodeValue();
    }

    static boolean isNameResourceId(ResXmlAttribute resXmlAttribute) {
        return resXmlAttribute.getNameId() == AndroidManifest.ID_name;
    }

    static /* synthetic */ boolean lambda$static$1(ResXmlAttribute resXmlAttribute) {
        return isNameResourceId(resXmlAttribute) && resXmlAttribute.getValueType() == ValueType.STRING;
    }
}
