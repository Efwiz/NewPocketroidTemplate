package kotlin;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlin-stdlib-1.8.20.jar:kotlin/PreconditionsKt__AssertionsJVMKt.class
  classes11.dex
 */
/* JADX INFO: compiled from: AssertionsJVM.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {PsiKeyword.ASSERT, "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", PathUtil.KOTLIN_JAVA_STDLIB_NAME}, k = 5, mv = {1, 9, 0}, xi = 49, xs = "kotlin/PreconditionsKt")
class PreconditionsKt__AssertionsJVMKt {
    /* JADX INFO: renamed from: assert, reason: not valid java name */
    private static final void m2895assert(boolean z) {
    }

    /* JADX INFO: renamed from: assert, reason: not valid java name */
    private static final void m2896assert(boolean z, Function0<? extends Object> lazyMessage) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
    }
}
