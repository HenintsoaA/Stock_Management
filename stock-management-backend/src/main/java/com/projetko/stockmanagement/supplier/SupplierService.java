package com.projetko.stockmanagement.supplier;

import com.projetko.stockmanagement.common.exception.DuplicateResourceException;
import com.projetko.stockmanagement.common.exception.ResourceNotFoundException;
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

    public SupplierResponse Create(SupplierRequest request) {

        // mijery fotsiny oe ef misy ve le email fa tsis hidiran'le email
        if (request.email() != null && supplierRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Supplier email already exists");
        }

        Supplier supplier = Supplier.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.address())
                .build();

        Supplier sevedSupplier = supplierRepository.save(supplier);
        return SupplierResponse.fromEntity(sevedSupplier);
    }

    public SupplierResponse update(Long id, SupplierRequest request) {
        Supplier supplier = getSupplierById(id);

        supplierRepository.findByEmail(request.email())
                .filter(existingSupplier -> !existingSupplier.getId().equals(id))
                .ifPresent(existingSupplier -> {
                    throw new DuplicateResourceException("Supplier email already exists");
                });

        supplier.setName(request.name());
        supplier.setEmail(request.email());
        supplier.setPhone(request.address());
        supplier.setAddress(request.address());

        Supplier updatedSupplier = supplierRepository.save(supplier);
        return SupplierResponse.fromEntity(updatedSupplier);

    }

    public void delete(Long id) {
        Supplier supplier = getSupplierById(id);
        supplierRepository.delete(supplier);
    }

    private Supplier getSupplierById(Long id){
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    }
}
