package org.catrobat.catroid.libraryeditor.data;

import android.util.Xml;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.xmlpull.v1.XmlSerializer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/XmlGenerator.class
  classes14.dex
 */
/* JADX INFO: compiled from: XmlGenerator.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u0014\u0010\b\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/XmlGenerator;", "", "()V", "generateBricksXml", "", "bricks", "", "Lorg/catrobat/catroid/libraryeditor/data/EditableBrick;", "generateFormulasXml", "formulas", "Lorg/catrobat/catroid/libraryeditor/data/EditableFormula;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class XmlGenerator {
    public static final XmlGenerator INSTANCE = new XmlGenerator();

    private XmlGenerator() {
    }

    public final String generateFormulasXml(List<EditableFormula> formulas) throws IOException {
        Intrinsics.checkNotNullParameter(formulas, "formulas");
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.startDocument("UTF-8", true);
        xmlSerializerNewSerializer.startTag(null, "formulas");
        for (EditableFormula editableFormula : formulas) {
            xmlSerializerNewSerializer.startTag(null, "formula");
            xmlSerializerNewSerializer.attribute(null, "id", editableFormula.getId());
            xmlSerializerNewSerializer.attribute(null, "function", editableFormula.getFunctionName());
            xmlSerializerNewSerializer.attribute(null, "displayName", editableFormula.getDisplayName());
            xmlSerializerNewSerializer.startTag(null, "params");
            for (EditableParam editableParam : editableFormula.getParams()) {
                xmlSerializerNewSerializer.startTag(null, "param");
                xmlSerializerNewSerializer.attribute(null, ModuleXmlParser.TYPE, editableParam.getType());
                String defaultValue = editableParam.getDefaultValue();
                if (defaultValue == null) {
                    defaultValue = "";
                }
                xmlSerializerNewSerializer.attribute(null, "default", defaultValue);
                xmlSerializerNewSerializer.endTag(null, "param");
            }
            xmlSerializerNewSerializer.endTag(null, "params");
            xmlSerializerNewSerializer.endTag(null, "formula");
        }
        xmlSerializerNewSerializer.endTag(null, "formulas");
        xmlSerializerNewSerializer.endDocument();
        String string = stringWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final String generateBricksXml(List<EditableBrick> bricks) throws IOException {
        Intrinsics.checkNotNullParameter(bricks, "bricks");
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.startDocument("UTF-8", true);
        xmlSerializerNewSerializer.startTag(null, "bricks");
        for (EditableBrick editableBrick : bricks) {
            xmlSerializerNewSerializer.startTag(null, "brick");
            xmlSerializerNewSerializer.attribute(null, "id", editableBrick.getId());
            xmlSerializerNewSerializer.attribute(null, "function", editableBrick.getFunctionName());
            xmlSerializerNewSerializer.attribute(null, "header", editableBrick.getHeaderText());
            xmlSerializerNewSerializer.startTag(null, "params");
            for (EditableParam editableParam : editableBrick.getParams()) {
                xmlSerializerNewSerializer.startTag(null, "param");
                xmlSerializerNewSerializer.attribute(null, ModuleXmlParser.TYPE, editableParam.getType());
                String name2 = editableParam.getName();
                if (name2 == null) {
                    name2 = "";
                }
                xmlSerializerNewSerializer.attribute(null, "name", name2);
                xmlSerializerNewSerializer.endTag(null, "param");
            }
            xmlSerializerNewSerializer.endTag(null, "params");
            xmlSerializerNewSerializer.endTag(null, "brick");
        }
        xmlSerializerNewSerializer.endTag(null, "bricks");
        xmlSerializerNewSerializer.endDocument();
        String string = stringWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
