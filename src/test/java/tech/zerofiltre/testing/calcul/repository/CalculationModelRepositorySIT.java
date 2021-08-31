package tech.zerofiltre.testing.calcul.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.zerofiltre.testing.calcul.domain.model.CalculationModel;
import tech.zerofiltre.testing.calcul.domain.model.CalculationType;

@DataJpaTest
public class CalculationModelRepositorySIT {

  @Autowired
  CalculationModelRepository calculationModelRepository;

  @Test
  void save_mustGenerateId() {
    CalculationModel calculationModel = new CalculationModel(CalculationType.ADDITION, 1, 2, 3);
    calculationModelRepository.save(calculationModel);
    assertThat(calculationModel.getId()).isNotNull();
  }

}
