package org.catrobat.paintroid.ui;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.catrobat.paintroid.FileIO;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.UserPreferences;
import org.catrobat.paintroid.WelcomeActivity;
import org.catrobat.paintroid.colorpicker.ColorPickerDialog;
import org.catrobat.paintroid.colorpicker.OnColorPickedListener;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.command.CommandFactory;
import org.catrobat.paintroid.command.implementation.DefaultCommandFactory;
import org.catrobat.paintroid.common.ConstantsKt;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.catrobat.paintroid.dialog.AboutDialog;
import org.catrobat.paintroid.dialog.AdvancedSettingsDialog;
import org.catrobat.paintroid.dialog.CatrobatImageInfoDialog;
import org.catrobat.paintroid.dialog.FeedbackDialog;
import org.catrobat.paintroid.dialog.ImportImageDialog;
import org.catrobat.paintroid.dialog.IndeterminateProgressDialog;
import org.catrobat.paintroid.dialog.InfoDialog;
import org.catrobat.paintroid.dialog.JpgInfoDialog;
import org.catrobat.paintroid.dialog.LikeUsDialog;
import org.catrobat.paintroid.dialog.OraInfoDialog;
import org.catrobat.paintroid.dialog.OverwriteDialog;
import org.catrobat.paintroid.dialog.PermanentDenialPermissionInfoDialog;
import org.catrobat.paintroid.dialog.PermissionInfoDialog;
import org.catrobat.paintroid.dialog.PngInfoDialog;
import org.catrobat.paintroid.dialog.RateUsDialog;
import org.catrobat.paintroid.dialog.SaveBeforeFinishDialog;
import org.catrobat.paintroid.dialog.SaveBeforeLoadImageDialog;
import org.catrobat.paintroid.dialog.SaveBeforeNewImageDialog;
import org.catrobat.paintroid.dialog.SaveInformationDialog;
import org.catrobat.paintroid.dialog.ScaleImageOnLoadDialog;
import org.catrobat.paintroid.dialog.ZoomWindowSettingsDialog;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolReference;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.ui.fragments.CatroidMediaGalleryFragment;

/* JADX INFO: compiled from: MainActivityNavigator.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J#\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016¢\u0006\u0002\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u000fH\u0016J\u0010\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u0012H\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u0012H\u0002J\b\u0010\u001f\u001a\u00020\u000fH\u0016J\u0014\u0010 \u001a\u0004\u0018\u00010\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u001b\u0010!\u001a\u00020\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016¢\u0006\u0002\u0010\"J\u0010\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u0012H\u0002J\b\u0010%\u001a\u00020\u000fH\u0016J\b\u0010&\u001a\u00020\u000fH\u0016J\u0012\u0010'\u001a\u00020\u000f2\b\u0010(\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010)\u001a\u00020\u000fH\u0016J\b\u0010*\u001a\u00020\u000fH\u0016J\b\u0010+\u001a\u00020\u000fH\u0016J\u0010\u0010,\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020\u000f2\u0006\u0010-\u001a\u000200H\u0002J\b\u00101\u001a\u00020\u000fH\u0016J\b\u00102\u001a\u00020\u000fH\u0016J\b\u00103\u001a\u00020\u000fH\u0016J\b\u00104\u001a\u00020\u000fH\u0016J\b\u00105\u001a\u00020\u000fH\u0016J\u0018\u00106\u001a\u00020\u000f2\u0006\u0010-\u001a\u0002072\u0006\u0010\u001e\u001a\u00020\u0012H\u0002J\b\u00108\u001a\u00020\u000fH\u0016J\u001a\u00109\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\u0012H\u0002J\b\u0010;\u001a\u00020\u000fH\u0016J\b\u0010<\u001a\u00020\u000fH\u0016J\b\u0010=\u001a\u00020\u000fH\u0016J\b\u0010>\u001a\u00020\u000fH\u0016J\b\u0010?\u001a\u00020\u000fH\u0016J\b\u0010@\u001a\u00020\u000fH\u0016J\u0018\u0010A\u001a\u00020\u000f2\u0006\u0010B\u001a\u00020\u00142\u0006\u0010C\u001a\u00020\nH\u0016J\b\u0010D\u001a\u00020\u000fH\u0016J\b\u0010E\u001a\u00020\u000fH\u0016J\b\u0010F\u001a\u00020\u000fH\u0016J+\u0010G\u001a\u00020\u000f2\u0006\u0010H\u001a\u00020I2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016¢\u0006\u0002\u0010JJ\b\u0010K\u001a\u00020\u000fH\u0016J\b\u0010L\u001a\u00020\u000fH\u0016J\b\u0010M\u001a\u00020\u000fH\u0016J\b\u0010N\u001a\u00020\u000fH\u0016J \u0010O\u001a\u00020\u000f2\u0006\u0010B\u001a\u00020\u00142\u0006\u0010P\u001a\u00020\u00142\u0006\u0010C\u001a\u00020\nH\u0017J\u001a\u0010Q\u001a\u00020\u000f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010R\u001a\u00020\u000f2\u0006\u0010S\u001a\u00020\u00142\u0006\u0010T\u001a\u00020\u0014H\u0016J\u0018\u0010R\u001a\u00020\u000f2\u0006\u0010U\u001a\u00020\u00122\u0006\u0010T\u001a\u00020\u0014H\u0016J\u0018\u0010V\u001a\u00020\u000f2\u0006\u0010W\u001a\u00020\u00142\u0006\u0010X\u001a\u00020\u0014H\u0016J\u0010\u0010Y\u001a\u00020\u000f2\u0006\u0010Z\u001a\u00020[H\u0016J\u0010\u0010\\\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010]\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0012\u0010^\u001a\u00020\u000f2\b\u0010_\u001a\u0004\u0018\u00010`H\u0016J\u0010\u0010a\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8WX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0014\u0010\f\u001a\u00020\n8WX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000bR\u0014\u0010\r\u001a\u00020\n8WX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lorg/catrobat/paintroid/ui/MainActivityNavigator;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Navigator;", "mainActivity", "Lorg/catrobat/paintroid/MainActivity;", "toolReference", "Lorg/catrobat/paintroid/tools/ToolReference;", "(Lorg/catrobat/paintroid/MainActivity;Lorg/catrobat/paintroid/tools/ToolReference;)V", "commandFactory", "Lorg/catrobat/paintroid/command/CommandFactory;", "isSdkAboveOrEqualM", "", "()Z", "isSdkAboveOrEqualQ", "isSdkAboveOrEqualT", "askForPermission", "", "permissions", "", "", "requestCode", "", "([Ljava/lang/String;I)V", "broadcastAddPictureToGallery", "uri", "Landroid/net/Uri;", "dismissIndeterminateProgressDialog", "doIHavePermission", "permission", "findFragmentByTag", "Landroidx/fragment/app/Fragment;", "tag", "finishActivity", "getFileName", "isPermissionPermanentlyDenied", "([Ljava/lang/String;)Z", "openPlayStore", "applicationId", "rateUsClicked", "restoreFragmentListeners", "returnToPocketCode", "path", "sendFeedback", "setAntialiasingOnToolPaint", "setMaskFilterToNull", "setupCatroidMediaGalleryListeners", "dialog", "Lorg/catrobat/paintroid/ui/fragments/CatroidMediaGalleryFragment;", "setupColorPickerDialogListeners", "Lorg/catrobat/paintroid/colorpicker/ColorPickerDialog;", "showAboutDialog", "showAdvancedSettingsDialog", "showCatrobatInformationDialog", "showCatroidMediaGallery", "showColorPickerDialog", "showDialogFragmentSafely", "Landroidx/fragment/app/DialogFragment;", "showFeedbackDialog", "showFragment", "fragment", "showImageImportDialog", "showIndeterminateProgressDialog", "showJpgInformationDialog", "showLikeUsDialog", "showLoadErrorDialog", "showOraInformationDialog", "showOverwriteDialog", "permissionCode", "isExport", "showPngInformationDialog", "showRateUsDialog", "showRequestPermanentlyDeniedPermissionRationaleDialog", "showRequestPermissionRationaleDialog", "permissionType", "Lorg/catrobat/paintroid/dialog/PermissionInfoDialog$PermissionType;", "(Lorg/catrobat/paintroid/dialog/PermissionInfoDialog$PermissionType;[Ljava/lang/String;I)V", "showSaveBeforeFinishDialog", "showSaveBeforeLoadImageDialog", "showSaveBeforeNewImageDialog", "showSaveErrorDialog", "showSaveImageInformationDialogWhenStandalone", "imageNumber", "showScaleImageRequestDialog", "showToast", "resId", "duration", NotificationCompat.CATEGORY_MESSAGE, "showToolChangeToast", TypedValues.CycleType.S_WAVE_OFFSET, "idRes", "showZoomWindowSettingsDialog", "sharedPreferences", "Lorg/catrobat/paintroid/UserPreferences;", "startImportImageActivity", "startLoadImageActivity", "startShareImageActivity", "bitmap", "Landroid/graphics/Bitmap;", "startWelcomeActivity", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MainActivityNavigator implements MainActivityContracts.Navigator {
    private CommandFactory commandFactory;
    private final MainActivity mainActivity;
    private final ToolReference toolReference;

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public boolean isSdkAboveOrEqualM() {
        return true;
    }

    public MainActivityNavigator(MainActivity mainActivity, ToolReference toolReference) {
        Intrinsics.checkNotNullParameter(mainActivity, "mainActivity");
        Intrinsics.checkNotNullParameter(toolReference, "toolReference");
        this.mainActivity = mainActivity;
        this.toolReference = toolReference;
        this.commandFactory = new DefaultCommandFactory();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public boolean isSdkAboveOrEqualQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public boolean isSdkAboveOrEqualT() {
        return Build.VERSION.SDK_INT >= 33;
    }

    static /* synthetic */ void showFragment$default(MainActivityNavigator mainActivityNavigator, Fragment fragment, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = ConstantsKt.CATROID_MEDIA_GALLERY_FRAGMENT_TAG;
        }
        mainActivityNavigator.showFragment(fragment, str);
    }

    private final void showFragment(Fragment fragment, String tag) {
        FragmentManager supportFragmentManager = this.mainActivity.getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "mainActivity.supportFragmentManager");
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_to_top, R.anim.slide_to_bottom, R.anim.slide_to_top, R.anim.slide_to_bottom).addToBackStack(null).add(R.id.fragment_container, fragment, tag).commit();
    }

    private final void showDialogFragmentSafely(DialogFragment dialog, String tag) {
        FragmentManager supportFragmentManager = this.mainActivity.getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "mainActivity.supportFragmentManager");
        if (supportFragmentManager.isStateSaved()) {
            return;
        }
        dialog.show(supportFragmentManager, tag);
    }

    private final Fragment findFragmentByTag(String tag) {
        return this.mainActivity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    private final void setupColorPickerDialogListeners(ColorPickerDialog dialog) {
        dialog.addOnColorPickedListener(new OnColorPickedListener() { // from class: org.catrobat.paintroid.ui.MainActivityNavigator.setupColorPickerDialogListeners.1
            @Override // org.catrobat.paintroid.colorpicker.OnColorPickedListener
            public void colorChanged(int color) {
                Command commandCreateColorChangedCommand = MainActivityNavigator.this.commandFactory.createColorChangedCommand(MainActivityNavigator.this.toolReference, MainActivityNavigator.this.mainActivity, color);
                MainActivityNavigator.this.mainActivity.getModel().getColorHistory().addColor(color);
                Tool tool = MainActivityNavigator.this.toolReference.getTool();
                if ((tool == null ? null : tool.getToolType()) != ToolType.CLIP) {
                    MainActivityNavigator.this.mainActivity.getCommandManager().addCommand(commandCreateColorChangedCommand);
                } else {
                    MainActivityNavigator.this.mainActivity.getCommandManager().addCommandWithoutUndo(commandCreateColorChangedCommand);
                }
            }
        });
        Bitmap bitmap = this.mainActivity.getPresenter().getBitmap();
        if (bitmap == null) {
            return;
        }
        dialog.setBitmap(bitmap);
    }

    private final void setupCatroidMediaGalleryListeners(CatroidMediaGalleryFragment dialog) {
        dialog.setMediaGalleryListener(new CatroidMediaGalleryFragment.MediaGalleryListener() { // from class: org.catrobat.paintroid.ui.MainActivityNavigator.setupCatroidMediaGalleryListeners.1
            @Override // org.catrobat.paintroid.ui.fragments.CatroidMediaGalleryFragment.MediaGalleryListener
            public void bitmapLoadedFromSource(Bitmap loadedBitmap) {
                Intrinsics.checkNotNullParameter(loadedBitmap, "loadedBitmap");
                MainActivityNavigator.this.mainActivity.getPresenter().bitmapLoadedFromSource(loadedBitmap);
            }

            @Override // org.catrobat.paintroid.ui.fragments.CatroidMediaGalleryFragment.MediaGalleryListener
            public void showProgressDialog() {
                MainActivityNavigator.this.showIndeterminateProgressDialog();
            }

            @Override // org.catrobat.paintroid.ui.fragments.CatroidMediaGalleryFragment.MediaGalleryListener
            public void dismissProgressDialog() {
                MainActivityNavigator.this.dismissIndeterminateProgressDialog();
            }
        });
    }

    private final void openPlayStore(String applicationId) {
        try {
            this.mainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Intrinsics.stringPlus("market://details?id=", applicationId))));
        } catch (ActivityNotFoundException unused) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Intrinsics.stringPlus("http://play.google.com/store/apps/details?id=", applicationId)));
            try {
                Result.Companion companion = Result.INSTANCE;
                MainActivityNavigator mainActivityNavigator = this;
                this.mainActivity.startActivity(intent);
                Result.m2898constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                Result.m2898constructorimpl(ResultKt.createFailure(th));
            }
        }
    }

    private final String getFileName(Uri uri) {
        String string;
        String strSubstring = null;
        if (uri == null) {
            return null;
        }
        if (Intrinsics.areEqual(uri.getScheme(), FirebaseAnalytics.Param.CONTENT)) {
            Cursor cursorQuery = this.mainActivity.getContentResolver().query(uri, null, null, null, null);
            try {
                Cursor cursor = cursorQuery;
                string = (cursor == null || !cursor.moveToFirst()) ? null : cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursorQuery, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(cursorQuery, th);
                    throw th2;
                }
            }
        } else {
            string = null;
        }
        if (string != null) {
            return string;
        }
        String path = uri.getPath();
        Integer numValueOf = path == null ? null : Integer.valueOf(StringsKt.lastIndexOf$default((CharSequence) path, '/', 0, false, 6, (Object) null));
        if (numValueOf == null || numValueOf.intValue() == -1) {
            return path;
        }
        if (path != null) {
            strSubstring = path.substring(numValueOf.intValue() + 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.String).substring(startIndex)");
        }
        return strSubstring;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showColorPickerDialog() {
        Tool tool;
        if (findFragmentByTag(ConstantsKt.COLOR_PICKER_DIALOG_TAG) != null || (tool = this.toolReference.getTool()) == null) {
            return;
        }
        ColorPickerDialog colorPickerDialogNewInstance = ColorPickerDialog.INSTANCE.newInstance(tool.getDrawPaint().getColor(), this.mainActivity.getModel().getIsOpenedFromCatroid(), this.mainActivity.getModel().getIsOpenedFromFormulaEditorInCatroid(), this.mainActivity.getModel().getColorHistory());
        setupColorPickerDialogListeners(colorPickerDialogNewInstance);
        showDialogFragmentSafely(colorPickerDialogNewInstance, ConstantsKt.COLOR_PICKER_DIALOG_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showCatroidMediaGallery() {
        if (findFragmentByTag(ConstantsKt.CATROID_MEDIA_GALLERY_FRAGMENT_TAG) == null) {
            CatroidMediaGalleryFragment catroidMediaGalleryFragment = new CatroidMediaGalleryFragment();
            catroidMediaGalleryFragment.setMediaGalleryListener(new CatroidMediaGalleryFragment.MediaGalleryListener() { // from class: org.catrobat.paintroid.ui.MainActivityNavigator.showCatroidMediaGallery.1
                @Override // org.catrobat.paintroid.ui.fragments.CatroidMediaGalleryFragment.MediaGalleryListener
                public void bitmapLoadedFromSource(Bitmap loadedBitmap) {
                    Intrinsics.checkNotNullParameter(loadedBitmap, "loadedBitmap");
                    MainActivityNavigator.this.mainActivity.getPresenter().bitmapLoadedFromSource(loadedBitmap);
                }

                @Override // org.catrobat.paintroid.ui.fragments.CatroidMediaGalleryFragment.MediaGalleryListener
                public void showProgressDialog() {
                    MainActivityNavigator.this.showIndeterminateProgressDialog();
                }

                @Override // org.catrobat.paintroid.ui.fragments.CatroidMediaGalleryFragment.MediaGalleryListener
                public void dismissProgressDialog() {
                    MainActivityNavigator.this.dismissIndeterminateProgressDialog();
                }
            });
            showFragment$default(this, catroidMediaGalleryFragment, null, 2, null);
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void startLoadImageActivity(int requestCode) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        intent.setFlags(524288);
        this.mainActivity.startActivityForResult(intent, requestCode);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void startImportImageActivity(int requestCode) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.setFlags(524288);
        this.mainActivity.startActivityForResult(intent, requestCode);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void startWelcomeActivity(int requestCode) {
        Intent intent = new Intent(this.mainActivity.getApplicationContext(), (Class<?>) WelcomeActivity.class);
        intent.setFlags(1);
        this.mainActivity.startActivityForResult(intent, requestCode);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void startShareImageActivity(Bitmap bitmap) {
        Uri uriSaveBitmapToCache = FileIO.INSTANCE.saveBitmapToCache(bitmap, this.mainActivity, Constants.TMP_IMAGE_FILE_NAME);
        if (uriSaveBitmapToCache == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("android.intent.extra.STREAM", uriSaveBitmapToCache);
        intent.setDataAndType(uriSaveBitmapToCache, this.mainActivity.getContentResolver().getType(uriSaveBitmapToCache));
        intent.setFlags(1);
        intent.setAction("android.intent.action.SEND");
        String string = this.mainActivity.getResources().getString(R.string.share_image_via_text);
        Intrinsics.checkNotNullExpressionValue(string, "mainActivity.resources.g…ing.share_image_via_text)");
        this.mainActivity.startActivity(Intent.createChooser(intent, string));
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showAboutDialog() {
        new AboutDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.ABOUT_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showLikeUsDialog() {
        new LikeUsDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.LIKE_US_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showRateUsDialog() {
        new RateUsDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.RATE_US_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showFeedbackDialog() {
        new FeedbackDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.FEEDBACK_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showZoomWindowSettingsDialog(UserPreferences sharedPreferences) {
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        new ZoomWindowSettingsDialog(sharedPreferences).show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.ZOOM_WINDOW_SETTINGS_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showAdvancedSettingsDialog() {
        new AdvancedSettingsDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.ADVANCED_SETTINGS_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showOverwriteDialog(int permissionCode, boolean isExport) {
        this.mainActivity.getIdlingResource().increment();
        OverwriteDialog.INSTANCE.newInstance(permissionCode, isExport).show(this.mainActivity.getSupportFragmentManager(), "saveinformationdialogfragment");
        this.mainActivity.getIdlingResource().decrement();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showPngInformationDialog() {
        new PngInfoDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.PNG_INFORMATION_DIALOG_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showJpgInformationDialog() {
        new JpgInfoDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.JPG_INFORMATION_DIALOG_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showOraInformationDialog() {
        new OraInfoDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.ORA_INFORMATION_DIALOG_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showCatrobatInformationDialog() {
        new CatrobatImageInfoDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.CATROBAT_INFORMATION_DIALOG_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void sendFeedback() {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.parse("mailto:support-paintroid@catrobat.org"));
        this.mainActivity.startActivity(intent);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showImageImportDialog() {
        new ImportImageDialog().show(this.mainActivity.getSupportFragmentManager(), ConstantsKt.ABOUT_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showIndeterminateProgressDialog() {
        showDialogFragmentSafely(new IndeterminateProgressDialog(), ConstantsKt.INDETERMINATE_PROGRESS_DIALOG_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void dismissIndeterminateProgressDialog() {
        DialogFragment dialogFragment = (DialogFragment) findFragmentByTag(ConstantsKt.INDETERMINATE_PROGRESS_DIALOG_TAG);
        if (dialogFragment == null) {
            return;
        }
        dialogFragment.dismiss();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void returnToPocketCode(String path) {
        Intent intent = new Intent();
        intent.putExtra("org.catrobat.extra.PAINTROID_PICTURE_PATH", path);
        this.mainActivity.setResult(-1, intent);
        this.mainActivity.finish();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showToast(int resId, int duration) {
        ToastFactory.makeText(this.mainActivity, resId, duration).show();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showToast(String msg, int duration) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        ToastFactory.makeText(this.mainActivity, msg, duration).show();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showSaveErrorDialog() {
        showDialogFragmentSafely(InfoDialog.INSTANCE.newInstance(InfoDialog.DialogType.WARNING, R.string.dialog_error_sdcard_text, R.string.dialog_error_save_title), ConstantsKt.SAVE_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showLoadErrorDialog() {
        showDialogFragmentSafely(InfoDialog.INSTANCE.newInstance(InfoDialog.DialogType.WARNING, R.string.dialog_loading_image_failed_title, R.string.dialog_loading_image_failed_text), ConstantsKt.LOAD_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showRequestPermissionRationaleDialog(PermissionInfoDialog.PermissionType permissionType, String[] permissions, int requestCode) {
        Intrinsics.checkNotNullParameter(permissionType, "permissionType");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        showDialogFragmentSafely(PermissionInfoDialog.INSTANCE.newInstance(permissionType, permissions, requestCode), ConstantsKt.PERMISSION_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showRequestPermanentlyDeniedPermissionRationaleDialog() {
        PermanentDenialPermissionInfoDialog.Companion companion = PermanentDenialPermissionInfoDialog.INSTANCE;
        String packageName = this.mainActivity.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "mainActivity.packageName");
        showDialogFragmentSafely(companion.newInstance(packageName), ConstantsKt.PERMISSION_DIALOG_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void askForPermission(String[] permissions, int requestCode) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        ActivityCompat.requestPermissions(this.mainActivity, permissions, requestCode);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public boolean doIHavePermission(String permission) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        return ContextCompat.checkSelfPermission(this.mainActivity, permission) == 0;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public boolean isPermissionPermanentlyDenied(String[] permissions) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        return !ActivityCompat.shouldShowRequestPermissionRationale(this.mainActivity, permissions[0]);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void finishActivity() {
        this.mainActivity.finish();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showSaveBeforeFinishDialog() {
        showDialogFragmentSafely(new SaveBeforeFinishDialog(), ConstantsKt.SAVE_QUESTION_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showSaveBeforeNewImageDialog() {
        showDialogFragmentSafely(new SaveBeforeNewImageDialog(), ConstantsKt.SAVE_QUESTION_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showSaveBeforeLoadImageDialog() {
        showDialogFragmentSafely(new SaveBeforeLoadImageDialog(), ConstantsKt.SAVE_QUESTION_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showScaleImageRequestDialog(Uri uri, int requestCode) {
        if (uri == null) {
            return;
        }
        showDialogFragmentSafely(ScaleImageOnLoadDialog.INSTANCE.newInstance(uri, requestCode), ConstantsKt.SCALE_IMAGE_FRAGMENT_TAG);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showSaveImageInformationDialogWhenStandalone(int permissionCode, int imageNumber, boolean isExport) {
        Uri savedPictureUri = this.mainActivity.getModel().getSavedPictureUri();
        if (savedPictureUri != null && permissionCode != 2) {
            FileIO fileIO = FileIO.INSTANCE;
            ContentResolver contentResolver = this.mainActivity.getContentResolver();
            Intrinsics.checkNotNullExpressionValue(contentResolver, "mainActivity.contentResolver");
            fileIO.parseFileName(savedPictureUri, contentResolver);
        }
        if (!isExport && this.mainActivity.getModel().getIsOpenedFromCatroid()) {
            String fileName = getFileName(savedPictureUri);
            if (fileName != null && (StringsKt.endsWith$default(fileName, FileIO.FileType.JPG.getValue(), false, 2, (Object) null) || StringsKt.endsWith$default(fileName, "jpeg", false, 2, (Object) null))) {
                FileIO fileIO2 = FileIO.INSTANCE;
                FileIO.compressFormat = Bitmap.CompressFormat.JPEG;
                FileIO fileIO3 = FileIO.INSTANCE;
                FileIO.fileType = FileIO.FileType.JPG;
            } else {
                FileIO fileIO4 = FileIO.INSTANCE;
                FileIO.compressFormat = Bitmap.CompressFormat.PNG;
                FileIO fileIO5 = FileIO.INSTANCE;
                FileIO.fileType = FileIO.FileType.PNG;
            }
            FileIO fileIO6 = FileIO.INSTANCE;
            FileIO.filename = Intrinsics.stringPlus(Constants.TMP_IMAGE_FILE_NAME, Integer.valueOf(imageNumber));
            FileIO.INSTANCE.setCatroidFlag(true);
            this.mainActivity.getPresenter().switchBetweenVersions(permissionCode, isExport);
            return;
        }
        SaveInformationDialog.INSTANCE.newInstance(permissionCode, imageNumber, permissionCode == 2, isExport).show(this.mainActivity.getSupportFragmentManager(), "saveinformationdialogfragment");
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void showToolChangeToast(int offset, int idRes) {
        Toast toastMakeText = ToastFactory.makeText(this.mainActivity, idRes, 0);
        int i = this.mainActivity.getResources().getConfiguration().orientation;
        toastMakeText.show();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void broadcastAddPictureToGallery(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(uri);
        this.mainActivity.sendBroadcast(intent);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void restoreFragmentListeners() {
        Fragment fragmentFindFragmentByTag = findFragmentByTag(ConstantsKt.COLOR_PICKER_DIALOG_TAG);
        if (fragmentFindFragmentByTag != null) {
            setupColorPickerDialogListeners((ColorPickerDialog) fragmentFindFragmentByTag);
        }
        Fragment fragmentFindFragmentByTag2 = findFragmentByTag(ConstantsKt.CATROID_MEDIA_GALLERY_FRAGMENT_TAG);
        if (fragmentFindFragmentByTag2 != null) {
            setupCatroidMediaGalleryListeners((CatroidMediaGalleryFragment) fragmentFindFragmentByTag2);
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void rateUsClicked() {
        String packageName = this.mainActivity.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "mainActivity.packageName");
        openPlayStore(packageName);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void setAntialiasingOnToolPaint() {
        this.mainActivity.getToolPaint().setAntialiasing();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Navigator
    public void setMaskFilterToNull() {
        this.mainActivity.getToolPaint().getBitmapPaint().setMaskFilter(null);
        this.mainActivity.getToolPaint().getPreviewPaint().setMaskFilter(null);
        Tool tool = this.toolReference.getTool();
        if (tool == null) {
            return;
        }
        this.mainActivity.getToolPaint().getBitmapPaint().setAlpha(tool.getDrawPaint().getAlpha());
        this.mainActivity.getToolPaint().getPreviewPaint().setAlpha(tool.getDrawPaint().getAlpha());
    }
}
