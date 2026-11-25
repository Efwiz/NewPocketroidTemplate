package org.jetbrains.kotlin.resolve.calls.mpp;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ExpectActualCollectionArgumentsCompatibilityCheckStrategy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u000b\fB\u0007\b\u0004¢\u0006\u0002\u0010\u0002JD\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00040\nH&\u0082\u0001\u0002\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "", "()V", "areCompatible", "", ExifInterface.GPS_DIRECTION_TRUE, "expectArg", "", "actualArg", "elementsEqual", "Lkotlin/Function2;", "Default", "ExpectIsSubsetOfActual", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy$Default;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy$ExpectIsSubsetOfActual;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExpectActualCollectionArgumentsCompatibilityCheckStrategy {
    public /* synthetic */ ExpectActualCollectionArgumentsCompatibilityCheckStrategy(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract <T> boolean areCompatible(Collection<? extends T> expectArg, Collection<? extends T> actualArg, Function2<? super T, ? super T, Boolean> elementsEqual);

    private ExpectActualCollectionArgumentsCompatibilityCheckStrategy() {
    }

    /* JADX INFO: compiled from: ExpectActualCollectionArgumentsCompatibilityCheckStrategy.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÀ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JD\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00040\nH\u0016J\u0013\u0010\u000b\u001a\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy$Default;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "()V", "areCompatible", "", ExifInterface.GPS_DIRECTION_TRUE, "expectArg", "", "actualArg", "elementsEqual", "Lkotlin/Function2;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Default extends ExpectActualCollectionArgumentsCompatibilityCheckStrategy {
        public static final Default INSTANCE = new Default();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Default)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -100205080;
        }

        public String toString() {
            return "Default";
        }

        private Default() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualCollectionArgumentsCompatibilityCheckStrategy
        public <T> boolean areCompatible(Collection<? extends T> expectArg, Collection<? extends T> actualArg, Function2<? super T, ? super T, Boolean> elementsEqual) {
            Intrinsics.checkNotNullParameter(expectArg, "expectArg");
            Intrinsics.checkNotNullParameter(actualArg, "actualArg");
            Intrinsics.checkNotNullParameter(elementsEqual, "elementsEqual");
            if (expectArg.size() == actualArg.size()) {
                List<Pair> listZip = CollectionsKt.zip(expectArg, actualArg);
                if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
                    for (Pair pair : listZip) {
                        if (!elementsEqual.invoke((Object) pair.component1(), (Object) pair.component2()).booleanValue()) {
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }

    /* JADX INFO: compiled from: ExpectActualCollectionArgumentsCompatibilityCheckStrategy.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÀ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JD\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00040\nH\u0016J\u0013\u0010\u000b\u001a\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy$ExpectIsSubsetOfActual;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "()V", "areCompatible", "", ExifInterface.GPS_DIRECTION_TRUE, "expectArg", "", "actualArg", "elementsEqual", "Lkotlin/Function2;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ExpectIsSubsetOfActual extends ExpectActualCollectionArgumentsCompatibilityCheckStrategy {
        public static final ExpectIsSubsetOfActual INSTANCE = new ExpectIsSubsetOfActual();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExpectIsSubsetOfActual)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -1409631677;
        }

        public String toString() {
            return "ExpectIsSubsetOfActual";
        }

        private ExpectIsSubsetOfActual() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualCollectionArgumentsCompatibilityCheckStrategy
        public <T> boolean areCompatible(Collection<? extends T> expectArg, Collection<? extends T> actualArg, Function2<? super T, ? super T, Boolean> elementsEqual) {
            Intrinsics.checkNotNullParameter(expectArg, "expectArg");
            Intrinsics.checkNotNullParameter(actualArg, "actualArg");
            Intrinsics.checkNotNullParameter(elementsEqual, "elementsEqual");
            Collection<? extends T> collection = expectArg;
            if (collection.isEmpty()) {
                return true;
            }
            for (T t : collection) {
                Collection<? extends T> collection2 = actualArg;
                if (!collection2.isEmpty()) {
                    Iterator<T> it = collection2.iterator();
                    while (it.hasNext()) {
                        if (elementsEqual.invoke(t, it.next()).booleanValue()) {
                        }
                    }
                }
                return false;
            }
            return true;
        }
    }
}
