package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProvider;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirCompanionGenerationProcessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\f\u001a\u00020\rH\u0002J)\u0010\u0010\u001a\u0002H\u0011\"\b\b\u0000\u0010\u0011*\u00020\u00122\u0006\u0010\u0013\u001a\u0002H\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u0015J\u001a\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirCompanionGenerationTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "generatedDeclarationProvider", "Lorg/jetbrains/kotlin/fir/extensions/FirSwitchableExtensionDeclarationsSymbolProvider;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "generateAndUpdateCompanion", "", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "generateCompanion", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Void;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "transformRegularClass", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCompanionGenerationTransformer extends FirTransformer {
    private final FirSwitchableExtensionDeclarationsSymbolProvider generatedDeclarationProvider;
    private final FirSession session;

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Void data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return element;
    }

    public FirCompanionGenerationTransformer(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.generatedDeclarationProvider = FirSwitchableExtensionDeclarationsSymbolProviderKt.getGeneratedDeclarationsSymbolProvider(session);
    }

    public final FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, Void data) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (this.generatedDeclarationProvider == null) {
            return file;
        }
        try {
            return file.transformDeclarations(this, data);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, Void data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        generateAndUpdateCompanion(regularClass);
        return regularClass.transformDeclarations((FirTransformer<? super Void>) this, data);
    }

    public final void generateAndUpdateCompanion(FirRegularClass regularClass) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        FirRegularClassSymbol firRegularClassSymbolGenerateCompanion = generateCompanion(regularClass);
        if (firRegularClassSymbolGenerateCompanion != null) {
            regularClass.replaceCompanionObjectSymbol(firRegularClassSymbolGenerateCompanion);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirRegularClassSymbol generateCompanion(FirRegularClass regularClass) {
        FirClassLikeSymbol<?> classLikeSymbolByClassId;
        if (this.generatedDeclarationProvider == null) {
            return null;
        }
        FirRegularClass firRegularClass = regularClass;
        if (firRegularClass.getSymbol().getClassId().isLocal()) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(this.session, regularClass);
            if (firNestedClassifierScopeNestedClassifierScope != null) {
                Name name2 = SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT;
                final Function1<FirClassifierSymbol<?>, Unit> function1 = new Function1<FirClassifierSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirCompanionGenerationTransformer$generateCompanion$generatedCompanion$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol) {
                        invoke2(firClassifierSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirClassifierSymbol<?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        if ((it instanceof FirClassLikeSymbol) && it.getOrigin().getGenerated()) {
                            objectRef.element = it;
                        }
                    }
                };
                firNestedClassifierScopeNestedClassifierScope.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirCompanionGenerationTransformer$generateCompanion$$inlined$processClassifiersByName$1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                        invoke2(firClassifierSymbol, coneSubstitutor);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor coneSubstitutor) {
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        Intrinsics.checkNotNullParameter(coneSubstitutor, "<anonymous parameter 1>");
                        function1.invoke(symbol);
                    }
                });
            }
            classLikeSymbolByClassId = (FirClassLikeSymbol) objectRef.element;
        } else {
            ClassId classIdCreateNestedClassId = FirDeclarationUtilKt.getClassId(firRegularClass).createNestedClassId(SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT);
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
            classLikeSymbolByClassId = this.generatedDeclarationProvider.getClassLikeSymbolByClassId(classIdCreateNestedClassId);
            if (classLikeSymbolByClassId == null || !classLikeSymbolByClassId.getOrigin().getGenerated()) {
                classLikeSymbolByClassId = null;
            }
        }
        if (classLikeSymbolByClassId == null) {
            return null;
        }
        if (classLikeSymbolByClassId instanceof FirRegularClassSymbol) {
            if (regularClass.getCompanionObjectSymbol() != null) {
                throw new IllegalStateException(("Plugin generated companion object for class " + regularClass + ", but it is already present in class").toString());
            }
            return (FirRegularClassSymbol) classLikeSymbolByClassId;
        }
        throw new IllegalStateException("Plugin generated non regular class as companion object".toString());
    }
}
