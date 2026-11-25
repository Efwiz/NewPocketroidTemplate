package org.catrobat.paintroid.contract;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Menu;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.paintroid.UserPreferences;
import org.catrobat.paintroid.colorpicker.ColorHistory;
import org.catrobat.paintroid.command.serialization.CommandSerializer;
import org.catrobat.paintroid.dialog.PermissionInfoDialog;
import org.catrobat.paintroid.iotasks.CreateFile;
import org.catrobat.paintroid.iotasks.LoadImage;
import org.catrobat.paintroid.iotasks.SaveImage;
import org.catrobat.paintroid.iotasks.WorkspaceReturnValue;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.ui.LayerAdapter;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: MainActivityContracts.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001:\n\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts;", "", "BottomBarViewHolder", "BottomNavigationAppearance", "BottomNavigationViewHolder", "DrawerLayoutViewHolder", "Interactor", "MainView", ExifInterface.TAG_MODEL, "Navigator", "Presenter", "TopBarViewHolder", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface MainActivityContracts {

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0006H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\b"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomBarViewHolder;", "", "isVisible", "", "()Z", "hide", "", "show", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface BottomBarViewHolder {
        void hide();

        boolean isVisible();

        void show();
    }

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomNavigationAppearance;", "", "showCurrentTool", "", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface BottomNavigationAppearance {
        void showCurrentTool(ToolType toolType);
    }

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\u0012\u0010\u0007\u001a\u00020\u00032\b\b\u0001\u0010\b\u001a\u00020\tH&J\b\u0010\u0004\u001a\u00020\u0003H&J\u0012\u0010\n\u001a\u00020\u00032\b\u0010\u000b\u001a\u0004\u0018\u00010\fH&¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomNavigationViewHolder;", "", "enableColorItemView", "", "show", "", "hide", "setColorButtonColor", TypedValues.Custom.S_COLOR, "", "showCurrentTool", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface BottomNavigationViewHolder {
        void enableColorItemView(boolean show);

        void hide();

        void setColorButtonColor(int color);

        void show();

        void showCurrentTool(ToolType toolType);
    }

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u000b"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$DrawerLayoutViewHolder;", "", "closeDrawer", "", "gravity", "", "animate", "", "isDrawerOpen", "isDrawerVisible", "openDrawer", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface DrawerLayoutViewHolder {
        void closeDrawer(int gravity, boolean animate);

        boolean isDrawerOpen(int gravity);

        boolean isDrawerVisible(int gravity);

        void openDrawer(int gravity);
    }

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J:\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&J:\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH&J:\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH&¨\u0006\u0019"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$Interactor;", "", "createFile", "", "callback", "Lorg/catrobat/paintroid/iotasks/CreateFile$CreateFileCallback;", "requestCode", "", "filename", "", "loadFile", "Lorg/catrobat/paintroid/iotasks/LoadImage$LoadImageCallback;", "uri", "Landroid/net/Uri;", "context", "Landroid/content/Context;", "scaling", "", "commandSerializer", "Lorg/catrobat/paintroid/command/serialization/CommandSerializer;", "saveCopy", "Lorg/catrobat/paintroid/iotasks/SaveImage$SaveImageCallback;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "saveImage", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Interactor {
        void createFile(CreateFile.CreateFileCallback callback, int requestCode, String filename);

        void loadFile(LoadImage.LoadImageCallback callback, int requestCode, Uri uri, Context context, boolean scaling, CommandSerializer commandSerializer);

        void saveCopy(SaveImage.SaveImageCallback callback, int requestCode, LayerContracts.Model layerModel, CommandSerializer commandSerializer, Uri uri, Context context);

        void saveImage(SaveImage.SaveImageCallback callback, int requestCode, LayerContracts.Model layerModel, CommandSerializer commandSerializer, Uri uri, Context context);
    }

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0014H&J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H&J\b\u0010\u001a\u001a\u00020\u0014H&J\b\u0010\u001b\u001a\u00020\u0014H&J\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u0007H&J\b\u0010\u001e\u001a\u00020\u0014H&J\b\u0010\u001f\u001a\u00020\u0014H&J\"\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\b\u0010$\u001a\u0004\u0018\u00010%H&J+\u0010&\u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(2\u0006\u0010*\u001a\u00020+H&¢\u0006\u0002\u0010,R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\tR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006-"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$MainView;", "", "displayMetrics", "Landroid/util/DisplayMetrics;", "getDisplayMetrics", "()Landroid/util/DisplayMetrics;", "finishing", "", "getFinishing", "()Z", "isKeyboardShown", "myContentResolver", "Landroid/content/ContentResolver;", "getMyContentResolver", "()Landroid/content/ContentResolver;", "presenter", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;", "getPresenter", "()Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;", "enterHideButtons", "", "exitHideButtons", "getUriFromFile", "Landroid/net/Uri;", "file", "Ljava/io/File;", "hideContentLoadingProgressBar", "hideKeyboard", "initializeActionBar", "isOpenedFromCatroid", "refreshDrawingSurface", "showContentLoadingProgressBar", "superHandleActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "superHandleRequestPermissionsResult", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface MainView {
        void enterHideButtons();

        void exitHideButtons();

        DisplayMetrics getDisplayMetrics();

        boolean getFinishing();

        ContentResolver getMyContentResolver();

        Presenter getPresenter();

        Uri getUriFromFile(File file);

        void hideContentLoadingProgressBar();

        void hideKeyboard();

        void initializeActionBar(boolean isOpenedFromCatroid);

        boolean isKeyboardShown();

        void refreshDrawingSurface();

        void showContentLoadingProgressBar();

        void superHandleActivityResult(int requestCode, int resultCode, Intent data);

        void superHandleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
    }

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000fH&J\b\u0010\u001e\u001a\u00020\u000fH&R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012R\u0018\u0010\u0015\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0010\"\u0004\b\u0016\u0010\u0012R\u0018\u0010\u0017\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u0005\"\u0004\b\u001b\u0010\u0007¨\u0006\u001f"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$Model;", "", "cameraImageUri", "Landroid/net/Uri;", "getCameraImageUri", "()Landroid/net/Uri;", "setCameraImageUri", "(Landroid/net/Uri;)V", "colorHistory", "Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "getColorHistory", "()Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "setColorHistory", "(Lorg/catrobat/paintroid/colorpicker/ColorHistory;)V", "isFullscreen", "", "()Z", "setFullscreen", "(Z)V", "isOpenedFromCatroid", "setOpenedFromCatroid", "isOpenedFromFormulaEditorInCatroid", "setOpenedFromFormulaEditorInCatroid", "isSaved", "setSaved", "savedPictureUri", "getSavedPictureUri", "setSavedPictureUri", "setInitialAnimationPlayed", "", "wasInitialAnimationPlayed", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Model {
        Uri getCameraImageUri();

        ColorHistory getColorHistory();

        Uri getSavedPictureUri();

        boolean isFullscreen();

        boolean isOpenedFromCatroid();

        boolean isOpenedFromFormulaEditorInCatroid();

        boolean isSaved();

        void setCameraImageUri(Uri uri);

        void setColorHistory(ColorHistory colorHistory);

        void setFullscreen(boolean z);

        void setInitialAnimationPlayed(boolean wasInitialAnimationPlayed);

        void setOpenedFromCatroid(boolean z);

        void setOpenedFromFormulaEditorInCatroid(boolean z);

        void setSaved(boolean z);

        void setSavedPictureUri(Uri uri);

        boolean wasInitialAnimationPlayed();
    }

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J#\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH&¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\bH&J\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u000bH&J\b\u0010\u0015\u001a\u00020\bH&J\u001b\u0010\u0016\u001a\u00020\u00032\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH&¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\bH&J\b\u0010\u0019\u001a\u00020\bH&J\u0012\u0010\u001a\u001a\u00020\b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000bH&J\b\u0010\u001c\u001a\u00020\bH&J\b\u0010\u001d\u001a\u00020\bH&J\b\u0010\u001e\u001a\u00020\bH&J\b\u0010\u001f\u001a\u00020\bH&J\b\u0010 \u001a\u00020\bH&J\b\u0010!\u001a\u00020\bH&J\b\u0010\"\u001a\u00020\bH&J\b\u0010#\u001a\u00020\bH&J\b\u0010$\u001a\u00020\bH&J\b\u0010%\u001a\u00020\bH&J\b\u0010&\u001a\u00020\bH&J\b\u0010'\u001a\u00020\bH&J\b\u0010(\u001a\u00020\bH&J\b\u0010)\u001a\u00020\bH&J\b\u0010*\u001a\u00020\bH&J\u0018\u0010+\u001a\u00020\b2\u0006\u0010,\u001a\u00020\r2\u0006\u0010-\u001a\u00020\u0003H&J\b\u0010.\u001a\u00020\bH&J\b\u0010/\u001a\u00020\bH&J\b\u00100\u001a\u00020\bH&J+\u00101\u001a\u00020\b2\u0006\u00102\u001a\u0002032\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH&¢\u0006\u0002\u00104J\b\u00105\u001a\u00020\bH&J\b\u00106\u001a\u00020\bH&J\b\u00107\u001a\u00020\bH&J\b\u00108\u001a\u00020\bH&J \u00109\u001a\u00020\b2\u0006\u0010,\u001a\u00020\r2\u0006\u0010:\u001a\u00020\r2\u0006\u0010-\u001a\u00020\u0003H&J\u001a\u0010;\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\f\u001a\u00020\rH&J\u001a\u0010<\u001a\u00020\b2\b\b\u0001\u0010=\u001a\u00020\r2\u0006\u0010>\u001a\u00020\rH&J\u0018\u0010<\u001a\u00020\b2\u0006\u0010?\u001a\u00020\u000b2\u0006\u0010>\u001a\u00020\rH&J\u0018\u0010@\u001a\u00020\b2\u0006\u0010A\u001a\u00020\r2\u0006\u0010B\u001a\u00020\rH&J\u0010\u0010C\u001a\u00020\b2\u0006\u0010D\u001a\u00020EH&J\u0010\u0010F\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010G\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH&J\u0012\u0010H\u001a\u00020\b2\b\u0010I\u001a\u0004\u0018\u00010JH&J\u0010\u0010K\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0004R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006L"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$Navigator;", "", "isSdkAboveOrEqualM", "", "()Z", "isSdkAboveOrEqualQ", "isSdkAboveOrEqualT", "askForPermission", "", "permissions", "", "", "requestCode", "", "([Ljava/lang/String;I)V", "broadcastAddPictureToGallery", "uri", "Landroid/net/Uri;", "dismissIndeterminateProgressDialog", "doIHavePermission", "permission", "finishActivity", "isPermissionPermanentlyDenied", "([Ljava/lang/String;)Z", "rateUsClicked", "restoreFragmentListeners", "returnToPocketCode", "path", "sendFeedback", "setAntialiasingOnToolPaint", "setMaskFilterToNull", "showAboutDialog", "showAdvancedSettingsDialog", "showCatrobatInformationDialog", "showCatroidMediaGallery", "showColorPickerDialog", "showFeedbackDialog", "showImageImportDialog", "showIndeterminateProgressDialog", "showJpgInformationDialog", "showLikeUsDialog", "showLoadErrorDialog", "showOraInformationDialog", "showOverwriteDialog", "permissionCode", "isExport", "showPngInformationDialog", "showRateUsDialog", "showRequestPermanentlyDeniedPermissionRationaleDialog", "showRequestPermissionRationaleDialog", "permissionType", "Lorg/catrobat/paintroid/dialog/PermissionInfoDialog$PermissionType;", "(Lorg/catrobat/paintroid/dialog/PermissionInfoDialog$PermissionType;[Ljava/lang/String;I)V", "showSaveBeforeFinishDialog", "showSaveBeforeLoadImageDialog", "showSaveBeforeNewImageDialog", "showSaveErrorDialog", "showSaveImageInformationDialogWhenStandalone", "imageNumber", "showScaleImageRequestDialog", "showToast", "resId", "duration", NotificationCompat.CATEGORY_MESSAGE, "showToolChangeToast", TypedValues.CycleType.S_WAVE_OFFSET, "idRes", "showZoomWindowSettingsDialog", "sharedPreferences", "Lorg/catrobat/paintroid/UserPreferences;", "startImportImageActivity", "startLoadImageActivity", "startShareImageActivity", "bitmap", "Landroid/graphics/Bitmap;", "startWelcomeActivity", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Navigator {
        void askForPermission(String[] permissions, int requestCode);

        void broadcastAddPictureToGallery(Uri uri);

        void dismissIndeterminateProgressDialog();

        boolean doIHavePermission(String permission);

        void finishActivity();

        boolean isPermissionPermanentlyDenied(String[] permissions);

        boolean isSdkAboveOrEqualM();

        boolean isSdkAboveOrEqualQ();

        boolean isSdkAboveOrEqualT();

        void rateUsClicked();

        void restoreFragmentListeners();

        void returnToPocketCode(String path);

        void sendFeedback();

        void setAntialiasingOnToolPaint();

        void setMaskFilterToNull();

        void showAboutDialog();

        void showAdvancedSettingsDialog();

        void showCatrobatInformationDialog();

        void showCatroidMediaGallery();

        void showColorPickerDialog();

        void showFeedbackDialog();

        void showImageImportDialog();

        void showIndeterminateProgressDialog();

        void showJpgInformationDialog();

        void showLikeUsDialog();

        void showLoadErrorDialog();

        void showOraInformationDialog();

        void showOverwriteDialog(int permissionCode, boolean isExport);

        void showPngInformationDialog();

        void showRateUsDialog();

        void showRequestPermanentlyDeniedPermissionRationaleDialog();

        void showRequestPermissionRationaleDialog(PermissionInfoDialog.PermissionType permissionType, String[] permissions, int requestCode);

        void showSaveBeforeFinishDialog();

        void showSaveBeforeLoadImageDialog();

        void showSaveBeforeNewImageDialog();

        void showSaveErrorDialog();

        void showSaveImageInformationDialogWhenStandalone(int permissionCode, int imageNumber, boolean isExport);

        void showScaleImageRequestDialog(Uri uri, int requestCode);

        void showToast(int resId, int duration);

        void showToast(String msg, int duration);

        void showToolChangeToast(int offset, int idRes);

        void showZoomWindowSettingsDialog(UserPreferences sharedPreferences);

        void startImportImageActivity(int requestCode);

        void startLoadImageActivity(int requestCode);

        void startShareImageActivity(Bitmap bitmap);

        void startWelcomeActivity(int requestCode);
    }

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\u000fH&J\b\u0010\u0011\u001a\u00020\u000fH&J\b\u0010\u0012\u001a\u00020\u000fH&J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0003H&J\b\u0010\u0015\u001a\u00020\u0016H&J\b\u0010\u0017\u001a\u00020\u000fH&J\b\u0010\u0018\u001a\u00020\u000fH&J\b\u0010\u0019\u001a\u00020\u000fH&J\b\u0010\u001a\u001a\u00020\u000fH&J\b\u0010\u001b\u001a\u00020\u000fH&J\"\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u000b2\b\u0010\u001f\u001a\u0004\u0018\u00010 H&J+\u0010!\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000b2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010%\u001a\u00020&H&¢\u0006\u0002\u0010'J\b\u0010(\u001a\u00020\u000fH&J\b\u0010)\u001a\u00020\u000fH&J\u001c\u0010*\u001a\u00020\u000f2\b\u0010+\u001a\u0004\u0018\u00010$2\b\u0010,\u001a\u0004\u0018\u00010$H&J\b\u0010-\u001a\u00020\u000fH&J\u001a\u0010.\u001a\u00020\u000f2\b\u0010/\u001a\u0004\u0018\u0001002\u0006\u0010\u001d\u001a\u00020\u000bH&J\b\u00101\u001a\u00020\u000fH&J\b\u00102\u001a\u00020\u000fH&J\b\u00103\u001a\u00020\u000fH&J\b\u00104\u001a\u00020\u000fH&J\b\u00105\u001a\u00020\u000fH&J\n\u00106\u001a\u0004\u0018\u000107H&J\b\u00108\u001a\u00020\u000fH&J\b\u00109\u001a\u00020\u000fH&J\u0012\u0010:\u001a\u00020\u000f2\b\u0010;\u001a\u0004\u0018\u00010<H&J\b\u0010=\u001a\u00020\u000fH&J<\u0010>\u001a\u00020\u000f2\u0006\u0010?\u001a\u00020\u00162\u0006\u0010@\u001a\u00020\u00162\u0006\u0010A\u001a\u00020\u00162\u0006\u0010B\u001a\u00020\u00162\b\u0010C\u001a\u0004\u0018\u0001002\b\u0010D\u001a\u0004\u0018\u000100H&J\b\u0010E\u001a\u00020\u000fH&J\b\u0010F\u001a\u00020\u000fH&J\b\u0010G\u001a\u00020\u000fH&J\u0010\u0010H\u001a\u00020\u000f2\u0006\u0010I\u001a\u00020\u0016H&J\u001a\u0010J\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000b2\b\u0010/\u001a\u0004\u0018\u000100H&J\b\u0010K\u001a\u00020\u000fH&J\u001a\u0010L\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000b2\b\u0010/\u001a\u0004\u0018\u000100H&J\b\u0010M\u001a\u00020\u000fH&J\b\u0010N\u001a\u00020\u000fH&J\b\u0010O\u001a\u00020\u000fH&J\u0010\u0010P\u001a\u00020\u000f2\u0006\u0010Q\u001a\u00020\u000bH&J\u0012\u0010R\u001a\u00020\u000f2\b\u0010S\u001a\u0004\u0018\u00010TH&J\u0010\u0010U\u001a\u00020\u000f2\u0006\u0010V\u001a\u00020WH&J\b\u0010X\u001a\u00020\u000fH&J\b\u0010Y\u001a\u00020\u000fH&J\b\u0010Z\u001a\u00020\u000fH&J\b\u0010[\u001a\u00020\u000fH&J\b\u0010\\\u001a\u00020\u000fH&J\b\u0010]\u001a\u00020\u000fH&J\b\u0010^\u001a\u00020\u000fH&J\b\u0010_\u001a\u00020\u000fH&J\b\u0010`\u001a\u00020\u000fH&J\b\u0010a\u001a\u00020\u000fH&J\b\u0010b\u001a\u00020\u000fH&J\u0018\u0010c\u001a\u00020\u000f2\u0006\u0010d\u001a\u00020\u000b2\u0006\u0010I\u001a\u00020\u0016H&J\b\u0010e\u001a\u00020\u000fH&J\b\u0010f\u001a\u00020\u000fH&J\u0010\u0010g\u001a\u00020\u000f2\u0006\u0010h\u001a\u00020iH&J\u0018\u0010j\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010I\u001a\u00020\u0016H&J\u0010\u0010k\u001a\u00020\u000f2\u0006\u0010l\u001a\u00020mH&J\b\u0010n\u001a\u00020\u000fH&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006o"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;", "", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "imageNumber", "", "getImageNumber", "()I", "actionCurrentToolClicked", "", "actionToolsClicked", "addImageToCurrentLayerClicked", "backToPocketCodeClicked", "bitmapLoadedFromSource", "loadedImage", "checkForTemporaryFile", "", "discardImageClicked", "enterHideButtonsClicked", "exitHideButtonsClicked", "finishActivity", "finishInitialize", "handleActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "handleRequestPermissionsResult", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "importFromGalleryClicked", "importStickersClicked", "initializeFromCleanState", "extraPicturePath", "extraPictureName", "loadNewImage", "loadScaledImage", "uri", "Landroid/net/Uri;", "newImageClicked", "onBackPressed", "onCommandPostExecute", "onCreateTool", "onNewImage", "openTemporaryFile", "Lorg/catrobat/paintroid/iotasks/WorkspaceReturnValue;", "rateUsClicked", "redoClicked", "removeMoreOptionsItems", LineReader.MENU, "Landroid/view/Menu;", "replaceImageClicked", "restoreState", "isFullscreen", "isSaved", "isOpenedFromCatroid", "isOpenedFromFormulaEditorInCatroid", "savedPictureUri", "cameraImageUri", "saveBeforeFinish", "saveBeforeLoadImage", "saveBeforeNewImage", "saveCopyClicked", "isExport", "saveCopyConfirmClicked", "saveImageClicked", "saveImageConfirmClicked", "saveNewTemporaryImage", "sendFeedback", "setAntialiasingOnOkClicked", "setBottomNavigationColor", TypedValues.Custom.S_COLOR, "setColorHistoryAfterLoadImage", "colorHistory", "Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "setLayerAdapter", "layerAdapter", "Lorg/catrobat/paintroid/ui/LayerAdapter;", "shareImageClicked", "showAboutClicked", "showAdvancedSettingsClicked", "showCatrobatInformationDialog", "showColorPickerClicked", "showFeedbackDialog", "showHelpClicked", "showImportDialog", "showJpgInformationDialog", "showLayerMenuClicked", "showOraInformationDialog", "showOverwriteDialog", "permissionCode", "showPngInformationDialog", "showRateUsDialog", "showZoomWindowSettingsClicked", "sharedPreferences", "Lorg/catrobat/paintroid/UserPreferences;", "switchBetweenVersions", "toolClicked", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "undoClicked", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Presenter {
        void actionCurrentToolClicked();

        void actionToolsClicked();

        void addImageToCurrentLayerClicked();

        void backToPocketCodeClicked();

        void bitmapLoadedFromSource(Bitmap loadedImage);

        boolean checkForTemporaryFile();

        void discardImageClicked();

        void enterHideButtonsClicked();

        void exitHideButtonsClicked();

        void finishActivity();

        void finishInitialize();

        Bitmap getBitmap();

        Context getContext();

        int getImageNumber();

        void handleActivityResult(int requestCode, int resultCode, Intent data);

        void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

        void importFromGalleryClicked();

        void importStickersClicked();

        void initializeFromCleanState(String extraPicturePath, String extraPictureName);

        void loadNewImage();

        void loadScaledImage(Uri uri, int requestCode);

        void newImageClicked();

        void onBackPressed();

        void onCommandPostExecute();

        void onCreateTool();

        void onNewImage();

        WorkspaceReturnValue openTemporaryFile();

        void rateUsClicked();

        void redoClicked();

        void removeMoreOptionsItems(Menu menu);

        void replaceImageClicked();

        void restoreState(boolean isFullscreen, boolean isSaved, boolean isOpenedFromCatroid, boolean isOpenedFromFormulaEditorInCatroid, Uri savedPictureUri, Uri cameraImageUri);

        void saveBeforeFinish();

        void saveBeforeLoadImage();

        void saveBeforeNewImage();

        void saveCopyClicked(boolean isExport);

        void saveCopyConfirmClicked(int requestCode, Uri uri);

        void saveImageClicked();

        void saveImageConfirmClicked(int requestCode, Uri uri);

        void saveNewTemporaryImage();

        void sendFeedback();

        void setAntialiasingOnOkClicked();

        void setBottomNavigationColor(int color);

        void setColorHistoryAfterLoadImage(ColorHistory colorHistory);

        void setLayerAdapter(LayerAdapter layerAdapter);

        void shareImageClicked();

        void showAboutClicked();

        void showAdvancedSettingsClicked();

        void showCatrobatInformationDialog();

        void showColorPickerClicked();

        void showFeedbackDialog();

        void showHelpClicked();

        void showImportDialog();

        void showJpgInformationDialog();

        void showLayerMenuClicked();

        void showOraInformationDialog();

        void showOverwriteDialog(int permissionCode, boolean isExport);

        void showPngInformationDialog();

        void showRateUsDialog();

        void showZoomWindowSettingsClicked(UserPreferences sharedPreferences);

        void switchBetweenVersions(int requestCode, boolean isExport);

        void toolClicked(ToolType toolType);

        void undoClicked();
    }

    /* JADX INFO: compiled from: MainActivityContracts.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\u0007H&J\b\u0010\t\u001a\u00020\u0007H&J\b\u0010\n\u001a\u00020\u0007H&J\b\u0010\u000b\u001a\u00020\u0007H&J\b\u0010\f\u001a\u00020\u0007H&J\u0012\u0010\r\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH&J\u0012\u0010\u0010\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH&J\b\u0010\u0011\u001a\u00020\u0007H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0012"}, d2 = {"Lorg/catrobat/paintroid/contract/MainActivityContracts$TopBarViewHolder;", "", "height", "", "getHeight", "()I", "disableRedoButton", "", "disableUndoButton", "enableRedoButton", "enableUndoButton", "hide", "hideTitleIfNotStandalone", "removeCatroidMenuItems", LineReader.MENU, "Landroid/view/Menu;", "removeStandaloneMenuItems", "show", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface TopBarViewHolder {
        void disableRedoButton();

        void disableUndoButton();

        void enableRedoButton();

        void enableUndoButton();

        int getHeight();

        void hide();

        void hideTitleIfNotStandalone();

        void removeCatroidMenuItems(Menu menu);

        void removeStandaloneMenuItems(Menu menu);

        void show();
    }
}
