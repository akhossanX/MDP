package control;

import java.io.PrintStream;
import java.util.ArrayList;


public class Environment
{
  private int numberOfStates;
  private int numberOfRows;
  private double obstaclesPer;
  private ArrayList<Coordinate> obstacles;
  private ArrayList<Integer> obstacleStates;
  private Coordinate goal;
  private Coordinate initialState;
  private int startIndex;
  private int goalIndex;
  private double[][] R;
  
  public Environment(int numberOfStates, double obstaclesPer)
  {
    this.numberOfStates = ((int)Math.pow((int)Math.sqrt(numberOfStates), 2.0D));
    numberOfRows = ((int)Math.sqrt(this.numberOfStates));
    this.obstaclesPer = obstaclesPer;
    R = new double[this.numberOfStates][this.numberOfStates];
    obstacles = new ArrayList();
    obstacleStates = new ArrayList();
    generate();
  }
  

  public void generate()
  {
    for (int i = 0; i < numberOfStates; i++)
    {
      Coordinate rowState = new Coordinate(i / numberOfRows, i % numberOfRows);
      
      for (int j = 0; j < numberOfStates; j++)
      {
        Coordinate colState = new Coordinate(j / numberOfRows, j % numberOfRows);
        

        if ((rowState.getCol() == colState.getCol()) && (rowState.getRow() - 1 == colState.getRow())) { R[i][j] = -1.0D;
        } else if ((rowState.getCol() == colState.getCol()) && (rowState.getRow() + 1 == colState.getRow())) { R[i][j] = -1.0D;
        } else if ((rowState.getCol() - 1 == colState.getCol()) && (rowState.getRow() == colState.getRow())) { R[i][j] = -1.0D;
        } else if ((rowState.getCol() + 1 == colState.getCol()) && (rowState.getRow() == colState.getRow())) R[i][j] = -1.0D; else {
          R[i][j] = Double.NEGATIVE_INFINITY;
        }
      }
    }
    
    goal = Coordinate.generateRandom((int)Math.sqrt(numberOfStates));
    
    Coordinate obstacle;
    for (int i = 0; i < (int)(numberOfStates * obstaclesPer); i++)
    {

      do
      {
        obstacle = Coordinate.generateRandom((int)Math.sqrt(numberOfStates));
      }
      while ((obstacle.equals(goal)) || (obstacles.contains(obstacle)));
      
      obstacles.add(obstacle);
      obstacleStates.add(Integer.valueOf(obstacle.getRow() * (int)Math.sqrt(numberOfStates) + obstacle.getCol()));
    }
    
    do
    {
      initialState = Coordinate.generateRandom((int)Math.sqrt(numberOfStates));
    } while ((initialState.equals(goal)) || (obstacles.contains(initialState)) || (Math.abs(initialState.getCol() - goal.getCol()) < numberOfRows / 2) || (Math.abs(initialState.getRow() - goal.getRow()) < numberOfRows / 2));
    
    goalIndex = (goal.getRow() * (int)Math.sqrt(numberOfStates) + goal.getCol());
    
    startIndex = (initialState.getRow() * (int)Math.sqrt(numberOfStates) + initialState.getCol());
    
    R[goalIndex][goalIndex] = 1000.0D;
    
    if (goal.getCol() - 1 >= 0)
    {
      int westState = goal.getRow() * (int)Math.sqrt(numberOfStates) + (goal.getCol() - 1);
      R[westState][goalIndex] = 1000.0D;
    }
    
    if (goal.getCol() + 1 < (int)Math.sqrt(numberOfStates))
    {
      int eastState = goal.getRow() * (int)Math.sqrt(numberOfStates) + (goal.getCol() + 1);
      R[eastState][goalIndex] = 1000.0D;
    }  

    if (goal.getRow() - 1 >= 0)
    {
      int northState = (goal.getRow() - 1) * (int)Math.sqrt(numberOfStates) + goal.getCol();
      R[northState][goalIndex] = 1000.0D;
    }
    
    if (goal.getRow() + 1 < (int)Math.sqrt(numberOfStates))
    {
      int southState = (goal.getRow() + 1) * (int)Math.sqrt(numberOfStates) + goal.getCol();
      R[southState][goalIndex] = 1000.0D;
    }
    
    for (Coordinate obstacle : obstacles)
    {
      int obstacleIndex = obstacle.getRow() * (int)Math.sqrt(numberOfStates) + obstacle.getCol();   

      if (obstacle.getCol() - 1 >= 0)
      {
        int westState = obstacle.getRow() * (int)Math.sqrt(numberOfStates) + (obstacle.getCol() - 1);
        R[westState][obstacleIndex] = Double.NEGATIVE_INFINITY;
      }
      if (obstacle.getCol() + 1 < (int)Math.sqrt(numberOfStates))
      {
        int eastState = obstacle.getRow() * (int)Math.sqrt(numberOfStates) + (obstacle.getCol() + 1);
        R[eastState][obstacleIndex] = Double.NEGATIVE_INFINITY;
      }
      

      if (obstacle.getRow() - 1 >= 0)
      {
        int northState = (obstacle.getRow() - 1) * (int)Math.sqrt(numberOfStates) + obstacle.getCol();
        R[northState][obstacleIndex] = Double.NEGATIVE_INFINITY;
      }
      if (obstacle.getRow() + 1 < (int)Math.sqrt(numberOfStates))
      {
        int southState = (obstacle.getRow() + 1) * (int)Math.sqrt(numberOfStates) + obstacle.getCol();
        R[southState][obstacleIndex] = Double.NEGATIVE_INFINITY;
      }
    }
  }
  /*
  **  For Debugging purposes
  */
  public void drawEnvironment()
  {
    for (int i = 0; i < numberOfRows; i++)
    {
      for (int j = 0; j < numberOfRows; j++) System.out.print("----");
      System.out.println("-");
      for (int j = 0; j < numberOfRows; j++)
      {
        Coordinate state = new Coordinate(i, j);
        int index = i * (int)Math.sqrt(numberOfStates) + j;
        if (state.equals(goal)) { System.out.print("| G ");
        } else if (state.equals(initialState)) { System.out.print("| S ");
        } else if (obstacles.contains(state)) System.out.print("| # "); else
          System.out.print("|  " + index);
      }
      System.out.println("|");
    }
    for (int j = 0; j < numberOfRows; j++) System.out.print("----");
    System.out.println("-");
  }
  

  public ArrayList<Integer> getObstacles()
  {
    return obstacleStates;
  }
  
  public ArrayList<Coordinate> getObstaclesCoordinate()
  {
    return obstacles;
  }
  
  public double[][] getRewardMatrix() {
    return R;
  }
  
  public int getNumberOfStates() {
    return numberOfStates;
  }
  
  public int getNumberOfRows() {
    return numberOfRows;
  }
  
  public Coordinate getGoal() {
    return goal;
  }
  
  public Coordinate getInitialState() {
    return initialState;
  }
  
  public int getStartIndex() {
    return startIndex;
  }
  
  public int getGoalIndex() {
    return goalIndex;
  }
}
