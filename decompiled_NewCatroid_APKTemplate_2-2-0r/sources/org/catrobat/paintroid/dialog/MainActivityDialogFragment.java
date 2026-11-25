package org.catrobat.paintroid.dialog;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.contract.MainActivityContracts;

/* JADX INFO: compiled from: MainActivityDialogFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/dialog/MainActivityDialogFragment;", "Landroidx/appcompat/app/AppCompatDialogFragment;", "()V", "presenter", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;", "getPresenter", "()Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;", "setPresenter", "(Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;)V", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class MainActivityDialogFragment extends AppCompatDialogFragment {
    public MainActivityContracts.Presenter presenter;

    public final MainActivityContracts.Presenter getPresenter() {
        MainActivityContracts.Presenter presenter = this.presenter;
        if (presenter != null) {
            return presenter;
        }
        Intrinsics.throwUninitializedPropertyAccessException("presenter");
        return null;
    }

    public final void setPresenter(MainActivityContracts.Presenter presenter) {
        Intrinsics.checkNotNullParameter(presenter, "<set-?>");
        this.presenter = presenter;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setPresenter(((MainActivityContracts.MainView) requireActivity()).getPresenter());
    }
}
