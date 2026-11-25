package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TextView.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-ktx-1.2.0.aar:classes.jar:androidx/core/widget/TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$1.class */
@Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000;\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\b\u0005\n\u0002\b\u0005\n\u0002\b\u0005\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J*\u0010\u0006\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016J*\u0010\r\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\u000f¸\u0006\u0000"}, d2 = {"androidx/core/widget/TextViewKt$addTextChangedListener$textWatcher$1", "Landroid/text/TextWatcher;", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "text", "", "start", "", "count", "after", "onTextChanged", "before", "core-ktx_release"})
public final class TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$1 implements TextWatcher {
    final /* synthetic */ Function1 $afterTextChanged;

    public TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$1(Function1 $captured_local_variable$0) {
        this.$afterTextChanged = $captured_local_variable$0;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(@Nullable Editable s) {
        this.$afterTextChanged.invoke(s);
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
    }
}
