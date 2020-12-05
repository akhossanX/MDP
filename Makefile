
NAME =	Main.class

SRCS =	Main.java \
	environment/Environment.java \
	environment/Coordinate.java \
	algorithms/Algorithms.java \
	algorithms/AlgorithmResult.java \
	userInterface/MarkovWindow.java

CLASSOBJS = *.class environment/*.class algorithms/*.class userInterface/*.class

all: $(NAME) run

$(NAME):
	@javac $(SRCS)

run:
	@java $(NAME:.class=)

clean:
	/bin/rm -f $(CLASSOBJS)
