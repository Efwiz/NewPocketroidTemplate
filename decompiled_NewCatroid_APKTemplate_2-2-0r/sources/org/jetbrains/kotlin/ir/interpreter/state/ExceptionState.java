package org.jetbrains.kotlin.ir.interpreter.state;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.stack.Variable;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.koin.core.instance.InstanceFactory;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ExceptionState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\f\b\u0000\u0018\u0000 E2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001EB\u0017\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB-\b\u0016\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\rB5\b\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0016\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fj\u0002`\u0012\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\u0013J\u000e\u00104\u001a\u0002052\u0006\u00106\u001a\u00020,J\u0006\u00107\u001a\u00020\fJ\u0006\u00108\u001a\u00020\fJ\u000e\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020\u0005J\b\u0010<\u001a\u000205H\u0002J\u0012\u0010=\u001a\u0002052\b\u0010>\u001a\u0004\u0018\u00010\u0011H\u0002J\u0018\u0010?\u001a\u0002052\u0006\u0010@\u001a\u00020\u00102\u0006\u0010A\u001a\u00020\u0011H\u0016J\u0012\u0010B\u001a\u0002052\b\u0010C\u001a\u0004\u0018\u00010\fH\u0002J\b\u0010D\u001a\u00020\fH\u0016R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\f0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fj\u0002`\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0016\u0010 \u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010$\u001a\u0016\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0018\u00010%j\u0004\u0018\u0001`&X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R \u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u0002020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b3\u0010\u001d¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/ExceptionState;", "Lorg/jetbrains/kotlin/ir/interpreter/state/Complex;", "Lorg/jetbrains/kotlin/ir/interpreter/state/StateWithClosure;", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;)V", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, "stackTrace", "", "", "(Ljava/lang/Throwable;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/util/List;Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;)V", "fields", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Fields;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/util/Map;Ljava/util/List;)V", "cause", "getCause", "()Lorg/jetbrains/kotlin/ir/interpreter/state/ExceptionState;", "causeProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "exceptionFqName", "exceptionHierarchy", "", "getFields", "()Ljava/util/Map;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", CrashHianalyticsData.MESSAGE, "getMessage", "()Ljava/lang/String;", "messageProperty", "outerClass", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Field;", "getOuterClass", "()Lkotlin/Pair;", "setOuterClass", "(Lkotlin/Pair;)V", "superWrapperClass", "Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;", "getSuperWrapperClass", "()Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;", "setSuperWrapperClass", "(Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;)V", "upValues", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Variable;", "getUpValues", "copyFieldsFrom", "", "wrapper", "getFullDescription", "getShortDescription", "isSubtypeOf", "", "ancestor", "recalculateCauseAndMessage", "setCause", "causeValue", "setField", NativeSymbol.TYPE_NAME, "state", "setMessage", "messageValue", "toString", "Companion", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExceptionState extends Throwable implements Complex, StateWithClosure {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final IrProperty causeProperty;
    private String exceptionFqName;
    private final List<String> exceptionHierarchy;
    private final Map<IrSymbol, State> fields;
    private final IrClass irClass;
    private final IrProperty messageProperty;
    private Pair<? extends IrSymbol, ? extends State> outerClass;
    private final List<String> stackTrace;
    private Wrapper superWrapperClass;
    private final Map<IrSymbol, Variable> upValues;

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public Map<IrSymbol, State> getFields() {
        return this.fields;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    private ExceptionState(IrClass irClass, Map<IrSymbol, State> map, List<String> list) {
        this.irClass = irClass;
        this.fields = map;
        this.upValues = new LinkedHashMap();
        this.exceptionHierarchy = new ArrayList();
        IrProperty originalPropertyByName = UtilsKt.getOriginalPropertyByName(getIrClass(), CrashHianalyticsData.MESSAGE);
        this.messageProperty = originalPropertyByName;
        IrProperty originalPropertyByName2 = UtilsKt.getOriginalPropertyByName(getIrClass(), "cause");
        this.causeProperty = originalPropertyByName2;
        this.stackTrace = CollectionsKt.reversed(list);
        if (this.exceptionFqName == null) {
            this.exceptionFqName = irClassFqName();
        }
        if (!getFields().containsKey(originalPropertyByName.getSymbol())) {
            setMessage(null);
        }
        if (getFields().containsKey(originalPropertyByName2.getSymbol())) {
            return;
        }
        setCause(null);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.StateWithClosure
    public Map<IrSymbol, Variable> getUpValues() {
        return this.upValues;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public Wrapper getSuperWrapperClass() {
        return this.superWrapperClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public void setSuperWrapperClass(Wrapper wrapper) {
        this.superWrapperClass = wrapper;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public Pair<IrSymbol, State> getOuterClass() {
        return this.outerClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public void setOuterClass(Pair<? extends IrSymbol, ? extends State> pair) {
        this.outerClass = pair;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        State field = getField(this.messageProperty.getSymbol());
        if (field != null) {
            return StateKt.asStringOrNull(field);
        }
        return null;
    }

    @Override // java.lang.Throwable
    public ExceptionState getCause() {
        State field = getField(this.causeProperty.getSymbol());
        if (field instanceof ExceptionState) {
            return (ExceptionState) field;
        }
        return null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ExceptionState(IrClass irClass, IrInterpreterEnvironment environment) {
        this(irClass, new LinkedHashMap(), environment.getCallStack().getStackTrace());
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(environment, "environment");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ExceptionState(Throwable exception, IrClass irClass, List<String> stackTrace, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(exception, "exception");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(stackTrace, "stackTrace");
        Intrinsics.checkNotNullParameter(environment, "environment");
        Companion companion = INSTANCE;
        this(irClass, companion.evaluateFields(exception, irClass, environment), CollectionsKt.plus((Collection) stackTrace, (Iterable) companion.evaluateAdditionalStackTrace(exception, environment)));
        String str = null;
        setCause(null);
        if (Intrinsics.areEqual(irClass.getName().asString(), exception.getClass().getSimpleName())) {
            return;
        }
        String name2 = exception.getClass().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        this.exceptionFqName = name2;
        List<String> list = this.exceptionHierarchy;
        if (name2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("exceptionFqName");
        } else {
            str = name2;
        }
        list.add(str);
        Iterator it = SequencesKt.generateSequence(exception.getClass().getSuperclass(), new Function1<Class<? extends Object>, Class<? extends Object>>() { // from class: org.jetbrains.kotlin.ir.interpreter.state.ExceptionState.1
            @Override // kotlin.jvm.functions.Function1
            public final Class<? extends Object> invoke(Class<? extends Object> it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getSuperclass();
            }
        }).iterator();
        while (it.hasNext()) {
            this.exceptionHierarchy.add(((Class) it.next()).getName());
        }
        List<String> list2 = this.exceptionHierarchy;
        list2.remove(CollectionsKt.getLastIndex(list2));
    }

    public final void copyFieldsFrom(Wrapper wrapper) {
        Intrinsics.checkNotNullParameter(wrapper, "wrapper");
        Object value = wrapper.getValue();
        Throwable th = value instanceof Throwable ? (Throwable) value : null;
        if (th != null) {
            setMessage(th.getMessage());
            Throwable cause = th.getCause();
            setCause(cause instanceof ExceptionState ? (ExceptionState) cause : null);
        }
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public void setField(IrSymbol symbol, State state) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(state, "state");
        super.setField(symbol, state);
        recalculateCauseAndMessage();
    }

    private final void recalculateCauseAndMessage() {
        String str;
        if (getMessage() != null || getCause() == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        ExceptionState cause = getCause();
        Intrinsics.checkNotNull(cause);
        String str2 = cause.exceptionFqName;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("exceptionFqName");
            str2 = null;
        }
        StringBuilder sbAppend = sb.append(str2);
        ExceptionState cause2 = getCause();
        Intrinsics.checkNotNull(cause2);
        String message = cause2.getMessage();
        if (message == null || (str = ": " + message) == null) {
            str = "";
        }
        setMessage(sbAppend.append(str).toString());
    }

    public final boolean isSubtypeOf(IrClass ancestor) {
        Intrinsics.checkNotNullParameter(ancestor, "ancestor");
        if (!this.exceptionHierarchy.isEmpty()) {
            List<String> list = this.exceptionHierarchy;
            if ((list instanceof Collection) && list.isEmpty()) {
                return false;
            }
            for (String str : list) {
                String strAsString = ancestor.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                if (StringsKt.contains$default((CharSequence) str, (CharSequence) strAsString, false, 2, (Object) null)) {
                    return true;
                }
            }
            return false;
        }
        return IrUtilsKt.isSubclassOf(getIrClass(), ancestor);
    }

    private final void setMessage(String messageValue) {
        IrPropertySymbol symbol = this.messageProperty.getSymbol();
        IrSimpleFunction getter = this.messageProperty.getGetter();
        Intrinsics.checkNotNull(getter);
        setField(symbol, new Primitive(messageValue, getter.getReturnType()));
    }

    private final void setCause(State causeValue) {
        IrPropertySymbol symbol = this.causeProperty.getSymbol();
        if (causeValue == null) {
            Primitive.Companion companion = Primitive.INSTANCE;
            IrSimpleFunction getter = this.causeProperty.getGetter();
            Intrinsics.checkNotNull(getter);
            causeValue = companion.nullStateOfType(getter.getReturnType());
        }
        setField(symbol, causeValue);
    }

    public final String getShortDescription() {
        String message = getMessage();
        return (message == null || message.length() <= 0) ? "???" : message;
    }

    public final String getFullDescription() {
        String fullDescription;
        String strReplaceFirst$default;
        String message = getMessage();
        String str = "";
        String str2 = (message == null || message.length() <= 0) ? "" : ": " + message;
        CharSequence charSequence = !this.stackTrace.isEmpty() ? InstanceFactory.ERROR_SEPARATOR : "";
        String str3 = this.stackTrace.size() > 10 ? "\n\t..." : "";
        ExceptionState cause = getCause();
        if (cause != null && (fullDescription = cause.getFullDescription()) != null && (strReplaceFirst$default = StringsKt.replaceFirst$default(fullDescription, "Exception ", "\nCaused by: ", false, 4, (Object) null)) != null) {
            str = strReplaceFirst$default;
        }
        StringBuilder sb = new StringBuilder("Exception ");
        String str4 = this.exceptionFqName;
        if (str4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("exceptionFqName");
            str4 = null;
        }
        StringBuilder sbAppend = sb.append(str4).append(str2);
        List<String> list = this.stackTrace;
        return sbAppend.append(CollectionsKt.joinToString$default(list.subList(0, Math.min(list.size(), 10)), InstanceFactory.ERROR_SEPARATOR, charSequence, str3, 0, null, null, 56, null)).append(str).toString();
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002e  */
    /* JADX WARN: Code duplicated, block: B:9:0x002a  */
    @Override // java.lang.Throwable
    public String toString() {
        String string;
        String message = getMessage();
        if (message != null) {
            StringBuilder sb = new StringBuilder();
            String str = this.exceptionFqName;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("exceptionFqName");
                str = null;
            }
            string = sb.append(str).append(": ").append(message).toString();
            if (string == null) {
                string = this.exceptionFqName;
                if (string == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("exceptionFqName");
                    return null;
                }
            }
        } else {
            string = this.exceptionFqName;
            if (string == null) {
                Intrinsics.throwUninitializedPropertyAccessException("exceptionFqName");
                return null;
            }
        }
        return string;
    }

    /* JADX INFO: compiled from: ExceptionState.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J0\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/ExceptionState$Companion;", "", "()V", "evaluateAdditionalStackTrace", "", "", "e", "", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "evaluateFields", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Fields;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Map<IrSymbol, State> evaluateFields(Throwable exception, IrClass irClass, IrInterpreterEnvironment environment) {
            Pair pair;
            Map<IrSymbol, State> mapMutableMapOf;
            List<String> stackTrace = environment.getCallStack().getStackTrace();
            IrProperty originalPropertyByName = UtilsKt.getOriginalPropertyByName(irClass, CrashHianalyticsData.MESSAGE);
            IrProperty originalPropertyByName2 = UtilsKt.getOriginalPropertyByName(irClass, "cause");
            IrPropertySymbol symbol = originalPropertyByName.getSymbol();
            String message = exception.getMessage();
            IrSimpleFunction getter = originalPropertyByName.getGetter();
            Intrinsics.checkNotNull(getter);
            Pair pair2 = TuplesKt.to(symbol, new Primitive(message, getter.getReturnType()));
            Throwable cause = exception.getCause();
            if (cause != null) {
                IrPropertySymbol symbol2 = originalPropertyByName2.getSymbol();
                List<String> list = stackTrace;
                StackTraceElement[] stackTrace2 = cause.getStackTrace();
                Intrinsics.checkNotNullExpressionValue(stackTrace2, "getStackTrace(...)");
                List listReversed = ArraysKt.reversed(stackTrace2);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listReversed, 10));
                Iterator it = listReversed.iterator();
                while (it.hasNext()) {
                    arrayList.add("at " + ((StackTraceElement) it.next()));
                }
                pair = TuplesKt.to(symbol2, new ExceptionState(cause, irClass, CollectionsKt.plus((Collection) list, (Iterable) arrayList), environment));
            } else {
                pair = null;
            }
            return (pair == null || (mapMutableMapOf = MapsKt.mutableMapOf(pair2, pair)) == null) ? MapsKt.mutableMapOf(pair2) : mapMutableMapOf;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<String> evaluateAdditionalStackTrace(Throwable e, IrInterpreterEnvironment environment) {
            ArrayList arrayList = new ArrayList();
            StackTraceElement[] stackTrace = e.getStackTrace();
            Intrinsics.checkNotNullExpressionValue(stackTrace, "getStackTrace(...)");
            for (StackTraceElement stackTraceElement : stackTrace) {
                if (Intrinsics.areEqual(stackTraceElement.getClassName(), "java.lang.invoke.MethodHandle")) {
                    StackTraceElement[] stackTrace2 = e.getStackTrace();
                    Intrinsics.checkNotNullExpressionValue(stackTrace2, "getStackTrace(...)");
                    int length = stackTrace2.length;
                    for (int i = 0; i < length; i++) {
                        if (Intrinsics.areEqual(stackTrace2[i].getMethodName(), "invokeWithArguments")) {
                            StackTraceElement[] stackTrace3 = e.getStackTrace();
                            Intrinsics.checkNotNullExpressionValue(stackTrace3, "getStackTrace(...)");
                            List listReversed = CollectionsKt.reversed(ArraysKt.slice((Object[]) stackTrace3, RangesKt.until(0, i)));
                            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listReversed, 10));
                            Iterator it = listReversed.iterator();
                            while (it.hasNext()) {
                                arrayList2.add("at " + ((StackTraceElement) it.next()));
                            }
                            arrayList.addAll(arrayList2);
                            break;
                        }
                    }
                    StackTraceElement[] stackTrace4 = e.getStackTrace();
                    Intrinsics.checkNotNullExpressionValue(stackTrace4, "getStackTrace(...)");
                    StackTraceElement stackTraceElement2 = (StackTraceElement) ArraysKt.first(stackTrace4);
                    String str = stackTraceElement2.getClassName() + '.' + stackTraceElement2.getMethodName();
                    for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
                        StackTraceElement[] stackTrace5 = cause.getStackTrace();
                        Intrinsics.checkNotNullExpressionValue(stackTrace5, "getStackTrace(...)");
                        int length2 = stackTrace5.length;
                        for (int i2 = 0; i2 < length2; i2++) {
                            StackTraceElement stackTraceElement3 = stackTrace5[i2];
                            if (Intrinsics.areEqual(stackTraceElement3.getClassName() + '.' + stackTraceElement3.getMethodName(), str)) {
                                StackTraceElement[] stackTrace6 = cause.getStackTrace();
                                Intrinsics.checkNotNullExpressionValue(stackTrace6, "getStackTrace(...)");
                                cause.setStackTrace((StackTraceElement[]) ArraysKt.reversedArray(ArraysKt.sliceArray(stackTrace6, RangesKt.until(0, i2))));
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            return (!environment.getConfiguration().getCollapseStackTraceFromJDK() || arrayList.isEmpty()) ? arrayList : CollectionsKt.listOf("at <JDK>");
        }
    }
}
