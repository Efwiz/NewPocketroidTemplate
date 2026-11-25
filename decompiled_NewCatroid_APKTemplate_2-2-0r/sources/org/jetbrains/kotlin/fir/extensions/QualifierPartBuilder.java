package org.jetbrains.kotlin.fir.extensions;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.impl.FirQualifierPartImpl;
import org.jetbrains.kotlin.fir.types.impl.FirTypeArgumentListImpl;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: RawUserTypeBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0010B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J)\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0019\b\u0002\u0010\f\u001a\u0013\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\t0\r¢\u0006\u0002\b\u000fR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/QualifierPartBuilder;", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", "(Ljava/util/List;)V", "getDestination$resolve", "()Ljava/util/List;", DependencyDownloader.TMP_SUFFIX, "", "name", "Lorg/jetbrains/kotlin/name/Name;", "argumentsBuilder", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/extensions/QualifierPartBuilder$TypeArgumentsBuilder;", "Lkotlin/ExtensionFunctionType;", "TypeArgumentsBuilder", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class QualifierPartBuilder {
    private final List<FirQualifierPart> destination;

    public QualifierPartBuilder(List<FirQualifierPart> destination) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        this.destination = destination;
    }

    public final List<FirQualifierPart> getDestination$resolve() {
        return this.destination;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void part$default(QualifierPartBuilder qualifierPartBuilder, Name name2, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1<TypeArgumentsBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.extensions.QualifierPartBuilder.part.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(TypeArgumentsBuilder typeArgumentsBuilder) {
                    Intrinsics.checkNotNullParameter(typeArgumentsBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(TypeArgumentsBuilder typeArgumentsBuilder) {
                    invoke2(typeArgumentsBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        qualifierPartBuilder.part(name2, function1);
    }

    public final void part(Name name2, Function1<? super TypeArgumentsBuilder, Unit> argumentsBuilder) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(argumentsBuilder, "argumentsBuilder");
        FirTypeArgumentListImpl firTypeArgumentListImpl = new FirTypeArgumentListImpl(null);
        argumentsBuilder.invoke(new TypeArgumentsBuilder(firTypeArgumentListImpl));
        this.destination.add(new FirQualifierPartImpl(null, name2, firTypeArgumentListImpl));
    }

    /* JADX INFO: compiled from: RawUserTypeBuilder.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/QualifierPartBuilder$TypeArgumentsBuilder;", "", "typeArgumentList", "Lorg/jetbrains/kotlin/fir/types/impl/FirTypeArgumentListImpl;", "(Lorg/jetbrains/kotlin/fir/types/impl/FirTypeArgumentListImpl;)V", "getTypeArgumentList$resolve", "()Lorg/jetbrains/kotlin/fir/types/impl/FirTypeArgumentListImpl;", "argument", "", "typeArgument", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeArgumentsBuilder {
        private final FirTypeArgumentListImpl typeArgumentList;

        public TypeArgumentsBuilder(FirTypeArgumentListImpl typeArgumentList) {
            Intrinsics.checkNotNullParameter(typeArgumentList, "typeArgumentList");
            this.typeArgumentList = typeArgumentList;
        }

        /* JADX INFO: renamed from: getTypeArgumentList$resolve, reason: from getter */
        public final FirTypeArgumentListImpl getTypeArgumentList() {
            return this.typeArgumentList;
        }

        public final void argument(FirTypeProjection typeArgument) {
            Intrinsics.checkNotNullParameter(typeArgument, "typeArgument");
            this.typeArgumentList.getTypeArguments().add(typeArgument);
        }
    }
}
