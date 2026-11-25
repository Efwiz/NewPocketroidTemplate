package org.jetbrains.kotlin.ir.linkage.partial;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ExploredClassifier.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier;", "", "Unusable", "Usable", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Usable;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ExploredClassifier {

    /* JADX INFO: compiled from: ExploredClassifier.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0005\u0006\u0007\b\t\nR\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0082\u0001\u0002\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "AnnotationWithUnacceptableParameter", "CanBeRootCause", "DueToOtherClassifier", "InvalidInheritance", "MissingClassifier", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$DueToOtherClassifier;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Unusable extends ExploredClassifier {

        /* JADX INFO: compiled from: ExploredClassifier.kt */
        @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001\u0082\u0001\u0003\u0002\u0003\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$AnnotationWithUnacceptableParameter;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$InvalidInheritance;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$MissingClassifier;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public interface CanBeRootCause extends Unusable {
        }

        IrClassifierSymbol getSymbol();

        /* JADX INFO: compiled from: ExploredClassifier.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$MissingClassifier;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;)V", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class MissingClassifier implements CanBeRootCause {
            private final IrClassifierSymbol symbol;

            public static /* synthetic */ MissingClassifier copy$default(MissingClassifier missingClassifier, IrClassifierSymbol irClassifierSymbol, int i, Object obj) {
                if ((i & 1) != 0) {
                    irClassifierSymbol = missingClassifier.symbol;
                }
                return missingClassifier.copy(irClassifierSymbol);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final IrClassifierSymbol getSymbol() {
                return this.symbol;
            }

            public final MissingClassifier copy(IrClassifierSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                return new MissingClassifier(symbol);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof MissingClassifier) && Intrinsics.areEqual(this.symbol, ((MissingClassifier) other).symbol);
            }

            public int hashCode() {
                return this.symbol.hashCode();
            }

            public String toString() {
                return "MissingClassifier(symbol=" + this.symbol + ')';
            }

            public MissingClassifier(IrClassifierSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                this.symbol = symbol;
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier.Unusable
            public IrClassifierSymbol getSymbol() {
                return this.symbol;
            }
        }

        /* JADX INFO: compiled from: ExploredClassifier.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$InvalidInheritance;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "superClassSymbols", "", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Ljava/util/Collection;)V", "getSuperClassSymbols", "()Ljava/util/Collection;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class InvalidInheritance implements CanBeRootCause {
            private final Collection<IrClassSymbol> superClassSymbols;
            private final IrClassSymbol symbol;

            /* JADX WARN: Multi-variable type inference failed */
            public InvalidInheritance(IrClassSymbol symbol, Collection<? extends IrClassSymbol> superClassSymbols) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(superClassSymbols, "superClassSymbols");
                this.symbol = symbol;
                this.superClassSymbols = superClassSymbols;
                if (superClassSymbols.isEmpty()) {
                    throw new IllegalStateException("Check failed.".toString());
                }
            }

            public final Collection<IrClassSymbol> getSuperClassSymbols() {
                return this.superClassSymbols;
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier.Unusable
            public IrClassSymbol getSymbol() {
                return this.symbol;
            }
        }

        /* JADX INFO: compiled from: ExploredClassifier.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$AnnotationWithUnacceptableParameter;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "unacceptableClassifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;)V", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getUnacceptableClassifierSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class AnnotationWithUnacceptableParameter implements CanBeRootCause {
            private final IrClassSymbol symbol;
            private final IrClassifierSymbol unacceptableClassifierSymbol;

            public static /* synthetic */ AnnotationWithUnacceptableParameter copy$default(AnnotationWithUnacceptableParameter annotationWithUnacceptableParameter, IrClassSymbol irClassSymbol, IrClassifierSymbol irClassifierSymbol, int i, Object obj) {
                if ((i & 1) != 0) {
                    irClassSymbol = annotationWithUnacceptableParameter.symbol;
                }
                if ((i & 2) != 0) {
                    irClassifierSymbol = annotationWithUnacceptableParameter.unacceptableClassifierSymbol;
                }
                return annotationWithUnacceptableParameter.copy(irClassSymbol, irClassifierSymbol);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final IrClassSymbol getSymbol() {
                return this.symbol;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final IrClassifierSymbol getUnacceptableClassifierSymbol() {
                return this.unacceptableClassifierSymbol;
            }

            public final AnnotationWithUnacceptableParameter copy(IrClassSymbol symbol, IrClassifierSymbol unacceptableClassifierSymbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(unacceptableClassifierSymbol, "unacceptableClassifierSymbol");
                return new AnnotationWithUnacceptableParameter(symbol, unacceptableClassifierSymbol);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof AnnotationWithUnacceptableParameter)) {
                    return false;
                }
                AnnotationWithUnacceptableParameter annotationWithUnacceptableParameter = (AnnotationWithUnacceptableParameter) other;
                return Intrinsics.areEqual(this.symbol, annotationWithUnacceptableParameter.symbol) && Intrinsics.areEqual(this.unacceptableClassifierSymbol, annotationWithUnacceptableParameter.unacceptableClassifierSymbol);
            }

            public int hashCode() {
                return (this.symbol.hashCode() * 31) + this.unacceptableClassifierSymbol.hashCode();
            }

            public String toString() {
                return "AnnotationWithUnacceptableParameter(symbol=" + this.symbol + ", unacceptableClassifierSymbol=" + this.unacceptableClassifierSymbol + ')';
            }

            public AnnotationWithUnacceptableParameter(IrClassSymbol symbol, IrClassifierSymbol unacceptableClassifierSymbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(unacceptableClassifierSymbol, "unacceptableClassifierSymbol");
                this.symbol = symbol;
                this.unacceptableClassifierSymbol = unacceptableClassifierSymbol;
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier.Unusable
            public IrClassSymbol getSymbol() {
                return this.symbol;
            }

            public final IrClassifierSymbol getUnacceptableClassifierSymbol() {
                return this.unacceptableClassifierSymbol;
            }
        }

        /* JADX INFO: compiled from: ExploredClassifier.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$DueToOtherClassifier;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "rootCause", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;)V", "getRootCause", "()Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class DueToOtherClassifier implements Unusable {
            private final CanBeRootCause rootCause;
            private final IrClassifierSymbol symbol;

            public static /* synthetic */ DueToOtherClassifier copy$default(DueToOtherClassifier dueToOtherClassifier, IrClassifierSymbol irClassifierSymbol, CanBeRootCause canBeRootCause, int i, Object obj) {
                if ((i & 1) != 0) {
                    irClassifierSymbol = dueToOtherClassifier.symbol;
                }
                if ((i & 2) != 0) {
                    canBeRootCause = dueToOtherClassifier.rootCause;
                }
                return dueToOtherClassifier.copy(irClassifierSymbol, canBeRootCause);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final IrClassifierSymbol getSymbol() {
                return this.symbol;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final CanBeRootCause getRootCause() {
                return this.rootCause;
            }

            public final DueToOtherClassifier copy(IrClassifierSymbol symbol, CanBeRootCause rootCause) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(rootCause, "rootCause");
                return new DueToOtherClassifier(symbol, rootCause);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof DueToOtherClassifier)) {
                    return false;
                }
                DueToOtherClassifier dueToOtherClassifier = (DueToOtherClassifier) other;
                return Intrinsics.areEqual(this.symbol, dueToOtherClassifier.symbol) && Intrinsics.areEqual(this.rootCause, dueToOtherClassifier.rootCause);
            }

            public int hashCode() {
                return (this.symbol.hashCode() * 31) + this.rootCause.hashCode();
            }

            public String toString() {
                return "DueToOtherClassifier(symbol=" + this.symbol + ", rootCause=" + this.rootCause + ')';
            }

            public DueToOtherClassifier(IrClassifierSymbol symbol, CanBeRootCause rootCause) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(rootCause, "rootCause");
                this.symbol = symbol;
                this.rootCause = rootCause;
            }

            public final CanBeRootCause getRootCause() {
                return this.rootCause;
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier.Unusable
            public IrClassifierSymbol getSymbol() {
                return this.symbol;
            }
        }
    }

    /* JADX INFO: compiled from: ExploredClassifier.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Usable;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Usable implements ExploredClassifier {
        public static final Usable INSTANCE = new Usable();

        private Usable() {
        }
    }
}
