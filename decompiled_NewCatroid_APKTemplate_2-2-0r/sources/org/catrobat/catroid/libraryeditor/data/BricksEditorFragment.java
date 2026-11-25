package org.catrobat.catroid.libraryeditor.data;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/BricksEditorFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: BricksEditorFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u001a\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010\u0016\u001a\u00020\u0014H\u0002J\u0018\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/BricksEditorFragment;", "Landroidx/fragment/app/Fragment;", "()V", "adapter", "Lorg/catrobat/catroid/libraryeditor/data/BrickAdapter;", "viewModel", "Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "getViewModel", "()Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", Promotion.ACTION_VIEW, "setupAdapter", "showDeleteConfirmationDialog", "brickId", "", "name", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BricksEditorFragment extends Fragment {
    private BrickAdapter adapter;

    /* JADX INFO: renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    public BricksEditorFragment() {
        final BricksEditorFragment bricksEditorFragment = this;
        final Function0 function0 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(bricksEditorFragment, Reflection.getOrCreateKotlinClass(LibraryEditorViewModel.class), new Function0<ViewModelStore>() { // from class: org.catrobat.catroid.libraryeditor.data.BricksEditorFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = bricksEditorFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: org.catrobat.catroid.libraryeditor.data.BricksEditorFragment$special$$inlined$activityViewModels$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                if (function1 != null && (creationExtras = (CreationExtras) function1.invoke()) != null) {
                    return creationExtras;
                }
                CreationExtras defaultViewModelCreationExtras = bricksEditorFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: org.catrobat.catroid.libraryeditor.data.BricksEditorFragment$special$$inlined$activityViewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = bricksEditorFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
    }

    private final LibraryEditorViewModel getViewModel() {
        return (LibraryEditorViewModel) this.viewModel.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_bricks_editor, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        View viewFindViewById = view.findViewById(R.id.bricks_recycler_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById;
        View viewFindViewById2 = view.findViewById(R.id.fab_add_brick);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        FloatingActionButton floatingActionButton = (FloatingActionButton) viewFindViewById2;
        setupAdapter();
        BrickAdapter brickAdapter = this.adapter;
        if (brickAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            brickAdapter = null;
        }
        recyclerView.setAdapter(brickAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        getViewModel().getLibraryDraft().observe(getViewLifecycleOwner(), new BricksEditorFragment$sam$androidx_lifecycle_Observer$0(new Function1<LibraryDraft, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.BricksEditorFragment.onViewCreated.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LibraryDraft libraryDraft) {
                invoke2(libraryDraft);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LibraryDraft libraryDraft) {
                BrickAdapter brickAdapter2 = BricksEditorFragment.this.adapter;
                if (brickAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    brickAdapter2 = null;
                }
                brickAdapter2.submitList(CollectionsKt.toList(libraryDraft.getBricks()));
            }
        }));
        floatingActionButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.libraryeditor.data.BricksEditorFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BricksEditorFragment.onViewCreated$lambda$0(this.f$0, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(BricksEditorFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BrickEditDialogFragment.INSTANCE.newInstance(null).show(this$0.getChildFragmentManager(), "EDIT_BRICK_DIALOG");
    }

    private final void setupAdapter() {
        this.adapter = new BrickAdapter(new Function1<EditableBrick, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.BricksEditorFragment.setupAdapter.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(EditableBrick editableBrick) {
                invoke2(editableBrick);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(EditableBrick brick) {
                Intrinsics.checkNotNullParameter(brick, "brick");
                BrickEditDialogFragment.INSTANCE.newInstance(brick.getId()).show(BricksEditorFragment.this.getChildFragmentManager(), "EDIT_BRICK_DIALOG");
            }
        }, new Function1<EditableBrick, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.BricksEditorFragment.setupAdapter.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(EditableBrick editableBrick) {
                invoke2(editableBrick);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(EditableBrick brick) {
                Intrinsics.checkNotNullParameter(brick, "brick");
                BricksEditorFragment.this.showDeleteConfirmationDialog(brick.getId(), brick.getHeaderText());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDeleteConfirmationDialog(final String brickId, String name2) {
        new AlertDialog.Builder(requireContext()).setTitle(getString(R.string.libs_delete_w)).setMessage(getString(R.string.libs_delete_w2) + " '" + name2 + "'?").setPositiveButton(getString(R.string.libs_delete), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.libraryeditor.data.BricksEditorFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BricksEditorFragment.showDeleteConfirmationDialog$lambda$1(this.f$0, brickId, dialogInterface, i);
            }
        }).setNegativeButton(getString(R.string.libs_cancel), (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteConfirmationDialog$lambda$1(BricksEditorFragment this$0, String brickId, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(brickId, "$brickId");
        this$0.getViewModel().deleteBrick(brickId);
    }
}
