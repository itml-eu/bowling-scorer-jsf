package eu.itml.service;

import static eu.itml.service.BowlingGameService.MAX_PINS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Frame implements Frameable {

    protected final List<Integer> hits = new ArrayList<>(2);
    protected final List<Boolean> misses = new ArrayList<>(2);
    protected final List<Boolean> strikes = new ArrayList<>(2);
    protected final List<Boolean> spares = new ArrayList<>(2);

    private int frameNo;

    protected boolean completed;
    private Frame nextFrame;
    protected Frame prevFrame;

    protected int currentTry = 1;

    public Frame() {
        misses.add(0, false);
        misses.add(1, false);
        misses.add(2, false);

        spares.add(0, false);
        spares.add(1, false);
        spares.add(2, false);

        strikes.add(0, false);
        strikes.add(1, false);
        strikes.add(2, false);
    }

    public Frame(Integer round) {
        this();
        this.frameNo = round;
    }

    protected void setPoints(int pins) {
        validatePins(pins);

        if (completed) {
            return;
        }
        if (pins == MAX_PINS) {
            this.hits.add(MAX_PINS);
            strikes.add(0, true);
            completed = true;
            return;
        }

        if (currentTry == 1) {
            this.hits.add(pins);

            if (pins == 0) {
                misses.set(0, true);
            }

            currentTry++;
        } else if (currentTry == 2) {
            if (this.hits.get(0) + pins > MAX_PINS) {
                throw new IllegalArgumentException("Max 10 pins!");
            }

            this.hits.add(pins);

            if (pins == 0) {
                misses.set(1, true);
            }
            if (getPoints() == MAX_PINS) {
                spares.add(0, true);
            }
            completed = true;
        }
    }

    protected void validatePins(int pins) {
        if (pins > MAX_PINS) {
            throw new IllegalArgumentException(String.format("Max pins ist %s", MAX_PINS));
        }
        if (pins < 0) {
            throw new IllegalArgumentException("Pins könnte nicht negativ sein!");
        }
    }

    /**
     * Sum points in given frame
     *
     * @return points for this frame
     */
    public int getPoints(Frameable frame) {
        return this.hits.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getPoints() {
        return getPoints(this);
    }

    private int getStrikePoints() {
        if (isStrike()) {
            if (nextFrame == null) {
                return this.getPoints();
            } else {
                boolean nextFrameStrike = nextFrame.isStrike();
                if (nextFrameStrike) {
                    if (nextFrame.nextFrame != null) {
                        return this.getPoints() + nextFrame.getPoints(this)
                                + nextFrame.nextFrame.getPoints(this);
                    }
                }
                return this.getPoints() + nextFrame.getPoints(this);
            }
        }
        return this.getPoints();
    }

    /**
     * Calculates score for given frame (this frame + all previous)
     *
     * @return score
     */
    public int getScore() {
        if (nextFrame != null) {
            if (isSpare()) {
                return Optional.ofNullable(prevFrame)
                        .map(prev -> prev.getScore() + getPoints() + nextFrame.getFirstHitPoints())
                        .orElseGet(() -> getPoints() + nextFrame.getFirstHitPoints());
            }
            if (isStrike()) {
                return Optional.ofNullable(prevFrame)
                        .map(prev -> prev.getScore() + getStrikePoints())
                        .orElseGet(this::getStrikePoints);
            }
        }

        return Optional.ofNullable(prevFrame)
                .map(prev -> prev.getScore() + getPoints())
                .orElseGet(this::getPoints);
    }

    public boolean isStrike() {
        return strikes.get(0);
    }

    public boolean isSpare() {
        return spares.get(0);
    }

    private int getFirstHitPoints() {
        return hits.get(0);
    }

    public boolean isFirstMiss() {
        return misses.get(0);
    }

    public boolean isSecondMiss() {
        return misses.get(1);
    }

    public int getFrameNo() {
        return frameNo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Frame getNextFrame() {
        return nextFrame;
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }

    public Frame getPrevFrame() {
        return prevFrame;
    }

    public void setPrevFrame(Frame prevFrame) {
        this.prevFrame = prevFrame;
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
}
