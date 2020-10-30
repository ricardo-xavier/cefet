#include <stdio.h>
#include <stdlib.h>

#define uint8 unsigned char

int width;
int height;

uint8 getvalue(uint8 *img, int x, int y, int plane) {
    return *(img + (y * width + x) * 3 + plane);
}

void abc(uint8 *img, uint8 *contour, uint8 *ellipse, int _width, int _height, int center, int *abc) {

    width = _width;
    height = _height;
    //fprintf(stderr, "abc %d %d %d\n", width, height, center);

    int a = 0;
    int b = 0;
    int c = 0;
    int n = 0;
    int B = 0;
    int G = 0;
    int R = 0;

    for (int y=0; y<height; y++) {
        int l = 0;
        int r = 0;
        for (int x=0; x<width; x++) {
            uint8 vc = getvalue(contour, x, y, 1);
            uint8 ve = getvalue(ellipse, x, y, 1);
            if (vc == 255) {
                if (x < center) {
                    l++;
                } else if (x > center) {
                    r++;
                }
                if (ve != 255) {
                    b++;
                }
                uint8 b = getvalue(img, x, y, 0);
                uint8 g = getvalue(img, x, y, 1);
                uint8 r = getvalue(img, x, y, 2);
                B += b;
                G += g;
                R += r;
                n++;
            } else {
                if (ve == 255) {
                    b++;
                }
            }
        }
        int dif = abs(l - r);
        a += dif;
    }

    int _b = B / n;
    int _g = G / n;
    int _r = R / n;
    for (int y=0; y<height; y++) {
        for (int x=0; x<width; x++) {
            uint8 vc = getvalue(contour, x, y, 1);
            uint8 ve = getvalue(ellipse, x, y, 1);
            if (vc == 255) {
                uint8 b = getvalue(img, x, y, 0);
                uint8 g = getvalue(img, x, y, 1);
                uint8 r = getvalue(img, x, y, 2);
                c += abs(b - _b) + abs(g - _g) + abs(r - _r);
            }
        }
    }

    abc[0] = a;
    abc[1] = b;
    abc[2] = c / n;

}

