package com.projetko.stockmanagement.supplier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Transactional(readOnly = true)
    public List<SupplierResponse> findAll(){
        return supplierRepository.findAll()
                .stream()
                .map(SupplierResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public SupplierResponse findById(Long id){
        Supplier supplier = getSupplierById(id);
        return SupplierResponse.fromEntity(supplier);
    }

    private Supplier getSupplierById(Long id){
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }
}
