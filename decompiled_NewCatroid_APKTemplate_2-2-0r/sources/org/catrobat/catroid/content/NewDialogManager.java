package org.catrobat.catroid.content;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/NewDialogManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: NewDialogManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/content/NewDialogManager;", "", "()V", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewDialogManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Map<String, Companion.DialogData> dialogMap = new LinkedHashMap();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/NewDialogManager$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: NewDialogManager.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u001aB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u0005J\u0016\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005J\u001e\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005J1\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0013\u0012\b\b\t\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\b0\u0012J\u0016\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005J\u0016\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005J\u0016\u0010\u0018\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005J\u000e\u0010\u0019\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/content/NewDialogManager$Companion;", "", "()V", "dialogMap", "", "", "Lorg/catrobat/catroid/content/NewDialogManager$Companion$DialogData;", "addEditText", "", "name", "defaultValue", "addRadio", "option", "createEmptyDialog", "title", CrashHianalyticsData.MESSAGE, "setCallback", "callback", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "result", "setNegativeButton", "buttonText", "setNeutralButton", "setPositiveButton", "showDialog", "DialogData", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void createEmptyDialog(String name2, String title, String message) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(message, "message");
            NewDialogManager.dialogMap.put(name2, new DialogData(title, message));
        }

        public final void setPositiveButton(String name2, String buttonText) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(buttonText, "buttonText");
            DialogData dialogData = (DialogData) NewDialogManager.dialogMap.get(name2);
            if (dialogData == null) {
                return;
            }
            dialogData.setPositiveButtonText(buttonText);
        }

        public final void setNegativeButton(String name2, String buttonText) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(buttonText, "buttonText");
            DialogData dialogData = (DialogData) NewDialogManager.dialogMap.get(name2);
            if (dialogData == null) {
                return;
            }
            dialogData.setNegativeButtonText(buttonText);
        }

        public final void setNeutralButton(String name2, String buttonText) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(buttonText, "buttonText");
            DialogData dialogData = (DialogData) NewDialogManager.dialogMap.get(name2);
            if (dialogData == null) {
                return;
            }
            dialogData.setNeutralButtonText(buttonText);
        }

        public static /* synthetic */ void addEditText$default(Companion companion, String str, String str2, int i, Object obj) {
            if ((i & 2) != 0) {
                str2 = "";
            }
            companion.addEditText(str, str2);
        }

        public final void addEditText(String name2, String defaultValue) {
            List<String> inputs;
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
            DialogData dialogData = (DialogData) NewDialogManager.dialogMap.get(name2);
            if (dialogData == null || (inputs = dialogData.getInputs()) == null) {
                return;
            }
            inputs.add(defaultValue);
        }

        public final void addRadio(String name2, String option) {
            List<String> radioOptions;
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(option, "option");
            DialogData dialogData = (DialogData) NewDialogManager.dialogMap.get(name2);
            if (dialogData == null || (radioOptions = dialogData.getRadioOptions()) == null) {
                return;
            }
            radioOptions.add(option);
        }

        public final void setCallback(String name2, Function1<? super String, Unit> callback) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(callback, "callback");
            DialogData dialogData = (DialogData) NewDialogManager.dialogMap.get(name2);
            if (dialogData == null) {
                return;
            }
            dialogData.setCallback(callback);
        }

        public final void showDialog(String name2) {
            final DialogData dialogData;
            Intrinsics.checkNotNullParameter(name2, "name");
            final Activity stage_activity = MyActivityManager.INSTANCE.getStage_activity();
            if (stage_activity == null || (dialogData = (DialogData) NewDialogManager.dialogMap.get(name2)) == null) {
                return;
            }
            stage_activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.NewDialogManager$Companion$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    NewDialogManager.Companion.showDialog$lambda$10(stage_activity, dialogData);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v5, types: [T, android.widget.RadioGroup] */
        public static final void showDialog$lambda$10(Activity context, final DialogData dialogData) {
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(dialogData, "$dialogData");
            Activity activity = context;
            LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.setOrientation(1);
            final ArrayList arrayList = new ArrayList();
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            for (String str : dialogData.getInputs()) {
                AppCompatEditText appCompatEditText = new AppCompatEditText(activity);
                appCompatEditText.setText(str);
                appCompatEditText.setTextColor(-1);
                appCompatEditText.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                arrayList.add(appCompatEditText);
                linearLayout.addView(appCompatEditText);
            }
            if (!dialogData.getRadioOptions().isEmpty()) {
                ?? radioGroup = new RadioGroup(activity);
                radioGroup.setLayoutParams(new RadioGroup.LayoutParams(-1, -2));
                radioGroup.setOrientation(1);
                radioGroup.setPadding(radioGroup.getResources().getDimensionPixelSize(R.dimen.dialog_content_area_padding), radioGroup.getResources().getDimensionPixelSize(R.dimen.dialog_content_area_padding), radioGroup.getResources().getDimensionPixelSize(R.dimen.dialog_content_area_padding_input), radioGroup.getResources().getDimensionPixelSize(R.dimen.dialog_content_area_padding_input));
                objectRef.element = radioGroup;
                for (String str2 : dialogData.getRadioOptions()) {
                    RadioButton radioButton = new RadioButton(activity);
                    radioButton.setText(str2);
                    radioButton.setTextColor(-1);
                    ((RadioGroup) objectRef.element).addView(radioButton);
                }
                linearLayout.addView((View) objectRef.element);
            }
            AlertDialog.Builder view = new AlertDialog.Builder(activity).setTitle(dialogData.getTitle()).setMessage(dialogData.getMessage()).setCancelable(false).setView(linearLayout);
            String positiveButtonText = dialogData.getPositiveButtonText();
            if (positiveButtonText != null) {
                view.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.NewDialogManager$Companion$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        NewDialogManager.Companion.showDialog$lambda$10$lambda$5$lambda$4(dialogData, objectRef, arrayList, dialogInterface, i);
                    }
                });
            }
            String negativeButtonText = dialogData.getNegativeButtonText();
            if (negativeButtonText != null) {
                view.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.NewDialogManager$Companion$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        NewDialogManager.Companion.showDialog$lambda$10$lambda$7$lambda$6(dialogData, objectRef, arrayList, dialogInterface, i);
                    }
                });
            }
            String neutralButtonText = dialogData.getNeutralButtonText();
            if (neutralButtonText != null) {
                view.setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.NewDialogManager$Companion$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        NewDialogManager.Companion.showDialog$lambda$10$lambda$9$lambda$8(dialogData, objectRef, arrayList, dialogInterface, i);
                    }
                });
            }
            view.create().show();
        }

        private static final String showDialog$lambda$10$getEnteredValue(Ref.ObjectRef<RadioGroup> objectRef, List<EditText> list) {
            StringBuilder sb = new StringBuilder();
            if (objectRef.element != null) {
                RadioGroup radioGroup = objectRef.element;
                Intrinsics.checkNotNull(radioGroup);
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId != -1) {
                    RadioGroup radioGroup2 = objectRef.element;
                    Intrinsics.checkNotNull(radioGroup2);
                    sb.append(((RadioButton) radioGroup2.findViewById(checkedRadioButtonId)).getText()).append("\n");
                } else {
                    sb.append("\n");
                }
            }
            Iterator<EditText> it = list.iterator();
            while (it.hasNext()) {
                sb.append(it.next().getText().toString()).append("\n");
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return StringsKt.trim((CharSequence) string).toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void showDialog$lambda$10$lambda$5$lambda$4(DialogData dialogData, Ref.ObjectRef radioGroup, List editTextFields, DialogInterface dialogInterface, int i) {
            Intrinsics.checkNotNullParameter(dialogData, "$dialogData");
            Intrinsics.checkNotNullParameter(radioGroup, "$radioGroup");
            Intrinsics.checkNotNullParameter(editTextFields, "$editTextFields");
            dialogInterface.dismiss();
            Function1<String, Unit> callback = dialogData.getCallback();
            if (callback != null) {
                callback.invoke("1\n" + showDialog$lambda$10$getEnteredValue(radioGroup, editTextFields));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void showDialog$lambda$10$lambda$7$lambda$6(DialogData dialogData, Ref.ObjectRef radioGroup, List editTextFields, DialogInterface dialogInterface, int i) {
            Intrinsics.checkNotNullParameter(dialogData, "$dialogData");
            Intrinsics.checkNotNullParameter(radioGroup, "$radioGroup");
            Intrinsics.checkNotNullParameter(editTextFields, "$editTextFields");
            dialogInterface.dismiss();
            Function1<String, Unit> callback = dialogData.getCallback();
            if (callback != null) {
                callback.invoke("-1\n" + showDialog$lambda$10$getEnteredValue(radioGroup, editTextFields));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void showDialog$lambda$10$lambda$9$lambda$8(DialogData dialogData, Ref.ObjectRef radioGroup, List editTextFields, DialogInterface dialogInterface, int i) {
            Intrinsics.checkNotNullParameter(dialogData, "$dialogData");
            Intrinsics.checkNotNullParameter(radioGroup, "$radioGroup");
            Intrinsics.checkNotNullParameter(editTextFields, "$editTextFields");
            dialogInterface.dismiss();
            Function1<String, Unit> callback = dialogData.getCallback();
            if (callback != null) {
                callback.invoke("0\n" + showDialog$lambda$10$getEnteredValue(radioGroup, editTextFields));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/NewDialogManager$Companion$DialogData.class
          classes14.dex
         */
        /* JADX INFO: compiled from: NewDialogManager.kt */
        @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0014\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R7\u0010\u0006\u001a\u001f\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0015\"\u0004\b\u001a\u0010\u0017R\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0015\"\u0004\b\u001d\u0010\u0017R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0015\"\u0004\b \u0010\u0017R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0013R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0015\"\u0004\b$\u0010\u0017¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/content/NewDialogManager$Companion$DialogData;", "", "title", "", CrashHianalyticsData.MESSAGE, "(Ljava/lang/String;Ljava/lang/String;)V", "callback", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "result", "", "getCallback", "()Lkotlin/jvm/functions/Function1;", "setCallback", "(Lkotlin/jvm/functions/Function1;)V", "inputs", "", "getInputs", "()Ljava/util/List;", "getMessage", "()Ljava/lang/String;", "setMessage", "(Ljava/lang/String;)V", "negativeButtonText", "getNegativeButtonText", "setNegativeButtonText", "neutralButtonText", "getNeutralButtonText", "setNeutralButtonText", "positiveButtonText", "getPositiveButtonText", "setPositiveButtonText", "radioOptions", "getRadioOptions", "getTitle", "setTitle", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
        static final class DialogData {
            private Function1<? super String, Unit> callback;
            private final List<String> inputs;
            private String message;
            private String negativeButtonText;
            private String neutralButtonText;
            private String positiveButtonText;
            private final List<String> radioOptions;
            private String title;

            public DialogData(String title, String message) {
                Intrinsics.checkNotNullParameter(title, "title");
                Intrinsics.checkNotNullParameter(message, "message");
                this.title = title;
                this.message = message;
                this.inputs = new ArrayList();
                this.radioOptions = new ArrayList();
            }

            public final String getMessage() {
                return this.message;
            }

            public final String getTitle() {
                return this.title;
            }

            public final void setMessage(String str) {
                Intrinsics.checkNotNullParameter(str, "<set-?>");
                this.message = str;
            }

            public final void setTitle(String str) {
                Intrinsics.checkNotNullParameter(str, "<set-?>");
                this.title = str;
            }

            public final List<String> getInputs() {
                return this.inputs;
            }

            public final List<String> getRadioOptions() {
                return this.radioOptions;
            }

            public final String getPositiveButtonText() {
                return this.positiveButtonText;
            }

            public final void setPositiveButtonText(String str) {
                this.positiveButtonText = str;
            }

            public final String getNegativeButtonText() {
                return this.negativeButtonText;
            }

            public final void setNegativeButtonText(String str) {
                this.negativeButtonText = str;
            }

            public final String getNeutralButtonText() {
                return this.neutralButtonText;
            }

            public final void setNeutralButtonText(String str) {
                this.neutralButtonText = str;
            }

            public final Function1<String, Unit> getCallback() {
                return this.callback;
            }

            public final void setCallback(Function1<? super String, Unit> function1) {
                this.callback = function1;
            }
        }
    }
}
