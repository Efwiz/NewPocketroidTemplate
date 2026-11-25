package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "()V", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "getLookupTag", "()Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeLookupTagBasedType extends ConeSimpleKotlinType {
    public abstract ConeClassifierLookupTag getLookupTag();

    public ConeLookupTagBasedType() {
        super(null);
    }
}
