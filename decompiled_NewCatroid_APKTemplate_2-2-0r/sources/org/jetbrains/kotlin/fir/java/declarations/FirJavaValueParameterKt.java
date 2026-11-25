package org.jetbrains.kotlin.fir.java.declarations;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirJavaValueParameter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a%\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u001a:\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\n"}, d2 = {"buildJavaValueParameter", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaValueParameter;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaValueParameterBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildJavaValueParameterCopy", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "original", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirJavaValueParameterKt {
    public static final FirJavaValueParameter buildJavaValueParameter(Function1<? super FirJavaValueParameterBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirJavaValueParameterBuilder firJavaValueParameterBuilder = new FirJavaValueParameterBuilder();
        init.invoke(firJavaValueParameterBuilder);
        return firJavaValueParameterBuilder.build();
    }

    public static final FirValueParameter buildJavaValueParameterCopy(FirValueParameter original, Function1<? super FirJavaValueParameterBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirJavaValueParameterBuilder firJavaValueParameterBuilder = new FirJavaValueParameterBuilder();
        firJavaValueParameterBuilder.setSource(original.getSource());
        firJavaValueParameterBuilder.setModuleData(original.getModuleData());
        firJavaValueParameterBuilder.setAttributes(original.getAttributes().copy());
        firJavaValueParameterBuilder.setFromSource(original.getOrigin().getFromSource());
        firJavaValueParameterBuilder.setReturnTypeRef(original.getReturnTypeRef());
        firJavaValueParameterBuilder.setName(original.getName());
        firJavaValueParameterBuilder.setAnnotationBuilder(new AnonymousClass2(original.getAnnotations()));
        firJavaValueParameterBuilder.setDefaultValue(original.getDefaultValue());
        firJavaValueParameterBuilder.setContainingFunctionSymbol(original.getContainingFunctionSymbol());
        firJavaValueParameterBuilder.setVararg(original.getIsVararg());
        init.invoke(firJavaValueParameterBuilder);
        return firJavaValueParameterBuilder.build();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.java.declarations.FirJavaValueParameterKt$buildJavaValueParameterCopy$2, reason: invalid class name */
    /* JADX INFO: compiled from: FirJavaValueParameter.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 176)
    public static final class AnonymousClass2 extends Lambda implements Function0<List<? extends FirAnnotation>> {
        final /* synthetic */ List<FirAnnotation> $annotations;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass2(List<? extends FirAnnotation> list) {
            super(0);
            this.$annotations = list;
        }

        @Override // kotlin.jvm.functions.Function0
        public final List<? extends FirAnnotation> invoke() {
            return this.$annotations;
        }
    }
}
