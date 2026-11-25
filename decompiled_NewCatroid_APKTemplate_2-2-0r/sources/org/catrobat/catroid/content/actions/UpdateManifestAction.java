package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.apkbuild.ApkToolboxManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/UpdateManifestAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: UpdateManifestAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001c\u0010'\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\b¨\u0006."}, d2 = {"Lorg/catrobat/catroid/content/actions/UpdateManifestAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "apkPathFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getApkPathFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setApkPathFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "appNameFormula", "getAppNameFormula", "setAppNameFormula", "debuggableFormula", "getDebuggableFormula", "setDebuggableFormula", "minSdkFormula", "getMinSdkFormula", "setMinSdkFormula", "packageNameFormula", "getPackageNameFormula", "setPackageNameFormula", "permsAddFormula", "getPermsAddFormula", "setPermsAddFormula", "permsRemoveFormula", "getPermsRemoveFormula", "setPermsRemoveFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "targetSdkFormula", "getTargetSdkFormula", "setTargetSdkFormula", "versionCodeFormula", "getVersionCodeFormula", "setVersionCodeFormula", "versionNameFormula", "getVersionNameFormula", "setVersionNameFormula", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UpdateManifestAction extends TemporalAction {
    private Formula apkPathFormula;
    private Formula appNameFormula;
    private Formula debuggableFormula;
    private Formula minSdkFormula;
    private Formula packageNameFormula;
    private Formula permsAddFormula;
    private Formula permsRemoveFormula;
    private Scope scope;
    private Formula targetSdkFormula;
    private Formula versionCodeFormula;
    private Formula versionNameFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getApkPathFormula() {
        return this.apkPathFormula;
    }

    public final void setApkPathFormula(Formula formula) {
        this.apkPathFormula = formula;
    }

    public final Formula getPackageNameFormula() {
        return this.packageNameFormula;
    }

    public final void setPackageNameFormula(Formula formula) {
        this.packageNameFormula = formula;
    }

    public final Formula getAppNameFormula() {
        return this.appNameFormula;
    }

    public final void setAppNameFormula(Formula formula) {
        this.appNameFormula = formula;
    }

    public final Formula getVersionCodeFormula() {
        return this.versionCodeFormula;
    }

    public final void setVersionCodeFormula(Formula formula) {
        this.versionCodeFormula = formula;
    }

    public final Formula getVersionNameFormula() {
        return this.versionNameFormula;
    }

    public final void setVersionNameFormula(Formula formula) {
        this.versionNameFormula = formula;
    }

    public final Formula getMinSdkFormula() {
        return this.minSdkFormula;
    }

    public final void setMinSdkFormula(Formula formula) {
        this.minSdkFormula = formula;
    }

    public final Formula getTargetSdkFormula() {
        return this.targetSdkFormula;
    }

    public final void setTargetSdkFormula(Formula formula) {
        this.targetSdkFormula = formula;
    }

    public final Formula getDebuggableFormula() {
        return this.debuggableFormula;
    }

    public final void setDebuggableFormula(Formula formula) {
        this.debuggableFormula = formula;
    }

    public final Formula getPermsAddFormula() {
        return this.permsAddFormula;
    }

    public final void setPermsAddFormula(Formula formula) {
        this.permsAddFormula = formula;
    }

    public final Formula getPermsRemoveFormula() {
        return this.permsRemoveFormula;
    }

    public final void setPermsRemoveFormula(Formula formula) {
        this.permsRemoveFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Project project;
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null) {
            return;
        }
        Formula formula = this.apkPathFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        File file = project.getFile(strInterpretString);
        if (file == null || !file.exists()) {
            Log.e("ManifestAction", "APK not found: " + strInterpretString);
            return;
        }
        ApkToolboxManager.ManifestConfig manifestConfig = new ApkToolboxManager.ManifestConfig(update$getStr(this, this.appNameFormula), update$getStr(this, this.packageNameFormula), update$getInt(this, this.versionCodeFormula), update$getStr(this, this.versionNameFormula), update$getInt(this, this.minSdkFormula), update$getInt(this, this.targetSdkFormula), update$getList(this, this.permsAddFormula), update$getList(this, this.permsRemoveFormula), update$getBool(this, this.debuggableFormula));
        ApkToolboxManager apkToolboxManager = ApkToolboxManager.INSTANCE;
        String absolutePath = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        apkToolboxManager.updateManifest(absolutePath, manifestConfig);
    }

    private static final String update$getStr(UpdateManifestAction updateManifestAction, Formula formula) {
        String strInterpretString = formula != null ? formula.interpretString(updateManifestAction.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            return null;
        }
        return strInterpretString;
    }

    private static final Integer update$getInt(UpdateManifestAction updateManifestAction, Formula formula) {
        String strUpdate$getStr = update$getStr(updateManifestAction, formula);
        if (strUpdate$getStr != null) {
            return StringsKt.toIntOrNull(strUpdate$getStr);
        }
        return null;
    }

    private static final Boolean update$getBool(UpdateManifestAction updateManifestAction, Formula formula) {
        String strUpdate$getStr = update$getStr(updateManifestAction, formula);
        if (strUpdate$getStr != null) {
            return StringsKt.toBooleanStrictOrNull(strUpdate$getStr);
        }
        return null;
    }

    private static final List<String> update$getList(UpdateManifestAction updateManifestAction, Formula formula) {
        String strUpdate$getStr = update$getStr(updateManifestAction, formula);
        if (strUpdate$getStr == null) {
            return null;
        }
        List listSplit$default = StringsKt.split$default((CharSequence) strUpdate$getStr, new String[]{"\n"}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (((String) obj).length() > 0) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }
}
