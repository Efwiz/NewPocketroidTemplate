package org.jetbrains.kotlin.analysis.providers;

import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.psi.KtCodeFragment;

/* JADX INFO: compiled from: ForeignValueProviderService.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u001c\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/ForeignValueProviderService;", "", "getForeignValues", "", "", "codeFragment", "Lorg/jetbrains/kotlin/psi/KtCodeFragment;", "Companion", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ForeignValueProviderService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    Map<String, String> getForeignValues(KtCodeFragment codeFragment);

    /* JADX INFO: compiled from: ForeignValueProviderService.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/ForeignValueProviderService$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analysis/providers/ForeignValueProviderService;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final ForeignValueProviderService getInstance() {
            return (ForeignValueProviderService) ApplicationManager.getApplication().getService(ForeignValueProviderService.class);
        }
    }
}
