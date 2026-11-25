package org.jetbrains.kotlin.types.checker;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.OverridingUtil;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eR\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker;", "kotlinTypePreparator", "Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;", "getKotlinTypePreparator", "()Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getKotlinTypeRefiner", "()Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "overridingUtil", "Lorg/jetbrains/kotlin/resolve/OverridingUtil;", "getOverridingUtil", "()Lorg/jetbrains/kotlin/resolve/OverridingUtil;", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NewKotlinTypeChecker extends KotlinTypeChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    KotlinTypePreparator getKotlinTypePreparator();

    KotlinTypeRefiner getKotlinTypeRefiner();

    OverridingUtil getOverridingUtil();

    /* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker$Companion;", "", "()V", "Default", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeCheckerImpl;", "getDefault", "()Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeCheckerImpl;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final NewKotlinTypeCheckerImpl Default = new NewKotlinTypeCheckerImpl(KotlinTypeRefiner.Default.INSTANCE, null, 2, 0 == true ? 1 : 0);

        private Companion() {
        }

        public final NewKotlinTypeCheckerImpl getDefault() {
            return Default;
        }
    }
}
