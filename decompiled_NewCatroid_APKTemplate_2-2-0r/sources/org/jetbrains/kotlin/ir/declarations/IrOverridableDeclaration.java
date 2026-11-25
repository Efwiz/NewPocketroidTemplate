package org.jetbrains.kotlin.ir.declarations;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrOverridableDeclaration.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\b\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003R\u0018\u0010\u0004\u001a\u00020\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nX¦\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "isFakeOverride", "", "()Z", "setFakeOverride", "(Z)V", "overriddenSymbols", "", "getOverriddenSymbols", "()Ljava/util/List;", "setOverriddenSymbols", "(Ljava/util/List;)V", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrOverridableDeclaration<S extends IrSymbol> extends IrOverridableMember {
    List<S> getOverriddenSymbols();

    S getSymbol();

    boolean isFakeOverride();

    void setFakeOverride(boolean z);

    void setOverriddenSymbols(List<? extends S> list);
}
