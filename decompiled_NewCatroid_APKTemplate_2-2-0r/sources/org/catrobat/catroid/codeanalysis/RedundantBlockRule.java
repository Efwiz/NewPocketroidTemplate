package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ChangeVariableBrick;
import org.catrobat.catroid.content.bricks.ChangeXByNBrick;
import org.catrobat.catroid.content.bricks.ChangeYByNBrick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.NoteBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PreviousLookBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.content.bricks.SetXBrick;
import org.catrobat.catroid.content.bricks.SetYBrick;
import org.catrobat.catroid.content.bricks.StopScriptBrick;
import org.catrobat.catroid.content.bricks.UserDataBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/RedundantBlockRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: RedundantBlockRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0002J\u0018\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J&\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u001aH\u0002J\u001e\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H\u0002J\u001e\u0010\u001c\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H\u0002J\u001e\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H\u0002J&\u0010\u001e\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H\u0002J\u0018\u0010\u001f\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/codeanalysis/RedundantBlockRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context2", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "areBricksEffectivelyEqual", "", "brick1", "brick2", "hasVariableReference", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "varName", "", "isBrickActionOverwrittenBeforeUse", "startIndex", "", "siblings", "", "isHaltingStopScript", "Lorg/catrobat/catroid/content/bricks/StopScriptBrick;", "isLookOverwrittenBeforeUse", "isPositionXOverwrittenBeforeUse", "isPositionYOverwrittenBeforeUse", "isVariableOverwrittenBeforeUse", "usesVariable", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantBlockRule implements AnalysisRule {
    private final Context context2;

    public RedundantBlockRule(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context2");
        this.context2 = context2;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context) {
        Brick parent;
        List<Brick> dragAndDropTargetList;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((brick instanceof CompositeBrick) || (brick instanceof NoteBrick) || (parent = brick.getParent()) == null || (dragAndDropTargetList = parent.getDragAndDropTargetList()) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : dragAndDropTargetList) {
            if (!CodeAnalyzer.INSTANCE.isBrickCommented((Brick) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        int iIndexOf = arrayList2.indexOf(brick);
        if (iIndexOf == -1) {
            return null;
        }
        if (iIndexOf < arrayList2.size() - 1 && isBrickActionOverwrittenBeforeUse(brick, iIndexOf + 1, arrayList2)) {
            Severity severity = Severity.WARNING;
            String string = this.context2.getString(R.string.analysis_redundant_overwritten);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            return new AnalysisResult(severity, string);
        }
        if (iIndexOf > 0) {
            Brick brick2 = (Brick) arrayList2.get(iIndexOf - 1);
            if (Intrinsics.areEqual(brick.getClass(), brick2.getClass())) {
                Intrinsics.checkNotNull(brick2);
                if (areBricksEffectivelyEqual(brick, brick2)) {
                    Severity severity2 = Severity.WARNING;
                    String string2 = this.context2.getString(R.string.analysis_redundant_same_action);
                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                    return new AnalysisResult(severity2, string2);
                }
            }
        }
        return null;
    }

    private final boolean isBrickActionOverwrittenBeforeUse(Brick brick, int startIndex, List<? extends Brick> siblings) {
        if (brick instanceof SetVariableBrick) {
            UserVariable userVariable = ((SetVariableBrick) brick).getUserVariable();
            String name2 = userVariable != null ? userVariable.getName() : null;
            if (name2 == null) {
                return false;
            }
            return isVariableOverwrittenBeforeUse(name2, startIndex, siblings);
        }
        if (brick instanceof SetXBrick) {
            return isPositionXOverwrittenBeforeUse(startIndex, siblings);
        }
        if (brick instanceof SetYBrick) {
            return isPositionYOverwrittenBeforeUse(startIndex, siblings);
        }
        if (brick instanceof PlaceAtBrick) {
            return isPositionXOverwrittenBeforeUse(startIndex, siblings) && isPositionYOverwrittenBeforeUse(startIndex, siblings);
        }
        if (brick instanceof SetLookBrick) {
            return isLookOverwrittenBeforeUse(startIndex, siblings);
        }
        return false;
    }

    private final boolean isVariableOverwrittenBeforeUse(String varName, int startIndex, List<? extends Brick> siblings) {
        int size = siblings.size();
        while (startIndex < size) {
            Brick brick = siblings.get(startIndex);
            if (usesVariable(brick, varName)) {
                return false;
            }
            if (brick instanceof SetVariableBrick) {
                UserVariable userVariable = ((SetVariableBrick) brick).getUserVariable();
                if (Intrinsics.areEqual(userVariable != null ? userVariable.getName() : null, varName)) {
                    return true;
                }
            }
            if (brick instanceof ChangeVariableBrick) {
                UserVariable userVariable2 = ((ChangeVariableBrick) brick).getUserVariable();
                if (Intrinsics.areEqual(userVariable2 != null ? userVariable2.getName() : null, varName)) {
                    return false;
                }
            }
            if (brick instanceof CompositeBrick) {
                ArrayList arrayList = new ArrayList();
                brick.addToFlatList(arrayList);
                ArrayList arrayList2 = arrayList;
                if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        if (usesVariable((Brick) it.next(), varName)) {
                            return false;
                        }
                    }
                }
            }
            if ((brick instanceof StopScriptBrick) && isHaltingStopScript((StopScriptBrick) brick)) {
                return true;
            }
            startIndex++;
        }
        return false;
    }

    private final boolean isPositionXOverwrittenBeforeUse(int startIndex, List<? extends Brick> siblings) {
        int size = siblings.size();
        while (startIndex < size) {
            Brick brick = siblings.get(startIndex);
            if ((brick instanceof WaitBrick) || (brick instanceof GlideToBrick) || (brick instanceof ChangeXByNBrick) || (brick instanceof MoveNStepsBrick)) {
                break;
            }
            if ((brick instanceof SetXBrick) || (brick instanceof PlaceAtBrick)) {
                return true;
            }
            startIndex++;
        }
        return false;
    }

    private final boolean isPositionYOverwrittenBeforeUse(int startIndex, List<? extends Brick> siblings) {
        int size = siblings.size();
        while (startIndex < size) {
            Brick brick = siblings.get(startIndex);
            if ((brick instanceof WaitBrick) || (brick instanceof GlideToBrick) || (brick instanceof ChangeYByNBrick) || (brick instanceof MoveNStepsBrick)) {
                break;
            }
            if ((brick instanceof SetYBrick) || (brick instanceof PlaceAtBrick)) {
                return true;
            }
            startIndex++;
        }
        return false;
    }

    private final boolean isLookOverwrittenBeforeUse(int startIndex, List<? extends Brick> siblings) {
        int size = siblings.size();
        while (startIndex < size) {
            Brick brick = siblings.get(startIndex);
            if ((brick instanceof WaitBrick) || (brick instanceof NextLookBrick) || (brick instanceof PreviousLookBrick)) {
                break;
            }
            if (brick instanceof SetLookBrick) {
                return true;
            }
            startIndex++;
        }
        return false;
    }

    private final boolean usesVariable(Brick brick, String varName) {
        if (!(brick instanceof FormulaBrick)) {
            return false;
        }
        Iterator<Formula> it = ((FormulaBrick) brick).getAllFormulaFieldsWithFormulas().values().iterator();
        while (it.hasNext()) {
            FormulaElement root = it.next().getRoot();
            if (root != null && hasVariableReference(root, varName)) {
                return true;
            }
        }
        return false;
    }

    private final boolean hasVariableReference(FormulaElement element, String varName) {
        boolean z;
        if (StringsKt.contains$default((CharSequence) element.type.name(), (CharSequence) "VAR", false, 2, (Object) null)) {
            String value = element.getValue();
            if (Intrinsics.areEqual(value != null ? value.toString() : null, varName)) {
                return true;
            }
        }
        FormulaElement formulaElement = element.leftChild;
        boolean zHasVariableReference = formulaElement != null ? hasVariableReference(formulaElement, varName) : false;
        FormulaElement formulaElement2 = element.rightChild;
        boolean zHasVariableReference2 = formulaElement2 != null ? hasVariableReference(formulaElement2, varName) : false;
        List<FormulaElement> additionalChildren = element.additionalChildren;
        Intrinsics.checkNotNullExpressionValue(additionalChildren, "additionalChildren");
        List<FormulaElement> list = additionalChildren;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                FormulaElement formulaElement3 = (FormulaElement) it.next();
                Intrinsics.checkNotNull(formulaElement3);
                if (hasVariableReference(formulaElement3, varName)) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        return zHasVariableReference || zHasVariableReference2 || z;
    }

    private final boolean isHaltingStopScript(StopScriptBrick brick) {
        String string;
        String string2;
        String str = "";
        try {
            try {
                Field declaredField = brick.getClass().getDeclaredField("selection");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(brick);
                if (obj == null || (string2 = obj.toString()) == null) {
                    string2 = "";
                }
                return (StringsKt.contains$default((CharSequence) string2, (CharSequence) "other", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) string2, (CharSequence) "другие", false, 2, (Object) null)) ? false : true;
            } catch (Exception unused) {
                return true;
            }
        } catch (Exception unused2) {
            Field declaredField2 = brick.getClass().getDeclaredField("stopType");
            declaredField2.setAccessible(true);
            Object obj2 = declaredField2.get(brick);
            if (obj2 != null && (string = obj2.toString()) != null) {
                str = string;
            }
            return (StringsKt.contains$default((CharSequence) str, (CharSequence) "other", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "другие", false, 2, (Object) null)) ? false : true;
        }
    }

    private final boolean areBricksEffectivelyEqual(Brick brick1, Brick brick2) {
        if ((brick1 instanceof NoteBrick) || (brick2 instanceof NoteBrick) || !Intrinsics.areEqual(brick1.getClass(), brick2.getClass())) {
            return false;
        }
        try {
            if (!Intrinsics.areEqual(brick1.getClass().getMethod("getUserVariable", new Class[0]).invoke(brick1, new Object[0]), brick2.getClass().getMethod("getUserVariable", new Class[0]).invoke(brick2, new Object[0]))) {
                return false;
            }
        } catch (Exception unused) {
        }
        try {
            if (!Intrinsics.areEqual(brick1.getClass().getMethod("getUserList", new Class[0]).invoke(brick1, new Object[0]), brick2.getClass().getMethod("getUserList", new Class[0]).invoke(brick2, new Object[0]))) {
                return false;
            }
        } catch (Exception unused2) {
        }
        try {
            if (!Intrinsics.areEqual(brick1.getClass().getMethod("getLook", new Class[0]).invoke(brick1, new Object[0]), brick2.getClass().getMethod("getLook", new Class[0]).invoke(brick2, new Object[0]))) {
                return false;
            }
        } catch (Exception unused3) {
        }
        try {
            if (!Intrinsics.areEqual(brick1.getClass().getMethod("getSound", new Class[0]).invoke(brick1, new Object[0]), brick2.getClass().getMethod("getSound", new Class[0]).invoke(brick2, new Object[0]))) {
                return false;
            }
        } catch (Exception unused4) {
        }
        if ((brick1 instanceof FormulaBrick) && (brick2 instanceof FormulaBrick)) {
            Map<Brick.BrickField, Formula> allFormulaFieldsWithFormulas = ((FormulaBrick) brick1).getAllFormulaFieldsWithFormulas();
            Map<Brick.BrickField, Formula> allFormulaFieldsWithFormulas2 = ((FormulaBrick) brick2).getAllFormulaFieldsWithFormulas();
            if (allFormulaFieldsWithFormulas.size() != allFormulaFieldsWithFormulas2.size()) {
                return false;
            }
            if (allFormulaFieldsWithFormulas.isEmpty()) {
                return true;
            }
            Intrinsics.checkNotNull(allFormulaFieldsWithFormulas);
            if (!allFormulaFieldsWithFormulas.isEmpty()) {
                for (Map.Entry<Brick.BrickField, Formula> entry : allFormulaFieldsWithFormulas.entrySet()) {
                    Brick.BrickField key = entry.getKey();
                    Formula value = entry.getValue();
                    Formula formula = allFormulaFieldsWithFormulas2.get(key);
                    if (formula == null || !Intrinsics.areEqual(value.getTrimmedFormulaString(this.context2), formula.getTrimmedFormulaString(this.context2))) {
                        return false;
                    }
                }
            }
            return true;
        }
        if ((brick1 instanceof UserDataBrick) && (brick2 instanceof UserDataBrick)) {
            Map<Brick.BrickData, UserData> allBrickDataWithValues = ((UserDataBrick) brick1).getAllBrickDataWithValues();
            Map<Brick.BrickData, UserData> allBrickDataWithValues2 = ((UserDataBrick) brick2).getAllBrickDataWithValues();
            if (allBrickDataWithValues.size() != allBrickDataWithValues2.size()) {
                return false;
            }
            if (allBrickDataWithValues.isEmpty()) {
                return true;
            }
            Intrinsics.checkNotNull(allBrickDataWithValues);
            if (!allBrickDataWithValues.isEmpty()) {
                for (Map.Entry<Brick.BrickData, UserData> entry2 : allBrickDataWithValues.entrySet()) {
                    Brick.BrickData key2 = entry2.getKey();
                    UserData value2 = entry2.getValue();
                    UserData userData = allBrickDataWithValues2.get(key2);
                    if (value2 == null) {
                        value2 = null;
                    }
                    String name2 = value2 != null ? value2.getName() : null;
                    if (userData == null) {
                        userData = null;
                    }
                    String name3 = userData != null ? userData.getName() : null;
                    if (name2 != null || name3 != null) {
                        if (name2 == null || !Intrinsics.areEqual(name2, name3)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        if (!(brick1 instanceof SetLookBrick) || !(brick2 instanceof SetLookBrick)) {
            return true;
        }
        LookData look = ((SetLookBrick) brick1).getLook();
        String str = look != null ? look.f512name : null;
        LookData look2 = ((SetLookBrick) brick2).getLook();
        return Intrinsics.areEqual(str, look2 != null ? look2.f512name : null);
    }
}
