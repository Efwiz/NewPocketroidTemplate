package org.jetbrains.kotlin.fir.lightTree.fir;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.chunk.TypeBlock;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirGenerationKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.lightTree.converter.ConverterUtilKt;
import org.jetbrains.kotlin.fir.lightTree.fir.modifier.Modifier;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: DestructuringDeclaration.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u0011\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\bHÆ\u0003J\t\u0010\u001a\u001a\u00020\nHÆ\u0003J\t\u0010\u001b\u001a\u00020\fHÆ\u0003JC\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fHÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u00032\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020 HÖ\u0001J\u000e\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020#J\t\u0010$\u001a\u00020%HÖ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/DestructuringDeclaration;", "", "isVar", "", TypeBlock.NAME_entries, "", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "initializer", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "modifier", "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;", "(ZLjava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;)V", "getEntries", "()Ljava/util/List;", "getInitializer", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "()Z", "getModifier", "()Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toFirDestructingDeclaration", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "toString", "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DestructuringDeclaration {
    private final List<FirVariable> entries;
    private final FirExpression initializer;
    private final boolean isVar;
    private final Modifier modifier;
    private final KtSourceElement source;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DestructuringDeclaration copy$default(DestructuringDeclaration destructuringDeclaration, boolean z, List list, FirExpression firExpression, KtSourceElement ktSourceElement, Modifier modifier, int i, Object obj) {
        if ((i & 1) != 0) {
            z = destructuringDeclaration.isVar;
        }
        if ((i & 2) != 0) {
            list = destructuringDeclaration.entries;
        }
        List list2 = list;
        if ((i & 4) != 0) {
            firExpression = destructuringDeclaration.initializer;
        }
        FirExpression firExpression2 = firExpression;
        if ((i & 8) != 0) {
            ktSourceElement = destructuringDeclaration.source;
        }
        KtSourceElement ktSourceElement2 = ktSourceElement;
        if ((i & 16) != 0) {
            modifier = destructuringDeclaration.modifier;
        }
        return destructuringDeclaration.copy(z, list2, firExpression2, ktSourceElement2, modifier);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final boolean getIsVar() {
        return this.isVar;
    }

    public final List<FirVariable> component2() {
        return this.entries;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final FirExpression getInitializer() {
        return this.initializer;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final Modifier getModifier() {
        return this.modifier;
    }

    public final DestructuringDeclaration copy(boolean isVar, List<? extends FirVariable> entries, FirExpression initializer, KtSourceElement source, Modifier modifier) {
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        return new DestructuringDeclaration(isVar, entries, initializer, source, modifier);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DestructuringDeclaration)) {
            return false;
        }
        DestructuringDeclaration destructuringDeclaration = (DestructuringDeclaration) other;
        return this.isVar == destructuringDeclaration.isVar && Intrinsics.areEqual(this.entries, destructuringDeclaration.entries) && Intrinsics.areEqual(this.initializer, destructuringDeclaration.initializer) && Intrinsics.areEqual(this.source, destructuringDeclaration.source) && Intrinsics.areEqual(this.modifier, destructuringDeclaration.modifier);
    }

    public int hashCode() {
        return (((((((Boolean.hashCode(this.isVar) * 31) + this.entries.hashCode()) * 31) + this.initializer.hashCode()) * 31) + this.source.hashCode()) * 31) + this.modifier.hashCode();
    }

    public String toString() {
        return "DestructuringDeclaration(isVar=" + this.isVar + ", entries=" + this.entries + ", initializer=" + this.initializer + ", source=" + this.source + ", modifier=" + this.modifier + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DestructuringDeclaration(boolean z, List<? extends FirVariable> entries, FirExpression initializer, KtSourceElement source, Modifier modifier) {
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        this.isVar = z;
        this.entries = entries;
        this.initializer = initializer;
        this.source = source;
        this.modifier = modifier;
    }

    public final boolean isVar() {
        return this.isVar;
    }

    public final List<FirVariable> getEntries() {
        return this.entries;
    }

    public final FirExpression getInitializer() {
        return this.initializer;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final Modifier getModifier() {
        return this.modifier;
    }

    public final FirExpression toFirDestructingDeclaration(FirModuleData moduleData) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        return ConverterUtilKt.generateDestructuringBlock(moduleData, this, FirGenerationKt.generateTemporaryVariable$default(moduleData, this.source, SpecialNames.DESTRUCT, this.initializer, null, this.modifier.getAnnotations(), 16, null), true);
    }
}
