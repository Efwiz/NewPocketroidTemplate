package org.catrobat.catroid.formulaeditor;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/CustomFormulaManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: CustomFormula.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005J\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\tJ\u000e\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\rR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/CustomFormulaManager;", "", "()V", "formulas", "", "Lorg/catrobat/catroid/formulaeditor/CustomFormula;", "getFormulas", "()Ljava/util/List;", "addFormula", "", "formula", "getFormulaByUniqueName", "name", "", "initialize", "removeFormulasByOwner", "libraryId", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CustomFormulaManager {
    public static final CustomFormulaManager INSTANCE = new CustomFormulaManager();
    private static final List<CustomFormula> formulas = new ArrayList();

    public final void initialize() {
    }

    private CustomFormulaManager() {
    }

    public final List<CustomFormula> getFormulas() {
        return formulas;
    }

    public final void addFormula(CustomFormula formula) {
        Intrinsics.checkNotNullParameter(formula, "formula");
        List<CustomFormula> list = formulas;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((CustomFormula) it.next()).getUniqueName(), formula.getUniqueName())) {
                    Log.w("CustomFormulaManager", "Попытка добавить дублирующуюся формулу: " + formula.getUniqueName());
                    return;
                }
            }
        }
        if (Functions.isFunction(formula.getUniqueName())) {
            Log.e("CustomFormulaManager", "Имя кастомной формулы '" + formula.getUniqueName() + "' конфликтует с существующей стандартной функцией!");
        } else {
            formulas.add(formula);
            Log.i("CustomFormulaManager", "Добавлена кастомная формула: " + formula.getUniqueName());
        }
    }

    public final CustomFormula getFormulaByUniqueName(String name2) {
        Object next;
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = formulas.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(((CustomFormula) next).getUniqueName(), name2)) {
                return (CustomFormula) next;
            }
        }
        next = null;
        return (CustomFormula) next;
    }

    public final void removeFormulasByOwner(final String libraryId) {
        Intrinsics.checkNotNullParameter(libraryId, "libraryId");
        CollectionsKt.removeAll((List) formulas, (Function1) new Function1<CustomFormula, Boolean>() { // from class: org.catrobat.catroid.formulaeditor.CustomFormulaManager.removeFormulasByOwner.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CustomFormula it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getOwnerLibraryId(), libraryId));
            }
        });
        Log.i("CustomFormulaManager", "Удалены формулы, принадлежащие " + libraryId);
    }
}
