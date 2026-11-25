package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CallableReferenceResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy;", "", "()V", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getKind", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "CustomConversion", "NoConversion", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy$CustomConversion;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy$NoConversion;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CallableReferenceConversionStrategy {
    public /* synthetic */ CallableReferenceConversionStrategy(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract FunctionTypeKind getKind();

    private CallableReferenceConversionStrategy() {
    }

    /* JADX INFO: compiled from: CallableReferenceResolution.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy$NoConversion;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy;", "()V", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getKind", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoConversion extends CallableReferenceConversionStrategy {
        public static final NoConversion INSTANCE = new NoConversion();

        @Override // org.jetbrains.kotlin.fir.resolve.calls.CallableReferenceConversionStrategy
        public FunctionTypeKind getKind() {
            return null;
        }

        private NoConversion() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: CallableReferenceResolution.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy$CustomConversion;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "(Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;)V", "getKind", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CustomConversion extends CallableReferenceConversionStrategy {
        private final FunctionTypeKind kind;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CustomConversion(FunctionTypeKind kind) {
            super(null);
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.kind = kind;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.CallableReferenceConversionStrategy
        public FunctionTypeKind getKind() {
            return this.kind;
        }
    }
}
