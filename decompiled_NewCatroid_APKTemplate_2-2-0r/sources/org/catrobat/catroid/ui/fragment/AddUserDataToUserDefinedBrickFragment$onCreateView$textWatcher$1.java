package org.catrobat.catroid.ui.fragment;

import android.text.Editable;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher;
import org.catrobat.catroid.userbrick.UserDefinedBrickData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/AddUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: AddUserDataToUserDefinedBrickFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016¨\u0006\u0006"}, d2 = {"org/catrobat/catroid/ui/fragment/AddUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1", "Lorg/catrobat/catroid/ui/recyclerview/dialog/textwatcher/InputWatcher$TextWatcher;", "afterTextChanged", "", "editable", "Landroid/text/Editable;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AddUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1 extends InputWatcher.TextWatcher {
    final /* synthetic */ AddUserDataToUserDefinedBrickFragment this$0;

    AddUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1(AddUserDataToUserDefinedBrickFragment addUserDataToUserDefinedBrickFragment) {
        this.this$0 = addUserDataToUserDefinedBrickFragment;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher.TextWatcher, android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        ScrollView scrollView;
        TextView textView = this.this$0.userBrickTextView;
        if (textView != null) {
            textView.setText(String.valueOf(editable));
        }
        if (this.this$0.dataTypeToAdd == UserDefinedBrickData.UserDefinedBrickDataType.INPUT) {
            String strValidateInput = validateInput(String.valueOf(editable), this.this$0.getContext());
            if (strValidateInput != null && (scrollView = this.this$0.scrollView) != null) {
                final AddUserDataToUserDefinedBrickFragment addUserDataToUserDefinedBrickFragment = this.this$0;
                scrollView.post(new Runnable() { // from class: org.catrobat.catroid.ui.fragment.AddUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AddUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1.afterTextChanged$lambda$0(addUserDataToUserDefinedBrickFragment);
                    }
                });
            }
            TextInputLayout textInputLayout = this.this$0.addUserDataUserBrickTextLayout;
            if (textInputLayout != null) {
                textInputLayout.setError(strValidateInput);
            }
            this.this$0.setNextItemEnabled(strValidateInput == null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void afterTextChanged$lambda$0(AddUserDataToUserDefinedBrickFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ScrollView scrollView = this$0.scrollView;
        if (scrollView != null) {
            scrollView.fullScroll(130);
        }
    }
}
