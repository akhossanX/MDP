package userInterface;

import algorithms.AlgorithmResult;
import algorithms.Algorithms;
import environment.Coordinate;
import environment.Environment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MarkovWindow extends JFrame
{
  private static final long serialVersionUID = 1L;
  private Environment env;
  private JPanel[][] stateSquare;
  private JPanel contentPan;
  private JPanel panelEnvironment;
  private JPanel panelBottom;
  private JTextField numberOfStates;
  private JTextField obstaclePer;
  private JTextField epsilon;
  private JTextField GAMMA;
  private JButton generate;
  private JButton VI;
  private JButton PI;
  private JButton GSJVI;
  private JButton MVI;
  private JLabel statesLabel = new JLabel("Number of States");
  private JLabel obsPerLabel = new JLabel("% of Obstacles:");
  private JLabel epsilonLabel = new JLabel("Epsilon:");
  private JLabel gammaLabel = new JLabel("Gamma:");
  private JLabel generateLabel = new JLabel("Environment");
  private JLabel algoLabel = new JLabel("Algorithms:");
  private JLabel viLabel;
  private JLabel viTimeLabel;
  private JLabel piLabel;
  private JLabel piTimeLabel;
  private JLabel mviLabel;
  private JLabel mviTimeLabel;
  private int numOfStates = 0;
  private double obsPercentage = 0.0D;
  private double discountFactor = 0.0D;
  private double error = 0.0D;
  
  public MarkovWindow()
  {
    JMenuBar menuBar = new JMenuBar();
    JMenu fichier = new JMenu("Files");
    JMenu help = new JMenu("Help");
    JMenuItem fermer = new JMenuItem(new AbstractAction("Close")
    {
      private static final long serialVersionUID = 1L;
      
      public void actionPerformed(ActionEvent arg0)
      {
        System.exit(0);
      }
    });
    JMenuItem aPropos = new JMenuItem(new AbstractAction("About")
    {
      private static final long serialVersionUID = 1L;

      public void actionPerformed(ActionEvent arg0) {
        EventQueue.invokeLater(new Runnable() {
          public void run() {
            JPanel labelsJPanel = new JPanel(new GridLayout(2, 1));
            JLabel label1 = new JLabel("Projet de fin d'études sous le thème: ");
            JLabel label2 = new JLabel("Comparaison des methodes iteratives de resolution des processus decisionnels de Markov");
            labelsJPanel.add(label1);labelsJPanel.add(label2);
            JOptionPane.showMessageDialog(
              null, 
              labelsJPanel, 
              "About", 1);
          }
        });
      }
    });
    fichier.add(fermer);
    help.add(aPropos);
    menuBar.add(fichier);
    menuBar.add(help);
    setJMenuBar(menuBar);
    contentPan = new JPanel();
    contentPan.setLayout(new BorderLayout());
    contentPan.setBackground(Color.white);
    numberOfStates = new JTextField("0", 7);
    obstaclePer = new JTextField("0", 7);
    epsilon = new JTextField("0", 7);
    GAMMA = new JTextField("0", 7);
    generate = new JButton("Generate Environment");
    generate.setFocusable(true);
    generate.setBackground(Color.orange);
    VI = new JButton("Value Iteration");
    VI.setBackground(Color.orange);
    PI = new JButton("Policy Iteration");
    PI.setBackground(Color.orange);
    GSJVI = new JButton("GSJ Value Iteration");
    GSJVI.setBackground(Color.orange);
    MVI = new JButton("Accelerated VI");
    MVI.setBackground(Color.orange);
    paint();
  }

  public void paint()
  {
    obstaclePer.setBorder(BorderFactory.createEtchedBorder(Color.red, Color.orange));
    obstaclePer.setFont(new Font("American Typewriter", 1, 18));
    obstaclePer.setToolTipText("Must be between 10% and 40%");
    epsilon.setBorder(BorderFactory.createEtchedBorder(Color.red, Color.orange));
    epsilon.setFont(new Font("American Typewriter", 1, 18));
    epsilon.setToolTipText("epsilon must be > 0");
    GAMMA.setBorder(BorderFactory.createEtchedBorder(Color.red, Color.orange));
    GAMMA.setFont(new Font("American Typewriter", 1, 18));
    GAMMA.setToolTipText("Gamma must be > 0 and < 1");
    numberOfStates.setBorder(BorderFactory.createEtchedBorder(Color.red, Color.orange));
    numberOfStates.setFont(new Font("American Typewriter", 1, 18));
    numberOfStates.setToolTipText("Number of states between 10 and 4000");
    generate.setPreferredSize(new Dimension(100, 30));
    generate.setFont(new Font("American Typewriter", 1, 12));
    Font labelFont = new Font("American Typewriter", 0, 12);
    statesLabel.setFont(labelFont);
    statesLabel.setForeground(Color.black);
    obsPerLabel.setFont(labelFont);
    obsPerLabel.setForeground(Color.black);
    epsilonLabel.setFont(labelFont);
    epsilonLabel.setForeground(Color.black);
    gammaLabel.setFont(labelFont);
    gammaLabel.setForeground(Color.black);
    generateLabel.setFont(labelFont);
    generateLabel.setForeground(Color.black);
    algoLabel.setFont(labelFont);
    algoLabel.setForeground(Color.black);
    JPanel statesPan = new JPanel(new FlowLayout(4));
    statesPan.setBorder(BorderFactory.createTitledBorder("States"));
    statesPan.setBackground(Color.LIGHT_GRAY);
    statesPan.add(statesLabel);
    statesPan.add(numberOfStates);
    JPanel obsPerPan = new JPanel(new FlowLayout(4));
    obsPerPan.setBorder(BorderFactory.createTitledBorder("Obstacles"));
    obsPerPan.setBackground(Color.LIGHT_GRAY);
    obsPerPan.add(obsPerLabel);
    obsPerPan.add(obstaclePer);
    JPanel epsilonPan = new JPanel(new FlowLayout(4));
    epsilonPan.setBorder(BorderFactory.createTitledBorder("Precision"));
    epsilonPan.setBackground(Color.LIGHT_GRAY);
    epsilonPan.add(epsilonLabel);
    epsilonPan.add(epsilon);
    JPanel gammaPan = new JPanel(new FlowLayout(4));
    gammaPan.setBorder(BorderFactory.createTitledBorder("Gamma factor"));
    gammaPan.setBackground(Color.LIGHT_GRAY);
    gammaPan.add(gammaLabel);
    gammaPan.add(GAMMA);
    JPanel regPan = new JPanel(new FlowLayout(4));
    regPan.setBorder(BorderFactory.createTitledBorder("Environment"));
    regPan.setBackground(Color.LIGHT_GRAY);
    regPan.add(generateLabel);
    regPan.add(generate);
    JPanel parametersPanel = new JPanel();
    parametersPanel.setLayout(new GridLayout(5, 2));
    parametersPanel.setPreferredSize(new Dimension(250, getHeight()));
    parametersPanel.setBorder(BorderFactory.createEtchedBorder(Color.red, Color.orange));
    parametersPanel.setBackground(Color.LIGHT_GRAY);
    parametersPanel.add(statesPan);
    parametersPanel.add(obsPerPan);
    parametersPanel.add(epsilonPan);
    parametersPanel.add(gammaPan);
    parametersPanel.add(regPan);
    viLabel = new JLabel("0 iteration"); 
    viTimeLabel = new JLabel("/ 0 ms");
    piLabel = new JLabel("0 iteration");
    piTimeLabel = new JLabel("/ 0 ms");
    mviLabel = new JLabel("0 iteration");
    mviTimeLabel = new JLabel("/ 0 ms");
    algoLabel.setSize(300, 50);
    JPanel algoLabelPanel = new JPanel(new FlowLayout(3));
    algoLabelPanel.setBackground(Color.LIGHT_GRAY);
    algoLabelPanel.setPreferredSize(new Dimension(parametersPanel.getWidth(), algoLabelPanel.getHeight()));
    algoLabelPanel.add(algoLabel);
    JPanel viPanel = new JPanel(new FlowLayout(3));
    viPanel.setBackground(Color.LIGHT_GRAY);
    viPanel.add(VI);
    viPanel.add(viLabel);
    viPanel.add(viTimeLabel);
    JPanel piPanel = new JPanel(new FlowLayout(3));
    piPanel.setBackground(Color.LIGHT_GRAY);
    piPanel.add(PI);
    piPanel.add(piLabel);
    piPanel.add(piTimeLabel);
    JPanel mviPanel = new JPanel(new FlowLayout(3));
    mviPanel.setBackground(Color.LIGHT_GRAY);
    mviPanel.add(MVI);
    mviPanel.add(mviLabel);
    mviPanel.add(mviTimeLabel);
    panelBottom = new JPanel(new FlowLayout(3));
    panelBottom.setLayout(new GridLayout(1, 4));
    panelBottom.setBorder(BorderFactory.createEtchedBorder(Color.red, Color.orange));
    panelBottom.add(algoLabelPanel);
    panelBottom.add(viPanel);
    panelBottom.add(piPanel);
    panelBottom.add(mviPanel);
    panelBottom.setBackground(Color.cyan);
    panelEnvironment = new JPanel();
    panelEnvironment.setBackground(Color.white);
    panelEnvironment.setBorder(BorderFactory.createEtchedBorder(Color.red, Color.orange));
    contentPan.add(parametersPanel, "West");
    contentPan.add(panelBottom, "South");
    contentPan.add(panelEnvironment, "Center");
    generate.addActionListener(new GenerateEnvironmentListener());
    VI.addActionListener(new ValueIterationListener());
    PI.addActionListener(new PolicyIterationListener());
    MVI.addActionListener(new acceleratedValueIterationListener());
    ImageIcon icon = new ImageIcon(getClass().getResource("../assets/faculty.jpg"));
    setIconImage(icon.getImage());
    setContentPane(contentPan);
    setLocation(5, 5);
    setSize(1350, 715);
    setResizable(true);
    setTitle("Comparison of iterative algorithms");
    setDefaultCloseOperation(3);
    setVisible(true);
  }

  class GenerateEnvironmentListener implements ActionListener
  {
    GenerateEnvironmentListener() {}
    public void actionPerformed(ActionEvent event)
    {
      numOfStates = Integer.parseInt(numberOfStates.getText());
      obsPercentage = Double.parseDouble(obstaclePer.getText());
      double gamma = Double.parseDouble(GAMMA.getText());
      algoLabel.setForeground(Color.BLACK);
      if (numOfStates >= 10 && numOfStates <= 4000 
	 && obsPercentage >= 0.1D && obsPercentage <= 0.4D
	 && gamma > 0.0D && gamma < 1.0D)
      {
        piLabel.setText("0 iteration");piTimeLabel.setText("/0 ms");
        viLabel.setText("0 iteration");viTimeLabel.setText("/0 ms");
        mviLabel.setText("0 iteration");mviTimeLabel.setText("/0 ms");
        env = new Environment(numOfStates, obsPercentage);
        ArrayList<Coordinate> obstacles = env.getObstaclesCoordinate();
        int rows = env.getNumberOfRows();
        panelEnvironment.removeAll();
        panelEnvironment.setBackground(Color.white);
        panelEnvironment.setLayout(new GridLayout(0, rows));
        stateSquare = new JPanel[rows][rows];
        for (int i = 0; i < rows; i++) 
	{
          for (int j = 0; j < rows; j++)
          {
            stateSquare[i][j] = new JPanel();
            stateSquare[i][j].setBorder(BorderFactory.createBevelBorder(0));
            panelEnvironment.add(stateSquare[i][j]);
          }
        }
        for (int i = 0; i < rows; i++) 
	{
          for (int j = 0; j < rows; j++)
          {
            if ((i == env.getInitialState().getRow()) && (j == env.getInitialState().getCol()))
            {
              stateSquare[i][j].setBackground(Color.RED);
              stateSquare[i][j].setToolTipText("START");

            }
            else if ((i == env.getGoal().getRow()) && (j == env.getGoal().getCol()))
            {
              stateSquare[i][j].setBackground(Color.green);
              stateSquare[i][j].setToolTipText("GOAL");
            }
            else
	    {
              stateSquare[i][j].setBackground(Color.WHITE);
            }
            for (Coordinate obstacle : obstacles)
            {
              if ((i == obstacle.getRow()) && (j == obstacle.getCol())) 
	      {
                stateSquare[i][j].setBackground(Color.black);
                stateSquare[i][j].setToolTipText("OBSTACLE");
              } 
	    }
          }
        }
        algoLabel.setText("Algorithme de résolution");algoLabel.setBackground(Color.BLACK);
        panelEnvironment.validate();
        panelEnvironment.repaint();
      }
      else if ((numOfStates < 10) || (numOfStates > 4000))
      {
        algoLabel.setText("le nombre d'états doit etre >= 10 et <= 4000");
        algoLabel.setFont(new Font("Apple LiSung", 2, 12));
        algoLabel.setForeground(Color.red);
      }
      else if ((obsPercentage < 0.1D) || (obsPercentage > 0.4D))
      {
        algoLabel.setText("% d'obstacles doit etre >= 0.1 et <= 0.4");
        algoLabel.setFont(new Font("Apple LiSung", 2, 12));
        algoLabel.setForeground(Color.red);
      }
      else if ((gamma <= 0.0D) || (gamma >= 1.0D))
      {
        algoLabel.setText("Gamma doit etre > 0 et < 1");
        algoLabel.setFont(new Font("Apple LiSung", 2, 12));
        algoLabel.setForeground(Color.red);
      }
    }
  }
  
  class ValueIterationListener implements ActionListener 
  {
    ValueIterationListener() {}
    public void actionPerformed(ActionEvent arg0) 
    {
      repaintEnvironment();
      error = Double.parseDouble(epsilon.getText());
      discountFactor = Double.parseDouble(GAMMA.getText());
      AlgorithmResult res = Algorithms.valueIteration(env, error, discountFactor);
      int[] policy = new int[env.getNumberOfStates()];
      for (int i = 0; i < env.getNumberOfStates(); i++)
      {
        policy[i] = res.getPolicy()[i];
      }
      ArrayList<Integer> pathTracking = new ArrayList<Integer>();
      int numberOfRows = env.getNumberOfRows();
      int i = env.getStartIndex();int count = 0;boolean isUnreachable = false;
      while (i != env.getGoalIndex())
      {
        if (policy[i] != -1)
        {
          if (count > env.getNumberOfStates())
          {
            isUnreachable = true;
            break;
          }
          pathTracking.add(Integer.valueOf(policy[i]));
          i = policy[i];
          count++;
        }
        else
        {
          isUnreachable = true;
          break;
        }
      }
      if (!pathTracking.isEmpty()) 
        pathTracking.remove(pathTracking.size() - 1);
      if (!isUnreachable)
      {
        for (int j = 0; j < pathTracking.size(); j++)
        {
          int row = (pathTracking.get(j)).intValue() / numberOfRows;
          int column = (pathTracking.get(j)).intValue() % numberOfRows;
          stateSquare[row][column].setBackground(Color.ORANGE);
          stateSquare[row][column].repaint();
          stateSquare[row][column].revalidate();
        }
        algoLabel.setText("Algorithme de résolution:");
        algoLabel.setForeground(Color.black);
        viLabel.setText(res.getNumberOfIterations() + " itérations");
        viTimeLabel.setText("/" + res.getExecutionTime() + " ms");
      }
      else
      {
        algoLabel.setText("le but est non atteignable ou la politique est sous-optimal");
        algoLabel.setForeground(Color.red);
        viLabel.setText(res.getNumberOfIterations() + " itérations");
        viTimeLabel.setText("/" + res.getExecutionTime() + " ms");
        panelBottom.repaint();
        panelBottom.revalidate();
      }
    }
  }
  
  class PolicyIterationListener implements ActionListener
  {
    PolicyIterationListener() {}

    public void actionPerformed(ActionEvent arg0) {
      repaintEnvironment();
      discountFactor = Double.parseDouble(GAMMA.getText());
      AlgorithmResult res = Algorithms.policyIteration(env, discountFactor);
      int[] policy = new int[env.getNumberOfStates()];
      for (int i = 0; i < env.getNumberOfStates(); i++)
      {
        policy[i] = res.getPolicy()[i];
      }
      ArrayList<Integer> pathTracking = new ArrayList<Integer>();
      int numberOfRows = env.getNumberOfRows();
      int i = env.getStartIndex();int count = 0;boolean isUnreachable = false;
      while (i != env.getGoalIndex())
      {
        if (policy[i] != -1)
        {
          if (count > env.getNumberOfStates())
          {
            isUnreachable = true;
            break;
          }
          pathTracking.add(Integer.valueOf(policy[i]));
          i = policy[i];
          count++;
        }
        else
        {
          isUnreachable = true;
          break;
        }
      }
      if (!pathTracking.isEmpty()) {
        pathTracking.remove(pathTracking.size() - 1);
      }
      if (!isUnreachable)
      {
        for (int j = 0; j < pathTracking.size(); j++)
        {
          int row = (pathTracking.get(j)).intValue() / numberOfRows;
          int column = (pathTracking.get(j)).intValue() % numberOfRows;
          stateSquare[row][column].setBackground(Color.BLUE);
          stateSquare[row][column].repaint();
          stateSquare[row][column].revalidate();
        }
        algoLabel.setText("Algorithme de résolution:");
        algoLabel.setForeground(Color.black);
        piLabel.setText(res.getNumberOfIterations() + " itérations");
        piTimeLabel.setText("/" + res.getExecutionTime() + " ms");
      }
      else
      {
        algoLabel.setText("le but est non atteignable ou la politique est sous-optimal");
        algoLabel.setForeground(Color.red);
        piLabel.setText(res.getNumberOfIterations() + " itérations");
        piTimeLabel.setText("/" + res.getExecutionTime() + " ms");
        panelBottom.repaint();
        panelBottom.revalidate();
      }
    }
  }

  class acceleratedValueIterationListener implements ActionListener
  {
    acceleratedValueIterationListener() {}
    public void actionPerformed(ActionEvent arg0)
    {
      repaintEnvironment();
      error = Double.parseDouble(epsilon.getText());
      discountFactor = Double.parseDouble(GAMMA.getText());
      AlgorithmResult res = Algorithms.acceleratedValueIteration(env, error, discountFactor);
      int[] policy = new int[env.getNumberOfStates()];
      for (int i = 0; i < env.getNumberOfStates(); i++)
      {
        policy[i] = res.getPolicy()[i];
      }
      ArrayList<Integer> pathTracking = new ArrayList<Integer>();
      int numberOfRows = env.getNumberOfRows();
      int i = env.getStartIndex();
      int count = 0;
      boolean isUnreachable = false;
      while (i != env.getGoalIndex())
      {
        if (policy[i] != -1)
        {
          if (count > env.getNumberOfStates())
          {
            isUnreachable = true;
            break;
          }
          pathTracking.add(Integer.valueOf(policy[i]));
          i = policy[i];
          count++;
        }
        else
        {
          isUnreachable = true;
          break;
        }
      }
      if (!pathTracking.isEmpty())
        pathTracking.remove(pathTracking.size() - 1);
      if (!isUnreachable)
      {
        for (int j = 0; j < pathTracking.size(); j++)
        {
          int row = (pathTracking.get(j)).intValue() / numberOfRows;
          int column = (pathTracking.get(j)).intValue() % numberOfRows;
          stateSquare[row][column].setBackground(Color.MAGENTA);
          stateSquare[row][column].repaint();
          stateSquare[row][column].revalidate();
        }
        algoLabel.setText("Algorithme de résolution:");
        algoLabel.setForeground(Color.black);
        mviLabel.setText(res.getNumberOfIterations() + " itérations");
        mviTimeLabel.setText("/" + res.getExecutionTime() + " ms");
      }
      else
      {
        algoLabel.setText("le but est non atteignable ou la politique est sous-optimal");
        algoLabel.setForeground(Color.red);
        mviLabel.setText(res.getNumberOfIterations() + " itérations");
        mviTimeLabel.setText("/" + res.getExecutionTime() + " ms");
        panelBottom.repaint();
        panelBottom.revalidate();
      }
    }
  }
  
  public void repaintEnvironment()
  {
    algoLabel.setText("Algorithmes de résolution:");
    algoLabel.setForeground(Color.black);
    viLabel.setText("0 itérations");
    viTimeLabel.setText("/0 ms");
    piLabel.setText("0 itérations");
    piTimeLabel.setText("/0 ms");
    mviLabel.setText("0 itérations");
    mviTimeLabel.setText("/0 ms");
    ArrayList<Coordinate> obstacles = env.getObstaclesCoordinate();
    int rows = env.getNumberOfRows();
    panelEnvironment.removeAll();
    panelEnvironment.setBackground(Color.white);
    panelEnvironment.setLayout(new GridLayout(0, rows));
    stateSquare = new JPanel[rows][rows];
    for (int i = 0; i < rows; i++)
      for (int j = 0; j < rows; j++)
      {
        stateSquare[i][j] = new JPanel();
        stateSquare[i][j].setBorder(BorderFactory.createBevelBorder(0));
        if ((i == env.getInitialState().getRow()) && (j == env.getInitialState().getCol()))
        {
          stateSquare[i][j].setBackground(Color.red);
          stateSquare[i][j].setToolTipText("START");
        }
        else if ((i == env.getGoal().getRow()) && (j == env.getGoal().getCol()))
        {
          stateSquare[i][j].setBackground(Color.green);
          stateSquare[i][j].setToolTipText("GOAL");
        } else {
          stateSquare[i][j].setBackground(Color.WHITE);
        }
        for (Coordinate obstacle : obstacles)
        {
          if ((i == obstacle.getRow()) && (j == obstacle.getCol()))
          {
            stateSquare[i][j].setBackground(Color.black);
            stateSquare[i][j].setToolTipText("OBSTACLE");
          }
        }
        panelEnvironment.add(stateSquare[i][j]);
      }
    panelEnvironment.validate();
    panelEnvironment.repaint();
  }
}
