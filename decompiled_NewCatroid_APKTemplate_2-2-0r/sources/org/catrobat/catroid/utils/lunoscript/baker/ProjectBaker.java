package org.catrobat.catroid.utils.lunoscript.baker;

import android.content.Context;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.userbrick.UserDefinedBrickInput;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/baker/ProjectBaker.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectBaker.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0007H\u0002J\u000e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0015J\u001c\u0010\u0016\u001a\u00020\u00072\n\u0010\u0017\u001a\u00060\u000bj\u0002`\f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001e\u0010\u001a\u001a\u00020\u00072\n\u0010\u0017\u001a\u00060\u000bj\u0002`\f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J \u0010\u001d\u001a\u00020\u00072\n\u0010\u0017\u001a\u00060\u000bj\u0002`\f2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001fH\u0002J\u001c\u0010 \u001a\u00020\u00072\n\u0010\u0017\u001a\u00060\u000bj\u0002`\f2\u0006\u0010!\u001a\u00020\u0001H\u0002J\u0010\u0010\"\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0007H\u0002J$\u0010$\u001a\u00020\u00102\n\u0010\u0017\u001a\u00060\u000bj\u0002`\f2\u0006\u0010%\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0001H\u0002J\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u001f2\n\u0010(\u001a\u0006\u0012\u0002\b\u00030)H\u0002J\u001e\u0010*\u001a\u00020\u00072\n\u0010\u0017\u001a\u00060\u000bj\u0002`\f2\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u0002J\u0010\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00060\u000bj\u0002`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/baker/ProjectBaker;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "bakedObjects", "Ljava/util/IdentityHashMap;", "", "imports", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "varCounter", "", "addDefaultImports", "", "addImport", "className", "bake", "project", "Lorg/catrobat/catroid/content/Project;", "bakeFormula", "body", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "bakeFormulaElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "bakeList", SchemaSymbols.ATTVAL_LIST, "", "bakeObject", "obj", "escape", "str", "fillFields", "varName", "getAllFields", "Ljava/lang/reflect/Field;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "getValueCode", "value", "nextVar", "prefix", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectBaker {
    private final IdentityHashMap<Object, String> bakedObjects;
    private final Context context;
    private final Set<String> imports;
    private final StringBuilder sb;
    private int varCounter;

    public ProjectBaker(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.sb = new StringBuilder();
        this.imports = new LinkedHashSet();
        this.bakedObjects = new IdentityHashMap<>();
    }

    private final void addDefaultImports() {
        addImport("org.catrobat.catroid.content.Project");
        addImport("org.catrobat.catroid.content.Scene");
        addImport("org.catrobat.catroid.content.Sprite");
        addImport("org.catrobat.catroid.content.Script");
        addImport("org.catrobat.catroid.content.Look");
        addImport("org.catrobat.catroid.common.LookData");
        addImport("org.catrobat.catroid.common.SoundInfo");
        addImport("org.catrobat.catroid.formulaeditor.UserVariable");
        addImport("org.catrobat.catroid.formulaeditor.UserList");
        addImport("org.catrobat.catroid.formulaeditor.Formula");
        addImport("org.catrobat.catroid.formulaeditor.FormulaElement");
        addImport("org.catrobat.catroid.content.bricks.Brick");
        addImport(CommonClassNames.JAVA_IO_FILE);
        addImport(CommonClassNames.JAVA_UTIL_ARRAY_LIST);
        addImport("java.util.UUID");
        addImport("org.catrobat.catroid.userbrick.UserDefinedBrickLabel");
        addImport("org.catrobat.catroid.userbrick.UserDefinedBrickInput");
        addImport("org.catrobat.catroid.content.bricks.UserDefinedBrick");
        addImport("org.catrobat.catroid.content.bricks.ScriptBrick");
    }

    public final String bake(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.sb.setLength(0);
        this.bakedObjects.clear();
        this.varCounter = 0;
        this.imports.clear();
        addDefaultImports();
        StringBuilder sb = new StringBuilder();
        this.bakedObjects.put(project, "proj");
        sb.append("var proj = CreateEmptyProjectContext();\n");
        fillFields(sb, "proj", project);
        sb.append("\nSetGlobalProject(proj);\n");
        StringBuilder sb2 = new StringBuilder();
        Iterator it = CollectionsKt.sorted(this.imports).iterator();
        while (it.hasNext()) {
            sb2.append("import " + ((String) it.next()) + ";\n");
        }
        return sb2.toString() + "\n" + ((Object) sb);
    }

    private final String getValueCode(StringBuilder body, Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            CharSequence charSequence = (CharSequence) value;
            if (StringsKt.contains$default(charSequence, (CharSequence) "#ifdef GL_ES", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) "void main", false, 2, (Object) null)) {
                List<String> listLines = StringsKt.lines(charSequence);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listLines, 10));
                for (String strSubstring : listLines) {
                    int iIndexOf$default = StringsKt.indexOf$default((CharSequence) strSubstring, "//", 0, false, 6, (Object) null);
                    if (iIndexOf$default != -1) {
                        strSubstring = strSubstring.substring(0, iIndexOf$default);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    }
                    arrayList.add(strSubstring);
                }
                return escape(CollectionsKt.joinToString$default(arrayList, "\n", null, null, 0, null, null, 62, null));
            }
            return escape((String) value);
        }
        if (value instanceof Number ? true : value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof Enum) {
            Class<?> cls = value.getClass();
            String name2 = cls.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            addImport(name2);
            if (cls.isMemberClass() && cls.getDeclaringClass() != null) {
                Class<?> declaringClass = cls.getDeclaringClass();
                Intrinsics.checkNotNull(declaringClass);
                return declaringClass.getSimpleName() + "." + cls.getSimpleName() + "." + ((Enum) value).name();
            }
            return cls.getSimpleName() + "." + ((Enum) value).name();
        }
        if (value instanceof File) {
            String path = ((File) value).getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            return "File(" + escape(path) + Constant.AFTER_QUTO;
        }
        if (value instanceof Formula) {
            return bakeFormula(body, (Formula) value);
        }
        if (value instanceof List) {
            return bakeList(body, (List) value);
        }
        if (value instanceof UUID) {
            addImport("java.util.UUID");
            return "UUID.fromString(\"" + value + "\")";
        }
        return bakeObject(body, value);
    }

    private final String bakeObject(StringBuilder body, Object obj) {
        Field declaredField;
        Object obj2;
        String str;
        Field declaredField2;
        String string;
        if (this.bakedObjects.containsKey(obj)) {
            String str2 = this.bakedObjects.get(obj);
            Intrinsics.checkNotNull(str2);
            return str2;
        }
        Class<?> cls = obj.getClass();
        if (cls.isAnonymousClass() || cls.isSynthetic()) {
            return "null";
        }
        String name2 = cls.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        addImport(name2);
        String simpleName = cls.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        String lowerCase = simpleName.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String strNextVar = nextVar(lowerCase);
        this.bakedObjects.put(obj, strNextVar);
        String str3 = "";
        if (Intrinsics.areEqual(cls.getSimpleName(), "UserDefinedBrickLabel")) {
            try {
                declaredField = cls.getDeclaredField(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME);
            } catch (Exception unused) {
                declaredField = null;
            }
            if (declaredField != null) {
                declaredField.setAccessible(true);
            }
            Object obj3 = declaredField != null ? declaredField.get(obj) : null;
            obj2 = obj3 instanceof String ? (String) obj3 : null;
            str = "UserDefinedBrickLabel(" + escape(obj2 != null ? obj2 : "") + Constant.AFTER_QUTO;
        } else if (Intrinsics.areEqual(cls.getSimpleName(), "UserDefinedBrickInput")) {
            try {
                declaredField2 = cls.getDeclaredField("name");
            } catch (Exception unused2) {
                declaredField2 = null;
            }
            if (declaredField2 != null) {
                declaredField2.setAccessible(true);
            }
            obj2 = declaredField2 != null ? declaredField2.get(obj) : null;
            if (obj2 != null && (string = obj2.toString()) != null) {
                str3 = string;
            }
            str = "UserDefinedBrickInput(" + escape(str3) + Constant.AFTER_QUTO;
        } else {
            str = cls.getSimpleName() + "()";
        }
        body.append("var " + strNextVar + " = " + str + ";\n");
        fillFields(body, strNextVar, obj);
        return strNextVar;
    }

    private final String bakeList(StringBuilder body, List<?> list) {
        String strNextVar = nextVar(SchemaSymbols.ATTVAL_LIST);
        body.append("var " + strNextVar + " = ArrayList();\n");
        for (Object obj : list) {
            if (obj != null && (!(obj instanceof Brick) || !((Brick) obj).isCommentedOut())) {
                if (!(obj instanceof Script) || !((Script) obj).isCommentedOut()) {
                    body.append(strNextVar + ".add(" + getValueCode(body, obj) + ");\n");
                }
            }
        }
        return strNextVar;
    }

    private final void fillFields(StringBuilder body, String varName, Object obj) {
        Object obj2;
        String str;
        Project project;
        String str2;
        if ((obj instanceof Scene) && (project = ((Scene) obj).getProject()) != null && (str2 = this.bakedObjects.get(project)) != null) {
            body.append(varName + ".setProject(" + str2 + ");\n");
        }
        for (Field field : getAllFields(obj.getClass())) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers()) && !Intrinsics.areEqual(field.getName(), Promotion.ACTION_VIEW) && !Intrinsics.areEqual(field.getName(), "checkbox") && !Intrinsics.areEqual(field.getName(), "stage") && !Intrinsics.areEqual(field.getName(), "batch")) {
                String name2 = field.getType().getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (!StringsKt.startsWith$default(name2, "android.view", false, 2, (Object) null) && (!(obj instanceof Script) || !Intrinsics.areEqual(field.getName(), "formulaMap"))) {
                    field.setAccessible(true);
                    try {
                        obj2 = field.get(obj);
                    } catch (Exception unused) {
                        obj2 = null;
                    }
                    if ((obj instanceof ScriptBrick) && Intrinsics.areEqual(field.getName(), "script")) {
                        if (obj2 != null) {
                            String valueCode = getValueCode(body, obj2);
                            body.append(varName + ".script = " + valueCode + ";\n");
                            body.append(varName + ".formulaMap = " + valueCode + ".getFormulaMap();\n");
                            body.append(valueCode + ".setScriptBrick(" + varName + ");\n");
                        }
                    } else if (obj2 != null) {
                        if ((obj instanceof FormulaBrick) && Intrinsics.areEqual(field.getName(), "formulaMap")) {
                            Map<Brick.FormulaField, Formula> allFormulasMap = ((FormulaBrick) obj).getAllFormulasMap();
                            Intrinsics.checkNotNull(allFormulasMap);
                            for (Map.Entry<Brick.FormulaField, Formula> entry : allFormulasMap.entrySet()) {
                                Object obj3 = (Brick.FormulaField) entry.getKey();
                                Formula value = entry.getValue();
                                if (obj3 instanceof Enum) {
                                    Class<?> declaringClass = obj3.getClass().getDeclaringClass();
                                    String simpleName = declaringClass != null ? declaringClass.getSimpleName() : null;
                                    if (simpleName == null) {
                                        simpleName = "Brick";
                                    }
                                    str = simpleName + ".BrickField." + ((Enum) obj3).name();
                                } else {
                                    Iterator<Map.Entry<Object, String>> it = this.bakedObjects.entrySet().iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            str = "null";
                                            break;
                                        }
                                        Map.Entry<Object, String> next = it.next();
                                        Object key = next.getKey();
                                        String value2 = next.getValue();
                                        if (key instanceof UserDefinedBrickInput) {
                                            UserDefinedBrickInput userDefinedBrickInput = (UserDefinedBrickInput) key;
                                            if (userDefinedBrickInput.getInputFormulaField() == obj3 || Intrinsics.areEqual(userDefinedBrickInput.getInputFormulaField(), obj3)) {
                                                str = value2 + ".getInputFormulaField()";
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!Intrinsics.areEqual(str, "null")) {
                                    body.append(varName + ".formulaMap.put(" + str + ", " + getValueCode(body, value) + ");\n");
                                }
                            }
                        } else {
                            body.append(varName + "." + field.getName() + " = " + getValueCode(body, obj2) + ";\n");
                        }
                    }
                }
            }
        }
        if (obj instanceof LookData) {
            LookData lookData = (LookData) obj;
            String str3 = lookData.fileName;
            if (str3 == null) {
                str3 = lookData.f512name;
            }
            if (str3 != null) {
                body.append(varName + ".file = File(ROOT_PATH, \"images/" + str3 + "\");\n");
            }
        }
        if (obj instanceof SoundInfo) {
            SoundInfo soundInfo = (SoundInfo) obj;
            String str4 = soundInfo.fileName;
            if (str4 == null) {
                str4 = soundInfo.f517name;
            }
            if (str4 != null) {
                body.append(varName + ".file = File(ROOT_PATH, \"sounds/" + str4 + "\");\n");
            }
        }
    }

    private final List<Field> getAllFields(Class<?> type) {
        ArrayList arrayList = new ArrayList();
        while (type != null && !Intrinsics.areEqual(type, Object.class)) {
            Field[] declaredFields = type.getDeclaredFields();
            Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
            CollectionsKt.addAll(arrayList, declaredFields);
            type = type.getSuperclass();
        }
        return arrayList;
    }

    private final void addImport(String className) {
        if (StringsKt.contains$default((CharSequence) className, (CharSequence) "$", false, 2, (Object) null)) {
            this.imports.add(StringsKt.substringBefore$default(className, "$", (String) null, 2, (Object) null));
        } else {
            this.imports.add(className);
        }
    }

    private final String nextVar(String prefix) {
        int i = this.varCounter;
        this.varCounter = i + 1;
        return prefix + "_" + i;
    }

    private final String escape(String str) {
        StringBuilder sb = new StringBuilder("\"");
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\') {
                sb.append("\\\\");
            } else if (cCharAt == '\"') {
                sb.append("\\\"");
            } else if (cCharAt == '\n') {
                sb.append("\\n");
            } else if (cCharAt == '\r') {
                sb.append("\\r");
            } else if (cCharAt == '\t') {
                sb.append("\\t");
            } else if (cCharAt < ' ' || cCharAt > '~') {
                sb.append(cCharAt);
            } else {
                sb.append(cCharAt);
            }
        }
        sb.append("\"");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final String bakeFormula(StringBuilder body, Formula formula) {
        String name2 = Formula.class.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        addImport(name2);
        return "Formula(" + bakeFormulaElement(body, formula.getFormulaTree()) + Constant.AFTER_QUTO;
    }

    private final String bakeFormulaElement(StringBuilder body, FormulaElement element) {
        if (element == null) {
            return "null";
        }
        String strNextVar = nextVar("elem");
        String name2 = FormulaElement.class.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        addImport(name2);
        FormulaElement.ElementType elementType = element.type;
        String value = element.getValue();
        String strBakeFormulaElement = bakeFormulaElement(body, element.leftChild);
        String strBakeFormulaElement2 = bakeFormulaElement(body, element.rightChild);
        body.append("var " + strNextVar + " = FormulaElement(FormulaElement.ElementType." + elementType.name() + ", " + getValueCode(body, value) + ", null, " + strBakeFormulaElement + ", " + strBakeFormulaElement2 + ");\n");
        if (element.additionalChildren != null) {
            List<FormulaElement> additionalChildren = element.additionalChildren;
            Intrinsics.checkNotNullExpressionValue(additionalChildren, "additionalChildren");
            if (!additionalChildren.isEmpty()) {
                Iterator<FormulaElement> it = element.additionalChildren.iterator();
                while (it.hasNext()) {
                    String strBakeFormulaElement3 = bakeFormulaElement(body, it.next());
                    body.append(strNextVar + ".additionalChildren.add(" + strBakeFormulaElement3 + ");\n");
                    body.append(strBakeFormulaElement3 + ".parent = " + strNextVar + ";\n");
                }
            }
        }
        return strNextVar;
    }
}
