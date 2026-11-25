package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DeclarationSignatureAnonymousTypeTransformer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DeclarationSignatureAnonymousTypeTransformer;", "", "transformAnonymousType", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", ModuleXmlParser.TYPE, "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DeclarationSignatureAnonymousTypeTransformer {
    KotlinType transformAnonymousType(DeclarationDescriptorWithVisibility descriptor, KotlinType type);
}
