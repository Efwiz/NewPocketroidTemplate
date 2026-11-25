package org.catrobat.catroid.ui.fragment;

import android.R;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.databinding.DialogCommandPromptBinding;
import org.catrobat.catroid.python.CommandOutputListener;
import org.catrobat.catroid.python.PythonCommandManager;
import org.catrobat.catroid.python.PythonEngine;
import org.catrobat.catroid.ui.MainMenuActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/CommandPromptDialogFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: CommandPromptDialogFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 02\u00020\u00012\u00020\u0002:\u00010B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0012H\u0002J\b\u0010\u001a\u001a\u00020\u0018H\u0016J\u0012\u0010\u001b\u001a\u00020\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J$\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010$\u001a\u00020\u0018H\u0016J\u0010\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u0014H\u0016J\u0010\u0010'\u001a\u00020\u00182\u0006\u0010(\u001a\u00020\u000bH\u0016J\u001a\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u001f2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u0010\u0010+\u001a\u00020\u00182\u0006\u0010,\u001a\u00020-H\u0002J\b\u0010.\u001a\u00020\u0018H\u0002J\b\u0010/\u001a\u00020\u0018H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/CommandPromptDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "Lorg/catrobat/catroid/python/CommandOutputListener;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/DialogCommandPromptBinding;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/DialogCommandPromptBinding;", "commandHistory", "", "", "commandManager", "Lorg/catrobat/catroid/python/PythonCommandManager;", "displayPath", "getDisplayPath", "()Ljava/lang/String;", "historyIndex", "", "projectFilesDir", "Ljava/io/File;", "pythonEngine", "Lorg/catrobat/catroid/python/PythonEngine;", "navigateHistory", "", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "onComplete", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroyView", "onOpenEditor", "file", "onOutput", "output", "onViewCreated", Promotion.ACTION_VIEW, "showLoading", "isLoading", "", "submitCommand", "updatePrompt", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommandPromptDialogFragment extends DialogFragment implements CommandOutputListener {
    private static final String ARG_PROJECT_PATH = "project_path";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private DialogCommandPromptBinding _binding;
    private PythonCommandManager commandManager;
    private File projectFilesDir;
    private PythonEngine pythonEngine;
    private final List<String> commandHistory = new ArrayList();
    private int historyIndex = -1;

    private final DialogCommandPromptBinding getBinding() {
        DialogCommandPromptBinding dialogCommandPromptBinding = this._binding;
        Intrinsics.checkNotNull(dialogCommandPromptBinding);
        return dialogCommandPromptBinding;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(1, R.style.Theme.DeviceDefault.Light.NoActionBar);
        PythonEngine pythonEngine = MainMenuActivity.pythonEngine;
        if (pythonEngine == null) {
            dismiss();
            return;
        }
        this.pythonEngine = pythonEngine;
        PythonEngine pythonEngine2 = null;
        if (pythonEngine == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pythonEngine");
            pythonEngine = null;
        }
        pythonEngine.initialize();
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        File filesDir = currentProject.getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "getFilesDir(...)");
        this.projectFilesDir = filesDir;
        if (filesDir == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectFilesDir");
            filesDir = null;
        }
        if (!filesDir.exists()) {
            File file = this.projectFilesDir;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("projectFilesDir");
                file = null;
            }
            file.mkdirs();
        }
        PythonEngine pythonEngine3 = this.pythonEngine;
        if (pythonEngine3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pythonEngine");
        } else {
            pythonEngine2 = pythonEngine3;
        }
        Intrinsics.checkNotNull(currentProject);
        this.commandManager = new PythonCommandManager(pythonEngine2, currentProject);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = DialogCommandPromptBinding.inflate(inflater, container, false);
        LinearLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        PythonCommandManager pythonCommandManager = this.commandManager;
        if (pythonCommandManager == null) {
            return;
        }
        if (pythonCommandManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commandManager");
            pythonCommandManager = null;
        }
        pythonCommandManager.setOutputListener(this);
        getBinding().sendButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.CommandPromptDialogFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws IOException {
                CommandPromptDialogFragment.onViewCreated$lambda$0(this.f$0, view2);
            }
        });
        getBinding().terminalInput.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.catrobat.catroid.ui.fragment.CommandPromptDialogFragment$$ExternalSyntheticLambda4
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return CommandPromptDialogFragment.onViewCreated$lambda$1(this.f$0, textView, i, keyEvent);
            }
        });
        getBinding().btnHistoryUp.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.CommandPromptDialogFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CommandPromptDialogFragment.onViewCreated$lambda$2(this.f$0, view2);
            }
        });
        getBinding().btnHistoryDown.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.CommandPromptDialogFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CommandPromptDialogFragment.onViewCreated$lambda$3(this.f$0, view2);
            }
        });
        getBinding().terminalOutput.setTypeface(Typeface.MONOSPACE);
        getBinding().terminalOutput.setText(SyntaxHighlighter.INSTANCE.highlight("Shell 1.2\nLinux coreutils available (ls, cd, cp, rm, cat, nano, etc.)\n\n"));
        updatePrompt();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(CommandPromptDialogFragment this$0, View view) throws IOException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.submitCommand();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onViewCreated$lambda$1(CommandPromptDialogFragment this$0, TextView textView, int i, KeyEvent keyEvent) throws IOException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i != 6) {
            return false;
        }
        this$0.submitCommand();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$2(CommandPromptDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.navigateHistory(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$3(CommandPromptDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.navigateHistory(1);
    }

    private final void navigateHistory(int direction) {
        if (this.commandHistory.isEmpty()) {
            return;
        }
        int i = this.historyIndex + direction;
        this.historyIndex = i;
        int iCoerceIn = RangesKt.coerceIn(i, -1, this.commandHistory.size() - 1);
        this.historyIndex = iCoerceIn;
        if (iCoerceIn == -1) {
            getBinding().terminalInput.setText("");
            return;
        }
        List<String> list = this.commandHistory;
        String str = list.get((list.size() - 1) - this.historyIndex);
        getBinding().terminalInput.setText(str);
        getBinding().terminalInput.setSelection(str.length());
    }

    private final void submitCommand() throws IOException {
        String string = getBinding().terminalInput.getText().toString();
        if (StringsKt.isBlank(string)) {
            return;
        }
        if (this.commandHistory.isEmpty() || !Intrinsics.areEqual(CollectionsKt.last((List) this.commandHistory), string)) {
            this.commandHistory.add(string);
        }
        this.historyIndex = -1;
        getBinding().terminalInput.getText().clear();
        showLoading(true);
        PythonCommandManager pythonCommandManager = this.commandManager;
        if (pythonCommandManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commandManager");
            pythonCommandManager = null;
        }
        pythonCommandManager.processCommand(string);
    }

    @Override // org.catrobat.catroid.python.CommandOutputListener
    public void onOutput(String output) {
        Intrinsics.checkNotNullParameter(output, "output");
        getBinding().terminalOutput.append(SyntaxHighlighter.INSTANCE.highlight(output));
        getBinding().scrollView.post(new Runnable() { // from class: org.catrobat.catroid.ui.fragment.CommandPromptDialogFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CommandPromptDialogFragment.onOutput$lambda$4(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onOutput$lambda$4(CommandPromptDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().scrollView.fullScroll(130);
    }

    @Override // org.catrobat.catroid.python.CommandOutputListener
    public void onComplete() {
        showLoading(false);
        updatePrompt();
    }

    @Override // org.catrobat.catroid.python.CommandOutputListener
    public void onOpenEditor(final File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        final Dialog dialog = new Dialog(requireContext(), R.style.Theme.DeviceDefault.NoActionBar.Fullscreen);
        dialog.setContentView(org.DanVexTeam.NewCatroidApkTemplate.R.layout.dialog_nano_editor);
        TextView textView = (TextView) dialog.findViewById(org.DanVexTeam.NewCatroidApkTemplate.R.id.nano_title);
        final EditText editText = (EditText) dialog.findViewById(org.DanVexTeam.NewCatroidApkTemplate.R.id.nano_editor_input);
        Button button = (Button) dialog.findViewById(org.DanVexTeam.NewCatroidApkTemplate.R.id.nano_btn_save);
        Button button2 = (Button) dialog.findViewById(org.DanVexTeam.NewCatroidApkTemplate.R.id.nano_btn_exit);
        textView.setText("nano - " + file.getName());
        if (file.exists()) {
            editText.setText(FilesKt.readText$default(file, null, 1, null));
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.CommandPromptDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CommandPromptDialogFragment.onOpenEditor$lambda$5(file, editText, this, view);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.CommandPromptDialogFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CommandPromptDialogFragment.onOpenEditor$lambda$6(this.f$0, dialog, view);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onOpenEditor$lambda$5(File file, EditText editText, CommandPromptDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(file, "$file");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            FilesKt.writeText$default(file, editText.getText().toString(), null, 2, null);
            this$0.onOutput("Saved " + file.getName() + "\n");
            Toast.makeText(this$0.requireContext(), "Файл " + file.getName() + " сохранен", 0).show();
        } catch (Exception e) {
            this$0.onOutput("Error saving file: " + e.getMessage() + "\n");
            Toast.makeText(this$0.requireContext(), "Ошибка сохранения", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onOpenEditor$lambda$6(CommandPromptDialogFragment this$0, Dialog dialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(dialog, "$dialog");
        this$0.onOutput("Editor exited\n");
        this$0.onComplete();
        dialog.dismiss();
    }

    private final void showLoading(boolean isLoading) {
        getBinding().progressBar.setVisibility(isLoading ? 0 : 8);
        getBinding().terminalInput.setEnabled(!isLoading);
        getBinding().sendButton.setEnabled(!isLoading);
        if (isLoading) {
            return;
        }
        getBinding().terminalInput.requestFocus();
        getBinding().scrollView.post(new Runnable() { // from class: org.catrobat.catroid.ui.fragment.CommandPromptDialogFragment$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                CommandPromptDialogFragment.showLoading$lambda$7(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showLoading$lambda$7(CommandPromptDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().scrollView.fullScroll(130);
    }

    private final String getDisplayPath() {
        PythonCommandManager pythonCommandManager = this.commandManager;
        File file = null;
        if (pythonCommandManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commandManager");
            pythonCommandManager = null;
        }
        String absolutePath = pythonCommandManager.getCurrentWorkingDirectory().getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        File file2 = this.projectFilesDir;
        if (file2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectFilesDir");
        } else {
            file = file2;
        }
        String absolutePath2 = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
        String strRemovePrefix = StringsKt.removePrefix(absolutePath, (CharSequence) absolutePath2);
        return strRemovePrefix.length() == 0 ? "~" : "~" + strRemovePrefix;
    }

    private final void updatePrompt() {
        getBinding().terminalPrompt.setText(getDisplayPath() + " $ ");
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        PythonCommandManager pythonCommandManager = this.commandManager;
        if (pythonCommandManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commandManager");
            pythonCommandManager = null;
        }
        pythonCommandManager.setOutputListener(null);
        this._binding = null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/CommandPromptDialogFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CommandPromptDialogFragment.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/CommandPromptDialogFragment$Companion;", "", "()V", "ARG_PROJECT_PATH", "", "TAG", "getTAG", "()Ljava/lang/String;", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/catroid/ui/fragment/CommandPromptDialogFragment;", Constants.EXTRA_PROJECT_PATH, "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return CommandPromptDialogFragment.TAG;
        }

        public final CommandPromptDialogFragment newInstance(String projectPath) {
            Intrinsics.checkNotNullParameter(projectPath, "projectPath");
            Bundle bundle = new Bundle();
            bundle.putString(CommandPromptDialogFragment.ARG_PROJECT_PATH, projectPath);
            CommandPromptDialogFragment commandPromptDialogFragment = new CommandPromptDialogFragment();
            commandPromptDialogFragment.setArguments(bundle);
            return commandPromptDialogFragment;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("CommandPromptDialogFragment", "getSimpleName(...)");
        TAG = "CommandPromptDialogFragment";
    }
}
