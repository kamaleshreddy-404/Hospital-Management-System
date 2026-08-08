package com.hospital.dao;

import com.hospital.model.Medicine;
import java.util.List;

public interface MedicineDAO {
    List<Medicine> findAll();
    Medicine findById(int medicineId);
    List<Medicine> findLowStockMedicines();
    boolean create(Medicine medicine);
    boolean update(Medicine medicine);
    boolean updateStock(int medicineId, int newStock);
    boolean reduceStock(int medicineId, int quantity);
    boolean delete(int medicineId);
    int getTotalMedicinesCount();
}
