package nl.bos;

import javafx.concurrent.Task;

public class TimerTask extends Task {
    private boolean running = false;
    private int countDownTimer = (45 * 60) + 10;
    private boolean runTask = true;

    @Override
    protected Object call() throws Exception {
        while (runTask) {
            if (running) {
                if (countDownTimer >= 0) {
                    updateMessage(String.format("%02d:%02d", countDownTimer / 60, countDownTimer % 60));
                } else {
                    updateMessage(String.format("-%02d:%02d", -(countDownTimer / 60), -(countDownTimer % 60)));
                }

                countDownTimer--;
            }
            Thread.sleep(1000);
        }

        return null;
    }

    public void toggleTimer(boolean toggle) {
        running = toggle;
    }
}
