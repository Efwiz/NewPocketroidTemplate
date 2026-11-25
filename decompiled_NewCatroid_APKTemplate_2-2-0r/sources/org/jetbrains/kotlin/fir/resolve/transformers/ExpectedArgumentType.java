package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: FirCallCompletionResultsWriterTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType;", "", "()V", "ArgumentsMap", "ExpectedType", "NoApproximation", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType$ArgumentsMap;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType$ExpectedType;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType$NoApproximation;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExpectedArgumentType {
    public /* synthetic */ ExpectedArgumentType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ExpectedArgumentType() {
    }

    /* JADX INFO: compiled from: FirCallCompletionResultsWriterTransformer.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B-\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\bR\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType$ArgumentsMap;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType;", "map", "", "Lorg/jetbrains/kotlin/fir/FirElement;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "lambdasReturnTypes", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "(Ljava/util/Map;Ljava/util/Map;)V", "getLambdasReturnTypes", "()Ljava/util/Map;", "getMap", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ArgumentsMap extends ExpectedArgumentType {
        private final Map<FirAnonymousFunction, ConeKotlinType> lambdasReturnTypes;
        private final Map<FirElement, ConeKotlinType> map;

        public final Map<FirElement, ConeKotlinType> getMap() {
            return this.map;
        }

        public final Map<FirAnonymousFunction, ConeKotlinType> getLambdasReturnTypes() {
            return this.lambdasReturnTypes;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public ArgumentsMap(Map<FirElement, ? extends ConeKotlinType> map, Map<FirAnonymousFunction, ? extends ConeKotlinType> lambdasReturnTypes) {
            super(null);
            Intrinsics.checkNotNullParameter(map, "map");
            Intrinsics.checkNotNullParameter(lambdasReturnTypes, "lambdasReturnTypes");
            this.map = map;
            this.lambdasReturnTypes = lambdasReturnTypes;
        }
    }

    /* JADX INFO: compiled from: FirCallCompletionResultsWriterTransformer.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType$ExpectedType;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExpectedType extends ExpectedArgumentType {
        private final ConeKotlinType type;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ExpectedType(ConeKotlinType type) {
            super(null);
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
        }

        public final ConeKotlinType getType() {
            return this.type;
        }
    }

    /* JADX INFO: compiled from: FirCallCompletionResultsWriterTransformer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType$NoApproximation;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoApproximation extends ExpectedArgumentType {
        public static final NoApproximation INSTANCE = new NoApproximation();

        private NoApproximation() {
            super(null);
        }
    }
}
