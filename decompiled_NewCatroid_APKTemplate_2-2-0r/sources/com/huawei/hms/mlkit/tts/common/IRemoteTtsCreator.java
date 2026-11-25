package com.huawei.hms.mlkit.tts.common;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteTtsCreator extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.mlkit.tts.common.IRemoteTtsCreator";

    public static class Default implements IRemoteTtsCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsCreator
        public IRemoteTtsDelegate newRemoteTtsDelegate() throws RemoteException {
            return null;
        }
    }

    IRemoteTtsDelegate newRemoteTtsDelegate() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteTtsCreator {
        static final int TRANSACTION_newRemoteTtsDelegate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteTtsCreator.DESCRIPTOR);
        }

        public static IRemoteTtsCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteTtsCreator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteTtsCreator)) {
                return (IRemoteTtsCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteTtsCreator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteTtsCreator.DESCRIPTOR);
                IRemoteTtsDelegate iRemoteTtsDelegateNewRemoteTtsDelegate = newRemoteTtsDelegate();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iRemoteTtsDelegateNewRemoteTtsDelegate != null ? iRemoteTtsDelegateNewRemoteTtsDelegate.asBinder() : null);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteTtsCreator {
            public static IRemoteTtsCreator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTtsCreator.DESCRIPTOR;
            }

            @Override // com.huawei.hms.mlkit.tts.common.IRemoteTtsCreator
            public IRemoteTtsDelegate newRemoteTtsDelegate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTtsCreator.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().newRemoteTtsDelegate();
                    }
                    parcelObtain2.readException();
                    return IRemoteTtsDelegate.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteTtsCreator iRemoteTtsCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteTtsCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteTtsCreator;
            return true;
        }

        public static IRemoteTtsCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
