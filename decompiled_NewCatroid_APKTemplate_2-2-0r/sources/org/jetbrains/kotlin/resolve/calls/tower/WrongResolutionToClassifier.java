package org.jetbrains.kotlin.resolve.calls.tower;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ErrorCandidateFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001b\b\u0002\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/WrongResolutionToClassifier;", "", CrashHianalyticsData.MESSAGE, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "(Ljava/lang/String;ILkotlin/jvm/functions/Function1;)V", "getMessage", "()Lkotlin/jvm/functions/Function1;", "TYPE_PARAMETER_AS_VALUE", "TYPE_PARAMETER_AS_FUNCTION", "INTERFACE_AS_VALUE", "INTERFACE_AS_FUNCTION", "EXPECT_CLASS_AS_FUNCTION", "CLASS_AS_VALUE", "INNER_CLASS_CONSTRUCTOR_NO_RECEIVER", "OBJECT_AS_FUNCTION", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum WrongResolutionToClassifier {
    TYPE_PARAMETER_AS_VALUE(new Function1<Name, String>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.WrongResolutionToClassifier.1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Name it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "Type parameter " + it + " cannot be used as value";
        }
    }),
    TYPE_PARAMETER_AS_FUNCTION(new Function1<Name, String>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.WrongResolutionToClassifier.2
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Name it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "Type parameter " + it + " cannot be called as function";
        }
    }),
    INTERFACE_AS_VALUE(new Function1<Name, String>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.WrongResolutionToClassifier.3
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Name it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "Interface " + it + " does not have companion object";
        }
    }),
    INTERFACE_AS_FUNCTION(new Function1<Name, String>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.WrongResolutionToClassifier.4
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Name it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "Interface " + it + " does not have constructors";
        }
    }),
    EXPECT_CLASS_AS_FUNCTION(new Function1<Name, String>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.WrongResolutionToClassifier.5
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Name it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "Expected class " + it + " does not have default constructor";
        }
    }),
    CLASS_AS_VALUE(new Function1<Name, String>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.WrongResolutionToClassifier.6
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Name it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "Class " + it + " does not have companion object";
        }
    }),
    INNER_CLASS_CONSTRUCTOR_NO_RECEIVER(new Function1<Name, String>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.WrongResolutionToClassifier.7
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Name it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "Constructor of inner class " + it + " can be called only with receiver of containing class";
        }
    }),
    OBJECT_AS_FUNCTION(new Function1<Name, String>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.WrongResolutionToClassifier.8
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Name it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "Function 'invoke()' is not found in object " + it;
        }
    });

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final Function1<Name, String> message;

    public static EnumEntries<WrongResolutionToClassifier> getEntries() {
        return $ENTRIES;
    }

    WrongResolutionToClassifier(Function1 function1) {
        this.message = function1;
    }

    public final Function1<Name, String> getMessage() {
        return this.message;
    }
}
