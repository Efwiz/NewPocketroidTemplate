package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/UBJsonReader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class UBJsonReader implements BaseJsonReader {
    public boolean oldFormat = true;

    @Override // com.badlogic.gdx.utils.BaseJsonReader
    public JsonValue parse(InputStream inputStream) throws Throwable {
        DataInputStream dataInputStream = null;
        try {
            try {
                DataInputStream dataInputStream2 = new DataInputStream(inputStream);
                try {
                    JsonValue jsonValue = parse(dataInputStream2);
                    StreamUtils.closeQuietly(dataInputStream2);
                    return jsonValue;
                } catch (IOException e) {
                    e = e;
                    throw new SerializationException(e);
                } catch (Throwable th) {
                    th = th;
                    dataInputStream = dataInputStream2;
                    StreamUtils.closeQuietly(dataInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    @Override // com.badlogic.gdx.utils.BaseJsonReader
    public JsonValue parse(FileHandle fileHandle) {
        try {
            return parse(fileHandle.read(8192));
        } catch (Exception e) {
            throw new SerializationException("Error parsing file: " + fileHandle, e);
        }
    }

    public JsonValue parse(DataInputStream dataInputStream) throws IOException {
        try {
            return parse(dataInputStream, dataInputStream.readByte());
        } finally {
            StreamUtils.closeQuietly(dataInputStream);
        }
    }

    protected JsonValue parse(DataInputStream dataInputStream, byte b) throws IOException {
        if (b == 91) {
            return parseArray(dataInputStream);
        }
        if (b == 123) {
            return parseObject(dataInputStream);
        }
        if (b == 90) {
            return new JsonValue(JsonValue.ValueType.nullValue);
        }
        if (b == 84) {
            return new JsonValue(true);
        }
        if (b == 70) {
            return new JsonValue(false);
        }
        if (b == 66) {
            return new JsonValue(readUChar(dataInputStream));
        }
        if (b == 85) {
            return new JsonValue(readUChar(dataInputStream));
        }
        if (b == 105) {
            return new JsonValue(this.oldFormat ? dataInputStream.readShort() : dataInputStream.readByte());
        }
        if (b == 73) {
            return new JsonValue(this.oldFormat ? dataInputStream.readInt() : dataInputStream.readShort());
        }
        if (b == 108) {
            return new JsonValue(dataInputStream.readInt());
        }
        if (b == 76) {
            return new JsonValue(dataInputStream.readLong());
        }
        if (b == 100) {
            return new JsonValue(dataInputStream.readFloat());
        }
        if (b == 68) {
            return new JsonValue(dataInputStream.readDouble());
        }
        if (b == 115 || b == 83) {
            return new JsonValue(parseString(dataInputStream, b));
        }
        if (b == 97 || b == 65) {
            return parseData(dataInputStream, b);
        }
        if (b == 67) {
            return new JsonValue(dataInputStream.readChar());
        }
        throw new GdxRuntimeException("Unrecognized data type");
    }

    protected JsonValue parseArray(DataInputStream dataInputStream) throws IOException {
        byte b;
        JsonValue jsonValue = new JsonValue(JsonValue.ValueType.array);
        byte b2 = dataInputStream.readByte();
        if (b2 == 36) {
            b = dataInputStream.readByte();
            b2 = dataInputStream.readByte();
        } else {
            b = 0;
        }
        long size = -1;
        if (b2 == 35) {
            size = parseSize(dataInputStream, false, -1L);
            if (size < 0) {
                throw new GdxRuntimeException("Unrecognized data type");
            }
            if (size == 0) {
                return jsonValue;
            }
            b2 = b == 0 ? dataInputStream.readByte() : b;
        }
        JsonValue jsonValue2 = null;
        long j = 0;
        while (dataInputStream.available() > 0 && b2 != 93) {
            JsonValue jsonValue3 = parse(dataInputStream, b2);
            jsonValue3.parent = jsonValue;
            if (jsonValue2 != null) {
                jsonValue3.prev = jsonValue2;
                jsonValue2.next = jsonValue3;
                jsonValue.size++;
            } else {
                jsonValue.child = jsonValue3;
                jsonValue.size = 1;
            }
            if (size > 0) {
                j++;
                if (j >= size) {
                    break;
                }
            }
            jsonValue2 = jsonValue3;
            b2 = b == 0 ? dataInputStream.readByte() : b;
        }
        return jsonValue;
    }

    protected JsonValue parseObject(DataInputStream dataInputStream) throws IOException {
        byte b;
        JsonValue jsonValue = new JsonValue(JsonValue.ValueType.object);
        byte b2 = dataInputStream.readByte();
        if (b2 == 36) {
            b = dataInputStream.readByte();
            b2 = dataInputStream.readByte();
        } else {
            b = 0;
        }
        long size = -1;
        if (b2 == 35) {
            size = parseSize(dataInputStream, false, -1L);
            if (size < 0) {
                throw new GdxRuntimeException("Unrecognized data type");
            }
            if (size == 0) {
                return jsonValue;
            }
            b2 = dataInputStream.readByte();
        }
        JsonValue jsonValue2 = null;
        long j = 0;
        while (dataInputStream.available() > 0 && b2 != 125) {
            String string = parseString(dataInputStream, true, b2);
            JsonValue jsonValue3 = parse(dataInputStream, b == 0 ? dataInputStream.readByte() : b);
            jsonValue3.setName(string);
            jsonValue3.parent = jsonValue;
            if (jsonValue2 != null) {
                jsonValue3.prev = jsonValue2;
                jsonValue2.next = jsonValue3;
                jsonValue.size++;
            } else {
                jsonValue.child = jsonValue3;
                jsonValue.size = 1;
            }
            if (size > 0) {
                j++;
                if (j >= size) {
                    break;
                }
            }
            b2 = dataInputStream.readByte();
            jsonValue2 = jsonValue3;
        }
        return jsonValue;
    }

    protected JsonValue parseData(DataInputStream dataInputStream, byte b) throws IOException {
        byte b2 = dataInputStream.readByte();
        long uInt = b == 65 ? readUInt(dataInputStream) : readUChar(dataInputStream);
        JsonValue jsonValue = new JsonValue(JsonValue.ValueType.array);
        JsonValue jsonValue2 = null;
        long j = 0;
        while (j < uInt) {
            JsonValue jsonValue3 = parse(dataInputStream, b2);
            jsonValue3.parent = jsonValue;
            if (jsonValue2 != null) {
                jsonValue2.next = jsonValue3;
                jsonValue.size++;
            } else {
                jsonValue.child = jsonValue3;
                jsonValue.size = 1;
            }
            j++;
            jsonValue2 = jsonValue3;
        }
        return jsonValue;
    }

    protected String parseString(DataInputStream dataInputStream, byte b) throws IOException {
        return parseString(dataInputStream, false, b);
    }

    protected String parseString(DataInputStream dataInputStream, boolean z, byte b) throws IOException {
        long size = -1;
        if (b == 83) {
            size = parseSize(dataInputStream, true, -1L);
        } else if (b == 115) {
            size = readUChar(dataInputStream);
        } else if (z) {
            size = parseSize(dataInputStream, b, false, -1L);
        }
        if (size >= 0) {
            return size > 0 ? readString(dataInputStream, size) : "";
        }
        throw new GdxRuntimeException("Unrecognized data type, string expected");
    }

    protected long parseSize(DataInputStream dataInputStream, boolean z, long j) throws IOException {
        return parseSize(dataInputStream, dataInputStream.readByte(), z, j);
    }

    protected long parseSize(DataInputStream dataInputStream, byte b, boolean z, long j) throws IOException {
        if (b == 105) {
            return readUChar(dataInputStream);
        }
        if (b == 73) {
            return readUShort(dataInputStream);
        }
        if (b == 108) {
            return readUInt(dataInputStream);
        }
        if (b == 76) {
            return dataInputStream.readLong();
        }
        return z ? (((long) (b & 255)) << 24) | (((long) (dataInputStream.readByte() & 255)) << 16) | (((long) (dataInputStream.readByte() & 255)) << 8) | ((long) (dataInputStream.readByte() & 255)) : j;
    }

    protected short readUChar(DataInputStream dataInputStream) throws IOException {
        return (short) (dataInputStream.readByte() & 255);
    }

    protected int readUShort(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readShort() & 65535;
    }

    protected long readUInt(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readInt();
    }

    protected String readString(DataInputStream dataInputStream, long j) throws IOException {
        byte[] bArr = new byte[(int) j];
        dataInputStream.readFully(bArr);
        return new String(bArr, "UTF-8");
    }
}
