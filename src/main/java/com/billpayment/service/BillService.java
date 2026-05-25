package com.billpayment.service;

import com.billpayment.model.Bill;
import com.billpayment.storage.BillStorage;

import java.util.List;

public class BillService {
    private final BillStorage billStorage;

    public BillService(BillStorage repository) {
        this.billStorage = repository;
    }

    public void create(Bill bill) {
        billStorage.save(bill);
    }

    public List<Bill> getAll() {
        return billStorage.findAll();
    }

    public void update(long id, long amount,
                       String provider,
                       java.time.LocalDate dueDate) {

        Bill bill = billStorage.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));

        bill.update(amount, dueDate, provider);

        billStorage.save(bill);
    }

    public void delete(long id) {

        if (billStorage.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Bill not found");
        }

        billStorage.delete(id);
    }

    public List<Bill> searchByProvider(String provider) {
        return billStorage.findByProvider(provider);
    }
}
