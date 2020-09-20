package eu.itml.service;

import static eu.itml.service.BowlingGameService.MAX_PINS;
import static eu.itml.service.BowlingGameService.MAX_ROUNDS;

public class LastFrame extends Frame {

    public LastFrame(Integer round) {
        super(round);
    }

    public void setPoints(int pins) {
        if (currentTry == 1) {
            this.hits.add(pins);
            if (pins == 0) {
                misses.set(0, true);
            }
            if (pins == MAX_PINS) {
                strikes.set(0, true);
            }
            currentTry++;
        } else if (currentTry == 2) {
            this.hits.add(pins);

            if (pins == 0) {
                misses.set(1, true);
            }

            // if strike wasn't at first hit then check if second was spare
            if (!strikes.get(0)) {
                if (getPoints(this) == MAX_PINS) {
                    spares.add(1, true);
                    currentTry++; // extra shot
                } else {
                    completed = true; // no strike at first hit, no spare at second = game completed
                }
            } else { // strike was at 1 shot
                if (pins == MAX_PINS) {
                    strikes.set(1, true); // second strike
                    currentTry++; // get extra shot
                }
            }
        } else if (currentTry == 3) {
            this.hits.add(pins);

            if (pins == 0) {
                misses.set(2, true);
            }

            if (pins == MAX_PINS) {
                strikes.set(2, true); // third strike
                completed = true;
            }

            if (hits.get(1) + pins == MAX_PINS) { // spare
                spares.set(2, true);
                completed = true;
            }
        }
    }

    public int getPoints(Frameable frame) {
        if (frame.getFrameNo() == MAX_ROUNDS) {
            return this.hits.stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        } else if (frame.getFrameNo() == 8) {
            return this.hits.get(0);
        } else if (frame.getFrameNo() == 9) {
            return this.hits.get(0) + this.hits.get(1);
        }
        throw new IllegalStateException("Cannot calculate points");
    }

    public int getScore() {
        return prevFrame.getScore() + getPoints(this);
    }

    public boolean isFirstMiss() {
        return misses.get(0);
    }

    public boolean isSecondMiss() {
        return misses.get(1);
    }
}
