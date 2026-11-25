package org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.ir.DescriptorBasedUtilsKt;
import org.jetbrains.kotlin.backend.common.serialization.CompatibilityMode;
import org.jetbrains.kotlin.backend.common.serialization.DeclarationTable;
import org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JsIrFileSerializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 &2\u00020\u0001:\u0001&Ba\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\r\u0012\u0006\u0010\u0011\u001a\u00020\r\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\u0002\u0010\u0015J\u0010\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\f\u0010\"\u001a\u00020\u0014*\u00020#H\u0002J\f\u0010$\u001a\u00020\r*\u00020\u001cH\u0002J\f\u0010%\u001a\u00020\r*\u00020\u001cH\u0002R\u0018\u0010\u0016\u001a\u00020\u0014*\u00020\u00178BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrFileSerializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer;", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "declarationTable", "Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;", "expectDescriptorToSymbol", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "compatibilityMode", "Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "skipExpects", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "bodiesOnlyForInlines", "normalizeAbsolutePaths", "sourceBaseDirs", "", "", "(Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;Ljava/util/Map;Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;ZLorg/jetbrains/kotlin/config/LanguageVersionSettings;ZZLjava/util/Collection;)V", "exportedName", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getExportedName", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;)Ljava/lang/String;", "backendSpecificExplicitRoot", "node", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "backendSpecificExplicitRootExclusion", "backendSpecificMetadata", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$FileBackendSpecificMetadata;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getSingleConstStringArgument", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "isExportIgnoreDeclaration", "isExportedDeclaration", "Companion", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrFileSerializer extends IrFileSerializer {
    private static final FqName JS_NAME_FQN = new FqName("kotlin.js.JsName");
    private static final FqName JS_EXPORT_FQN = new FqName("kotlin.js.JsExport");
    private static final FqName JS_EXPORT_IGNORE_FQN = new FqName("kotlin.js.JsExport.Ignore");

    public /* synthetic */ JsIrFileSerializer(IrMessageLogger irMessageLogger, DeclarationTable declarationTable, Map map, CompatibilityMode compatibilityMode, boolean z, LanguageVersionSettings languageVersionSettings, boolean z2, boolean z3, Collection collection, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irMessageLogger, declarationTable, map, compatibilityMode, z, languageVersionSettings, (i & 64) != 0 ? false : z2, z3, collection);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsIrFileSerializer(IrMessageLogger messageLogger, DeclarationTable declarationTable, Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol, CompatibilityMode compatibilityMode, boolean z, LanguageVersionSettings languageVersionSettings, boolean z2, boolean z3, Collection<String> sourceBaseDirs) {
        super(messageLogger, declarationTable, expectDescriptorToSymbol, compatibilityMode, languageVersionSettings, z2, z, z3, sourceBaseDirs);
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(declarationTable, "declarationTable");
        Intrinsics.checkNotNullParameter(expectDescriptorToSymbol, "expectDescriptorToSymbol");
        Intrinsics.checkNotNullParameter(compatibilityMode, "compatibilityMode");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sourceBaseDirs, "sourceBaseDirs");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isExportedDeclaration(IrAnnotationContainer irAnnotationContainer) {
        return AdditionalIrUtilsKt.hasAnnotation(irAnnotationContainer.getAnnotations(), JS_EXPORT_FQN) && !isExportIgnoreDeclaration(irAnnotationContainer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isExportIgnoreDeclaration(IrAnnotationContainer irAnnotationContainer) {
        return AdditionalIrUtilsKt.hasAnnotation(irAnnotationContainer.getAnnotations(), JS_EXPORT_IGNORE_FQN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getExportedName(IrDeclarationWithName irDeclarationWithName) {
        String singleConstStringArgument;
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irDeclarationWithName, JS_NAME_FQN);
        if (annotation != null && (singleConstStringArgument = getSingleConstStringArgument(annotation)) != null) {
            return singleConstStringArgument;
        }
        String string = irDeclarationWithName.getName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer
    public boolean backendSpecificExplicitRoot(IrAnnotationContainer node) {
        Intrinsics.checkNotNullParameter(node, "node");
        return isExportedDeclaration(node);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer
    public boolean backendSpecificExplicitRootExclusion(IrAnnotationContainer node) {
        Intrinsics.checkNotNullParameter(node, "node");
        return isExportIgnoreDeclaration(node);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer
    public IrFileSerializer.FileBackendSpecificMetadata backendSpecificMetadata(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        final boolean zHasAnnotation = AdditionalIrUtilsKt.hasAnnotation(irFile.getAnnotations(), JS_EXPORT_FQN);
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irFile.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrFileSerializer$backendSpecificMetadata$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrDeclarationWithName);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return new JsIrFileMetadata(SequencesKt.toList(SequencesKt.map(SequencesKt.filter(SequencesKt.filter(sequenceFilter, new Function1<IrDeclarationWithName, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrFileSerializer$backendSpecificMetadata$exportedNames$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclarationWithName it) {
                boolean zIsExportedDeclaration;
                Intrinsics.checkNotNullParameter(it, "it");
                if (zHasAnnotation) {
                    zIsExportedDeclaration = !this.isExportIgnoreDeclaration(it);
                } else {
                    zIsExportedDeclaration = this.isExportedDeclaration(it);
                }
                return Boolean.valueOf(zIsExportedDeclaration);
            }
        }), new Function1<IrDeclarationWithName, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrFileSerializer$backendSpecificMetadata$exportedNames$2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclarationWithName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationWithName irDeclarationWithName = it;
                return Boolean.valueOf((IrUtilsKt.isEffectivelyExternal(irDeclarationWithName) || DescriptorBasedUtilsKt.isExpect(irDeclarationWithName)) ? false : true);
            }
        }), new Function1<IrDeclarationWithName, String>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrFileSerializer$backendSpecificMetadata$exportedNames$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(IrDeclarationWithName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.getExportedName(it);
            }
        })));
    }

    private final String getSingleConstStringArgument(IrConstructorCall irConstructorCall) {
        IrExpression valueArgument = irConstructorCall.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<kotlin.String>");
        return (String) ((IrConst) valueArgument).getValue();
    }
}
