package org.jetbrains.kotlin.util;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;

/* JADX INFO: compiled from: modifierChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0007\b\t\nB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0004\u000b\f\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/util/ValueParameterCountCheck;", "Lorg/jetbrains/kotlin/util/Check;", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "AtLeast", "Equals", "NoValueParameters", "SingleValueParameter", "Lorg/jetbrains/kotlin/util/ValueParameterCountCheck$AtLeast;", "Lorg/jetbrains/kotlin/util/ValueParameterCountCheck$Equals;", "Lorg/jetbrains/kotlin/util/ValueParameterCountCheck$NoValueParameters;", "Lorg/jetbrains/kotlin/util/ValueParameterCountCheck$SingleValueParameter;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ValueParameterCountCheck implements Check {
    private final String description;

    public /* synthetic */ ValueParameterCountCheck(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    /* JADX INFO: compiled from: modifierChecks.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/util/ValueParameterCountCheck$NoValueParameters;", "Lorg/jetbrains/kotlin/util/ValueParameterCountCheck;", "()V", "check", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoValueParameters extends ValueParameterCountCheck {
        public static final NoValueParameters INSTANCE = new NoValueParameters();

        private NoValueParameters() {
            super("must have no value parameters", null);
        }

        @Override // org.jetbrains.kotlin.util.Check
        public boolean check(FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getValueParameters().isEmpty();
        }
    }

    private ValueParameterCountCheck(String str) {
        this.description = str;
    }

    @Override // org.jetbrains.kotlin.util.Check
    public String getDescription() {
        return this.description;
    }

    @Override // org.jetbrains.kotlin.util.Check
    public String invoke(FunctionDescriptor functionDescriptor) {
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }

    /* JADX INFO: compiled from: modifierChecks.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/util/ValueParameterCountCheck$SingleValueParameter;", "Lorg/jetbrains/kotlin/util/ValueParameterCountCheck;", "()V", "check", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SingleValueParameter extends ValueParameterCountCheck {
        public static final SingleValueParameter INSTANCE = new SingleValueParameter();

        private SingleValueParameter() {
            super("must have a single value parameter", null);
        }

        @Override // org.jetbrains.kotlin.util.Check
        public boolean check(FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getValueParameters().size() == 1;
        }
    }

    /* JADX INFO: compiled from: modifierChecks.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/util/ValueParameterCountCheck$AtLeast;", "Lorg/jetbrains/kotlin/util/ValueParameterCountCheck;", "n", "", "(I)V", "getN", "()I", "check", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AtLeast extends ValueParameterCountCheck {
        private final int n;

        public AtLeast(int i) {
            super("must have at least " + i + " value parameter" + (i > 1 ? "s" : ""), null);
            this.n = i;
        }

        public final int getN() {
            return this.n;
        }

        @Override // org.jetbrains.kotlin.util.Check
        public boolean check(FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getValueParameters().size() >= this.n;
        }
    }

    /* JADX INFO: compiled from: modifierChecks.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/util/ValueParameterCountCheck$Equals;", "Lorg/jetbrains/kotlin/util/ValueParameterCountCheck;", "n", "", "(I)V", "getN", "()I", "check", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Equals extends ValueParameterCountCheck {
        private final int n;

        public Equals(int i) {
            super("must have exactly " + i + " value parameters", null);
            this.n = i;
        }

        public final int getN() {
            return this.n;
        }

        @Override // org.jetbrains.kotlin.util.Check
        public boolean check(FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getValueParameters().size() == this.n;
        }
    }
}
