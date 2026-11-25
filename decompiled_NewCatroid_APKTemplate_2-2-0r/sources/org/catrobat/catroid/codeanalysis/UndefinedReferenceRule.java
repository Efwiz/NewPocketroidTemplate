package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/UndefinedReferenceRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: UndefinedReferenceRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J.\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/UndefinedReferenceRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context2", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "scanForUndefined", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "declaredVars", "", "", "declaredLists", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UndefinedReferenceRule implements AnalysisRule {
    private final Context context;

    public UndefinedReferenceRule(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context2) {
        Project currentProject;
        Sprite currentSprite;
        ArrayList arrayListEmptyList;
        List listEmptyList;
        ArrayList arrayListEmptyList2;
        List listEmptyList2;
        AnalysisResult analysisResultScanForUndefined;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context2, "context2");
        if (!(brick instanceof FormulaBrick) || (currentProject = ProjectManager.getInstance().getCurrentProject()) == null || (currentSprite = ProjectManager.getInstance().getCurrentSprite()) == null) {
            return null;
        }
        List<UserVariable> userVariables = currentProject.getUserVariables();
        if (userVariables != null) {
            List<UserVariable> list = userVariables;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((UserVariable) it.next()).getName());
            }
            arrayListEmptyList = arrayList;
        } else {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        List list2 = arrayListEmptyList;
        List<UserVariable> userVariables2 = currentSprite.getUserVariables();
        if (userVariables2 != null) {
            List<UserVariable> list3 = userVariables2;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            Iterator<T> it2 = list3.iterator();
            while (it2.hasNext()) {
                arrayList2.add(((UserVariable) it2.next()).getName());
            }
            listEmptyList = arrayList2;
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        List<String> listPlus = CollectionsKt.plus((Collection) list2, listEmptyList);
        List<UserList> userLists = currentProject.getUserLists();
        if (userLists != null) {
            List<UserList> list4 = userLists;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            Iterator<T> it3 = list4.iterator();
            while (it3.hasNext()) {
                arrayList3.add(((UserList) it3.next()).getName());
            }
            arrayListEmptyList2 = arrayList3;
        } else {
            arrayListEmptyList2 = CollectionsKt.emptyList();
        }
        List list5 = arrayListEmptyList2;
        List<UserList> userLists2 = currentSprite.getUserLists();
        if (userLists2 != null) {
            List<UserList> list6 = userLists2;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
            Iterator<T> it4 = list6.iterator();
            while (it4.hasNext()) {
                arrayList4.add(((UserList) it4.next()).getName());
            }
            listEmptyList2 = arrayList4;
        } else {
            listEmptyList2 = CollectionsKt.emptyList();
        }
        List<String> listPlus2 = CollectionsKt.plus((Collection) list5, listEmptyList2);
        Iterator<Formula> it5 = ((FormulaBrick) brick).getAllFormulaFieldsWithFormulas().values().iterator();
        while (it5.hasNext()) {
            FormulaElement root = it5.next().getRoot();
            if (root != null && (analysisResultScanForUndefined = scanForUndefined(root, listPlus, listPlus2)) != null) {
                return analysisResultScanForUndefined;
            }
        }
        return null;
    }

    private final AnalysisResult scanForUndefined(FormulaElement element, List<String> declaredVars, List<String> declaredLists) {
        AnalysisResult analysisResultScanForUndefined;
        AnalysisResult analysisResultScanForUndefined2;
        String string;
        String string2;
        String str = "";
        if (element.type == FormulaElement.ElementType.USER_VARIABLE) {
            String value = element.getValue();
            if (value == null || (string2 = value.toString()) == null) {
                string2 = "";
            }
            if (!StringsKt.isBlank(string2) && !declaredVars.contains(string2)) {
                Severity severity = Severity.ERROR;
                String string3 = this.context.getString(R.string.analysis_error_undefined_variable, string2);
                Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                return new AnalysisResult(severity, string3);
            }
        }
        if (element.type == FormulaElement.ElementType.USER_LIST) {
            String value2 = element.getValue();
            if (value2 != null && (string = value2.toString()) != null) {
                str = string;
            }
            if (!StringsKt.isBlank(str) && !declaredLists.contains(str)) {
                Severity severity2 = Severity.ERROR;
                String string4 = this.context.getString(R.string.analysis_error_undefined_list, str);
                Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                return new AnalysisResult(severity2, string4);
            }
        }
        FormulaElement formulaElement = element.leftChild;
        if (formulaElement != null && (analysisResultScanForUndefined2 = scanForUndefined(formulaElement, declaredVars, declaredLists)) != null) {
            return analysisResultScanForUndefined2;
        }
        FormulaElement formulaElement2 = element.rightChild;
        if (formulaElement2 != null && (analysisResultScanForUndefined = scanForUndefined(formulaElement2, declaredVars, declaredLists)) != null) {
            return analysisResultScanForUndefined;
        }
        for (FormulaElement formulaElement3 : element.additionalChildren) {
            Intrinsics.checkNotNull(formulaElement3);
            AnalysisResult analysisResultScanForUndefined3 = scanForUndefined(formulaElement3, declaredVars, declaredLists);
            if (analysisResultScanForUndefined3 != null) {
                return analysisResultScanForUndefined3;
            }
        }
        return null;
    }
}
