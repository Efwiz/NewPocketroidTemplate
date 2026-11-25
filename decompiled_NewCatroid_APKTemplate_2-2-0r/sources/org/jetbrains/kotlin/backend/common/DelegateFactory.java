package org.jetbrains.kotlin.backend.common;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: Mappings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J.\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\b\b\u0000\u0010\u0004*\u00020\u0006\"\u000e\b\u0001\u0010\u0005*\b\u0012\u0004\u0012\u00020\u00060\u0007H&J(\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\b\b\u0000\u0010\u0004*\u00020\u0006\"\b\b\u0001\u0010\u0005*\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/DelegateFactory;", "", "newDeclarationToDeclarationCollectionMapping", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "", "newDeclarationToDeclarationMapping", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DelegateFactory {
    <K extends IrDeclaration, V extends Collection<? extends IrDeclaration>> Mapping.Delegate<K, V> newDeclarationToDeclarationCollectionMapping();

    <K extends IrDeclaration, V extends IrDeclaration> Mapping.Delegate<K, V> newDeclarationToDeclarationMapping();
}
