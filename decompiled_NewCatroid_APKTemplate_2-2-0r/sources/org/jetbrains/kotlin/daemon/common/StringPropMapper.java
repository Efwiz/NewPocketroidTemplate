package org.jetbrains.kotlin.daemon.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KMutableProperty1;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0016\b\u0001\u0010\u0002 \u0001*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00040\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00020\u0005Bu\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0001\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\t\u0012\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u000b\u0012\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u000b\u0012\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e0\u000b\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/StringPropMapper;", "C", "P", "Lkotlin/reflect/KMutableProperty1;", "", "Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "dest", "prop", "names", "", "fromString", "Lkotlin/Function1;", "toString", "skipIf", "", "mergeDelimiter", "(Ljava/lang/Object;Lkotlin/reflect/KMutableProperty1;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Ljava/lang/String;)V", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StringPropMapper<C, P extends KMutableProperty1<C, String>> extends PropMapper<C, String, P> {
    public /* synthetic */ StringPropMapper(Object obj, KMutableProperty1 kMutableProperty1, List list, Function1 function1, Function1 function2, Function1 function3, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, kMutableProperty1, (i & 4) != 0 ? CollectionsKt.emptyList() : list, (i & 8) != 0 ? new Function1<String, String>() { // from class: org.jetbrains.kotlin.daemon.common.StringPropMapper.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        } : function1, (i & 16) != 0 ? new Function1<String, String>() { // from class: org.jetbrains.kotlin.daemon.common.StringPropMapper.2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        } : function2, (i & 32) != 0 ? AnonymousClass3.INSTANCE : function3, (i & 64) != 0 ? null : str);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.daemon.common.StringPropMapper$3, reason: invalid class name */
    /* JADX INFO: compiled from: DaemonParams.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass3 extends FunctionReference implements Function1<String, Boolean> {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        AnonymousClass3() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "isEmpty(Ljava/lang/CharSequence;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "isEmpty";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(StringsKt.class, "daemon-common");
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(String p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(p0.length() == 0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StringPropMapper(C c, P prop, List<String> names, Function1<? super String, String> fromString, Function1<? super String, String> toString, Function1<? super String, Boolean> skipIf, String str) {
        super(c, prop, CollectionsKt.any(names) ? names : CollectionsKt.listOf(prop.getName()), fromString, toString, skipIf, str);
        Intrinsics.checkNotNullParameter(prop, "prop");
        Intrinsics.checkNotNullParameter(names, "names");
        Intrinsics.checkNotNullParameter(fromString, "fromString");
        Intrinsics.checkNotNullParameter(toString, "toString");
        Intrinsics.checkNotNullParameter(skipIf, "skipIf");
    }
}
