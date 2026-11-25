package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/CallableNativeLunoFunction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CallableNativeLunoFunction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012<\u0010\u0006\u001a8\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\u0002\u0012\u0004\b\b(\n\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\f\b\t\u0012\b\b\u0002\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\f0\u0007¢\u0006\u0002\u0010\u000eJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J?\u0010\u0017\u001a8\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\u0002\u0012\u0004\b\b(\n\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\f\b\t\u0012\b\b\u0002\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\f0\u0007HÆ\u0003J]\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052>\b\u0002\u0010\u0006\u001a8\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\u0002\u0012\u0004\b\b(\n\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\f\b\t\u0012\b\b\u0002\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\f0\u0007HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010RG\u0010\u0006\u001a8\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\u0002\u0012\u0004\b\b(\n\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\f\b\t\u0012\b\b\u0002\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\f0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/CallableNativeLunoFunction;", "", "name", "", "arity", "Lkotlin/ranges/IntRange;", "function", "Lkotlin/Function2;", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "Lkotlin/ParameterName;", "interpreter", "", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "arguments", "(Ljava/lang/String;Lkotlin/ranges/IntRange;Lkotlin/jvm/functions/Function2;)V", "getArity", "()Lkotlin/ranges/IntRange;", "getFunction", "()Lkotlin/jvm/functions/Function2;", "getName", "()Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CallableNativeLunoFunction {
    private final IntRange arity;
    private final Function2<Interpreter, List<? extends LunoValue>, LunoValue> function;
    private final String name;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CallableNativeLunoFunction copy$default(CallableNativeLunoFunction callableNativeLunoFunction, String str, IntRange intRange, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = callableNativeLunoFunction.name;
        }
        if ((i & 2) != 0) {
            intRange = callableNativeLunoFunction.arity;
        }
        if ((i & 4) != 0) {
            function2 = callableNativeLunoFunction.function;
        }
        return callableNativeLunoFunction.copy(str, intRange, function2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final IntRange getArity() {
        return this.arity;
    }

    public final Function2<Interpreter, List<? extends LunoValue>, LunoValue> component3() {
        return this.function;
    }

    public final CallableNativeLunoFunction copy(String name2, IntRange arity, Function2<? super Interpreter, ? super List<? extends LunoValue>, ? extends LunoValue> function) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(arity, "arity");
        Intrinsics.checkNotNullParameter(function, "function");
        return new CallableNativeLunoFunction(name2, arity, function);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CallableNativeLunoFunction)) {
            return false;
        }
        CallableNativeLunoFunction callableNativeLunoFunction = (CallableNativeLunoFunction) other;
        return Intrinsics.areEqual(this.name, callableNativeLunoFunction.name) && Intrinsics.areEqual(this.arity, callableNativeLunoFunction.arity) && Intrinsics.areEqual(this.function, callableNativeLunoFunction.function);
    }

    public int hashCode() {
        return (((this.name.hashCode() * 31) + this.arity.hashCode()) * 31) + this.function.hashCode();
    }

    public String toString() {
        return "CallableNativeLunoFunction(name=" + this.name + ", arity=" + this.arity + ", function=" + this.function + Constant.AFTER_QUTO;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CallableNativeLunoFunction(String name2, IntRange arity, Function2<? super Interpreter, ? super List<? extends LunoValue>, ? extends LunoValue> function) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(arity, "arity");
        Intrinsics.checkNotNullParameter(function, "function");
        this.name = name2;
        this.arity = arity;
        this.function = function;
    }

    public final String getName() {
        return this.name;
    }

    public final IntRange getArity() {
        return this.arity;
    }

    public final Function2<Interpreter, List<? extends LunoValue>, LunoValue> getFunction() {
        return this.function;
    }
}
