#include <stdio.h>

#define uint8 unsigned char

#define FRENTE     0
#define FUNDO      255
#define PROCESSADO 128

typedef struct {
    int pixels;
    int x1; 
    int y1;
    int x2;
    int y2;
} rect_t;

rect_t rect;
uint8 *img;
int width;
int height;

uint8 getvalue(int x, int y) {
    return *(img + y * width + x);
}

void setvalue(int x, int y, uint8 v) {
    *(img + y * width + x) = v;
}

void grow(int x, int y) {

    setvalue(x, y, PROCESSADO);
    rect.pixels++;
    if (x < rect.x1) {
        rect.x1 = x;
    }
    if (x > rect.x2) {
        rect.x2 = x;
    }
    if (y < rect.y1) {
        rect.y1 = y;
    }
    if (y > rect.y2) {
        rect.y2 = y;
    }

    if ((x < (width - 1)) && (getvalue(x+1, y) == FRENTE)) {
        grow(x+1, y);
    }
    if ((x < (width - 1)) && (y < (height - 1)) && (getvalue(x+1, y+1) == FRENTE)) {
        grow(x+1, y+1);
    }
    if ((y < (height - 1)) && (getvalue(x, y+1) == FRENTE)) {
        grow(x, y+1);
    }
    if ((x > 0) && (y < (height - 1)) && (getvalue(x-1, y+1) == FRENTE)) {
        grow(x-1, y+1);
    }
    if ((x > 0) && (getvalue(x-1, y) == FRENTE)) {
        grow(x-1, y);
    }
    if ((x > 0) && (y > 0) && (getvalue(x-1, y-1) == FRENTE)) {
        grow(x-1, y-1);
    }
    if ((y > 0) && (getvalue(x, y-1) == FRENTE)) {
        grow(x, y-1);
    }
    if ((x < (width - 1)) && (y > 0) && (getvalue(x+1, y-1) == FRENTE)) {
        grow(x+1, y-1);
    }
}

int seed_growing(uint8 *_img, int _width, int _height) {

    img = _img;
    width = _width;
    height = _height;
    fprintf(stderr, "seed_growing %d %d\n", width, height);

    for (int y=0; y<height; y++) {
        for (int x=0; x<width; x++) {

            uint8 v = getvalue(x, y);

            // encontrou um ponto preto (frente)
            if (v == FRENTE) {
                rect.pixels = 0;
                rect.x1 = x;
                rect.x2 = x;
                rect.y1 = y;
                rect.y2 = y;
                grow(x, y);
                fprintf(stderr, "%d %d %d %d-%d %d-%d\n", y, x, rect.pixels, rect.x1, rect.x2, rect.y1, rect.y2);
            }

        }
    }

    return 0;
}

