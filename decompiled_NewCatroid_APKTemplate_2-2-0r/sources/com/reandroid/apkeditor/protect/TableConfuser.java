package com.reandroid.apkeditor.protect;

import com.reandroid.apkeditor.APKEditor;
import com.reandroid.arsc.ARSCLib;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.UnknownChunk;
import com.reandroid.arsc.item.ByteArray;
import com.reandroid.arsc.item.FixedLengthString;
import com.reandroid.arsc.item.TypeString;
import java.util.Iterator;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: loaded from: classes10.dex */
public class TableConfuser extends Confuser {
    public TableConfuser(Protector protector) {
        super(protector, "TableConfuser: ");
    }

    @Override // com.reandroid.apkeditor.protect.Confuser
    public void confuse() {
        logMessage("Confusing ...");
        confuseWithUnknownChunk();
        confuseTypeNames();
    }

    private void confuseWithUnknownChunk() {
        TableBlock tableBlock = getApkModule().getTableBlock();
        UnknownChunk unknownChunk = new UnknownChunk();
        FixedLengthString fixedLengthString = new FixedLengthString(256);
        fixedLengthString.set(APKEditor.getRepo());
        ByteArray extraBytes = unknownChunk.getHeaderBlock().getExtraBytes();
        byte[] bytes = fixedLengthString.getBytes();
        extraBytes.setSize(bytes.length);
        extraBytes.putByteArray(0, bytes);
        fixedLengthString.set(ARSCLib.getRepo());
        ByteArray body = unknownChunk.getBody();
        byte[] bytes2 = fixedLengthString.getBytes();
        body.setSize(bytes2.length);
        body.putByteArray(0, bytes2);
        fixedLengthString.set(ARSCLib.getRepo());
        unknownChunk.refresh();
        tableBlock.getFirstPlaceHolder().setItem(unknownChunk);
        tableBlock.refresh();
    }

    private void confuseTypeNames() {
        TableBlock tableBlock = getApkModule().getTableBlock();
        Iterator<PackageBlock> it = tableBlock.listPackages().iterator();
        while (it.hasNext()) {
            for (TypeString typeString : it.next().getTypeStringPool()) {
                String str = typeString.get();
                if (ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED.equals(str)) {
                    typeString.set("style");
                } else if ("style".equals(str)) {
                    typeString.set("plurals");
                } else if ("id".equals(str)) {
                    typeString.set(ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED);
                } else if ("mipmap".equals(str)) {
                    typeString.set("id");
                }
                logVerbose(FormatHelper.QUOTE + str + "' -> '" + typeString.get() + FormatHelper.QUOTE);
            }
        }
        tableBlock.refresh();
    }
}
