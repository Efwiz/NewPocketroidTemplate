package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.EditOption;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/BroadcastMessageBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class BroadcastMessageBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<StringOption> {
    private transient BrickSpinner<StringOption> spinner;

    public abstract String getBroadcastMessage();

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, StringOption item) {
    }

    public abstract void setBroadcastMessage(String broadcastMessage);

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        BroadcastMessageBrick broadcastMessageBrick = (BroadcastMessageBrick) super.clone();
        broadcastMessageBrick.spinner = null;
        return broadcastMessageBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        BrickSpinner<StringOption> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_broadcast_spinner), this.view, getSortedItemListFromMessages(context, ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().getBroadcastMessages()));
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(getBroadcastMessage());
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView instanceof SpriteActivity) {
            new TextInputDialog.Builder(activityFromView).setHint(activityFromView.getString(R.string.dialog_broadcast_message_name)).setTextWatcher(new DuplicateInputTextWatcher(new ArrayList())).setText(new UniqueNameProvider().getUniqueName(activityFromView.getString(R.string.default_broadcast_message_name), ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().getBroadcastMessages())).setPositiveButton(activityFromView.getString(R.string.ok), getOkButtonListener(activityFromView)).setTitle(R.string.dialog_new_broadcast_message_title).setNegativeButton(R.string.cancel, getNegativeButtonListener()).setOnCancelListener(getCanceledListener()).show();
        }
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
        AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView instanceof SpriteActivity) {
            String name2 = this.spinner.getSelection() instanceof StringOption ? ((StringOption) this.spinner.getSelection()).getName() : null;
            new TextInputDialog.Builder(activityFromView).setText(name2).setTextWatcher(new DuplicateInputTextWatcher(new ArrayList())).setPositiveButton(activityFromView.getString(R.string.ok), getEditButtonListener(activityFromView, name2)).setTitle(R.string.dialog_edit_broadcast_message_title).setNegativeButton(R.string.cancel, getNegativeButtonListener()).setOnCancelListener(getCanceledListener()).show();
        }
    }

    public void addItem(String item) {
        if (ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().addBroadcastMessage(item)) {
            this.spinner.add(new StringOption(item));
        }
        this.spinner.setSelection(item);
    }

    public boolean removeItem(String item) {
        return ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().removeBroadcastMessage(item);
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
        setBroadcastMessage(string);
    }

    public TextInputDialog.OnClickListener getOkButtonListener(final AppCompatActivity activity) {
        return new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.BroadcastMessageBrick$$ExternalSyntheticLambda3
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m4897xd9fc6f78(activity, dialogInterface, str);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$getOkButtonListener$0$org-catrobat-catroid-content-bricks-BroadcastMessageBrick, reason: not valid java name */
    /* synthetic */ void m4897xd9fc6f78(AppCompatActivity appCompatActivity, DialogInterface dialogInterface, String str) {
        addItem(str);
        notifyDataSetChanged(appCompatActivity);
    }

    public TextInputDialog.OnClickListener getEditButtonListener(final AppCompatActivity activity, final String editMessage) {
        return new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.BroadcastMessageBrick$$ExternalSyntheticLambda2
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m4895xc12ac8a7(editMessage, activity, dialogInterface, str);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$getEditButtonListener$1$org-catrobat-catroid-content-bricks-BroadcastMessageBrick, reason: not valid java name */
    /* synthetic */ void m4895xc12ac8a7(String str, AppCompatActivity appCompatActivity, DialogInterface dialogInterface, String str2) {
        if (removeItem(str)) {
            addItem(str2);
            ProjectManager.getInstance().getCurrentlyEditedScene().editBroadcastMessagesInUse(str, str2);
            notifyDataSetChanged(appCompatActivity);
        }
    }

    public DialogInterface.OnClickListener getNegativeButtonListener() {
        return new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.BroadcastMessageBrick$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m4896xfe098d53(dialogInterface, i);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$getNegativeButtonListener$2$org-catrobat-catroid-content-bricks-BroadcastMessageBrick, reason: not valid java name */
    /* synthetic */ void m4896xfe098d53(DialogInterface dialogInterface, int i) {
        this.spinner.setSelection(getBroadcastMessage());
    }

    public DialogInterface.OnCancelListener getCanceledListener() {
        return new DialogInterface.OnCancelListener() { // from class: org.catrobat.catroid.content.bricks.BroadcastMessageBrick$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                this.f$0.m4894x191d08a6(dialogInterface);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$getCanceledListener$3$org-catrobat-catroid-content-bricks-BroadcastMessageBrick, reason: not valid java name */
    /* synthetic */ void m4894x191d08a6(DialogInterface dialogInterface) {
        this.spinner.setSelection(getBroadcastMessage());
    }

    public static List<Nameable> getSortedItemListFromMessages(Context context, List<String> messages) {
        if (messages.isEmpty()) {
            return Collections.singletonList(new StringOption(context.getString(R.string.brick_broadcast_default_value)));
        }
        Collections.sort(messages, new Comparator() { // from class: org.catrobat.catroid.content.bricks.BroadcastMessageBrick$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((String) obj).compareToIgnoreCase((String) obj2);
            }
        });
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.new_option)));
        arrayList.add(new EditOption(context.getString(R.string.edit_option)));
        Iterator<String> it = messages.iterator();
        while (it.hasNext()) {
            arrayList.add(new StringOption(it.next()));
        }
        return arrayList;
    }
}
