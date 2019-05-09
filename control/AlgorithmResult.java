package control;



public class AlgorithmResult
{
  private int[] policy;
  
  private int numberOfIterations;
  
  private long executionTime = 0L;
  private double[] utilityFunction;
  
  public AlgorithmResult(int[] policy, double[] utilityFunction, int numberOfIterations, long executionTime) {
    this.policy = policy;
    this.numberOfIterations = numberOfIterations;
    this.utilityFunction = utilityFunction;
    this.executionTime = executionTime;
  }
  
  public int[] getPolicy()
  {
    return policy;
  }
  
  public int getNumberOfIterations()
  {
    return numberOfIterations;
  }
  
  public double[] getUtilityFunction() {
    return utilityFunction;
  }
  
  public long getExecutionTime() {
    return executionTime;
  }
}
