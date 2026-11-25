package org.catrobat.catroid.ui;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.databinding.ViewScriptFinderBinding;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ScriptFinder.class
  classes14.dex
 */
/* JADX INFO: compiled from: ScriptFinder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\r\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 52\u00020\u0001:\u00045678B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020\u001aJ\b\u0010$\u001a\u00020!H\u0002J\b\u0010%\u001a\u00020!H\u0002J\b\u0010&\u001a\u00020!H\u0002J\u0010\u0010'\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020(H\u0002J\b\u0010)\u001a\u00020!H\u0002J\u0006\u0010*\u001a\u00020!J\u0010\u0010+\u001a\u00020!2\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u0010\u0010,\u001a\u00020!2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u0010\u0010-\u001a\u00020!2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012J\b\u0010.\u001a\u00020!H\u0002J \u0010/\u001a\u00020!2\u0006\u0010#\u001a\u00020\u001a2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0002J\b\u00104\u001a\u00020!H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0011\u0010\f\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\f\u0010\u000bR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001d\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001f\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lorg/catrobat/catroid/ui/ScriptFinder;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "binding", "Lorg/catrobat/catroid/databinding/ViewScriptFinderBinding;", "isClosed", "", "()Z", "isOpen", "onCloseListener", "Lorg/catrobat/catroid/ui/ScriptFinder$OnCloseListener;", "onOpenListener", "Lorg/catrobat/catroid/ui/ScriptFinder$OnOpenListener;", "onResultFoundListener", "Lorg/catrobat/catroid/ui/ScriptFinder$OnResultFoundListener;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "searchQuery", "", "searchResultIndex", "", "searchResults", "", "", "close", "", "fillIndices", "query", "find", "findNext", "findPrevious", "formatSearchQuery", "", "hideNavigationButtons", "open", "setOnCloseListener", "setOnOpenListener", "setOnResultFoundListener", "showNavigationButtons", "startThreadToFillIndices", "activeScene", "Lorg/catrobat/catroid/content/Scene;", "activeSprite", "Lorg/catrobat/catroid/content/Sprite;", "updateUI", "Companion", "OnCloseListener", "OnOpenListener", "OnResultFoundListener", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptFinder extends LinearLayout {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "ScriptFinder";
    private ViewScriptFinderBinding binding;
    private OnCloseListener onCloseListener;
    private OnOpenListener onOpenListener;
    private OnResultFoundListener onResultFoundListener;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private String searchQuery;
    private int searchResultIndex;
    private List<Integer[]> searchResults;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ScriptFinder$OnCloseListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ScriptFinder.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/ui/ScriptFinder$OnCloseListener;", "", "onClose", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnCloseListener {
        void onClose();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ScriptFinder$OnOpenListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ScriptFinder.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/ui/ScriptFinder$OnOpenListener;", "", "onOpen", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnOpenListener {
        void onOpen();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ScriptFinder$OnResultFoundListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ScriptFinder.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J2\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\b\u0010\t\u001a\u0004\u0018\u00010\nH&¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ui/ScriptFinder$OnResultFoundListener;", "", "onResultFound", "", "sceneIndex", "", "spriteIndex", "brickIndex", "totalResults", "textView", "Landroid/widget/TextView;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnResultFoundListener {
        void onResultFound(int sceneIndex, int spriteIndex, int brickIndex, int totalResults, TextView textView);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptFinder(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.projectManager = KoinJavaComponent.inject$default(ProjectManager.class, null, null, 6, null);
        this.searchQuery = "";
        setOrientation(1);
        Object systemService = context.getSystemService("layout_inflater");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.LayoutInflater");
        ViewScriptFinderBinding viewScriptFinderBindingInflate = ViewScriptFinderBinding.inflate((LayoutInflater) systemService, this);
        Intrinsics.checkNotNullExpressionValue(viewScriptFinderBindingInflate, "inflate(...)");
        this.binding = viewScriptFinderBindingInflate;
        viewScriptFinderBindingInflate.find.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ScriptFinder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScriptFinder._init_$lambda$0(this.f$0, view);
            }
        });
        this.binding.findNext.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ScriptFinder$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScriptFinder._init_$lambda$1(this.f$0, view);
            }
        });
        this.binding.findPrevious.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ScriptFinder$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScriptFinder._init_$lambda$2(this.f$0, view);
            }
        });
        this.binding.close.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ScriptFinder$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScriptFinder._init_$lambda$3(this.f$0, view);
            }
        });
        this.binding.searchBar.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.ui.ScriptFinder$textWatcher$1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                Intrinsics.checkNotNullParameter(newText, "newText");
                if (Intrinsics.areEqual(this.this$0.searchQuery, this.this$0.formatSearchQuery(newText))) {
                    this.this$0.showNavigationButtons();
                } else {
                    this.this$0.hideNavigationButtons();
                }
            }
        });
        this.binding.searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.catrobat.catroid.ui.ScriptFinder$$ExternalSyntheticLambda4
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return ScriptFinder._init_$lambda$4(this.f$0, textView, i, keyEvent);
            }
        });
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showNavigationButtons() {
        this.binding.findNext.setVisibility(0);
        this.binding.findPrevious.setVisibility(0);
        this.binding.searchPositionIndicator.setVisibility(0);
        this.binding.find.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hideNavigationButtons() {
        this.binding.findNext.setVisibility(8);
        this.binding.findPrevious.setVisibility(8);
        this.binding.searchPositionIndicator.setVisibility(8);
        this.binding.find.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String formatSearchQuery(CharSequence query) {
        String string = StringsKt.trim((CharSequence) query.toString()).toString();
        Locale ROOT = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        String lowerCase = string.toLowerCase(ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return lowerCase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(ScriptFinder this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.find();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(ScriptFinder this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.findNext();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$2(ScriptFinder this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.findPrevious();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$3(ScriptFinder this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean _init_$lambda$4(ScriptFinder this$0, TextView textView, int i, KeyEvent keyEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i == 3 || i == 6) {
            this$0.find();
            return false;
        }
        if (keyEvent.getAction() != 66) {
            return false;
        }
        this$0.find();
        return false;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ScriptFinder$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ScriptFinder.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u0004R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/ui/ScriptFinder$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getTAG", "()Ljava/lang/String;", "searchBrickViews", "", RegisterSpec.PREFIX, "Landroid/view/View;", "searchQuery", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ScriptFinder.TAG;
        }

        public final boolean searchBrickViews(View v, String searchQuery) {
            Intrinsics.checkNotNullParameter(searchQuery, "searchQuery");
            try {
                if (v instanceof Spinner) {
                    Object selectedItem = ((Spinner) v).getSelectedItem();
                    if (selectedItem instanceof Nameable) {
                        String name2 = ((Nameable) selectedItem).getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        Locale ROOT = Locale.ROOT;
                        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
                        String lowerCase = name2.toLowerCase(ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                        if (StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) searchQuery, false, 2, (Object) null)) {
                            return true;
                        }
                    }
                } else if (v instanceof ViewGroup) {
                    int childCount = ((ViewGroup) v).getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        if (searchBrickViews(((ViewGroup) v).getChildAt(i), searchQuery)) {
                            return true;
                        }
                    }
                } else if (v instanceof TextView) {
                    String string = ((TextView) v).getText().toString();
                    Locale ROOT2 = Locale.ROOT;
                    Intrinsics.checkNotNullExpressionValue(ROOT2, "ROOT");
                    String lowerCase2 = string.toLowerCase(ROOT2);
                    Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                    if (StringsKt.contains$default((CharSequence) lowerCase2, (CharSequence) searchQuery, false, 2, (Object) null)) {
                        return true;
                    }
                }
            } catch (NullPointerException e) {
                Log.e(getTAG(), Log.getStackTraceString(e));
            }
            return false;
        }
    }

    private final void find() {
        Editable text = this.binding.searchBar.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        String searchQuery = formatSearchQuery(text);
        if (searchQuery.length() > 0) {
            if (!Intrinsics.areEqual(this.searchQuery, searchQuery)) {
                this.searchQuery = searchQuery;
                fillIndices(searchQuery);
                this.binding.find.setVisibility(8);
                this.binding.findNext.setVisibility(0);
                this.binding.findPrevious.setVisibility(0);
                return;
            }
            if (this.searchResults != null) {
                findNext();
                return;
            }
            return;
        }
        ToastUtil.showError(getContext(), getContext().getString(R.string.query_field_is_empty));
    }

    private final void findNext() {
        List<Integer[]> list = this.searchResults;
        if (list != null) {
            if (!list.isEmpty()) {
                this.searchResultIndex = (this.searchResultIndex + 1) % list.size();
                updateUI();
            } else {
                this.binding.searchPositionIndicator.setText("0/0");
                ToastUtil.showError(getContext(), getContext().getString(R.string.no_results_found));
            }
        }
    }

    private final void findPrevious() {
        List<Integer[]> list = this.searchResults;
        if (list != null) {
            if (!list.isEmpty()) {
                int i = this.searchResultIndex;
                this.searchResultIndex = i == 0 ? list.size() - 1 : i - 1;
                updateUI();
            } else {
                this.binding.searchPositionIndicator.setText("0/0");
                ToastUtil.showError(getContext(), getContext().getString(R.string.no_results_found));
            }
        }
    }

    private final void updateUI() {
        List<Integer[]> list;
        List<Integer[]> list2 = this.searchResults;
        Integer[] numArr = list2 != null ? list2.get(this.searchResultIndex) : null;
        TextView textView = this.binding.searchPositionIndicator;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.ROOT;
        Integer numValueOf = Integer.valueOf(this.searchResultIndex + 1);
        List<Integer[]> list3 = this.searchResults;
        String str = String.format(locale, "%d/%d", Arrays.copyOf(new Object[]{numValueOf, list3 != null ? Integer.valueOf(list3.size()) : null}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        textView.setText(str);
        if (numArr == null || (list = this.searchResults) == null) {
            return;
        }
        int size = list.size();
        OnResultFoundListener onResultFoundListener = this.onResultFoundListener;
        if (onResultFoundListener != null) {
            onResultFoundListener.onResultFound(numArr[0].intValue(), numArr[1].intValue(), numArr[2].intValue(), size, this.binding.sceneAndSpriteName);
        }
    }

    public final void fillIndices(String query) {
        Intrinsics.checkNotNullParameter(query, "query");
        this.searchResultIndex = -1;
        Scene currentlyEditedScene = getProjectManager().getCurrentlyEditedScene();
        Sprite currentSprite = getProjectManager().getCurrentSprite();
        List<Integer[]> list = this.searchResults;
        if (list == null) {
            this.searchResults = new ArrayList();
        } else if (list != null) {
            list.clear();
        }
        Intrinsics.checkNotNull(currentlyEditedScene);
        Intrinsics.checkNotNull(currentSprite);
        startThreadToFillIndices(query, currentlyEditedScene, currentSprite);
    }

    private final void startThreadToFillIndices(final String query, final Scene activeScene, final Sprite activeSprite) {
        new Thread(new Runnable() { // from class: org.catrobat.catroid.ui.ScriptFinder$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ScriptFinder.startThreadToFillIndices$lambda$11(this.f$0, query, activeScene, activeSprite);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startThreadToFillIndices$lambda$11(final ScriptFinder this$0, String query, final Scene activeScene, final Sprite activeSprite) {
        List<Integer[]> list;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(query, "$query");
        Intrinsics.checkNotNullParameter(activeScene, "$activeScene");
        Intrinsics.checkNotNullParameter(activeSprite, "$activeSprite");
        Context context = this$0.getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
        Activity activity = (Activity) context;
        if (!activity.isFinishing()) {
            activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.ScriptFinder$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    ScriptFinder.startThreadToFillIndices$lambda$11$lambda$9(this.f$0);
                }
            });
        }
        List<Scene> sceneList = this$0.getProjectManager().getCurrentProject().getSceneList();
        int size = sceneList.size();
        for (int i = 0; i < size; i++) {
            Scene scene = sceneList.get(i);
            List<Sprite> spriteList = scene.getSpriteList();
            int size2 = spriteList.size();
            int i2 = 0;
            while (i2 < size2) {
                Sprite sprite = spriteList.get(i2);
                List<Script> scriptList = sprite.getScriptList();
                ArrayList arrayList = new ArrayList();
                List<Scene> list2 = sceneList;
                this$0.getProjectManager().setCurrentSceneAndSprite(scene.getName(), sprite.getName());
                for (Script script : scriptList) {
                    script.setParents();
                    script.addToFlatList(arrayList);
                }
                int size3 = arrayList.size();
                int i3 = 0;
                while (i3 < size3) {
                    int i4 = size3;
                    if (INSTANCE.searchBrickViews(((Brick) arrayList.get(i3)).getView(this$0.getContext()), query) && (list = this$0.searchResults) != null) {
                        list.add(new Integer[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
                    }
                    i3++;
                    size3 = i4;
                }
                i2++;
                sceneList = list2;
            }
        }
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.ScriptFinder$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ScriptFinder.startThreadToFillIndices$lambda$11$lambda$10(this.f$0, activeScene, activeSprite);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startThreadToFillIndices$lambda$11$lambda$9(ScriptFinder this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.binding.find.setVisibility(8);
        this$0.binding.findNext.setVisibility(8);
        this$0.binding.findPrevious.setVisibility(8);
        this$0.binding.progressBar.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startThreadToFillIndices$lambda$11$lambda$10(ScriptFinder this$0, Scene activeScene, Sprite activeSprite) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(activeScene, "$activeScene");
        Intrinsics.checkNotNullParameter(activeSprite, "$activeSprite");
        this$0.binding.findNext.setVisibility(0);
        this$0.binding.findPrevious.setVisibility(0);
        this$0.binding.searchPositionIndicator.setVisibility(0);
        this$0.binding.progressBar.setVisibility(8);
        this$0.getProjectManager().setCurrentSceneAndSprite(activeScene.getName(), activeSprite.getName());
        this$0.findNext();
    }

    public final boolean isOpen() {
        return getVisibility() == 0;
    }

    public final void open() {
        setVisibility(0);
        Object systemService = getContext().getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        ((InputMethodManager) systemService).toggleSoftInputFromWindow(this.binding.searchBar.getApplicationWindowToken(), 2, 0);
        OnOpenListener onOpenListener = this.onOpenListener;
        if (onOpenListener != null) {
            onOpenListener.onOpen();
        }
        this.binding.searchBar.requestFocus();
    }

    public final void close() {
        setVisibility(8);
        List<Integer[]> list = this.searchResults;
        if (list != null) {
            list.clear();
        }
        this.binding.searchBar.setText("");
        this.searchQuery = "";
        OnCloseListener onCloseListener = this.onCloseListener;
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }
        ViewUtils.hideKeyboard(this);
    }

    public final boolean isClosed() {
        return getVisibility() == 8;
    }

    public final void setOnResultFoundListener(OnResultFoundListener onResultFoundListener) {
        this.onResultFoundListener = onResultFoundListener;
    }

    public final void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    public final void setOnOpenListener(OnOpenListener onOpenListener) {
        this.onOpenListener = onOpenListener;
    }
}
