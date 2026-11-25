package com.reandroid.arsc.chunk.xml;

import com.huawei.hms.framework.common.ContainerUtils;
import com.reandroid.app.AndroidManifest;
import com.reandroid.arsc.ApkFile;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.FilterIterator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class AndroidManifestBlock extends ResXmlDocument implements AndroidManifest {
    private int mGuessedPackageId;

    public AndroidManifestBlock() {
        super.getStringPool().setUtf8(false);
    }

    public ApkFile.ApkType guessApkType() {
        if (isSplit()) {
            return ApkFile.ApkType.SPLIT;
        }
        Boolean boolIsCoreApp = isCoreApp();
        if (boolIsCoreApp != null && boolIsCoreApp.booleanValue()) {
            return ApkFile.ApkType.CORE;
        }
        if (getMainActivity() != null) {
            return ApkFile.ApkType.BASE;
        }
        return null;
    }

    public Boolean isCoreApp() {
        ResXmlAttribute resXmlAttributeSearchAttributeByName;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null || (resXmlAttributeSearchAttributeByName = manifestElement.searchAttributeByName(NAME_coreApp)) == null || resXmlAttributeSearchAttributeByName.getValueType() != ValueType.BOOLEAN) {
            return null;
        }
        return Boolean.valueOf(resXmlAttributeSearchAttributeByName.getValueAsBoolean());
    }

    public boolean isSplit() {
        ResXmlElement manifestElement = getManifestElement();
        return (manifestElement == null || manifestElement.searchAttributeByName(NAME_split) == null) ? false : true;
    }

    public String getSplit() {
        ResXmlAttribute resXmlAttributeSearchAttributeByName;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null || (resXmlAttributeSearchAttributeByName = manifestElement.searchAttributeByName(NAME_split)) == null) {
            return null;
        }
        return resXmlAttributeSearchAttributeByName.getValueAsString();
    }

    public void setSplit(String str, boolean z) {
        ResXmlAttribute resXmlAttributeSearchAttributeByName;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null) {
            return;
        }
        if (z) {
            resXmlAttributeSearchAttributeByName = manifestElement.getOrCreateAttribute(NAME_split, 0);
        } else {
            resXmlAttributeSearchAttributeByName = manifestElement.searchAttributeByName(NAME_split);
            if (resXmlAttributeSearchAttributeByName == null) {
                return;
            }
        }
        resXmlAttributeSearchAttributeByName.setValueAsString(str);
    }

    public int guessCurrentPackageId() {
        if (this.mGuessedPackageId == 0) {
            this.mGuessedPackageId = (getIconResourceId() >> 24) & 255;
        }
        return this.mGuessedPackageId;
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlDocument
    PackageBlock selectPackageBlock(TableBlock tableBlock) {
        ResourceEntry resource = tableBlock.getResource(getIconResourceId());
        if (resource == null) {
            return super.selectPackageBlock(tableBlock);
        }
        PackageBlock packageBlock = resource.getPackageBlock();
        return packageBlock.getTableBlock() != tableBlock ? super.selectPackageBlock(tableBlock) : packageBlock;
    }

    public int getIconResourceId() {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null || (resXmlAttributeSearchAttributeByResourceId = applicationElement.searchAttributeByResourceId(ID_icon)) == null || resXmlAttributeSearchAttributeByResourceId.getValueType() != ValueType.REFERENCE) {
            return 0;
        }
        return resXmlAttributeSearchAttributeByResourceId.getData();
    }

    public void setIconResourceId(int i) {
        getOrCreateApplicationElement().getOrCreateAndroidAttribute(NAME_icon, ID_icon).setTypeAndData(ValueType.REFERENCE, i);
    }

    public int getRoundIconResourceId() {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null || (resXmlAttributeSearchAttributeByResourceId = applicationElement.searchAttributeByResourceId(ID_roundIcon)) == null || resXmlAttributeSearchAttributeByResourceId.getValueType() != ValueType.REFERENCE) {
            return 0;
        }
        return resXmlAttributeSearchAttributeByResourceId.getData();
    }

    public void setRoundIconResourceId(int i) {
        getOrCreateApplicationElement().getOrCreateAndroidAttribute(NAME_icon, ID_roundIcon).setTypeAndData(ValueType.REFERENCE, i);
    }

    public Integer getApplicationLabelReference() {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null || (resXmlAttributeSearchAttributeByResourceId = applicationElement.searchAttributeByResourceId(ID_label)) == null || resXmlAttributeSearchAttributeByResourceId.getValueType() != ValueType.REFERENCE) {
            return null;
        }
        return Integer.valueOf(resXmlAttributeSearchAttributeByResourceId.getData());
    }

    public void setApplicationLabel(int i) {
        getOrCreateApplicationElement().getOrCreateAndroidAttribute(NAME_label, ID_label).setTypeAndData(ValueType.REFERENCE, i);
    }

    public String getApplicationLabelString() {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null || (resXmlAttributeSearchAttributeByResourceId = applicationElement.searchAttributeByResourceId(ID_label)) == null || resXmlAttributeSearchAttributeByResourceId.getValueType() != ValueType.STRING) {
            return null;
        }
        return resXmlAttributeSearchAttributeByResourceId.getValueAsString();
    }

    public void setApplicationLabel(String str) {
        getOrCreateApplicationElement().getOrCreateAndroidAttribute(NAME_label, ID_label).setValueAsString(str);
    }

    public boolean isDebuggable() {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null || (resXmlAttributeSearchAttributeByResourceId = applicationElement.searchAttributeByResourceId(ID_debuggable)) == null) {
            return false;
        }
        return resXmlAttributeSearchAttributeByResourceId.getValueAsBoolean();
    }

    public void setDebuggable(boolean z) {
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null) {
            return;
        }
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = applicationElement.searchAttributeByResourceId(ID_debuggable);
        if (z) {
            if (resXmlAttributeSearchAttributeByResourceId == null) {
                resXmlAttributeSearchAttributeByResourceId = applicationElement.createAndroidAttribute(NAME_debuggable, ID_debuggable);
            }
            resXmlAttributeSearchAttributeByResourceId.setValueAsBoolean(true);
        } else if (resXmlAttributeSearchAttributeByResourceId != null) {
            applicationElement.removeAttribute(resXmlAttributeSearchAttributeByResourceId);
        }
    }

    public ResXmlElement getMainActivity() {
        for (ResXmlElement resXmlElement : listActivities()) {
            Iterator<ResXmlElement> it = resXmlElement.listElements(TAG_intent_filter).iterator();
            while (it.hasNext()) {
                Iterator<ResXmlElement> it2 = it.next().listElements(TAG_action).iterator();
                while (it2.hasNext()) {
                    ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = it2.next().searchAttributeByResourceId(ID_name);
                    if (resXmlAttributeSearchAttributeByResourceId != null && VALUE_android_intent_action_MAIN.equals(resXmlAttributeSearchAttributeByResourceId.getValueAsString())) {
                        return resXmlElement;
                    }
                }
            }
        }
        return null;
    }

    public ResXmlElement getOrCreateMainActivity(String str) {
        ResXmlElement mainActivity = getMainActivity();
        if (mainActivity == null) {
            mainActivity = getOrCreateApplicationElement().createChildElement(0, TAG_activity);
            ResXmlElement resXmlElementCreateChildElement = mainActivity.createChildElement(TAG_intent_filter);
            resXmlElementCreateChildElement.createChildElement(TAG_action).getOrCreateAndroidAttribute(NAME_name, ID_name).setValueAsString(VALUE_android_intent_action_MAIN);
            resXmlElementCreateChildElement.createChildElement(TAG_category).getOrCreateAndroidAttribute(NAME_name, ID_name).setValueAsString("android.intent.category.DEFAULT");
            resXmlElementCreateChildElement.createChildElement(TAG_category).getOrCreateAndroidAttribute(NAME_name, ID_name).setValueAsString("android.intent.category.LAUNCHER");
        }
        mainActivity.getOrCreateAndroidAttribute(NAME_name, ID_name).setValueAsString(str);
        return mainActivity;
    }

    public ResXmlElement getOrCreateActivity(String str, boolean z) {
        ResXmlElement activity = getActivity(str, z);
        if (activity != null) {
            return activity;
        }
        ResXmlElement resXmlElementCreateChildElement = getOrCreateApplicationElement().createChildElement(z ? TAG_activity_alias : TAG_activity);
        resXmlElementCreateChildElement.createAndroidAttribute(NAME_name, ID_name).setValueAsString(str);
        return resXmlElementCreateChildElement;
    }

    public ResXmlElement getActivity(String str, boolean z) {
        String strFullClassName = fullClassName(str);
        for (ResXmlElement resXmlElement : listActivities(z)) {
            ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = resXmlElement.searchAttributeByResourceId(ID_name);
            if (resXmlAttributeSearchAttributeByResourceId != null && strFullClassName.equals(fullClassName(resXmlAttributeSearchAttributeByResourceId.getValueAsString()))) {
                return resXmlElement;
            }
        }
        return null;
    }

    public List<ResXmlElement> listActivities() {
        return listActivities(true);
    }

    public List<ResXmlElement> listActivities(boolean z) {
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null) {
            return new ArrayCollection();
        }
        List<ResXmlElement> listListElements = applicationElement.listElements(TAG_activity);
        if (z && !listListElements.isEmpty()) {
            listListElements.addAll(applicationElement.listElements(TAG_activity_alias));
        }
        return listListElements;
    }

    public List<ResXmlElement> listApplicationElementsByTag(String str) {
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null) {
            return new ArrayCollection();
        }
        return applicationElement.listElements(str);
    }

    public List<String> getUsesPermissions() {
        String valueAsString;
        ArrayCollection arrayCollection = new ArrayCollection();
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null) {
            return arrayCollection;
        }
        Iterator<ResXmlElement> it = manifestElement.listElements(TAG_uses_permission).iterator();
        while (it.hasNext()) {
            ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = it.next().searchAttributeByResourceId(ID_name);
            if (resXmlAttributeSearchAttributeByResourceId != null && resXmlAttributeSearchAttributeByResourceId.getValueType() == ValueType.STRING && (valueAsString = resXmlAttributeSearchAttributeByResourceId.getValueAsString()) != null) {
                arrayCollection.add(valueAsString);
            }
        }
        return arrayCollection;
    }

    public ResXmlElement getUsesPermission(String str) {
        String valueAsString;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null) {
            return null;
        }
        for (ResXmlElement resXmlElement : manifestElement.listElements(TAG_uses_permission)) {
            ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = resXmlElement.searchAttributeByResourceId(ID_name);
            if (resXmlAttributeSearchAttributeByResourceId != null && resXmlAttributeSearchAttributeByResourceId.getValueType() == ValueType.STRING && (valueAsString = resXmlAttributeSearchAttributeByResourceId.getValueAsString()) != null && valueAsString.equals(str)) {
                return resXmlElement;
            }
        }
        return null;
    }

    public ResXmlElement addUsesPermission(String str) {
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null) {
            return null;
        }
        ResXmlElement usesPermission = getUsesPermission(str);
        if (usesPermission != null) {
            return usesPermission;
        }
        int iLastIndexOf = manifestElement.lastIndexOf(TAG_uses_permission) + 1;
        ResXmlElement resXmlElementCreateChildElement = manifestElement.createChildElement(TAG_uses_permission);
        resXmlElementCreateChildElement.getOrCreateAndroidAttribute(NAME_name, ID_name).setValueAsString(str);
        manifestElement.changeIndex(resXmlElementCreateChildElement, iLastIndexOf);
        return resXmlElementCreateChildElement;
    }

    @Override // com.reandroid.app.AndroidManifest
    public String getPackageName() {
        ResXmlAttribute resXmlAttributeSearchAttributeByName;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null || (resXmlAttributeSearchAttributeByName = manifestElement.searchAttributeByName(NAME_PACKAGE)) == null || resXmlAttributeSearchAttributeByName.getValueType() != ValueType.STRING) {
            return null;
        }
        return resXmlAttributeSearchAttributeByName.getValueAsString();
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setPackageName(String str) {
        getOrCreateManifestElement().getOrCreateAttribute(NAME_PACKAGE, 0).setValueAsString(str);
    }

    @Override // com.reandroid.app.AndroidManifest
    public String getApplicationClassName() {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null || (resXmlAttributeSearchAttributeByResourceId = applicationElement.searchAttributeByResourceId(ID_name)) == null) {
            return null;
        }
        return fullClassName(resXmlAttributeSearchAttributeByResourceId.getValueAsString());
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setApplicationClassName(String str) {
        getOrCreateApplicationElement().getOrCreateAndroidAttribute(NAME_name, ID_name).setValueAsString(str);
    }

    @Override // com.reandroid.app.AndroidManifest
    public String getMainActivityClassName() {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement mainActivity = getMainActivity();
        if (mainActivity == null || (resXmlAttributeSearchAttributeByResourceId = mainActivity.searchAttributeByResourceId(ID_name)) == null) {
            return null;
        }
        return fullClassName(resXmlAttributeSearchAttributeByResourceId.getValueAsString());
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setMainActivityClassName(String str) {
        getOrCreateMainActivity(str);
    }

    @Override // com.reandroid.app.AndroidManifest
    public Integer getVersionCode() {
        return getManifestAttributeInt(ID_versionCode);
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setVersionCode(int i) {
        setManifestAttributeInt(NAME_versionCode, ID_versionCode, i);
    }

    @Override // com.reandroid.app.AndroidManifest
    public String getVersionName() {
        return getManifestAttributeString(ID_versionName);
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setVersionName(String str) {
        setManifestAttributeString(NAME_versionName, ID_versionName, str);
    }

    @Override // com.reandroid.app.AndroidManifest
    public Integer getPlatformBuildVersionCode() {
        ResXmlAttribute resXmlAttributeSearchAttributeByName;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null || (resXmlAttributeSearchAttributeByName = manifestElement.searchAttributeByName(NAME_platformBuildVersionCode)) == null || resXmlAttributeSearchAttributeByName.getValueType() != ValueType.DEC) {
            return null;
        }
        return Integer.valueOf(resXmlAttributeSearchAttributeByName.getData());
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setPlatformBuildVersionCode(int i) {
        setManifestAttributeInt(NAME_platformBuildVersionCode, 0, i);
    }

    @Override // com.reandroid.app.AndroidManifest
    public Object getPlatformBuildVersionName() {
        ResXmlAttribute resXmlAttributeSearchAttributeByName;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null || (resXmlAttributeSearchAttributeByName = manifestElement.searchAttributeByName(NAME_platformBuildVersionName)) == null) {
            return null;
        }
        if (resXmlAttributeSearchAttributeByName.getValueType() == ValueType.STRING) {
            return resXmlAttributeSearchAttributeByName.getValueAsString();
        }
        return Integer.valueOf(resXmlAttributeSearchAttributeByName.getData());
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setPlatformBuildVersionName(Object obj) {
        Integer numValueOf;
        if (obj instanceof Integer) {
            numValueOf = (Integer) obj;
        } else {
            try {
                numValueOf = Integer.valueOf(Integer.parseInt((String) obj));
            } catch (NumberFormatException unused) {
                numValueOf = null;
            }
        }
        if (numValueOf != null) {
            setManifestAttributeInt(NAME_platformBuildVersionName, 0, numValueOf.intValue());
        } else {
            setManifestAttributeString(NAME_platformBuildVersionName, 0, (String) obj);
        }
    }

    @Override // com.reandroid.app.AndroidManifest
    public Integer getMinSdkVersion() {
        ResXmlElement element;
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null || (element = manifestElement.getElement(TAG_uses_sdk)) == null || (resXmlAttributeSearchAttributeByResourceId = element.searchAttributeByResourceId(ID_minSdkVersion)) == null || resXmlAttributeSearchAttributeByResourceId.getValueType() != ValueType.DEC) {
            return null;
        }
        return Integer.valueOf(resXmlAttributeSearchAttributeByResourceId.getData());
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setMinSdkVersion(int i) {
        ResXmlElement orCreateManifestElement = getOrCreateManifestElement();
        ResXmlElement element = orCreateManifestElement.getElement(TAG_uses_sdk);
        if (element == null) {
            element = orCreateManifestElement.createChildElement(TAG_uses_sdk);
        }
        element.getOrCreateAndroidAttribute(NAME_minSdkVersion, ID_minSdkVersion).setTypeAndData(ValueType.DEC, i);
    }

    @Override // com.reandroid.app.AndroidManifest
    public Integer getTargetSdkVersion() {
        ResXmlElement element;
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null || (element = manifestElement.getElement(TAG_uses_sdk)) == null || (resXmlAttributeSearchAttributeByResourceId = element.searchAttributeByResourceId(ID_targetSdkVersion)) == null || resXmlAttributeSearchAttributeByResourceId.getValueType() != ValueType.DEC) {
            return null;
        }
        return Integer.valueOf(resXmlAttributeSearchAttributeByResourceId.getData());
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setTargetSdkVersion(int i) {
        ResXmlElement orCreateManifestElement = getOrCreateManifestElement();
        ResXmlElement element = orCreateManifestElement.getElement(TAG_uses_sdk);
        if (element == null) {
            element = orCreateManifestElement.createChildElement(TAG_uses_sdk);
        }
        element.getOrCreateAndroidAttribute(NAME_targetSdkVersion, ID_targetSdkVersion).setTypeAndData(ValueType.DEC, i);
    }

    @Override // com.reandroid.app.AndroidManifest
    public Integer getCompileSdkVersion() {
        return getManifestAttributeInt(ID_compileSdkVersion);
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setCompileSdkVersion(int i) {
        setManifestAttributeInt(NAME_compileSdkVersion, ID_compileSdkVersion, i);
    }

    @Override // com.reandroid.app.AndroidManifest
    public String getCompileSdkVersionCodename() {
        return getManifestAttributeString(ID_compileSdkVersionCodename);
    }

    @Override // com.reandroid.app.AndroidManifest
    public void setCompileSdkVersionCodename(String str) {
        setManifestAttributeString(NAME_compileSdkVersionCodename, ID_compileSdkVersionCodename, str);
    }

    private String getManifestAttributeString(int i) {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null || (resXmlAttributeSearchAttributeByResourceId = manifestElement.searchAttributeByResourceId(i)) == null || resXmlAttributeSearchAttributeByResourceId.getValueType() != ValueType.STRING) {
            return null;
        }
        return resXmlAttributeSearchAttributeByResourceId.getValueAsString();
    }

    private void setManifestAttributeString(String str, int i, String str2) {
        getOrCreateManifestElement().getOrCreateAndroidAttribute(str, i).setValueAsString(str2);
    }

    private void setManifestAttributeInt(String str, int i, int i2) {
        getOrCreateManifestElement().getOrCreateAndroidAttribute(str, i).setTypeAndData(ValueType.DEC, i2);
    }

    private Integer getManifestAttributeInt(int i) {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null || (resXmlAttributeSearchAttributeByResourceId = manifestElement.searchAttributeByResourceId(i)) == null || resXmlAttributeSearchAttributeByResourceId.getValueType() != ValueType.DEC) {
            return null;
        }
        return Integer.valueOf(resXmlAttributeSearchAttributeByResourceId.getData());
    }

    public ResXmlElement getOrCreateApplicationElement() {
        ResXmlElement orCreateManifestElement = getOrCreateManifestElement();
        ResXmlElement element = orCreateManifestElement.getElement(TAG_application);
        return element == null ? orCreateManifestElement.createChildElement(TAG_application) : element;
    }

    public ResXmlElement getApplicationElement() {
        ResXmlElement manifestElement = getManifestElement();
        if (manifestElement == null) {
            return null;
        }
        return manifestElement.getElement(TAG_application);
    }

    public ResXmlElement getManifestElement() {
        return getElement(AndroidManifest.TAG_manifest);
    }

    public void ensureFullClassNames() {
        ResXmlElement applicationElement = getApplicationElement();
        if (applicationElement == null) {
            return;
        }
        Iterator<ResXmlAttribute> itRecursiveAttributes = applicationElement.recursiveAttributes();
        while (itRecursiveAttributes.hasNext()) {
            ResXmlAttribute next = itRecursiveAttributes.next();
            if (next.getNameId() == ID_name && next.getValueType() == ValueType.STRING) {
                next.setValueAsString(fullClassName(next.getValueAsString()));
            }
        }
        applicationElement.refresh();
    }

    public String fullClassName(String str) {
        String packageName;
        return (str == null || str.length() == 0 || str.charAt(0) != '.' || (packageName = getPackageName()) == null) ? str : packageName + str;
    }

    private ResXmlElement getOrCreateManifestElement() {
        return getOrCreateElement(AndroidManifest.TAG_manifest);
    }

    public Iterator<ResXmlElement> getAndroidNameElements(final String str, final String str2) {
        return FilterIterator.of(recursiveElements(), new Predicate() { // from class: com.reandroid.arsc.chunk.xml.AndroidManifestBlock$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AndroidManifestBlock.lambda$getAndroidNameElements$0(str, str2, (ResXmlElement) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$getAndroidNameElements$0(String str, String str2, ResXmlElement resXmlElement) {
        return resXmlElement.equalsName(str) && str2.equals(getAndroidNameValue(resXmlElement));
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append(NAME_PACKAGE).append(ContainerUtils.KEY_VALUE_DELIMITER).append(getPackageName());
        sb.append(", ").append(NAME_versionCode).append(ContainerUtils.KEY_VALUE_DELIMITER).append(getVersionCode());
        sb.append(", ").append(NAME_versionName).append(ContainerUtils.KEY_VALUE_DELIMITER).append(getVersionName());
        sb.append(", ").append(NAME_compileSdkVersion).append(ContainerUtils.KEY_VALUE_DELIMITER).append(getCompileSdkVersion());
        sb.append(", ").append(NAME_compileSdkVersionCodename).append(ContainerUtils.KEY_VALUE_DELIMITER).append(getCompileSdkVersionCodename());
        List<String> usesPermissions = getUsesPermissions();
        sb.append(", PERMISSIONS[");
        boolean z = false;
        for (String str : usesPermissions) {
            if (z) {
                sb.append(", ");
            }
            sb.append(str);
            z = true;
        }
        sb.append("]}");
        return sb.toString();
    }

    public static String getAndroidNameValue(ResXmlElement resXmlElement) {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = resXmlElement.searchAttributeByResourceId(ID_name);
        if (resXmlAttributeSearchAttributeByResourceId != null) {
            return resXmlAttributeSearchAttributeByResourceId.getValueAsString();
        }
        return null;
    }

    public static boolean isAndroidManifestBlock(ResXmlDocument resXmlDocument) {
        return (resXmlDocument == null || resXmlDocument.getElement(AndroidManifest.TAG_manifest) == null) ? false : true;
    }

    public static AndroidManifestBlock load(File file) throws IOException {
        AndroidManifestBlock androidManifestBlock = new AndroidManifestBlock();
        androidManifestBlock.readBytes(new BlockReader(file));
        return androidManifestBlock;
    }

    public static AndroidManifestBlock load(InputStream inputStream) throws IOException {
        AndroidManifestBlock androidManifestBlock = new AndroidManifestBlock();
        androidManifestBlock.readBytes(inputStream);
        return androidManifestBlock;
    }

    public static AndroidManifestBlock empty() {
        AndroidManifestBlock androidManifestBlock = new AndroidManifestBlock();
        androidManifestBlock.getOrCreateElement(EMPTY_MANIFEST_TAG);
        return androidManifestBlock;
    }
}
