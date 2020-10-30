import cv2
import math
import numpy as np
import ctypes
from numpy.ctypeslib import ndpointer
from matplotlib import pyplot as plt

class ImageData:

    def __init__(self, name, a, b, c):
        self.name = name
        self.a = a
        self.b = b
        self.c = c

def process(filename):

    # carrega a imagem
    im_input = cv2.imread(filename)

    # converte para cinza
    im_gray = cv2.cvtColor(im_input, cv2.COLOR_BGR2GRAY)

    # suaviza a imagem
    im_blur = cv2.GaussianBlur(im_gray, (3, 3), 0)

    # separa a frente do fundo
    ret, im_bw = cv2.threshold(im_blur, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)

    # identifica contornos
    imgEdge, contours, hierarchy = cv2.findContours(im_bw, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
    #im_contours = cv2.drawContours(img, contours, -1, (0, 0, 255), 2)

    # procura o contorno com maior area
    big_contour = max(contours, key=cv2.contourArea)

    # procura uma elipse proxima ao contorno
    ellipse = cv2.fitEllipse(big_contour)

    # preenche o contorno de verde
    im_contour = im_input.copy() 
    im_contour = cv2.drawContours(im_contour, [ big_contour ], -1, (0, 255, 0), -1)

    # preenche a elipse de verde
    im_ellipse = im_input.copy() 
    cv2.ellipse(im_ellipse, ellipse, (0, 255, 0), -1)

    # recupera informacoes da elipse
    center, diagonal, angle = ellipse

    # marca o centro
    #cv2.circle(im_output, (int(xc), int(yc)), 5, (255, 0, 0), -1)

    # marca o eixo principal
    rmajor = max(diagonal[0], diagonal[1]) / 2
    if angle > 90:
        angle = angle - 90
    else:
        angle = angle + 90
    xtop = center[0] + math.cos(math.radians(angle)) * rmajor
    ytop = center[1] + math.sin(math.radians(angle)) * rmajor
    xbot = center[0] + math.cos(math.radians(angle+180)) * rmajor
    ybot = center[1] + math.sin(math.radians(angle+180)) * rmajor
    cv2.line(im_contour, (int(xtop), int(ytop)), (int(xbot), int(ybot)), (255, 0, 0), 2)

    # calcula a matriz de rotacao
    rot_mat = cv2.getRotationMatrix2D(center, angle-90, 1.0)

    # rotaciona as imagens
    im_input_rot = cv2.warpAffine(im_input, rot_mat, im_input.shape[1::-1], flags=cv2.INTER_LINEAR)
    im_contour_rot = cv2.warpAffine(im_contour, rot_mat, im_contour.shape[1::-1], flags=cv2.INTER_LINEAR)
    im_ellipse_rot = cv2.warpAffine(im_ellipse, rot_mat, im_ellipse.shape[1::-1], flags=cv2.INTER_LINEAR)

    # mostra as imagens

    # calcula os valores crisp de A, B e C
    abc = np.zeros((3, 1), dtype=np.int32)
    abc_c(im_input_rot, im_contour_rot, im_ellipse_rot, im_input_rot.shape[1], im_input_rot.shape[0], int(center[0]), abc)
    #print(max(diagonal[0], diagonal[1]))
    #print(abc[0])
    #print(abc[0] / max(diagonal[0], diagonal[1]))
    #print(abc[1])
    #print(abc[1] / max(diagonal[0], diagonal[1]))
    #print(abc[2])

    height = max(diagonal[0], diagonal[1])
    data = ImageData(filename, abc[0] / height, abc[1] / height, abc[2])

    #cv2.imshow(filename, im_input_rot);
    #cv2.imshow("contorno_" + filename, im_contour_rot);
    #cv2.imshow("elipse_" + filename, im_ellipse_rot);
    #cv2.waitKey()
    return

lib = ctypes.cdll.LoadLibrary("./libabc.so")
abc_c = lib.abc
abc_c.restype = None
abc_c.argtypes = [
                  ndpointer(ctypes.c_uint8, flags="C_CONTIGUOUS"),  # image
                  ndpointer(ctypes.c_uint8, flags="C_CONTIGUOUS"),  # contour
                  ndpointer(ctypes.c_uint8, flags="C_CONTIGUOUS"),  # ellipse
                  ctypes.c_int,                                     # width
                  ctypes.c_int,                                     # height
                  ctypes.c_int,                                     # x-center
                  ndpointer(ctypes.c_int32, flags="C_CONTIGUOUS") ] # abc

'''
for i in range(1, 10):
    for tipo in [ "b", "m" ]:
        name = "images/" + tipo + str(i) + ".jpg"
        process(name)
'''
process("images/b1.jpg")
#process("images/m3.jpg")

