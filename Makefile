
NAME =	Main

SRCS =	Main.java \
	environment/Environment.java \
	environment/Coordinate.java \
	algorithms/Algorithms.java \
	algorithms/AlgorithmResult.java \
	userInterface/MarkovWindow.java

CLASSOBJS = *.class environment/*.class algorithms/*.class userInterface/*.class

all:
	@javac $(SRCS)

run:
	@java $(NAME)

clean:
	/bin/rm -f $(CLASSOBJS)
