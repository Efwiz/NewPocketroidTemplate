package org.catrobat.catroid.ui.dialogs.regexassistant;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.web.WebpageUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/regexassistant/RegularExpressionAssistantDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RegularExpressionAssistantDialog {
    static List<RegularExpressionFeature> listOfFeatures;
    Context context;
    FragmentManager fragmentManager;
    List<String> namesOfFeatures;

    public RegularExpressionAssistantDialog(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        createListOfFeatures();
    }

    public void createAssistant() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this.context, 2131952355));
        builder.setTitle(R.string.formula_editor_dialog_regular_expression_assistant_title);
        builder.setNegativeButton(R.string.help, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.regexassistant.RegularExpressionAssistantDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5688x7a9579b(dialogInterface, i);
            }
        });
        builder.setPositiveButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        buildListOfFeatures(builder);
        builder.create().show();
    }

    /* JADX INFO: renamed from: lambda$createAssistant$0$org-catrobat-catroid-ui-dialogs-regexassistant-RegularExpressionAssistantDialog, reason: not valid java name */
    /* synthetic */ void m5688x7a9579b(DialogInterface dialogInterface, int i) {
        WebpageUtils.openWikiPage(this.context);
    }

    private void buildListOfFeatures(AlertDialog.Builder builder) {
        this.namesOfFeatures = new ArrayList();
        Iterator<RegularExpressionFeature> it = listOfFeatures.iterator();
        while (it.hasNext()) {
            this.namesOfFeatures.add(this.context.getString(it.next().getTitleId()));
        }
        builder.setItems((CharSequence[]) this.namesOfFeatures.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.regexassistant.RegularExpressionAssistantDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5687xdc19bda(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$buildListOfFeatures$1$org-catrobat-catroid-ui-dialogs-regexassistant-RegularExpressionAssistantDialog, reason: not valid java name */
    /* synthetic */ void m5687xdc19bda(DialogInterface dialogInterface, int i) {
        listOfFeatures.get(i).openDialog(this.context);
    }

    private void createListOfFeatures() {
        ArrayList arrayList = new ArrayList();
        listOfFeatures = arrayList;
        arrayList.add(new HtmlExtractorDialog(this.fragmentManager));
        listOfFeatures.add(new JsonExtractorDialog(this.fragmentManager));
    }
}
