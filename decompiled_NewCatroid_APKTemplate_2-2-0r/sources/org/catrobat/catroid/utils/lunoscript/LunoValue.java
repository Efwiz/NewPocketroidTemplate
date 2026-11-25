package org.catrobat.catroid.utils.lunoscript;

import android.content.Context;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.archive.PathTree;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.IntRange;
import org.catrobat.catroid.CatroidApplication;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue.class
  classes14.dex
 */
/* JADX INFO: compiled from: LunoValue.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \r2\u00020\u0001:\u0010\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0006\u001a\u00020\u00002\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b\u0082\u0001\t\u0019\u001a\u001b\u001c\u001d\u001e\u001f !¨\u0006\""}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "", "()V", "isTruthy", "", "lunoValueToKotlin", "value", "targetClass", "Ljava/lang/Class;", "Boolean", "BoundMethod", "BoundMethod2", "Callable", "Companion", "Float", "List", "LunoClass", "LunoFunction", "LunoObject", "NativeCallable", "NativeClass", "NativeObject", "Null", "Number", CommonClassNames.JAVA_LANG_STRING_SHORT, "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Boolean;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Callable;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Float;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$List;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoObject;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$NativeObject;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Null;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Number;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LunoValue {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public /* synthetic */ LunoValue(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$Null.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Null;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "()V", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Null extends LunoValue {
        public static final Null INSTANCE = new Null();

        private Null() {
            super(null);
        }

        public java.lang.String toString() {
            return "null";
        }
    }

    private LunoValue() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$Number.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Number;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "value", "", "(D)V", "getValue", "()D", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Number extends LunoValue {
        private final double value;

        public static /* synthetic */ Number copy$default(Number number, double d, int i, Object obj) {
            if ((i & 1) != 0) {
                d = number.value;
            }
            return number.copy(d);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final double getValue() {
            return this.value;
        }

        public final Number copy(double value) {
            return new Number(value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Number) && Double.compare(this.value, ((Number) other).value) == 0;
        }

        public int hashCode() {
            return Double.hashCode(this.value);
        }

        public Number(double d) {
            super(null);
            this.value = d;
        }

        public final double getValue() {
            return this.value;
        }

        public java.lang.String toString() {
            double d = this.value;
            return d == ((double) ((long) d)) ? java.lang.String.valueOf((long) d) : java.lang.String.valueOf(d);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$String.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0006\u0010\u000f\u001a\u00020\u0003J\b\u0010\u0010\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$String;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "value", "", "(Ljava/lang/String;)V", "getValue", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toLunoScriptString", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class String extends LunoValue {
        private final java.lang.String value;

        public static /* synthetic */ String copy$default(String string, java.lang.String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = string.value;
            }
            return string.copy(str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final java.lang.String getValue() {
            return this.value;
        }

        public final String copy(java.lang.String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return new String(value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof String) && Intrinsics.areEqual(this.value, ((String) other).value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public String(java.lang.String value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final java.lang.String getValue() {
            return this.value;
        }

        public java.lang.String toString() {
            return this.value;
        }

        public final java.lang.String toLunoScriptString() {
            return "\"" + this.value + "\"";
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$Boolean.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bHÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Boolean;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "value", "", "(Z)V", "getValue", "()Z", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Boolean extends LunoValue {
        private final boolean value;

        public static /* synthetic */ Boolean copy$default(Boolean r0, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                z = r0.value;
            }
            return r0.copy(z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getValue() {
            return this.value;
        }

        public final Boolean copy(boolean value) {
            return new Boolean(value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Boolean) && this.value == ((Boolean) other).value;
        }

        public int hashCode() {
            return java.lang.Boolean.hashCode(this.value);
        }

        public Boolean(boolean z) {
            super(null);
            this.value = z;
        }

        public final boolean getValue() {
            return this.value;
        }

        public java.lang.String toString() {
            return java.lang.String.valueOf(this.value);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$NativeClass.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J&\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0017J\r\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\u0017\u0010\u0012\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$NativeClass;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Callable;", "klass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "getKlass", "()Ljava/lang/Class;", "arity", "Lkotlin/ranges/IntRange;", "call", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "arguments", "", "callSiteToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class NativeClass extends Callable {
        private final Class<?> klass;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ NativeClass copy$default(NativeClass nativeClass, Class cls, int i, Object obj) {
            if ((i & 1) != 0) {
                cls = nativeClass.klass;
            }
            return nativeClass.copy(cls);
        }

        public final Class<?> component1() {
            return this.klass;
        }

        public final NativeClass copy(Class<?> klass) {
            Intrinsics.checkNotNullParameter(klass, "klass");
            return new NativeClass(klass);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof NativeClass) && Intrinsics.areEqual(this.klass, ((NativeClass) other).klass);
        }

        public int hashCode() {
            return this.klass.hashCode();
        }

        public NativeClass(Class<?> klass) {
            Intrinsics.checkNotNullParameter(klass, "klass");
            this.klass = klass;
        }

        public final Class<?> getKlass() {
            return this.klass;
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public IntRange arity() {
            return new IntRange(0, Integer.MAX_VALUE);
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public LunoValue call(Interpreter interpreter, java.util.List<? extends LunoValue> arguments, Token callSiteToken) {
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(callSiteToken, "callSiteToken");
            Constructor<?>[] constructors = this.klass.getConstructors();
            Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
            ArrayList arrayList = new ArrayList();
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == arguments.size()) {
                    arrayList.add(constructor);
                }
            }
            ArrayList<Constructor> arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                throw new LunoRuntimeError("No constructor for class '" + this.klass.getSimpleName() + "' found with " + arguments.size() + " arguments.", callSiteToken.getLine(), null, 4, null);
            }
            for (Constructor constructor2 : arrayList2) {
                try {
                    java.util.List<? extends LunoValue> list = arguments;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    int i = 0;
                    for (Object obj : list) {
                        int i2 = i + 1;
                        if (i < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        Class<?> cls = constructor2.getParameterTypes()[i];
                        Intrinsics.checkNotNull(cls);
                        arrayList3.add(interpreter.lunoValueToKotlin((LunoValue) obj, cls));
                        i = i2;
                    }
                    Object[] array = arrayList3.toArray(new Object[0]);
                    Object objNewInstance = constructor2.newInstance(Arrays.copyOf(array, array.length));
                    Intrinsics.checkNotNull(objNewInstance);
                    return new NativeObject(objNewInstance);
                } catch (LunoRuntimeError | Exception unused) {
                }
            }
            throw new LunoRuntimeError("Could not find a suitable constructor for '" + this.klass.getSimpleName() + "' with argument types: (" + CollectionsKt.joinToString$default(arguments, ", ", null, null, 0, null, new Function1<LunoValue, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.LunoValue$NativeClass$call$argTypes$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(LunoValue it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String simpleName = Reflection.getOrCreateKotlinClass(it.getClass()).getSimpleName();
                    if (simpleName == null) {
                        simpleName = "unknown";
                    }
                    return simpleName;
                }
            }, 30, null) + Constant.AFTER_QUTO, callSiteToken.getLine(), null, 4, null);
        }

        public java.lang.String toString() {
            return "<native class " + this.klass.getName() + ">";
        }
    }

    /* JADX WARN: Code duplicated, block: B:41:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:43:0x00a7  */
    /* JADX WARN: Code duplicated, block: B:44:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:46:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:56:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:58:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:59:0x00da  */
    /* JADX WARN: Code duplicated, block: B:61:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:68:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:70:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:71:0x0101  */
    /* JADX WARN: Code duplicated, block: B:73:0x0104  */
    /* JADX WARN: Code duplicated, block: B:77:0x0117  */
    /* JADX WARN: Code duplicated, block: B:79:0x011b  */
    /* JADX WARN: Code duplicated, block: B:80:0x011f  */
    /* JADX WARN: Code duplicated, block: B:82:0x0122  */
    /* JADX WARN: Code duplicated, block: B:86:0x0134  */
    /* JADX WARN: Code duplicated, block: B:88:0x0138  */
    /* JADX WARN: Code duplicated, block: B:89:0x013c  */
    /* JADX WARN: Code duplicated, block: B:91:0x013f  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final Object lunoValueToKotlin(LunoValue value, Class<?> targetClass) {
        Number number;
        Number number2;
        Boolean r2;
        Number number3;
        String string;
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(targetClass, "targetClass");
        Object objValueOf = null;
        if (value instanceof Null) {
            return null;
        }
        boolean z = value instanceof NativeObject;
        if (z) {
            NativeObject nativeObject = (NativeObject) value;
            if (targetClass.isInstance(nativeObject.getObj())) {
                return nativeObject.getObj();
            }
        }
        if (FileHandle.class.isAssignableFrom(targetClass)) {
            if (value instanceof String) {
                return Gdx.files.absolute(((String) value).getValue());
            }
            if (!z) {
                return null;
            }
            NativeObject nativeObject2 = (NativeObject) value;
            if (nativeObject2.getObj() instanceof File) {
                return Gdx.files.absolute(((File) nativeObject2.getObj()).getAbsolutePath());
            }
            return null;
        }
        if (File.class.isAssignableFrom(targetClass) && (value instanceof String)) {
            return new File(((String) value).getValue());
        }
        java.lang.String name2 = targetClass.getName();
        if (name2 != null) {
            switch (name2.hashCode()) {
                case -2056817302:
                    if (name2.equals(CommonClassNames.JAVA_LANG_INTEGER)) {
                        if (value instanceof Number) {
                            number = (Number) value;
                        } else {
                            number = null;
                        }
                        if (number != null) {
                            objValueOf = Integer.valueOf((int) number.getValue());
                        }
                    }
                    break;
                case -1325958191:
                    if (name2.equals("double")) {
                        if (value instanceof Number) {
                            number3 = (Number) value;
                        } else {
                            number3 = null;
                        }
                        if (number3 != null) {
                            objValueOf = Double.valueOf(number3.getValue());
                        }
                    }
                    break;
                case -527879800:
                    if (name2.equals(CommonClassNames.JAVA_LANG_FLOAT)) {
                        if (value instanceof Number) {
                            number2 = (Number) value;
                        } else {
                            number2 = null;
                        }
                        if (number2 != null) {
                            objValueOf = java.lang.Float.valueOf((float) number2.getValue());
                        }
                    }
                    break;
                case 104431:
                    if (name2.equals("int")) {
                        if (value instanceof Number) {
                            number = (Number) value;
                        } else {
                            number = null;
                        }
                        if (number != null) {
                            objValueOf = Integer.valueOf((int) number.getValue());
                        }
                    }
                    break;
                case 64711720:
                    if (name2.equals("boolean")) {
                        if (value instanceof Boolean) {
                            r2 = (Boolean) value;
                        } else {
                            r2 = null;
                        }
                        if (r2 != null) {
                            objValueOf = java.lang.Boolean.valueOf(r2.getValue());
                        }
                    }
                    break;
                case 97526364:
                    if (name2.equals("float")) {
                        if (value instanceof Number) {
                            number2 = (Number) value;
                        } else {
                            number2 = null;
                        }
                        if (number2 != null) {
                            objValueOf = java.lang.Float.valueOf((float) number2.getValue());
                        }
                    }
                    break;
                case 344809556:
                    if (name2.equals(CommonClassNames.JAVA_LANG_BOOLEAN)) {
                        if (value instanceof Boolean) {
                            r2 = (Boolean) value;
                        } else {
                            r2 = null;
                        }
                        if (r2 != null) {
                            objValueOf = java.lang.Boolean.valueOf(r2.getValue());
                        }
                    }
                    break;
                case 366142910:
                    if (name2.equals("kotlin.String")) {
                        if (value instanceof String) {
                            string = (String) value;
                        } else {
                            string = null;
                        }
                        if (string != null) {
                            objValueOf = string.getValue();
                        }
                    }
                    break;
                case 761287205:
                    if (name2.equals(CommonClassNames.JAVA_LANG_DOUBLE)) {
                        if (value instanceof Number) {
                            number3 = (Number) value;
                        } else {
                            number3 = null;
                        }
                        if (number3 != null) {
                            objValueOf = Double.valueOf(number3.getValue());
                        }
                    }
                    break;
                case 1195259493:
                    if (name2.equals(CommonClassNames.JAVA_LANG_STRING)) {
                        if (value instanceof String) {
                            string = (String) value;
                        } else {
                            string = null;
                        }
                        if (string != null) {
                            objValueOf = string.getValue();
                        }
                    }
                    break;
            }
        }
        if (objValueOf != null) {
            return objValueOf;
        }
        throw new LunoRuntimeError("Cannot convert LunoValue type `" + (z ? ((NativeObject) value).getObj().getClass().getSimpleName() : Reflection.getOrCreateKotlinClass(value.getClass()).getSimpleName()) + "` to native type `" + targetClass.getSimpleName() + "`", 0, null, 6, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$List.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003HÆ\u0003J\u0019\u0010\b\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$List;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", PathTree.NAME_elements, "", "(Ljava/util/List;)V", "getElements", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class List extends LunoValue {
        private final java.util.List<LunoValue> elements;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ List copy$default(List list, java.util.List list2, int i, Object obj) {
            if ((i & 1) != 0) {
                list2 = list.elements;
            }
            return list.copy(list2);
        }

        public final java.util.List<LunoValue> component1() {
            return this.elements;
        }

        public final List copy(java.util.List<LunoValue> elements) {
            Intrinsics.checkNotNullParameter(elements, "elements");
            return new List(elements);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof List) && Intrinsics.areEqual(this.elements, ((List) other).elements);
        }

        public int hashCode() {
            return this.elements.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public List(java.util.List<LunoValue> elements) {
            super(null);
            Intrinsics.checkNotNullParameter(elements, "elements");
            this.elements = elements;
        }

        public final java.util.List<LunoValue> getElements() {
            return this.elements;
        }

        public java.lang.String toString() {
            return CollectionsKt.joinToString$default(this.elements, ", ", "[", "]", 0, null, new Function1<LunoValue, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.LunoValue$List$toString$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(LunoValue it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.toString();
                }
            }, 24, null);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$Float.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Float;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "value", "", "(F)V", "getValue", "()F", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Float extends LunoValue {
        private final float value;

        public static /* synthetic */ Float copy$default(Float r0, float f, int i, Object obj) {
            if ((i & 1) != 0) {
                f = r0.value;
            }
            return r0.copy(f);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final float getValue() {
            return this.value;
        }

        public final Float copy(float value) {
            return new Float(value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Float) && java.lang.Float.compare(this.value, ((Float) other).value) == 0;
        }

        public int hashCode() {
            return java.lang.Float.hashCode(this.value);
        }

        public Float(float f) {
            super(null);
            this.value = f;
        }

        public final float getValue() {
            return this.value;
        }

        public java.lang.String toString() {
            return this.value + "f";
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$Callable.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J&\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH&¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Callable;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "()V", "arity", "Lkotlin/ranges/IntRange;", "call", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "arguments", "", "callSiteToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Callable extends LunoValue {
        public abstract IntRange arity();

        public abstract LunoValue call(Interpreter interpreter, java.util.List<? extends LunoValue> arguments, Token callSiteToken);

        public Callable() {
            super(null);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$NativeCallable.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J&\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$NativeCallable;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Callable;", "callable", "Lorg/catrobat/catroid/utils/lunoscript/CallableNativeLunoFunction;", "(Lorg/catrobat/catroid/utils/lunoscript/CallableNativeLunoFunction;)V", "getCallable", "()Lorg/catrobat/catroid/utils/lunoscript/CallableNativeLunoFunction;", "arity", "Lkotlin/ranges/IntRange;", "call", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "arguments", "", "callSiteToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class NativeCallable extends Callable {
        private final CallableNativeLunoFunction callable;

        public static /* synthetic */ NativeCallable copy$default(NativeCallable nativeCallable, CallableNativeLunoFunction callableNativeLunoFunction, int i, Object obj) {
            if ((i & 1) != 0) {
                callableNativeLunoFunction = nativeCallable.callable;
            }
            return nativeCallable.copy(callableNativeLunoFunction);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final CallableNativeLunoFunction getCallable() {
            return this.callable;
        }

        public final NativeCallable copy(CallableNativeLunoFunction callable) {
            Intrinsics.checkNotNullParameter(callable, "callable");
            return new NativeCallable(callable);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof NativeCallable) && Intrinsics.areEqual(this.callable, ((NativeCallable) other).callable);
        }

        public int hashCode() {
            return this.callable.hashCode();
        }

        public java.lang.String toString() {
            return "NativeCallable(callable=" + this.callable + Constant.AFTER_QUTO;
        }

        public NativeCallable(CallableNativeLunoFunction callable) {
            Intrinsics.checkNotNullParameter(callable, "callable");
            this.callable = callable;
        }

        public final CallableNativeLunoFunction getCallable() {
            return this.callable;
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public IntRange arity() {
            return this.callable.getArity();
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public LunoValue call(Interpreter interpreter, java.util.List<? extends LunoValue> arguments, Token callSiteToken) {
            java.lang.String strValueOf;
            java.lang.String message;
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(callSiteToken, "callSiteToken");
            IntRange arity = this.callable.getArity();
            int first = arity.getFirst();
            int last = arity.getLast();
            int size = arguments.size();
            if (first > size || size > last) {
                if (this.callable.getArity().getFirst() == this.callable.getArity().getLast()) {
                    strValueOf = java.lang.String.valueOf(this.callable.getArity().getFirst());
                } else {
                    strValueOf = this.callable.getArity().getFirst() + ".." + this.callable.getArity().getLast();
                }
                throw new LunoRuntimeError("Native function '" + this.callable.getName() + "' expected " + strValueOf + " arguments, but got " + arguments.size() + ".", callSiteToken.getLine(), null, 4, null);
            }
            try {
                return this.callable.getFunction().invoke(interpreter, arguments);
            } catch (Exception e) {
                java.lang.String name2 = this.callable.getName();
                Throwable cause = e.getCause();
                if (cause == null || (message = cause.getMessage()) == null) {
                    message = e.getMessage();
                }
                throw new LunoRuntimeError("Error in native function '" + name2 + "': " + message, callSiteToken.getLine(), e);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$LunoFunction.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J&\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bHÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoFunction;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Callable;", "declaration", "Lorg/catrobat/catroid/utils/lunoscript/FunDeclarationStatement;", "closure", "Lorg/catrobat/catroid/utils/lunoscript/Scope;", "(Lorg/catrobat/catroid/utils/lunoscript/FunDeclarationStatement;Lorg/catrobat/catroid/utils/lunoscript/Scope;)V", "getClosure", "()Lorg/catrobat/catroid/utils/lunoscript/Scope;", "getDeclaration", "()Lorg/catrobat/catroid/utils/lunoscript/FunDeclarationStatement;", "arity", "Lkotlin/ranges/IntRange;", "call", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "arguments", "", "callSiteToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class LunoFunction extends Callable {
        private final Scope closure;
        private final FunDeclarationStatement declaration;

        public static /* synthetic */ LunoFunction copy$default(LunoFunction lunoFunction, FunDeclarationStatement funDeclarationStatement, Scope scope, int i, Object obj) {
            if ((i & 1) != 0) {
                funDeclarationStatement = lunoFunction.declaration;
            }
            if ((i & 2) != 0) {
                scope = lunoFunction.closure;
            }
            return lunoFunction.copy(funDeclarationStatement, scope);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FunDeclarationStatement getDeclaration() {
            return this.declaration;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Scope getClosure() {
            return this.closure;
        }

        public final LunoFunction copy(FunDeclarationStatement declaration, Scope closure) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(closure, "closure");
            return new LunoFunction(declaration, closure);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LunoFunction)) {
                return false;
            }
            LunoFunction lunoFunction = (LunoFunction) other;
            return Intrinsics.areEqual(this.declaration, lunoFunction.declaration) && Intrinsics.areEqual(this.closure, lunoFunction.closure);
        }

        public int hashCode() {
            return (this.declaration.hashCode() * 31) + this.closure.hashCode();
        }

        public java.lang.String toString() {
            return "LunoFunction(declaration=" + this.declaration + ", closure=" + this.closure + Constant.AFTER_QUTO;
        }

        public LunoFunction(FunDeclarationStatement declaration, Scope closure) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(closure, "closure");
            this.declaration = declaration;
            this.closure = closure;
        }

        public final Scope getClosure() {
            return this.closure;
        }

        public final FunDeclarationStatement getDeclaration() {
            return this.declaration;
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public IntRange arity() {
            return new IntRange(this.declaration.getParams().size(), this.declaration.getParams().size());
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public LunoValue call(Interpreter interpreter, java.util.List<? extends LunoValue> arguments, Token callSiteToken) {
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(callSiteToken, "callSiteToken");
            Scope scope = new Scope(this.closure);
            int size = this.declaration.getParams().size();
            int i = 0;
            while (i < size) {
                scope.define(this.declaration.getParams().get(i).getLexeme(), (i < 0 || i > CollectionsKt.getLastIndex(arguments)) ? Null.INSTANCE : arguments.get(i));
                i++;
            }
            try {
                interpreter.executeBlock(this.declaration.getBody().getStatements(), scope);
                return Null.INSTANCE;
            } catch (ReturnSignal e) {
                return e.getValue();
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$BoundMethod.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J&\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$BoundMethod;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Callable;", "instance", "", "methodName", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$String;", "(Ljava/lang/Object;Lorg/catrobat/catroid/utils/lunoscript/LunoValue$String;)V", "getInstance", "()Ljava/lang/Object;", "getMethodName", "()Lorg/catrobat/catroid/utils/lunoscript/LunoValue$String;", "arity", "Lkotlin/ranges/IntRange;", "call", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "arguments", "", "callSiteToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class BoundMethod extends Callable {
        private final Object instance;
        private final String methodName;

        public static /* synthetic */ BoundMethod copy$default(BoundMethod boundMethod, Object obj, String string, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = boundMethod.instance;
            }
            if ((i & 2) != 0) {
                string = boundMethod.methodName;
            }
            return boundMethod.copy(obj, string);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Object getInstance() {
            return this.instance;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getMethodName() {
            return this.methodName;
        }

        public final BoundMethod copy(Object instance, String methodName) {
            Intrinsics.checkNotNullParameter(instance, "instance");
            Intrinsics.checkNotNullParameter(methodName, "methodName");
            return new BoundMethod(instance, methodName);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BoundMethod)) {
                return false;
            }
            BoundMethod boundMethod = (BoundMethod) other;
            return Intrinsics.areEqual(this.instance, boundMethod.instance) && Intrinsics.areEqual(this.methodName, boundMethod.methodName);
        }

        public int hashCode() {
            return (this.instance.hashCode() * 31) + this.methodName.hashCode();
        }

        public java.lang.String toString() {
            return "BoundMethod(instance=" + this.instance + ", methodName=" + this.methodName + Constant.AFTER_QUTO;
        }

        public BoundMethod(Object instance, String methodName) {
            Intrinsics.checkNotNullParameter(instance, "instance");
            Intrinsics.checkNotNullParameter(methodName, "methodName");
            this.instance = instance;
            this.methodName = methodName;
        }

        public final Object getInstance() {
            return this.instance;
        }

        public final String getMethodName() {
            return this.methodName;
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public IntRange arity() {
            return new IntRange(0, Integer.MAX_VALUE);
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public LunoValue call(Interpreter interpreter, java.util.List<? extends LunoValue> arguments, Token callSiteToken) {
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(callSiteToken, "callSiteToken");
            return interpreter.callNativeMethod(this.instance, this.methodName.toString(), arguments, callSiteToken.getLine());
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$BoundMethod2.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J&\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bHÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$BoundMethod2;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Callable;", "receiver", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoObject;", "method", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoFunction;", "(Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoObject;Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoFunction;)V", "getMethod", "()Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoFunction;", "getReceiver", "()Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoObject;", "arity", "Lkotlin/ranges/IntRange;", "call", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "arguments", "", "callSiteToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class BoundMethod2 extends Callable {
        private final LunoFunction method;
        private final LunoObject receiver;

        public static /* synthetic */ BoundMethod2 copy$default(BoundMethod2 boundMethod2, LunoObject lunoObject, LunoFunction lunoFunction, int i, Object obj) {
            if ((i & 1) != 0) {
                lunoObject = boundMethod2.receiver;
            }
            if ((i & 2) != 0) {
                lunoFunction = boundMethod2.method;
            }
            return boundMethod2.copy(lunoObject, lunoFunction);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final LunoObject getReceiver() {
            return this.receiver;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final LunoFunction getMethod() {
            return this.method;
        }

        public final BoundMethod2 copy(LunoObject receiver, LunoFunction method) {
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            Intrinsics.checkNotNullParameter(method, "method");
            return new BoundMethod2(receiver, method);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BoundMethod2)) {
                return false;
            }
            BoundMethod2 boundMethod2 = (BoundMethod2) other;
            return Intrinsics.areEqual(this.receiver, boundMethod2.receiver) && Intrinsics.areEqual(this.method, boundMethod2.method);
        }

        public int hashCode() {
            return (this.receiver.hashCode() * 31) + this.method.hashCode();
        }

        public java.lang.String toString() {
            return "BoundMethod2(receiver=" + this.receiver + ", method=" + this.method + Constant.AFTER_QUTO;
        }

        public BoundMethod2(LunoObject receiver, LunoFunction method) {
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            Intrinsics.checkNotNullParameter(method, "method");
            this.receiver = receiver;
            this.method = method;
        }

        public final LunoFunction getMethod() {
            return this.method;
        }

        public final LunoObject getReceiver() {
            return this.receiver;
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public IntRange arity() {
            return this.method.arity();
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public LunoValue call(Interpreter interpreter, java.util.List<? extends LunoValue> arguments, Token callSiteToken) {
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(callSiteToken, "callSiteToken");
            Scope scope = new Scope(this.method.getClosure());
            scope.define("this", this.receiver);
            int size = this.method.getDeclaration().getParams().size();
            int i = 0;
            while (i < size) {
                scope.define(this.method.getDeclaration().getParams().get(i).getLexeme(), (i < 0 || i > CollectionsKt.getLastIndex(arguments)) ? Null.INSTANCE : arguments.get(i));
                i++;
            }
            try {
                interpreter.executeBlock(this.method.getDeclaration().getBody().getStatements(), scope);
                return Null.INSTANCE;
            } catch (ReturnSignal e) {
                return e.getValue();
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$LunoObject.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0007J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005HÆ\u0003J+\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\b\u0010\u0015\u001a\u00020\u0006H\u0016R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoObject;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "klass", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoClass;", "fields", "", "", "(Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoClass;Ljava/util/Map;)V", "getFields", "()Ljava/util/Map;", "getKlass", "()Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoClass;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class LunoObject extends LunoValue {
        private final Map<java.lang.String, LunoValue> fields;
        private final LunoClass klass;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ LunoObject copy$default(LunoObject lunoObject, LunoClass lunoClass, Map map, int i, Object obj) {
            if ((i & 1) != 0) {
                lunoClass = lunoObject.klass;
            }
            if ((i & 2) != 0) {
                map = lunoObject.fields;
            }
            return lunoObject.copy(lunoClass, map);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final LunoClass getKlass() {
            return this.klass;
        }

        public final Map<java.lang.String, LunoValue> component2() {
            return this.fields;
        }

        public final LunoObject copy(LunoClass klass, Map<java.lang.String, LunoValue> fields) {
            Intrinsics.checkNotNullParameter(fields, "fields");
            return new LunoObject(klass, fields);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LunoObject)) {
                return false;
            }
            LunoObject lunoObject = (LunoObject) other;
            return Intrinsics.areEqual(this.klass, lunoObject.klass) && Intrinsics.areEqual(this.fields, lunoObject.fields);
        }

        public int hashCode() {
            LunoClass lunoClass = this.klass;
            return ((lunoClass == null ? 0 : lunoClass.hashCode()) * 31) + this.fields.hashCode();
        }

        public final LunoClass getKlass() {
            return this.klass;
        }

        public /* synthetic */ LunoObject(LunoClass lunoClass, LinkedHashMap linkedHashMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(lunoClass, (i & 2) != 0 ? new LinkedHashMap() : linkedHashMap);
        }

        public final Map<java.lang.String, LunoValue> getFields() {
            return this.fields;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LunoObject(LunoClass lunoClass, Map<java.lang.String, LunoValue> fields) {
            super(null);
            Intrinsics.checkNotNullParameter(fields, "fields");
            this.klass = lunoClass;
            this.fields = fields;
        }

        public java.lang.String toString() {
            java.lang.String name2;
            java.lang.String string;
            LunoClass lunoClass = this.klass;
            return (lunoClass == null || (name2 = lunoClass.getName()) == null || (string = new StringBuilder().append(name2).append(" instance").toString()) == null) ? this.fields.toString() : string;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$LunoClass.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0000\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\b\u0010\u0017\u001a\u00020\u0018H\u0016J&\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001a0\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\u0015\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0000HÆ\u0003J\u000f\u0010%\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000bHÆ\u0003JO\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00002\u000e\b\u0002\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000bHÆ\u0001J\u0013\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*HÖ\u0003J\u0010\u0010+\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0002\u001a\u00020\u0003J\t\u0010,\u001a\u00020-HÖ\u0001J\b\u0010.\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006/"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoClass;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Callable;", "name", "", "declaration", "Lorg/catrobat/catroid/utils/lunoscript/ClassDeclarationStatement;", "methods", "", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoFunction;", "lunoSuperclass", "nativeSuperclass", "Ljava/lang/Class;", "(Ljava/lang/String;Lorg/catrobat/catroid/utils/lunoscript/ClassDeclarationStatement;Ljava/util/Map;Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoClass;Ljava/lang/Class;)V", "getDeclaration", "()Lorg/catrobat/catroid/utils/lunoscript/ClassDeclarationStatement;", "getLunoSuperclass", "()Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoClass;", "getMethods", "()Ljava/util/Map;", "getName", "()Ljava/lang/String;", "getNativeSuperclass", "()Ljava/lang/Class;", "arity", "Lkotlin/ranges/IntRange;", "call", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "arguments", "", "callSiteToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "findMethod", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class LunoClass extends Callable {
        private final ClassDeclarationStatement declaration;
        private final LunoClass lunoSuperclass;
        private final Map<java.lang.String, LunoFunction> methods;
        private final java.lang.String name;
        private final Class<?> nativeSuperclass;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ LunoClass copy$default(LunoClass lunoClass, java.lang.String str, ClassDeclarationStatement classDeclarationStatement, Map map, LunoClass lunoClass2, Class cls, int i, Object obj) {
            if ((i & 1) != 0) {
                str = lunoClass.name;
            }
            if ((i & 2) != 0) {
                classDeclarationStatement = lunoClass.declaration;
            }
            ClassDeclarationStatement classDeclarationStatement2 = classDeclarationStatement;
            if ((i & 4) != 0) {
                map = lunoClass.methods;
            }
            Map map2 = map;
            if ((i & 8) != 0) {
                lunoClass2 = lunoClass.lunoSuperclass;
            }
            LunoClass lunoClass3 = lunoClass2;
            if ((i & 16) != 0) {
                cls = lunoClass.nativeSuperclass;
            }
            return lunoClass.copy(str, classDeclarationStatement2, map2, lunoClass3, cls);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final java.lang.String getName() {
            return this.name;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ClassDeclarationStatement getDeclaration() {
            return this.declaration;
        }

        public final Map<java.lang.String, LunoFunction> component3() {
            return this.methods;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final LunoClass getLunoSuperclass() {
            return this.lunoSuperclass;
        }

        public final Class<?> component5() {
            return this.nativeSuperclass;
        }

        public final LunoClass copy(java.lang.String name2, ClassDeclarationStatement declaration, Map<java.lang.String, LunoFunction> methods, LunoClass lunoSuperclass, Class<?> nativeSuperclass) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(methods, "methods");
            return new LunoClass(name2, declaration, methods, lunoSuperclass, nativeSuperclass);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LunoClass)) {
                return false;
            }
            LunoClass lunoClass = (LunoClass) other;
            return Intrinsics.areEqual(this.name, lunoClass.name) && Intrinsics.areEqual(this.declaration, lunoClass.declaration) && Intrinsics.areEqual(this.methods, lunoClass.methods) && Intrinsics.areEqual(this.lunoSuperclass, lunoClass.lunoSuperclass) && Intrinsics.areEqual(this.nativeSuperclass, lunoClass.nativeSuperclass);
        }

        public int hashCode() {
            int iHashCode = ((((this.name.hashCode() * 31) + this.declaration.hashCode()) * 31) + this.methods.hashCode()) * 31;
            LunoClass lunoClass = this.lunoSuperclass;
            int iHashCode2 = (iHashCode + (lunoClass == null ? 0 : lunoClass.hashCode())) * 31;
            Class<?> cls = this.nativeSuperclass;
            return iHashCode2 + (cls != null ? cls.hashCode() : 0);
        }

        public /* synthetic */ LunoClass(java.lang.String str, ClassDeclarationStatement classDeclarationStatement, Map map, LunoClass lunoClass, Class cls, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, classDeclarationStatement, map, (i & 8) != 0 ? null : lunoClass, (i & 16) != 0 ? null : cls);
        }

        public final java.lang.String getName() {
            return this.name;
        }

        public final ClassDeclarationStatement getDeclaration() {
            return this.declaration;
        }

        public final Map<java.lang.String, LunoFunction> getMethods() {
            return this.methods;
        }

        public final LunoClass getLunoSuperclass() {
            return this.lunoSuperclass;
        }

        public final Class<?> getNativeSuperclass() {
            return this.nativeSuperclass;
        }

        public LunoClass(java.lang.String name2, ClassDeclarationStatement declaration, Map<java.lang.String, LunoFunction> methods, LunoClass lunoClass, Class<?> cls) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(methods, "methods");
            this.name = name2;
            this.declaration = declaration;
            this.methods = methods;
            this.lunoSuperclass = lunoClass;
            this.nativeSuperclass = cls;
        }

        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public IntRange arity() {
            IntRange intRangeArity;
            LunoFunction lunoFunction = this.methods.get(ConfigConstants.CONFIG_INIT_SECTION);
            return (lunoFunction == null || (intRangeArity = lunoFunction.arity()) == null) ? new IntRange(0, 0) : intRangeArity;
        }

        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // org.catrobat.catroid.utils.lunoscript.LunoValue.Callable
        public LunoValue call(Interpreter interpreter, java.util.List<? extends LunoValue> arguments, Token callSiteToken) {
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(callSiteToken, "callSiteToken");
            LunoObject lunoObject = new LunoObject(this, new LinkedHashMap());
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            BlockStatement staticBlock = this.declaration.getStaticBlock();
            int i = 0;
            if (staticBlock != null) {
                Scope scope = new Scope(interpreter.getGlobals());
                try {
                    interpreter.executeBlock(staticBlock.getStatements(), scope);
                    for (Map.Entry<java.lang.String, Scope.ScopeEntry> entry : scope.getValues().entrySet()) {
                        java.lang.String key = entry.getKey();
                        Scope.ScopeEntry value = entry.getValue();
                        if (key.length() > 0) {
                            StringBuilder sb = new StringBuilder();
                            java.lang.String strValueOf = java.lang.String.valueOf(key.charAt(0));
                            Intrinsics.checkNotNull(strValueOf, "null cannot be cast to non-null type java.lang.String");
                            java.lang.String upperCase = strValueOf.toUpperCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                            StringBuilder sbAppend = sb.append((Object) upperCase);
                            java.lang.String strSubstring = key.substring(1);
                            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                            key = sbAppend.append(strSubstring).toString();
                        }
                        linkedHashMap.put("get" + key, interpreter.lunoValueToKotlin(value.getValue(), Object.class));
                    }
                } catch (Exception e) {
                    throw new LunoRuntimeError("Error executing static block for class '" + this.name + "': " + e.getMessage(), this.declaration.getLine(), e);
                }
            }
            LunoValue nativeObject = lunoObject;
            if (this.nativeSuperclass != null) {
                Context appContext = CatroidApplication.getAppContext();
                if (appContext == null) {
                    throw new LunoRuntimeError("An Android Context is required to create native-backed objects.", callSiteToken.getLine(), null, 4, null);
                }
                nativeObject = new NativeObject(NativeProxyFactory.INSTANCE.createProxy(lunoObject, this.nativeSuperclass, interpreter, appContext, linkedHashMap));
            }
            LunoFunction lunoFunction = this.methods.get(ConfigConstants.CONFIG_INIT_SECTION);
            if (lunoFunction != null) {
                Scope scope2 = new Scope(lunoFunction.getClosure());
                scope2.define("this", nativeObject);
                int size = lunoFunction.getDeclaration().getParams().size();
                while (i < size) {
                    scope2.define(lunoFunction.getDeclaration().getParams().get(i).getLexeme(), (i < 0 || i > CollectionsKt.getLastIndex(arguments)) ? Null.INSTANCE : arguments.get(i));
                    i++;
                }
                try {
                    interpreter.executeBlock(lunoFunction.getDeclaration().getBody().getStatements(), scope2);
                } catch (ReturnSignal unused) {
                }
            }
            return nativeObject;
        }

        public final LunoFunction findMethod(java.lang.String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            LunoFunction lunoFunction = this.methods.get(name2);
            if (lunoFunction != null) {
                return lunoFunction;
            }
            LunoClass lunoClass = this.lunoSuperclass;
            if (lunoClass != null) {
                return lunoClass.findMethod(name2);
            }
            return null;
        }

        public java.lang.String toString() {
            return "<class " + this.name + ">";
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$NativeObject.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$NativeObject;", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "obj", "", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getObj", "()Ljava/lang/Object;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class NativeObject extends LunoValue {
        private final Object obj;

        public static /* synthetic */ NativeObject copy$default(NativeObject nativeObject, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = nativeObject.obj;
            }
            return nativeObject.copy(obj);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Object getObj() {
            return this.obj;
        }

        public final NativeObject copy(Object obj) {
            Intrinsics.checkNotNullParameter(obj, "obj");
            return new NativeObject(obj);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof NativeObject) && Intrinsics.areEqual(this.obj, ((NativeObject) other).obj);
        }

        public int hashCode() {
            return this.obj.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NativeObject(Object obj) {
            super(null);
            Intrinsics.checkNotNullParameter(obj, "obj");
            this.obj = obj;
        }

        public final Object getObj() {
            return this.obj;
        }

        public java.lang.String toString() {
            return "NativeObject(" + Reflection.getOrCreateKotlinClass(this.obj.getClass()).getSimpleName() + Constant.AFTER_QUTO;
        }
    }

    public final boolean isTruthy() {
        if (this instanceof Null) {
            return false;
        }
        if (this instanceof Boolean) {
            return ((Boolean) this).getValue();
        }
        if (this instanceof Number) {
            if (((Number) this).getValue() == 0.0d) {
                return false;
            }
        } else if (this instanceof String) {
            if (((String) this).getValue().length() <= 0) {
                return false;
            }
        } else if (this instanceof List) {
            if (((List) this).getElements().isEmpty()) {
                return false;
            }
        } else if (!(this instanceof LunoObject) && !(this instanceof Callable) && !(this instanceof NativeObject) && !(this instanceof BoundMethod)) {
            if (!(this instanceof Float)) {
                throw new NoWhenBranchMatchedException();
            }
            if (((Float) this).getValue() == 0.0f) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoValue$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LunoValue.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Companion;", "", "()V", "fromKotlin", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LunoValue fromKotlin(Object value) {
            if (value == null) {
                return Null.INSTANCE;
            }
            if (value instanceof java.lang.Number) {
                return new Number(((java.lang.Number) value).doubleValue());
            }
            if (value instanceof java.lang.String) {
                return new String((java.lang.String) value);
            }
            if (value instanceof java.lang.Boolean) {
                return new Boolean(((java.lang.Boolean) value).booleanValue());
            }
            if (value instanceof LunoValue) {
                return (LunoValue) value;
            }
            if (value instanceof Context) {
                return new NativeObject(value);
            }
            return value instanceof CallableNativeLunoFunction ? new NativeCallable((CallableNativeLunoFunction) value) : new NativeObject(value);
        }
    }
}
