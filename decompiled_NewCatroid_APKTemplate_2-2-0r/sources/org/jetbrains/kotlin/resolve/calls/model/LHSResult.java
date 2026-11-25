package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "", "()V", "Empty", "Error", "Expression", CommonClassNames.JAVA_LANG_OBJECT_SHORT, "Type", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Empty;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Error;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Expression;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Object;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Type;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LHSResult {
    public /* synthetic */ LHSResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: KotlinCallArguments.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Type;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "qualifier", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "resolvedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "getQualifier", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "unboundDetailedReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getUnboundDetailedReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Type extends LHSResult {
        private final QualifierReceiver qualifier;
        private final ReceiverValueWithSmartCastInfo unboundDetailedReceiver;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Type(QualifierReceiver qualifierReceiver, UnwrappedType resolvedType) {
            super(null);
            Intrinsics.checkNotNullParameter(resolvedType, "resolvedType");
            this.qualifier = qualifierReceiver;
            if (qualifierReceiver != null && !(qualifierReceiver.getDescriptor() instanceof ClassDescriptor)) {
                boolean z = qualifierReceiver.getDescriptor() instanceof TypeAliasDescriptor;
            }
            this.unboundDetailedReceiver = new ReceiverValueWithSmartCastInfo(new TransientReceiver(resolvedType), SetsKt.emptySet(), true, null, 8, null);
        }

        public final QualifierReceiver getQualifier() {
            return this.qualifier;
        }

        public final ReceiverValueWithSmartCastInfo getUnboundDetailedReceiver() {
            return this.unboundDetailedReceiver;
        }
    }

    private LHSResult() {
    }

    /* JADX INFO: compiled from: KotlinCallArguments.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Object;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "qualifier", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;)V", "objectValueReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getObjectValueReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getQualifier", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Object extends LHSResult {
        private final ReceiverValueWithSmartCastInfo objectValueReceiver;
        private final QualifierReceiver qualifier;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Object(QualifierReceiver qualifier) {
            super(null);
            Intrinsics.checkNotNullParameter(qualifier, "qualifier");
            this.qualifier = qualifier;
            DescriptorUtils.isObject(qualifier.getDescriptor());
            ReceiverValueWithSmartCastInfo classValueReceiverWithSmartCastInfo = qualifier.getClassValueReceiverWithSmartCastInfo();
            if (classValueReceiverWithSmartCastInfo == null) {
                throw new IllegalStateException(("class value should be not null for " + qualifier).toString());
            }
            this.objectValueReceiver = classValueReceiverWithSmartCastInfo;
        }

        public final QualifierReceiver getQualifier() {
            return this.qualifier;
        }

        public final ReceiverValueWithSmartCastInfo getObjectValueReceiver() {
            return this.objectValueReceiver;
        }
    }

    /* JADX INFO: compiled from: KotlinCallArguments.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Expression;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "lshCallArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "(Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;)V", "getLshCallArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Expression extends LHSResult {
        private final SimpleKotlinCallArgument lshCallArgument;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Expression(SimpleKotlinCallArgument lshCallArgument) {
            super(null);
            Intrinsics.checkNotNullParameter(lshCallArgument, "lshCallArgument");
            this.lshCallArgument = lshCallArgument;
        }

        public final SimpleKotlinCallArgument getLshCallArgument() {
            return this.lshCallArgument;
        }
    }

    /* JADX INFO: compiled from: KotlinCallArguments.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Empty;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "()V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty extends LHSResult {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: KotlinCallArguments.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult$Error;", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "()V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Error extends LHSResult {
        public static final Error INSTANCE = new Error();

        private Error() {
            super(null);
        }
    }
}
