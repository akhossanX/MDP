package control;

import java.util.Random;

public class Coordinate
{
  private int row;
  private int col;
  
  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }
  
  public int getRow()
  {
    return row;
  }
  
  public int getCol()
  {
    return col;
  }
  

  public boolean equals(Object o)
  {
    return ((o instanceof Coordinate)) && (((Coordinate)o).getRow() == getRow()) && (((Coordinate)o).getCol() == getCol());
  }
  

  public static Coordinate generateRandom(int max)
  {
    return new Coordinate(new Random().nextInt(max), new Random().nextInt(max));
  }
}
