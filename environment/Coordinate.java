package environment;

import java.util.Random;

public class Coordinate
{
  private int row;
  private int col;
  
  public Coordinate(int row, int col) 
  {
    this.row = row;
    this.col = col;
  }
  
  public int getRow()
  {
    return this.row;
  }
  
  public int getCol()
  {
    return this.col;
  }

  public boolean equals(Object o)
  {
    return ((o instanceof Coordinate)) && (((Coordinate)o).getRow() == this.getRow()) && (((Coordinate)o).getCol() == this.getCol());
  }

  public static Coordinate generateRandom(int max)
  {
    return new Coordinate(new Random().nextInt(max), new Random().nextInt(max));
  }
}
