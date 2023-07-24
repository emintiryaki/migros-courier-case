package com.emin.couriercase.repository;

import com.emin.couriercase.entity.CourierStoreMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CourierStoreMatchRepository extends JpaRepository<CourierStoreMatch, Integer> {
    Optional<CourierStoreMatch> findByStoreNameAndCourierId(String storeName, Integer courierId);
}
