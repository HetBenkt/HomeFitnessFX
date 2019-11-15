package nl.bos;

import javafx.concurrent.Task;

public class TimerTask extends Task {//TODO There are StopWatch classes that can do the same trick, maybe even nicer!
    private boolean running = false;
    private int countDownTimer = (45 * 60) + 10;
    private boolean runTask = true;

    @Override
    protected Object call() {
        while (runTask && !Thread.currentThread().isInterrupted()) {

            if (running) {
                if (countDownTimer >= 0) {
                    updateMessage(String.format("%02d:%02d", countDownTimer / 60, countDownTimer % 60));
                } else {
                    updateMessage(String.format("-%02d:%02d", -(countDownTimer / 60), -(countDownTimer % 60)));
                }

                countDownTimer--;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }

    public void toggleTimer(boolean toggle) {
        running = toggle;
    }
}
