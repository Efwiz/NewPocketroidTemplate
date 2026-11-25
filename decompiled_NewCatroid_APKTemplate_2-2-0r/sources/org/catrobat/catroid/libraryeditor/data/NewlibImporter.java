package org.catrobat.catroid.libraryeditor.data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.Xml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/NewlibImporter.class
  classes14.dex
 */
/* JADX INFO: compiled from: NewlibImporter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n2\u0006\u0010\f\u001a\u00020\rH\u0002¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/NewlibImporter;", "", "()V", PsiKeyword.IMPORT, "Lorg/catrobat/catroid/libraryeditor/data/LibraryDraft;", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "parseBricksXml", "", "Lorg/catrobat/catroid/libraryeditor/data/EditableBrick;", "stream", "Ljava/io/InputStream;", "parseFormulasXml", "Lorg/catrobat/catroid/libraryeditor/data/EditableFormula;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewlibImporter {
    public static final NewlibImporter INSTANCE = new NewlibImporter();

    private NewlibImporter() {
    }

    /* JADX INFO: renamed from: import, reason: not valid java name */
    public final LibraryDraft m5414import(Context context, Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream == null) {
                return null;
            }
            InputStream inputStream = inputStreamOpenInputStream;
            try {
                String string = CatroidApplication.getAppContext().getString(R.string.libs_newlib);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                LibraryDraft libraryDraft = new LibraryDraft("last_session", string, null, null, null, null, 60, null);
                ZipInputStream zipInputStream = new ZipInputStream(inputStream);
                for (ZipEntry nextEntry = zipInputStream.getNextEntry(); nextEntry != null; nextEntry = zipInputStream.getNextEntry()) {
                    String name2 = nextEntry.getName();
                    if (name2 != null) {
                        int iHashCode = name2.hashCode();
                        if (iHashCode != -869098769) {
                            if (iHashCode != 12904054) {
                                if (iHashCode == 1476190331 && name2.equals("bricks.xml")) {
                                    libraryDraft.getBricks().addAll(INSTANCE.parseBricksXml(zipInputStream));
                                }
                            } else if (name2.equals("formulas.xml")) {
                                libraryDraft.getFormulas().addAll(INSTANCE.parseFormulasXml(zipInputStream));
                            }
                        } else if (name2.equals("code.txt")) {
                            Reader inputStreamReader = new InputStreamReader(zipInputStream, Charsets.UTF_8);
                            libraryDraft.setCode(TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192)));
                        }
                    }
                }
                CloseableKt.closeFinally(inputStream, null);
                return libraryDraft;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(inputStream, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            Log.e("NewlibImporter", "Failed to import .newlib file", e);
            return null;
        }
    }

    private final List<EditableFormula> parseFormulasXml(InputStream stream) throws XmlPullParserException, IOException {
        List<EditableParam> params;
        ArrayList arrayList = new ArrayList();
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlPullParserNewPullParser.setInput(stream, null);
        EditableFormula editableFormula = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType != 2) {
                if (eventType == 3 && Intrinsics.areEqual(xmlPullParserNewPullParser.getName(), "formula") && editableFormula != null) {
                    arrayList.add(editableFormula);
                    editableFormula = null;
                }
            } else {
                String name2 = xmlPullParserNewPullParser.getName();
                if (Intrinsics.areEqual(name2, "formula")) {
                    String attributeValue = xmlPullParserNewPullParser.getAttributeValue(null, "id");
                    if (attributeValue == null) {
                        attributeValue = "";
                    }
                    String attributeValue2 = xmlPullParserNewPullParser.getAttributeValue(null, "function");
                    if (attributeValue2 == null) {
                        attributeValue2 = "";
                    }
                    String attributeValue3 = xmlPullParserNewPullParser.getAttributeValue(null, "displayName");
                    editableFormula = new EditableFormula(attributeValue, attributeValue2, attributeValue3 != null ? attributeValue3 : "", new ArrayList());
                } else if (Intrinsics.areEqual(name2, "param")) {
                    String attributeValue4 = xmlPullParserNewPullParser.getAttributeValue(null, ModuleXmlParser.TYPE);
                    if (attributeValue4 == null) {
                        attributeValue4 = "";
                    }
                    String attributeValue5 = xmlPullParserNewPullParser.getAttributeValue(null, "default");
                    String str = attributeValue5 != null ? attributeValue5 : "";
                    if (editableFormula != null && (params = editableFormula.getParams()) != null) {
                        params.add(new EditableParam(attributeValue4, str, null));
                    }
                }
            }
        }
        return arrayList;
    }

    private final List<EditableBrick> parseBricksXml(InputStream stream) throws XmlPullParserException, IOException {
        List<EditableParam> params;
        ArrayList arrayList = new ArrayList();
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlPullParserNewPullParser.setInput(stream, null);
        EditableBrick editableBrick = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType != 2) {
                if (eventType == 3 && Intrinsics.areEqual(xmlPullParserNewPullParser.getName(), "brick") && editableBrick != null) {
                    arrayList.add(editableBrick);
                    editableBrick = null;
                }
            } else {
                String name2 = xmlPullParserNewPullParser.getName();
                if (Intrinsics.areEqual(name2, "brick")) {
                    String attributeValue = xmlPullParserNewPullParser.getAttributeValue(null, "id");
                    if (attributeValue == null) {
                        attributeValue = "";
                    }
                    String attributeValue2 = xmlPullParserNewPullParser.getAttributeValue(null, "function");
                    if (attributeValue2 == null) {
                        attributeValue2 = "";
                    }
                    String attributeValue3 = xmlPullParserNewPullParser.getAttributeValue(null, "header");
                    editableBrick = new EditableBrick(attributeValue, attributeValue2, attributeValue3 != null ? attributeValue3 : "", new ArrayList());
                } else if (Intrinsics.areEqual(name2, "param")) {
                    String attributeValue4 = xmlPullParserNewPullParser.getAttributeValue(null, ModuleXmlParser.TYPE);
                    if (attributeValue4 == null) {
                        attributeValue4 = "";
                    }
                    String attributeValue5 = xmlPullParserNewPullParser.getAttributeValue(null, "name");
                    String str = attributeValue5 != null ? attributeValue5 : "";
                    if (editableBrick != null && (params = editableBrick.getParams()) != null) {
                        params.add(new EditableParam(attributeValue4, null, str));
                    }
                }
            }
        }
        return arrayList;
    }
}
