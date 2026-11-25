package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.cast.HlsSegmentFormat;
import com.google.common.io.Files;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.apkeditor.Options;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.UserDefinedScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;
import org.catrobat.catroid.content.strategy.ShowColorPickerFormulaEditorStrategy$$ExternalSyntheticLambda1;
import org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaEditorEditText;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.FormulaSpannableStringBuilder;
import org.catrobat.catroid.formulaeditor.InternFormulaKeyboardAdapter;
import org.catrobat.catroid.formulaeditor.InternFormulaParser;
import org.catrobat.catroid.formulaeditor.InternToken;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.UndoState;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.FormulaEditorClipboard;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.SpriteActivityOnTabSelectedListenerKt;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.dialogs.FormulaEditorComputeDialog;
import org.catrobat.catroid.ui.dialogs.FormulaEditorIntroDialog;
import org.catrobat.catroid.ui.dialogs.RuntimeConsoleActivity;
import org.catrobat.catroid.ui.dialogs.regexassistant.RegularExpressionAssistantDialog;
import org.catrobat.catroid.ui.recyclerview.adapter.CategoryListRVAdapter;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.fragment.CategoryListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.ui.runtimepermissions.BrickResourcesToRuntimePermissions;
import org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.ui.workspace.WorkspaceLayout;
import org.catrobat.catroid.userbrick.UserDefinedBrickInput;
import org.catrobat.catroid.utils.ProjectManagerExtensionsKt;
import org.catrobat.catroid.utils.ShowTextUtils;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.paintroid.colorpicker.ColorPickerDialog;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.idea.KotlinFileType;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/FormulaEditorFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FormulaEditorFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener, DataListFragment.FormulaEditorDataInterface {
    public static final String DO_NOT_SHOW_WARNING = "DO_NOT_SHOW_WARNING";
    public static final String FORMULA_BRICK_BUNDLE_ARGUMENT = "formula_brick";
    public static final String FORMULA_EDITOR_FRAGMENT_TAG = "FormulaEditorFragment";
    public static final String FORMULA_FIELD_BUNDLE_ARGUMENT = "formula_field";
    public static final int REQUEST_GPS = 1;
    public static final int REQUEST_PERMISSIONS_COMPUTE_DIALOG = 701;
    private static final int SET_FORMULA_ON_CREATE_VIEW = 0;
    private static final int SET_FORMULA_ON_RETURN_FROM_COLOR_PICKER = 3;
    private static final int SET_FORMULA_ON_RETURN_FROM_VISUAL_PLACEMENT = 2;
    private static final int SET_FORMULA_ON_SWITCH_EDIT_TEXT = 1;
    public static final String TAG = "FormulaEditorFragment";
    private static final int TIME_WINDOW = 2000;
    private static Formula currentFormula;
    private static Brick.FormulaField currentFormulaField;
    private Menu currentMenu;
    private FormulaBrick formulaBrick;
    private LinearLayout formulaEditorBrick;
    private FormulaEditorEditText formulaEditorEditText;
    private ViewGroup formulaEditorKeyboard;
    private long[] confirmSwitchEditTextTimeStamp = {0, 0};
    private int confirmSwitchEditTextCounter = 0;
    private boolean hasFormulaBeenChanged = false;
    private String actionBarTitleBuffer = "";
    private CategoryListRVAdapter.CategoryListItem chosenCategoryItem = null;
    private UserData<?> chosenUserDataItem = null;
    private boolean showCustomView = false;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getFragmentManager().popBackStack(FORMULA_EDITOR_FRAGMENT_TAG, 1);
            return;
        }
        this.formulaBrick = (FormulaBrick) getArguments().getSerializable(FORMULA_BRICK_BUNDLE_ARGUMENT);
        Brick.FormulaField formulaField = (Brick.FormulaField) getArguments().getSerializable(FORMULA_FIELD_BUNDLE_ARGUMENT);
        currentFormulaField = formulaField;
        currentFormula = this.formulaBrick.getFormulaWithBrickField(formulaField);
    }

    public void addCustomFunctionToActiveFormula(String customFunctionName) {
        FormulaEditorEditText formulaEditorEditText = this.formulaEditorEditText;
        if (formulaEditorEditText == null || formulaEditorEditText.getInternFormula() == null) {
            Log.w(TAG, "FormulaEditorEditText или его InternFormula не инициализированы в addCustomFunctionToActiveFormula");
            return;
        }
        List<InternToken> listBuildCustomJsFunction = new InternFormulaKeyboardAdapter().buildCustomJsFunction(customFunctionName);
        if (listBuildCustomJsFunction != null) {
            addTokensToActiveFormula(listBuildCustomJsFunction);
        } else {
            Log.e(TAG, "Не удалось создать токены для кастомной функции: " + customFunctionName);
        }
        updateButtonsOnKeyboardAndInvalidateOptionsMenu();
    }

    public void setShowCustomView(boolean showCustomView) {
        this.showCustomView = showCustomView;
    }

    private void addTokensToActiveFormula(List<InternToken> tokens) {
        FormulaEditorEditText formulaEditorEditText = this.formulaEditorEditText;
        if (formulaEditorEditText != null) {
            formulaEditorEditText.addTokensToActiveFormula(tokens);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        String string = "";
        if (supportActionBar != null) {
            CharSequence title = supportActionBar.getTitle();
            if (title != null) {
                string = title.toString();
            }
            this.actionBarTitleBuffer = string;
            supportActionBar.setTitle(R.string.formula_editor_title);
        } else {
            this.actionBarTitleBuffer = "";
        }
        setHasOptionsMenu(true);
        SettingsFragment.setToChosenLanguage(getActivity());
    }

    private static void showFragment(Context context, FormulaBrick formulaBrick, Brick.FormulaField formulaField, boolean showCustomView) {
        AppCompatActivity activityFromContextWrapper = UiUtils.getActivityFromContextWrapper(context);
        if (activityFromContextWrapper == null) {
            return;
        }
        WorkspaceLayout workspaceLayout = (WorkspaceLayout) activityFromContextWrapper.findViewById(R.id.workspace_layout);
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0) {
            workspaceLayout.openFormulaEditorWindow(formulaBrick, formulaField, showCustomView);
            return;
        }
        FragmentManager supportFragmentManager = activityFromContextWrapper.getSupportFragmentManager();
        String str = FORMULA_EDITOR_FRAGMENT_TAG;
        FormulaEditorFragment formulaEditorFragment = (FormulaEditorFragment) supportFragmentManager.findFragmentByTag(str);
        if (formulaEditorFragment == null) {
            FormulaEditorFragment formulaEditorFragment2 = new FormulaEditorFragment();
            formulaEditorFragment2.showCustomView = showCustomView;
            Bundle bundle = new Bundle();
            bundle.putSerializable(FORMULA_BRICK_BUNDLE_ARGUMENT, formulaBrick);
            bundle.putSerializable(FORMULA_FIELD_BUNDLE_ARGUMENT, formulaField);
            formulaEditorFragment2.setArguments(bundle);
            activityFromContextWrapper.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, formulaEditorFragment2, str).addToBackStack(str).commit();
            BottomBar.hideBottomBar(activityFromContextWrapper);
            return;
        }
        formulaEditorFragment.showCustomView = false;
        formulaEditorFragment.updateBrickView();
        formulaEditorFragment.setInputFormula(formulaField, 1);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (SnackbarUtil.areHintsEnabled(getActivity())) {
            SnackbarUtil.dismissAllHints();
            if (SnackbarUtil.wasHintAlreadyShown(getActivity(), getActivity().getResources().getResourceName(R.string.formula_editor_intro_title_formula_editor))) {
                return;
            }
            new FormulaEditorIntroDialog(this, R.style.StageDialog).show();
            SnackbarUtil.setHintShown(getActivity(), getActivity().getResources().getResourceName(R.string.formula_editor_intro_title_formula_editor));
        }
    }

    public static void showFragment(Context context, FormulaBrick formulaBrick, Brick.FormulaField formulaField) {
        showFragment(context, formulaBrick, formulaField, false);
    }

    public static void showCustomFragment(Context context, FormulaBrick formulaBrick, Brick.FormulaField formulaField) {
        showFragment(context, formulaBrick, formulaField, true);
    }

    public void updateBrickView() {
        this.formulaEditorBrick.removeAllViews();
        if (this.showCustomView) {
            this.formulaEditorEditText.setVisibility(8);
            this.formulaEditorKeyboard.setVisibility(8);
            this.formulaEditorBrick.addView(this.formulaBrick.getCustomView(getActivity()));
            return;
        }
        this.formulaEditorEditText.setVisibility(0);
        this.formulaEditorKeyboard.setVisibility(0);
        View view = this.formulaBrick.getView(getActivity());
        this.formulaBrick.setClickListeners();
        this.formulaBrick.disableSpinners();
        this.formulaBrick.highlightTextView(currentFormulaField);
        this.formulaEditorBrick.addView(view);
    }

    public void updateFragmentAfterVisualPlacement() {
        updateBrickView();
        setInputFormula(currentFormulaField, 2);
    }

    public void updateFragmentAfterColorPicker() {
        updateBrickView();
        setInputFormula(currentFormulaField, 3);
    }

    private void onUserDismiss() {
        refreshFormulaPreviewString(currentFormula.getTrimmedFormulaString(getActivity()));
        this.formulaEditorEditText.endEdit();
        View viewFindViewById = getActivity().findViewById(R.id.workspace_layout);
        if (viewFindViewById == null || viewFindViewById.getVisibility() != 0) {
            getFragmentManager().popBackStack();
        }
        if (getActivity() == null || (getActivity() instanceof RuntimeConsoleActivity)) {
            return;
        }
        BottomBar.showBottomBar(getActivity());
        BottomBar.showPlayButton(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View viewInflate = inflater.inflate(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("pref_pc_mode_enabled", false) ? R.layout.fragment_formula_editor_pc : R.layout.fragment_formula_editor, container, false);
        viewInflate.setFocusableInTouchMode(true);
        viewInflate.requestFocus();
        this.formulaEditorBrick = (LinearLayout) viewInflate.findViewById(R.id.formula_editor_brick_space);
        this.formulaEditorEditText = (FormulaEditorEditText) viewInflate.findViewById(R.id.formula_editor_edit_field);
        this.formulaEditorKeyboard = (ViewGroup) viewInflate.findViewById(R.id.formula_editor_keyboardview);
        this.formulaEditorEditText.setShowSoftInputOnFocus(false);
        updateBrickView();
        viewInflate.getViewTreeObserver().addOnGlobalLayoutListener(this);
        setInputFormula(currentFormulaField, 0);
        this.formulaEditorEditText.init(this);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null && appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setTitle(R.string.formula_editor_title);
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        this.formulaEditorKeyboard.setClickable(true);
        getView().requestFocus();
        attachTouchListenerToButtons(this.formulaEditorKeyboard, new View.OnTouchListener() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment.1
            private Handler clipboardHandler;
            private Runnable clipboardLongClickAction;
            private Runnable deleteAction;
            private Handler handler;
            private boolean isPasteLongClick = false;

            private boolean handleLongClick(final View view, MotionEvent event) {
                if (event.getAction() == 1) {
                    Handler handler = this.handler;
                    if (handler == null) {
                        return true;
                    }
                    handler.removeCallbacks(this.deleteAction);
                    this.handler = null;
                }
                if (event.getAction() == 0) {
                    this.deleteAction = new Runnable() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass1.this.handler.postDelayed(this, 100L);
                            if (FormulaEditorFragment.this.formulaEditorEditText.isThereSomethingToDelete()) {
                                FormulaEditorFragment.this.formulaEditorEditText.handleKeyEvent(view.getId(), "");
                            }
                        }
                    };
                    if (this.handler != null) {
                        return true;
                    }
                    Handler handler2 = new Handler();
                    this.handler = handler2;
                    handler2.postDelayed(this.deleteAction, 400L);
                }
                return true;
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent event) {
                Runnable runnable;
                if (event.getAction() == 1) {
                    FormulaEditorFragment.this.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                    view.setPressed(false);
                    if (view.getId() == R.id.formula_editor_keyboard_paste) {
                        Handler handler = this.clipboardHandler;
                        if (handler != null && (runnable = this.clipboardLongClickAction) != null) {
                            handler.removeCallbacks(runnable);
                        }
                        if (!this.isPasteLongClick) {
                            FormulaEditorClipboard.INSTANCE.paste(FormulaEditorFragment.this.formulaEditorEditText);
                        }
                        this.isPasteLongClick = false;
                        return true;
                    }
                    handleLongClick(view, event);
                    return true;
                }
                if (event.getAction() != 0) {
                    return false;
                }
                view.setPressed(true);
                switch (view.getId()) {
                    case R.id.formula_editor_keyboard_color_picker /* 2131364456 */:
                        FormulaEditorFragment.this.showColorPickerDialog(view);
                        return true;
                    case R.id.formula_editor_keyboard_compute /* 2131364457 */:
                        FormulaEditorFragment.this.showComputeDialog();
                        return true;
                    case R.id.formula_editor_keyboard_copy /* 2131364458 */:
                        FormulaEditorClipboard.INSTANCE.checkIfSelectedAndCopy(FormulaEditorFragment.this.formulaEditorEditText);
                        return true;
                    case R.id.formula_editor_keyboard_data /* 2131364459 */:
                        FormulaEditorFragment.this.showDataFragment();
                        return true;
                    case R.id.formula_editor_keyboard_decimal_mark /* 2131364460 */:
                    case R.id.formula_editor_keyboard_divide /* 2131364462 */:
                    case R.id.formula_editor_keyboard_minus /* 2131364466 */:
                    case R.id.formula_editor_keyboard_mult /* 2131364467 */:
                    case R.id.formula_editor_keyboard_plus /* 2131364470 */:
                    default:
                        FormulaEditorFragment.this.formulaEditorEditText.handleKeyEvent(view.getId(), "");
                        return true;
                    case R.id.formula_editor_keyboard_delete /* 2131364461 */:
                        FormulaEditorFragment.this.formulaEditorEditText.handleKeyEvent(view.getId(), "");
                        return handleLongClick(view, event);
                    case R.id.formula_editor_keyboard_function /* 2131364463 */:
                        FormulaEditorFragment.this.showCategoryListFragment(CategoryListFragment.FUNCTION_TAG, R.string.formula_editor_functions);
                        return true;
                    case R.id.formula_editor_keyboard_functional_button_toggle /* 2131364464 */:
                        FormulaEditorFragment.this.toggleFunctionalButtons();
                        return true;
                    case R.id.formula_editor_keyboard_logic /* 2131364465 */:
                        FormulaEditorFragment.this.showCategoryListFragment(CategoryListFragment.LOGIC_TAG, R.string.formula_editor_logic);
                        return true;
                    case R.id.formula_editor_keyboard_object /* 2131364468 */:
                        FormulaEditorFragment.this.showCategoryListFragment(CategoryListFragment.OBJECT_TAG, R.string.formula_editor_choose_object_variable);
                        return true;
                    case R.id.formula_editor_keyboard_paste /* 2131364469 */:
                        this.isPasteLongClick = false;
                        if (this.clipboardHandler == null) {
                            this.clipboardHandler = new Handler();
                        }
                        Runnable runnable2 = new Runnable() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                Context context = FormulaEditorFragment.this.getContext();
                                if (context == null || !FormulaEditorFragment.this.isAdded()) {
                                    return;
                                }
                                AnonymousClass1.this.isPasteLongClick = true;
                                FormulaEditorClipboard.INSTANCE.showClipboardHistoryDialog(context, FormulaEditorFragment.this.formulaEditorEditText);
                            }
                        };
                        this.clipboardLongClickAction = runnable2;
                        this.clipboardHandler.postDelayed(runnable2, 500L);
                        return true;
                    case R.id.formula_editor_keyboard_sensors /* 2131364471 */:
                        FormulaEditorFragment.this.showCategoryListFragment(CategoryListFragment.SENSOR_TAG, R.string.formula_editor_device);
                        return true;
                    case R.id.formula_editor_keyboard_string /* 2131364472 */:
                        if (FormulaEditorFragment.this.isSelectedTextFirstParamOfRegularExpression()) {
                            FormulaEditorFragment.this.showNewRegexAssistantDialog();
                        } else {
                            FormulaEditorFragment.this.showNewStringDialog();
                        }
                        return true;
                }
            }
        });
        updateButtonsOnKeyboardAndInvalidateOptionsMenu();
        this.formulaEditorEditText.requestFocus();
        this.formulaEditorEditText.setOnKeyListener(new View.OnKeyListener() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment.2
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int i;
                if (FormulaEditorFragment.this.formulaEditorEditText.getInternFormula() == null) {
                    return false;
                }
                if (event.isCtrlPressed() && event.getAction() == 0) {
                    if (keyCode == 31) {
                        FormulaEditorClipboard.INSTANCE.checkIfSelectedAndCopy(FormulaEditorFragment.this.formulaEditorEditText);
                        return true;
                    }
                    if (keyCode == 50) {
                        FormulaEditorClipboard.INSTANCE.paste(FormulaEditorFragment.this.formulaEditorEditText);
                        return true;
                    }
                    if (keyCode == 52) {
                        FormulaEditorClipboard.INSTANCE.checkIfSelectedAndCopy(FormulaEditorFragment.this.formulaEditorEditText);
                        if (FormulaEditorFragment.this.formulaEditorEditText.isThereSomethingToDelete()) {
                            FormulaEditorFragment.this.formulaEditorEditText.handleKeyEvent(R.id.formula_editor_keyboard_delete, "");
                        }
                        return true;
                    }
                    if (keyCode == 54) {
                        FormulaEditorFragment.this.formulaEditorEditText.undo();
                        FormulaEditorFragment.this.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                        return true;
                    }
                }
                if (keyCode == 21 || keyCode == 22 || keyCode == 19 || keyCode == 20) {
                    if (event.getAction() == 1) {
                        FormulaEditorFragment.this.formulaEditorEditText.syncCursorPosition();
                    }
                    return false;
                }
                if (event.getAction() == 0) {
                    char unicodeChar = (char) event.getUnicodeChar();
                    if (keyCode == 66 || keyCode == 160) {
                        if (FormulaEditorFragment.this.saveFormulaIfPossible()) {
                            FormulaEditorFragment.this.exitFormulaEditorFragment();
                        }
                        return true;
                    }
                    if (keyCode == 67) {
                        if (FormulaEditorFragment.this.formulaEditorEditText.isThereSomethingToDelete()) {
                            FormulaEditorFragment.this.formulaEditorEditText.handleKeyEvent(R.id.formula_editor_keyboard_delete, "");
                        }
                        return true;
                    }
                    if (unicodeChar >= '0' && unicodeChar <= '9') {
                        i = new int[]{R.id.formula_editor_keyboard_0, R.id.formula_editor_keyboard_1, R.id.formula_editor_keyboard_2, R.id.formula_editor_keyboard_3, R.id.formula_editor_keyboard_4, R.id.formula_editor_keyboard_5, R.id.formula_editor_keyboard_6, R.id.formula_editor_keyboard_7, R.id.formula_editor_keyboard_8, R.id.formula_editor_keyboard_9}[unicodeChar - ExternalAnnotationProvider.NULLABLE];
                    } else if (unicodeChar == '+') {
                        i = R.id.formula_editor_keyboard_plus;
                    } else if (unicodeChar == '-') {
                        i = R.id.formula_editor_keyboard_minus;
                    } else if (unicodeChar == '*' || unicodeChar == 'x' || unicodeChar == 'X') {
                        i = R.id.formula_editor_keyboard_mult;
                    } else if (unicodeChar == '/') {
                        i = R.id.formula_editor_keyboard_divide;
                    } else if (unicodeChar == '(') {
                        i = R.id.formula_editor_keyboard_bracket_open;
                    } else if (unicodeChar == ')') {
                        i = R.id.formula_editor_keyboard_bracket_close;
                    } else {
                        i = (unicodeChar == '.' || unicodeChar == ',') ? R.id.formula_editor_keyboard_decimal_mark : -1;
                    }
                    if (i != -1) {
                        FormulaEditorFragment.this.formulaEditorEditText.handleKeyEvent(i, "");
                        FormulaEditorFragment.this.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                        return true;
                    }
                    if (event.isPrintingKey() || keyCode == 62) {
                        return true;
                    }
                }
                return false;
            }
        });
        super.onStart();
    }

    private void attachTouchListenerToButtons(ViewGroup parent, View.OnTouchListener listener) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                attachTouchListenerToButtons((ViewGroup) childAt, listener);
            } else if (childAt.getId() != -1 && childAt.getVisibility() != 8) {
                childAt.setOnTouchListener(listener);
            }
        }
    }

    private void showColorPicker(ShowFormulaEditorStrategy.Callback callback, FragmentManager fragmentManager) {
        ColorPickerDialog colorPickerDialogNewInstance = ColorPickerDialog.INSTANCE.newInstance(callback.getValue(), true, true);
        colorPickerDialogNewInstance.setBitmap(ProjectManagerExtensionsKt.getProjectBitmap(ProjectManager.getInstance()));
        Objects.requireNonNull(callback);
        colorPickerDialogNewInstance.addOnColorPickedListener(new ShowColorPickerFormulaEditorStrategy$$ExternalSyntheticLambda1(callback));
        colorPickerDialogNewInstance.setStyle(0, R.style.AlertDialogWithTitle);
        colorPickerDialogNewInstance.show(fragmentManager, (String) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showColorPickerDialog(View view) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(view);
        if (activityFromView == null) {
            return;
        }
        FragmentManager supportFragmentManager = activityFromView.getSupportFragmentManager();
        if (supportFragmentManager.isStateSaved()) {
            return;
        }
        showColorPicker(new ShowFormulaEditorStrategy.Callback() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment.3
            @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
            public void showFormulaEditor(View view2) {
            }

            @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
            public void setValue(int value) {
                FormulaEditorFragment.this.addString(String.format("#%06X", Integer.valueOf(value & 16777215)));
            }

            @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
            public int getValue() {
                String selectedFormulaText = FormulaEditorFragment.this.getSelectedFormulaText();
                if (selectedFormulaText != null && selectedFormulaText.matches("^#[0-9A-Fa-f]{6}$")) {
                    return Color.parseColor(selectedFormulaText);
                }
                if (selectedFormulaText == null || !selectedFormulaText.matches("^#[0-9A-Fa-f]{8}$")) {
                    return 0;
                }
                return Color.parseColor(selectedFormulaText);
            }
        }, supportFragmentManager);
    }

    public void toggleFunctionalButtons() {
        View viewFindViewById = getActivity().findViewById(R.id.tableRow11);
        View viewFindViewById2 = getActivity().findViewById(R.id.tableRow12);
        ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.formula_editor_keyboard_functional_button_toggle);
        if (viewFindViewById == null || viewFindViewById2 == null || imageButton == null) {
            return;
        }
        boolean z = viewFindViewById.getVisibility() == 0;
        viewFindViewById.setVisibility(z ? 8 : 0);
        viewFindViewById2.setVisibility(z ? 8 : 0);
        imageButton.setImageDrawable(ContextCompat.getDrawable(getContext(), z ? R.drawable.ic_keyboard_toggle_caret_up : R.drawable.ic_keyboard_toggle_caret_down));
        toggleFormulaEditorSpace(z);
    }

    private void showBeautifulFilePickerDialog() {
        Project currentProject;
        Context context = getContext();
        if (context == null || (currentProject = ProjectManager.getInstance().getCurrentProject()) == null) {
            return;
        }
        File filesDir = currentProject.getFilesDir();
        if (filesDir == null || !filesDir.exists()) {
            ToastUtil.showError(context, "Files directory not found");
            return;
        }
        File[] fileArrListFiles = filesDir.listFiles(new FileFilter() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment.4
            @Override // java.io.FileFilter
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            ToastUtil.showInfoLong(context, getString(R.string.no_files_found));
            return;
        }
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_project_file_picker, (ViewGroup) null);
        EditText editText = (EditText) viewInflate.findViewById(R.id.search_file_input);
        ListView listView = (ListView) viewInflate.findViewById(R.id.project_files_list);
        ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            arrayList.add(file);
        }
        Collections.sort(arrayList, new Comparator<File>() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment.5
            @Override // java.util.Comparator
            public int compare(File f1, File f2) {
                return f1.getName().compareToIgnoreCase(f2.getName());
            }
        });
        final FileAdapter fileAdapter = new FileAdapter(context, arrayList);
        listView.setAdapter((ListAdapter) fileAdapter);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(context, 2131952355).setTitle(R.string.dialog_project_files_title).setView(viewInflate).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).create();
        editText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment.6
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fileAdapter.filter(s.toString());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                this.f$0.m5706xe27e286d(fileAdapter, alertDialogCreate, adapterView, view, i, j);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$showBeautifulFilePickerDialog$0$org-catrobat-catroid-ui-fragment-FormulaEditorFragment, reason: not valid java name */
    /* synthetic */ void m5706xe27e286d(FileAdapter fileAdapter, AlertDialog alertDialog, AdapterView adapterView, View view, int i, long j) {
        addString(fileAdapter.getItem(i).getName());
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/FormulaEditorFragment$FileAdapter.class
      classes14.dex
     */
    class FileAdapter extends ArrayAdapter<File> {
        private final List<File> filteredList;
        private final List<File> originalList;

        public FileAdapter(Context context, List<File> files) {
            super(context, 0, files);
            this.originalList = new ArrayList(files);
            this.filteredList = new ArrayList(files);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public int getCount() {
            return this.filteredList.size();
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public File getItem(int position) {
            return this.filteredList.get(position);
        }

        public void filter(String text) {
            this.filteredList.clear();
            if (text.isEmpty()) {
                this.filteredList.addAll(this.originalList);
            } else {
                String lowerCase = text.toLowerCase();
                for (File file : this.originalList) {
                    if (file.getName().toLowerCase().contains(lowerCase)) {
                        this.filteredList.add(file);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            String str;
            int i;
            String str2;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_project_file_picker, parent, false);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.file_icon);
            TextView textView = (TextView) convertView.findViewById(R.id.file_name);
            TextView textView2 = (TextView) convertView.findViewById(R.id.file_info);
            File item = getItem(position);
            textView.setText(item.getName());
            long length = item.length();
            if (length < 1024) {
                str = length + " B";
            } else if (length < 1048576) {
                str = String.format("%.1f KB", Double.valueOf(length / 1024.0d));
            } else {
                str = String.format("%.1f MB", Double.valueOf(length / 1048576.0d));
            }
            String lowerCase = getFileExtension(item.getName()).toLowerCase();
            if (lowerCase.equals("png") || lowerCase.equals("jpg") || lowerCase.equals("jpeg") || lowerCase.equals("webp") || lowerCase.equals("gif")) {
                i = R.drawable.ic_draw_image;
                str2 = "Image";
            } else if (lowerCase.equals(HlsSegmentFormat.MP3) || lowerCase.equals("wav") || lowerCase.equals("ogg") || lowerCase.equals("m4a")) {
                i = R.drawable.ic_music_library;
                str2 = "Audio";
            } else if (lowerCase.equals("xml") || lowerCase.equals(Options.TYPE_JSON) || lowerCase.equals("txt") || lowerCase.equals("py") || lowerCase.equals("lua") || lowerCase.equals("js") || lowerCase.equals(SuffixConstants.EXTENSION_java) || lowerCase.equals(KotlinFileType.EXTENSION) || lowerCase.equals("md")) {
                i = R.drawable.code_24px;
                str2 = "Text";
            } else if (lowerCase.equals("rscene") || lowerCase.equals("glb") || lowerCase.equals("obj")) {
                i = R.drawable.deployed_code_24px;
                str2 = ExifInterface.TAG_MODEL;
            } else {
                i = R.drawable.file_present_24px;
                str2 = "File";
            }
            imageView.setImageResource(i);
            imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.accent));
            textView2.setText(str2 + " • " + str);
            return convertView;
        }

        private String getFileExtension(String fileName) {
            int iLastIndexOf = fileName.lastIndexOf(46);
            if (iLastIndexOf == -1) {
                return "";
            }
            return fileName.substring(iLastIndexOf + 1);
        }
    }

    private void toggleFormulaEditorSpace(boolean isVisible) {
        View viewFindViewById = getActivity().findViewById(R.id.formula_editor_keyboardview);
        View viewFindViewById2 = getActivity().findViewById(R.id.formula_editor_brick_and_formula);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1, 1.0f);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -1, 1.0f);
        if (isVisible) {
            int height = getActivity().findViewById(R.id.tableRow11).getHeight() + getActivity().findViewById(R.id.tableRow12).getHeight();
            layoutParams.topMargin = height;
            layoutParams2.bottomMargin = -height;
        } else {
            layoutParams.topMargin = 0;
            layoutParams2.bottomMargin = 0;
        }
        viewFindViewById2.setLayoutParams(layoutParams2);
        viewFindViewById.setLayoutParams(layoutParams);
    }

    public FormulaEditorEditText getFormulaEditorEditText() {
        return this.formulaEditorEditText;
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        ActionBar supportActionBar;
        super.onStop();
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity == null || (supportActionBar = appCompatActivity.getSupportActionBar()) == null) {
            return;
        }
        supportActionBar.setTitle(this.actionBarTitleBuffer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSelectedTextFirstParamOfRegularExpression() {
        return getFormulaEditorEditText().isSelectedTokenFirstParamOfRegularExpression();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNewRegexAssistantDialog() {
        String selectedFormulaText = getSelectedFormulaText();
        TextInputDialog.Builder builder = new TextInputDialog.Builder(getContext());
        builder.setHint(getString(R.string.string_label)).setText(selectedFormulaText).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment$$ExternalSyntheticLambda2
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m5708x436657d0(dialogInterface, str);
            }
        });
        builder.setNeutralButton(R.string.assistant, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5709x449caaaf(dialogInterface, i);
            }
        });
        builder.setTitle(R.string.formula_editor_dialog_change_regular_expression).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$showNewRegexAssistantDialog$1$org-catrobat-catroid-ui-fragment-FormulaEditorFragment, reason: not valid java name */
    /* synthetic */ void m5708x436657d0(DialogInterface dialogInterface, String str) {
        addString(str);
    }

    /* JADX INFO: renamed from: lambda$showNewRegexAssistantDialog$2$org-catrobat-catroid-ui-fragment-FormulaEditorFragment, reason: not valid java name */
    /* synthetic */ void m5709x449caaaf(DialogInterface dialogInterface, int i) {
        openAssistantDialog();
    }

    private void openAssistantDialog() {
        new RegularExpressionAssistantDialog(getContext(), getFragmentManager()).createAssistant();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNewStringDialog() {
        String selectedFormulaText = getSelectedFormulaText();
        TextInputDialog.Builder builder = new TextInputDialog.Builder(getContext());
        builder.setHint(getString(R.string.string_label)).setText(selectedFormulaText).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m5710x5c6f8a94(dialogInterface, str);
            }
        });
        builder.setTitle(selectedFormulaText == null ? R.string.formula_editor_new_string_name : R.string.formula_editor_dialog_change_text).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$showNewStringDialog$3$org-catrobat-catroid-ui-fragment-FormulaEditorFragment, reason: not valid java name */
    /* synthetic */ void m5710x5c6f8a94(DialogInterface dialogInterface, String str) {
        addString(str);
    }

    public void addString(String string) {
        String selectedFormulaText = getSelectedFormulaText();
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        Context context = getContext();
        if (context != null) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (!(defaultSharedPreferences.contains(DO_NOT_SHOW_WARNING) ? defaultSharedPreferences.getBoolean(DO_NOT_SHOW_WARNING, false) : false) && recognizedFormulaInText(string, context, currentProject, currentSprite)) {
                showFormulaInTextWarning();
            }
        }
        if (selectedFormulaText != null && !selectedFormulaText.matches("\\s*")) {
            overrideSelectedText(string);
        } else {
            addStringToActiveFormula(string);
        }
        updateButtonsOnKeyboardAndInvalidateOptionsMenu();
    }

    public boolean recognizedFormulaInText(String string, Context context, Project project, Sprite sprite) {
        boolean z;
        String[] stringArray = context.getResources().getStringArray(R.array.formulas_with_params);
        String[] stringArray2 = context.getResources().getStringArray(R.array.formulas_without_params);
        int length = stringArray.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (string.matches(".*" + stringArray[i] + "\\(.+\\).*")) {
                z = true;
                break;
            }
            i++;
        }
        for (String str : stringArray2) {
            if (string.contains(str)) {
                z = true;
                break;
            }
        }
        return z | (stringContainsUserVariable(string, project.getMultiplayerVariables()) || stringContainsUserVariable(string, project.getUserVariables()) || stringContainsUserVariable(string, sprite.getUserVariables()) || stringContainsUserList(string, project.getUserLists()) || stringContainsUserList(string, sprite.getUserLists()) || stringContainsUserDefinedBrickInput(string));
    }

    private boolean stringContainsUserVariable(String string, List<UserVariable> variableList) {
        Iterator<UserVariable> it = variableList.iterator();
        while (it.hasNext()) {
            if (string.contains(it.next().getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean stringContainsUserList(String string, List<UserList> userList) {
        Iterator<UserList> it = userList.iterator();
        while (it.hasNext()) {
            if (string.contains(it.next().getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean stringContainsUserDefinedBrickInput(String string) {
        if (!(getFormulaBrick().getScript().getScriptBrick() instanceof UserDefinedReceiverBrick)) {
            return false;
        }
        Iterator<UserDefinedBrickInput> it = ((UserDefinedReceiverBrick) getFormulaBrick().getScript().getScriptBrick()).getUserDefinedBrick().getUserDefinedBrickInputs().iterator();
        while (it.hasNext()) {
            if (string.contains(it.next().getName())) {
                return true;
            }
        }
        return false;
    }

    private void showFormulaInTextWarning() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.warning).setMessage(R.string.warning_formula_recognized).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).setNegativeButton(R.string.do_not_show_again, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5707x5d8d7754(dialogInterface, i);
            }
        }).create().show();
    }

    /* JADX INFO: renamed from: lambda$showFormulaInTextWarning$4$org-catrobat-catroid-ui-fragment-FormulaEditorFragment, reason: not valid java name */
    /* synthetic */ void m5707x5d8d7754(DialogInterface dialogInterface, int i) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(DO_NOT_SHOW_WARNING, true).apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showComputeDialog() {
        InternFormulaParser formulaParser = this.formulaEditorEditText.getFormulaParser();
        final FormulaElement formula = formulaParser.parseFormula(generateScope());
        if (formula == null) {
            if (formulaParser.getErrorTokenIndex() >= 0) {
                this.formulaEditorEditText.setParseErrorCursorAndSelection();
            }
        } else {
            final Brick.ResourcesSet resourcesSet = new Brick.ResourcesSet();
            formula.addRequiredResources(resourcesSet);
            new RequiresPermissionTask(701, BrickResourcesToRuntimePermissions.translate(resourcesSet), R.string.runtime_permission_general) { // from class: org.catrobat.catroid.ui.fragment.FormulaEditorFragment.7
                @Override // org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask
                public void task() {
                    if (resourcesSet.contains(18)) {
                        SensorHandler.getInstance(FormulaEditorFragment.this.getActivity()).setLocationManager((LocationManager) FormulaEditorFragment.this.getActivity().getSystemService(FirebaseAnalytics.Param.LOCATION));
                        if (!SensorHandler.gpsAvailable()) {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
                            FormulaEditorFragment.this.startActivityForResult(intent, 1);
                            return;
                        }
                    }
                    Formula formula2 = new Formula(formula);
                    FormulaEditorComputeDialog formulaEditorComputeDialog = new FormulaEditorComputeDialog(FormulaEditorFragment.this.getActivity(), FormulaEditorFragment.this.generateScope());
                    formulaEditorComputeDialog.setFormula(formula2);
                    formulaEditorComputeDialog.show();
                }
            }.execute(getActivity());
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 0 && SensorHandler.gpsAvailable()) {
            showComputeDialog();
        } else {
            ToastUtil.showError(getActivity(), R.string.error_gps_not_available);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        this.currentMenu = menu;
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
        MenuItem menuItemFindItem = menu.findItem(R.id.menu_undo);
        FormulaEditorEditText formulaEditorEditText = this.formulaEditorEditText;
        if (formulaEditorEditText == null || !formulaEditorEditText.getHistory().undoIsPossible()) {
            menuItemFindItem.setIcon(R.drawable.icon_undo_disabled);
            menuItemFindItem.setEnabled(false);
        } else {
            menuItemFindItem.setIcon(R.drawable.icon_undo);
            menuItemFindItem.setEnabled(true);
        }
        MenuItem menuItemFindItem2 = menu.findItem(R.id.menu_redo);
        FormulaEditorEditText formulaEditorEditText2 = this.formulaEditorEditText;
        if (formulaEditorEditText2 == null || !formulaEditorEditText2.getHistory().redoIsPossible()) {
            menuItemFindItem2.setIcon(R.drawable.icon_redo_disabled);
            menuItemFindItem2.setEnabled(false);
        } else {
            menuItemFindItem2.setIcon(R.drawable.icon_redo);
            menuItemFindItem2.setEnabled(true);
        }
        menu.findItem(R.id.menu_undo).setVisible(true);
        menu.findItem(R.id.menu_redo).setVisible(true);
        menu.findItem(R.id.menu_files).setVisible(true);
        super.onPrepareOptionsMenu(menu);
        updateButtonsOnKeyboard();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_formulaeditor, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_files) {
            showBeautifulFilePickerDialog();
        } else if (itemId == R.id.menu_redo) {
            this.formulaEditorEditText.redo();
        } else if (itemId == R.id.menu_undo) {
            this.formulaEditorEditText.undo();
        }
        updateButtonsOnKeyboardAndInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    public void setInputFormula(Brick.FormulaField formulaField, int mode) {
        if (mode == 0) {
            this.formulaEditorEditText.enterNewFormula(new UndoState(currentFormula.getInternFormulaState(), formulaField));
            refreshFormulaPreviewString(this.formulaEditorEditText.getStringFromInternFormula());
            return;
        }
        if (mode == 1 || mode == 2 || mode == 3) {
            try {
                Formula formulaWithBrickField = this.formulaBrick.getFormulaWithBrickField(formulaField);
                if (currentFormula == formulaWithBrickField && this.formulaEditorEditText.hasChanges()) {
                    this.formulaEditorEditText.quickSelect();
                    return;
                }
                if (this.formulaEditorEditText.hasChanges()) {
                    long[] jArr = this.confirmSwitchEditTextTimeStamp;
                    jArr[0] = jArr[1];
                    jArr[1] = System.currentTimeMillis();
                    this.confirmSwitchEditTextCounter++;
                    if (!saveFormulaIfPossible()) {
                        return;
                    }
                }
                Menu menu = this.currentMenu;
                if (menu != null) {
                    MenuItem menuItemFindItem = menu.findItem(R.id.menu_undo);
                    if (menuItemFindItem != null) {
                        menuItemFindItem.setIcon(R.drawable.icon_undo_disabled);
                        menuItemFindItem.setEnabled(false);
                    }
                    MenuItem menuItemFindItem2 = this.currentMenu.findItem(R.id.menu_redo);
                    if (menuItemFindItem2 != null) {
                        menuItemFindItem2.setIcon(R.drawable.icon_redo_disabled);
                        menuItemFindItem2.setEnabled(false);
                    }
                }
                this.formulaEditorEditText.endEdit();
                currentFormulaField = formulaField;
                currentFormula = formulaWithBrickField;
                this.formulaEditorEditText.enterNewFormula(new UndoState(currentFormula.getInternFormulaState(), currentFormulaField));
                refreshFormulaPreviewString(this.formulaEditorEditText.getStringFromInternFormula());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Scope generateScope() {
        ScriptSequenceAction scriptSequenceAction;
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        Script script = this.formulaBrick.getScript();
        if (script instanceof UserDefinedScript) {
            List<UserDefinedBrickInput> userDefinedBrickInputs = ((UserDefinedReceiverBrick) script.getScriptBrick()).getUserDefinedBrick().getUserDefinedBrickInputs();
            ArrayList arrayList = new ArrayList();
            Iterator<UserDefinedBrickInput> it = userDefinedBrickInputs.iterator();
            while (it.hasNext()) {
                arrayList.add(convertUserDefinedBrickInputToUserVariable(it.next()));
            }
            scriptSequenceAction = new ScriptSequenceAction(script);
            ((UserDefinedScript) scriptSequenceAction.getScript()).setUserDefinedBrickInputs(arrayList);
        } else {
            scriptSequenceAction = null;
        }
        return new Scope(currentProject, currentSprite, scriptSequenceAction);
    }

    private UserVariable convertUserDefinedBrickInputToUserVariable(UserDefinedBrickInput input) {
        return new UserVariable(input.getName(), input.getValue().getUserFriendlyString(new ShowTextUtils.AndroidStringProvider(getContext()), null));
    }

    public boolean saveFormulaIfPossible() {
        InternFormulaParser formulaParser = this.formulaEditorEditText.getFormulaParser();
        FormulaElement formula = formulaParser.parseFormula(generateScope());
        int errorTokenIndex = formulaParser.getErrorTokenIndex();
        if (errorTokenIndex == -4) {
            Brick.FormulaField formulaField = currentFormulaField;
            if ((formulaField instanceof Brick.BrickField) && Brick.BrickField.isExpectingStringValue((Brick.BrickField) formulaField)) {
                return saveValidFormula(new FormulaElement(FormulaElement.ElementType.STRING, "", null));
            }
        } else {
            if (errorTokenIndex == -2) {
                return checkReturnWithoutSaving(-2);
            }
            if (errorTokenIndex == -1) {
                return saveValidFormula(formula);
            }
        }
        this.formulaEditorEditText.setParseErrorCursorAndSelection();
        return checkReturnWithoutSaving(-3);
    }

    private boolean saveValidFormula(FormulaElement formulaElement) {
        currentFormula.setRoot(formulaElement);
        this.formulaEditorEditText.formulaSaved();
        this.hasFormulaBeenChanged = true;
        return true;
    }

    private boolean checkReturnWithoutSaving(int errorType) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long[] jArr = this.confirmSwitchEditTextTimeStamp;
        if (jCurrentTimeMillis > jArr[0] + 2000 || this.confirmSwitchEditTextCounter <= 1) {
            if (errorType == -3) {
                ToastUtil.showError(getActivity(), R.string.formula_editor_parse_fail);
            } else if (errorType == -2) {
                ToastUtil.showError(getActivity(), R.string.formula_editor_parse_fail_formula_too_long);
            }
            return false;
        }
        jArr[0] = 0;
        jArr[1] = 0;
        this.confirmSwitchEditTextCounter = 0;
        ToastUtil.showSuccess(getActivity(), R.string.formula_editor_changes_discarded);
        return true;
    }

    private boolean hasFileChanged() {
        File file = new File(ProjectManager.getInstance().getCurrentProject().getDirectory(), Constants.CODE_XML_FILE_NAME);
        File file2 = new File(ProjectManager.getInstance().getCurrentProject().getDirectory(), Constants.UNDO_CODE_XML_FILE_NAME);
        if (!file.exists() || !file2.exists()) {
            return false;
        }
        try {
            return !Files.readLines(file, StandardCharsets.UTF_8).equals(Files.readLines(file2, StandardCharsets.UTF_8));
        } catch (IOException e) {
            Log.e(TAG, "Comparing project files failed.", e);
            return false;
        }
    }

    public void exitFormulaEditorFragment() {
        ScriptFragment scriptFragment;
        if (getActivity() instanceof SpriteActivity) {
            ((SpriteActivity) getActivity()).setUndoMenuItemVisibility(false);
        }
        if (this.hasFormulaBeenChanged || this.formulaEditorEditText.hasChanges()) {
            if (!saveFormulaIfPossible()) {
                return;
            } else {
                this.hasFormulaBeenChanged = false;
            }
        }
        ScriptFragment scriptFragment2 = (ScriptFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG);
        if (scriptFragment2 != null) {
            scriptFragment2.notifyDataSetChanged();
        }
        onUserDismiss();
        if (!(getActivity() instanceof RuntimeConsoleActivity)) {
            XstreamSerializer.getInstance().saveProject(ProjectManager.getInstance().getCurrentProject());
        }
        if (getActivity() == null || (scriptFragment = (ScriptFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG)) == null) {
            return;
        }
        if ((hasFileChanged() || scriptFragment.checkVariables()) && (getActivity() instanceof SpriteActivity)) {
            ((SpriteActivity) getActivity()).setUndoMenuItemVisibility(true);
        }
    }

    public void endFormulaEditor() {
        if (this.formulaEditorEditText.hasChanges()) {
            if (saveFormulaIfPossible()) {
                this.hasFormulaBeenChanged = false;
                onUserDismiss();
                return;
            }
            return;
        }
        onUserDismiss();
    }

    public void refreshFormulaPreviewString(String newString) {
        updateBrickView();
        TextView textView = this.formulaBrick.getTextView(currentFormulaField);
        textView.setText(FormulaSpannableStringBuilder.buildSpannableFormulaString(getContext(), newString, textView.getTextSize()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCategoryListFragment(String tag, int actionbarResId) {
        View viewFindViewById = getActivity().findViewById(R.id.workspace_layout);
        if (viewFindViewById != null && viewFindViewById.getVisibility() == 0) {
            ((WorkspaceLayout) viewFindViewById).openCategoryListWindow(tag, getActivity().getString(actionbarResId));
            return;
        }
        CategoryListFragment categoryListFragment = new CategoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CategoryListFragment.ACTION_BAR_TITLE_BUNDLE_ARGUMENT, getActivity().getString(actionbarResId));
        bundle.putString(CategoryListFragment.FRAGMENT_TAG_BUNDLE_ARGUMENT, tag);
        categoryListFragment.setArguments(bundle);
        categoryListFragment.onPrepareOptionsMenu(this.currentMenu);
        getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentByTag(FORMULA_EDITOR_FRAGMENT_TAG)).add(R.id.fragment_container, categoryListFragment, tag).addToBackStack(tag).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDataFragment() {
        View viewFindViewById = getActivity().findViewById(R.id.workspace_layout);
        if (viewFindViewById != null && viewFindViewById.getVisibility() == 0) {
            ((WorkspaceLayout) viewFindViewById).openDataListWindow(this.formulaBrick.getScript().getScriptBrick());
            return;
        }
        DataListFragment dataListFragment = new DataListFragment();
        dataListFragment.setFormulaEditorDataInterface(this);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataListFragment.PARENT_SCRIPT_BRICK_BUNDLE_ARGUMENT, this.formulaBrick.getScript().getScriptBrick());
        dataListFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentByTag(FORMULA_EDITOR_FRAGMENT_TAG)).add(R.id.fragment_container, dataListFragment, DataListFragment.TAG).addToBackStack(DataListFragment.TAG).commit();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment.FormulaEditorDataInterface
    public void onVariableRenamed(String previousName, String newName) {
        this.formulaEditorEditText.updateVariableReferences(previousName, newName);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment.FormulaEditorDataInterface
    public void onListRenamed(String previousName, String newName) {
        this.formulaEditorEditText.updateListReferences(previousName, newName);
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        this.formulaEditorBrick.getGlobalVisibleRect(rect);
        this.formulaEditorKeyboard.getGlobalVisibleRect(rect2);
    }

    public void addResourceToActiveFormula(int resource) {
        this.formulaEditorEditText.handleKeyEvent(resource, "");
        if (resource == R.string.formula_editor_function_collides_with_edge || resource == R.string.formula_editor_function_touched) {
            ProjectManager.getInstance().getCurrentSprite().createCollisionPolygons();
        }
    }

    public void addUserListToActiveFormula(String userListName) {
        this.formulaEditorEditText.handleKeyEvent(1, userListName);
    }

    public void addUserVariableToActiveFormula(String userVariableName) {
        this.formulaEditorEditText.handleKeyEvent(0, userVariableName);
    }

    public void addUserDefinedBrickInputToActiveFormula(String userDefinedBrickInput) {
        this.formulaEditorEditText.handleKeyEvent(2, userDefinedBrickInput);
    }

    public void addCollideFormulaToActiveFormula(String spriteName) {
        this.formulaEditorEditText.handleKeyEvent(3, spriteName);
    }

    public void addStringToActiveFormula(String string) {
        this.formulaEditorEditText.handleKeyEvent(R.id.formula_editor_keyboard_string, string);
    }

    public String getSelectedFormulaText() {
        return this.formulaEditorEditText.getSelectedTextFromInternFormula();
    }

    public FormulaBrick getFormulaBrick() {
        return this.formulaBrick;
    }

    public Brick.FormulaField getCurrentBrickField() {
        return currentFormulaField;
    }

    public void overrideSelectedText(String string) {
        this.formulaEditorEditText.overrideSelectedText(string);
    }

    public void setChosenCategoryItem(CategoryListRVAdapter.CategoryListItem chosenCategoryItem) {
        this.chosenCategoryItem = chosenCategoryItem;
        View viewFindViewById = getActivity() != null ? getActivity().findViewById(R.id.workspace_layout) : null;
        if (viewFindViewById == null || viewFindViewById.getVisibility() != 0) {
            return;
        }
        ((WorkspaceLayout) viewFindViewById).setProgrammaticBackPress(true);
        onHiddenChanged(false);
    }

    public void setChosenUserDataItem(UserData<?> chosenUserDataItem) {
        this.chosenUserDataItem = chosenUserDataItem;
        View viewFindViewById = getActivity() != null ? getActivity().findViewById(R.id.workspace_layout) : null;
        if (viewFindViewById == null || viewFindViewById.getVisibility() != 0) {
            return;
        }
        ((WorkspaceLayout) viewFindViewById).setProgrammaticBackPress(true);
        onHiddenChanged(false);
        if (getActivity() instanceof SpriteActivity) {
            getActivity().onBackPressed();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        if (getActivity() == null || hidden) {
            return;
        }
        ActionBar supportActionBar = getActivity() instanceof AppCompatActivity ? ((AppCompatActivity) getActivity()).getSupportActionBar() : null;
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.formula_editor_title);
        }
        BottomBar.hideBottomBar(getActivity());
        updateButtonsOnKeyboardAndInvalidateOptionsMenu();
        updateBrickView();
        CategoryListRVAdapter.CategoryListItem categoryListItem = this.chosenCategoryItem;
        if (categoryListItem != null) {
            if (categoryListItem.isCustomFunction && this.chosenCategoryItem.customFunctionName != null) {
                addCustomFunctionToActiveFormula(this.chosenCategoryItem.customFunctionName);
            } else {
                addResourceToActiveFormula(this.chosenCategoryItem.nameResId);
            }
            this.chosenCategoryItem = null;
        }
        UserData<?> userData = this.chosenUserDataItem;
        if (userData != null) {
            if (userData instanceof UserVariable) {
                addUserVariableToActiveFormula(userData.getName());
            } else if (userData instanceof UserList) {
                addUserListToActiveFormula(userData.getName());
            } else if (userData instanceof UserDefinedBrickInput) {
                addUserDefinedBrickInputToActiveFormula(userData.getName());
            }
            this.chosenUserDataItem = null;
        }
        FormulaEditorEditText formulaEditorEditText = this.formulaEditorEditText;
        if (formulaEditorEditText != null) {
            formulaEditorEditText.requestFocus();
        }
    }

    public void updateButtonsOnKeyboardAndInvalidateOptionsMenu() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.invalidateOptionsMenu();
        }
        updateButtonsOnKeyboard();
    }

    public void updateButtonsOnKeyboard() {
        ImageButton imageButton;
        FragmentActivity activity = getActivity();
        if (activity == null || (imageButton = (ImageButton) activity.findViewById(R.id.formula_editor_keyboard_delete)) == null) {
            return;
        }
        if (!this.formulaEditorEditText.isThereSomethingToDelete()) {
            imageButton.setAlpha(85);
            imageButton.setEnabled(false);
        } else {
            imageButton.setAlpha(255);
            imageButton.setEnabled(true);
        }
    }

    public int getIndexOfCorrespondingRegularExpression() {
        return this.formulaEditorEditText.getIndexOfCorrespondingRegularExpression();
    }

    public void setSelectionToFirstParamOfRegularExpressionAtInternalIndex(int indexOfRegularExpression) {
        this.formulaEditorEditText.setSelectionToFirstParamOfRegularExpressionAtInternalIndex(indexOfRegularExpression);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        SpriteActivityOnTabSelectedListenerKt.removeTabLayout(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        SpriteActivityOnTabSelectedListenerKt.addTabLayout(getActivity(), 0);
        super.onDetach();
    }
}
