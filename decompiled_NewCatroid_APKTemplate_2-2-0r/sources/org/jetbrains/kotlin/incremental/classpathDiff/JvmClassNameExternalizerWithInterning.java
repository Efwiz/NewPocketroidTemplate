package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.DataInput;
import java.io.DataOutput;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.incremental.storage.JvmClassNameExternalizer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: ClasspathSnapshotSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J(\u0010\u000f\u001a\u00020\u00102\r\b\u0001\u0010\u0011\u001a\u00070\u0012¢\u0006\u0002\b\u00072\u000e\u0010\u0013\u001a\n \u0006*\u0004\u0018\u00010\u00020\u0002H\u0096\u0001R.\u0010\u0004\u001a\u0015\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00020\u00020\u0005¢\u0006\u0002\b\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/JvmClassNameExternalizerWithInterning;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "()V", "jvmClassNameInterner", "Lorg/jetbrains/kotlin/com/intellij/util/containers/Interner;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/annotations/NotNull;", "getJvmClassNameInterner", "()Lcom/intellij/util/containers/Interner;", "jvmClassNameInterner$delegate", "Lkotlin/Lazy;", "read", "input", "Ljava/io/DataInput;", "save", "", "p0", "Ljava/io/DataOutput;", "p1", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JvmClassNameExternalizerWithInterning implements DataExternalizer<JvmClassName> {
    public static final JvmClassNameExternalizerWithInterning INSTANCE = new JvmClassNameExternalizerWithInterning();

    /* JADX INFO: renamed from: jvmClassNameInterner$delegate, reason: from kotlin metadata */
    private static final Lazy jvmClassNameInterner = LazyKt.lazy(new Function0<Interner<JvmClassName>>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.JvmClassNameExternalizerWithInterning$jvmClassNameInterner$2
        @Override // kotlin.jvm.functions.Function0
        public final Interner<JvmClassName> invoke() {
            return Interner.createWeakInterner();
        }
    });
    private final /* synthetic */ JvmClassNameExternalizer $$delegate_0 = JvmClassNameExternalizer.INSTANCE;

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput p0, JvmClassName p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        JvmClassNameExternalizer jvmClassNameExternalizer = this.$$delegate_0;
        Intrinsics.checkNotNull(p1);
        jvmClassNameExternalizer.save(p0, p1);
    }

    private JvmClassNameExternalizerWithInterning() {
    }

    private final Interner<JvmClassName> getJvmClassNameInterner() {
        return (Interner) jvmClassNameInterner.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public JvmClassName read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        JvmClassName jvmClassNameIntern = getJvmClassNameInterner().intern(JvmClassNameExternalizer.INSTANCE.read(input));
        Intrinsics.checkNotNullExpressionValue(jvmClassNameIntern, "intern(...)");
        return jvmClassNameIntern;
    }
}
