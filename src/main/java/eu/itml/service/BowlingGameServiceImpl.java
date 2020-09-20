package eu.itml.service;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BowlingGameServiceImpl implements BowlingGameService {

    private static BowlingGameServiceImpl INSTANCE;

    private final Map<Integer, Frame> frames = new HashMap<>();
    private boolean gameCompleted = false;
    private Integer round = 1;

    BowlingGameServiceImpl() {
    }

    public static BowlingGameServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BowlingGameServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public Frame roll(int pins) {
        validate(pins);

        Frame frame = recordPins(pins);

        if (frame.isCompleted()) {
            createReference(frame);

            if (isLastRound()) {
                completeGame();
                return frame;
            }
            round++;
        }
        return frame;
    }

    private void validate(int pins) {
        if (gameCompleted) {
            throw new IllegalArgumentException("Spiel wurde beendet!");
        }
    }

    private void createReference(Frame frame) {
        if (round != 1) {
            final Frame previousFrame = getLastFrame();
            previousFrame.setNextFrame(frame);
            frame.setPrevFrame(previousFrame);
        }
    }

    private void completeGame() {
        System.out.println("Game completed!");
        gameCompleted = true;
    }

    private Frame recordPins(int pins) {
        Frame frame;
        if (frames.containsKey(round)) {
            frame = frames.get(round);
            frame.setPoints(pins);
        } else {
            frame = createFrame();
            frame.setPoints(pins);
            frames.put(round, frame);
        }
        return frame;
    }

    private Frame createFrame() {
        Frame frame;
        if (isLastRound()) {
            frame = new LastFrame();
        } else {
            frame = new Frame(round);
        }
        return frame;
    }

    public boolean isLastRound() {
        return MAX_ROUNDS == round;
    }

    public int getRoundNo() {
        if (gameCompleted) {
            return MAX_ROUNDS;
        } else {
            return round - 1;
        }
    }

    private Frame getLastFrame() {
        final int previousRound = round - 1;
        return frames.get(previousRound);
    }

    public Map<Integer, Frame> getFrames() {
        return ImmutableMap.copyOf(frames);
    }

    @Override
    public boolean isGameCompleted() {
        return gameCompleted;
    }

    @Override
    public void reset() {
        frames.clear();
        gameCompleted = false;
        round = 1;
    }

    @Override
    public int getTotalScore() {
        return Optional.of(frames)
                .map(frames -> Optional.ofNullable(frames.get(getRoundNo()))
                        .map(Frame::getScore)
                        .orElse(0))
                .orElse(0);
    }
}
