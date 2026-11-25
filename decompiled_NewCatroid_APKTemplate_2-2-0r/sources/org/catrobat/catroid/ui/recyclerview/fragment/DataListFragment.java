package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.badlogic.gdx.Net;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.material.card.MaterialCardView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.adapter.DataListAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.userbrick.UserDefinedBrickInput;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.UserDataUtil;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/DataListFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: DataListFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Â\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b!\u0018\u0000 x2\u00020\u00012\u00020\u00022\u00020\u00032\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0004:\u0003wxyB\u0005¢\u0006\u0002\u0010\u0006J\b\u0010-\u001a\u00020.H\u0002J\u001a\u0010/\u001a\u00020.2\u0010\u00100\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050&H\u0002J\u001e\u00101\u001a\u00020.2\n\u00102\u001a\u0006\u0012\u0002\b\u00030\u00052\b\u00103\u001a\u0004\u0018\u00010\u0013H\u0002J\b\u00104\u001a\u00020.H\u0002J\b\u00105\u001a\u00020.H\u0002J\u0006\u00106\u001a\u00020.J\\\u00107\u001a\u00020.2\f\u00108\u001a\b\u0012\u0004\u0012\u00020'0&2\f\u00109\u001a\b\u0012\u0004\u0012\u00020!0\u001e2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020!0\u001e2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020!0\u001e2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0002J\b\u0010>\u001a\u00020.H\u0002J\u0006\u0010?\u001a\u00020.J\u0018\u0010@\u001a\u00020\u00192\u0006\u0010A\u001a\u00020\b2\u0006\u00102\u001a\u00020BH\u0016J\u0012\u0010C\u001a\u00020.2\b\u0010D\u001a\u0004\u0018\u00010EH\u0016J\b\u0010F\u001a\u00020.H\u0002J\u0018\u0010G\u001a\u00020\u00192\u0006\u0010A\u001a\u00020\b2\u0006\u0010H\u001a\u00020IH\u0017J\u0018\u0010J\u001a\u00020.2\u0006\u0010H\u001a\u00020I2\u0006\u0010K\u001a\u00020LH\u0016J&\u0010M\u001a\u0004\u0018\u00010N2\u0006\u0010K\u001a\u00020O2\b\u0010P\u001a\u0004\u0018\u00010Q2\b\u0010R\u001a\u0004\u0018\u00010EH\u0016J\u0010\u0010S\u001a\u00020.2\u0006\u0010A\u001a\u00020\bH\u0016J\u001e\u0010T\u001a\u00020.2\n\u00102\u001a\u0006\u0012\u0002\b\u00030\u00052\b\u0010U\u001a\u0004\u0018\u00010VH\u0016J\u001c\u0010W\u001a\u00020.2\n\u00102\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010X\u001a\u00020YH\u0016J\u0010\u0010Z\u001a\u00020\u00192\u0006\u00102\u001a\u00020BH\u0016J\b\u0010[\u001a\u00020.H\u0016J\u0018\u0010\\\u001a\u00020\u00192\u0006\u0010A\u001a\u00020\b2\u0006\u0010H\u001a\u00020IH\u0016J\u0010\u0010]\u001a\u00020.2\u0006\u0010H\u001a\u00020IH\u0016J\b\u0010^\u001a\u00020.H\u0016J\u0010\u0010_\u001a\u00020.2\u0006\u0010`\u001a\u00020\nH\u0016J\u001e\u0010a\u001a\u00020.2\n\u00102\u001a\u0006\u0012\u0002\b\u00030\u00052\b\u0010b\u001a\u0004\u0018\u00010NH\u0016J\b\u0010c\u001a\u00020.H\u0016J\u001e\u0010d\u001a\u00020.2\n\u00102\u001a\u0006\u0012\u0002\b\u00030\u00052\b\u0010e\u001a\u0004\u0018\u00010\u0013H\u0002J\b\u0010f\u001a\u00020.H\u0002J\u0010\u0010g\u001a\u00020.2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017J\u000e\u0010h\u001a\u00020.2\u0006\u0010i\u001a\u00020\u0019J\u0016\u0010j\u001a\u00020.2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0002J\u0016\u0010l\u001a\u00020.2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020!0\u001eH\u0002J\u0006\u0010m\u001a\u00020\u0019J\u001a\u0010n\u001a\u00020.2\u0010\u00100\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050&H\u0002J\u001a\u0010o\u001a\u00020.2\u0010\u00100\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050&H\u0002J\u001a\u0010p\u001a\u00020.2\u0010\u00100\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050&H\u0002J\u001c\u0010q\u001a\u00020.2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010r\u001a\u00020\u0019J\u001c\u0010s\u001a\u00020.2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020!0\u001e2\u0006\u0010r\u001a\u00020\u0019J\\\u0010t\u001a\u00020.2\f\u00108\u001a\b\u0012\u0004\u0012\u00020'0&2\f\u00109\u001a\b\u0012\u0004\u0012\u00020!0\u001e2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020!0\u001e2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020!0\u001e2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0002J\u0010\u0010u\u001a\u00020.2\u0006\u0010v\u001a\u00020\nH\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u000b\u0010\u0006\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020!0\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020!0\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006z"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/DataListFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/view/ActionMode$Callback;", "Lorg/catrobat/catroid/ui/recyclerview/adapter/RVAdapter$SelectionListener;", "Lorg/catrobat/catroid/ui/recyclerview/adapter/RVAdapter$OnItemClickListener;", "Lorg/catrobat/catroid/formulaeditor/UserData;", "()V", "actionMode", "Landroid/view/ActionMode;", "actionModeType", "", "getActionModeType$annotations", "getActionModeType", "()I", "setActionModeType", "(I)V", "adapter", "Lorg/catrobat/catroid/ui/recyclerview/adapter/DataListAdapter;", "currentSearchQuery", "", "emptyView", "Landroid/widget/TextView;", "formulaEditorDataInterface", "Lorg/catrobat/catroid/ui/recyclerview/fragment/DataListFragment$FormulaEditorDataInterface;", "indexVariable", "", "isObserverRegistered", "observer", "Landroidx/recyclerview/widget/RecyclerView$AdapterDataObserver;", "originalGlobalLists", "", "Lorg/catrobat/catroid/formulaeditor/UserList;", "originalGlobalVars", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "originalLocalLists", "originalLocalVars", "originalMultiplayerVars", "originalUserDefinedBrickInputs", "", "Lorg/catrobat/catroid/userbrick/UserDefinedBrickInput;", "parentScriptBrick", "Lorg/catrobat/catroid/content/bricks/ScriptBrick;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "sortData", "applyFilterAndSetAdapter", "", "deleteItems", "selectedItems", "editItem", "item", "value", "finishActionMode", "handleContextualAction", "indexAndSort", "initialIndexing", "userDefinedBrickInputs", "globalVars", "localVars", "multiplayerVars", "globalLists", "localLists", "initializeAdapter", "notifyDataSetChanged", "onActionItemClicked", "mode", "Landroid/view/MenuItem;", "onActivityCreated", "savedInstance", "Landroid/os/Bundle;", "onAdapterReady", "onCreateActionMode", LineReader.MENU, "Landroid/view/Menu;", "onCreateOptionsMenu", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "onDestroyActionMode", "onItemClick", "selectionManager", "Lorg/catrobat/catroid/ui/recyclerview/adapter/multiselection/MultiSelectionManager;", "onItemLongClick", "holder", "Lorg/catrobat/catroid/ui/recyclerview/viewholder/CheckableViewHolder;", "onOptionsItemSelected", "onPause", "onPrepareActionMode", "onPrepareOptionsMenu", "onResume", "onSelectionChanged", "selectedItemCnt", "onSettingsClick", Promotion.ACTION_VIEW, "onStop", "renameItem", "name", "resetActionModeParameters", "setFormulaEditorDataInterface", "setShowEmptyView", "visible", "setUserListIndex", "data", "setUserVariableIndex", "shouldShowEmptyView", "showDeleteAlert", "showEditDialog", "showRenameDialog", "sortUserList", "sorted", "sortUserVariable", "sortVariableAndList", "startActionMode", ModuleXmlParser.TYPE, "ActionModeType", "Companion", "FormulaEditorDataInterface", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataListFragment extends Fragment implements ActionMode.Callback, RVAdapter.SelectionListener, RVAdapter.OnItemClickListener<UserData<?>> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int DELETE = 1;
    private static final int NONE = 0;
    public static final String PARENT_SCRIPT_BRICK_BUNDLE_ARGUMENT = "parent_script_brick";
    public static final String TAG;
    private static DataListFragment activeInstance;
    private ActionMode actionMode;
    private int actionModeType;
    private DataListAdapter adapter;
    private TextView emptyView;
    private FormulaEditorDataInterface formulaEditorDataInterface;
    private boolean indexVariable;
    private boolean isObserverRegistered;
    private ScriptBrick parentScriptBrick;
    private RecyclerView recyclerView;
    private boolean sortData;
    private List<? extends UserDefinedBrickInput> originalUserDefinedBrickInputs = CollectionsKt.emptyList();
    private List<UserVariable> originalGlobalVars = new ArrayList();
    private List<UserVariable> originalLocalVars = new ArrayList();
    private List<UserVariable> originalMultiplayerVars = new ArrayList();
    private List<UserList> originalGlobalLists = new ArrayList();
    private List<UserList> originalLocalLists = new ArrayList();
    private String currentSearchQuery = "";
    private RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$observer$1
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            super.onChanged();
            DataListFragment dataListFragment = this.this$0;
            dataListFragment.setShowEmptyView(dataListFragment.shouldShowEmptyView());
        }
    };

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/DataListFragment$ActionModeType.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DataListFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0081\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/DataListFragment$ActionModeType;", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface ActionModeType {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/DataListFragment$FormulaEditorDataInterface.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DataListFragment.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H&J\u001c\u0010\u0007\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H&¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/DataListFragment$FormulaEditorDataInterface;", "", "onListRenamed", "", "previousName", "", "newName", "onVariableRenamed", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface FormulaEditorDataInterface {
        void onListRenamed(String previousName, String newName);

        void onVariableRenamed(String previousName, String newName);
    }

    public static /* synthetic */ void getActionModeType$annotations() {
    }

    public static final DataListFragment getActiveInstance() {
        return INSTANCE.getActiveInstance();
    }

    @JvmStatic
    public static final void refreshActiveInstance() {
        INSTANCE.refreshActiveInstance();
    }

    @JvmStatic
    public static final void updateUserDataReferences(String str, String str2, UserData<?> userData) {
        INSTANCE.updateUserDataReferences(str, str2, userData);
    }

    @JvmStatic
    public static final void updateUserVariableValue(String str, UserData<?> userData) {
        INSTANCE.updateUserVariableValue(str, userData);
    }

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(menu, "menu");
        return false;
    }

    public final int getActionModeType() {
        return this.actionModeType;
    }

    public final void setActionModeType(int i) {
        this.actionModeType = i;
    }

    public final void setFormulaEditorDataInterface(FormulaEditorDataInterface formulaEditorDataInterface) {
        this.formulaEditorDataInterface = formulaEditorDataInterface;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(menu, "menu");
        int i = this.actionModeType;
        if (i == 0) {
            return false;
        }
        if (i == 1) {
            mode.setTitle(getString(R.string.am_delete));
        }
        mode.getMenuInflater().inflate(R.menu.context_menu, menu);
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.showCheckBoxes(true);
        }
        DataListAdapter dataListAdapter2 = this.adapter;
        if (dataListAdapter2 != null) {
            dataListAdapter2.updateDataSet();
        }
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() != R.id.confirm) {
            return false;
        }
        handleContextualAction();
        return true;
    }

    public final boolean shouldShowEmptyView() {
        DataListAdapter dataListAdapter = this.adapter;
        Intrinsics.checkNotNull(dataListAdapter);
        return dataListAdapter.getItemCount() == 0;
    }

    public final void setShowEmptyView(boolean visible) {
        TextView textView = this.emptyView;
        Intrinsics.checkNotNull(textView);
        textView.setVisibility(visible ? 0 : 8);
    }

    @Override // android.view.ActionMode.Callback
    public void onDestroyActionMode(ActionMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        resetActionModeParameters();
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.clearSelection();
        }
    }

    private final void handleContextualAction() {
        List<UserData> selectedItems;
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter == null || (selectedItems = dataListAdapter.getSelectedItems()) == null || selectedItems.isEmpty()) {
            ActionMode actionMode = this.actionMode;
            if (actionMode != null) {
                actionMode.finish();
                return;
            }
            return;
        }
        int i = this.actionModeType;
        if (i == 0) {
            throw new IllegalStateException("ActionModeType not set Correctly");
        }
        if (i != 1) {
            return;
        }
        DataListAdapter dataListAdapter2 = this.adapter;
        Intrinsics.checkNotNull(dataListAdapter2);
        List<UserData> selectedItems2 = dataListAdapter2.getSelectedItems();
        Intrinsics.checkNotNullExpressionValue(selectedItems2, "getSelectedItems(...)");
        showDeleteAlert(selectedItems2);
    }

    private final void resetActionModeParameters() {
        this.actionModeType = 0;
        this.actionMode = null;
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.showCheckBoxes(false);
        }
        DataListAdapter dataListAdapter2 = this.adapter;
        if (dataListAdapter2 != null) {
            dataListAdapter2.allowMultiSelection = true;
        }
        BottomBar.showAddButton(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycler_view);
        this.emptyView = (TextView) viewInflate.findViewById(R.id.empty_view);
        setHasOptionsMenu(true);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        float f = getResources().getDisplayMetrics().density;
        final int iGenerateViewId = View.generateViewId();
        final FrameLayout frameLayout = new FrameLayout(contextRequireContext);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setPadding(recyclerView.getPaddingLeft(), (int) (72 * f), recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
            recyclerView.setClipToPadding(false);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$onCreateView$1$1
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView2, int newState) {
                    Intrinsics.checkNotNullParameter(recyclerView2, "recyclerView");
                    super.onScrollStateChanged(recyclerView2, newState);
                    View viewFindViewById = frameLayout.findViewById(iGenerateViewId);
                    if (viewFindViewById != null) {
                        viewFindViewById.clearFocus();
                    }
                }
            });
        }
        MaterialCardView materialCardView = new MaterialCardView(contextRequireContext);
        float f2 = 12 * f;
        materialCardView.setRadius(f2);
        float f3 = 4 * f;
        materialCardView.setCardElevation(f3);
        materialCardView.setStrokeWidth(0);
        materialCardView.setCardBackgroundColor(ContextCompat.getColor(contextRequireContext, R.color.button_background));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        int i = (int) (16 * f);
        int i2 = (int) (8 * f);
        layoutParams.setMargins(i, i2, i, i2);
        materialCardView.setLayoutParams(layoutParams);
        SearchView searchView = new SearchView(contextRequireContext);
        searchView.setId(iGenerateViewId);
        searchView.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        searchView.setQueryHint(getString(R.string.search) + "...");
        searchView.setIconified(false);
        View viewFindViewById = searchView.findViewById(R.id.search_plate);
        if (viewFindViewById != null) {
            viewFindViewById.setBackground(null);
        }
        EditText editText = (EditText) searchView.findViewById(R.id.search_src_text);
        if (editText != null) {
            Intrinsics.checkNotNull(editText);
            editText.setBackground(null);
            editText.setTextColor(ContextCompat.getColor(contextRequireContext, R.color.solid_white));
            editText.setHintTextColor(ContextCompat.getColor(contextRequireContext, R.color.spinner_icon_and_inactive_elements));
            editText.setPadding(i2, editText.getPaddingTop(), editText.getPaddingRight(), editText.getPaddingBottom());
        }
        ImageView imageView = (ImageView) searchView.findViewById(R.id.search_mag_icon);
        if (imageView != null) {
            Intrinsics.checkNotNull(imageView);
            ViewGroup.LayoutParams layoutParams2 = imageView.getLayoutParams();
            LinearLayout.LayoutParams layoutParams3 = layoutParams2 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams2 : null;
            if (layoutParams3 != null) {
                layoutParams3.setMargins((int) f2, 0, (int) f3, 0);
            }
            imageView.setLayoutParams(layoutParams3);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$onCreateView$searchView$1$3
            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(String query) {
                DataListFragment dataListFragment = this.this$0;
                if (query == null) {
                    query = "";
                }
                dataListFragment.currentSearchQuery = query;
                this.this$0.applyFilterAndSetAdapter();
                return true;
            }

            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(String newText) {
                DataListFragment dataListFragment = this.this$0;
                if (newText == null) {
                    newText = "";
                }
                dataListFragment.currentSearchQuery = newText;
                this.this$0.applyFilterAndSetAdapter();
                return true;
            }
        });
        searchView.clearFocus();
        materialCardView.addView(searchView);
        frameLayout.addView(viewInflate);
        frameLayout.addView(materialCardView);
        return frameLayout;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        initializeAdapter();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        ActionBar supportActionBar;
        super.onResume();
        activeInstance = this;
        initializeAdapter();
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null && (supportActionBar = appCompatActivity.getSupportActionBar()) != null) {
            supportActionBar.setTitle(R.string.formula_editor_data);
        }
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.notifyDataSetChanged();
        }
        setShowEmptyView(shouldShowEmptyView());
        BottomBar.showBottomBar(getActivity());
        BottomBar.hidePlayButton(getActivity());
        BottomBar.hideAiAssistButton(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        DataListAdapter dataListAdapter;
        super.onPause();
        if (Intrinsics.areEqual(activeInstance, this)) {
            activeInstance = null;
        }
        if (!this.isObserverRegistered || (dataListAdapter = this.adapter) == null) {
            return;
        }
        if (dataListAdapter != null) {
            try {
                dataListAdapter.unregisterAdapterDataObserver(this.observer);
            } catch (IllegalStateException unused) {
            }
        }
        this.isObserverRegistered = false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        finishActionMode();
        BottomBar.hideBottomBar(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initializeAdapter() {
        List<UserDefinedBrickInput> listEmptyList;
        Bundle arguments = getArguments();
        this.parentScriptBrick = (ScriptBrick) (arguments != null ? arguments.getSerializable(PARENT_SCRIPT_BRICK_BUNDLE_ARGUMENT) : null);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        ScriptBrick scriptBrick = this.parentScriptBrick;
        if (scriptBrick instanceof UserDefinedReceiverBrick) {
            Intrinsics.checkNotNull(scriptBrick, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick");
            listEmptyList = ((UserDefinedReceiverBrick) scriptBrick).getUserDefinedBrick().getUserDefinedBrickInputs();
            Intrinsics.checkNotNull(listEmptyList);
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        this.originalUserDefinedBrickInputs = listEmptyList;
        List<UserVariable> userVariables = currentProject.getUserVariables();
        Intrinsics.checkNotNullExpressionValue(userVariables, "getUserVariables(...)");
        this.originalGlobalVars = userVariables;
        List<UserVariable> userVariables2 = currentSprite.getUserVariables();
        Intrinsics.checkNotNullExpressionValue(userVariables2, "getUserVariables(...)");
        this.originalLocalVars = userVariables2;
        List<UserVariable> multiplayerVariables = currentProject.getMultiplayerVariables();
        Intrinsics.checkNotNullExpressionValue(multiplayerVariables, "getMultiplayerVariables(...)");
        this.originalMultiplayerVars = multiplayerVariables;
        List<UserList> userLists = currentProject.getUserLists();
        Intrinsics.checkNotNullExpressionValue(userLists, "getUserLists(...)");
        this.originalGlobalLists = userLists;
        List<UserList> userLists2 = currentSprite.getUserLists();
        Intrinsics.checkNotNullExpressionValue(userLists2, "getUserLists(...)");
        this.originalLocalLists = userLists2;
        indexAndSort();
        applyFilterAndSetAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void applyFilterAndSetAdapter() {
        ArrayList list;
        List mutableList;
        List mutableList2;
        List mutableList3;
        List mutableList4;
        List mutableList5;
        DataListAdapter dataListAdapter;
        String string = StringsKt.trim((CharSequence) this.currentSearchQuery).toString();
        if (string.length() == 0) {
            list = CollectionsKt.toList(this.originalUserDefinedBrickInputs);
        } else {
            List<? extends UserDefinedBrickInput> list2 = this.originalUserDefinedBrickInputs;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list2) {
                String name2 = ((UserDefinedBrickInput) obj).getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (StringsKt.contains((CharSequence) name2, (CharSequence) string, true)) {
                    arrayList.add(obj);
                }
            }
            list = arrayList;
        }
        List list3 = list;
        if (string.length() == 0) {
            mutableList = CollectionsKt.toMutableList((Collection) this.originalMultiplayerVars);
        } else {
            List<UserVariable> list4 = this.originalMultiplayerVars;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : list4) {
                String name3 = ((UserVariable) obj2).getName();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                if (StringsKt.contains((CharSequence) name3, (CharSequence) string, true)) {
                    arrayList2.add(obj2);
                }
            }
            mutableList = CollectionsKt.toMutableList((Collection) arrayList2);
        }
        List list5 = mutableList;
        if (string.length() == 0) {
            mutableList2 = CollectionsKt.toMutableList((Collection) this.originalGlobalVars);
        } else {
            List<UserVariable> list6 = this.originalGlobalVars;
            ArrayList arrayList3 = new ArrayList();
            for (Object obj3 : list6) {
                String name4 = ((UserVariable) obj3).getName();
                Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                if (StringsKt.contains((CharSequence) name4, (CharSequence) string, true)) {
                    arrayList3.add(obj3);
                }
            }
            mutableList2 = CollectionsKt.toMutableList((Collection) arrayList3);
        }
        List list7 = mutableList2;
        if (string.length() == 0) {
            mutableList3 = CollectionsKt.toMutableList((Collection) this.originalLocalVars);
        } else {
            List<UserVariable> list8 = this.originalLocalVars;
            ArrayList arrayList4 = new ArrayList();
            for (Object obj4 : list8) {
                String name5 = ((UserVariable) obj4).getName();
                Intrinsics.checkNotNullExpressionValue(name5, "getName(...)");
                if (StringsKt.contains((CharSequence) name5, (CharSequence) string, true)) {
                    arrayList4.add(obj4);
                }
            }
            mutableList3 = CollectionsKt.toMutableList((Collection) arrayList4);
        }
        List list9 = mutableList3;
        if (string.length() == 0) {
            mutableList4 = CollectionsKt.toMutableList((Collection) this.originalGlobalLists);
        } else {
            List<UserList> list10 = this.originalGlobalLists;
            ArrayList arrayList5 = new ArrayList();
            for (Object obj5 : list10) {
                String name6 = ((UserList) obj5).getName();
                Intrinsics.checkNotNullExpressionValue(name6, "getName(...)");
                if (StringsKt.contains((CharSequence) name6, (CharSequence) string, true)) {
                    arrayList5.add(obj5);
                }
            }
            mutableList4 = CollectionsKt.toMutableList((Collection) arrayList5);
        }
        List list11 = mutableList4;
        if (string.length() == 0) {
            mutableList5 = CollectionsKt.toMutableList((Collection) this.originalLocalLists);
        } else {
            List<UserList> list12 = this.originalLocalLists;
            ArrayList arrayList6 = new ArrayList();
            for (Object obj6 : list12) {
                String name7 = ((UserList) obj6).getName();
                Intrinsics.checkNotNullExpressionValue(name7, "getName(...)");
                if (StringsKt.contains((CharSequence) name7, (CharSequence) string, true)) {
                    arrayList6.add(obj6);
                }
            }
            mutableList5 = CollectionsKt.toMutableList((Collection) arrayList6);
        }
        List list13 = mutableList5;
        if (this.isObserverRegistered && (dataListAdapter = this.adapter) != null) {
            try {
                Intrinsics.checkNotNull(dataListAdapter);
                dataListAdapter.unregisterAdapterDataObserver(this.observer);
            } catch (IllegalStateException unused) {
            }
            this.isObserverRegistered = false;
        }
        DataListAdapter dataListAdapter2 = new DataListAdapter(list3, list5, list7, list9, list11, list13);
        this.adapter = dataListAdapter2;
        try {
            Intrinsics.checkNotNull(dataListAdapter2);
            dataListAdapter2.registerAdapterDataObserver(this.observer);
            this.isObserverRegistered = true;
        } catch (IllegalStateException unused2) {
        }
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(this.adapter);
        }
        DataListAdapter dataListAdapter3 = this.adapter;
        Intrinsics.checkNotNull(dataListAdapter3);
        dataListAdapter3.setSelectionListener(this);
        DataListAdapter dataListAdapter4 = this.adapter;
        Intrinsics.checkNotNull(dataListAdapter4);
        dataListAdapter4.setOnItemClickListener(this);
        setShowEmptyView(shouldShowEmptyView());
    }

    public final void indexAndSort() {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        List<UserDefinedBrickInput> listEmptyList = CollectionsKt.emptyList();
        ScriptBrick scriptBrick = this.parentScriptBrick;
        if (scriptBrick instanceof UserDefinedReceiverBrick) {
            Intrinsics.checkNotNull(scriptBrick, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick");
            listEmptyList = ((UserDefinedReceiverBrick) scriptBrick).getUserDefinedBrick().getUserDefinedBrickInputs();
            Intrinsics.checkNotNullExpressionValue(listEmptyList, "getUserDefinedBrickInputs(...)");
        }
        List<UserVariable> userVariables = currentProject.getUserVariables();
        List<UserVariable> userVariables2 = currentSprite.getUserVariables();
        List<UserVariable> multiplayerVariables = currentProject.getMultiplayerVariables();
        List<UserList> userLists = currentProject.getUserLists();
        List<UserList> userLists2 = currentSprite.getUserLists();
        boolean z = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(SharedPreferenceKeys.INDEXING_VARIABLE_PREFERENCE_KEY, false);
        this.indexVariable = z;
        if (!z) {
            Intrinsics.checkNotNull(userVariables);
            Intrinsics.checkNotNull(userVariables2);
            Intrinsics.checkNotNull(multiplayerVariables);
            Intrinsics.checkNotNull(userLists);
            Intrinsics.checkNotNull(userLists2);
            initialIndexing(listEmptyList, userVariables, userVariables2, multiplayerVariables, userLists, userLists2);
            this.indexVariable = true;
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(SharedPreferenceKeys.INDEXING_VARIABLE_PREFERENCE_KEY, this.indexVariable).apply();
        }
        Intrinsics.checkNotNull(userVariables);
        Intrinsics.checkNotNull(userVariables2);
        Intrinsics.checkNotNull(multiplayerVariables);
        Intrinsics.checkNotNull(userLists);
        Intrinsics.checkNotNull(userLists2);
        sortVariableAndList(listEmptyList, userVariables, userVariables2, multiplayerVariables, userLists, userLists2);
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.notifyDataSetChanged();
        }
    }

    private final void sortVariableAndList(List<? extends UserDefinedBrickInput> userDefinedBrickInputs, List<UserVariable> globalVars, List<UserVariable> localVars, List<UserVariable> multiplayerVars, List<UserList> globalLists, List<UserList> localLists) {
        boolean z = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(SharedPreferenceKeys.SORT_VARIABLE_PREFERENCE_KEY, false);
        this.sortData = z;
        if (z) {
            final AnonymousClass1 anonymousClass1 = new Function2<UserDefinedBrickInput, UserDefinedBrickInput, Integer>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment.sortVariableAndList.1
                @Override // kotlin.jvm.functions.Function2
                public final Integer invoke(UserDefinedBrickInput item1, UserDefinedBrickInput item2) {
                    Intrinsics.checkNotNullParameter(item1, "item1");
                    Intrinsics.checkNotNullParameter(item2, "item2");
                    String name2 = item1.getName();
                    String name3 = item2.getName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    return Integer.valueOf(name2.compareTo(name3));
                }
            };
            Collections.sort(userDefinedBrickInputs, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda7
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return DataListFragment.sortVariableAndList$lambda$14(anonymousClass1, obj, obj2);
                }
            });
        } else {
            final AnonymousClass2 anonymousClass2 = new Function2<UserDefinedBrickInput, UserDefinedBrickInput, Integer>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment.sortVariableAndList.2
                @Override // kotlin.jvm.functions.Function2
                public final Integer invoke(UserDefinedBrickInput item1, UserDefinedBrickInput item2) {
                    Intrinsics.checkNotNullParameter(item1, "item1");
                    Intrinsics.checkNotNullParameter(item2, "item2");
                    return Integer.valueOf(Intrinsics.compare(item1.getInitialIndex(), item2.getInitialIndex()));
                }
            };
            Collections.sort(userDefinedBrickInputs, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda8
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return DataListFragment.sortVariableAndList$lambda$15(anonymousClass2, obj, obj2);
                }
            });
        }
        sortUserVariable(multiplayerVars, this.sortData);
        sortUserVariable(globalVars, this.sortData);
        sortUserVariable(localVars, this.sortData);
        sortUserList(globalLists, this.sortData);
        sortUserList(localLists, this.sortData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortVariableAndList$lambda$14(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.invoke(obj, obj2)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortVariableAndList$lambda$15(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.invoke(obj, obj2)).intValue();
    }

    public final void sortUserVariable(List<UserVariable> data, boolean sorted) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (sorted) {
            CollectionsKt.sortWith(data, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda10
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return DataListFragment.sortUserVariable$lambda$16((UserVariable) obj, (UserVariable) obj2);
                }
            });
        } else {
            CollectionsKt.sortWith(data, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return DataListFragment.sortUserVariable$lambda$17((UserVariable) obj, (UserVariable) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortUserVariable$lambda$16(UserVariable item1, UserVariable item2) {
        Intrinsics.checkNotNullParameter(item1, "item1");
        Intrinsics.checkNotNullParameter(item2, "item2");
        String name2 = item1.getName();
        String name3 = item2.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        return name2.compareTo(name3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortUserVariable$lambda$17(UserVariable item1, UserVariable item2) {
        Intrinsics.checkNotNullParameter(item1, "item1");
        Intrinsics.checkNotNullParameter(item2, "item2");
        return Intrinsics.compare(item1.getInitialIndex(), item2.getInitialIndex());
    }

    public final void sortUserList(List<UserList> data, boolean sorted) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (sorted) {
            CollectionsKt.sortWith(data, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda3
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return DataListFragment.sortUserList$lambda$18((UserList) obj, (UserList) obj2);
                }
            });
        } else {
            CollectionsKt.sortWith(data, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda4
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return DataListFragment.sortUserList$lambda$19((UserList) obj, (UserList) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortUserList$lambda$18(UserList item1, UserList item2) {
        Intrinsics.checkNotNullParameter(item1, "item1");
        Intrinsics.checkNotNullParameter(item2, "item2");
        String name2 = item1.getName();
        String name3 = item2.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        return name2.compareTo(name3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortUserList$lambda$19(UserList item1, UserList item2) {
        Intrinsics.checkNotNullParameter(item1, "item1");
        Intrinsics.checkNotNullParameter(item2, "item2");
        return Intrinsics.compare(item1.getInitialIndex(), item2.getInitialIndex());
    }

    private final void initialIndexing(List<? extends UserDefinedBrickInput> userDefinedBrickInputs, List<UserVariable> globalVars, List<UserVariable> localVars, List<UserVariable> multiplayerVars, List<UserList> globalLists, List<UserList> localLists) {
        if (!userDefinedBrickInputs.isEmpty()) {
            int i = 0;
            for (UserDefinedBrickInput userDefinedBrickInput : userDefinedBrickInputs) {
                int i2 = i + 1;
                if (userDefinedBrickInput.getInitialIndex() == -1) {
                    userDefinedBrickInput.setInitialIndex(i);
                }
                i = i2;
            }
        }
        setUserVariableIndex(globalVars);
        setUserVariableIndex(localVars);
        setUserVariableIndex(multiplayerVars);
        setUserListIndex(globalLists);
        setUserListIndex(localLists);
    }

    private final void setUserVariableIndex(List<UserVariable> data) {
        if (data.size() > 0) {
            int i = 0;
            for (UserVariable userVariable : data) {
                int i2 = i + 1;
                if (userVariable.getInitialIndex() == -1) {
                    userVariable.setInitialIndex(i);
                }
                i = i2;
            }
        }
    }

    private final void setUserListIndex(List<UserList> data) {
        if (data.size() > 0) {
            int i = 0;
            for (UserList userList : data) {
                int i2 = i + 1;
                if (userList.getInitialIndex() == -1) {
                    userList.setInitialIndex(i);
                }
                i = i2;
            }
        }
    }

    private final void onAdapterReady() {
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(this.adapter);
        }
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.setSelectionListener(this);
        }
        DataListAdapter dataListAdapter2 = this.adapter;
        if (dataListAdapter2 != null) {
            dataListAdapter2.setOnItemClickListener(this);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_sort, menu);
    }

    public final void notifyDataSetChanged() {
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            menu.getItem(i).setVisible(false);
        }
        MenuItem menuItemFindItem = menu.findItem(R.id.delete);
        if (menuItemFindItem != null) {
            menuItemFindItem.setVisible(true);
        }
        if (getContext() != null) {
            this.sortData = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(SharedPreferenceKeys.SORT_VARIABLE_PREFERENCE_KEY, false);
            menu.findItem(R.id.sort).setTitle(this.sortData ? R.string.undo_sort : R.string.sort).setVisible(true);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        if (itemId == R.id.delete) {
            startActionMode(1);
        } else if (itemId == R.id.sort) {
            this.sortData = !this.sortData;
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(SharedPreferenceKeys.SORT_VARIABLE_PREFERENCE_KEY, this.sortData).apply();
            indexAndSort();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private final void startActionMode(int type) {
        List<UserData> items;
        BottomBar.hideAddButton(getActivity());
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter == null || (items = dataListAdapter.getItems()) == null || items.isEmpty()) {
            ToastUtil.showError(requireActivity(), R.string.am_empty_list);
        } else {
            this.actionModeType = type;
            this.actionMode = requireActivity().startActionMode(this);
        }
    }

    private final void finishActionMode() {
        ActionMode actionMode;
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.clearSelection();
        }
        if (this.actionModeType == 0 || (actionMode = this.actionMode) == null) {
            return;
        }
        actionMode.finish();
    }

    private final void showDeleteAlert(final List<? extends UserData<?>> selectedItems) {
        new AlertDialog.Builder(requireContext()).setTitle(R.string.deletion_alert_title).setMessage(R.string.deletion_alert_text).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DataListFragment.showDeleteAlert$lambda$20(this.f$0, selectedItems, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).setCancelable(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteAlert$lambda$20(DataListFragment this$0, List selectedItems, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(selectedItems, "$selectedItems");
        this$0.deleteItems(selectedItems);
    }

    private final void deleteItems(List<? extends UserData<?>> selectedItems) {
        finishActionMode();
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        for (UserData<?> userData : selectedItems) {
            DataListAdapter dataListAdapter = this.adapter;
            if (dataListAdapter != null) {
                dataListAdapter.remove(userData);
            }
            if (userData instanceof UserVariable) {
                currentProject.getUserVariables().remove(userData);
                currentSprite.getUserVariables().remove(userData);
                currentProject.getMultiplayerVariables().remove(userData);
            } else if (userData instanceof UserList) {
                currentProject.getUserLists().remove(userData);
                currentSprite.getUserLists().remove(userData);
            }
        }
        ProjectManager.getInstance().getCurrentProject().deselectElements(selectedItems);
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_Items, selectedItems.size(), Integer.valueOf(selectedItems.size())));
        initializeAdapter();
    }

    private final void renameItem(UserData<?> item, String name2) {
        String name3 = item.getName();
        INSTANCE.updateUserDataReferences(name3, name2, item);
        UserDataUtil.renameUserData(item, name2 == null ? "" : name2);
        indexAndSort();
        finishActionMode();
        if (item instanceof UserVariable) {
            FormulaEditorDataInterface formulaEditorDataInterface = this.formulaEditorDataInterface;
            if (formulaEditorDataInterface != null) {
                formulaEditorDataInterface.onVariableRenamed(name3, name2);
            }
        } else {
            FormulaEditorDataInterface formulaEditorDataInterface2 = this.formulaEditorDataInterface;
            if (formulaEditorDataInterface2 != null) {
                formulaEditorDataInterface2.onListRenamed(name3, name2);
            }
        }
        initializeAdapter();
    }

    private final void editItem(UserData<?> item, String value) {
        INSTANCE.updateUserVariableValue(value, item);
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.updateDataSet();
        }
        finishActionMode();
        initializeAdapter();
    }

    private final void showRenameDialog(List<? extends UserData<?>> selectedItems) {
        final UserData<?> userData = selectedItems.get(0);
        TextInputDialog.Builder builder = new TextInputDialog.Builder(requireContext());
        DataListAdapter dataListAdapter = this.adapter;
        Intrinsics.checkNotNull(dataListAdapter);
        builder.setHint(getString(R.string.data_label)).setText(userData.getName()).setTextWatcher(new DuplicateInputTextWatcher(dataListAdapter.getItems())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda9
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                DataListFragment.showRenameDialog$lambda$21(this.f$0, userData, dialogInterface, str);
            }
        });
        builder.setTitle(R.string.rename_data_dialog).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showRenameDialog$lambda$21(DataListFragment this$0, UserData item, DialogInterface dialogInterface, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        this$0.renameItem(item, str);
    }

    private final void showEditDialog(List<? extends UserData<?>> selectedItems) {
        final UserData<?> userData = selectedItems.get(0);
        TextInputDialog.Builder builder = new TextInputDialog.Builder(requireContext());
        builder.setHint(getString(R.string.data_value)).setText(userData.getValue().toString()).setPositiveButton(getString(R.string.save), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                DataListFragment.showEditDialog$lambda$22(this.f$0, userData, dialogInterface, str);
            }
        });
        builder.setTitle(getString(R.string.edit) + " '" + userData.getName() + FormatHelper.QUOTE).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showEditDialog$lambda$22(DataListFragment this$0, UserData item, DialogInterface dialogInterface, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        this$0.editItem(item, str);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener
    public void onSelectionChanged(int selectedItemCnt) {
        ActionMode actionMode;
        if (this.actionModeType != 1 || (actionMode = this.actionMode) == null) {
            return;
        }
        actionMode.setTitle(getString(R.string.am_delete) + " " + selectedItemCnt);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemClick(UserData<?> item, MultiSelectionManager selectionManager) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (this.actionModeType == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            FormulaEditorFragment formulaEditorFragment = (FormulaEditorFragment) (fragmentManager != null ? fragmentManager.findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG) : null);
            if (formulaEditorFragment != null) {
                formulaEditorFragment.setChosenUserDataItem(item);
            }
            FragmentManager fragmentManager2 = getFragmentManager();
            if (fragmentManager2 != null) {
                fragmentManager2.popBackStack();
            }
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemLongClick(UserData<?> item, CheckableViewHolder holder) {
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(holder, "holder");
        onItemClick(item, (MultiSelectionManager) null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/DataListFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DataListFragment.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0007J*\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u00072\f\u0010\u0014\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0015H\u0007J\u001e\u0010\u0016\u001a\u00020\u00102\b\u0010\u0017\u001a\u0004\u0018\u00010\u00072\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R*\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\t\u001a\u0004\u0018\u00010\n8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b\f\u0010\u0002\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/DataListFragment$Companion;", "", "()V", Net.HttpMethods.DELETE, "", "NONE", "PARENT_SCRIPT_BRICK_BUNDLE_ARGUMENT", "", "TAG", "<set-?>", "Lorg/catrobat/catroid/ui/recyclerview/fragment/DataListFragment;", "activeInstance", "getActiveInstance$annotations", "getActiveInstance", "()Lorg/catrobat/catroid/ui/recyclerview/fragment/DataListFragment;", "refreshActiveInstance", "", "updateUserDataReferences", "oldName", "newName", "item", "Lorg/catrobat/catroid/formulaeditor/UserData;", "updateUserVariableValue", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getActiveInstance$annotations() {
        }

        private Companion() {
        }

        public final DataListFragment getActiveInstance() {
            return DataListFragment.activeInstance;
        }

        @JvmStatic
        public final void refreshActiveInstance() {
            DataListFragment activeInstance = getActiveInstance();
            if (activeInstance != null) {
                activeInstance.initializeAdapter();
            }
        }

        @JvmStatic
        public final void updateUserDataReferences(String oldName, String newName, UserData<?> item) {
            ProjectManager.getInstance().getCurrentProject().updateUserDataReferences(oldName, newName, item);
        }

        @JvmStatic
        public final void updateUserVariableValue(String value, UserData<?> item) {
            Intrinsics.checkNotNullParameter(item, "item");
            item.setValue(value);
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("DataListFragment", "getSimpleName(...)");
        TAG = "DataListFragment";
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(final UserData<?> item, View view) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item instanceof UserDefinedBrickInput) {
            return;
        }
        new ArrayList().add(item);
        List listMutableListOf = CollectionsKt.mutableListOf(Integer.valueOf(R.id.copy), Integer.valueOf(R.id.show_details), Integer.valueOf(R.id.from_library), Integer.valueOf(R.id.from_local), Integer.valueOf(R.id.new_group), Integer.valueOf(R.id.new_scene), Integer.valueOf(R.id.cast_button), Integer.valueOf(R.id.backpack), Integer.valueOf(R.id.project_options), Integer.valueOf(R.id.project_files), Integer.valueOf(R.id.project_libs));
        if (item instanceof UserVariable) {
            PopupMenu popupMenuCreateSettingsPopUpMenu = UiUtils.createSettingsPopUpMenu(view, requireContext(), R.menu.menu_project_activity, CollectionsKt.toIntArray(listMutableListOf));
            Intrinsics.checkNotNullExpressionValue(popupMenuCreateSettingsPopUpMenu, "createSettingsPopUpMenu(...)");
            popupMenuCreateSettingsPopUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda5
                @Override // android.widget.PopupMenu.OnMenuItemClickListener
                public final boolean onMenuItemClick(MenuItem menuItem) {
                    return DataListFragment.onSettingsClick$lambda$23(this.f$0, item, menuItem);
                }
            });
            popupMenuCreateSettingsPopUpMenu.show();
            return;
        }
        listMutableListOf.add(Integer.valueOf(R.id.edit));
        PopupMenu popupMenuCreateSettingsPopUpMenu2 = UiUtils.createSettingsPopUpMenu(view, requireContext(), R.menu.menu_project_activity, CollectionsKt.toIntArray(listMutableListOf));
        Intrinsics.checkNotNullExpressionValue(popupMenuCreateSettingsPopUpMenu2, "createSettingsPopUpMenu(...)");
        popupMenuCreateSettingsPopUpMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment$$ExternalSyntheticLambda6
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return DataListFragment.onSettingsClick$lambda$24(this.f$0, item, menuItem);
            }
        });
        popupMenuCreateSettingsPopUpMenu2.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onSettingsClick$lambda$23(DataListFragment this$0, UserData item, MenuItem menuItem) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        int itemId = menuItem.getItemId();
        if (itemId == R.id.delete) {
            this$0.showDeleteAlert(new ArrayList(CollectionsKt.listOf(item)));
            return true;
        }
        if (itemId == R.id.edit) {
            this$0.showEditDialog(new ArrayList(CollectionsKt.listOf(item)));
            return true;
        }
        if (itemId != R.id.rename) {
            return true;
        }
        this$0.showRenameDialog(new ArrayList(CollectionsKt.listOf(item)));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onSettingsClick$lambda$24(DataListFragment this$0, UserData item, MenuItem menuItem) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        int itemId = menuItem.getItemId();
        if (itemId == R.id.delete) {
            this$0.showDeleteAlert(new ArrayList(CollectionsKt.listOf(item)));
            return true;
        }
        if (itemId != R.id.rename) {
            return true;
        }
        this$0.showRenameDialog(new ArrayList(CollectionsKt.listOf(item)));
        return true;
    }
}
