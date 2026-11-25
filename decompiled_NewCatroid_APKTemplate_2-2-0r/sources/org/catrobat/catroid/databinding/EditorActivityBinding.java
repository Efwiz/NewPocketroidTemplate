package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/EditorActivityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class EditorActivityBinding implements ViewBinding {
    public final Button btnAddCube;
    public final Button btnAddEmpty;
    public final Button btnAddSphere;
    public final Button btnCamA;
    public final Button btnCamD;
    public final Button btnCamE;
    public final Button btnCamQ;
    public final Button btnCamS;
    public final Button btnCamShift;
    public final Button btnCamW;
    public final ImageButton btnMassDelete;
    public final ImageButton btnQuickDelete;
    public final ImageButton btnQuickDuplicate;
    public final ImageButton btnQuickFocus;
    public final ImageButton btnQuickInspector;
    public final ImageButton btnQuickMoveToCam;
    public final ImageButton btnQuickRotateToCam;
    public final ImageButton btnRedo;
    public final ImageButton btnUndo;
    public final GridLayout cameraControls;
    public final DrawerLayout drawerLayout;
    public final FrameLayout fragmentContainer;
    public final RecyclerView hierarchyRecyclerview;
    public final EditText hierarchySearch;
    public final LinearLayout inspectorContainer;
    public final TextView inspectorTitle;
    public final CardView leftDrawer;
    public final CoordinatorLayout mainContentCoordinator;
    public final LinearLayout quickActionsPanel;
    public final CardView rightDrawer;
    private final DrawerLayout rootView;
    public final Toolbar toolbar;

    private EditorActivityBinding(DrawerLayout rootView, Button btnAddCube, Button btnAddEmpty, Button btnAddSphere, Button btnCamA, Button btnCamD, Button btnCamE, Button btnCamQ, Button btnCamS, Button btnCamShift, Button btnCamW, ImageButton btnMassDelete, ImageButton btnQuickDelete, ImageButton btnQuickDuplicate, ImageButton btnQuickFocus, ImageButton btnQuickInspector, ImageButton btnQuickMoveToCam, ImageButton btnQuickRotateToCam, ImageButton btnRedo, ImageButton btnUndo, GridLayout cameraControls, DrawerLayout drawerLayout, FrameLayout fragmentContainer, RecyclerView hierarchyRecyclerview, EditText hierarchySearch, LinearLayout inspectorContainer, TextView inspectorTitle, CardView leftDrawer, CoordinatorLayout mainContentCoordinator, LinearLayout quickActionsPanel, CardView rightDrawer, Toolbar toolbar) {
        this.rootView = rootView;
        this.btnAddCube = btnAddCube;
        this.btnAddEmpty = btnAddEmpty;
        this.btnAddSphere = btnAddSphere;
        this.btnCamA = btnCamA;
        this.btnCamD = btnCamD;
        this.btnCamE = btnCamE;
        this.btnCamQ = btnCamQ;
        this.btnCamS = btnCamS;
        this.btnCamShift = btnCamShift;
        this.btnCamW = btnCamW;
        this.btnMassDelete = btnMassDelete;
        this.btnQuickDelete = btnQuickDelete;
        this.btnQuickDuplicate = btnQuickDuplicate;
        this.btnQuickFocus = btnQuickFocus;
        this.btnQuickInspector = btnQuickInspector;
        this.btnQuickMoveToCam = btnQuickMoveToCam;
        this.btnQuickRotateToCam = btnQuickRotateToCam;
        this.btnRedo = btnRedo;
        this.btnUndo = btnUndo;
        this.cameraControls = cameraControls;
        this.drawerLayout = drawerLayout;
        this.fragmentContainer = fragmentContainer;
        this.hierarchyRecyclerview = hierarchyRecyclerview;
        this.hierarchySearch = hierarchySearch;
        this.inspectorContainer = inspectorContainer;
        this.inspectorTitle = inspectorTitle;
        this.leftDrawer = leftDrawer;
        this.mainContentCoordinator = mainContentCoordinator;
        this.quickActionsPanel = quickActionsPanel;
        this.rightDrawer = rightDrawer;
        this.toolbar = toolbar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public DrawerLayout getRoot() {
        return this.rootView;
    }

    public static EditorActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static EditorActivityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.editor_activity, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static EditorActivityBinding bind(View rootView) {
        int i = R.id.btn_add_cube;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_add_cube);
        if (button != null) {
            i = R.id.btn_add_empty;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_add_empty);
            if (button2 != null) {
                i = R.id.btn_add_sphere;
                Button button3 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_add_sphere);
                if (button3 != null) {
                    i = R.id.btn_cam_a;
                    Button button4 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_cam_a);
                    if (button4 != null) {
                        i = R.id.btn_cam_d;
                        Button button5 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_cam_d);
                        if (button5 != null) {
                            i = R.id.btn_cam_e;
                            Button button6 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_cam_e);
                            if (button6 != null) {
                                i = R.id.btn_cam_q;
                                Button button7 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_cam_q);
                                if (button7 != null) {
                                    i = R.id.btn_cam_s;
                                    Button button8 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_cam_s);
                                    if (button8 != null) {
                                        i = R.id.btn_cam_shift;
                                        Button button9 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_cam_shift);
                                        if (button9 != null) {
                                            i = R.id.btn_cam_w;
                                            Button button10 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_cam_w);
                                            if (button10 != null) {
                                                i = R.id.btn_mass_delete;
                                                ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_mass_delete);
                                                if (imageButton != null) {
                                                    i = R.id.btn_quick_delete;
                                                    ImageButton imageButton2 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_quick_delete);
                                                    if (imageButton2 != null) {
                                                        i = R.id.btn_quick_duplicate;
                                                        ImageButton imageButton3 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_quick_duplicate);
                                                        if (imageButton3 != null) {
                                                            i = R.id.btn_quick_focus;
                                                            ImageButton imageButton4 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_quick_focus);
                                                            if (imageButton4 != null) {
                                                                i = R.id.btn_quick_inspector;
                                                                ImageButton imageButton5 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_quick_inspector);
                                                                if (imageButton5 != null) {
                                                                    i = R.id.btn_quick_move_to_cam;
                                                                    ImageButton imageButton6 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_quick_move_to_cam);
                                                                    if (imageButton6 != null) {
                                                                        i = R.id.btn_quick_rotate_to_cam;
                                                                        ImageButton imageButton7 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_quick_rotate_to_cam);
                                                                        if (imageButton7 != null) {
                                                                            i = R.id.btn_redo;
                                                                            ImageButton imageButton8 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_redo);
                                                                            if (imageButton8 != null) {
                                                                                i = R.id.btn_undo;
                                                                                ImageButton imageButton9 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_undo);
                                                                                if (imageButton9 != null) {
                                                                                    i = R.id.camera_controls;
                                                                                    GridLayout gridLayout = (GridLayout) ViewBindings.findChildViewById(rootView, R.id.camera_controls);
                                                                                    if (gridLayout != null) {
                                                                                        DrawerLayout drawerLayout = (DrawerLayout) rootView;
                                                                                        i = R.id.fragment_container;
                                                                                        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.fragment_container);
                                                                                        if (frameLayout != null) {
                                                                                            i = R.id.hierarchy_recyclerview;
                                                                                            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.hierarchy_recyclerview);
                                                                                            if (recyclerView != null) {
                                                                                                i = R.id.hierarchy_search;
                                                                                                EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.hierarchy_search);
                                                                                                if (editText != null) {
                                                                                                    i = R.id.inspector_container;
                                                                                                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.inspector_container);
                                                                                                    if (linearLayout != null) {
                                                                                                        i = R.id.inspector_title;
                                                                                                        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.inspector_title);
                                                                                                        if (textView != null) {
                                                                                                            i = R.id.left_drawer;
                                                                                                            CardView cardView = (CardView) ViewBindings.findChildViewById(rootView, R.id.left_drawer);
                                                                                                            if (cardView != null) {
                                                                                                                i = R.id.main_content_coordinator;
                                                                                                                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) ViewBindings.findChildViewById(rootView, R.id.main_content_coordinator);
                                                                                                                if (coordinatorLayout != null) {
                                                                                                                    i = R.id.quick_actions_panel;
                                                                                                                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.quick_actions_panel);
                                                                                                                    if (linearLayout2 != null) {
                                                                                                                        i = R.id.right_drawer;
                                                                                                                        CardView cardView2 = (CardView) ViewBindings.findChildViewById(rootView, R.id.right_drawer);
                                                                                                                        if (cardView2 != null) {
                                                                                                                            i = R.id.toolbar;
                                                                                                                            Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(rootView, R.id.toolbar);
                                                                                                                            if (toolbar != null) {
                                                                                                                                return new EditorActivityBinding(drawerLayout, button, button2, button3, button4, button5, button6, button7, button8, button9, button10, imageButton, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, imageButton8, imageButton9, gridLayout, drawerLayout, frameLayout, recyclerView, editText, linearLayout, textView, cardView, coordinatorLayout, linearLayout2, cardView2, toolbar);
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
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
