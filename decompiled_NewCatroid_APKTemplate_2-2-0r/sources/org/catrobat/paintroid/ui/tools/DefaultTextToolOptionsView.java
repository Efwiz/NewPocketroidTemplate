package org.catrobat.paintroid.ui.tools;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Checkable;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.android.material.button.MaterialButton;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.FontType;
import org.catrobat.paintroid.tools.options.TextToolOptionsView;

/* JADX INFO: compiled from: DefaultTextToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0018\u001a\u00020\bH\u0016J\b\u0010\u0019\u001a\u00020\bH\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\u0010\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\u0013H\u0002J\u0010\u0010\"\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u001fH\u0002J\u0010\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020&H\u0002J\u0010\u0010'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020)H\u0002J\u0010\u0010*\u001a\u00020\u001b2\u0006\u0010+\u001a\u00020\u001fH\u0002J\u0010\u0010,\u001a\u00020\u001b2\u0006\u0010-\u001a\u00020\nH\u0016J8\u0010.\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020&2\u0006\u0010(\u001a\u00020)2\u0006\u0010!\u001a\u00020\u0013H\u0016J\b\u0010/\u001a\u00020\u001bH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultTextToolOptionsView;", "Lorg/catrobat/paintroid/tools/options/TextToolOptionsView;", "rootView", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "boldToggleButton", "Lcom/google/android/material/button/MaterialButton;", "bottomLayout", "Landroid/view/View;", "callback", "Lorg/catrobat/paintroid/tools/options/TextToolOptionsView$Callback;", "context", "Landroid/content/Context;", "fontList", "Landroidx/recyclerview/widget/RecyclerView;", "fontSizeText", "Landroid/widget/EditText;", "fontTypes", "", "Lorg/catrobat/paintroid/tools/FontType;", "italicToggleButton", "textEditText", "topLayout", "underlinedToggleButton", "getBottomLayout", "getTopLayout", "hideKeyboard", "", "initializeListeners", "notifyBoldChanged", "bold", "", "notifyFontChanged", "fontType", "notifyItalicChanged", "italic", "notifyTextChanged", "text", "", "notifyTextSizeChanged", StageActivity.STYLE_TEXT_SIZE, "", "notifyUnderlinedChanged", "underlined", "setCallback", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setState", "showKeyboard", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultTextToolOptionsView implements TextToolOptionsView {
    private final MaterialButton boldToggleButton;
    private final View bottomLayout;
    private TextToolOptionsView.Callback callback;
    private final Context context;
    private final RecyclerView fontList;
    private final EditText fontSizeText;
    private final List<FontType> fontTypes;
    private final MaterialButton italicToggleButton;
    private final EditText textEditText;
    private final View topLayout;
    private final MaterialButton underlinedToggleButton;

    public DefaultTextToolOptionsView(ViewGroup rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        Context context = rootView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "rootView.context");
        this.context = context;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_pocketpaint_text_tool, rootView);
        View viewFindViewById = viewInflate.findViewById(R.id.pocketpaint_text_top_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "textToolView.findViewByI…ketpaint_text_top_layout)");
        this.topLayout = viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.pocketpaint_text_bottom_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "textToolView.findViewByI…paint_text_bottom_layout)");
        this.bottomLayout = viewFindViewById2;
        View viewFindViewById3 = viewInflate.findViewById(R.id.pocketpaint_text_tool_dialog_input_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "textToolView.findViewByI…t_tool_dialog_input_text)");
        EditText editText = (EditText) viewFindViewById3;
        this.textEditText = editText;
        View viewFindViewById4 = viewInflate.findViewById(R.id.pocketpaint_text_tool_dialog_list_font);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "textToolView.findViewByI…xt_tool_dialog_list_font)");
        this.fontList = (RecyclerView) viewFindViewById4;
        View viewFindViewById5 = viewInflate.findViewById(R.id.pocketpaint_text_tool_dialog_toggle_underlined);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "textToolView.findViewByI…dialog_toggle_underlined)");
        MaterialButton materialButton = (MaterialButton) viewFindViewById5;
        this.underlinedToggleButton = materialButton;
        View viewFindViewById6 = viewInflate.findViewById(R.id.pocketpaint_text_tool_dialog_toggle_italic);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "textToolView.findViewByI…ool_dialog_toggle_italic)");
        this.italicToggleButton = (MaterialButton) viewFindViewById6;
        View viewFindViewById7 = viewInflate.findViewById(R.id.pocketpaint_text_tool_dialog_toggle_bold);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "textToolView.findViewByI…_tool_dialog_toggle_bold)");
        this.boldToggleButton = (MaterialButton) viewFindViewById7;
        View viewFindViewById8 = viewInflate.findViewById(R.id.pocketpaint_font_size_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "textToolView.findViewByI…cketpaint_font_size_text)");
        EditText editText2 = (EditText) viewFindViewById8;
        this.fontSizeText = editText2;
        editText2.setText("20");
        materialButton.setPaintFlags(materialButton.getPaintFlags() | 8);
        this.fontTypes = ArraysKt.toList(FontType.values());
        initializeListeners();
        editText.requestFocus();
    }

    private final void initializeListeners() {
        this.textEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultTextToolOptionsView.initializeListeners.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                Intrinsics.checkNotNullParameter(editable, "editable");
                DefaultTextToolOptionsView.this.notifyTextChanged(editable.toString());
            }
        });
        this.textEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTextToolOptionsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                DefaultTextToolOptionsView.m6002initializeListeners$lambda0(this.f$0, view, z);
            }
        });
        this.fontList.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
        this.fontList.setAdapter(new FontListAdapter(this.context, this.fontTypes, new Function1<FontType, Unit>() { // from class: org.catrobat.paintroid.ui.tools.DefaultTextToolOptionsView.initializeListeners.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FontType fontType) {
                invoke2(fontType);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FontType font) {
                Intrinsics.checkNotNullParameter(font, "font");
                DefaultTextToolOptionsView.this.notifyFontChanged(font);
                DefaultTextToolOptionsView.this.hideKeyboard();
            }
        }));
        this.underlinedToggleButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTextToolOptionsView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTextToolOptionsView.m6003initializeListeners$lambda1(this.f$0, view);
            }
        });
        this.italicToggleButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTextToolOptionsView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTextToolOptionsView.m6004initializeListeners$lambda2(this.f$0, view);
            }
        });
        this.boldToggleButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTextToolOptionsView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTextToolOptionsView.m6005initializeListeners$lambda3(this.f$0, view);
            }
        });
        this.fontSizeText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultTextToolOptionsView.initializeListeners.7
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Intrinsics.checkNotNullParameter(charSequence, "charSequence");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Intrinsics.checkNotNullParameter(charSequence, "charSequence");
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                int i;
                Intrinsics.checkNotNullParameter(editable, "editable");
                try {
                    i = Integer.parseInt(DefaultTextToolOptionsView.this.fontSizeText.getText().toString());
                } catch (NumberFormatException unused) {
                    i = 1;
                }
                if (i > 300) {
                    DefaultTextToolOptionsView.this.fontSizeText.setText("300");
                    DefaultTextToolOptionsView.this.fontSizeText.setSelection("300".length());
                    i = 300;
                }
                DefaultTextToolOptionsView.this.notifyTextSizeChanged(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-0, reason: not valid java name */
    public static final void m6002initializeListeners$lambda0(DefaultTextToolOptionsView this$0, View view, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (z) {
            return;
        }
        this$0.hideKeyboard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initializeListeners$lambda-1, reason: not valid java name */
    public static final void m6003initializeListeners$lambda1(DefaultTextToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (view == 0) {
            throw new NullPointerException("null cannot be cast to non-null type android.widget.Checkable");
        }
        this$0.notifyUnderlinedChanged(((Checkable) view).isChecked());
        this$0.hideKeyboard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initializeListeners$lambda-2, reason: not valid java name */
    public static final void m6004initializeListeners$lambda2(DefaultTextToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (view == 0) {
            throw new NullPointerException("null cannot be cast to non-null type android.widget.Checkable");
        }
        this$0.notifyItalicChanged(((Checkable) view).isChecked());
        this$0.hideKeyboard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initializeListeners$lambda-3, reason: not valid java name */
    public static final void m6005initializeListeners$lambda3(DefaultTextToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (view == 0) {
            throw new NullPointerException("null cannot be cast to non-null type android.widget.Checkable");
        }
        this$0.notifyBoldChanged(((Checkable) view).isChecked());
        this$0.hideKeyboard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyFontChanged(FontType fontType) {
        TextToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.setFont(fontType);
    }

    private final void notifyUnderlinedChanged(boolean underlined) {
        TextToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.setUnderlined(underlined);
    }

    private final void notifyItalicChanged(boolean italic) {
        TextToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.setItalic(italic);
    }

    private final void notifyBoldChanged(boolean bold) {
        TextToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.setBold(bold);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyTextSizeChanged(int textSize) {
        TextToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.setTextSize(textSize);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyTextChanged(String text) {
        TextToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.setText(text);
    }

    @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView
    public void setState(boolean bold, boolean italic, boolean underlined, String text, int textSize, FontType fontType) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(fontType, "fontType");
        this.boldToggleButton.setChecked(bold);
        this.italicToggleButton.setChecked(italic);
        this.underlinedToggleButton.setChecked(underlined);
        this.textEditText.setText(text);
        RecyclerView.Adapter adapter = this.fontList.getAdapter();
        if (adapter == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.ui.tools.FontListAdapter");
        }
        ((FontListAdapter) adapter).setSelectedIndex(this.fontTypes.indexOf(fontType));
        this.fontSizeText.setText("20");
    }

    @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView
    public void setCallback(TextToolOptionsView.Callback listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.callback = listener;
    }

    @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView
    public void hideKeyboard() {
        Object systemService = this.context.getSystemService("input_method");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        }
        ((InputMethodManager) systemService).hideSoftInputFromWindow(this.textEditText.getWindowToken(), 2);
    }

    @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView
    public void showKeyboard() {
        Object systemService = this.context.getSystemService("input_method");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        }
        ((InputMethodManager) systemService).toggleSoftInputFromWindow(this.textEditText.getWindowToken(), 1, 2);
    }

    @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView
    public View getTopLayout() {
        return this.topLayout;
    }

    @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView
    public View getBottomLayout() {
        return this.bottomLayout;
    }
}
