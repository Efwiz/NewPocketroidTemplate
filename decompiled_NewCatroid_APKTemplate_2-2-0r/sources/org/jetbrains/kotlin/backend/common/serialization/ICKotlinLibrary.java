package org.jetbrains.kotlin.backend.common.serialization;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.library.IrLibrary;
import org.jetbrains.kotlin.library.SerializedIrFile;
import org.jetbrains.kotlin.library.impl.DeclarationId;
import org.jetbrains.kotlin.library.impl.DeclarationIrTableMemoryReader;
import org.jetbrains.kotlin.library.impl.IrArrayMemoryReader;

/* JADX INFO: compiled from: IncrementalCompilationSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u0017H\u0016J\b\u0010\u001d\u001a\u00020\u0017H\u0016J\u0018\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u001f\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010 \u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010!\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\"\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010#\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010$\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017H\u0016JN\u0010%\u001a\u00020\u0007\"\u0004\b\u0000\u0010&\"\u000e\b\u0001\u0010'*\b\u0012\u0004\u0012\u0002H&0(*\n\u0012\u0006\u0012\u0004\u0018\u0001H'0\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010)\u001a\u0002H&2\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H'0+H\u0082\b¢\u0006\u0002\u0010,JB\u0010%\u001a\u00020\u0007\"\b\b\u0000\u0010'*\u00020-*\n\u0012\u0006\u0012\u0004\u0018\u0001H'0\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H'0+H\u0082\b¢\u0006\u0002\u0010.JF\u0010/\u001a\u0004\u0018\u00010\u0007\"\n\b\u0000\u0010'*\u0004\u0018\u00010-*\n\u0012\u0006\u0012\u0004\u0018\u0001H'0\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H'0+H\u0082\b¢\u0006\u0002\u0010.R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u0018\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u0018\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0011R\u0018\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u0018\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\r¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/ICKotlinLibrary;", "Lorg/jetbrains/kotlin/library/IrLibrary;", "icData", "", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "(Ljava/util/List;)V", "dataFlowGraph", "", "getDataFlowGraph", "()[B", "indexedBodies", "", "Lorg/jetbrains/kotlin/library/impl/IrArrayMemoryReader;", "[Lorg/jetbrains/kotlin/library/impl/IrArrayMemoryReader;", "indexedDebugInfos", "indexedDeclarations", "Lorg/jetbrains/kotlin/library/impl/DeclarationIrTableMemoryReader;", "[Lorg/jetbrains/kotlin/library/impl/DeclarationIrTableMemoryReader;", "indexedSignatures", "indexedStrings", "indexedTypes", "bodies", "fileIndex", "", "body", "index", "debugInfo", "declarations", "file", "fileCount", "irDeclaration", "signature", "signatures", "string", "strings", ModuleXmlParser.TYPE, SpecTypePair.NAME_types, "itemBytes", "K", "R", "Lorg/jetbrains/kotlin/library/impl/IrTableReader;", ASTExpr.DEFAULT_MAP_KEY_NAME, "factory", "Lkotlin/Function0;", "([Lorg/jetbrains/kotlin/library/impl/IrTableReader;ILjava/lang/Object;Lkotlin/jvm/functions/Function0;)[B", "Lorg/jetbrains/kotlin/library/impl/IrArrayReader;", "([Lorg/jetbrains/kotlin/library/impl/IrArrayReader;IILkotlin/jvm/functions/Function0;)[B", "itemNullableBytes", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ICKotlinLibrary implements IrLibrary {
    private final byte[] dataFlowGraph;
    private final List<SerializedIrFile> icData;
    private final IrArrayMemoryReader[] indexedBodies;
    private final IrArrayMemoryReader[] indexedDebugInfos;
    private final DeclarationIrTableMemoryReader[] indexedDeclarations;
    private final IrArrayMemoryReader[] indexedSignatures;
    private final IrArrayMemoryReader[] indexedStrings;
    private final IrArrayMemoryReader[] indexedTypes;

    public ICKotlinLibrary(List<SerializedIrFile> icData) {
        Intrinsics.checkNotNullParameter(icData, "icData");
        this.icData = icData;
        this.indexedDeclarations = new DeclarationIrTableMemoryReader[icData.size()];
        this.indexedTypes = new IrArrayMemoryReader[icData.size()];
        this.indexedSignatures = new IrArrayMemoryReader[icData.size()];
        this.indexedStrings = new IrArrayMemoryReader[icData.size()];
        this.indexedDebugInfos = new IrArrayMemoryReader[icData.size()];
        this.indexedBodies = new IrArrayMemoryReader[icData.size()];
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] getDataFlowGraph() {
        return this.dataFlowGraph;
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] irDeclaration(int index, int fileIndex) {
        DeclarationIrTableMemoryReader[] declarationIrTableMemoryReaderArr = this.indexedDeclarations;
        DeclarationId declarationId = new DeclarationId(index);
        DeclarationIrTableMemoryReader declarationIrTableMemoryReader = declarationIrTableMemoryReaderArr[fileIndex];
        if (declarationIrTableMemoryReader == null) {
            declarationIrTableMemoryReader = new DeclarationIrTableMemoryReader(this.icData.get(fileIndex).getDeclarations());
            declarationIrTableMemoryReaderArr[fileIndex] = declarationIrTableMemoryReader;
        }
        return declarationIrTableMemoryReader.tableItemBytes(declarationId);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] type(int index, int fileIndex) {
        IrArrayMemoryReader[] irArrayMemoryReaderArr = this.indexedTypes;
        IrArrayMemoryReader irArrayMemoryReader = irArrayMemoryReaderArr[fileIndex];
        if (irArrayMemoryReader == null) {
            irArrayMemoryReader = new IrArrayMemoryReader(this.icData.get(fileIndex).getTypes());
            irArrayMemoryReaderArr[fileIndex] = irArrayMemoryReader;
        }
        return irArrayMemoryReader.tableItemBytes(index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signature(int index, int fileIndex) {
        IrArrayMemoryReader[] irArrayMemoryReaderArr = this.indexedSignatures;
        IrArrayMemoryReader irArrayMemoryReader = irArrayMemoryReaderArr[fileIndex];
        if (irArrayMemoryReader == null) {
            irArrayMemoryReader = new IrArrayMemoryReader(this.icData.get(fileIndex).getSignatures());
            irArrayMemoryReaderArr[fileIndex] = irArrayMemoryReader;
        }
        return irArrayMemoryReader.tableItemBytes(index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] string(int index, int fileIndex) {
        IrArrayMemoryReader[] irArrayMemoryReaderArr = this.indexedStrings;
        IrArrayMemoryReader irArrayMemoryReader = irArrayMemoryReaderArr[fileIndex];
        if (irArrayMemoryReader == null) {
            irArrayMemoryReader = new IrArrayMemoryReader(this.icData.get(fileIndex).getStrings());
            irArrayMemoryReaderArr[fileIndex] = irArrayMemoryReader;
        }
        return irArrayMemoryReader.tableItemBytes(index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] body(int index, int fileIndex) {
        IrArrayMemoryReader[] irArrayMemoryReaderArr = this.indexedBodies;
        IrArrayMemoryReader irArrayMemoryReader = irArrayMemoryReaderArr[fileIndex];
        if (irArrayMemoryReader == null) {
            irArrayMemoryReader = new IrArrayMemoryReader(this.icData.get(fileIndex).getBodies());
            irArrayMemoryReaderArr[fileIndex] = irArrayMemoryReader;
        }
        return irArrayMemoryReader.tableItemBytes(index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] debugInfo(int index, int fileIndex) {
        IrArrayMemoryReader[] irArrayMemoryReaderArr = this.indexedDebugInfos;
        IrArrayMemoryReader irArrayMemoryReader = irArrayMemoryReaderArr[fileIndex];
        if (irArrayMemoryReader == null) {
            byte[] debugInfo = this.icData.get(fileIndex).getDebugInfo();
            irArrayMemoryReader = debugInfo != null ? new IrArrayMemoryReader(debugInfo) : null;
            irArrayMemoryReaderArr[fileIndex] = irArrayMemoryReader;
        }
        if (irArrayMemoryReader != null) {
            return irArrayMemoryReader.tableItemBytes(index);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] file(int index) {
        return this.icData.get(index).getFileData();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public int fileCount() {
        return this.icData.size();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] types(int fileIndex) {
        return this.icData.get(fileIndex).getTypes();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signatures(int fileIndex) {
        return this.icData.get(fileIndex).getSignatures();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] strings(int fileIndex) {
        return this.icData.get(fileIndex).getStrings();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] declarations(int fileIndex) {
        return this.icData.get(fileIndex).getDeclarations();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] bodies(int fileIndex) {
        return this.icData.get(fileIndex).getBodies();
    }
}
