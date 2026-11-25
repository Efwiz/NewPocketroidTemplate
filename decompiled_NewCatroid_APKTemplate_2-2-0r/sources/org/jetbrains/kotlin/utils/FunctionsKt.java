package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* JADX INFO: compiled from: functions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u001a$\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u0002H\u0013\u0012\u0006\u0012\u0004\u0018\u0001H\u00140\u0001\"\u0004\b\u0000\u0010\u0013\"\b\b\u0001\u0010\u0014*\u00020\u0002\u001a\u0018\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u00020\u00040\u0001\"\u0004\b\u0000\u0010\u0013\u001a\u0006\u0010\u0016\u001a\u00020\u0006\u001a\u0018\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u00020\u00060\u0001\"\u0004\b\u0000\u0010\u0013\u001a\u0018\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00130\u0001\"\u0004\b\u0000\u0010\u0013\"\u001e\u0010\u0000\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001c\u0010\u0003\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00040\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001f\u0010\u0005\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00060\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\"'\u0010\t\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00060\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\"/\u0010\r\u001a \u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00060\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u001e\u0010\u0011\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"ALWAYS_NULL", "Lkotlin/Function1;", "", "ALWAYS_TRUE", "", "DO_NOTHING", "", "getDO_NOTHING", "()Lkotlin/jvm/functions/Function1;", "DO_NOTHING_2", "Lkotlin/Function2;", "getDO_NOTHING_2", "()Lkotlin/jvm/functions/Function2;", "DO_NOTHING_3", "Lkotlin/Function3;", "getDO_NOTHING_3", "()Lkotlin/jvm/functions/Function3;", "IDENTITY", "alwaysNull", ExifInterface.GPS_DIRECTION_TRUE, "R", "alwaysTrue", "doNothing", "identity", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionsKt {
    private static final Function1<Object, Object> IDENTITY = new Function1<Object, Object>() { // from class: org.jetbrains.kotlin.utils.FunctionsKt$IDENTITY$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return obj;
        }
    };
    private static final Function1<Object, Boolean> ALWAYS_TRUE = new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.utils.FunctionsKt$ALWAYS_TRUE$1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(Object obj) {
            return true;
        }
    };
    private static final Function1<Object, Object> ALWAYS_NULL = new Function1() { // from class: org.jetbrains.kotlin.utils.FunctionsKt$ALWAYS_NULL$1
        @Override // kotlin.jvm.functions.Function1
        public final Void invoke(Object obj) {
            return null;
        }
    };
    private static final Function1<Object, Unit> DO_NOTHING = new Function1<Object, Unit>() { // from class: org.jetbrains.kotlin.utils.FunctionsKt$DO_NOTHING$1
        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Object obj) {
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
            invoke2(obj);
            return Unit.INSTANCE;
        }
    };
    private static final Function2<Object, Object, Unit> DO_NOTHING_2 = new Function2<Object, Object, Unit>() { // from class: org.jetbrains.kotlin.utils.FunctionsKt$DO_NOTHING_2$1
        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Object obj, Object obj2) {
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Object obj, Object obj2) {
            invoke2(obj, obj2);
            return Unit.INSTANCE;
        }
    };
    private static final Function3<Object, Object, Object, Unit> DO_NOTHING_3 = new Function3<Object, Object, Object, Unit>() { // from class: org.jetbrains.kotlin.utils.FunctionsKt$DO_NOTHING_3$1
        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Object obj, Object obj2, Object obj3) {
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(Object obj, Object obj2, Object obj3) {
            invoke2(obj, obj2, obj3);
            return Unit.INSTANCE;
        }
    };

    /* JADX INFO: renamed from: doNothing, reason: collision with other method in class */
    public static final void m7486doNothing() {
    }

    public static final <T> Function1<T, T> identity() {
        Function1<Object, Object> function1 = IDENTITY;
        Intrinsics.checkNotNull(function1, "null cannot be cast to non-null type kotlin.Function1<T of org.jetbrains.kotlin.utils.FunctionsKt.identity, T of org.jetbrains.kotlin.utils.FunctionsKt.identity>");
        return (Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1);
    }

    public static final <T> Function1<T, Boolean> alwaysTrue() {
        return (Function1<T, Boolean>) ALWAYS_TRUE;
    }

    public static final <T, R> Function1<T, R> alwaysNull() {
        Function1<Object, Object> function1 = ALWAYS_NULL;
        Intrinsics.checkNotNull(function1, "null cannot be cast to non-null type kotlin.Function1<T of org.jetbrains.kotlin.utils.FunctionsKt.alwaysNull, R of org.jetbrains.kotlin.utils.FunctionsKt.alwaysNull?>");
        return (Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1);
    }

    public static final Function1<Object, Unit> getDO_NOTHING() {
        return DO_NOTHING;
    }

    public static final Function2<Object, Object, Unit> getDO_NOTHING_2() {
        return DO_NOTHING_2;
    }

    public static final Function3<Object, Object, Object, Unit> getDO_NOTHING_3() {
        return DO_NOTHING_3;
    }

    public static final <T> Function1<T, Unit> doNothing() {
        return (Function1<T, Unit>) DO_NOTHING;
    }
}
