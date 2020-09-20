package eu.itml.service;

import java.util.Map;

public interface BowlingGameService {

    int MAX_PINS = 10;
    int MAX_ROUNDS = 10;

    Frame roll(int pins);

    int getTotalScore();

    Map<Integer, Frame> getFrames();

    boolean isGameCompleted();

    void reset();
}
