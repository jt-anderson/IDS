public abstract class Detector<E> implements iDetector<E>{
    private iAlert alertListener;
    public Detector(iAlert onAlertCallback){
        this.alertListener = onAlertCallback;
    }
    public void setAlertListener(iAlert onAlertCallback) {
        this.alertListener = onAlertCallback;
    }
    protected void alert(){
        if(this.alertListener != null) this.alertListener.onAlert(this);
    }
}

interface iDetector <E> {
    boolean input(E e);
}
