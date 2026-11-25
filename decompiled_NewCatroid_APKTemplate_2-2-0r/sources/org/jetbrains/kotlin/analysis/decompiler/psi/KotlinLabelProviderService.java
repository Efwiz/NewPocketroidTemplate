package org.jetbrains.kotlin.analysis.decompiler.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: compiled from: KotlinLabelProviderService.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinLabelProviderService;", "", "()V", "getLabelForBuiltInFileType", "", "Companion", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinLabelProviderService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract String getLabelForBuiltInFileType();

    /* JADX INFO: compiled from: KotlinLabelProviderService.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinLabelProviderService$Companion;", "", "()V", "getService", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinLabelProviderService;", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinLabelProviderService getService() {
            return (KotlinLabelProviderService) ApplicationManager.getApplication().getServiceIfCreated(KotlinLabelProviderService.class);
        }
    }
}
