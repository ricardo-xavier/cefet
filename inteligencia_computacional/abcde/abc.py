import cv2
import math
import numpy as np
import ctypes
from numpy.ctypeslib import ndpointer
from matplotlib import pyplot as plt
import skfuzzy as fuzzy
from skfuzzy import control as ctrl

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

    height = max(diagonal[0], diagonal[1])
    data = ImageData(filename, abc[0] / height, abc[1] / height, abc[2])
    images.append(data)

    #cv2.imshow(filename, im_input_rot);
    #cv2.imshow("contorno_" + filename, im_contour_rot);
    #cv2.imshow("elipse_" + filename, im_ellipse_rot);
    #cv2.waitKey()
    return

def minmax():
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

images = []

#'''
for i in range(1, 10):
    for tipo in [ "b", "m" ]:
        name = "images/" + tipo + str(i) + ".jpg"
        process(name)
#'''
#process("images/b1.jpg")
#process("images/m3.jpg")

maxA = 0
minA = 999999
maxB = 0
minB = 999999
maxC = 0
minC = 999999
for im in images:
    maxA = max(maxA, im.a)
    minA = min(minA, im.a)
    maxB = max(maxB, im.b)
    minB = min(minB, im.b)
    maxC = max(maxC, im.c)
    minC = min(minC, im.c)
maxA = math.ceil(maxA)
minA = math.floor(minA)
maxB = math.ceil(maxB)
minB = math.floor(minB)
maxC = math.ceil(maxC)
minC = math.floor(minC)

# definicao de antecedentes
a = ctrl.Antecedent(np.arange(minA, maxA+1, 1), 'a')
b = ctrl.Antecedent(np.arange(minB, maxB+1, 1), 'b')
c = ctrl.Antecedent(np.arange(minC, maxC+1, 1), 'c')

# definicao dos termos de A
f = int((maxA - minA) / 4)
a['baixa'] = fuzzy.trapmf(a.universe, [minA, minA, minA+f, minA+f*2])
a['media'] = fuzzy.trimf(a.universe, [minA+f, minA+f*2, minA+f*3])
a['alta'] = fuzzy.trapmf(a.universe, [minA+f*2, minA+f*3, maxA, maxA])
a.view()
fig = plt.gcf()
fig.canvas.set_window_title('Assimetria')

# definicao dos termos de B
f = int((maxB - minB) / 4)
b['baixa'] = fuzzy.trapmf(b.universe, [minB, minB, minB+f, minB+f*2])
b['media'] = fuzzy.trimf(b.universe, [minB+f, minB+f*2, minB+f*3])
b['alta'] = fuzzy.trapmf(b.universe, [minB+f*2, minB+f*3, maxB, maxB])
b.view()
fig = plt.gcf()
fig.canvas.set_window_title('Borda')

# definicao dos termos de C
f = int((maxC - minC) / 4)
c['baixa'] = fuzzy.trapmf(c.universe, [minC, minC, minC+f, minC+f*2])
c['media'] = fuzzy.trimf(c.universe, [minC+f, minC+f*2, minC+f*3])
c['alta'] = fuzzy.trapmf(c.universe, [minC+f*2, minC+f*3, maxC, maxC])
c.view()
fig = plt.gcf()
fig.canvas.set_window_title('Cores')

plt.show()
