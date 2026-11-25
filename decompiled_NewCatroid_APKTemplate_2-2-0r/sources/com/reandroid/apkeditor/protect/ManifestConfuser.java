package com.reandroid.apkeditor.protect;

import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.chunk.xml.ResXmlElement;
import com.reandroid.utils.collection.CollectionUtil;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes10.dex */
public class ManifestConfuser extends Confuser {
    public ManifestConfuser(Protector protector) {
        super(protector, "ManifestConfuser: ");
    }

    @Override // com.reandroid.apkeditor.protect.Confuser
    public void confuse() {
        if (getOptions().skipManifest) {
            logMessage("Skip");
            return;
        }
        AndroidManifestBlock androidManifest = getApkModule().getAndroidManifest();
        List<ResXmlElement> list = CollectionUtil.toList(androidManifest.recursiveElements());
        Random random = new Random();
        for (ResXmlElement resXmlElement : list) {
            resXmlElement.setAttributesUnitSize(random.nextInt(6) + 21, false);
            ResXmlAttribute resXmlAttributeNewAttribute = resXmlElement.newAttribute();
            resXmlAttributeNewAttribute.setName(" >\n  </" + resXmlElement.getName() + ">\n  android:name", 0);
            resXmlAttributeNewAttribute.setValueAsBoolean(false);
        }
        androidManifest.getManifestElement().setAttributesUnitSize(20, false);
        androidManifest.refresh();
    }
}
