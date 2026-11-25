package org.jetbrains.kotlin.incremental;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.incremental.components.LocationInfo;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.Position;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiFactoryKt;

/* JADX INFO: compiled from: KotlinLookupLocation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/KotlinLookupLocation;", "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/psi/KtElement;)V", "cachedLocation", "Lorg/jetbrains/kotlin/incremental/components/LocationInfo;", "getCachedLocation", "()Lorg/jetbrains/kotlin/incremental/components/LocationInfo;", "cachedLocation$delegate", "Lkotlin/Lazy;", "getElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", FirebaseAnalytics.Param.LOCATION, "getLocation", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinLookupLocation implements LookupLocation {

    /* JADX INFO: renamed from: cachedLocation$delegate, reason: from kotlin metadata */
    private final Lazy cachedLocation;
    private final KtElement element;

    public KotlinLookupLocation(KtElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        this.element = element;
        this.cachedLocation = LazyKt.lazy(new Function0<KotlinLookupLocation$cachedLocation$2.AnonymousClass1>() { // from class: org.jetbrains.kotlin.incremental.KotlinLookupLocation$cachedLocation$2
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r1v2, types: [org.jetbrains.kotlin.incremental.KotlinLookupLocation$cachedLocation$2$1] */
            @Override // kotlin.jvm.functions.Function0
            public final AnonymousClass1 invoke() {
                KtFile containingKtFile = this.this$0.getElement().getContainingKtFile();
                Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
                if (KtPsiFactoryKt.getDoNotAnalyze(containingKtFile) != null) {
                    return null;
                }
                return new LocationInfo(this.this$0) { // from class: org.jetbrains.kotlin.incremental.KotlinLookupLocation$cachedLocation$2.1
                    private final String filePath;
                    final /* synthetic */ KotlinLookupLocation this$0;

                    {
                        this.this$0 = kotlinLookupLocation;
                        this.filePath = this.$containingKtFile.getVirtualFilePath();
                    }

                    @Override // org.jetbrains.kotlin.incremental.components.LocationInfo
                    public String getFilePath() {
                        return this.filePath;
                    }

                    @Override // org.jetbrains.kotlin.incremental.components.LocationInfo
                    public Position getPosition() {
                        PsiDiagnosticUtils.LineAndColumn lineAndColumnInPsiFile = DiagnosticUtils.getLineAndColumnInPsiFile(this.$containingKtFile, this.this$0.getElement().getTextRange());
                        return new Position(lineAndColumnInPsiFile.getLine(), lineAndColumnInPsiFile.getColumn());
                    }
                };
            }
        });
    }

    public final KtElement getElement() {
        return this.element;
    }

    public final LocationInfo getCachedLocation() {
        return (LocationInfo) this.cachedLocation.getValue();
    }

    @Override // org.jetbrains.kotlin.incremental.components.LookupLocation
    public LocationInfo getLocation() {
        return getCachedLocation();
    }
}
