import cv2
import ctypes
from numpy.ctypeslib import ndpointer

def processa(arquivo):

    img = cv2.imread(arquivo)
    #cv2.imshow(arquivo, img);
    #cv2.waitKey()

    im_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    im_blur = cv2.GaussianBlur(im_gray, (3, 3), 0)
    ret, th = cv2.threshold(im_blur, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)
    imgEdge, contours, hierarchy = cv2.findContours(th, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
    #image = cv2.drawContours(img, contours, -1, (0, 0, 255), 2)

    bigger_idx = 0
    bigger_len = 0
    for idx in range(0, len(contours)):
        cont = contours[idx]
        cont_len = len(cont)
        if cont_len > bigger_len:
            bigger_idx = idx
            bigger_len = cont_len

    cont = contours[bigger_idx]
    elps = cv2.fitEllipse(cont)
    image = cv2.drawContours(img, [ cont ], -1, (0, 0, 255), 2)
    cv2.ellipse(image, elps, (0, 255, 0), 2)
    cv2.imshow(arquivo, image);
    cv2.waitKey()
    return

#'''
for i in range(1, 10):
    for tipo in [ "b", "m" ]:
        nome = "imagens/" + tipo + str(i) + ".jpg"
        processa(nome)
#'''

#processa("imagens/b1.jpg")
