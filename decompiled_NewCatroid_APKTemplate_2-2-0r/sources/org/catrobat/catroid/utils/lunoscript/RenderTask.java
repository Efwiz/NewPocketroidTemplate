package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/RenderTask.class
  classes14.dex
 */
/* JADX INFO: compiled from: RenderManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/RenderTask;", "", "function", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoFunction;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "(Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoFunction;Lorg/catrobat/catroid/utils/lunoscript/Interpreter;)V", "getFunction", "()Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoFunction;", "getInterpreter", "()Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class RenderTask {
    private final LunoValue.LunoFunction function;
    private final Interpreter interpreter;

    public static /* synthetic */ RenderTask copy$default(RenderTask renderTask, LunoValue.LunoFunction lunoFunction, Interpreter interpreter, int i, Object obj) {
        if ((i & 1) != 0) {
            lunoFunction = renderTask.function;
        }
        if ((i & 2) != 0) {
            interpreter = renderTask.interpreter;
        }
        return renderTask.copy(lunoFunction, interpreter);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final LunoValue.LunoFunction getFunction() {
        return this.function;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Interpreter getInterpreter() {
        return this.interpreter;
    }

    public final RenderTask copy(LunoValue.LunoFunction function, Interpreter interpreter) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        return new RenderTask(function, interpreter);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RenderTask)) {
            return false;
        }
        RenderTask renderTask = (RenderTask) other;
        return Intrinsics.areEqual(this.function, renderTask.function) && Intrinsics.areEqual(this.interpreter, renderTask.interpreter);
    }

    public int hashCode() {
        return (this.function.hashCode() * 31) + this.interpreter.hashCode();
    }

    public String toString() {
        return "RenderTask(function=" + this.function + ", interpreter=" + this.interpreter + Constant.AFTER_QUTO;
    }

    public RenderTask(LunoValue.LunoFunction function, Interpreter interpreter) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        this.function = function;
        this.interpreter = interpreter;
    }

    public final LunoValue.LunoFunction getFunction() {
        return this.function;
    }

    public final Interpreter getInterpreter() {
        return this.interpreter;
    }
}
