package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.catroid.common.Constants;
import org.catrobat.paintroid.FileIO;
import org.catrobat.paintroid.R;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: compiled from: SaveInformationDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 <2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001<B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000eH\u0002J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000eH\u0002J\b\u0010\u001b\u001a\u00020\u0018H\u0002J\b\u0010\u001c\u001a\u00020\u0018H\u0002J\b\u0010\u001d\u001a\u00020\u0018H\u0002J\u0010\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000eH\u0002J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000eH\u0002J\u0010\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u000eH\u0002J\u0012\u0010\"\u001a\u00020\u00182\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0012\u0010%\u001a\u00020&2\b\u0010#\u001a\u0004\u0018\u00010$H\u0017J0\u0010'\u001a\u00020\u00182\f\u0010(\u001a\b\u0012\u0002\b\u0003\u0018\u00010)2\b\u0010\u0019\u001a\u0004\u0018\u00010\u000e2\u0006\u0010*\u001a\u00020\u00122\u0006\u0010+\u001a\u00020,H\u0016J\u0016\u0010-\u001a\u00020\u00182\f\u0010(\u001a\b\u0012\u0002\b\u0003\u0018\u00010)H\u0016J \u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u00122\u0006\u00102\u001a\u00020\fH\u0016J\u0010\u00103\u001a\u00020\u00182\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00104\u001a\u00020\u00182\u0006\u0010/\u001a\u000200H\u0016J\u001a\u00105\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000e2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0018\u00106\u001a\u00020\u00182\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0002J\b\u0010;\u001a\u00020\u0018H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000¨\u0006="}, d2 = {"Lorg/catrobat/paintroid/dialog/SaveInformationDialog;", "Lorg/catrobat/paintroid/dialog/MainActivityDialogFragment;", "Landroid/widget/AdapterView$OnItemSelectedListener;", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", "()V", PsiTreeChangeEvent.PROP_FILE_NAME, "", "imageName", "Landroidx/appcompat/widget/AppCompatEditText;", "inflater", "Landroid/view/LayoutInflater;", "isExport", "", "jpgView", "Landroid/view/View;", "percentage", "Landroidx/appcompat/widget/AppCompatTextView;", "permission", "", "specificFormatLayout", "Landroid/view/ViewGroup;", "spinner", "Landroid/widget/Spinner;", "initImageName", "", Promotion.ACTION_VIEW, "initInfoButton", "initJpgView", "initPercentage", "initSeekBar", "initSpecificFormatLayout", "initSpinner", "initViews", "customLayout", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "onItemSelected", "parent", "Landroid/widget/AdapterView;", "position", "id", "", "onNothingSelected", "onProgressChanged", "seekBar", "Landroid/widget/SeekBar;", "progress", "fromUser", "onStartTrackingTouch", "onStopTrackingTouch", "onViewCreated", "setFileDetails", "compressFormat", "Landroid/graphics/Bitmap$CompressFormat;", "fileType", "Lorg/catrobat/paintroid/FileIO$FileType;", "setSpinnerSelection", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SaveInformationDialog extends MainActivityDialogFragment implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private String fileName;
    private AppCompatEditText imageName;
    private LayoutInflater inflater;
    private boolean isExport;
    private View jpgView;
    private AppCompatTextView percentage;
    private int permission;
    private ViewGroup specificFormatLayout;
    private Spinner spinner;

    /* JADX INFO: compiled from: SaveInformationDialog.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FileIO.FileType.values().length];
            iArr[FileIO.FileType.JPG.ordinal()] = 1;
            iArr[FileIO.FileType.ORA.ordinal()] = 2;
            iArr[FileIO.FileType.CATROBAT.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        Intrinsics.checkNotNullParameter(seekBar, "seekBar");
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        Intrinsics.checkNotNullParameter(seekBar, "seekBar");
    }

    /* JADX INFO: compiled from: SaveInformationDialog.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t¨\u0006\u000b"}, d2 = {"Lorg/catrobat/paintroid/dialog/SaveInformationDialog$Companion;", "", "()V", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/paintroid/dialog/SaveInformationDialog;", "permissionCode", "", "imageNumber", "isStandard", "", "isExport", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SaveInformationDialog newInstance(int permissionCode, int imageNumber, boolean isStandard, boolean isExport) {
            if (isStandard) {
                FileIO fileIO = FileIO.INSTANCE;
                FileIO.filename = Constants.TMP_IMAGE_FILE_NAME;
                FileIO fileIO2 = FileIO.INSTANCE;
                FileIO.compressFormat = Bitmap.CompressFormat.PNG;
                FileIO fileIO3 = FileIO.INSTANCE;
                FileIO.fileType = FileIO.FileType.PNG;
            }
            SaveInformationDialog saveInformationDialog = new SaveInformationDialog();
            Bundle bundle = new Bundle();
            if (Intrinsics.areEqual(FileIO.filename, Constants.TMP_IMAGE_FILE_NAME)) {
                bundle.putString("setName", Intrinsics.stringPlus(FileIO.filename, Integer.valueOf(imageNumber)));
            } else {
                bundle.putString("setName", FileIO.filename);
            }
            bundle.putInt("permission", permissionCode);
            bundle.putBoolean("isExport", isExport);
            Unit unit = Unit.INSTANCE;
            saveInformationDialog.setArguments(bundle);
            return saveInformationDialog;
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleRequireArguments = requireArguments();
        Intrinsics.checkNotNullExpressionValue(bundleRequireArguments, "requireArguments()");
        this.permission = bundleRequireArguments.getInt("permission");
        this.fileName = String.valueOf(bundleRequireArguments.getString("setName"));
        this.isExport = bundleRequireArguments.getBoolean("isExport");
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setSpinnerSelection();
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        Intrinsics.checkNotNullExpressionValue(layoutInflater, "requireActivity().layoutInflater");
        this.inflater = layoutInflater;
        if (layoutInflater == null) {
            Intrinsics.throwUninitializedPropertyAccessException("inflater");
            layoutInflater = null;
        }
        View customLayout = layoutInflater.inflate(R.layout.dialog_pocketpaint_save, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(customLayout, "customLayout");
        onViewCreated(customLayout, savedInstanceState);
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireContext(), R.style.PocketPaintAlertDialog).setTitle(R.string.dialog_save_image_title).setView(customLayout).setPositiveButton(R.string.save_button_text, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.SaveInformationDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SaveInformationDialog.m5963onCreateDialog$lambda1(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel_button_text, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.SaveInformationDialog$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SaveInformationDialog.m5964onCreateDialog$lambda2(this.f$0, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "Builder(requireContext()…) }\n            .create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-1, reason: not valid java name */
    public static final void m5963onCreateDialog$lambda1(SaveInformationDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FileIO fileIO = FileIO.INSTANCE;
        AppCompatEditText appCompatEditText = this$0.imageName;
        if (appCompatEditText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageName");
            appCompatEditText = null;
        }
        FileIO.filename = String.valueOf(appCompatEditText.getText());
        FileIO fileIO2 = FileIO.INSTANCE;
        FileIO.storeImageUri = null;
        FileIO fileIO3 = FileIO.INSTANCE;
        FileIO.FileType fileType = FileIO.fileType;
        String defaultFileName = FileIO.INSTANCE.getDefaultFileName();
        ContentResolver contentResolver = this$0.requireContext().getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "requireContext().contentResolver");
        if (fileIO3.checkFileExists(fileType, defaultFileName, contentResolver)) {
            this$0.getPresenter().showOverwriteDialog(this$0.permission, this$0.isExport);
        } else {
            this$0.getPresenter().switchBetweenVersions(this$0.permission, this$0.isExport);
        }
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-2, reason: not valid java name */
    public static final void m5964onCreateDialog$lambda2(SaveInformationDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    private final void initViews(View customLayout) {
        initSpecificFormatLayout(customLayout);
        initJpgView();
        initSeekBar();
        initPercentage();
        initSpinner(customLayout);
        initInfoButton(customLayout);
        initImageName(customLayout);
    }

    private final void initSpecificFormatLayout(View view) {
        View viewFindViewById = view.findViewById(R.id.pocketpaint_save_format_specific_options);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "view.findViewById(R.id.p…_format_specific_options)");
        this.specificFormatLayout = (ViewGroup) viewFindViewById;
    }

    private final void initJpgView() {
        LayoutInflater layoutInflater = this.inflater;
        ViewGroup viewGroup = null;
        if (layoutInflater == null) {
            Intrinsics.throwUninitializedPropertyAccessException("inflater");
            layoutInflater = null;
        }
        int i = R.layout.dialog_pocketpaint_save_jpg_sub_dialog;
        ViewGroup viewGroup2 = this.specificFormatLayout;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("specificFormatLayout");
        } else {
            viewGroup = viewGroup2;
        }
        View viewInflate = layoutInflater.inflate(i, viewGroup, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflater.inflate(\n      …          false\n        )");
        this.jpgView = viewInflate;
    }

    private final void initSeekBar() {
        View view = this.jpgView;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("jpgView");
            view = null;
        }
        View viewFindViewById = view.findViewById(R.id.pocketpaint_jpg_seekbar_save_info);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "jpgView.findViewById(R.i…nt_jpg_seekbar_save_info)");
        SeekBar seekBar = (SeekBar) viewFindViewById;
        seekBar.setProgress(FileIO.INSTANCE.getCompressQuality());
        seekBar.setOnSeekBarChangeListener(this);
    }

    private final void initPercentage() {
        View view = this.jpgView;
        AppCompatTextView appCompatTextView = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("jpgView");
            view = null;
        }
        View viewFindViewById = view.findViewById(R.id.pocketpaint_percentage_save_info);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "jpgView.findViewById(R.i…int_percentage_save_info)");
        this.percentage = (AppCompatTextView) viewFindViewById;
        String string = new StringBuilder().append(FileIO.INSTANCE.getCompressQuality()).append(MangleConstant.PLATFORM_FUNCTION_MARKER).toString();
        AppCompatTextView appCompatTextView2 = this.percentage;
        if (appCompatTextView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("percentage");
        } else {
            appCompatTextView = appCompatTextView2;
        }
        appCompatTextView.setText(string);
    }

    private final void initInfoButton(View view) {
        View viewFindViewById = view.findViewById(R.id.pocketpaint_btn_save_info);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "view.findViewById(R.id.pocketpaint_btn_save_info)");
        ((AppCompatImageButton) viewFindViewById).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.dialog.SaveInformationDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SaveInformationDialog.m5962initInfoButton$lambda3(this.f$0, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initInfoButton$lambda-3, reason: not valid java name */
    public static final void m5962initInfoButton$lambda3(SaveInformationDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int i = WhenMappings.$EnumSwitchMapping$0[FileIO.fileType.ordinal()];
        if (i == 1) {
            this$0.getPresenter().showJpgInformationDialog();
            return;
        }
        if (i == 2) {
            this$0.getPresenter().showOraInformationDialog();
        } else if (i == 3) {
            this$0.getPresenter().showCatrobatInformationDialog();
        } else {
            this$0.getPresenter().showPngInformationDialog();
        }
    }

    private final void initSpinner(View view) {
        View viewFindViewById = view.findViewById(R.id.pocketpaint_save_dialog_spinner);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "view.findViewById(R.id.p…aint_save_dialog_spinner)");
        this.spinner = (Spinner) viewFindViewById;
        FileIO.FileType[] fileTypeArrValues = FileIO.FileType.values();
        ArrayList arrayList = new ArrayList(fileTypeArrValues.length);
        for (FileIO.FileType fileType : fileTypeArrValues) {
            arrayList.add(fileType.getValue());
        }
        ArrayList arrayList2 = arrayList;
        Spinner spinner = this.spinner;
        Spinner spinner2 = null;
        if (spinner == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spinner");
            spinner = null;
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(spinner.getContext(), android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner3 = this.spinner;
        if (spinner3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spinner");
            spinner3 = null;
        }
        spinner3.setAdapter((SpinnerAdapter) arrayAdapter);
        Spinner spinner4 = this.spinner;
        if (spinner4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spinner");
        } else {
            spinner2 = spinner4;
        }
        spinner2.setOnItemSelectedListener(this);
    }

    private final void initImageName(View view) {
        View viewFindViewById = view.findViewById(R.id.pocketpaint_image_name_save_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "view.findViewById(R.id.p…int_image_name_save_text)");
        AppCompatEditText appCompatEditText = (AppCompatEditText) viewFindViewById;
        this.imageName = appCompatEditText;
        String str = null;
        if (appCompatEditText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageName");
            appCompatEditText = null;
        }
        String str2 = this.fileName;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PsiTreeChangeEvent.PROP_FILE_NAME);
        } else {
            str = str2;
        }
        appCompatEditText.setText(str);
    }

    private final void setFileDetails(Bitmap.CompressFormat compressFormat, FileIO.FileType fileType) {
        ViewGroup viewGroup = this.specificFormatLayout;
        View view = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("specificFormatLayout");
            viewGroup = null;
        }
        viewGroup.removeAllViews();
        if (fileType == FileIO.FileType.JPG) {
            ViewGroup viewGroup2 = this.specificFormatLayout;
            if (viewGroup2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("specificFormatLayout");
                viewGroup2 = null;
            }
            View view2 = this.jpgView;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("jpgView");
            } else {
                view = view2;
            }
            viewGroup2.addView(view);
        }
        FileIO fileIO = FileIO.INSTANCE;
        FileIO.compressFormat = compressFormat;
        FileIO fileIO2 = FileIO.INSTANCE;
        FileIO.fileType = fileType;
    }

    private final void setSpinnerSelection() {
        int i = WhenMappings.$EnumSwitchMapping$0[FileIO.fileType.ordinal()];
        Spinner spinner = null;
        if (i == 1) {
            Spinner spinner2 = this.spinner;
            if (spinner2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spinner");
            } else {
                spinner = spinner2;
            }
            spinner.setSelection(FileIO.FileType.JPG.ordinal());
            return;
        }
        if (i == 2) {
            Spinner spinner3 = this.spinner;
            if (spinner3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spinner");
            } else {
                spinner = spinner3;
            }
            spinner.setSelection(FileIO.FileType.ORA.ordinal());
            return;
        }
        if (i == 3) {
            Spinner spinner4 = this.spinner;
            if (spinner4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("spinner");
            } else {
                spinner = spinner4;
            }
            spinner.setSelection(FileIO.FileType.CATROBAT.ordinal());
            return;
        }
        Spinner spinner5 = this.spinner;
        if (spinner5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("spinner");
        } else {
            spinner = spinner5;
        }
        spinner.setSelection(FileIO.FileType.PNG.ordinal());
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
        String strValueOf = String.valueOf(parent == null ? null : parent.getItemAtPosition(position));
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        if (strValueOf == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = strValueOf.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        if (Intrinsics.areEqual(lowerCase, FileIO.FileType.JPG.getValue())) {
            setFileDetails(Bitmap.CompressFormat.JPEG, FileIO.FileType.JPG);
            return;
        }
        if (Intrinsics.areEqual(lowerCase, FileIO.FileType.PNG.getValue())) {
            setFileDetails(Bitmap.CompressFormat.PNG, FileIO.FileType.PNG);
        } else if (Intrinsics.areEqual(lowerCase, FileIO.FileType.ORA.getValue())) {
            setFileDetails(Bitmap.CompressFormat.PNG, FileIO.FileType.ORA);
        } else if (Intrinsics.areEqual(lowerCase, FileIO.FileType.CATROBAT.getValue())) {
            setFileDetails(Bitmap.CompressFormat.PNG, FileIO.FileType.CATROBAT);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Intrinsics.checkNotNullParameter(seekBar, "seekBar");
        AppCompatTextView appCompatTextView = this.percentage;
        if (appCompatTextView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("percentage");
            appCompatTextView = null;
        }
        appCompatTextView.setText(new StringBuilder().append(progress).append(MangleConstant.PLATFORM_FUNCTION_MARKER).toString());
        FileIO.INSTANCE.setCompressQuality(progress);
    }
}
