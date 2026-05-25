package com.billpayment.storage;

import com.billpayment.model.Bill;

import java.util.*;

public class BillStorage {
    private final Map<Long, Bill> storage = new HashMap<>();

    public void save(Bill bill) {
        storage.put(bill.getId(), bill);
    }

    public Optional<Bill> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Bill> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void delete(long id) {
        storage.remove(id);
    }

    public List<Bill> findByProvider(String provider) {
        return storage.values()
                .stream()
                .filter(b -> b.getProvider().equalsIgnoreCase(provider))
                .toList();
    }
}
