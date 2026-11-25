package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirExpression;

/* JADX INFO: compiled from: ResolvedCallArgument.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\b\t\nB\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0003\u000b\f\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "", "()V", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getArguments", "()Ljava/util/List;", "DefaultArgument", "SimpleArgument", "VarargArgument", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument$DefaultArgument;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument$SimpleArgument;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument$VarargArgument;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolvedCallArgument {
    public /* synthetic */ ResolvedCallArgument(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract List<FirExpression> getArguments();

    private ResolvedCallArgument() {
    }

    /* JADX INFO: compiled from: ResolvedCallArgument.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument$DefaultArgument;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "()V", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getArguments", "()Ljava/util/List;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultArgument extends ResolvedCallArgument {
        public static final DefaultArgument INSTANCE = new DefaultArgument();

        private DefaultArgument() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolvedCallArgument
        public List<FirExpression> getArguments() {
            return CollectionsKt.emptyList();
        }
    }

    /* JADX INFO: compiled from: ResolvedCallArgument.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument$SimpleArgument;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "callArgument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "arguments", "", "getArguments", "()Ljava/util/List;", "getCallArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SimpleArgument extends ResolvedCallArgument {
        private final FirExpression callArgument;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SimpleArgument(FirExpression callArgument) {
            super(null);
            Intrinsics.checkNotNullParameter(callArgument, "callArgument");
            this.callArgument = callArgument;
        }

        public final FirExpression getCallArgument() {
            return this.callArgument;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolvedCallArgument
        public List<FirExpression> getArguments() {
            return CollectionsKt.listOf(this.callArgument);
        }
    }

    /* JADX INFO: compiled from: ResolvedCallArgument.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument$VarargArgument;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Ljava/util/List;)V", "getArguments", "()Ljava/util/List;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class VarargArgument extends ResolvedCallArgument {
        private final List<FirExpression> arguments;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public VarargArgument(List<? extends FirExpression> arguments) {
            super(null);
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            this.arguments = arguments;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolvedCallArgument
        public List<FirExpression> getArguments() {
            return this.arguments;
        }
    }
}
