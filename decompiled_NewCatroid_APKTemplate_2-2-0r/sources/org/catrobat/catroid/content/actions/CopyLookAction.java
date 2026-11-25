package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CopyLookAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CopyLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002J\u000e\u0010\t\u001a\u00020\u001d2\u0006\u0010\t\u001a\u00020\nR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0015\u001a\n \u0017*\u0004\u0018\u00010\u00160\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/CopyLookAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "nextLookAction", "Lorg/catrobat/catroid/content/actions/SetNextLookAction;", "getNextLookAction", "()Lorg/catrobat/catroid/content/actions/SetNextLookAction;", "setNextLookAction", "(Lorg/catrobat/catroid/content/actions/SetNextLookAction;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "xstreamSerializer", "Lorg/catrobat/catroid/io/XstreamSerializer;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "act", "", "delta", "", "copyLook", "", "copyLookFile", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CopyLookAction extends Action {
    private Formula formula;
    private SetNextLookAction nextLookAction;
    private Scope scope;
    private XstreamSerializer xstreamSerializer = XstreamSerializer.getInstance();

    public final Formula getFormula() {
        return this.formula;
    }

    public final void setFormula(Formula formula) {
        this.formula = formula;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final SetNextLookAction getNextLookAction() {
        return this.nextLookAction;
    }

    public final void setNextLookAction(SetNextLookAction setNextLookAction) {
        this.nextLookAction = setNextLookAction;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        SetNextLookAction setNextLookAction = this.nextLookAction;
        if (setNextLookAction != null) {
            setNextLookAction.setChange(0);
        }
        copyLook();
        return true;
    }

    public final void nextLookAction(SetNextLookAction nextLookAction) {
        Intrinsics.checkNotNullParameter(nextLookAction, "nextLookAction");
        this.nextLookAction = nextLookAction;
    }

    private final void copyLook() {
        try {
            copyLookFile();
        } catch (IOException e) {
            Log.e("CopyLookAction", Log.getStackTraceString(e));
        }
    }

    private final void copyLookFile() {
        Sprite sprite;
        Formula formula;
        LookData lookData;
        Scope scope = this.scope;
        if (scope == null || (sprite = scope.getSprite()) == null || (formula = this.formula) == null) {
            return;
        }
        Look look = sprite.look;
        File file = null;
        LookData lookData2 = look != null ? look.getLookData() : null;
        if (lookData2 == null) {
            return;
        }
        Look look2 = sprite.look;
        if (look2 != null && (lookData = look2.getLookData()) != null) {
            file = lookData.getFile();
        }
        if (file == null) {
            return;
        }
        String string = formula.interpretObject(this.scope).toString();
        int iIndexOf = sprite.getLookList().indexOf(sprite.look.getLookData());
        Scene currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(string, sprite.getLookList());
        Intrinsics.checkNotNullExpressionValue(uniqueNameInNameables, "getUniqueNameInNameables(...)");
        try {
            if (file.exists()) {
                LookData lookData3 = new LookData(uniqueNameInNameables, StorageOperations.duplicateFile(file));
                sprite.getLookList().add(iIndexOf + 1, lookData3);
                lookData3.getCollisionInformation().calculate();
            } else {
                File file2 = new File(new File(currentlyPlayingScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME), uniqueNameInNameables + Constants.DEFAULT_IMAGE_EXTENSION);
                file2.createNewFile();
                PixmapIO.writePNG(new FileHandle(file2), lookData2.getPixmap());
                LookData lookData4 = new LookData(uniqueNameInNameables, file2);
                sprite.getLookList().add(sprite.look.getLookListIndexBeforeLookRequest() + 1, lookData4);
                SetNextLookAction setNextLookAction = this.nextLookAction;
                if (setNextLookAction != null) {
                    setNextLookAction.setChange(1);
                }
                lookData4.getCollisionInformation().calculate();
            }
        } catch (IOException e) {
            Log.e("CopyLookAction", Log.getStackTraceString(e));
        }
        this.xstreamSerializer.saveProject(ProjectManager.getInstance().getCurrentProject());
    }
}
