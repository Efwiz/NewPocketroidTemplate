package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import io.github.rosemoe.sora.widget.CodeEditor;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityIdeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityIdeBinding implements ViewBinding {
    public final RecyclerView aiChatRecycler;
    public final EditText aiInputField;
    public final LinearLayout bottomPanel;
    public final Button btnAiAssistant;
    public final Button btnBuildCloud;
    public final Button btnCheckSyntax;
    public final ImageButton btnCloseBottomPanel;
    public final Button btnCreatePr;
    public final ImageButton btnOpenFiles;
    public final ImageButton btnSendAi;
    public final DrawerLayout drawerLayout;
    public final CodeEditor editor;
    public final RecyclerView fileTreeRecycler;
    private final DrawerLayout rootView;
    public final RecyclerView tabsRecycler;

    private ActivityIdeBinding(DrawerLayout rootView, RecyclerView aiChatRecycler, EditText aiInputField, LinearLayout bottomPanel, Button btnAiAssistant, Button btnBuildCloud, Button btnCheckSyntax, ImageButton btnCloseBottomPanel, Button btnCreatePr, ImageButton btnOpenFiles, ImageButton btnSendAi, DrawerLayout drawerLayout, CodeEditor editor, RecyclerView fileTreeRecycler, RecyclerView tabsRecycler) {
        this.rootView = rootView;
        this.aiChatRecycler = aiChatRecycler;
        this.aiInputField = aiInputField;
        this.bottomPanel = bottomPanel;
        this.btnAiAssistant = btnAiAssistant;
        this.btnBuildCloud = btnBuildCloud;
        this.btnCheckSyntax = btnCheckSyntax;
        this.btnCloseBottomPanel = btnCloseBottomPanel;
        this.btnCreatePr = btnCreatePr;
        this.btnOpenFiles = btnOpenFiles;
        this.btnSendAi = btnSendAi;
        this.drawerLayout = drawerLayout;
        this.editor = editor;
        this.fileTreeRecycler = fileTreeRecycler;
        this.tabsRecycler = tabsRecycler;
    }

    @Override // androidx.viewbinding.ViewBinding
    public DrawerLayout getRoot() {
        return this.rootView;
    }

    public static ActivityIdeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityIdeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_ide, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityIdeBinding bind(View rootView) {
        int i = R.id.ai_chat_recycler;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.ai_chat_recycler);
        if (recyclerView != null) {
            i = R.id.ai_input_field;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.ai_input_field);
            if (editText != null) {
                i = R.id.bottom_panel;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.bottom_panel);
                if (linearLayout != null) {
                    i = R.id.btn_ai_assistant;
                    Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_ai_assistant);
                    if (button != null) {
                        i = R.id.btn_build_cloud;
                        Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_build_cloud);
                        if (button2 != null) {
                            i = R.id.btn_check_syntax;
                            Button button3 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_check_syntax);
                            if (button3 != null) {
                                i = R.id.btn_close_bottom_panel;
                                ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_close_bottom_panel);
                                if (imageButton != null) {
                                    i = R.id.btn_create_pr;
                                    Button button4 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_create_pr);
                                    if (button4 != null) {
                                        i = R.id.btn_open_files;
                                        ImageButton imageButton2 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_open_files);
                                        if (imageButton2 != null) {
                                            i = R.id.btn_send_ai;
                                            ImageButton imageButton3 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_send_ai);
                                            if (imageButton3 != null) {
                                                DrawerLayout drawerLayout = (DrawerLayout) rootView;
                                                i = R.id.editor;
                                                CodeEditor codeEditor = (CodeEditor) ViewBindings.findChildViewById(rootView, R.id.editor);
                                                if (codeEditor != null) {
                                                    i = R.id.file_tree_recycler;
                                                    RecyclerView recyclerView2 = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.file_tree_recycler);
                                                    if (recyclerView2 != null) {
                                                        i = R.id.tabs_recycler;
                                                        RecyclerView recyclerView3 = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.tabs_recycler);
                                                        if (recyclerView3 != null) {
                                                            return new ActivityIdeBinding(drawerLayout, recyclerView, editText, linearLayout, button, button2, button3, imageButton, button4, imageButton2, imageButton3, drawerLayout, codeEditor, recyclerView2, recyclerView3);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
