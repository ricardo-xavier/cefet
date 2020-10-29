INCS=-I /usr/include/python3.6m
CC=gcc -g

all: libabc.so

OBJS=seed_growing.o

clean:
	rm -f libabc.so $(OBJS)

seed_growing.o: seed_growing.c
	$(CC) -fPIC -c seed_growing.c

libabc.so: $(OBJS)
	$(CC) $(OBJS) -shared -o libabc.so

