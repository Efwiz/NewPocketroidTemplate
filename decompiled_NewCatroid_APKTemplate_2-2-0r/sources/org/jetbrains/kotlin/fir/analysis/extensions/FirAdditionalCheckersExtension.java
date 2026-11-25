package org.jetbrains.kotlin.fir.analysis.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.LanguageVersionSettingsCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.type.TypeCheckers;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.extensions.FirExtensionPointName;

/* JADX INFO: compiled from: FirAdditionalCheckersExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001d\u001eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0019\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/extensions/FirAdditionalCheckersExtension;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "declarationCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "getDeclarationCheckers", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "expressionCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "getExpressionCheckers", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "extensionType", "Lkotlin/reflect/KClass;", "getExtensionType", "()Lkotlin/reflect/KClass;", "languageVersionSettingsCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "getLanguageVersionSettingsCheckers", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "name", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getName", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "typeCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "getTypeCheckers", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "Companion", "Factory", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAdditionalCheckersExtension extends FirExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FirExtensionPointName NAME = new FirExtensionPointName("ExtensionCheckers");
    private final DeclarationCheckers declarationCheckers;
    private final ExpressionCheckers expressionCheckers;
    private final LanguageVersionSettingsCheckers languageVersionSettingsCheckers;
    private final TypeCheckers typeCheckers;

    /* JADX INFO: compiled from: FirAdditionalCheckersExtension.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/extensions/FirAdditionalCheckersExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "Lorg/jetbrains/kotlin/fir/analysis/extensions/FirAdditionalCheckersExtension;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Factory extends FirExtension.Factory<FirAdditionalCheckersExtension> {
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final KClass<? extends FirExtension> getExtensionType() {
        return Reflection.getOrCreateKotlinClass(FirAdditionalCheckersExtension.class);
    }

    /* JADX INFO: compiled from: FirAdditionalCheckersExtension.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/extensions/FirAdditionalCheckersExtension$Companion;", "", "()V", "NAME", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getNAME", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirExtensionPointName getNAME() {
            return FirAdditionalCheckersExtension.NAME;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAdditionalCheckersExtension(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        this.declarationCheckers = DeclarationCheckers.INSTANCE.getEMPTY();
        this.expressionCheckers = ExpressionCheckers.INSTANCE.getEMPTY();
        this.typeCheckers = TypeCheckers.INSTANCE.getEMPTY();
        this.languageVersionSettingsCheckers = LanguageVersionSettingsCheckers.INSTANCE.getEMPTY();
    }

    public DeclarationCheckers getDeclarationCheckers() {
        return this.declarationCheckers;
    }

    public ExpressionCheckers getExpressionCheckers() {
        return this.expressionCheckers;
    }

    public TypeCheckers getTypeCheckers() {
        return this.typeCheckers;
    }

    public LanguageVersionSettingsCheckers getLanguageVersionSettingsCheckers() {
        return this.languageVersionSettingsCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final FirExtensionPointName getName() {
        return NAME;
    }
}
