package com.huawei.hms.ml.common.object;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteObjectCreator extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.object.IRemoteObjectCreator";

    public static class Default implements IRemoteObjectCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.object.IRemoteObjectCreator
        public IRemoteObjectDecoderDelegate newRemoteObjectDecoderDelegate() throws RemoteException {
            return null;
        }
    }

    IRemoteObjectDecoderDelegate newRemoteObjectDecoderDelegate() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteObjectCreator {
        static final int TRANSACTION_newRemoteObjectDecoderDelegate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteObjectCreator.DESCRIPTOR);
        }

        public static IRemoteObjectCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteObjectCreator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteObjectCreator)) {
                return (IRemoteObjectCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteObjectCreator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteObjectCreator.DESCRIPTOR);
                IRemoteObjectDecoderDelegate iRemoteObjectDecoderDelegateNewRemoteObjectDecoderDelegate = newRemoteObjectDecoderDelegate();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iRemoteObjectDecoderDelegateNewRemoteObjectDecoderDelegate != null ? iRemoteObjectDecoderDelegateNewRemoteObjectDecoderDelegate.asBinder() : null);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteObjectCreator {
            public static IRemoteObjectCreator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteObjectCreator.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.object.IRemoteObjectCreator
            public IRemoteObjectDecoderDelegate newRemoteObjectDecoderDelegate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteObjectCreator.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().newRemoteObjectDecoderDelegate();
                    }
                    parcelObtain2.readException();
                    return IRemoteObjectDecoderDelegate.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteObjectCreator iRemoteObjectCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteObjectCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteObjectCreator;
            return true;
        }

        public static IRemoteObjectCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
