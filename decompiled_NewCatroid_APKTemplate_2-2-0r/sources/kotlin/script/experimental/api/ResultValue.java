package kotlin.script.experimental.api;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\n\u000b\f\rB#\b\u0004\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u0082\u0001\u0004\u000e\u000f\u0010\u0011¨\u0006\u0012"}, d2 = {"Lkotlin/script/experimental/api/ResultValue;", "", "scriptClass", "Lkotlin/reflect/KClass;", "scriptInstance", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)V", "getScriptClass", "()Lkotlin/reflect/KClass;", "getScriptInstance", "()Ljava/lang/Object;", "Error", "NotEvaluated", "Unit", "Value", "Lkotlin/script/experimental/api/ResultValue$Error;", "Lkotlin/script/experimental/api/ResultValue$NotEvaluated;", "Lkotlin/script/experimental/api/ResultValue$Unit;", "Lkotlin/script/experimental/api/ResultValue$Value;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResultValue {
    private final KClass<?> scriptClass;
    private final Object scriptInstance;

    public /* synthetic */ ResultValue(KClass kClass, Object obj, DefaultConstructorMarker defaultConstructorMarker) {
        this(kClass, obj);
    }

    private ResultValue(KClass<?> kClass, Object obj) {
        this.scriptClass = kClass;
        this.scriptInstance = obj;
    }

    public /* synthetic */ ResultValue(KClass kClass, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : kClass, (i & 2) != 0 ? null : obj, null);
    }

    public final KClass<?> getScriptClass() {
        return this.scriptClass;
    }

    public final Object getScriptInstance() {
        return this.scriptInstance;
    }

    /* JADX INFO: compiled from: scriptEvaluation.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\nJ\b\u0010\u0010\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lkotlin/script/experimental/api/ResultValue$Value;", "Lkotlin/script/experimental/api/ResultValue;", "name", "", "value", "", ModuleXmlParser.TYPE, "scriptClass", "Lkotlin/reflect/KClass;", "scriptInstance", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Lkotlin/reflect/KClass;Ljava/lang/Object;)V", "getName", "()Ljava/lang/String;", "getType", "getValue", "()Ljava/lang/Object;", "toString", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Value extends ResultValue {
        private final String name;
        private final String type;
        private final Object value;

        public final String getName() {
            return this.name;
        }

        public final String getType() {
            return this.type;
        }

        public final Object getValue() {
            return this.value;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Value(String name2, Object obj, String type, KClass<?> kClass, Object obj2) {
            super(kClass, obj2, null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(type, "type");
            this.name = name2;
            this.value = obj;
            this.type = type;
        }

        public String toString() {
            return this.name + ": " + this.type + " = " + this.value;
        }
    }

    /* JADX INFO: compiled from: scriptEvaluation.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lkotlin/script/experimental/api/ResultValue$Unit;", "Lkotlin/script/experimental/api/ResultValue;", "scriptClass", "Lkotlin/reflect/KClass;", "scriptInstance", "", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)V", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Unit extends ResultValue {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Unit(KClass<?> scriptClass, Object obj) {
            super(scriptClass, obj, null);
            Intrinsics.checkNotNullParameter(scriptClass, "scriptClass");
        }

        public String toString() {
            return "Unit";
        }
    }

    /* JADX INFO: compiled from: scriptEvaluation.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\r"}, d2 = {"Lkotlin/script/experimental/api/ResultValue$Error;", "Lkotlin/script/experimental/api/ResultValue;", "error", "", "wrappingException", "scriptClass", "Lkotlin/reflect/KClass;", "(Ljava/lang/Throwable;Ljava/lang/Throwable;Lkotlin/reflect/KClass;)V", "getError", "()Ljava/lang/Throwable;", "getWrappingException", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Error extends ResultValue {
        private final Throwable error;
        private final Throwable wrappingException;

        /* JADX WARN: Illegal instructions before constructor call */
        public Error(Throwable error, Throwable th, KClass<?> kClass) {
            Intrinsics.checkNotNullParameter(error, "error");
            DefaultConstructorMarker defaultConstructorMarker = null;
            super(kClass, defaultConstructorMarker, 2, defaultConstructorMarker);
            this.error = error;
            this.wrappingException = th;
        }

        public /* synthetic */ Error(Throwable th, Throwable th2, KClass kClass, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(th, (i & 2) != 0 ? null : th2, (i & 4) != 0 ? null : kClass);
        }

        public final Throwable getError() {
            return this.error;
        }

        public final Throwable getWrappingException() {
            return this.wrappingException;
        }

        public String toString() {
            return this.error.toString();
        }
    }

    /* JADX INFO: compiled from: scriptEvaluation.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lkotlin/script/experimental/api/ResultValue$NotEvaluated;", "Lkotlin/script/experimental/api/ResultValue;", "()V", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotEvaluated extends ResultValue {
        public static final NotEvaluated INSTANCE = new NotEvaluated();

        /* JADX WARN: Multi-variable type inference failed */
        private NotEvaluated() {
            super(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }
    }
}
