package com.android.step.gsensor;

public class StepCount implements StepCountListener{

    private int count = 0;
    private int mCount = 0;
    private StepValuePassListener mStepValuePassListener;
    private long timeOfLastPeak = 0;
    private long timeOfThisPeak = 0;

    @Override
    public void countStep() {
        this.timeOfLastPeak = this.timeOfThisPeak;
        this.timeOfThisPeak = System.currentTimeMillis();
        if (this.timeOfThisPeak - this.timeOfLastPeak <= 3000L){
            if(this.count<9){
                this.count++;
            }else if(this.count == 9){
                this.count++;
                this.mCount += this.count;
                notifyListener();
            }else{
                this.mCount++;
                notifyListener();
            }
        }else{//超时
            this.count = 1;//为1,不是0
        }

    }

    public void initListener(StepValuePassListener listener) {
        this.mStepValuePassListener = listener;
    }

    public void notifyListener() {
        if (this.mStepValuePassListener != null)
            this.mStepValuePassListener.stepChanged(this.mCount);
    }


    public void setSteps(int initValue) {
        this.mCount = initValue;
        this.count = 0;
        timeOfLastPeak = 0;
        timeOfThisPeak = 0;
        notifyListener();
    }
}
