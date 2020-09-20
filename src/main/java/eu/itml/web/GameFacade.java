package eu.itml.web;

import eu.itml.beans.FrameBean;
import eu.itml.service.BowlingGameServiceImpl;
import eu.itml.service.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class GameFacade {

    private BowlingGameServiceImpl bowlingGame;
    private final List<FrameBean> frameBeans = new ArrayList<>();

    public GameFacade() {
        this.bowlingGame = BowlingGameServiceImpl.getInstance();
    }

    public void roll(int pins) {
        try {
            final Frame frame = bowlingGame.roll(pins);

            final Optional<FrameBean> optFrameBean = frameBeans.stream()
                    .filter(frameBean -> frameBean.getFrameNo() == frame.getFrameNo())
                    .findAny();

            if (optFrameBean.isPresent()) {
                optFrameBean.get().update(frame);
            } else {
                frameBeans.add(FrameBean.create(frame));
            }
        } catch (Throwable e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance()
                    .addMessage("form:message", new FacesMessage(e.getMessage()));
        }
    }

    public List<FrameBean> getFrameBeans() {
        return frameBeans;
    }

    public int getTotalScore() {
        return bowlingGame.getTotalScore();
    }

    public void resetGame() {
        bowlingGame.reset();
        frameBeans.clear();
    }
}
