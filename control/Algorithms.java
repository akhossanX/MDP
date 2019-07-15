package control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Algorithms
{
  public Algorithms() {}
  
  public static AlgorithmResult valueIteration(Environment env, double epsilon, double GAMMA)
  {
    long executionTime = 0L;
    
    int iterations = 0;
    
    double[][] rewardMatrix = env.getRewardMatrix();
    
    int[] policy = new int[rewardMatrix.length];
    
    Arrays.fill(policy, -1);
    
    ArrayList<Integer> obstacles = env.getObstacles();
    

    double[] utility = new double[rewardMatrix.length];
    double[] utilityNew = new double[rewardMatrix.length];
    


    Arrays.fill(utilityNew, 0.0D);
    long startTime = System.currentTimeMillis();
    double delta;
    do {
      delta = 0.0D;
      iterations++;
      utility = Arrays.copyOf(utilityNew, utilityNew.length);
      for (int initialState = 0; initialState < rewardMatrix.length; initialState++)
      {

        if (!obstacles.contains(Integer.valueOf(initialState)))
        {
          utilityNew[initialState] = Double.NEGATIVE_INFINITY;
          for (int nextState = 0; nextState < rewardMatrix.length; nextState++)
          {

            double reward = rewardMatrix[initialState][nextState] + GAMMA * utility[nextState];
            
            if (utilityNew[initialState] < reward)
            {

              utilityNew[initialState] = reward;
              
              policy[initialState] = nextState;
            }
          }
        }
        else
        {
          utilityNew[initialState] = Double.NEGATIVE_INFINITY;
        }
      }
      
      for (int i = 0; i < utilityNew.length; i++)
      {
        if (Math.abs(utilityNew[i] - utility[i]) > delta) delta = Math.abs(utilityNew[i] - utility[i]);
      }
    } while (
    
      delta >= epsilon * (1.0D - GAMMA) / (2.0D * GAMMA));
    long endTime = System.currentTimeMillis();
    executionTime = endTime - startTime;
    return new AlgorithmResult(policy, utility, iterations, executionTime);
  }
  
  public static AlgorithmResult acceleratedValueIteration(Environment env, double epsilon, double GAMMA)
  {
    int iterations = 0;
    
    double[][] rewardMatrix = env.getRewardMatrix();
    
    int[] policy = new int[rewardMatrix.length];
    
    Arrays.fill(policy, -1);
    
    ArrayList<Integer> obstacles = env.getObstacles();
    

    double[] utility = new double[rewardMatrix.length];
    double[] utilityNew = new double[rewardMatrix.length];
    


    Arrays.fill(utilityNew, 0.0D);
    long startTime = System.currentTimeMillis();
    double delta;
    do {
      delta = 0.0D;
      iterations++;
      utility = Arrays.copyOf(utilityNew, utilityNew.length);
      for (int initialState = 0; initialState < rewardMatrix.length; initialState++)
      {

        if (!obstacles.contains(Integer.valueOf(initialState)))
        {
          utilityNew[initialState] = Double.NEGATIVE_INFINITY;
          for (int nextState = 0; nextState < rewardMatrix.length; nextState++)
          {


            if ((rewardMatrix[initialState][nextState] != Double.NEGATIVE_INFINITY) && (nextState != initialState))
            {

              double reward = 0.0D;
              if (nextState < initialState) {
                reward = rewardMatrix[initialState][nextState] + GAMMA * utilityNew[nextState];
              } else {
                reward = rewardMatrix[initialState][nextState] + GAMMA * utility[nextState];
              }
              if (utilityNew[initialState] < reward)
              {

                utilityNew[initialState] = reward;
                
                policy[initialState] = nextState;
              }
            }
          }
        } else {
          utilityNew[initialState] = Double.NEGATIVE_INFINITY;
        }
      }
      for (int i = 0; i < utilityNew.length; i++) {
        if (Math.abs(utilityNew[i] - utility[i]) > delta) delta = Math.abs(utilityNew[i] - utility[i]);
      }
    } while (
    
      delta >= epsilon * (1.0D - GAMMA) / (2.0D * GAMMA));
    
    long endTime = System.currentTimeMillis();
    
    long executionTime = endTime - startTime;
    return new AlgorithmResult(policy, utility, iterations, executionTime);
  }
  
  
  public static AlgorithmResult policyIteration(Environment env, double GAMMA)
  {
    long executionTime = 0L;
    double[][] rewardMatrix = env.getRewardMatrix();
    ArrayList<Integer> obstacles = env.getObstacles();
    
    int[] policy = new int[rewardMatrix.length];
    
    int iterations = 0;
    
    double[] utility = new double[rewardMatrix.length];
    
    Arrays.fill(utility, 0.0D);
    
    for (int i = 0; i < policy.length; i++)
    {
      if (!obstacles.contains(Integer.valueOf(i))) policy[i] = getRandomNextState(rewardMatrix, i); else
        policy[i] = -1;
    }
    long startTime = System.currentTimeMillis();
    boolean noChange;
    do {
      noChange = true;
      iterations++;
      
      utility = Arrays.copyOf(policyEvaluation(policy, utility, rewardMatrix, GAMMA), policyEvaluation(policy, utility, rewardMatrix, GAMMA).length);
      
      for (int initialState = 0; initialState < rewardMatrix.length; initialState++)
      {
        double maxUtility = utility[initialState];
        if (!obstacles.contains(Integer.valueOf(initialState)))
        {
          for (int nextState = 0; nextState < rewardMatrix.length; nextState++)
          {



            double reward = rewardMatrix[initialState][nextState] + GAMMA * utility[nextState];
            

            if (reward > maxUtility)
            {

              policy[initialState] = nextState;
              
              maxUtility = reward;
              noChange = false;
            }
            
          }
        }
      }
    } while (!noChange);
    long endTime = System.currentTimeMillis();
    executionTime = endTime - startTime;
    return new AlgorithmResult(policy, utility, iterations, executionTime);
  }
  

  public static int getRandomNextState(double[][] R, int currentState)
  {
    int randomNextState = -1;
    ArrayList<Integer> nextStates = new ArrayList();
    for (int j = 0; j < R.length; j++)
    {

      if (R[currentState][j] != Double.NEGATIVE_INFINITY) { nextStates.add(Integer.valueOf(j));
      }
    }
    if (!nextStates.isEmpty()) { randomNextState = ((Integer)nextStates.get(new Random().nextInt(nextStates.size()))).intValue();
    }
    return randomNextState;
  }
  

  public static double[] policyEvaluation(int[] policy, double[] utility, double[][] rewardMatrix, double GAMMA)
  {
    double[] newUtility = new double[utility.length];
    newUtility = Arrays.copyOf(utility, utility.length);
    

    for (int i = 0; i < 10; i++) {
      for (int initialState = 0; initialState < policy.length; initialState++)
      {


        if (policy[initialState] != -1)
        {

          if (rewardMatrix[initialState][policy[initialState]] != Double.NEGATIVE_INFINITY)
          {
            newUtility[initialState] = (rewardMatrix[initialState][policy[initialState]] + GAMMA * newUtility[policy[initialState]]); }
        }
      }
    }
    return newUtility;
  }
}
