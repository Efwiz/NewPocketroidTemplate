package com.thoughtworks.xstream.io.binary;

import com.thoughtworks.xstream.io.StreamException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.jvm.internal.ShortCompanionObject;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/Token.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class Token {
    private static final byte ID_EIGHT_BYTES = 32;
    private static final byte ID_FOUR_BYTES = 24;
    private static final byte ID_MASK = 56;
    private static final byte ID_ONE_BYTE = 8;
    private static final String ID_SPLITTED = "\u0000‡\u0000";
    private static final byte ID_TWO_BYTES = 16;
    private static final int MAX_UTF8_LENGTH = 65535;
    public static final byte TYPE_ATTRIBUTE = 5;
    public static final byte TYPE_END_NODE = 4;
    public static final byte TYPE_MAP_ID_TO_VALUE = 2;
    private static final byte TYPE_MASK = 7;
    public static final byte TYPE_START_NODE = 3;
    public static final byte TYPE_VALUE = 6;
    public static final byte TYPE_VERSION = 1;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    protected long f268id = -1;
    private final byte type;
    protected String value;

    public abstract void readFrom(DataInput dataInput, byte b) throws IOException;

    public abstract void writeTo(DataOutput dataOutput, byte b) throws IOException;

    public Token(byte b) {
        this.type = b;
    }

    public byte getType() {
        return this.type;
    }

    public long getId() {
        return this.f268id;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return new StringBuffer().append(getClass().getName()).append(" [id=").append(this.f268id).append(", value='").append(this.value).append("']").toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Token token = (Token) obj;
        if (this.f268id != token.f268id || this.type != token.type) {
            return false;
        }
        String str = this.value;
        String str2 = token.value;
        if (str != null) {
            if (str.equals(str2)) {
                return true;
            }
        } else if (str2 == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = this.type * 29;
        long j = this.f268id;
        int i2 = (i + ((int) (j ^ (j >>> 32)))) * 29;
        String str = this.value;
        return i2 + (str != null ? str.hashCode() : 0);
    }

    protected void writeId(DataOutput dataOutput, long j, byte b) throws IOException {
        if (j < 0) {
            throw new IOException(new StringBuffer("id must not be negative ").append(j).toString());
        }
        if (b == 8) {
            dataOutput.writeByte(((byte) j) - 128);
            return;
        }
        if (b == 16) {
            dataOutput.writeShort(((short) j) + ShortCompanionObject.MIN_VALUE);
        } else if (b == 24) {
            dataOutput.writeInt(((int) j) - Integer.MIN_VALUE);
        } else {
            if (b == 32) {
                dataOutput.writeLong(j - Long.MIN_VALUE);
                return;
            }
            throw new Error(new StringBuffer("Unknown idType ").append((int) b).toString());
        }
    }

    protected void writeString(DataOutput dataOutput, String str) throws IOException {
        byte[] bytes = str.length() > 16383 ? str.getBytes("utf-8") : new byte[0];
        if (bytes.length <= 65535) {
            dataOutput.writeUTF(str);
            return;
        }
        dataOutput.writeUTF(ID_SPLITTED);
        dataOutput.writeInt(bytes.length);
        dataOutput.write(bytes);
    }

    protected long readId(DataInput dataInput, byte b) throws IOException {
        if (b == 8) {
            return dataInput.readByte() + 128;
        }
        if (b == 16) {
            return dataInput.readShort() - ShortCompanionObject.MIN_VALUE;
        }
        if (b == 24) {
            return dataInput.readInt() - Integer.MIN_VALUE;
        }
        if (b == 32) {
            return dataInput.readLong() - Long.MIN_VALUE;
        }
        throw new Error(new StringBuffer("Unknown idType ").append((int) b).toString());
    }

    protected String readString(DataInput dataInput) throws IOException {
        String utf = dataInput.readUTF();
        if (!ID_SPLITTED.equals(utf)) {
            return utf;
        }
        byte[] bArr = new byte[dataInput.readInt()];
        dataInput.readFully(bArr);
        return new String(bArr, "utf-8");
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/Token$Formatter.class
      classes11.dex
     */
    public static class Formatter {
        public void write(DataOutput dataOutput, Token token) throws IOException {
            byte b;
            long id2 = token.getId();
            if (id2 <= 255) {
                b = 8;
            } else if (id2 <= WebSocketProtocol.PAYLOAD_SHORT_MAX) {
                b = 16;
            } else {
                b = id2 <= 4294967295L ? (byte) 24 : (byte) 32;
            }
            dataOutput.write(token.getType() + b);
            token.writeTo(dataOutput, b);
        }

        public Token read(DataInput dataInput) throws IOException {
            byte b = dataInput.readByte();
            Token tokenContructToken = contructToken((byte) (b & 7));
            tokenContructToken.readFrom(dataInput, (byte) (b & 56));
            return tokenContructToken;
        }

        private Token contructToken(byte b) {
            if (b == 2) {
                return new MapIdToValue();
            }
            if (b == 3) {
                return new StartNode();
            }
            if (b == 4) {
                return new EndNode();
            }
            if (b == 5) {
                return new Attribute();
            }
            if (b == 6) {
                return new Value();
            }
            throw new StreamException("Unknown token type");
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/Token$MapIdToValue.class
      classes11.dex
     */
    public static class MapIdToValue extends Token {
        public MapIdToValue(long j, String str) {
            super((byte) 2);
            this.f268id = j;
            this.value = str;
        }

        public MapIdToValue() {
            super((byte) 2);
        }

        @Override // com.thoughtworks.xstream.io.binary.Token
        public void writeTo(DataOutput dataOutput, byte b) throws IOException {
            writeId(dataOutput, this.f268id, b);
            writeString(dataOutput, this.value);
        }

        @Override // com.thoughtworks.xstream.io.binary.Token
        public void readFrom(DataInput dataInput, byte b) throws IOException {
            this.f268id = readId(dataInput, b);
            this.value = readString(dataInput);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/Token$StartNode.class
      classes11.dex
     */
    public static class StartNode extends Token {
        public StartNode(long j) {
            super((byte) 3);
            this.f268id = j;
        }

        public StartNode() {
            super((byte) 3);
        }

        @Override // com.thoughtworks.xstream.io.binary.Token
        public void writeTo(DataOutput dataOutput, byte b) throws IOException {
            writeId(dataOutput, this.f268id, b);
        }

        @Override // com.thoughtworks.xstream.io.binary.Token
        public void readFrom(DataInput dataInput, byte b) throws IOException {
            this.f268id = readId(dataInput, b);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/Token$EndNode.class
      classes11.dex
     */
    public static class EndNode extends Token {
        @Override // com.thoughtworks.xstream.io.binary.Token
        public void readFrom(DataInput dataInput, byte b) {
        }

        @Override // com.thoughtworks.xstream.io.binary.Token
        public void writeTo(DataOutput dataOutput, byte b) {
        }

        public EndNode() {
            super((byte) 4);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/Token$Attribute.class
      classes11.dex
     */
    public static class Attribute extends Token {
        public Attribute(long j, String str) {
            super((byte) 5);
            this.f268id = j;
            this.value = str;
        }

        public Attribute() {
            super((byte) 5);
        }

        @Override // com.thoughtworks.xstream.io.binary.Token
        public void writeTo(DataOutput dataOutput, byte b) throws IOException {
            writeId(dataOutput, this.f268id, b);
            writeString(dataOutput, this.value);
        }

        @Override // com.thoughtworks.xstream.io.binary.Token
        public void readFrom(DataInput dataInput, byte b) throws IOException {
            this.f268id = readId(dataInput, b);
            this.value = readString(dataInput);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/Token$Value.class
      classes11.dex
     */
    public static class Value extends Token {
        public Value(String str) {
            super((byte) 6);
            this.value = str;
        }

        public Value() {
            super((byte) 6);
        }

        @Override // com.thoughtworks.xstream.io.binary.Token
        public void writeTo(DataOutput dataOutput, byte b) throws IOException {
            writeString(dataOutput, this.value);
        }

        @Override // com.thoughtworks.xstream.io.binary.Token
        public void readFrom(DataInput dataInput, byte b) throws IOException {
            this.value = readString(dataInput);
        }
    }
}
