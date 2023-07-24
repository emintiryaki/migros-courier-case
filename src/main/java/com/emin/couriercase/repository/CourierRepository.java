package com.emin.couriercase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emin.couriercase.entity.Courier;
import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Integer> {
    Optional<Courier> findByCourierId(Integer courierId);
}