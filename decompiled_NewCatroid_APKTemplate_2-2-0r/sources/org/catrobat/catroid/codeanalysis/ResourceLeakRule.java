package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.LoadNNBrick;
import org.catrobat.catroid.content.bricks.RunVMBrick;
import org.catrobat.catroid.content.bricks.RunVm2Brick;
import org.catrobat.catroid.content.bricks.StartRecordingBrick;
import org.catrobat.catroid.content.bricks.StartServerBrick;
import org.catrobat.catroid.content.bricks.StopRecordingBrick;
import org.catrobat.catroid.content.bricks.StopServerBrick;
import org.catrobat.catroid.content.bricks.StopVMBrick;
import org.catrobat.catroid.content.bricks.UnloadNNBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/ResourceLeakRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: ResourceLeakRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/ResourceLeakRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context2", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResourceLeakRule implements AnalysisRule {
    private final Context context;

    public ResourceLeakRule(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context2) {
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context2, "context2");
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        if (currentSprite == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Script> it = currentSprite.getScriptList().iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(arrayList);
        }
        boolean z = true;
        if (brick instanceof LoadNNBrick) {
            ArrayList arrayList2 = arrayList;
            if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                Iterator it2 = arrayList2.iterator();
                do {
                    if (!it2.hasNext()) {
                        z = false;
                        break;
                    }
                } while (!(((Brick) it2.next()) instanceof UnloadNNBrick));
            } else {
                z = false;
                break;
            }
            if (!z) {
                Severity severity = Severity.WARNING;
                String string = this.context.getString(R.string.analysis_leak_nn);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                return new AnalysisResult(severity, string);
            }
        } else {
            if (brick instanceof RunVMBrick ? true : brick instanceof RunVm2Brick) {
                ArrayList arrayList3 = arrayList;
                if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                    Iterator it3 = arrayList3.iterator();
                    do {
                        if (!it3.hasNext()) {
                            z = false;
                            break;
                        }
                    } while (!(((Brick) it3.next()) instanceof StopVMBrick));
                } else {
                    z = false;
                    break;
                }
                if (!z) {
                    Severity severity2 = Severity.WARNING;
                    String string2 = this.context.getString(R.string.analysis_leak_vm);
                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                    return new AnalysisResult(severity2, string2);
                }
            } else if (brick instanceof StartServerBrick) {
                ArrayList arrayList4 = arrayList;
                if (!(arrayList4 instanceof Collection) || !arrayList4.isEmpty()) {
                    Iterator it4 = arrayList4.iterator();
                    do {
                        if (!it4.hasNext()) {
                            z = false;
                            break;
                        }
                    } while (!(((Brick) it4.next()) instanceof StopServerBrick));
                } else {
                    z = false;
                    break;
                }
                if (!z) {
                    Severity severity3 = Severity.WARNING;
                    String string3 = this.context.getString(R.string.analysis_leak_server);
                    Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                    return new AnalysisResult(severity3, string3);
                }
            } else if (brick instanceof StartRecordingBrick) {
                ArrayList arrayList5 = arrayList;
                if (!(arrayList5 instanceof Collection) || !arrayList5.isEmpty()) {
                    Iterator it5 = arrayList5.iterator();
                    do {
                        if (!it5.hasNext()) {
                            z = false;
                            break;
                        }
                    } while (!(((Brick) it5.next()) instanceof StopRecordingBrick));
                } else {
                    z = false;
                    break;
                }
                if (!z) {
                    Severity severity4 = Severity.WARNING;
                    String string4 = this.context.getString(R.string.analysis_leak_recording);
                    Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                    return new AnalysisResult(severity4, string4);
                }
            }
        }
        return null;
    }
}
