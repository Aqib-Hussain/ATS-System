package sample.Timers;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.Calendar;

public class MonthlyTimer {
    // What will be done
    private final Runnable whatToDo;

    // When the task will be done
    private final int dayOfMonth;
    private final int hourOfDay;

    // The current timer
    private Timer current = new Timer();

    public void cancelCurrent() {
        current.cancel();
        current.purge();
    }

    // create a new instance of the monthly timer
    public static MonthlyTimer schedule(Runnable runnable, int dayOfMonth, int hourOfDay) {
        return new MonthlyTimer(runnable, dayOfMonth, hourOfDay);
    }

    private MonthlyTimer(Runnable runnable, int day, int hour) {
        this.whatToDo = runnable;
        this.dayOfMonth = day;
        this.hourOfDay = hour;
        schedule();
    }

    // Schedules the task for execution on next month.
    private void schedule() {
        cancelCurrent();
        current = new Timer();
        current.schedule(new TimerTask() {
            public void run() {
                try {
                    whatToDo.run();
                } finally {
                    schedule();
                }
            }
        }, nextDate());
    }

    // Choose the next date
    private Date nextDate() {
        Calendar runDate = Calendar.getInstance();
        runDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        runDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        runDate.set(Calendar.MINUTE, 0);
        runDate.add(Calendar.MONTH, 1);
        return runDate.getTime();
    }
}

