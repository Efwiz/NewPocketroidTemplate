package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: IrInterningService.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000bR!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\t\u001a\u0004\b\f\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "", "()V", "names", "Lorg/jetbrains/kotlin/it/unimi/dsi/fastutil/objects/ObjectOpenHashSet;", "Lorg/jetbrains/kotlin/name/Name;", "getNames", "()Lit/unimi/dsi/fastutil/objects/ObjectOpenHashSet;", "names$delegate", "Lkotlin/Lazy;", "strings", "", "getStrings", "strings$delegate", LineReader.CLEAR, "", "name", "string", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInterningService {

    /* JADX INFO: renamed from: strings$delegate, reason: from kotlin metadata */
    private final Lazy strings = LazyKt.lazy(new Function0<ObjectOpenHashSet<String>>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrInterningService$strings$2
        @Override // kotlin.jvm.functions.Function0
        public final ObjectOpenHashSet<String> invoke() {
            return new ObjectOpenHashSet<>();
        }
    });

    /* JADX INFO: renamed from: names$delegate, reason: from kotlin metadata */
    private final Lazy names = LazyKt.lazy(new Function0<ObjectOpenHashSet<Name>>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrInterningService$names$2
        @Override // kotlin.jvm.functions.Function0
        public final ObjectOpenHashSet<Name> invoke() {
            return new ObjectOpenHashSet<>();
        }
    });

    private final ObjectOpenHashSet<String> getStrings() {
        return (ObjectOpenHashSet) this.strings.getValue();
    }

    private final ObjectOpenHashSet<Name> getNames() {
        return (ObjectOpenHashSet) this.names.getValue();
    }

    public final String string(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        String strAddOrGet = getStrings().addOrGet(string);
        Intrinsics.checkNotNullExpressionValue(strAddOrGet, "addOrGet(...)");
        return strAddOrGet;
    }

    public final Name name(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Name nameAddOrGet = getNames().addOrGet(name2);
        Intrinsics.checkNotNullExpressionValue(nameAddOrGet, "addOrGet(...)");
        return nameAddOrGet;
    }

    public final void clear() {
        getStrings().clear();
        getNames().clear();
    }
}
