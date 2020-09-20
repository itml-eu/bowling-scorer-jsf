package eu.itml.beans;

import eu.itml.service.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class FrameBean {

    protected List<Integer> hits = new ArrayList<>(2);
    protected List<Boolean> misses = new ArrayList<>(2);
    protected List<Boolean> strikes = new ArrayList<>(2);
    protected List<Boolean> spares = new ArrayList<>(2);

    private int frameNo;
    protected boolean completed;

    public void setHits(List<Integer> hits) {
        this.hits = hits;
    }

    public void setMisses(List<Boolean> misses) {
        this.misses = misses;
    }

    public void setStrikes(List<Boolean> strikes) {
        this.strikes = strikes;
    }

    public void setSpares(List<Boolean> spares) {
        this.spares = spares;
    }

    public List<Integer> getHits() {
        return hits;
    }

    public List<Boolean> getMisses() {
        return misses;
    }

    public List<Boolean> getStrikes() {
        return strikes;
    }

    public List<Boolean> getSpares() {
        return spares;
    }

    public int getFrameNo() {
        return frameNo;
    }

    public void setFrameNo(int frameNo) {
        this.frameNo = frameNo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public static FrameBean create(Frame frame) {
        FrameBean frameBean = new FrameBean();
        frameBean.setHits(frame.getHits());
        frameBean.setMisses(frame.getMisses());
        frameBean.setStrikes(frame.getStrikes());
        frameBean.setSpares(frame.getSpares());
        frameBean.setFrameNo(frame.getFrameNo());
        frameBean.setCompleted(frame.isCompleted());
        return frameBean;
    }

    public FrameBean update(Frame frame) {
        this.setHits(frame.getHits());
        this.setMisses(frame.getMisses());
        this.setSpares(frame.getSpares());
        this.setStrikes(frame.getStrikes());
        this.setFrameNo(frame.getFrameNo());
        this.setCompleted(frame.isCompleted());
        return this;
    }

    @Override
    public String toString() {
        return "eu.itml.beans.FrameBean{" +
                "hits=" + hits +
                ", misses=" + misses +
                ", strikes=" + strikes +
                ", spares=" + spares +
                ", frameNo=" + frameNo +
                ", completed=" + completed +
                '}';
    }
}
