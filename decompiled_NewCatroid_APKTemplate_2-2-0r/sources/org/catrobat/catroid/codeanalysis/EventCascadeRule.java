package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastBrick;
import org.catrobat.catroid.content.bricks.BroadcastWaitBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/EventCascadeRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: EventCascadeRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/EventCascadeRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context2", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EventCascadeRule implements AnalysisRule {
    private final Context context;

    public EventCascadeRule(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context2) {
        String broadcastMessage;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context2, "context2");
        boolean z = brick instanceof BroadcastBrick;
        if (!z && !(brick instanceof BroadcastWaitBrick)) {
            return null;
        }
        if (z) {
            broadcastMessage = ((BroadcastBrick) brick).getBroadcastMessage();
        } else {
            broadcastMessage = brick instanceof BroadcastWaitBrick ? ((BroadcastWaitBrick) brick).getBroadcastMessage() : null;
        }
        if (broadcastMessage == null) {
            return null;
        }
        Script script = brick.getScript();
        if (!(script instanceof BroadcastScript) || !Intrinsics.areEqual(((BroadcastScript) script).getBroadcastMessage(), broadcastMessage)) {
            return null;
        }
        Severity severity = Severity.ERROR;
        String string = this.context.getString(R.string.analysis_event_cascade, broadcastMessage);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return new AnalysisResult(severity, string);
    }
}
