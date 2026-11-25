package org.catrobat.catroid.ai;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveModelConverter.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveModelConverter.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0012\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\"\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00102\u0006\u0010\u0017\u001a\u00020\u0018J\u001c\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0002J\u001c\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0002J\u000e\u0010 \u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0018\u0010!\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J&\u0010\"\u001a\u0004\u0018\u00010\u00012\u0006\u0010#\u001a\u00020\u00012\n\u0010$\u001a\u0006\u0012\u0002\b\u00030%2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0012\u0010&\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u001c\u0010'\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u001a0\u00042\u0006\u0010(\u001a\u00020\u0011H\u0002J\u001c\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00042\u0006\u0010(\u001a\u00020\u0011H\u0002J\u0010\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u0005H\u0002J\u0012\u0010,\u001a\u0004\u0018\u00010\u00112\u0006\u0010-\u001a\u00020\u0005H\u0002J,\u0010.\u001a\u00020\f2\u0006\u0010(\u001a\u00020\u00112\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001c0\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J \u00100\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\u00112\u0006\u00101\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u001aH\u0002J(\u00103\u001a\u00020\f2\u0006\u0010(\u001a\u00020\u00112\u0006\u0010+\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u00104\u001a\u00020\u00052\u0006\u00105\u001a\u00020\u0005H\u0002J\u0010\u00106\u001a\u00020\u00052\u0006\u00105\u001a\u00020\u0005H\u0002R'\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u00067"}, d2 = {"Lorg/catrobat/catroid/ai/KoveModelConverter;", "", "()V", "shortToBrickFieldMap", "", "", "Lorg/catrobat/catroid/content/bricks/Brick$BrickField;", "getShortToBrickFieldMap", "()Ljava/util/Map;", "shortToBrickFieldMap$delegate", "Lkotlin/Lazy;", "addNestedBricksToComposite", "", "composite", "Lorg/catrobat/catroid/content/bricks/CompositeBrick;", "bricks", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "secondary", "", "buildBricks", "parsedList", "Lorg/catrobat/catroid/ai/ParsedBrick;", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "buildFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "parsed", "Lorg/catrobat/catroid/ai/ParsedFormulaElement;", "parent", "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "buildFormulaElement", "convertFormula", "convertSingleBrick", "convertValueToType", "value", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "extractConstantFromFormula", "getAllFormulasFromBrick", "brick", "getPrimitiveFields", "getShortParamName", "fieldName", "instantiateBrickByName", "dslName", "populateBrickArguments", "arguments", "setFormulaOnBrick", "field", "formula", "setPrimitiveFieldOnBrick", "toCamelCase", "s", "toCamelCaseField", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KoveModelConverter {
    public static final KoveModelConverter INSTANCE = new KoveModelConverter();

    /* JADX INFO: renamed from: shortToBrickFieldMap$delegate, reason: from kotlin metadata */
    private static final Lazy shortToBrickFieldMap = LazyKt.lazy(new Function0<Map<String, Brick.BrickField>>() { // from class: org.catrobat.catroid.ai.KoveModelConverter$shortToBrickFieldMap$2
        @Override // kotlin.jvm.functions.Function0
        public final Map<String, Brick.BrickField> invoke() {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Brick.BrickField brickField : Brick.BrickField.values()) {
                linkedHashMap.put(KoveModelConverter.INSTANCE.getShortParamName(brickField.name()), brickField);
            }
            return linkedHashMap;
        }
    });

    private KoveModelConverter() {
    }

    private final Map<String, Brick.BrickField> getShortToBrickFieldMap() {
        return (Map) shortToBrickFieldMap.getValue();
    }

    public final List<Brick> buildBricks(List<? extends ParsedBrick> parsedList, Sprite sprite) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(parsedList, "parsedList");
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        ArrayList arrayList = new ArrayList();
        Iterator<? extends ParsedBrick> it = parsedList.iterator();
        while (it.hasNext()) {
            Brick brickConvertSingleBrick = convertSingleBrick(it.next(), sprite);
            if (brickConvertSingleBrick != null) {
                arrayList.add(brickConvertSingleBrick);
            }
        }
        return arrayList;
    }

    public final Brick convertSingleBrick(ParsedBrick parsed, Sprite sprite) throws IllegalAccessException, InvocationTargetException {
        Brick brickInstantiateBrickByName;
        Intrinsics.checkNotNullParameter(parsed, "parsed");
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        if (parsed instanceof ParsedBrick.Simple) {
            ParsedBrick.Simple simple = (ParsedBrick.Simple) parsed;
            brickInstantiateBrickByName = instantiateBrickByName(simple.getName());
            if (brickInstantiateBrickByName != null) {
                populateBrickArguments(brickInstantiateBrickByName, simple.getArguments(), sprite);
            }
        } else if (parsed instanceof ParsedBrick.If) {
            ParsedBrick.If r6 = (ParsedBrick.If) parsed;
            brickInstantiateBrickByName = instantiateBrickByName(r6.getElseBranch() != null ? "if_logic_begin" : "if_then_logic_begin");
            if (brickInstantiateBrickByName != null && (brickInstantiateBrickByName instanceof CompositeBrick)) {
                setFormulaOnBrick(brickInstantiateBrickByName, Brick.BrickField.IF_CONDITION, buildFormula$default(this, r6.getCondition(), null, 2, null));
                CompositeBrick compositeBrick = (CompositeBrick) brickInstantiateBrickByName;
                addNestedBricksToComposite(compositeBrick, buildBricks(r6.getThenBranch(), sprite), false);
                if (r6.getElseBranch() != null && compositeBrick.hasSecondaryList()) {
                    addNestedBricksToComposite(compositeBrick, buildBricks(r6.getElseBranch(), sprite), true);
                }
            }
        } else if (parsed instanceof ParsedBrick.Loop) {
            ParsedBrick.Loop loop = (ParsedBrick.Loop) parsed;
            brickInstantiateBrickByName = instantiateBrickByName(loop.getLoopType());
            if (brickInstantiateBrickByName != null && (brickInstantiateBrickByName instanceof CompositeBrick)) {
                populateBrickArguments(brickInstantiateBrickByName, loop.getArguments(), sprite);
                addNestedBricksToComposite((CompositeBrick) brickInstantiateBrickByName, buildBricks(loop.getBody(), sprite), false);
            }
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return brickInstantiateBrickByName;
    }

    public final Formula convertFormula(ParsedFormulaElement parsed) {
        Intrinsics.checkNotNullParameter(parsed, "parsed");
        return buildFormula$default(this, parsed, null, 2, null);
    }

    static /* synthetic */ Formula buildFormula$default(KoveModelConverter koveModelConverter, ParsedFormulaElement parsedFormulaElement, FormulaElement formulaElement, int i, Object obj) {
        if ((i & 2) != 0) {
            formulaElement = null;
        }
        return koveModelConverter.buildFormula(parsedFormulaElement, formulaElement);
    }

    private final Formula buildFormula(ParsedFormulaElement parsed, FormulaElement parent) {
        return new Formula(buildFormulaElement(parsed, parent));
    }

    static /* synthetic */ FormulaElement buildFormulaElement$default(KoveModelConverter koveModelConverter, ParsedFormulaElement parsedFormulaElement, FormulaElement formulaElement, int i, Object obj) {
        if ((i & 2) != 0) {
            formulaElement = null;
        }
        return koveModelConverter.buildFormulaElement(parsedFormulaElement, formulaElement);
    }

    private final FormulaElement buildFormulaElement(ParsedFormulaElement parsed, FormulaElement parent) {
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.valueOf(parsed.getType().name()), parsed.getValue(), parent);
        ParsedFormulaElement leftChild = parsed.getLeftChild();
        if (leftChild != null) {
            formulaElement.setLeftChild(INSTANCE.buildFormulaElement(leftChild, formulaElement));
        }
        ParsedFormulaElement rightChild = parsed.getRightChild();
        if (rightChild != null) {
            formulaElement.setRightChild(INSTANCE.buildFormulaElement(rightChild, formulaElement));
        }
        Iterator<T> it = parsed.getAdditionalChildren().iterator();
        while (it.hasNext()) {
            formulaElement.addAdditionalChild(INSTANCE.buildFormulaElement((ParsedFormulaElement) it.next(), formulaElement));
        }
        return formulaElement;
    }

    private final Brick instantiateBrickByName(String dslName) {
        String strJoinToString$default = CollectionsKt.joinToString$default(StringsKt.split$default((CharSequence) dslName, new String[]{"_"}, false, 0, 6, (Object) null), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.catrobat.catroid.ai.KoveModelConverter$instantiateBrickByName$camelCaseName$1
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
        Iterator it = CollectionsKt.listOf((Object[]) new String[]{"org.catrobat.catroid.content.bricks." + strJoinToString$default + "Brick", "org.catrobat.catroid.content.bricks." + strJoinToString$default}).iterator();
        while (it.hasNext()) {
            try {
                Constructor<?> declaredConstructor = Class.forName((String) it.next()).getDeclaredConstructor(new Class[0]);
                declaredConstructor.setAccessible(true);
                Object objNewInstance = declaredConstructor.newInstance(new Object[0]);
                Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.Brick");
                return (Brick) objNewInstance;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private final void populateBrickArguments(Brick brick, Map<String, ParsedFormulaElement> arguments, Sprite sprite) throws IllegalAccessException, InvocationTargetException {
        for (Map.Entry<String, ParsedFormulaElement> entry : arguments.entrySet()) {
            String key = entry.getKey();
            ParsedFormulaElement value = entry.getValue();
            if (Intrinsics.areEqual(key, "val")) {
                Set<Brick.BrickField> setKeySet = getAllFormulasFromBrick(brick).keySet();
                if (setKeySet.size() == 1) {
                    setFormulaOnBrick(brick, (Brick.BrickField) CollectionsKt.first(setKeySet), buildFormula$default(this, value, null, 2, null));
                } else {
                    Map<String, Object> primitiveFields = getPrimitiveFields(brick);
                    if (primitiveFields.size() == 1) {
                        key = (String) CollectionsKt.first(primitiveFields.keySet());
                    }
                }
            }
            Brick.BrickField brickField = getShortToBrickFieldMap().get(key);
            if (brickField == null || !setFormulaOnBrick(brick, brickField, buildFormula$default(this, value, null, 2, null))) {
                String camelCaseField = toCamelCaseField(key);
                Object objExtractConstantFromFormula = extractConstantFromFormula(value);
                if (objExtractConstantFromFormula != null) {
                    setPrimitiveFieldOnBrick(brick, camelCaseField, objExtractConstantFromFormula, sprite);
                }
            }
        }
    }

    private final boolean setFormulaOnBrick(Brick brick, Brick.BrickField field, Formula formula) throws IllegalAccessException, InvocationTargetException {
        Method method;
        Class<?> superclass = brick.getClass();
        while (true) {
            method = null;
            if (superclass == null) {
                break;
            }
            String name2 = superclass.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (!StringsKt.startsWith$default(name2, "org.catrobat.catroid", false, 2, (Object) null)) {
                break;
            }
            try {
                Method declaredMethod = superclass.getDeclaredMethod("setFormulaWithBrickField", Brick.BrickField.class, Formula.class);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(brick, field, formula);
                return true;
            } catch (NoSuchMethodException unused) {
                superclass = superclass.getSuperclass();
            }
        }
        String str = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX + toCamelCase(field.name());
        try {
            Method[] methods = brick.getClass().getMethods();
            Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
            for (Method method2 : methods) {
                Method method3 = method2;
                if (StringsKt.equals(method3.getName(), str, true) && method3.getParameterCount() == 1 && Formula.class.isAssignableFrom(method3.getParameterTypes()[0])) {
                    method = method2;
                    break;
                }
            }
            Method method4 = method;
            if (method4 != null) {
                method4.invoke(brick, formula);
                return true;
            }
        } catch (Exception unused2) {
        }
        return false;
    }

    private final void setPrimitiveFieldOnBrick(Brick brick, String fieldName, Object value, Sprite sprite) throws IllegalAccessException, InvocationTargetException {
        String string;
        for (Class<?> superclass = brick.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            String name2 = superclass.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (!StringsKt.startsWith$default(name2, "org.catrobat.catroid", false, 2, (Object) null)) {
                return;
            }
            Method[] declaredMethods = superclass.getDeclaredMethods();
            Intrinsics.checkNotNullExpressionValue(declaredMethods, "getDeclaredMethods(...)");
            for (Method method : declaredMethods) {
                if (fieldName.length() > 0) {
                    StringBuilder sb = new StringBuilder();
                    String strValueOf = String.valueOf(fieldName.charAt(0));
                    Intrinsics.checkNotNull(strValueOf, "null cannot be cast to non-null type java.lang.String");
                    String upperCase = strValueOf.toUpperCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                    StringBuilder sbAppend = sb.append((Object) upperCase);
                    String strSubstring = fieldName.substring(1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    string = sbAppend.append(strSubstring).toString();
                } else {
                    string = fieldName;
                }
                if (StringsKt.equals(method.getName(), FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX + string, true) && method.getParameterCount() == 1) {
                    method.setAccessible(true);
                    Class<?> cls = method.getParameterTypes()[0];
                    Intrinsics.checkNotNullExpressionValue(cls, "get(...)");
                    Object objConvertValueToType = convertValueToType(value, cls, sprite);
                    if (objConvertValueToType != null) {
                        method.invoke(brick, objConvertValueToType);
                        return;
                    }
                }
            }
            Field[] declaredFields = superclass.getDeclaredFields();
            Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
            for (Field field : declaredFields) {
                if (StringsKt.equals(field.getName(), fieldName, true)) {
                    field.setAccessible(true);
                    try {
                        Class<?> type = field.getType();
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                        Object objConvertValueToType2 = convertValueToType(value, type, sprite);
                        if (objConvertValueToType2 != null) {
                            field.set(brick, objConvertValueToType2);
                            return;
                        }
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                }
            }
        }
    }

    private final Object convertValueToType(Object value, Class<?> type, Sprite sprite) {
        Integer numValueOf;
        Object next;
        Serializable serializable;
        Object next2;
        try {
            if (Intrinsics.areEqual(type, Integer.TYPE) || Intrinsics.areEqual(type, Integer.TYPE)) {
                numValueOf = Integer.valueOf((int) Double.parseDouble(value.toString()));
            } else if (Intrinsics.areEqual(type, Double.TYPE) || Intrinsics.areEqual(type, Double.TYPE)) {
                numValueOf = Double.valueOf(Double.parseDouble(value.toString()));
            } else if (Intrinsics.areEqual(type, Float.TYPE) || Intrinsics.areEqual(type, Float.TYPE)) {
                numValueOf = Float.valueOf(Float.parseFloat(value.toString()));
            } else if (Intrinsics.areEqual(type, Boolean.TYPE) || Intrinsics.areEqual(type, Boolean.TYPE)) {
                numValueOf = Boolean.valueOf(Boolean.parseBoolean(value.toString()));
            } else if (Intrinsics.areEqual(type, String.class)) {
                numValueOf = StringsKt.trim(value.toString(), '\"');
            } else {
                String name2 = type.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (StringsKt.contains$default((CharSequence) name2, (CharSequence) "LookData", false, 2, (Object) null)) {
                    String strTrim = StringsKt.trim(value.toString(), '\"');
                    List<LookData> lookList = sprite.getLookList();
                    Intrinsics.checkNotNullExpressionValue(lookList, "getLookList(...)");
                    Iterator<T> it = lookList.iterator();
                    do {
                        if (!it.hasNext()) {
                            next2 = null;
                            break;
                        }
                        next2 = it.next();
                    } while (!Intrinsics.areEqual(((LookData) next2).f512name, strTrim));
                    serializable = (Serializable) next2;
                } else {
                    String name3 = type.getName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    if (!StringsKt.contains$default((CharSequence) name3, (CharSequence) "SoundInfo", false, 2, (Object) null)) {
                        return null;
                    }
                    String strTrim2 = StringsKt.trim(value.toString(), '\"');
                    List<SoundInfo> soundList = sprite.getSoundList();
                    Intrinsics.checkNotNullExpressionValue(soundList, "getSoundList(...)");
                    Iterator<T> it2 = soundList.iterator();
                    do {
                        if (!it2.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it2.next();
                    } while (!Intrinsics.areEqual(((SoundInfo) next).f517name, strTrim2));
                    serializable = (Serializable) next;
                }
                return serializable;
            }
            return numValueOf;
        } catch (Exception unused) {
            return null;
        }
    }

    private final void addNestedBricksToComposite(CompositeBrick composite, List<? extends Brick> bricks, boolean secondary) {
        List<Brick> nestedBricks;
        try {
            Method method = composite.getClass().getMethod(secondary ? "addSecondaryBrick" : "addBrick", Brick.class);
            Iterator<? extends Brick> it = bricks.iterator();
            while (it.hasNext()) {
                method.invoke(composite, it.next());
            }
        } catch (Exception unused) {
            if (secondary) {
                nestedBricks = composite.getSecondaryNestedBricks();
            } else {
                nestedBricks = composite.getNestedBricks();
            }
            if (nestedBricks == null) {
                nestedBricks = null;
            }
            if (nestedBricks != null) {
                nestedBricks.addAll(bricks);
            }
        }
    }

    private final Object extractConstantFromFormula(ParsedFormulaElement parsed) {
        if (parsed.getType() == FormulaElementType.NUMBER || parsed.getType() == FormulaElementType.STRING) {
            return parsed.getValue();
        }
        return null;
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

    /* JADX INFO: Access modifiers changed from: private */
    public final String getShortParamName(String fieldName) {
        Map mapMapOf = MapsKt.mapOf(TuplesKt.to("VALUE", "val"), TuplesKt.to("VALUE_1", "val1"), TuplesKt.to("VALUE_2", "val2"), TuplesKt.to("VALUE_3", "val3"), TuplesKt.to("TIMES_TO_REPEAT", "times"), TuplesKt.to("INTERVAL", "interval"), TuplesKt.to("X_POSITION", "x"), TuplesKt.to("Y_POSITION", "y"), TuplesKt.to("SIZE", "size"), TuplesKt.to("COLOR", TypedValues.Custom.S_COLOR), TuplesKt.to("STEPS", "steps"), TuplesKt.to("DEGREES", "degrees"), TuplesKt.to("IF_CONDITION", "cond"), TuplesKt.to("REPEAT_UNTIL_CONDITION", "cond"), TuplesKt.to("VARIABLE_CHANGE", "val"), TuplesKt.to("VARIABLE", "val"), TuplesKt.to("OPEN_URL", "url"), TuplesKt.to("OPEN_URL_STRING", "url"), TuplesKt.to("DURATION", "duration"), TuplesKt.to("VOLUME", "volume"));
        String upperCase = fieldName.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        String str = (String) mapMapOf.get(upperCase);
        if (str != null) {
            return str;
        }
        String lowerCase = fieldName.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return lowerCase;
    }

    private final String toCamelCase(String s) {
        return CollectionsKt.joinToString$default(StringsKt.split$default((CharSequence) s, new String[]{"_"}, false, 0, 6, (Object) null), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.catrobat.catroid.ai.KoveModelConverter.toCamelCase.1
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

    private final String toCamelCaseField(String s) {
        List listSplit$default = StringsKt.split$default((CharSequence) s, new String[]{"_"}, false, 0, 6, (Object) null);
        if (listSplit$default.isEmpty()) {
            return "";
        }
        String lowerCase = ((String) listSplit$default.get(0)).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return lowerCase + CollectionsKt.joinToString$default(CollectionsKt.drop(listSplit$default, 1), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.catrobat.catroid.ai.KoveModelConverter$toCamelCaseField$rest$1
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
