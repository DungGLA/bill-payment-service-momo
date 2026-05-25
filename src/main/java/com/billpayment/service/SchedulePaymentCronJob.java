package com.billpayment.service;

public class SchedulePaymentCronJob {

    // Scheduled to run every day
    public void run() {
        System.out.println("Running scheduled payment processing...");

        // query PENDING payments by date
        // for each payment, process it and update status to PROCESSED or FAILED it not enough fund or other issue.
    }
}
