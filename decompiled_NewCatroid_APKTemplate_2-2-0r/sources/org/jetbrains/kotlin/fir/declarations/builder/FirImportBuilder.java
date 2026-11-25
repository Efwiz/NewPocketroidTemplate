package org.jetbrains.kotlin.fir.declarations.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.impl.FirImportImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirImportBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010 \u001a\u00020!R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R+\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u00168F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\f\"\u0004\b\u001f\u0010\u000e¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirImportBuilder;", "", "()V", "aliasName", "Lorg/jetbrains/kotlin/name/Name;", "getAliasName", "()Lorg/jetbrains/kotlin/name/Name;", "setAliasName", "(Lorg/jetbrains/kotlin/name/Name;)V", "aliasSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "getAliasSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setAliasSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "importedFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getImportedFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "setImportedFqName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "<set-?>", "", "isAllUnder", "()Z", "setAllUnder", "(Z)V", "isAllUnder$delegate", "Lkotlin/properties/ReadWriteProperty;", FirebaseAnalytics.Param.SOURCE, "getSource", "setSource", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirImportBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirImportBuilder.class), "isAllUnder", "isAllUnder()Z"))};
    private Name aliasName;
    private KtSourceElement aliasSource;
    private FqName importedFqName;

    /* JADX INFO: renamed from: isAllUnder$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isAllUnder = Delegates.INSTANCE.notNull();
    private KtSourceElement source;

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final FqName getImportedFqName() {
        return this.importedFqName;
    }

    public final void setImportedFqName(FqName fqName) {
        this.importedFqName = fqName;
    }

    public final boolean isAllUnder() {
        return ((Boolean) this.isAllUnder.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setAllUnder(boolean z) {
        this.isAllUnder.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public final Name getAliasName() {
        return this.aliasName;
    }

    public final void setAliasName(Name name2) {
        this.aliasName = name2;
    }

    public final KtSourceElement getAliasSource() {
        return this.aliasSource;
    }

    public final void setAliasSource(KtSourceElement ktSourceElement) {
        this.aliasSource = ktSourceElement;
    }

    public final FirImport build() {
        return new FirImportImpl(this.source, this.importedFqName, isAllUnder(), this.aliasName, this.aliasSource);
    }
}
