package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirRedeclarationPresenter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bJ\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000bJ\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\fJ\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000fH\u0002J\u0018\u0010\u0010\u001a\u00020\u0011*\u00060\u0012j\u0002`\u00132\u0006\u0010\u0005\u001a\u00020\u0014H\u0002J\u0018\u0010\u0010\u001a\u00020\u0011*\u00060\u0012j\u0002`\u00132\u0006\u0010\u0005\u001a\u00020\u0015H\u0002J\u0018\u0010\u0010\u001a\u00020\u0011*\u00060\u0012j\u0002`\u00132\u0006\u0010\u0005\u001a\u00020\u0016H\u0002J\u0018\u0010\u0017\u001a\u00020\u0011*\u00060\u0012j\u0002`\u00132\u0006\u0010\u0005\u001a\u00020\u0018H\u0002J\u0018\u0010\u0019\u001a\u00020\u0011*\u00060\u0012j\u0002`\u00132\u0006\u0010\u0005\u001a\u00020\u000bH\u0002¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirRedeclarationPresenter;", "", "()V", "represent", "", "it", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "owner", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "representClassLike", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "appendRepresentation", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/name/ClassId;", "appendRepresentationBeforeCallableId", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "appendValueParameters", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirRedeclarationPresenter {
    public static final FirRedeclarationPresenter INSTANCE = new FirRedeclarationPresenter();

    private FirRedeclarationPresenter() {
    }

    private final void appendRepresentation(StringBuilder sb, ClassId classId) {
        sb.append(classId.getPackageFqName().asString());
        sb.append('/');
        sb.append(classId.getRelativeClassName().asString());
    }

    private final void appendRepresentation(StringBuilder sb, CallableId callableId) {
        sb.append(callableId.getPackageName().asString());
        sb.append('/');
        if (callableId.getClassName() != null) {
            sb.append(callableId.getClassName());
            sb.append('.');
        }
        sb.append(callableId.getCallableName());
    }

    private final void appendRepresentation(StringBuilder sb, FirValueParameter firValueParameter) {
        if (firValueParameter.getIsVararg()) {
            sb.append("vararg ");
        }
    }

    private final void appendRepresentationBeforeCallableId(StringBuilder sb, FirCallableDeclaration firCallableDeclaration) {
        int size = firCallableDeclaration.getContextReceivers().size();
        for (int i = 0; i < size; i++) {
            sb.append(',');
        }
        sb.append('<');
        int size2 = firCallableDeclaration.getTypeParameters().size();
        for (int i2 = 0; i2 < size2; i2++) {
            sb.append(',');
        }
        sb.append('>');
        sb.append('[');
        FirReceiverParameter receiverParameter = firCallableDeclaration.getReceiverParameter();
        if (receiverParameter != null && receiverParameter.getTypeRef() != null) {
            sb.append(',');
        }
        sb.append(']');
    }

    private final void appendValueParameters(StringBuilder sb, FirSimpleFunction firSimpleFunction) {
        sb.append('(');
        Iterator<T> it = firSimpleFunction.getValueParameters().iterator();
        while (it.hasNext()) {
            INSTANCE.appendRepresentation(sb, (FirValueParameter) it.next());
            sb.append(',');
        }
        sb.append(')');
    }

    public final String represent(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof FirSimpleFunction) {
            return represent((FirSimpleFunction) declaration);
        }
        if (declaration instanceof FirRegularClass) {
            return represent((FirRegularClass) declaration);
        }
        if (declaration instanceof FirTypeAlias) {
            return represent((FirTypeAlias) declaration);
        }
        if (declaration instanceof FirProperty) {
            return represent((FirVariable) declaration);
        }
        return null;
    }

    public final String represent(FirSimpleFunction it) {
        Intrinsics.checkNotNullParameter(it, "it");
        StringBuilder sb = new StringBuilder();
        FirRedeclarationPresenter firRedeclarationPresenter = INSTANCE;
        firRedeclarationPresenter.appendRepresentationBeforeCallableId(sb, it);
        if (it.getStatus().isOperator()) {
            sb.append("operator ");
        }
        firRedeclarationPresenter.appendRepresentation(sb, it.getSymbol().getCallableId());
        firRedeclarationPresenter.appendValueParameters(sb, it);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final String represent(FirVariable it) {
        Intrinsics.checkNotNullParameter(it, "it");
        StringBuilder sb = new StringBuilder();
        FirRedeclarationPresenter firRedeclarationPresenter = INSTANCE;
        firRedeclarationPresenter.appendRepresentationBeforeCallableId(sb, it);
        firRedeclarationPresenter.appendRepresentation(sb, it.getSymbol().getCallableId());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final String represent(FirTypeAlias it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return representClassLike(it);
    }

    public final String represent(FirRegularClass it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return representClassLike(it);
    }

    private final String representClassLike(FirClassLikeDeclaration it) {
        StringBuilder sb = new StringBuilder();
        sb.append("<>[]");
        INSTANCE.appendRepresentation(sb, it.getSymbol().getClassId());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final String represent(FirConstructor it, FirRegularClass owner) {
        Intrinsics.checkNotNullParameter(it, "it");
        Intrinsics.checkNotNullParameter(owner, "owner");
        StringBuilder sb = new StringBuilder();
        int size = it.getContextReceivers().size();
        for (int i = 0; i < size; i++) {
            sb.append(',');
        }
        sb.append('<');
        int size2 = it.getTypeParameters().size();
        for (int i2 = 0; i2 < size2; i2++) {
            sb.append(',');
        }
        sb.append(">[]");
        INSTANCE.appendRepresentation(sb, owner.getSymbol().getClassId());
        sb.append('(');
        Iterator<T> it2 = it.getValueParameters().iterator();
        while (it2.hasNext()) {
            INSTANCE.appendRepresentation(sb, (FirValueParameter) it2.next());
            sb.append(',');
        }
        sb.append(')');
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
