package org.catrobat.catroid.ai;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.beust.jcommander.Parameters;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.slf4j.Marker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveContextGenerator.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveContextGenerator.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J*\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u001c\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0010H\u0002J\u001c\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0002J*\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\u001e2\n\u0010\u001f\u001a\u00060 j\u0002`!2\u0006\u0010\"\u001a\u00020\rH\u0002J\u0010\u0010#\u001a\u00020\u00042\b\u0010$\u001a\u0004\u0018\u00010%J\u0010\u0010&\u001a\u00020\u00042\u0006\u0010'\u001a\u00020\u0004H\u0002J\u0010\u0010(\u001a\u00020\u00042\u0006\u0010'\u001a\u00020\u0004H\u0002¨\u0006)"}, d2 = {"Lorg/catrobat/catroid/ai/KoveContextGenerator;", "", "()V", "cleanBrickName", "", "className", "generateContext", "Lkotlin/Pair;", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "activeScript", "Lorg/catrobat/catroid/content/Script;", "cursorBrickIndex", "", "getAllFormulasFromBrick", "", "Lorg/catrobat/catroid/content/bricks/Brick$BrickField;", "Lorg/catrobat/catroid/formulaeditor/Formula;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "getFormulaFromBrick", "field", "getPrimitiveFields", "getShortParamName", "fieldName", "mapOperatorToSymbol", "op", "serializeBricks", "", "bricks", "", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", OutputKeys.INDENT, "serializeFormula", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "toCamelCase", "s", "toSnakeCase", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KoveContextGenerator {
    public static final KoveContextGenerator INSTANCE = new KoveContextGenerator();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveContextGenerator$WhenMappings.class
      classes9.dex
     */
    /* JADX INFO: compiled from: KoveContextGenerator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FormulaElement.ElementType.values().length];
            try {
                iArr[FormulaElement.ElementType.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FormulaElement.ElementType.STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FormulaElement.ElementType.USER_VARIABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FormulaElement.ElementType.USER_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[FormulaElement.ElementType.SENSOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[FormulaElement.ElementType.COLLISION_FORMULA.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[FormulaElement.ElementType.USER_DEFINED_BRICK_INPUT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[FormulaElement.ElementType.OPERATOR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[FormulaElement.ElementType.FUNCTION.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private KoveContextGenerator() {
    }

    public final Pair<String, String> generateContext(Sprite sprite, Script activeScript, int cursorBrickIndex) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(activeScript, "activeScript");
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        List<LookData> lookList = sprite.getLookList();
        Intrinsics.checkNotNullExpressionValue(lookList, "getLookList(...)");
        List<LookData> list = lookList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(FormatHelper.QUOTE + ((LookData) it.next()).f512name + FormatHelper.QUOTE);
        }
        ArrayList arrayList2 = arrayList;
        List<SoundInfo> soundList = sprite.getSoundList();
        Intrinsics.checkNotNullExpressionValue(soundList, "getSoundList(...)");
        List<SoundInfo> list2 = soundList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(FormatHelper.QUOTE + ((SoundInfo) it2.next()).f517name + FormatHelper.QUOTE);
        }
        sb.append("@Sprite(\"" + sprite.getName() + "\")\n");
        String name2 = sprite.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        sb.append(ExternalAnnotationProvider.CLASS_PREFIX + toCamelCase(name2) + ":\n");
        sb.append("    looks = " + arrayList2 + "\n");
        sb.append("    sounds = " + arrayList3 + "\n\n");
        for (Script script : sprite.getScriptList()) {
            String simpleName = script.getClass().getSimpleName();
            boolean zAreEqual = Intrinsics.areEqual(script, activeScript);
            String str = PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + simpleName;
            Intrinsics.checkNotNull(simpleName);
            String snakeCase = toSnakeCase(StringsKt.replace$default(simpleName, "Script", "", false, 4, (Object) null));
            if (zAreEqual) {
                sb.append("    " + str + "\n");
                sb.append("    def " + snakeCase + "():\n");
                int i = cursorBrickIndex < 0 ? 0 : cursorBrickIndex;
                List<Brick> brickList = script.getBrickList();
                Intrinsics.checkNotNullExpressionValue(brickList, "getBrickList(...)");
                List<? extends Brick> listTake = CollectionsKt.take(brickList, i);
                List<Brick> brickList2 = script.getBrickList();
                Intrinsics.checkNotNullExpressionValue(brickList2, "getBrickList(...)");
                List<? extends Brick> listDrop = CollectionsKt.drop(brickList2, i);
                if (listTake.isEmpty()) {
                    sb.append("        pass\n");
                } else {
                    serializeBricks(listTake, sb, 8);
                }
                serializeBricks(listDrop, sb2, 8);
            } else {
                sb.append("    " + str + "\n");
                sb.append("    def " + snakeCase + "():\n");
                if (script.getBrickList().isEmpty()) {
                    sb.append("        pass\n");
                } else {
                    List<Brick> brickList3 = script.getBrickList();
                    Intrinsics.checkNotNullExpressionValue(brickList3, "getBrickList(...)");
                    serializeBricks(brickList3, sb, 8);
                }
                sb.append("\n");
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String string2 = sb2.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        return new Pair<>(string, string2);
    }

    private final void serializeBricks(List<? extends Brick> bricks, StringBuilder builder, int indent) throws IllegalAccessException {
        String string;
        String string2;
        KoveContextGenerator koveContextGenerator = this;
        String strRepeat = StringsKt.repeat(" ", indent);
        for (Brick brick : bricks) {
            if (!brick.isCommentedOut()) {
                String simpleName = brick.getClass().getSimpleName();
                Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
                String strCleanBrickName = koveContextGenerator.cleanBrickName(simpleName);
                if (brick instanceof CompositeBrick) {
                    if (brick instanceof IfThenLogicBeginBrick) {
                        Formula formulaFromBrick = koveContextGenerator.getFormulaFromBrick(brick, Brick.BrickField.IF_CONDITION);
                        builder.append(strRepeat).append("if " + koveContextGenerator.serializeFormula(formulaFromBrick != null ? formulaFromBrick.getFormulaTree() : null) + ":\n");
                        IfThenLogicBeginBrick ifThenLogicBeginBrick = (IfThenLogicBeginBrick) brick;
                        List<Brick> nestedBricks = ifThenLogicBeginBrick.getNestedBricks();
                        Intrinsics.checkNotNullExpressionValue(nestedBricks, "getNestedBricks(...)");
                        int i = indent + 4;
                        koveContextGenerator.serializeBricks(nestedBricks, builder, i);
                        if (((CompositeBrick) brick).hasSecondaryList() && ifThenLogicBeginBrick.getSecondaryNestedBricks() != null) {
                            builder.append(strRepeat).append("else:\n");
                            List<Brick> secondaryNestedBricks = ifThenLogicBeginBrick.getSecondaryNestedBricks();
                            Intrinsics.checkNotNullExpressionValue(secondaryNestedBricks, "getSecondaryNestedBricks(...)");
                            koveContextGenerator.serializeBricks(secondaryNestedBricks, builder, i);
                        }
                    } else if (Intrinsics.areEqual(strCleanBrickName, "forever")) {
                        builder.append(strRepeat).append("forever():\n");
                        List<Brick> nestedBricks2 = ((CompositeBrick) brick).getNestedBricks();
                        Intrinsics.checkNotNullExpressionValue(nestedBricks2, "getNestedBricks(...)");
                        koveContextGenerator.serializeBricks(nestedBricks2, builder, indent + 4);
                    } else if (Intrinsics.areEqual(strCleanBrickName, "repeat")) {
                        Formula formulaFromBrick2 = koveContextGenerator.getFormulaFromBrick(brick, Brick.BrickField.TIMES_TO_REPEAT);
                        builder.append(strRepeat).append("repeat(" + koveContextGenerator.serializeFormula(formulaFromBrick2 != null ? formulaFromBrick2.getFormulaTree() : null) + "):\n");
                        List<Brick> nestedBricks3 = ((CompositeBrick) brick).getNestedBricks();
                        Intrinsics.checkNotNullExpressionValue(nestedBricks3, "getNestedBricks(...)");
                        koveContextGenerator.serializeBricks(nestedBricks3, builder, indent + 4);
                    } else if (Intrinsics.areEqual(strCleanBrickName, "repeat_until")) {
                        Formula formulaFromBrick3 = koveContextGenerator.getFormulaFromBrick(brick, Brick.BrickField.REPEAT_UNTIL_CONDITION);
                        builder.append(strRepeat).append("repeat_until(" + koveContextGenerator.serializeFormula(formulaFromBrick3 != null ? formulaFromBrick3.getFormulaTree() : null) + "):\n");
                        List<Brick> nestedBricks4 = ((CompositeBrick) brick).getNestedBricks();
                        Intrinsics.checkNotNullExpressionValue(nestedBricks4, "getNestedBricks(...)");
                        koveContextGenerator.serializeBricks(nestedBricks4, builder, indent + 4);
                    } else {
                        builder.append(strRepeat).append(strCleanBrickName + "():\n");
                        List<Brick> nestedBricks5 = ((CompositeBrick) brick).getNestedBricks();
                        Intrinsics.checkNotNullExpressionValue(nestedBricks5, "getNestedBricks(...)");
                        koveContextGenerator.serializeBricks(nestedBricks5, builder, indent + 4);
                    }
                } else {
                    Map<Brick.BrickField, Formula> allFormulasFromBrick = koveContextGenerator.getAllFormulasFromBrick(brick);
                    Map<String, Object> primitiveFields = koveContextGenerator.getPrimitiveFields(brick);
                    if (allFormulasFromBrick.size() + primitiveFields.size() == 1) {
                        if (!allFormulasFromBrick.isEmpty()) {
                            string = koveContextGenerator.serializeFormula(((Formula) CollectionsKt.first(allFormulasFromBrick.values())).getFormulaTree());
                        } else {
                            Object objFirst = CollectionsKt.first(primitiveFields.values());
                            string = objFirst instanceof String ? "\"" + objFirst + "\"" : objFirst.toString();
                        }
                        builder.append(strRepeat).append(strCleanBrickName + "(" + string + ")\n");
                    } else {
                        ArrayList arrayList = new ArrayList();
                        for (Map.Entry<Brick.BrickField, Formula> entry : allFormulasFromBrick.entrySet()) {
                            Brick.BrickField key = entry.getKey();
                            Formula value = entry.getValue();
                            KoveContextGenerator koveContextGenerator2 = INSTANCE;
                            arrayList.add(koveContextGenerator2.getShortParamName(key.name()) + ContainerUtils.KEY_VALUE_DELIMITER + koveContextGenerator2.serializeFormula(value.getFormulaTree()));
                        }
                        for (Map.Entry<String, Object> entry2 : primitiveFields.entrySet()) {
                            String key2 = entry2.getKey();
                            Object value2 = entry2.getValue();
                            String snakeCase = INSTANCE.toSnakeCase(key2);
                            if (value2 instanceof String) {
                                string2 = "\"" + value2 + "\"";
                            } else {
                                if (value2 instanceof Boolean) {
                                    string2 = value2.toString();
                                    if (string2.length() > 0) {
                                        StringBuilder sb = new StringBuilder();
                                        String strValueOf = String.valueOf(string2.charAt(0));
                                        Intrinsics.checkNotNull(strValueOf, "null cannot be cast to non-null type java.lang.String");
                                        String upperCase = strValueOf.toUpperCase(Locale.ROOT);
                                        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                                        StringBuilder sbAppend = sb.append((Object) upperCase);
                                        String strSubstring = string2.substring(1);
                                        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                                        string2 = sbAppend.append(strSubstring).toString();
                                    }
                                } else {
                                    string2 = value2.toString();
                                }
                                arrayList.add(snakeCase + ContainerUtils.KEY_VALUE_DELIMITER + string2);
                            }
                            arrayList.add(snakeCase + ContainerUtils.KEY_VALUE_DELIMITER + string2);
                        }
                        builder.append(strRepeat).append(strCleanBrickName + "(" + CollectionsKt.joinToString$default(arrayList, ", ", null, null, 0, null, null, 62, null) + ")\n");
                    }
                }
            }
            koveContextGenerator = this;
        }
    }

    public final String serializeFormula(FormulaElement element) {
        if (element == null) {
            return "";
        }
        FormulaElement.ElementType elementType = element.getElementType();
        switch (elementType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[elementType.ordinal()]) {
            case 1:
                String value = element.getValue();
                return value == null ? "0" : value;
            case 2:
                return "\"" + element.getValue() + "\"";
            case 3:
                return "var(\"" + element.getValue() + "\")";
            case 4:
                return "list(\"" + element.getValue() + "\")";
            case 5:
                return "sensor(\"" + element.getValue() + "\")";
            case 6:
                return "touches(\"" + element.getValue() + "\")";
            case 7:
                return "input(\"" + element.getValue() + "\")";
            case 8:
                String value2 = element.getValue();
                Intrinsics.checkNotNullExpressionValue(value2, "getValue(...)");
                String strMapOperatorToSymbol = mapOperatorToSymbol(value2);
                String strSerializeFormula = serializeFormula(element.leftChild);
                return (strSerializeFormula.length() > 0 ? new StringBuilder("(").append(strSerializeFormula).append(" ").append(strMapOperatorToSymbol).append(" ") : new StringBuilder("(").append(strMapOperatorToSymbol)).append(serializeFormula(element.rightChild)).append(Constant.AFTER_QUTO).toString();
            case 9:
                String value3 = element.getValue();
                Intrinsics.checkNotNullExpressionValue(value3, "getValue(...)");
                String lowerCase = value3.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                ArrayList arrayList = new ArrayList();
                if (element.leftChild != null) {
                    arrayList.add(serializeFormula(element.leftChild));
                }
                if (element.rightChild != null) {
                    arrayList.add(serializeFormula(element.rightChild));
                }
                List<FormulaElement> list = element.additionalChildren;
                if (list != null) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(INSTANCE.serializeFormula((FormulaElement) it.next()));
                    }
                }
                return lowerCase + "(" + CollectionsKt.joinToString$default(arrayList, ", ", null, null, 0, null, null, 62, null) + Constant.AFTER_QUTO;
            default:
                String value4 = element.getValue();
                return value4 == null ? "" : value4;
        }
    }

    private final Formula getFormulaFromBrick(Brick brick, Brick.BrickField field) {
        Map<Brick.BrickField, Formula> allFormulaFieldsWithFormulas;
        if (!(brick instanceof FormulaBrick) || (allFormulaFieldsWithFormulas = ((FormulaBrick) brick).getAllFormulaFieldsWithFormulas()) == null) {
            return null;
        }
        return allFormulaFieldsWithFormulas.get(field);
    }

    private final Map<Brick.BrickField, Formula> getAllFormulasFromBrick(Brick brick) {
        Map<Brick.BrickField, Formula> allFormulaFieldsWithFormulas;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if ((brick instanceof FormulaBrick) && (allFormulaFieldsWithFormulas = ((FormulaBrick) brick).getAllFormulaFieldsWithFormulas()) != null) {
            linkedHashMap.putAll(allFormulaFieldsWithFormulas);
        }
        return linkedHashMap;
    }

    private final Map<String, Object> getPrimitiveFields(Brick brick) throws IllegalAccessException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Set of = SetsKt.setOf((Object[]) new String[]{CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, Promotion.ACTION_VIEW, "checkbox", "spinner", "parent", "endBrick", "loopBricks", "tryBricks", "catchBricks", "finallyBricks", "posX", "posY", "scriptId", "scriptBrick", "commentedOut"});
        for (Class<?> superclass = brick.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            String name2 = superclass.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (!StringsKt.startsWith$default(name2, "org.catrobat.catroid", false, 2, (Object) null)) {
                break;
            }
            Field[] declaredFields = superclass.getDeclaredFields();
            Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
            for (Field field : declaredFields) {
                if (!Modifier.isTransient(field.getModifiers()) && !of.contains(field.getName())) {
                    Class<?> type = field.getType();
                    if (type.isPrimitive() || Intrinsics.areEqual(type, String.class) || type.isEnum()) {
                        field.setAccessible(true);
                        Object obj = field.get(brick);
                        if (obj != null) {
                            String name3 = field.getName();
                            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                            linkedHashMap.put(name3, obj);
                        }
                    } else if (LookData.class.isAssignableFrom(type) || SoundInfo.class.isAssignableFrom(type)) {
                        field.setAccessible(true);
                        Object obj2 = field.get(brick);
                        if (obj2 != null) {
                            try {
                                Object objInvoke = obj2.getClass().getMethod("getName", new Class[0]).invoke(obj2, new Object[0]);
                                Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.String");
                                String name4 = field.getName();
                                Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                                linkedHashMap.put(name4, (String) objInvoke);
                            } catch (Exception unused) {
                            }
                        }
                    }
                }
            }
        }
        return linkedHashMap;
    }

    private final String mapOperatorToSymbol(String op) {
        String str = (String) MapsKt.mapOf(TuplesKt.to("PLUS", Marker.ANY_NON_NULL_MARKER), TuplesKt.to("MINUS", Parameters.DEFAULT_OPTION_PREFIXES), TuplesKt.to("MULT", "*"), TuplesKt.to("DIVIDE", "/"), TuplesKt.to("POW", "**"), TuplesKt.to("MOD", "%"), TuplesKt.to("EQUAL", "=="), TuplesKt.to("NOT_EQUAL", "!="), TuplesKt.to("GREATER_THAN", ">"), TuplesKt.to("GREATER_OR_EQUAL", ">="), TuplesKt.to("SMALLER_THAN", "<"), TuplesKt.to("SMALLER_OR_EQUAL", "<="), TuplesKt.to("LOGICAL_AND", "and"), TuplesKt.to("LOGICAL_OR", "or"), TuplesKt.to("LOGICAL_NOT", "not")).get(op);
        return str == null ? op : str;
    }

    private final String cleanBrickName(String className) {
        if (StringsKt.endsWith$default(className, "Brick", false, 2, (Object) null)) {
            className = className.substring(0, className.length() - 5);
            Intrinsics.checkNotNullExpressionValue(className, "substring(...)");
        }
        String snakeCase = toSnakeCase(className);
        String str = (String) MapsKt.mapOf(TuplesKt.to("set_size_to", "set_size"), TuplesKt.to("set_variable", "set_var"), TuplesKt.to("change_variable", "change_var"), TuplesKt.to("place_at", "place_at"), TuplesKt.to("play_sound_and_wait", "play_sound_wait"), TuplesKt.to("scene_start", "start_scene")).get(snakeCase);
        return str == null ? snakeCase : str;
    }

    private final String getShortParamName(String fieldName) {
        Map mapMapOf = MapsKt.mapOf(TuplesKt.to("VALUE", "val"), TuplesKt.to("VALUE_1", "val1"), TuplesKt.to("VALUE_2", "val2"), TuplesKt.to("VALUE_3", "val3"), TuplesKt.to("TIMES_TO_REPEAT", "times"), TuplesKt.to("INTERVAL", "interval"), TuplesKt.to("X_POSITION", "x"), TuplesKt.to("Y_POSITION", "y"), TuplesKt.to("SIZE", "size"), TuplesKt.to("COLOR", TypedValues.Custom.S_COLOR), TuplesKt.to("STEPS", "steps"), TuplesKt.to("DEGREES", "degrees"), TuplesKt.to("IF_CONDITION", "cond"), TuplesKt.to("REPEAT_UNTIL_CONDITION", "cond"), TuplesKt.to("VARIABLE_CHANGE", "val"), TuplesKt.to("VARIABLE", "val"), TuplesKt.to("OPEN_URL", "url"), TuplesKt.to("OPEN_URL_STRING", "url"), TuplesKt.to("DURATION", "duration"), TuplesKt.to("VOLUME", "volume"));
        String upperCase = fieldName.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        String str = (String) mapMapOf.get(upperCase);
        return str == null ? toSnakeCase(fieldName) : str;
    }

    private final String toSnakeCase(String s) {
        String lowerCase = new Regex("([a-z0-9])([A-Z])").replace(s, "$1_$2").toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return lowerCase;
    }

    private final String toCamelCase(String s) {
        return CollectionsKt.joinToString$default(StringsKt.split$default((CharSequence) s, new String[]{"_"}, false, 0, 6, (Object) null), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.catrobat.catroid.ai.KoveContextGenerator.toCamelCase.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String part) {
                Intrinsics.checkNotNullParameter(part, "part");
                if (part.length() > 0) {
                    StringBuilder sb = new StringBuilder();
                    char cCharAt = part.charAt(0);
                    StringBuilder sbAppend = sb.append((Object) (Character.isLowerCase(cCharAt) ? CharsKt.titlecase(cCharAt) : String.valueOf(cCharAt)));
                    String strSubstring = part.substring(1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    part = sbAppend.append(strSubstring).toString();
                }
                return part;
            }
        }, 30, null);
    }
}
