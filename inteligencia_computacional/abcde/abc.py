import cv2
import math
import numpy as np
import ctypes
from numpy.ctypeslib import ndpointer
from matplotlib import pyplot as plt
import skfuzzy as fuzzy
from skfuzzy import control as ctrl
import os

class ImageData:

    def __init__(self, tp, name, a, b, c, im_input):
        self.tp = tp
        self.name = name
        self.a = a
        self.b = b
        self.c = c
        self.im_input = im_input

    def print(self):
        print(self.name, " ", self.a, " ", self.b, " ", self.c)

######################
## Processa as imagens
######################
def process(tp, filename):

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
    data = ImageData(tp, filename, abc[0] / height, abc[1] / height, abc[2], im_input)
    images.append(data)

    #cv2.imshow(filename, im_input_rot);
    #cv2.imshow("contorno_" + filename, im_contour_rot);
    #cv2.imshow("elipse_" + filename, im_ellipse_rot);
    #cv2.waitKey()
    return

########################################
## Normaliza os resultados entre 0 e 100
########################################
def normalize():

    sumA = 0
    sumB = 0
    sumC = 0
    maxA = images[0].a
    minA = images[0].a
    maxB = images[0].b
    minB = images[0].b
    maxC = images[0].c
    minC = images[0].c
    for im in images:
        maxA = max(maxA, im.a)
        minA = min(minA, im.a)
        maxB = max(maxB, im.b)
        minB = min(minB, im.b)
        maxC = max(maxC, im.c)
        minC = min(minC, im.c)
        sumA = sumA + im.a
        sumB = sumB + im.b
        sumC = sumC + im.c
        difA = maxA - minA
        difB = maxB - minB
        difC = maxC - minC
    for im in images:
        im.a = int(( im.a - minA ) / difA * 100)
        im.b = int(( im.b - minB ) / difB * 100)
        im.c = int(( im.c - minC ) / difC * 100)
        #im.print()
    avgA = sumA / len(images)
    avgB = sumB / len(images)
    avgC = sumC / len(images)
    global centerA, centerB, centerC
    centerA = int(( avgA - minA ) / difA * 100)
    centerB = int(( avgB - minB ) / difB * 100)
    centerC = int(( avgC - minC ) / difC * 100)

    return

##########################
## Inicio do processamento
##########################

# configura acesso a biblioteca em C
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
centerA = 0
centerB = 0
centerC = 0

# processa as imagens
'''
for i in range(1, 10):
    for tp in [ "b", "m" ]:
        name = "images/" + tp + str(i) + ".jpg"
        process(tp, name)
'''
for f in os.listdir("images/benign"):
    name = "images/benign/" + f
    process("b", name)
for f in os.listdir("images/malignant"):
    name = "images/malignant/" + f
    process("m", name)
#process("images/b1.jpg")
#process("images/m3.jpg")

# normaliza os resultados entre 0 e 100
normalize()

##### FUZZY #####

# definicao de antecedentes
a = ctrl.Antecedent(np.arange(0, 101, 1), 'Assimetria')
b = ctrl.Antecedent(np.arange(0, 101, 1), 'Borda')
c = ctrl.Antecedent(np.arange(0, 101, 1), 'Cores')

# definicao dos termos de A
a['baixa'] = fuzzy.trimf(a.universe, [0, 0, centerA])
a['media'] = fuzzy.trimf(a.universe, [centerA/2, centerA, centerA/2*3])
a['alta'] = fuzzy.trimf(a.universe, [centerA, 100, 100])
#a.view()
#fig = plt.gcf()
#fig.canvas.set_window_title('Assimetria')

# definicao dos termos de B
b['baixa'] = fuzzy.trimf(b.universe, [0, 0, centerB])
b['media'] = fuzzy.trimf(b.universe, [centerB/2, centerB, centerB/2*3])
b['alta'] = fuzzy.trimf(b.universe, [centerB, 100, 100])
#b.view()
#fig = plt.gcf()
#fig.canvas.set_window_title('Borda')

# definicao dos termos de C
c['baixa'] = fuzzy.trimf(c.universe, [0, 0, centerC])
c['media'] = fuzzy.trimf(c.universe, [centerC/2, centerC, centerC/2*3])
c['alta'] = fuzzy.trimf(c.universe, [centerC, 100, 100])
#c.view()
#fig = plt.gcf()
#fig.canvas.set_window_title('Cor')

# consequente
m = ctrl.Consequent(np.arange(0, 101, 1), 'Melanoma')
m['muito_baixa'] = fuzzy.trapmf(m.universe, [0, 0, 10, 30])
m['baixa'] = fuzzy.trimf(m.universe, [10, 30, 50])
m['media'] = fuzzy.trimf(m.universe, [30, 50, 70])
m['alta'] = fuzzy.trimf(m.universe, [50, 70, 90])
m['muito_alta'] = fuzzy.trapmf(m.universe, [70, 90, 100, 100])
#m.view()
#fig = plt.gcf()
#fig.canvas.set_window_title('Melanoma')

# definicao das regras

##   A   B   C               
#1   3   3   3   9   15  MA 
#2   3   3   2   8   14  MA
r1 = ctrl.Rule(a['alta'] & b['alta'] & ~c['baixa'], m['muito_alta'])

#3   3   3   1   7   13  A
r2 = ctrl.Rule(a['alta'] & b['alta'] & c['baixa'], m['alta'])

#4   3   2   3   8   13  A
#5   3   2   2   7   12  A
r3 = ctrl.Rule(a['alta'] & b['media'] & ~c['baixa'], m['alta'])

#6   3   2   1   6   11  M
r4 = ctrl.Rule(a['alta'] & b['media'] & c['alta'], m['media'])

#7   3   1   3   7   11  M
#8   3   1   2   6   10  M
#9   3   1   1   5   9   M
r5 = ctrl.Rule(a['alta'] & b['baixa'], m['media'])

#10  2   3   3   8   13  A
#11  2   3   2   7   12  A
r6 = ctrl.Rule(a['media'] & b['alta'] & ~c['baixa'], m['alta'])

#12  2   3   1   6   11  M
r7 = ctrl.Rule(a['media'] & b['alta'] & c['baixa'], m['media'])

#13  2   2   3   7   11  M
#14  2   2   2   6   10  M
#15  2   2   1   5   9   M
r8 = ctrl.Rule(a['media'] & b['media'], m['media'])

#16  2   1   3   6   9   M
r9 = ctrl.Rule(a['media'] & b['baixa'] & c['alta'], m['media'])

#17  2   1   2   5   8   B
#18  2   1   1   4   7   B
r10 = ctrl.Rule(a['media'] & b['baixa'] & ~c['alta'], m['baixa'])

#19  1   3   3   7   11  M
#20  1   3   2   6   10  M
#21  1   3   1   5   9   M
r11 = ctrl.Rule(a['baixa'] & b['alta'], m['media'])

#22  1   2   3   6   9   M
r12 = ctrl.Rule(a['baixa'] & b['media'] & c['alta'], m['media'])

#23  1   2   2   5   8   B
#24  1   2   1   4   7   B
r13 = ctrl.Rule(a['baixa'] & b['media'] & ~c['baixa'], m['baixa'])

#25  1   1   3   5   7   B
r14 = ctrl.Rule(a['baixa'] & b['baixa'] & c['alta'], m['baixa'])

#26  1   1   2   4   6   MB  2
#27  1   1   1   3   5   MB  2
r15 = ctrl.Rule(a['baixa'] & b['baixa'] & ~c['alta'], m['muito_baixa'])

# cria o simulador
controlador = ctrl.ControlSystem([r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15])
simulador = ctrl.ControlSystemSimulation(controlador)

# faz as simulacoes
false_positives=0
false_negatives=0
n=0
for im in images:
    #im.print()
    simulador.input['Assimetria'] = im.a
    simulador.input['Borda'] = im.b
    simulador.input['Cores'] = im.c
    simulador.compute()
    result = simulador.output['Melanoma']
    if im.tp == "b":
        if result > 50:
            false_positives = false_positives + 1
    else:
        if result < 50:
            false_negatives = false_negatives + 1
    n = n + 1
    im.print()
    print(str(n) + " " + str(result), " fp=", str(false_positives), " fn=", str(false_negatives))
    #a.view(sim=simulador)
    #b.view(sim=simulador)
    #c.view(sim=simulador)
    #cv2.imshow(im.name, im.im_input)
    #cv2.waitKey()
    #m.view(sim=simulador)
    #plt.show()
