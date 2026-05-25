package com.billpayment.service;

import com.billpayment.model.Bill;
import com.billpayment.storage.BillStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class BillServiceTest {

    private BillStorage billStorage;
    private BillService billService;

    @BeforeEach
    void setUp() {
        billStorage = new BillStorage();
        billService = new BillService(billStorage);
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreateBillSuccessfully() {

        Bill bill = new Bill(
                1L,
                "ELECTRIC",
                200000,
                LocalDate.of(2026, 10, 25),
                "EVN"
        );

        billService.create(bill);

        List<Bill> bills = billService.getAll();

        assertEquals(1, bills.size());
        assertEquals("EVN", bills.get(0).getProvider());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAllBills() {

        billService.create(new Bill(1, "ELECTRIC", 100, LocalDate.now(), "EVN"));
        billService.create(new Bill(2, "WATER", 200, LocalDate.now(), "SAVACO"));

        List<Bill> bills = billService.getAll();

        assertEquals(2, bills.size());
    }

    // =========================
    // UPDATE
    // =========================
    @Test
    void shouldUpdateBillSuccessfully() {

        Bill bill = new Bill(1, "ELECTRIC", 100, LocalDate.now(), "EVN");
        billService.create(bill);

        billService.update(
                1,
                500000,
                "EVN-UPDATED",
                LocalDate.of(2026, 12, 31)
        );

        Bill updated = billService.getAll().get(0);

        assertEquals(500000, updated.getAmount());
        assertEquals("EVN-UPDATED", updated.getProvider());
        assertEquals(LocalDate.of(2026, 12, 31), updated.getDueDate());
    }

    // =========================
    // DELETE
    // =========================
    @Test
    void shouldDeleteBillSuccessfully() {

        billService.create(new Bill(1, "ELECTRIC", 100, LocalDate.now(), "EVN"));

        billService.delete(1);

        assertEquals(0, billService.getAll().size());
    }

    // =========================
    // DELETE NOT FOUND
    // =========================
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingBill() {

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> billService.delete(999)
        );

        assertEquals("Bill not found", ex.getMessage());
    }

    // =========================
    // SEARCH
    // =========================
    @Test
    void shouldSearchByProvider() {

        billService.create(new Bill(1, "ELECTRIC", 100, LocalDate.now(), "EVN"));
        billService.create(new Bill(2, "WATER", 200, LocalDate.now(), "SAVACO"));
        billService.create(new Bill(3, "INTERNET", 300, LocalDate.now(), "EVN"));

        List<Bill> result = billService.searchByProvider("EVN");

        assertEquals(2, result.size());
    }
}