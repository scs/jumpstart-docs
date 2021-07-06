package ch.scs.jumpstart.movierental.exercise.common.repository;

import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {}
