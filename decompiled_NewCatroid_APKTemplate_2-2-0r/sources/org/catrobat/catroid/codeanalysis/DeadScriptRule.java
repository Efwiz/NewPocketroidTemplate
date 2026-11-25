package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastBrick;
import org.catrobat.catroid.content.bricks.BroadcastReceiverBrick;
import org.catrobat.catroid.content.bricks.BroadcastWaitBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/DeadScriptRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: DeadScriptRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/DeadScriptRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context2", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeadScriptRule implements AnalysisRule {
    private final Context context2;

    public DeadScriptRule(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context2");
        this.context2 = context2;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context) {
        String broadcastMessage;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context, "context");
        if (brick instanceof BroadcastReceiverBrick) {
            String broadcastMessage2 = ((BroadcastReceiverBrick) brick).getBroadcastMessage();
            if (broadcastMessage2 == null || StringsKt.isBlank(broadcastMessage2)) {
                return null;
            }
            if (!context.getAllBroadcastsSent().contains(broadcastMessage2)) {
                Severity severity = Severity.WARNING;
                String string = this.context2.getString(R.string.analysis_dead_broadcast, broadcastMessage2);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                return new AnalysisResult(severity, string);
            }
        }
        boolean z = brick instanceof BroadcastBrick;
        if (z || (brick instanceof BroadcastWaitBrick)) {
            if (z) {
                broadcastMessage = ((BroadcastBrick) brick).getBroadcastMessage();
            } else {
                broadcastMessage = brick instanceof BroadcastWaitBrick ? ((BroadcastWaitBrick) brick).getBroadcastMessage() : null;
            }
            if (broadcastMessage == null || StringsKt.isBlank(broadcastMessage)) {
                return null;
            }
            if (!context.getVariablesRead().contains(broadcastMessage)) {
                Set<String> allBroadcastsSent = context.getAllBroadcastsSent();
                if (!(allBroadcastsSent instanceof Collection) || !allBroadcastsSent.isEmpty()) {
                    Iterator<T> it = allBroadcastsSent.iterator();
                    while (it.hasNext() && !Intrinsics.areEqual((String) it.next(), broadcastMessage)) {
                    }
                }
            }
            context.getAllBroadcastsSent().contains(broadcastMessage);
            if (!context.getAllBroadcastsReceived().contains(broadcastMessage)) {
                Severity severity2 = Severity.WARNING;
                String string2 = this.context2.getString(R.string.analysis_unused_broadcast_sent, broadcastMessage);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                return new AnalysisResult(severity2, string2);
            }
        }
        return null;
    }
}
