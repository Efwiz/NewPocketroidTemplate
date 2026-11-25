package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.builder.FirPlaceholderProjectionBuilder;

/* JADX INFO: compiled from: TypeArgumentMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H¦\u0002\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping;", "", "()V", "get", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "typeParameterIndex", "", "Mapped", "NoExplicitArguments", "Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping$Mapped;", "Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping$NoExplicitArguments;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeArgumentMapping {
    public /* synthetic */ TypeArgumentMapping(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract FirTypeProjection get(int typeParameterIndex);

    private TypeArgumentMapping() {
    }

    /* JADX INFO: compiled from: TypeArgumentMapping.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0096\u0002¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping$NoExplicitArguments;", "Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping;", "()V", "get", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "typeParameterIndex", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoExplicitArguments extends TypeArgumentMapping {
        public static final NoExplicitArguments INSTANCE = new NoExplicitArguments();

        private NoExplicitArguments() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.TypeArgumentMapping
        public FirTypeProjection get(int typeParameterIndex) {
            return new FirPlaceholderProjectionBuilder().build();
        }
    }

    /* JADX INFO: compiled from: TypeArgumentMapping.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0096\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping$Mapped;", "Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping;", "ordered", "", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "(Ljava/util/List;)V", "get", "typeParameterIndex", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Mapped extends TypeArgumentMapping {
        private final List<FirTypeProjection> ordered;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public Mapped(List<? extends FirTypeProjection> ordered) {
            super(null);
            Intrinsics.checkNotNullParameter(ordered, "ordered");
            this.ordered = ordered;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.TypeArgumentMapping
        public FirTypeProjection get(int typeParameterIndex) {
            List<FirTypeProjection> list = this.ordered;
            return (typeParameterIndex < 0 || typeParameterIndex > CollectionsKt.getLastIndex(list)) ? new FirPlaceholderProjectionBuilder().build() : list.get(typeParameterIndex);
        }
    }
}
