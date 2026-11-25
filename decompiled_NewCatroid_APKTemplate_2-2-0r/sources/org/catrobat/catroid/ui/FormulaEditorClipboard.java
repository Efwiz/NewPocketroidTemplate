package org.catrobat.catroid.ui;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.formulaeditor.FormulaEditorEditText;
import org.catrobat.catroid.formulaeditor.InternFormula;
import org.catrobat.catroid.formulaeditor.InternToken;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/FormulaEditorClipboard.class
  classes14.dex
 */
/* JADX INFO: compiled from: FormulaEditorClipboard.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002J\u000e\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/ui/FormulaEditorClipboard;", "", "()V", "MAX_HISTORY_SIZE", "", "history", "Ljava/util/ArrayList;", "Lorg/catrobat/catroid/ui/FormulaEditorClipboard$ClipboardItem;", "checkIfSelectedAndCopy", "", "editText", "Lorg/catrobat/catroid/formulaeditor/FormulaEditorEditText;", "cloneTokens", "", "Lorg/catrobat/catroid/formulaeditor/InternToken;", "tokens", "paste", "showClipboardHistoryDialog", "context", "Landroid/content/Context;", "ClipboardItem", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FormulaEditorClipboard {
    private static final int MAX_HISTORY_SIZE = 10;
    public static final FormulaEditorClipboard INSTANCE = new FormulaEditorClipboard();
    private static final ArrayList<ClipboardItem> history = new ArrayList<>();

    private FormulaEditorClipboard() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/FormulaEditorClipboard$ClipboardItem.class
      classes14.dex
     */
    /* JADX INFO: compiled from: FormulaEditorClipboard.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/ui/FormulaEditorClipboard$ClipboardItem;", "", "tokens", "", "Lorg/catrobat/catroid/formulaeditor/InternToken;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "", "(Ljava/util/List;Ljava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "getTokens", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ClipboardItem {
        private final String label;
        private final List<InternToken> tokens;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ClipboardItem copy$default(ClipboardItem clipboardItem, List list, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                list = clipboardItem.tokens;
            }
            if ((i & 2) != 0) {
                str = clipboardItem.label;
            }
            return clipboardItem.copy(list, str);
        }

        public final List<InternToken> component1() {
            return this.tokens;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getLabel() {
            return this.label;
        }

        public final ClipboardItem copy(List<? extends InternToken> tokens, String label) {
            Intrinsics.checkNotNullParameter(tokens, "tokens");
            Intrinsics.checkNotNullParameter(label, "label");
            return new ClipboardItem(tokens, label);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ClipboardItem)) {
                return false;
            }
            ClipboardItem clipboardItem = (ClipboardItem) other;
            return Intrinsics.areEqual(this.tokens, clipboardItem.tokens) && Intrinsics.areEqual(this.label, clipboardItem.label);
        }

        public int hashCode() {
            return (this.tokens.hashCode() * 31) + this.label.hashCode();
        }

        public String toString() {
            return "ClipboardItem(tokens=" + this.tokens + ", label=" + this.label + Constant.AFTER_QUTO;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ClipboardItem(List<? extends InternToken> tokens, String label) {
            Intrinsics.checkNotNullParameter(tokens, "tokens");
            Intrinsics.checkNotNullParameter(label, "label");
            this.tokens = tokens;
            this.label = label;
        }

        public final String getLabel() {
            return this.label;
        }

        public final List<InternToken> getTokens() {
            return this.tokens;
        }
    }

    private final List<InternToken> cloneTokens(List<? extends InternToken> tokens) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = tokens.iterator();
        while (it.hasNext()) {
            arrayList.add(((InternToken) it.next()).deepCopy());
        }
        return arrayList;
    }

    public final void checkIfSelectedAndCopy(FormulaEditorEditText editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        List<InternToken> selectedTokens = editText.getSelectedTokens();
        if (selectedTokens == null) {
            InternFormula internFormula = editText.getInternFormula();
            selectedTokens = internFormula != null ? internFormula.getInternTokenFormulaList() : null;
        }
        if (selectedTokens == null || selectedTokens.isEmpty()) {
            return;
        }
        String stringFromInternFormula = editText.getStringFromInternFormula();
        if (stringFromInternFormula == null) {
            stringFromInternFormula = "";
        }
        final String str = "Formula";
        if (editText.getSelectedTokens() != null) {
            InternFormula internFormula2 = editText.getInternFormula();
            int externSelectionStartIndex = internFormula2 != null ? internFormula2.getExternSelectionStartIndex() : -1;
            InternFormula internFormula3 = editText.getInternFormula();
            int externSelectionEndIndex = internFormula3 != null ? internFormula3.getExternSelectionEndIndex() : -1;
            if (externSelectionStartIndex >= 0 && externSelectionStartIndex <= externSelectionEndIndex && externSelectionEndIndex <= stringFromInternFormula.length()) {
                String strSubstring = stringFromInternFormula.substring(externSelectionStartIndex, externSelectionEndIndex);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                String string = StringsKt.trim((CharSequence) strSubstring).toString();
                str = string.length() != 0 ? string : "Formula";
            } else {
                String selectedTextFromInternFormula = editText.getSelectedTextFromInternFormula();
                String str2 = selectedTextFromInternFormula;
                if (str2 != null && str2.length() != 0) {
                    Intrinsics.checkNotNull(selectedTextFromInternFormula);
                    str = selectedTextFromInternFormula;
                }
            }
        } else {
            String string2 = StringsKt.trim((CharSequence) stringFromInternFormula).toString();
            str = string2.length() != 0 ? string2 : "Formula";
        }
        ClipboardItem clipboardItem = new ClipboardItem(cloneTokens(selectedTokens), str);
        ArrayList<ClipboardItem> arrayList = history;
        CollectionsKt.removeAll((List) arrayList, (Function1) new Function1<ClipboardItem, Boolean>() { // from class: org.catrobat.catroid.ui.FormulaEditorClipboard.checkIfSelectedAndCopy.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ClipboardItem it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getLabel(), str));
            }
        });
        arrayList.add(0, clipboardItem);
        if (arrayList.size() > 10) {
            arrayList.remove(arrayList.size() - 1);
        }
    }

    public final void paste(FormulaEditorEditText editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        ArrayList<ClipboardItem> arrayList = history;
        if (arrayList.isEmpty()) {
            return;
        }
        editText.addTokens(cloneTokens(arrayList.get(0).getTokens()));
    }

    public final void showClipboardHistoryDialog(Context context, final FormulaEditorEditText editText) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(editText, "editText");
        ArrayList<ClipboardItem> arrayList = history;
        if (arrayList.isEmpty()) {
            new AlertDialog.Builder(context).setTitle(R.string.paste).setMessage(R.string.formula_nothing_selected).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
            return;
        }
        ArrayList<ClipboardItem> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator<T> it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((ClipboardItem) it.next()).getLabel());
        }
        new AlertDialog.Builder(context).setTitle(R.string.paste).setItems((String[]) arrayList3.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.FormulaEditorClipboard$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FormulaEditorClipboard.showClipboardHistoryDialog$lambda$4(editText, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showClipboardHistoryDialog$lambda$4(FormulaEditorEditText editText, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(editText, "$editText");
        FormulaEditorClipboard formulaEditorClipboard = INSTANCE;
        ArrayList<ClipboardItem> arrayList = history;
        editText.addTokens(formulaEditorClipboard.cloneTokens(arrayList.get(i).getTokens()));
        ClipboardItem clipboardItemRemove = arrayList.remove(i);
        Intrinsics.checkNotNullExpressionValue(clipboardItemRemove, "removeAt(...)");
        arrayList.add(0, clipboardItemRemove);
    }
}
