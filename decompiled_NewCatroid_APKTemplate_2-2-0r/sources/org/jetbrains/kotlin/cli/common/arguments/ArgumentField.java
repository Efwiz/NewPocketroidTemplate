package org.jetbrains.kotlin.cli.common.arguments;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: parseCommandLineArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentField;", "", "getter", "Ljava/lang/reflect/Method;", "setter", "argument", "Lorg/jetbrains/kotlin/cli/common/arguments/Argument;", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Lorg/jetbrains/kotlin/cli/common/arguments/Argument;)V", "getArgument", "()Lorg/jetbrains/kotlin/cli/common/arguments/Argument;", "getGetter", "()Ljava/lang/reflect/Method;", "getSetter", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class ArgumentField {
    private final Argument argument;
    private final Method getter;
    private final Method setter;

    public static /* synthetic */ ArgumentField copy$default(ArgumentField argumentField, Method method, Method method2, Argument argument, int i, Object obj) {
        if ((i & 1) != 0) {
            method = argumentField.getter;
        }
        if ((i & 2) != 0) {
            method2 = argumentField.setter;
        }
        if ((i & 4) != 0) {
            argument = argumentField.argument;
        }
        return argumentField.copy(method, method2, argument);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Method getGetter() {
        return this.getter;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Method getSetter() {
        return this.setter;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Argument getArgument() {
        return this.argument;
    }

    public final ArgumentField copy(Method getter, Method setter, Argument argument) {
        Intrinsics.checkNotNullParameter(getter, "getter");
        Intrinsics.checkNotNullParameter(setter, "setter");
        Intrinsics.checkNotNullParameter(argument, "argument");
        return new ArgumentField(getter, setter, argument);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ArgumentField)) {
            return false;
        }
        ArgumentField argumentField = (ArgumentField) other;
        return Intrinsics.areEqual(this.getter, argumentField.getter) && Intrinsics.areEqual(this.setter, argumentField.setter) && Intrinsics.areEqual(this.argument, argumentField.argument);
    }

    public int hashCode() {
        return (((this.getter.hashCode() * 31) + this.setter.hashCode()) * 31) + this.argument.hashCode();
    }

    public String toString() {
        return "ArgumentField(getter=" + this.getter + ", setter=" + this.setter + ", argument=" + this.argument + ')';
    }

    public ArgumentField(Method getter, Method setter, Argument argument) {
        Intrinsics.checkNotNullParameter(getter, "getter");
        Intrinsics.checkNotNullParameter(setter, "setter");
        Intrinsics.checkNotNullParameter(argument, "argument");
        this.getter = getter;
        this.setter = setter;
        this.argument = argument;
    }

    public final Argument getArgument() {
        return this.argument;
    }

    public final Method getGetter() {
        return this.getter;
    }

    public final Method getSetter() {
        return this.setter;
    }
}
