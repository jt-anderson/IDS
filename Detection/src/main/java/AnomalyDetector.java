import java.util.LinkedList;

public class AnomalyDetector<E extends Number> extends Detector<E> {
    private final LinkedList<Double> window1 = new LinkedList<>();
    private final LinkedList<Double> window2 = new LinkedList<>();
    private final LinkedList<Double> window1Avg = new LinkedList<>();
    private final LinkedList<Double> window2Avg = new LinkedList<>();
    private final LinkedList<Double> baseline = new LinkedList<>();
    private int windowSize;
    private int baselineCount = 1;
    private Double baselineMean;
    private Double baselineSD;

    public AnomalyDetector(iAlert onAlertCallback){
        super(onAlertCallback);
    }
    public AnomalyDetector(iAlert onAlertCallback, int windowSize){
        super(onAlertCallback);
        this.baselineCount = windowSize;
        this.windowSize = windowSize;
    }
    public AnomalyDetector(iAlert onAlertCallback, int windowSize, int baselineWindowSize){
        super(onAlertCallback);
        this.baselineCount = baselineWindowSize;
        this.windowSize = windowSize;
    }
    public AnomalyDetector(iAlert onAlertCallback, int windowSize, int baselineWindowSize, LinkedList<Number> data){
        super(onAlertCallback);
        this.windowSize = windowSize;
        this.baselineCount = baselineWindowSize;
        for (Number num: data) input(num);
    }

    @Override
    public boolean input(Number e) {
        if(useBaselineWindow()){
            baseline(e.doubleValue());
            return false;
        }
        pushToWindow(e.doubleValue());
        return detect();
    }

    private void baseline(Double e){
        baseline.addFirst(e);
        baselineCount--;
        if(!useBaselineWindow()){
            pushToAverage(mean(baseline));
        }
    }

    private boolean useBaselineWindow(){
        return baselineCount < 0;
    }

    private void pushToWindow(Double e){
        window1.addFirst(e);
        if(window1.size()>windowSize) window2.addFirst(window1.removeLast());
        if(window2.size()>windowSize) window2.removeLast();
        pushToAverage(movingAverage(e));
    }

    private Double getWindowIndex(int i){
        return i < windowSize ? window1.get(i) : window2.get(i % windowSize);
    }

    private void pushToAverage(Double i){
        window1Avg.addFirst(i);
        if(window1Avg.size() > windowSize) window2Avg.add(window1Avg.remove(0));
    }

    private boolean detect(){
        if(BasicDetection() || KLDivergence()) {
            this.alert();
            return true;
        }
        return false;
    }

    private boolean BasicDetection(){
        if(window2.size() < windowSize){

        } else {

        }
        Double meanOfWindow1Avg = mean(window1Avg);
        Double meanOfWindow2Avg = mean(window2Avg);


        return false;
    }

    private boolean KLDivergence(){
        return false;
    }

    private Double movingAverage(Double n){
        Double prevAvg = this.window1Avg.getFirst();
        return prevAvg + (1.0 / n)*(n-prevAvg);
    }

    private Double mean(LinkedList<Double> nums){
        double sum = 0.0;
        for (Number n: nums) {
            sum += n.doubleValue();
        }
        return sum/nums.size();
    }

    private Double standardDeviation(LinkedList<Double> nums, Double mean){
        return null;
    }
}
