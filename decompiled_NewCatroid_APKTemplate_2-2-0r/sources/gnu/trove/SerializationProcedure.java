package gnu.trove;

import java.io.IOException;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
class SerializationProcedure implements TDoubleByteProcedure, TDoubleDoubleProcedure, TDoubleFloatProcedure, TDoubleIntProcedure, TDoubleLongProcedure, TDoubleObjectProcedure, TDoubleProcedure, TFloatByteProcedure, TFloatDoubleProcedure, TFloatFloatProcedure, TFloatIntProcedure, TFloatLongProcedure, TFloatObjectProcedure, TFloatProcedure, TIntByteProcedure, TIntDoubleProcedure, TIntFloatProcedure, TIntIntProcedure, TIntLongProcedure, TIntObjectProcedure, TIntProcedure, TByteByteProcedure, TByteDoubleProcedure, TByteFloatProcedure, TByteIntProcedure, TByteLongProcedure, TByteObjectProcedure, TByteProcedure, TLongByteProcedure, TLongDoubleProcedure, TLongFloatProcedure, TLongIntProcedure, TLongLongProcedure, TLongObjectProcedure, TLongProcedure, TObjectByteProcedure, TObjectDoubleProcedure, TObjectFloatProcedure, TObjectIntProcedure, TObjectLongProcedure, TObjectObjectProcedure, TObjectProcedure {
    IOException exception;
    private final ObjectOutputStream stream;

    SerializationProcedure(ObjectOutputStream objectOutputStream) {
        this.stream = objectOutputStream;
    }

    @Override // gnu.trove.TIntProcedure
    public boolean execute(int i) {
        try {
            this.stream.writeInt(i);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TByteProcedure
    public boolean execute(byte b) {
        try {
            this.stream.writeByte(b);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TDoubleProcedure
    public boolean execute(double d) {
        try {
            this.stream.writeDouble(d);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TLongProcedure
    public boolean execute(long j) {
        try {
            this.stream.writeLong(j);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TFloatProcedure
    public boolean execute(float f) {
        try {
            this.stream.writeFloat(f);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TObjectProcedure
    public boolean execute(Object obj) {
        try {
            this.stream.writeObject(obj);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TObjectObjectProcedure
    public boolean execute(Object obj, Object obj2) {
        try {
            this.stream.writeObject(obj);
            this.stream.writeObject(obj2);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TObjectIntProcedure
    public boolean execute(Object obj, int i) {
        try {
            this.stream.writeObject(obj);
            this.stream.writeInt(i);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TObjectByteProcedure
    public boolean execute(Object obj, byte b) {
        try {
            this.stream.writeObject(obj);
            this.stream.writeByte(b);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TObjectLongProcedure
    public boolean execute(Object obj, long j) {
        try {
            this.stream.writeObject(obj);
            this.stream.writeLong(j);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TObjectDoubleProcedure
    public boolean execute(Object obj, double d) {
        try {
            this.stream.writeObject(obj);
            this.stream.writeDouble(d);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TObjectFloatProcedure
    public boolean execute(Object obj, float f) {
        try {
            this.stream.writeObject(obj);
            this.stream.writeFloat(f);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TIntObjectProcedure
    public boolean execute(int i, Object obj) {
        try {
            this.stream.writeInt(i);
            this.stream.writeObject(obj);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TIntIntProcedure
    public boolean execute(int i, int i2) {
        try {
            this.stream.writeInt(i);
            this.stream.writeInt(i2);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TIntLongProcedure
    public boolean execute(int i, long j) {
        try {
            this.stream.writeInt(i);
            this.stream.writeLong(j);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TIntDoubleProcedure
    public boolean execute(int i, double d) {
        try {
            this.stream.writeInt(i);
            this.stream.writeDouble(d);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TIntFloatProcedure
    public boolean execute(int i, float f) {
        try {
            this.stream.writeInt(i);
            this.stream.writeFloat(f);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TByteByteProcedure
    public boolean execute(byte b, byte b2) {
        try {
            this.stream.writeByte(b);
            this.stream.writeByte(b2);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TByteObjectProcedure
    public boolean execute(byte b, Object obj) {
        try {
            this.stream.writeByte(b);
            this.stream.writeObject(obj);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TByteIntProcedure
    public boolean execute(byte b, int i) {
        try {
            this.stream.writeByte(b);
            this.stream.writeInt(i);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TByteLongProcedure
    public boolean execute(byte b, long j) {
        try {
            this.stream.writeByte(b);
            this.stream.writeLong(j);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TByteDoubleProcedure
    public boolean execute(byte b, double d) {
        try {
            this.stream.writeByte(b);
            this.stream.writeDouble(d);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TByteFloatProcedure
    public boolean execute(byte b, float f) {
        try {
            this.stream.writeByte(b);
            this.stream.writeFloat(f);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TLongObjectProcedure
    public boolean execute(long j, Object obj) {
        try {
            this.stream.writeLong(j);
            this.stream.writeObject(obj);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TLongIntProcedure
    public boolean execute(long j, int i) {
        try {
            this.stream.writeLong(j);
            this.stream.writeInt(i);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TLongLongProcedure
    public boolean execute(long j, long j2) {
        try {
            this.stream.writeLong(j);
            this.stream.writeLong(j2);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TLongDoubleProcedure
    public boolean execute(long j, double d) {
        try {
            this.stream.writeLong(j);
            this.stream.writeDouble(d);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TLongFloatProcedure
    public boolean execute(long j, float f) {
        try {
            this.stream.writeLong(j);
            this.stream.writeFloat(f);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TDoubleObjectProcedure
    public boolean execute(double d, Object obj) {
        try {
            this.stream.writeDouble(d);
            this.stream.writeObject(obj);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TDoubleIntProcedure
    public boolean execute(double d, int i) {
        try {
            this.stream.writeDouble(d);
            this.stream.writeInt(i);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TDoubleLongProcedure
    public boolean execute(double d, long j) {
        try {
            this.stream.writeDouble(d);
            this.stream.writeLong(j);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TDoubleDoubleProcedure
    public boolean execute(double d, double d2) {
        try {
            this.stream.writeDouble(d);
            this.stream.writeDouble(d2);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TDoubleFloatProcedure
    public boolean execute(double d, float f) {
        try {
            this.stream.writeDouble(d);
            this.stream.writeFloat(f);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TFloatObjectProcedure
    public boolean execute(float f, Object obj) {
        try {
            this.stream.writeFloat(f);
            this.stream.writeObject(obj);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TFloatIntProcedure
    public boolean execute(float f, int i) {
        try {
            this.stream.writeFloat(f);
            this.stream.writeInt(i);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TFloatLongProcedure
    public boolean execute(float f, long j) {
        try {
            this.stream.writeFloat(f);
            this.stream.writeLong(j);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TFloatDoubleProcedure
    public boolean execute(float f, double d) {
        try {
            this.stream.writeFloat(f);
            this.stream.writeDouble(d);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TFloatFloatProcedure
    public boolean execute(float f, float f2) {
        try {
            this.stream.writeFloat(f);
            this.stream.writeFloat(f2);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TDoubleByteProcedure
    public boolean execute(double d, byte b) {
        try {
            this.stream.writeDouble(d);
            this.stream.writeByte(b);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TFloatByteProcedure
    public boolean execute(float f, byte b) {
        try {
            this.stream.writeFloat(f);
            this.stream.writeByte(b);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TIntByteProcedure
    public boolean execute(int i, byte b) {
        try {
            this.stream.writeInt(i);
            this.stream.writeByte(b);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // gnu.trove.TLongByteProcedure
    public boolean execute(long j, byte b) {
        try {
            this.stream.writeLong(j);
            this.stream.writeByte(b);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }
}
