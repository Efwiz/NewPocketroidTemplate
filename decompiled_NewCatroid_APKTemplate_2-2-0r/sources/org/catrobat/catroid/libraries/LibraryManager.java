package org.catrobat.catroid.libraries;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.CustomFormula;
import org.catrobat.catroid.formulaeditor.CustomFormulaManager;
import org.catrobat.catroid.formulaeditor.InternTokenType;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.utils.lunoscript.Interpreter;
import org.catrobat.catroid.utils.lunoscript.Lexer;
import org.catrobat.catroid.utils.lunoscript.Parser;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraries/LibraryManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: LibraryManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u0007J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007H\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\rH\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0007H\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0016\u001a\u00020\u0007H\u0002J\u0018\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\bH\u0002J\u0018\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\bH\u0002J\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u0007H\u0002J\u001e\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u001e2\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u0007H\u0002J\u000e\u0010#\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010$\u001a\u00020\u0010J\u0010\u0010%\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/catrobat/catroid/libraries/LibraryManager;", "", "()V", "errors", "", "loadedLibraries", "", "", "Lorg/catrobat/catroid/libraries/LoadedLibrary;", "getLoadedLibrary", "id", "getZipEntryContent", "zipFile", "Ljava/io/File;", "entryName", "loadLibraryFromFile", "", "project", "Lorg/catrobat/catroid/content/Project;", "libFile", "mapStringToInternTokenType", "Lorg/catrobat/catroid/formulaeditor/InternTokenType;", "typeName", "mapStringToParameterType", "Lorg/catrobat/catroid/libraries/ParameterType;", "parseAndRegisterBricks", "xmlContent", "library", "parseAndRegisterFormulas", "parseBricksXml", "", "Lorg/catrobat/catroid/libraries/CustomBrickDefinition;", "libraryId", "parseFormulasXml", "Lorg/catrobat/catroid/formulaeditor/CustomFormula;", "syncAndLoadLibraries", "unloadAllLibraries", "unloadLibrary", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LibraryManager {
    private static int errors;
    public static final LibraryManager INSTANCE = new LibraryManager();
    private static final Map<String, LoadedLibrary> loadedLibraries = new LinkedHashMap();

    private LibraryManager() {
    }

    public final LoadedLibrary getLoadedLibrary(String id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        return loadedLibraries.get(id2);
    }

    public final void syncAndLoadLibraries(Project project) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(project, "project");
        errors = 0;
        File libsDir = project.getLibsDir();
        if (!libsDir.exists()) {
            libsDir.mkdirs();
        }
        File[] fileArrListFiles = libsDir.listFiles(new FilenameFilter() { // from class: org.catrobat.catroid.libraries.LibraryManager$$ExternalSyntheticLambda0
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return LibraryManager.syncAndLoadLibraries$lambda$0(file, str);
            }
        });
        if (fileArrListFiles == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else {
            ArrayList arrayList = new ArrayList(fileArrListFiles.length);
            for (File file : fileArrListFiles) {
                arrayList.add(file.getName());
            }
            arrayListEmptyList = arrayList;
        }
        List<String> list = arrayListEmptyList;
        for (String str : SetsKt.minus(CollectionsKt.toSet(loadedLibraries.keySet()), (Iterable) CollectionsKt.toSet(list))) {
            LibraryManager libraryManager = INSTANCE;
            Intrinsics.checkNotNull(str);
            libraryManager.unloadLibrary(str);
        }
        for (String str2 : list) {
            if (!loadedLibraries.containsKey(str2)) {
                INSTANCE.loadLibraryFromFile(project, new File(libsDir, str2));
            }
        }
        if (errors == 0) {
            File[] fileArrListFiles2 = libsDir.listFiles();
            if ((fileArrListFiles2 != null ? fileArrListFiles2.length : 0) != 0) {
                MainMenuActivity.INSTANCE.toast("Синхронизация прошла успешно!", 0);
                return;
            }
            return;
        }
        MainMenuActivity.INSTANCE.toast("Синхронизация завершилась со счетчиком ошибок: " + errors, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean syncAndLoadLibraries$lambda$0(File file, String str) {
        Intrinsics.checkNotNull(str);
        return StringsKt.endsWith$default(str, ".newlib", false, 2, (Object) null);
    }

    private final void loadLibraryFromFile(Project project, File libFile) {
        String name2 = libFile.getName();
        try {
            Log.i("LibraryManager", "Загрузка библиотеки: " + name2);
            String zipEntryContent = getZipEntryContent(libFile, "code.txt");
            String zipEntryContent2 = getZipEntryContent(libFile, "formulas.xml");
            String zipEntryContent3 = getZipEntryContent(libFile, "bricks.xml");
            if (zipEntryContent == null) {
                throw new IllegalStateException("В библиотеке отсутствует code.txt");
            }
            Context appContext = CatroidApplication.getAppContext();
            Sprite sprite = project.getDefaultScene().getSpriteList().get(0);
            Intrinsics.checkNotNullExpressionValue(sprite, "get(...)");
            Interpreter interpreter = new Interpreter(appContext, new Scope(project, sprite, new SequenceAction()));
            interpreter.interpret(new Parser(new Lexer(zipEntryContent).scanTokens()).parse());
            Intrinsics.checkNotNull(name2);
            LoadedLibrary loadedLibrary = new LoadedLibrary(name2, interpreter);
            if (zipEntryContent2 != null) {
                INSTANCE.parseAndRegisterFormulas(zipEntryContent2, loadedLibrary);
            }
            if (zipEntryContent3 != null) {
                INSTANCE.parseAndRegisterBricks(zipEntryContent3, loadedLibrary);
            }
            loadedLibraries.put(name2, loadedLibrary);
        } catch (Exception e) {
            Log.e("LibraryManager", "Ошибка загрузки библиотеки " + name2, e);
            MainMenuActivity.INSTANCE.toast("Ошибка загрузки '" + name2 + FormatHelper.QUOTE, 0);
            errors++;
        }
    }

    private final void unloadLibrary(String libraryId) {
        Log.i("LibraryManager", "Выгрузка библиотеки: " + libraryId);
        CustomFormulaManager.INSTANCE.removeFormulasByOwner(libraryId);
        CustomBrickManager.INSTANCE.removeBricksByOwner(libraryId);
        loadedLibraries.remove(libraryId);
    }

    private final String getZipEntryContent(File zipFile, String entryName) {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        try {
            ZipInputStream zipInputStream2 = zipInputStream;
            for (ZipEntry nextEntry = zipInputStream2.getNextEntry(); nextEntry != null; nextEntry = zipInputStream2.getNextEntry()) {
                if (Intrinsics.areEqual(nextEntry.getName(), entryName)) {
                    Reader inputStreamReader = new InputStreamReader(zipInputStream2, Charsets.UTF_8);
                    String text = TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192));
                    CloseableKt.closeFinally(zipInputStream, null);
                    return text;
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipInputStream, null);
            return null;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(zipInputStream, th);
                throw th2;
            }
        }
    }

    private final void parseAndRegisterFormulas(String xmlContent, LoadedLibrary library) {
        Log.d("LibraryManager", "Начинаю парсинг formulas.xml для " + library.getId());
        try {
            for (CustomFormula customFormula : parseFormulasXml(xmlContent, library.getId())) {
                CustomFormulaManager.INSTANCE.addFormula(customFormula);
                Log.i("LibraryManager", "Зарегистрирована формула '" + customFormula.getDisplayName() + "' из библиотеки " + library.getId());
            }
        } catch (Exception e) {
            Log.e("LibraryManager", "Ошибка парсинга formulas.xml для " + library.getId(), e);
        }
    }

    private final List<CustomFormula> parseFormulasXml(String xmlContent, String libraryId) throws XmlPullParserException, IOException {
        ArrayList arrayList;
        String str;
        String str2;
        ArrayList arrayList2;
        ArrayList arrayList3 = new ArrayList();
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        Intrinsics.checkNotNullExpressionValue(xmlPullParserNewPullParser, "newPullParser(...)");
        xmlPullParserNewPullParser.setInput(new StringReader(xmlContent));
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        String attributeValue = null;
        String attributeValue2 = null;
        String attributeValue3 = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType != 2) {
                if (eventType != 3) {
                    arrayList2 = arrayList5;
                    str = attributeValue;
                    str2 = attributeValue2;
                } else {
                    if (!Intrinsics.areEqual(xmlPullParserNewPullParser.getName(), "formula")) {
                        arrayList = arrayList5;
                        str = attributeValue;
                        str2 = attributeValue2;
                    } else if (attributeValue3 == null || attributeValue2 == null || attributeValue == null) {
                        arrayList = arrayList5;
                        str = attributeValue;
                        str2 = attributeValue2;
                        Log.d("LibManager", "id: " + attributeValue3 + ", func: " + str2 + ", name: " + str);
                        Log.d("LibManager", "File: " + xmlContent);
                    } else {
                        Log.d("LibManager", "name: " + attributeValue3 + ", display: " + attributeValue + ", params: " + arrayList4.size() + ", def: " + arrayList4 + ", types: " + arrayList5 + ", func: " + attributeValue2 + ", lib: " + libraryId);
                        str = attributeValue;
                        arrayList = arrayList5;
                        str2 = attributeValue2;
                        arrayList3.add(new CustomFormula(attributeValue3, attributeValue, arrayList4.size(), arrayList4, arrayList5, attributeValue2, libraryId));
                    }
                    arrayList2 = arrayList;
                }
                attributeValue2 = str2;
                attributeValue = str;
                arrayList5 = arrayList2;
            } else {
                arrayList = arrayList5;
                str = attributeValue;
                str2 = attributeValue2;
                String name2 = xmlPullParserNewPullParser.getName();
                if (Intrinsics.areEqual(name2, "formula")) {
                    attributeValue3 = xmlPullParserNewPullParser.getAttributeValue(null, "id");
                    attributeValue2 = xmlPullParserNewPullParser.getAttributeValue(null, "function");
                    attributeValue = xmlPullParserNewPullParser.getAttributeValue(null, "displayName");
                    ArrayList arrayList6 = new ArrayList();
                    arrayList5 = new ArrayList();
                    arrayList4 = arrayList6;
                } else {
                    if (Intrinsics.areEqual(name2, "param")) {
                        String attributeValue4 = xmlPullParserNewPullParser.getAttributeValue(null, ModuleXmlParser.TYPE);
                        String attributeValue5 = xmlPullParserNewPullParser.getAttributeValue(null, "default");
                        if (attributeValue5 == null) {
                            attributeValue5 = "";
                        }
                        arrayList4.add(attributeValue5);
                        if (attributeValue4 == null) {
                            attributeValue4 = "STRING";
                        }
                        arrayList2 = arrayList;
                        arrayList2.add(mapStringToInternTokenType(attributeValue4));
                    } else {
                        arrayList2 = arrayList;
                    }
                    attributeValue2 = str2;
                    attributeValue = str;
                    arrayList5 = arrayList2;
                }
            }
        }
        return arrayList3;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final InternTokenType mapStringToInternTokenType(String typeName) {
        String upperCase = typeName.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        switch (upperCase.hashCode()) {
            case -1981034679:
                if (upperCase.equals("NUMBER")) {
                    return InternTokenType.NUMBER;
                }
                break;
            case -1838656495:
                if (upperCase.equals("STRING")) {
                    return InternTokenType.STRING;
                }
                break;
            case -1139530606:
                if (upperCase.equals("USER_LIST")) {
                    return InternTokenType.USER_LIST;
                }
                break;
            case -86760336:
                if (upperCase.equals("USER_VARIABLE")) {
                    return InternTokenType.USER_VARIABLE;
                }
                break;
        }
        return InternTokenType.STRING;
    }

    private final void parseAndRegisterBricks(String xmlContent, LoadedLibrary library) {
        Log.d("LibraryManager", "Начинаю парсинг bricks.xml для " + library.getId());
        try {
            for (CustomBrickDefinition customBrickDefinition : parseBricksXml(xmlContent, library.getId())) {
                CustomBrickManager.INSTANCE.registerBrick(customBrickDefinition);
                Log.i("LibraryManager", "Зарегистрирован блок '" + customBrickDefinition.getHeaderText() + "' из библиотеки " + library.getId());
            }
        } catch (Exception e) {
            Log.e("LibraryManager", "Ошибка парсинга bricks.xml для " + library.getId(), e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x00ea  */
    private final List<CustomBrickDefinition> parseBricksXml(String xmlContent, String libraryId) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        Intrinsics.checkNotNullExpressionValue(xmlPullParserNewPullParser, "newPullParser(...)");
        xmlPullParserNewPullParser.setInput(new StringReader(xmlContent));
        ArrayList arrayList2 = new ArrayList();
        String attributeValue = null;
        String attributeValue2 = null;
        String attributeValue3 = null;
        String str = "";
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (Intrinsics.areEqual(name2, "brick")) {
                    attributeValue = xmlPullParserNewPullParser.getAttributeValue(null, "id");
                    attributeValue2 = xmlPullParserNewPullParser.getAttributeValue(null, "function");
                    attributeValue3 = xmlPullParserNewPullParser.getAttributeValue(null, "header");
                    arrayList2 = new ArrayList();
                } else if (Intrinsics.areEqual(name2, "param")) {
                    String attributeValue4 = xmlPullParserNewPullParser.getAttributeValue(null, ModuleXmlParser.TYPE);
                    String attributeValue5 = xmlPullParserNewPullParser.getAttributeValue(null, "name");
                    if (attributeValue5 != null) {
                        if (attributeValue4 == null) {
                            attributeValue4 = "TEXT_FIELD";
                        }
                        arrayList2.add(new BrickParameter(mapStringToParameterType(attributeValue4), attributeValue5));
                    }
                }
            } else if (eventType == 3) {
                if (Intrinsics.areEqual(xmlPullParserNewPullParser.getName(), "brick")) {
                    Log.d("LibManager", "id " + attributeValue + ", ht " + str + ", params " + arrayList2 + ", name: " + attributeValue2 + ", owner " + libraryId);
                    if (attributeValue != null && attributeValue2 != null && attributeValue3 != null) {
                        CustomBrickDefinition customBrickDefinition = new CustomBrickDefinition(attributeValue, attributeValue3, arrayList2, attributeValue2, libraryId);
                        arrayList.add(customBrickDefinition);
                        Log.d("LibManager", "Успешно спарсен блок: " + customBrickDefinition.getId());
                    } else {
                        Log.d("LibManager", "Brick error: id " + attributeValue + ", func " + attributeValue2 + ", hc " + (!StringsKt.isBlank(str)));
                        Log.d("LibManager", xmlContent);
                    }
                    attributeValue = null;
                    attributeValue2 = null;
                    str = "";
                }
            }
        }
        return arrayList;
    }

    public final void unloadAllLibraries() {
        Log.i("LibraryManager", "Выгрузка всех загруженных библиотек...");
        Iterator it = CollectionsKt.toList(loadedLibraries.keySet()).iterator();
        while (it.hasNext()) {
            INSTANCE.unloadLibrary((String) it.next());
        }
        Log.i("LibraryManager", "Все библиотеки выгружены.");
    }

    private final ParameterType mapStringToParameterType(String typeName) {
        Intrinsics.checkNotNullExpressionValue(typeName.toUpperCase(Locale.ROOT), "toUpperCase(...)");
        return ParameterType.TEXT_FIELD;
    }
}
