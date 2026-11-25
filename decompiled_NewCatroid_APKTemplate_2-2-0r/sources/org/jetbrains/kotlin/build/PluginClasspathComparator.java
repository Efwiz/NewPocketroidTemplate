package org.jetbrains.kotlin.build;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PluginClasspathsUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0006\u0010\u0010\u001a\u00020\u0011R-\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\b0\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR-\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\b0\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\f\u001a\u0004\b\u000e\u0010\n¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/build/PluginClasspathComparator;", "", "old", "", PsiKeyword.NEW, "(Ljava/lang/String;Ljava/lang/String;)V", "newPluginClasspath", "", "Lkotlin/Pair;", "getNewPluginClasspath", "()Ljava/util/List;", "newPluginClasspath$delegate", "Lkotlin/Lazy;", "oldPluginClasspath", "getOldPluginClasspath", "oldPluginClasspath$delegate", Namer.EQUALS_METHOD_NAME, "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PluginClasspathComparator {

    /* JADX INFO: renamed from: newPluginClasspath$delegate, reason: from kotlin metadata */
    private final Lazy newPluginClasspath;

    /* JADX INFO: renamed from: oldPluginClasspath$delegate, reason: from kotlin metadata */
    private final Lazy oldPluginClasspath;

    public PluginClasspathComparator(final String old, final String str) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(str, "new");
        this.oldPluginClasspath = LazyKt.lazy(new Function0<List<? extends Pair<? extends String, ? extends String>>>() { // from class: org.jetbrains.kotlin.build.PluginClasspathComparator$oldPluginClasspath$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends Pair<? extends String, ? extends String>> invoke() {
                return PluginClasspath.Companion.deserializeWithHashes(old);
            }
        });
        this.newPluginClasspath = LazyKt.lazy(new Function0<List<? extends Pair<? extends String, ? extends String>>>() { // from class: org.jetbrains.kotlin.build.PluginClasspathComparator$newPluginClasspath$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends Pair<? extends String, ? extends String>> invoke() {
                return PluginClasspath.Companion.deserializeWithHashes(str);
            }
        });
    }

    private final List<Pair<String, String>> getOldPluginClasspath() {
        return (List) this.oldPluginClasspath.getValue();
    }

    private final List<Pair<String, String>> getNewPluginClasspath() {
        return (List) this.newPluginClasspath.getValue();
    }

    public final boolean equals() {
        return Intrinsics.areEqual(getOldPluginClasspath(), getNewPluginClasspath());
    }
}
