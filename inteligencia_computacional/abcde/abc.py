import cv2
import ctypes
from numpy.ctypeslib import ndpointer

def processa(arquivo):

    img = cv2.imread(arquivo)
    #cv2.imshow(arquivo, img);
    #cv2.waitKey()

    # separa a frente(preto) do fundo(branco)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    (thresh, bw) = cv2.threshold(gray, 128, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)
    cv2.imshow(arquivo, bw)

    # retorna os segmentos da imagem
    num_segments = seed_growing_c(bw, bw.shape[1], bw.shape[0])
    print(num_segments)

    cv2.waitKey()
    return

'''
for i in range(1, 11):
    for tipo in [ "b", "m" ]:
        nome = "imagens/" + tipo + str(i) + ".jpg"
        processa(nome)
'''

lib = ctypes.cdll.LoadLibrary("./libabc.so")
seed_growing_c = lib.seed_growing
seed_growing_c.restype = int
seed_growing_c.argtypes = [
    ndpointer(ctypes.c_uint8, flags="C_CONTIGUOUS"), # image
    ctypes.c_int,                                    # width
    ctypes.c_int ]                                   # height

processa("imagens/b1.jpg")
