INCS=-I /usr/include/python3.6m
CC=gcc -g

all: libabc.so

OBJS=abc.o

clean:
	rm -f libabc.so $(OBJS)

abc.o: abc.c
	$(CC) -fPIC -c abc.c

libabc.so: $(OBJS)
	$(CC) $(OBJS) -shared -o libabc.so

