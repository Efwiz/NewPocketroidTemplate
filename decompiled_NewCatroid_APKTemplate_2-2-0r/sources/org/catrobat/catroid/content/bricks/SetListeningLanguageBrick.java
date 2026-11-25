package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.LanguageObject;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetListeningLanguageBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetListeningLanguageBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0017\u0010\u0017\u001a\u00020\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0016H\u0016¢\u0006\u0002\u0010\u0019J!\u0010\u001a\u001a\u00020\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u001cJ\u0017\u0010\u001d\u001a\u00020\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0016H\u0016¢\u0006\u0002\u0010\u0019J!\u0010\u001e\u001a\u00020\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00162\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016¢\u0006\u0002\u0010!R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082.¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/catrobat/catroid/content/bricks/SetListeningLanguageBrick;", "Lorg/catrobat/catroid/content/bricks/BrickBaseType;", "Lorg/catrobat/catroid/content/bricks/brickspinner/BrickSpinner$OnItemSelectedListener;", "Lorg/catrobat/catroid/content/bricks/brickspinner/LanguageObject;", "()V", "languageObject", "spinner", "Lorg/catrobat/catroid/content/bricks/brickspinner/BrickSpinner;", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "addRequiredResources", "requiredResourcesSet", "Lorg/catrobat/catroid/content/bricks/Brick$ResourcesSet;", "getView", "Landroid/view/View;", "context", "Landroid/content/Context;", "getViewResource", "", "onEditOptionSelected", "spinnerId", "(Ljava/lang/Integer;)V", "onItemSelected", "item", "(Ljava/lang/Integer;Lorg/catrobat/catroid/content/bricks/brickspinner/LanguageObject;)V", "onNewOptionSelected", "onStringOptionSelected", "string", "", "(Ljava/lang/Integer;Ljava/lang/String;)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetListeningLanguageBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<LanguageObject> {
    private LanguageObject languageObject;
    private transient BrickSpinner<LanguageObject> spinner;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_listening_language;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        ActionFactory actionFactory = sprite.getActionFactory();
        LanguageObject languageObject = this.languageObject;
        sequence.addAction(actionFactory.createSetListeningLanguageAction(languageObject != null ? languageObject.getLanguageTag() : null));
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        ArrayList<String> SPEECH_RECOGNITION_SUPPORTED_LANGUAGES = Utils.SPEECH_RECOGNITION_SUPPORTED_LANGUAGES;
        Intrinsics.checkNotNullExpressionValue(SPEECH_RECOGNITION_SUPPORTED_LANGUAGES, "SPEECH_RECOGNITION_SUPPORTED_LANGUAGES");
        for (String str : SPEECH_RECOGNITION_SUPPORTED_LANGUAGES) {
            Locale localeForLanguageTag = Locale.forLanguageTag(str);
            String displayName = localeForLanguageTag.getDisplayName(localeForLanguageTag);
            Intrinsics.checkNotNull(displayName);
            Intrinsics.checkNotNull(str);
            arrayList.add(new LanguageObject(displayName, str));
        }
        BrickSpinner<LanguageObject> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_set_listening_language_spinner), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        brickSpinner.setSelection(this.languageObject);
        View view = this.view;
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, LanguageObject item) {
        this.languageObject = item;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(Brick.ResourcesSet requiredResourcesSet) {
        Intrinsics.checkNotNullParameter(requiredResourcesSet, "requiredResourcesSet");
        requiredResourcesSet.add((Integer) 23);
        super.addRequiredResources(requiredResourcesSet);
    }
}
