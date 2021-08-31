package tech.zerofiltre.testing.calcul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.zerofiltre.testing.calcul.domain.model.CalculationModel;

public interface CalculationModelRepository extends JpaRepository<CalculationModel, Integer> {

}
