package org.jetbrains.kotlin.fir.symbols;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirBasedSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0013\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00028\u0000¢\u0006\u0002\u0010+R\u0012\u0010\u0005\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00028\u00008FX\u0087\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u0004\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\b8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u000bR\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\b8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u000bR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0\b8F¢\u0006\u0006\u001a\u0004\b!\u0010\u000bR\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\t0\b8F¢\u0006\u0006\u001a\u0004\b#\u0010\u000bR\u0013\u0010$\u001a\u0004\u0018\u00010%8F¢\u0006\u0006\u001a\u0004\b&\u0010'¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "()V", "_fir", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "fir", "getFir$annotations", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isBound", "", "()Z", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "resolvedAnnotationClassIds", "Lorg/jetbrains/kotlin/name/ClassId;", "getResolvedAnnotationClassIds", "resolvedAnnotationsWithArguments", "getResolvedAnnotationsWithArguments", "resolvedAnnotationsWithClassIds", "getResolvedAnnotationsWithClassIds", "resolvedCompilerAnnotationsWithClassIds", "getResolvedCompilerAnnotationsWithClassIds", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "bind", "", "e", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirBasedSymbol<E extends FirDeclaration> implements DeclarationSymbolMarker {
    private E _fir;

    @SymbolInternals
    public static /* synthetic */ void getFir$annotations() {
    }

    public final E getFir() {
        E e = this._fir;
        if (e != null) {
            return e;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Fir is not initialized for " + Reflection.getOrCreateKotlinClass(getClass()), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirSymbolIdEntry(exceptionAttachmentBuilder, NativeSymbol.TYPE_NAME, this);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public final void bind(E e) {
        Intrinsics.checkNotNullParameter(e, "e");
        this._fir = e;
    }

    public final boolean isBound() {
        return this._fir != null;
    }

    public final FirDeclarationOrigin getOrigin() {
        return getFir().getOrigin();
    }

    public final KtSourceElement getSource() {
        return getFir().getSource();
    }

    public final FirModuleData getModuleData() {
        return getFir().getModuleData();
    }

    public final List<FirAnnotation> getAnnotations() {
        return getFir().getAnnotations();
    }

    public final List<FirAnnotation> getResolvedAnnotationsWithArguments() {
        return FirBasedSymbolKt.resolvedAnnotationsWithArguments(getFir(), this);
    }

    public final List<FirAnnotation> getResolvedAnnotationsWithClassIds() {
        return FirBasedSymbolKt.resolvedAnnotationsWithClassIds(getFir(), this);
    }

    public final List<FirAnnotation> getResolvedCompilerAnnotationsWithClassIds() {
        return FirBasedSymbolKt.resolvedCompilerRequiredAnnotations(getFir(), this);
    }

    public final List<ClassId> getResolvedAnnotationClassIds() {
        return FirBasedSymbolKt.resolvedAnnotationClassIds(getFir(), this);
    }
}
