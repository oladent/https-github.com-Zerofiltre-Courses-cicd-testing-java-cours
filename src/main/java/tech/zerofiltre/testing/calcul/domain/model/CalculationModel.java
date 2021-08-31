package tech.zerofiltre.testing.calcul.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A model to represent a two argument integer calculation which needs to be performed.
 */
@Data
@Entity
@NoArgsConstructor
public class CalculationModel {

  private static final String SEPARATOR = " ";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer leftArgument;
  private Integer rightArgument;
  @Transient
  private CalculationType type;
  private Integer solution;
  private String formattedSolution;
  private String typeSymbol;

  public CalculationModel(CalculationType calculationType, int leftArgument, int rightArgument) {
    type = calculationType;
    this.leftArgument = leftArgument;
    this.rightArgument = rightArgument;
    this.typeSymbol = CalculationType.toSymbol(calculationType);

  }

  /**
   * Convenience Constructor used in test
   */
  public CalculationModel(CalculationType calculationType, int leftArgument, int rightArgument, Integer solution) {
    type = calculationType;
    this.leftArgument = leftArgument;
    this.rightArgument = rightArgument;
    this.solution = solution;
    this.typeSymbol = CalculationType.toSymbol(calculationType);
  }

  /**
   * Builds a Calculation from a string such as 2 + 2
   *
   * @param calculation in written form
   * @return model representing the calculatoin
   */
  public static CalculationModel fromText(String calculation) {
    final String[] parts = calculation.split(SEPARATOR);
    final int leftArgument = Integer.parseInt(parts[0]);
    final int rightArgument = Integer.parseInt(parts[2]);
    final CalculationType calculationType = CalculationType.fromSymbol(parts[1]);

    return new CalculationModel(calculationType, leftArgument, rightArgument);
  }

}
