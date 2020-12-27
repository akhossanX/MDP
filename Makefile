
NAME =	Main.class

SRCS =	Main.java \
		environment/Environment.java \
		environment/Coordinate.java \
		algorithms/Algorithms.java \
		algorithms/AlgorithmResult.java \
		userInterface/MarkovWindow.java

CLASSDIR = class/

CLASSOBJS = $(addprefix $(CLASSDIR), $(shell basename -a $(SRCS))

all: $(NAME) run

$(NAME): $(CLASSDIR)
	@javac $(SRCS) -d $<
	@mv $(CLASSDIR)Main.class .

run:
	@java $(NAME:.class=)

clean:
	/bin/rm -rf $(CLASSDIR)

$(CLASSDIR):
	@mkdir -p $@
