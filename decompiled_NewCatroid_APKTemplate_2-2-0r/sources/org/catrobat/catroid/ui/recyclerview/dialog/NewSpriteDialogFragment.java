package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.io.File;
import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.merge.ImportProjectHelper;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.visualplacement.VisualPlacementActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/NewSpriteDialogFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: NewSpriteDialogFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 *2\u00020\u0001:\u0001*B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bBK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011J\u001c\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u0002J\b\u0010\u001e\u001a\u00020\u001aH\u0002J\u001a\u0010\u001f\u001a\u00020\u001a2\b\u0010\u001d\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010 \u001a\u00020\u001aH\u0002J\u0012\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010%\u001a\u00020\u001aH\u0002J\b\u0010&\u001a\u00020\u0003H\u0002J\b\u0010'\u001a\u00020\u001aH\u0002J\u0010\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u0003H\u0002R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/dialog/NewSpriteDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "emptySprite", "", "lookDataName", "", "currentFragment", "Landroidx/fragment/app/Fragment;", "(ZLjava/lang/String;Landroidx/fragment/app/Fragment;)V", "lookFileName", "contentResolver", "Landroid/content/ContentResolver;", "uri", "Landroid/net/Uri;", "isObject", "importProjectHelper", "Lorg/catrobat/catroid/merge/ImportProjectHelper;", "(ZLjava/lang/String;Ljava/lang/String;Landroid/content/ContentResolver;Landroid/net/Uri;Landroidx/fragment/app/Fragment;ZLorg/catrobat/catroid/merge/ImportProjectHelper;)V", "isPlaceVisually", "placeVisuallyTextView", "Landroid/widget/TextView;", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "visuallyPlaceSwitch", "Landroidx/appcompat/widget/SwitchCompat;", "addLookDataToSprite", "", "currentScene", "Lorg/catrobat/catroid/content/Scene;", "textInput", "handleNegativeButton", "handlePositiveButton", "imgFormatNotSupportedDialog", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "setupToggleButtonListener", "showCastDialog", "startVisualPlacementActivity", "visualTextViewVisibility", "isVisible", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewSpriteDialogFragment extends DialogFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private final ContentResolver contentResolver;
    private final Fragment currentFragment;
    private final boolean emptySprite;
    private final ImportProjectHelper importProjectHelper;
    private final boolean isObject;
    private boolean isPlaceVisually;
    private final String lookDataName;
    private final String lookFileName;
    private TextView placeVisuallyTextView;
    private Sprite sprite;
    private final Uri uri;
    private SwitchCompat visuallyPlaceSwitch;

    public NewSpriteDialogFragment(boolean z, String lookDataName, String lookFileName, ContentResolver contentResolver, Uri uri, Fragment currentFragment, boolean z2, ImportProjectHelper importProjectHelper) {
        Intrinsics.checkNotNullParameter(lookDataName, "lookDataName");
        Intrinsics.checkNotNullParameter(lookFileName, "lookFileName");
        Intrinsics.checkNotNullParameter(currentFragment, "currentFragment");
        this.emptySprite = z;
        this.lookDataName = lookDataName;
        this.lookFileName = lookFileName;
        this.contentResolver = contentResolver;
        this.uri = uri;
        this.currentFragment = currentFragment;
        this.isObject = z2;
        this.importProjectHelper = importProjectHelper;
        this.isPlaceVisually = true;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NewSpriteDialogFragment(boolean z, String lookDataName, Fragment currentFragment) {
        this(z, lookDataName, "", null, null, currentFragment, false, null);
        Intrinsics.checkNotNullParameter(lookDataName, "lookDataName");
        Intrinsics.checkNotNullParameter(currentFragment, "currentFragment");
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/NewSpriteDialogFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: NewSpriteDialogFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/dialog/NewSpriteDialogFragment$Companion;", "", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return NewSpriteDialogFragment.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("NewSpriteDialogFragment", "getSimpleName(...)");
        TAG = "NewSpriteDialogFragment";
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder view;
        AlertDialog.Builder negativeButton;
        AlertDialog.Builder title;
        TextInputDialog.Builder hint;
        TextInputDialog.Builder text;
        TextInputDialog.Builder textWatcher;
        AlertDialog alertDialogCreate = null;
        View viewInflate = View.inflate(getActivity(), R.layout.dialog_new_sprite, null);
        final Scene currentlyEditedScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        this.visuallyPlaceSwitch = (SwitchCompat) viewInflate.findViewById(R.id.place_visually_sprite_switch);
        this.placeVisuallyTextView = (TextView) viewInflate.findViewById(R.id.place_visually_textView);
        if (this.emptySprite) {
            SwitchCompat switchCompat = this.visuallyPlaceSwitch;
            if (switchCompat != null) {
                switchCompat.setVisibility(8);
            }
            TextView textView = this.placeVisuallyTextView;
            if (textView != null) {
                textView.setText(getString(R.string.new_empty_sprite_note));
            }
            this.isPlaceVisually = false;
        } else {
            setupToggleButtonListener();
        }
        Context context = getContext();
        TextInputDialog.Builder builder = context != null ? new TextInputDialog.Builder(context) : null;
        if (builder != null && (hint = builder.setHint(getString(R.string.sprite_name_label))) != null && (text = hint.setText(this.lookDataName)) != null && (textWatcher = text.setTextWatcher(new DuplicateInputTextWatcher(currentlyEditedScene.getSpriteList()))) != null) {
            textWatcher.setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.NewSpriteDialogFragment$$ExternalSyntheticLambda0
                @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
                public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) throws Throwable {
                    NewSpriteDialogFragment.onCreateDialog$lambda$1(this.f$0, currentlyEditedScene, dialogInterface, str);
                }
            });
        }
        if (builder != null && (title = builder.setTitle(R.string.new_sprite_dialog_title)) != null) {
            title.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.NewSpriteDialogFragment$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    NewSpriteDialogFragment.onCreateDialog$lambda$2(this.f$0, dialogInterface, i);
                }
            });
        }
        if (builder != null && (view = builder.setView(viewInflate)) != null && (negativeButton = view.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null)) != null) {
            alertDialogCreate = negativeButton.create();
        }
        Intrinsics.checkNotNull(alertDialogCreate, "null cannot be cast to non-null type androidx.appcompat.app.AlertDialog");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateDialog$lambda$1(NewSpriteDialogFragment this$0, Scene scene, DialogInterface dialogInterface, String str) throws Throwable {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(scene);
        this$0.handlePositiveButton(str, scene);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateDialog$lambda$2(NewSpriteDialogFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleNegativeButton();
    }

    private final void handlePositiveButton(String textInput, Scene currentScene) throws Throwable {
        if (this.isObject) {
            ImportProjectHelper importProjectHelper = this.importProjectHelper;
            Sprite spriteAddObjectDataToNewSprite = importProjectHelper != null ? importProjectHelper.addObjectDataToNewSprite(null) : null;
            this.sprite = spriteAddObjectDataToNewSprite;
            if (spriteAddObjectDataToNewSprite != null) {
                spriteAddObjectDataToNewSprite.rename(textInput);
            }
            currentScene.addSprite(this.sprite);
        } else {
            Sprite sprite = new Sprite(textInput);
            this.sprite = sprite;
            currentScene.addSprite(sprite);
            if (!this.emptySprite) {
                addLookDataToSprite(currentScene, textInput);
            }
        }
        Fragment fragment = this.currentFragment;
        if (fragment instanceof SpriteListFragment) {
            ((SpriteListFragment) fragment).notifyDataSetChanged();
        }
        if (this.isPlaceVisually) {
            startVisualPlacementActivity();
        }
        if (showCastDialog()) {
            CastManager castManager = CastManager.getInstance();
            FragmentActivity activity = getActivity();
            castManager.openDeviceSelectorOrDisconnectDialog(activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null);
        }
    }

    private final void handleNegativeButton() {
        try {
            if (Constants.MEDIA_LIBRARY_CACHE_DIRECTORY.exists()) {
                StorageOperations.deleteDir(Constants.MEDIA_LIBRARY_CACHE_DIRECTORY);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private final boolean showCastDialog() {
        return SettingsFragment.isCastSharedPreferenceEnabled(getActivity()) && ProjectManager.getInstance().getCurrentProject().isCastProject() && !CastManager.getInstance().isConnected();
    }

    private final void addLookDataToSprite(Scene currentScene, String textInput) throws Throwable {
        List<LookData> lookList;
        try {
            File file = new File(currentScene != null ? currentScene.getDirectory() : null, Constants.IMAGE_DIRECTORY_NAME);
            File fileCopyUriToDir = StorageOperations.copyUriToDir(this.contentResolver, this.uri, file, this.lookFileName);
            Utils.removeExifData(file, this.lookFileName);
            LookData lookData = new LookData(textInput, fileCopyUriToDir);
            if (lookData.getImageMimeType() == null) {
                imgFormatNotSupportedDialog();
                if (currentScene != null) {
                    currentScene.removeSprite(this.sprite);
                    return;
                }
                return;
            }
            Sprite sprite = this.sprite;
            if (sprite != null && (lookList = sprite.getLookList()) != null) {
                lookList.add(lookData);
            }
            lookData.getCollisionInformation().calculate();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private final void setupToggleButtonListener() {
        boolean z = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(SharedPreferenceKeys.NEW_SPRITE_VISUAL_PLACEMENT_KEY, true);
        SwitchCompat switchCompat = this.visuallyPlaceSwitch;
        if (switchCompat != null) {
            switchCompat.setChecked(z);
        }
        visualTextViewVisibility(z);
        SwitchCompat switchCompat2 = this.visuallyPlaceSwitch;
        if (switchCompat2 != null) {
            switchCompat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.NewSpriteDialogFragment$$ExternalSyntheticLambda2
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    NewSpriteDialogFragment.setupToggleButtonListener$lambda$4(this.f$0, compoundButton, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupToggleButtonListener$lambda$4(NewSpriteDialogFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.visualTextViewVisibility(z);
        PreferenceManager.getDefaultSharedPreferences(this$0.getActivity()).edit().putBoolean(SharedPreferenceKeys.NEW_SPRITE_VISUAL_PLACEMENT_KEY, this$0.isPlaceVisually).apply();
    }

    private final void visualTextViewVisibility(boolean isVisible) {
        this.isPlaceVisually = isVisible;
        TextView textView = this.placeVisuallyTextView;
        if (textView == null) {
            return;
        }
        textView.setVisibility(isVisible ? 0 : 8);
    }

    private final void startVisualPlacementActivity() {
        ProjectManager.getInstance().setCurrentSprite(this.sprite);
        Intent intent = new Intent(requireContext(), new VisualPlacementActivity().getClass());
        intent.putExtra(SpriteActivity.EXTRA_X_TRANSFORM, 100);
        intent.putExtra(SpriteActivity.EXTRA_Y_TRANSFORM, 200);
        intent.putExtra(SpriteActivity.EXTRA_ROTATION, 0.0f);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.startActivityForResult(intent, 2019);
        }
    }

    private final void imgFormatNotSupportedDialog() {
        Context context = getContext();
        AlertDialog.Builder positiveButton = context != null ? new AlertDialog.Builder(context).setMessage(getString(R.string.Image_format_not_supported)).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.NewSpriteDialogFragment$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                NewSpriteDialogFragment.imgFormatNotSupportedDialog$lambda$6$lambda$5(dialogInterface, i);
            }
        }) : null;
        AlertDialog alertDialogCreate = positiveButton != null ? positiveButton.create() : null;
        if (alertDialogCreate != null) {
            alertDialogCreate.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void imgFormatNotSupportedDialog$lambda$6$lambda$5(DialogInterface dialog, int i) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        dialog.cancel();
    }
}
