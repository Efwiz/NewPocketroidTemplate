package com.huawei.hms.mlkit.tts.common;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteTtsCallback extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.mlkit.tts.common.IRemoteTtsCallback";

    public static class Default implements IRemoteTtsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsCallback
        public void callbackFromAPK(TtsParcel ttsParcel) throws RemoteException {
        }
    }

    void callbackFromAPK(TtsParcel ttsParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteTtsCallback {
        static final int TRANSACTION_callbackFromAPK = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteTtsCallback.DESCRIPTOR);
        }

        public static IRemoteTtsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteTtsCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteTtsCallback)) {
                return (IRemoteTtsCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteTtsCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteTtsCallback.DESCRIPTOR);
                callbackFromAPK(parcel.readInt() != 0 ? TtsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteTtsCallback {
            public static IRemoteTtsCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTtsCallback.DESCRIPTOR;
            }

            @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsCallback
            public void callbackFromAPK(TtsParcel ttsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTtsCallback.DESCRIPTOR);
                    if (ttsParcel != null) {
                        parcelObtain.writeInt(1);
                        ttsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callbackFromAPK(ttsParcel);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteTtsCallback iRemoteTtsCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteTtsCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteTtsCallback;
            return true;
        }

        public static IRemoteTtsCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
