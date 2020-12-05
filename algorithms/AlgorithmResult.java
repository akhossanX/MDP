package algorithms;

public class AlgorithmResult
{
  private int[] policy;
  private int numberOfIterations;
  private long executionTime = 0L;
  private double[] utilityFunction;
  
  public AlgorithmResult(int[] policy, double[] utilityFunction, int numberOfIterations, long executionTime) 
  {
    this.policy = policy;
    this.numberOfIterations = numberOfIterations;
    this.utilityFunction = utilityFunction;
    this.executionTime = executionTime;
  }
  
  public int[] getPolicy()
  {
    return this.policy;
  }
  
  public int getNumberOfIterations()
  {
    return this.numberOfIterations;
  }
  
  public double[] getUtilityFunction() 
  {
    return this.utilityFunction;
  }
  
  public long getExecutionTime() 
  {
    return this.executionTime;
  }
}
