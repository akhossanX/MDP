package environment;

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
  private double[][] reward;
  
  public Environment(int numberOfStates, double obstaclesPer)
  {
    this.numberOfStates = ((int)Math.pow((int)Math.sqrt(numberOfStates), 2.0D));
    this.numberOfRows = ((int)Math.sqrt(this.numberOfStates));
    this.obstaclesPer = obstaclesPer;
    reward = new double[this.numberOfStates][this.numberOfStates];
    obstacles = new ArrayList<Coordinate>();
    obstacleStates = new ArrayList<Integer>();
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
        if (rowState.getCol() == colState.getCol()
			&& rowState.getRow() - 1 == colState.getRow()) 
			reward[i][j] = -1.0D;
        else if (rowState.getCol() == colState.getCol()
			&& rowState.getRow() + 1 == colState.getRow()) 
			reward[i][j] = -1.0D;
        else if ((rowState.getCol() - 1 == colState.getCol())
			&& (rowState.getRow() == colState.getRow()))
			reward[i][j] = -1.0D;
        else if (rowState.getCol() + 1 == colState.getCol()
			&& rowState.getRow() == colState.getRow())
			reward[i][j] = -1.0D;
	   	else
        	reward[i][j] = Double.NEGATIVE_INFINITY;
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
      this.obstacles.add(obstacle);
      this.obstacleStates.add(Integer.valueOf(obstacle.getRow() * (int)Math.sqrt(numberOfStates) + obstacle.getCol()));
    }
    do
    {
      initialState = Coordinate.generateRandom((int)Math.sqrt(numberOfStates));
    }
   	while (initialState.equals(goal) || obstacles.contains(initialState) || 
		(Math.abs(initialState.getCol() - goal.getCol()) < numberOfRows / 2) ||
		(Math.abs(initialState.getRow() - goal.getRow()) < numberOfRows / 2));
    this.goalIndex = (goal.getRow() * (int)Math.sqrt(numberOfStates) + goal.getCol());
    this.startIndex = (initialState.getRow() * (int)Math.sqrt(numberOfStates) + initialState.getCol());
    this.reward[goalIndex][goalIndex] = 1000.0D;
    if (goal.getCol() - 1 >= 0)
    {
      int westState = goal.getRow() * (int)Math.sqrt(numberOfStates) + (goal.getCol() - 1);
      reward[westState][goalIndex] = 1000.0D;
    }
    if (goal.getCol() + 1 < (int)Math.sqrt(numberOfStates))
    {
      int eastState = goal.getRow() * (int)Math.sqrt(numberOfStates) + (goal.getCol() + 1);
      reward[eastState][goalIndex] = 1000.0D;
    }  
    if (goal.getRow() - 1 >= 0)
    {
      int northState = (goal.getRow() - 1) * (int)Math.sqrt(numberOfStates) + goal.getCol();
      reward[northState][goalIndex] = 1000.0D;
    }
    if (goal.getRow() + 1 < (int)Math.sqrt(numberOfStates))
    {
      int southState = (goal.getRow() + 1) * (int)Math.sqrt(numberOfStates) + goal.getCol();
      reward[southState][goalIndex] = 1000.0D;
    }
    for (Coordinate obs : obstacles)
    {
      int obstacleIndex = obs.getRow() * (int)Math.sqrt(numberOfStates) + obs.getCol();   
      if (obs.getCol() - 1 >= 0)
      {
        int westState = obs.getRow() * (int)Math.sqrt(numberOfStates) + (obs.getCol() - 1);
        this.reward[westState][obstacleIndex] = Double.NEGATIVE_INFINITY;
      }
      if (obs.getCol() + 1 < (int)Math.sqrt(numberOfStates))
      {
        int eastState = obs.getRow() * (int)Math.sqrt(numberOfStates) + (obs.getCol() + 1);
        this.reward[eastState][obstacleIndex] = Double.NEGATIVE_INFINITY;
      }
      if (obs.getRow() - 1 >= 0)
      {
        int northState = (obs.getRow() - 1) * (int)Math.sqrt(numberOfStates) + obs.getCol();
        this.reward[northState][obstacleIndex] = Double.NEGATIVE_INFINITY;
      }
      if (obs.getRow() + 1 < (int)Math.sqrt(numberOfStates))
      {
        int southState = (obs.getRow() + 1) * (int)Math.sqrt(numberOfStates) + obs.getCol();
        this.reward[southState][obstacleIndex] = Double.NEGATIVE_INFINITY;
      }
    }
  }

  /*
  ** Getters and setters
  */

  public ArrayList<Integer> getObstacles()
  {
    return this.obstacleStates;
  }
  
  public ArrayList<Coordinate> getObstaclesCoordinate()
  {
    return this.obstacles;
  }
  
  public double[][] getRewardMatrix()
  {
    return this.reward;
  }
  
  public int getNumberOfStates()
  {
    return this.numberOfStates;
  }
  
  public int getNumberOfRows()
  {
    return this.numberOfRows;
  }
  
  public Coordinate getGoal()
  {
    return this.goal;
  }
  
  public Coordinate getInitialState()
  {
    return this.initialState;
  }
  
  public int getStartIndex()
  {
    return this.startIndex;
  }
  
  public int getGoalIndex()
  {
    return this.goalIndex;
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
  
}
