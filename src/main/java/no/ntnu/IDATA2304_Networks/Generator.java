package no.ntnu.IDATA2304_Networks;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * Returns current time and electricity price.
 */
public class Generator {
    private double price;
    private static final long SLEEP_DURATION_MS = 4000;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();


    public Generator() {
        //Left empty on purpose
    }

    public String getDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        return sdf.format(date);
    }

    public String getCurrentHour() {
        Date currentHour = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        return sdf.format(currentHour);
    }

    /**
     * Returns current time.
     * @return Current time
     */
    public int getCurrentTime() {
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");

        return Integer.parseInt(simpleDateFormat.format(currentDate));
    }

    /**
     * Returns the current price of electricity.
     *
     * @return The current price of electricity
     */
    public String getPrice() {
        Generator electricityPrice = new Generator();
        Random random = new Random();
        if ((electricityPrice.getCurrentTime() >= 100000) || (electricityPrice.getCurrentTime() <= 140000)) {
            price = 20.33 + random.nextDouble(15);
        }
        else if ((electricityPrice.getCurrentTime() >= 140001) || (electricityPrice.getCurrentTime() <= 180000))  {
            price = 30.15 + random.nextInt(25);
        }
        else if ((electricityPrice.getCurrentTime() >= 180001) || (electricityPrice.getCurrentTime() <= 235959)) {
            price = 25.79 + random.nextInt(15);
        }
        else {
            price = 8.88 + random.nextInt(13);
        }
        decimalFormatSymbols.setDecimalSeparator('.');
        return decimalFormat.format(price);
    }

    public void run() throws InterruptedException {
        while(true) {
            getPrice();
            getCurrentHour();
            getDate();
            System.out.println("===============================");
            Thread.sleep(SLEEP_DURATION_MS);
        }
    }
}