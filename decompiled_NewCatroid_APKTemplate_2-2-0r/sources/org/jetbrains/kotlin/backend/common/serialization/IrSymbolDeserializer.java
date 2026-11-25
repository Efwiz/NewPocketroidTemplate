package org.jetbrains.kotlin.backend.common.serialization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.backend.common.serialization.proto.Actual;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrSymbolDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B¬\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f\u0012\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012%\b\u0002\u0010\u0014\u001a\u001f\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00110\u0015¢\u0006\u0002\b\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u0012\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\u0002\u0010\u001bJ\u000e\u00107\u001a\u00020\r2\u0006\u00108\u001a\u000209J\u000e\u0010:\u001a\u00020\u00112\u0006\u0010;\u001a\u000201J\u0016\u0010:\u001a\u00020\u00112\u0006\u0010<\u001a\u00020\r2\u0006\u0010=\u001a\u00020\u001aJ\u0018\u0010>\u001a\u00020\u00112\u0006\u0010?\u001a\u00020\r2\u0006\u0010=\u001a\u00020\u001aH\u0002J\u001a\u0010@\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0A2\u0006\u0010;\u001a\u000201J\u001b\u0010B\u001a\u00020C2\u0006\u0010;\u001a\u000201ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bD\u0010EJ\u0018\u0010F\u001a\u00020\u00112\u0006\u0010=\u001a\u00020\u001a2\u0006\u0010<\u001a\u00020\rH\u0002J\u0016\u0010G\u001a\u00020\u000e2\u0006\u0010H\u001a\u00020\u00112\u0006\u0010I\u001a\u00020\rJ\u000e\u0010J\u001a\u00020K2\u0006\u0010?\u001a\u00020\rJ\u000e\u0010L\u001a\u00020M2\u0006\u0010?\u001a\u00020\rR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR#\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001d\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00110!¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R#\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010+\u001a\u00020,¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R*\u0010/\u001a\u001e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u001100j\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u0011`2X\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u0014\u001a\u001f\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00110\u0015¢\u0006\u0002\b\u0016¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b5\u00106\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006N"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "libraryFile", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "fileSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "actuals", "", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/Actual;", "enqueueLocalTopLevelDeclaration", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "", "handleExpectActualMapping", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "internationService", "Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "symbolProcessor", "Lkotlin/Function3;", "Lkotlin/ExtensionFunctionType;", "fileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "deserializePublicSymbol", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "(Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;Lkotlin/jvm/functions/Function3;Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;Lkotlin/jvm/functions/Function2;)V", "getActuals", "()Ljava/util/List;", "getDeserializePublicSymbol", "()Lkotlin/jvm/functions/Function2;", "deserializedSymbols", "", "getDeserializedSymbols", "()Ljava/util/Map;", "getEnqueueLocalTopLevelDeclaration", "()Lkotlin/jvm/functions/Function1;", "getFileSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "getHandleExpectActualMapping", "getLibraryFile", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "signatureDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "getSignatureDeserializer", "()Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "symbolCache", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "getSymbolProcessor", "()Lkotlin/jvm/functions/Function3;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "deserializeIdSignature", "index", "", "deserializeIrSymbol", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "idSig", "symbolKind", "deserializeIrSymbolData", "idSignature", "deserializeIrSymbolToDeclare", "Lkotlin/Pair;", "parseSymbolData", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData;", "parseSymbolData-9x8F8T0", "(J)J", "referenceDeserializedSymbol", "referenceLocalIrSymbol", NativeSymbol.TYPE_NAME, "signature", "referencePropertyByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "referenceSimpleFunctionByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSymbolDeserializer {
    private final List<Actual> actuals;
    private final Function2<IdSignature, BinarySymbolData.SymbolKind, IrSymbol> deserializePublicSymbol;
    private final Map<IdSignature, IrSymbol> deserializedSymbols;
    private final Function1<IdSignature, Unit> enqueueLocalTopLevelDeclaration;
    private final IrFileSymbol fileSymbol;
    private final Function2<IdSignature, IrSymbol, IrSymbol> handleExpectActualMapping;
    private final IrLibraryFile libraryFile;
    private final IdSignatureDeserializer signatureDeserializer;
    private final HashMap<Long, IrSymbol> symbolCache;
    private final Function3<IrSymbolDeserializer, IrSymbol, IdSignature, IrSymbol> symbolProcessor;
    private final ReferenceSymbolTable symbolTable;

    /* JADX WARN: Multi-variable type inference failed */
    public IrSymbolDeserializer(ReferenceSymbolTable symbolTable, IrLibraryFile libraryFile, IrFileSymbol fileSymbol, List<Actual> actuals, Function1<? super IdSignature, Unit> enqueueLocalTopLevelDeclaration, Function2<? super IdSignature, ? super IrSymbol, ? extends IrSymbol> handleExpectActualMapping, IrInterningService internationService, Function3<? super IrSymbolDeserializer, ? super IrSymbol, ? super IdSignature, ? extends IrSymbol> symbolProcessor, IdSignature.FileSignature fileSignature, Function2<? super IdSignature, ? super BinarySymbolData.SymbolKind, ? extends IrSymbol> deserializePublicSymbol) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(fileSymbol, "fileSymbol");
        Intrinsics.checkNotNullParameter(actuals, "actuals");
        Intrinsics.checkNotNullParameter(enqueueLocalTopLevelDeclaration, "enqueueLocalTopLevelDeclaration");
        Intrinsics.checkNotNullParameter(handleExpectActualMapping, "handleExpectActualMapping");
        Intrinsics.checkNotNullParameter(internationService, "internationService");
        Intrinsics.checkNotNullParameter(symbolProcessor, "symbolProcessor");
        Intrinsics.checkNotNullParameter(fileSignature, "fileSignature");
        Intrinsics.checkNotNullParameter(deserializePublicSymbol, "deserializePublicSymbol");
        this.symbolTable = symbolTable;
        this.libraryFile = libraryFile;
        this.fileSymbol = fileSymbol;
        this.actuals = actuals;
        this.enqueueLocalTopLevelDeclaration = enqueueLocalTopLevelDeclaration;
        this.handleExpectActualMapping = handleExpectActualMapping;
        this.symbolProcessor = symbolProcessor;
        this.deserializePublicSymbol = deserializePublicSymbol;
        this.deserializedSymbols = new HashMap();
        this.symbolCache = new HashMap<>();
        this.signatureDeserializer = new IdSignatureDeserializer(libraryFile, fileSignature, internationService);
    }

    public final ReferenceSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final IrLibraryFile getLibraryFile() {
        return this.libraryFile;
    }

    public final IrFileSymbol getFileSymbol() {
        return this.fileSymbol;
    }

    public final List<Actual> getActuals() {
        return this.actuals;
    }

    public final Function1<IdSignature, Unit> getEnqueueLocalTopLevelDeclaration() {
        return this.enqueueLocalTopLevelDeclaration;
    }

    public final Function2<IdSignature, IrSymbol, IrSymbol> getHandleExpectActualMapping() {
        return this.handleExpectActualMapping;
    }

    public /* synthetic */ IrSymbolDeserializer(ReferenceSymbolTable referenceSymbolTable, IrLibraryFile irLibraryFile, IrFileSymbol irFileSymbol, List list, Function1 function1, Function2 function2, IrInterningService irInterningService, Function3 function3, IdSignature.FileSignature fileSignature, Function2 function4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(referenceSymbolTable, irLibraryFile, irFileSymbol, list, function1, function2, irInterningService, (i & 128) != 0 ? new Function3<IrSymbolDeserializer, IrSymbol, IdSignature, IrSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrSymbolDeserializer.1
            @Override // kotlin.jvm.functions.Function3
            public final IrSymbol invoke(IrSymbolDeserializer irSymbolDeserializer, IrSymbol s, IdSignature idSignature) {
                Intrinsics.checkNotNullParameter(irSymbolDeserializer, "$this$null");
                Intrinsics.checkNotNullParameter(s, "s");
                Intrinsics.checkNotNullParameter(idSignature, "<anonymous parameter 1>");
                return s;
            }
        } : function3, (i & 256) != 0 ? new IdSignature.FileSignature(irFileSymbol) : fileSignature, function4);
    }

    public final Function3<IrSymbolDeserializer, IrSymbol, IdSignature, IrSymbol> getSymbolProcessor() {
        return this.symbolProcessor;
    }

    public final Function2<IdSignature, BinarySymbolData.SymbolKind, IrSymbol> getDeserializePublicSymbol() {
        return this.deserializePublicSymbol;
    }

    public final Map<IdSignature, IrSymbol> getDeserializedSymbols() {
        return this.deserializedSymbols;
    }

    public final IrSymbol deserializeIrSymbol(IdSignature idSig, BinarySymbolData.SymbolKind symbolKind) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
        Map<IdSignature, IrSymbol> map = this.deserializedSymbols;
        IrSymbol irSymbolInvoke = map.get(idSig);
        if (irSymbolInvoke == null) {
            irSymbolInvoke = this.handleExpectActualMapping.invoke(idSig, referenceDeserializedSymbol(symbolKind, idSig));
            map.put(idSig, irSymbolInvoke);
        }
        return irSymbolInvoke;
    }

    private final IrSymbol referenceDeserializedSymbol(BinarySymbolData.SymbolKind symbolKind, IdSignature idSig) {
        return this.symbolProcessor.invoke(this, IrSymbolDeserializerKt.referenceDeserializedSymbol(this.symbolTable, this.fileSymbol, symbolKind, idSig), idSig);
    }

    public final void referenceLocalIrSymbol(IrSymbol symbol, IdSignature signature) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.deserializedSymbols.put(signature, symbol);
    }

    public final IrSimpleFunctionSymbol referenceSimpleFunctionByLocalSignature(IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        IrSymbol irSymbolDeserializeIrSymbolData = deserializeIrSymbolData(idSignature, BinarySymbolData.SymbolKind.FUNCTION_SYMBOL);
        Intrinsics.checkNotNull(irSymbolDeserializeIrSymbolData, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
        return (IrSimpleFunctionSymbol) irSymbolDeserializeIrSymbolData;
    }

    public final IrPropertySymbol referencePropertyByLocalSignature(IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        IrSymbol irSymbolDeserializeIrSymbolData = deserializeIrSymbolData(idSignature, BinarySymbolData.SymbolKind.PROPERTY_SYMBOL);
        Intrinsics.checkNotNull(irSymbolDeserializeIrSymbolData, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrPropertySymbol");
        return (IrPropertySymbol) irSymbolDeserializeIrSymbolData;
    }

    private final IrSymbol deserializeIrSymbolData(IdSignature idSignature, BinarySymbolData.SymbolKind symbolKind) {
        if (!idSignature.isPubliclyVisible()) {
            Map<IdSignature, IrSymbol> map = this.deserializedSymbols;
            IrSymbol irSymbolReferenceDeserializedSymbol = map.get(idSignature);
            if (irSymbolReferenceDeserializedSymbol == null) {
                if (idSignature.getHasTopLevel()) {
                    this.enqueueLocalTopLevelDeclaration.invoke(idSignature.topLevelSignature());
                }
                irSymbolReferenceDeserializedSymbol = referenceDeserializedSymbol(symbolKind, idSignature);
                map.put(idSignature, irSymbolReferenceDeserializedSymbol);
            }
            return irSymbolReferenceDeserializedSymbol;
        }
        return this.deserializePublicSymbol.invoke(idSignature, symbolKind);
    }

    public final Pair<IrSymbol, IdSignature> deserializeIrSymbolToDeclare(long code) {
        long jM6140parseSymbolData9x8F8T0 = m6140parseSymbolData9x8F8T0(code);
        IdSignature idSignatureDeserializeIdSignature = deserializeIdSignature(BinarySymbolData.m6206getSignatureIdimpl(jM6140parseSymbolData9x8F8T0));
        return new Pair<>(deserializeIrSymbolData(idSignatureDeserializeIdSignature, BinarySymbolData.m6205getKindimpl(jM6140parseSymbolData9x8F8T0)), idSignatureDeserializeIdSignature);
    }

    /* JADX INFO: renamed from: parseSymbolData-9x8F8T0, reason: not valid java name */
    public final long m6140parseSymbolData9x8F8T0(long code) {
        return BinarySymbolData.INSTANCE.m6211decode9x8F8T0(code);
    }

    public final IrSymbol deserializeIrSymbol(long code) {
        HashMap<Long, IrSymbol> map = this.symbolCache;
        Long lValueOf = Long.valueOf(code);
        IrSymbol irSymbolDeserializeIrSymbolData = map.get(lValueOf);
        if (irSymbolDeserializeIrSymbolData == null) {
            long jM6140parseSymbolData9x8F8T0 = m6140parseSymbolData9x8F8T0(code);
            irSymbolDeserializeIrSymbolData = deserializeIrSymbolData(deserializeIdSignature(BinarySymbolData.m6206getSignatureIdimpl(jM6140parseSymbolData9x8F8T0)), BinarySymbolData.m6205getKindimpl(jM6140parseSymbolData9x8F8T0));
            map.put(lValueOf, irSymbolDeserializeIrSymbolData);
        }
        return irSymbolDeserializeIrSymbolData;
    }

    public final IdSignatureDeserializer getSignatureDeserializer() {
        return this.signatureDeserializer;
    }

    public final IdSignature deserializeIdSignature(int index) {
        return this.signatureDeserializer.deserializeIdSignature(index);
    }
}
