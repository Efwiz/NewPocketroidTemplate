package com.huawei.hms.ml.common.face;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteFaceCreator extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.face.IRemoteFaceCreator";

    public static class Default implements IRemoteFaceCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.face.IRemoteFaceCreator
        public IRemoteFaceDetectorDelegate newRemoteFaceDetectorDelegate() throws RemoteException {
            return null;
        }
    }

    IRemoteFaceDetectorDelegate newRemoteFaceDetectorDelegate() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteFaceCreator {
        static final int TRANSACTION_newRemoteFaceDetectorDelegate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteFaceCreator.DESCRIPTOR);
        }

        public static IRemoteFaceCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteFaceCreator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteFaceCreator)) {
                return (IRemoteFaceCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteFaceCreator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteFaceCreator.DESCRIPTOR);
                IRemoteFaceDetectorDelegate iRemoteFaceDetectorDelegateNewRemoteFaceDetectorDelegate = newRemoteFaceDetectorDelegate();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iRemoteFaceDetectorDelegateNewRemoteFaceDetectorDelegate != null ? iRemoteFaceDetectorDelegateNewRemoteFaceDetectorDelegate.asBinder() : null);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteFaceCreator {
            public static IRemoteFaceCreator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteFaceCreator.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.face.IRemoteFaceCreator
            public IRemoteFaceDetectorDelegate newRemoteFaceDetectorDelegate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteFaceCreator.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().newRemoteFaceDetectorDelegate();
                    }
                    parcelObtain2.readException();
                    return IRemoteFaceDetectorDelegate.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteFaceCreator iRemoteFaceCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteFaceCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteFaceCreator;
            return true;
        }

        public static IRemoteFaceCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
