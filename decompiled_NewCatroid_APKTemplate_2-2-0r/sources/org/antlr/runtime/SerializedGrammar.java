package org.antlr.runtime;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public class SerializedGrammar {
    public static final String COOKIE = "$ANTLR";
    public static final int FORMAT_VERSION = 1;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f422name;
    public List<? extends Rule> rules;
    public char type;

    protected class Rule {
        Block block;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        String f423name;

        public Rule(String str, Block block) {
            this.f423name = str;
            this.block = block;
        }

        public String toString() {
            return this.f423name + ":" + this.block;
        }
    }

    protected abstract class Node {
        public abstract String toString();

        protected Node() {
        }
    }

    protected class Block extends Node {
        List[] alts;

        public Block(List[] listArr) {
            super();
            this.alts = listArr;
        }

        @Override // org.antlr.runtime.SerializedGrammar.Node
        public String toString() {
            StringBuilder sb = new StringBuilder("(");
            int i = 0;
            while (true) {
                List[] listArr = this.alts;
                if (i < listArr.length) {
                    List list = listArr[i];
                    if (i > 0) {
                        sb.append("|");
                    }
                    sb.append(list.toString());
                    i++;
                } else {
                    sb.append(Constant.AFTER_QUTO);
                    return sb.toString();
                }
            }
        }
    }

    protected class TokenRef extends Node {
        int ttype;

        public TokenRef(int i) {
            super();
            this.ttype = i;
        }

        @Override // org.antlr.runtime.SerializedGrammar.Node
        public String toString() {
            return String.valueOf(this.ttype);
        }
    }

    protected class RuleRef extends Node {
        int ruleIndex;

        public RuleRef(int i) {
            super();
            this.ruleIndex = i;
        }

        @Override // org.antlr.runtime.SerializedGrammar.Node
        public String toString() {
            return String.valueOf(this.ruleIndex);
        }
    }

    public SerializedGrammar(String str) throws IOException {
        System.out.println("loading " + str);
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(str)));
        readFile(dataInputStream);
        dataInputStream.close();
    }

    protected void readFile(DataInputStream dataInputStream) throws IOException {
        if (!readString(dataInputStream).equals(COOKIE)) {
            throw new IOException("not a serialized grammar file");
        }
        dataInputStream.readByte();
        char c = (char) dataInputStream.readByte();
        this.type = c;
        String string = readString(dataInputStream);
        this.f422name = string;
        System.out.println(c + " grammar " + string);
        short s = dataInputStream.readShort();
        System.out.println("num rules = " + ((int) s));
        this.rules = readRules(dataInputStream, s);
    }

    protected List<? extends Rule> readRules(DataInputStream dataInputStream, int i) throws IOException {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(readRule(dataInputStream));
        }
        return arrayList;
    }

    protected Rule readRule(DataInputStream dataInputStream) throws IOException {
        if (dataInputStream.readByte() != 82) {
            throw new IOException("missing R on start of rule");
        }
        String string = readString(dataInputStream);
        System.out.println("rule: " + string);
        dataInputStream.readByte();
        Block block = readBlock(dataInputStream);
        if (dataInputStream.readByte() != 46) {
            throw new IOException("missing . on end of rule");
        }
        return new Rule(string, block);
    }

    protected Block readBlock(DataInputStream dataInputStream) throws IOException {
        int i = dataInputStream.readShort();
        List[] listArr = new List[i];
        for (int i2 = 0; i2 < i; i2++) {
            listArr[i2] = readAlt(dataInputStream);
        }
        return new Block(listArr);
    }

    protected List<Node> readAlt(DataInputStream dataInputStream) throws IOException {
        ArrayList arrayList = new ArrayList();
        if (dataInputStream.readByte() != 65) {
            throw new IOException("missing A on start of alt");
        }
        byte b = dataInputStream.readByte();
        while (b != 59) {
            if (b == 45) {
                dataInputStream.readChar();
                dataInputStream.readChar();
            } else if (b == 66) {
                arrayList.add(readBlock(dataInputStream));
            } else if (b == 114) {
                arrayList.add(new RuleRef(dataInputStream.readShort()));
            } else if (b == 116) {
                arrayList.add(new TokenRef(dataInputStream.readShort()));
            } else if (b == 126) {
                dataInputStream.readShort();
            }
            b = dataInputStream.readByte();
        }
        return arrayList;
    }

    protected String readString(DataInputStream dataInputStream) throws IOException {
        byte b = dataInputStream.readByte();
        StringBuilder sb = new StringBuilder();
        while (b != 59) {
            sb.append((char) b);
            b = dataInputStream.readByte();
        }
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.type).append(" grammar ").append(this.f422name);
        sb.append(this.rules);
        return sb.toString();
    }
}
