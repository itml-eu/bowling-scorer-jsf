package eu.itml.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class BowlingGameServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldNotRollFor11Pins() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Max pins is 10");

        BowlingGameService bowlingGameService = new BowlingGameServiceImpl();
        bowlingGameService.roll(11);
    }

    @Test
    public void shouldNotRollForNegativePins() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Pins cannot be nagative");

        BowlingGameService bowlingGameService = new BowlingGameServiceImpl();
        bowlingGameService.roll(-1);
    }

    @Test
    public void shouldCalculatePointsWhenStrike() {
        final BowlingGameService bowlingGameService = new BowlingGameServiceImpl();
        bowlingGameService.roll(4);
        bowlingGameService.roll(3);

        // strike
        bowlingGameService.roll(10);

        bowlingGameService.roll(4);
        bowlingGameService.roll(5);

        bowlingGameService.roll(1);
        bowlingGameService.roll(3);

        bowlingGameService.roll(0);
        bowlingGameService.roll(4);

        bowlingGameService.roll(2);
        bowlingGameService.roll(5);

        bowlingGameService.roll(8);
        bowlingGameService.roll(0);

        bowlingGameService.roll(9);
        bowlingGameService.roll(1);

        bowlingGameService.roll(6);
        bowlingGameService.roll(2);

        bowlingGameService.roll(2);
        bowlingGameService.roll(3);


        assertEquals(7, bowlingGameService.getFrames().get(1).getScore());
        assertEquals(26, bowlingGameService.getFrames().get(2).getScore());
        assertTrue(bowlingGameService.getFrames().get(2).isStrike());
        assertEquals(35, bowlingGameService.getFrames().get(3).getScore());
        assertEquals(39, bowlingGameService.getFrames().get(4).getScore());
        assertEquals(43, bowlingGameService.getFrames().get(5).getScore());
        assertEquals(50, bowlingGameService.getFrames().get(6).getScore());
        assertEquals(58, bowlingGameService.getFrames().get(7).getScore());
        assertEquals(74, bowlingGameService.getFrames().get(8).getScore());
        assertEquals(82, bowlingGameService.getFrames().get(9).getScore());
        assertEquals(87, bowlingGameService.getFrames().get(10).getScore());
        assertEquals(87, bowlingGameService.getTotalScore());
    }

    @Test
    public void shouldDisplayMiss() {
        final BowlingGameService bowlingGameService = new BowlingGameServiceImpl();
        bowlingGameService.roll(0);
        bowlingGameService.roll(0);

        assertEquals(0, bowlingGameService.getTotalScore());
        assertEquals(0, bowlingGameService.getFrames().get(1).getScore());

        assertTrue(bowlingGameService.getFrames().get(1).isFirstMiss());
        assertTrue(bowlingGameService.getFrames().get(1).isSecondMiss());
        assertFalse(bowlingGameService.getFrames().get(1).isStrike());
        assertFalse(bowlingGameService.getFrames().get(1).isSpare());
    }


    @Test
    public void shouldDisplayPerfectGame() {
        final BowlingGameService bowlingGameService = new BowlingGameServiceImpl();
        bowlingGameService.roll(10); // strike
        bowlingGameService.roll(10); // strike
        bowlingGameService.roll(10); // strike
        bowlingGameService.roll(10); // strike
        bowlingGameService.roll(10); // strike
        bowlingGameService.roll(10); // strike
        bowlingGameService.roll(10); // strike
        bowlingGameService.roll(10); // strike
        bowlingGameService.roll(10); // strike
        bowlingGameService.roll(10); // strike in 10th round, get 2 extra shoots
        bowlingGameService.roll(10);
        bowlingGameService.roll(10);


        assertEquals(30, bowlingGameService.getFrames().get(1).getScore());
        assertEquals(60, bowlingGameService.getFrames().get(2).getScore());
        assertEquals(90, bowlingGameService.getFrames().get(3).getScore());
        assertEquals(120, bowlingGameService.getFrames().get(4).getScore());
        assertEquals(150, bowlingGameService.getFrames().get(5).getScore());
        assertEquals(180, bowlingGameService.getFrames().get(6).getScore());
        assertEquals(210, bowlingGameService.getFrames().get(7).getScore());
        assertEquals(240, bowlingGameService.getFrames().get(8).getScore());
        assertEquals(270, bowlingGameService.getFrames().get(9).getScore());
        assertEquals(300, bowlingGameService.getFrames().get(10).getScore());

        assertEquals(300, bowlingGameService.getTotalScore());
    }
}
