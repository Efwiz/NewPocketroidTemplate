package org.catrobat.paintroid.tools.implementation;

import kotlin.Metadata;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolReference;

/* JADX INFO: compiled from: DefaultToolReference.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/DefaultToolReference;", "Lorg/catrobat/paintroid/tools/ToolReference;", "()V", "tool", "Lorg/catrobat/paintroid/tools/Tool;", "getTool", "()Lorg/catrobat/paintroid/tools/Tool;", "setTool", "(Lorg/catrobat/paintroid/tools/Tool;)V", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultToolReference implements ToolReference {
    private Tool tool;

    @Override // org.catrobat.paintroid.tools.ToolReference
    public Tool getTool() {
        return this.tool;
    }

    @Override // org.catrobat.paintroid.tools.ToolReference
    public void setTool(Tool tool) {
        this.tool = tool;
    }
}
