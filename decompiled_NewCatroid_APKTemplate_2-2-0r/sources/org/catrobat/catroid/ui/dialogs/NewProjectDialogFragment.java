package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import java.io.IOException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.DefaultProjectHandler;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.databinding.DialogNewProjectBinding;
import org.catrobat.catroid.merge.NewProjectNameTextWatcher;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.git.GitController;
import org.catrobat.catroid.utils.git.GitResult;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/NewProjectDialogFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: NewProjectDialogFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0012H\u0002J\u0012\u0010\u0015\u001a\u00020\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J$\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\"\u001a\u00020\t2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010&\u001a\u00020\u0012H\u0016J\u001a\u0010'\u001a\u00020\u00122\u0006\u0010(\u001a\u00020\u001e2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010)\u001a\u00020\u0012H\u0002J\b\u0010*\u001a\u00020\u0012H\u0002J\b\u0010+\u001a\u00020\u0012H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006-"}, d2 = {"Lorg/catrobat/catroid/ui/dialogs/NewProjectDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/DialogNewProjectBinding;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/DialogNewProjectBinding;", "isCloningInProgress", "", "isPcMode", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "cloneProjectFromGit", "", "createStandardProject", "handleConfirmClick", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPrepareOptionsMenu", "onStart", "onViewCreated", Promotion.ACTION_VIEW, "resetUIState", "setupToolbar", "updateConfirmButtonState", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewProjectDialogFragment extends DialogFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private DialogNewProjectBinding _binding;
    private boolean isCloningInProgress;
    private boolean isPcMode;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;

    /* JADX WARN: Multi-variable type inference failed */
    public NewProjectDialogFragment() {
        final NewProjectDialogFragment newProjectDialogFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = newProjectDialogFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DialogNewProjectBinding getBinding() {
        DialogNewProjectBinding dialogNewProjectBinding = this._binding;
        Intrinsics.checkNotNull(dialogNewProjectBinding);
        return dialogNewProjectBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = DialogNewProjectBinding.inflate(inflater, container, false);
        ScrollView root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean z = PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean("pref_pc_mode_enabled", false);
        this.isPcMode = z;
        if (!z) {
            setStyle(0, 1);
        } else {
            setStyle(1, 2131952046);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        Window window;
        Window window2;
        super.onStart();
        if (this.isPcMode) {
            int iApplyDimension = (int) TypedValue.applyDimension(1, 450.0f, getResources().getDisplayMetrics());
            Dialog dialog = getDialog();
            if (dialog == null || (window2 = dialog.getWindow()) == null) {
                return;
            }
            window2.setLayout(iApplyDimension, -2);
            return;
        }
        Dialog dialog2 = getDialog();
        if (dialog2 == null || (window = dialog2.getWindow()) == null) {
            return;
        }
        window.setLayout(-1, -1);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        UniqueNameProvider uniqueNameProvider = new UniqueNameProvider() { // from class: org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$onViewCreated$uniqueNameProvider$1
            @Override // org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider, org.catrobat.catroid.ui.recyclerview.util.UniqueNameProviderInterface
            public boolean isUnique(String newName) {
                Intrinsics.checkNotNullParameter(newName, "newName");
                return !ReplaceExistingProjectDialogFragment.projectExistsInDirectory(newName).booleanValue();
            }
        };
        getBinding().input.setHint(getString(R.string.project_name_label));
        final TextInputEditText textInputEditText = getBinding().inputEditText;
        textInputEditText.setText(uniqueNameProvider.getUniqueName(getString(R.string.default_project_name), null));
        textInputEditText.addTextChangedListener(new NewProjectNameTextWatcher<Nameable>() { // from class: org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$onViewCreated$1$1
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher.TextWatcher, android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                this.this$0.getBinding().input.setError(validateInput(String.valueOf(s), textInputEditText.getContext()));
                this.this$0.updateConfirmButtonState();
            }
        });
        textInputEditText.requestFocus();
        getBinding().gitCloneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                NewProjectDialogFragment.onViewCreated$lambda$1(this.f$0, compoundButton, z);
            }
        });
        TextInputEditText gitUrlEditText = getBinding().gitUrlEditText;
        Intrinsics.checkNotNullExpressionValue(gitUrlEditText, "gitUrlEditText");
        gitUrlEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$onViewCreated$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence text, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                this.this$0.updateConfirmButtonState();
            }
        });
        updateConfirmButtonState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$1(NewProjectDialogFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().gitInputContainer.setVisibility(z ? 0 : 8);
        this$0.getBinding().standardSettingsContainer.setVisibility(z ? 8 : 0);
        this$0.updateConfirmButtonState();
    }

    private final void setupToolbar() {
        if (this.isPcMode) {
            getBinding().toolbar.toolbar.setTitle(R.string.new_project_title);
            getBinding().toolbar.toolbar.inflateMenu(R.menu.menu_confirm);
            getBinding().toolbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$$ExternalSyntheticLambda1
                @Override // androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
                public final boolean onMenuItemClick(MenuItem menuItem) {
                    return NewProjectDialogFragment.setupToolbar$lambda$3(this.f$0, menuItem);
                }
            });
        } else {
            setHasOptionsMenu(true);
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNull(fragmentActivityRequireActivity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
            ((AppCompatActivity) fragmentActivityRequireActivity).setSupportActionBar(getBinding().toolbar.toolbar);
            FragmentActivity fragmentActivityRequireActivity2 = requireActivity();
            Intrinsics.checkNotNull(fragmentActivityRequireActivity2, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
            ActionBar supportActionBar = ((AppCompatActivity) fragmentActivityRequireActivity2).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(R.string.new_project_title);
            }
        }
        getBinding().toolbar.toolbar.setNavigationIcon(R.drawable.ic_close);
        getBinding().toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NewProjectDialogFragment.setupToolbar$lambda$4(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupToolbar$lambda$3(NewProjectDialogFragment this$0, MenuItem menuItem) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (menuItem.getItemId() != R.id.confirm) {
            return false;
        }
        this$0.handleConfirmClick();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupToolbar$lambda$4(NewProjectDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateConfirmButtonState() {
        MenuItem menuItemFindItem;
        if (this.isPcMode) {
            menuItemFindItem = getBinding().toolbar.toolbar.getMenu().findItem(R.id.confirm);
        } else {
            Menu menu = getBinding().toolbar.toolbar.getMenu();
            menuItemFindItem = menu != null ? menu.findItem(R.id.confirm) : null;
        }
        if (menuItemFindItem == null) {
            return;
        }
        boolean z = getBinding().input.getError() == null && !this.isCloningInProgress;
        if (getBinding().gitCloneSwitch.isChecked()) {
            if (StringsKt.trim((CharSequence) String.valueOf(getBinding().gitUrlEditText.getText())).toString().length() == 0) {
                z = false;
            }
        }
        if (z) {
            menuItemFindItem.setIcon(R.drawable.ic_done);
            menuItemFindItem.setEnabled(true);
        } else {
            menuItemFindItem.setIcon(R.drawable.ic_done_disabled);
            menuItemFindItem.setEnabled(false);
        }
    }

    private final void handleConfirmClick() {
        if (getBinding().gitCloneSwitch.isChecked()) {
            cloneProjectFromGit();
        } else {
            createStandardProject();
            dismiss();
        }
    }

    private final void createStandardProject() {
        String string = StringsKt.trim((CharSequence) String.valueOf(getBinding().inputEditText.getText())).toString();
        boolean z = getBinding().orientationToggleGroup.getCheckedButtonId() == R.id.btn_landscape;
        DefaultProjectHandler.ProjectCreatorType projectCreatorType = DefaultProjectHandler.ProjectCreatorType.PROJECT_CREATOR_DEFAULT;
        try {
            boolean zIsChecked = getBinding().exampleProjectSwitch.isChecked();
            if (zIsChecked) {
                getProjectManager().createNewExampleProject(string, projectCreatorType, z);
            } else if (!zIsChecked) {
                getProjectManager().createNewEmptyProject(string, z, false);
            }
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.startActivity(new Intent(getActivity(), (Class<?>) ProjectActivity.class));
            }
        } catch (IOException unused) {
            ToastUtil.showError(getActivity(), R.string.error_new_project);
        }
    }

    private final void cloneProjectFromGit() {
        String string = StringsKt.trim((CharSequence) String.valueOf(getBinding().inputEditText.getText())).toString();
        String string2 = StringsKt.trim((CharSequence) String.valueOf(getBinding().gitUrlEditText.getText())).toString();
        String string3 = StringsKt.trim((CharSequence) String.valueOf(getBinding().gitTokenEditText.getText())).toString();
        this.isCloningInProgress = true;
        updateConfirmButtonState();
        getBinding().gitProgressBar.setVisibility(0);
        getBinding().contentContainer.setAlpha(0.5f);
        getBinding().inputEditText.setEnabled(false);
        getBinding().gitUrlEditText.setEnabled(false);
        getBinding().gitTokenEditText.setEnabled(false);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new AnonymousClass1(new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(string)), string2, string3, this, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/NewProjectDialogFragment$cloneProjectFromGit$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$cloneProjectFromGit$1, reason: invalid class name */
    /* JADX INFO: compiled from: NewProjectDialogFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$cloneProjectFromGit$1", f = "NewProjectDialogFragment.kt", i = {}, l = {196}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $gitToken;
        final /* synthetic */ String $gitUrl;
        final /* synthetic */ File $targetDir;
        int label;
        final /* synthetic */ NewProjectDialogFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(File file, String str, String str2, NewProjectDialogFragment newProjectDialogFragment, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$targetDir = file;
            this.$gitUrl = str;
            this.$gitToken = str2;
            this.this$0 = newProjectDialogFragment;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$targetDir, this.$gitUrl, this.$gitToken, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                GitResult<Unit> gitResultCloneRepository = new GitController(this.$targetDir).cloneRepository(this.$gitUrl, this.$gitToken, this.$targetDir);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02581(this.this$0, gitResultCloneRepository, this.$targetDir, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/NewProjectDialogFragment$cloneProjectFromGit$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$cloneProjectFromGit$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NewProjectDialogFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment$cloneProjectFromGit$1$1", f = "NewProjectDialogFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02581 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ GitResult<Unit> $result;
            final /* synthetic */ File $targetDir;
            int label;
            final /* synthetic */ NewProjectDialogFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02581(NewProjectDialogFragment newProjectDialogFragment, GitResult<Unit> gitResult, File file, Continuation<? super C02581> continuation) {
                super(2, continuation);
                this.this$0 = newProjectDialogFragment;
                this.$result = gitResult;
                this.$targetDir = file;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02581(this.this$0, this.$result, this.$targetDir, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02581) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.isCloningInProgress = false;
                    GitResult<Unit> gitResult = this.$result;
                    if (gitResult instanceof GitResult.Success) {
                        try {
                            this.this$0.getProjectManager().loadProject(this.$targetDir);
                            ToastUtil.showSuccess(this.this$0.getActivity(), "Проект успешно клонирован!");
                            FragmentActivity activity = this.this$0.getActivity();
                            if (activity != null) {
                                activity.startActivity(new Intent(this.this$0.getActivity(), (Class<?>) ProjectActivity.class));
                            }
                            this.this$0.dismiss();
                        } catch (Exception e) {
                            ToastUtil.showError(this.this$0.getActivity(), "Клонирование успешно, но проект не читается: " + e.getMessage());
                            this.this$0.resetUIState();
                        }
                    } else if (gitResult instanceof GitResult.Error) {
                        ToastUtil.showError(this.this$0.getActivity(), "Ошибка клонирования: " + ((GitResult.Error) this.$result).getMessage());
                        this.this$0.resetUIState();
                    } else {
                        ToastUtil.showError(this.this$0.getActivity(), "Ошибка клонирования: " + this.$result);
                        this.this$0.resetUIState();
                    }
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resetUIState() {
        getBinding().gitProgressBar.setVisibility(8);
        getBinding().contentContainer.setAlpha(1.0f);
        getBinding().inputEditText.setEnabled(true);
        getBinding().gitUrlEditText.setEnabled(true);
        getBinding().gitTokenEditText.setEnabled(true);
        updateConfirmButtonState();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        inflater.inflate(R.menu.menu_confirm, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            menu.getItem(i).setVisible(false);
        }
        menu.findItem(R.id.confirm).setVisible(true);
        super.onPrepareOptionsMenu(menu);
        updateConfirmButtonState();
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == R.id.confirm) {
            handleConfirmClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/NewProjectDialogFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: NewProjectDialogFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/ui/dialogs/NewProjectDialogFragment$Companion;", "", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return NewProjectDialogFragment.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("NewProjectDialogFragment", "getSimpleName(...)");
        TAG = "NewProjectDialogFragment";
    }
}
