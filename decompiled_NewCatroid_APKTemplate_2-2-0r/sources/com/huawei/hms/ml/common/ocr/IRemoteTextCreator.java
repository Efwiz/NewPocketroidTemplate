package com.huawei.hms.ml.common.ocr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteTextCreator extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.ocr.IRemoteTextCreator";

    public static class Default implements IRemoteTextCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.ocr.IRemoteTextCreator
        public IRemoteTextRecognizerDelegate newRemoteTextRecognizerDelegate() throws RemoteException {
            return null;
        }
    }

    IRemoteTextRecognizerDelegate newRemoteTextRecognizerDelegate() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteTextCreator {
        static final int TRANSACTION_newRemoteTextRecognizerDelegate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteTextCreator.DESCRIPTOR);
        }

        public static IRemoteTextCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteTextCreator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteTextCreator)) {
                return (IRemoteTextCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteTextCreator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteTextCreator.DESCRIPTOR);
                IRemoteTextRecognizerDelegate iRemoteTextRecognizerDelegateNewRemoteTextRecognizerDelegate = newRemoteTextRecognizerDelegate();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iRemoteTextRecognizerDelegateNewRemoteTextRecognizerDelegate != null ? iRemoteTextRecognizerDelegateNewRemoteTextRecognizerDelegate.asBinder() : null);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteTextCreator {
            public static IRemoteTextCreator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTextCreator.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.ocr.IRemoteTextCreator
            public IRemoteTextRecognizerDelegate newRemoteTextRecognizerDelegate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTextCreator.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().newRemoteTextRecognizerDelegate();
                    }
                    parcelObtain2.readException();
                    return IRemoteTextRecognizerDelegate.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteTextCreator iRemoteTextCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteTextCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteTextCreator;
            return true;
        }

        public static IRemoteTextCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
