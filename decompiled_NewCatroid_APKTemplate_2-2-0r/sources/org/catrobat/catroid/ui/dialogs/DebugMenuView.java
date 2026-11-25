package org.catrobat.catroid.ui.dialogs;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import com.google.android.material.badge.BadgeDrawable;
import com.reandroid.arsc.value.ResConfigBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/DebugMenuView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DebugMenuView extends FrameLayout {
    private static final int MAX_LIST_ITEMS_DISPLAY = 50;
    private static final int MAX_TEXT_DISPLAY_LENGTH = 60;
    private static final int UPDATE_INTERVAL_MS = 250;
    private float dX;
    private float dY;
    private final Map<String, TextView> entryViewMap;
    private boolean isUpdatePending;
    private long lastUpdateTime;
    private final WindowManager.LayoutParams params;
    private final LinearLayout variablesContainer;
    private final WindowManager windowManager;
    private static final int COLOR_ACCENT = Color.parseColor("#A8DFF4");
    private static final int COLOR_TEXT_PRIMARY = Color.parseColor("#FFFFFF");
    private static final int COLOR_TEXT_SECONDARY = Color.parseColor("#B0BEC5");
    private static final int COLOR_LIST_TITLE = Color.parseColor("#AADBF0");
    private static final int COLOR_INPUT_BG = Color.parseColor("#00476F");

    public DebugMenuView(Context context) {
        super(context);
        this.entryViewMap = new HashMap();
        this.isUpdatePending = false;
        this.lastUpdateTime = 0L;
        this.windowManager = (WindowManager) context.getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
        this.params = createLayoutParams();
        LayoutInflater.from(context).inflate(R.layout.dialog_debug_menu, (ViewGroup) this, true);
        View childAt = getChildAt(0);
        if (childAt != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            childAt.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.variables_container);
        this.variablesContainer = linearLayout;
        if (linearLayout == null) {
            throw new IllegalStateException("Could not find variables_container in dialog_debug_menu.xml");
        }
        setupWindowControls();
        setupResizer();
        buildInitialLayout();
        setupLogcatButton();
    }

    public void update() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.lastUpdateTime < 250) {
            return;
        }
        this.lastUpdateTime = jCurrentTimeMillis;
        post(new Runnable() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5665lambda$update$0$orgcatrobatcatroiduidialogsDebugMenuView();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$update$0$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5665lambda$update$0$orgcatrobatcatroiduidialogsDebugMenuView() {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        if (currentProject == null) {
            return;
        }
        for (UserVariable userVariable : currentProject.getUserVariables()) {
            updateVariableView("global." + userVariable.getName(), userVariable.getName(), String.valueOf(userVariable.getValue()));
        }
        for (UserList userList : currentProject.getUserLists()) {
            updateListView("global." + userList.getName(), userList, this.variablesContainer);
        }
        try {
            for (Sprite sprite : new ArrayList(currentProject.getSpriteListWithClones())) {
                for (UserVariable userVariable2 : sprite.getUserVariables()) {
                    updateVariableView(sprite.getName() + "." + userVariable2.getName(), userVariable2.getName(), String.valueOf(userVariable2.getValue()));
                }
                for (UserList userList2 : sprite.getUserLists()) {
                    updateListView(sprite.getName() + "." + userList2.getName(), userList2, this.variablesContainer);
                }
            }
        } catch (Exception unused) {
        }
    }

    private void updateVariableView(String uniqueId, String name2, String rawValue) {
        TextView textView = this.entryViewMap.get(uniqueId);
        if (textView != null) {
            String variableText = formatVariableText(name2, rawValue);
            if (textView.getText().toString().equals(variableText)) {
                return;
            }
            textView.setText(variableText);
        }
    }

    private void updateListView(String uniqueId, UserList list, ViewGroup container) {
        TextView textView = this.entryViewMap.get(uniqueId);
        if (textView != null) {
            ListState listState = (ListState) textView.getTag();
            String listText = formatListText(list.getName(), list.getValue().size(), listState.isExpanded);
            if (!textView.getText().toString().equals(listText)) {
                textView.setText(listText);
            }
            if (listState.isExpanded) {
                refreshListItems(list, listState, container, textView);
            }
        }
    }

    private String formatVariableText(String name2, String value) {
        if (value.length() > 60) {
            value = value.substring(0, 60) + StringUtil.ELLIPSIS;
        }
        return "  • " + name2 + " = " + value;
    }

    private String formatListText(String name2, int size, boolean isExpanded) {
        return getContext().getString(R.string.debug_menu_list_format, isExpanded ? "  ▼ " : "  ▶ ", name2, Integer.valueOf(size));
    }

    private void buildInitialLayout() {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        if (currentProject == null) {
            return;
        }
        this.variablesContainer.removeAllViews();
        this.entryViewMap.clear();
        addHeader(this.variablesContainer, getContext().getString(R.string.debug_menu_global_data));
        for (UserVariable userVariable : currentProject.getUserVariables()) {
            addDebugVariable(this.variablesContainer, "global." + userVariable.getName(), userVariable);
        }
        for (UserList userList : currentProject.getUserLists()) {
            addDebugList(this.variablesContainer, "global." + userList.getName(), userList);
        }
        for (Sprite sprite : currentProject.getSpriteListWithClones()) {
            if (sprite != null && (!sprite.getUserVariables().isEmpty() || !sprite.getUserLists().isEmpty())) {
                addHeader(this.variablesContainer, getContext().getString(R.string.debug_menu_sprite) + ": " + sprite.getName().toUpperCase());
                for (UserVariable userVariable2 : sprite.getUserVariables()) {
                    addDebugVariable(this.variablesContainer, sprite.getName() + "." + userVariable2.getName(), userVariable2);
                }
                for (UserList userList2 : sprite.getUserLists()) {
                    addDebugList(this.variablesContainer, sprite.getName() + "." + userList2.getName(), userList2);
                }
            }
        }
    }

    private void addHeader(ViewGroup container, String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(12.0f);
        textView.setTypeface(Typeface.MONOSPACE, 1);
        textView.setTextColor(COLOR_ACCENT);
        textView.setPadding(8, 24, 8, 4);
        container.addView(textView);
    }

    private void addDebugVariable(ViewGroup container, String uniqueId, final UserVariable var) {
        TextView textView = new TextView(getContext());
        textView.setText(formatVariableText(var.getName(), String.valueOf(var.getValue())));
        textView.setTextSize(13.0f);
        textView.setTypeface(Typeface.MONOSPACE);
        textView.setTextColor(COLOR_TEXT_PRIMARY);
        textView.setPadding(8, 8, 8, 8);
        textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5652xaeb8e5b1(var, view);
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.m5653x24330bf2(var, view);
            }
        });
        container.addView(textView);
        this.entryViewMap.put(uniqueId, textView);
    }

    /* JADX INFO: renamed from: lambda$addDebugVariable$1$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5652xaeb8e5b1(UserVariable userVariable, View view) {
        showEditVariableDialog(userVariable);
    }

    /* JADX INFO: renamed from: lambda$addDebugVariable$2$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ boolean m5653x24330bf2(UserVariable userVariable, View view) {
        copyToClipboard(userVariable.getName(), String.valueOf(userVariable.getValue()));
        return true;
    }

    private void addDebugList(final ViewGroup container, String uniqueId, final UserList list) {
        final TextView textView = new TextView(getContext());
        textView.setText(formatListText(list.getName(), list.getValue().size(), false));
        textView.setTextSize(13.0f);
        textView.setTypeface(Typeface.MONOSPACE, 1);
        textView.setTextColor(COLOR_LIST_TITLE);
        textView.setPadding(8, 8, 8, 8);
        textView.setTag(new ListState(false, new ArrayList()));
        textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5650xb578a3d5(container, textView, list, view);
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.m5651x2af2ca16(list, view);
            }
        });
        container.addView(textView);
        this.entryViewMap.put(uniqueId, textView);
    }

    /* JADX INFO: renamed from: lambda$addDebugList$3$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5650xb578a3d5(ViewGroup viewGroup, TextView textView, UserList userList, View view) {
        toggleList(viewGroup, textView, userList);
    }

    /* JADX INFO: renamed from: lambda$addDebugList$4$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ boolean m5651x2af2ca16(UserList userList, View view) {
        copyToClipboard(userList.getName(), String.valueOf(userList.getValue()));
        return true;
    }

    private void toggleList(ViewGroup container, TextView headerView, UserList listData) {
        ListState listState = (ListState) headerView.getTag();
        if (listState.isExpanded) {
            Iterator<View> it = listState.childViews.iterator();
            while (it.hasNext()) {
                container.removeView(it.next());
            }
            if (listState.moreView != null) {
                container.removeView(listState.moreView);
                listState.moreView = null;
            }
            listState.childViews.clear();
            listState.isExpanded = false;
        } else {
            listState.isExpanded = true;
            refreshListItems(listData, listState, container, headerView);
        }
        headerView.setText(formatListText(listData.getName(), listData.getValue().size(), listState.isExpanded));
    }

    private void refreshListItems(final UserList listData, ListState state, ViewGroup container, TextView headerView) {
        ArrayList arrayList = new ArrayList();
        List<Object> value = listData.getValue();
        int i = 0;
        if (value != null) {
            int size = value.size();
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    arrayList.add(value.get(i2));
                } catch (Exception unused) {
                }
            }
        }
        int iIndexOfChild = container.indexOfChild(headerView);
        int size2 = arrayList.size();
        int iMin = Math.min(size2, 50);
        if (state.moreView != null) {
            container.removeView(state.moreView);
            state.moreView = null;
        }
        while (state.childViews.size() > iMin) {
            container.removeView(state.childViews.remove(state.childViews.size() - 1));
        }
        final int i3 = 0;
        while (i3 < iMin) {
            final String strValueOf = String.valueOf(arrayList.get(i3));
            int i4 = i3 + 1;
            String str = String.format("      [%d] %s", Integer.valueOf(i4), strValueOf.length() > 60 ? strValueOf.substring(i, 60) + StringUtil.ELLIPSIS : strValueOf);
            if (i3 < state.childViews.size()) {
                TextView textView = (TextView) state.childViews.get(i3);
                if (!textView.getText().toString().equals(str)) {
                    textView.setText(str);
                }
                textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda11
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.m5654x2c69470e(listData, i3, strValueOf, view);
                    }
                });
                textView.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda12
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return this.f$0.m5655xa1e36d4f(i3, strValueOf, view);
                    }
                });
            } else {
                TextView textView2 = new TextView(getContext());
                textView2.setText(str);
                textView2.setTextSize(12.0f);
                textView2.setTypeface(Typeface.MONOSPACE);
                textView2.setTextColor(COLOR_TEXT_SECONDARY);
                textView2.setPadding(8, 4, 8, 4);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda13
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.m5656x175d9390(listData, i3, strValueOf, view);
                    }
                });
                textView2.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda14
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return this.f$0.m5657x8cd7b9d1(i3, strValueOf, view);
                    }
                });
                container.addView(textView2, iIndexOfChild + 1 + i3);
                state.childViews.add(textView2);
            }
            i3 = i4;
            i = 0;
        }
        if (size2 > 50) {
            TextView textView3 = new TextView(getContext());
            textView3.setText(String.format(getContext().getString(R.string.debug_menu_more_items), Integer.valueOf(size2 - 50)));
            textView3.setTextSize(12.0f);
            textView3.setTypeface(Typeface.MONOSPACE, 2);
            textView3.setTextColor(-7829368);
            container.addView(textView3, iIndexOfChild + 1 + iMin);
            state.moreView = textView3;
        }
    }

    /* JADX INFO: renamed from: lambda$refreshListItems$5$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5654x2c69470e(UserList userList, int i, String str, View view) {
        showEditListItemDialog(userList, i, str);
    }

    /* JADX INFO: renamed from: lambda$refreshListItems$6$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ boolean m5655xa1e36d4f(int i, String str, View view) {
        copyToClipboard("Item " + (i + 1), str);
        return true;
    }

    /* JADX INFO: renamed from: lambda$refreshListItems$7$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5656x175d9390(UserList userList, int i, String str, View view) {
        showEditListItemDialog(userList, i, str);
    }

    /* JADX INFO: renamed from: lambda$refreshListItems$8$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ boolean m5657x8cd7b9d1(int i, String str, View view) {
        copyToClipboard("Item " + (i + 1), str);
        return true;
    }

    private void setupResizer() {
        final TextView textView = new TextView(getContext());
        textView.setText("↘");
        textView.setTextColor(COLOR_ACCENT);
        textView.setTextSize(22.0f);
        textView.setPadding(32, 32, 32, 32);
        addView(textView, new FrameLayout.LayoutParams(-2, -2, BadgeDrawable.BOTTOM_END));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView.1
            private float initialX;
            private float initialY;
            private int originalHeight;
            private int originalWidth;
            private int originalX;
            private int originalY;
            private View resizePreview;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == 0) {
                    this.originalX = DebugMenuView.this.params.x;
                    this.originalY = DebugMenuView.this.params.y;
                    this.originalWidth = DebugMenuView.this.getWidth();
                    this.originalHeight = DebugMenuView.this.getHeight();
                    this.initialX = event.getRawX();
                    this.initialY = event.getRawY();
                    View childAt = DebugMenuView.this.getChildAt(0);
                    if (childAt != null) {
                        childAt.setVisibility(8);
                    }
                    this.resizePreview = new View(DebugMenuView.this.getContext());
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.argb(80, 255, 255, 255));
                    gradientDrawable.setCornerRadius(32.0f);
                    gradientDrawable.setStroke(6, DebugMenuView.COLOR_ACCENT);
                    this.resizePreview.setBackground(gradientDrawable);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.originalWidth, this.originalHeight);
                    layoutParams.leftMargin = this.originalX;
                    layoutParams.topMargin = this.originalY;
                    layoutParams.gravity = BadgeDrawable.TOP_START;
                    this.resizePreview.setLayoutParams(layoutParams);
                    DebugMenuView.this.addView(this.resizePreview);
                    DebugMenuView.this.params.width = -1;
                    DebugMenuView.this.params.height = -1;
                    DebugMenuView.this.params.x = 0;
                    DebugMenuView.this.params.y = 0;
                    WindowManager windowManager = DebugMenuView.this.windowManager;
                    DebugMenuView debugMenuView = DebugMenuView.this;
                    windowManager.updateViewLayout(debugMenuView, debugMenuView.params);
                    textView.bringToFront();
                    return true;
                }
                if (action != 1) {
                    if (action == 2) {
                        int rawX = (int) (event.getRawX() - this.initialX);
                        int rawY = (int) (event.getRawY() - this.initialY);
                        int i = (int) (DebugMenuView.this.getResources().getDisplayMetrics().density * 200.0f);
                        int iMax = Math.max(this.originalWidth + rawX, i);
                        int iMax2 = Math.max(this.originalHeight + rawY, i);
                        View view = this.resizePreview;
                        if (view != null) {
                            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view.getLayoutParams();
                            layoutParams2.width = iMax;
                            layoutParams2.height = iMax2;
                            this.resizePreview.setLayoutParams(layoutParams2);
                        }
                        return true;
                    }
                    if (action != 3) {
                        return false;
                    }
                }
                int width = this.originalWidth;
                int height = this.originalHeight;
                View view2 = this.resizePreview;
                if (view2 != null) {
                    width = view2.getWidth();
                    height = this.resizePreview.getHeight();
                    DebugMenuView.this.removeView(this.resizePreview);
                    this.resizePreview = null;
                }
                View childAt2 = DebugMenuView.this.getChildAt(0);
                if (childAt2 != null) {
                    childAt2.setVisibility(0);
                }
                DebugMenuView.this.params.width = width;
                DebugMenuView.this.params.height = height;
                DebugMenuView.this.params.x = this.originalX;
                DebugMenuView.this.params.y = this.originalY;
                WindowManager windowManager2 = DebugMenuView.this.windowManager;
                DebugMenuView debugMenuView2 = DebugMenuView.this;
                windowManager2.updateViewLayout(debugMenuView2, debugMenuView2.params);
                return true;
            }
        });
    }

    private void showEditVariableDialog(final UserVariable var) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 2131952355);
        builder.setTitle(String.format(getContext().getString(R.string.debug_menu_edit_variable), var.getName()));
        View viewCreateStyledInputContainer = createStyledInputContainer(String.valueOf(var.getValue()));
        final EditText editText = (EditText) viewCreateStyledInputContainer.findViewById(R.id.formula_editor_edit_field);
        builder.setView(viewCreateStyledInputContainer);
        builder.setPositiveButton(R.string.debug_menu_save, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5664x2f821a96(var, editText, dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.debug_menu_cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda16
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNeutralButton(R.string.debug_menu_copy_full, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5663x86923035(var, dialogInterface, i);
            }
        });
        builder.show();
    }

    /* JADX INFO: renamed from: lambda$showEditVariableDialog$9$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5664x2f821a96(UserVariable userVariable, EditText editText, DialogInterface dialogInterface, int i) {
        userVariable.setValue(editText.getText().toString());
        update();
    }

    /* JADX INFO: renamed from: lambda$showEditVariableDialog$11$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5663x86923035(UserVariable userVariable, DialogInterface dialogInterface, int i) {
        copyToClipboard(userVariable.getName(), String.valueOf(userVariable.getValue()));
    }

    private void showEditListItemDialog(final UserList list, final int index, String currentValue) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 2131952355);
        builder.setTitle(String.format(getContext().getString(R.string.debug_menu_edit_list_item), Integer.valueOf(index + 1), list.getName()));
        View viewCreateStyledInputContainer = createStyledInputContainer(currentValue);
        final EditText editText = (EditText) viewCreateStyledInputContainer.findViewById(R.id.formula_editor_edit_field);
        builder.setView(viewCreateStyledInputContainer);
        builder.setPositiveButton(R.string.debug_menu_save, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5662x714acc81(list, index, editText, dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.debug_menu_cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    /* JADX INFO: renamed from: lambda$showEditListItemDialog$12$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5662x714acc81(UserList userList, int i, EditText editText, DialogInterface dialogInterface, int i2) {
        userList.getValue().set(i, editText.getText().toString());
        update();
    }

    private View createStyledInputContainer(String initialText) {
        float f = getResources().getDisplayMetrics().density;
        FrameLayout frameLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        int i = (int) (16.0f * f);
        layoutParams.setMargins(i, i, i, i);
        EditText editText = new EditText(getContext());
        editText.setId(R.id.formula_editor_edit_field);
        editText.setText(initialText);
        editText.setTextColor(COLOR_TEXT_PRIMARY);
        editText.setHintTextColor(COLOR_TEXT_SECONDARY);
        float f2 = 12.0f * f;
        int i2 = (int) f2;
        editText.setPadding(i2, i2, i2, i2);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(COLOR_INPUT_BG);
        gradientDrawable.setCornerRadius(f2);
        gradientDrawable.setStroke((int) (f * 2.0f), COLOR_ACCENT);
        editText.setBackground(gradientDrawable);
        if (initialText.length() > 30) {
            editText.setInputType(131073);
            editText.setMaxLines(8);
        } else {
            editText.setInputType(1);
        }
        editText.setLayoutParams(layoutParams);
        frameLayout.addView(editText);
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.addView(frameLayout);
        return scrollView;
    }

    private void copyToClipboard(String label, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
        ClipData clipDataNewPlainText = ClipData.newPlainText(label, text);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipDataNewPlainText);
            Toast.makeText(getContext(), R.string.debug_menu_copied, 0).show();
        }
    }

    private WindowManager.LayoutParams createLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams((int) (getContext().getResources().getDisplayMetrics().density * 280.0f), -2, 1000, 32, -3);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.x = 100;
        layoutParams.y = 100;
        return layoutParams;
    }

    private void setupWindowControls() {
        View viewFindViewById = findViewById(R.id.title_bar);
        findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DebugMenuManager.getInstance().hide();
            }
        });
        if (viewFindViewById instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) viewFindViewById;
            TextView textView = new TextView(getContext());
            textView.setText(">_");
            textView.setTextColor(COLOR_ACCENT);
            textView.setTypeface(Typeface.MONOSPACE, 1);
            textView.setTextSize(20.0f);
            textView.setPadding(24, 0, 24, 0);
            textView.setGravity(17);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
            layoutParams.gravity = 16;
            textView.setLayoutParams(layoutParams);
            textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m5660x236dc59b(view);
                }
            });
            viewGroup.addView(textView, viewGroup.getChildCount() - 1);
        }
        viewFindViewById.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.m5661x98e7ebdc(view, motionEvent);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupWindowControls$15$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5660x236dc59b(View view) {
        DebugMenuManager.getInstance().hide();
        int i = getContext().getResources().getConfiguration().orientation != 1 ? 0 : 1;
        Intent intent = new Intent(getContext(), (Class<?>) RuntimeConsoleActivity.class);
        intent.putExtra(ResConfigBase.NAME_orientation, i);
        intent.addFlags(268435456);
        getContext().startActivity(intent);
    }

    /* JADX INFO: renamed from: lambda$setupWindowControls$16$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ boolean m5661x98e7ebdc(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.dX = this.params.x - motionEvent.getRawX();
            this.dY = this.params.y - motionEvent.getRawY();
            return true;
        }
        if (action != 1) {
            if (action == 2) {
                this.params.x = (int) (motionEvent.getRawX() + this.dX);
                this.params.y = (int) (motionEvent.getRawY() + this.dY);
                scheduleWindowUpdate();
                return true;
            }
            if (action != 3) {
                return false;
            }
        }
        scheduleWindowUpdate();
        return true;
    }

    @Override // android.view.View
    public WindowManager.LayoutParams getLayoutParams() {
        return this.params;
    }

    private void scheduleWindowUpdate() {
        if (this.isUpdatePending) {
            return;
        }
        this.isUpdatePending = true;
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda9
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                this.f$0.m5658xc7832d76(j);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$scheduleWindowUpdate$17$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5658xc7832d76(long j) {
        this.isUpdatePending = false;
        if (getParent() != null) {
            try {
                this.windowManager.updateViewLayout(this, this.params);
            } catch (Exception unused) {
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/DebugMenuView$ListState.class
      classes14.dex
     */
    private static class ListState {
        List<View> childViews;
        boolean isExpanded;
        View moreView = null;

        ListState(boolean isExpanded, List<View> childViews) {
            this.isExpanded = isExpanded;
            this.childViews = childViews;
        }
    }

    private void setupLogcatButton() {
        final ImageButton imageButton = (ImageButton) findViewById(R.id.btn_record_logs);
        if (imageButton == null) {
            return;
        }
        final LogcatRecorder logcatRecorder = LogcatRecorder.getInstance();
        updateRecordButtonUI(imageButton, logcatRecorder.isRecording());
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5659xe76f6300(logcatRecorder, imageButton, view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupLogcatButton$18$org-catrobat-catroid-ui-dialogs-DebugMenuView, reason: not valid java name */
    /* synthetic */ void m5659xe76f6300(LogcatRecorder logcatRecorder, ImageButton imageButton, View view) {
        if (!logcatRecorder.isRecording()) {
            logcatRecorder.startRecording();
            Toast.makeText(getContext(), R.string.log_recording_started, 0).show();
            updateRecordButtonUI(imageButton, true);
        } else {
            logcatRecorder.stopAndExport(getContext());
            updateRecordButtonUI(imageButton, false);
        }
    }

    private void updateRecordButtonUI(ImageButton btn, boolean isRecording) {
        if (isRecording) {
            btn.setImageResource(R.drawable.ic_debug_stop);
            btn.setColorFilter(Color.parseColor("#FF5252"));
        } else {
            btn.setImageResource(R.drawable.ic_debug_record);
            btn.setColorFilter(COLOR_ACCENT);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/DebugMenuView$LogcatRecorder.class
      classes14.dex
     */
    public static class LogcatRecorder {
        private static LogcatRecorder instance;
        private boolean isRecording = false;
        private long startTime = 0;

        private LogcatRecorder() {
        }

        public static synchronized LogcatRecorder getInstance() {
            if (instance == null) {
                instance = new LogcatRecorder();
            }
            return instance;
        }

        public boolean isRecording() {
            return this.isRecording;
        }

        public void startRecording() {
            this.isRecording = true;
            this.startTime = System.currentTimeMillis();
        }

        public void stopAndExport(final Context context) {
            this.isRecording = false;
            new Thread(new Runnable() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$LogcatRecorder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5667x769a75e2(context);
                }
            }).start();
        }

        /* JADX INFO: renamed from: lambda$stopAndExport$1$org-catrobat-catroid-ui-dialogs-DebugMenuView$LogcatRecorder, reason: not valid java name */
        /* synthetic */ void m5667x769a75e2(final Context context) {
            final File fileSaveReportToCache = saveReportToCache(context, generateReport(context));
            if (fileSaveReportToCache != null) {
                new Handler(context.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.ui.dialogs.DebugMenuView$LogcatRecorder$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m5666xe25c0643(context, fileSaveReportToCache);
                    }
                });
            }
        }

        private String generateReport(Context context) {
            BufferedReader bufferedReader;
            StringBuilder sb = new StringBuilder("=========================================\n         NEWCATROID DEBUG REPORT         \n=========================================\n\nReport Generated: ");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DEFAULT, Locale.getDefault());
            sb.append(simpleDateFormat.format(new Date())).append("\nRecording Started: ");
            sb.append(simpleDateFormat.format(new Date(this.startTime))).append("\n-----------------------------------------\n\n--- DEVICE INFORMATION ---\nManufacturer: ");
            sb.append(Build.MANUFACTURER).append("\nModel: ");
            sb.append(Build.MODEL).append("\nBrand: ");
            sb.append(Build.BRAND).append("\nDevice: ");
            sb.append(Build.DEVICE).append("\nOS Version: ");
            sb.append(Build.VERSION.RELEASE).append(" (API ").append(Build.VERSION.SDK_INT).append(")\nCPU ABI: ");
            sb.append(Build.SUPPORTED_ABIS[0]).append("\n");
            try {
                try {
                    try {
                        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
                        if (activityManager != null) {
                            activityManager.getMemoryInfo(memoryInfo);
                            sb.append("Total RAM: ").append(memoryInfo.totalMem / 1048576).append(" MB\n");
                            sb.append("Available RAM: ").append(memoryInfo.availMem / 1048576).append(" MB\n");
                        }
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line).append("\n");
                        }
                    } catch (Exception unused) {
                    }
                    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                    sb.append("Screen Resolution: ").append(displayMetrics.widthPixels).append("x").append(displayMetrics.heightPixels).append("\n");
                    sb.append("Screen Density: ").append(displayMetrics.densityDpi).append(" DPI\n");
                } catch (Exception unused2) {
                }
                bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -d -v time").getInputStream()));
                bufferedReader.close();
            } catch (Exception e) {
                sb.append("ERROR CAPTURING LOGS: ").append(e.getMessage()).append("\n");
            }
            sb.append("\n--- APPLICATION INFORMATION ---\nPackage Name: ");
            sb.append(context.getPackageName()).append("\n");
            Project currentProject = ProjectManager.getInstance().getCurrentProject();
            if (currentProject != null) {
                sb.append("\n--- ACTIVE PROJECT INFORMATION ---\nProject Name: ");
                sb.append(currentProject.getName()).append("\nLanguage Version: ");
                sb.append(currentProject.getCatrobatLanguageVersion()).append("\n");
            }
            sb.append("\n=========================================\n               LOGCAT LOGS               \n=========================================\n\n");
            return sb.toString();
        }

        private File saveReportToCache(Context context, String reportText) {
            try {
                File file = new File(context.getCacheDir(), "newcatroid_debug_report.txt");
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(reportText.getBytes(StandardCharsets.UTF_8));
                fileOutputStream.close();
                return file;
            } catch (Exception e) {
                Log.e("LogcatRecorder", "Failed to write debug report to cache", e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: shareReportFile, reason: merged with bridge method [inline-methods] */
        public void m5666xe25c0643(Context context, File file) {
            try {
                Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                intent.addFlags(1);
                intent.putExtra("android.intent.extra.SUBJECT", "NewCatroid Debug Report");
                Intent intentCreateChooser = Intent.createChooser(intent, context.getString(R.string.share_report_title));
                intentCreateChooser.addFlags(268435456);
                context.startActivity(intentCreateChooser);
            } catch (Exception e) {
                Toast.makeText(context, "Error sharing file: " + e.getMessage(), 0).show();
            }
        }
    }
}
